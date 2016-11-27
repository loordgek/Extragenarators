package loordgek.extragenarators.client.gui.widgets;

import loordgek.extragenarators.client.RenderUtils;
import loordgek.extragenarators.client.gui.GuiExtragenarators;
import loordgek.extragenarators.util.PowerUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class WidgetPower extends WidgetBase {
    private final IEnergyStorage energyStorage;
    private static final ResourceLocation arrow = new WidgetResourceLocation("arrowright");

    public WidgetPower(int id, int x, int y, int width, int height, GuiExtragenarators gui, IEnergyStorage energyStorage) {
        super(id, x, y, width, height, gui);
        this.energyStorage = energyStorage;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        RenderUtils.drawWidgetUp(this, arrow, RenderUtils.reverseNumber(energyStorage.getEnergyStored(), 0, energyStorage.getMaxEnergyStored()), energyStorage.getMaxEnergyStored());
    }

    @Override
    public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
        List<String> stringList = new ArrayList<String>();
        if (shift){
            stringList.add(Integer.toString(energyStorage.getEnergyStored())+ "/");
            stringList.add(Integer.toString(energyStorage.getMaxEnergyStored()));
        }
        else stringList.add(Double.toString(PowerUtil.getpercentageEnergyfilled(energyStorage)) + " &");
        tooltips.addAll(stringList);

    }
}
