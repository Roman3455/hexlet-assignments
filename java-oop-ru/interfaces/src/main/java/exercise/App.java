package exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// BEGIN
public class App {
    public static List<String> buildApartmentsList(List<Home> apartments, int n) {
        apartments.sort(Comparator.comparingDouble(Home::getArea));
        var result = new ArrayList<String>();
        apartments.stream().limit(n).forEach(b -> result.add(b.toString()));
        return result;
    }
}
// END
