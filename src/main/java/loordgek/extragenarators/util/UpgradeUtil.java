package loordgek.extragenarators.util;

import loordgek.extragenarators.api.IUpgradeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class UpgradeUtil {
    public static int getSpeedBoost(NonNullList<ItemStack> stacks) {
        int speedboost = 1;
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty() && stack.getItem() instanceof IUpgradeItem) {
                speedboost += ((IUpgradeItem) stack.getItem()).getspeedboost(stack) * stack.getCount();
            }
        }
        return speedboost;
    }

    public static int getPowerstoreBoost(NonNullList<ItemStack> stacks) {
        int speedboost = 1;
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty() && stack.getItem() instanceof IUpgradeItem) {
                speedboost += ((IUpgradeItem) stack.getItem()).getpowerstoreboost(stack) * stack.getCount();
            }
        }
        return speedboost;
    }

    public static int getMultiplierBoost(NonNullList<ItemStack> stacks) {
        int speedboost = 1;
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty() && stack.getItem() instanceof IUpgradeItem) {
                speedboost += ((IUpgradeItem) stack.getItem()).getmultiplierboost(stack) * stack.getCount();
            }
        }
        return speedboost;
    }
}
