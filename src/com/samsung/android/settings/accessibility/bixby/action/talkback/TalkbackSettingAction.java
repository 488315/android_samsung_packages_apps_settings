package com.samsung.android.settings.accessibility.bixby.action.talkback;

import android.content.Context;
import android.content.Intent;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TalkbackSettingAction extends BixbyActionTarget {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        return new Intent()
                .setComponent(
                        AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK_SETTING_ACTIVITY);
    }
}
