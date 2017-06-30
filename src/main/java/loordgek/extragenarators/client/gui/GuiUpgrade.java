package loordgek.extragenarators.client.gui;

import loordgek.extragenarators.container.container.ContainerUpgrade;
import loordgek.extragenarators.tile.TileGenBase;
import net.minecraft.entity.player.EntityPlayer;

public class GuiUpgrade extends GuiExtragenarators<TileGenBase> {
    public GuiUpgrade(EntityPlayer player, TileGenBase tileGenBase) {
        super(new ContainerUpgrade(player, tileGenBase), "upgradegui", player, tileGenBase, "Upgrade");
    }
}
