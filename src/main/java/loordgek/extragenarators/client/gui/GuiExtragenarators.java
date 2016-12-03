package loordgek.extragenarators.client.gui;

import loordgek.extragenarators.client.gui.widgets.WidgetBase;
import loordgek.extragenarators.ref.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiExtragenarators extends GuiContainer {
    private final EntityPlayer player;
    private final ResourceLocation guiTexture;
    private List<WidgetBase> widgetBaseList = new ArrayList<WidgetBase>();

    public GuiExtragenarators(Container container, String guiTextureName, EntityPlayer player){
        super(container);
        guiTexture = new ResourceLocation(Reference.MODINFO.MOD_ID + ":textures/gui/" + guiTextureName + ".png");
        this.player = player;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY){
        mc.getTextureManager().bindTexture(guiTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        drawWidgets(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = "UpGrade";
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (WidgetBase widgetBase : widgetBaseList){
            if (widgetBase.getBounds().contains(mouseX, mouseY) && widgetBase.enabled)
                widgetBase.onWidgetClicked(mouseX, mouseY, mouseButton, player);
        }
    }

    public void addWidget(WidgetBase widget) {
        widgetBaseList.add(widget);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        List<String> tooltips = new ArrayList<String>();

        for (WidgetBase widget : widgetBaseList)
            if (widget.getBounds().contains(x, y))
                widget.addTooltip(x, y, tooltips, isShiftKeyDown(), player);

        if (!tooltips.isEmpty()) {
            List<String> finalLines = new ArrayList<String>();
            for (String line : tooltips) {
                String[] lines = WordUtils.wrap(line, 30).split(System.getProperty("line.separator"));
                Collections.addAll(finalLines, lines);
            }
            drawHoveringText(finalLines, x, y, fontRendererObj);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        initialize();
    }

    public void redraw() {
        widgetBaseList.clear();
        buttonList.clear();
        initialize();
    }

    public void initialize() {}

    protected void drawWidgets(int x, int y) {
        for (WidgetBase widget : widgetBaseList) {
            widget.render(x, y);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public float getzlevel(){
        return zLevel;
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        widgetBaseList.clear();
        super.setWorldAndResolution(mc, width, height);
    }
    public int getguitop(){
        return guiTop;
    }

    public int getguileft(){
        return guiLeft;
    }

}
