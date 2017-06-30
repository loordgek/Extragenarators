package loordgek.extragenarators.util.item;

import loordgek.extragenarators.enums.EnumInvFlow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public class InventorySimpleItemHandler implements IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {


    private final int stacksize;
    private final int invsize;
    private final String name;
    private final IInventoryOnwer onwer;
    private ItemStack[] stacks;

    public InventorySimpleItemHandler(int stacksize, int invsize, String name, IInventoryOnwer onwer) {
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

    public boolean isStackValidForSlot(int Slot, ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack == null || stack.stackSize == 0)
            return null;

        if (!isStackValidForSlot(slot, stack)) {
            return stack;
        }

        ItemStack existing = this.stacks[slot];

        int limit = stacksize;

        if (existing != null) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.stackSize;
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.stackSize > limit;

        if (!simulate) {
            if (existing == null) {
                this.stacks[slot] = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack;
            } else {
                existing.stackSize += reachedLimit ? limit : stack.stackSize;
            }
            ItemStack stack1 = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.stackSize - limit) : null;
            onwer.OnInventoryChanged(stack1, slot, name, EnumInvFlow.INSERT);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.stackSize - limit) : null;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return null;

        ItemStack existing = this.stacks[slot];

        if (existing == null)
            return null;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.stackSize <= toExtract) {
            if (!simulate) {
                this.stacks[slot] = null;
                onwer.OnInventoryChanged(existing, slot, name, EnumInvFlow.EXTRACT);
            }
            return existing;
        } else {
            if (!simulate) {
                ItemStack stack = ItemHandlerHelper.copyStackWithSize(existing, existing.stackSize - toExtract);
                this.stacks[slot] = stack;
                onwer.OnInventoryChanged(stack, slot, name, EnumInvFlow.EXTRACT);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < stacks.length; i++) {
            if (stacks[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("Slot", i);
                stacks[i].writeToNBT(itemTag);
                nbtTagList.appendTag(itemTag);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("items", nbtTagList);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList tagList = nbt.getTagList("items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
            int slot = itemTags.getInteger("Slot");

            if (slot >= 0 && slot <= stacks.length) {
                stacks[slot] = ItemStack.loadItemStackFromNBT(itemTags);

            }
        }
        onwer.markDirty();
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        stacks[slot] = stack;
        onwer.markDirty();
    }

    protected IInventoryOnwer getOnwer() {
        return onwer;
    }
}