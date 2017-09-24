package loordgek.extragenarators.container.container;

import loordgek.extragenarators.tile.TileLavaGen;
import loordgek.extragenarators.util.fluid.FluidInvHolder;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerLavaGen extends ContainerExtragenarators<TileLavaGen> {

    public ContainerLavaGen(EntityPlayer player, TileLavaGen te) {

        super(player, te);

        addPlayerSlots(playerInv,  8, 84);
        FluidInvHolder fluidInvHolder = new FluidInvHolder(te.tank);
        addShiftClickingHandler(playerInv, fluidInvHolder);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
