package com.whk.actor.component.attribute;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.HashMap;
import java.util.Map;

public class AttributeTransForm {

    public static final float PROP_100 = 100.0f;
    public static final float PROP_10000 = 10000.0f;
    public static final float PROP_1000_0000 = 10000000.0f;
    public static final float PROP_10000_0000 = 100000000.0f;
    public static final double PROP_10000_D = 10000d;
    public static final int PROP_10000_I = 10000;

    public static BiMap<Integer, String> AttributeName = HashBiMap.create();
    private static final BiMap<String, Integer> NameTransformId;

    static {
        AttributeName.put(1, "strength");
        AttributeName.put(2, "agility");
        AttributeName.put(3, "vitality");
        AttributeName.put(4, "energy");
        AttributeName.put(5, "leadership");
        AttributeName.put(6, "experienceRate");
        AttributeName.put(7, "moneyAmountRate");
        AttributeName.put(8, "attackSpeed");
        AttributeName.put(9, "moveSpeed");
        AttributeName.put(10, "sellGoldUpRatio");
        AttributeName.put(11, "maximumHealth");
        AttributeName.put(13, "maximumMana");
        AttributeName.put(15, "maximumShield");
        AttributeName.put(17, "maximumAbility");
        AttributeName.put(18, "healthRecoveryMultiplier");
        AttributeName.put(19, "manaRecoveryMultiplier");
        AttributeName.put(20, "shieldRecoveryMultiplier");
        AttributeName.put(21, "abilityRecoveryMultiplier");
        AttributeName.put(22, "healthRecoveryAbsolute");
        AttributeName.put(23, "manaRecoveryAbsolute");
        AttributeName.put(24, "shieldRecoveryAbsolute");
        AttributeName.put(25, "abilityRecoveryAbsolute");
        AttributeName.put(26, "healthAfterMonsterKillMultiplier");
        AttributeName.put(27, "manaAfterMonsterKillMultiplier");
        AttributeName.put(28, "shieldAfterMonsterKillMultiplier");
        AttributeName.put(29, "abilityAfterMonsterKillMultiplier");
        AttributeName.put(30, "healthAfterMonsterKillAbsolute");
        AttributeName.put(31, "manaAfterMonsterKillAbsolute");
        AttributeName.put(32, "shieldAfterMonsterKillAbsolute");
        AttributeName.put(33, "abilityAfterMonsterKillAbsolute");
        AttributeName.put(40, "attackRatePvm");
        AttributeName.put(41, "attackRatePvp");
        AttributeName.put(42, "defenseRatePvm");
        AttributeName.put(43, "defenseRatePvp");
        AttributeName.put(44, "criticalDamageBonus");
        AttributeName.put(45, "excellentDamageBonus");
        AttributeName.put(46, "criticalDamageChance");
        AttributeName.put(47, "excellentDamageChance");
        AttributeName.put(53, "minimumPhysBaseDmg");
        AttributeName.put(54, "maximumPhysBaseDmg");
        AttributeName.put(55, "minimumWizBaseDmg");
        AttributeName.put(56, "maximumWizBaseDmg");
        AttributeName.put(57, "minimumCurseBaseDmg");
        AttributeName.put(58, "maximumCurseBaseDmg");
        AttributeName.put(70, "defenseIgnoreChance");
        AttributeName.put(71, "defenseBase");
        AttributeName.put(72, "defensePvm");
        AttributeName.put(73, "defensePvp");
        AttributeName.put(74, "defenseIncreaseWithEquippedShield");
        AttributeName.put(80, "damageReceiveDecrement");
        AttributeName.put(81, "attackDamageIncrease");
        AttributeName.put(82, "skillDamageBonus");
        AttributeName.put(83, "skillMultiplier");
        AttributeName.put(84, "twoHandedWeaponEquipped");
        AttributeName.put(85, "twoHandedWeaponDamageIncrease");
        AttributeName.put(86, "finalDamageIncreasePvp");
        AttributeName.put(87, "doubleDamageChance");
        AttributeName.put(90, "shieldBypassChance");
        AttributeName.put(91, "shieldDecreaseRateIncrease");
        AttributeName.put(92, "shieldRateIncrease");
        AttributeName.put(93, "damageReflection");
        AttributeName.put(100, "iceResistance");
        AttributeName.put(101, "fireResistance");
        AttributeName.put(102, "waterResistance");
        AttributeName.put(103, "earthResistance");
        AttributeName.put(104, "windResistance");
        AttributeName.put(105, "poisonResistance");
        AttributeName.put(106, "lightningResistance");
        AttributeName.put(110, "shieldRecoveryEverywhere");
        AttributeName.put(111, "abilityUsageReduction");
        AttributeName.put(112, "manaUsageReduction");
        AttributeName.put(113, "itemDurationIncrease");
        AttributeName.put(114, "petDurationIncrease");
        AttributeName.put(115, "fullyRecoverManaAfterHitChance");
        AttributeName.put(116, "fullyRecoverHealthAfterHitChance");
        AttributeName.put(117, "requiredStrengthReduction");
        AttributeName.put(118, "requiredAgilityReduction");
        AttributeName.put(119, "requiredVitalityReduction");
        AttributeName.put(120, "requiredEnergyReduction");
        AttributeName.put(121, "requiredLeadershipReduction");
        AttributeName.put(122, "fight");
        AttributeName.put(123, "petAttackDamageIncrease");
        AttributeName.put(124, "defenseIgnoreChanceResistance");
        AttributeName.put(125, "shieldBypassChanceResistance");
        AttributeName.put(126, "doubleDamageChanceResistance");
        AttributeName.put(127, "excellentDamageChanceResistance");
        AttributeName.put(128, "criticalDamageBonusResistance");
        AttributeName.put(129, "attackDistanceIncrease");
        AttributeName.put(130, "physBaseDmgByLevel");
        AttributeName.put(131, "wizBaseDmgByLevel");
        AttributeName.put(140, "reducedFixedHealthPerSuccessfulAttack");
        AttributeName.put(141, "staticMoveSpeed");
        AttributeName.put(142, "extraDamage");
        AttributeName.put(143, "receivedDamageReduceRate");
        AttributeName.put(144, "receivedDamageReduceRateFix");
        AttributeName.put(145, "hurtReduceMPRate");
        AttributeName.put(146, "hurtReduceMPFix");
        AttributeName.put(147, "damageIncreaseRate");
        AttributeName.put(148, "damageIncreaseFix");
        AttributeName.put(149, "oneHandedWeaponIncRate");
        AttributeName.put(150, "bagLatticeNumberUp");
        AttributeName.put(151, "sellBlueDiamondsUpRatio");
        AttributeName.put(152, "auctionSaleMaxUp");
        AttributeName.put(153, "autoPickOpen");
        AttributeName.put(154, "monsterDropRate");
        AttributeName.put(155, "damageBonus");
        AttributeName.put(156, "damageAbsorption");
        AttributeName.put(157, "excellenceDamageDecrement");
        AttributeName.put(158, "entryRating");
        AttributeName.put(159, "autoBugDrugs");
        AttributeName.put(160, "expDrugsUp");
        AttributeName.put(161, "pushedImmunity");
        AttributeName.put(162, "basicDefenseRate");
        AttributeName.put(163, "extraIntensifyAttributeIncrease");
        AttributeName.put(164, "extraAdditionalAttributeIncrease");
        AttributeName.put(165, "baseDefenseByLevel");
        AttributeName.put(166, "moveSpeedResistance");
        AttributeName.put(167, "leaveHealthRecoveryMultiplier");
        AttributeName.put(168, "monsterDamageAbsorption");
        AttributeName.put(169, "positionDamageAbsorption");
        AttributeName.put(170, "hangUpProtection");
        AttributeName.put(171, "comboRecovery");
        AttributeName.put(172, "attackSpeedCalculateValue");
        AttributeName.put(177, "damageReceiveDecrementBuff");
        AttributeName.put(178, "stoneDropRate");
        AttributeName.put(179, "exEquipDropRate");
        AttributeName.put(180, "doubleDropRate");
        AttributeName.put(181, "heal5s");
        AttributeName.put(182, "baseHealthByLevel");
        AttributeName.put(183, "potionRate");
        AttributeName.put(184, "comboDamage");
        AttributeName.put(185, "comboAdditionalRatio");
        AttributeName.put(186, "toWarriorHarmIncrease");
        AttributeName.put(187, "toMagicianHarmIncrease");
        AttributeName.put(188, "toArcherHarmIncrease");
        AttributeName.put(189, "fromWarriorHarmDecrease");
        AttributeName.put(190, "fromMagicianHarmDecrease");
        AttributeName.put(191, "fromArcherHarmDecrease");
        AttributeName.put(192, "toPlayerHarmIncrease");
        AttributeName.put(193, "fromPlayerHarmDecrease");
        AttributeName.put(194, "damageReflectionDecrease");
        AttributeName.put(195, "runeHoleLevelUp");
        AttributeName.put(196, "toBossHarmIncrease");
        AttributeName.put(197, "toGoldHarmIncrease");
        AttributeName.put(198, "toMinionsHarmIncrease");
        AttributeName.put(199, "cdMinus");
        AttributeName.put(200, "moderateHarmIncrease");
        AttributeName.put(201, "extraPartiAttribute");
        AttributeName.put(202, "maximumFlare");
        AttributeName.put(203, "healblue5s");
        AttributeName.put(204, "noIgnoreDefenseBase");
        AttributeName.put(205, "toSpellswordHarmIncrease");
        AttributeName.put(206, "fromSpellswordHarmDecrease");
        AttributeName.put(207, "trebleDropRate");
        AttributeName.put(208, "fivefoldDropRate");
        AttributeName.put(209, "tenfoldDropRate");
        AttributeName.put(210, "xin");
        AttributeName.put(211, "li");
        AttributeName.put(212, "ti");
        AttributeName.put(213, "shen");
        AttributeName.put(214, "resistDefense");
        AttributeName.put(215, "holyChance");
        AttributeName.put(216, "holyDamage");
        AttributeName.put(217, "kungFuDefense");
        AttributeName.put(218, "kungFuPvm");
        AttributeName.put(219, "kungFuRate1");
        AttributeName.put(220, "kungFuRate2");
        AttributeName.put(221, "doubleHitRate");
        AttributeName.put(222, "angerTime");
        AttributeName.put(223, "mpReduceRate");
        AttributeName.put(224, "cureRate");
        AttributeName.put(225, "castSkillDistance");
        AttributeName.put(226, "charm");
        AttributeName.put(227, "angerRate");
        AttributeName.put(228, "pvpAddRatio");
        AttributeName.put(229, "pvpReduceRatio");
        AttributeName.put(230, "angerFixed");
        AttributeName.put(231, "doubleHitRate2");
        AttributeName.put(232, "liLianRate");
        AttributeName.put(233, "suckRate");
        AttributeName.put(234, "qiGongLevelUp");
        AttributeName.put(235, "deadlyProbability");
        AttributeName.put(236, "deadlyRate");
        AttributeName.put(237, "deadlyFixedValue");
        AttributeName.put(238, "kungFuDodge");
        AttributeName.put(239, "resistDeadlyRate");
        AttributeName.put(240, "luckRate");
        AttributeName.put(241, "resistLuckRate");
        AttributeName.put(242, "resistDeadlyProbability");
        AttributeName.put(243, "resistLuckProbability");
        AttributeName.put(244, "resistKungFuDefense");
        AttributeName.put(245, "demon");
        AttributeName.put(246, "militaryLevel");
        AttributeName.put(247, "militaryHurt");
        AttributeName.put(248, "militaryHurtRate");
        AttributeName.put(249, "suckFixedValue");
        AttributeName.put(250, "pveAddHurtRate");
        AttributeName.put(251, "pveMinusHurtRate");
        AttributeName.put(252, "pvpAddFixed");
        AttributeName.put(253, "pvpReduceFixed");

        AttributeName.put(254, "career1HurtRate");
        AttributeName.put(255, "career4HurtRate");
        AttributeName.put(256, "career2HurtRate");
        AttributeName.put(257, "career3HurtRate");
        AttributeName.put(258, "career5HurtRate");

        AttributeName.put(259, "career1HurtReduceRate");
        AttributeName.put(260, "career4HurtReduceRate");
        AttributeName.put(261, "career2HurtReduceRate");
        AttributeName.put(262, "career3HurtReduceRate");
        AttributeName.put(263, "career5HurtReduceRate");

        AttributeName.put(264, "bossFixedValue");
        AttributeName.put(265, "suckRateResist");
        AttributeName.put(266, "petHurtRate");
        AttributeName.put(267, "petHurtReduceRate");
        AttributeName.put(268, "pvpSuckRate");

        AttributeName.put(269, "kungFuHit");
        AttributeName.put(270, "kungFuSkillAddFixedValue");
        AttributeName.put(271, "baseRateWeapon");
        AttributeName.put(272, "baseRateCloth");
        AttributeName.put(273, "baseRateInsideCloth");
        AttributeName.put(274, "baseRateArmLeft");
        AttributeName.put(275, "baseRateArmRight");
        AttributeName.put(276, "dodgeChance");
        AttributeName.put(277, "baseRateShoes");
        AttributeName.put(278, "damageReflectionFixedValue");
        AttributeName.put(279, "damageReflectionReduceFixedValue");
        AttributeName.put(280, "angerAddTimeOnTeam");
        AttributeName.put(281, "feiShengLiLianRate");
        AttributeName.put(282, "godAttack");
        AttributeName.put(283, "godDefense");
        AttributeName.put(284, "teamMapMonsterHurtRate");
        AttributeName.put(285, "expPoint");
        AttributeName.put(286, "kungFuDefenseToMon");
        AttributeName.put(287, "duanTiExpRate");

        AttributeName.put(288, "godWeaponMapMonsterHurtRate");
        AttributeName.put(289, "godWeaponMapMonsterFixedValue");
        AttributeName.put(290, "damageReflectionReduceRate");
        AttributeName.put(291, "monsterHpRecoverFixedValue");
        AttributeName.put(292, "monsterHpRecoverReduceRate");
        AttributeName.put(293, "dodgeChanceReduce");

        NameTransformId = AttributeName.inverse();
    }

