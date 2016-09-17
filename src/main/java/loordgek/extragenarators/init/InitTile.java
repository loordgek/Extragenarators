package loordgek.extragenarators.init;

import loordgek.extragenarators.tile.TileFurnaceGen;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitTile {
    public static void Init(){
        GameRegistry.registerTileEntity(TileFurnaceGen.class, "tilefurgen");
    }
}
