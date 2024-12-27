package com.samsung.android.settings.uwb;

import android.content.Context;
import android.provider.Settings;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UwbSettingDb {
    public final Context mContext;

    public UwbSettingDb(Context context) {
        this.mContext = context;
    }

    public final boolean getUwbSettingDb() {
        int i = Settings.Global.getInt(this.mContext.getContentResolver(), "uwb_enabled", 0);
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "getUwbSettingDb : ", "UwbSettingDb");
        return i == 1;
    }

    public final void setUwbSettingDb(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setUwbSettingDb : ", "UwbSettingDb", z);
        Settings.Global.putInt(this.mContext.getContentResolver(), "uwb_enabled", z ? 1 : 0);
    }
}
