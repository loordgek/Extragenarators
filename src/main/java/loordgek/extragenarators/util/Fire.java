package loordgek.extragenarators.util;

import loordgek.extragenarators.network.GuiSync;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class Fire implements INBTSerializable<NBTTagCompound>{
    @GuiSync
    public long firecurrent;
    @GuiSync
    public long firemax;

    public Fire(long firecurrent, long firemax) {
        this.firecurrent = firecurrent;
        this.firemax = firemax;
    }

    public Fire(){
        this(0, 0);
    }

    public long getFirecurrent() {
        return firecurrent;
    }

    public void setFirecurrent(long firecurrent) {
        this.firecurrent = firecurrent;
    }

    public long getFiremax() {
        return firemax;
    }

    public void setFiremax(long firemax) {
        this.firemax = firemax;
    }

    public void decrease(long amount){
        this.increase(-amount);
    }
    public void increase(long amount){
        this.setFirecurrent(this.firecurrent + amount);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setLong("firecurrent", firecurrent);
        compound.setLong("firemax", firemax);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        firecurrent = compound.getLong("firecurrent");
        firemax = compound.getLong("firemax");
    }
}
