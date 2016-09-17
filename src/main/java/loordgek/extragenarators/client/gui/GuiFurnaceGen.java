package loordgek.extragenarators.client.gui;

import loordgek.extragenarators.container.container.ContainerFurnaceGen;
import loordgek.extragenarators.tile.TileFurnaceGen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class GuiFurnaceGen extends GuiExtragenarators {
    public GuiFurnaceGen(EntityPlayer player , TileFurnaceGen tileFurnaceGen) {
        super(new ContainerFurnaceGen(player, tileFurnaceGen), "furnacegen");
    }
}
