package loordgek.extragenarators.util.item;

import loordgek.extragenarators.container.slot.SlotHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface ISlotHandler {
    @Nonnull
    ItemStack getStackInSlot(int slot);

    int size();

    int StackLimit();

    default int ItemStacklimit(int slot, @Nonnull ItemStack stack) {
        return Math.min(StackLimit(), stack.getMaxStackSize());
    }

    @Nonnull
    ItemStack decrStackSize(int slot, int amount);

    void putStackInSlot(@Nonnull ItemStack stack, int slot);

    boolean isStackValidForSlot(int slot, ItemStack stack);

    default Optional<ItemStack> handleShiftClick(SlotHandler from, EntityPlayer player) {
        ItemStack fromStack = from.getStack().copy();
        for (int i = 0; i < size(); i++) {
            ItemStack toStack = getStackInSlot(i);
            if (toStack.isEmpty() || (ItemStack.areItemStacksEqual(toStack, fromStack) && ItemStack.areItemStackTagsEqual(toStack, fromStack))) {

                ItemStack put = fromStack.splitStack(this.ItemStacklimit(i, fromStack) - (toStack.isEmpty() ? 0 : toStack.getCount()));
                if (!put.isEmpty()) {
                    put.grow(toStack.getCount());
                    putStackInSlot(put, i);
                    if (fromStack.isEmpty()) {
                        from.putStack(ItemStack.EMPTY);
                        return Optional.of(put);
                    }
                }
            }
        }
        return Optional.empty();
    }

}
