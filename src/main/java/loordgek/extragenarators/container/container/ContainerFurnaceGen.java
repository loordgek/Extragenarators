package loordgek.extragenarators.container.container;

import loordgek.extragenarators.container.slot.SlotFurnaceFuel;
import loordgek.extragenarators.tile.TileFurnaceGen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ContainerFurnaceGen extends ContainerExtragenarators<TileFurnaceGen> {
    TileFurnaceGen te;

    public ContainerFurnaceGen(EntityPlayer player, TileFurnaceGen te) {
        super(player, te);
        this.te = te;
        addSlotToContainer(new SlotFurnaceFuel(te.fuelSlot, 0, 79, 24));
        addPlayerSlots(player.inventory, 8, 84);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return null;
    }
}
