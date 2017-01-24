package loordgek.extragenarators.enums;

import loordgek.extragenarators.Extragenarators;
import loordgek.extragenarators.items.Items;
import loordgek.extragenarators.ref.Reference;
import loordgek.extragenarators.util.BasicItemJsonGen;
import loordgek.extragenarators.util.IVariantLookup;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

import java.io.IOException;


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

    public void RegisterRender()  {
        for (int i = 0; i < lookup.variantnames().length; i++) {
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Reference.RESOURCE.RESOURCE_PREFIX + name + lookup.variantnames()[i], "inventory");
            Extragenarators.proxy.setCustomModelResourceLocationitem(item, i, modelResourceLocation);
        }
        try {
            BasicItemJsonGen.genItemJson("C://Modding/forge", Reference.MODINFO.MOD_ID, name , lookup);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
