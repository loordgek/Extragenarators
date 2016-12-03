package loordgek.extragenarators.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumGenarator implements IStringSerializable {
    furnace("furnace", 0),
    lava("lava",1),
    //ender("ender",2),
    ;

    public static final EnumGenarator[] EnumLookup = new EnumGenarator[values().length];

    static {
        for (EnumGenarator enumgen : values()) {
            EnumLookup[enumgen.getMeta()] = enumgen;
        }
    }

    private final String name;
    private final int meta;
    EnumGenarator(String name, int meta) {
        this.name = name;
        this.meta = meta;
    }
    public static int getlenth(){
        return EnumGenarator.values().length;
    }


    public static EnumGenarator byMeta(int meta) {
        if (meta < 0 || meta >= EnumLookup.length) {
            meta = 0;
        }
        return EnumLookup[meta];
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }

    public int getMeta() {
        return meta;
    }
}
