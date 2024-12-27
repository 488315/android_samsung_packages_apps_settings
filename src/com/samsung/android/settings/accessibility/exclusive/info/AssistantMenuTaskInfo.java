package com.samsung.android.settings.accessibility.exclusive.info;

import android.content.ComponentName;
import android.content.Context;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.AccessibilityConstant;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AssistantMenuTaskInfo implements ExclusiveTaskInfo {
    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final ComponentName getComponentName() {
        return AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU;
    }

    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final String getControllerName() {
        return "com.samsung.android.settings.accessibility.dexterity.AssistantMenuPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final String getPreferenceKey() {
        return "assistant_menu_preference";
    }

    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final String getTaskName() {
        return "assistant_menu";
    }

    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final CharSequence getTaskTitle(Context context) {
        return context.getText(R.string.assistant_menu_title);
    }
}
