package com.samsung.android.settings.accessibility.bixby.action.advanced;

import android.content.Context;
import android.content.Intent;

import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SpeakKeyboardInputAloudAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.vision.controllers.SpeakKeyboardPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        return new Intent(
                "com.samsung.android.honeyboard.intent.action.SPEAK_KEYBOARD_INPUT_ALOUD_SETTING");
    }
}
