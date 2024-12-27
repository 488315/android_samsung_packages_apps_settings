package com.samsung.context.sdk.samsunganalytics.internal.connection;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public enum Directory {
    DEVICE_CONTROLLER_DIR("/v1/quotas"),
    DATA_DELETE("/app/delete"),
    DLS_DIR(ApnSettings.MVNO_NONE),
    DLS_DIR_BAT(ApnSettings.MVNO_NONE);

    String directory;

    Directory(String str) {
        this.directory = str;
    }

    public final void setDirectory(String str) {
        this.directory = str;
    }
}
