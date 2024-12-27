package com.google.protobuf;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;

import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ByteString implements Iterable<Byte>, Serializable {
    public static final ByteString EMPTY = new LiteralByteString(Internal.EMPTY_BYTE_ARRAY);
    public static final SystemByteArrayCopier byteArrayCopier;
    private int hash = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class BoundedByteString extends LiteralByteString {
        private static final long serialVersionUID = 1;
        private final int bytesLength;
        private final int bytesOffset;

        public BoundedByteString(byte[] bArr, int i, int i2) {
            super(bArr);
            ByteString.checkRange(i, i + i2, bArr.length);
            this.bytesOffset = i;
            this.bytesLength = i2;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException {
            throw new InvalidObjectException(
                    "BoundedByteStream instances are not to be serialized directly");
        }

        @Override // com.google.protobuf.ByteString.LiteralByteString,
                  // com.google.protobuf.ByteString
        public final byte byteAt(int i) {
            int i2 = this.bytesLength;
            if (((i2 - (i + 1)) | i) >= 0) {
                return this.bytes[this.bytesOffset + i];
            }
            if (i < 0) {
                throw new ArrayIndexOutOfBoundsException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Index < 0: "));
            }
            throw new ArrayIndexOutOfBoundsException(
                    HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(
                            i, i2, "Index > length: ", ", "));
        }

        @Override // com.google.protobuf.ByteString.LiteralByteString
        public final int getOffsetIntoBytes() {
            return this.bytesOffset;
        }

        @Override // com.google.protobuf.ByteString.LiteralByteString,
                  // com.google.protobuf.ByteString
        public final byte internalByteAt(int i) {
            return this.bytes[this.bytesOffset + i];
        }

        @Override // com.google.protobuf.ByteString.LiteralByteString,
                  // com.google.protobuf.ByteString
        public final int size() {
            return this.bytesLength;
        }

        public Object writeReplace() {
            byte[] bArr;
            int i = this.bytesLength;
            if (i == 0) {
                bArr = Internal.EMPTY_BYTE_ARRAY;
            } else {
                byte[] bArr2 = new byte[i];
                System.arraycopy(this.bytes, this.bytesOffset, bArr2, 0, i);
                bArr = bArr2;
            }
            return new LiteralByteString(bArr);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class LeafByteString extends ByteString {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class LiteralByteString extends LeafByteString {
        private static final long serialVersionUID = 1;
        protected final byte[] bytes;

        public LiteralByteString(byte[] bArr) {
            bArr.getClass();
            this.bytes = bArr;
        }

        @Override // com.google.protobuf.ByteString
        public byte byteAt(int i) {
            return this.bytes[i];
        }

        @Override // com.google.protobuf.ByteString
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ByteString) || size() != ((ByteString) obj).size()) {
                return false;
            }
            if (size() == 0) {
                return true;
            }
            if (!(obj instanceof LiteralByteString)) {
                return obj.equals(this);
            }
            LiteralByteString literalByteString = (LiteralByteString) obj;
            int peekCachedHashCode = peekCachedHashCode();
            int peekCachedHashCode2 = literalByteString.peekCachedHashCode();
            if (peekCachedHashCode != 0
                    && peekCachedHashCode2 != 0
                    && peekCachedHashCode != peekCachedHashCode2) {
                return false;
            }
            int size = size();
            if (size > literalByteString.size()) {
                throw new IllegalArgumentException("Length too large: " + size + size());
            }
            if (size > literalByteString.size()) {
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                size, "Ran off end of other: 0, ", ", ");
                m.append(literalByteString.size());
                throw new IllegalArgumentException(m.toString());
            }
            byte[] bArr = this.bytes;
            byte[] bArr2 = literalByteString.bytes;
            int offsetIntoBytes = getOffsetIntoBytes() + size;
            int offsetIntoBytes2 = getOffsetIntoBytes();
            int offsetIntoBytes3 = literalByteString.getOffsetIntoBytes();
            while (offsetIntoBytes2 < offsetIntoBytes) {
                if (bArr[offsetIntoBytes2] != bArr2[offsetIntoBytes3]) {
                    return false;
                }
                offsetIntoBytes2++;
                offsetIntoBytes3++;
            }
            return true;
        }

        public int getOffsetIntoBytes() {
            return 0;
        }

        @Override // com.google.protobuf.ByteString
        public byte internalByteAt(int i) {
            return this.bytes[i];
        }

        @Override // com.google.protobuf.ByteString
        public int size() {
            return this.bytes.length;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SystemByteArrayCopier {}

    static {
        Class cls = Android.MEMORY_CLASS;
        byteArrayCopier = new SystemByteArrayCopier();
    }

    public static int checkRange(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException(
                    LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                            i, "Beginning index: ", " < 0"));
        }
        if (i2 < i) {
            throw new IndexOutOfBoundsException(
                    HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(
                            i, i2, "Beginning index larger than ending index: ", ", "));
        }
        throw new IndexOutOfBoundsException(
                HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(
                        i2, i3, "End index: ", " >= "));
    }

    public static ByteString copyFrom(byte[] bArr, int i, int i2) {
        checkRange(i, i + i2, bArr.length);
        byteArrayCopier.getClass();
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return new LiteralByteString(bArr2);
    }

    public abstract byte byteAt(int i);

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.hash;
        if (i == 0) {
            int size = size();
            LiteralByteString literalByteString = (LiteralByteString) this;
            byte[] bArr = literalByteString.bytes;
            int offsetIntoBytes = literalByteString.getOffsetIntoBytes();
            int i2 = size;
            for (int i3 = offsetIntoBytes; i3 < offsetIntoBytes + size; i3++) {
                i2 = (i2 * 31) + bArr[i3];
            }
            i = i2 == 0 ? 1 : i2;
            this.hash = i;
        }
        return i;
    }

    public abstract byte internalByteAt(int i);

    @Override // java.lang.Iterable
    public final Iterator<Byte> iterator() {
        return new Iterator() { // from class: com.google.protobuf.ByteString.1
            public final int limit;
            public int position = 0;

            {
                this.limit = ByteString.this.size();
            }

            @Override // java.util.Iterator
            public final boolean hasNext() {
                return this.position < this.limit;
            }

            @Override // java.util.Iterator
            public final Object next() {
                int i = this.position;
                if (i >= this.limit) {
                    throw new NoSuchElementException();
                }
                this.position = i + 1;
                return Byte.valueOf(ByteString.this.internalByteAt(i));
            }

            @Override // java.util.Iterator
            public final void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public final int peekCachedHashCode() {
        return this.hash;
    }

    public abstract int size();

    public final String toString() {
        String sb;
        Locale locale = Locale.ROOT;
        String hexString = Integer.toHexString(System.identityHashCode(this));
        int size = size();
        if (size() <= 50) {
            sb = TextFormatEscaper.escapeBytes(this);
        } else {
            StringBuilder sb2 = new StringBuilder();
            LiteralByteString literalByteString = (LiteralByteString) this;
            int checkRange = checkRange(0, 47, literalByteString.size());
            sb2.append(
                    TextFormatEscaper.escapeBytes(
                            checkRange == 0
                                    ? EMPTY
                                    : new BoundedByteString(
                                            literalByteString.bytes,
                                            literalByteString.getOffsetIntoBytes(),
                                            checkRange)));
            sb2.append("...");
            sb = sb2.toString();
        }
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        size, "<ByteString@", hexString, " size=", " contents=\""),
                sb,
                "\">");
    }
}
