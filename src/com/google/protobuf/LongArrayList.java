package com.google.protobuf;

import java.util.RandomAccess;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class LongArrayList extends AbstractProtobufList
        implements RandomAccess, PrimitiveNonBoxingCollection {
    public final long[] array;

    public abstract void addLong(long j);

    public abstract void ensureIndexInRange$4(int i);
}
