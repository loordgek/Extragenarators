package loordgek.extragenarators.client;

import loordgek.extragenarators.client.gui.widgets.WidgetBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderUtils {
    public static final ResourceLocation BLOCK_TEX = TextureMap.LOCATION_BLOCKS_TEXTURE;

    public static TextureAtlasSprite getStillTexture(FluidStack fluid) {
        if (fluid == null || fluid.getFluid() == null) {
            return null;
        }
        return getStillTexture(fluid.getFluid());
    }

    public static TextureAtlasSprite getStillTexture(Fluid fluid) {
        ResourceLocation iconKey = fluid.getStill();
        if (iconKey == null) {
            return null;
        }
        return Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(iconKey.toString());
    }

    public static void renderGuiTank(IFluidTank tank, double x, double y, double width, double height) {
        renderGuiTank(tank.getFluid(), tank.getCapacity(), tank.getFluidAmount(), x, y, width, height);
    }

    public static void renderGuiTank(FluidStack fluid, int capacity, int amount, double x, double y, double width, double height) {
        if (fluid == null || fluid.getFluid() == null || fluid.amount <= 0) {
            return;
        }

        TextureAtlasSprite sprite = getStillTexture(fluid);
        if (sprite == null) {
            return;
        }

        int renderAmount = (int) Math.max(Math.min(height, amount * height / capacity), 1);
        int posY = (int) (y + height - renderAmount);

        bindBlockTexture();
        int color = fluid.getFluid().getColor(fluid);
        GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));

        GlStateManager.enableBlend();
        for (int i = 0; i < width; i += 16) {
            for (int j = 0; j < renderAmount; j += 16) {
                int drawWidth = (int) Math.min(width - i, 16);
                int drawHeight = Math.min(renderAmount - j, 16);

                int drawX = (int) (x + i);
                int drawY = posY + j;

                double minU = sprite.getMinU();
                double maxU = sprite.getMaxU();
                double minV = sprite.getMinV();
                double maxV = sprite.getMaxV();

                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer tes = tessellator.getBuffer();
                tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                tes.pos(drawX, drawY + drawHeight, 0).tex(minU, minV + (maxV - minV) * drawHeight / 16F).endVertex();
                tes.pos(drawX + drawWidth, drawY + drawHeight, 0).tex(minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F).endVertex();
                tes.pos(drawX + drawWidth, drawY, 0).tex(minU + (maxU - minU) * drawWidth / 16F, minV).endVertex();
                tes.pos(drawX, drawY, 0).tex(minU, minV).endVertex();
                tessellator.draw();
            }
        }
        GlStateManager.disableBlend();
    }

    public static TextureManager getTextureManager() {
        return Minecraft.getMinecraft().renderEngine;
    }

    public static void bindBlockTexture() {
        getTextureManager().bindTexture(BLOCK_TEX);
    }

    public static void drawWidgetRight(WidgetBase widgetBase, ResourceLocation location, int currunt, int max) {
        getTextureManager().bindTexture(location);
        drawModalRectWithCustomSizedTexture(widgetBase.x, widgetBase.y, 0, 0, widgetBase.width, widgetBase.height, widgetBase.width, widgetBase.height, Drawgui.RIGHT, scale(currunt, max, widgetBase.width));
    }

    public static void drawWidgetLeft(WidgetBase widgetBase, ResourceLocation location, int currunt, int max) {
        getTextureManager().bindTexture(location);
        drawModalRectWithCustomSizedTexture(widgetBase.x, widgetBase.y, 0, 0, scalereverse(currunt, max, widgetBase.width), widgetBase.height, widgetBase.width, widgetBase.height, Drawgui.LEFT, currunt);
    }

    public static void drawWidgetDown(WidgetBase widgetBase, ResourceLocation location, int currunt, int max) {
        getTextureManager().bindTexture(location);
        drawModalRectWithCustomSizedTexture(widgetBase.x, widgetBase.y, 0, 0, widgetBase.width, scalereverse(currunt, max, widgetBase.height), widgetBase.width, widgetBase.height, Drawgui.DOWN, currunt);
    }

    public static void drawWidgetUp(WidgetBase widgetBase, ResourceLocation location, int currunt, int max) {
        getTextureManager().bindTexture(location);
        drawModalRectWithCustomSizedTexture(widgetBase.x, widgetBase.y, 0, 0, widgetBase.width, widgetBase.height, widgetBase.width, widgetBase.height, Drawgui.UP, scale(currunt, max, widgetBase.height));
    }

    public static void drawWidgetStatic(WidgetBase widgetBase, ResourceLocation location) {
        getTextureManager().bindTexture(location);
        Gui.drawModalRectWithCustomSizedTexture(widgetBase.x, widgetBase.y, 0, 0, widgetBase.width, widgetBase.height, widgetBase.width, widgetBase.height);
    }

    public static int scale(int currentint, int maxint, int size) {
        return currentint * size / maxint;
    }

    public static int reverseNumber(int num, int min, int max) {
        return (max + min) - num;
    }

    public static int scalereverse(int currentint, int maxint, int size) {
        return reverseNumber(currentint, 0, maxint) * size / maxint;
    }
    public enum Drawgui{
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight, Drawgui drawgui, int current) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        int drawup = Drawgui.UP == drawgui ? current : 0;
        int drawright = Drawgui.RIGHT == drawgui ? current : 0;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double) x + drawright, (double) (y + height), 0.0D).tex((double) ((u + (float) drawright)* f), (double) ((v + (float) height) * f1)).endVertex();
        vertexbuffer.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
        vertexbuffer.pos((double) (x + width), (double) y + drawup, 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) drawup) * f1)).endVertex();
        vertexbuffer.pos((double) x + drawright, (double) y + drawup, 0.0D).tex((double) ((u + (float) drawright)* f), (double) ((v + (float) drawup) * f1)).endVertex();
        tessellator.draw();
    }
}