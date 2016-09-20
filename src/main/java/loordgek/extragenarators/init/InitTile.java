package loordgek.extragenarators.init;

import loordgek.extragenarators.tile.TileFurnaceGen;
import loordgek.extragenarators.tile.TileNBTTest;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitTile {
    public static void Init(){
        GameRegistry.registerTileEntity(TileFurnaceGen.class, "tilefurgen");
        GameRegistry.registerTileEntity(TileNBTTest.class, "NBTtest");
    }
}
