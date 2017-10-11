package io.github.novacrypto;

/**
 * Created by aevans on 2017-10-09.
 */
enum ToStringStrategy {

    RESTRICT {
        @Override
        public String toString(final CharSequence charSequence) {
            throw new UnsupportedOperationException();
        }
    },

    ALLOW {
        @Override
        public String toString(final CharSequence charSequence) {
            final int length = charSequence.length();
            final char[] chars = new char[length];
            for (int i = 0; i < length; i++) {
                chars[i] = charSequence.charAt(i);
            }
            return new String(chars);
        }
    };

    abstract String toString(final CharSequence charSequence);
}