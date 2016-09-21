package loordgek.extragenarators.nbt;

import loordgek.extragenarators.util.LogHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.lang.reflect.Field;
import java.util.*;

public class NBTUtil {

    public static NBTTagCompound getFieldswrite(Object te, List<Field> nbtFieldsList, NBTTagCompound compound) throws IllegalAccessException {
        NBTTagList nbt = new NBTTagList();
        for (Field field : nbtFieldsList) {
            field.setAccessible(true);
            Object object = field.get(te);
            NBTTagCompound nbtcomp = new NBTTagCompound();
            nbtcomp.setString("fieldname", field.getName());

            if (object instanceof int[]) {
                int[] array = (int[]) object;
                LogHelper.info(array);
                nbtcomp.setIntArray(field.getName(), array);
                nbt.appendTag(nbtcomp);
                continue;

            }
            if (object instanceof INBTSerializable) {
                LogHelper.info(object);
                INBTSerializable in = (INBTSerializable) object;
                nbtcomp.setTag(field.getName(), in.serializeNBT());
                nbt.appendTag(nbtcomp);

            }
        }
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setTag("NBTave", nbt);
        return tagCompound;
        //return compound;
    }

    public static void getFieldsread(Object te, List<Field> nbtFieldsList, NBTTagCompound compound) throws IllegalAccessException, NoSuchFieldException {
        NBTTagList nbttag = compound.getTagList("NBTave", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttag.tagCount(); i++) {
            NBTTagCompound comp = nbttag.getCompoundTagAt(i);
            Class clazz = te.getClass();
            Field field = null;
            if (clazz.getField(comp.getString("fieldname")) == null) {
                clazz = te.getClass().getSuperclass();
            } else field = clazz.getField(comp.getString("fieldname"));

            if (field == null)
                return;

            field.setAccessible(true);
            Object object = field.get(te);
            if (object instanceof int[]) {

                int[] array = (int[]) object;
                LogHelper.info(array);
                field.set(te, comp.getIntArray(field.getName()));
            }
            if (object instanceof INBTSerializable) {
                LogHelper.info(object);
                INBTSerializable in = ((INBTSerializable) object);
                in.deserializeNBT(comp.getCompoundTag(field.getName()));
                return;


            }
        }
    }


    public static List<Field> GetFields(Object te, Object teend, Class searchedAnnotation) {
        List<Field> fieldList = new ArrayList<Field>();
        for (Class clazz : GetClasslist(te, teend)) {
            {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.getAnnotation(searchedAnnotation) != null) {
                        fieldList.add(field);
                    }
                }
            }
        }
        return fieldList;
    }

    private static List<Class> GetClasslist(Object startobj, Object endobj) {
        List<Class> classList = new ArrayList<Class>();
        Class clazz = startobj.getClass();
        if (startobj.getClass() == endobj.getClass()) {
            while (clazz != null) {
                classList.add(clazz);
            }
            clazz.getSuperclass();
        } else {
            while (startobj.getClass() != endobj.getClass()) {
                if (clazz != null) {
                    classList.add(clazz);
                    clazz = clazz.getSuperclass();
                } else {
                    break;
                }
            }
        }
        return classList;
    }
}
