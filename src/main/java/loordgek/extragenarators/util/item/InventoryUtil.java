package loordgek.extragenarators.util.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtil {
    public static ItemStack[] getStacks(IItemHandler itemHandler) {
        ItemStack[] stacks = new ItemStack[itemHandler.getSlots()];
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            stacks[i] = itemHandler.getStackInSlot(i);
        }
        return stacks;
    }

    public static void dropInv(World world, ItemStack[] stackList, BlockPos pos) {
        for (ItemStack stack : stackList) {
            if (stack != null)
                world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
        }
    }

    public static boolean isBucket(ItemStack stack) {
        return stack != null && stack.getItem() != null && stack.getItem() == Items.BUCKET;
    }
}