package com.samsung.android.settings.accessibility.exclusive.info;

import android.content.Context;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SmartAlertTaskInfo extends MotionFeatureTaskInfo {
    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final String getTaskName() {
        return "smart_alert";
    }

    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final CharSequence getTaskTitle(Context context) {
        return context.getText(R.string.pick_up_title);
    }
}