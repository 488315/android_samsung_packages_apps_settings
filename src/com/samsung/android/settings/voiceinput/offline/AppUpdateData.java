package com.samsung.android.settings.voiceinput.offline;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AppUpdateData {
    private String mResultCode = ApnSettings.MVNO_NONE;
    private String mVersionCode = ApnSettings.MVNO_NONE;
    private String mContentSize = ApnSettings.MVNO_NONE;

    public final String getmResultCode() {
        return this.mResultCode;
    }

    public final String getmVersionCode() {
        return this.mVersionCode;
    }

    public final void setmContentSize(String str) {
        this.mContentSize = str;
    }

    public final void setmResultCode(String str) {
        this.mResultCode = str;
    }

    public final void setmVersionCode(String str) {
        this.mVersionCode = str;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AppUpdateData{mResultCode='");
        sb.append(this.mResultCode);
        sb.append("', mVersionCode='");
        sb.append(this.mVersionCode);
        sb.append("', mContentSize='");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.mContentSize, "'}");
    }
}
