import net.minecraft.client.main.*;
import java.util.*;

public class Start
{
    public static void main(final String[] args) {
        Main.main(concat(new String[] { "--version", "mcp", "--assetsDir", "assets", "--assetIndex", "1.7.10", "--userProperties", "{}" }, args));
    }
    
    private static <T> T[] concat(final T[] first, final T[] second) {
        final T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
