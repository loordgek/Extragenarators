package loordgek.extragenarators.util.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class InventoryUtil {
    public static NonNullList<ItemStack> getStacks(IItemHandler itemHandler) {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(itemHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            nonNullList.set(i, itemHandler.getStackInSlot(i));
        }
        return nonNullList;
    }

    public static void dropstacklist(World world, NonNullList<ItemStack> stackList, BlockPos pos) {
        for (ItemStack stack : stackList) {
            if (!stack.isEmpty())
                spawnEntityItem(world, pos, stack);
        }
    }

    public static void dropInv(World world, IItemHandler itemHandler, BlockPos pos) {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if (!itemStack.isEmpty()){
                spawnEntityItem(world, pos, itemStack);
            }
        }
    }

    public static Iterator<ItemStack> invIterator(IItemHandler itemHandler){
        return new Iterator<ItemStack>() {
            int index;
            @Override
            public boolean hasNext() {
                return index < itemHandler.getSlots();
            }

            @Override
            public ItemStack next() {
                ItemStack stack = itemHandler.getStackInSlot(index);
                index++;
                return stack;

            }
        };
    }

    public static Stream<ItemStack> invStream(IItemHandler itemHandler){
        return StreamSupport.stream(Spliterators.spliterator(invIterator(itemHandler), itemHandler.getSlots(), 0), false);
    }

    public static void spawnEntityItem(World world, BlockPos pos,@Nonnull ItemStack stack){
        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }

    public static boolean isBucket(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.BUCKET;
    }
}