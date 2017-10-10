package io.github.novacrypto;

/**
 * Created by aevans on 2017-10-08.
 */
final class ProxyCharSequence implements CharSequence {
    private final CharSequence parent;
    private final int start;
    private final int end;
    private final ToStringStrategy toStringStrategy;

    static CharSequence secureSubSequenceProxy(
            final CharSequence parent,
            final int start,
            final int end,
            final ToStringStrategy toStringStrategy) {
        final int length = parent.length();
        if ((start > end) || (start < 0) || (end > length))
            throw new StringIndexOutOfBoundsException();
        return new ProxyCharSequence(parent, start, end, toStringStrategy);
    }

    private ProxyCharSequence(
            final CharSequence parent,
            final int start,
            final int end,
            final ToStringStrategy toStringStrategy) {
        this.parent = parent;
        this.start = start;
        this.end = end;
        this.toStringStrategy = toStringStrategy;
    }

    public int length() {
        return end - start;
    }

    public char charAt(int index) {
        if (index < 0 || index >= length())
            throw new IndexOutOfBoundsException();
        return parent.charAt(index + start);
    }

    public CharSequence subSequence(int start, int end) {
        if (start == 0 && end == length())
            return this;
        return secureSubSequenceProxy(parent,
                this.start + start,
                this.start + end,
                toStringStrategy);
    }

    @Override
    public String toString() {
        return toStringStrategy.toString(this);
    }
}