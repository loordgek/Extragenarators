package loordgek.extragenarators.client.gui.widgets;

import loordgek.extragenarators.client.RenderUtils;
import loordgek.extragenarators.client.gui.GuiExtragenarators;
import loordgek.extragenarators.util.MathUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.DecimalFormat;
import java.util.List;

@SideOnly(Side.CLIENT)
public class WidgetPower extends WidgetBase {
    private final IEnergyStorage energyStorage;
    private static final ResourceLocation powerbarback = new WidgetResourceLocation("powerbarback");
    private static final ResourceLocation powerbar = new WidgetResourceLocation("powerbar");
    private static final DecimalFormat energyformat = new DecimalFormat("###.###");

    public WidgetPower(int id, int x, int y, int width, int height, GuiExtragenarators gui, IEnergyStorage energyStorage) {
        super(id, x, y, width, height, gui);
        this.energyStorage = energyStorage;
    }
    public WidgetPower(int id, int x, int y, GuiExtragenarators gui, IEnergyStorage energyStorage) {
        this(id, x, y, 16, 64, gui, energyStorage);

    }

    @Override
    public void render(int mouseX, int mouseY) {
        RenderUtils.drawWidgetStatic(this, powerbarback);
        RenderUtils.drawWidgetUp(this, powerbar, (int) MathUtil.getpercentagereverse(energyStorage.getEnergyStored() , energyStorage.getMaxEnergyStored()), 100D);
    }

    @Override
    public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift, EntityPlayer player) {
        if (shift){
            tooltips.add(Integer.toString(energyStorage.getEnergyStored()) + "/");
            tooltips.add(Integer.toString(energyStorage.getMaxEnergyStored()));
        }
        else tooltips.add(energyformat.format(MathUtil.getpercentage(energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored())) + " &");
    }
}
