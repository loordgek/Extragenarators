package loordgek.extragenarators.enums;

import loordgek.extragenarators.Extragenarators;
import loordgek.extragenarators.items.Items;
import loordgek.extragenarators.ref.Reference;
import loordgek.extragenarators.util.IVariantLookup;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum EnumItems {
    ITEMUPGRADE(Items.itemupgrade, "upgrade", Items.itemupgrade);

    private final Item item;
    private final String name;
    private final IVariantLookup lookup;

    EnumItems(Item item, String name, IVariantLookup lookup) {
        this.item = item;
        this.name = name;
        this.lookup = lookup;
    }

    public void RegisterItem(IForgeRegistry<Item> iForgeRegistry) {
        item.setRegistryName(Reference.MODINFO.MOD_ID, name);
        iForgeRegistry.register(item);
    }

    @SideOnly(Side.CLIENT)
    public void RegisterRender() {
        for (int i = 0; i < lookup.variantnames().length; i++) {
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Reference.RESOURCE.RESOURCE_PREFIX + name, lookup.variantnames()[i]);
            Extragenarators.proxy.setCustomModelResourceLocationitem(item, i, modelResourceLocation);
        }
    }
}
