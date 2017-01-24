package loordgek.extragenarators.container.container;

import loordgek.extragenarators.container.slot.SlotUpgrade;
import loordgek.extragenarators.tile.TileGenBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ContainerUpgrade extends ContainerExtragenarators<TileGenBase> {

    public ContainerUpgrade(EntityPlayer player, TileGenBase tileGenBase) {
        super(tileGenBase);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                addSlotToContainer(new SlotUpgrade(tileGenBase.upgradeinv,i + j * 2, 8 + j * 18, 18 + i * 18));
            }
        }
        addSlotListToContainer(tileGenBase.upgradeinv, 50, 50, 2, 4);
        addPlayerSlots(player.inventory,4,80);
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
