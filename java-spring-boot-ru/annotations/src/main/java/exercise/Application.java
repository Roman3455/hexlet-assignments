package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        for (Method methods : Address.class.getDeclaredMethods()) {
            if (methods.isAnnotationPresent(Inspect.class)) {
                var methodsName = methods.getName();
                var methodsType = methods.getReturnType().getSimpleName();

                try {
                    methods.invoke(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Method " + methodsName + " returns a value of type " + methodsType);
            }
        }
        // END
    }
}
