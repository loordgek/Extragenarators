package loordgek.extragenarators.event;

import loordgek.extragenarators.enums.EnumBlocks;
import loordgek.extragenarators.enums.EnumItems;
import loordgek.extragenarators.ref.Reference;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.MODINFO.MOD_ID)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        for (EnumItems enumitems : EnumItems.values()) {
            enumitems.RegisterRender();
        }

        for (EnumBlocks enumblocks : EnumBlocks.values()) {
            enumblocks.RegisterRender();
        }
    }
}
