package net.minecraft.nbt;

import org.apache.logging.log4j.*;
import java.util.*;

public class JsonToNBT
{
    private static final Logger logger;
    private static final String __OBFID = "CL_00001232";
    
    public static NBTBase func_150315_a(String p_150315_0_) throws NBTException {
        p_150315_0_ = p_150315_0_.trim();
        final int var1 = func_150310_b(p_150315_0_);
        if (var1 != 1) {
            throw new NBTException("Encountered multiple top tags, only one expected");
        }
        Any var2 = null;
        if (p_150315_0_.startsWith("{")) {
            var2 = func_150316_a("tag", p_150315_0_);
        }
        else {
            var2 = func_150316_a(func_150313_b(p_150315_0_, false), func_150311_c(p_150315_0_, false));
        }
        return var2.func_150489_a();
    }
    
    static int func_150310_b(final String p_150310_0_) throws NBTException {
        int var1 = 0;
        boolean var2 = false;
        final Stack var3 = new Stack();
        for (int var4 = 0; var4 < p_150310_0_.length(); ++var4) {
            final char var5 = p_150310_0_.charAt(var4);
            if (var5 == '\"') {
                if (var4 > 0 && p_150310_0_.charAt(var4 - 1) == '\\') {
                    if (!var2) {
                        throw new NBTException("Illegal use of \\\": " + p_150310_0_);
                    }
                }
                else {
                    var2 = !var2;
                }
            }
            else if (!var2) {
                if (var5 != '{' && var5 != '[') {
                    if (var5 == '}' && (var3.isEmpty() || var3.pop() != '{')) {
                        throw new NBTException("Unbalanced curly brackets {}: " + p_150310_0_);
                    }
                    if (var5 == ']' && (var3.isEmpty() || var3.pop() != '[')) {
                        throw new NBTException("Unbalanced square brackets []: " + p_150310_0_);
                    }
                }
                else {
                    if (var3.isEmpty()) {
                        ++var1;
                    }
                    var3.push(var5);
                }
            }
        }
        if (var2) {
            throw new NBTException("Unbalanced quotation: " + p_150310_0_);
        }
        if (!var3.isEmpty()) {
            throw new NBTException("Unbalanced brackets: " + p_150310_0_);
        }
        if (var1 == 0 && !p_150310_0_.isEmpty()) {
            return 1;
        }
        return var1;
    }
    
    static Any func_150316_a(final String p_150316_0_, String p_150316_1_) throws NBTException {
        p_150316_1_ = p_150316_1_.trim();
        func_150310_b(p_150316_1_);
        if (p_150316_1_.startsWith("{")) {
            if (!p_150316_1_.endsWith("}")) {
                throw new NBTException("Unable to locate ending bracket for: " + p_150316_1_);
            }
            p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
            final Compound var7 = new Compound(p_150316_0_);
            while (p_150316_1_.length() > 0) {
                final String var8 = func_150314_a(p_150316_1_, false);
                if (var8.length() > 0) {
                    final String var9 = func_150313_b(var8, false);
                    final String var10 = func_150311_c(var8, false);
                    var7.field_150491_b.add(func_150316_a(var9, var10));
                    if (p_150316_1_.length() < var8.length() + 1) {
                        break;
                    }
                    final char var11 = p_150316_1_.charAt(var8.length());
                    if (var11 != ',' && var11 != '{' && var11 != '}' && var11 != '[' && var11 != ']') {
                        throw new NBTException("Unexpected token '" + var11 + "' at: " + p_150316_1_.substring(var8.length()));
                    }
                    p_150316_1_ = p_150316_1_.substring(var8.length() + 1);
                }
            }
            return var7;
        }
        else {
            if (!p_150316_1_.startsWith("[") || p_150316_1_.matches("\\[[-\\d|,\\s]+\\]")) {
                return new Primitive(p_150316_0_, p_150316_1_);
            }
            if (!p_150316_1_.endsWith("]")) {
                throw new NBTException("Unable to locate ending bracket for: " + p_150316_1_);
            }
            p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
            final List var12 = new List(p_150316_0_);
            while (p_150316_1_.length() > 0) {
                final String var8 = func_150314_a(p_150316_1_, true);
                if (var8.length() > 0) {
                    final String var9 = func_150313_b(var8, true);
                    final String var10 = func_150311_c(var8, true);
                    var12.field_150492_b.add(func_150316_a(var9, var10));
                    if (p_150316_1_.length() < var8.length() + 1) {
                        break;
                    }
                    final char var11 = p_150316_1_.charAt(var8.length());
                    if (var11 != ',' && var11 != '{' && var11 != '}' && var11 != '[' && var11 != ']') {
                        throw new NBTException("Unexpected token '" + var11 + "' at: " + p_150316_1_.substring(var8.length()));
                    }
                    p_150316_1_ = p_150316_1_.substring(var8.length() + 1);
                }
                else {
                    JsonToNBT.logger.debug(p_150316_1_);
                }
            }
            return var12;
        }
    }
    
