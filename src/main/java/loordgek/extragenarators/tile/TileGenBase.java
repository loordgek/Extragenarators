package loordgek.extragenarators.tile;

import loordgek.extragenarators.enums.EnumInvFlow;
import loordgek.extragenarators.nbt.NBTSave;
import loordgek.extragenarators.network.GuiSync;
import loordgek.extragenarators.network.GuiSyncInnerFields;
import loordgek.extragenarators.util.FacingUtil;
import loordgek.extragenarators.util.Fire;
import loordgek.extragenarators.util.ForgePower;
import loordgek.extragenarators.util.UpgradeUtil;
import loordgek.extragenarators.util.item.IInventoryOnwer;
import loordgek.extragenarators.util.item.InventoryUtil;
import loordgek.extragenarators.util.item.UpgradeInv;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class TileGenBase extends TileMain implements IInventoryOnwer {
    @GuiSync
    public int upgrademultiplier;
    @NBTSave
    @GuiSync
    public int maxmultiplier = 1;
    @GuiSync
    public float runspeed;
    @GuiSync
    public float upgradespeed;
    @NBTSave
    @GuiSyncInnerFields
    public Fire fire = new Fire();
    @GuiSync
    public boolean isburing;
    public int forgepower = 10;
    @NBTSave
    @GuiSyncInnerFields
    public ForgePower power = new ForgePower(50000);
    private final Map<BlockPos, IEnergyStorage> energyStorageMap = new HashMap<>();
    @NBTSave
    public UpgradeInv upgradeinv = new UpgradeInv(64, 8, "upgrade", this);

    protected void ReCalculateUpgrade() {
        upgrademultiplier = UpgradeUtil.getMultiplierBoost(InventoryUtil.getStacks(upgradeinv));
        power.Increasecapacity(UpgradeUtil.getPowerstoreBoost(InventoryUtil.getStacks(upgradeinv)));
        upgradespeed = UpgradeUtil.getSpeedBoost(InventoryUtil.getStacks(upgradeinv));
    }

    public boolean HasRoomForEnergy() {
        return power.getEnergyStored() < power.getMaxEnergyStored();
    }

    public boolean HasEnergy() {
        return power.getEnergyStored() > 0;
    }

    public boolean IsRunning() {
        return fire.getFirecurrent() > 0;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(power);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void OnInventoryChanged(ItemStack stack, int slot, String name, EnumInvFlow flow) {
    }

    @Override
    public void onNeighborChange(BlockPos neighbor) {
        scanNeighborForEnergy(neighbor);
    }

    private void scanNeighborForEnergy(BlockPos neighbor) {
        if (worldObj.isRemote) return;
        EnumFacing facing = FacingUtil.getFacingFromPos(pos, neighbor);

        TileEntity tileEntity = worldObj.getTileEntity(neighbor);
        if (tileEntity == null) return;
        if (!tileEntity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) return;
        IEnergyStorage energyStorage = tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
        if (energyStorageMap.containsKey(neighbor) && !(energyStorageMap.get(neighbor) == energyStorage)) {
            energyStorageMap.remove(neighbor);
        } else if (energyStorage.canReceive() && (!energyStorageMap.containsKey(neighbor))) {
            energyStorageMap.put(neighbor, energyStorage);
        }
    }

    protected void sendEnergy() {
        for (IEnergyStorage energyStorage : energyStorageMap.values()) {
            energyStorage.receiveEnergy(power.extractEnergyInternal(100000 / energyStorageMap.size(), false), false);
        }
    }

    @Override
    public void updateItemHandler() {
        if (!worldObj.isRemote) {
            ReCalculateUpgrade();
        }
    }

    protected void Burn() {
        isburing = true;
        runspeed = upgradespeed / maxmultiplier;
        float minburntime = Math.min(fire.getFirecurrent(), runspeed);
        fire.decrease((int) minburntime);
        power.floatreceiveEnergy(forgepower * minburntime, false);
        if (fire.getFirecurrent() <= 0) {
            fire.setFirecurrent(0);
            fire.setFiremax(0);
        }

    }

    @Override
    protected void firstTick() {
        ReCalculateUpgrade();
        for (EnumFacing facing : EnumFacing.VALUES) {
            scanNeighborForEnergy(pos.offset(facing));
        }
    }

    @Override
    public void breakBlock() {
        InventoryUtil.dropInv(worldObj, InventoryUtil.getStacks(upgradeinv), pos);
    }

    @Nullable
    @Override
    public World getWord() {
        return worldObj;
    }

    @Nullable
    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }
}

