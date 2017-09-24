package loordgek.extragenarators.util.item;

import loordgek.extragenarators.enums.EnumInvFlow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class InventorySimpleItemHandler implements IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {


    private final int stacksize;
    private final int invsize;
    private final String name;
    private final IInventoryOwner onwer;
    private NonNullList<ItemStack> stacks;

    public InventorySimpleItemHandler(int stacksize, int invsize, String name, IInventoryOwner onwer) {
        this.stacksize = stacksize;
        this.invsize = invsize;
        this.name = name;
        this.onwer = onwer;
        this.stacks = NonNullList.withSize(invsize, ItemStack.EMPTY);
    }

    @Override
    public int getSlots() {
        return invsize;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        return stacks.get(slot);
    }

    public boolean isStackValidForSlot(int Slot, ItemStack stack) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot,@Nonnull ItemStack stack, boolean simulate) {
        if (stack == ItemStack.EMPTY || stack.getCount() == 0)
            return ItemStack.EMPTY;

        if (!isStackValidForSlot(slot, stack)) {
            return stack;
        }

        ItemStack existing = stacks.get(slot);

        int limit = stacksize;

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (!existing.isEmpty()) {
                stacks.set(slot,  reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            ItemStack stack1 = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : null;
            onwer.OnInventoryChanged(stack1, slot, name, EnumInvFlow.INSERT);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        ItemStack existing = stacks.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                stacks.set(slot, ItemStack.EMPTY);
                onwer.OnInventoryChanged(existing, slot, name, EnumInvFlow.EXTRACT);
            }
            return existing;
        } else {
            if (!simulate) {
                ItemStack stack = ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract);
                stacks.set(slot, stack);
                onwer.OnInventoryChanged(stack, slot, name, EnumInvFlow.EXTRACT);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public int getSlotLimit(int slot) {
       return stacksize;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("Slot", i);
                stacks.get(i).writeToNBT(itemTag);
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

            if (slot >= 0 && slot <= stacks.size()) {
                stacks.set(slot, new ItemStack(itemTags));

            }
        }
        onwer.markDirty();
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        stacks.set(slot, stack);
        onwer.markDirty();
    }

    protected IInventoryOwner getOwner() {
        return onwer;
    }
}