    private static String func_150314_a(final String p_150314_0_, final boolean p_150314_1_) throws NBTException {
        int var2 = func_150312_a(p_150314_0_, ':');
        if (var2 < 0 && !p_150314_1_) {
            throw new NBTException("Unable to locate name/value separator for string: " + p_150314_0_);
        }
        final int var3 = func_150312_a(p_150314_0_, ',');
        if (var3 >= 0 && var3 < var2 && !p_150314_1_) {
            throw new NBTException("Name error at: " + p_150314_0_);
        }
        if (p_150314_1_ && (var2 < 0 || var2 > var3)) {
            var2 = -1;
        }
        final Stack var4 = new Stack();
        int var5 = var2 + 1;
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        int var9 = 0;
        while (var5 < p_150314_0_.length()) {
            final char var10 = p_150314_0_.charAt(var5);
            if (var10 == '\"') {
                if (var5 > 0 && p_150314_0_.charAt(var5 - 1) == '\\') {
                    if (!var6) {
                        throw new NBTException("Illegal use of \\\": " + p_150314_0_);
                    }
                }
                else {
                    var6 = !var6;
                    if (var6 && !var8) {
                        var7 = true;
                    }
                    if (!var6) {
                        var9 = var5;
                    }
                }
            }
            else if (!var6) {
                if (var10 != '{' && var10 != '[') {
                    if (var10 == '}' && (var4.isEmpty() || var4.pop() != '{')) {
                        throw new NBTException("Unbalanced curly brackets {}: " + p_150314_0_);
                    }
                    if (var10 == ']' && (var4.isEmpty() || var4.pop() != '[')) {
                        throw new NBTException("Unbalanced square brackets []: " + p_150314_0_);
                    }
                    if (var10 == ',' && var4.isEmpty()) {
                        return p_150314_0_.substring(0, var5);
                    }
                }
                else {
                    var4.push(var10);
                }
            }
            if (!Character.isWhitespace(var10)) {
                if (!var6 && var7 && var9 != var5) {
                    return p_150314_0_.substring(0, var9 + 1);
                }
                var8 = true;
            }
            ++var5;
        }
        return p_150314_0_.substring(0, var5);
    }
    
    private static String func_150313_b(String p_150313_0_, final boolean p_150313_1_) throws NBTException {
        if (p_150313_1_) {
            p_150313_0_ = p_150313_0_.trim();
            if (p_150313_0_.startsWith("{") || p_150313_0_.startsWith("[")) {
                return "";
            }
        }
        final int var2 = p_150313_0_.indexOf(58);
        if (var2 >= 0) {
            return p_150313_0_.substring(0, var2).trim();
        }
        if (p_150313_1_) {
            return "";
        }
        throw new NBTException("Unable to locate name/value separator for string: " + p_150313_0_);
    }
    
    private static String func_150311_c(String p_150311_0_, final boolean p_150311_1_) throws NBTException {
        if (p_150311_1_) {
            p_150311_0_ = p_150311_0_.trim();
            if (p_150311_0_.startsWith("{") || p_150311_0_.startsWith("[")) {
                return p_150311_0_;
            }
        }
        final int var2 = p_150311_0_.indexOf(58);
        if (var2 >= 0) {
            return p_150311_0_.substring(var2 + 1).trim();
        }
        if (p_150311_1_) {
            return p_150311_0_;
        }
        throw new NBTException("Unable to locate name/value separator for string: " + p_150311_0_);
    }
    
