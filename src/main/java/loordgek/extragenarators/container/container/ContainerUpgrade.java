package loordgek.extragenarators.container.container;

import loordgek.extragenarators.tile.TileGenBase;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerUpgrade extends ContainerExtragenarators<TileGenBase> {

    public ContainerUpgrade(EntityPlayer player, TileGenBase tileGenBase) {
        super(player, tileGenBase);

        addSlotMatrix(52, 26, 18, 18, te.upgradeinv, 0, 4, 2);
        addPlayerSlots(playerInv,8,84);
        addShiftClickingHandler(playerInv, te.upgradeinv);
        addShiftClickingHandler(te.upgradeinv, playerInv);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
