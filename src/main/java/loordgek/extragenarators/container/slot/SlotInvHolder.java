package loordgek.extragenarators.container.slot;

import loordgek.extragenarators.util.item.IInvHolder;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotInvHolder extends Slot {
    private static IInventory emptyInventory = new InventoryBasic("[Null]", true, 0);
    private final IInvHolder invHolder;

    public SlotInvHolder(IInvHolder invHolder, int index, int xPosition, int yPosition) {
        super(emptyInventory, index, xPosition, yPosition);
        this.invHolder = invHolder;
    }

    @Override
    public int getSlotStackLimit() {
        return invHolder.StackLimit();
    }

    @Override
    @Nonnull
    public ItemStack getStack() {
        return invHolder.getStackInSlot(getSlotIndex());
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
       return invHolder.isStackValidForSlot(getSlotIndex(), stack);
    }

    @Override
    public void putStack(@Nonnull ItemStack stack) {
        invHolder.putStackInSlot(stack, getSlotIndex());
        invHolder.onSlotChanged();
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int amount) {
        invHolder.onSlotChanged();
        return invHolder.decrStackSize(getSlotIndex(), amount);

    }

    public IInvHolder getInvHolder() {
        return invHolder;
    }

    @Override
    public boolean isHere(IInventory inv, int slotIn) {
        return false;
    }

    @Override
    public boolean isSameInventory(Slot other) {
        return other instanceof SlotInvHolder && ((SlotInvHolder) other).getInvHolder() == invHolder;
    }
}
