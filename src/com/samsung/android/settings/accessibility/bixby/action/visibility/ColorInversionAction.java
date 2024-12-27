package com.samsung.android.settings.accessibility.bixby.action.visibility;

import android.content.Context;
import android.os.Bundle;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ColorInversionAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetExclusivePopup(Context context) {
        return AbsAdapter$1$$ExternalSyntheticOutline0.m("result", "false");
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.vision.controllers.ColorInversionPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.android.settings.accessibility.ToggleColorInversionPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.accessibility_display_inversion_preference_title;
    }
}
