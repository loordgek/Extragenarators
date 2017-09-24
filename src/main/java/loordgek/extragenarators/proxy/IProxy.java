package loordgek.extragenarators.proxy;

import loordgek.extragenarators.client.ModModelManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public interface IProxy
{
    EntityPlayer getclientplayer();
    void setCustomModelResourceLocationItem(Item item, int meta, ModelResourceLocation location);
    Minecraft getMinecraft();
    ModModelManager getModelManager();

}
