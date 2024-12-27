package com.samsung.android.settings.accessibility.exclusive.info;

import android.content.ComponentName;
import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface ExclusiveTaskInfo {
    default ComponentName getComponentName() {
        return null;
    }

    default String getControllerName() {
        return null;
    }

    default String getPreferenceKey() {
        return null;
    }

    String getTaskName();

    CharSequence getTaskTitle(Context context);

    default boolean isAccessibilityTask() {
        return true;
    }

    default boolean isAvailable(Context context) {
        return true;
    }
}
