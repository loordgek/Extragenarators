package loordgek.extragenarators.event;

import loordgek.extragenarators.client.gui.widgets.WidgetBase;
import loordgek.extragenarators.enums.EnumBlocks;
import loordgek.extragenarators.enums.EnumItems;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event) {
        for (EnumItems enumitems : EnumItems.values()) {
            enumitems.RegisterRender();
        }
        for (EnumBlocks enumblocks : EnumBlocks.values()) {
            enumblocks.RegisterRender();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        WidgetBase.debugtimer--;
        if (WidgetBase.debugtimer == 0){
            WidgetBase.debugtimer = 100;
        }

    }
}
