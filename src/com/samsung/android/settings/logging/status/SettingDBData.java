package com.samsung.android.settings.logging.status;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SettingDBData {
    public final String mSettingsDBCategory;
    public final String mSettingsDBName;

    public SettingDBData(String str, String str2) {
        this.mSettingsDBName = str;
        this.mSettingsDBCategory = str2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Name : ");
        sb.append(this.mSettingsDBName);
        sb.append("  Category :");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                sb, this.mSettingsDBCategory, " type : null");
    }
}
