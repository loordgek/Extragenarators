package loordgek.extragenarators.util.item;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class InventorySlotHandler implements ISlotHandler{
    private final IInventory inventory;

    public InventorySlotHandler(IInventory inventory) {
        this.inventory = inventory;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public int size() {
        return inventory.getSizeInventory();
    }

    @Override
    public int StackLimit() {
       return inventory.getInventoryStackLimit();
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return inventory.decrStackSize(slot, amount);
    }

    @Override
    public void putStackInSlot(@Nonnull ItemStack stack, int slot) {
        inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        return inventory.isItemValidForSlot(slot, stack);
    }
}
