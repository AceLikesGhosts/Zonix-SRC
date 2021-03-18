package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class Block
{
    public static final RegistryNamespaced blockRegistry;
    private CreativeTabs displayOnCreativeTab;
    protected String textureName;
    public static final SoundType soundTypeStone;
    public static final SoundType soundTypeWood;
    public static final SoundType soundTypeGravel;
    public static final SoundType soundTypeGrass;
    public static final SoundType soundTypePiston;
    public static final SoundType soundTypeMetal;
    public static final SoundType soundTypeGlass;
    public static final SoundType soundTypeCloth;
    public static final SoundType field_149776_m;
    public static final SoundType soundTypeSnow;
    public static final SoundType soundTypeLadder;
    public static final SoundType soundTypeAnvil;
    protected boolean opaque;
    protected int lightOpacity;
    protected boolean canBlockGrass;
    protected int lightValue;
    protected boolean field_149783_u;
    protected float blockHardness;
    protected float blockResistance;
    protected boolean field_149791_x;
    protected boolean enableStats;
    protected boolean needsRandomTick;
    protected boolean isBlockContainer;
    protected double field_149759_B;
    protected double field_149760_C;
    protected double field_149754_D;
    protected double field_149755_E;
    protected double field_149756_F;
    protected double field_149757_G;
    public SoundType stepSound;
    public float blockParticleGravity;
    protected final Material blockMaterial;
    public float slipperiness;
    private String unlocalizedNameBlock;
    protected IIcon blockIcon;
    private static final String __OBFID = "CL_00000199";
    
    public static int getIdFromBlock(final Block p_149682_0_) {
        return Block.blockRegistry.getIDForObject(p_149682_0_);
    }
    
    public static Block getBlockById(final int p_149729_0_) {
        return (Block)Block.blockRegistry.getObjectForID(p_149729_0_);
    }
    
    public static Block getBlockFromItem(final Item p_149634_0_) {
        return getBlockById(Item.getIdFromItem(p_149634_0_));
    }
    
    public static Block getBlockFromName(final String p_149684_0_) {
        if (Block.blockRegistry.containsKey(p_149684_0_)) {
            return (Block)Block.blockRegistry.getObject(p_149684_0_);
        }
        try {
            return (Block)Block.blockRegistry.getObjectForID(Integer.parseInt(p_149684_0_));
        }
        catch (NumberFormatException var2) {
            return null;
        }
    }
    
    public boolean func_149730_j() {
        return this.opaque;
    }
    
    public int getLightOpacity() {
        return this.lightOpacity;
    }
    
    public boolean getCanBlockGrass() {
        return this.canBlockGrass;
    }
    
    public int getLightValue() {
        return this.lightValue;
    }
    
    public boolean func_149710_n() {
        return this.field_149783_u;
    }
    
    public Material getMaterial() {
        return this.blockMaterial;
    }
    
    public MapColor getMapColor(final int p_149728_1_) {
        return this.getMaterial().getMaterialMapColor();
    }
    
    public static void registerBlocks() {
        Block.blockRegistry.addObject(0, "air", new BlockAir().setBlockName("air"));
        Block.blockRegistry.addObject(1, "stone", new BlockStone().setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("stone").setBlockTextureName("stone"));
        Block.blockRegistry.addObject(2, "grass", new BlockGrass().setHardness(0.6f).setStepSound(Block.soundTypeGrass).setBlockName("grass").setBlockTextureName("grass"));
        Block.blockRegistry.addObject(3, "dirt", new BlockDirt().setHardness(0.5f).setStepSound(Block.soundTypeGravel).setBlockName("dirt").setBlockTextureName("dirt"));
        final Block var0 = new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("stonebrick").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("cobblestone");
        Block.blockRegistry.addObject(4, "cobblestone", var0);
        final Block var2 = new BlockWood().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setBlockName("wood").setBlockTextureName("planks");
        Block.blockRegistry.addObject(5, "planks", var2);
        Block.blockRegistry.addObject(6, "sapling", new BlockSapling().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setBlockName("sapling").setBlockTextureName("sapling"));
        Block.blockRegistry.addObject(7, "bedrock", new Block(Material.rock).setBlockUnbreakable().setResistance(6000000.0f).setStepSound(Block.soundTypePiston).setBlockName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("bedrock"));
        Block.blockRegistry.addObject(8, "flowing_water", new BlockDynamicLiquid(Material.water).setHardness(100.0f).setLightOpacity(3).setBlockName("water").disableStats().setBlockTextureName("water_flow"));
        Block.blockRegistry.addObject(9, "water", new BlockStaticLiquid(Material.water).setHardness(100.0f).setLightOpacity(3).setBlockName("water").disableStats().setBlockTextureName("water_still"));
        Block.blockRegistry.addObject(10, "flowing_lava", new BlockDynamicLiquid(Material.lava).setHardness(100.0f).setLightLevel(1.0f).setBlockName("lava").disableStats().setBlockTextureName("lava_flow"));
        Block.blockRegistry.addObject(11, "lava", new BlockStaticLiquid(Material.lava).setHardness(100.0f).setLightLevel(1.0f).setBlockName("lava").disableStats().setBlockTextureName("lava_still"));
        Block.blockRegistry.addObject(12, "sand", new BlockSand().setHardness(0.5f).setStepSound(Block.field_149776_m).setBlockName("sand").setBlockTextureName("sand"));
        Block.blockRegistry.addObject(13, "gravel", new BlockGravel().setHardness(0.6f).setStepSound(Block.soundTypeGravel).setBlockName("gravel").setBlockTextureName("gravel"));
        Block.blockRegistry.addObject(14, "gold_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("oreGold").setBlockTextureName("gold_ore"));
        Block.blockRegistry.addObject(15, "iron_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("oreIron").setBlockTextureName("iron_ore"));
        Block.blockRegistry.addObject(16, "coal_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("oreCoal").setBlockTextureName("coal_ore"));
        Block.blockRegistry.addObject(17, "log", new BlockOldLog().setBlockName("log").setBlockTextureName("log"));
        Block.blockRegistry.addObject(18, "leaves", new BlockOldLeaf().setBlockName("leaves").setBlockTextureName("leaves"));
        Block.blockRegistry.addObject(19, "sponge", new BlockSponge().setHardness(0.6f).setStepSound(Block.soundTypeGrass).setBlockName("sponge").setBlockTextureName("sponge"));
        Block.blockRegistry.addObject(20, "glass", new BlockGlass(Material.glass, false).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setBlockName("glass").setBlockTextureName("glass"));
        Block.blockRegistry.addObject(21, "lapis_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("oreLapis").setBlockTextureName("lapis_ore"));
        Block.blockRegistry.addObject(22, "lapis_block", new BlockCompressed(MapColor.field_151652_H).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("blockLapis").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("lapis_block"));
        Block.blockRegistry.addObject(23, "dispenser", new BlockDispenser().setHardness(3.5f).setStepSound(Block.soundTypePiston).setBlockName("dispenser").setBlockTextureName("dispenser"));
        final Block var3 = new BlockSandStone().setStepSound(Block.soundTypePiston).setHardness(0.8f).setBlockName("sandStone").setBlockTextureName("sandstone");
        Block.blockRegistry.addObject(24, "sandstone", var3);
        Block.blockRegistry.addObject(25, "noteblock", new BlockNote().setHardness(0.8f).setBlockName("musicBlock").setBlockTextureName("noteblock"));
        Block.blockRegistry.addObject(26, "bed", new BlockBed().setHardness(0.2f).setBlockName("bed").disableStats().setBlockTextureName("bed"));
        Block.blockRegistry.addObject(27, "golden_rail", new BlockRailPowered().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setBlockName("goldenRail").setBlockTextureName("rail_golden"));
        Block.blockRegistry.addObject(28, "detector_rail", new BlockRailDetector().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setBlockName("detectorRail").setBlockTextureName("rail_detector"));
        Block.blockRegistry.addObject(29, "sticky_piston", new BlockPistonBase(true).setBlockName("pistonStickyBase"));
        Block.blockRegistry.addObject(30, "web", new BlockWeb().setLightOpacity(1).setHardness(4.0f).setBlockName("web").setBlockTextureName("web"));
        Block.blockRegistry.addObject(31, "tallgrass", new BlockTallGrass().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setBlockName("tallgrass"));
        Block.blockRegistry.addObject(32, "deadbush", new BlockDeadBush().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setBlockName("deadbush").setBlockTextureName("deadbush"));
        Block.blockRegistry.addObject(33, "piston", new BlockPistonBase(false).setBlockName("pistonBase"));
        Block.blockRegistry.addObject(34, "piston_head", new BlockPistonExtension());
        Block.blockRegistry.addObject(35, "wool", new BlockColored(Material.cloth).setHardness(0.8f).setStepSound(Block.soundTypeCloth).setBlockName("cloth").setBlockTextureName("wool_colored"));
        Block.blockRegistry.addObject(36, "piston_extension", new BlockPistonMoving());
        Block.blockRegistry.addObject(37, "yellow_flower", new BlockFlower(0).setHardness(0.0f).setStepSound(Block.soundTypeGrass).setBlockName("flower1").setBlockTextureName("flower_dandelion"));
        Block.blockRegistry.addObject(38, "red_flower", new BlockFlower(1).setHardness(0.0f).setStepSound(Block.soundTypeGrass).setBlockName("flower2").setBlockTextureName("flower_rose"));
        Block.blockRegistry.addObject(39, "brown_mushroom", new BlockMushroom().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setLightLevel(0.125f).setBlockName("mushroom").setBlockTextureName("mushroom_brown"));
        Block.blockRegistry.addObject(40, "red_mushroom", new BlockMushroom().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setBlockName("mushroom").setBlockTextureName("mushroom_red"));
        Block.blockRegistry.addObject(41, "gold_block", new BlockCompressed(MapColor.field_151647_F).setHardness(3.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setBlockName("blockGold").setBlockTextureName("gold_block"));
        Block.blockRegistry.addObject(42, "iron_block", new BlockCompressed(MapColor.field_151668_h).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setBlockName("blockIron").setBlockTextureName("iron_block"));
        Block.blockRegistry.addObject(43, "double_stone_slab", new BlockStoneSlab(true).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("stoneSlab"));
        Block.blockRegistry.addObject(44, "stone_slab", new BlockStoneSlab(false).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("stoneSlab"));
        final Block var4 = new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("brick").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("brick");
        Block.blockRegistry.addObject(45, "brick_block", var4);
        Block.blockRegistry.addObject(46, "tnt", new BlockTNT().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setBlockName("tnt").setBlockTextureName("tnt"));
        Block.blockRegistry.addObject(47, "bookshelf", new BlockBookshelf().setHardness(1.5f).setStepSound(Block.soundTypeWood).setBlockName("bookshelf").setBlockTextureName("bookshelf"));
        Block.blockRegistry.addObject(48, "mossy_cobblestone", new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("cobblestone_mossy"));
        Block.blockRegistry.addObject(49, "obsidian", new BlockObsidian().setHardness(50.0f).setResistance(2000.0f).setStepSound(Block.soundTypePiston).setBlockName("obsidian").setBlockTextureName("obsidian"));
        Block.blockRegistry.addObject(50, "torch", new BlockTorch().setHardness(0.0f).setLightLevel(0.9375f).setStepSound(Block.soundTypeWood).setBlockName("torch").setBlockTextureName("torch_on"));
        Block.blockRegistry.addObject(51, "fire", new BlockFire().setHardness(0.0f).setLightLevel(1.0f).setStepSound(Block.soundTypeWood).setBlockName("fire").disableStats().setBlockTextureName("fire"));
        Block.blockRegistry.addObject(52, "mob_spawner", new BlockMobSpawner().setHardness(5.0f).setStepSound(Block.soundTypeMetal).setBlockName("mobSpawner").disableStats().setBlockTextureName("mob_spawner"));
        Block.blockRegistry.addObject(53, "oak_stairs", new BlockStairs(var2, 0).setBlockName("stairsWood"));
        Block.blockRegistry.addObject(54, "chest", new BlockChest(0).setHardness(2.5f).setStepSound(Block.soundTypeWood).setBlockName("chest"));
        Block.blockRegistry.addObject(55, "redstone_wire", new BlockRedstoneWire().setHardness(0.0f).setStepSound(Block.soundTypeStone).setBlockName("redstoneDust").disableStats().setBlockTextureName("redstone_dust"));
        Block.blockRegistry.addObject(56, "diamond_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("oreDiamond").setBlockTextureName("diamond_ore"));
        Block.blockRegistry.addObject(57, "diamond_block", new BlockCompressed(MapColor.field_151648_G).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setBlockName("blockDiamond").setBlockTextureName("diamond_block"));
        Block.blockRegistry.addObject(58, "crafting_table", new BlockWorkbench().setHardness(2.5f).setStepSound(Block.soundTypeWood).setBlockName("workbench").setBlockTextureName("crafting_table"));
        Block.blockRegistry.addObject(59, "wheat", new BlockCrops().setBlockName("crops").setBlockTextureName("wheat"));
        final Block var5 = new BlockFarmland().setHardness(0.6f).setStepSound(Block.soundTypeGravel).setBlockName("farmland").setBlockTextureName("farmland");
        Block.blockRegistry.addObject(60, "farmland", var5);
        Block.blockRegistry.addObject(61, "furnace", new BlockFurnace(false).setHardness(3.5f).setStepSound(Block.soundTypePiston).setBlockName("furnace").setCreativeTab(CreativeTabs.tabDecorations));
        Block.blockRegistry.addObject(62, "lit_furnace", new BlockFurnace(true).setHardness(3.5f).setStepSound(Block.soundTypePiston).setLightLevel(0.875f).setBlockName("furnace"));
        Block.blockRegistry.addObject(63, "standing_sign", new BlockSign(TileEntitySign.class, true).setHardness(1.0f).setStepSound(Block.soundTypeWood).setBlockName("sign").disableStats());
        Block.blockRegistry.addObject(64, "wooden_door", new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setBlockName("doorWood").disableStats().setBlockTextureName("door_wood"));
        Block.blockRegistry.addObject(65, "ladder", new BlockLadder().setHardness(0.4f).setStepSound(Block.soundTypeLadder).setBlockName("ladder").setBlockTextureName("ladder"));
        Block.blockRegistry.addObject(66, "rail", new BlockRail().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setBlockName("rail").setBlockTextureName("rail_normal"));
        Block.blockRegistry.addObject(67, "stone_stairs", new BlockStairs(var0, 0).setBlockName("stairsStone"));
        Block.blockRegistry.addObject(68, "wall_sign", new BlockSign(TileEntitySign.class, false).setHardness(1.0f).setStepSound(Block.soundTypeWood).setBlockName("sign").disableStats());
        Block.blockRegistry.addObject(69, "lever", new BlockLever().setHardness(0.5f).setStepSound(Block.soundTypeWood).setBlockName("lever").setBlockTextureName("lever"));
        Block.blockRegistry.addObject(70, "stone_pressure_plate", new BlockPressurePlate("stone", Material.rock, BlockPressurePlate.Sensitivity.mobs).setHardness(0.5f).setStepSound(Block.soundTypePiston).setBlockName("pressurePlate"));
        Block.blockRegistry.addObject(71, "iron_door", new BlockDoor(Material.iron).setHardness(5.0f).setStepSound(Block.soundTypeMetal).setBlockName("doorIron").disableStats().setBlockTextureName("door_iron"));
        Block.blockRegistry.addObject(72, "wooden_pressure_plate", new BlockPressurePlate("planks_oak", Material.wood, BlockPressurePlate.Sensitivity.everything).setHardness(0.5f).setStepSound(Block.soundTypeWood).setBlockName("pressurePlate"));
        Block.blockRegistry.addObject(73, "redstone_ore", new BlockRedstoneOre(false).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("redstone_ore"));
        Block.blockRegistry.addObject(74, "lit_redstone_ore", new BlockRedstoneOre(true).setLightLevel(0.625f).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("oreRedstone").setBlockTextureName("redstone_ore"));
        Block.blockRegistry.addObject(75, "unlit_redstone_torch", new BlockRedstoneTorch(false).setHardness(0.0f).setStepSound(Block.soundTypeWood).setBlockName("notGate").setBlockTextureName("redstone_torch_off"));
        Block.blockRegistry.addObject(76, "redstone_torch", new BlockRedstoneTorch(true).setHardness(0.0f).setLightLevel(0.5f).setStepSound(Block.soundTypeWood).setBlockName("notGate").setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName("redstone_torch_on"));
        Block.blockRegistry.addObject(77, "stone_button", new BlockButtonStone().setHardness(0.5f).setStepSound(Block.soundTypePiston).setBlockName("button"));
        Block.blockRegistry.addObject(78, "snow_layer", new BlockSnow().setHardness(0.1f).setStepSound(Block.soundTypeSnow).setBlockName("snow").setLightOpacity(0).setBlockTextureName("snow"));
        Block.blockRegistry.addObject(79, "ice", new BlockIce().setHardness(0.5f).setLightOpacity(3).setStepSound(Block.soundTypeGlass).setBlockName("ice").setBlockTextureName("ice"));
        Block.blockRegistry.addObject(80, "snow", new BlockSnowBlock().setHardness(0.2f).setStepSound(Block.soundTypeSnow).setBlockName("snow").setBlockTextureName("snow"));
        Block.blockRegistry.addObject(81, "cactus", new BlockCactus().setHardness(0.4f).setStepSound(Block.soundTypeCloth).setBlockName("cactus").setBlockTextureName("cactus"));
        Block.blockRegistry.addObject(82, "clay", new BlockClay().setHardness(0.6f).setStepSound(Block.soundTypeGravel).setBlockName("clay").setBlockTextureName("clay"));
        Block.blockRegistry.addObject(83, "reeds", new BlockReed().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setBlockName("reeds").disableStats().setBlockTextureName("reeds"));
        Block.blockRegistry.addObject(84, "jukebox", new BlockJukebox().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("jukebox").setBlockTextureName("jukebox"));
        Block.blockRegistry.addObject(85, "fence", new BlockFence("planks_oak", Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setBlockName("fence"));
        final Block var6 = new BlockPumpkin(false).setHardness(1.0f).setStepSound(Block.soundTypeWood).setBlockName("pumpkin").setBlockTextureName("pumpkin");
        Block.blockRegistry.addObject(86, "pumpkin", var6);
        Block.blockRegistry.addObject(87, "netherrack", new BlockNetherrack().setHardness(0.4f).setStepSound(Block.soundTypePiston).setBlockName("hellrock").setBlockTextureName("netherrack"));
        Block.blockRegistry.addObject(88, "soul_sand", new BlockSoulSand().setHardness(0.5f).setStepSound(Block.field_149776_m).setBlockName("hellsand").setBlockTextureName("soul_sand"));
        Block.blockRegistry.addObject(89, "glowstone", new BlockGlowstone(Material.glass).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setLightLevel(1.0f).setBlockName("lightgem").setBlockTextureName("glowstone"));
        Block.blockRegistry.addObject(90, "portal", new BlockPortal().setHardness(-1.0f).setStepSound(Block.soundTypeGlass).setLightLevel(0.75f).setBlockName("portal").setBlockTextureName("portal"));
        Block.blockRegistry.addObject(91, "lit_pumpkin", new BlockPumpkin(true).setHardness(1.0f).setStepSound(Block.soundTypeWood).setLightLevel(1.0f).setBlockName("litpumpkin").setBlockTextureName("pumpkin"));
        Block.blockRegistry.addObject(92, "cake", new BlockCake().setHardness(0.5f).setStepSound(Block.soundTypeCloth).setBlockName("cake").disableStats().setBlockTextureName("cake"));
        Block.blockRegistry.addObject(93, "unpowered_repeater", new BlockRedstoneRepeater(false).setHardness(0.0f).setStepSound(Block.soundTypeWood).setBlockName("diode").disableStats().setBlockTextureName("repeater_off"));
        Block.blockRegistry.addObject(94, "powered_repeater", new BlockRedstoneRepeater(true).setHardness(0.0f).setLightLevel(0.625f).setStepSound(Block.soundTypeWood).setBlockName("diode").disableStats().setBlockTextureName("repeater_on"));
        Block.blockRegistry.addObject(95, "stained_glass", new BlockStainedGlass(Material.glass).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setBlockName("stainedGlass").setBlockTextureName("glass"));
        Block.blockRegistry.addObject(96, "trapdoor", new BlockTrapDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setBlockName("trapdoor").disableStats().setBlockTextureName("trapdoor"));
        Block.blockRegistry.addObject(97, "monster_egg", new BlockSilverfish().setHardness(0.75f).setBlockName("monsterStoneEgg"));
        final Block var7 = new BlockStoneBrick().setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("stonebricksmooth").setBlockTextureName("stonebrick");
        Block.blockRegistry.addObject(98, "stonebrick", var7);
        Block.blockRegistry.addObject(99, "brown_mushroom_block", new BlockHugeMushroom(Material.wood, 0).setHardness(0.2f).setStepSound(Block.soundTypeWood).setBlockName("mushroom").setBlockTextureName("mushroom_block"));
        Block.blockRegistry.addObject(100, "red_mushroom_block", new BlockHugeMushroom(Material.wood, 1).setHardness(0.2f).setStepSound(Block.soundTypeWood).setBlockName("mushroom").setBlockTextureName("mushroom_block"));
        Block.blockRegistry.addObject(101, "iron_bars", new BlockPane("iron_bars", "iron_bars", Material.iron, true).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setBlockName("fenceIron"));
        Block.blockRegistry.addObject(102, "glass_pane", new BlockPane("glass", "glass_pane_top", Material.glass, false).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setBlockName("thinGlass"));
        final Block var8 = new BlockMelon().setHardness(1.0f).setStepSound(Block.soundTypeWood).setBlockName("melon").setBlockTextureName("melon");
        Block.blockRegistry.addObject(103, "melon_block", var8);
        Block.blockRegistry.addObject(104, "pumpkin_stem", new BlockStem(var6).setHardness(0.0f).setStepSound(Block.soundTypeWood).setBlockName("pumpkinStem").setBlockTextureName("pumpkin_stem"));
        Block.blockRegistry.addObject(105, "melon_stem", new BlockStem(var8).setHardness(0.0f).setStepSound(Block.soundTypeWood).setBlockName("pumpkinStem").setBlockTextureName("melon_stem"));
        Block.blockRegistry.addObject(106, "vine", new BlockVine().setHardness(0.2f).setStepSound(Block.soundTypeGrass).setBlockName("vine").setBlockTextureName("vine"));
        Block.blockRegistry.addObject(107, "fence_gate", new BlockFenceGate().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setBlockName("fenceGate"));
        Block.blockRegistry.addObject(108, "brick_stairs", new BlockStairs(var4, 0).setBlockName("stairsBrick"));
        Block.blockRegistry.addObject(109, "stone_brick_stairs", new BlockStairs(var7, 0).setBlockName("stairsStoneBrickSmooth"));
        Block.blockRegistry.addObject(110, "mycelium", new BlockMycelium().setHardness(0.6f).setStepSound(Block.soundTypeGrass).setBlockName("mycel").setBlockTextureName("mycelium"));
        Block.blockRegistry.addObject(111, "waterlily", new BlockLilyPad().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setBlockName("waterlily").setBlockTextureName("waterlily"));
        final Block var9 = new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("netherBrick").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("nether_brick");
        Block.blockRegistry.addObject(112, "nether_brick", var9);
        Block.blockRegistry.addObject(113, "nether_brick_fence", new BlockFence("nether_brick", Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("netherFence"));
        Block.blockRegistry.addObject(114, "nether_brick_stairs", new BlockStairs(var9, 0).setBlockName("stairsNetherBrick"));
        Block.blockRegistry.addObject(115, "nether_wart", new BlockNetherWart().setBlockName("netherStalk").setBlockTextureName("nether_wart"));
        Block.blockRegistry.addObject(116, "enchanting_table", new BlockEnchantmentTable().setHardness(5.0f).setResistance(2000.0f).setBlockName("enchantmentTable").setBlockTextureName("enchanting_table"));
        Block.blockRegistry.addObject(117, "brewing_stand", new BlockBrewingStand().setHardness(0.5f).setLightLevel(0.125f).setBlockName("brewingStand").setBlockTextureName("brewing_stand"));
        Block.blockRegistry.addObject(118, "cauldron", new BlockCauldron().setHardness(2.0f).setBlockName("cauldron").setBlockTextureName("cauldron"));
        Block.blockRegistry.addObject(119, "end_portal", new BlockEndPortal(Material.Portal).setHardness(-1.0f).setResistance(6000000.0f));
        Block.blockRegistry.addObject(120, "end_portal_frame", new BlockEndPortalFrame().setStepSound(Block.soundTypeGlass).setLightLevel(0.125f).setHardness(-1.0f).setBlockName("endPortalFrame").setResistance(6000000.0f).setCreativeTab(CreativeTabs.tabDecorations).setBlockTextureName("endframe"));
        Block.blockRegistry.addObject(121, "end_stone", new Block(Material.rock).setHardness(3.0f).setResistance(15.0f).setStepSound(Block.soundTypePiston).setBlockName("whiteStone").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("end_stone"));
        Block.blockRegistry.addObject(122, "dragon_egg", new BlockDragonEgg().setHardness(3.0f).setResistance(15.0f).setStepSound(Block.soundTypePiston).setLightLevel(0.125f).setBlockName("dragonEgg").setBlockTextureName("dragon_egg"));
        Block.blockRegistry.addObject(123, "redstone_lamp", new BlockRedstoneLight(false).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setBlockName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName("redstone_lamp_off"));
        Block.blockRegistry.addObject(124, "lit_redstone_lamp", new BlockRedstoneLight(true).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setBlockName("redstoneLight").setBlockTextureName("redstone_lamp_on"));
        Block.blockRegistry.addObject(125, "double_wooden_slab", new BlockWoodSlab(true).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setBlockName("woodSlab"));
        Block.blockRegistry.addObject(126, "wooden_slab", new BlockWoodSlab(false).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setBlockName("woodSlab"));
        Block.blockRegistry.addObject(127, "cocoa", new BlockCocoa().setHardness(0.2f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setBlockName("cocoa").setBlockTextureName("cocoa"));
        Block.blockRegistry.addObject(128, "sandstone_stairs", new BlockStairs(var3, 0).setBlockName("stairsSandStone"));
        Block.blockRegistry.addObject(129, "emerald_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("oreEmerald").setBlockTextureName("emerald_ore"));
        Block.blockRegistry.addObject(130, "ender_chest", new BlockEnderChest().setHardness(22.5f).setResistance(1000.0f).setStepSound(Block.soundTypePiston).setBlockName("enderChest").setLightLevel(0.5f));
        Block.blockRegistry.addObject(131, "tripwire_hook", new BlockTripWireHook().setBlockName("tripWireSource").setBlockTextureName("trip_wire_source"));
        Block.blockRegistry.addObject(132, "tripwire", new BlockTripWire().setBlockName("tripWire").setBlockTextureName("trip_wire"));
        Block.blockRegistry.addObject(133, "emerald_block", new BlockCompressed(MapColor.field_151653_I).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setBlockName("blockEmerald").setBlockTextureName("emerald_block"));
        Block.blockRegistry.addObject(134, "spruce_stairs", new BlockStairs(var2, 1).setBlockName("stairsWoodSpruce"));
        Block.blockRegistry.addObject(135, "birch_stairs", new BlockStairs(var2, 2).setBlockName("stairsWoodBirch"));
        Block.blockRegistry.addObject(136, "jungle_stairs", new BlockStairs(var2, 3).setBlockName("stairsWoodJungle"));
        Block.blockRegistry.addObject(137, "command_block", new BlockCommandBlock().setBlockUnbreakable().setResistance(6000000.0f).setBlockName("commandBlock").setBlockTextureName("command_block"));
        Block.blockRegistry.addObject(138, "beacon", new BlockBeacon().setBlockName("beacon").setLightLevel(1.0f).setBlockTextureName("beacon"));
        Block.blockRegistry.addObject(139, "cobblestone_wall", new BlockWall(var0).setBlockName("cobbleWall"));
        Block.blockRegistry.addObject(140, "flower_pot", new BlockFlowerPot().setHardness(0.0f).setStepSound(Block.soundTypeStone).setBlockName("flowerPot").setBlockTextureName("flower_pot"));
        Block.blockRegistry.addObject(141, "carrots", new BlockCarrot().setBlockName("carrots").setBlockTextureName("carrots"));
        Block.blockRegistry.addObject(142, "potatoes", new BlockPotato().setBlockName("potatoes").setBlockTextureName("potatoes"));
        Block.blockRegistry.addObject(143, "wooden_button", new BlockButtonWood().setHardness(0.5f).setStepSound(Block.soundTypeWood).setBlockName("button"));
        Block.blockRegistry.addObject(144, "skull", new BlockSkull().setHardness(1.0f).setStepSound(Block.soundTypePiston).setBlockName("skull").setBlockTextureName("skull"));
        Block.blockRegistry.addObject(145, "anvil", new BlockAnvil().setHardness(5.0f).setStepSound(Block.soundTypeAnvil).setResistance(2000.0f).setBlockName("anvil"));
        Block.blockRegistry.addObject(146, "trapped_chest", new BlockChest(1).setHardness(2.5f).setStepSound(Block.soundTypeWood).setBlockName("chestTrap"));
        Block.blockRegistry.addObject(147, "light_weighted_pressure_plate", new BlockPressurePlateWeighted("gold_block", Material.iron, 15).setHardness(0.5f).setStepSound(Block.soundTypeWood).setBlockName("weightedPlate_light"));
        Block.blockRegistry.addObject(148, "heavy_weighted_pressure_plate", new BlockPressurePlateWeighted("iron_block", Material.iron, 150).setHardness(0.5f).setStepSound(Block.soundTypeWood).setBlockName("weightedPlate_heavy"));
        Block.blockRegistry.addObject(149, "unpowered_comparator", new BlockRedstoneComparator(false).setHardness(0.0f).setStepSound(Block.soundTypeWood).setBlockName("comparator").disableStats().setBlockTextureName("comparator_off"));
        Block.blockRegistry.addObject(150, "powered_comparator", new BlockRedstoneComparator(true).setHardness(0.0f).setLightLevel(0.625f).setStepSound(Block.soundTypeWood).setBlockName("comparator").disableStats().setBlockTextureName("comparator_on"));
        Block.blockRegistry.addObject(151, "daylight_detector", new BlockDaylightDetector().setHardness(0.2f).setStepSound(Block.soundTypeWood).setBlockName("daylightDetector").setBlockTextureName("daylight_detector"));
        Block.blockRegistry.addObject(152, "redstone_block", new BlockCompressedPowered(MapColor.field_151656_f).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setBlockName("blockRedstone").setBlockTextureName("redstone_block"));
        Block.blockRegistry.addObject(153, "quartz_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setBlockName("netherquartz").setBlockTextureName("quartz_ore"));
        Block.blockRegistry.addObject(154, "hopper", new BlockHopper().setHardness(3.0f).setResistance(8.0f).setStepSound(Block.soundTypeWood).setBlockName("hopper").setBlockTextureName("hopper"));
        final Block var10 = new BlockQuartz().setStepSound(Block.soundTypePiston).setHardness(0.8f).setBlockName("quartzBlock").setBlockTextureName("quartz_block");
        Block.blockRegistry.addObject(155, "quartz_block", var10);
        Block.blockRegistry.addObject(156, "quartz_stairs", new BlockStairs(var10, 0).setBlockName("stairsQuartz"));
        Block.blockRegistry.addObject(157, "activator_rail", new BlockRailPowered().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setBlockName("activatorRail").setBlockTextureName("rail_activator"));
        Block.blockRegistry.addObject(158, "dropper", new BlockDropper().setHardness(3.5f).setStepSound(Block.soundTypePiston).setBlockName("dropper").setBlockTextureName("dropper"));
        Block.blockRegistry.addObject(159, "stained_hardened_clay", new BlockColored(Material.rock).setHardness(1.25f).setResistance(7.0f).setStepSound(Block.soundTypePiston).setBlockName("clayHardenedStained").setBlockTextureName("hardened_clay_stained"));
        Block.blockRegistry.addObject(160, "stained_glass_pane", new BlockStainedGlassPane().setHardness(0.3f).setStepSound(Block.soundTypeGlass).setBlockName("thinStainedGlass").setBlockTextureName("glass"));
        Block.blockRegistry.addObject(161, "leaves2", new BlockNewLeaf().setBlockName("leaves").setBlockTextureName("leaves"));
        Block.blockRegistry.addObject(162, "log2", new BlockNewLog().setBlockName("log").setBlockTextureName("log"));
        Block.blockRegistry.addObject(163, "acacia_stairs", new BlockStairs(var2, 4).setBlockName("stairsWoodAcacia"));
        Block.blockRegistry.addObject(164, "dark_oak_stairs", new BlockStairs(var2, 5).setBlockName("stairsWoodDarkOak"));
        Block.blockRegistry.addObject(170, "hay_block", new BlockHay().setHardness(0.5f).setStepSound(Block.soundTypeGrass).setBlockName("hayBlock").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("hay_block"));
        Block.blockRegistry.addObject(171, "carpet", new BlockCarpet().setHardness(0.1f).setStepSound(Block.soundTypeCloth).setBlockName("woolCarpet").setLightOpacity(0));
        Block.blockRegistry.addObject(172, "hardened_clay", new BlockHardenedClay().setHardness(1.25f).setResistance(7.0f).setStepSound(Block.soundTypePiston).setBlockName("clayHardened").setBlockTextureName("hardened_clay"));
        Block.blockRegistry.addObject(173, "coal_block", new Block(Material.rock).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setBlockName("blockCoal").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("coal_block"));
        Block.blockRegistry.addObject(174, "packed_ice", new BlockPackedIce().setHardness(0.5f).setStepSound(Block.soundTypeGlass).setBlockName("icePacked").setBlockTextureName("ice_packed"));
        Block.blockRegistry.addObject(175, "double_plant", new BlockDoublePlant());
        for (final Block var12 : Block.blockRegistry) {
            if (var12.blockMaterial == Material.air) {
                var12.field_149783_u = false;
            }
            else {
                boolean var13 = false;
                final boolean var14 = var12.getRenderType() == 10;
                final boolean var15 = var12 instanceof BlockSlab;
                final boolean var16 = var12 == var5;
                final boolean var17 = var12.canBlockGrass;
                final boolean var18 = var12.lightOpacity == 0;
                if (var14 || var15 || var16 || var17 || var18) {
                    var13 = true;
                }
                var12.field_149783_u = var13;
            }
        }
    }
    
    protected Block(final Material p_i45394_1_) {
        this.field_149791_x = true;
        this.enableStats = true;
        this.stepSound = Block.soundTypeStone;
        this.blockParticleGravity = 1.0f;
        this.slipperiness = 0.6f;
        this.blockMaterial = p_i45394_1_;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.opaque = this.isOpaqueCube();
        this.lightOpacity = (this.isOpaqueCube() ? 255 : 0);
        this.canBlockGrass = !p_i45394_1_.getCanBlockGrass();
    }
    
    protected Block setStepSound(final SoundType p_149672_1_) {
        this.stepSound = p_149672_1_;
        return this;
    }
    
    public Block setLightOpacity(final int p_149713_1_) {
        this.lightOpacity = p_149713_1_;
        return this;
    }
    
    protected Block setLightLevel(final float p_149715_1_) {
        this.lightValue = (int)(15.0f * p_149715_1_);
        return this;
    }
    
    protected Block setResistance(final float p_149752_1_) {
        this.blockResistance = p_149752_1_ * 3.0f;
        return this;
    }
    
    public boolean isBlockNormalCube() {
        return this.blockMaterial.blocksMovement() && this.renderAsNormalBlock();
    }
    
    public boolean isNormalCube() {
        return this.blockMaterial.isOpaque() && this.renderAsNormalBlock() && !this.canProvidePower();
    }
    
    public boolean renderAsNormalBlock() {
        return true;
    }
    
    public boolean getBlocksMovement(final IBlockAccess p_149655_1_, final int p_149655_2_, final int p_149655_3_, final int p_149655_4_) {
        return !this.blockMaterial.blocksMovement();
    }
    
    public int getRenderType() {
        return 0;
    }
    
    protected Block setHardness(final float p_149711_1_) {
        this.blockHardness = p_149711_1_;
        if (this.blockResistance < p_149711_1_ * 5.0f) {
            this.blockResistance = p_149711_1_ * 5.0f;
        }
        return this;
    }
    
    protected Block setBlockUnbreakable() {
        this.setHardness(-1.0f);
        return this;
    }
    
    public float getBlockHardness(final World p_149712_1_, final int p_149712_2_, final int p_149712_3_, final int p_149712_4_) {
        return this.blockHardness;
    }
    
    protected Block setTickRandomly(final boolean p_149675_1_) {
        this.needsRandomTick = p_149675_1_;
        return this;
    }
    
    public boolean getTickRandomly() {
        return this.needsRandomTick;
    }
    
    public boolean hasTileEntity() {
        return this.isBlockContainer;
    }
    
    protected final void setBlockBounds(final float p_149676_1_, final float p_149676_2_, final float p_149676_3_, final float p_149676_4_, final float p_149676_5_, final float p_149676_6_) {
        this.field_149759_B = p_149676_1_;
        this.field_149760_C = p_149676_2_;
        this.field_149754_D = p_149676_3_;
        this.field_149755_E = p_149676_4_;
        this.field_149756_F = p_149676_5_;
        this.field_149757_G = p_149676_6_;
    }
    
    public int getBlockBrightness(final IBlockAccess p_149677_1_, final int p_149677_2_, int p_149677_3_, final int p_149677_4_) {
        Block var5 = p_149677_1_.getBlock(p_149677_2_, p_149677_3_, p_149677_4_);
        final int var6 = p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, var5.getLightValue());
        if (var6 == 0 && var5 instanceof BlockSlab) {
            --p_149677_3_;
            var5 = p_149677_1_.getBlock(p_149677_2_, p_149677_3_, p_149677_4_);
            return p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, var5.getLightValue());
        }
        return var6;
    }
    
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return (p_149646_5_ == 0 && this.field_149760_C > 0.0) || (p_149646_5_ == 1 && this.field_149756_F < 1.0) || (p_149646_5_ == 2 && this.field_149754_D > 0.0) || (p_149646_5_ == 3 && this.field_149757_G < 1.0) || (p_149646_5_ == 4 && this.field_149759_B > 0.0) || (p_149646_5_ == 5 && this.field_149755_E < 1.0) || !p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_).isOpaqueCube();
    }
    
    public boolean isBlockSolid(final IBlockAccess p_149747_1_, final int p_149747_2_, final int p_149747_3_, final int p_149747_4_, final int p_149747_5_) {
        return p_149747_1_.getBlock(p_149747_2_, p_149747_3_, p_149747_4_).getMaterial().isSolid();
    }
    
    public IIcon getIcon(final IBlockAccess p_149673_1_, final int p_149673_2_, final int p_149673_3_, final int p_149673_4_, final int p_149673_5_) {
        return this.getIcon(p_149673_5_, p_149673_1_.getBlockMetadata(p_149673_2_, p_149673_3_, p_149673_4_));
    }
    
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return this.blockIcon;
    }
    
    public final IIcon getBlockTextureFromSide(final int p_149733_1_) {
        return this.getIcon(p_149733_1_, 0);
    }
    
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World p_149633_1_, final int p_149633_2_, final int p_149633_3_, final int p_149633_4_) {
        return AxisAlignedBB.getBoundingBox(p_149633_2_ + this.field_149759_B, p_149633_3_ + this.field_149760_C, p_149633_4_ + this.field_149754_D, p_149633_2_ + this.field_149755_E, p_149633_3_ + this.field_149756_F, p_149633_4_ + this.field_149757_G);
    }
    
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        final AxisAlignedBB var8 = this.getCollisionBoundingBoxFromPool(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
        if (var8 != null && p_149743_5_.intersectsWith(var8)) {
            p_149743_6_.add(var8);
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return AxisAlignedBB.getBoundingBox(p_149668_2_ + this.field_149759_B, p_149668_3_ + this.field_149760_C, p_149668_4_ + this.field_149754_D, p_149668_2_ + this.field_149755_E, p_149668_3_ + this.field_149756_F, p_149668_4_ + this.field_149757_G);
    }
    
    public boolean isOpaqueCube() {
        return true;
    }
    
    public boolean canCollideCheck(final int p_149678_1_, final boolean p_149678_2_) {
        return this.isCollidable();
    }
    
    public boolean isCollidable() {
        return true;
    }
    
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
    }
    
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
    }
    
    public void onBlockDestroyedByPlayer(final World p_149664_1_, final int p_149664_2_, final int p_149664_3_, final int p_149664_4_, final int p_149664_5_) {
    }
    
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
    }
    
    public int func_149738_a(final World p_149738_1_) {
        return 10;
    }
    
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
    }
    
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
    }
    
    public int quantityDropped(final Random p_149745_1_) {
        return 1;
    }
    
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Item.getItemFromBlock(this);
    }
    
    public float getPlayerRelativeBlockHardness(final EntityPlayer p_149737_1_, final World p_149737_2_, final int p_149737_3_, final int p_149737_4_, final int p_149737_5_) {
        final float var6 = this.getBlockHardness(p_149737_2_, p_149737_3_, p_149737_4_, p_149737_5_);
        return (var6 < 0.0f) ? 0.0f : (p_149737_1_.canHarvestBlock(this) ? (p_149737_1_.getCurrentPlayerStrVsBlock(this, true) / var6 / 30.0f) : (p_149737_1_.getCurrentPlayerStrVsBlock(this, false) / var6 / 100.0f));
    }
    
    public final void dropBlockAsItem(final World p_149697_1_, final int p_149697_2_, final int p_149697_3_, final int p_149697_4_, final int p_149697_5_, final int p_149697_6_) {
        this.dropBlockAsItemWithChance(p_149697_1_, p_149697_2_, p_149697_3_, p_149697_4_, p_149697_5_, 1.0f, p_149697_6_);
    }
    
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        if (!p_149690_1_.isClient) {
            for (int var8 = this.quantityDroppedWithBonus(p_149690_7_, p_149690_1_.rand), var9 = 0; var9 < var8; ++var9) {
                if (p_149690_1_.rand.nextFloat() <= p_149690_6_) {
                    final Item var10 = this.getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_);
                    if (var10 != null) {
                        this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var10, 1, this.damageDropped(p_149690_5_)));
                    }
                }
            }
        }
    }
    
    protected void dropBlockAsItem_do(final World p_149642_1_, final int p_149642_2_, final int p_149642_3_, final int p_149642_4_, final ItemStack p_149642_5_) {
        if (!p_149642_1_.isClient && p_149642_1_.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            final float var6 = 0.7f;
            final double var7 = p_149642_1_.rand.nextFloat() * var6 + (1.0f - var6) * 0.5;
            final double var8 = p_149642_1_.rand.nextFloat() * var6 + (1.0f - var6) * 0.5;
            final double var9 = p_149642_1_.rand.nextFloat() * var6 + (1.0f - var6) * 0.5;
            final EntityItem var10 = new EntityItem(p_149642_1_, p_149642_2_ + var7, p_149642_3_ + var8, p_149642_4_ + var9, p_149642_5_);
            var10.delayBeforeCanPickup = 10;
            p_149642_1_.spawnEntityInWorld(var10);
        }
    }
    
    protected void dropXpOnBlockBreak(final World p_149657_1_, final int p_149657_2_, final int p_149657_3_, final int p_149657_4_, int p_149657_5_) {
        if (!p_149657_1_.isClient) {
            while (p_149657_5_ > 0) {
                final int var6 = EntityXPOrb.getXPSplit(p_149657_5_);
                p_149657_5_ -= var6;
                p_149657_1_.spawnEntityInWorld(new EntityXPOrb(p_149657_1_, p_149657_2_ + 0.5, p_149657_3_ + 0.5, p_149657_4_ + 0.5, var6));
            }
        }
    }
    
    public int damageDropped(final int p_149692_1_) {
        return 0;
    }
    
    public float getExplosionResistance(final Entity p_149638_1_) {
        return this.blockResistance / 5.0f;
    }
    
    public MovingObjectPosition collisionRayTrace(final World p_149731_1_, final int p_149731_2_, final int p_149731_3_, final int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_) {
        this.setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
        p_149731_5_ = p_149731_5_.addVector(-p_149731_2_, -p_149731_3_, -p_149731_4_);
        p_149731_6_ = p_149731_6_.addVector(-p_149731_2_, -p_149731_3_, -p_149731_4_);
        Vec3 var7 = p_149731_5_.getIntermediateWithXValue(p_149731_6_, this.field_149759_B);
        Vec3 var8 = p_149731_5_.getIntermediateWithXValue(p_149731_6_, this.field_149755_E);
        Vec3 var9 = p_149731_5_.getIntermediateWithYValue(p_149731_6_, this.field_149760_C);
        Vec3 var10 = p_149731_5_.getIntermediateWithYValue(p_149731_6_, this.field_149756_F);
        Vec3 var11 = p_149731_5_.getIntermediateWithZValue(p_149731_6_, this.field_149754_D);
        Vec3 var12 = p_149731_5_.getIntermediateWithZValue(p_149731_6_, this.field_149757_G);
        if (!this.isVecInsideYZBounds(var7)) {
            var7 = null;
        }
        if (!this.isVecInsideYZBounds(var8)) {
            var8 = null;
        }
        if (!this.isVecInsideXZBounds(var9)) {
            var9 = null;
        }
        if (!this.isVecInsideXZBounds(var10)) {
            var10 = null;
        }
        if (!this.isVecInsideXYBounds(var11)) {
            var11 = null;
        }
        if (!this.isVecInsideXYBounds(var12)) {
            var12 = null;
        }
        Vec3 var13 = null;
        if (var7 != null && (var13 == null || p_149731_5_.squareDistanceTo(var7) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var7;
        }
        if (var8 != null && (var13 == null || p_149731_5_.squareDistanceTo(var8) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var8;
        }
        if (var9 != null && (var13 == null || p_149731_5_.squareDistanceTo(var9) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var9;
        }
        if (var10 != null && (var13 == null || p_149731_5_.squareDistanceTo(var10) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var10;
        }
        if (var11 != null && (var13 == null || p_149731_5_.squareDistanceTo(var11) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var11;
        }
        if (var12 != null && (var13 == null || p_149731_5_.squareDistanceTo(var12) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var12;
        }
        if (var13 == null) {
            return null;
        }
        byte var14 = -1;
        if (var13 == var7) {
            var14 = 4;
        }
        if (var13 == var8) {
            var14 = 5;
        }
        if (var13 == var9) {
            var14 = 0;
        }
        if (var13 == var10) {
            var14 = 1;
        }
        if (var13 == var11) {
            var14 = 2;
        }
        if (var13 == var12) {
            var14 = 3;
        }
        return new MovingObjectPosition(p_149731_2_, p_149731_3_, p_149731_4_, var14, var13.addVector(p_149731_2_, p_149731_3_, p_149731_4_));
    }
    
    private boolean isVecInsideYZBounds(final Vec3 p_149654_1_) {
        return p_149654_1_ != null && (p_149654_1_.yCoord >= this.field_149760_C && p_149654_1_.yCoord <= this.field_149756_F && p_149654_1_.zCoord >= this.field_149754_D && p_149654_1_.zCoord <= this.field_149757_G);
    }
    
    private boolean isVecInsideXZBounds(final Vec3 p_149687_1_) {
        return p_149687_1_ != null && (p_149687_1_.xCoord >= this.field_149759_B && p_149687_1_.xCoord <= this.field_149755_E && p_149687_1_.zCoord >= this.field_149754_D && p_149687_1_.zCoord <= this.field_149757_G);
    }
    
    private boolean isVecInsideXYBounds(final Vec3 p_149661_1_) {
        return p_149661_1_ != null && (p_149661_1_.xCoord >= this.field_149759_B && p_149661_1_.xCoord <= this.field_149755_E && p_149661_1_.yCoord >= this.field_149760_C && p_149661_1_.yCoord <= this.field_149756_F);
    }
    
    public void onBlockDestroyedByExplosion(final World p_149723_1_, final int p_149723_2_, final int p_149723_3_, final int p_149723_4_, final Explosion p_149723_5_) {
    }
    
    public int getRenderBlockPass() {
        return 0;
    }
    
    public boolean canReplace(final World p_149705_1_, final int p_149705_2_, final int p_149705_3_, final int p_149705_4_, final int p_149705_5_, final ItemStack p_149705_6_) {
        return this.canPlaceBlockOnSide(p_149705_1_, p_149705_2_, p_149705_3_, p_149705_4_, p_149705_5_);
    }
    
    public boolean canPlaceBlockOnSide(final World p_149707_1_, final int p_149707_2_, final int p_149707_3_, final int p_149707_4_, final int p_149707_5_) {
        return this.canPlaceBlockAt(p_149707_1_, p_149707_2_, p_149707_3_, p_149707_4_);
    }
    
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_).blockMaterial.isReplaceable();
    }
    
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        return false;
    }
    
    public void onEntityWalking(final World p_149724_1_, final int p_149724_2_, final int p_149724_3_, final int p_149724_4_, final Entity p_149724_5_) {
    }
    
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        return p_149660_9_;
    }
    
    public void onBlockClicked(final World p_149699_1_, final int p_149699_2_, final int p_149699_3_, final int p_149699_4_, final EntityPlayer p_149699_5_) {
    }
    
    public void velocityToAddToEntity(final World p_149640_1_, final int p_149640_2_, final int p_149640_3_, final int p_149640_4_, final Entity p_149640_5_, final Vec3 p_149640_6_) {
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
    }
    
    public final double getBlockBoundsMinX() {
        return this.field_149759_B;
    }
    
    public final double getBlockBoundsMaxX() {
        return this.field_149755_E;
    }
    
    public final double getBlockBoundsMinY() {
        return this.field_149760_C;
    }
    
    public final double getBlockBoundsMaxY() {
        return this.field_149756_F;
    }
    
    public final double getBlockBoundsMinZ() {
        return this.field_149754_D;
    }
    
    public final double getBlockBoundsMaxZ() {
        return this.field_149757_G;
    }
    
    public int getBlockColor() {
        return 16777215;
    }
    
    public int getRenderColor(final int p_149741_1_) {
        return 16777215;
    }
    
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        return 16777215;
    }
    
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        return 0;
    }
    
    public boolean canProvidePower() {
        return false;
    }
    
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
    }
    
    public int isProvidingStrongPower(final IBlockAccess p_149748_1_, final int p_149748_2_, final int p_149748_3_, final int p_149748_4_, final int p_149748_5_) {
        return 0;
    }
    
    public void setBlockBoundsForItemRender() {
    }
    
    public void harvestBlock(final World p_149636_1_, final EntityPlayer p_149636_2_, final int p_149636_3_, final int p_149636_4_, final int p_149636_5_, final int p_149636_6_) {
        p_149636_2_.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
        p_149636_2_.addExhaustion(0.025f);
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(p_149636_2_)) {
            final ItemStack var8 = this.createStackedBlock(p_149636_6_);
            if (var8 != null) {
                this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, var8);
            }
        }
        else {
            final int var9 = EnchantmentHelper.getFortuneModifier(p_149636_2_);
            this.dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, var9);
        }
    }
    
    protected boolean canSilkHarvest() {
        return this.renderAsNormalBlock() && !this.isBlockContainer;
    }
    
    protected ItemStack createStackedBlock(final int p_149644_1_) {
        int var2 = 0;
        final Item var3 = Item.getItemFromBlock(this);
        if (var3 != null && var3.getHasSubtypes()) {
            var2 = p_149644_1_;
        }
        return new ItemStack(var3, 1, var2);
    }
    
    public int quantityDroppedWithBonus(final int p_149679_1_, final Random p_149679_2_) {
        return this.quantityDropped(p_149679_2_);
    }
    
    public boolean canBlockStay(final World p_149718_1_, final int p_149718_2_, final int p_149718_3_, final int p_149718_4_) {
        return true;
    }
    
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
    }
    
    public void onPostBlockPlaced(final World p_149714_1_, final int p_149714_2_, final int p_149714_3_, final int p_149714_4_, final int p_149714_5_) {
    }
    
    public Block setBlockName(final String p_149663_1_) {
        this.unlocalizedNameBlock = p_149663_1_;
        return this;
    }
    
    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }
    
    public String getUnlocalizedName() {
        return "tile." + this.unlocalizedNameBlock;
    }
    
    public boolean onBlockEventReceived(final World p_149696_1_, final int p_149696_2_, final int p_149696_3_, final int p_149696_4_, final int p_149696_5_, final int p_149696_6_) {
        return false;
    }
    
    public boolean getEnableStats() {
        return this.enableStats;
    }
    
    protected Block disableStats() {
        this.enableStats = false;
        return this;
    }
    
    public int getMobilityFlag() {
        return this.blockMaterial.getMaterialMobility();
    }
    
    public float getAmbientOcclusionLightValue() {
        return this.isBlockNormalCube() ? 0.2f : 1.0f;
    }
    
    public void onFallenUpon(final World p_149746_1_, final int p_149746_2_, final int p_149746_3_, final int p_149746_4_, final Entity p_149746_5_, final float p_149746_6_) {
    }
    
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Item.getItemFromBlock(this);
    }
    
    public int getDamageValue(final World p_149643_1_, final int p_149643_2_, final int p_149643_3_, final int p_149643_4_) {
        return this.damageDropped(p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_));
    }
    
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
    }
    
    public CreativeTabs getCreativeTabToDisplayOn() {
        return this.displayOnCreativeTab;
    }
    
    public Block setCreativeTab(final CreativeTabs p_149647_1_) {
        this.displayOnCreativeTab = p_149647_1_;
        return this;
    }
    
    public void onBlockHarvested(final World p_149681_1_, final int p_149681_2_, final int p_149681_3_, final int p_149681_4_, final int p_149681_5_, final EntityPlayer p_149681_6_) {
    }
    
    public void onBlockPreDestroy(final World p_149725_1_, final int p_149725_2_, final int p_149725_3_, final int p_149725_4_, final int p_149725_5_) {
    }
    
    public void fillWithRain(final World p_149639_1_, final int p_149639_2_, final int p_149639_3_, final int p_149639_4_) {
    }
    
    public boolean isFlowerPot() {
        return false;
    }
    
    public boolean func_149698_L() {
        return true;
    }
    
    public boolean canDropFromExplosion(final Explosion p_149659_1_) {
        return true;
    }
    
    public boolean func_149667_c(final Block p_149667_1_) {
        return this == p_149667_1_;
    }
    
    public static boolean isEqualTo(final Block p_149680_0_, final Block p_149680_1_) {
        return p_149680_0_ != null && p_149680_1_ != null && (p_149680_0_ == p_149680_1_ || p_149680_0_.func_149667_c(p_149680_1_));
    }
    
    public boolean hasComparatorInputOverride() {
        return false;
    }
    
    public int getComparatorInputOverride(final World p_149736_1_, final int p_149736_2_, final int p_149736_3_, final int p_149736_4_, final int p_149736_5_) {
        return 0;
    }
    
    protected Block setBlockTextureName(final String p_149658_1_) {
        this.textureName = p_149658_1_;
        return this;
    }
    
    protected String getTextureName() {
        return (this.textureName == null) ? ("MISSING_ICON_BLOCK_" + getIdFromBlock(this) + "_" + this.unlocalizedNameBlock) : this.textureName;
    }
    
    public IIcon func_149735_b(final int p_149735_1_, final int p_149735_2_) {
        return this.getIcon(p_149735_1_, p_149735_2_);
    }
    
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName());
    }
    
    public String getItemIconName() {
        return null;
    }
    
    static {
        blockRegistry = new RegistryNamespacedDefaultedByKey("air");
        soundTypeStone = new SoundType("stone", 1.0f, 1.0f);
        soundTypeWood = new SoundType("wood", 1.0f, 1.0f);
        soundTypeGravel = new SoundType("gravel", 1.0f, 1.0f);
        soundTypeGrass = new SoundType("grass", 1.0f, 1.0f);
        soundTypePiston = new SoundType("stone", 1.0f, 1.0f);
        soundTypeMetal = new SoundType("stone", 1.0f, 1.5f);
        soundTypeGlass = new SoundType("stone", 1.0f, 1.0f) {
            private static final String __OBFID = "CL_00000200";
            
            @Override
            public String func_150495_a() {
                return "dig.glass";
            }
            
            @Override
            public String func_150496_b() {
                return "step.stone";
            }
        };
        soundTypeCloth = new SoundType("cloth", 1.0f, 1.0f);
        field_149776_m = new SoundType("sand", 1.0f, 1.0f);
        soundTypeSnow = new SoundType("snow", 1.0f, 1.0f);
        soundTypeLadder = new SoundType("ladder", 1.0f, 1.0f) {
            private static final String __OBFID = "CL_00000201";
            
            @Override
            public String func_150495_a() {
                return "dig.wood";
            }
        };
        soundTypeAnvil = new SoundType("anvil", 0.3f, 1.0f) {
            private static final String __OBFID = "CL_00000202";
            
            @Override
            public String func_150495_a() {
                return "dig.stone";
            }
            
            @Override
            public String func_150496_b() {
                return "random.anvil_land";
            }
        };
    }
    
    public static class SoundType
    {
        public final String field_150501_a;
        public final float field_150499_b;
        public final float field_150500_c;
        private static final String __OBFID = "CL_00000203";
        
        public SoundType(final String p_i45393_1_, final float p_i45393_2_, final float p_i45393_3_) {
            this.field_150501_a = p_i45393_1_;
            this.field_150499_b = p_i45393_2_;
            this.field_150500_c = p_i45393_3_;
        }
        
        public float func_150497_c() {
            return this.field_150499_b;
        }
        
        public float func_150494_d() {
            return this.field_150500_c;
        }
        
        public String func_150495_a() {
            return "dig." + this.field_150501_a;
        }
        
        public String func_150498_e() {
            return "step." + this.field_150501_a;
        }
        
        public String func_150496_b() {
            return this.func_150495_a();
        }
    }
}
