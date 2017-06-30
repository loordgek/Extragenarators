package loordgek.extragenarators.util;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluidUtil {



    public static boolean drainHeldfluidContainer(TileEntity tileEntity, EnumFacing hitSide, ItemStack itemFluidContainer, boolean doDrain){
        if (tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, hitSide) || (itemFluidContainer.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)))
            return false;

        boolean cando = true;
        IFluidHandler targetHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, hitSide);
        IFluidHandler itemFluidHander = itemFluidContainer.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        for (IFluidTankProperties tankProperties : itemFluidHander.getTankProperties()){
            if (tankProperties.getCapacity() == 0) cando = false;
           // if (targetHandler.)
        }

        return targetHandler.fill(itemFluidHander.drain(1000, doDrain), doDrain) != 0;
    }
}
