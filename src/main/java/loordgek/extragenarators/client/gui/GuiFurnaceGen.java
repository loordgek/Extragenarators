package loordgek.extragenarators.client.gui;

import loordgek.extragenarators.client.gui.widgets.WidgetFire;
import loordgek.extragenarators.client.gui.widgets.WidgetPower;
import loordgek.extragenarators.container.container.ContainerFurnaceGen;
import loordgek.extragenarators.tile.TileFurnaceGen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiFurnaceGen extends GuiExtragenarators {
    private final TileFurnaceGen te;
    public GuiFurnaceGen(EntityPlayer player, TileFurnaceGen te) {
        super(new ContainerFurnaceGen(player, te), "furnacegen", player);
        this.te = te;
        this.ySize = 200;
        this.xSize = 190;
    }

    @Override
    public void initialize() {
        addWidget(new WidgetPower(0, 150,  10, 16, 64, this , te.power));
        addWidget(new WidgetFire(1, 80, 45, this, te));
    }
}
