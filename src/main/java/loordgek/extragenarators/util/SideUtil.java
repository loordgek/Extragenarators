package loordgek.extragenarators.util;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class SideUtil {

    public static boolean isServer() {
        return FMLCommonHandler.instance().getEffectiveSide().isServer();
    }

    public static boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide().isClient();
    }
}
