package loordgek.extragenarators.container.slot;

import loordgek.extragenarators.api.IUpgradeItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class SlotUpgrade extends SlotItemHandler {


    public SlotUpgrade(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return stack != null && stack.getItem() instanceof IUpgradeItem && super.isItemValid(stack);
    }
}
