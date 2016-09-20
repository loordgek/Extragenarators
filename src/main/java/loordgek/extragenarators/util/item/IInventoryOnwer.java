package loordgek.extragenarators.util.item;

import loordgek.extragenarators.enums.EnumInvFlow;
import net.minecraft.item.ItemStack;

public interface IInventoryOnwer{
    void OnInventoryChanged(ItemStack stack, int slot, String name, EnumInvFlow flow);
    void markDirty();
}
