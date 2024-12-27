package com.google.protobuf;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FieldSet {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SmallSortedMap$1 fields = new SmallSortedMap$1(16);
    public boolean isImmutable;

    static {
        new FieldSet(0);
    }

    public FieldSet() {}

    public static void writeElement(
            CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder,
            WireFormat$FieldType wireFormat$FieldType,
            int i,
            Object obj) {
        if (wireFormat$FieldType == WireFormat$FieldType.GROUP) {
            codedOutputStream$ArrayEncoder.writeTag(i, 3);
            ((GeneratedMessageLite) ((MessageLite) obj)).writeTo(codedOutputStream$ArrayEncoder);
            codedOutputStream$ArrayEncoder.writeTag(i, 4);
            return;
        }
        codedOutputStream$ArrayEncoder.writeTag(i, wireFormat$FieldType.getWireType());
        switch (wireFormat$FieldType.ordinal()) {
            case 0:
                codedOutputStream$ArrayEncoder.writeFixed64NoTag(
                        Double.doubleToRawLongBits(((Double) obj).doubleValue()));
                break;
            case 1:
                codedOutputStream$ArrayEncoder.writeFixed32NoTag(
                        Float.floatToRawIntBits(((Float) obj).floatValue()));
                break;
            case 2:
                codedOutputStream$ArrayEncoder.writeUInt64NoTag(((Long) obj).longValue());
                break;
            case 3:
                codedOutputStream$ArrayEncoder.writeUInt64NoTag(((Long) obj).longValue());
                break;
            case 4:
                codedOutputStream$ArrayEncoder.writeInt32NoTag(((Integer) obj).intValue());
                break;
            case 5:
                codedOutputStream$ArrayEncoder.writeFixed64NoTag(((Long) obj).longValue());
                break;
            case 6:
                codedOutputStream$ArrayEncoder.writeFixed32NoTag(((Integer) obj).intValue());
                break;
            case 7:
                codedOutputStream$ArrayEncoder.write(
                        ((Boolean) obj).booleanValue() ? (byte) 1 : (byte) 0);
                break;
            case 8:
                if (!(obj instanceof ByteString)) {
                    codedOutputStream$ArrayEncoder.writeStringNoTag((String) obj);
                    break;
                } else {
                    codedOutputStream$ArrayEncoder.writeBytesNoTag((ByteString) obj);
                    break;
                }
            case 9:
                ((GeneratedMessageLite) ((MessageLite) obj))
                        .writeTo(codedOutputStream$ArrayEncoder);
                break;
            case 10:
                GeneratedMessageLite generatedMessageLite =
                        (GeneratedMessageLite) ((MessageLite) obj);
                codedOutputStream$ArrayEncoder.writeUInt32NoTag(
                        generatedMessageLite.getSerializedSize(null));
                generatedMessageLite.writeTo(codedOutputStream$ArrayEncoder);
                break;
            case 11:
                if (!(obj instanceof ByteString)) {
                    byte[] bArr = (byte[]) obj;
                    int length = bArr.length;
                    codedOutputStream$ArrayEncoder.writeUInt32NoTag(length);
                    codedOutputStream$ArrayEncoder.write(bArr, 0, length);
                    break;
                } else {
                    codedOutputStream$ArrayEncoder.writeBytesNoTag((ByteString) obj);
                    break;
                }
            case 12:
                codedOutputStream$ArrayEncoder.writeUInt32NoTag(((Integer) obj).intValue());
                break;
            case 13:
                if (!(obj instanceof Internal.EnumLite)) {
                    codedOutputStream$ArrayEncoder.writeInt32NoTag(((Integer) obj).intValue());
                    break;
                } else {
                    codedOutputStream$ArrayEncoder.writeInt32NoTag(
                            ((Internal.EnumLite) obj).getNumber());
                    break;
                }
            case 14:
                codedOutputStream$ArrayEncoder.writeFixed32NoTag(((Integer) obj).intValue());
                break;
            case 15:
                codedOutputStream$ArrayEncoder.writeFixed64NoTag(((Long) obj).longValue());
                break;
            case 16:
                int intValue = ((Integer) obj).intValue();
                codedOutputStream$ArrayEncoder.writeUInt32NoTag((intValue >> 31) ^ (intValue << 1));
                break;
            case 17:
                long longValue = ((Long) obj).longValue();
                codedOutputStream$ArrayEncoder.writeUInt64NoTag(
                        (longValue >> 63) ^ (longValue << 1));
                break;
        }
    }

    public final Object clone() {
        FieldSet fieldSet = new FieldSet();
        SmallSortedMap$1 smallSortedMap$1 = this.fields;
        if (smallSortedMap$1.entryList.size() > 0) {
            Map.Entry arrayEntryAt = smallSortedMap$1.getArrayEntryAt(0);
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(arrayEntryAt.getKey());
            arrayEntryAt.getValue();
            throw null;
        }
        Iterator it = smallSortedMap$1.getOverflowEntries().iterator();
        if (!it.hasNext()) {
            return fieldSet;
        }
        Map.Entry entry = (Map.Entry) it.next();
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(entry.getKey());
        entry.getValue();
        throw null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FieldSet) {
            return this.fields.equals(((FieldSet) obj).fields);
        }
        return false;
    }

    public final int hashCode() {
        return this.fields.hashCode();
    }

    public final void makeImmutable() {
        SmallSortedMap$1 smallSortedMap$1;
        if (this.isImmutable) {
            return;
        }
        int i = 0;
        while (true) {
            smallSortedMap$1 = this.fields;
            if (i >= smallSortedMap$1.entryList.size()) {
                break;
            }
            Map.Entry arrayEntryAt = smallSortedMap$1.getArrayEntryAt(i);
            if (arrayEntryAt.getValue() instanceof GeneratedMessageLite) {
                GeneratedMessageLite generatedMessageLite =
                        (GeneratedMessageLite) arrayEntryAt.getValue();
                generatedMessageLite.getClass();
                Protobuf protobuf = Protobuf.INSTANCE;
                protobuf.getClass();
                protobuf.schemaFor(generatedMessageLite.getClass())
                        .makeImmutable(generatedMessageLite);
                generatedMessageLite.markImmutable();
            }
            i++;
        }
        if (!smallSortedMap$1.isImmutable) {
            if (smallSortedMap$1.entryList.size() > 0) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                        smallSortedMap$1.getArrayEntryAt(0).getKey());
                throw null;
            }
            Iterator it = smallSortedMap$1.getOverflowEntries().iterator();
            if (it.hasNext()) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                        ((Map.Entry) it.next()).getKey());
                throw null;
            }
        }
        if (!smallSortedMap$1.isImmutable) {
            smallSortedMap$1.overflowEntries =
                    smallSortedMap$1.overflowEntries.isEmpty()
                            ? Collections.emptyMap()
                            : Collections.unmodifiableMap(smallSortedMap$1.overflowEntries);
            smallSortedMap$1.overflowEntriesDescending =
                    smallSortedMap$1.overflowEntriesDescending.isEmpty()
                            ? Collections.emptyMap()
                            : Collections.unmodifiableMap(
                                    smallSortedMap$1.overflowEntriesDescending);
            smallSortedMap$1.isImmutable = true;
        }
        this.isImmutable = true;
    }

    public FieldSet(int i) {
        makeImmutable();
        makeImmutable();
    }
}
