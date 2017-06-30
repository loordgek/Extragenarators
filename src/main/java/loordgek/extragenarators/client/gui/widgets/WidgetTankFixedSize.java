package loordgek.extragenarators.client.gui.widgets;

import loordgek.extragenarators.client.RenderUtils;
import loordgek.extragenarators.client.gui.GuiExtragenarators;
import net.minecraftforge.fluids.IFluidTank;

public class WidgetTankFixedSize extends WidgetTank{
    private static final WidgetResourceLocation tankstripes = new WidgetResourceLocation("tankstripes");
    private static final WidgetResourceLocation tankback = new WidgetResourceLocation("tankback");
    public WidgetTankFixedSize(int id, int x, int y, GuiExtragenarators gui, IFluidTank tank) {
        super(id, x, y,16, 64, gui, tank);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        RenderUtils.drawWidgetStatic(this, tankback);
        if (getTank().getFluidAmount() > 0)
            RenderUtils.renderGuiTank(getTank(), x + 1, y, width - 2, height - 1);
        RenderUtils.drawWidgetStatic(this, tankstripes);
    }
}
