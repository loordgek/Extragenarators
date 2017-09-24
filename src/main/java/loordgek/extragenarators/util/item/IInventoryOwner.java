package loordgek.extragenarators.util.item;

import loordgek.extragenarators.enums.EnumInvFlow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public interface IInventoryOwner {
    void OnInventoryChanged(ItemStack stack, int slot, IItemHandler itemHandler, EnumInvFlow flow);
    void markDirty();
    void updateItemHandler();
    @Nullable
    World getWord();

    @Nullable
    BlockPos getBlockPos();
}
