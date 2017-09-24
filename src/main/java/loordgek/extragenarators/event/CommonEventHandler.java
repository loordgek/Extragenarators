package loordgek.extragenarators.event;

import loordgek.extragenarators.enums.EnumBlocks;
import loordgek.extragenarators.enums.EnumItems;
import loordgek.extragenarators.ref.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODINFO.MOD_ID)
public class CommonEventHandler {
    @SubscribeEvent
    public static void onRegistryRegisterBlocks(RegistryEvent.Register<Block> event) {
        for (EnumBlocks enumBlocks : EnumBlocks.values()) {
            enumBlocks.RegisterBlock(event.getRegistry());
        }
    }

    @SubscribeEvent
    public static void onRegistryRegisterItems(RegistryEvent.Register<Item> event) {
        for (EnumBlocks enumBlocks : EnumBlocks.values()) {
            enumBlocks.RegisterItemBlock(event.getRegistry());
        }
        for (EnumItems enumitems : EnumItems.values()) {
            enumitems.RegisterItem(event.getRegistry());
        }
    }
}
