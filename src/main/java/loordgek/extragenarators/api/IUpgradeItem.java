package loordgek.extragenarators.api;

import net.minecraft.item.ItemStack;

public interface IUpgradeItem {
    int getspeedboost(ItemStack stack);
    int getpowerstoreboost(ItemStack stack);
    int getmultiplierboost(ItemStack stack);
}
