package loordgek.extragenarators;

import loordgek.extragenarators.init.InitBlocks;
import loordgek.extragenarators.init.InitItems;
import loordgek.extragenarators.init.InitTile;
import loordgek.extragenarators.network.NetworkHandler;
import loordgek.extragenarators.proxy.IProxy;
import loordgek.extragenarators.ref.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(
        modid = "extragenarators",
        name = "Extragenarators",
        version = "1.9.4-1.0"
)
public class Extragenarators {
    @Mod.Instance(Reference.MODINFO.MOD_ID)
    public static Extragenarators instance;

    @SidedProxy(clientSide = Reference.MODINFO.CLIENT_PROXY_CLASS, serverSide = Reference.MODINFO.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @EventHandler
    public void preinit(FMLPreInitializationEvent event){
        InitBlocks.Init();
        InitItems.Init();
        InitTile.Init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHander());
        NetworkHandler.initNetwork();


    }
    @EventHandler
    public void init(FMLInitializationEvent event){}
}
