package loordgek.extragenarators.proxy;

import loordgek.extragenarators.client.ModModelManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy implements IProxy {

    @Override
    public EntityPlayer getclientplayer() {
        return getMinecraft().player;
    }

    @Override
    public void setCustomModelResourceLocationItem(Item item, int meta, ModelResourceLocation location) {
        ModelLoader.setCustomModelResourceLocation(item, meta, location);
    }

    @Override
    public Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    @Override
    public ModModelManager getModelManager() {
        return ModModelManager.INSTANCE;
    }

}