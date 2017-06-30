package loordgek.extragenarators.init;

import loordgek.extragenarators.tile.TileFurnaceGen;
import loordgek.extragenarators.tile.TileLavaGen;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitTile {
    public static void Init(){
        GameRegistry.registerTileEntity(TileFurnaceGen.class, "tilefurnacegen");
        GameRegistry.registerTileEntity(TileLavaGen.class, "tilelavagen");
    }
}
