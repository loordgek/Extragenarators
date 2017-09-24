package loordgek.extragenarators.api;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IUpgradeItem {
    int getspeedboost(@Nonnull ItemStack stack);
    int getpowerstoreboost(@Nonnull ItemStack stack);
    int getmultiplierboost(@Nonnull ItemStack stack);
}
