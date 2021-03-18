package net.minecraft.src;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import java.io.*;
import java.util.zip.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import java.util.*;

public class CustomItems
{
    private static CustomItemProperties[][] itemProperties;
    private static Map mapPotionIds;
    
    public static void updateIcons(final TextureMap textureMap) {
        CustomItems.itemProperties = null;
        if (Config.isCustomItems()) {
            final IResourcePack[] rps = Config.getResourcePacks();
            for (int i = rps.length - 1; i >= 0; --i) {
                final IResourcePack rp = rps[i];
                updateIcons(textureMap, rp);
            }
            updateIcons(textureMap, Config.getDefaultResourcePack());
        }
    }
    
    public static void updateIcons(final TextureMap textureMap, final IResourcePack rp) {
        String[] names = collectFiles(rp, "mcpatcher/cit/", ".properties");
        final Map mapAutoProperties = makeAutoImageProperties(rp);
        if (mapAutoProperties.size() > 0) {
            final Set itemList = mapAutoProperties.keySet();
            final String[] i = itemList.toArray(new String[itemList.size()]);
            names = (String[])Config.addObjectsToArray(names, i);
        }
        Arrays.sort(names);
        final List var13 = makePropertyList(CustomItems.itemProperties);
        for (int var14 = 0; var14 < names.length; ++var14) {
            final String name = names[var14];
            Config.dbg("CustomItems: " + name);
            try {
                CustomItemProperties e = null;
                if (mapAutoProperties.containsKey(name)) {
                    e = mapAutoProperties.get(name);
                }
                if (e == null) {
                    final ResourceLocation locFile = new ResourceLocation(name);
                    final InputStream in = rp.getInputStream(locFile);
                    if (in == null) {
                        Config.warn("CustomItems file not found: " + name);
                        continue;
                    }
                    final Properties props = new Properties();
                    props.load(in);
                    e = new CustomItemProperties(props, name);
                }
                if (e.isValid(name)) {
                    e.updateIcons(textureMap);
                    addToItemList(e, var13);
                }
            }
            catch (FileNotFoundException var16) {
                Config.warn("CustomItems file not found: " + name);
            }
            catch (IOException var15) {
                var15.printStackTrace();
            }
        }
        CustomItems.itemProperties = propertyListToArray(var13);
    }
    
    private static Map makeAutoImageProperties(final IResourcePack rp) {
        final HashMap map = new HashMap();
        map.putAll(makePotionImageProperties(rp, false));
        map.putAll(makePotionImageProperties(rp, true));
        return map;
    }
    
    private static Map makePotionImageProperties(final IResourcePack rp, final boolean splash) {
        final HashMap map = new HashMap();
        String prefix = "mcpatcher/cit/potion/";
        if (splash) {
            prefix += "splash/";
        }
        else {
            prefix += "normal/";
        }
        final String suffix = ".png";
        final String[] names = collectFiles(rp, prefix, suffix);
        for (int i = 0; i < names.length; ++i) {
            final String path = names[i];
            if (path.startsWith(prefix) && path.endsWith(suffix)) {
                final String name = path.substring(prefix.length(), path.length() - suffix.length());
                final Properties props = makePotionProperties(name, splash, path);
                if (props != null) {
                    final String pathProp = path.substring(0, path.length() - suffix.length()) + ".properties";
                    final CustomItemProperties cip = new CustomItemProperties(props, pathProp);
                    map.put(pathProp, cip);
                }
            }
            else {
                Config.warn("Invalid potion name: " + path);
            }
        }
        return map;
    }
    
