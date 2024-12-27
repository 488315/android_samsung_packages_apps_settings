package com.android.settingslib.datastore;

import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface BackupCodec {
    InputStream decode(InputStream inputStream);

    OutputStream encode(OutputStream outputStream);

    byte getId();

    String getName();
}
