package loordgek.extragenarators.client.gui.widgets;

import loordgek.extragenarators.client.RenderUtils;
import loordgek.extragenarators.client.gui.GuiExtragenarators;
import loordgek.extragenarators.util.Fire;
import loordgek.extragenarators.util.MathUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetFire extends WidgetBase {
    private final ResourceLocation firepng = new WidgetResourceLocation("fire");
    private final ResourceLocation firebackpng = new WidgetResourceLocation("fireback");
    private final Fire fire;
    public WidgetFire(int id, int x, int y, GuiExtragenarators gui, Fire fire) {
        super(id, x, y, 14, 14, gui);
        this.fire = fire;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        RenderUtils.drawWidgetStatic(this, firebackpng);
        if (fire.getFirecurrent() != 0){
            RenderUtils.drawWidgetUp(this, firepng, (int) MathUtil.reverseNumber(fire.getFirecurrent(), 0, fire.getFiremax()), fire.getFiremax());
        }
    }
}