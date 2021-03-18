package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.potion.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;
import net.minecraft.init.*;

public class Item
{
    public static final RegistryNamespaced itemRegistry;
    protected static final UUID field_111210_e;
    private CreativeTabs tabToDisplayOn;
    protected static Random itemRand;
    protected int maxStackSize;
    private int maxDamage;
    protected boolean bFull3D;
    protected boolean hasSubtypes;
    private Item containerItem;
    private String potionEffect;
    private String unlocalizedName;
    protected IIcon itemIcon;
    protected String iconString;
    private static final String __OBFID = "CL_00000041";
    
    public Item() {
        this.maxStackSize = 64;
    }
    
    public static int getIdFromItem(final Item p_150891_0_) {
        return (p_150891_0_ == null) ? 0 : Item.itemRegistry.getIDForObject(p_150891_0_);
    }
    
    public static Item getItemById(final int p_150899_0_) {
        return (Item)Item.itemRegistry.getObjectForID(p_150899_0_);
    }
    
    public static Item getItemFromBlock(final Block p_150898_0_) {
        return getItemById(Block.getIdFromBlock(p_150898_0_));
    }
    
    public static void registerItems() {
        Item.itemRegistry.addObject(256, "iron_shovel", new ItemSpade(ToolMaterial.IRON).setUnlocalizedName("shovelIron").setTextureName("iron_shovel"));
        Item.itemRegistry.addObject(257, "iron_pickaxe", new ItemPickaxe(ToolMaterial.IRON).setUnlocalizedName("pickaxeIron").setTextureName("iron_pickaxe"));
        Item.itemRegistry.addObject(258, "iron_axe", new ItemAxe(ToolMaterial.IRON).setUnlocalizedName("hatchetIron").setTextureName("iron_axe"));
        Item.itemRegistry.addObject(259, "flint_and_steel", new ItemFlintAndSteel().setUnlocalizedName("flintAndSteel").setTextureName("flint_and_steel"));
        Item.itemRegistry.addObject(260, "apple", new ItemFood(4, 0.3f, false).setUnlocalizedName("apple").setTextureName("apple"));
        Item.itemRegistry.addObject(261, "bow", new ItemBow().setUnlocalizedName("bow").setTextureName("bow"));
        Item.itemRegistry.addObject(262, "arrow", new Item().setUnlocalizedName("arrow").setCreativeTab(CreativeTabs.tabCombat).setTextureName("arrow"));
        Item.itemRegistry.addObject(263, "coal", new ItemCoal().setUnlocalizedName("coal").setTextureName("coal"));
        Item.itemRegistry.addObject(264, "diamond", new Item().setUnlocalizedName("diamond").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("diamond"));
        Item.itemRegistry.addObject(265, "iron_ingot", new Item().setUnlocalizedName("ingotIron").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("iron_ingot"));
        Item.itemRegistry.addObject(266, "gold_ingot", new Item().setUnlocalizedName("ingotGold").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("gold_ingot"));
        Item.itemRegistry.addObject(267, "iron_sword", new ItemSword(ToolMaterial.IRON).setUnlocalizedName("swordIron").setTextureName("iron_sword"));
        Item.itemRegistry.addObject(268, "wooden_sword", new ItemSword(ToolMaterial.WOOD).setUnlocalizedName("swordWood").setTextureName("wood_sword"));
        Item.itemRegistry.addObject(269, "wooden_shovel", new ItemSpade(ToolMaterial.WOOD).setUnlocalizedName("shovelWood").setTextureName("wood_shovel"));
        Item.itemRegistry.addObject(270, "wooden_pickaxe", new ItemPickaxe(ToolMaterial.WOOD).setUnlocalizedName("pickaxeWood").setTextureName("wood_pickaxe"));
        Item.itemRegistry.addObject(271, "wooden_axe", new ItemAxe(ToolMaterial.WOOD).setUnlocalizedName("hatchetWood").setTextureName("wood_axe"));
        Item.itemRegistry.addObject(272, "stone_sword", new ItemSword(ToolMaterial.STONE).setUnlocalizedName("swordStone").setTextureName("stone_sword"));
        Item.itemRegistry.addObject(273, "stone_shovel", new ItemSpade(ToolMaterial.STONE).setUnlocalizedName("shovelStone").setTextureName("stone_shovel"));
        Item.itemRegistry.addObject(274, "stone_pickaxe", new ItemPickaxe(ToolMaterial.STONE).setUnlocalizedName("pickaxeStone").setTextureName("stone_pickaxe"));
        Item.itemRegistry.addObject(275, "stone_axe", new ItemAxe(ToolMaterial.STONE).setUnlocalizedName("hatchetStone").setTextureName("stone_axe"));
        Item.itemRegistry.addObject(276, "diamond_sword", new ItemSword(ToolMaterial.EMERALD).setUnlocalizedName("swordDiamond").setTextureName("diamond_sword"));
        Item.itemRegistry.addObject(277, "diamond_shovel", new ItemSpade(ToolMaterial.EMERALD).setUnlocalizedName("shovelDiamond").setTextureName("diamond_shovel"));
        Item.itemRegistry.addObject(278, "diamond_pickaxe", new ItemPickaxe(ToolMaterial.EMERALD).setUnlocalizedName("pickaxeDiamond").setTextureName("diamond_pickaxe"));
        Item.itemRegistry.addObject(279, "diamond_axe", new ItemAxe(ToolMaterial.EMERALD).setUnlocalizedName("hatchetDiamond").setTextureName("diamond_axe"));
        Item.itemRegistry.addObject(280, "stick", new Item().setFull3D().setUnlocalizedName("stick").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("stick"));
        Item.itemRegistry.addObject(281, "bowl", new Item().setUnlocalizedName("bowl").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("bowl"));
        Item.itemRegistry.addObject(282, "mushroom_stew", new ItemSoup(6).setUnlocalizedName("mushroomStew").setTextureName("mushroom_stew"));
        Item.itemRegistry.addObject(283, "golden_sword", new ItemSword(ToolMaterial.GOLD).setUnlocalizedName("swordGold").setTextureName("gold_sword"));
        Item.itemRegistry.addObject(284, "golden_shovel", new ItemSpade(ToolMaterial.GOLD).setUnlocalizedName("shovelGold").setTextureName("gold_shovel"));
        Item.itemRegistry.addObject(285, "golden_pickaxe", new ItemPickaxe(ToolMaterial.GOLD).setUnlocalizedName("pickaxeGold").setTextureName("gold_pickaxe"));
        Item.itemRegistry.addObject(286, "golden_axe", new ItemAxe(ToolMaterial.GOLD).setUnlocalizedName("hatchetGold").setTextureName("gold_axe"));
        Item.itemRegistry.addObject(287, "string", new ItemReed(Blocks.tripwire).setUnlocalizedName("string").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("string"));
        Item.itemRegistry.addObject(288, "feather", new Item().setUnlocalizedName("feather").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("feather"));
        Item.itemRegistry.addObject(289, "gunpowder", new Item().setUnlocalizedName("sulphur").setPotionEffect(PotionHelper.gunpowderEffect).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("gunpowder"));
        Item.itemRegistry.addObject(290, "wooden_hoe", new ItemHoe(ToolMaterial.WOOD).setUnlocalizedName("hoeWood").setTextureName("wood_hoe"));
        Item.itemRegistry.addObject(291, "stone_hoe", new ItemHoe(ToolMaterial.STONE).setUnlocalizedName("hoeStone").setTextureName("stone_hoe"));
        Item.itemRegistry.addObject(292, "iron_hoe", new ItemHoe(ToolMaterial.IRON).setUnlocalizedName("hoeIron").setTextureName("iron_hoe"));
        Item.itemRegistry.addObject(293, "diamond_hoe", new ItemHoe(ToolMaterial.EMERALD).setUnlocalizedName("hoeDiamond").setTextureName("diamond_hoe"));
        Item.itemRegistry.addObject(294, "golden_hoe", new ItemHoe(ToolMaterial.GOLD).setUnlocalizedName("hoeGold").setTextureName("gold_hoe"));
        Item.itemRegistry.addObject(295, "wheat_seeds", new ItemSeeds(Blocks.wheat, Blocks.farmland).setUnlocalizedName("seeds").setTextureName("seeds_wheat"));
        Item.itemRegistry.addObject(296, "wheat", new Item().setUnlocalizedName("wheat").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("wheat"));
        Item.itemRegistry.addObject(297, "bread", new ItemFood(5, 0.6f, false).setUnlocalizedName("bread").setTextureName("bread"));
        Item.itemRegistry.addObject(298, "leather_helmet", new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 0).setUnlocalizedName("helmetCloth").setTextureName("leather_helmet"));
        Item.itemRegistry.addObject(299, "leather_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 1).setUnlocalizedName("chestplateCloth").setTextureName("leather_chestplate"));
        Item.itemRegistry.addObject(300, "leather_leggings", new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 2).setUnlocalizedName("leggingsCloth").setTextureName("leather_leggings"));
        Item.itemRegistry.addObject(301, "leather_boots", new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 3).setUnlocalizedName("bootsCloth").setTextureName("leather_boots"));
        Item.itemRegistry.addObject(302, "chainmail_helmet", new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 0).setUnlocalizedName("helmetChain").setTextureName("chainmail_helmet"));
        Item.itemRegistry.addObject(303, "chainmail_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 1).setUnlocalizedName("chestplateChain").setTextureName("chainmail_chestplate"));
        Item.itemRegistry.addObject(304, "chainmail_leggings", new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 2).setUnlocalizedName("leggingsChain").setTextureName("chainmail_leggings"));
        Item.itemRegistry.addObject(305, "chainmail_boots", new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 3).setUnlocalizedName("bootsChain").setTextureName("chainmail_boots"));
        Item.itemRegistry.addObject(306, "iron_helmet", new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 0).setUnlocalizedName("helmetIron").setTextureName("iron_helmet"));
        Item.itemRegistry.addObject(307, "iron_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 1).setUnlocalizedName("chestplateIron").setTextureName("iron_chestplate"));
        Item.itemRegistry.addObject(308, "iron_leggings", new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 2).setUnlocalizedName("leggingsIron").setTextureName("iron_leggings"));
        Item.itemRegistry.addObject(309, "iron_boots", new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 3).setUnlocalizedName("bootsIron").setTextureName("iron_boots"));
        Item.itemRegistry.addObject(310, "diamond_helmet", new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 0).setUnlocalizedName("helmetDiamond").setTextureName("diamond_helmet"));
        Item.itemRegistry.addObject(311, "diamond_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 1).setUnlocalizedName("chestplateDiamond").setTextureName("diamond_chestplate"));
        Item.itemRegistry.addObject(312, "diamond_leggings", new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 2).setUnlocalizedName("leggingsDiamond").setTextureName("diamond_leggings"));
        Item.itemRegistry.addObject(313, "diamond_boots", new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 3).setUnlocalizedName("bootsDiamond").setTextureName("diamond_boots"));
        Item.itemRegistry.addObject(314, "golden_helmet", new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 0).setUnlocalizedName("helmetGold").setTextureName("gold_helmet"));
        Item.itemRegistry.addObject(315, "golden_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 1).setUnlocalizedName("chestplateGold").setTextureName("gold_chestplate"));
        Item.itemRegistry.addObject(316, "golden_leggings", new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 2).setUnlocalizedName("leggingsGold").setTextureName("gold_leggings"));
        Item.itemRegistry.addObject(317, "golden_boots", new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 3).setUnlocalizedName("bootsGold").setTextureName("gold_boots"));
        Item.itemRegistry.addObject(318, "flint", new Item().setUnlocalizedName("flint").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("flint"));
        Item.itemRegistry.addObject(319, "porkchop", new ItemFood(3, 0.3f, true).setUnlocalizedName("porkchopRaw").setTextureName("porkchop_raw"));
        Item.itemRegistry.addObject(320, "cooked_porkchop", new ItemFood(8, 0.8f, true).setUnlocalizedName("porkchopCooked").setTextureName("porkchop_cooked"));
        Item.itemRegistry.addObject(321, "painting", new ItemHangingEntity(EntityPainting.class).setUnlocalizedName("painting").setTextureName("painting"));
        Item.itemRegistry.addObject(322, "golden_apple", new ItemAppleGold(4, 1.2f, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0f).setUnlocalizedName("appleGold").setTextureName("apple_golden"));
        Item.itemRegistry.addObject(323, "sign", new ItemSign().setUnlocalizedName("sign").setTextureName("sign"));
        Item.itemRegistry.addObject(324, "wooden_door", new ItemDoor(Material.wood).setUnlocalizedName("doorWood").setTextureName("door_wood"));
        final Item var0 = new ItemBucket(Blocks.air).setUnlocalizedName("bucket").setMaxStackSize(16).setTextureName("bucket_empty");
        Item.itemRegistry.addObject(325, "bucket", var0);
        Item.itemRegistry.addObject(326, "water_bucket", new ItemBucket(Blocks.flowing_water).setUnlocalizedName("bucketWater").setContainerItem(var0).setTextureName("bucket_water"));
        Item.itemRegistry.addObject(327, "lava_bucket", new ItemBucket(Blocks.flowing_lava).setUnlocalizedName("bucketLava").setContainerItem(var0).setTextureName("bucket_lava"));
        Item.itemRegistry.addObject(328, "minecart", new ItemMinecart(0).setUnlocalizedName("minecart").setTextureName("minecart_normal"));
        Item.itemRegistry.addObject(329, "saddle", new ItemSaddle().setUnlocalizedName("saddle").setTextureName("saddle"));
        Item.itemRegistry.addObject(330, "iron_door", new ItemDoor(Material.iron).setUnlocalizedName("doorIron").setTextureName("door_iron"));
        Item.itemRegistry.addObject(331, "redstone", new ItemRedstone().setUnlocalizedName("redstone").setPotionEffect(PotionHelper.redstoneEffect).setTextureName("redstone_dust"));
        Item.itemRegistry.addObject(332, "snowball", new ItemSnowball().setUnlocalizedName("snowball").setTextureName("snowball"));
        Item.itemRegistry.addObject(333, "boat", new ItemBoat().setUnlocalizedName("boat").setTextureName("boat"));
        Item.itemRegistry.addObject(334, "leather", new Item().setUnlocalizedName("leather").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("leather"));
        Item.itemRegistry.addObject(335, "milk_bucket", new ItemBucketMilk().setUnlocalizedName("milk").setContainerItem(var0).setTextureName("bucket_milk"));
        Item.itemRegistry.addObject(336, "brick", new Item().setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("brick"));
        Item.itemRegistry.addObject(337, "clay_ball", new Item().setUnlocalizedName("clay").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("clay_ball"));
        Item.itemRegistry.addObject(338, "reeds", new ItemReed(Blocks.reeds).setUnlocalizedName("reeds").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("reeds"));
        Item.itemRegistry.addObject(339, "paper", new Item().setUnlocalizedName("paper").setCreativeTab(CreativeTabs.tabMisc).setTextureName("paper"));
        Item.itemRegistry.addObject(340, "book", new ItemBook().setUnlocalizedName("book").setCreativeTab(CreativeTabs.tabMisc).setTextureName("book_normal"));
        Item.itemRegistry.addObject(341, "slime_ball", new Item().setUnlocalizedName("slimeball").setCreativeTab(CreativeTabs.tabMisc).setTextureName("slimeball"));
        Item.itemRegistry.addObject(342, "chest_minecart", new ItemMinecart(1).setUnlocalizedName("minecartChest").setTextureName("minecart_chest"));
        Item.itemRegistry.addObject(343, "furnace_minecart", new ItemMinecart(2).setUnlocalizedName("minecartFurnace").setTextureName("minecart_furnace"));
        Item.itemRegistry.addObject(344, "egg", new ItemEgg().setUnlocalizedName("egg").setTextureName("egg"));
        Item.itemRegistry.addObject(345, "compass", new Item().setUnlocalizedName("compass").setCreativeTab(CreativeTabs.tabTools).setTextureName("compass"));
        Item.itemRegistry.addObject(346, "fishing_rod", new ItemFishingRod().setUnlocalizedName("fishingRod").setTextureName("fishing_rod"));
        Item.itemRegistry.addObject(347, "clock", new Item().setUnlocalizedName("clock").setCreativeTab(CreativeTabs.tabTools).setTextureName("clock"));
        Item.itemRegistry.addObject(348, "glowstone_dust", new Item().setUnlocalizedName("yellowDust").setPotionEffect(PotionHelper.glowstoneEffect).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("glowstone_dust"));
        Item.itemRegistry.addObject(349, "fish", new ItemFishFood(false).setUnlocalizedName("fish").setTextureName("fish_raw").setHasSubtypes(true));
        Item.itemRegistry.addObject(350, "cooked_fished", new ItemFishFood(true).setUnlocalizedName("fish").setTextureName("fish_cooked").setHasSubtypes(true));
        Item.itemRegistry.addObject(351, "dye", new ItemDye().setUnlocalizedName("dyePowder").setTextureName("dye_powder"));
        Item.itemRegistry.addObject(352, "bone", new Item().setUnlocalizedName("bone").setFull3D().setCreativeTab(CreativeTabs.tabMisc).setTextureName("bone"));
        Item.itemRegistry.addObject(353, "sugar", new Item().setUnlocalizedName("sugar").setPotionEffect(PotionHelper.sugarEffect).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("sugar"));
        Item.itemRegistry.addObject(354, "cake", new ItemReed(Blocks.cake).setMaxStackSize(1).setUnlocalizedName("cake").setCreativeTab(CreativeTabs.tabFood).setTextureName("cake"));
        Item.itemRegistry.addObject(355, "bed", new ItemBed().setMaxStackSize(1).setUnlocalizedName("bed").setTextureName("bed"));
        Item.itemRegistry.addObject(356, "repeater", new ItemReed(Blocks.unpowered_repeater).setUnlocalizedName("diode").setCreativeTab(CreativeTabs.tabRedstone).setTextureName("repeater"));
        Item.itemRegistry.addObject(357, "cookie", new ItemFood(2, 0.1f, false).setUnlocalizedName("cookie").setTextureName("cookie"));
        Item.itemRegistry.addObject(358, "filled_map", new ItemMap().setUnlocalizedName("map").setTextureName("map_filled"));
        Item.itemRegistry.addObject(359, "shears", new ItemShears().setUnlocalizedName("shears").setTextureName("shears"));
        Item.itemRegistry.addObject(360, "melon", new ItemFood(2, 0.3f, false).setUnlocalizedName("melon").setTextureName("melon"));
        Item.itemRegistry.addObject(361, "pumpkin_seeds", new ItemSeeds(Blocks.pumpkin_stem, Blocks.farmland).setUnlocalizedName("seeds_pumpkin").setTextureName("seeds_pumpkin"));
        Item.itemRegistry.addObject(362, "melon_seeds", new ItemSeeds(Blocks.melon_stem, Blocks.farmland).setUnlocalizedName("seeds_melon").setTextureName("seeds_melon"));
        Item.itemRegistry.addObject(363, "beef", new ItemFood(3, 0.3f, true).setUnlocalizedName("beefRaw").setTextureName("beef_raw"));
        Item.itemRegistry.addObject(364, "cooked_beef", new ItemFood(8, 0.8f, true).setUnlocalizedName("beefCooked").setTextureName("beef_cooked"));
        Item.itemRegistry.addObject(365, "chicken", new ItemFood(2, 0.3f, true).setPotionEffect(Potion.hunger.id, 30, 0, 0.3f).setUnlocalizedName("chickenRaw").setTextureName("chicken_raw"));
        Item.itemRegistry.addObject(366, "cooked_chicken", new ItemFood(6, 0.6f, true).setUnlocalizedName("chickenCooked").setTextureName("chicken_cooked"));
        Item.itemRegistry.addObject(367, "rotten_flesh", new ItemFood(4, 0.1f, true).setPotionEffect(Potion.hunger.id, 30, 0, 0.8f).setUnlocalizedName("rottenFlesh").setTextureName("rotten_flesh"));
        Item.itemRegistry.addObject(368, "ender_pearl", new ItemEnderPearl().setUnlocalizedName("enderPearl").setTextureName("ender_pearl"));
        Item.itemRegistry.addObject(369, "blaze_rod", new Item().setUnlocalizedName("blazeRod").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("blaze_rod"));
        Item.itemRegistry.addObject(370, "ghast_tear", new Item().setUnlocalizedName("ghastTear").setPotionEffect("+0-1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing).setTextureName("ghast_tear"));
        Item.itemRegistry.addObject(371, "gold_nugget", new Item().setUnlocalizedName("goldNugget").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("gold_nugget"));
        Item.itemRegistry.addObject(372, "nether_wart", new ItemSeeds(Blocks.nether_wart, Blocks.soul_sand).setUnlocalizedName("netherStalkSeeds").setPotionEffect("+4").setTextureName("nether_wart"));
        Item.itemRegistry.addObject(373, "potion", new ItemPotion().setUnlocalizedName("potion").setTextureName("potion"));
        Item.itemRegistry.addObject(374, "glass_bottle", new ItemGlassBottle().setUnlocalizedName("glassBottle").setTextureName("potion_bottle_empty"));
        Item.itemRegistry.addObject(375, "spider_eye", new ItemFood(2, 0.8f, false).setPotionEffect(Potion.poison.id, 5, 0, 1.0f).setUnlocalizedName("spiderEye").setPotionEffect(PotionHelper.spiderEyeEffect).setTextureName("spider_eye"));
        Item.itemRegistry.addObject(376, "fermented_spider_eye", new Item().setUnlocalizedName("fermentedSpiderEye").setPotionEffect(PotionHelper.fermentedSpiderEyeEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("spider_eye_fermented"));
        Item.itemRegistry.addObject(377, "blaze_powder", new Item().setUnlocalizedName("blazePowder").setPotionEffect(PotionHelper.blazePowderEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("blaze_powder"));
        Item.itemRegistry.addObject(378, "magma_cream", new Item().setUnlocalizedName("magmaCream").setPotionEffect(PotionHelper.magmaCreamEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("magma_cream"));
        Item.itemRegistry.addObject(379, "brewing_stand", new ItemReed(Blocks.brewing_stand).setUnlocalizedName("brewingStand").setCreativeTab(CreativeTabs.tabBrewing).setTextureName("brewing_stand"));
        Item.itemRegistry.addObject(380, "cauldron", new ItemReed(Blocks.cauldron).setUnlocalizedName("cauldron").setCreativeTab(CreativeTabs.tabBrewing).setTextureName("cauldron"));
        Item.itemRegistry.addObject(381, "ender_eye", new ItemEnderEye().setUnlocalizedName("eyeOfEnder").setTextureName("ender_eye"));
        Item.itemRegistry.addObject(382, "speckled_melon", new Item().setUnlocalizedName("speckledMelon").setPotionEffect(PotionHelper.speckledMelonEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("melon_speckled"));
        Item.itemRegistry.addObject(383, "spawn_egg", new ItemMonsterPlacer().setUnlocalizedName("monsterPlacer").setTextureName("spawn_egg"));
        Item.itemRegistry.addObject(384, "experience_bottle", new ItemExpBottle().setUnlocalizedName("expBottle").setTextureName("experience_bottle"));
        Item.itemRegistry.addObject(385, "fire_charge", new ItemFireball().setUnlocalizedName("fireball").setTextureName("fireball"));
        Item.itemRegistry.addObject(386, "writable_book", new ItemWritableBook().setUnlocalizedName("writingBook").setCreativeTab(CreativeTabs.tabMisc).setTextureName("book_writable"));
        Item.itemRegistry.addObject(387, "written_book", new ItemEditableBook().setUnlocalizedName("writtenBook").setTextureName("book_written").setMaxStackSize(16));
        Item.itemRegistry.addObject(388, "emerald", new Item().setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("emerald"));
        Item.itemRegistry.addObject(389, "item_frame", new ItemHangingEntity(EntityItemFrame.class).setUnlocalizedName("frame").setTextureName("item_frame"));
        Item.itemRegistry.addObject(390, "flower_pot", new ItemReed(Blocks.flower_pot).setUnlocalizedName("flowerPot").setCreativeTab(CreativeTabs.tabDecorations).setTextureName("flower_pot"));
        Item.itemRegistry.addObject(391, "carrot", new ItemSeedFood(4, 0.6f, Blocks.carrots, Blocks.farmland).setUnlocalizedName("carrots").setTextureName("carrot"));
        Item.itemRegistry.addObject(392, "potato", new ItemSeedFood(1, 0.3f, Blocks.potatoes, Blocks.farmland).setUnlocalizedName("potato").setTextureName("potato"));
        Item.itemRegistry.addObject(393, "baked_potato", new ItemFood(6, 0.6f, false).setUnlocalizedName("potatoBaked").setTextureName("potato_baked"));
        Item.itemRegistry.addObject(394, "poisonous_potato", new ItemFood(2, 0.3f, false).setPotionEffect(Potion.poison.id, 5, 0, 0.6f).setUnlocalizedName("potatoPoisonous").setTextureName("potato_poisonous"));
        Item.itemRegistry.addObject(395, "map", new ItemEmptyMap().setUnlocalizedName("emptyMap").setTextureName("map_empty"));
        Item.itemRegistry.addObject(396, "golden_carrot", new ItemFood(6, 1.2f, false).setUnlocalizedName("carrotGolden").setPotionEffect(PotionHelper.goldenCarrotEffect).setTextureName("carrot_golden"));
        Item.itemRegistry.addObject(397, "skull", new ItemSkull().setUnlocalizedName("skull").setTextureName("skull"));
        Item.itemRegistry.addObject(398, "carrot_on_a_stick", new ItemCarrotOnAStick().setUnlocalizedName("carrotOnAStick").setTextureName("carrot_on_a_stick"));
        Item.itemRegistry.addObject(399, "nether_star", new ItemSimpleFoiled().setUnlocalizedName("netherStar").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("nether_star"));
        Item.itemRegistry.addObject(400, "pumpkin_pie", new ItemFood(8, 0.3f, false).setUnlocalizedName("pumpkinPie").setCreativeTab(CreativeTabs.tabFood).setTextureName("pumpkin_pie"));
        Item.itemRegistry.addObject(401, "fireworks", new ItemFirework().setUnlocalizedName("fireworks").setTextureName("fireworks"));
        Item.itemRegistry.addObject(402, "firework_charge", new ItemFireworkCharge().setUnlocalizedName("fireworksCharge").setCreativeTab(CreativeTabs.tabMisc).setTextureName("fireworks_charge"));
        Item.itemRegistry.addObject(403, "enchanted_book", new ItemEnchantedBook().setMaxStackSize(1).setUnlocalizedName("enchantedBook").setTextureName("book_enchanted"));
        Item.itemRegistry.addObject(404, "comparator", new ItemReed(Blocks.unpowered_comparator).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.tabRedstone).setTextureName("comparator"));
        Item.itemRegistry.addObject(405, "netherbrick", new Item().setUnlocalizedName("netherbrick").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("netherbrick"));
        Item.itemRegistry.addObject(406, "quartz", new Item().setUnlocalizedName("netherquartz").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("quartz"));
        Item.itemRegistry.addObject(407, "tnt_minecart", new ItemMinecart(3).setUnlocalizedName("minecartTnt").setTextureName("minecart_tnt"));
        Item.itemRegistry.addObject(408, "hopper_minecart", new ItemMinecart(5).setUnlocalizedName("minecartHopper").setTextureName("minecart_hopper"));
        Item.itemRegistry.addObject(417, "iron_horse_armor", new Item().setUnlocalizedName("horsearmormetal").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName("iron_horse_armor"));
        Item.itemRegistry.addObject(418, "golden_horse_armor", new Item().setUnlocalizedName("horsearmorgold").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName("gold_horse_armor"));
        Item.itemRegistry.addObject(419, "diamond_horse_armor", new Item().setUnlocalizedName("horsearmordiamond").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName("diamond_horse_armor"));
        Item.itemRegistry.addObject(420, "lead", new ItemLead().setUnlocalizedName("leash").setTextureName("lead"));
        Item.itemRegistry.addObject(421, "name_tag", new ItemNameTag().setUnlocalizedName("nameTag").setTextureName("name_tag"));
        Item.itemRegistry.addObject(422, "command_block_minecart", new ItemMinecart(6).setUnlocalizedName("minecartCommandBlock").setTextureName("minecart_command_block").setCreativeTab(null));
        Item.itemRegistry.addObject(2256, "record_13", new ItemRecord("13").setUnlocalizedName("record").setTextureName("record_13"));
        Item.itemRegistry.addObject(2257, "record_cat", new ItemRecord("cat").setUnlocalizedName("record").setTextureName("record_cat"));
        Item.itemRegistry.addObject(2258, "record_blocks", new ItemRecord("blocks").setUnlocalizedName("record").setTextureName("record_blocks"));
        Item.itemRegistry.addObject(2259, "record_chirp", new ItemRecord("chirp").setUnlocalizedName("record").setTextureName("record_chirp"));
        Item.itemRegistry.addObject(2260, "record_far", new ItemRecord("far").setUnlocalizedName("record").setTextureName("record_far"));
        Item.itemRegistry.addObject(2261, "record_mall", new ItemRecord("mall").setUnlocalizedName("record").setTextureName("record_mall"));
        Item.itemRegistry.addObject(2262, "record_mellohi", new ItemRecord("mellohi").setUnlocalizedName("record").setTextureName("record_mellohi"));
        Item.itemRegistry.addObject(2263, "record_stal", new ItemRecord("stal").setUnlocalizedName("record").setTextureName("record_stal"));
        Item.itemRegistry.addObject(2264, "record_strad", new ItemRecord("strad").setUnlocalizedName("record").setTextureName("record_strad"));
        Item.itemRegistry.addObject(2265, "record_ward", new ItemRecord("ward").setUnlocalizedName("record").setTextureName("record_ward"));
        Item.itemRegistry.addObject(2266, "record_11", new ItemRecord("11").setUnlocalizedName("record").setTextureName("record_11"));
        Item.itemRegistry.addObject(2267, "record_wait", new ItemRecord("wait").setUnlocalizedName("record").setTextureName("record_wait"));
        final HashSet var2 = Sets.newHashSet((Object[])new Block[] { Blocks.air, Blocks.brewing_stand, Blocks.bed, Blocks.nether_wart, Blocks.cauldron, Blocks.flower_pot, Blocks.wheat, Blocks.reeds, Blocks.cake, Blocks.skull, Blocks.piston_head, Blocks.piston_extension, Blocks.lit_redstone_ore, Blocks.powered_repeater, Blocks.pumpkin_stem, Blocks.standing_sign, Blocks.powered_comparator, Blocks.tripwire, Blocks.lit_redstone_lamp, Blocks.melon_stem, Blocks.unlit_redstone_torch, Blocks.unpowered_comparator, Blocks.redstone_wire, Blocks.wall_sign, Blocks.unpowered_repeater, Blocks.iron_door, Blocks.wooden_door });
        for (final String var4 : Block.blockRegistry.getKeys()) {
            final Block var5 = (Block)Block.blockRegistry.getObject(var4);
            Object var6;
            if (var5 == Blocks.wool) {
                var6 = new ItemCloth(Blocks.wool).setUnlocalizedName("cloth");
            }
            else if (var5 == Blocks.stained_hardened_clay) {
                var6 = new ItemCloth(Blocks.stained_hardened_clay).setUnlocalizedName("clayHardenedStained");
            }
            else if (var5 == Blocks.stained_glass) {
                var6 = new ItemCloth(Blocks.stained_glass).setUnlocalizedName("stainedGlass");
            }
            else if (var5 == Blocks.stained_glass_pane) {
                var6 = new ItemCloth(Blocks.stained_glass_pane).setUnlocalizedName("stainedGlassPane");
            }
            else if (var5 == Blocks.carpet) {
                var6 = new ItemCloth(Blocks.carpet).setUnlocalizedName("woolCarpet");
            }
            else if (var5 == Blocks.dirt) {
                var6 = new ItemMultiTexture(Blocks.dirt, Blocks.dirt, BlockDirt.field_150009_a).setUnlocalizedName("dirt");
            }
            else if (var5 == Blocks.sand) {
                var6 = new ItemMultiTexture(Blocks.sand, Blocks.sand, BlockSand.field_149838_a).setUnlocalizedName("sand");
            }
            else if (var5 == Blocks.log) {
                var6 = new ItemMultiTexture(Blocks.log, Blocks.log, BlockOldLog.field_150168_M).setUnlocalizedName("log");
            }
            else if (var5 == Blocks.log2) {
                var6 = new ItemMultiTexture(Blocks.log2, Blocks.log2, BlockNewLog.field_150169_M).setUnlocalizedName("log");
            }
            else if (var5 == Blocks.planks) {
                var6 = new ItemMultiTexture(Blocks.planks, Blocks.planks, BlockWood.field_150096_a).setUnlocalizedName("wood");
            }
            else if (var5 == Blocks.monster_egg) {
                var6 = new ItemMultiTexture(Blocks.monster_egg, Blocks.monster_egg, BlockSilverfish.field_150198_a).setUnlocalizedName("monsterStoneEgg");
            }
            else if (var5 == Blocks.stonebrick) {
                var6 = new ItemMultiTexture(Blocks.stonebrick, Blocks.stonebrick, BlockStoneBrick.field_150142_a).setUnlocalizedName("stonebricksmooth");
            }
            else if (var5 == Blocks.sandstone) {
                var6 = new ItemMultiTexture(Blocks.sandstone, Blocks.sandstone, BlockSandStone.field_150157_a).setUnlocalizedName("sandStone");
            }
            else if (var5 == Blocks.quartz_block) {
                var6 = new ItemMultiTexture(Blocks.quartz_block, Blocks.quartz_block, BlockQuartz.field_150191_a).setUnlocalizedName("quartzBlock");
            }
            else if (var5 == Blocks.stone_slab) {
                var6 = new ItemSlab(Blocks.stone_slab, Blocks.stone_slab, Blocks.double_stone_slab, false).setUnlocalizedName("stoneSlab");
            }
            else if (var5 == Blocks.double_stone_slab) {
                var6 = new ItemSlab(Blocks.double_stone_slab, Blocks.stone_slab, Blocks.double_stone_slab, true).setUnlocalizedName("stoneSlab");
            }
            else if (var5 == Blocks.wooden_slab) {
                var6 = new ItemSlab(Blocks.wooden_slab, Blocks.wooden_slab, Blocks.double_wooden_slab, false).setUnlocalizedName("woodSlab");
            }
            else if (var5 == Blocks.double_wooden_slab) {
                var6 = new ItemSlab(Blocks.double_wooden_slab, Blocks.wooden_slab, Blocks.double_wooden_slab, true).setUnlocalizedName("woodSlab");
            }
            else if (var5 == Blocks.sapling) {
                var6 = new ItemMultiTexture(Blocks.sapling, Blocks.sapling, BlockSapling.field_149882_a).setUnlocalizedName("sapling");
            }
            else if (var5 == Blocks.leaves) {
                var6 = new ItemLeaves(Blocks.leaves).setUnlocalizedName("leaves");
            }
            else if (var5 == Blocks.leaves2) {
                var6 = new ItemLeaves(Blocks.leaves2).setUnlocalizedName("leaves");
            }
            else if (var5 == Blocks.vine) {
                var6 = new ItemColored(Blocks.vine, false);
            }
            else if (var5 == Blocks.tallgrass) {
                var6 = new ItemColored(Blocks.tallgrass, true).func_150943_a(new String[] { "shrub", "grass", "fern" });
            }
            else if (var5 == Blocks.yellow_flower) {
                var6 = new ItemMultiTexture(Blocks.yellow_flower, Blocks.yellow_flower, BlockFlower.field_149858_b).setUnlocalizedName("flower");
            }
            else if (var5 == Blocks.red_flower) {
                var6 = new ItemMultiTexture(Blocks.red_flower, Blocks.red_flower, BlockFlower.field_149859_a).setUnlocalizedName("rose");
            }
            else if (var5 == Blocks.snow_layer) {
                var6 = new ItemSnow(Blocks.snow_layer, Blocks.snow_layer);
            }
            else if (var5 == Blocks.waterlily) {
                var6 = new ItemLilyPad(Blocks.waterlily);
            }
            else if (var5 == Blocks.piston) {
                var6 = new ItemPiston(Blocks.piston);
            }
            else if (var5 == Blocks.sticky_piston) {
                var6 = new ItemPiston(Blocks.sticky_piston);
            }
            else if (var5 == Blocks.cobblestone_wall) {
                var6 = new ItemMultiTexture(Blocks.cobblestone_wall, Blocks.cobblestone_wall, BlockWall.field_150092_a).setUnlocalizedName("cobbleWall");
            }
            else if (var5 == Blocks.anvil) {
                var6 = new ItemAnvilBlock(Blocks.anvil).setUnlocalizedName("anvil");
            }
            else if (var5 == Blocks.double_plant) {
                var6 = new ItemDoublePlant(Blocks.double_plant, Blocks.double_plant, BlockDoublePlant.field_149892_a).setUnlocalizedName("doublePlant");
            }
            else {
                if (var2.contains(var5)) {
                    continue;
                }
                var6 = new ItemBlock(var5);
            }
            Item.itemRegistry.addObject(Block.getIdFromBlock(var5), var4, var6);
        }
    }
    
    public Item setMaxStackSize(final int p_77625_1_) {
        this.maxStackSize = p_77625_1_;
        return this;
    }
    
    public int getSpriteNumber() {
        return 1;
    }
    
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return this.itemIcon;
    }
    
    public final IIcon getIconIndex(final ItemStack p_77650_1_) {
        return this.getIconFromDamage(p_77650_1_.getItemDamage());
    }
    
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        return false;
    }
    
    public float func_150893_a(final ItemStack p_150893_1_, final Block p_150893_2_) {
        return 1.0f;
    }
    
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        return p_77659_1_;
    }
    
    public ItemStack onEaten(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        return p_77654_1_;
    }
    
    public int getItemStackLimit() {
        return this.maxStackSize;
    }
    
    public int getMetadata(final int p_77647_1_) {
        return 0;
    }
    
    public boolean getHasSubtypes() {
        return this.hasSubtypes;
    }
    
    protected Item setHasSubtypes(final boolean p_77627_1_) {
        this.hasSubtypes = p_77627_1_;
        return this;
    }
    
    public int getMaxDamage() {
        return this.maxDamage;
    }
    
    protected Item setMaxDamage(final int p_77656_1_) {
        this.maxDamage = p_77656_1_;
        return this;
    }
    
    public boolean isDamageable() {
        return this.maxDamage > 0 && !this.hasSubtypes;
    }
    
    public boolean hitEntity(final ItemStack p_77644_1_, final EntityLivingBase p_77644_2_, final EntityLivingBase p_77644_3_) {
        return false;
    }
    
    public boolean onBlockDestroyed(final ItemStack p_150894_1_, final World p_150894_2_, final Block p_150894_3_, final int p_150894_4_, final int p_150894_5_, final int p_150894_6_, final EntityLivingBase p_150894_7_) {
        return false;
    }
    
    public boolean func_150897_b(final Block p_150897_1_) {
        return false;
    }
    
    public boolean itemInteractionForEntity(final ItemStack p_111207_1_, final EntityPlayer p_111207_2_, final EntityLivingBase p_111207_3_) {
        return false;
    }
    
    public Item setFull3D() {
        this.bFull3D = true;
        return this;
    }
    
    public boolean isFull3D() {
        return this.bFull3D;
    }
    
    public boolean shouldRotateAroundWhenRendering() {
        return false;
    }
    
    public Item setUnlocalizedName(final String p_77655_1_) {
        this.unlocalizedName = p_77655_1_;
        return this;
    }
    
    public String getUnlocalizedNameInefficiently(final ItemStack p_77657_1_) {
        final String var2 = this.getUnlocalizedName(p_77657_1_);
        return (var2 == null) ? "" : StatCollector.translateToLocal(var2);
    }
    
    public String getUnlocalizedName() {
        return "item." + this.unlocalizedName;
    }
    
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        return "item." + this.unlocalizedName;
    }
    
    public Item setContainerItem(final Item p_77642_1_) {
        this.containerItem = p_77642_1_;
        return this;
    }
    
    public boolean doesContainerItemLeaveCraftingGrid(final ItemStack p_77630_1_) {
        return true;
    }
    
    public boolean getShareTag() {
        return true;
    }
    
    public Item getContainerItem() {
        return this.containerItem;
    }
    
    public boolean hasContainerItem() {
        return this.containerItem != null;
    }
    
    public int getColorFromItemStack(final ItemStack p_82790_1_, final int p_82790_2_) {
        return 16777215;
    }
    
    public void onUpdate(final ItemStack p_77663_1_, final World p_77663_2_, final Entity p_77663_3_, final int p_77663_4_, final boolean p_77663_5_) {
    }
    
    public void onCreated(final ItemStack p_77622_1_, final World p_77622_2_, final EntityPlayer p_77622_3_) {
    }
    
    public boolean isMap() {
        return false;
    }
    
    public EnumAction getItemUseAction(final ItemStack p_77661_1_) {
        return EnumAction.none;
    }
    
    public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
        return 0;
    }
    
    public void onPlayerStoppedUsing(final ItemStack p_77615_1_, final World p_77615_2_, final EntityPlayer p_77615_3_, final int p_77615_4_) {
    }
    
    protected Item setPotionEffect(final String p_77631_1_) {
        this.potionEffect = p_77631_1_;
        return this;
    }
    
    public String getPotionEffect(final ItemStack p_150896_1_) {
        return this.potionEffect;
    }
    
    public boolean isPotionIngredient(final ItemStack p_150892_1_) {
        return this.getPotionEffect(p_150892_1_) != null;
    }
    
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
    }
    
    public String getItemStackDisplayName(final ItemStack p_77653_1_) {
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(p_77653_1_) + ".name")).trim();
    }
    
    public boolean hasEffect(final ItemStack p_77636_1_) {
        return p_77636_1_.isItemEnchanted();
    }
    
    public EnumRarity getRarity(final ItemStack p_77613_1_) {
        return p_77613_1_.isItemEnchanted() ? EnumRarity.rare : EnumRarity.common;
    }
    
    public boolean isItemTool(final ItemStack p_77616_1_) {
        return this.getItemStackLimit() == 1 && this.isDamageable();
    }
    
    protected MovingObjectPosition getMovingObjectPositionFromPlayer(final World p_77621_1_, final EntityPlayer p_77621_2_, final boolean p_77621_3_) {
        final float var4 = 1.0f;
        final float var5 = p_77621_2_.prevRotationPitch + (p_77621_2_.rotationPitch - p_77621_2_.prevRotationPitch) * var4;
        final float var6 = p_77621_2_.prevRotationYaw + (p_77621_2_.rotationYaw - p_77621_2_.prevRotationYaw) * var4;
        final double var7 = p_77621_2_.prevPosX + (p_77621_2_.posX - p_77621_2_.prevPosX) * var4;
        final double var8 = p_77621_2_.prevPosY + (p_77621_2_.posY - p_77621_2_.prevPosY) * var4 + 1.62 - p_77621_2_.yOffset;
        final double var9 = p_77621_2_.prevPosZ + (p_77621_2_.posZ - p_77621_2_.prevPosZ) * var4;
        final Vec3 var10 = Vec3.createVectorHelper(var7, var8, var9);
        final float var11 = MathHelper.cos(-var6 * 0.017453292f - 3.1415927f);
        final float var12 = MathHelper.sin(-var6 * 0.017453292f - 3.1415927f);
        final float var13 = -MathHelper.cos(-var5 * 0.017453292f);
        final float var14 = MathHelper.sin(-var5 * 0.017453292f);
        final float var15 = var12 * var13;
        final float var16 = var11 * var13;
        final double var17 = 5.0;
        final Vec3 var18 = var10.addVector(var15 * var17, var14 * var17, var16 * var17);
        return p_77621_1_.func_147447_a(var10, var18, p_77621_3_, !p_77621_3_, false);
    }
    
    public int getItemEnchantability() {
        return 0;
    }
    
    public boolean requiresMultipleRenderPasses() {
        return false;
    }
    
    public IIcon getIconFromDamageForRenderPass(final int p_77618_1_, final int p_77618_2_) {
        return this.getIconFromDamage(p_77618_1_);
    }
    
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List p_150895_3_) {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
    }
    
    public CreativeTabs getCreativeTab() {
        return this.tabToDisplayOn;
    }
    
    public Item setCreativeTab(final CreativeTabs p_77637_1_) {
        this.tabToDisplayOn = p_77637_1_;
        return this;
    }
    
    public boolean canItemEditBlocks() {
        return true;
    }
    
    public boolean getIsRepairable(final ItemStack p_82789_1_, final ItemStack p_82789_2_) {
        return false;
    }
    
    public void registerIcons(final IIconRegister p_94581_1_) {
        this.itemIcon = p_94581_1_.registerIcon(this.getIconString());
    }
    
    public Multimap getItemAttributeModifiers() {
        return (Multimap)HashMultimap.create();
    }
    
    protected Item setTextureName(final String p_111206_1_) {
        this.iconString = p_111206_1_;
        return this;
    }
    
    protected String getIconString() {
        return (this.iconString == null) ? ("MISSING_ICON_ITEM_" + Item.itemRegistry.getIDForObject(this) + "_" + this.unlocalizedName) : this.iconString;
    }
    
    static {
        itemRegistry = new RegistryNamespaced();
        field_111210_e = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
        Item.itemRand = new Random();
    }
    
    public enum ToolMaterial
    {
        WOOD("WOOD", 0, 0, 59, 2.0f, 0.0f, 15), 
        STONE("STONE", 1, 1, 131, 4.0f, 1.0f, 5), 
        IRON("IRON", 2, 2, 250, 6.0f, 2.0f, 14), 
        EMERALD("EMERALD", 3, 3, 1561, 8.0f, 3.0f, 10), 
        GOLD("GOLD", 4, 0, 32, 12.0f, 0.0f, 22);
        
        private final int harvestLevel;
        private final int maxUses;
        private final float efficiencyOnProperMaterial;
        private final float damageVsEntity;
        private final int enchantability;
        private static final ToolMaterial[] $VALUES;
        private static final String __OBFID = "CL_00000042";
        
        private ToolMaterial(final String p_i1874_1_, final int p_i1874_2_, final int p_i1874_3_, final int p_i1874_4_, final float p_i1874_5_, final float p_i1874_6_, final int p_i1874_7_) {
            this.harvestLevel = p_i1874_3_;
            this.maxUses = p_i1874_4_;
            this.efficiencyOnProperMaterial = p_i1874_5_;
            this.damageVsEntity = p_i1874_6_;
            this.enchantability = p_i1874_7_;
        }
        
        public int getMaxUses() {
            return this.maxUses;
        }
        
        public float getEfficiencyOnProperMaterial() {
            return this.efficiencyOnProperMaterial;
        }
        
        public float getDamageVsEntity() {
            return this.damageVsEntity;
        }
        
        public int getHarvestLevel() {
            return this.harvestLevel;
        }
        
        public int getEnchantability() {
            return this.enchantability;
        }
        
        public Item func_150995_f() {
            return (this == ToolMaterial.WOOD) ? Item.getItemFromBlock(Blocks.planks) : ((this == ToolMaterial.STONE) ? Item.getItemFromBlock(Blocks.cobblestone) : ((this == ToolMaterial.GOLD) ? Items.gold_ingot : ((this == ToolMaterial.IRON) ? Items.iron_ingot : ((this == ToolMaterial.EMERALD) ? Items.diamond : null))));
        }
        
        static {
            $VALUES = new ToolMaterial[] { ToolMaterial.WOOD, ToolMaterial.STONE, ToolMaterial.IRON, ToolMaterial.EMERALD, ToolMaterial.GOLD };
        }
    }
}
