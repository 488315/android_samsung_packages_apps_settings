package com.samsung.android.settings.account.controller;

import android.content.Intent;

import com.android.settings.core.SubSettingLauncher;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract /* synthetic */ class SecAccountPreferenceController$$ExternalSyntheticOutline0 {
    public static Intent m(SubSettingLauncher subSettingLauncher, String str, int i, int i2) {
        subSettingLauncher.setTitleRes(i, str);
        subSettingLauncher.addFlags(i2);
        return subSettingLauncher.toIntent();
    }
}
