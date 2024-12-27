package com.google.common.collect;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class UnmodifiableIterator implements Iterator {
    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
