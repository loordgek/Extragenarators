package loordgek.extragenarators.util;

import loordgek.extragenarators.container.slot.SlotInvHolder;
import loordgek.extragenarators.util.item.IInvHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface IShiftHandler extends IInvHolder {
    @Nonnull
    @Override
     default ItemStack getStackInSlot(int slot){
        return ItemStack.EMPTY;
    }

    @Override
     default int size(){
        return 0;
    }

    @Override
    default int StackLimit(){
        return 0;
    }

    @Override
    default int ItemStackLimit(int slot, @Nonnull ItemStack stack) {
        return 0;
    }

    @Nonnull
    @Override
    default ItemStack decrStackSize(int slot, int amount){
        return ItemStack.EMPTY;
    }

    @Override
    default void onSlotChanged(){}

    @Override
    default void putStackInSlot(@Nonnull ItemStack stack, int slot){ }

    @Override
    default boolean isStackValidForSlot(int slot, ItemStack stack){
        return false;
    }

    @Override
    Optional<ItemStack> handleShiftClick(SlotInvHolder from, EntityPlayer player);
}