    public static void setAttribute(String filed, Attribute attribute, int i, long l) {
        switch (filed) {
            case "strength":
                attribute.setStrength(i);
                break;
            case "agility":
                attribute.setAgility(i);
                break;
            case "vitality":
                attribute.setVitality(i);
                break;
            case "energy":
                attribute.setEnergy(i);
                break;
            case "leadership":
                attribute.setLeadership(i);
                break;
            case "experienceRate":
                attribute.setExperienceRate(i);
                break;
            case "moneyAmountRate":
                attribute.setMoneyAmountRate(i);
                break;
            case "attackSpeed":
                attribute.setAttackSpeed(i);
                break;
            case "moveSpeed":
                attribute.setMoveSpeed(i);
                break;
            case "sellGoldUpRatio":
                attribute.setSellGoldUpRatio(i);
                break;
            case "maximumHealth":
                attribute.setMaximumHealth(l);
                break;
            case "maximumMana":
                attribute.setMaximumMana(i);
                break;
            case "maximumShield":
                attribute.setMaximumShield(i);
                break;
            case "maximumAbility":
                attribute.setMaximumAbility(i);
                break;
            case "healthRecoveryMultiplier":
                attribute.setHealthRecoveryMultiplier(i);
                break;
            case "manaRecoveryMultiplier":
                attribute.setManaRecoveryMultiplier(i);
                break;
            case "shieldRecoveryMultiplier":
                attribute.setShieldRecoveryMultiplier(i);
                break;
            case "abilityRecoveryMultiplier":
                attribute.setAbilityRecoveryMultiplier(i);
                break;
            case "healthRecoveryAbsolute":
                attribute.setHealthRecoveryAbsolute(i);
                break;
            case "manaRecoveryAbsolute":
                attribute.setManaRecoveryAbsolute(i);
                break;
            case "shieldRecoveryAbsolute":
                attribute.setShieldRecoveryAbsolute(i);
                break;
            case "abilityRecoveryAbsolute":
                attribute.setAbilityRecoveryAbsolute(i);
                break;
            case "healthAfterMonsterKillMultiplier":
                attribute.setHealthAfterMonsterKillMultiplier(i);
                break;
            case "manaAfterMonsterKillMultiplier":
                attribute.setManaAfterMonsterKillMultiplier(i);
                break;
            case "shieldAfterMonsterKillMultiplier":
                attribute.setShieldAfterMonsterKillMultiplier(i);
                break;
            case "abilityAfterMonsterKillMultiplier":
                attribute.setAbilityAfterMonsterKillMultiplier(i);
                break;
            case "healthAfterMonsterKillAbsolute":
                attribute.setHealthAfterMonsterKillAbsolute(i);
                break;
            case "manaAfterMonsterKillAbsolute":
                attribute.setManaAfterMonsterKillAbsolute(i);
                break;
            case "shieldAfterMonsterKillAbsolute":
                attribute.setShieldAfterMonsterKillAbsolute(i);
                break;
            case "abilityAfterMonsterKillAbsolute":
                attribute.setAbilityAfterMonsterKillAbsolute(i);
                break;
            case "attackRatePvm":
                attribute.setAttackRatePvm(i);
                break;
            case "attackRatePvp":
                attribute.setAttackRatePvp(i);
                break;
            case "defenseRatePvm":
                attribute.setDefenseRatePvm(i);
                break;
            case "defenseRatePvp":
                attribute.setDefenseRatePvp(i);
                break;
            case "criticalDamageBonus":
                attribute.setCriticalDamageBonus(i);
                break;
            case "excellentDamageBonus":
                attribute.setExcellentDamageBonus(i);
                break;
            case "criticalDamageChance":
                attribute.setCriticalDamageChance(i);
                break;
            case "excellentDamageChance":
                attribute.setExcellentDamageChance(i);
                break;
            case "minimumPhysBaseDmg":
                attribute.setMinimumPhysBaseDmg(i);
                break;
            case "maximumPhysBaseDmg":
                attribute.setMaximumPhysBaseDmg(i);
                break;
            case "minimumWizBaseDmg":
                attribute.setMinimumWizBaseDmg(i);
                break;
            case "maximumWizBaseDmg":
                attribute.setMaximumWizBaseDmg(i);
                break;
            case "minimumCurseBaseDmg":
                attribute.setMinimumCurseBaseDmg(i);
                break;
            case "maximumCurseBaseDmg":
                attribute.setMaximumCurseBaseDmg(i);
                break;
            case "defenseIgnoreChance":
                attribute.setDefenseIgnoreChance(i);
                break;
            case "defenseBase":
                attribute.setDefenseBase(i);
                break;
            case "defensePvm":
                attribute.setDefensePvm(i);
                break;
            case "defensePvp":
                attribute.setDefensePvp(i);
                break;
            case "defenseIncreaseWithEquippedShield":
                attribute.setDefenseIncreaseWithEquippedShield(i);
                break;
            case "damageReceiveDecrement":
                attribute.setDamageReceiveDecrement(i);
                break;
            case "attackDamageIncrease":
                attribute.setAttackDamageIncrease(i);
                break;
            case "skillDamageBonus":
                attribute.setSkillDamageBonus(i);
                break;
            case "skillMultiplier":
                attribute.setSkillMultiplier(i);
                break;
            case "twoHandedWeaponEquipped":
                attribute.setTwoHandedWeaponEquipped(i >= 1);
                break;
            case "twoHandedWeaponDamageIncrease":
                attribute.setTwoHandedWeaponDamageIncrease(i);
                break;
            case "finalDamageIncreasePvp":
                attribute.setFinalDamageIncreasePvp(i);
                break;
            case "doubleDamageChance":
                attribute.setDoubleDamageChance(i);
                break;
            case "shieldBypassChance":
                attribute.setShieldBypassChance(i);
                break;
            case "shieldDecreaseRateIncrease":
                attribute.setShieldDecreaseRateIncrease(i);
                break;
            case "shieldRateIncrease":
                attribute.setShieldRateIncrease(i);
                break;
            case "damageReflection":
                attribute.setDamageReflection(i);
                break;
            case "iceResistance":
                attribute.setIceResistance(i);
                break;
            case "fireResistance":
                attribute.setFireResistance(i);
                break;
            case "waterResistance":
                attribute.setWaterResistance(i);
                break;
            case "earthResistance":
                attribute.setEarthResistance(i);
                break;
            case "windResistance":
                attribute.setWindResistance(i);
                break;
            case "poisonResistance":
                attribute.setPoisonResistance(i);
                break;
            case "lightningResistance":
                attribute.setLightningResistance(i);
                break;
            case "shieldRecoveryEverywhere":
                attribute.setShieldRecoveryEverywhere(i);
                break;
            case "abilityUsageReduction":
                attribute.setAbilityUsageReduction(i);
                break;
            case "manaUsageReduction":
                attribute.setManaUsageReduction(i);
                break;
            case "itemDurationIncrease":
                attribute.setItemDurationIncrease(i);
                break;
            case "petDurationIncrease":
                attribute.setPetDurationIncrease(i);
                break;
            case "fullyRecoverManaAfterHitChance":
                attribute.setFullyRecoverManaAfterHitChance(i);
                break;
            case "fullyRecoverHealthAfterHitChance":
                attribute.setFullyRecoverHealthAfterHitChance(i);
                break;
            case "requiredStrengthReduction":
                attribute.setRequiredStrengthReduction(i);
                break;
            case "requiredAgilityReduction":
                attribute.setRequiredAgilityReduction(i);
                break;
            case "requiredVitalityReduction":
                attribute.setRequiredVitalityReduction(i);
                break;
            case "requiredEnergyReduction":
                attribute.setRequiredEnergyReduction(i);
                break;
            case "requiredLeadershipReduction":
                attribute.setRequiredLeadershipReduction(i);
                break;
            case "fight":
                attribute.setFight(i);
                break;
            case "petAttackDamageIncrease":
                attribute.setPetAttackDamageIncrease(i);
                break;
            case "defenseIgnoreChanceResistance":
                attribute.setDefenseIgnoreChanceResistance(i);
                break;
            case "shieldBypassChanceResistance":
                attribute.setShieldBypassChanceResistance(i);
                break;
            case "doubleDamageChanceResistance":
                attribute.setDoubleDamageChanceResistance(i);
                break;
            case "excellentDamageChanceResistance":
                attribute.setExcellentDamageChanceResistance(i);
                break;
            case "criticalDamageBonusResistance":
                attribute.setCriticalDamageBonusResistance(i);
                break;
            case "attackDistanceIncrease":
                attribute.setAttackDistanceIncrease(i);
                break;
            case "physBaseDmgByLevel":
                attribute.setPhysBaseDmgByLevel(i);
                break;
            case "wizBaseDmgByLevel":
                attribute.setWizBaseDmgByLevel(i);
                break;
            case "reducedFixedHealthPerSuccessfulAttack":
                attribute.setReducedFixedHealthPerSuccessfulAttack(i);
                break;
            case "staticMoveSpeed":
                attribute.setStaticMoveSpeed(i);
                break;
            case "extraDamage":
                attribute.setExtraDamage(i);
                break;
            case "receivedDamageReduceRate":
                attribute.setReceivedDamageReduceRate(i);
                break;
            case "receivedDamageReduceRateFix":
                attribute.setReceivedDamageReduceRateFix(i);
                break;
            case "hurtReduceMPRate":
                attribute.setHurtReduceMPRate(i);
                break;
            case "hurtReduceMPFix":
                attribute.setHurtReduceMPFix(i);
                break;
            case "damageIncreaseRate":
                attribute.setDamageIncreaseRate(i);
                break;
            case "damageIncreaseFix":
                attribute.setDamageIncreaseFix(i);
                break;
            case "oneHandedWeaponIncRate":
                attribute.setOneHandedWeaponIncRate(i);
                break;
            case "bagLatticeNumberUp":
                attribute.setBagLatticeNumberUp(i);
                break;
            case "sellBlueDiamondsUpRatio":
                attribute.setSellBlueDiamondsUpRatio(i);
                break;
            case "auctionSaleMaxUp":
                attribute.setAuctionSaleMaxUp(i);
                break;
            case "autoPickOpen":
                attribute.setAutoPickOpen(i);
                break;
            case "monsterDropRate":
                attribute.setMonsterDropRate(i);
                break;
            case "damageBonus":
                attribute.setDamageBonus(i);
                break;
            case "damageAbsorption":
                attribute.setDamageAbsorption(i);
                break;
            case "excellenceDamageDecrement":
                attribute.setExcellenceDamageDecrement(i);
                break;
            case "entryRating":
                attribute.setEntryRating(i);
                break;
            case "autoBugDrugs":
                attribute.setAutoBugDrugs(i);
                break;
            case "expDrugsUp":
                attribute.setExpDrugsUp(i);
                break;
            case "pushedImmunity":
                attribute.setPushedImmunity(i >= 1);
                break;
            case "basicDefenseRate":
                attribute.setBasicDefenseRate(i);
                break;
            case "extraIntensifyAttributeIncrease":
                attribute.setExtraIntensifyAttributeIncrease(i);
                break;
            case "extraAdditionalAttributeIncrease":
                attribute.setExtraAdditionalAttributeIncrease(i);
                break;
            case "baseDefenseByLevel":
                attribute.setBaseDefenseByLevel(i);
                break;
            case "moveSpeedResistance":
                attribute.setMoveSpeedResistance(i);
                break;
            case "leaveHealthRecoveryMultiplier":
                attribute.setLeaveHealthRecoveryMultiplier(i);
                break;
            case "monsterDamageAbsorption":
                attribute.setMonsterDamageAbsorption(i);
                break;
            case "positionDamageAbsorption":
                attribute.setPositionDamageAbsorption(i);
                break;
            case "hangUpProtection":
                attribute.setHangUpProtection(i);
                break;
            case "comboRecovery":
                attribute.setComboRecovery(i);
                break;
            case "attackSpeedCalculateValue":
                attribute.setAttackSpeedCalculateValue(i);
                break;
            case "damageReceiveDecrementBuff":
                attribute.setDamageReceiveDecrementBuff(i);
                break;
            case "stoneDropRate":
                attribute.setStoneDropRate(i);
                break;
            case "exEquipDropRate":
                attribute.setExEquipDropRate(i);
                break;
            case "doubleDropRate":
                attribute.setDoubleDropRate(i);
                break;
            case "heal5s":
                attribute.setHeal5s(i);
                break;
            case "baseHealthByLevel":
                attribute.setBaseHealthByLevel(i);
                break;
            case "potionRate":
                attribute.setPotionRate(i);
                break;
            case "comboDamage":
                attribute.setComboDamage(i);
                break;
            case "comboAdditionalRatio":
                attribute.setComboAdditionalRatio(i);
                break;
            case "toWarriorHarmIncrease":
                attribute.setToWarriorHarmIncrease(i);
                break;
            case "toMagicianHarmIncrease":
                attribute.setToMagicianHarmIncrease(i);
                break;
            case "toArcherHarmIncrease":
                attribute.setToArcherHarmIncrease(i);
                break;
            case "fromWarriorHarmDecrease":
                attribute.setFromWarriorHarmDecrease(i);
                break;
            case "fromMagicianHarmDecrease":
                attribute.setFromMagicianHarmDecrease(i);
                break;
            case "fromArcherHarmDecrease":
                attribute.setFromArcherHarmDecrease(i);
                break;
            case "toPlayerHarmIncrease":
                attribute.setToPlayerHarmIncrease(i);
                break;
            case "fromPlayerHarmDecrease":
                attribute.setFromPlayerHarmDecrease(i);
                break;
            case "damageReflectionDecrease":
                attribute.setDamageReflectionDecrease(i);
                break;
            case "runeHoleLevelUp":
                attribute.setRuneHoleLevelUp(i);
                break;
            case "toBossHarmIncrease":
                attribute.setToBossHarmIncrease(i);
                break;
            case "toGoldHarmIncrease":
                attribute.setToGoldHarmIncrease(i);
                break;
            case "toMinionsHarmIncrease":
                attribute.setToMinionsHarmIncrease(i);
                break;
            case "cdMinus":
                attribute.setCdMinus(i);
                break;
            case "moderateHarmIncrease":
                attribute.setModerateHarmIncrease(i);
                break;
            case "extraPartiAttribute":
                attribute.setExtraPartiAttribute(i);
                break;
            case "maximumFlare":
                attribute.setMaximumFlare(i);
                break;
            case "healblue5s":
                attribute.setHealblue5s(i);
                break;
            case "noIgnoreDefenseBase":
                attribute.setNoIgnoreDefenseBase(i);
                break;
            case "toSpellswordHarmIncrease":
                attribute.setToSpellswordHarmIncrease(i);
                break;
            case "fromSpellswordHarmDecrease":
                attribute.setFromSpellswordHarmDecrease(i);
                break;
            case "trebleDropRate":
                attribute.setTrebleDropRate(i);
                break;
            case "fivefoldDropRate":
                attribute.setFivefoldDropRate(i);
                break;
            case "tenfoldDropRate":
                attribute.setTenfoldDropRate(i);
                break;
            case "xin":
                attribute.setXin(i);
                break;
            case "li":
                attribute.setLi(i);
                break;
            case "ti":
                attribute.setTi(i);
                break;
            case "shen":
                attribute.setShen(i);
                break;
            case "resistDefense":
                attribute.setResistDefense(i);
                break;
            case "holyChance":
                attribute.setHolyChance(i);
                break;
            case "holyDamage":
                attribute.setHolyDamage(i);
                break;
            case "kungFuDefense":
                attribute.setKungFuDefense(i);
                break;
            case "kungFuPvm":
                attribute.setKungFuPvm(i);
                break;
            case "kungFuRate1":
                attribute.setKungFuRate1(i);
                break;
            case "kungFuRate2":
                attribute.setKungFuRate2(i);
                break;
            case "doubleHitRate":
                attribute.setDoubleHitRate(i);
                break;
            case "angerTime":
                attribute.setAngerTime(i);
                break;
            case "mpReduceRate":
                attribute.setMpReduceRate(i);
                break;
            case "cureRate":
                attribute.setCureRate(i);
                break;
            case "castSkillDistance":
                attribute.setCastSkillDistance(i);
                break;
            case "charm":
                attribute.setCharm(i);
                break;
            case "angerRate": {
                attribute.setAngerRate(i);
                break;
            }
            case "pvpAddRatio": {
                attribute.setPvpAddRatio(i);
                break;
            }
            case "pvpReduceRatio": {
                attribute.setPvpReduceRatio(i);
                break;
            }
            case "angerFixed": {
                attribute.setAngerFixed(i);
                break;
            }
            case "doubleHitRate2": {
                attribute.setDoubleHitRate2(i);
                break;
            }
            case "liLianRate": {
                attribute.setLiLianRate(i);
                break;
            }
            case "suckRate": {
                attribute.setSuckRate(i);
                break;
            }
            case "qiGongLevelUp": {
                attribute.setQiGongLevelUp(i);
                break;
            }
            case "deadlyProbability": {
                attribute.setDeadlyProbability(i);
                break;
            }
            case "deadlyRate": {
                attribute.setDeadlyRate(i);
                break;
            }
            case "deadlyFixedValue": {
                attribute.setDeadlyFixedValue(i);
                break;
            }
            case "resistDeadlyRate": {
                attribute.setResistDeadlyRate(i);
                break;
            }
            case "kungFuDodge": {
                attribute.setKungFuDodge(i);
                break;
            }
            case "luckRate": {
                attribute.setLuckRate(i);
                break;
            }
            case "resistLuckRate": {
                attribute.setResistLuckRate(i);
                break;
            }
            case "resistDeadlyProbability": {
                attribute.setResistDeadlyProbability(i);
                break;
            }
            case "resistLuckProbability": {
                attribute.setResistLuckProbability(i);
                break;
            }
            case "resistKungFuDefense": {
                attribute.setResistKungFuDefense(i);
                break;
            }
            case "demon": {
                attribute.setDemon(i);
                break;
            }
            case "militaryLevel": {
                attribute.setMilitaryLevel(i);
                break;
            }
            case "militaryHurt": {
                attribute.setMilitaryHurt(i);
                break;
            }
            case "militaryHurtRate": {
                attribute.setMilitaryHurtRate(i);
                break;
            }
            case "suckFixedValue": {
                attribute.setSuckFixedValue(i);
                break;
            }
            case "pveAddHurtRate": {
                attribute.setPveAddHurtRate(i);
                break;
            }
            case "pveMinusHurtRate": {
                attribute.setPveMinusHurtRate(i);
                break;
            }
            case "pvpAddFixed": {
                attribute.setPvpAddFixed(i);
                break;
            }
            case "pvpReduceFixed": {
                attribute.setPvpReduceFixed(i);
                break;
            }

            case "career1HurtRate": {
                attribute.setCareer1HurtRate(i);
                break;
            }
            case "career4HurtRate": {
                attribute.setCareer4HurtRate(i);
                break;
            }
            case "career2HurtRate": {
                attribute.setCareer2HurtRate(i);
                break;
            }
            case "career3HurtRate": {
                attribute.setCareer3HurtRate(i);
                break;
            }
            case "career5HurtRate": {
                attribute.setCareer5HurtRate(i);
                break;
            }

            case "career1HurtReduceRate": {
                attribute.setCareer1HurtReduceRate(i);
                break;
            }
            case "career4HurtReduceRate": {
                attribute.setCareer4HurtReduceRate(i);
                break;
            }
            case "career2HurtReduceRate": {
                attribute.setCareer2HurtReduceRate(i);
                break;
            }
            case "career3HurtReduceRate": {
                attribute.setCareer3HurtReduceRate(i);
                break;
            }
            case "career5HurtReduceRate": {
                attribute.setCareer5HurtReduceRate(i);
                break;
            }

            case "bossFixedValue": {
                attribute.setBossFixedValue(i);
                break;
            }
            case "suckRateResist": {
                attribute.setSuckRateResist(i);
                break;
            }
            case "petHurtRate": {
                attribute.setPetHurtRate(i);
                break;
            }
            case "petHurtReduceRate": {
                attribute.setPetHurtReduceRate(i);
                break;
            }
            case "pvpSuckRate": {
                attribute.setPvpSuckRate(i);
                break;
            }
            case "kungFuHit": {
                attribute.setKungFuHit(i);
                break;
            }
            case "kungFuSkillAddFixedValue": {
                attribute.setKungFuSkillAddFixedValue(i);
                break;
            }
            case "baseRateWeapon": {
                attribute.setBaseRateWeapon(i);
                break;
            }
            case "baseRateCloth": {
                attribute.setBaseRateCloth(i);
                break;
            }
            case "baseRateInsideCloth": {
                attribute.setBaseRateInsideCloth(i);
                break;
            }
            case "baseRateArmLeft": {
                attribute.setBaseRateArmLeft(i);
                break;
            }
            case "baseRateArmRight": {
                attribute.setBaseRateArmRight(i);
                break;
            }
            case "dodgeChance": {
                attribute.setDodgeChance(i);
                break;
            }
            case "baseRateShoes": {
                attribute.setBaseRateShoes(i);
                break;
            }
            case "damageReflectionFixedValue": {
                attribute.setDamageReflectionFixedValue(i);
                break;
            }
            case "damageReflectionReduceFixedValue": {
                attribute.setDamageReflectionReduceFixedValue(i);
                break;
            }
            case "angerAddTimeOnTeam": {
                attribute.setAngerAddTimeOnTeam(i);
                break;
            }
            case "feiShengLiLianRate": {
                attribute.setFeiShengLiLianRate(i);
                break;
            }
            case "godAttack": {
                attribute.setGodAttack(i);
                break;
            }
            case "godDefense": {
                attribute.setGodDefense(i);
                break;
            }
            case "teamMapMonsterHurtRate": {
                attribute.setTeamMapMonsterHurtRate(i);
                break;
            }
            case "expPoint": {
                attribute.setExpPoint(i);
                break;
            }
            case "kungFuDefenseToMon": {
                attribute.setKungFuDefenseToMon(i);
                break;
            }
            case "duanTiExpRate": {
                attribute.setDuanTiExpRate(i);
                break;
            }
            case "godWeaponMapMonsterHurtRate": {
                attribute.setGodWeaponMapMonsterHurtRate(i);
                break;
            }
            case "godWeaponMapMonsterFixedValue": {
                attribute.setGodWeaponMapMonsterFixedValue(i);
                break;
            }
            case "damageReflectionReduceRate": {
                attribute.setDamageReflectionReduceRate(i);
                break;
            }
            case "monsterHpRecoverFixedValue": {
                attribute.setMonsterHpRecoverFixedValue(i);
                break;
            }
            case "monsterHpRecoverReduceRate": {
                attribute.setMonsterHpRecoverReduceRate(i);
                break;
            }
            case "dodgeChanceReduce": {
                attribute.setDodgeChanceReduce(i);
                break;
            }
        }
    }

