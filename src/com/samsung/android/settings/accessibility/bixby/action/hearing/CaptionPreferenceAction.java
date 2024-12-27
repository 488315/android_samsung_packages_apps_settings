package com.samsung.android.settings.accessibility.bixby.action.hearing;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CaptionPreferenceAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        Settings.Secure.putInt(
                context.getContentResolver(), "accessibility_captioning_enabled", z ? 1 : 0);
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
                        Settings.Secure.getInt(
                                        context.getContentResolver(),
                                        "accessibility_captioning_enabled",
                                        0)
                                == 1));
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.hearing.controllers.SecCaptioningPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.android.settings.accessibility.CaptioningPropertiesFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.accessibility_captioning_title;
    }
}
