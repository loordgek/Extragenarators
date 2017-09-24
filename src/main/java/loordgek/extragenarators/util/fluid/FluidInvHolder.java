package loordgek.extragenarators.util.fluid;

import loordgek.extragenarators.container.slot.SlotInvHolder;
import loordgek.extragenarators.util.IShiftHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Optional;

public class FluidInvHolder implements IShiftHandler {
    private final IFluidHandler fluidTank;

    public FluidInvHolder(IFluidHandler fluidTank) {
        this.fluidTank = fluidTank;
    }

    @Override
    public Optional<ItemStack> handleShiftClick(SlotInvHolder from, EntityPlayer player) {
        ItemStack stack = from.getStack();
        if (!stack.isEmpty()){
            FluidUtil.tryEmptyContainerAndStow(stack, fluidTank, from.getInvHolder().wrapItemHandler(), Integer.MAX_VALUE, player);
        }
        return Optional.empty();
    }
}
