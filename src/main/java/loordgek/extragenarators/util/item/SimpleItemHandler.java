package loordgek.extragenarators.util.item;

import loordgek.extragenarators.enums.EnumInvFlow;
import net.minecraft.inventory.ItemStackHelper;
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

public class SimpleItemHandler implements IItemHandler ,IInvHolder ,IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {

    private final int stacksize;
    private final int invsize;
    private final IInventoryOwner owner;
    private NonNullList<ItemStack> stacks;

    public SimpleItemHandler(int stacksize, int invsize, IInventoryOwner owner) {
        this.stacksize = stacksize;
        this.invsize = invsize;
        this.owner = owner;
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

    @Override
    public int size() {
        return getSlots();
    }

    @Override
    public int StackLimit() {
        return stacksize;
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        {
            ItemStack itemstack = ItemStackHelper.getAndSplit(this.stacks, slot, amount);

            if (!itemstack.isEmpty())
            {
                this.owner.markDirty();
            }

            return itemstack;
        }
    }

    @Override
    public void putStackInSlot(@Nonnull ItemStack stack, int slot) {
        stacks.set(slot, stack);
    }

    public boolean isStackValidForSlot(int Slot, ItemStack stack) {
        return true;
    }

    @Override
    public void onSlotChanged() {

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
            owner.OnInventoryChanged(stack1, slot, this, EnumInvFlow.INSERT);
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
                owner.OnInventoryChanged(existing, slot, this, EnumInvFlow.EXTRACT);
            }
            return existing;
        } else {
            if (!simulate) {
                ItemStack stack = ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract);
                stacks.set(slot, stack);
                owner.OnInventoryChanged(stack, slot, this, EnumInvFlow.EXTRACT);
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
        owner.markDirty();
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        stacks.set(slot, stack);
        owner.markDirty();
    }

    protected IInventoryOwner getOwner() {
        return owner;
    }
}