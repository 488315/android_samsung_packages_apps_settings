package com.android.settingslib.datastore;

import com.samsung.android.knox.ex.peripheral.PeripheralConstants;

import kotlin.jvm.internal.Intrinsics;

import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackupNoOpCodec implements BackupCodec {
    @Override // com.android.settingslib.datastore.BackupCodec
    public final OutputStream encode(OutputStream outputStream) {
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        return outputStream;
    }

    @Override // com.android.settingslib.datastore.BackupCodec
    public final byte getId() {
        return (byte) 0;
    }

    @Override // com.android.settingslib.datastore.BackupCodec
    public final String getName() {
        return PeripheralConstants.Result.NOT_AVAILABLE;
    }

    @Override // com.android.settingslib.datastore.BackupCodec
    public final InputStream decode(InputStream inputStream) {
        return inputStream;
    }
}
