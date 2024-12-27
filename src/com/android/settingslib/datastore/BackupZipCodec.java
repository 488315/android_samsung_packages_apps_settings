package com.android.settingslib.datastore;

import kotlin.jvm.internal.Intrinsics;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackupZipCodec implements BackupCodec {
    public static final BackupZipCodec BEST_COMPRESSION =
            new BackupZipCodec(9, "ZipBestCompression");
    public final int compressionLevel;
    public final String name;

    public BackupZipCodec(int i, String str) {
        this.compressionLevel = i;
        this.name = str;
    }

    @Override // com.android.settingslib.datastore.BackupCodec
    public final InputStream decode(InputStream inputStream) {
        return new InflaterInputStream(inputStream);
    }

    @Override // com.android.settingslib.datastore.BackupCodec
    public final OutputStream encode(OutputStream outputStream) {
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        return new DeflaterOutputStream(outputStream, new Deflater(this.compressionLevel));
    }

    @Override // com.android.settingslib.datastore.BackupCodec
    public final byte getId() {
        return (byte) 1;
    }

    @Override // com.android.settingslib.datastore.BackupCodec
    public final String getName() {
        return this.name;
    }
}
