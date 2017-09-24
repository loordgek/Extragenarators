package loordgek.extragenarators.network;

import loordgek.extragenarators.Extragenarators;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
/*
*    PneumaticCraft code. author = MineMaarten
*    https://github.com/MineMaarten/PneumaticCraft
*/
public abstract class AbstractPacket <REQ extends IThreadSafeMessage> implements IThreadSafeMessage ,IMessageHandler<REQ, REQ> {

    @Override
    public  REQ onMessage(REQ message, MessageContext ctx) {
        if(ctx.side == Side.SERVER) {
            EntityPlayer player = ctx.getServerHandler().player;
            if (message.isThreadSafe()){
                handleServerSide(message, player, true);
            }
            else {
                IThreadListener serverside = (WorldServer)player.world;
                serverside.addScheduledTask(() -> handleServerSide(message, player, false));
            }
        } else {
            if (message.isThreadSafe()){
                handleClientSide(message, Extragenarators.proxy.getclientplayer(), true);
            }
            else {
                IThreadListener clientside = Extragenarators.proxy.getMinecraft();
                clientside.addScheduledTask(() -> handleClientSide(message, Extragenarators.proxy.getclientplayer(), false));
            }
        }
        return null;
    }

    public abstract void handleClientSide(REQ message, EntityPlayer player, boolean handledFromNettyThread);

    public abstract void handleServerSide(REQ message, EntityPlayer player, boolean handledFromNettyThread);
}
