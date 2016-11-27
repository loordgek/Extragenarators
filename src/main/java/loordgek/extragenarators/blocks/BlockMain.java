package loordgek.extragenarators.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public abstract class BlockMain extends Block {

    public BlockMain(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }
    public BlockMain(){
        super(Material.ROCK, MapColor.GRAY);
    }
    public abstract BlockMain getblock();

}