    private static Properties makePotionProperties(final String name, final boolean splash, final String path) {
        if (name.equals("empty") && !splash) {
            final int potionItemId = Item.getIdFromItem(Items.glass_bottle);
            final Properties var8 = new Properties();
            ((Hashtable<String, String>)var8).put("type", "item");
            ((Hashtable<String, String>)var8).put("items", "" + potionItemId);
            return var8;
        }
        final int potionItemId = Item.getIdFromItem(Items.potionitem);
        final int[] damages = getMapPotionIds().get(name);
        if (damages == null) {
            Config.warn("Potion not found for image: " + path);
            return null;
        }
        final StringBuffer bufDamage = new StringBuffer();
        for (int damageMask = 0; damageMask < damages.length; ++damageMask) {
            int props = damages[damageMask];
            if (splash) {
                props |= 0x4000;
            }
            if (damageMask > 0) {
                bufDamage.append(" ");
            }
            bufDamage.append(props);
        }
        final short var9 = 16447;
        final Properties var10 = new Properties();
        ((Hashtable<String, String>)var10).put("type", "item");
        ((Hashtable<String, String>)var10).put("items", "" + potionItemId);
        ((Hashtable<String, String>)var10).put("damage", "" + bufDamage.toString());
        ((Hashtable<String, String>)var10).put("damageMask", "" + var9);
        return var10;
    }
    
    private static Map getMapPotionIds() {
        if (CustomItems.mapPotionIds == null) {
            (CustomItems.mapPotionIds = new LinkedHashMap()).put("water", new int[] { 0 });
            CustomItems.mapPotionIds.put("awkward", new int[] { 16 });
            CustomItems.mapPotionIds.put("thick", new int[] { 32 });
            CustomItems.mapPotionIds.put("potent", new int[] { 48 });
            CustomItems.mapPotionIds.put("regeneration", getPotionIds(1));
            CustomItems.mapPotionIds.put("moveSpeed", getPotionIds(2));
            CustomItems.mapPotionIds.put("fireResistance", getPotionIds(3));
            CustomItems.mapPotionIds.put("poison", getPotionIds(4));
            CustomItems.mapPotionIds.put("heal", getPotionIds(5));
            CustomItems.mapPotionIds.put("nightVision", getPotionIds(6));
            CustomItems.mapPotionIds.put("clear", getPotionIds(7));
            CustomItems.mapPotionIds.put("bungling", getPotionIds(23));
            CustomItems.mapPotionIds.put("charming", getPotionIds(39));
            CustomItems.mapPotionIds.put("rank", getPotionIds(55));
            CustomItems.mapPotionIds.put("weakness", getPotionIds(8));
            CustomItems.mapPotionIds.put("damageBoost", getPotionIds(9));
            CustomItems.mapPotionIds.put("moveSlowdown", getPotionIds(10));
            CustomItems.mapPotionIds.put("diffuse", getPotionIds(11));
            CustomItems.mapPotionIds.put("smooth", getPotionIds(27));
            CustomItems.mapPotionIds.put("refined", getPotionIds(43));
            CustomItems.mapPotionIds.put("acrid", getPotionIds(59));
            CustomItems.mapPotionIds.put("harm", getPotionIds(12));
            CustomItems.mapPotionIds.put("waterBreathing", getPotionIds(13));
            CustomItems.mapPotionIds.put("invisibility", getPotionIds(14));
            CustomItems.mapPotionIds.put("thin", getPotionIds(15));
            CustomItems.mapPotionIds.put("debonair", getPotionIds(31));
            CustomItems.mapPotionIds.put("sparkling", getPotionIds(47));
            CustomItems.mapPotionIds.put("stinky", getPotionIds(63));
        }
        return CustomItems.mapPotionIds;
    }
    
    private static int[] getPotionIds(final int baseId) {
        return new int[] { baseId, baseId + 16, baseId + 32, baseId + 48 };
    }
    
    private static int getPotionNameDamage(final String name) {
        final String fullName = "potion." + name;
        final Potion[] effectPotions = Potion.potionTypes;
        for (int i = 0; i < effectPotions.length; ++i) {
            final Potion potion = effectPotions[i];
            if (potion != null) {
                final String potionName = potion.getName();
                if (fullName.equals(potionName)) {
                    return potion.getId();
                }
            }
        }
        return -1;
    }
    
    private static List makePropertyList(final CustomItemProperties[][] propsArr) {
        final ArrayList list = new ArrayList();
        if (propsArr != null) {
            for (int i = 0; i < propsArr.length; ++i) {
                final CustomItemProperties[] props = propsArr[i];
                ArrayList propList = null;
                if (props != null) {
                    propList = new ArrayList((Collection<? extends E>)Arrays.asList(props));
                }
                list.add(propList);
            }
        }
        return list;
    }
    
