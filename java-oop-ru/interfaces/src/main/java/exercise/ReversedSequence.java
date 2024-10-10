package exercise;

// BEGIN
public class ReversedSequence implements CharSequence {
    private final String word;

    public ReversedSequence(String word) {
        this.word = new StringBuilder(word).reverse().toString();
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public int length() {
        return word.length();
    }

    @Override
    public char charAt(int index) {
        return word.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return word.subSequence(start, end);
    }
}
// END
