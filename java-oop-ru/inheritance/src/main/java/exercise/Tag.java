package exercise;

import java.util.Map;

// BEGIN
public abstract class Tag {
    private String tag;
    private Map<String, String> attributes;

    public Tag(String tag, Map<String, String> attributes) {
        this.tag = tag;
        this.attributes = attributes;
    }

    public String getTag() {
        return tag;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    protected String getAttributesToString() {
        var sb = new StringBuilder(" ");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\" ");
        }

        return sb.toString();
    }

    public abstract String toString();
}
// END
