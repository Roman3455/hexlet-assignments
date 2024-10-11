package exercise;

// BEGIN
public class LabelTag implements TagInterface {
    private String label;
    private TagInterface childTag;

    public LabelTag(String label, TagInterface childTag) {
        this.label = label;
        this.childTag = childTag;
    }

    @Override
    public String render() {
        return String.format("<label>Press Submit%s</label>", childTag.render());
    }
}
// END
