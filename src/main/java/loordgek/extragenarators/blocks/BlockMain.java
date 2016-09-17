package loordgek.extragenarators.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;

public class BlockMain extends Block {

    public BlockMain(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }
    public BlockMain(){
        super(Material.ROCK, MapColor.GRAY);
    }

    public static int Meta(){
        return 0;
    }
}
