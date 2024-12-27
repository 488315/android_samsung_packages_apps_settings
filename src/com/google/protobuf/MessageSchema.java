package com.google.protobuf;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.samsung.android.knox.net.apn.ApnSettings;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MessageSchema implements Schema {
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Unsafe UNSAFE;
    public final int[] buffer;
    public final int checkInitializedCount;
    public final MessageLite defaultInstance;
    public final int[] intArray;
    public final ListFieldSchema listFieldSchema;
    public final MapFieldSchemaLite mapFieldSchema;
    public final int maxFieldNumber;
    public final int minFieldNumber;
    public final NewInstanceSchemaLite newInstanceSchema;
    public final Object[] objects;
    public final boolean proto3;
    public final int repeatedFieldOffsetStart;
    public final UnknownFieldSchema unknownFieldSchema;

    static {
        Unsafe unsafe;
        try {
            unsafe = (Unsafe) AccessController.doPrivileged(new UnsafeUtil.AnonymousClass1());
        } catch (Throwable unused) {
            unsafe = null;
        }
        UNSAFE = unsafe;
    }

    public MessageSchema(
            int[] iArr,
            Object[] objArr,
            int i,
            int i2,
            MessageLite messageLite,
            boolean z,
            int[] iArr2,
            int i3,
            int i4,
            NewInstanceSchemaLite newInstanceSchemaLite,
            ListFieldSchema listFieldSchema,
            UnknownFieldSchema unknownFieldSchema,
            ExtensionSchemaLite extensionSchemaLite,
            MapFieldSchemaLite mapFieldSchemaLite) {
        this.buffer = iArr;
        this.objects = objArr;
        this.minFieldNumber = i;
        this.maxFieldNumber = i2;
        boolean z2 = messageLite instanceof GeneratedMessageLite;
        this.proto3 = z;
        this.intArray = iArr2;
        this.checkInitializedCount = i3;
        this.repeatedFieldOffsetStart = i4;
        this.newInstanceSchema = newInstanceSchemaLite;
        this.listFieldSchema = listFieldSchema;
        this.unknownFieldSchema = unknownFieldSchema;
        this.defaultInstance = messageLite;
        this.mapFieldSchema = mapFieldSchemaLite;
    }

    public static void checkMutable(Object obj) {
        if (isMutable(obj)) {
            return;
        }
        throw new IllegalArgumentException("Mutating immutable message: " + obj);
    }

    public static int decodeMapEntryValue(
            byte[] bArr,
            int i,
            int i2,
            WireFormat$FieldType wireFormat$FieldType,
            Class cls,
            ArrayDecoders.Registers registers) {
        switch (wireFormat$FieldType.ordinal()) {
            case 0:
                registers.object1 =
                        Double.valueOf(
                                Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i, bArr)));
                return i + 8;
            case 1:
                registers.object1 =
                        Float.valueOf(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i, bArr)));
                return i + 4;
            case 2:
            case 3:
                int decodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = Long.valueOf(registers.long1);
                return decodeVarint64;
            case 4:
            case 12:
            case 13:
                int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                registers.object1 = Integer.valueOf(registers.int1);
                return decodeVarint32;
            case 5:
            case 15:
                registers.object1 = Long.valueOf(ArrayDecoders.decodeFixed64(i, bArr));
                return i + 8;
            case 6:
            case 14:
                registers.object1 = Integer.valueOf(ArrayDecoders.decodeFixed32(i, bArr));
                return i + 4;
            case 7:
                int decodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = Boolean.valueOf(registers.long1 != 0);
                return decodeVarint642;
            case 8:
                return ArrayDecoders.decodeStringRequireUtf8(bArr, i, registers);
            case 9:
            default:
                throw new RuntimeException("unsupported field type.");
            case 10:
                Schema schemaFor = Protobuf.INSTANCE.schemaFor(cls);
                GeneratedMessageLite newInstance = schemaFor.newInstance();
                int mergeMessageField =
                        ArrayDecoders.mergeMessageField(
                                newInstance, schemaFor, bArr, i, i2, registers);
                schemaFor.makeImmutable(newInstance);
                registers.object1 = newInstance;
                return mergeMessageField;
            case 11:
                return ArrayDecoders.decodeBytes(bArr, i, registers);
            case 16:
                int decodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                registers.object1 =
                        Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                return decodeVarint322;
            case 17:
                int decodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                return decodeVarint643;
        }
    }

    public static UnknownFieldSetLite getMutableUnknownFields(Object obj) {
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
        UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        if (unknownFieldSetLite != UnknownFieldSetLite.DEFAULT_INSTANCE) {
            return unknownFieldSetLite;
        }
        UnknownFieldSetLite newInstance = UnknownFieldSetLite.newInstance();
        generatedMessageLite.unknownFields = newInstance;
        return newInstance;
    }

    public static boolean isMutable(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof GeneratedMessageLite) {
            return ((GeneratedMessageLite) obj).isMutable();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x027e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.protobuf.MessageSchema newSchema(
            com.google.protobuf.RawMessageInfo r34,
            com.google.protobuf.NewInstanceSchemaLite r35,
            com.google.protobuf.ListFieldSchema r36,
            com.google.protobuf.UnknownFieldSchema r37,
            com.google.protobuf.ExtensionSchemaLite r38,
            com.google.protobuf.MapFieldSchemaLite r39) {
        /*
            Method dump skipped, instructions count: 1041
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.protobuf.MessageSchema.newSchema(com.google.protobuf.RawMessageInfo,"
                    + " com.google.protobuf.NewInstanceSchemaLite,"
                    + " com.google.protobuf.ListFieldSchema,"
                    + " com.google.protobuf.UnknownFieldSchema,"
                    + " com.google.protobuf.ExtensionSchemaLite,"
                    + " com.google.protobuf.MapFieldSchemaLite):com.google.protobuf.MessageSchema");
    }

    public static int oneofIntAt(long j, Object obj) {
        return ((Integer) UnsafeUtil.getObject(j, obj)).intValue();
    }

    public static long oneofLongAt(long j, Object obj) {
        return ((Long) UnsafeUtil.getObject(j, obj)).longValue();
    }

    public static Field reflectField(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m("Field ", str, " for ");
            m.append(cls.getName());
            m.append(" not found. Known fields are ");
            m.append(Arrays.toString(declaredFields));
            throw new RuntimeException(m.toString());
        }
    }

    public static int type(int i) {
        return (i & 267386880) >>> 20;
    }

    public static void writeString(
            int i, Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        if (!(obj instanceof String)) {
            codedOutputStreamWriter.writeBytes(i, (ByteString) obj);
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        codedOutputStream$ArrayEncoder.writeStringNoTag((String) obj);
    }

    public final boolean arePresentForEquals(int i, Object obj, Object obj2) {
        return isFieldPresent(i, obj) == isFieldPresent(i, obj2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x006c, code lost:

       if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0080, code lost:

       if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0092, code lost:

       if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a6, code lost:

       if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b8, code lost:

       if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ca, code lost:

       if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00dc, code lost:

       if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f2, code lost:

       if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0108, code lost:

       if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x011e, code lost:

       if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0132, code lost:

       if (r5.getBoolean(r7, r12) == r5.getBoolean(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0144, code lost:

       if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0158, code lost:

       if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x016a, code lost:

       if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x017d, code lost:

       if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0190, code lost:

       if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01ab, code lost:

       if (java.lang.Float.floatToIntBits(r5.getFloat(r7, r12)) == java.lang.Float.floatToIntBits(r5.getFloat(r7, r13))) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01c8, code lost:

       if (java.lang.Double.doubleToLongBits(r5.getDouble(r7, r12)) == java.lang.Double.doubleToLongBits(r5.getDouble(r7, r13))) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0037, code lost:

       if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
    */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(java.lang.Object r12, java.lang.Object r13) {
        /*
            Method dump skipped, instructions count: 634
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.protobuf.MessageSchema.equals(java.lang.Object,"
                    + " java.lang.Object):boolean");
    }

    public final Internal.EnumVerifier getEnumFieldVerifier(int i) {
        return (Internal.EnumVerifier) this.objects[((i / 3) * 2) + 1];
    }

    public final Object getMapFieldDefaultEntry(int i) {
        return this.objects[(i / 3) * 2];
    }

    public final Schema getMessageFieldSchema(int i) {
        int i2 = (i / 3) * 2;
        Object[] objArr = this.objects;
        Schema schema = (Schema) objArr[i2];
        if (schema != null) {
            return schema;
        }
        Schema schemaFor = Protobuf.INSTANCE.schemaFor((Class) objArr[i2 + 1]);
        objArr[i2] = schemaFor;
        return schemaFor;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.protobuf.Schema
    public final int getSerializedSize(Object obj) {
        int i;
        int i2;
        int i3;
        char c;
        int computeTagSize;
        int computeUInt64SizeNoTag;
        int i4;
        int computeBytesSize;
        int computeTagSize2;
        int computeUInt32SizeNoTag;
        int computeGroupSize;
        int computeTagSize3;
        int i5;
        char c2;
        char c3;
        int computeTagSize4;
        int computeUInt64SizeNoTag2;
        int computeTagSize5;
        int computeInt32SizeNoTag;
        int computeTagSize6;
        int computeUInt64SizeNoTag3;
        int computeTagSize7;
        int computeInt32SizeNoTag2;
        int computeFixed64Size;
        int computeTagSize8;
        int computeStringSizeNoTag;
        int computeBytesSize2;
        int i6;
        int computeTagSize9;
        int computeUInt64SizeNoTag4;
        boolean z = this.proto3;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        MapFieldSchemaLite mapFieldSchemaLite = this.mapFieldSchema;
        int i7 = 1;
        int i8 = 1048575;
        int[] iArr = this.buffer;
        if (z) {
            Unsafe unsafe = UNSAFE;
            int i9 = 0;
            int i10 = 0;
            while (i9 < iArr.length) {
                int typeAndOffsetAt = typeAndOffsetAt(i9);
                int type = type(typeAndOffsetAt);
                int i11 = iArr[i9];
                long j = typeAndOffsetAt & i8;
                if (type >= FieldType.DOUBLE_LIST_PACKED.id()
                        && type <= FieldType.SINT64_LIST_PACKED.id()) {
                    int i12 = iArr[i9 + 2];
                }
                switch (type) {
                    case 0:
                        if (isFieldPresent(i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 8, i10);
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (isFieldPresent(i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 4, i10);
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (isFieldPresent(i9, obj)) {
                            long j2 = UnsafeUtil.getLong(j, obj);
                            computeTagSize6 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeUInt64SizeNoTag3 =
                                    CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(j2);
                            computeFixed64Size = computeUInt64SizeNoTag3 + computeTagSize6;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (isFieldPresent(i9, obj)) {
                            long j3 = UnsafeUtil.getLong(j, obj);
                            computeTagSize6 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeUInt64SizeNoTag3 =
                                    CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(j3);
                            computeFixed64Size = computeUInt64SizeNoTag3 + computeTagSize6;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (isFieldPresent(i9, obj)) {
                            int i13 = UnsafeUtil.getInt(j, obj);
                            computeTagSize7 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeInt32SizeNoTag2 =
                                    CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(i13);
                            computeFixed64Size = computeInt32SizeNoTag2 + computeTagSize7;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (isFieldPresent(i9, obj)) {
                            computeFixed64Size =
                                    CodedOutputStream$ArrayEncoder.computeFixed64Size(i11);
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (isFieldPresent(i9, obj)) {
                            computeFixed64Size =
                                    CodedOutputStream$ArrayEncoder.computeFixed32Size(i11);
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (isFieldPresent(i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 1, i10);
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (!isFieldPresent(i9, obj)) {
                            break;
                        } else {
                            Object object = UnsafeUtil.getObject(j, obj);
                            if (object instanceof ByteString) {
                                computeBytesSize2 =
                                        CodedOutputStream$ArrayEncoder.computeBytesSize(
                                                i11, (ByteString) object);
                                i10 = computeBytesSize2 + i10;
                                break;
                            } else {
                                computeTagSize8 =
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                                computeStringSizeNoTag =
                                        CodedOutputStream$ArrayEncoder.computeStringSizeNoTag(
                                                (String) object);
                                computeBytesSize2 = computeStringSizeNoTag + computeTagSize8;
                                i10 = computeBytesSize2 + i10;
                            }
                        }
                    case 9:
                        if (isFieldPresent(i9, obj)) {
                            Object object2 = UnsafeUtil.getObject(j, obj);
                            Schema messageFieldSchema = getMessageFieldSchema(i9);
                            Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                            int computeTagSize10 =
                                    CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            int serializedSize =
                                    ((AbstractMessageLite) ((MessageLite) object2))
                                            .getSerializedSize(messageFieldSchema);
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            serializedSize, serializedSize, computeTagSize10, i10);
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (isFieldPresent(i9, obj)) {
                            computeFixed64Size =
                                    CodedOutputStream$ArrayEncoder.computeBytesSize(
                                            i11, (ByteString) UnsafeUtil.getObject(j, obj));
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (isFieldPresent(i9, obj)) {
                            int i14 = UnsafeUtil.getInt(j, obj);
                            computeTagSize7 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeInt32SizeNoTag2 =
                                    CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(i14);
                            computeFixed64Size = computeInt32SizeNoTag2 + computeTagSize7;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (isFieldPresent(i9, obj)) {
                            int i15 = UnsafeUtil.getInt(j, obj);
                            computeTagSize7 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeInt32SizeNoTag2 =
                                    CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(i15);
                            computeFixed64Size = computeInt32SizeNoTag2 + computeTagSize7;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (isFieldPresent(i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 4, i10);
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (isFieldPresent(i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 8, i10);
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (isFieldPresent(i9, obj)) {
                            int i16 = UnsafeUtil.getInt(j, obj);
                            computeTagSize7 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeInt32SizeNoTag2 =
                                    CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(
                                            (i16 >> 31) ^ (i16 << 1));
                            computeFixed64Size = computeInt32SizeNoTag2 + computeTagSize7;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (isFieldPresent(i9, obj)) {
                            long j4 = UnsafeUtil.getLong(j, obj);
                            computeTagSize6 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeUInt64SizeNoTag3 =
                                    CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                                            (j4 >> 63) ^ (j4 << 1));
                            computeFixed64Size = computeUInt64SizeNoTag3 + computeTagSize6;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (isFieldPresent(i9, obj)) {
                            computeFixed64Size =
                                    CodedOutputStream$ArrayEncoder.computeGroupSize(
                                            i11,
                                            (MessageLite) UnsafeUtil.getObject(j, obj),
                                            getMessageFieldSchema(i9));
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        computeFixed64Size =
                                SchemaUtil.computeSizeFixed64List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 19:
                        computeFixed64Size =
                                SchemaUtil.computeSizeFixed32List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 20:
                        computeFixed64Size =
                                SchemaUtil.computeSizeInt64List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 21:
                        computeFixed64Size =
                                SchemaUtil.computeSizeUInt64List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 22:
                        computeFixed64Size =
                                SchemaUtil.computeSizeInt32List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 23:
                        computeFixed64Size =
                                SchemaUtil.computeSizeFixed64List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 24:
                        computeFixed64Size =
                                SchemaUtil.computeSizeFixed32List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 25:
                        List list = (List) UnsafeUtil.getObject(j, obj);
                        Class cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size = list.size();
                        i10 +=
                                size == 0
                                        ? 0
                                        : (CodedOutputStream$ArrayEncoder.computeTagSize(i11) + 1)
                                                * size;
                        break;
                    case 26:
                        computeFixed64Size =
                                SchemaUtil.computeSizeStringList(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 27:
                        computeFixed64Size =
                                SchemaUtil.computeSizeMessageList(
                                        i11,
                                        (List) UnsafeUtil.getObject(j, obj),
                                        getMessageFieldSchema(i9));
                        i10 += computeFixed64Size;
                        break;
                    case 28:
                        computeFixed64Size =
                                SchemaUtil.computeSizeByteStringList(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 29:
                        computeFixed64Size =
                                SchemaUtil.computeSizeUInt32List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 30:
                        computeFixed64Size =
                                SchemaUtil.computeSizeEnumList(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 31:
                        computeFixed64Size =
                                SchemaUtil.computeSizeFixed32List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 32:
                        computeFixed64Size =
                                SchemaUtil.computeSizeFixed64List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 33:
                        computeFixed64Size =
                                SchemaUtil.computeSizeSInt32List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 34:
                        computeFixed64Size =
                                SchemaUtil.computeSizeSInt64List(
                                        i11, (List) UnsafeUtil.getObject(j, obj));
                        i10 += computeFixed64Size;
                        break;
                    case 35:
                        int computeSizeFixed64ListNoTag =
                                SchemaUtil.computeSizeFixed64ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeFixed64ListNoTag,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeFixed64ListNoTag,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 36:
                        int computeSizeFixed32ListNoTag =
                                SchemaUtil.computeSizeFixed32ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeFixed32ListNoTag > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeFixed32ListNoTag,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeFixed32ListNoTag,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 37:
                        int computeSizeInt64ListNoTag =
                                SchemaUtil.computeSizeInt64ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeInt64ListNoTag > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeInt64ListNoTag,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeInt64ListNoTag,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 38:
                        int computeSizeUInt64ListNoTag =
                                SchemaUtil.computeSizeUInt64ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeUInt64ListNoTag > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeUInt64ListNoTag,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeUInt64ListNoTag,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 39:
                        int computeSizeInt32ListNoTag =
                                SchemaUtil.computeSizeInt32ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeInt32ListNoTag > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeInt32ListNoTag,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeInt32ListNoTag,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 40:
                        int computeSizeFixed64ListNoTag2 =
                                SchemaUtil.computeSizeFixed64ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeFixed64ListNoTag2,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeFixed64ListNoTag2,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 41:
                        int computeSizeFixed32ListNoTag2 =
                                SchemaUtil.computeSizeFixed32ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeFixed32ListNoTag2 > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeFixed32ListNoTag2,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeFixed32ListNoTag2,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 42:
                        List list2 = (List) unsafe.getObject(obj, j);
                        Class cls3 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size2 = list2.size();
                        if (size2 > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            size2,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            size2,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 43:
                        int computeSizeUInt32ListNoTag =
                                SchemaUtil.computeSizeUInt32ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeUInt32ListNoTag > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeUInt32ListNoTag,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeUInt32ListNoTag,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 44:
                        int computeSizeEnumListNoTag =
                                SchemaUtil.computeSizeEnumListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeEnumListNoTag > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeEnumListNoTag,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeEnumListNoTag,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 45:
                        int computeSizeFixed32ListNoTag3 =
                                SchemaUtil.computeSizeFixed32ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeFixed32ListNoTag3 > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeFixed32ListNoTag3,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeFixed32ListNoTag3,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 46:
                        int computeSizeFixed64ListNoTag3 =
                                SchemaUtil.computeSizeFixed64ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag3 > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeFixed64ListNoTag3,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeFixed64ListNoTag3,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 47:
                        int computeSizeSInt32ListNoTag =
                                SchemaUtil.computeSizeSInt32ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeSInt32ListNoTag > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeSInt32ListNoTag,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeSInt32ListNoTag,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 48:
                        int computeSizeSInt64ListNoTag =
                                SchemaUtil.computeSizeSInt64ListNoTag(
                                        (List) unsafe.getObject(obj, j));
                        if (computeSizeSInt64ListNoTag > 0) {
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            computeSizeSInt64ListNoTag,
                                            CodedOutputStream$ArrayEncoder.computeTagSize(i11),
                                            computeSizeSInt64ListNoTag,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 49:
                        List list3 = (List) UnsafeUtil.getObject(j, obj);
                        Schema messageFieldSchema2 = getMessageFieldSchema(i9);
                        Class cls4 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size3 = list3.size();
                        if (size3 == 0) {
                            i6 = 0;
                        } else {
                            i6 = 0;
                            for (int i17 = 0; i17 < size3; i17++) {
                                i6 =
                                        CodedOutputStream$ArrayEncoder.computeGroupSize(
                                                        i11,
                                                        (MessageLite) list3.get(i17),
                                                        messageFieldSchema2)
                                                + i6;
                            }
                        }
                        i10 = i6 + i10;
                        break;
                    case 50:
                        Object object3 = UnsafeUtil.getObject(j, obj);
                        Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i9);
                        mapFieldSchemaLite.getClass();
                        computeFixed64Size =
                                MapFieldSchemaLite.getSerializedSize(
                                        i11, object3, mapFieldDefaultEntry);
                        i10 += computeFixed64Size;
                        break;
                    case 51:
                        if (isOneofPresent(i11, i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 8, i10);
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (isOneofPresent(i11, i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 4, i10);
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (isOneofPresent(i11, i9, obj)) {
                            long oneofLongAt = oneofLongAt(j, obj);
                            computeTagSize9 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeUInt64SizeNoTag4 =
                                    CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                                            oneofLongAt);
                            computeFixed64Size = computeUInt64SizeNoTag4 + computeTagSize9;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (isOneofPresent(i11, i9, obj)) {
                            long oneofLongAt2 = oneofLongAt(j, obj);
                            computeTagSize9 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeUInt64SizeNoTag4 =
                                    CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                                            oneofLongAt2);
                            computeFixed64Size = computeUInt64SizeNoTag4 + computeTagSize9;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (isOneofPresent(i11, i9, obj)) {
                            int oneofIntAt = oneofIntAt(j, obj);
                            computeTagSize7 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeInt32SizeNoTag2 =
                                    CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(
                                            oneofIntAt);
                            computeFixed64Size = computeInt32SizeNoTag2 + computeTagSize7;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (isOneofPresent(i11, i9, obj)) {
                            computeFixed64Size =
                                    CodedOutputStream$ArrayEncoder.computeFixed64Size(i11);
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (isOneofPresent(i11, i9, obj)) {
                            computeFixed64Size =
                                    CodedOutputStream$ArrayEncoder.computeFixed32Size(i11);
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (isOneofPresent(i11, i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 1, i10);
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (!isOneofPresent(i11, i9, obj)) {
                            break;
                        } else {
                            Object object4 = UnsafeUtil.getObject(j, obj);
                            if (object4 instanceof ByteString) {
                                computeBytesSize2 =
                                        CodedOutputStream$ArrayEncoder.computeBytesSize(
                                                i11, (ByteString) object4);
                                i10 = computeBytesSize2 + i10;
                                break;
                            } else {
                                computeTagSize8 =
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                                computeStringSizeNoTag =
                                        CodedOutputStream$ArrayEncoder.computeStringSizeNoTag(
                                                (String) object4);
                                computeBytesSize2 = computeStringSizeNoTag + computeTagSize8;
                                i10 = computeBytesSize2 + i10;
                            }
                        }
                    case 60:
                        if (isOneofPresent(i11, i9, obj)) {
                            Object object5 = UnsafeUtil.getObject(j, obj);
                            Schema messageFieldSchema3 = getMessageFieldSchema(i9);
                            Class cls5 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                            int computeTagSize11 =
                                    CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            int serializedSize2 =
                                    ((AbstractMessageLite) ((MessageLite) object5))
                                            .getSerializedSize(messageFieldSchema3);
                            i10 =
                                    MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                            serializedSize2,
                                            serializedSize2,
                                            computeTagSize11,
                                            i10);
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (isOneofPresent(i11, i9, obj)) {
                            computeFixed64Size =
                                    CodedOutputStream$ArrayEncoder.computeBytesSize(
                                            i11, (ByteString) UnsafeUtil.getObject(j, obj));
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (isOneofPresent(i11, i9, obj)) {
                            int oneofIntAt2 = oneofIntAt(j, obj);
                            computeTagSize7 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeInt32SizeNoTag2 =
                                    CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(
                                            oneofIntAt2);
                            computeFixed64Size = computeInt32SizeNoTag2 + computeTagSize7;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (isOneofPresent(i11, i9, obj)) {
                            int oneofIntAt3 = oneofIntAt(j, obj);
                            computeTagSize7 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeInt32SizeNoTag2 =
                                    CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(
                                            oneofIntAt3);
                            computeFixed64Size = computeInt32SizeNoTag2 + computeTagSize7;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (isOneofPresent(i11, i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 4, i10);
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (isOneofPresent(i11, i9, obj)) {
                            i10 = MessageSchema$$ExternalSyntheticOutline0.m(i11, 8, i10);
                            break;
                        } else {
                            break;
                        }
                    case 66:
                        if (isOneofPresent(i11, i9, obj)) {
                            int oneofIntAt4 = oneofIntAt(j, obj);
                            computeTagSize7 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeInt32SizeNoTag2 =
                                    CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(
                                            (oneofIntAt4 >> 31) ^ (oneofIntAt4 << 1));
                            computeFixed64Size = computeInt32SizeNoTag2 + computeTagSize7;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 67:
                        if (isOneofPresent(i11, i9, obj)) {
                            long oneofLongAt3 = oneofLongAt(j, obj);
                            computeTagSize9 = CodedOutputStream$ArrayEncoder.computeTagSize(i11);
                            computeUInt64SizeNoTag4 =
                                    CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                                            (oneofLongAt3 >> 63) ^ (oneofLongAt3 << 1));
                            computeFixed64Size = computeUInt64SizeNoTag4 + computeTagSize9;
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 68:
                        if (isOneofPresent(i11, i9, obj)) {
                            computeFixed64Size =
                                    CodedOutputStream$ArrayEncoder.computeGroupSize(
                                            i11,
                                            (MessageLite) UnsafeUtil.getObject(j, obj),
                                            getMessageFieldSchema(i9));
                            i10 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                }
                i9 += 3;
                i8 = 1048575;
            }
            ((UnknownFieldSetLiteSchema) unknownFieldSchema).getClass();
            return ((GeneratedMessageLite) obj).unknownFields.getSerializedSize() + i10;
        }
        Unsafe unsafe2 = UNSAFE;
        int i18 = 0;
        int i19 = 0;
        int i20 = 1048575;
        int i21 = 0;
        while (i18 < iArr.length) {
            int typeAndOffsetAt2 = typeAndOffsetAt(i18);
            int i22 = iArr[i18];
            int type2 = type(typeAndOffsetAt2);
            if (type2 <= 17) {
                int i23 = iArr[i18 + 2];
                i = 1048575;
                int i24 = i23 & 1048575;
                i2 = i7 << (i23 >>> 20);
                if (i24 != i20) {
                    i21 = unsafe2.getInt(obj, i24);
                    i20 = i24;
                }
            } else {
                i = 1048575;
                i2 = 0;
            }
            int i25 = typeAndOffsetAt2 & i;
            int i26 = i20;
            long j5 = i25;
            switch (type2) {
                case 0:
                    i3 = 1;
                    c = '?';
                    if ((i21 & i2) != 0) {
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 8, i19);
                        break;
                    }
                    break;
                case 1:
                    i3 = 1;
                    c = '?';
                    if ((i21 & i2) != 0) {
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 4, i19);
                        break;
                    }
                case 2:
                    i3 = 1;
                    c = '?';
                    if ((i21 & i2) != 0) {
                        long j6 = unsafe2.getLong(obj, j5);
                        computeTagSize = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeUInt64SizeNoTag =
                                CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(j6);
                        i4 = computeUInt64SizeNoTag + computeTagSize;
                        i19 += i4;
                    }
                    break;
                case 3:
                    i3 = 1;
                    c = '?';
                    if ((i21 & i2) != 0) {
                        long j7 = unsafe2.getLong(obj, j5);
                        computeTagSize = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeUInt64SizeNoTag =
                                CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(j7);
                        i4 = computeUInt64SizeNoTag + computeTagSize;
                        i19 += i4;
                    }
                    break;
                case 4:
                    i3 = 1;
                    c = '?';
                    if ((i21 & i2) != 0) {
                        i4 =
                                CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(
                                                unsafe2.getInt(obj, j5))
                                        + CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        i19 += i4;
                    }
                    break;
                case 5:
                    i3 = 1;
                    c = '?';
                    if ((i21 & i2) != 0) {
                        i4 = CodedOutputStream$ArrayEncoder.computeFixed64Size(i22);
                        i19 += i4;
                    }
                    break;
                case 6:
                    i3 = 1;
                    c = '?';
                    if ((i21 & i2) != 0) {
                        i4 = CodedOutputStream$ArrayEncoder.computeFixed32Size(i22);
                        i19 += i4;
                    }
                    break;
                case 7:
                    c = '?';
                    if ((i21 & i2) != 0) {
                        i3 = 1;
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 1, i19);
                        break;
                    }
                    i3 = 1;
                case 8:
                    c = '?';
                    if ((i21 & i2) != 0) {
                        Object object6 = unsafe2.getObject(obj, j5);
                        i19 =
                                (object6 instanceof ByteString
                                                ? CodedOutputStream$ArrayEncoder.computeBytesSize(
                                                        i22, (ByteString) object6)
                                                : CodedOutputStream$ArrayEncoder
                                                                .computeStringSizeNoTag(
                                                                        (String) object6)
                                                        + CodedOutputStream$ArrayEncoder
                                                                .computeTagSize(i22))
                                        + i19;
                    }
                    i3 = 1;
                    break;
                case 9:
                    c = '?';
                    if ((i21 & i2) != 0) {
                        Object object7 = unsafe2.getObject(obj, j5);
                        Schema messageFieldSchema4 = getMessageFieldSchema(i18);
                        Class cls6 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int computeTagSize12 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        int serializedSize3 =
                                ((AbstractMessageLite) ((MessageLite) object7))
                                        .getSerializedSize(messageFieldSchema4);
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        serializedSize3, serializedSize3, computeTagSize12, i19);
                    }
                    i3 = 1;
                    break;
                case 10:
                    c = '?';
                    if ((i21 & i2) != 0) {
                        computeBytesSize =
                                CodedOutputStream$ArrayEncoder.computeBytesSize(
                                        i22, (ByteString) unsafe2.getObject(obj, j5));
                        i19 += computeBytesSize;
                    }
                    i3 = 1;
                    break;
                case 11:
                    c = '?';
                    if ((i21 & i2) != 0) {
                        int i27 = unsafe2.getInt(obj, j5);
                        computeTagSize2 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeUInt32SizeNoTag =
                                CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(i27);
                        computeBytesSize = computeUInt32SizeNoTag + computeTagSize2;
                        i19 += computeBytesSize;
                    }
                    i3 = 1;
                    break;
                case 12:
                    c = '?';
                    if ((i21 & i2) != 0) {
                        int i28 = unsafe2.getInt(obj, j5);
                        computeTagSize2 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeUInt32SizeNoTag =
                                CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(i28);
                        computeBytesSize = computeUInt32SizeNoTag + computeTagSize2;
                        i19 += computeBytesSize;
                    }
                    i3 = 1;
                    break;
                case 13:
                    c = '?';
                    if ((i21 & i2) != 0) {
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 4, i19);
                        i3 = 1;
                        break;
                    }
                    i3 = 1;
                case 14:
                    c = '?';
                    if ((i21 & i2) != 0) {
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 8, i19);
                        i3 = 1;
                        break;
                    }
                    i3 = 1;
                    break;
                case 15:
                    c = '?';
                    if ((i21 & i2) != 0) {
                        int i29 = unsafe2.getInt(obj, j5);
                        computeTagSize2 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeUInt32SizeNoTag =
                                CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(
                                        (i29 >> 31) ^ (i29 << 1));
                        computeBytesSize = computeUInt32SizeNoTag + computeTagSize2;
                        i19 += computeBytesSize;
                    }
                    i3 = 1;
                    break;
                case 16:
                    if ((i21 & i2) != 0) {
                        long j8 = unsafe2.getLong(obj, j5);
                        c = '?';
                        i19 +=
                                CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                                                (j8 >> 63) ^ (j8 << 1))
                                        + CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                    } else {
                        c = '?';
                    }
                    i3 = 1;
                    break;
                case 17:
                    if ((i21 & i2) != 0) {
                        computeGroupSize =
                                CodedOutputStream$ArrayEncoder.computeGroupSize(
                                        i22,
                                        (MessageLite) unsafe2.getObject(obj, j5),
                                        getMessageFieldSchema(i18));
                        i19 += computeGroupSize;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 18:
                    computeGroupSize =
                            SchemaUtil.computeSizeFixed64List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 19:
                    computeGroupSize =
                            SchemaUtil.computeSizeFixed32List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 20:
                    computeGroupSize =
                            SchemaUtil.computeSizeInt64List(i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 21:
                    computeGroupSize =
                            SchemaUtil.computeSizeUInt64List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 22:
                    computeGroupSize =
                            SchemaUtil.computeSizeInt32List(i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 23:
                    computeGroupSize =
                            SchemaUtil.computeSizeFixed64List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 24:
                    computeGroupSize =
                            SchemaUtil.computeSizeFixed32List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 25:
                    List list4 = (List) unsafe2.getObject(obj, j5);
                    Class cls7 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size4 = list4.size();
                    computeTagSize3 =
                            size4 == 0
                                    ? 0
                                    : (CodedOutputStream$ArrayEncoder.computeTagSize(i22) + 1)
                                            * size4;
                    i19 += computeTagSize3;
                    i3 = 1;
                    c = '?';
                    break;
                case 26:
                    computeGroupSize =
                            SchemaUtil.computeSizeStringList(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 27:
                    computeGroupSize =
                            SchemaUtil.computeSizeMessageList(
                                    i22,
                                    (List) unsafe2.getObject(obj, j5),
                                    getMessageFieldSchema(i18));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 28:
                    computeGroupSize =
                            SchemaUtil.computeSizeByteStringList(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 29:
                    computeGroupSize =
                            SchemaUtil.computeSizeUInt32List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 30:
                    computeGroupSize =
                            SchemaUtil.computeSizeEnumList(i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 31:
                    computeGroupSize =
                            SchemaUtil.computeSizeFixed32List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 32:
                    computeGroupSize =
                            SchemaUtil.computeSizeFixed64List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 33:
                    computeGroupSize =
                            SchemaUtil.computeSizeSInt32List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 34:
                    computeGroupSize =
                            SchemaUtil.computeSizeSInt64List(
                                    i22, (List) unsafe2.getObject(obj, j5));
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 35:
                    int computeSizeFixed64ListNoTag4 =
                            SchemaUtil.computeSizeFixed64ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag4 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeFixed64ListNoTag4,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeFixed64ListNoTag4,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 36:
                    int computeSizeFixed32ListNoTag4 =
                            SchemaUtil.computeSizeFixed32ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeFixed32ListNoTag4 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeFixed32ListNoTag4,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeFixed32ListNoTag4,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 37:
                    int computeSizeInt64ListNoTag2 =
                            SchemaUtil.computeSizeInt64ListNoTag((List) unsafe2.getObject(obj, j5));
                    if (computeSizeInt64ListNoTag2 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeInt64ListNoTag2,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeInt64ListNoTag2,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 38:
                    int computeSizeUInt64ListNoTag2 =
                            SchemaUtil.computeSizeUInt64ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeUInt64ListNoTag2 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeUInt64ListNoTag2,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeUInt64ListNoTag2,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 39:
                    int computeSizeInt32ListNoTag2 =
                            SchemaUtil.computeSizeInt32ListNoTag((List) unsafe2.getObject(obj, j5));
                    if (computeSizeInt32ListNoTag2 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeInt32ListNoTag2,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeInt32ListNoTag2,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 40:
                    int computeSizeFixed64ListNoTag5 =
                            SchemaUtil.computeSizeFixed64ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag5 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeFixed64ListNoTag5,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeFixed64ListNoTag5,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 41:
                    int computeSizeFixed32ListNoTag5 =
                            SchemaUtil.computeSizeFixed32ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeFixed32ListNoTag5 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeFixed32ListNoTag5,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeFixed32ListNoTag5,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 42:
                    List list5 = (List) unsafe2.getObject(obj, j5);
                    Class cls8 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size5 = list5.size();
                    if (size5 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        size5,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        size5,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 43:
                    int computeSizeUInt32ListNoTag2 =
                            SchemaUtil.computeSizeUInt32ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeUInt32ListNoTag2 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeUInt32ListNoTag2,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeUInt32ListNoTag2,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 44:
                    int computeSizeEnumListNoTag2 =
                            SchemaUtil.computeSizeEnumListNoTag((List) unsafe2.getObject(obj, j5));
                    if (computeSizeEnumListNoTag2 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeEnumListNoTag2,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeEnumListNoTag2,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 45:
                    int computeSizeFixed32ListNoTag6 =
                            SchemaUtil.computeSizeFixed32ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeFixed32ListNoTag6 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeFixed32ListNoTag6,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeFixed32ListNoTag6,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 46:
                    int computeSizeFixed64ListNoTag6 =
                            SchemaUtil.computeSizeFixed64ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag6 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeFixed64ListNoTag6,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeFixed64ListNoTag6,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 47:
                    int computeSizeSInt32ListNoTag2 =
                            SchemaUtil.computeSizeSInt32ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeSInt32ListNoTag2 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeSInt32ListNoTag2,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeSInt32ListNoTag2,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 48:
                    int computeSizeSInt64ListNoTag2 =
                            SchemaUtil.computeSizeSInt64ListNoTag(
                                    (List) unsafe2.getObject(obj, j5));
                    if (computeSizeSInt64ListNoTag2 > 0) {
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        computeSizeSInt64ListNoTag2,
                                        CodedOutputStream$ArrayEncoder.computeTagSize(i22),
                                        computeSizeSInt64ListNoTag2,
                                        i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 49:
                    List list6 = (List) unsafe2.getObject(obj, j5);
                    Schema messageFieldSchema5 = getMessageFieldSchema(i18);
                    Class cls9 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size6 = list6.size();
                    if (size6 == 0) {
                        i5 = 0;
                    } else {
                        int i30 = 0;
                        i5 = 0;
                        while (i30 < size6) {
                            i5 +=
                                    CodedOutputStream$ArrayEncoder.computeGroupSize(
                                            i22, (MessageLite) list6.get(i30), messageFieldSchema5);
                            i30++;
                            list6 = list6;
                        }
                    }
                    i19 += i5;
                    i3 = 1;
                    c = '?';
                    break;
                case 50:
                    Object object8 = unsafe2.getObject(obj, j5);
                    Object mapFieldDefaultEntry2 = getMapFieldDefaultEntry(i18);
                    mapFieldSchemaLite.getClass();
                    computeGroupSize =
                            MapFieldSchemaLite.getSerializedSize(
                                    i22, object8, mapFieldDefaultEntry2);
                    i19 += computeGroupSize;
                    i3 = 1;
                    c = '?';
                    break;
                case 51:
                    if (isOneofPresent(i22, i18, obj)) {
                        c2 = '\b';
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 8, i19);
                        i3 = 1;
                        c = '?';
                        break;
                    }
                    i3 = 1;
                    c = '?';
                case 52:
                    if (isOneofPresent(i22, i18, obj)) {
                        c3 = 4;
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 4, i19);
                        i3 = 1;
                        c = '?';
                        break;
                    }
                    i3 = 1;
                    c = '?';
                case 53:
                    if (isOneofPresent(i22, i18, obj)) {
                        long oneofLongAt4 = oneofLongAt(j5, obj);
                        computeTagSize4 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeUInt64SizeNoTag2 =
                                CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(oneofLongAt4);
                        computeTagSize3 = computeUInt64SizeNoTag2 + computeTagSize4;
                        i19 += computeTagSize3;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 54:
                    if (isOneofPresent(i22, i18, obj)) {
                        long oneofLongAt5 = oneofLongAt(j5, obj);
                        computeTagSize4 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeUInt64SizeNoTag2 =
                                CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(oneofLongAt5);
                        computeTagSize3 = computeUInt64SizeNoTag2 + computeTagSize4;
                        i19 += computeTagSize3;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 55:
                    if (isOneofPresent(i22, i18, obj)) {
                        int oneofIntAt5 = oneofIntAt(j5, obj);
                        computeTagSize5 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeInt32SizeNoTag =
                                CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(oneofIntAt5);
                        computeGroupSize = computeInt32SizeNoTag + computeTagSize5;
                        i19 += computeGroupSize;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 56:
                    if (isOneofPresent(i22, i18, obj)) {
                        computeGroupSize = CodedOutputStream$ArrayEncoder.computeFixed64Size(i22);
                        i19 += computeGroupSize;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 57:
                    if (isOneofPresent(i22, i18, obj)) {
                        computeGroupSize = CodedOutputStream$ArrayEncoder.computeFixed32Size(i22);
                        i19 += computeGroupSize;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 58:
                    if (isOneofPresent(i22, i18, obj)) {
                        i3 = 1;
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 1, i19);
                        c = '?';
                        break;
                    }
                    i3 = 1;
                    c = '?';
                case 59:
                    if (isOneofPresent(i22, i18, obj)) {
                        Object object9 = unsafe2.getObject(obj, j5);
                        i19 =
                                (object9 instanceof ByteString
                                                ? CodedOutputStream$ArrayEncoder.computeBytesSize(
                                                        i22, (ByteString) object9)
                                                : CodedOutputStream$ArrayEncoder
                                                                .computeStringSizeNoTag(
                                                                        (String) object9)
                                                        + CodedOutputStream$ArrayEncoder
                                                                .computeTagSize(i22))
                                        + i19;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 60:
                    if (isOneofPresent(i22, i18, obj)) {
                        Object object10 = unsafe2.getObject(obj, j5);
                        Schema messageFieldSchema6 = getMessageFieldSchema(i18);
                        Class cls10 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int computeTagSize13 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        int serializedSize4 =
                                ((AbstractMessageLite) ((MessageLite) object10))
                                        .getSerializedSize(messageFieldSchema6);
                        i19 =
                                MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                        serializedSize4, serializedSize4, computeTagSize13, i19);
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 61:
                    if (isOneofPresent(i22, i18, obj)) {
                        computeGroupSize =
                                CodedOutputStream$ArrayEncoder.computeBytesSize(
                                        i22, (ByteString) unsafe2.getObject(obj, j5));
                        i19 += computeGroupSize;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 62:
                    if (isOneofPresent(i22, i18, obj)) {
                        int oneofIntAt6 = oneofIntAt(j5, obj);
                        computeTagSize5 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeInt32SizeNoTag =
                                CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(oneofIntAt6);
                        computeGroupSize = computeInt32SizeNoTag + computeTagSize5;
                        i19 += computeGroupSize;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 63:
                    if (isOneofPresent(i22, i18, obj)) {
                        int oneofIntAt7 = oneofIntAt(j5, obj);
                        computeTagSize5 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeInt32SizeNoTag =
                                CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(oneofIntAt7);
                        computeGroupSize = computeInt32SizeNoTag + computeTagSize5;
                        i19 += computeGroupSize;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 64:
                    if (isOneofPresent(i22, i18, obj)) {
                        c3 = 4;
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 4, i19);
                        i3 = 1;
                        c = '?';
                        break;
                    }
                    i3 = 1;
                    c = '?';
                case 65:
                    if (isOneofPresent(i22, i18, obj)) {
                        c2 = '\b';
                        i19 = MessageSchema$$ExternalSyntheticOutline0.m(i22, 8, i19);
                        i3 = 1;
                        c = '?';
                        break;
                    }
                    i3 = 1;
                    c = '?';
                case 66:
                    if (isOneofPresent(i22, i18, obj)) {
                        int oneofIntAt8 = oneofIntAt(j5, obj);
                        computeTagSize5 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeInt32SizeNoTag =
                                CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(
                                        (oneofIntAt8 >> 31) ^ (oneofIntAt8 << 1));
                        computeGroupSize = computeInt32SizeNoTag + computeTagSize5;
                        i19 += computeGroupSize;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 67:
                    if (isOneofPresent(i22, i18, obj)) {
                        long oneofLongAt6 = oneofLongAt(j5, obj);
                        computeTagSize4 = CodedOutputStream$ArrayEncoder.computeTagSize(i22);
                        computeUInt64SizeNoTag2 =
                                CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                                        (oneofLongAt6 >> 63) ^ (oneofLongAt6 << 1));
                        computeTagSize3 = computeUInt64SizeNoTag2 + computeTagSize4;
                        i19 += computeTagSize3;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                case 68:
                    if (isOneofPresent(i22, i18, obj)) {
                        computeGroupSize =
                                CodedOutputStream$ArrayEncoder.computeGroupSize(
                                        i22,
                                        (MessageLite) unsafe2.getObject(obj, j5),
                                        getMessageFieldSchema(i18));
                        i19 += computeGroupSize;
                    }
                    i3 = 1;
                    c = '?';
                    break;
                default:
                    i3 = 1;
                    c = '?';
                    break;
            }
            i18 += 3;
            i7 = i3;
            i20 = i26;
        }
        ((UnknownFieldSetLiteSchema) unknownFieldSchema).getClass();
        return ((GeneratedMessageLite) obj).unknownFields.getSerializedSize() + i19;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01f0, code lost:

       if (r4 != false) goto L42;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00d5, code lost:

       if (r4 != false) goto L42;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00d7, code lost:

       r8 = 1231;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00d8, code lost:

       r3 = r8 + r3;
    */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int hashCode(java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 750
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.protobuf.MessageSchema.hashCode(java.lang.Object):int");
    }

    public final boolean isFieldPresent(int i, Object obj) {
        int i2 = this.buffer[i + 2];
        long j = i2 & 1048575;
        if (j != 1048575) {
            return ((1 << (i2 >>> 20)) & UnsafeUtil.getInt(j, obj)) != 0;
        }
        int typeAndOffsetAt = typeAndOffsetAt(i);
        long j2 = typeAndOffsetAt & 1048575;
        switch (type(typeAndOffsetAt)) {
            case 0:
                return Double.doubleToRawLongBits(UnsafeUtil.MEMORY_ACCESSOR.getDouble(j2, obj))
                        != 0;
            case 1:
                return Float.floatToRawIntBits(UnsafeUtil.MEMORY_ACCESSOR.getFloat(j2, obj)) != 0;
            case 2:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 3:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 4:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 5:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 6:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 7:
                return UnsafeUtil.MEMORY_ACCESSOR.getBoolean(j2, obj);
            case 8:
                Object object = UnsafeUtil.getObject(j2, obj);
                if (object instanceof String) {
                    return !((String) object).isEmpty();
                }
                if (object instanceof ByteString) {
                    return !ByteString.EMPTY.equals(object);
                }
                throw new IllegalArgumentException();
            case 9:
                return UnsafeUtil.getObject(j2, obj) != null;
            case 10:
                return !ByteString.EMPTY.equals(UnsafeUtil.getObject(j2, obj));
            case 11:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 12:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 13:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 14:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 15:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 16:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 17:
                return UnsafeUtil.getObject(j2, obj) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override // com.google.protobuf.Schema
    public final boolean isInitialized(Object obj) {
        int i = 1048575;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            boolean z = true;
            if (i2 >= this.checkInitializedCount) {
                return true;
            }
            int i4 = this.intArray[i2];
            int[] iArr = this.buffer;
            int i5 = iArr[i4];
            int typeAndOffsetAt = typeAndOffsetAt(i4);
            int i6 = iArr[i4 + 2];
            int i7 = i6 & 1048575;
            int i8 = 1 << (i6 >>> 20);
            if (i7 != i) {
                if (i7 != 1048575) {
                    i3 = UNSAFE.getInt(obj, i7);
                }
                i = i7;
            }
            if ((268435456 & typeAndOffsetAt) != 0) {
                if (!(i == 1048575 ? isFieldPresent(i4, obj) : (i3 & i8) != 0)) {
                    return false;
                }
            }
            int type = type(typeAndOffsetAt);
            if (type == 9 || type == 17) {
                if (i == 1048575) {
                    z = isFieldPresent(i4, obj);
                } else if ((i8 & i3) == 0) {
                    z = false;
                }
                if (z
                        && !getMessageFieldSchema(i4)
                                .isInitialized(
                                        UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))) {
                    return false;
                }
            } else {
                if (type != 27) {
                    if (type == 60 || type == 68) {
                        if (isOneofPresent(i5, i4, obj)
                                && !getMessageFieldSchema(i4)
                                        .isInitialized(
                                                UnsafeUtil.getObject(
                                                        typeAndOffsetAt & 1048575, obj))) {
                            return false;
                        }
                    } else if (type != 49) {
                        if (type != 50) {
                            continue;
                        } else {
                            Object object = UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj);
                            this.mapFieldSchema.getClass();
                            MapFieldLite mapFieldLite = (MapFieldLite) object;
                            if (!mapFieldLite.isEmpty()
                                    && ((MapEntryLite) getMapFieldDefaultEntry(i4))
                                                    .metadata.valueType.getJavaType()
                                            == WireFormat$JavaType.MESSAGE) {
                                Schema schema = null;
                                for (Object obj2 : mapFieldLite.values()) {
                                    if (schema == null) {
                                        schema = Protobuf.INSTANCE.schemaFor(obj2.getClass());
                                    }
                                    if (!schema.isInitialized(obj2)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
                List list = (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj);
                if (list.isEmpty()) {
                    continue;
                } else {
                    Schema messageFieldSchema = getMessageFieldSchema(i4);
                    for (int i9 = 0; i9 < list.size(); i9++) {
                        if (!messageFieldSchema.isInitialized(list.get(i9))) {
                            return false;
                        }
                    }
                }
            }
            i2++;
        }
    }

    public final boolean isOneofPresent(int i, int i2, Object obj) {
        return UnsafeUtil.getInt((long) (this.buffer[i2 + 2] & 1048575), obj) == i;
    }

    @Override // com.google.protobuf.Schema
    public final void makeImmutable(Object obj) {
        if (isMutable(obj)) {
            if (obj instanceof GeneratedMessageLite) {
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
                generatedMessageLite.setMemoizedSerializedSize(Preference.DEFAULT_ORDER);
                generatedMessageLite.memoizedHashCode = 0;
                generatedMessageLite.markImmutable();
            }
            int length = this.buffer.length;
            for (int i = 0; i < length; i += 3) {
                int typeAndOffsetAt = typeAndOffsetAt(i);
                long j = 1048575 & typeAndOffsetAt;
                int type = type(typeAndOffsetAt);
                if (type != 9) {
                    switch (type) {
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                        case 45:
                        case 46:
                        case 47:
                        case 48:
                        case 49:
                            this.listFieldSchema.makeImmutableListAt(j, obj);
                            break;
                        case 50:
                            Unsafe unsafe = UNSAFE;
                            Object object = unsafe.getObject(obj, j);
                            if (object != null) {
                                this.mapFieldSchema.getClass();
                                ((MapFieldLite) object).makeImmutable();
                                unsafe.putObject(obj, j, object);
                                break;
                            } else {
                                break;
                            }
                    }
                }
                if (isFieldPresent(i, obj)) {
                    getMessageFieldSchema(i).makeImmutable(UNSAFE.getObject(obj, j));
                }
            }
            ((UnknownFieldSetLiteSchema) this.unknownFieldSchema).getClass();
            ((GeneratedMessageLite) obj).unknownFields.isMutable = false;
        }
    }

    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, Object obj2) {
        checkMutable(obj);
        obj2.getClass();
        int i = 0;
        while (true) {
            int[] iArr = this.buffer;
            if (i >= iArr.length) {
                SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, obj, obj2);
                return;
            }
            int typeAndOffsetAt = typeAndOffsetAt(i);
            long j = typeAndOffsetAt & 1048575;
            int i2 = iArr[i];
            switch (type(typeAndOffsetAt)) {
                case 0:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.Android32MemoryAccessor android32MemoryAccessor =
                                UnsafeUtil.MEMORY_ACCESSOR;
                        android32MemoryAccessor.putDouble(
                                obj, j, android32MemoryAccessor.getDouble(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 1:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.Android32MemoryAccessor android32MemoryAccessor2 =
                                UnsafeUtil.MEMORY_ACCESSOR;
                        android32MemoryAccessor2.putFloat(
                                obj, j, android32MemoryAccessor2.getFloat(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 2:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.MEMORY_ACCESSOR.unsafe.putLong(
                                obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 3:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.MEMORY_ACCESSOR.unsafe.putLong(
                                obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 4:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(obj, j, UnsafeUtil.getInt(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 5:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.MEMORY_ACCESSOR.unsafe.putLong(
                                obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 6:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(obj, j, UnsafeUtil.getInt(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 7:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.Android32MemoryAccessor android32MemoryAccessor3 =
                                UnsafeUtil.MEMORY_ACCESSOR;
                        android32MemoryAccessor3.putBoolean(
                                obj, j, android32MemoryAccessor3.getBoolean(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 8:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 9:
                    mergeMessage(i, obj, obj2);
                    break;
                case 10:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 11:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(obj, j, UnsafeUtil.getInt(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 12:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(obj, j, UnsafeUtil.getInt(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 13:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(obj, j, UnsafeUtil.getInt(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 14:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.MEMORY_ACCESSOR.unsafe.putLong(
                                obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 15:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(obj, j, UnsafeUtil.getInt(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 16:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.MEMORY_ACCESSOR.unsafe.putLong(
                                obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 17:
                    mergeMessage(i, obj, obj2);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.listFieldSchema.mergeListsAt(j, obj, obj2);
                    break;
                case 50:
                    Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    Object object = UnsafeUtil.getObject(j, obj);
                    Object object2 = UnsafeUtil.getObject(j, obj2);
                    this.mapFieldSchema.getClass();
                    UnsafeUtil.putObject(j, obj, MapFieldSchemaLite.mergeFrom(object, object2));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (!isOneofPresent(i2, i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        UnsafeUtil.putInt(obj, iArr[i + 2] & 1048575, i2);
                        break;
                    }
                case 60:
                    mergeOneofMessage(i, obj, obj2);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (!isOneofPresent(i2, i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        UnsafeUtil.putInt(obj, iArr[i + 2] & 1048575, i2);
                        break;
                    }
                case 68:
                    mergeOneofMessage(i, obj, obj2);
                    break;
            }
            i += 3;
        }
    }

    public final void mergeMessage(int i, Object obj, Object obj2) {
        if (isFieldPresent(i, obj2)) {
            long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(obj2, typeAndOffsetAt);
            if (object == null) {
                throw new IllegalStateException(
                        "Source subfield " + this.buffer[i] + " is present but null: " + obj2);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i);
            if (!isFieldPresent(i, obj)) {
                if (isMutable(object)) {
                    GeneratedMessageLite newInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(newInstance, object);
                    unsafe.putObject(obj, typeAndOffsetAt, newInstance);
                } else {
                    unsafe.putObject(obj, typeAndOffsetAt, object);
                }
                setFieldPresent(i, obj);
                return;
            }
            Object object2 = unsafe.getObject(obj, typeAndOffsetAt);
            if (!isMutable(object2)) {
                GeneratedMessageLite newInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(newInstance2, object2);
                unsafe.putObject(obj, typeAndOffsetAt, newInstance2);
                object2 = newInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    public final void mergeOneofMessage(int i, Object obj, Object obj2) {
        int[] iArr = this.buffer;
        int i2 = iArr[i];
        if (isOneofPresent(i2, i, obj2)) {
            long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(obj2, typeAndOffsetAt);
            if (object == null) {
                throw new IllegalStateException(
                        "Source subfield " + iArr[i] + " is present but null: " + obj2);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i);
            if (!isOneofPresent(i2, i, obj)) {
                if (isMutable(object)) {
                    GeneratedMessageLite newInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(newInstance, object);
                    unsafe.putObject(obj, typeAndOffsetAt, newInstance);
                } else {
                    unsafe.putObject(obj, typeAndOffsetAt, object);
                }
                UnsafeUtil.putInt(obj, iArr[i + 2] & 1048575, i2);
                return;
            }
            Object object2 = unsafe.getObject(obj, typeAndOffsetAt);
            if (!isMutable(object2)) {
                GeneratedMessageLite newInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(newInstance2, object2);
                unsafe.putObject(obj, typeAndOffsetAt, newInstance2);
                object2 = newInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    public final Object mutableMessageFieldForMerge(int i, Object obj) {
        Schema messageFieldSchema = getMessageFieldSchema(i);
        long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
        if (!isFieldPresent(i, obj)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(obj, typeAndOffsetAt);
        if (isMutable(object)) {
            return object;
        }
        GeneratedMessageLite newInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(newInstance, object);
        }
        return newInstance;
    }

    public final Object mutableOneofMessageFieldForMerge(int i, int i2, Object obj) {
        Schema messageFieldSchema = getMessageFieldSchema(i2);
        if (!isOneofPresent(i, i2, obj)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(obj, typeAndOffsetAt(i2) & 1048575);
        if (isMutable(object)) {
            return object;
        }
        GeneratedMessageLite newInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(newInstance, object);
        }
        return newInstance;
    }

    @Override // com.google.protobuf.Schema
    public final GeneratedMessageLite newInstance() {
        this.newInstanceSchema.getClass();
        return (GeneratedMessageLite)
                ((GeneratedMessageLite) this.defaultInstance).dynamicMethod$1();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [int] */
    public final int parseMapField(
            Object obj,
            byte[] bArr,
            int i,
            int i2,
            int i3,
            long j,
            ArrayDecoders.Registers registers) {
        Unsafe unsafe = UNSAFE;
        Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i3);
        Object object = unsafe.getObject(obj, j);
        this.mapFieldSchema.getClass();
        if (!((MapFieldLite) object).isMutable()) {
            MapFieldLite mutableCopy = MapFieldLite.EMPTY_MAP_FIELD.mutableCopy();
            MapFieldSchemaLite.mergeFrom(mutableCopy, object);
            unsafe.putObject(obj, j, mutableCopy);
            object = mutableCopy;
        }
        MapEntryLite.Metadata metadata = ((MapEntryLite) mapFieldDefaultEntry).metadata;
        MapFieldLite mapFieldLite = (MapFieldLite) object;
        int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
        int i4 = registers.int1;
        if (i4 < 0 || i4 > i2 - decodeVarint32) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int i5 = decodeVarint32 + i4;
        Object obj2 = metadata.defaultKey;
        Object obj3 = metadata.defaultValue;
        Object obj4 = obj2;
        Object obj5 = obj3;
        while (decodeVarint32 < i5) {
            int i6 = decodeVarint32 + 1;
            byte b = bArr[decodeVarint32];
            if (b < 0) {
                i6 = ArrayDecoders.decodeVarint32(b, bArr, i6, registers);
                b = registers.int1;
            }
            int i7 = b >>> 3;
            int i8 = b & 7;
            if (i7 != 1) {
                if (i7 == 2 && i8 == metadata.valueType.getWireType()) {
                    decodeVarint32 =
                            decodeMapEntryValue(
                                    bArr, i6, i2, metadata.valueType, obj3.getClass(), registers);
                    obj5 = registers.object1;
                }
                decodeVarint32 = ArrayDecoders.skipField(b, bArr, i6, i2, registers);
            } else if (i8 == metadata.keyType.getWireType()) {
                decodeVarint32 =
                        decodeMapEntryValue(bArr, i6, i2, metadata.keyType, null, registers);
                obj4 = registers.object1;
            } else {
                decodeVarint32 = ArrayDecoders.skipField(b, bArr, i6, i2, registers);
            }
        }
        if (decodeVarint32 != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        mapFieldLite.put(obj4, obj5);
        return i5;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int parseOneofField(
            Object obj,
            byte[] bArr,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            int i7,
            long j,
            int i8,
            ArrayDecoders.Registers registers) {
        int mergeMessageField;
        Unsafe unsafe = UNSAFE;
        int[] iArr = this.buffer;
        long j2 = iArr[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(
                            obj,
                            j,
                            Double.valueOf(
                                    Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i, bArr))));
                    int i9 = i + 8;
                    unsafe.putInt(obj, j2, i4);
                    return i9;
                }
                return i;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(
                            obj,
                            j,
                            Float.valueOf(
                                    Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i, bArr))));
                    int i10 = i + 4;
                    unsafe.putInt(obj, j2, i4);
                    return i10;
                }
                return i;
            case 53:
            case 54:
                if (i5 == 0) {
                    int decodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    unsafe.putObject(obj, j, Long.valueOf(registers.long1));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint64;
                }
                return i;
            case 55:
            case 62:
                if (i5 == 0) {
                    int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    unsafe.putObject(obj, j, Integer.valueOf(registers.int1));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint32;
                }
                return i;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(obj, j, Long.valueOf(ArrayDecoders.decodeFixed64(i, bArr)));
                    int i11 = i + 8;
                    unsafe.putInt(obj, j2, i4);
                    return i11;
                }
                return i;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(obj, j, Integer.valueOf(ArrayDecoders.decodeFixed32(i, bArr)));
                    int i12 = i + 4;
                    unsafe.putInt(obj, j2, i4);
                    return i12;
                }
                return i;
            case 58:
                if (i5 == 0) {
                    int decodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    unsafe.putObject(obj, j, Boolean.valueOf(registers.long1 != 0));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint642;
                }
                return i;
            case 59:
                if (i5 == 2) {
                    int decodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i13 = registers.int1;
                    if (i13 == 0) {
                        unsafe.putObject(obj, j, ApnSettings.MVNO_NONE);
                    } else {
                        if ((i6 & 536870912) != 0) {
                            if (!Utf8.processor.isValidUtf8(
                                    bArr, decodeVarint322, decodeVarint322 + i13)) {
                                throw InvalidProtocolBufferException.invalidUtf8();
                            }
                        }
                        unsafe.putObject(
                                obj, j, new String(bArr, decodeVarint322, i13, Internal.UTF_8));
                        decodeVarint322 += i13;
                    }
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint322;
                }
                return i;
            case 60:
                if (i5 == 2) {
                    Object mutableOneofMessageFieldForMerge =
                            mutableOneofMessageFieldForMerge(i4, i8, obj);
                    mergeMessageField =
                            ArrayDecoders.mergeMessageField(
                                    mutableOneofMessageFieldForMerge,
                                    getMessageFieldSchema(i8),
                                    bArr,
                                    i,
                                    i2,
                                    registers);
                    unsafe.putObject(
                            obj, typeAndOffsetAt(i8) & 1048575, mutableOneofMessageFieldForMerge);
                    UnsafeUtil.putInt(obj, iArr[r13] & 1048575, i4);
                    break;
                }
                return i;
            case 61:
                if (i5 == 2) {
                    int decodeBytes = ArrayDecoders.decodeBytes(bArr, i, registers);
                    unsafe.putObject(obj, j, registers.object1);
                    unsafe.putInt(obj, j2, i4);
                    return decodeBytes;
                }
                return i;
            case 63:
                if (i5 == 0) {
                    int decodeVarint323 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i14 = registers.int1;
                    Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i8);
                    if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i14)) {
                        unsafe.putObject(obj, j, Integer.valueOf(i14));
                        unsafe.putInt(obj, j2, i4);
                    } else {
                        getMutableUnknownFields(obj).storeField(i3, Long.valueOf(i14));
                    }
                    return decodeVarint323;
                }
                return i;
            case 66:
                if (i5 == 0) {
                    int decodeVarint324 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    unsafe.putObject(
                            obj,
                            j,
                            Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint324;
                }
                return i;
            case 67:
                if (i5 == 0) {
                    int decodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    unsafe.putObject(
                            obj, j, Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint643;
                }
                return i;
            case 68:
                if (i5 == 3) {
                    Object mutableOneofMessageFieldForMerge2 =
                            mutableOneofMessageFieldForMerge(i4, i8, obj);
                    mergeMessageField =
                            ((MessageSchema) getMessageFieldSchema(i8))
                                    .parseProto2Message(
                                            mutableOneofMessageFieldForMerge2,
                                            bArr,
                                            i,
                                            i2,
                                            (i3 & (-8)) | 4,
                                            registers);
                    registers.object1 = mutableOneofMessageFieldForMerge2;
                    unsafe.putObject(
                            obj, typeAndOffsetAt(i8) & 1048575, mutableOneofMessageFieldForMerge2);
                    UnsafeUtil.putInt(obj, iArr[r13] & 1048575, i4);
                    break;
                }
                return i;
            default:
                return i;
        }
        return mergeMessageField;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:133:0x00b4. Please report as an issue. */
    public final int parseProto2Message(
            Object obj, byte[] bArr, int i, int i2, int i3, ArrayDecoders.Registers registers) {
        int[] iArr;
        Unsafe unsafe;
        int i4;
        char c;
        Object obj2;
        Internal.EnumVerifier enumFieldVerifier;
        char c2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        char c3;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int decodeVarint64;
        int i22;
        int i23;
        int i24;
        MessageSchema messageSchema = this;
        Object obj3 = obj;
        byte[] bArr2 = bArr;
        int i25 = i2;
        int i26 = i3;
        ArrayDecoders.Registers registers2 = registers;
        checkMutable(obj);
        Unsafe unsafe2 = UNSAFE;
        int i27 = i;
        int i28 = -1;
        int i29 = 0;
        int i30 = 0;
        int i31 = 0;
        int i32 = 1048575;
        while (true) {
            int[] iArr2 = messageSchema.buffer;
            if (i27 < i25) {
                int i33 = i27 + 1;
                byte b = bArr2[i27];
                if (b < 0) {
                    i6 = ArrayDecoders.decodeVarint32(b, bArr2, i33, registers2);
                    i5 = registers2.int1;
                } else {
                    i5 = b;
                    i6 = i33;
                }
                int i34 = i5 >>> 3;
                int i35 = i5 & 7;
                int i36 = i6;
                int i37 = messageSchema.maxFieldNumber;
                int i38 = i5;
                int i39 = messageSchema.minFieldNumber;
                if (i34 > i28) {
                    i9 =
                            (i34 < i39 || i34 > i37)
                                    ? -1
                                    : messageSchema.slowPositionForFieldNumber(i34, i29 / 3);
                    i10 = -1;
                    i7 = 0;
                } else {
                    if (i34 < i39 || i34 > i37) {
                        i7 = 0;
                        i8 = -1;
                    } else {
                        i7 = 0;
                        i8 = messageSchema.slowPositionForFieldNumber(i34, 0);
                    }
                    i9 = i8;
                    i10 = -1;
                }
                if (i9 == i10) {
                    i11 = i7;
                    i12 = i31;
                    i13 = i32;
                    i14 = i34;
                    unsafe = unsafe2;
                    i4 = i26;
                    i15 = i36;
                    c = 2;
                    iArr = iArr2;
                    i16 = i38;
                } else {
                    int i40 = iArr2[i9 + 1];
                    int type = type(i40);
                    long j = i40 & 1048575;
                    if (type <= 17) {
                        int i41 = iArr2[i9 + 2];
                        int i42 = 1 << (i41 >>> 20);
                        int i43 = i41 & 1048575;
                        if (i43 != i32) {
                            if (i32 != 1048575) {
                                unsafe2.putInt(obj3, i32, i31);
                            }
                            i12 = unsafe2.getInt(obj3, i43);
                            i13 = i43;
                        } else {
                            i12 = i31;
                            i13 = i32;
                        }
                        switch (type) {
                            case 0:
                                i20 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 1) {
                                    i19 = i20;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    i21 = i20;
                                    UnsafeUtil.MEMORY_ACCESSOR.putDouble(
                                            obj,
                                            j,
                                            Double.longBitsToDouble(
                                                    ArrayDecoders.decodeFixed64(i17, bArr2)));
                                    i27 = i17 + 8;
                                    i31 = i12 | i42;
                                    i26 = i3;
                                    i28 = i34;
                                    i29 = i21;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                }
                            case 1:
                                i20 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 5) {
                                    i19 = i20;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putFloat(
                                            obj3,
                                            j,
                                            Float.intBitsToFloat(
                                                    ArrayDecoders.decodeFixed32(i17, bArr2)));
                                    i27 = i17 + 4;
                                    i26 = i3;
                                    i29 = i20;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                    i31 = i12 | i42;
                                    i28 = i34;
                                }
                            case 2:
                            case 3:
                                i20 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 0) {
                                    i19 = i20;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    decodeVarint64 =
                                            ArrayDecoders.decodeVarint64(bArr2, i17, registers2);
                                    i21 = i20;
                                    unsafe2.putLong(obj, j, registers2.long1);
                                    i31 = i12 | i42;
                                    i26 = i3;
                                    i27 = decodeVarint64;
                                    i28 = i34;
                                    i29 = i21;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                }
                            case 4:
                            case 11:
                                i20 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 0) {
                                    i19 = i20;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    i27 = ArrayDecoders.decodeVarint32(bArr2, i17, registers2);
                                    unsafe2.putInt(obj3, j, registers2.int1);
                                    i26 = i3;
                                    i29 = i20;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                    i31 = i12 | i42;
                                    i28 = i34;
                                }
                            case 5:
                            case 14:
                                i20 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 1) {
                                    i19 = i20;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    i21 = i20;
                                    unsafe2.putLong(
                                            obj, j, ArrayDecoders.decodeFixed64(i17, bArr2));
                                    i27 = i17 + 8;
                                    i31 = i12 | i42;
                                    i26 = i3;
                                    i28 = i34;
                                    i29 = i21;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                }
                            case 6:
                            case 13:
                                i20 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 5) {
                                    i19 = i20;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    unsafe2.putInt(
                                            obj3, j, ArrayDecoders.decodeFixed32(i17, bArr2));
                                    i27 = i17 + 4;
                                    i26 = i3;
                                    i29 = i20;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                    i31 = i12 | i42;
                                    i28 = i34;
                                }
                            case 7:
                                i20 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 0) {
                                    i19 = i20;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    i27 = ArrayDecoders.decodeVarint64(bArr2, i17, registers2);
                                    UnsafeUtil.MEMORY_ACCESSOR.putBoolean(
                                            obj3, j, registers2.long1 != 0);
                                    i26 = i3;
                                    i29 = i20;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                    i31 = i12 | i42;
                                    i28 = i34;
                                }
                            case 8:
                                i20 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 2) {
                                    i19 = i20;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    i27 =
                                            (i40 & 536870912) == 0
                                                    ? ArrayDecoders.decodeString(
                                                            bArr2, i17, registers2)
                                                    : ArrayDecoders.decodeStringRequireUtf8(
                                                            bArr2, i17, registers2);
                                    unsafe2.putObject(obj3, j, registers2.object1);
                                    i26 = i3;
                                    i29 = i20;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                    i31 = i12 | i42;
                                    i28 = i34;
                                }
                            case 9:
                                i22 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 2) {
                                    i19 = i22;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    Object mutableMessageFieldForMerge =
                                            messageSchema.mutableMessageFieldForMerge(i22, obj3);
                                    i21 = i22;
                                    i27 =
                                            ArrayDecoders.mergeMessageField(
                                                    mutableMessageFieldForMerge,
                                                    messageSchema.getMessageFieldSchema(i22),
                                                    bArr,
                                                    i17,
                                                    i2,
                                                    registers);
                                    messageSchema.storeMessageField(
                                            i21, obj3, mutableMessageFieldForMerge);
                                    i31 = i12 | i42;
                                    i26 = i3;
                                    i28 = i34;
                                    i29 = i21;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                }
                            case 10:
                                i22 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 2) {
                                    i19 = i22;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    i27 = ArrayDecoders.decodeBytes(bArr2, i17, registers2);
                                    unsafe2.putObject(obj3, j, registers2.object1);
                                    i31 = i12 | i42;
                                    i26 = i3;
                                    i29 = i22;
                                    i28 = i34;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                }
                            case 12:
                                i22 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 0) {
                                    i19 = i22;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    i27 = ArrayDecoders.decodeVarint32(bArr2, i17, registers2);
                                    int i44 = registers2.int1;
                                    Internal.EnumVerifier enumFieldVerifier2 =
                                            messageSchema.getEnumFieldVerifier(i22);
                                    if (enumFieldVerifier2 == null
                                            || enumFieldVerifier2.isInRange(i44)) {
                                        unsafe2.putInt(obj3, j, i44);
                                        i31 = i12 | i42;
                                        i26 = i3;
                                        i29 = i22;
                                        i28 = i34;
                                        i30 = i18;
                                        i32 = i13;
                                        i25 = i2;
                                    } else {
                                        getMutableUnknownFields(obj)
                                                .storeField(i18, Long.valueOf(i44));
                                        i26 = i3;
                                        i29 = i22;
                                        i28 = i34;
                                        i30 = i18;
                                        i31 = i12;
                                        i32 = i13;
                                        i25 = i2;
                                    }
                                }
                                break;
                            case 15:
                                i22 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 0) {
                                    i19 = i22;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    i27 = ArrayDecoders.decodeVarint32(bArr2, i17, registers2);
                                    unsafe2.putInt(
                                            obj3,
                                            j,
                                            CodedInputStream.decodeZigZag32(registers2.int1));
                                    i31 = i12 | i42;
                                    i26 = i3;
                                    i29 = i22;
                                    i28 = i34;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                }
                            case 16:
                                i20 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                if (i35 != 0) {
                                    i19 = i20;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    decodeVarint64 =
                                            ArrayDecoders.decodeVarint64(bArr2, i17, registers2);
                                    i21 = i20;
                                    unsafe2.putLong(
                                            obj,
                                            j,
                                            CodedInputStream.decodeZigZag64(registers2.long1));
                                    i31 = i12 | i42;
                                    i26 = i3;
                                    i27 = decodeVarint64;
                                    i28 = i34;
                                    i29 = i21;
                                    i30 = i18;
                                    i32 = i13;
                                    i25 = i2;
                                }
                            case 17:
                                c3 = 3;
                                if (i35 != 3) {
                                    i17 = i36;
                                    i18 = i38;
                                    i19 = i9;
                                    i4 = i3;
                                    iArr = iArr2;
                                    i14 = i34;
                                    i11 = i19;
                                    unsafe = unsafe2;
                                    i15 = i17;
                                    i16 = i18;
                                    c = 2;
                                    break;
                                } else {
                                    Object mutableMessageFieldForMerge2 =
                                            messageSchema.mutableMessageFieldForMerge(i9, obj3);
                                    int i45 = i9;
                                    i27 =
                                            ((MessageSchema)
                                                            messageSchema.getMessageFieldSchema(i9))
                                                    .parseProto2Message(
                                                            mutableMessageFieldForMerge2,
                                                            bArr,
                                                            i36,
                                                            i2,
                                                            (i34 << 3) | 4,
                                                            registers);
                                    registers2.object1 = mutableMessageFieldForMerge2;
                                    messageSchema.storeMessageField(
                                            i45, obj3, mutableMessageFieldForMerge2);
                                    i31 = i12 | i42;
                                    i28 = i34;
                                    i29 = i45;
                                    i30 = i38;
                                    i32 = i13;
                                    i25 = i2;
                                    i26 = i3;
                                }
                            default:
                                i19 = i9;
                                i17 = i36;
                                i18 = i38;
                                c3 = 3;
                                i4 = i3;
                                iArr = iArr2;
                                i14 = i34;
                                i11 = i19;
                                unsafe = unsafe2;
                                i15 = i17;
                                i16 = i18;
                                c = 2;
                                break;
                        }
                    } else {
                        i14 = i34;
                        iArr = iArr2;
                        int i46 = i9;
                        if (type != 27) {
                            i11 = i46;
                            i12 = i31;
                            i13 = i32;
                            if (type <= 49) {
                                unsafe = unsafe2;
                                i24 = i38;
                                i27 =
                                        parseRepeatedField(
                                                obj, bArr, i36, i2, i38, i14, i35, i11, i40, type,
                                                j, registers);
                                if (i27 == i36) {
                                    i16 = i24;
                                    i4 = i3;
                                    i15 = i27;
                                    c = 2;
                                }
                            } else {
                                i23 = i36;
                                unsafe = unsafe2;
                                i24 = i38;
                                if (type == 50) {
                                    c = 2;
                                    if (i35 == 2) {
                                        i27 = parseMapField(obj, bArr, i23, i2, i11, j, registers);
                                        if (i27 == i23) {
                                            i16 = i24;
                                            i4 = i3;
                                            i15 = i27;
                                        }
                                    }
                                } else {
                                    c = 2;
                                    i27 =
                                            parseOneofField(
                                                    obj, bArr, i23, i2, i24, i14, i35, i40, type, j,
                                                    i11, registers);
                                    if (i27 == i23) {
                                        i16 = i24;
                                        i4 = i3;
                                        i15 = i27;
                                    }
                                }
                            }
                            messageSchema = this;
                            obj3 = obj;
                            bArr2 = bArr;
                            i30 = i24;
                            i25 = i2;
                            i26 = i3;
                            registers2 = registers;
                            i28 = i14;
                            i29 = i11;
                            i31 = i12;
                            i32 = i13;
                            unsafe2 = unsafe;
                        } else if (i35 == 2) {
                            Internal.ProtobufList protobufList =
                                    (Internal.ProtobufList) unsafe2.getObject(obj3, j);
                            if (!((AbstractProtobufList) protobufList).isMutable) {
                                int size = protobufList.size();
                                protobufList =
                                        protobufList.mutableCopyWithCapacity(
                                                size == 0 ? 10 : size * 2);
                                unsafe2.putObject(obj3, j, protobufList);
                            }
                            i12 = i31;
                            i13 = i32;
                            i27 =
                                    ArrayDecoders.decodeMessageList(
                                            messageSchema.getMessageFieldSchema(i46),
                                            i38,
                                            bArr,
                                            i36,
                                            i2,
                                            protobufList,
                                            registers);
                            i26 = i3;
                            i30 = i38;
                            i28 = i14;
                            i29 = i46;
                            i31 = i12;
                            i32 = i13;
                            i25 = i2;
                        } else {
                            i11 = i46;
                            i12 = i31;
                            i13 = i32;
                            c = 2;
                            i23 = i36;
                            unsafe = unsafe2;
                            i24 = i38;
                        }
                        i16 = i24;
                        i15 = i23;
                        i4 = i3;
                    }
                }
                if (i16 != i4 || i4 == 0) {
                    i27 =
                            ArrayDecoders.decodeUnknownField(
                                    i16, bArr, i15, i2, getMutableUnknownFields(obj), registers);
                    obj3 = obj;
                    bArr2 = bArr;
                    i25 = i2;
                    registers2 = registers;
                    i26 = i4;
                    i30 = i16;
                    i28 = i14;
                    i29 = i11;
                    i31 = i12;
                    i32 = i13;
                    unsafe2 = unsafe;
                    messageSchema = this;
                } else {
                    i27 = i15;
                    i30 = i16;
                    i31 = i12;
                    i32 = i13;
                }
            } else {
                iArr = iArr2;
                unsafe = unsafe2;
                i4 = i26;
                c = 2;
            }
        }
        int i47 = 1048575;
        if (i32 != 1048575) {
            obj2 = obj;
            unsafe.putInt(obj2, i32, i31);
        } else {
            obj2 = obj;
        }
        UnknownFieldSetLite unknownFieldSetLite = null;
        MessageSchema messageSchema2 = this;
        int i48 = messageSchema2.checkInitializedCount;
        while (true) {
            int i49 = messageSchema2.repeatedFieldOffsetStart;
            UnknownFieldSchema unknownFieldSchema = messageSchema2.unknownFieldSchema;
            if (i48 >= i49) {
                if (unknownFieldSetLite != null) {
                    ((UnknownFieldSetLiteSchema) unknownFieldSchema).getClass();
                    ((GeneratedMessageLite) obj2).unknownFields = unknownFieldSetLite;
                }
                if (i4 == 0) {
                    if (i27 != i2) {
                        throw InvalidProtocolBufferException.parseFailure();
                    }
                } else if (i27 > i2 || i30 != i4) {
                    throw InvalidProtocolBufferException.parseFailure();
                }
                return i27;
            }
            int i50 = messageSchema2.intArray[i48];
            int i51 = iArr[i50];
            Object object = UnsafeUtil.getObject(messageSchema2.typeAndOffsetAt(i50) & i47, obj2);
            if (object != null
                    && (enumFieldVerifier = messageSchema2.getEnumFieldVerifier(i50)) != null) {
                messageSchema2.mapFieldSchema.getClass();
                MapEntryLite.Metadata metadata =
                        ((MapEntryLite) messageSchema2.getMapFieldDefaultEntry(i50)).metadata;
                Iterator it = ((MapFieldLite) object).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    if (enumFieldVerifier.isInRange(((Integer) entry.getValue()).intValue())) {
                        c2 = c;
                    } else {
                        if (unknownFieldSetLite == null) {
                            ((UnknownFieldSetLiteSchema) unknownFieldSchema).getClass();
                            GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj2;
                            UnknownFieldSetLite unknownFieldSetLite2 =
                                    generatedMessageLite.unknownFields;
                            if (unknownFieldSetLite2 == UnknownFieldSetLite.DEFAULT_INSTANCE) {
                                UnknownFieldSetLite newInstance = UnknownFieldSetLite.newInstance();
                                generatedMessageLite.unknownFields = newInstance;
                                unknownFieldSetLite = newInstance;
                            } else {
                                unknownFieldSetLite = unknownFieldSetLite2;
                            }
                        }
                        int computeSerializedSize =
                                MapEntryLite.computeSerializedSize(
                                        metadata, entry.getKey(), entry.getValue());
                        byte[] bArr3 = new byte[computeSerializedSize];
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                                new CodedOutputStream$ArrayEncoder(computeSerializedSize, bArr3);
                        try {
                            MapEntryLite.writeTo(
                                    codedOutputStream$ArrayEncoder,
                                    metadata,
                                    entry.getKey(),
                                    entry.getValue());
                            if (computeSerializedSize - codedOutputStream$ArrayEncoder.position
                                    != 0) {
                                throw new IllegalStateException(
                                        "Did not write as much data as expected.");
                            }
                            ByteString.LiteralByteString literalByteString =
                                    new ByteString.LiteralByteString(bArr3);
                            ((UnknownFieldSetLiteSchema) unknownFieldSchema).getClass();
                            c2 = 2;
                            unknownFieldSetLite.storeField((i51 << 3) | 2, literalByteString);
                            it.remove();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    c = c2;
                }
            }
            i48++;
            messageSchema2 = this;
            c = c;
            i47 = 1048575;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:192:0x0304  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:147:0x0317 -> B:140:0x02ec). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int parseRepeatedField(
            java.lang.Object r19,
            byte[] r20,
            int r21,
            int r22,
            int r23,
            int r24,
            int r25,
            int r26,
            long r27,
            int r29,
            long r30,
            com.google.protobuf.ArrayDecoders.Registers r32) {
        /*
            Method dump skipped, instructions count: 1402
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.protobuf.MessageSchema.parseRepeatedField(java.lang.Object,"
                    + " byte[], int, int, int, int, int, int, long, int, long,"
                    + " com.google.protobuf.ArrayDecoders$Registers):int");
    }

    public final void setFieldPresent(int i, Object obj) {
        int i2 = this.buffer[i + 2];
        long j = 1048575 & i2;
        if (j == 1048575) {
            return;
        }
        UnsafeUtil.putInt(obj, j, (1 << (i2 >>> 20)) | UnsafeUtil.getInt(j, obj));
    }

    public final int slowPositionForFieldNumber(int i, int i2) {
        int[] iArr = this.buffer;
        int length = (iArr.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = iArr[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    public final void storeMessageField(int i, Object obj, Object obj2) {
        UNSAFE.putObject(obj, typeAndOffsetAt(i) & 1048575, obj2);
        setFieldPresent(i, obj);
    }

    public final int typeAndOffsetAt(int i) {
        return this.buffer[i + 1];
    }

    public final void writeMapHelper(
            CodedOutputStreamWriter codedOutputStreamWriter, int i, Object obj, int i2) {
        if (obj != null) {
            Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i2);
            MapFieldSchemaLite mapFieldSchemaLite = this.mapFieldSchema;
            mapFieldSchemaLite.getClass();
            MapEntryLite.Metadata metadata = ((MapEntryLite) mapFieldDefaultEntry).metadata;
            mapFieldSchemaLite.getClass();
            CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                    codedOutputStreamWriter.output;
            codedOutputStream$ArrayEncoder.getClass();
            for (Map.Entry entry : ((MapFieldLite) obj).entrySet()) {
                codedOutputStream$ArrayEncoder.writeTag(i, 2);
                codedOutputStream$ArrayEncoder.writeUInt32NoTag(
                        MapEntryLite.computeSerializedSize(
                                metadata, entry.getKey(), entry.getValue()));
                MapEntryLite.writeTo(
                        codedOutputStream$ArrayEncoder, metadata, entry.getKey(), entry.getValue());
            }
        }
    }

    @Override // com.google.protobuf.Schema
    public final void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        int i;
        int i2;
        codedOutputStreamWriter.getClass();
        if (this.proto3) {
            int[] iArr = this.buffer;
            int length = iArr.length;
            for (int i3 = 0; i3 < length; i3 += 3) {
                int typeAndOffsetAt = typeAndOffsetAt(i3);
                int i4 = iArr[i3];
                int type = type(typeAndOffsetAt);
                CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                        codedOutputStreamWriter.output;
                switch (type) {
                    case 0:
                        if (isFieldPresent(i3, obj)) {
                            double d =
                                    UnsafeUtil.MEMORY_ACCESSOR.getDouble(
                                            typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.getClass();
                            codedOutputStream$ArrayEncoder.writeFixed64(
                                    i4, Double.doubleToRawLongBits(d));
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (isFieldPresent(i3, obj)) {
                            float f =
                                    UnsafeUtil.MEMORY_ACCESSOR.getFloat(
                                            typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.getClass();
                            codedOutputStream$ArrayEncoder.writeFixed32(
                                    i4, Float.floatToRawIntBits(f));
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (isFieldPresent(i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeUInt64(
                                    i4, UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (isFieldPresent(i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeUInt64(
                                    i4, UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (isFieldPresent(i3, obj)) {
                            int i5 = UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.writeInt32NoTag(i5);
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (isFieldPresent(i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeFixed64(
                                    i4, UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (isFieldPresent(i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeFixed32(
                                    i4, UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (isFieldPresent(i3, obj)) {
                            boolean z =
                                    UnsafeUtil.MEMORY_ACCESSOR.getBoolean(
                                            typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.write(z ? (byte) 1 : (byte) 0);
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (isFieldPresent(i3, obj)) {
                            writeString(
                                    i4,
                                    UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                    codedOutputStreamWriter);
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        if (isFieldPresent(i3, obj)) {
                            codedOutputStreamWriter.writeMessage(
                                    i4,
                                    UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                    getMessageFieldSchema(i3));
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (isFieldPresent(i3, obj)) {
                            codedOutputStreamWriter.writeBytes(
                                    i4,
                                    (ByteString)
                                            UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (isFieldPresent(i3, obj)) {
                            int i6 = UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.writeUInt32NoTag(i6);
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (isFieldPresent(i3, obj)) {
                            int i7 = UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.writeInt32NoTag(i7);
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (isFieldPresent(i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeFixed32(
                                    i4, UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (isFieldPresent(i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeFixed64(
                                    i4, UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (isFieldPresent(i3, obj)) {
                            int i8 = UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.writeUInt32NoTag((i8 >> 31) ^ (i8 << 1));
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (isFieldPresent(i3, obj)) {
                            long j = UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeUInt64(i4, (j >> 63) ^ (j << 1));
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (isFieldPresent(i3, obj)) {
                            codedOutputStreamWriter.writeGroup(
                                    i4,
                                    UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                    getMessageFieldSchema(i3));
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        SchemaUtil.writeDoubleList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 19:
                        SchemaUtil.writeFloatList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 20:
                        SchemaUtil.writeInt64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 21:
                        SchemaUtil.writeUInt64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 22:
                        SchemaUtil.writeInt32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 23:
                        SchemaUtil.writeFixed64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 24:
                        SchemaUtil.writeFixed32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 25:
                        SchemaUtil.writeBoolList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 26:
                        SchemaUtil.writeStringList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter);
                        break;
                    case 27:
                        SchemaUtil.writeMessageList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                getMessageFieldSchema(i3));
                        break;
                    case 28:
                        SchemaUtil.writeBytesList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter);
                        break;
                    case 29:
                        SchemaUtil.writeUInt32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 30:
                        SchemaUtil.writeEnumList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 31:
                        SchemaUtil.writeSFixed32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 32:
                        SchemaUtil.writeSFixed64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 33:
                        SchemaUtil.writeSInt32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 34:
                        SchemaUtil.writeSInt64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                false);
                        break;
                    case 35:
                        SchemaUtil.writeDoubleList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 36:
                        SchemaUtil.writeFloatList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 37:
                        SchemaUtil.writeInt64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 38:
                        SchemaUtil.writeUInt64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 39:
                        SchemaUtil.writeInt32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 40:
                        SchemaUtil.writeFixed64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 41:
                        SchemaUtil.writeFixed32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 42:
                        SchemaUtil.writeBoolList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 43:
                        SchemaUtil.writeUInt32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 44:
                        SchemaUtil.writeEnumList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 45:
                        SchemaUtil.writeSFixed32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 46:
                        SchemaUtil.writeSFixed64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 47:
                        SchemaUtil.writeSInt32List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 48:
                        SchemaUtil.writeSInt64List(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                true);
                        break;
                    case 49:
                        SchemaUtil.writeGroupList(
                                iArr[i3],
                                (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                codedOutputStreamWriter,
                                getMessageFieldSchema(i3));
                        break;
                    case 50:
                        writeMapHelper(
                                codedOutputStreamWriter,
                                i4,
                                UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                i3);
                        break;
                    case 51:
                        if (isOneofPresent(i4, i3, obj)) {
                            double doubleValue =
                                    ((Double) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))
                                            .doubleValue();
                            codedOutputStream$ArrayEncoder.getClass();
                            codedOutputStream$ArrayEncoder.writeFixed64(
                                    i4, Double.doubleToRawLongBits(doubleValue));
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (isOneofPresent(i4, i3, obj)) {
                            float floatValue =
                                    ((Float) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))
                                            .floatValue();
                            codedOutputStream$ArrayEncoder.getClass();
                            codedOutputStream$ArrayEncoder.writeFixed32(
                                    i4, Float.floatToRawIntBits(floatValue));
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (isOneofPresent(i4, i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeUInt64(
                                    i4, oneofLongAt(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (isOneofPresent(i4, i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeUInt64(
                                    i4, oneofLongAt(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (isOneofPresent(i4, i3, obj)) {
                            int oneofIntAt = oneofIntAt(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.writeInt32NoTag(oneofIntAt);
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (isOneofPresent(i4, i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeFixed64(
                                    i4, oneofLongAt(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (isOneofPresent(i4, i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeFixed32(
                                    i4, oneofIntAt(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (isOneofPresent(i4, i3, obj)) {
                            boolean booleanValue =
                                    ((Boolean) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))
                                            .booleanValue();
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.write(
                                    booleanValue ? (byte) 1 : (byte) 0);
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (isOneofPresent(i4, i3, obj)) {
                            writeString(
                                    i4,
                                    UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                    codedOutputStreamWriter);
                            break;
                        } else {
                            break;
                        }
                    case 60:
                        if (isOneofPresent(i4, i3, obj)) {
                            codedOutputStreamWriter.writeMessage(
                                    i4,
                                    UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                    getMessageFieldSchema(i3));
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (isOneofPresent(i4, i3, obj)) {
                            codedOutputStreamWriter.writeBytes(
                                    i4,
                                    (ByteString)
                                            UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (isOneofPresent(i4, i3, obj)) {
                            int oneofIntAt2 = oneofIntAt(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.writeUInt32NoTag(oneofIntAt2);
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (isOneofPresent(i4, i3, obj)) {
                            int oneofIntAt3 = oneofIntAt(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.writeInt32NoTag(oneofIntAt3);
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (isOneofPresent(i4, i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeFixed32(
                                    i4, oneofIntAt(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (isOneofPresent(i4, i3, obj)) {
                            codedOutputStream$ArrayEncoder.writeFixed64(
                                    i4, oneofLongAt(typeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 66:
                        if (isOneofPresent(i4, i3, obj)) {
                            int oneofIntAt4 = oneofIntAt(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeTag(i4, 0);
                            codedOutputStream$ArrayEncoder.writeUInt32NoTag(
                                    (oneofIntAt4 >> 31) ^ (oneofIntAt4 << 1));
                            break;
                        } else {
                            break;
                        }
                    case 67:
                        if (isOneofPresent(i4, i3, obj)) {
                            long oneofLongAt = oneofLongAt(typeAndOffsetAt & 1048575, obj);
                            codedOutputStream$ArrayEncoder.writeUInt64(
                                    i4, (oneofLongAt >> 63) ^ (oneofLongAt << 1));
                            break;
                        } else {
                            break;
                        }
                    case 68:
                        if (isOneofPresent(i4, i3, obj)) {
                            codedOutputStreamWriter.writeGroup(
                                    i4,
                                    UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj),
                                    getMessageFieldSchema(i3));
                            break;
                        } else {
                            break;
                        }
                }
            }
            ((UnknownFieldSetLiteSchema) this.unknownFieldSchema).getClass();
            ((GeneratedMessageLite) obj).unknownFields.writeTo(codedOutputStreamWriter);
            return;
        }
        int[] iArr2 = this.buffer;
        int length2 = iArr2.length;
        Unsafe unsafe = UNSAFE;
        int i9 = 1048575;
        int i10 = 1048575;
        int i11 = 0;
        int i12 = 0;
        while (i11 < length2) {
            int typeAndOffsetAt2 = typeAndOffsetAt(i11);
            int i13 = iArr2[i11];
            int type2 = type(typeAndOffsetAt2);
            if (type2 <= 17) {
                int i14 = iArr2[i11 + 2];
                int i15 = i14 & i9;
                if (i15 != i10) {
                    i12 = unsafe.getInt(obj, i15);
                    i10 = i15;
                }
                i = 1 << (i14 >>> 20);
            } else {
                i = 0;
            }
            int i16 = i11;
            long j2 = typeAndOffsetAt2 & i9;
            switch (type2) {
                case 0:
                    i2 = i16;
                    if ((i & i12) == 0) {
                        break;
                    } else {
                        double d2 = UnsafeUtil.MEMORY_ACCESSOR.getDouble(j2, obj);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder2 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder2.getClass();
                        codedOutputStream$ArrayEncoder2.writeFixed64(
                                i13, Double.doubleToRawLongBits(d2));
                        continue;
                    }
                case 1:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        float f2 = UnsafeUtil.MEMORY_ACCESSOR.getFloat(j2, obj);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder3 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder3.getClass();
                        codedOutputStream$ArrayEncoder3.writeFixed32(
                                i13, Float.floatToRawIntBits(f2));
                        break;
                    } else {
                        continue;
                    }
                case 2:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        codedOutputStreamWriter.output.writeUInt64(i13, unsafe.getLong(obj, j2));
                        break;
                    } else {
                        continue;
                    }
                case 3:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        codedOutputStreamWriter.output.writeUInt64(i13, unsafe.getLong(obj, j2));
                        break;
                    } else {
                        continue;
                    }
                case 4:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        int i17 = unsafe.getInt(obj, j2);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder4 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder4.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder4.writeInt32NoTag(i17);
                        break;
                    }
                    break;
                case 5:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        codedOutputStreamWriter.output.writeFixed64(i13, unsafe.getLong(obj, j2));
                        break;
                    }
                    break;
                case 6:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        codedOutputStreamWriter.output.writeFixed32(i13, unsafe.getInt(obj, j2));
                        break;
                    }
                    break;
                case 7:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        boolean z2 = UnsafeUtil.MEMORY_ACCESSOR.getBoolean(j2, obj);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder5 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder5.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder5.write(z2 ? (byte) 1 : (byte) 0);
                        break;
                    }
                    break;
                case 8:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        writeString(i13, unsafe.getObject(obj, j2), codedOutputStreamWriter);
                        break;
                    }
                    break;
                case 9:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        codedOutputStreamWriter.writeMessage(
                                i13, unsafe.getObject(obj, j2), getMessageFieldSchema(i2));
                        break;
                    }
                    break;
                case 10:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        codedOutputStreamWriter.writeBytes(
                                i13, (ByteString) unsafe.getObject(obj, j2));
                        break;
                    }
                    break;
                case 11:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        int i18 = unsafe.getInt(obj, j2);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder6 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder6.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder6.writeUInt32NoTag(i18);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        int i19 = unsafe.getInt(obj, j2);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder7 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder7.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder7.writeInt32NoTag(i19);
                        break;
                    }
                    break;
                case 13:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        codedOutputStreamWriter.output.writeFixed32(i13, unsafe.getInt(obj, j2));
                        break;
                    }
                    break;
                case 14:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        codedOutputStreamWriter.output.writeFixed64(i13, unsafe.getLong(obj, j2));
                        break;
                    }
                    break;
                case 15:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        int i20 = unsafe.getInt(obj, j2);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder8 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder8.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder8.writeUInt32NoTag((i20 >> 31) ^ (i20 << 1));
                        break;
                    }
                    break;
                case 16:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        long j3 = unsafe.getLong(obj, j2);
                        codedOutputStreamWriter.output.writeUInt64(i13, (j3 << 1) ^ (j3 >> 63));
                        break;
                    }
                    break;
                case 17:
                    i2 = i16;
                    if ((i & i12) != 0) {
                        codedOutputStreamWriter.writeGroup(
                                i13, unsafe.getObject(obj, j2), getMessageFieldSchema(i2));
                        break;
                    }
                    break;
                case 18:
                    i2 = i16;
                    SchemaUtil.writeDoubleList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 19:
                    i2 = i16;
                    SchemaUtil.writeFloatList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 20:
                    i2 = i16;
                    SchemaUtil.writeInt64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 21:
                    i2 = i16;
                    SchemaUtil.writeUInt64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 22:
                    i2 = i16;
                    SchemaUtil.writeInt32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 23:
                    i2 = i16;
                    SchemaUtil.writeFixed64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 24:
                    i2 = i16;
                    SchemaUtil.writeFixed32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 25:
                    i2 = i16;
                    SchemaUtil.writeBoolList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 26:
                    i2 = i16;
                    SchemaUtil.writeStringList(
                            iArr2[i2], (List) unsafe.getObject(obj, j2), codedOutputStreamWriter);
                    break;
                case 27:
                    i2 = i16;
                    SchemaUtil.writeMessageList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            getMessageFieldSchema(i2));
                    break;
                case 28:
                    i2 = i16;
                    SchemaUtil.writeBytesList(
                            iArr2[i2], (List) unsafe.getObject(obj, j2), codedOutputStreamWriter);
                    break;
                case 29:
                    i2 = i16;
                    SchemaUtil.writeUInt32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 30:
                    i2 = i16;
                    SchemaUtil.writeEnumList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 31:
                    i2 = i16;
                    SchemaUtil.writeSFixed32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 32:
                    i2 = i16;
                    SchemaUtil.writeSFixed64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 33:
                    i2 = i16;
                    SchemaUtil.writeSInt32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 34:
                    i2 = i16;
                    SchemaUtil.writeSInt64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            false);
                    continue;
                case 35:
                    i2 = i16;
                    SchemaUtil.writeDoubleList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 36:
                    i2 = i16;
                    SchemaUtil.writeFloatList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 37:
                    i2 = i16;
                    SchemaUtil.writeInt64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 38:
                    i2 = i16;
                    SchemaUtil.writeUInt64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 39:
                    i2 = i16;
                    SchemaUtil.writeInt32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 40:
                    i2 = i16;
                    SchemaUtil.writeFixed64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 41:
                    i2 = i16;
                    SchemaUtil.writeFixed32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 42:
                    i2 = i16;
                    SchemaUtil.writeBoolList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 43:
                    i2 = i16;
                    SchemaUtil.writeUInt32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 44:
                    i2 = i16;
                    SchemaUtil.writeEnumList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 45:
                    i2 = i16;
                    SchemaUtil.writeSFixed32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 46:
                    i2 = i16;
                    SchemaUtil.writeSFixed64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 47:
                    i2 = i16;
                    SchemaUtil.writeSInt32List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 48:
                    i2 = i16;
                    SchemaUtil.writeSInt64List(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            true);
                    break;
                case 49:
                    i2 = i16;
                    SchemaUtil.writeGroupList(
                            iArr2[i2],
                            (List) unsafe.getObject(obj, j2),
                            codedOutputStreamWriter,
                            getMessageFieldSchema(i2));
                    break;
                case 50:
                    i2 = i16;
                    writeMapHelper(codedOutputStreamWriter, i13, unsafe.getObject(obj, j2), i2);
                    break;
                case 51:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        double doubleValue2 =
                                ((Double) UnsafeUtil.getObject(j2, obj)).doubleValue();
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder9 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder9.getClass();
                        codedOutputStream$ArrayEncoder9.writeFixed64(
                                i13, Double.doubleToRawLongBits(doubleValue2));
                        break;
                    }
                    break;
                case 52:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        float floatValue2 = ((Float) UnsafeUtil.getObject(j2, obj)).floatValue();
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder10 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder10.getClass();
                        codedOutputStream$ArrayEncoder10.writeFixed32(
                                i13, Float.floatToRawIntBits(floatValue2));
                        break;
                    }
                    break;
                case 53:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        codedOutputStreamWriter.output.writeUInt64(i13, oneofLongAt(j2, obj));
                        break;
                    }
                    break;
                case 54:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        codedOutputStreamWriter.output.writeUInt64(i13, oneofLongAt(j2, obj));
                        break;
                    }
                    break;
                case 55:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        int oneofIntAt5 = oneofIntAt(j2, obj);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder11 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder11.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder11.writeInt32NoTag(oneofIntAt5);
                        break;
                    }
                    break;
                case 56:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        codedOutputStreamWriter.output.writeFixed64(i13, oneofLongAt(j2, obj));
                        break;
                    }
                    break;
                case 57:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        codedOutputStreamWriter.output.writeFixed32(i13, oneofIntAt(j2, obj));
                        break;
                    }
                    break;
                case 58:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        boolean booleanValue2 =
                                ((Boolean) UnsafeUtil.getObject(j2, obj)).booleanValue();
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder12 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder12.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder12.write(booleanValue2 ? (byte) 1 : (byte) 0);
                        break;
                    }
                    break;
                case 59:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        writeString(i13, unsafe.getObject(obj, j2), codedOutputStreamWriter);
                        break;
                    }
                    break;
                case 60:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        codedOutputStreamWriter.writeMessage(
                                i13, unsafe.getObject(obj, j2), getMessageFieldSchema(i2));
                        break;
                    }
                    break;
                case 61:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        codedOutputStreamWriter.writeBytes(
                                i13, (ByteString) unsafe.getObject(obj, j2));
                        break;
                    }
                    break;
                case 62:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        int oneofIntAt6 = oneofIntAt(j2, obj);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder13 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder13.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder13.writeUInt32NoTag(oneofIntAt6);
                        break;
                    } else {
                        break;
                    }
                case 63:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        int oneofIntAt7 = oneofIntAt(j2, obj);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder14 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder14.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder14.writeInt32NoTag(oneofIntAt7);
                        break;
                    }
                    break;
                case 64:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        codedOutputStreamWriter.output.writeFixed32(i13, oneofIntAt(j2, obj));
                        break;
                    }
                    break;
                case 65:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        codedOutputStreamWriter.output.writeFixed64(i13, oneofLongAt(j2, obj));
                        break;
                    }
                    break;
                case 66:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        int oneofIntAt8 = oneofIntAt(j2, obj);
                        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder15 =
                                codedOutputStreamWriter.output;
                        codedOutputStream$ArrayEncoder15.writeTag(i13, 0);
                        codedOutputStream$ArrayEncoder15.writeUInt32NoTag(
                                (oneofIntAt8 >> 31) ^ (oneofIntAt8 << 1));
                        break;
                    }
                    break;
                case 67:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        long oneofLongAt2 = oneofLongAt(j2, obj);
                        codedOutputStreamWriter.output.writeUInt64(
                                i13, (oneofLongAt2 << 1) ^ (oneofLongAt2 >> 63));
                        break;
                    }
                    break;
                case 68:
                    i2 = i16;
                    if (isOneofPresent(i13, i2, obj)) {
                        codedOutputStreamWriter.writeGroup(
                                i13, unsafe.getObject(obj, j2), getMessageFieldSchema(i2));
                        break;
                    }
                    break;
                default:
                    i2 = i16;
                    break;
            }
            i11 = i2 + 3;
            i9 = 1048575;
        }
        ((UnknownFieldSetLiteSchema) this.unknownFieldSchema).getClass();
        ((GeneratedMessageLite) obj).unknownFields.writeTo(codedOutputStreamWriter);
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x02b5, code lost:

       if (r0 != r32) goto L107;
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x02b7, code lost:

       r15 = r29;
       r14 = r30;
       r12 = r31;
       r13 = r33;
       r11 = r34;
       r1 = r17;
       r2 = r18;
       r10 = r20;
       r6 = r26;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x02cf, code lost:

       r2 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0301, code lost:

       if (r0 != r15) goto L107;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0321, code lost:

       if (r0 != r15) goto L107;
    */
    /* JADX WARN: Failed to find 'out' block for switch in B:66:0x00a1. Please report as an issue. */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void mergeFrom(
            java.lang.Object r30,
            byte[] r31,
            int r32,
            int r33,
            com.google.protobuf.ArrayDecoders.Registers r34) {
        /*
            Method dump skipped, instructions count: 924
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.protobuf.MessageSchema.mergeFrom(java.lang.Object, byte[], int,"
                    + " int, com.google.protobuf.ArrayDecoders$Registers):void");
    }
}
