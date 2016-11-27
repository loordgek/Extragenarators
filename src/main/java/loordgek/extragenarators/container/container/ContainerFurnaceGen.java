package loordgek.extragenarators.container.container;

import loordgek.extragenarators.container.slot.SlotFurnaceFuel;
import loordgek.extragenarators.tile.TileFurnaceGen;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerFurnaceGen extends ContainerExtragenarators<TileFurnaceGen> {
    TileFurnaceGen te;

    public ContainerFurnaceGen(EntityPlayer player, TileFurnaceGen te) {
        super(te);
        this.te = te;
        addSlotToContainer(new SlotFurnaceFuel(te.fuelSlot, 0, 50, 50));
        addPlayerSlots(player.inventory, 80, 80);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
