package loordgek.extragenarators.event;

import loordgek.extragenarators.enums.EnumBlocks;
import loordgek.extragenarators.enums.EnumItems;
import loordgek.extragenarators.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommenEventHandler {
    @SubscribeEvent
    public void onRegistryRegisterBlocks(RegistryEvent.Register<Block> event) {
        for (EnumBlocks enumBlocks : EnumBlocks.values()) {
            enumBlocks.RegisterBlock(event.getRegistry());
        }
    }

    @SubscribeEvent
    public void onRegistryRegisterItems(RegistryEvent.Register<Item> event) {
        LogHelper.info(event);
        for (EnumBlocks enumBlocks : EnumBlocks.values()) {
            enumBlocks.RegisterItemBlock(event.getRegistry());
        }
        for (EnumItems enumitems : EnumItems.values()) {
            enumitems.RegisterItem(event.getRegistry());
        }
    }
}
