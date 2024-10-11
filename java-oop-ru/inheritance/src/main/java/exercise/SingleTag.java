package exercise;

import java.util.Map;

// BEGIN
public class SingleTag extends Tag {

    public SingleTag(String tag, Map<String, String> attributes) {
        super(tag, attributes);
    }

    @Override
    public String toString() {
        var attributes = getAttributesToString();

        return String.format("<%s%s>", getTag(), attributes.isEmpty()
                ? attributes : attributes.substring(0, attributes.length() - 1));
    }
}
// END
