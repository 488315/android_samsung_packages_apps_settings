package com.google.protobuf;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MapFieldSchemaLite {
    public static int getSerializedSize(int i, Object obj, Object obj2) {
        MapFieldLite mapFieldLite = (MapFieldLite) obj;
        MapEntryLite mapEntryLite = (MapEntryLite) obj2;
        int i2 = 0;
        if (!mapFieldLite.isEmpty()) {
            for (Map.Entry entry : mapFieldLite.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                mapEntryLite.getClass();
                int computeTagSize = CodedOutputStream$ArrayEncoder.computeTagSize(i);
                int computeSerializedSize =
                        MapEntryLite.computeSerializedSize(mapEntryLite.metadata, key, value);
                i2 =
                        MapFieldSchemaLite$$ExternalSyntheticOutline0.m(
                                computeSerializedSize, computeSerializedSize, computeTagSize, i2);
            }
        }
        return i2;
    }

    public static MapFieldLite mergeFrom(Object obj, Object obj2) {
        MapFieldLite mapFieldLite = (MapFieldLite) obj;
        MapFieldLite mapFieldLite2 = (MapFieldLite) obj2;
        if (!mapFieldLite2.isEmpty()) {
            if (!mapFieldLite.isMutable()) {
                mapFieldLite = mapFieldLite.mutableCopy();
            }
            mapFieldLite.ensureMutable();
            if (!mapFieldLite2.isEmpty()) {
                mapFieldLite.putAll(mapFieldLite2);
            }
        }
        return mapFieldLite;
    }
}
