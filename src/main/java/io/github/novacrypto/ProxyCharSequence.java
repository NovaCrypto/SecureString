package io.github.novacrypto;

/**
 * Created by aevans on 2017-10-08.
 */
final class ProxyCharSequence implements CharSequence {
    private final CharSequence parent;
    private final int start;
    private final int end;

    static CharSequence secureSubSequenceProxy(
            final CharSequence parent,
            final int start,
            final int end) {
        final int length = parent.length();
        if ((start > end) || (start < 0) || (end > length))
            throw new StringIndexOutOfBoundsException();
        if (start == 0 && end == length) return parent;
        return new ProxyCharSequence(parent, start, end);
    }

    private ProxyCharSequence(
            final CharSequence parent,
            final int start,
            final int end) {
        this.parent = parent;
        this.start = start;
        this.end = end;
    }

    public int length() {
        return end - start;
    }

    public char charAt(int index) {
        return parent.charAt(index + start);
    }

    public CharSequence subSequence(int start, int end) {
        return parent.subSequence(this.start + start, this.start + end);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }
}