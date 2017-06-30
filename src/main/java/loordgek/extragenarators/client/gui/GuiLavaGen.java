package loordgek.extragenarators.client.gui;

import loordgek.extragenarators.client.gui.widgets.WidgetFire;
import loordgek.extragenarators.client.gui.widgets.WidgetPower;
import loordgek.extragenarators.client.gui.widgets.WidgetTankFixedSize;
import loordgek.extragenarators.container.container.ContainerLavaGen;
import loordgek.extragenarators.tile.TileLavaGen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiLavaGen extends GuiExtragenarators<TileLavaGen> {
    public GuiLavaGen(EntityPlayer player, TileLavaGen tile) {
        super(new ContainerLavaGen(player, tile), "lavagen", player, tile, "LavaGenerator");
    }

    @Override
    public void initialize() {
        addWidget(new WidgetFire(0, 120, 40, this, tile.fire));
        addWidget(new WidgetTankFixedSize(1, 90,10, this, tile.tank));
        addWidget(new WidgetPower(2,150,  10, this , tile.power));
    }
}

