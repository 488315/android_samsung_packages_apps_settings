package com.android.settingslib.devicestate;

import android.content.ContentResolver;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AndroidSecureSettings implements SecureSettings {
    public final ContentResolver mContentResolver;

    public AndroidSecureSettings(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }
}
