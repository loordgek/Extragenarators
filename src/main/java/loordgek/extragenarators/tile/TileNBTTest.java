package loordgek.extragenarators.tile;

import loordgek.extragenarators.enums.EnumInvFlow;
import loordgek.extragenarators.nbt.NBTSave;
import loordgek.extragenarators.nbt.NBTUtil;
import loordgek.extragenarators.util.LogHelper;
import loordgek.extragenarators.util.item.IInventoryOnwer;
import loordgek.extragenarators.util.item.InventorySimpleItemhander;
import loordgek.extragenarators.util.item.InventoryUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TileNBTTest extends TileEntity implements IInventoryOnwer, ITickable {
    @NBTSave public int lol;
    @NBTSave public InventorySimpleItemhander simpleItemhander = new InventorySimpleItemhander(64, 1, "test", this);
    int timer;


    List<Field> fieldList = new ArrayList<Field>();

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        try {
            NBTUtil.getFieldsread(this, fieldList, compound.getCompoundTag("lol"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //  NBTUtil.getFieldsread(this, fieldList, compound);
        LogHelper.info(compound);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        try {
            if (NBTUtil.getFieldswrite(this, fieldList, compound) != null) {
                try {
                    compound.setTag("lol", NBTUtil.getFieldswrite(this, fieldList, compound));
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

    private void addFields(Object annotatedObject){
        this.fieldList = NBTUtil.GetFields(annotatedObject, NBTSave.class);
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){

        simpleItemhander.insertItem(0, new ItemStack(Items.ITEM_FRAME ), false);
        lol = 23;
    }

    @Override
    public void onLoad() {
        addFields(this);
        if (!worldObj.isRemote){
            LogHelper.info("load");
            LogHelper.info(fieldList);

        }
    }


    @Override
    public void OnInventoryChanged(ItemStack stack, int slot, String name, EnumInvFlow flow) {

    }

    @Override
    public void update() {
        if (!worldObj.isRemote){
            timer++;
            if (timer == 40){
                timer = 0;
                update2sec();
            }
        }
    }
    public void update2sec(){
        LogHelper.info(simpleItemhander);
        LogHelper.info(simpleItemhander.getStackInSlot(0));
        NBTTagCompound compound = new NBTTagCompound();
    }
}