    private static String[] collectFiles(final IResourcePack rp, final String prefix, final String suffix) {
        if (rp instanceof DefaultResourcePack) {
            return collectFilesDefault(rp);
        }
        if (!(rp instanceof AbstractResourcePack)) {
            return new String[0];
        }
        final AbstractResourcePack arp = (AbstractResourcePack)rp;
        final File tpFile = ResourceUtils.getResourcePackFile(arp);
        return (tpFile == null) ? new String[0] : (tpFile.isDirectory() ? collectFilesFolder(tpFile, "", prefix, suffix) : (tpFile.isFile() ? collectFilesZIP(tpFile, prefix, suffix) : new String[0]));
    }
    
    private static String[] collectFilesDefault(final IResourcePack rp) {
        return new String[0];
    }
    
    private static String[] collectFilesFolder(final File tpFile, final String basePath, final String prefix, final String suffix) {
        final ArrayList list = new ArrayList();
        final String prefixAssets = "assets/minecraft/";
        final File[] files = tpFile.listFiles();
        if (files == null) {
            return new String[0];
        }
        for (int names = 0; names < files.length; ++names) {
            final File file = files[names];
            if (file.isFile()) {
                String dirPath = basePath + file.getName();
                if (dirPath.startsWith(prefixAssets)) {
                    dirPath = dirPath.substring(prefixAssets.length());
                    if (dirPath.startsWith(prefix) && dirPath.endsWith(suffix)) {
                        list.add(dirPath);
                    }
                }
            }
            else if (file.isDirectory()) {
                final String dirPath = basePath + file.getName() + "/";
                final String[] names2 = collectFilesFolder(file, dirPath, prefix, suffix);
                for (int n = 0; n < names2.length; ++n) {
                    final String name = names2[n];
                    list.add(name);
                }
            }
        }
        final String[] var13 = list.toArray(new String[list.size()]);
        return var13;
    }
    
    private static String[] collectFilesZIP(final File tpFile, final String prefix, final String suffix) {
        final ArrayList list = new ArrayList();
        final String prefixAssets = "assets/minecraft/";
        try {
            final ZipFile e = new ZipFile(tpFile);
            final Enumeration en = e.entries();
            while (en.hasMoreElements()) {
                final ZipEntry names = en.nextElement();
                String name = names.getName();
                if (name.startsWith(prefixAssets)) {
                    name = name.substring(prefixAssets.length());
                    if (!name.startsWith(prefix) || !name.endsWith(suffix)) {
                        continue;
                    }
                    list.add(name);
                }
            }
            e.close();
            final String[] names2 = list.toArray(new String[list.size()]);
            return names2;
        }
        catch (IOException var9) {
            var9.printStackTrace();
            return new String[0];
        }
    }
    
    private static CustomItemProperties[][] propertyListToArray(final List list) {
        final CustomItemProperties[][] propArr = new CustomItemProperties[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            final List subList = list.get(i);
            if (subList != null) {
                final CustomItemProperties[] subArr = subList.toArray(new CustomItemProperties[subList.size()]);
                Arrays.sort(subArr, new CustomItemsComparator());
                propArr[i] = subArr;
            }
        }
        return propArr;
    }
    
    private static void addToItemList(final CustomItemProperties cp, final List itemList) {
        if (cp.items != null) {
            for (int i = 0; i < cp.items.length; ++i) {
                final int itemId = cp.items[i];
                if (itemId <= 0) {
                    Config.warn("Invalid item ID: " + itemId);
                }
                else {
                    addToList(cp, itemList, itemId);
                }
            }
        }
    }
    
    private static void addToList(final CustomItemProperties cp, final List list, final int id) {
        while (id >= list.size()) {
            list.add(null);
        }
        Object subList = list.get(id);
        if (subList == null) {
            subList = new ArrayList();
            list.set(id, subList);
        }
        ((List)subList).add(cp);
    }
    
