package loordgek.extragenarators.items;

import loordgek.extragenarators.Extragenarators;
import loordgek.extragenarators.api.IUpgradeItem;
import loordgek.extragenarators.enums.EnumUpgrade;
import loordgek.extragenarators.ref.Reference;
import loordgek.extragenarators.util.IVariantLookup;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemUpgrade extends ItemMain implements IUpgradeItem, IVariantLookup {
    public ItemUpgrade() {
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.FOOD);
    }

    @Override
    public int getspeedboost(ItemStack stack) {
        if (stack.getItemDamage() == 0) return 1;
        if (stack.getItemDamage() == 1) return 2;
        if (stack.getItemDamage() == 2) return 4;
        if (stack.getItemDamage() == 3) return 8;

        return 0;
    }

    @Override
    public int getpowerstoreboost(ItemStack stack) {
        if (stack.getItemDamage() == 4) return 1;
        if (stack.getItemDamage() == 5) return 2;
        if (stack.getItemDamage() == 6) return 4;
        if (stack.getItemDamage() == 7) return 8;

        return 0;
    }

    @Override
    public int getmultiplierboost(ItemStack stack) {
        if (stack.getItemDamage() == 8) return 1;
        if (stack.getItemDamage() == 9) return 2;
        if (stack.getItemDamage() == 10) return 4;
        if (stack.getItemDamage() == 11) return 8;

        return 0;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Reference.RESOURCE.RESOURCE_PREFIX, Reference.ITEMS.upgrade);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return String.format("item.%s%s.%s", Reference.RESOURCE.RESOURCE_PREFIX, Reference.ITEMS.upgrade, Reference.ITEMS.typeupgrade[MathHelper.clamp(stack.getItemDamage(), 0, Reference.ITEMS.typeupgrade.length - 1)]);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems( CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (EnumUpgrade enumUpgrade : EnumUpgrade.values()) {
            subItems.add(new ItemStack(this, 1, enumUpgrade.getMeta()));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void RegisterRender() {
        Extragenarators.proxy.getModelManager().registerVariantItemModels(this, "variant", EnumUpgrade.values());
        Extragenarators.proxy.getModelManager().registerItemModel(this);
    }

    @Override
    public String[] variantnames() {
        String[] strings = new String[EnumUpgrade.values().length];
        for (EnumUpgrade enumUpgrade : EnumUpgrade.values()){
            strings[enumUpgrade.getMeta()] = enumUpgrade.getName();
        }
        return strings;
    }
}
