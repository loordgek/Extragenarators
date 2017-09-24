package loordgek.extragenarators.util;

import loordgek.extragenarators.network.GuiSync;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public class ForgePower implements IEnergyStorage, INBTSerializable<NBTTagCompound>{
    protected int initialcapacity;
    @GuiSync protected long energy;
    @GuiSync protected long capacity;
    @GuiSync protected int maxReceive;
    @GuiSync protected int maxExtract;
    protected float Floatenergy;

    public ForgePower(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public ForgePower(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer);
    }

    public ForgePower(int capacity, int maxReceive, int maxExtract)
    {
        this.initialcapacity = capacity;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (canReceive())
            return receiveEnergyInternal(maxReceive, simulate);
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (canExtract())
            return extractEnergyInternal(maxExtract, simulate);
        return 0;
    }


    public int receiveEnergyInternal(int maxReceive, boolean simulate) {
        int energyReceived = (int) Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }
    public int extractEnergyInternal(int maxExtract, boolean simulate) {
        int energyExtracted = (int) Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

    public float floatreceiveEnergy(float maxReceive, boolean simulate) {
        float energyReceived = Math.min(capacity - Floatenergy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            Floatenergy += energyReceived;
            int floor_float = MathHelper.floor(Floatenergy);
            receiveEnergyInternal(floor_float, false);
            Floatenergy -= floor_float;
        }

        return energyReceived;
    }

    // TODO: 10-10-2016
    @Deprecated
    public float floatextractEnergy(float maxExtract, boolean simulate) {
        float energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            Floatenergy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return (int) energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) capacity;
    }

    @Override
    public boolean canExtract()
    {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive()
    {
        return false;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setDouble("energy", energy);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        energy = nbt.getLong("energy");

    }

    public void Increasecapacity(int TimesMultiplier){
        capacity = initialcapacity * TimesMultiplier;
    }
}
