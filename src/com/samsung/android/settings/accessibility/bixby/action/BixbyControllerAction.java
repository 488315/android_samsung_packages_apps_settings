package com.samsung.android.settings.accessibility.bixby.action;

import android.content.Context;
import android.os.Bundle;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.accessibility.bixby.BixbyUtils;
import com.samsung.android.settings.accessibility.bixby.data.ParsedBundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BixbyControllerAction extends BixbyActionTarget {
    public BasePreferenceController controller = null;

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public Bundle doEnableAction(Context context, ParsedBundle parsedBundle, boolean z) {
        Bundle bundle = new Bundle();
        BasePreferenceController basePreferenceController = this.controller;
        bundle.putString(
                "result",
                basePreferenceController instanceof TogglePreferenceController
                        ? ((TogglePreferenceController) basePreferenceController).setChecked(z)
                        : false ? "success" : "fail");
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public Bundle doGetCurrentStatus(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        String str = parsedBundle.menuValue;
        BasePreferenceController basePreferenceController = this.controller;
        bundle.putString(
                "result",
                BixbyUtils.getStateAlreadyChecked(
                        str,
                        basePreferenceController instanceof TogglePreferenceController
                                ? ((TogglePreferenceController) basePreferenceController)
                                        .getThreadEnabled()
                                : true));
        return bundle;
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public Bundle doGetSupportFeature(Context context, ParsedBundle parsedBundle) {
        Bundle bundle = new Bundle();
        BasePreferenceController basePreferenceController = this.controller;
        int availabilityStatus =
                basePreferenceController != null
                        ? basePreferenceController.getAvailabilityStatus()
                        : -1;
        bundle.putString(
                "result",
                (availabilityStatus == 0 || availabilityStatus == 1)
                        ? "true"
                        : availabilityStatus == 3 ? "DeviceChangeNeeded" : "false");
        return bundle;
    }

    public abstract String getControllerName(Context context);
}
