package loordgek.extragenarators.enums;

import loordgek.extragenarators.Extragenarators;
import loordgek.extragenarators.blocks.Blocks;
import loordgek.extragenarators.ref.Reference;
import loordgek.extragenarators.util.IVariantLookup;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

public enum EnumBlocks {
    FURNACEGENBLOCK(Blocks.GEN, Blocks.GenITEM, "genaratorbase", Blocks.GEN);

    private final Block block;
    private final ItemBlock itemBlock;
    private final String name;
    private final IVariantLookup lookup;

    EnumBlocks(Block block, ItemBlock itemBlock, String name, IVariantLookup lookup) {
        this.block = block;
        this.itemBlock = itemBlock;
        this.name = name;
        this.lookup = lookup;
    }

    EnumBlocks(Block block, String name, IVariantLookup lookup) {
        this(block, new ItemBlock(block), name, lookup);
    }

    public void RegisterBlock(IForgeRegistry<Block> iForgeRegistry) {
        block.setRegistryName(Reference.MODINFO.MOD_ID, name);
        iForgeRegistry.register(block);
    }

    public void RegisterItemBlock(IForgeRegistry<Item> iForgeRegistry) {
        itemBlock.setRegistryName(block.getRegistryName());
        iForgeRegistry.register(itemBlock);
    }

    public void RegisterRender() {
        for (int i = 0; i < lookup.variantnames().length; i++) {
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Reference.RESOURCE.RESOURCE_PREFIX + name, lookup.variantnames()[i]);
            Extragenarators.proxy.setCustomModelResourceLocationitem(itemBlock, i, modelResourceLocation);
        }

    }
}
