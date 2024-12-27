package com.samsung.android.settings.accessibility.exclusive.info;

import android.content.Context;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.AccessibilityRune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RemoveAnimationsTaskInfo implements ExclusiveTaskInfo {
    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final String getTaskName() {
        return "remove_animation";
    }

    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final CharSequence getTaskTitle(Context context) {
        return AccessibilityRune.isAtLeastOneUI_6_1()
                ? context.getText(R.string.reduce_animations_title)
                : context.getText(R.string.accessibility_disable_animations);
    }
}
