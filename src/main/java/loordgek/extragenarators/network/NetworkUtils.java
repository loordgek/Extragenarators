package loordgek.extragenarators.network;

import loordgek.extragenarators.util.LogHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/*
*    PneumaticCraft code. author = MineMaarten
*    https://github.com/MineMaarten/PneumaticCraft
*/
public class NetworkUtils {

    public static <T extends Annotation> List<SyncField> getSyncFields(Object owner, Class<T> searchedAnnotation) {
        List<SyncField> syncedFields = new ArrayList<>();
        Class examinedClass = owner.getClass();
        while (examinedClass != null) {
            for (Field field : examinedClass.getDeclaredFields()) {
                if (field.getAnnotation(GuiSyncInnerFields.class) != null) {
                    field.setAccessible(true);
                    Object o;
                    try {
                        o = field.get(owner);
                        for (Field field1 : o.getClass().getDeclaredFields()){
                            syncedFields.addAll(getSyncFieldsForField(field1, o, searchedAnnotation));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if (field.getAnnotation(searchedAnnotation) != null) {
                    syncedFields.addAll(getSyncFieldsForField(field, owner, searchedAnnotation));
                }
            }
            examinedClass = examinedClass.getSuperclass();
        }
        return syncedFields;
    }

    private static List<SyncField> getSyncFieldsForField(Field field, Object owner, Class searchedAnnotation) {
        boolean isLazy = field.getAnnotation(LazySync.class) != null;
        List<SyncField> syncedFields = new ArrayList<>();
        SyncField syncedField = getSyncFieldForField(field, owner);
        if (syncedField != null) {
            syncedFields.add(syncedField.setLazy(isLazy));
            return syncedFields;
        } else {
            Object o;
            try {
                int filteredIndex = field.getAnnotation(FilteredSync.class) != null ? field.getAnnotation(FilteredSync.class).index() : -1;
                field.setAccessible(true);
                o = field.get(owner);
                if (o instanceof int[]) {
                    int[] array = (int[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.Intfieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncField.Intfieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof float[]) {
                    float[] array = (float[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.floatfieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncField.floatfieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof double[]) {
                    double[] array = (double[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.doublefieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncField.doublefieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof long[]) {
                    long[] array = (long[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.longfieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncField.longfieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof boolean[]) {
                    boolean[] array = (boolean[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.booleanfieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncField.booleanfieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof short[]) {
                    short[] array = (short[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.shortfieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncField.shortfieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }

                if (o instanceof String[]) {
                    String[] array = (String[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.Stringfieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncField.Stringfieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o.getClass().isArray() && o.getClass().getComponentType().isEnum()) {
                    Object[] enumArray = (Object[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.bytefieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < enumArray.length; i++) {
                            syncedFields.add(new SyncField.bytefieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof ItemStack[]) {
                    ItemStack[] array = (ItemStack[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.ItemStackfieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncField.ItemStackfieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof FluidTank[]) {
                    FluidTank[] array = (FluidTank[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncField.FluidStackfieldsync(owner, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncField.FluidStackfieldsync(owner, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (field.getType().isArray()) {
                    Object[] array = (Object[]) o;
                    for (Object obj : array) {
                        syncedFields.addAll(getSyncFields(obj, searchedAnnotation));
                    }
                } else {
                    syncedFields.addAll(getSyncFields(o, searchedAnnotation));
                }
                if (syncedFields.size() > 0) return syncedFields;
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogHelper.warn("Field " + field + " didn't produce any syncable fields!");
            return syncedFields;
        }
    }

    private static SyncField getSyncFieldForField(Field field, Object owner) {
        if (int.class.isAssignableFrom(field.getType())) return new SyncField.Intfieldsync(owner, field);
        if (float.class.isAssignableFrom(field.getType())) return new SyncField.floatfieldsync(owner, field);
        if (long.class.isAssignableFrom(field.getType())) return new SyncField.longfieldsync(owner, field);
        if (double.class.isAssignableFrom(field.getType())) return new SyncField.doublefieldsync(owner, field);
        if (boolean.class.isAssignableFrom(field.getType())) return new SyncField.booleanfieldsync(owner, field);
        if (String.class.isAssignableFrom(field.getType())) return new SyncField.Stringfieldsync(owner, field);
        if (field.getType().isEnum()) return new SyncField.bytefieldsync(owner, field);
        if (ItemStack.class.isAssignableFrom(field.getType())) return new SyncField.ItemStackfieldsync(owner, field);
        if (FluidTank.class.isAssignableFrom(field.getType()))return new SyncField.FluidStackfieldsync(owner, field);
        return null;
    }
}

