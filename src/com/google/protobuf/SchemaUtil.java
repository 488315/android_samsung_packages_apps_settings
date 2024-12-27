package com.google.protobuf;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SchemaUtil {
    public static final Class GENERATED_MESSAGE_CLASS;
    public static final UnknownFieldSchema PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
    public static final UnknownFieldSchema PROTO3_UNKNOWN_FIELD_SET_SCHEMA;
    public static final UnknownFieldSetLiteSchema UNKNOWN_FIELD_SET_LITE_SCHEMA;

    static {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessageV3");
        } catch (Throwable unused) {
            cls = null;
        }
        GENERATED_MESSAGE_CLASS = cls;
        PROTO2_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(false);
        PROTO3_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(true);
        UNKNOWN_FIELD_SET_LITE_SCHEMA = new UnknownFieldSetLiteSchema();
    }

    public static int computeSizeByteStringList(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream$ArrayEncoder.computeTagSize(i) * size;
        for (int i2 = 0; i2 < list.size(); i2++) {
            computeTagSize +=
                    CodedOutputStream$ArrayEncoder.computeBytesSizeNoTag((ByteString) list.get(i2));
        }
        return computeTagSize;
    }

    public static int computeSizeEnumList(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream$ArrayEncoder.computeTagSize(i) * size)
                + computeSizeEnumListNoTag(list);
    }

    public static int computeSizeEnumListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            i = 0;
            while (i2 < size) {
                intArrayList.ensureIndexInRange$3(i2);
                i += CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(intArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i +=
                        CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(
                                ((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeFixed32List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return CodedOutputStream$ArrayEncoder.computeFixed32Size(i) * size;
    }

    public static int computeSizeFixed32ListNoTag(List list) {
        return list.size() * 4;
    }

    public static int computeSizeFixed64List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return CodedOutputStream$ArrayEncoder.computeFixed64Size(i) * size;
    }

    public static int computeSizeFixed64ListNoTag(List list) {
        return list.size() * 8;
    }

    public static int computeSizeInt32List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream$ArrayEncoder.computeTagSize(i) * size)
                + computeSizeInt32ListNoTag(list);
    }

    public static int computeSizeInt32ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            i = 0;
            while (i2 < size) {
                intArrayList.ensureIndexInRange$3(i2);
                i += CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(intArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i +=
                        CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(
                                ((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeInt64List(int i, List list) {
        if (list.size() == 0) {
            return 0;
        }
        return (CodedOutputStream$ArrayEncoder.computeTagSize(i) * list.size())
                + computeSizeInt64ListNoTag(list);
    }

    public static int computeSizeInt64ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            i = 0;
            while (i2 < size) {
                longArrayList.ensureIndexInRange$4(i2);
                i += CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(longArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i +=
                        CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                                ((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeMessageList(int i, List list, Schema schema) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream$ArrayEncoder.computeTagSize(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            int serializedSize =
                    ((AbstractMessageLite) ((MessageLite) list.get(i2))).getSerializedSize(schema);
            computeTagSize +=
                    CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(serializedSize)
                            + serializedSize;
        }
        return computeTagSize;
    }

    public static int computeSizeSInt32List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream$ArrayEncoder.computeTagSize(i) * size)
                + computeSizeSInt32ListNoTag(list);
    }

    public static int computeSizeSInt32ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            i = 0;
            while (i2 < size) {
                intArrayList.ensureIndexInRange$3(i2);
                int i3 = intArrayList.array[i2];
                i += CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag((i3 >> 31) ^ (i3 << 1));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                int intValue = ((Integer) list.get(i2)).intValue();
                i +=
                        CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(
                                (intValue >> 31) ^ (intValue << 1));
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeSInt64List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream$ArrayEncoder.computeTagSize(i) * size)
                + computeSizeSInt64ListNoTag(list);
    }

    public static int computeSizeSInt64ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            i = 0;
            while (i2 < size) {
                longArrayList.ensureIndexInRange$4(i2);
                long j = longArrayList.array[i2];
                i += CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag((j >> 63) ^ (j << 1));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                long longValue = ((Long) list.get(i2)).longValue();
                i +=
                        CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                                (longValue >> 63) ^ (longValue << 1));
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeStringList(int i, List list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream$ArrayEncoder.computeTagSize(i) * size;
        if (list instanceof LazyStringList) {
            LazyStringList lazyStringList = (LazyStringList) list;
            while (i2 < size) {
                Object raw = lazyStringList.getRaw(i2);
                computeTagSize =
                        (raw instanceof ByteString
                                        ? CodedOutputStream$ArrayEncoder.computeBytesSizeNoTag(
                                                (ByteString) raw)
                                        : CodedOutputStream$ArrayEncoder.computeStringSizeNoTag(
                                                (String) raw))
                                + computeTagSize;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                computeTagSize =
                        (obj instanceof ByteString
                                        ? CodedOutputStream$ArrayEncoder.computeBytesSizeNoTag(
                                                (ByteString) obj)
                                        : CodedOutputStream$ArrayEncoder.computeStringSizeNoTag(
                                                (String) obj))
                                + computeTagSize;
                i2++;
            }
        }
        return computeTagSize;
    }

    public static int computeSizeUInt32List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream$ArrayEncoder.computeTagSize(i) * size)
                + computeSizeUInt32ListNoTag(list);
    }

    public static int computeSizeUInt32ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            i = 0;
            while (i2 < size) {
                intArrayList.ensureIndexInRange$3(i2);
                i += CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(intArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i +=
                        CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(
                                ((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeUInt64List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream$ArrayEncoder.computeTagSize(i) * size)
                + computeSizeUInt64ListNoTag(list);
    }

    public static int computeSizeUInt64ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            i = 0;
            while (i2 < size) {
                longArrayList.ensureIndexInRange$4(i2);
                i += CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(longArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i +=
                        CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                                ((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    public static UnknownFieldSchema getUnknownFieldSetSchema(boolean z) {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            cls = null;
        }
        if (cls == null) {
            return null;
        }
        try {
            return (UnknownFieldSchema)
                    cls.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused2) {
            return null;
        }
    }

    public static void mergeUnknownFields(
            UnknownFieldSchema unknownFieldSchema, Object obj, Object obj2) {
        ((UnknownFieldSetLiteSchema) unknownFieldSchema).getClass();
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
        UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        UnknownFieldSetLite unknownFieldSetLite2 = ((GeneratedMessageLite) obj2).unknownFields;
        UnknownFieldSetLite unknownFieldSetLite3 = UnknownFieldSetLite.DEFAULT_INSTANCE;
        if (!unknownFieldSetLite3.equals(unknownFieldSetLite2)) {
            if (unknownFieldSetLite3.equals(unknownFieldSetLite)) {
                int i = unknownFieldSetLite.count + unknownFieldSetLite2.count;
                int[] copyOf = Arrays.copyOf(unknownFieldSetLite.tags, i);
                System.arraycopy(
                        unknownFieldSetLite2.tags,
                        0,
                        copyOf,
                        unknownFieldSetLite.count,
                        unknownFieldSetLite2.count);
                Object[] copyOf2 = Arrays.copyOf(unknownFieldSetLite.objects, i);
                System.arraycopy(
                        unknownFieldSetLite2.objects,
                        0,
                        copyOf2,
                        unknownFieldSetLite.count,
                        unknownFieldSetLite2.count);
                unknownFieldSetLite = new UnknownFieldSetLite(i, copyOf, copyOf2, true);
            } else {
                unknownFieldSetLite.getClass();
                if (!unknownFieldSetLite2.equals(unknownFieldSetLite3)) {
                    if (!unknownFieldSetLite.isMutable) {
                        throw new UnsupportedOperationException();
                    }
                    int i2 = unknownFieldSetLite.count + unknownFieldSetLite2.count;
                    unknownFieldSetLite.ensureCapacity(i2);
                    System.arraycopy(
                            unknownFieldSetLite2.tags,
                            0,
                            unknownFieldSetLite.tags,
                            unknownFieldSetLite.count,
                            unknownFieldSetLite2.count);
                    System.arraycopy(
                            unknownFieldSetLite2.objects,
                            0,
                            unknownFieldSetLite.objects,
                            unknownFieldSetLite.count,
                            unknownFieldSetLite2.count);
                    unknownFieldSetLite.count = i2;
                }
            }
        }
        generatedMessageLite.unknownFields = unknownFieldSetLite;
    }

    public static boolean safeEquals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static void writeBoolList(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        if (!z) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                boolean booleanValue = ((Boolean) list.get(i2)).booleanValue();
                codedOutputStream$ArrayEncoder.writeTag(i, 0);
                codedOutputStream$ArrayEncoder.write(booleanValue ? (byte) 1 : (byte) 0);
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Boolean) list.get(i4)).getClass();
            Logger logger = CodedOutputStream$ArrayEncoder.logger;
            i3++;
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        for (int i5 = 0; i5 < list.size(); i5++) {
            codedOutputStream$ArrayEncoder.write(
                    ((Boolean) list.get(i5)).booleanValue() ? (byte) 1 : (byte) 0);
        }
    }

    public static void writeBytesList(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        for (int i2 = 0; i2 < list.size(); i2++) {
            ByteString byteString = (ByteString) list.get(i2);
            CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                    codedOutputStreamWriter.output;
            codedOutputStream$ArrayEncoder.writeTag(i, 2);
            codedOutputStream$ArrayEncoder.writeBytesNoTag(byteString);
        }
    }

    public static void writeDoubleList(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                double doubleValue = ((Double) list.get(i2)).doubleValue();
                codedOutputStream$ArrayEncoder.getClass();
                codedOutputStream$ArrayEncoder.writeFixed64(
                        i, Double.doubleToRawLongBits(doubleValue));
                i2++;
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Double) list.get(i4)).getClass();
            Logger logger = CodedOutputStream$ArrayEncoder.logger;
            i3 += 8;
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream$ArrayEncoder.writeFixed64NoTag(
                    Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
            i2++;
        }
    }

    public static void writeEnumList(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        if (!z) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                int intValue = ((Integer) list.get(i2)).intValue();
                codedOutputStream$ArrayEncoder.writeTag(i, 0);
                codedOutputStream$ArrayEncoder.writeInt32NoTag(intValue);
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 +=
                    CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(
                            ((Integer) list.get(i4)).intValue());
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        for (int i5 = 0; i5 < list.size(); i5++) {
            codedOutputStream$ArrayEncoder.writeInt32NoTag(((Integer) list.get(i5)).intValue());
        }
    }

    public static void writeFixed32List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream$ArrayEncoder.writeFixed32(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Integer) list.get(i4)).getClass();
            Logger logger = CodedOutputStream$ArrayEncoder.logger;
            i3 += 4;
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream$ArrayEncoder.writeFixed32NoTag(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public static void writeFixed64List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream$ArrayEncoder.writeFixed64(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Long) list.get(i4)).getClass();
            Logger logger = CodedOutputStream$ArrayEncoder.logger;
            i3 += 8;
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream$ArrayEncoder.writeFixed64NoTag(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public static void writeFloatList(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                float floatValue = ((Float) list.get(i2)).floatValue();
                codedOutputStream$ArrayEncoder.getClass();
                codedOutputStream$ArrayEncoder.writeFixed32(i, Float.floatToRawIntBits(floatValue));
                i2++;
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Float) list.get(i4)).getClass();
            Logger logger = CodedOutputStream$ArrayEncoder.logger;
            i3 += 4;
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream$ArrayEncoder.writeFixed32NoTag(
                    Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
            i2++;
        }
    }

    public static void writeGroupList(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, Schema schema) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        for (int i2 = 0; i2 < list.size(); i2++) {
            codedOutputStreamWriter.writeGroup(i, list.get(i2), schema);
        }
    }

    public static void writeInt32List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        if (!z) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                int intValue = ((Integer) list.get(i2)).intValue();
                codedOutputStream$ArrayEncoder.writeTag(i, 0);
                codedOutputStream$ArrayEncoder.writeInt32NoTag(intValue);
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 +=
                    CodedOutputStream$ArrayEncoder.computeInt32SizeNoTag(
                            ((Integer) list.get(i4)).intValue());
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        for (int i5 = 0; i5 < list.size(); i5++) {
            codedOutputStream$ArrayEncoder.writeInt32NoTag(((Integer) list.get(i5)).intValue());
        }
    }

    public static void writeInt64List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream$ArrayEncoder.writeUInt64(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 +=
                    CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                            ((Long) list.get(i4)).longValue());
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream$ArrayEncoder.writeUInt64NoTag(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public static void writeMessageList(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, Schema schema) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        for (int i2 = 0; i2 < list.size(); i2++) {
            codedOutputStreamWriter.writeMessage(i, list.get(i2), schema);
        }
    }

    public static void writeSFixed32List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream$ArrayEncoder.writeFixed32(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Integer) list.get(i4)).getClass();
            Logger logger = CodedOutputStream$ArrayEncoder.logger;
            i3 += 4;
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream$ArrayEncoder.writeFixed32NoTag(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public static void writeSFixed64List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream$ArrayEncoder.writeFixed64(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Long) list.get(i4)).getClass();
            Logger logger = CodedOutputStream$ArrayEncoder.logger;
            i3 += 8;
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream$ArrayEncoder.writeFixed64NoTag(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public static void writeSInt32List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        if (!z) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                int intValue = ((Integer) list.get(i2)).intValue();
                codedOutputStream$ArrayEncoder.writeTag(i, 0);
                codedOutputStream$ArrayEncoder.writeUInt32NoTag((intValue >> 31) ^ (intValue << 1));
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            int intValue2 = ((Integer) list.get(i4)).intValue();
            i3 +=
                    CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(
                            (intValue2 >> 31) ^ (intValue2 << 1));
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        for (int i5 = 0; i5 < list.size(); i5++) {
            int intValue3 = ((Integer) list.get(i5)).intValue();
            codedOutputStream$ArrayEncoder.writeUInt32NoTag((intValue3 >> 31) ^ (intValue3 << 1));
        }
    }

    public static void writeSInt64List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                long longValue = ((Long) list.get(i2)).longValue();
                codedOutputStream$ArrayEncoder.writeUInt64(i, (longValue >> 63) ^ (longValue << 1));
                i2++;
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            long longValue2 = ((Long) list.get(i4)).longValue();
            i3 +=
                    CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                            (longValue2 >> 63) ^ (longValue2 << 1));
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            long longValue3 = ((Long) list.get(i2)).longValue();
            codedOutputStream$ArrayEncoder.writeUInt64NoTag((longValue3 >> 63) ^ (longValue3 << 1));
            i2++;
        }
    }

    public static void writeStringList(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z = list instanceof LazyStringList;
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                String str = (String) list.get(i2);
                codedOutputStream$ArrayEncoder.writeTag(i, 2);
                codedOutputStream$ArrayEncoder.writeStringNoTag(str);
                i2++;
            }
            return;
        }
        LazyStringList lazyStringList = (LazyStringList) list;
        while (i2 < list.size()) {
            Object raw = lazyStringList.getRaw(i2);
            if (raw instanceof String) {
                codedOutputStream$ArrayEncoder.writeTag(i, 2);
                codedOutputStream$ArrayEncoder.writeStringNoTag((String) raw);
            } else {
                codedOutputStream$ArrayEncoder.writeTag(i, 2);
                codedOutputStream$ArrayEncoder.writeBytesNoTag((ByteString) raw);
            }
            i2++;
        }
    }

    public static void writeUInt32List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        if (!z) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                int intValue = ((Integer) list.get(i2)).intValue();
                codedOutputStream$ArrayEncoder.writeTag(i, 0);
                codedOutputStream$ArrayEncoder.writeUInt32NoTag(intValue);
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 +=
                    CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(
                            ((Integer) list.get(i4)).intValue());
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        for (int i5 = 0; i5 < list.size(); i5++) {
            codedOutputStream$ArrayEncoder.writeUInt32NoTag(((Integer) list.get(i5)).intValue());
        }
    }

    public static void writeUInt64List(
            int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream$ArrayEncoder.writeUInt64(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 +=
                    CodedOutputStream$ArrayEncoder.computeUInt64SizeNoTag(
                            ((Long) list.get(i4)).longValue());
        }
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream$ArrayEncoder.writeUInt64NoTag(((Long) list.get(i2)).longValue());
            i2++;
        }
    }
}
