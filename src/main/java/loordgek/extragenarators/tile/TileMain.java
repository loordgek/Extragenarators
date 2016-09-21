package loordgek.extragenarators.tile;

import loordgek.extragenarators.nbt.NBTSave;
import loordgek.extragenarators.nbt.NBTUtil;
import loordgek.extragenarators.network.DescSync;
import loordgek.extragenarators.network.IDescSync;
import loordgek.extragenarators.network.NetworkUtils;
import loordgek.extragenarators.network.SyncField;
import loordgek.extragenarators.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TileMain extends TileEntity implements ITickable, IDescSync {
    private List<Field> NBTfieldlist = new ArrayList<Field>();
    private List<SyncField> descriptionFields;
    private int timer;
    private boolean tickonetime = true;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        try {
            NBTUtil.getFieldsread(this, compound.getCompoundTag("lol"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //  NBTUtil.getFieldsread(this, NBTfieldlist, compound);
        LogHelper.info(compound);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        try {
            if (NBTUtil.getFieldswrite(this, NBTfieldlist) != null) {
                try {
                    compound.setTag("lol", NBTUtil.getFieldswrite(this, NBTfieldlist));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return compound;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        LogHelper.info(compound);
        return compound;
    }

    private void addFields(Object annotatedObject) {
        this.NBTfieldlist = NBTUtil.GetFields(annotatedObject, NBTSave.class);
    }

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            updateserverside();
            timer++;
            if (timer == 10){
                if (tickonetime){
                    tickonetimeafter10ticks();
                    tickonetime = false;
                }
            }
            if (timer == 40) {
                timer = 0;
                update2sec();
            }
        }
    }
    private void updateserverside(){}

    public void update2sec() {}

    private void tickonetimeafter10ticks(){
        if (!worldObj.isRemote) {
            LogHelper.info("load");
            addFields(worldObj.getTileEntity(pos));
            LogHelper.info(NBTfieldlist);
        }

    }

    @Override
    public Type getSyncType() {
        return Type.TILE_ENTITY;
    }

    @Override
    public List<SyncField> getDescriptionFields() {
        if(descriptionFields == null) {
            descriptionFields = NetworkUtils.getSyncFields(this, DescSync.class);
            for(SyncField field : descriptionFields) {
                field.update();
            }
        }
        return descriptionFields;
    }

    @Override
    public void writeToPacket(NBTTagCompound tag) {

    }

    @Override
    public void readFromPacket(NBTTagCompound tag) {

    }

    @Override
    public int getX() {
        return pos.getX();
    }

    @Override
    public int getY() {
        return pos.getY();
    }

    @Override
    public int getZ() {
        return pos.getZ();
    }

    @Override
    public void onDescUpdate() {

    }
}
