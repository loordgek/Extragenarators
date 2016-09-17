package loordgek.extragenarators.util.item;

import jdk.nashorn.internal.objects.annotations.Getter;
import loordgek.extragenarators.enums.EnumInvFlow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class InventorySimpleItemhander implements IItemHandler {


    private final int stacksize;
    private final int invsize;
    private final String name;
    private final IInventoryOnwer onwer;
    private ItemStack[] stacks;

    public InventorySimpleItemhander(int stacksize, int invsize, String name, IInventoryOnwer onwer) {
        this.stacksize = stacksize;
        this.invsize = invsize;
        this.name = name;
        this.onwer = onwer;
        this.stacks = new ItemStack[invsize];
    }
    @Override
    public int getSlots() {
        return invsize;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return stacks[slot];
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack == null)
            return null;

        ItemStack stackinslot = getStackInSlot(slot);

        int m;
        if (stackinslot != null){
            if (!ItemHandlerHelper.canItemStacksStack(stack ,stackinslot))
                return stack;

            m = Math.min(stack.getMaxStackSize(), stacksize - stackinslot.stackSize);
            if (stack.stackSize <= m){
                if (!simulate){
                    ItemStack copy = stack.copy();
                    copy.stackSize += stackinslot.stackSize;
                    setInventorySlotContents(slot, stack);
                    onwer.OnInventoryChange(stack,slot,name, EnumInvFlow.INSERT);
                }
                return null;

            }
            else{
                stack = stack.copy();
                if (!simulate){
                    ItemStack copy = stack.splitStack(m);
                    copy.stackSize += stackinslot.stackSize;
                    setInventorySlotContents(slot, stack);
                    onwer.OnInventoryChange(stack,slot,name, EnumInvFlow.INSERT);
                    }
            }
        }
        else {
            m = Math.min(stack.getMaxStackSize(), stacksize);
            if (m < stack.stackSize) {
                stack = stack.copy();
                if (!simulate) {
                    setInventorySlotContents(slot, stack);
                    onwer.OnInventoryChange(stack,slot,name, EnumInvFlow.INSERT);
                    return stack;
                }
                else {
                    stack.stackSize -= m;
                    return stack;
                }
            }
            else {
                if (!simulate) {
                    setInventorySlotContents(slot, stack);
                    onwer.OnInventoryChange(stack,slot,name, EnumInvFlow.INSERT);
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return null;
        ItemStack stackInSlot = stacks[slot];

        if (stackInSlot == null)
            return null;

        if (simulate)
            if (stackInSlot.stackSize < amount)
                return stackInSlot.copy();
            else {
                ItemStack copy = stackInSlot.copy();
                copy.stackSize = amount;
                return copy;
            }
        else {
            int m = Math.min(stackInSlot.stackSize, amount);

            ItemStack decrStackSize = decrStackSize(slot, amount);
            onwer.OnInventoryChange(decrStackSize ,slot ,name ,EnumInvFlow.EXTRAXT);
            return decrStackSize;
        }


    }
    public ItemStack decrStackSize(int slot, int count){
        if (stacks[slot].stackSize <= count){

            ItemStack stack = this.stacks[slot];
            stacks[slot] = null;
            return stack;
        }
        else {
            ItemStack stack = stacks[slot].splitStack(count);
            if (stacks[slot].stackSize == 0){
                stacks[slot] = null;
            }
            return stack;
        }

    }
    public void setInventorySlotContents(int slot, @Nullable ItemStack stack) {
        if (slot >= stacks.length) {
            return;
        }
        stacks[slot] = stack;

        if (stack != null && stack.stackSize > stacksize) {
            stack.stackSize = stacksize;
        }
        onwer.markDirty();

    }
}
