package loordgek.extragenarators.enums;

import loordgek.extragenarators.items.Items;
import loordgek.extragenarators.ref.Reference;
import loordgek.extragenarators.util.IVariantLookup;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;


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
        lookup.RegisterRender();
    }

    public Item getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public IVariantLookup getLookup() {
        return lookup;
    }
}