package loordgek.extragenarators.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy {
    @Override
    public EntityPlayer getclientplayer() {return null;}

    @Override
    public void preinit(FMLPreInitializationEvent event) {}

    @Override
    public void setCustomModelResourceLocationitem(Item item, int meta, ModelResourceLocation location) {}
}


