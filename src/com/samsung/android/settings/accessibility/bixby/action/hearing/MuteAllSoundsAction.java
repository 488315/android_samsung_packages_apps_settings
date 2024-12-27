package com.samsung.android.settings.accessibility.bixby.action.hearing;

import android.content.Context;
import android.os.Bundle;

import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MuteAllSoundsAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("result", "success");
        SecAccessibilityUtils.enableMuteAllSounds(context, z);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetExclusivePopup(Context context) {
        String string;
        Bundle bundle = new Bundle();
        if (AccessibilityUtils.getEnabledServicesFromSettings(context)
                .contains(AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK)) {
            String string2 = context.getString(R.string.talkback_title);
            StringBuilder sb = new StringBuilder();
            sb.append(
                    String.format(
                            context.getString(R.string.change_popup_msg),
                            context.getString(R.string.mute_all_sounds_title),
                            string2));
            sb.append("\n\n");
            string =
                    SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                            .m(context, R.string.mute_all_sounds_description, sb);
        } else {
            string = context.getString(R.string.mute_all_sounds_description);
        }
        bundle.putString("result", "true");
        bundle.putString("description", string);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.hearing.controllers.MuteAllSoundsPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.hearing.MuteAllSoundFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.mute_all_sounds_title;
    }
}
