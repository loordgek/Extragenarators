package loordgek.extragenarators.client.gui;

import loordgek.extragenarators.client.gui.widgets.WidgetFire;
import loordgek.extragenarators.client.gui.widgets.WidgetPower;
import loordgek.extragenarators.client.gui.widgets.WidgetTank;
import loordgek.extragenarators.container.container.ContainerLavaGen;
import loordgek.extragenarators.tile.TileLavaGen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiLavaGen extends GuiExtragenarators<TileLavaGen> {
    public GuiLavaGen(EntityPlayer player, TileLavaGen tile) {
        super(new ContainerLavaGen(player, tile), "lavagen", player, tile);
    }

    @Override
    public void initialize() {
        addWidget(new WidgetFire(0, 60, 60, this, tile));
        addWidget(new WidgetTank(1, 30,30,  this, tile.fluidTank));
        addWidget(new WidgetPower(2,70,70, this , tile.power));
    }
}

