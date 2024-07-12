package com.whk.constant;

/**
 * 代码生成通用常量
 */
public class GenConstants {
    /**
     * 单表（增删改查）
     */
    public static final String TPL_CRUD = "crud";

    /**
     * 树表（增删改查）
     */
    public static final String TPL_TREE = "tree";

    /**
     * 主子表（增删改查）
     */
    public static final String TPL_SUB = "sub";

    /**
     * 树编码字段
     */
    public static final String TREE_CODE = "treeCode";

    /**
     * 树父编码字段
     */
    public static final String TREE_PARENT_CODE = "treeParentCode";

    /**
     * 树名称字段
     */
    public static final String TREE_NAME = "treeName";

    /**
     * 上级菜单ID字段
     */
    public static final String PARENT_MENU_ID = "parentMenuId";

    /**
     * 上级菜单名称字段
     */
    public static final String PARENT_MENU_NAME = "parentMenuName";

    /**
     * 数据库字符串类型
     */
    public static final String[] COLUMNTYPE_STR = {"char", "varchar", "nvarchar", "varchar2"};

    /**
     * 数据库文本类型
     */
    public static final String[] COLUMNTYPE_TEXT = {"tinytext", "text", "mediumtext", "longtext"};

    /**
     * 数据库时间类型
     */
    public static final String[] COLUMNTYPE_TIME = {"datetime", "time", "date", "timestamp"};

    /**
     * 数据库数字类型
     */
    public static final String[] COLUMNTYPE_NUMBER = {"tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bigint", "float", "double", "decimal"};

    /**
     * 数据库json类型
     */
    public static final String[] COLUMNTYPE_JSON = {"json"};

    /**
     * 页面不需要插入的字段
     */
    public static final String[] COLUMNNAME_NOT_INSERT = {"id", "create_by", "create_time", "del_flag", "update_by", "update_time"};

    /**
     * 页面不需要编辑字段
     */
    public static final String[] COLUMNNAME_NOT_EDIT = {"id", "create_by", "create_time", "del_flag", "update_by", "update_time"};

    /**
     * 页面不需要的列表字段
     */
    public static final String[] COLUMNNAME_NOT_LIST = {"del_flag"};

    /**
     * 页面不需要查询字段
     */
    public static final String[] COLUMNNAME_NOT_QUERY = {"del_flag"};

    /**
     * 模糊匹配的字段
     */
    public static final String[] COLUMNNAME_QUERY_LIKE = {"name", "remark", "addr", "address", "phone"};

    /**
     * 模糊匹配的列描述
     */
    public static final String[] COLUMNNAME_QUERY_LIKE_COLUMN = {"姓名", "名称", "名字", "地点", "地址", "电话", "邮编"};

    /**
     * Entity基类字段
     */
    public static final String[] BASE_ENTITY = {"createBy", "createTime", "updateBy", "updateTime"}; /*, "remark"*/

    /**
     * Tree基类字段
     */
    public static final String[] TREE_ENTITY = {"parentName", "parentId", "orderNum", "ancestors"};

    /**
     * 文本框
     */
    public static final String HTML_INPUT = "input";

    /**
     * 文本域
     */
    public static final String HTML_TEXTAREA = "textarea";

    /**
     * 下拉框
     */
    public static final String HTML_SELECT = "select";

    /**
     * 单选框
     */
    public static final String HTML_RADIO = "radio";

    /**
     * 复选框
     */
    public static final String HTML_CHECKBOX = "checkbox";

    /**
     * 日期控件
     */
    public static final String HTML_DATETIME = "datetime";

    /**
     * json格式
     */
    public static final String HTML_JSON = "input";/*json*/

    /**
     * 图片上传控件
     */
    public static final String HTML_IMAGE_UPLOAD = "imageUpload";

    /**
     * 文件上传控件
     */
    public static final String HTML_FILE_UPLOAD = "fileUpload";

    /**
     * 富文本控件
     */
    public static final String HTML_EDITOR = "editor";

    /**
     * 字符串类型
     */
    public static final String TYPE_STRING = "String";

    /**
     * 整型
     */
    public static final String TYPE_INTEGER = "Integer";

    /**
     * 长整型
     */
    public static final String TYPE_LONG = "Long";

    /**
     * 浮点型
     */
    public static final String TYPE_DOUBLE = "Double";

    /**
     * 高精度计算类型
     */
    public static final String TYPE_BIGDECIMAL = "BigDecimal";

    /**
     * 时间类型
     */
    public static final String TYPE_DATE = "Date";

    /**
     * 时间类型
     */
    public static final String TYPE_JSON = "JSONObject";

    /**
     * 模糊查询
     */
    public static final String QUERY_LIKE = "LIKE";

    /**
     * 小于等于
     */
    public static final String QUERY_LTE = "LTE";

    /**
     * 相等查询
     */
    public static final String QUERY_EQ = "EQ";

    /**
     * 需要
     */
    public static final String REQUIRE = "1";

}