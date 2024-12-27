package com.samsung.android.settings.accessibility.bixby.action.visibility;

import android.content.Context;
import android.content.Intent;

import com.samsung.android.settings.accessibility.SIPUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HighContrastKeyboardAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.vision.controllers.HighContrastKeyboardPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        return new Intent()
                .setComponent(SIPUtils.HONEYBOARD_HIGH_CONTRAST_KEYBOARD_SETTING_ACTIVITY);
    }
}
