package loordgek.extragenarators.blocks;

import loordgek.extragenarators.items.itemblock.ItemBlockGenBase;
import loordgek.extragenarators.items.itemblock.ItemBlockMain;

//@GameRegistry.ObjectHolder(Reference.MODINFO.MOD_ID)
public class Blocks {
    public static final BlockGenBase GEN = new BlockGenBase();
    public static final ItemBlockMain GenITEM = new ItemBlockGenBase(GEN);
}
