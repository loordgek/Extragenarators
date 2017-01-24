package loordgek.extragenarators.util;

import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BasicItemJsonGen {

    public static void genItemJson(String path, String modid, String fileName, IVariantLookup lookup) throws IOException {
        File folder = new File(path + "/assets/" + modid + "/models/item/");
            folder.mkdirs();

        for (String variantname : lookup.variantnames()){
            File json = new File(path + "/assets/" + modid + "/models/item/" + fileName + variantname + ".json");

            JsonWriter jsonWriter = new JsonWriter(new FileWriter(json));
            jsonWriter.setIndent("    ");
            jsonWriter.setLenient(false);

            jsonWriter.beginObject();
            jsonWriter.name("parent").value("item/generated");
            jsonWriter.name("textures").beginObject();
            jsonWriter.name("layer0").value(modid + ":items/");
            jsonWriter.endObject().endObject();
            jsonWriter.close();
        }
    }
}
