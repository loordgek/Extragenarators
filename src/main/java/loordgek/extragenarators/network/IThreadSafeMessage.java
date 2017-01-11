package loordgek.extragenarators.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public interface IThreadSafeMessage extends IMessage {
    boolean isThreadSafe();
}
