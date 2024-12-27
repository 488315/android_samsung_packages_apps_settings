package com.google.protobuf;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UnknownFieldSetLite {
    public static final UnknownFieldSetLite DEFAULT_INSTANCE =
            new UnknownFieldSetLite(0, new int[0], new Object[0], false);
    public int count;
    public boolean isMutable;
    public int memoizedSerializedSize = -1;
    public Object[] objects;
    public int[] tags;

    public UnknownFieldSetLite(int i, int[] iArr, Object[] objArr, boolean z) {
        this.count = i;
        this.tags = iArr;
        this.objects = objArr;
        this.isMutable = z;
    }

    public static UnknownFieldSetLite newInstance() {
        return new UnknownFieldSetLite(0, new int[8], new Object[8], true);
    }

    public final void ensureCapacity(int i) {
        int[] iArr = this.tags;
        if (i > iArr.length) {
            int i2 = this.count;
            int i3 = (i2 / 2) + i2;
            if (i3 >= i) {
                i = i3;
            }
            if (i < 8) {
                i = 8;
            }
            this.tags = Arrays.copyOf(iArr, i);
            this.objects = Arrays.copyOf(this.objects, i);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UnknownFieldSetLite)) {
            return false;
        }
        UnknownFieldSetLite unknownFieldSetLite = (UnknownFieldSetLite) obj;
        int i = this.count;
        if (i == unknownFieldSetLite.count) {
            int[] iArr = this.tags;
            int[] iArr2 = unknownFieldSetLite.tags;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    Object[] objArr = this.objects;
                    Object[] objArr2 = unknownFieldSetLite.objects;
                    int i3 = this.count;
                    for (int i4 = 0; i4 < i3; i4++) {
                        if (objArr[i4].equals(objArr2[i4])) {}
                    }
                    return true;
                }
                if (iArr[i2] != iArr2[i2]) {
                    break;
                }
                i2++;
            }
        }
        return false;
    }

    public final int getSerializedSize() {
        int computeTagSize;
        int computeUInt64SizeNoTag;
        int computeFixed64Size;
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            int i4 = this.tags[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 != 0) {
                if (i6 == 1) {
                    ((Long) this.objects[i3]).getClass();
                    computeFixed64Size = CodedOutputStream$ArrayEncoder.computeFixed64Size(i5);
                } else if (i6 == 2) {
                    computeFixed64Size =
                            CodedOutputStream$ArrayEncoder.computeBytesSize(
                                    i5, (ByteString) this.objects[i3]);
                } else if (i6 == 3) {
                    computeTagSize = CodedOutputStream$ArrayEncoder.computeTagSize(i5) * 2;
                    computeUInt64SizeNoTag =
                            ((UnknownFieldSetLite) this.objects[i3]).getSerializedSize();
                } else {
                    if (i6 != 5) {
                        int i7 = InvalidProtocolBufferException.$r8$clinit;
                        throw new IllegalStateException(
                                new InvalidProtocolBufferException.InvalidWireTypeException(
                                        "Protocol message tag had invalid wire type."));
                    }
                    ((Integer) this.objects[i3]).getClass();
                    computeFixed64Size = CodedOutputStream$ArrayEncoder.computeFixed32Size(i5);
                }
                i2 = computeFixed64Size + i2;
            } else {
                long longValue = ((Long) this.objects[i3]).longValue();
                computeTagSize = CodedOutputStream$ArrayEncoder.computeTagSize(i5);
                computeUInt64SizeNoTag =
                        CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(longValue);
            }
            i2 = computeUInt64SizeNoTag + computeTagSize + i2;
        }
        this.memoizedSerializedSize = i2;
        return i2;
    }

    public final int hashCode() {
        int i = this.count;
        int i2 = (FileType.TORRENT + i) * 31;
        int[] iArr = this.tags;
        int i3 = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        int i6 = (i2 + i4) * 31;
        Object[] objArr = this.objects;
        int i7 = this.count;
        for (int i8 = 0; i8 < i7; i8++) {
            i3 = (i3 * 31) + objArr[i8].hashCode();
        }
        return i6 + i3;
    }

    public final void storeField(int i, Object obj) {
        if (!this.isMutable) {
            throw new UnsupportedOperationException();
        }
        ensureCapacity(this.count + 1);
        int[] iArr = this.tags;
        int i2 = this.count;
        iArr[i2] = i;
        this.objects[i2] = obj;
        this.count = i2 + 1;
    }

    public final void writeTo(CodedOutputStreamWriter codedOutputStreamWriter) {
        if (this.count == 0) {
            return;
        }
        codedOutputStreamWriter.getClass();
        for (int i = 0; i < this.count; i++) {
            int i2 = this.tags[i];
            Object obj = this.objects[i];
            int i3 = i2 >>> 3;
            int i4 = i2 & 7;
            CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                    codedOutputStreamWriter.output;
            if (i4 == 0) {
                codedOutputStream$ArrayEncoder.writeUInt64(i3, ((Long) obj).longValue());
            } else if (i4 == 1) {
                codedOutputStream$ArrayEncoder.writeFixed64(i3, ((Long) obj).longValue());
            } else if (i4 == 2) {
                codedOutputStreamWriter.writeBytes(i3, (ByteString) obj);
            } else if (i4 == 3) {
                codedOutputStream$ArrayEncoder.writeTag(i3, 3);
                ((UnknownFieldSetLite) obj).writeTo(codedOutputStreamWriter);
                codedOutputStream$ArrayEncoder.writeTag(i3, 4);
            } else {
                if (i4 != 5) {
                    int i5 = InvalidProtocolBufferException.$r8$clinit;
                    throw new RuntimeException(
                            new InvalidProtocolBufferException.InvalidWireTypeException(
                                    "Protocol message tag had invalid wire type."));
                }
                codedOutputStream$ArrayEncoder.writeFixed32(i3, ((Integer) obj).intValue());
            }
        }
    }
}
