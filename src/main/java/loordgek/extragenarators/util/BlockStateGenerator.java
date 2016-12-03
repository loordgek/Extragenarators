package loordgek.extragenarators.util;

import com.google.common.collect.ImmutableMap;
import com.google.gson.stream.JsonWriter;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BlockStateGenerator {
    /**
     * Generates a Blockstate JSON for the specified block using all of it's properties.
     *
     * @param path     Starting path ie "E://Modding/forge".
     * @param modid    The modid for this blocks mod, used mainly as an identifier.
     * @param fileName Pretty self explanatory.
     * @param block    The block that is relevant to the blockstate.
     */

    public static void BlocktateGeneratorforgeV1(String path, String modid, String fileName, Block block) throws IOException {
        File json = new File(path + "/assets/" + modid + "/blockstates/" + fileName + ".json");
        File folders = new File(path + "/assets/" + modid + "/blockstates/");
        folders.mkdir();
        IBlockState state = block.getBlockState().getBaseState();
        if (json.exists()){
            json.delete();
        }

        JsonWriter jsonWriter = new JsonWriter(new FileWriter(json));
        jsonWriter.setIndent("    ");
        jsonWriter.beginObject();
        jsonWriter.name("forge_marker").value(1);
        jsonWriter.name("defaults").beginObject();
        jsonWriter.endObject();
        jsonWriter.name("variants").beginObject();
        ImmutableMap<IProperty<?>, Comparable<?>> properties = state.getProperties();
        for (IProperty<?> property : properties.keySet()) {
            WriteProperty(property, jsonWriter);
        }
        jsonWriter.endObject();
        jsonWriter.endObject();
        jsonWriter.close();
    }

    private static void WriteProperty(IProperty<?> property, JsonWriter jsonWriter) throws IOException {
        jsonWriter.name(property.getName());
        jsonWriter.beginObject();
        for (Object object : property.getAllowedValues().toArray()) {
            LogHelper.info(object.toString());
            if (object instanceof Integer){
                jsonWriter.value(((Integer)object));
            }
            else jsonWriter.value(object.toString());
            jsonWriter.beginObject();
            jsonWriter.endObject();
        }
        jsonWriter.endObject();
    }
}