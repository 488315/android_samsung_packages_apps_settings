package com.samsung.android.settings.languagepack.gdiff;

import java.io.Closeable;
import java.io.RandomAccessFile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RandomAccessFileSeekableSource implements Closeable {
    public RandomAccessFile raf;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.raf.close();
    }
}