    public static IIcon getCustomItemTexture(final ItemStack itemStack, final IIcon icon) {
        if (CustomItems.itemProperties == null) {
            return icon;
        }
        if (itemStack == null) {
            return icon;
        }
        final Item item = itemStack.getItem();
        final int itemId = Item.getIdFromItem(item);
        if (itemId >= 0 && itemId < CustomItems.itemProperties.length) {
            final CustomItemProperties[] cips = CustomItems.itemProperties[itemId];
            if (cips != null) {
                for (int i = 0; i < cips.length; ++i) {
                    final CustomItemProperties cip = cips[i];
                    final IIcon iconNew = getCustomItemTexture(cip, itemStack, icon);
                    if (iconNew != null) {
                        return iconNew;
                    }
                }
            }
        }
        return icon;
    }
    
    public static IIcon getCustomPotionTexture(final ItemPotion item, final int damage) {
        if (CustomItems.itemProperties == null) {
            return null;
        }
        final int itemId = Item.getIdFromItem(item);
        if (itemId >= 0 && itemId < CustomItems.itemProperties.length) {
            final CustomItemProperties[] cips = CustomItems.itemProperties[itemId];
            if (cips != null) {
                for (int i = 0; i < cips.length; ++i) {
                    final CustomItemProperties cip = cips[i];
                    final IIcon iconNew = getCustomPotionTexture(cip, item, damage);
                    if (iconNew != null) {
                        return iconNew;
                    }
                }
            }
        }
        return null;
    }
    
    private static IIcon getCustomPotionTexture(final CustomItemProperties cip, final ItemPotion item, int damage) {
        if (cip.damage != null) {
            if (cip.damageMask != 0) {
                damage &= cip.damageMask;
            }
            if (!cip.damage.isInRange(damage)) {
                return null;
            }
        }
        return cip.textureIcon;
    }
    
    private static IIcon getCustomItemTexture(final CustomItemProperties cip, final ItemStack itemStack, final IIcon icon) {
        final Item item = itemStack.getItem();
        if (cip.damage != null) {
            int levels = itemStack.getItemDamage();
            if (cip.damageMask != 0) {
                levels &= cip.damageMask;
            }
            final int levelMatch = item.getMaxDamage();
            if (cip.damagePercent) {
                levels = (int)(levels * 100 / (double)levelMatch);
            }
            if (!cip.damage.isInRange(levels)) {
                return null;
            }
        }
        if (cip.stackSize != null && !cip.stackSize.isInRange(itemStack.stackSize)) {
            return null;
        }
        if (cip.enchantmentIds != null) {
            final int[] var8 = getEnchantmentIds(itemStack);
            boolean var9 = false;
            for (int i = 0; i < var8.length; ++i) {
                final int level = var8[i];
                if (cip.enchantmentIds.isInRange(level)) {
                    var9 = true;
                    break;
                }
            }
            if (!var9) {
                return null;
            }
        }
        if (cip.enchantmentLevels != null) {
            final int[] var8 = getEnchantmentLevels(itemStack);
            boolean var9 = false;
            for (int i = 0; i < var8.length; ++i) {
                final int level = var8[i];
                if (cip.enchantmentLevels.isInRange(level)) {
                    var9 = true;
                    break;
                }
            }
            if (!var9) {
                return null;
            }
        }
        if (cip.nbtTagValues != null) {}
        return cip.textureIcon;
    }
    
    private static int[] getEnchantmentIds(final ItemStack itemStack) {
        final Map map = EnchantmentHelper.getEnchantments(itemStack);
        final Set keySet = map.keySet();
        final int[] ids = new int[keySet.size()];
        int index = 0;
        for (final Integer id : keySet) {
            ids[index] = id;
            ++index;
        }
        return ids;
    }
    
    private static int[] getEnchantmentLevels(final ItemStack itemStack) {
        final Map map = EnchantmentHelper.getEnchantments(itemStack);
        final Collection values = map.values();
        final int[] levels = new int[values.size()];
        int index = 0;
        for (final Integer level : values) {
            levels[index] = level;
            ++index;
        }
        return levels;
    }
    
    public static ResourceLocation getLocationItemGlint(final ItemStack par2ItemStack, final ResourceLocation resItemGlint) {
        return resItemGlint;
    }
    
    static {
        CustomItems.itemProperties = null;
        CustomItems.mapPotionIds = null;
    }
}
