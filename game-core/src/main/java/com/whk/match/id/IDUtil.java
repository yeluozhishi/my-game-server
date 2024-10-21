package com.whk.match.id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IDUtil {

    private static Hashtable<String, String> AREA_CODE = null;

    /**
     * 2022年1月1日0点整
     */
    public static final long TIME_MILLS_2022_1_1_0_0_0_0 = 1640966400000L;


    private static final AtomicInteger BUFFER_ID = new AtomicInteger(1);

    private static final AtomicInteger DUPLICATE_LINE = new AtomicInteger(100000);

    protected static final ConcurrentHashMap<Integer, Creator> CREATOR_MAP = new ConcurrentHashMap<>();

    private static int serverId;

    private static int operationId;

    public static void init(int serverId, int operationId) {
        init(serverId, operationId, 0);
    }

    public static void init(int serverId, int operationId, int duplicateId) {
        if (serverId > 16383 || operationId > 511) {
            throw new RuntimeException("sid最大支持16383，平台id最大支持511，请合理配置.");
        }
        IDUtil.serverId = serverId;
        IDUtil.operationId = operationId;
        //初始化一个用于默认生成器，用于不需要存储的id生成
        CREATOR_MAP.put(0, new Creator(0, 0, 0));
        if (duplicateId > 0) {
            DUPLICATE_LINE.set(duplicateId);
        }

    }

    /**
     * 获取id
     *
     * @param type
     * @return
     */
    public static long getId(int type) {
        Creator creator = CREATOR_MAP.get(type);
        if (creator == null) {
            creator = new Creator(type, serverId, operationId);
            Creator existCreator = CREATOR_MAP.putIfAbsent(type, creator);
            if (existCreator != null) {
                creator = existCreator;
            }
        }
        return creator.get();
    }

    static class Creator {

        private final int type;

        private final int serverId;

        private final int operationId;

        private int id = 0;

        public Creator(int type, int serverId, int operationId) {
            this.serverId = serverId;
            this.operationId = operationId;
            this.type = type;
        }

        /**
         * 基于2022年1月1日0点整的时间秒数
         */
        private long lastSecond = getTimeStampFrom20220101();

        //9(平台-511） + 14(服务器id-16383) + 29（时间戳）(17年) + 11（自增-2047）
        public long get() {

            synchronized (this) {
                long second = getTimeStampFrom20220101();
                // ID增1
                id += 1;

                int max = 2047;
                if (id > max) {
                    // 如果ID大于2047 这里其实是 2的11次方 = 2047 因为自增ID只能占11位，所以不能超过2047
                    // ID大于2047后id复位，如果时间不增1，那么将会产生重复
                    id = 0;
                    // 每过2047当前秒数就增1
                    lastSecond += 1L;

                    System.out.println("每秒生成id超过上限,type-> " + type);
                }

                if (second > lastSecond) {
                    // 当前时间大于自增时间，那么就更新自增时间为当前时间
                    lastSecond = second;
                    //log.info("更新自增时间：{}",lastSecond);
                    //id重新从0开始
                    id = 0;
                } else if (second < lastSecond) {
                    // ID获取速度过快，1秒内获取了额超过2047个id的时候，时间需要使用自增时间
                    second = lastSecond;
                    //log.info("使用自增时间：{}",second);
                }
                return (operationId & 0B1111_1111_1L) << 54 |
                        (serverId & 0B1111_1111_1111_11L) << 40 |
                        (second & 0B1111_1111_1111_1111_1111_1111_1111_1L) << 11 |
                        (id & 0B1111_1111_111L);
            }

        }
    }


    /**
     * 获取基于2022年1月1日0点整的时间秒数
     *
     * @return long 秒数
     */
    private static long getTimeStampFrom20220101() {
        return (System.currentTimeMillis() - TIME_MILLS_2022_1_1_0_0_0_0) / 1000L;
    }

    public static int getBuffId() {
        return BUFFER_ID.incrementAndGet();
    }

    public static int getDuplicateLine() {
        return DUPLICATE_LINE.incrementAndGet();
    }


    /**
     * 功能：身份证的有效验证
     *
     * @param idNumber 身份证号
     * @return 有效：返回"" 无效：返回String信息
     */
    public static String idCardValidate(String idNumber) throws ParseException {
        String errorInfo;// 记录错误信息
        String[] valCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        String ai;
        // ================ 号码的长度 15位或18位 ================
        int adultAge = 18;
        int number = 15;
        if (idNumber.length() != number && idNumber.length() != adultAge) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后一位都为数字 ================
        int oneSeven = 17;
        if (idNumber.length() == adultAge) {
            ai = idNumber.substring(0, oneSeven);
        } else {
            ai = idNumber.substring(0, 6) + "19" + idNumber.substring(6, number);
        }
        if (!isNumeric(ai)) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        // 年份
        String strYear = ai.substring(6, 10);
        // 月份
        int lengthSub = 12;
        String strMonth = ai.substring(10, lengthSub);
        // 月份
        String strDay = ai.substring(lengthSub, 14);
        if (!checkDateFormat(strYear + "-" + strMonth + "-" + strDay)) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        Calendar gc = Calendar.getInstance();
        gc.setTime(new Date());
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        int maxYear = 150;
        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > maxYear || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
            errorInfo = "身份证生日不在有效范围。";
            return errorInfo;
        }

        if (Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(strYear) < adultAge) {
            return "您属于未成年人,无法通过防沉迷认证";
        }

        if (Integer.parseInt(strMonth) > lengthSub || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        int maxMonthDay = 31;
        if (Integer.parseInt(strDay) > maxMonthDay || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable<String, String> h = getAreaCode();
        int endIndex = 2;
        if (h.get(ai.substring(0, endIndex)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int totalMulAiWi = 0;
        for (int i = 0; i < oneSeven; i++) {
            totalMulAiWi = totalMulAiWi + Integer.parseInt(String.valueOf(ai.charAt(i))) * Integer.parseInt(wi[i]);
        }
        int modValue = totalMulAiWi % 11;
        String strVerifyCode = valCodeArr[modValue];
        ai = ai + strVerifyCode;

        if (idNumber.length() == adultAge) {
            if (!ai.equals(idNumber)) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        // =====================(end)=====================
        return "";
    }

    public static boolean isNumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        } else {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * 验证日期字符串是否是YYYY-MM-dd格式或者YYYYMMdd
     *
     * @param str str
     * @return boolean
     */
    public static boolean checkDateFormat(String str) {
        boolean flag = false;
        String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regex);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }


    /**
     * 功能：设置地区编码
     *
     * @return 对象
     */
    private static Hashtable<String, String> getAreaCode() {
        if (AREA_CODE == null) {
            Hashtable<String, String> table = new Hashtable<>();
            table.put("11", "北京");
            table.put("12", "天津");
            table.put("13", "河北");
            table.put("14", "山西");
            table.put("15", "内蒙古");
            table.put("21", "辽宁");
            table.put("22", "吉林");
            table.put("23", "黑龙江");
            table.put("31", "上海");
            table.put("32", "江苏");
            table.put("33", "浙江");
            table.put("34", "安徽");
            table.put("35", "福建");
            table.put("36", "江西");
            table.put("37", "山东");
            table.put("41", "河南");
            table.put("42", "湖北");
            table.put("43", "湖南");
            table.put("44", "广东");
            table.put("45", "广西");
            table.put("46", "海南");
            table.put("50", "重庆");
            table.put("51", "四川");
            table.put("52", "贵州");
            table.put("53", "云南");
            table.put("54", "西藏");
            table.put("61", "陕西");
            table.put("62", "甘肃");
            table.put("63", "青海");
            table.put("64", "宁夏");
            table.put("65", "新疆");
            table.put("71", "台湾");
            table.put("81", "香港");
            table.put("82", "澳门");
            table.put("91", "国外");
            AREA_CODE = table;
        }
        return AREA_CODE;
    }

}