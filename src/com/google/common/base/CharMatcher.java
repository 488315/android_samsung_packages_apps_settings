package com.google.common.base;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class CharMatcher {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class FastMatcher extends CharMatcher {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InRange extends FastMatcher {
        public final /* synthetic */ int $r8$classId;
        public final char endInclusive;
        public final char startInclusive;

        public InRange(char c, char c2, int i) {
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.startInclusive = c;
                    this.endInclusive = c2;
                    break;
                default:
                    Preconditions.checkArgument(c2 >= c);
                    this.startInclusive = c;
                    this.endInclusive = c2;
                    break;
            }
        }

        @Override // com.google.common.base.CharMatcher
        public final boolean matches(char c) {
            switch (this.$r8$classId) {
                case 0:
                    return this.startInclusive <= c && c <= this.endInclusive;
                default:
                    return c == this.startInclusive || c == this.endInclusive;
            }
        }

        public final String toString() {
            switch (this.$r8$classId) {
                case 0:
                    return "CharMatcher.inRange('"
                            + CharMatcher.access$100(this.startInclusive)
                            + "', '"
                            + CharMatcher.access$100(this.endInclusive)
                            + "')";
                default:
                    return "CharMatcher.anyOf(\""
                            + CharMatcher.access$100(this.startInclusive)
                            + CharMatcher.access$100(this.endInclusive)
                            + "\")";
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Is extends FastMatcher {
        public final char match;

        public Is(char c) {
            this.match = c;
        }

        @Override // com.google.common.base.CharMatcher
        public final boolean matches(char c) {
            return c == this.match;
        }

        @Override // com.google.common.base.CharMatcher
        public final String replaceFrom(CharSequence charSequence) {
            return charSequence.toString().replace(this.match, '.');
        }

        public final String toString() {
            return "CharMatcher.is('" + CharMatcher.access$100(this.match) + "')";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class NamedFastMatcher extends FastMatcher {
        public final String description;

        public NamedFastMatcher(String str) {
            this.description = str;
        }

        public final String toString() {
            return this.description;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class None extends NamedFastMatcher {
        public final /* synthetic */ int $r8$classId;
        public static final None INSTANCE$1 = new None("CharMatcher.ascii()", 1);
        public static final None INSTANCE = new None("CharMatcher.none()", 0);

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ None(String str, int i) {
            super(str);
            this.$r8$classId = i;
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence) {
            switch (this.$r8$classId) {
                case 0:
                    charSequence.getClass();
                    return -1;
                default:
                    return super.indexIn(charSequence);
            }
        }

        @Override // com.google.common.base.CharMatcher
        public final boolean matches(char c) {
            switch (this.$r8$classId) {
                case 0:
                    break;
                default:
                    if (c <= 127) {}
                    break;
            }
            return false;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesAllOf(CharSequence charSequence) {
            switch (this.$r8$classId) {
                case 0:
                    return charSequence.length() == 0;
                default:
                    return super.matchesAllOf(charSequence);
            }
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesNoneOf(CharSequence charSequence) {
            switch (this.$r8$classId) {
                case 0:
                    charSequence.getClass();
                    return true;
                default:
                    return super.matchesNoneOf(charSequence);
            }
        }

        @Override // com.google.common.base.CharMatcher
        public String removeFrom(CharSequence charSequence) {
            switch (this.$r8$classId) {
                case 0:
                    throw null;
                default:
                    return super.removeFrom(charSequence);
            }
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence) {
            switch (this.$r8$classId) {
                case 0:
                    return charSequence.toString();
                default:
                    return super.replaceFrom(charSequence);
            }
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence, int i) {
            switch (this.$r8$classId) {
                case 0:
                    Preconditions.checkPositionIndex(i, ((String) charSequence).length());
                    return -1;
                default:
                    return super.indexIn(charSequence, i);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Or extends CharMatcher {
        public final CharMatcher first;
        public final CharMatcher second;

        public Or(CharMatcher charMatcher, CharMatcher charMatcher2) {
            charMatcher.getClass();
            this.first = charMatcher;
            charMatcher2.getClass();
            this.second = charMatcher2;
        }

        @Override // com.google.common.base.CharMatcher
        public final boolean matches(char c) {
            return this.first.matches(c) || this.second.matches(c);
        }

        public final String toString() {
            return "CharMatcher.or(" + this.first + ", " + this.second + ")";
        }
    }

    public static String access$100(char c) {
        char[] cArr = new char[6];
        cArr[0] = '\\';
        cArr[1] = 'u';
        cArr[2] = 0;
        cArr[3] = 0;
        cArr[4] = 0;
        cArr[5] = 0;
        for (int i = 0; i < 4; i++) {
            cArr[5 - i] = "0123456789ABCDEF".charAt(c & 15);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(cArr);
    }

    public static CharMatcher anyOf(CharSequence charSequence) {
        String str = (String) charSequence;
        int length = str.length();
        return length != 0
                ? length != 1
                        ? length != 2
                                ? new AnyOf(charSequence)
                                : new InRange(str.charAt(0), str.charAt(1), 1)
                        : new Is(str.charAt(0))
                : None.INSTANCE;
    }

    public int indexIn(CharSequence charSequence) {
        return indexIn(charSequence, 0);
    }

    public abstract boolean matches(char c);

    public boolean matchesAllOf(CharSequence charSequence) {
        String str = (String) charSequence;
        for (int length = str.length() - 1; length >= 0; length--) {
            if (!matches(str.charAt(length))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesNoneOf(CharSequence charSequence) {
        return indexIn(charSequence) == -1;
    }

    public String removeFrom(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        int indexIn = indexIn(charSequence2);
        if (indexIn == -1) {
            return charSequence2;
        }
        char[] charArray = charSequence2.toCharArray();
        int i = 1;
        while (true) {
            indexIn++;
            while (indexIn != charArray.length) {
                if (matches(charArray[indexIn])) {
                    break;
                }
                charArray[indexIn - i] = charArray[indexIn];
                indexIn++;
            }
            return new String(charArray, 0, indexIn - i);
            i++;
        }
    }

    public String replaceFrom(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        int indexIn = indexIn(charSequence2);
        if (indexIn == -1) {
            return charSequence2;
        }
        char[] charArray = charSequence2.toCharArray();
        charArray[indexIn] = '.';
        while (true) {
            indexIn++;
            if (indexIn >= charArray.length) {
                return new String(charArray);
            }
            if (matches(charArray[indexIn])) {
                charArray[indexIn] = '.';
            }
        }
    }

    public int indexIn(CharSequence charSequence, int i) {
        String str = (String) charSequence;
        int length = str.length();
        Preconditions.checkPositionIndex(i, length);
        while (i < length) {
            if (matches(str.charAt(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AnyOf extends CharMatcher {
        public final /* synthetic */ int $r8$classId = 1;
        public final Object chars;

        public AnyOf(CharMatcher charMatcher) {
            charMatcher.getClass();
            this.chars = charMatcher;
        }

        @Override // com.google.common.base.CharMatcher
        public final boolean matches(char c) {
            switch (this.$r8$classId) {
                case 0:
                    return Arrays.binarySearch((char[]) this.chars, c) >= 0;
                default:
                    return !((CharMatcher) this.chars).matches(c);
            }
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesAllOf(CharSequence charSequence) {
            switch (this.$r8$classId) {
                case 1:
                    return ((CharMatcher) this.chars).matchesNoneOf(charSequence);
                default:
                    return super.matchesAllOf(charSequence);
            }
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesNoneOf(CharSequence charSequence) {
            switch (this.$r8$classId) {
                case 1:
                    return ((CharMatcher) this.chars).matchesAllOf(charSequence);
                default:
                    return super.matchesNoneOf(charSequence);
            }
        }

        public final String toString() {
            switch (this.$r8$classId) {
                case 0:
                    StringBuilder sb = new StringBuilder("CharMatcher.anyOf(\"");
                    for (char c : (char[]) this.chars) {
                        sb.append(CharMatcher.access$100(c));
                    }
                    sb.append("\")");
                    return sb.toString();
                default:
                    return ((CharMatcher) this.chars) + ".negate()";
            }
        }

        public AnyOf(CharSequence charSequence) {
            char[] charArray = ((String) charSequence).toString().toCharArray();
            this.chars = charArray;
            Arrays.sort(charArray);
        }
    }
}
