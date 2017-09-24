package loordgek.extragenarators.util.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class InvHolder2ItemHandler implements IItemHandler {
    private final IInvHolder invHolder;

    public InvHolder2ItemHandler(IInvHolder invHolder) {
        this.invHolder = invHolder;
    }

    @Override
    public int getSlots() {
        return invHolder.size();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return invHolder.getStackInSlot(slot);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot,@Nonnull ItemStack stack, boolean simulate) {
        if (stack == ItemStack.EMPTY || stack.getCount() == 0)
            return ItemStack.EMPTY;

        if (!invHolder.isStackValidForSlot(slot, stack)) {
            return stack;
        }

        ItemStack existing = invHolder.getStackInSlot(slot);

        int limit = invHolder.StackLimit();

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
                invHolder.putStackInSlot(reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack, slot);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        ItemStack existing = getStackInSlot(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                invHolder.putStackInSlot(ItemStack.EMPTY, slot);
                //owner.OnInventoryChanged(existing, slot, this, EnumInvFlow.EXTRACT);
            }
            return existing;
        } else {
            if (!simulate) {
                ItemStack stack = ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract);
                invHolder.putStackInSlot(stack, slot);
                //owner.OnInventoryChanged(stack, slot, this, EnumInvFlow.EXTRACT);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return invHolder.StackLimit();
    }
}
