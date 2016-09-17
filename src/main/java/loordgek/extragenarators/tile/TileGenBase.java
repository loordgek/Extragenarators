package loordgek.extragenarators.tile;

import loordgek.extragenarators.api.IUpgradeItem;
import loordgek.extragenarators.network.*;
import loordgek.extragenarators.util.UpgradeUtil;
import loordgek.extragenarators.util.item.IInventoryOnwerIinv;
import loordgek.extragenarators.util.item.InventorySimpleIinv;
import loordgek.extragenarators.util.item.InventoryUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import java.util.List;

public class TileGenBase extends TileEntity implements IInventoryOnwerIinv, IDescSync{

    @GuiSync public int maxpowercapacity = 1000000;
    @GuiSync public int upgradepowercapacity;
    @GuiSync public int upgrademultiplier;
    @GuiSync public int upgradespeed;
    @GuiSync public int burntime;
    @GuiSync public int currentburntime;
    @GuiSync public boolean isburing;
    public int forgepower = 10;
    private List<SyncField> descriptionFields;
    @GuiSync public EnergyStorage energyStorage = new EnergyStorage(maxpowercapacity);

    public InventorySimpleIinv upgradeinv = new InventorySimpleIinv("upgrade",64,8,this );

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        upgradeinv.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        upgradeinv.readFromNBT(compound);
    }

    @Override
    public void onInventoryChanged(IInventory inventoryowner) {
        markDirty();
        if (inventoryowner.getName().equals("upgrade")){
            ReCalculateUpgrade();
        }

    }

    private void ReCalculateUpgrade(){
        if (upgradeinv.getStackInSlot(0) != null){
            if (upgradeinv.getStackInSlot(0).getItem() instanceof IUpgradeItem){
                upgrademultiplier = UpgradeUtil.getMultiplierBoost(InventoryUtil.getStacks(upgradeinv));
                upgradepowercapacity = UpgradeUtil.getPowerstoreBoost(InventoryUtil.getStacks(upgradeinv));
                upgradespeed = UpgradeUtil.getSpeedBoost(InventoryUtil.getStacks(upgradeinv));
            }
        }
    }
    public void onGuiUpdate(){}

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
    public void writeToPacket(NBTTagCompound tag) {}

    @Override
    public void readFromPacket(NBTTagCompound tag) {}

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
    public boolean HasRoomForEnergy(){
        return energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored();
    }

    public int AddPowerToStorage() {
        return MathHelper.clamp_int(forgepower + energyStorage.getMaxEnergyStored(), 0, energyStorage.getMaxEnergyStored());
    }

    public boolean IsRunning(){
        return currentburntime > 0;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY){
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        return super.getCapability(capability, facing);
    }
}

