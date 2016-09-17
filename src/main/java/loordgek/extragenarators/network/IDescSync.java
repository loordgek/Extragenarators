package loordgek.extragenarators.network;

import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public interface IDescSync {

    public static enum Type{
        TILE_ENTITY, SEMI_BLOCK;
    }
    Type getSyncType();

    List<SyncField> getDescriptionFields();

    void writeToPacket(NBTTagCompound tag);

    void readFromPacket(NBTTagCompound tag);

    int getX();

    int getY();

    int getZ();

    void onDescUpdate();

}
