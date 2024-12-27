package com.android.settings.accessibility;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum ScreenFlashNotificationColor {
    /* JADX INFO: Fake field, exist only in values array */
    EF0(R.string.screen_flash_color_blue, "BLUE"),
    /* JADX INFO: Fake field, exist only in values array */
    EF1(R.string.screen_flash_color_azure, "AZURE"),
    /* JADX INFO: Fake field, exist only in values array */
    EF2(R.string.screen_flash_color_cyan, "CYAN"),
    /* JADX INFO: Fake field, exist only in values array */
    EF3(R.string.screen_flash_color_spring_green, "SPRING_GREEN"),
    /* JADX INFO: Fake field, exist only in values array */
    EF4(R.string.screen_flash_color_green, "GREEN"),
    /* JADX INFO: Fake field, exist only in values array */
    EF5(R.string.screen_flash_color_chartreuse_green, "CHARTREUSE_GREEN"),
    YELLOW(R.string.screen_flash_color_yellow, "YELLOW"),
    /* JADX INFO: Fake field, exist only in values array */
    EF111(R.string.screen_flash_color_orange, "ORANGE"),
    /* JADX INFO: Fake field, exist only in values array */
    EF125(R.string.screen_flash_color_red, "RED"),
    /* JADX INFO: Fake field, exist only in values array */
    EF140(R.string.screen_flash_color_rose, "ROSE"),
    /* JADX INFO: Fake field, exist only in values array */
    EF155(R.string.screen_flash_color_magenta, "MAGENTA"),
    /* JADX INFO: Fake field, exist only in values array */
    EF170(R.string.screen_flash_color_violet, "VIOLET");

    final int mColorInt;
    final int mOpaqueColorInt;
    final int mStringRes;

    ScreenFlashNotificationColor(int i, String str) {
        this.mColorInt = r2;
        this.mStringRes = i;
        this.mOpaqueColorInt = (-16777216) | r2;
    }
}
