package com.samsung.android.settings.accessibility.bixby.action.advanced;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.samsung.android.settings.accessibility.SIPUtils;
import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ReadDeletedCharactersAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        ComponentName componentName = SIPUtils.HONEYBOARD_HIGH_CONTRAST_KEYBOARD_SETTING_ACTIVITY;
        SIPUtils.putValueToSIPProvider(
                context, "speak_keyboard_input_aloud_read_deleted_character", Boolean.toString(z));
        bundle.putString("result", "success");
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetCurrentStatus(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        bundle.putString(
                "result",
                BixbyUtils.getStateAlreadyChecked(
                        parsedBundle.menuValue,
                        ((String)
                                        Optional.ofNullable(
                                                        SIPUtils.getValueFromSIPProvider(
                                                                context,
                                                                "speak_keyboard_input_aloud_on",
                                                                "speak_keyboard_input_aloud_read_deleted_character"))
                                                .orElse("false"))
                                .equals("true")));
        return bundle;
    }

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
