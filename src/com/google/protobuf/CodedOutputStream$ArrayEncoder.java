package com.google.protobuf;

import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CodedOutputStream$ArrayEncoder extends ByteOutput {
    public final byte[] buffer;
    public final int limit;
    public int position;
    public CodedOutputStreamWriter wrapper;
    public static final Logger logger =
            Logger.getLogger(CodedOutputStream$ArrayEncoder.class.getName());
    public static final boolean HAS_UNSAFE_ARRAY_OPERATIONS =
            UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS;

    public CodedOutputStream$ArrayEncoder(int i, byte[] bArr) {
        if (((bArr.length - i) | i) < 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Array range is invalid. Buffer.length=%d, offset=%d, length=%d",
                            Integer.valueOf(bArr.length), 0, Integer.valueOf(i)));
        }
        this.buffer = bArr;
        this.position = 0;
        this.limit = i;
    }

    public static int computeBytesSize(int i, ByteString byteString) {
        return computeBytesSizeNoTag(byteString) + computeTagSize(i);
    }

    public static int computeBytesSizeNoTag(ByteString byteString) {
        int size = byteString.size();
        return computeUInt32SizeNoTag(size) + size;
    }

    public static int computeFixed32Size(int i) {
        return computeTagSize(i) + 4;
    }

    public static int computeFixed64Size(int i) {
        return computeTagSize(i) + 8;
    }

    public static int computeGroupSize(int i, MessageLite messageLite, Schema schema) {
        return ((AbstractMessageLite) messageLite).getSerializedSize(schema)
                + (computeTagSize(i) * 2);
    }

    public static int computeInt32SizeNoTag(int i) {
        if (i >= 0) {
            return computeUInt32SizeNoTag(i);
        }
        return 10;
    }

    public static int computeStringSizeNoTag(String str) {
        int length;
        try {
            length = Utf8.encodedLength(str);
        } catch (Utf8.UnpairedSurrogateException unused) {
            length = str.getBytes(Internal.UTF_8).length;
        }
        return computeUInt32SizeNoTag(length) + length;
    }

    public static int computeTagSize(int i) {
        return computeUInt32SizeNoTag(i << 3);
    }

    public static int computeUInt32SizeNoTag(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int computeUInt64SizeNoTag(long j) {
        int i;
        if (((-128) & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if (((-34359738368L) & j) != 0) {
            j >>>= 28;
            i = 6;
        } else {
            i = 2;
        }
        if (((-2097152) & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        return (j & (-16384)) != 0 ? i + 1 : i;
    }

    public final void write(byte b) {
        try {
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = b;
        } catch (IndexOutOfBoundsException e) {
            throw new CodedOutputStream$OutOfSpaceException(
                    String.format(
                            "Pos: %d, limit: %d, len: %d",
                            Integer.valueOf(this.position), Integer.valueOf(this.limit), 1),
                    e);
        }
    }

    public final void writeBytesNoTag(ByteString byteString) {
        writeUInt32NoTag(byteString.size());
        ByteString.LiteralByteString literalByteString = (ByteString.LiteralByteString) byteString;
        write(
                literalByteString.bytes,
                literalByteString.getOffsetIntoBytes(),
                literalByteString.size());
    }

    public final void writeFixed32(int i, int i2) {
        writeTag(i, 5);
        writeFixed32NoTag(i2);
    }

    public final void writeFixed32NoTag(int i) {
        try {
            byte[] bArr = this.buffer;
            int i2 = this.position;
            bArr[i2] = (byte) (i & 255);
            bArr[i2 + 1] = (byte) ((i >> 8) & 255);
            bArr[i2 + 2] = (byte) ((i >> 16) & 255);
            this.position = i2 + 4;
            bArr[i2 + 3] = (byte) ((i >> 24) & 255);
        } catch (IndexOutOfBoundsException e) {
            throw new CodedOutputStream$OutOfSpaceException(
                    String.format(
                            "Pos: %d, limit: %d, len: %d",
                            Integer.valueOf(this.position), Integer.valueOf(this.limit), 1),
                    e);
        }
    }

    public final void writeFixed64(int i, long j) {
        writeTag(i, 1);
        writeFixed64NoTag(j);
    }

    public final void writeFixed64NoTag(long j) {
        try {
            byte[] bArr = this.buffer;
            int i = this.position;
            bArr[i] = (byte) (((int) j) & 255);
            bArr[i + 1] = (byte) (((int) (j >> 8)) & 255);
            bArr[i + 2] = (byte) (((int) (j >> 16)) & 255);
            bArr[i + 3] = (byte) (((int) (j >> 24)) & 255);
            bArr[i + 4] = (byte) (((int) (j >> 32)) & 255);
            bArr[i + 5] = (byte) (((int) (j >> 40)) & 255);
            bArr[i + 6] = (byte) (((int) (j >> 48)) & 255);
            this.position = i + 8;
            bArr[i + 7] = (byte) (((int) (j >> 56)) & 255);
        } catch (IndexOutOfBoundsException e) {
            throw new CodedOutputStream$OutOfSpaceException(
                    String.format(
                            "Pos: %d, limit: %d, len: %d",
                            Integer.valueOf(this.position), Integer.valueOf(this.limit), 1),
                    e);
        }
    }

    public final void writeInt32NoTag(int i) {
        if (i >= 0) {
            writeUInt32NoTag(i);
        } else {
            writeUInt64NoTag(i);
        }
    }

    public final void writeStringNoTag(String str) {
        int i = this.position;
        try {
            int computeUInt32SizeNoTag = computeUInt32SizeNoTag(str.length() * 3);
            int computeUInt32SizeNoTag2 = computeUInt32SizeNoTag(str.length());
            int i2 = this.limit;
            byte[] bArr = this.buffer;
            if (computeUInt32SizeNoTag2 == computeUInt32SizeNoTag) {
                int i3 = i + computeUInt32SizeNoTag2;
                this.position = i3;
                int encode = Utf8.encode(str, bArr, i3, i2 - i3);
                this.position = i;
                writeUInt32NoTag((encode - i) - computeUInt32SizeNoTag2);
                this.position = encode;
            } else {
                writeUInt32NoTag(Utf8.encodedLength(str));
                int i4 = this.position;
                this.position = Utf8.encode(str, bArr, i4, i2 - i4);
            }
        } catch (Utf8.UnpairedSurrogateException e) {
            this.position = i;
            logger.log(
                    Level.WARNING,
                    "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip"
                        + " correctly!",
                    (Throwable) e);
            byte[] bytes = str.getBytes(Internal.UTF_8);
            try {
                writeUInt32NoTag(bytes.length);
                write(bytes, 0, bytes.length);
            } catch (IndexOutOfBoundsException e2) {
                throw new CodedOutputStream$OutOfSpaceException(e2);
            }
        } catch (IndexOutOfBoundsException e3) {
            throw new CodedOutputStream$OutOfSpaceException(e3);
        }
    }

    public final void writeTag(int i, int i2) {
        writeUInt32NoTag((i << 3) | i2);
    }

    public final void writeUInt32NoTag(int i) {
        while (true) {
            int i2 = i & (-128);
            byte[] bArr = this.buffer;
            if (i2 == 0) {
                int i3 = this.position;
                this.position = i3 + 1;
                bArr[i3] = (byte) i;
                return;
            } else {
                try {
                    int i4 = this.position;
                    this.position = i4 + 1;
                    bArr[i4] = (byte) ((i & 127) | 128);
                    i >>>= 7;
                } catch (IndexOutOfBoundsException e) {
                    throw new CodedOutputStream$OutOfSpaceException(
                            String.format(
                                    "Pos: %d, limit: %d, len: %d",
                                    Integer.valueOf(this.position), Integer.valueOf(this.limit), 1),
                            e);
                }
            }
            throw new CodedOutputStream$OutOfSpaceException(
                    String.format(
                            "Pos: %d, limit: %d, len: %d",
                            Integer.valueOf(this.position), Integer.valueOf(this.limit), 1),
                    e);
        }
    }

    public final void writeUInt64(int i, long j) {
        writeTag(i, 0);
        writeUInt64NoTag(j);
    }

    public final void writeUInt64NoTag(long j) {
        boolean z = HAS_UNSAFE_ARRAY_OPERATIONS;
        int i = this.limit;
        byte[] bArr = this.buffer;
        if (z && i - this.position >= 10) {
            while ((j & (-128)) != 0) {
                int i2 = this.position;
                this.position = i2 + 1;
                UnsafeUtil.putByte(bArr, i2, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            int i3 = this.position;
            this.position = i3 + 1;
            UnsafeUtil.putByte(bArr, i3, (byte) j);
            return;
        }
        while ((j & (-128)) != 0) {
            try {
                int i4 = this.position;
                this.position = i4 + 1;
                bArr[i4] = (byte) ((((int) j) & 127) | 128);
                j >>>= 7;
            } catch (IndexOutOfBoundsException e) {
                throw new CodedOutputStream$OutOfSpaceException(
                        String.format(
                                "Pos: %d, limit: %d, len: %d",
                                Integer.valueOf(this.position), Integer.valueOf(i), 1),
                        e);
            }
        }
        int i5 = this.position;
        this.position = i5 + 1;
        bArr[i5] = (byte) j;
    }

    public final void write(byte[] bArr, int i, int i2) {
        try {
            System.arraycopy(bArr, i, this.buffer, this.position, i2);
            this.position += i2;
        } catch (IndexOutOfBoundsException e) {
            throw new CodedOutputStream$OutOfSpaceException(
                    String.format(
                            "Pos: %d, limit: %d, len: %d",
                            Integer.valueOf(this.position),
                            Integer.valueOf(this.limit),
                            Integer.valueOf(i2)),
                    e);
        }
    }
}
