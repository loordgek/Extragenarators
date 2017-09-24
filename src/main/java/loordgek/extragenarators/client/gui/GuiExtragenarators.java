package loordgek.extragenarators.client.gui;

import loordgek.extragenarators.client.gui.widgets.WidgetBase;
import loordgek.extragenarators.ref.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
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
public class GuiExtragenarators<TE extends TileEntity> extends GuiContainer {
    protected final TE tile;
    private final EntityPlayer player;
    private final ResourceLocation guiTexture;
    private List<WidgetBase> widgetBaseList = new ArrayList<>();
    private final String name;

    public GuiExtragenarators(Container container, String guiTextureName, EntityPlayer player, TE tile, String name){
        super(container);
        this.tile = tile;
        this.player = player;
        guiTexture = new ResourceLocation(Reference.MODINFO.MOD_ID + ":textures/gui/" + guiTextureName + ".png");
        this.name = name;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY){
        mc.getTextureManager().bindTexture(guiTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        drawWidgets(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        fontRenderer.drawString(name, 8, 6, 4210752);
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
        List<String> tooltips = new ArrayList<>();

        for (WidgetBase widget : widgetBaseList)
            if (widget.getBounds().contains(x, y))
                widget.addTooltip(x, y, tooltips, isShiftKeyDown(), player);

        if (!tooltips.isEmpty()) {
            List<String> finalLines = new ArrayList<String>();
            for (String line : tooltips) {
                String[] lines = WordUtils.wrap(line, 30).split(System.getProperty("line.separator"));
                Collections.addAll(finalLines, lines);
            }
            drawHoveringText(finalLines, x, y, fontRenderer);
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