    public static long getAttribute(int filed, Attribute attribute) {
        String name = AttributeName.get(filed);
        if (null != name) {
            return getAttribute(name, attribute);
        }
        return 0L;
    }

    public static long getAttribute(String filed, Attribute attribute) {
        return switch (filed) {
            case "strength" -> attribute.getStrength();
            case "agility" -> attribute.getAgility();
            case "vitality" -> attribute.getVitality();
            case "energy" -> attribute.getEnergy();
            case "leadership" -> attribute.getLeadership();
            case "experienceRate" -> attribute.getExperienceRate();
            case "moneyAmountRate" -> attribute.getMoneyAmountRate();
            case "attackSpeed" -> attribute.getAttackSpeed();
            case "moveSpeed" -> attribute.getMoveSpeed();
            case "sellGoldUpRatio" -> attribute.getSellGoldUpRatio();
            case "maximumHealth" -> attribute.getMaximumHealth();
            case "maximumMana" -> attribute.getMaximumMana();
            case "maximumShield" -> attribute.getMaximumShield();
            case "maximumAbility" -> attribute.getMaximumAbility();
            case "healthRecoveryMultiplier" -> attribute.getHealthRecoveryMultiplier();
            case "manaRecoveryMultiplier" -> attribute.getManaRecoveryMultiplier();
            case "shieldRecoveryMultiplier" -> attribute.getShieldRecoveryMultiplier();
            case "abilityRecoveryMultiplier" -> attribute.getAbilityRecoveryMultiplier();
            case "healthRecoveryAbsolute" -> attribute.getHealthRecoveryAbsolute();
            case "manaRecoveryAbsolute" -> attribute.getManaRecoveryAbsolute();
            case "shieldRecoveryAbsolute" -> attribute.getShieldRecoveryAbsolute();
            case "abilityRecoveryAbsolute" -> attribute.getAbilityRecoveryAbsolute();
            case "healthAfterMonsterKillMultiplier" -> attribute.getHealthAfterMonsterKillMultiplier();
            case "manaAfterMonsterKillMultiplier" -> attribute.getManaAfterMonsterKillMultiplier();
            case "shieldAfterMonsterKillMultiplier" -> attribute.getShieldAfterMonsterKillMultiplier();
            case "abilityAfterMonsterKillMultiplier" -> attribute.getAbilityAfterMonsterKillMultiplier();
            case "healthAfterMonsterKillAbsolute" -> attribute.getHealthAfterMonsterKillAbsolute();
            case "manaAfterMonsterKillAbsolute" -> attribute.getManaAfterMonsterKillAbsolute();
            case "shieldAfterMonsterKillAbsolute" -> attribute.getShieldAfterMonsterKillAbsolute();
            case "abilityAfterMonsterKillAbsolute" -> attribute.getAbilityAfterMonsterKillAbsolute();
            case "attackRatePvm" -> attribute.getAttackRatePvm();
            case "attackRatePvp" -> attribute.getAttackRatePvp();
            case "defenseRatePvm" -> attribute.getDefenseRatePvm();
            case "defenseRatePvp" -> attribute.getDefenseRatePvp();
            case "criticalDamageBonus" -> attribute.getCriticalDamageBonus();
            case "excellentDamageBonus" -> attribute.getExcellentDamageBonus();
            case "criticalDamageChance" -> attribute.getCriticalDamageChance();
            case "excellentDamageChance" -> attribute.getExcellentDamageChance();
            case "minimumPhysBaseDmg" -> attribute.getMinimumPhysBaseDmg();
            case "maximumPhysBaseDmg" -> attribute.getMaximumPhysBaseDmg();
            case "minimumWizBaseDmg" -> attribute.getMinimumWizBaseDmg();
            case "maximumWizBaseDmg" -> attribute.getMaximumWizBaseDmg();
            case "minimumCurseBaseDmg" -> attribute.getMinimumCurseBaseDmg();
            case "maximumCurseBaseDmg" -> attribute.getMaximumCurseBaseDmg();
            case "defenseIgnoreChance" -> attribute.getDefenseIgnoreChance();
            case "defenseBase" -> attribute.getDefenseBase();
            case "defensePvm" -> attribute.getDefensePvm();
            case "defensePvp" -> attribute.getDefensePvp();
            case "defenseIncreaseWithEquippedShield" -> attribute.getDefenseIncreaseWithEquippedShield();
            case "damageReceiveDecrement" -> attribute.getDamageReceiveDecrement();
            case "attackDamageIncrease" -> attribute.getAttackDamageIncrease();
            case "skillDamageBonus" -> attribute.getSkillDamageBonus();
            case "skillMultiplier" -> attribute.getSkillMultiplier();
            case "twoHandedWeaponEquipped" -> attribute.isTwoHandedWeaponEquipped() ? 1 : 0;
            case "twoHandedWeaponDamageIncrease" -> attribute.getTwoHandedWeaponDamageIncrease();
            case "finalDamageIncreasePvp" -> attribute.getFinalDamageIncreasePvp();
            case "doubleDamageChance" -> attribute.getDoubleDamageChance();
            case "shieldBypassChance" -> attribute.getShieldBypassChance();
            case "shieldDecreaseRateIncrease" -> attribute.getShieldDecreaseRateIncrease();
            case "shieldRateIncrease" -> attribute.getShieldRateIncrease();
            case "damageReflection" -> attribute.getDamageReflection();
            case "iceResistance" -> attribute.getIceResistance();
            case "fireResistance" -> attribute.getFireResistance();
            case "waterResistance" -> attribute.getWaterResistance();
            case "earthResistance" -> attribute.getEarthResistance();
            case "windResistance" -> attribute.getWindResistance();
            case "poisonResistance" -> attribute.getPoisonResistance();
            case "lightningResistance" -> attribute.getLightningResistance();
            case "shieldRecoveryEverywhere" -> attribute.getShieldRecoveryEverywhere();
            case "abilityUsageReduction" -> attribute.getAbilityUsageReduction();
            case "manaUsageReduction" -> attribute.getManaUsageReduction();
            case "itemDurationIncrease" -> attribute.getItemDurationIncrease();
            case "petDurationIncrease" -> attribute.getPetDurationIncrease();
            case "fullyRecoverManaAfterHitChance" -> attribute.getFullyRecoverManaAfterHitChance();
            case "fullyRecoverHealthAfterHitChance" -> attribute.getFullyRecoverHealthAfterHitChance();
            case "requiredStrengthReduction" -> attribute.getRequiredStrengthReduction();
            case "requiredAgilityReduction" -> attribute.getRequiredAgilityReduction();
            case "requiredVitalityReduction" -> attribute.getRequiredVitalityReduction();
            case "requiredEnergyReduction" -> attribute.getRequiredEnergyReduction();
            case "requiredLeadershipReduction" -> attribute.getRequiredLeadershipReduction();
            case "fight" -> attribute.getFight();
            case "petAttackDamageIncrease" -> attribute.getPetAttackDamageIncrease();
            case "defenseIgnoreChanceResistance" -> attribute.getDefenseIgnoreChanceResistance();
            case "shieldBypassChanceResistance" -> attribute.getShieldBypassChanceResistance();
            case "doubleDamageChanceResistance" -> attribute.getDoubleDamageChanceResistance();
            case "excellentDamageChanceResistance" -> attribute.getExcellentDamageChanceResistance();
            case "criticalDamageBonusResistance" -> attribute.getCriticalDamageBonusResistance();
            case "attackDistanceIncrease" -> attribute.getAttackDistanceIncrease();
            case "physBaseDmgByLevel" -> attribute.getPhysBaseDmgByLevel();
            case "wizBaseDmgByLevel" -> attribute.getWizBaseDmgByLevel();
            case "reducedFixedHealthPerSuccessfulAttack" -> attribute.getReducedFixedHealthPerSuccessfulAttack();
            case "staticMoveSpeed" -> attribute.getStaticMoveSpeed();
            case "extraDamage" -> attribute.getExtraDamage();
            case "receivedDamageReduceRate" -> attribute.getReceivedDamageReduceRate();
            case "receivedDamageReduceRateFix" -> attribute.getReceivedDamageReduceRateFix();
            case "hurtReduceMPRate" -> attribute.getHurtReduceMPRate();
            case "hurtReduceMPFix" -> attribute.getHurtReduceMPFix();
            case "damageIncreaseRate" -> attribute.getDamageIncreaseRate();
            case "damageIncreaseFix" -> attribute.getDamageIncreaseFix();
            case "oneHandedWeaponIncRate" -> attribute.getOneHandedWeaponIncRate();
            case "bagLatticeNumberUp" -> attribute.getBagLatticeNumberUp();
            case "sellBlueDiamondsUpRatio" -> attribute.getSellBlueDiamondsUpRatio();
            case "auctionSaleMaxUp" -> attribute.getAuctionSaleMaxUp();
            case "autoPickOpen" -> attribute.getAutoPickOpen();
            case "monsterDropRate" -> attribute.getMonsterDropRate();
            case "damageBonus" -> attribute.getDamageBonus();
            case "damageAbsorption" -> attribute.getDamageAbsorption();
            case "excellenceDamageDecrement" -> attribute.getExcellenceDamageDecrement();
            case "entryRating" -> attribute.getEntryRating();
            case "autoBugDrugs" -> attribute.getAutoBugDrugs();
            case "expDrugsUp" -> attribute.getExpDrugsUp();
            case "pushedImmunity" -> attribute.isPushedImmunity() ? 1 : 0;
            case "basicDefenseRate" -> attribute.getBasicDefenseRate();
            case "extraIntensifyAttributeIncrease" -> attribute.getExtraIntensifyAttributeIncrease();
            case "extraAdditionalAttributeIncrease" -> attribute.getExtraAdditionalAttributeIncrease();
            case "baseDefenseByLevel" -> attribute.getBaseDefenseByLevel();
            case "moveSpeedResistance" -> attribute.getMoveSpeedResistance();
            case "leaveHealthRecoveryMultiplier" -> attribute.getLeaveHealthRecoveryMultiplier();
            case "monsterDamageAbsorption" -> attribute.getMonsterDamageAbsorption();
            case "positionDamageAbsorption" -> attribute.getPositionDamageAbsorption();
            case "hangUpProtection" -> attribute.getHangUpProtection();
            case "comboRecovery" -> attribute.getComboRecovery();
            case "attackSpeedCalculateValue" -> attribute.getAttackSpeedCalculateValue();
            case "damageReceiveDecrementBuff" -> attribute.getDamageReceiveDecrementBuff();
            case "stoneDropRate" -> attribute.getStoneDropRate();
            case "exEquipDropRate" -> attribute.getExEquipDropRate();
            case "doubleDropRate" -> attribute.getDoubleDropRate();
            case "heal5s" -> attribute.getHeal5s();
            case "baseHealthByLevel" -> attribute.getBaseHealthByLevel();
            case "potionRate" -> attribute.getPotionRate();
            case "comboDamage" -> attribute.getComboDamage();
            case "comboAdditionalRatio" -> attribute.getComboAdditionalRatio();
            case "toWarriorHarmIncrease" -> attribute.getToWarriorHarmIncrease();
            case "toMagicianHarmIncrease" -> attribute.getToMagicianHarmIncrease();
            case "toArcherHarmIncrease" -> attribute.getToArcherHarmIncrease();
            case "fromWarriorHarmDecrease" -> attribute.getFromWarriorHarmDecrease();
            case "fromMagicianHarmDecrease" -> attribute.getFromMagicianHarmDecrease();
            case "fromArcherHarmDecrease" -> attribute.getFromArcherHarmDecrease();
            case "toPlayerHarmIncrease" -> attribute.getToPlayerHarmIncrease();
            case "fromPlayerHarmDecrease" -> attribute.getFromPlayerHarmDecrease();
            case "damageReflectionDecrease" -> attribute.getDamageReflectionDecrease();
            case "runeHoleLevelUp" -> attribute.getRuneHoleLevelUp();
            case "toBossHarmIncrease" -> attribute.getToBossHarmIncrease();
            case "toGoldHarmIncrease" -> attribute.getToGoldHarmIncrease();
            case "toMinionsHarmIncrease" -> attribute.getToMinionsHarmIncrease();
            case "cdMinus" -> attribute.getCdMinus();
            case "moderateHarmIncrease" -> attribute.getModerateHarmIncrease();
            case "extraPartiAttribute" -> attribute.getExtraPartiAttribute();
            case "maximumFlare" -> attribute.getMaximumFlare();
            case "healblue5s" -> attribute.getHealblue5s();
            case "noIgnoreDefenseBase" -> attribute.getNoIgnoreDefenseBase();
            case "toSpellswordHarmIncrease" -> attribute.getToSpellswordHarmIncrease();
            case "fromSpellswordHarmDecrease" -> attribute.getFromSpellswordHarmDecrease();
            case "trebleDropRate" -> attribute.getTrebleDropRate();
            case "fivefoldDropRate" -> attribute.getFivefoldDropRate();
            case "tenfoldDropRate" -> attribute.getTenfoldDropRate();
            case "xin" -> attribute.getXin();
            case "li" -> attribute.getLi();
            case "ti" -> attribute.getTi();
            case "shen" -> attribute.getShen();
            case "resistDefense" -> attribute.getResistDefense();
            case "holyChance" -> attribute.getHolyChance();
            case "holyDamage" -> attribute.getHolyDamage();
            case "kungFuDefense" -> attribute.getKungFuDefense();
            case "kungFuPvm" -> attribute.getKungFuPvm();
            case "kungFuRate1" -> attribute.getKungFuRate1();
            case "kungFuRate2" -> attribute.getKungFuRate2();
            case "doubleHitRate" -> attribute.getDoubleHitRate();
            case "angerTime" -> attribute.getAngerTime();
            case "mpReduceRate" -> attribute.getMpReduceRate();
            case "cureRate" -> attribute.getCureRate();
            case "castSkillDistance" -> attribute.getCastSkillDistance();
            case "charm" -> attribute.getCharm();
            case "angerRate" -> attribute.getAngerRate();
            case "pvpAddRatio" -> attribute.getPvpAddRatio();
            case "pvpReduceRatio" -> attribute.getPvpReduceRatio();
            case "angerFixed" -> attribute.getAngerFixed();
            case "doubleHitRate2" -> attribute.getDoubleHitRate2();
            case "liLianRate" -> attribute.getLiLianRate();
            case "suckRate" -> attribute.getSuckRate();
            case "qiGongLevelUp" -> attribute.getQiGongLevelUp();
            case "deadlyProbability" -> attribute.getDeadlyProbability();
            case "deadlyRate" -> attribute.getDeadlyRate();
            case "deadlyFixedValue" -> attribute.getDeadlyFixedValue();
            case "kungFuDodge" -> attribute.getKungFuDodge();
            case "resistDeadlyRate" -> attribute.getResistDeadlyRate();
            case "luckRate" -> attribute.getLuckRate();
            case "resistLuckRate" -> attribute.getResistLuckRate();
            case "resistDeadlyProbability" -> attribute.getResistDeadlyProbability();
            case "resistLuckProbability" -> attribute.getResistLuckProbability();
            case "resistKungFuDefense" -> attribute.getResistKungFuDefense();
            case "demon" -> attribute.getDemon();
            case "militaryLevel" -> attribute.getMilitaryLevel();
            case "militaryHurt" -> attribute.getMilitaryHurt();
            case "militaryHurtRate" -> attribute.getMilitaryHurtRate();
            case "suckFixedValue" -> attribute.getSuckFixedValue();
            case "pveAddHurtRate" -> attribute.getPveAddHurtRate();
            case "pveMinusHurtRate" -> attribute.getPveMinusHurtRate();
            case "pvpAddFixed" -> attribute.getPvpAddFixed();
            case "pvpReduceFixed" -> attribute.getPvpReduceFixed();
            case "career1HurtRate" -> attribute.getCareer1HurtRate();
            case "career4HurtRate" -> attribute.getCareer4HurtRate();
            case "career2HurtRate" -> attribute.getCareer2HurtRate();
            case "career3HurtRate" -> attribute.getCareer3HurtRate();
            case "career5HurtRate" -> attribute.getCareer5HurtRate();
            case "career1HurtReduceRate" -> attribute.getCareer1HurtReduceRate();
            case "career4HurtReduceRate" -> attribute.getCareer4HurtReduceRate();
            case "career2HurtReduceRate" -> attribute.getCareer2HurtReduceRate();
            case "career3HurtReduceRate" -> attribute.getCareer3HurtReduceRate();
            case "career5HurtReduceRate" -> attribute.getCareer5HurtReduceRate();
            case "bossFixedValue" -> attribute.getBossFixedValue();
            case "suckRateResist" -> attribute.getSuckRateResist();
            case "petHurtRate" -> attribute.getPetHurtRate();
            case "petHurtReduceRate" -> attribute.getPetHurtReduceRate();
            case "pvpSuckRate" -> attribute.getPvpSuckRate();
            case "kungFuHit" -> attribute.getKungFuHit();
            case "kungFuSkillAddFixedValue" -> attribute.getKungFuSkillAddFixedValue();
            case "baseRateWeapon" -> attribute.getBaseRateWeapon();
            case "baseRateCloth" -> attribute.getBaseRateCloth();
            case "baseRateInsideCloth" -> attribute.getBaseRateInsideCloth();
            case "baseRateArmLeft" -> attribute.getBaseRateArmLeft();
            case "baseRateArmRight" -> attribute.getBaseRateArmRight();
            case "baseRateShoes" -> attribute.getBaseRateShoes();
            case "damageReflectionFixedValue" -> attribute.getDamageReflectionFixedValue();
            case "damageReflectionReduceFixedValue" -> attribute.getDamageReflectionReduceFixedValue();
            case "angerAddTimeOnTeam" -> attribute.getAngerAddTimeOnTeam();
            case "feiShengLiLianRate" -> attribute.getFeiShengLiLianRate();
            case "godAttack" -> attribute.getGodAttack();
            case "godDefense" -> attribute.getGodDefense();
            case "teamMapMonsterHurtRate" -> attribute.getTeamMapMonsterHurtRate();
            case "expPoint" -> attribute.getExpPoint();
            case "kungFuDefenseToMon" -> attribute.getKungFuDefenseToMon();
            case "duanTiExpRate" -> attribute.getDuanTiExpRate();
            case "godWeaponMapMonsterHurtRate" -> attribute.getGodWeaponMapMonsterHurtRate();
            case "godWeaponMapMonsterFixedValue" -> attribute.getGodWeaponMapMonsterFixedValue();
            case "damageReflectionReduceRate" -> attribute.getDamageReflectionReduceRate();
            case "monsterHpRecoverFixedValue" -> attribute.getMonsterHpRecoverFixedValue();
            case "monsterHpRecoverReduceRate" -> attribute.getMonsterHpRecoverReduceRate();
            case "dodgeChanceReduce" -> attribute.getDodgeChanceReduce();
            default -> 0L;
        };
    }


