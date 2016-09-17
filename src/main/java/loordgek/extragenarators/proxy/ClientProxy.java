package loordgek.extragenarators.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;


public class ClientProxy extends CommonProxy {


    @Override
    public EntityPlayer getclientplayer() {
        return Minecraft.getMinecraft().thePlayer;
    }


}


