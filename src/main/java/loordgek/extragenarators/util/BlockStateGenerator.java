package loordgek.extragenarators.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockStateGenerator {
    /**
     * Generates a Blockstate JSON for the specified block using all of it's properties.
     *
     * @param path     Starting path ie "E://Modding/forge".
     * @param modid    The modid for this blocks mod, used mainly as an identifier.
     * @param fileName Pretty self explanatory.
     * @param block    The block that is relevant to the blockstate.
     */
/*    public static void createJson(String path, String modid, String fileName, Block block) {
        File json = new File(path + "/assets/" + modid + "/blockstates/" + fileName + ".json");
        File folders = new File(path + "/assets/" + modid + "/blockstates/");
        if (json.exists()) return;
        folders.mkdirs();
        try {
            json.createNewFile();
            if (!json.canWrite()) json.setWritable(true);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            JsonObject obj = new JsonObject();
            JsonObject element = new JsonObject();
            Collection<IBlockState> collec = block.getBlockState().getValidStates();
            for (IBlockState state : collec) {
                Collection<IProperty<?>> properties = state.getPropertyNames();
                String[] names = new String[properties.size()], values = new String[properties.size()];
                for (int i = 0; i < names.length; i++) {
                    IProperty<?> prop = (IProperty<?>) properties.toArray()[i];
                    names[i] = prop.getName();
                    values[i] = state.getProperties().get(prop).toString();
                }
                StringBuilder tagBuilder = new StringBuilder();
                for (int i = 0; i < properties.size(); i++) {
                    if (i == properties.size() - 1) {
                        tagBuilder.append(names[i] + "=" + values[i]);
                    } else tagBuilder.append(names[i] + "=" + values[i] + ",");
                }

                element.add(tagBuilder.toString(), new JsonObject());
            }
            obj.add("variants", element);
            String[] jsonText = gson.toJson(obj).split("[{]");
            for (int i = 0; i < jsonText.length; i++) {
                if (!jsonText[i].endsWith("}")) jsonText[i] = jsonText[i] += "{";
            }
            List<String[]> linesList = new ArrayList<String[]>();
            for (String line : jsonText) {
                String[] array = line.split("},");
                for (int i = 0; i < array.length; i++) {
                    if (i != array.length - 1) array[i] = array[i] += "},";
                }
                linesList.add(array);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(json));
            for (String[] lines : linesList) {
                for (String line : lines) {
                    if (line.startsWith("}")) writer.newLine();
                    writer.write(line);
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public static void BlocktateGeneratorforgeV1(String path, String modid, String fileName, Block block) {
        File json = new File(path + "/assets/" + modid + "/blockstates/" + fileName + ".json");
        File folders = new File(path + "/assets/" + modid + "/blockstates/");
        //if you need pretty printing
        Map<String, Object> config = new HashMap<String, Object>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);

        JsonBuilderFactory builderFactory = Json.createBuilderFactory(config);
        JsonObjectBuilder variants = builderFactory.createObjectBuilder();
        JsonObjectBuilder allowedValues = builderFactory.createObjectBuilder();
        JsonObjectBuilder variant = builderFactory.createObjectBuilder();
        String propertyname = "";

        if (json.exists()) {
            json.delete();
        }

        folders.mkdirs();

        Collection<IBlockState> blockstates = block.getBlockState().getValidStates();

        for (IBlockState state : blockstates) {
            ImmutableMap<IProperty<?>, Comparable<?>> properties = state.getProperties();
            for (IProperty<?> property : properties.keySet()) {
                for (Object values : property.getAllowedValues().toArray()) {
                    allowedValues.add("allowedValues" ,values.toString());
                }
                propertyname = property.getName();

            }
        }
        variant.add("variant" ,propertyname).add("allowedValues" ,allowedValues.build());
        variants.add("variants", variant);

        JsonGeneratorFactory factory = Json.createGeneratorFactory(config);
        JsonGenerator generator = null;


        try {
             generator = factory.createGenerator(new FileWriter(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
        generator.writeStartObject()
                .write("forge_marker", 1)
                .writeStartObject("defaults")
                .writeEnd()
                .writeStartObject("variants")

                .writeEnd()
                .writeEnd()
                .close();
        generator.close();


    }
}