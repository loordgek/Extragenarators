package loordgek.extragenarators.network;

import loordgek.extragenarators.ref.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
/*
*    PneumaticCraft code. author = MineMaarten
*    https://github.com/MineMaarten/PneumaticCraft
*/
public class NetworkHandler {
    private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODINFO.MOD_NAME);

    public static void initNetwork(){
        INSTANCE.registerMessage(GuiSyncPacket.class, GuiSyncPacket.class, 0, Side.CLIENT);
    }

    public static void SentTo(IMessage message, EntityPlayerMP playerMP){
        INSTANCE.sendTo(message, playerMP);
    }
}

