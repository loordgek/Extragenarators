package loordgek.extragenarators.tile;

import loordgek.extragenarators.api.IUpgradeItem;
import loordgek.extragenarators.enums.EnumInvFlow;
import loordgek.extragenarators.nbt.NBTSave;
import loordgek.extragenarators.network.GuiSync;
import loordgek.extragenarators.util.UpgradeUtil;
import loordgek.extragenarators.util.item.IInventoryOnwer;
import loordgek.extragenarators.util.item.InventorySimpleItemhander;
import loordgek.extragenarators.util.item.InventoryUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class TileGenBase extends TileMain implements IInventoryOnwer {


    @NBTSave @GuiSync public int maxpowercapacity = 1000000;
    @NBTSave @GuiSync public int upgradepowercapacity;
    @NBTSave @GuiSync public int upgrademultiplier;
    @NBTSave @GuiSync public int upgradespeed;
    @NBTSave @GuiSync public int burntime;
    @NBTSave @GuiSync public int currentburntime;
    @GuiSync public boolean isburing;
    public int forgepower = 10;

    @NBTSave @GuiSync public EnergyStorage energyStorage = new EnergyStorage(maxpowercapacity);

    @NBTSave public InventorySimpleItemhander upgradeinv = new InventorySimpleItemhander(64, 1,"upgrade",this);

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

