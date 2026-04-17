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
    @Tag(13)
    @FieldDescription(desc = "属性:最大魔力")
    protected long maximumMana;
    @Tag(15)
    @FieldDescription(desc = "属性:最大护盾")
    protected long maximumShield;
    @Tag(17)
    @FieldDescription(desc = "属性:最大技能值")
    protected long maximumAbility;
    @Tag(18)
    @FieldDescription(desc = "属性:生命回复比例-----------------万分比")
    protected long healthRecoveryMultiplier;
    @Tag(19)
    @FieldDescription(desc = "属性:魔力回复比例-----------------万分比")
    protected long manaRecoveryMultiplier;
    @Tag(20)
    @FieldDescription(desc = "属性:护盾回复比例-----------------万分比")
    protected long shieldRecoveryMultiplier;
    @Tag(21)
    @FieldDescription(desc = "属性:技能值回复比例-----------------万分比")
    protected long abilityRecoveryMultiplier;
    @Tag(22)
    @FieldDescription(desc = "属性:生命回复绝对值")
    protected long healthRecoveryAbsolute;
    @Tag(23)
    @FieldDescription(desc = "属性:魔力回复绝对值")
    protected long manaRecoveryAbsolute;
    @Tag(24)
    @FieldDescription(desc = "属性:护盾回复绝对值")
    protected long shieldRecoveryAbsolute;
    @Tag(25)
    @FieldDescription(desc = "属性:技能值回复绝对值")
    protected long abilityRecoveryAbsolute;
    @Tag(26)
    @FieldDescription(desc = "属性:杀怪后生命回复比例-----------------万分比")
    protected long healthAfterMonsterKillMultiplier;
    @Tag(27)
    @FieldDescription(desc = "属性:杀怪后魔力回复比例-----------------万分比")
    protected long manaAfterMonsterKillMultiplier;
    @Tag(28)
    @FieldDescription(desc = "属性:杀怪后护盾回复比例-----------------万分比")
    protected long shieldAfterMonsterKillMultiplier;
    @Tag(29)
    @FieldDescription(desc = "属性:杀怪后技能值回复比例-----------------万分比")
    protected long abilityAfterMonsterKillMultiplier;
    @Tag(30)
    @FieldDescription(desc = "属性:杀怪后生命回复绝对值")
    protected long healthAfterMonsterKillAbsolute;
    @Tag(31)
    @FieldDescription(desc = "属性:杀怪后魔力回复绝对值")
    protected long manaAfterMonsterKillAbsolute;
    @Tag(32)
    @FieldDescription(desc = "属性:杀怪后护盾回复绝对值")
    protected long shieldAfterMonsterKillAbsolute;
    @Tag(33)
    @FieldDescription(desc = "属性:杀怪后技能值回复绝对值")
    protected long abilityAfterMonsterKillAbsolute;

    @Tag(40)
    @FieldDescription(desc = "属性:攻击成功率PVM")
    protected long attackRatePvm;
    @Tag(41)
    @FieldDescription(desc = "属性:攻击成功率PVP")
    protected long attackRatePvp;
    @Tag(42)
    @FieldDescription(desc = "属性:防御成功率PVM 统一防御率用")
    protected long defenseRatePvm;
    @Tag(43)
    @FieldDescription(desc = "属性:防御成功率PVP 数值暂不用")
    protected long defenseRatePvp;
    @Tag(44)
    @FieldDescription(desc = "属性:幸运一击伤害加成----------              ----------作废")
    protected long criticalDamageBonus;
    @Tag(45)
    @FieldDescription(desc = "属性:卓越一击额外伤害加成----------              ----------作废")
    protected long excellentDamageBonus;
    @Tag(46)
    @FieldDescription(desc = "属性:幸运一击率-----------------万分比")
    protected long criticalDamageChance;
    @Tag(47)
    @FieldDescription(desc = "属性:卓越一击率-----------------万分比")
    protected long excellentDamageChance;

    @Tag(53)
    @FieldDescription(desc = "属性:物理伤害加成最小浮动")
    protected long minimumPhysBaseDmg;
    @Tag(54)
    @FieldDescription(desc = "属性:物理伤害加成最大浮动")
    protected long maximumPhysBaseDmg;
    @Tag(55)
    @FieldDescription(desc = "属性:法术伤害加成最小浮动")
    protected long minimumWizBaseDmg;
    @Tag(56)
    @FieldDescription(desc = "属性:法术伤害加成最大浮动")
    protected long maximumWizBaseDmg;
    @Tag(57)
    @FieldDescription(desc = "属性:咒语伤害加成最小浮动")
    protected long minimumCurseBaseDmg;
    @Tag(58)
    @FieldDescription(desc = "属性:咒语伤害加成最大浮动")
    protected long maximumCurseBaseDmg;

    @Tag(70)
    @FieldDescription(desc = "属性:无视防御率-----------------万分比")
    protected long defenseIgnoreChance;
    @Tag(71)
    @FieldDescription(desc = "属性:基础防御值，减伤害")
    protected long defenseBase;
    @Tag(72)
    @FieldDescription(desc = "属性:防御值PVM 对怪防御")
    protected long defensePvm;
    @Tag(73)
    @FieldDescription(desc = "属性:防御值PVP----------              ----------作废")
    protected long defensePvp;
    @Tag(74)
    @FieldDescription(desc = "属性:防御值提升比例-----------------万分比")
    protected long defenseIncreaseWithEquippedShield;

    @Tag(80)
    @FieldDescription(desc = "属性:减伤-----------------万分比")
    protected long damageReceiveDecrement;
    @Tag(81)
    @FieldDescription(desc = "属性:额外总伤害加成-----------------万分比")
    protected long attackDamageIncrease;
    @Tag(82)
    @FieldDescription(desc = "属性:技能额外总伤害加成")
    protected long skillDamageBonus;
    @Tag(83)
    @FieldDescription(desc = "属性:技能额外总伤害加成比例-----------------万分比")
    protected long skillMultiplier;
    @Tag(84)
    @FieldDescription(desc = "属性:是否有双手武器")
    protected boolean twoHandedWeaponEquipped;
    @Tag(85)
    @FieldDescription(desc = "属性:双手武器提供伤害-----------------万分比")
    protected long twoHandedWeaponDamageIncrease;
    @Tag(86)
    @FieldDescription(desc = "属性:PVP增伤")
    protected long finalDamageIncreasePvp;
    @Tag(87)
    @FieldDescription(desc = "属性:两倍伤害概率-----------------万分比")
    protected long doubleDamageChance;

    @Tag(90)
    @FieldDescription(desc = "属性:无视护盾概率-----------------万分比")
    protected long shieldBypassChance;
    @Tag(91)
    @FieldDescription(desc = "属性:护盾伤害增加率-----------------万分比")
    protected long shieldDecreaseRateIncrease;
    @Tag(92)
    @FieldDescription(desc = "属性:护盾伤害减少率-----------------万分比")
    protected long shieldRateIncrease;
    @Tag(93)
    @FieldDescription(desc = "属性:伤害反弹率，反射收到伤害比例-----------------万分比")
    protected long damageReflection;

    @Tag(100)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，冰-----------------万分比")
    protected long iceResistance;
    @Tag(101)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，火-----------------万分比")
    protected long fireResistance;
    @Tag(102)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，水-----------------万分比")
    protected long waterResistance;
    @Tag(103)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，地-----------------万分比")
    protected long earthResistance;
    @Tag(104)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，风-----------------万分比")
    protected long windResistance;
    @Tag(105)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，毒-----------------万分比")
    protected long poisonResistance;
    @Tag(106)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，雷-----------------万分比")
    protected long lightningResistance;

    @Tag(110)
    @FieldDescription(desc = "属性:可以任意地点回复护盾----------              ----------作废")
    protected long shieldRecoveryEverywhere;
    @Tag(111)
    @FieldDescription(desc = "属性:能力值消耗减少量----------              ----------作废")
    protected long abilityUsageReduction;
    @Tag(112)
    @FieldDescription(desc = "属性:魔力值消耗减少量----------              ----------作废")
    protected long manaUsageReduction;
    @Tag(113)
    @FieldDescription(desc = "属性:物品持续时间增加----------              ----------作废")
    protected long itemDurationIncrease;
    @Tag(114)
    @FieldDescription(desc = "属性:宠物持续时间增加----------              ----------作废")
    protected long petDurationIncrease;
    @Tag(115)
    @FieldDescription(desc = "属性:满魔回复率，被攻击时有概率回满魔-----------------万分比")
    protected long fullyRecoverManaAfterHitChance;
    @Tag(116)
    @FieldDescription(desc = "属性:满血回复率，被攻击时有概率回满血-----------------万分比")
    protected long fullyRecoverHealthAfterHitChance;
    @Tag(117)
    @FieldDescription(desc = "属性:所需力量降低----------              ----------作废")
    protected long requiredStrengthReduction;
    @Tag(118)
    @FieldDescription(desc = "属性:所需敏捷降低----------              ----------作废")
    protected long requiredAgilityReduction;
    @Tag(119)
    @FieldDescription(desc = "属性:所需智力降低----------              ----------作废")
    protected long requiredVitalityReduction;
    @Tag(120)
    @FieldDescription(desc = "属性:所需体力降低----------              ----------作废")
    protected long requiredEnergyReduction;
    @Tag(121)
    @FieldDescription(desc = "属性:所需统帅降低----------              ----------作废")
    protected long requiredLeadershipReduction;
    @Tag(122)
    @FieldDescription(desc = "属性:战力")
    protected long fight;
    @Tag(123)
    @FieldDescription(desc = "属性:宠物攻击百分比提升----------              ----------作废")
    protected long petAttackDamageIncrease;
    @Tag(124)
    @FieldDescription(desc = "属性:抵抗无视防御几率-----------------万分比")
    protected long defenseIgnoreChanceResistance;
    @Tag(125)
    @FieldDescription(desc = "属性:抵抗SD无视几率-----------------万分比")
    protected long shieldBypassChanceResistance;
    @Tag(126)
    @FieldDescription(desc = "属性:抵抗双倍伤害几率-----------------万分比")
    protected long doubleDamageChanceResistance;
    @Tag(127)
    @FieldDescription(desc = "属性:抵抗卓越一击几率-----------------万分比")
    protected long excellentDamageChanceResistance;
    @Tag(128)
    @FieldDescription(desc = "属性:抵抗致命一击几率-----------------万分比")
    protected long criticalDamageBonusResistance;
    @Tag(129)
    @FieldDescription(desc = "属性:攻击距离增加----------              ----------作废")
    protected long attackDistanceIncrease;
    @Tag(130)
    @FieldDescription(desc = "属性:随等级变化的物理攻击")
    protected long physBaseDmgByLevel;
    @Tag(131)
    @FieldDescription(desc = "属性:随等级变化的魔法攻击力")
    protected long wizBaseDmgByLevel;

    @Tag(140)
    @FieldDescription(desc = "属性:每次攻击成功减少固定生命值")
    protected long reducedFixedHealthPerSuccessfulAttack;

    @Tag(141)
    @FieldDescription(desc = "属性:基础移动速度")
    protected long staticMoveSpeed;

    @Tag(142)
    @FieldDescription(desc = "属性:额外伤害-----------------万分比")
    protected long extraDamage;

    @Tag(143)
    @FieldDescription(desc = "属性:收到伤害减少-----------------万分比")
    protected long receivedDamageReduceRate;

    @Tag(144)
    @FieldDescription(desc = "属性:收到伤害减少固定值")
    protected long receivedDamageReduceRateFix;

    @Tag(145)
    @FieldDescription(desc = "属性:收到伤害减魔力万分比")
    protected long hurtReduceMPRate;

    @Tag(146)
    @FieldDescription(desc = "属性:收到伤害减魔力固定值")
    protected long hurtReduceMPFix;

    @Tag(147)
    @FieldDescription(desc = "属性:总伤害增加-----------------万分比")
    protected long damageIncreaseRate;

    @Tag(148)
    @FieldDescription(desc = "属性:总伤害增加固定值")
    protected long damageIncreaseFix;

    @Tag(149)
    @FieldDescription(desc = "属性:单手武器攻速增益-----------------万分比")
    protected long oneHandedWeaponIncRate;

    @Tag(150)
    @FieldDescription(desc = "属性:额外背包格子行数")
    protected long bagLatticeNumberUp;

    @Tag(151)
    @FieldDescription(desc = "属性:回收积分提升比例-----------------万分比")
    protected long sellBlueDiamondsUpRatio;

    @Tag(152)
    @FieldDescription(desc = "属性:拍卖行上架物品上限提升")
    protected long auctionSaleMaxUp;

    @Tag(153)
    @FieldDescription(desc = "属性:自动拾取开启")
    protected long autoPickOpen;

    @Tag(154)
    @FieldDescription(desc = "属性:怪物装备掉落率")
    protected long monsterDropRate;

    @Tag(155)
    @FieldDescription(desc = "属性:翅膀伤害加成-----------------万分比")
    protected long damageBonus;
    @Tag(156)
    @FieldDescription(desc = "属性:翅膀伤害吸收-----------------万分比")
    protected long damageAbsorption;

    @Tag(157)
    @FieldDescription(desc = "属性:卓越伤害减少-----------------万分比")
    protected long excellenceDamageDecrement;

    @Tag(158)
    @FieldDescription(desc = "属性:评分")
    protected long entryRating;

    @Tag(159)
    @FieldDescription(desc = "属性:自动买药")
    protected long autoBugDrugs;

    @Tag(160)
    @FieldDescription(desc = "属性:经验丹经验加成")
    protected long expDrugsUp;

    @Tag(161)
    @FieldDescription(desc = "属性:能不能被推动")
    protected boolean pushedImmunity;

    @Tag(162)
    @FieldDescription(desc = "属性:人物基础闪避 万分比")
    protected long basicDefenseRate;

    @Tag(163)
    @FieldDescription(desc = "属性:强化属性加成 万分比")
    protected long extralongensifyAttributeIncrease;

    @Tag(164)
    @FieldDescription(desc = "属性:追加属性加成 万分比")
    protected long extraAdditionalAttributeIncrease;

    @Tag(165)
    @FieldDescription(desc = "属性:随等级变化的防御")
    protected long baseDefenseByLevel;

    @Tag(166)
    @FieldDescription(desc = "属性:技能效果属性免疫几率，减速-----------------万分比")
    protected long moveSpeedResistance;

    @Tag(167)
    @FieldDescription(desc = "脱战后生命恢复速度单位秒万分比")
    protected long leaveHealthRecoveryMultiplier;

    @Tag(168)
    @FieldDescription(desc = "怪物减伤 万分比")
    protected long monsterDamageAbsorption;

    @Tag(169)
    @FieldDescription(desc = "Boss 毒buff减少 万分比")
    protected long positionDamageAbsorption;

    @Tag(170)
    @FieldDescription(desc = "挂机保护")
    protected long hangUpProtection;

    @Tag(171)
    @FieldDescription(desc = "连击恢复加成 万分比")
    protected long comboRecovery;

    @Tag(172)
    @FieldDescription(desc = "仅客户端使用,攻击动画的速度")
    protected long attackSpeedCalculateValue = 1000;

    @Tag(177)
    @FieldDescription(desc = "属性：减伤buff 万分比")
    protected long damageReceiveDecrementBuff;

    @Tag(178)
    @FieldDescription(desc = "属性：宝石掉率 万分比")
    protected long stoneDropRate;

    @Tag(179)
    @FieldDescription(desc = "属性：卓越掉率  万分比")
    protected long exEquipDropRate;

    @Tag(180)
    @FieldDescription(desc = "属性：双倍掉率  万分比")
    protected long doubleDropRate;

    @Tag(181)
    @FieldDescription(desc = "属性：每5秒回血 万分比")
    protected long heal5s;

    @Tag(182)
    @FieldDescription(desc = "属性：随等级变化的生命值")
    protected long baseHealthByLevel;

    @Tag(183)
    @FieldDescription(desc = "属性：药水效果加成")
    protected long potionRate;

    @Tag(184)
    @FieldDescription(desc = "属性：连击伤害加成基础值")
    protected long comboDamage;

    @Tag(185)
    @FieldDescription(desc = "属性：连击伤害加成倍率")
    protected long comboAdditionalRatio;

    @Tag(186)
    @FieldDescription(desc = "属性：对战士伤害增加")
    protected long toWarriorHarmIncrease;

    @Tag(187)
    @FieldDescription(desc = "属性：对法师伤害增加")
    protected long toMagicianHarmIncrease;

    @Tag(188)
    @FieldDescription(desc = "属性：对弓箭手伤害增加")
    protected long toArcherHarmIncrease;

    @Tag(189)
    @FieldDescription(desc = "属性：受战士伤害减少")
    protected long fromWarriorHarmDecrease;

    @Tag(190)
    @FieldDescription(desc = "属性：受法师伤害减少")
    protected long fromMagicianHarmDecrease;

    @Tag(191)
    @FieldDescription(desc = "属性：受弓箭手伤害减少")
    protected long fromArcherHarmDecrease;

    @Tag(192)
    @FieldDescription(desc = "属性：对全职业伤害增加")
    protected long toPlayerHarmIncrease;

    @Tag(193)
    @FieldDescription(desc = "属性：受全职业伤害减少")
    protected long fromPlayerHarmDecrease;

    @Tag(194)
    @FieldDescription(desc = "属性：减少反射伤害")
    protected long damageReflectionDecrease;

    @Tag(195)
    @FieldDescription(desc = "属性：所有符文孔等级提升")
    protected long runeHoleLevelUp;

    @Tag(196)
    @FieldDescription(desc = "属性：对boss伤害增加")
    protected long toBossHarmIncrease;

    @Tag(197)
    @FieldDescription(desc = "属性：对黄金怪伤害增加")
    protected long toGoldHarmIncrease;

    @Tag(198)
    @FieldDescription(desc = "属性：对小怪伤害增加")
    protected long toMinionsHarmIncrease;

    @Tag(199)
    @FieldDescription(desc = "属性：职业技能冷却cd减少")
    protected long cdMinus;

    @Tag(200)
    @FieldDescription(desc = "属性：对减速目标增伤")
    protected long moderateHarmIncrease;

    @Tag(201)
    @FieldDescription(desc = "属性：额外增加一条特殊属性")
    protected long extraPartiAttribute;

    @Tag(202)
    @FieldDescription(desc = "属性：最大光焰值")
    protected long maximumFlare;

    @Tag(203)
    @FieldDescription(desc = "属性：每5秒回蓝 万分比")
    protected long healblue5s;

    @Tag(204)
    @FieldDescription(desc = "属性：无法忽视的防御力")
    protected long noIgnoreDefenseBase;

    @Tag(205)
    @FieldDescription(desc = "属性：对魔剑士伤害增加")
    protected long toSpellswordHarmIncrease;

    @Tag(206)
    @FieldDescription(desc = "属性：受魔剑士伤害减少")
    protected long fromSpellswordHarmDecrease;
    @Tag(207)
    @FieldDescription(desc = "属性：三倍掉率  万分比")
    protected long trebleDropRate;
    @Tag(208)
    @FieldDescription(desc = "属性：五倍掉率  万分比")
    protected long fivefoldDropRate;
    @Tag(209)
    @FieldDescription(desc = "属性：十倍掉率  万分比")
    protected long tenfoldDropRate;
    @Tag(210)
    @FieldDescription(desc = "属性：心,2点内力")
    protected long xin;
    @Tag(211)
    @FieldDescription(desc = "属性：力,1点攻击")
    protected long li;
    @Tag(212)
    @FieldDescription(desc = "属性：体,1点防御")
    protected long ti;
    @Tag(213)
    @FieldDescription(desc = "属性：身,1点命中1点闪避")
    protected long shen;
    @Tag(214)
    @FieldDescription(desc = "属性：抵抗防御")
    protected long resistDefense;
    @Tag(215)
    @FieldDescription(desc = "属性：神圣概率")
    protected long holyChance;
    @Tag(216)
    @FieldDescription(desc = "属性：神圣伤害")
    protected long holyDamage;
    @Tag(217)
    @FieldDescription(desc = "属性：武功防御")
    protected long kungFuDefense;
    @Tag(218)
    @FieldDescription(desc = "属性：对怪武功")
    protected long kungFuPvm;
    @Tag(219)
    @FieldDescription(desc = "属性：武功攻击力")
    protected long kungFuRate1;
    @Tag(220)
    @FieldDescription(desc = "属性：武功打击值")
    protected long kungFuRate2;
    @Tag(221)
    @FieldDescription(desc = "属性：连击")
    protected long doubleHitRate;
    @Tag(222)
    @FieldDescription(desc = "属性：愤怒额外时间")
    protected long angerTime;
    @Tag(223)
    @FieldDescription(desc = "属性：蓝消耗降低")
    protected long mpReduceRate;
    @Tag(224)
    @FieldDescription(desc = "属性：治疗类武功的最终治疗效果提升万分比")
    protected long cureRate;
    @Tag(225)
    @FieldDescription(desc = "属性：人物的攻击距离")
    protected long castSkillDistance;
    @Tag(226)
    @FieldDescription(desc = "属性：魅力值")
    protected long charm;
    @Tag(227)
    @FieldDescription(desc = "属性：怒气值增加倍率")
    protected long angerRate;
    @Tag(228)
    @FieldDescription(desc = "属性：pvp增伤")
    protected long pvpAddRatio;
    @Tag(229)
    @FieldDescription(desc = "属性：pvp减伤")
    protected long pvpReduceRatio;
    @Tag(230)
    @FieldDescription(desc = "属性：愤怒值固定增加")
    protected long angerFixed;
    @Tag(231)
    @FieldDescription(desc = "属性：连击2 , 给弓箭手专用")
    protected long doubleHitRate2;
    @Tag(232)
    @FieldDescription(desc = "属性：历练点万分比加成")
    protected long liLianRate;
    @Tag(233)
    @FieldDescription(desc = "属性：万分比伤害吸血") //策划说没有配过这个数值;
    protected long suckRate;
    @Tag(234)
    @FieldDescription(desc = "属性：所有气功等级加1")
    protected long qiGongLevelUp;
    @Tag(235)
    @FieldDescription(desc = "属性：致命一击几率")
    protected long deadlyProbability;
    @Tag(236)
    @FieldDescription(desc = "属性：致命伤害加成")
    protected long deadlyRate;
    @Tag(237)
    @FieldDescription(desc = "属性：致命加深,致命一击固定值")
    protected long deadlyFixedValue;
    @Tag(238)
    @FieldDescription(desc = "属性：武功闪避")
    protected long kungFuDodge;
    @Tag(239)
    @FieldDescription(desc = "属性：致命减免")
    protected long resistDeadlyRate;
    @Tag(240)
    @FieldDescription(desc = "属性：会心一击加成")
    protected long luckRate;
    @Tag(241)
    @FieldDescription(desc = "属性：会心一击减免")
    protected long resistLuckRate;
    @Tag(242)
    @FieldDescription(desc = "属性：致命一击几率抵抗")
    protected long resistDeadlyProbability;
    @Tag(243)
    @FieldDescription(desc = "属性：会心一击几率抵抗")
    protected long resistLuckProbability;
    @Tag(244)
    @FieldDescription(desc = "属性：武功忽视防御")
    protected long resistKungFuDefense;
    @Tag(245)
    @FieldDescription(desc = "属性：伏魔值")
    protected long demon;
    @Tag(246)
    @FieldDescription(desc = "属性：武勋称号等级")
    protected long militaryLevel;
    @Tag(247)
    @FieldDescription(desc = "属性：威压增伤")
    protected long militaryHurt;
    @Tag(248)
    @FieldDescription(desc = "属性：威压增幅")
    protected long militaryHurtRate;
    @Tag(249)
    @FieldDescription(desc = "属性：固定值吸血")//对怪,群体技能也只生效一个;
    protected long suckFixedValue;
    @Tag(250)
    @FieldDescription(desc = "属性：pve增伤")
    protected long pveAddHurtRate;
    @Tag(251)
    @FieldDescription(desc = "属性：pve减伤")
    protected long pveMinusHurtRate;
    @Tag(252)
    @FieldDescription(desc = "属性：pvp固定值增伤,不收任何属性加成")
    protected long pvpAddFixed;
    @Tag(253)
    @FieldDescription(desc = "属性：pvp固定值减伤,不收任何属性加成")
    protected long pvpReduceFixed;

    @Tag(254)
    @FieldDescription(desc = "属性：对刀增伤")
    protected long career1HurtRate;
    @Tag(255)
    @FieldDescription(desc = "属性：对剑增伤")
    protected long career4HurtRate;
    @Tag(256)
    @FieldDescription(desc = "属性：对枪增伤")
    protected long career2HurtRate;
    @Tag(257)
    @FieldDescription(desc = "属性：对弓增伤")
    protected long career3HurtRate;
    @Tag(258)
    @FieldDescription(desc = "属性：对医增伤")
    protected long career5HurtRate;

    @Tag(259)
    @FieldDescription(desc = "属性：对刀减伤")
    protected long career1HurtReduceRate;
    @Tag(260)
    @FieldDescription(desc = "属性：对剑减伤")
    protected long career4HurtReduceRate;
    @Tag(261)
    @FieldDescription(desc = "属性：对枪减伤")
    protected long career2HurtReduceRate;
    @Tag(262)
    @FieldDescription(desc = "属性：对弓减伤")
    protected long career3HurtReduceRate;
    @Tag(263)
    @FieldDescription(desc = "属性：对医减伤")
    protected long career5HurtReduceRate;

    @Tag(264)
    @FieldDescription(desc = "属性：对boss伤害增加固定值")
    protected long bossFixedValue;
    @Tag(265)
    @FieldDescription(desc = "属性：吸血抵抗")
    protected long suckRateResist;

    @Tag(266)
    @FieldDescription(desc = "属性：宠物造成的最终伤害提升")
    protected long petHurtRate;
    @Tag(267)
    @FieldDescription(desc = "属性：宠物受到伤害的万分比减免")
    protected long petHurtReduceRate;
    @Tag(268)
    @FieldDescription(desc = "属性：Pvp时造成伤害的万分比转化为自身生命值")
    protected long pvpSuckRate;

    @Tag(269)
    @FieldDescription(desc = "属性：武功命中 万分比，与武功闪避相对，武功闪避-武功命中>0时，武功闪避属性才会生效，小于等于0，则不会触发武功闪避")
    protected long kungFuHit;
    @Tag(270)
    @FieldDescription(desc = "属性：武功威力  固定值，直接增加对应职业的skill表中所有技能的武功威力")
    protected long kungFuSkillAddFixedValue;
    @Tag(271)
    @FieldDescription(desc = "武器基础属性加成  万分比，对穿戴的武器基础属性进行加成")
    protected long baseRateWeapon;
    @Tag(272)
    @FieldDescription(desc = "衣服基础属性加成")
    protected long baseRateCloth;
    @Tag(273)
    @FieldDescription(desc = "内甲基础属性加成")
    protected long baseRateInsideCloth;
    @Tag(274)
    @FieldDescription(desc = "左护手基础属性加成")
    protected long baseRateArmLeft;
    @Tag(275)
    @FieldDescription(desc = "右护手基础属性加成")
    protected long baseRateArmRight;
    @Tag(276)
    @FieldDescription(desc = "闪避先算这个再算,武功和普公的")//下次加上实现,还没实现;
    protected long dodgeChance;
    @Tag(277)
    @FieldDescription(desc = "鞋子基础属性加成")
    protected long baseRateShoes;
    @Tag(278)
    @FieldDescription(desc = "荆棘反伤：受到攻击时，对攻击者造成固定数值的反伤伤害")
    protected long damageReflectionFixedValue;
    @Tag(279)
    @FieldDescription(desc = "荆棘抵抗：降低受到的荆棘反伤的数值，固定值")
    protected long damageReflectionReduceFixedValue;
    @Tag(280)
    @FieldDescription(desc = "组队状态下增加的怒气时间,毫秒")
    protected long angerAddTimeOnTeam;
    @Tag(281)
    @FieldDescription(desc = "飞升历练万分比加成")
    protected long feiShengLiLianRate;
    @Tag(282)
    @FieldDescription(desc = "仙攻")
    protected long godAttack;
    @Tag(283)
    @FieldDescription(desc = "仙防")
    protected long godDefense;
    @Tag(284)
    @FieldDescription(desc = "组队副本怪物万分比增伤")
    protected long teamMapMonsterHurtRate;
    @Tag(285)
    @FieldDescription(desc = "经验点")
    protected long expPolong;
    @Tag(286)
    @FieldDescription(desc = "只能减怪物武功的威力")
    protected long kungFuDefenseToMon;
    @Tag(287)
    @FieldDescription(desc = "锻体经验加成")
    protected long duanTiExpRate;

    @Tag(288)
    @FieldDescription(desc = "万分比  类似组队副本增伤，只在神兵塔副本生效")
    protected long godWeaponMapMonsterHurtRate;
    @Tag(289)
    @FieldDescription(desc = "固定值  对神兵塔怪物额外造成的固定值伤害")
    protected long godWeaponMapMonsterFixedValue;
    @Tag(290)
    @FieldDescription(desc = "荆棘减免  万分比  计算方式为 最终荆棘反伤= （荆棘反伤-荆棘抵抗）*（1-荆棘减免）")
    protected long damageReflectionReduceRate;
    @Tag(291)
    @FieldDescription(desc = "怪物回血  固定值  每5秒恢复一定数值的生命值")
    protected long monsterHpRecoverFixedValue;
    @Tag(292)
    @FieldDescription(desc = "降低怪物回血比例  万分比  计算 最终回血值=怪物回血*（1-降低怪物回血比例 ")
    protected long monsterHpRecoverReduceRate;
    @Tag(293)
    @FieldDescription(desc = "忽视闪避  万分比，与276属性相对，计算方式 最终闪避率=闪避率*（1-忽视闪避）")
    protected long dodgeChanceReduce;

    public long getAttribute(String fieldName) {
        FieldAccessor accessor = AttributeTransform.getInstance().getFieldAccessor(fieldName);
        if (accessor == null) {
            return 0L;
        }
        try {
            return (long) accessor.getter().invoke(this);
        } catch (Throwable e) {
            throw new RuntimeException("Failed to get attribute: " + fieldName, e);
        }
    }

    public void setValue(String fieldName, long val) {
        FieldAccessor accessor = AttributeTransform.getInstance().getFieldAccessor(fieldName);
        if (accessor == null) {
            return;
        }
        try {
            accessor.setter().invoke(this, (long) val);
        } catch (Throwable e) {
            throw new RuntimeException("Failed to set attribute: " + fieldName, e);
        }
    }

}
