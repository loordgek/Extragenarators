package loordgek.extragenarators.util;

import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BasicItemJsonGen {

    public static void genItemBlockStateJson(String path, String modid, String fileName, IVariantLookup lookup) throws IOException {
        File folder = new File(path + "/assets/" + modid + "/blockstates/");
        File json = new File(path + "/assets/" + modid + "/blockstates/" + fileName + ".json");

        folder.mkdirs();

        JsonWriter jsonWriter = new JsonWriter(new FileWriter(json));
        jsonWriter.setIndent("    ");
        jsonWriter.setLenient(true);

        jsonWriter.beginObject();
        jsonWriter.name("forge_marker").value(1);
        jsonWriter.name("defaults");
        jsonWriter.beginObject();
        jsonWriter.name("model").value(modid + ":item/generated");
        jsonWriter.endObject();
        jsonWriter.name("variants").beginObject();
        jsonWriter.name("variant").beginObject();
        for (String string : lookup.variantnames()){
            jsonWriter.name(string).beginObject();
            jsonWriter.name("textures").beginObject();
            jsonWriter.name("layer0").value(modid + ":items/" + string);
            jsonWriter.endObject();
            jsonWriter.endObject();
        }
        jsonWriter.endObject();
        jsonWriter.endObject();
        jsonWriter.endObject();
        jsonWriter.close();
    }
    public static void genItemJson(String path, String modid, String fileName, IVariantLookup lookup) throws IOException{
        File folder = new File(path + "/assets/" + modid + "/models/item/");
        folder.mkdir();

        for (String lookup1 : lookup.variantnames()){
            File json = new File(path + "/assets/" + modid + "/models/item/" + fileName + "_M_" + lookup1 + ".json");
            JsonWriter jsonWriter = new JsonWriter(new FileWriter(json));
            jsonWriter.setIndent("    ");
            jsonWriter.setLenient(true);

            jsonWriter.beginObject();
            jsonWriter.name("parent").value("item/generated");
            jsonWriter.name("textures").beginObject();
            jsonWriter.name("layer0").value(modid + ":items/" + lookup1);
            jsonWriter.endObject();
            jsonWriter.endObject();
            jsonWriter.close();

        }


    }
}
