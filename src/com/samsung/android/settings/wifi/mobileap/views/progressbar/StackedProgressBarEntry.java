package com.samsung.android.settings.wifi.mobileap.views.progressbar;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class StackedProgressBarEntry {
    public final String mProgressEntryName;
    public final float mProgressEntryValue;

    public StackedProgressBarEntry(String str, float f) {
        WifiApSettingsUtils.convertToColorRGB("#FF0000");
        this.mProgressEntryName = str;
        this.mProgressEntryValue = f;
    }

    public final String toString() {
        return " * mProgressEntryName: "
                + this.mProgressEntryName
                + ", mProgressEntryValue: "
                + this.mProgressEntryValue;
    }
}
