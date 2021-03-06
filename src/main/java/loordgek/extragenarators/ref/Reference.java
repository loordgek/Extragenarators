package loordgek.extragenarators.ref;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Reference {
    public static class MODINFO {
        public static final String MOD_ID = "extragenarators";
        public static final String MOD_NAME = "Extragenarators";
        public static final String VERSION = "1.9.4-1.0";
        public static final String CLIENT_PROXY_CLASS = "loordgek.extragenarators.proxy.ClientProxy";
        public static final String SERVER_PROXY_CLASS = "loordgek.extragenarators.proxy.ServerProxy";

    }

    public static class BLOCKS {
        public static final String GENBASE = "genbase";
    }
    public static class TEXTURE {
            public static final String RESOURCE_PREFIX = MODINFO.MOD_ID.toLowerCase() + ":";
    }

    public static class ITEMS {
        public static final String upgrade = "upgrade";
            public static final String[] typeupgrade = {"speedboost1", "speedboost2", "speedboost3", "speedboost4", "powerstore1", "powerstore2", "powerstore3", "powerstore4", "multpliplier1", "multpliplier2", "multpliplier3", "multpliplier4"};
    }
}

