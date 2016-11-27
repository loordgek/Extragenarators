package loordgek.extragenarators.container.slot;

import loordgek.extragenarators.util.item.InventoryUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFurnaceFuel extends SlotItemHandler{
    public SlotFurnaceFuel(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return TileEntityFurnace.isItemFuel(stack) &&
                super.isItemValid(stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return InventoryUtil.isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

}
