package com.google.protobuf;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MapEntryLite {
    public final Metadata metadata;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Metadata {
        public final Object defaultKey;
        public final Object defaultValue;
        public final WireFormat$FieldType keyType;
        public final WireFormat$FieldType valueType;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0203  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int computeSerializedSize(
            com.google.protobuf.MapEntryLite.Metadata r17,
            java.lang.Object r18,
            java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 606
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.protobuf.MapEntryLite.computeSerializedSize(com.google.protobuf.MapEntryLite$Metadata,"
                    + " java.lang.Object, java.lang.Object):int");
    }

    public static void writeTo(
            CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder,
            Metadata metadata,
            Object obj,
            Object obj2) {
        FieldSet.writeElement(codedOutputStream$ArrayEncoder, metadata.keyType, 1, obj);
        FieldSet.writeElement(codedOutputStream$ArrayEncoder, metadata.valueType, 2, obj2);
    }
}
