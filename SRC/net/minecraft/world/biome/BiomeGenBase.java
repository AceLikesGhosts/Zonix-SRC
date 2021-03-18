package net.minecraft.world.biome;

import net.minecraft.world.gen.*;
import net.minecraft.init.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.util.*;

public abstract class BiomeGenBase
{
    private static final Logger logger;
    protected static final Height field_150596_a;
    protected static final Height field_150594_b;
    protected static final Height field_150595_c;
    protected static final Height field_150592_d;
    protected static final Height field_150593_e;
    protected static final Height field_150590_f;
    protected static final Height field_150591_g;
    protected static final Height field_150602_h;
    protected static final Height field_150603_i;
    protected static final Height field_150600_j;
    protected static final Height field_150601_k;
    protected static final Height field_150598_l;
    protected static final Height field_150599_m;
    private static final BiomeGenBase[] biomeList;
    public static final Set field_150597_n;
    public static final BiomeGenBase ocean;
    public static final BiomeGenBase plains;
    public static final BiomeGenBase desert;
    public static final BiomeGenBase extremeHills;
    public static final BiomeGenBase forest;
    public static final BiomeGenBase taiga;
    public static final BiomeGenBase swampland;
    public static final BiomeGenBase river;
    public static final BiomeGenBase hell;
    public static final BiomeGenBase sky;
    public static final BiomeGenBase frozenOcean;
    public static final BiomeGenBase frozenRiver;
    public static final BiomeGenBase icePlains;
    public static final BiomeGenBase iceMountains;
    public static final BiomeGenBase mushroomIsland;
    public static final BiomeGenBase mushroomIslandShore;
    public static final BiomeGenBase beach;
    public static final BiomeGenBase desertHills;
    public static final BiomeGenBase forestHills;
    public static final BiomeGenBase taigaHills;
    public static final BiomeGenBase extremeHillsEdge;
    public static final BiomeGenBase jungle;
    public static final BiomeGenBase jungleHills;
    public static final BiomeGenBase field_150574_L;
    public static final BiomeGenBase field_150575_M;
    public static final BiomeGenBase field_150576_N;
    public static final BiomeGenBase field_150577_O;
    public static final BiomeGenBase field_150583_P;
    public static final BiomeGenBase field_150582_Q;
    public static final BiomeGenBase field_150585_R;
    public static final BiomeGenBase field_150584_S;
    public static final BiomeGenBase field_150579_T;
    public static final BiomeGenBase field_150578_U;
    public static final BiomeGenBase field_150581_V;
    public static final BiomeGenBase field_150580_W;
    public static final BiomeGenBase field_150588_X;
    public static final BiomeGenBase field_150587_Y;
    public static final BiomeGenBase field_150589_Z;
    public static final BiomeGenBase field_150607_aa;
    public static final BiomeGenBase field_150608_ab;
    protected static final NoiseGeneratorPerlin field_150605_ac;
    protected static final NoiseGeneratorPerlin field_150606_ad;
    protected static final WorldGenDoublePlant field_150610_ae;
    public String biomeName;
    public int color;
    public int field_150609_ah;
    public Block topBlock;
    public int field_150604_aj;
    public Block fillerBlock;
    public int field_76754_C;
    public float minHeight;
    public float maxHeight;
    public float temperature;
    public float rainfall;
    public int waterColorMultiplier;
    public BiomeDecorator theBiomeDecorator;
    protected List spawnableMonsterList;
    protected List spawnableCreatureList;
    protected List spawnableWaterCreatureList;
    protected List spawnableCaveCreatureList;
    protected boolean enableSnow;
    protected boolean enableRain;
    public final int biomeID;
    protected WorldGenTrees worldGeneratorTrees;
    protected WorldGenBigTree worldGeneratorBigTree;
    protected WorldGenSwamp worldGeneratorSwamp;
    private static final String __OBFID = "CL_00000158";
    
