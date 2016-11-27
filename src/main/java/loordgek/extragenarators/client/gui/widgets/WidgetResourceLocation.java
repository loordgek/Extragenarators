package loordgek.extragenarators.client.gui.widgets;

import loordgek.extragenarators.ref.Reference;
import net.minecraft.util.ResourceLocation;

public class WidgetResourceLocation extends ResourceLocation {
    public WidgetResourceLocation(String widgetnmae) {
        super(Reference.MODINFO.MOD_ID, "textures/widgets/" + widgetnmae + ".png");
    }
}
