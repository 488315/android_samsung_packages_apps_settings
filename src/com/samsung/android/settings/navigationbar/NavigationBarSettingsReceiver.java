package com.samsung.android.settings.navigationbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarSettingsReceiver extends BroadcastReceiver {
    public NavigationBarOverlayInteractor mModeController;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (this.mModeController == null) {
            this.mModeController = new NavigationBarOverlayInteractor(context);
        }
        if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(action)
                || "com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action)) {
            if (NavigationBarSettingsUtil.isGestureDefault()) {
                this.mModeController.setInteractionMode(
                        "com.android.internal.systemui.navbar.gestural");
            } else {
                this.mModeController.setInteractionMode(
                        "com.android.internal.systemui.navbar.threebutton");
            }
        }
    }
}
