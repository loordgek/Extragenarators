package loordgek.extragenarators.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JavaUtil {
    public static <T extends Annotation> List<Field> GetFields(Object te, Class<T> searchedAnnotation) {
        List<Field> fieldList = new ArrayList<>();
        Class examinedClass = te.getClass();
        while (examinedClass != null) {
            for (Field field : examinedClass.getDeclaredFields()) {
                if (field.getAnnotation(searchedAnnotation) != null) {
                    fieldList.add(field);
                }
            }
            examinedClass = examinedClass.getSuperclass();
        }
        return fieldList;
    }
}
