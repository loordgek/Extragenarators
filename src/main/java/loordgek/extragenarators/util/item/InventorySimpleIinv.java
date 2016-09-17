package loordgek.extragenarators.util.item;

import loordgek.extragenarators.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class InventorySimpleIinv implements IInventory{
    private final String name;
    private final int maxstacksize;
    private final int invsize;
    private ItemStack[] stacks;
    private final IInventoryOnwerIinv onwer;

    public InventorySimpleIinv(String name, int maxstacksize, int invsize, IInventoryOnwerIinv onwer) {
        this.name = name;
        this.maxstacksize = maxstacksize;
        this.invsize = invsize;
        this.stacks = new ItemStack[invsize];
        this.onwer = onwer;
    }

    public void readFromNBT(NBTTagCompound tag) {
        stacks = new ItemStack[getSizeInventory()];
        NBTTagList camoStackTag = tag.getTagList(name, 10);

        for(int i = 0; i < camoStackTag.tagCount(); i++) {
            NBTTagCompound t = camoStackTag.getCompoundTagAt(i);
            int index = t.getByte("index");
            if(index >= 0 && index < stacks.length) {
                if(stacks[index] == null){
                    stacks[index] = ItemStack.loadItemStackFromNBT(t);
                }
            }
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        NBTTagList camoStackTag = new NBTTagList();
        for(int i = 0; i < stacks.length; i++) {
            ItemStack stack = stacks[i];
            if(stack != null) {
                NBTTagCompound t = new NBTTagCompound();
                stack.writeToNBT(t);
                t.setByte("index", (byte)i);
                camoStackTag.appendTag(t);
            }
        }
        tag.setTag(name, camoStackTag);
        return tag;
    }
    
    @Override
    public int getSizeInventory() {
        return invsize;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
 //       LogHelper.info(stacks[index]);
        return stacks[index];

    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.stacks[index] != null) {
            if (this.stacks[index].stackSize <= count) {
                ItemStack stack = this.stacks[index];
                this.stacks[index] = null;

                this.markDirty();

                return stack;
            } else {
                ItemStack stack = this.stacks[index].splitStack(count);

                if (this.stacks[index].stackSize == 0) {
                    this.stacks[index] = null;
                }
                this.markDirty();

                return stack;
            }
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int slot) {
        if (stacks[slot] != null) {
            ItemStack itemstack = stacks[slot];
            stacks[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, @Nullable ItemStack stack) {
        if (slot >= stacks.length) {
            return;
        }
        stacks[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
        markDirty();

    }

    @Override
    public int getInventoryStackLimit() {
        return maxstacksize;
    }

    @Override
    public void markDirty() {
        onwer.onInventoryChanged(this);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return name ;
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }
}
