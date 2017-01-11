package loordgek.extragenarators.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy
{
    EntityPlayer getclientplayer();
    void preinit(FMLPreInitializationEvent event);
    void setCustomModelResourceLocationitem(Item item, int meta, ModelResourceLocation location);
    Minecraft getMinecraft();

}
