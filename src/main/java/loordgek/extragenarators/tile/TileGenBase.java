package loordgek.extragenarators.tile;

import loordgek.extragenarators.api.IUpgradeItem;
import loordgek.extragenarators.enums.EnumInvFlow;
import loordgek.extragenarators.nbt.NBTSave;
import loordgek.extragenarators.network.GuiSync;
import loordgek.extragenarators.network.GuiSyncInnerFields;
import loordgek.extragenarators.util.FacingUtil;
import loordgek.extragenarators.util.Fire;
import loordgek.extragenarators.util.ForgePower;
import loordgek.extragenarators.util.UpgradeUtil;
import loordgek.extragenarators.util.item.IInventoryOwner;
import loordgek.extragenarators.util.item.InventoryUtil;
import loordgek.extragenarators.util.item.UpgradeInv;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class TileGenBase extends TileMain implements IInventoryOwner {
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
    private final Map<EnumFacing, TileEntity> entityEnumFacingMap = new HashMap<>();
    @NBTSave
    public UpgradeInv upgradeinv = new UpgradeInv(64, 8, this){
        @Override
        public boolean isStackValidForSlot(int Slot, ItemStack stack) {
            return !stack.isEmpty() && stack.getItem() instanceof IUpgradeItem;
        }
    };

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
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(power);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void neighborChanged(BlockPos fromPos, Block oldBlock, IBlockState newState) {
        scanNeighborForTileEntity(fromPos);
    }

    private void scanNeighborForTileEntity(BlockPos neighbor) {
        if (world.isRemote) return;
        EnumFacing facing = FacingUtil.getFacingFromPos(pos, neighbor);

        TileEntity tileEntity = world.getTileEntity(neighbor);
        if (tileEntity == null){
            if (entityEnumFacingMap.containsKey(facing)){
                entityEnumFacingMap.remove(tileEntity);
            }
        }
        else {
            if (!entityEnumFacingMap.containsKey(facing) || !(entityEnumFacingMap.get(facing) == tileEntity)){
                entityEnumFacingMap.put(facing, tileEntity);            }
        }
    }

    protected void sendEnergy() {
        for (Map.Entry<EnumFacing, TileEntity> entry : entityEnumFacingMap.entrySet()){
           if (entry.getValue().hasCapability(CapabilityEnergy.ENERGY, entry.getKey().getOpposite())){
              IEnergyStorage energyStorage = entry.getValue().getCapability(CapabilityEnergy.ENERGY, entry.getKey().getOpposite());
              if (energyStorage.canReceive()) energyStorage.receiveEnergy(50000, false);
           }
        }
    }

    @Override
    public void OnInventoryChanged(ItemStack stack, int slot, IItemHandler itemHandler, EnumInvFlow flow) {

    }

    @Override
    public void updateItemHandler() {
        if (!world.isRemote) {
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
            scanNeighborForTileEntity(pos.offset(facing));
        }
    }

    @Override
    public void breakBlock() {
        InventoryUtil.dropInv(world, upgradeinv, pos);
    }

    @Nullable
    @Override
    public World getWord() {
        return world;
    }

    @Nullable
    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }
}

