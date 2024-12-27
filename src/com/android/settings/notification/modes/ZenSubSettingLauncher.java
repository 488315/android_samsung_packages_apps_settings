package com.android.settings.notification.modes;

import android.content.Context;
import android.os.Bundle;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.core.SubSettingLauncher;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ZenSubSettingLauncher {
    public static SubSettingLauncher forMode(Context context, String str) {
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("MODE_ID", str);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = ZenModeFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = m;
        launchRequest.mSourceMetricsCategory = 142;
        return subSettingLauncher;
    }
}