    protected BiomeGenBase(final int p_i1971_1_) {
        this.topBlock = Blocks.grass;
        this.field_150604_aj = 0;
        this.fillerBlock = Blocks.dirt;
        this.field_76754_C = 5169201;
        this.minHeight = BiomeGenBase.field_150596_a.field_150777_a;
        this.maxHeight = BiomeGenBase.field_150596_a.field_150776_b;
        this.temperature = 0.5f;
        this.rainfall = 0.5f;
        this.waterColorMultiplier = 16777215;
        this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableCaveCreatureList = new ArrayList();
        this.enableRain = true;
        this.worldGeneratorTrees = new WorldGenTrees(false);
        this.worldGeneratorBigTree = new WorldGenBigTree(false);
        this.worldGeneratorSwamp = new WorldGenSwamp();
        this.biomeID = p_i1971_1_;
        BiomeGenBase.biomeList[p_i1971_1_] = this;
        this.theBiomeDecorator = this.createBiomeDecorator();
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 5, 1, 1));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
    }
    
    protected BiomeDecorator createBiomeDecorator() {
        return new BiomeDecorator();
    }
    
    protected BiomeGenBase setTemperatureRainfall(final float p_76732_1_, final float p_76732_2_) {
        if (p_76732_1_ > 0.1f && p_76732_1_ < 0.2f) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        this.temperature = p_76732_1_;
        this.rainfall = p_76732_2_;
        return this;
    }
    
    protected final BiomeGenBase func_150570_a(final Height p_150570_1_) {
        this.minHeight = p_150570_1_.field_150777_a;
        this.maxHeight = p_150570_1_.field_150776_b;
        return this;
    }
    
    protected BiomeGenBase setDisableRain() {
        this.enableRain = false;
        return this;
    }
    
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return (p_150567_1_.nextInt(10) == 0) ? this.worldGeneratorBigTree : this.worldGeneratorTrees;
    }
    
    public WorldGenerator getRandomWorldGenForGrass(final Random p_76730_1_) {
        return new WorldGenTallGrass(Blocks.tallgrass, 1);
    }
    
    public String func_150572_a(final Random p_150572_1_, final int p_150572_2_, final int p_150572_3_, final int p_150572_4_) {
        return (p_150572_1_.nextInt(3) > 0) ? BlockFlower.field_149858_b[0] : BlockFlower.field_149859_a[0];
    }
    
    protected BiomeGenBase setEnableSnow() {
        this.enableSnow = true;
        return this;
    }
    
    protected BiomeGenBase setBiomeName(final String p_76735_1_) {
        this.biomeName = p_76735_1_;
        return this;
    }
    
    protected BiomeGenBase func_76733_a(final int p_76733_1_) {
        this.field_76754_C = p_76733_1_;
        return this;
    }
    
    protected BiomeGenBase setColor(final int p_76739_1_) {
        this.func_150557_a(p_76739_1_, false);
        return this;
    }
    
    protected BiomeGenBase func_150563_c(final int p_150563_1_) {
        this.field_150609_ah = p_150563_1_;
        return this;
    }
    
    protected BiomeGenBase func_150557_a(final int p_150557_1_, final boolean p_150557_2_) {
        this.color = p_150557_1_;
        if (p_150557_2_) {
            this.field_150609_ah = (p_150557_1_ & 0xFEFEFE) >> 1;
        }
        else {
            this.field_150609_ah = p_150557_1_;
        }
        return this;
    }
    
    public int getSkyColorByTemp(float p_76731_1_) {
        p_76731_1_ /= 3.0f;
        if (p_76731_1_ < -1.0f) {
            p_76731_1_ = -1.0f;
        }
        if (p_76731_1_ > 1.0f) {
            p_76731_1_ = 1.0f;
        }
        return Color.getHSBColor(0.62222224f - p_76731_1_ * 0.05f, 0.5f + p_76731_1_ * 0.1f, 1.0f).getRGB();
    }
    
    public List getSpawnableList(final EnumCreatureType p_76747_1_) {
        return (p_76747_1_ == EnumCreatureType.monster) ? this.spawnableMonsterList : ((p_76747_1_ == EnumCreatureType.creature) ? this.spawnableCreatureList : ((p_76747_1_ == EnumCreatureType.waterCreature) ? this.spawnableWaterCreatureList : ((p_76747_1_ == EnumCreatureType.ambient) ? this.spawnableCaveCreatureList : null)));
    }
    
    public boolean getEnableSnow() {
        return this.func_150559_j();
    }
    
    public boolean canSpawnLightningBolt() {
        return !this.func_150559_j() && this.enableRain;
    }
    
    public boolean isHighHumidity() {
        return this.rainfall > 0.85f;
    }
    
    public float getSpawningChance() {
        return 0.1f;
    }
    
    public final int getIntRainfall() {
        return (int)(this.rainfall * 65536.0f);
    }
    
    public final float getFloatRainfall() {
        return this.rainfall;
    }
    
    public final float getFloatTemperature(final int p_150564_1_, final int p_150564_2_, final int p_150564_3_) {
        if (p_150564_2_ > 64) {
            final float var4 = (float)BiomeGenBase.field_150605_ac.func_151601_a(p_150564_1_ * 1.0 / 8.0, p_150564_3_ * 1.0 / 8.0) * 4.0f;
            return this.temperature - (var4 + p_150564_2_ - 64.0f) * 0.05f / 30.0f;
        }
        return this.temperature;
    }
    
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        this.theBiomeDecorator.func_150512_a(p_76728_1_, p_76728_2_, this, p_76728_3_, p_76728_4_);
    }
    
    public int getBiomeGrassColor(final int p_150558_1_, final int p_150558_2_, final int p_150558_3_) {
        final double var4 = MathHelper.clamp_float(this.getFloatTemperature(p_150558_1_, p_150558_2_, p_150558_3_), 0.0f, 1.0f);
        final double var5 = MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f);
        return ColorizerGrass.getGrassColor(var4, var5);
    }
    
    public int getBiomeFoliageColor(final int p_150571_1_, final int p_150571_2_, final int p_150571_3_) {
        final double var4 = MathHelper.clamp_float(this.getFloatTemperature(p_150571_1_, p_150571_2_, p_150571_3_), 0.0f, 1.0f);
        final double var5 = MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f);
        return ColorizerFoliage.getFoliageColor(var4, var5);
    }
    
    public boolean func_150559_j() {
        return this.enableSnow;
    }
    
    public void func_150573_a(final World p_150573_1_, final Random p_150573_2_, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_) {
        this.func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }
    
    public final void func_150560_b(final World p_150560_1_, final Random p_150560_2_, final Block[] p_150560_3_, final byte[] p_150560_4_, final int p_150560_5_, final int p_150560_6_, final double p_150560_7_) {
        final boolean var9 = true;
        Block var10 = this.topBlock;
        byte var11 = (byte)(this.field_150604_aj & 0xFF);
        Block var12 = this.fillerBlock;
        int var13 = -1;
        final int var14 = (int)(p_150560_7_ / 3.0 + 3.0 + p_150560_2_.nextDouble() * 0.25);
        final int var15 = p_150560_5_ & 0xF;
        final int var16 = p_150560_6_ & 0xF;
        final int var17 = p_150560_3_.length / 256;
        for (int var18 = 255; var18 >= 0; --var18) {
            final int var19 = (var16 * 16 + var15) * var17 + var18;
            if (var18 <= 0 + p_150560_2_.nextInt(5)) {
                p_150560_3_[var19] = Blocks.bedrock;
            }
            else {
                final Block var20 = p_150560_3_[var19];
                if (var20 != null && var20.getMaterial() != Material.air) {
                    if (var20 == Blocks.stone) {
                        if (var13 == -1) {
                            if (var14 <= 0) {
                                var10 = null;
                                var11 = 0;
                                var12 = Blocks.stone;
                            }
                            else if (var18 >= 59 && var18 <= 64) {
                                var10 = this.topBlock;
                                var11 = (byte)(this.field_150604_aj & 0xFF);
                                var12 = this.fillerBlock;
                            }
                            if (var18 < 63 && (var10 == null || var10.getMaterial() == Material.air)) {
                                if (this.getFloatTemperature(p_150560_5_, var18, p_150560_6_) < 0.15f) {
                                    var10 = Blocks.ice;
                                    var11 = 0;
                                }
                                else {
                                    var10 = Blocks.water;
                                    var11 = 0;
                                }
                            }
                            var13 = var14;
                            if (var18 >= 62) {
                                p_150560_3_[var19] = var10;
                                p_150560_4_[var19] = var11;
                            }
                            else if (var18 < 56 - var14) {
                                var10 = null;
                                var12 = Blocks.stone;
                                p_150560_3_[var19] = Blocks.gravel;
                            }
                            else {
                                p_150560_3_[var19] = var12;
                            }
                        }
                        else if (var13 > 0) {
                            --var13;
                            p_150560_3_[var19] = var12;
                            if (var13 == 0 && var12 == Blocks.sand) {
                                var13 = p_150560_2_.nextInt(4) + Math.max(0, var18 - 63);
                                var12 = Blocks.sandstone;
                            }
                        }
                    }
                }
                else {
                    var13 = -1;
                }
            }
        }
    }
    
    protected BiomeGenBase func_150566_k() {
        return new BiomeGenMutated(this.biomeID + 128, this);
    }
    
    public Class func_150562_l() {
        return this.getClass();
    }
    
    public boolean func_150569_a(final BiomeGenBase p_150569_1_) {
        return p_150569_1_ == this || (p_150569_1_ != null && this.func_150562_l() == p_150569_1_.func_150562_l());
    }
    
    public TempCategory func_150561_m() {
        return (this.temperature < 0.2) ? TempCategory.COLD : ((this.temperature < 1.0) ? TempCategory.MEDIUM : TempCategory.WARM);
    }
    
    public static BiomeGenBase[] getBiomeGenArray() {
        return BiomeGenBase.biomeList;
    }
    
    public static BiomeGenBase func_150568_d(final int p_150568_0_) {
        if (p_150568_0_ >= 0 && p_150568_0_ <= BiomeGenBase.biomeList.length) {
            return BiomeGenBase.biomeList[p_150568_0_];
        }
        BiomeGenBase.logger.warn("Biome ID is out of bounds: " + p_150568_0_ + ", defaulting to 0 (Ocean)");
        return BiomeGenBase.ocean;
    }
    
    static {
        logger = LogManager.getLogger();
        field_150596_a = new Height(0.1f, 0.2f);
        field_150594_b = new Height(-0.5f, 0.0f);
        field_150595_c = new Height(-1.0f, 0.1f);
        field_150592_d = new Height(-1.8f, 0.1f);
        field_150593_e = new Height(0.125f, 0.05f);
        field_150590_f = new Height(0.2f, 0.2f);
        field_150591_g = new Height(0.45f, 0.3f);
        field_150602_h = new Height(1.5f, 0.025f);
        field_150603_i = new Height(1.0f, 0.5f);
        field_150600_j = new Height(0.0f, 0.025f);
        field_150601_k = new Height(0.1f, 0.8f);
        field_150598_l = new Height(0.2f, 0.3f);
        field_150599_m = new Height(-0.2f, 0.1f);
        biomeList = new BiomeGenBase[256];
        field_150597_n = Sets.newHashSet();
        ocean = new BiomeGenOcean(0).setColor(112).setBiomeName("Ocean").func_150570_a(BiomeGenBase.field_150595_c);
        plains = new BiomeGenPlains(1).setColor(9286496).setBiomeName("Plains");
        desert = new BiomeGenDesert(2).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).func_150570_a(BiomeGenBase.field_150593_e);
        extremeHills = new BiomeGenHills(3, false).setColor(6316128).setBiomeName("Extreme Hills").func_150570_a(BiomeGenBase.field_150603_i).setTemperatureRainfall(0.2f, 0.3f);
        forest = new BiomeGenForest(4, 0).setColor(353825).setBiomeName("Forest");
        taiga = new BiomeGenTaiga(5, 0).setColor(747097).setBiomeName("Taiga").func_76733_a(5159473).setTemperatureRainfall(0.25f, 0.8f).func_150570_a(BiomeGenBase.field_150590_f);
        swampland = new BiomeGenSwamp(6).setColor(522674).setBiomeName("Swampland").func_76733_a(9154376).func_150570_a(BiomeGenBase.field_150599_m).setTemperatureRainfall(0.8f, 0.9f);
        river = new BiomeGenRiver(7).setColor(255).setBiomeName("River").func_150570_a(BiomeGenBase.field_150594_b);
        hell = new BiomeGenHell(8).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0f, 0.0f);
        sky = new BiomeGenEnd(9).setColor(8421631).setBiomeName("Sky").setDisableRain();
        frozenOcean = new BiomeGenOcean(10).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().func_150570_a(BiomeGenBase.field_150595_c).setTemperatureRainfall(0.0f, 0.5f);
        frozenRiver = new BiomeGenRiver(11).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().func_150570_a(BiomeGenBase.field_150594_b).setTemperatureRainfall(0.0f, 0.5f);
        icePlains = new BiomeGenSnow(12, false).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).func_150570_a(BiomeGenBase.field_150593_e);
        iceMountains = new BiomeGenSnow(13, false).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().func_150570_a(BiomeGenBase.field_150591_g).setTemperatureRainfall(0.0f, 0.5f);
        mushroomIsland = new BiomeGenMushroomIsland(14).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9f, 1.0f).func_150570_a(BiomeGenBase.field_150598_l);
        mushroomIslandShore = new BiomeGenMushroomIsland(15).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9f, 1.0f).func_150570_a(BiomeGenBase.field_150600_j);
        beach = new BiomeGenBeach(16).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8f, 0.4f).func_150570_a(BiomeGenBase.field_150600_j);
        desertHills = new BiomeGenDesert(17).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).func_150570_a(BiomeGenBase.field_150591_g);
        forestHills = new BiomeGenForest(18, 0).setColor(2250012).setBiomeName("ForestHills").func_150570_a(BiomeGenBase.field_150591_g);
        taigaHills = new BiomeGenTaiga(19, 0).setColor(1456435).setBiomeName("TaigaHills").func_76733_a(5159473).setTemperatureRainfall(0.25f, 0.8f).func_150570_a(BiomeGenBase.field_150591_g);
        extremeHillsEdge = new BiomeGenHills(20, true).setColor(7501978).setBiomeName("Extreme Hills Edge").func_150570_a(BiomeGenBase.field_150603_i.func_150775_a()).setTemperatureRainfall(0.2f, 0.3f);
        jungle = new BiomeGenJungle(21, false).setColor(5470985).setBiomeName("Jungle").func_76733_a(5470985).setTemperatureRainfall(0.95f, 0.9f);
        jungleHills = new BiomeGenJungle(22, false).setColor(2900485).setBiomeName("JungleHills").func_76733_a(5470985).setTemperatureRainfall(0.95f, 0.9f).func_150570_a(BiomeGenBase.field_150591_g);
        field_150574_L = new BiomeGenJungle(23, true).setColor(6458135).setBiomeName("JungleEdge").func_76733_a(5470985).setTemperatureRainfall(0.95f, 0.8f);
        field_150575_M = new BiomeGenOcean(24).setColor(48).setBiomeName("Deep Ocean").func_150570_a(BiomeGenBase.field_150592_d);
        field_150576_N = new BiomeGenStoneBeach(25).setColor(10658436).setBiomeName("Stone Beach").setTemperatureRainfall(0.2f, 0.3f).func_150570_a(BiomeGenBase.field_150601_k);
        field_150577_O = new BiomeGenBeach(26).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05f, 0.3f).func_150570_a(BiomeGenBase.field_150600_j).setEnableSnow();
        field_150583_P = new BiomeGenForest(27, 2).setBiomeName("Birch Forest").setColor(3175492);
        field_150582_Q = new BiomeGenForest(28, 2).setBiomeName("Birch Forest Hills").setColor(2055986).func_150570_a(BiomeGenBase.field_150591_g);
        field_150585_R = new BiomeGenForest(29, 3).setColor(4215066).setBiomeName("Roofed Forest");
        field_150584_S = new BiomeGenTaiga(30, 0).setColor(3233098).setBiomeName("Cold Taiga").func_76733_a(5159473).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).func_150570_a(BiomeGenBase.field_150590_f).func_150563_c(16777215);
        field_150579_T = new BiomeGenTaiga(31, 0).setColor(2375478).setBiomeName("Cold Taiga Hills").func_76733_a(5159473).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).func_150570_a(BiomeGenBase.field_150591_g).func_150563_c(16777215);
        field_150578_U = new BiomeGenTaiga(32, 1).setColor(5858897).setBiomeName("Mega Taiga").func_76733_a(5159473).setTemperatureRainfall(0.3f, 0.8f).func_150570_a(BiomeGenBase.field_150590_f);
        field_150581_V = new BiomeGenTaiga(33, 1).setColor(4542270).setBiomeName("Mega Taiga Hills").func_76733_a(5159473).setTemperatureRainfall(0.3f, 0.8f).func_150570_a(BiomeGenBase.field_150591_g);
        field_150580_W = new BiomeGenHills(34, true).setColor(5271632).setBiomeName("Extreme Hills+").func_150570_a(BiomeGenBase.field_150603_i).setTemperatureRainfall(0.2f, 0.3f);
        field_150588_X = new BiomeGenSavanna(35).setColor(12431967).setBiomeName("Savanna").setTemperatureRainfall(1.2f, 0.0f).setDisableRain().func_150570_a(BiomeGenBase.field_150593_e);
        field_150587_Y = new BiomeGenSavanna(36).setColor(10984804).setBiomeName("Savanna Plateau").setTemperatureRainfall(1.0f, 0.0f).setDisableRain().func_150570_a(BiomeGenBase.field_150602_h);
        field_150589_Z = new BiomeGenMesa(37, false, false).setColor(14238997).setBiomeName("Mesa");
        field_150607_aa = new BiomeGenMesa(38, false, true).setColor(11573093).setBiomeName("Mesa Plateau F").func_150570_a(BiomeGenBase.field_150602_h);
        field_150608_ab = new BiomeGenMesa(39, false, false).setColor(13274213).setBiomeName("Mesa Plateau").func_150570_a(BiomeGenBase.field_150602_h);
        BiomeGenBase.plains.func_150566_k();
        BiomeGenBase.desert.func_150566_k();
        BiomeGenBase.forest.func_150566_k();
        BiomeGenBase.taiga.func_150566_k();
        BiomeGenBase.swampland.func_150566_k();
        BiomeGenBase.icePlains.func_150566_k();
        BiomeGenBase.jungle.func_150566_k();
        BiomeGenBase.field_150574_L.func_150566_k();
        BiomeGenBase.field_150584_S.func_150566_k();
        BiomeGenBase.field_150588_X.func_150566_k();
        BiomeGenBase.field_150587_Y.func_150566_k();
        BiomeGenBase.field_150589_Z.func_150566_k();
        BiomeGenBase.field_150607_aa.func_150566_k();
        BiomeGenBase.field_150608_ab.func_150566_k();
        BiomeGenBase.field_150583_P.func_150566_k();
        BiomeGenBase.field_150582_Q.func_150566_k();
        BiomeGenBase.field_150585_R.func_150566_k();
        BiomeGenBase.field_150578_U.func_150566_k();
        BiomeGenBase.extremeHills.func_150566_k();
        BiomeGenBase.field_150580_W.func_150566_k();
        BiomeGenBase.biomeList[BiomeGenBase.field_150581_V.biomeID + 128] = BiomeGenBase.biomeList[BiomeGenBase.field_150578_U.biomeID + 128];
        for (final BiomeGenBase var4 : BiomeGenBase.biomeList) {
            if (var4 != null && var4.biomeID < 128) {
                BiomeGenBase.field_150597_n.add(var4);
            }
        }
        BiomeGenBase.field_150597_n.remove(BiomeGenBase.hell);
        BiomeGenBase.field_150597_n.remove(BiomeGenBase.sky);
        BiomeGenBase.field_150597_n.remove(BiomeGenBase.frozenOcean);
        BiomeGenBase.field_150597_n.remove(BiomeGenBase.extremeHillsEdge);
        field_150605_ac = new NoiseGeneratorPerlin(new Random(1234L), 1);
        field_150606_ad = new NoiseGeneratorPerlin(new Random(2345L), 1);
        field_150610_ae = new WorldGenDoublePlant();
    }
    
    public static class Height
    {
        public float field_150777_a;
        public float field_150776_b;
        private static final String __OBFID = "CL_00000159";
        
        public Height(final float p_i45371_1_, final float p_i45371_2_) {
            this.field_150777_a = p_i45371_1_;
            this.field_150776_b = p_i45371_2_;
        }
        
        public Height func_150775_a() {
            return new Height(this.field_150777_a * 0.8f, this.field_150776_b * 0.6f);
        }
    }
    
    public static class SpawnListEntry extends WeightedRandom.Item
    {
        public Class entityClass;
        public int minGroupCount;
        public int maxGroupCount;
        private static final String __OBFID = "CL_00000161";
        
        public SpawnListEntry(final Class p_i1970_1_, final int p_i1970_2_, final int p_i1970_3_, final int p_i1970_4_) {
            super(p_i1970_2_);
            this.entityClass = p_i1970_1_;
            this.minGroupCount = p_i1970_3_;
            this.maxGroupCount = p_i1970_4_;
        }
        
        @Override
        public String toString() {
            return this.entityClass.getSimpleName() + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
        }
    }
    
    public enum TempCategory
    {
        OCEAN("OCEAN", 0), 
        COLD("COLD", 1), 
        MEDIUM("MEDIUM", 2), 
        WARM("WARM", 3);
        
        private static final TempCategory[] $VALUES;
        private static final String __OBFID = "CL_00000160";
        
        private TempCategory(final String p_i45372_1_, final int p_i45372_2_) {
        }
        
        static {
            $VALUES = new TempCategory[] { TempCategory.OCEAN, TempCategory.COLD, TempCategory.MEDIUM, TempCategory.WARM };
        }
    }
}