    private static int func_150312_a(final String p_150312_0_, final char p_150312_1_) {
        int var2 = 0;
        boolean var3 = false;
        while (var2 < p_150312_0_.length()) {
            final char var4 = p_150312_0_.charAt(var2);
            if (var4 == '\"') {
                if (var2 <= 0 || p_150312_0_.charAt(var2 - 1) != '\\') {
                    var3 = !var3;
                }
            }
            else if (!var3) {
                if (var4 == p_150312_1_) {
                    return var2;
                }
                if (var4 == '{' || var4 == '[') {
                    return -1;
                }
            }
            ++var2;
        }
        return -1;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    abstract static class Any
    {
        protected String field_150490_a;
        private static final String __OBFID = "CL_00001233";
        
        public abstract NBTBase func_150489_a();
    }
    
    static class Compound extends Any
    {
        protected ArrayList field_150491_b;
        private static final String __OBFID = "CL_00001234";
        
        public Compound(final String p_i45137_1_) {
            this.field_150491_b = new ArrayList();
            this.field_150490_a = p_i45137_1_;
        }
        
        @Override
        public NBTBase func_150489_a() {
            final NBTTagCompound var1 = new NBTTagCompound();
            for (final Any var3 : this.field_150491_b) {
                var1.setTag(var3.field_150490_a, var3.func_150489_a());
            }
            return var1;
        }
    }
    
    static class List extends Any
    {
        protected ArrayList field_150492_b;
        private static final String __OBFID = "CL_00001235";
        
        public List(final String p_i45138_1_) {
            this.field_150492_b = new ArrayList();
            this.field_150490_a = p_i45138_1_;
        }
        
        @Override
        public NBTBase func_150489_a() {
            final NBTTagList var1 = new NBTTagList();
            for (final Any var3 : this.field_150492_b) {
                var1.appendTag(var3.func_150489_a());
            }
            return var1;
        }
    }
    
    static class Primitive extends Any
    {
        protected String field_150493_b;
        private static final String __OBFID = "CL_00001236";
        
        public Primitive(final String p_i45139_1_, final String p_i45139_2_) {
            this.field_150490_a = p_i45139_1_;
            this.field_150493_b = p_i45139_2_;
        }
        
        @Override
        public NBTBase func_150489_a() {
            try {
                if (this.field_150493_b.matches("[-+]?[0-9]*\\.?[0-9]+[d|D]")) {
                    return new NBTTagDouble(Double.parseDouble(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
                }
                if (this.field_150493_b.matches("[-+]?[0-9]*\\.?[0-9]+[f|F]")) {
                    return new NBTTagFloat(Float.parseFloat(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
                }
                if (this.field_150493_b.matches("[-+]?[0-9]+[b|B]")) {
                    return new NBTTagByte(Byte.parseByte(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
                }
                if (this.field_150493_b.matches("[-+]?[0-9]+[l|L]")) {
                    return new NBTTagLong(Long.parseLong(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
                }
                if (this.field_150493_b.matches("[-+]?[0-9]+[s|S]")) {
                    return new NBTTagShort(Short.parseShort(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
                }
                if (this.field_150493_b.matches("[-+]?[0-9]+")) {
                    return new NBTTagInt(Integer.parseInt(this.field_150493_b.substring(0, this.field_150493_b.length())));
                }
                if (this.field_150493_b.matches("[-+]?[0-9]*\\.?[0-9]+")) {
                    return new NBTTagDouble(Double.parseDouble(this.field_150493_b.substring(0, this.field_150493_b.length())));
                }
                if (this.field_150493_b.equalsIgnoreCase("true") || this.field_150493_b.equalsIgnoreCase("false")) {
                    return new NBTTagByte((byte)(Boolean.parseBoolean(this.field_150493_b) ? 1 : 0));
                }
                if (this.field_150493_b.startsWith("[") && this.field_150493_b.endsWith("]")) {
                    if (this.field_150493_b.length() > 2) {
                        final String var1 = this.field_150493_b.substring(1, this.field_150493_b.length() - 1);
                        final String[] var2 = var1.split(",");
                        try {
                            if (var2.length <= 1) {
                                return new NBTTagIntArray(new int[] { Integer.parseInt(var1.trim()) });
                            }
                            final int[] var3 = new int[var2.length];
                            for (int var4 = 0; var4 < var2.length; ++var4) {
                                var3[var4] = Integer.parseInt(var2[var4].trim());
                            }
                            return new NBTTagIntArray(var3);
                        }
                        catch (NumberFormatException var5) {
                            return new NBTTagString(this.field_150493_b);
                        }
                    }
                    return new NBTTagIntArray();
                }
                if (this.field_150493_b.startsWith("\"") && this.field_150493_b.endsWith("\"") && this.field_150493_b.length() > 2) {
                    this.field_150493_b = this.field_150493_b.substring(1, this.field_150493_b.length() - 1);
                }
                this.field_150493_b = this.field_150493_b.replaceAll("\\\\\"", "\"");
                return new NBTTagString(this.field_150493_b);
            }
            catch (NumberFormatException var6) {
                this.field_150493_b = this.field_150493_b.replaceAll("\\\\\"", "\"");
                return new NBTTagString(this.field_150493_b);
            }
        }
    }
}
