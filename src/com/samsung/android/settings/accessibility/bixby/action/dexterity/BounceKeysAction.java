package com.samsung.android.settings.accessibility.bixby.action.dexterity;

import android.content.Context;
import android.os.Bundle;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;
import com.samsung.android.settings.accessibility.dexterity.BounceKeysUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BounceKeysAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction,
              // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        BounceKeysUtils.setBounceKeysEnable(context, z);
        bundle.putString("result", "success");
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Bundle doGetExclusivePopup(Context context) {
        Bundle bundle = new Bundle();
        String exclusivepopupDescription =
                BixbyUtils.getExclusivepopupDescription(
                        context, "bounce_keys", context.getString(R.string.bounce_keys));
        bundle.putString("result", exclusivepopupDescription.isEmpty() ? "false" : "true");
        bundle.putString("description", exclusivepopupDescription);
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.dexterity.controllers.BounceKeysPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final String getDestinationFragment() {
        return "com.samsung.android.settings.accessibility.dexterity.BounceKeysPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final int getTitleRes() {
        return R.string.bounce_keys;
    }
}
