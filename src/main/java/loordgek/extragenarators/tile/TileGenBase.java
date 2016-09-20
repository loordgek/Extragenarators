package loordgek.extragenarators.tile;

import loordgek.extragenarators.api.IUpgradeItem;
import loordgek.extragenarators.enums.EnumInvFlow;
import loordgek.extragenarators.network.*;
import loordgek.extragenarators.util.UpgradeUtil;
import loordgek.extragenarators.util.item.IInventoryOnwer;
import loordgek.extragenarators.util.item.InventorySimpleItemhander;
import loordgek.extragenarators.util.item.InventoryUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import java.util.List;

public class TileGenBase extends TileEntity implements IInventoryOnwer, IDescSync{

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

    public InventorySimpleItemhander upgradeinv = new InventorySimpleItemhander(64, 1,"upgrade",this);

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        upgradeinv.deserializeNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        upgradeinv.serializeNBT();
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

    @Override
    public void OnInventoryChanged(ItemStack stack, int slot, String name, EnumInvFlow flow) {
        if (name.equals("upgrade"))
            ReCalculateUpgrade();

    }

    @Override
    public void markDirty() {
        super.markDirty();
    }
}

