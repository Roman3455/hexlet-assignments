package exercise;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// BEGIN
public class Validator {

    public static List<String> validate(Object obj) {
        var notValidFields = new ArrayList<String>();

        Class<?> aClass = obj.getClass();
        Field[] fields = aClass.getDeclaredFields();

        for (var field : fields) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) == null) {
                        notValidFields.add(field.getName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return notValidFields;
    }

    public static Map<String, List<String>> advancedValidate(Object obj) {
        var notValidFields = new HashMap<String, List<String>>();

        Class<?> aClass = obj.getClass();
        Field[] fields = aClass.getDeclaredFields();

        for (var field : fields) {
            field.setAccessible(true);
            var errors = new ArrayList<String>();

            try {
                if (field.isAnnotationPresent(NotNull.class) && field.get(obj) == null) {
                    errors.add("can not be null");
                }
                if (field.isAnnotationPresent(MinLength.class)) {
                    MinLength minLengthAnnotation = field.getAnnotation(MinLength.class);
                    int minLength = minLengthAnnotation.minLength();
                    String value = (String) field.get(obj);

                    if (value != null && value.length() < minLength) {
                        errors.add("length less than " + minLength);
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Обработка исключения
            }

            if (!errors.isEmpty()) {
                notValidFields.put(field.getName(), errors);
            }
        }

        return notValidFields;
    }
}
// END
