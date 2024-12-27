package com.google.protobuf;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ArrayDecoders {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Registers {
        public int int1;
        public long long1;
        public Object object1;
    }

    public static int decodeBytes(byte[] bArr, int i, Registers registers) {
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1;
        if (i2 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i2 > bArr.length - decodeVarint32) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        if (i2 == 0) {
            registers.object1 = ByteString.EMPTY;
            return decodeVarint32;
        }
        registers.object1 = ByteString.copyFrom(bArr, decodeVarint32, i2);
        return decodeVarint32 + i2;
    }

    public static int decodeFixed32(int i, byte[] bArr) {
        return ((bArr[i + 3] & 255) << 24)
                | (bArr[i] & 255)
                | ((bArr[i + 1] & 255) << 8)
                | ((bArr[i + 2] & 255) << 16);
    }

    public static long decodeFixed64(int i, byte[] bArr) {
        return ((bArr[i + 7] & 255) << 56)
                | (bArr[i] & 255)
                | ((bArr[i + 1] & 255) << 8)
                | ((bArr[i + 2] & 255) << 16)
                | ((bArr[i + 3] & 255) << 24)
                | ((bArr[i + 4] & 255) << 32)
                | ((bArr[i + 5] & 255) << 40)
                | ((bArr[i + 6] & 255) << 48);
    }

    public static int decodeMessageList(
            Schema schema,
            int i,
            byte[] bArr,
            int i2,
            int i3,
            Internal.ProtobufList protobufList,
            Registers registers) {
        GeneratedMessageLite newInstance = schema.newInstance();
        int mergeMessageField = mergeMessageField(newInstance, schema, bArr, i2, i3, registers);
        schema.makeImmutable(newInstance);
        registers.object1 = newInstance;
        protobufList.add(newInstance);
        while (mergeMessageField < i3) {
            int decodeVarint32 = decodeVarint32(bArr, mergeMessageField, registers);
            if (i != registers.int1) {
                break;
            }
            GeneratedMessageLite newInstance2 = schema.newInstance();
            int mergeMessageField2 =
                    mergeMessageField(newInstance2, schema, bArr, decodeVarint32, i3, registers);
            schema.makeImmutable(newInstance2);
            registers.object1 = newInstance2;
            protobufList.add(newInstance2);
            mergeMessageField = mergeMessageField2;
        }
        return mergeMessageField;
    }

    public static int decodeString(byte[] bArr, int i, Registers registers) {
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1;
        if (i2 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i2 == 0) {
            registers.object1 = ApnSettings.MVNO_NONE;
            return decodeVarint32;
        }
        registers.object1 = new String(bArr, decodeVarint32, i2, Internal.UTF_8);
        return decodeVarint32 + i2;
    }

    public static int decodeStringRequireUtf8(byte[] bArr, int i, Registers registers) {
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1;
        if (i2 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i2 == 0) {
            registers.object1 = ApnSettings.MVNO_NONE;
            return decodeVarint32;
        }
        Utf8.processor.getClass();
        if ((decodeVarint32 | i2 | ((bArr.length - decodeVarint32) - i2)) < 0) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format(
                            "buffer length=%d, index=%d, size=%d",
                            Integer.valueOf(bArr.length),
                            Integer.valueOf(decodeVarint32),
                            Integer.valueOf(i2)));
        }
        int i3 = decodeVarint32 + i2;
        char[] cArr = new char[i2];
        int i4 = 0;
        while (decodeVarint32 < i3) {
            byte b = bArr[decodeVarint32];
            if (b < 0) {
                break;
            }
            decodeVarint32++;
            cArr[i4] = (char) b;
            i4++;
        }
        while (decodeVarint32 < i3) {
            int i5 = decodeVarint32 + 1;
            byte b2 = bArr[decodeVarint32];
            if (b2 >= 0) {
                int i6 = i4 + 1;
                cArr[i4] = (char) b2;
                while (i5 < i3) {
                    byte b3 = bArr[i5];
                    if (b3 < 0) {
                        break;
                    }
                    i5++;
                    cArr[i6] = (char) b3;
                    i6++;
                }
                i4 = i6;
                decodeVarint32 = i5;
            } else if (b2 < -32) {
                if (i5 >= i3) {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
                decodeVarint32 += 2;
                byte b4 = bArr[i5];
                int i7 = i4 + 1;
                if (b2 < -62 || Utf8.DecodeUtil.isNotTrailingByte(b4)) {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
                cArr[i4] = (char) ((b4 & 63) | ((b2 & 31) << 6));
                i4 = i7;
            } else {
                if (b2 >= -16) {
                    if (i5 >= i3 - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    byte b5 = bArr[i5];
                    int i8 = decodeVarint32 + 3;
                    byte b6 = bArr[decodeVarint32 + 2];
                    decodeVarint32 += 4;
                    byte b7 = bArr[i8];
                    int i9 = i4 + 1;
                    if (!Utf8.DecodeUtil.isNotTrailingByte(b5)) {
                        if ((((b5 + 112) + (b2 << 28)) >> 30) == 0
                                && !Utf8.DecodeUtil.isNotTrailingByte(b6)
                                && !Utf8.DecodeUtil.isNotTrailingByte(b7)) {
                            int i10 =
                                    ((b5 & 63) << 12)
                                            | ((b2 & 7) << 18)
                                            | ((b6 & 63) << 6)
                                            | (b7 & 63);
                            cArr[i4] = (char) ((i10 >>> 10) + 55232);
                            cArr[i9] = (char) ((i10 & 1023) + 56320);
                            i4 += 2;
                        }
                    }
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
                if (i5 >= i3 - 1) {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
                int i11 = decodeVarint32 + 2;
                byte b8 = bArr[i5];
                decodeVarint32 += 3;
                byte b9 = bArr[i11];
                int i12 = i4 + 1;
                if (Utf8.DecodeUtil.isNotTrailingByte(b8)
                        || ((b2 == -32 && b8 < -96)
                                || ((b2 == -19 && b8 >= -96)
                                        || Utf8.DecodeUtil.isNotTrailingByte(b9)))) {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
                cArr[i4] = (char) (((b8 & 63) << 6) | ((b2 & 15) << 12) | (b9 & 63));
                i4 = i12;
            }
        }
        registers.object1 = new String(cArr, 0, i4);
        return i3;
    }

    public static int decodeUnknownField(
            int i,
            byte[] bArr,
            int i2,
            int i3,
            UnknownFieldSetLite unknownFieldSetLite,
            Registers registers) {
        if ((i >>> 3) == 0) {
            throw new InvalidProtocolBufferException(
                    "Protocol message contained an invalid tag (zero).");
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int decodeVarint64 = decodeVarint64(bArr, i2, registers);
            unknownFieldSetLite.storeField(i, Long.valueOf(registers.long1));
            return decodeVarint64;
        }
        if (i4 == 1) {
            unknownFieldSetLite.storeField(i, Long.valueOf(decodeFixed64(i2, bArr)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int decodeVarint32 = decodeVarint32(bArr, i2, registers);
            int i5 = registers.int1;
            if (i5 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (i5 > bArr.length - decodeVarint32) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (i5 == 0) {
                unknownFieldSetLite.storeField(i, ByteString.EMPTY);
            } else {
                unknownFieldSetLite.storeField(i, ByteString.copyFrom(bArr, decodeVarint32, i5));
            }
            return decodeVarint32 + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw new InvalidProtocolBufferException(
                        "Protocol message contained an invalid tag (zero).");
            }
            unknownFieldSetLite.storeField(i, Integer.valueOf(decodeFixed32(i2, bArr)));
            return i2 + 4;
        }
        UnknownFieldSetLite newInstance = UnknownFieldSetLite.newInstance();
        int i6 = (i & (-8)) | 4;
        int i7 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int decodeVarint322 = decodeVarint32(bArr, i2, registers);
            int i8 = registers.int1;
            if (i8 == i6) {
                i7 = i8;
                i2 = decodeVarint322;
                break;
            }
            i7 = i8;
            i2 = decodeUnknownField(i8, bArr, decodeVarint322, i3, newInstance, registers);
        }
        if (i2 > i3 || i7 != i6) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        unknownFieldSetLite.storeField(i, newInstance);
        return i2;
    }

    public static int decodeVarint32(byte[] bArr, int i, Registers registers) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return decodeVarint32(b, bArr, i2, registers);
        }
        registers.int1 = b;
        return i2;
    }

    public static int decodeVarint32List(
            int i,
            byte[] bArr,
            int i2,
            int i3,
            Internal.ProtobufList protobufList,
            Registers registers) {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        intArrayList.addInt(registers.int1);
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            intArrayList.addInt(registers.int1);
        }
        return decodeVarint32;
    }

    public static int decodeVarint64(byte[] bArr, int i, Registers registers) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            registers.long1 = j;
            return i2;
        }
        int i3 = i + 2;
        byte b = bArr[i2];
        long j2 = (j & 127) | ((b & Byte.MAX_VALUE) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            i4 += 7;
            j2 |= (r10 & Byte.MAX_VALUE) << i4;
            b = bArr[i3];
            i3 = i5;
        }
        registers.long1 = j2;
        return i3;
    }

    public static int mergeMessageField(
            Object obj, Schema schema, byte[] bArr, int i, int i2, Registers registers) {
        int i3 = i + 1;
        int i4 = bArr[i];
        if (i4 < 0) {
            i3 = decodeVarint32(i4, bArr, i3, registers);
            i4 = registers.int1;
        }
        int i5 = i3;
        if (i4 < 0 || i4 > i2 - i5) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int i6 = i4 + i5;
        schema.mergeFrom(obj, bArr, i5, i6, registers);
        registers.object1 = obj;
        return i6;
    }

    public static int skipField(int i, byte[] bArr, int i2, int i3, Registers registers) {
        if ((i >>> 3) == 0) {
            throw new InvalidProtocolBufferException(
                    "Protocol message contained an invalid tag (zero).");
        }
        int i4 = i & 7;
        if (i4 == 0) {
            return decodeVarint64(bArr, i2, registers);
        }
        if (i4 == 1) {
            return i2 + 8;
        }
        if (i4 == 2) {
            return decodeVarint32(bArr, i2, registers) + registers.int1;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                return i2 + 4;
            }
            throw new InvalidProtocolBufferException(
                    "Protocol message contained an invalid tag (zero).");
        }
        int i5 = (i & (-8)) | 4;
        int i6 = 0;
        while (i2 < i3) {
            i2 = decodeVarint32(bArr, i2, registers);
            i6 = registers.int1;
            if (i6 == i5) {
                break;
            }
            i2 = skipField(i6, bArr, i2, i3, registers);
        }
        if (i2 > i3 || i6 != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        return i2;
    }

    public static int decodeVarint32(int i, byte[] bArr, int i2, Registers registers) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            registers.int1 = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i2 + 2;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            registers.int1 = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i2 + 3;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            registers.int1 = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i2 + 4;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            registers.int1 = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                registers.int1 = i11;
                return i12;
            }
            i10 = i12;
        }
    }
}
