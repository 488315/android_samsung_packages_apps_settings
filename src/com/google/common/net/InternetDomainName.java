package com.google.common.net;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Splitter.AnonymousClass5;
import com.google.common.collect.ImmutableList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class InternetDomainName {
    public static final CharMatcher DASH_MATCHER;
    public static final CharMatcher.InRange DIGIT_MATCHER;
    public static final CharMatcher DOTS_MATCHER = CharMatcher.anyOf(".。．｡");
    public static final Splitter DOT_SPLITTER =
            new Splitter(
                    new Object() { // from class: com.google.common.base.Splitter.1
                        public final /* synthetic */ CharMatcher val$separatorMatcher;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        /* renamed from: com.google.common.base.Splitter$1$1 */
                        public final class C00501 implements Iterator {
                            public int limit;
                            public Object next;
                            public final CharSequence toSplit;
                            public final CharMatcher trimmer;
                            public AbstractIterator$State state = AbstractIterator$State.NOT_READY;
                            public int offset = 0;
                            public final boolean omitEmptyStrings = false;

                            public C00501(Splitter splitter, CharSequence charSequence) {
                                this.trimmer = splitter.trimmer;
                                this.limit = splitter.limit;
                                this.toSplit = charSequence;
                            }

                            @Override // java.util.Iterator
                            public final boolean hasNext() {
                                AbstractIterator$State abstractIterator$State;
                                String str;
                                int indexIn;
                                AbstractIterator$State abstractIterator$State2 = this.state;
                                AbstractIterator$State abstractIterator$State3 =
                                        AbstractIterator$State.FAILED;
                                if (abstractIterator$State2 == abstractIterator$State3) {
                                    throw new IllegalStateException();
                                }
                                int ordinal = abstractIterator$State2.ordinal();
                                if (ordinal == 0) {
                                    return true;
                                }
                                if (ordinal == 2) {
                                    return false;
                                }
                                this.state = abstractIterator$State3;
                                int i = this.offset;
                                while (true) {
                                    int i2 = this.offset;
                                    abstractIterator$State = AbstractIterator$State.DONE;
                                    if (i2 == -1) {
                                        this.state = abstractIterator$State;
                                        str = null;
                                        break;
                                    }
                                    indexIn = r1.indexIn(this.toSplit, i2);
                                    if (indexIn == -1) {
                                        indexIn = this.toSplit.length();
                                        this.offset = -1;
                                    } else {
                                        this.offset = indexIn + 1;
                                    }
                                    int i3 = this.offset;
                                    if (i3 == i) {
                                        int i4 = i3 + 1;
                                        this.offset = i4;
                                        if (i4 > this.toSplit.length()) {
                                            this.offset = -1;
                                        }
                                    } else {
                                        while (i < indexIn
                                                && this.trimmer.matches(this.toSplit.charAt(i))) {
                                            i++;
                                        }
                                        while (indexIn > i
                                                && this.trimmer.matches(
                                                        this.toSplit.charAt(indexIn - 1))) {
                                            indexIn--;
                                        }
                                        if (!this.omitEmptyStrings || i != indexIn) {
                                            break;
                                        }
                                        i = this.offset;
                                    }
                                }
                                int i5 = this.limit;
                                if (i5 == 1) {
                                    indexIn = this.toSplit.length();
                                    this.offset = -1;
                                    while (indexIn > i
                                            && this.trimmer.matches(
                                                    this.toSplit.charAt(indexIn - 1))) {
                                        indexIn--;
                                    }
                                } else {
                                    this.limit = i5 - 1;
                                }
                                str = this.toSplit.subSequence(i, indexIn).toString();
                                this.next = str;
                                if (this.state == abstractIterator$State) {
                                    return false;
                                }
                                this.state = AbstractIterator$State.READY;
                                return true;
                            }

                            @Override // java.util.Iterator
                            public final Object next() {
                                if (!hasNext()) {
                                    throw new NoSuchElementException();
                                }
                                this.state = AbstractIterator$State.NOT_READY;
                                Object obj = this.next;
                                this.next = null;
                                return obj;
                            }

                            @Override // java.util.Iterator
                            public final void remove() {
                                throw new UnsupportedOperationException();
                            }
                        }

                        public AnonymousClass1(CharMatcher.Is is) {
                            r1 = is;
                        }
                    });
    public static final CharMatcher.Or LETTER_MATCHER = null;
    public static final CharMatcher PART_CHAR_MATCHER;
    public final String name;

    static {
        String.valueOf('.').getClass();
        CharMatcher anyOf = CharMatcher.anyOf("-_");
        DASH_MATCHER = anyOf;
        CharMatcher.InRange inRange = new CharMatcher.InRange('0', '9', 0);
        DIGIT_MATCHER = inRange;
        PART_CHAR_MATCHER =
                new CharMatcher.Or(
                        new CharMatcher.Or(
                                inRange,
                                new CharMatcher.Or(
                                        new CharMatcher.InRange('a', 'z', 0),
                                        new CharMatcher.InRange('A', 'Z', 0))),
                        anyOf);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public InternetDomainName(String str) {
        String replaceFrom = DOTS_MATCHER.replaceFrom(str);
        int length = replaceFrom.length();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            char charAt = replaceFrom.charAt(i);
            if (charAt < 'A' || charAt > 'Z') {
                i++;
            } else {
                char[] charArray = replaceFrom.toCharArray();
                while (i < length) {
                    char c = charArray[i];
                    if (c >= 'A' && c <= 'Z') {
                        charArray[i] = (char) (c ^ ' ');
                    }
                    i++;
                }
                replaceFrom = String.valueOf(charArray);
            }
        }
        replaceFrom =
                replaceFrom.endsWith(".")
                        ? replaceFrom.substring(0, replaceFrom.length() - 1)
                        : replaceFrom;
        Preconditions.checkArgument(
                replaceFrom, "Domain name too long: '%s':", replaceFrom.length() <= 253);
        this.name = replaceFrom;
        Splitter splitter = DOT_SPLITTER;
        splitter.getClass();
        ImmutableList copyOf = ImmutableList.copyOf(splitter.new AnonymousClass5(replaceFrom));
        Preconditions.checkArgument(
                replaceFrom, "Domain has too many parts: '%s'", copyOf.size() <= 127);
        int size = copyOf.size() - 1;
        if (validatePart((String) copyOf.get(size), true)) {
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    z = true;
                    break;
                } else if (!validatePart((String) copyOf.get(i2), false)) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        Preconditions.checkArgument(replaceFrom, "Not a valid domain name: '%s'", z);
    }

    public static boolean validatePart(String str, boolean z) {
        if (str.length() >= 1 && str.length() <= 63) {
            CharMatcher.None none = CharMatcher.None.INSTANCE$1;
            none.getClass();
            if (!PART_CHAR_MATCHER.matchesAllOf(new CharMatcher.AnyOf(none).removeFrom(str))) {
                return false;
            }
            char charAt = str.charAt(0);
            CharMatcher charMatcher = DASH_MATCHER;
            if (!charMatcher.matches(charAt)
                    && !charMatcher.matches(str.charAt(str.length() - 1))) {
                return (z && DIGIT_MATCHER.matches(str.charAt(0))) ? false : true;
            }
        }
        return false;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof InternetDomainName) {
            return this.name.equals(((InternetDomainName) obj).name);
        }
        return false;
    }

    public final int hashCode() {
        return this.name.hashCode();
    }

    public final String toString() {
        return this.name;
    }
}
