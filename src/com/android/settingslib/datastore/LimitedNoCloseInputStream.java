package com.android.settingslib.datastore;

import java.io.FilterInputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LimitedNoCloseInputStream extends FilterInputStream {
    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public LimitedNoCloseInputStream(android.app.backup.BackupDataInputStream r4) {
        /*
            r3 = this;
            java.lang.String r0 = "inputStream"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            int r0 = r4.size()
            long r0 = (long) r0
            int r2 = com.google.common.io.ByteStreams.$r8$clinit
            com.google.common.io.ByteStreams$LimitedInputStream r2 = new com.google.common.io.ByteStreams$LimitedInputStream
            r2.<init>(r4, r0)
            r3.<init>(r2)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.datastore.LimitedNoCloseInputStream.<init>(android.app.backup.BackupDataInputStream):void");
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable,
              // java.lang.AutoCloseable
    public final void close() {}
}
