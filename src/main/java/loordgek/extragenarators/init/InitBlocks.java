package loordgek.extragenarators.init;

import loordgek.extragenarators.blocks.BlockGenBase;
import loordgek.extragenarators.blocks.BlockMain;
import loordgek.extragenarators.items.itemblock.ItemBlockGenBase;
import loordgek.extragenarators.items.itemblock.ItemBlockMain;
import loordgek.extragenarators.ref.Reference;
import loordgek.extragenarators.util.SideUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitBlocks {
    public static final BlockMain GEN = new BlockGenBase();
    public static final ItemBlockMain GenITEM = new ItemBlockGenBase(GEN);

    public static void Init(){
        registerBlock(GEN,GenITEM , "BlockGenBase");
    }

    private static Block registerBlock(BlockMain block, ItemBlock itemblock, String name){
        ModelResourceLocation modelResourceLocation = new  ModelResourceLocation(Reference.TEXTURE.RESOURCE_PREFIX ,"inventory");
        GameRegistry.register(block,new ResourceLocation(Reference.MODINFO.MOD_ID + ":" + name));
        if (itemblock == null ){GameRegistry.register(new ItemBlock(block),block.getRegistryName());}
        else GameRegistry.register(itemblock ,block.getRegistryName());
        block.setUnlocalizedName(name);
        if (SideUtil.isClient()){
            ModelLoader.setCustomModelResourceLocation((new ItemBlock(block)),0,modelResourceLocation);
        }
        return block;
    }
}