    public static HashMap<Integer, Long> toMap(Attribute attribute) {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(1, (long) attribute.getStrength());
        map.put(2, (long) attribute.getAgility());
        map.put(3, (long) attribute.getVitality());
        map.put(4, (long) attribute.getEnergy());
        map.put(5, (long) attribute.getLeadership());
        map.put(6, (long) attribute.getExperienceRate());
        map.put(7, (long) attribute.getMoneyAmountRate());
        map.put(8, (long) attribute.getAttackSpeed());
        map.put(9, (long) attribute.getMoveSpeed());
        map.put(10, (long) attribute.getSellGoldUpRatio());
        map.put(11, (long) attribute.getMaximumHealth());
        map.put(13, (long) attribute.getMaximumMana());
        map.put(15, (long) attribute.getMaximumShield());
        map.put(17, (long) attribute.getMaximumAbility());
        map.put(18, (long) attribute.getHealthRecoveryMultiplier());
        map.put(19, (long) attribute.getManaRecoveryMultiplier());
        map.put(20, (long) attribute.getShieldRecoveryMultiplier());
        map.put(21, (long) attribute.getAbilityRecoveryMultiplier());
        map.put(22, (long) attribute.getHealthRecoveryAbsolute());
        map.put(23, (long) attribute.getManaRecoveryAbsolute());
        map.put(24, (long) attribute.getShieldRecoveryAbsolute());
        map.put(25, (long) attribute.getAbilityRecoveryAbsolute());
        map.put(26, (long) attribute.getHealthAfterMonsterKillMultiplier());
        map.put(27, (long) attribute.getManaAfterMonsterKillMultiplier());
        map.put(28, (long) attribute.getShieldAfterMonsterKillMultiplier());
        map.put(29, (long) attribute.getAbilityAfterMonsterKillMultiplier());
        map.put(30, (long) attribute.getHealthAfterMonsterKillAbsolute());
        map.put(31, (long) attribute.getManaAfterMonsterKillAbsolute());
        map.put(32, (long) attribute.getShieldAfterMonsterKillAbsolute());
        map.put(33, (long) attribute.getAbilityAfterMonsterKillAbsolute());
        map.put(40, (long) attribute.getAttackRatePvm());
        map.put(41, (long) attribute.getAttackRatePvp());
        map.put(42, (long) attribute.getDefenseRatePvm());
        map.put(43, (long) attribute.getDefenseRatePvp());
        map.put(44, (long) attribute.getCriticalDamageBonus());
        map.put(45, (long) attribute.getExcellentDamageBonus());
        map.put(46, (long) attribute.getCriticalDamageChance());
        map.put(47, (long) attribute.getExcellentDamageChance());
        map.put(53, (long) attribute.getMinimumPhysBaseDmg());
        map.put(54, (long) attribute.getMaximumPhysBaseDmg());
        map.put(55, (long) attribute.getMinimumWizBaseDmg());
        map.put(56, (long) attribute.getMaximumWizBaseDmg());
        map.put(57, (long) attribute.getMinimumCurseBaseDmg());
        map.put(58, (long) attribute.getMaximumCurseBaseDmg());
        map.put(70, (long) attribute.getDefenseIgnoreChance());
        map.put(71, (long) attribute.getDefenseBase());
        map.put(72, (long) attribute.getDefensePvm());
        map.put(73, (long) attribute.getDefensePvp());
        map.put(74, (long) attribute.getDefenseIncreaseWithEquippedShield());
        map.put(80, (long) attribute.getDamageReceiveDecrement());
        map.put(81, (long) attribute.getAttackDamageIncrease());
        map.put(82, (long) attribute.getSkillDamageBonus());
        map.put(83, (long) attribute.getSkillMultiplier());
        map.put(84, (long) (attribute.isTwoHandedWeaponEquipped() ? 1 : 0));
        map.put(85, (long) attribute.getTwoHandedWeaponDamageIncrease());
        map.put(86, (long) attribute.getFinalDamageIncreasePvp());
        map.put(87, (long) attribute.getDoubleDamageChance());
        map.put(90, (long) attribute.getShieldBypassChance());
        map.put(91, (long) attribute.getShieldDecreaseRateIncrease());
        map.put(92, (long) attribute.getShieldRateIncrease());
        map.put(93, (long) attribute.getDamageReflection());
        map.put(100, (long) attribute.getIceResistance());
        map.put(101, (long) attribute.getFireResistance());
        map.put(102, (long) attribute.getWaterResistance());
        map.put(103, (long) attribute.getEarthResistance());
        map.put(104, (long) attribute.getWindResistance());
        map.put(105, (long) attribute.getPoisonResistance());
        map.put(106, (long) attribute.getLightningResistance());
        map.put(110, (long) attribute.getShieldRecoveryEverywhere());
        map.put(111, (long) attribute.getAbilityUsageReduction());
        map.put(112, (long) attribute.getManaUsageReduction());
        map.put(113, (long) attribute.getItemDurationIncrease());
        map.put(114, (long) attribute.getPetDurationIncrease());
        map.put(115, (long) attribute.getFullyRecoverManaAfterHitChance());
        map.put(116, (long) attribute.getFullyRecoverHealthAfterHitChance());
        map.put(117, (long) attribute.getRequiredStrengthReduction());
        map.put(118, (long) attribute.getRequiredAgilityReduction());
        map.put(119, (long) attribute.getRequiredVitalityReduction());
        map.put(120, (long) attribute.getRequiredEnergyReduction());
        map.put(121, (long) attribute.getRequiredLeadershipReduction());
        map.put(122, (long) attribute.getFight());
        map.put(123, (long) attribute.getPetAttackDamageIncrease());
        map.put(124, (long) attribute.getDefenseIgnoreChanceResistance());
        map.put(125, (long) attribute.getShieldBypassChanceResistance());
        map.put(126, (long) attribute.getDoubleDamageChanceResistance());
        map.put(127, (long) attribute.getExcellentDamageChanceResistance());
        map.put(128, (long) attribute.getCriticalDamageBonusResistance());
        map.put(129, (long) attribute.getAttackDistanceIncrease());
        map.put(130, (long) attribute.getPhysBaseDmgByLevel());
        map.put(131, (long) attribute.getWizBaseDmgByLevel());
        map.put(140, (long) attribute.getReducedFixedHealthPerSuccessfulAttack());
        map.put(141, (long) attribute.getStaticMoveSpeed());
        map.put(142, (long) attribute.getExtraDamage());
        map.put(143, (long) attribute.getReceivedDamageReduceRate());
        map.put(144, (long) attribute.getReceivedDamageReduceRateFix());
        map.put(145, (long) attribute.getHurtReduceMPRate());
        map.put(146, (long) attribute.getHurtReduceMPFix());
        map.put(147, (long) attribute.getDamageIncreaseRate());
        map.put(148, (long) attribute.getDamageIncreaseFix());
        map.put(149, (long) attribute.getOneHandedWeaponIncRate());
        map.put(150, (long) attribute.getBagLatticeNumberUp());
        map.put(151, (long) attribute.getSellBlueDiamondsUpRatio());
        map.put(152, (long) attribute.getAuctionSaleMaxUp());
        map.put(153, (long) attribute.getAutoPickOpen());
        map.put(154, (long) attribute.getMonsterDropRate());
        map.put(155, (long) attribute.getDamageBonus());
        map.put(156, (long) attribute.getDamageAbsorption());
        map.put(157, (long) attribute.getExcellenceDamageDecrement());
        map.put(158, (long) attribute.getEntryRating());
        map.put(159, (long) attribute.getAutoBugDrugs());
        map.put(160, (long) attribute.getExpDrugsUp());
        map.put(161, (long) (attribute.isPushedImmunity() ? 1 : 0));
        map.put(162, (long) attribute.getBasicDefenseRate());
        map.put(163, (long) attribute.getExtraIntensifyAttributeIncrease());
        map.put(164, (long) attribute.getExtraAdditionalAttributeIncrease());
        map.put(165, (long) attribute.getBaseDefenseByLevel());
        map.put(166, (long) attribute.getMoveSpeedResistance());
        map.put(167, (long) attribute.getLeaveHealthRecoveryMultiplier());
        map.put(168, (long) attribute.getMonsterDamageAbsorption());
        map.put(169, (long) attribute.getPositionDamageAbsorption());
        map.put(170, (long) attribute.getHangUpProtection());
        map.put(171, (long) attribute.getComboRecovery());
        map.put(172, (long) attribute.getAttackSpeedCalculateValue());
        map.put(177, (long) attribute.getDamageReceiveDecrementBuff());
        map.put(178, (long) attribute.getStoneDropRate());
        map.put(179, (long) attribute.getExEquipDropRate());
        map.put(180, (long) attribute.getDoubleDropRate());
        map.put(181, (long) attribute.getHeal5s());
        map.put(182, (long) attribute.getBaseHealthByLevel());
        map.put(183, (long) attribute.getPotionRate());
        map.put(184, (long) attribute.getComboDamage());
        map.put(185, (long) attribute.getComboAdditionalRatio());
        map.put(186, (long) attribute.getToWarriorHarmIncrease());
        map.put(187, (long) attribute.getToMagicianHarmIncrease());
        map.put(188, (long) attribute.getToArcherHarmIncrease());
        map.put(189, (long) attribute.getFromWarriorHarmDecrease());
        map.put(190, (long) attribute.getFromMagicianHarmDecrease());
        map.put(191, (long) attribute.getFromArcherHarmDecrease());
        map.put(192, (long) attribute.getToPlayerHarmIncrease());
        map.put(193, (long) attribute.getFromPlayerHarmDecrease());
        map.put(194, (long) attribute.getDamageReflectionDecrease());
        map.put(195, (long) attribute.getRuneHoleLevelUp());
        map.put(196, (long) attribute.getToBossHarmIncrease());
        map.put(197, (long) attribute.getToGoldHarmIncrease());
        map.put(198, (long) attribute.getToMinionsHarmIncrease());
        map.put(199, (long) attribute.getCdMinus());
        map.put(200, (long) attribute.getModerateHarmIncrease());
        map.put(201, (long) attribute.getExtraPartiAttribute());
        map.put(202, (long) attribute.getMaximumFlare());
        map.put(203, (long) attribute.getHealblue5s());
        map.put(204, (long) attribute.getNoIgnoreDefenseBase());
        map.put(205, (long) attribute.getToSpellswordHarmIncrease());
        map.put(206, (long) attribute.getFromSpellswordHarmDecrease());
        map.put(207, (long) attribute.getTrebleDropRate());
        map.put(208, (long) attribute.getFivefoldDropRate());
        map.put(209, (long) attribute.getTenfoldDropRate());
        map.put(210, (long) attribute.getXin());
        map.put(211, (long) attribute.getLi());
        map.put(212, (long) attribute.getTi());
        map.put(213, (long) attribute.getShen());
        map.put(214, (long) attribute.getResistDefense());
        map.put(215, (long) attribute.getHolyChance());
        map.put(216, (long) attribute.getHolyDamage());
        map.put(217, (long) attribute.getKungFuDefense());
        map.put(218, (long) attribute.getKungFuPvm());
        map.put(219, (long) attribute.getKungFuRate1());
        map.put(220, (long) attribute.getKungFuRate2());
        map.put(221, (long) attribute.getDoubleHitRate());
        map.put(222, (long) attribute.getAngerTime());
        map.put(223, (long) attribute.getMpReduceRate());
        map.put(224, (long) attribute.getCureRate());
        map.put(225, (long) attribute.getCastSkillDistance());
        map.put(226, (long) attribute.getCharm());
        map.put(227, (long) attribute.getAngerRate());
        map.put(228, (long) attribute.getPvpAddRatio());
        map.put(229, (long) attribute.getPvpReduceRatio());
        map.put(230, (long) attribute.getAngerFixed());
        map.put(231, (long) attribute.getDoubleHitRate2());
        map.put(232, (long) attribute.getLiLianRate());
        map.put(233, (long) attribute.getSuckRate());
        map.put(234, (long) attribute.getQiGongLevelUp());
        map.put(235, (long) attribute.getDeadlyProbability());
        map.put(236, (long) attribute.getDeadlyRate());
        map.put(237, (long) attribute.getDeadlyFixedValue());
        map.put(238, (long) attribute.getKungFuDodge());
        map.put(239, (long) attribute.getResistDeadlyRate());
        map.put(240, (long) attribute.getLuckRate());
        map.put(241, (long) attribute.getResistLuckRate());
        map.put(242, (long) attribute.getResistDeadlyProbability());
        map.put(243, (long) attribute.getResistLuckProbability());
        map.put(244, (long) attribute.getResistKungFuDefense());
        map.put(245, (long) attribute.getDemon());
        map.put(246, (long) attribute.getMilitaryLevel());
        map.put(247, (long) attribute.getMilitaryHurt());
        map.put(248, (long) attribute.getMilitaryHurtRate());
        map.put(249, (long) attribute.getSuckFixedValue());
        map.put(250, (long) attribute.getPveAddHurtRate());
        map.put(251, (long) attribute.getPveMinusHurtRate());
        map.put(252, (long) attribute.getPvpAddFixed());
        map.put(253, (long) attribute.getPvpReduceFixed());

        map.put(254, (long) attribute.getCareer1HurtRate());
        map.put(255, (long) attribute.getCareer4HurtRate());
        map.put(256, (long) attribute.getCareer2HurtRate());
        map.put(257, (long) attribute.getCareer3HurtRate());
        map.put(258, (long) attribute.getCareer5HurtRate());

        map.put(259, (long) attribute.getCareer1HurtReduceRate());
        map.put(260, (long) attribute.getCareer4HurtReduceRate());
        map.put(261, (long) attribute.getCareer2HurtReduceRate());
        map.put(262, (long) attribute.getCareer3HurtReduceRate());
        map.put(263, (long) attribute.getCareer5HurtReduceRate());

        map.put(264, (long) attribute.getBossFixedValue());
        map.put(265, (long) attribute.getSuckRateResist());
        map.put(266, (long) attribute.getPetHurtRate());
        map.put(267, (long) attribute.getPetHurtReduceRate());
        map.put(268, (long) attribute.getPvpSuckRate());

        map.put(269, (long) attribute.getKungFuHit());
        map.put(270, (long) attribute.getKungFuSkillAddFixedValue());
        map.put(271, (long) attribute.getBaseRateWeapon());
        map.put(272, (long) attribute.getBaseRateCloth());
        map.put(273, (long) attribute.getBaseRateInsideCloth());
        map.put(274, (long) attribute.getBaseRateArmLeft());
        map.put(275, (long) attribute.getBaseRateArmRight());
        map.put(276, (long) attribute.getDodgeChance());
        map.put(277, (long) attribute.getBaseRateShoes());
        map.put(278, (long) attribute.getDamageReflectionFixedValue());
        map.put(279, (long) attribute.getDamageReflectionReduceFixedValue());
        map.put(280, (long) attribute.getAngerAddTimeOnTeam());
        map.put(281, (long) attribute.getFeiShengLiLianRate());
        map.put(282, (long) attribute.getGodAttack());
        map.put(283, (long) attribute.getGodDefense());
        map.put(284, (long) attribute.getTeamMapMonsterHurtRate());
        map.put(285, (long) attribute.getExpPoint());
        map.put(286, (long) attribute.getKungFuDefenseToMon());
        map.put(287, (long) attribute.getDuanTiExpRate());

        map.put(288, (long) attribute.getGodWeaponMapMonsterHurtRate());
        map.put(289, (long) attribute.getGodWeaponMapMonsterFixedValue());
        map.put(290, (long) attribute.getDamageReflectionReduceRate());
        map.put(291, (long) attribute.getMonsterHpRecoverFixedValue());
        map.put(292, (long) attribute.getMonsterHpRecoverReduceRate());
        map.put(293, (long) attribute.getDodgeChanceReduce());
        return map;
    }

    public static HashMap<Integer, Long> toFightMap(Attribute attribute) {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(122, (long) attribute.getFight());
        return map;
    }

    public static HashMap<Integer, Long> toFightMap(Map<String, Long> attribute) {
        HashMap<Integer, Long> map = new HashMap<>();
        attribute.forEach((k, v) -> map.put(NameTransformId.getOrDefault(k, 0), v));
        return map;
    }


    public static int getAttributeId(String attributeName) {
        return NameTransformId.getOrDefault(attributeName, 0);
    }

    /**
     * 队伍中的属性信息
     *
     * @param attribute 属性
     */
    public static HashMap<Integer, Long> toTeam(Attribute attribute) {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(226, (long) attribute.getCharm());
        return map;
    }

    /**
     * 排行榜中中的属性信息
     *
     * @param attribute 属性
     */
    public static HashMap<Integer, Long> toRank(Attribute attribute) {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(226, (long) attribute.getCharm());
        return map;
    }

    /**
     * 场景中的属性
     *
     * @param attribute 属性
     */
    public static HashMap<Integer, Long> toRoundPlayer(Attribute attribute) {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(226, (long) attribute.getCharm());
        return map;
    }
}