package loordgek.extragenarators.container.container;

import loordgek.extragenarators.network.*;
import loordgek.extragenarators.tile.TileGenBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.List;
/*
*    PneumaticCraft code. author = MineMaarten
*    https://github.com/MineMaarten/PneumaticCraft
*/
public abstract class ContainerExtragenarators<Tile extends TileGenBase> extends Container {
    public Tile te;

    private final List<SyncField> syncFields = new ArrayList<SyncField>();

    public ContainerExtragenarators(Tile te) {
        this.te = te;
        if(te != null) addSyncFields(te);
    }
    protected void addPlayerSlots(InventoryPlayer playerInventory, int x, int y){
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, x + i * 18, y + 58));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < syncFields.size(); i++) {
            if(syncFields.get(i).update()){
                sendToCrafters( new GuiSyncPacket(i, syncFields.get(i)));
            }


        }
    }
    protected void sendToCrafters(IMessage message){
        for(IContainerListener crafter : listeners) {
            if(crafter instanceof EntityPlayerMP) {
                NetworkHandler.SentTo(message, (EntityPlayerMP)crafter);
            }
        }
    }
    public void Updatefield(int id , Object value){}

    protected void addSyncField(SyncField field){
        syncFields.add(field);
        field.setLazy(false);
    }

    protected void addSyncFields(Object annotatedObject){
        List<SyncField> fields = NetworkUtils.getSyncFields(annotatedObject, GuiSync.class);
        for(SyncField field : fields)
            addSyncField(field);
    }



    public void updateField(int index, Object value){
        syncFields.get(index).setValue(value);
        if(te != null) te.onGuiUpdate();
    }

}
