package exercise;

import java.util.Map;
import java.util.List;

// BEGIN
public class PairedTag extends Tag {
    private String tagBody;
    private List<Tag> children;

    public PairedTag(String tag, Map<String, String> attributes, String tagBody, List<Tag> children) {
        super(tag, attributes);
        this.tagBody = tagBody;
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(new SingleTag(getTag(), getAttributes()));

        if (!tagBody.isEmpty()) {
            sb.append(tagBody);
        }

        for (Tag child : children) {
            sb.append(child.toString());
        }

        sb.append("</").append(getTag()).append(">");

        return sb.toString();
    }
}
// END
