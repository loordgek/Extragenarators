package loordgek.extragenarators.container.container;

import loordgek.extragenarators.container.slot.SlotInvHolder;
import loordgek.extragenarators.tile.TileFurnaceGen;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerFurnaceGen extends ContainerExtragenarators<TileFurnaceGen> {

    public ContainerFurnaceGen(EntityPlayer player, TileFurnaceGen te) {
        super(player, te);
        addSlotToContainer(new SlotInvHolder(te.fuelSlot, 0, 79, 24));
        addPlayerSlots(playerInv, 8, 84);
        addShiftClickingHandler(playerInv, te.fuelSlot);
        addShiftClickingHandler(te.fuelSlot, playerInv);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
