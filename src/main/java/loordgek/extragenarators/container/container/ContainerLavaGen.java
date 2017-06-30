package loordgek.extragenarators.container.container;

import loordgek.extragenarators.tile.TileLavaGen;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerLavaGen extends ContainerExtragenarators<TileLavaGen> {


    public ContainerLavaGen(EntityPlayer player, TileLavaGen te) {

        super(player, te);
        addPlayerSlots(player.inventory, 50, 50);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
