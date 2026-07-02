package com.whk.actor.attribute;

import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attribute {
    @Tag(1)
    @FieldDescription(desc = "属性:力量")
    protected long strength;
    @Tag(2)
    @FieldDescription(desc = "属性:敏捷")
    protected long agility;
    @Tag(3)
    @FieldDescription(desc = "属性:体力")
    protected long vitality;
    @Tag(4)
    @FieldDescription(desc = "属性:智力")
    protected long energy;
    @Tag(5)
    @FieldDescription(desc = "属性:统帅")
    protected long leadership;
    @Tag(6)
    @FieldDescription(desc = "属性:经验增加率-----------------万分比")
    protected long experienceRate;
    @Tag(7)
    @FieldDescription(desc = "属性:金币增加率-----------------万分比")
    protected long moneyAmountRate;
    @Tag(8)
    @FieldDescription(desc = "属性:攻击速度")
    protected long attackSpeed = 0;
    @Tag(9)
    @FieldDescription(desc = "属性:移动速度")
    protected long moveSpeed = 200;
    @Tag(10)
    @FieldDescription(desc = "属性:回收金币增加率-----------------万分比")
    protected long sellGoldUpRatio;
    @Tag(11)
    @FieldDescription(desc = "属性:最大生命")
    protected long maximumHealth;
    @Tag(12)
    @FieldDescription(desc = "属性:最大魔力")
    protected long maximumMana;
    @Tag(13)
    @FieldDescription(desc = "属性:最大护盾")
    protected long maximumShield;
    @Tag(14)
    @FieldDescription(desc = "属性:最大技能值")
    protected long maximumAbility;
    @Tag(15)
    @FieldDescription(desc = "属性:生命回复比例-----------------万分比")
    protected long healthRecoveryMultiplier;
    @Tag(16)
    @FieldDescription(desc = "属性:魔力回复比例-----------------万分比")
    protected long manaRecoveryMultiplier;
    @Tag(17)
    @FieldDescription(desc = "属性:护盾回复比例-----------------万分比")
    protected long shieldRecoveryMultiplier;
    @Tag(18)
    @FieldDescription(desc = "属性:技能值回复比例-----------------万分比")
    protected long abilityRecoveryMultiplier;
    @Tag(19)
    @FieldDescription(desc = "属性:生命回复绝对值")
    protected long healthRecoveryAbsolute;
    @Tag(20)
    @FieldDescription(desc = "属性:魔力回复绝对值")
    protected long manaRecoveryAbsolute;
    @Tag(21)
    @FieldDescription(desc = "属性:护盾回复绝对值")
    protected long shieldRecoveryAbsolute;
    @Tag(22)
    @FieldDescription(desc = "属性:技能值回复绝对值")
    protected long abilityRecoveryAbsolute;
    @Tag(23)
    @FieldDescription(desc = "属性:杀怪后生命回复比例-----------------万分比")
    protected long healthAfterMonsterKillMultiplier;
    @Tag(24)
    @FieldDescription(desc = "属性:杀怪后魔力回复比例-----------------万分比")
    protected long manaAfterMonsterKillMultiplier;
    @Tag(25)
    @FieldDescription(desc = "属性:杀怪后护盾回复比例-----------------万分比")
    protected long shieldAfterMonsterKillMultiplier;
    @Tag(26)
    @FieldDescription(desc = "属性:杀怪后技能值回复比例-----------------万分比")
    protected long abilityAfterMonsterKillMultiplier;
    @Tag(27)
    @FieldDescription(desc = "属性:杀怪后生命回复绝对值")
    protected long healthAfterMonsterKillAbsolute;
    @Tag(28)
    @FieldDescription(desc = "属性:杀怪后魔力回复绝对值")
    protected long manaAfterMonsterKillAbsolute;
    @Tag(29)
    @FieldDescription(desc = "属性:杀怪后护盾回复绝对值")
    protected long shieldAfterMonsterKillAbsolute;
    @Tag(30)
    @FieldDescription(desc = "属性:杀怪后技能值回复绝对值")
    protected long abilityAfterMonsterKillAbsolute;
    @Tag(31)
    @FieldDescription(desc = "属性:攻击成功率PVM")
    protected long attackRatePvm;
    @Tag(32)
    @FieldDescription(desc = "属性:攻击成功率PVP")
    protected long attackRatePvp;
    @Tag(33)
    @FieldDescription(desc = "属性:防御成功率PVM 统一防御率用")
    protected long defenseRatePvm;
    @Tag(34)
    @FieldDescription(desc = "属性:防御成功率PVP 数值暂不用")
    protected long defenseRatePvp;
    @Tag(35)
    @FieldDescription(desc = "属性:幸运一击率-----------------万分比")
    protected long criticalDamageChance;
    @Tag(36)
    @FieldDescription(desc = "属性:卓越一击率-----------------万分比")
    protected long excellentDamageChance;
    @Tag(37)
    @FieldDescription(desc = "属性:物理伤害加成最小浮动")
    protected long minimumPhysBaseDmg;
    @Tag(38)
    @FieldDescription(desc = "属性:物理伤害加成最大浮动")
    protected long maximumPhysBaseDmg;
    @Tag(39)
    @FieldDescription(desc = "属性:法术伤害加成最小浮动")
    protected long minimumWizBaseDmg;
    @Tag(40)
    @FieldDescription(desc = "属性:法术伤害加成最大浮动")
    protected long maximumWizBaseDmg;
    @Tag(41)
    @FieldDescription(desc = "属性:咒语伤害加成最小浮动")
    protected long minimumCurseBaseDmg;
    @Tag(42)
    @FieldDescription(desc = "属性:咒语伤害加成最大浮动")
    protected long maximumCurseBaseDmg;
    @Tag(43)
    @FieldDescription(desc = "属性:无视防御率-----------------万分比")
    protected long defenseIgnoreChance;
    @Tag(44)
    @FieldDescription(desc = "属性:基础防御值，减伤害")
    protected long defenseBase;
    @Tag(45)
    @FieldDescription(desc = "属性:防御值PVM 对怪防御")
    protected long defensePvm;
    @Tag(46)
    @FieldDescription(desc = "属性:防御值提升比例-----------------万分比")
    protected long defenseIncreaseWithEquippedShield;
    @Tag(47)
    @FieldDescription(desc = "属性:减伤-----------------万分比")
    protected long damageReceiveDecrement;
    @Tag(48)
    @FieldDescription(desc = "属性:额外总伤害加成-----------------万分比")
    protected long attackDamageIncrease;
    @Tag(49)
    @FieldDescription(desc = "属性:技能额外总伤害加成")
    protected long skillDamageBonus;
    @Tag(50)
    @FieldDescription(desc = "属性:技能额外总伤害加成比例-----------------万分比")
    protected long skillMultiplier;
    @Tag(51)
    @FieldDescription(desc = "属性:是否有双手武器")
    protected boolean twoHandedWeaponEquipped;
    @Tag(52)
    @FieldDescription(desc = "属性:双手武器提供伤害-----------------万分比")
    protected long twoHandedWeaponDamageIncrease;
    @Tag(53)
    @FieldDescription(desc = "属性:PVP增伤")
    protected long finalDamageIncreasePvp;
    @Tag(54)
    @FieldDescription(desc = "属性:两倍伤害概率-----------------万分比")
    protected long doubleDamageChance;
    @Tag(55)
    @FieldDescription(desc = "属性:无视护盾概率-----------------万分比")
    protected long shieldBypassChance;
    @Tag(56)
    @FieldDescription(desc = "属性:护盾伤害增加率-----------------万分比")
    protected long shieldDecreaseRateIncrease;
    @Tag(57)
    @FieldDescription(desc = "属性:护盾伤害减少率-----------------万分比")
    protected long shieldRateIncrease;
    @Tag(58)
    @FieldDescription(desc = "属性:伤害反弹率，反射收到伤害比例-----------------万分比")
    protected long damageReflection;
    @Tag(59)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，冰-----------------万分比")
    protected long iceResistance;
    @Tag(60)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，火-----------------万分比")
    protected long fireResistance;
    @Tag(61)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，水-----------------万分比")
    protected long waterResistance;
    @Tag(62)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，地-----------------万分比")
    protected long earthResistance;
    @Tag(63)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，风-----------------万分比")
    protected long windResistance;
    @Tag(64)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，毒-----------------万分比")
    protected long poisonResistance;
    @Tag(65)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，雷-----------------万分比")
    protected long lightningResistance;
    @Tag(66)
    @FieldDescription(desc = "属性:满魔回复率，被攻击时有概率回满魔-----------------万分比")
    protected long fullyRecoverManaAfterHitChance;
    @Tag(67)
    @FieldDescription(desc = "属性:满血回复率，被攻击时有概率回满血-----------------万分比")
    protected long fullyRecoverHealthAfterHitChance;
    @Tag(68)
    @FieldDescription(desc = "属性:抵抗无视防御几率-----------------万分比")
    protected long defenseIgnoreChanceResistance;
    @Tag(69)
    @FieldDescription(desc = "属性:抵抗SD无视几率-----------------万分比")
    protected long shieldBypassChanceResistance;
    @Tag(70)
    @FieldDescription(desc = "属性:抵抗双倍伤害几率-----------------万分比")
    protected long doubleDamageChanceResistance;
    @Tag(71)
    @FieldDescription(desc = "属性:抵抗卓越一击几率-----------------万分比")
    protected long excellentDamageChanceResistance;
    @Tag(72)
    @FieldDescription(desc = "属性:抵抗致命一击几率-----------------万分比")
    protected long criticalDamageBonusResistance;
    @Tag(73)
    @FieldDescription(desc = "属性:随等级变化的物理攻击")
    protected long physBaseDmgByLevel;
    @Tag(74)
    @FieldDescription(desc = "属性:随等级变化的魔法攻击力")
    protected long wizBaseDmgByLevel;

    @Tag(75)
    @FieldDescription(desc = "属性:每次攻击成功减少固定生命值")
    protected long reducedFixedHealthPerSuccessfulAttack;

    @Tag(76)
    @FieldDescription(desc = "属性:基础移动速度")
    protected long staticMoveSpeed;

    @Tag(77)
    @FieldDescription(desc = "属性:额外伤害-----------------万分比")
    protected long extraDamage;

    @Tag(78)
    @FieldDescription(desc = "属性:收到伤害减少-----------------万分比")
    protected long receivedDamageReduceRate;

    @Tag(79)
    @FieldDescription(desc = "属性:收到伤害减少固定值")
    protected long receivedDamageReduceRateFix;

    @Tag(80)
    @FieldDescription(desc = "属性:收到伤害减魔力万分比")
    protected long hurtReduceMPRate;

    @Tag(81)
    @FieldDescription(desc = "属性:收到伤害减魔力固定值")
    protected long hurtReduceMPFix;

    @Tag(82)
    @FieldDescription(desc = "属性:总伤害增加-----------------万分比")
    protected long damageIncreaseRate;

    @Tag(83)
    @FieldDescription(desc = "属性:总伤害增加固定值")
    protected long damageIncreaseFix;

    @Tag(84)
    @FieldDescription(desc = "属性:单手武器攻速增益-----------------万分比")
    protected long oneHandedWeaponIncRate;

    @Tag(85)
    @FieldDescription(desc = "属性:额外背包格子行数")
    protected long bagLatticeNumberUp;

    @Tag(86)
    @FieldDescription(desc = "属性:回收积分提升比例-----------------万分比")
    protected long sellBlueDiamondsUpRatio;

    @Tag(87)
    @FieldDescription(desc = "属性:拍卖行上架物品上限提升")
    protected long auctionSaleMaxUp;

    @Tag(88)
    @FieldDescription(desc = "属性:自动拾取开启")
    protected long autoPickOpen;

    @Tag(89)
    @FieldDescription(desc = "属性:怪物装备掉落率")
    protected long monsterDropRate;

    @Tag(90)
    @FieldDescription(desc = "属性:翅膀伤害加成-----------------万分比")
    protected long damageBonus;
    @Tag(91)
    @FieldDescription(desc = "属性:翅膀伤害吸收-----------------万分比")
    protected long damageAbsorption;

    @Tag(92)
    @FieldDescription(desc = "属性:卓越伤害减少-----------------万分比")
    protected long excellenceDamageDecrement;

    @Tag(93)
    @FieldDescription(desc = "属性:评分")
    protected long entryRating;

    @Tag(94)
    @FieldDescription(desc = "属性:自动买药")
    protected long autoBugDrugs;

    @Tag(95)
    @FieldDescription(desc = "属性:经验丹经验加成")
    protected long expDrugsUp;

    @Tag(96)
    @FieldDescription(desc = "属性:能不能被推动")
    protected boolean pushedImmunity;

    @Tag(97)
    @FieldDescription(desc = "属性:人物基础闪避 万分比")
    protected long basicDefenseRate;

    @Tag(98)
    @FieldDescription(desc = "属性:强化属性加成 万分比")
    protected long extralongensifyAttributeIncrease;

    @Tag(99)
    @FieldDescription(desc = "属性:追加属性加成 万分比")
    protected long extraAdditionalAttributeIncrease;

    @Tag(100)
    @FieldDescription(desc = "属性:随等级变化的防御")
    protected long baseDefenseByLevel;

    @Tag(101)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，减速-----------------万分比")
    protected long moveSpeedResistance;

    @Tag(102)
    @FieldDescription(desc = "脱战后生命恢复速度单位秒万分比")
    protected long leaveHealthRecoveryMultiplier;

    @Tag(103)
    @FieldDescription(desc = "怪物减伤 万分比")
    protected long monsterDamageAbsorption;

    @Tag(104)
    @FieldDescription(desc = "Boss 毒buff减少 万分比")
    protected long positionDamageAbsorption;

    @Tag(105)
    @FieldDescription(desc = "挂机保护")
    protected long hangUpProtection;

    @Tag(106)
    @FieldDescription(desc = "连击恢复加成 万分比")
    protected long comboRecovery;

    @Tag(107)
    @FieldDescription(desc = "仅客户端使用,攻击动画的速度")
    protected long attackSpeedCalculateValue = 1000;

    @Tag(108)
    @FieldDescription(desc = "属性：减伤buff 万分比")
    protected long damageReceiveDecrementBuff;

    @Tag(109)
    @FieldDescription(desc = "属性：宝石掉率 万分比")
    protected long stoneDropRate;

    @Tag(110)
    @FieldDescription(desc = "属性：卓越掉率  万分比")
    protected long exEquipDropRate;

    @Tag(111)
    @FieldDescription(desc = "属性：双倍掉率  万分比")
    protected long doubleDropRate;

    @Tag(112)
    @FieldDescription(desc = "属性：每5秒回血 万分比")
    protected long heal5s;

    @Tag(113)
    @FieldDescription(desc = "属性：随等级变化的生命值")
    protected long baseHealthByLevel;

    @Tag(114)
    @FieldDescription(desc = "属性：药水效果加成")
    protected long potionRate;

    @Tag(115)
    @FieldDescription(desc = "属性：连击伤害加成基础值")
    protected long comboDamage;

    @Tag(116)
    @FieldDescription(desc = "属性：连击伤害加成倍率")
    protected long comboAdditionalRatio;

    @Tag(117)
    @FieldDescription(desc = "属性：对战士伤害增加")
    protected long toWarriorHarmIncrease;

    @Tag(118)
    @FieldDescription(desc = "属性：对法师伤害增加")
    protected long toMagicianHarmIncrease;

    @Tag(119)
    @FieldDescription(desc = "属性：对弓箭手伤害增加")
    protected long toArcherHarmIncrease;

    @Tag(120)
    @FieldDescription(desc = "属性：受战士伤害减少")
    protected long fromWarriorHarmDecrease;

    @Tag(121)
    @FieldDescription(desc = "属性：受法师伤害减少")
    protected long fromMagicianHarmDecrease;

    @Tag(122)
    @FieldDescription(desc = "属性：受弓箭手伤害减少")
    protected long fromArcherHarmDecrease;

    @Tag(123)
    @FieldDescription(desc = "属性：对全职业伤害增加")
    protected long toPlayerHarmIncrease;

    @Tag(124)
    @FieldDescription(desc = "属性：受全职业伤害减少")
    protected long fromPlayerHarmDecrease;

    @Tag(125)
    @FieldDescription(desc = "属性：减少反射伤害")
    protected long damageReflectionDecrease;

    @Tag(126)
    @FieldDescription(desc = "属性：所有符文孔等级提升")
    protected long runeHoleLevelUp;

    @Tag(127)
    @FieldDescription(desc = "属性：对boss伤害增加")
    protected long toBossHarmIncrease;

    @Tag(128)
    @FieldDescription(desc = "属性：对黄金怪伤害增加")
    protected long toGoldHarmIncrease;

    @Tag(129)
    @FieldDescription(desc = "属性：对小怪伤害增加")
    protected long toMinionsHarmIncrease;

    @Tag(130)
    @FieldDescription(desc = "属性：职业技能冷却cd减少")
    protected long cdMinus;

    @Tag(131)
    @FieldDescription(desc = "属性：对减速目标增伤")
    protected long moderateHarmIncrease;

    @Tag(132)
    @FieldDescription(desc = "属性：额外增加一条特殊属性")
    protected long extraPartiAttribute;

    @Tag(133)
    @FieldDescription(desc = "属性：最大光焰值")
    protected long maximumFlare;

    @Tag(134)
    @FieldDescription(desc = "属性：每5秒回蓝 万分比")
    protected long healblue5s;

    @Tag(135)
    @FieldDescription(desc = "属性：无法忽视的防御力")
    protected long noIgnoreDefenseBase;

    @Tag(136)
    @FieldDescription(desc = "属性：心,2点内力")
    protected long xin;
    @Tag(137)
    @FieldDescription(desc = "属性：力,1点攻击")
    protected long li;
    @Tag(138)
    @FieldDescription(desc = "属性：体,1点防御")
    protected long ti;
    @Tag(139)
    @FieldDescription(desc = "属性：身,1点命中1点闪避")
    protected long shen;
    @Tag(140)
    @FieldDescription(desc = "属性：抵抗防御")
    protected long resistDefense;
    @Tag(141)
    @FieldDescription(desc = "属性：神圣概率")
    protected long holyChance;
    @Tag(142)
    @FieldDescription(desc = "属性：神圣伤害")
    protected long holyDamage;
    @Tag(143)
    @FieldDescription(desc = "属性：武功防御")
    protected long kungFuDefense;
    @Tag(144)
    @FieldDescription(desc = "属性：对怪武功")
    protected long kungFuPvm;
    @Tag(145)
    @FieldDescription(desc = "属性：武功攻击力")
    protected long kungFuRate1;
    @Tag(146)
    @FieldDescription(desc = "属性：武功打击值")
    protected long kungFuRate2;
    @Tag(147)
    @FieldDescription(desc = "属性：连击")
    protected long doubleHitRate;
    @Tag(148)
    @FieldDescription(desc = "属性：愤怒额外时间")
    protected long angerTime;
    @Tag(149)
    @FieldDescription(desc = "属性：蓝消耗降低")
    protected long mpReduceRate;
    @Tag(150)
    @FieldDescription(desc = "属性：治疗类武功的最终治疗效果提升万分比")
    protected long cureRate;
    @Tag(151)
    @FieldDescription(desc = "属性：人物的攻击距离")
    protected long castSkillDistance;
    @Tag(152)
    @FieldDescription(desc = "属性：魅力值")
    protected long charm;
    @Tag(153)
    @FieldDescription(desc = "属性：怒气值增加倍率")
    protected long angerRate;
    @Tag(154)
    @FieldDescription(desc = "属性：pvp增伤")
    protected long pvpAddRatio;
    @Tag(155)
    @FieldDescription(desc = "属性：pvp减伤")
    protected long pvpReduceRatio;
    @Tag(156)
    @FieldDescription(desc = "属性：愤怒值固定增加")
    protected long angerFixed;
    @Tag(157)
    @FieldDescription(desc = "属性：连击2 , 给弓箭手专用")
    protected long doubleHitRate2;
    @Tag(158)
    @FieldDescription(desc = "属性：历练点万分比加成")
    protected long liLianRate;
    @Tag(159)
    @FieldDescription(desc = "属性：万分比伤害吸血")
    protected long suckRate;
    @Tag(160)
    @FieldDescription(desc = "属性：所有气功等级加1")
    protected long qiGongLevelUp;
    @Tag(161)
    @FieldDescription(desc = "属性：致命一击几率")
    protected long deadlyProbability;

    public long getAttribute(String fieldName) throws Throwable {
        FieldAccessor accessor = AttributeTransform.getInstance().getFieldAccessor(fieldName);
        if (accessor == null) {
            return 0L;
        }
        return (long) accessor.getter().invoke(this);
    }

    public void setValue(String fieldName, long val) throws Throwable {
        FieldAccessor accessor = AttributeTransform.getInstance().getFieldAccessor(fieldName);
        if (accessor == null) {
            return;
        }
        accessor.setter().invoke(this, (long) val);
    }

}
