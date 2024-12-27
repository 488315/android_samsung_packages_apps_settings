package com.android.settingslib.display;

import android.os.RemoteException;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManagerGlobal;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayDensityUtils$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ DisplayDensityUtils f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DisplayDensityUtils$$ExternalSyntheticLambda0(
            DisplayDensityUtils displayDensityUtils, int i, int i2) {
        this.f$0 = displayDensityUtils;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DisplayDensityUtils displayDensityUtils = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        displayDensityUtils.getClass();
        try {
            for (Display display :
                    displayDensityUtils.mDisplayManager.getDisplays(
                            "android.hardware.display.category.ALL_INCLUDING_DISABLED")) {
                int displayId = display.getDisplayId();
                DisplayInfo displayInfo = new DisplayInfo();
                if (!display.getDisplayInfo(displayInfo)) {
                    Log.w(
                            "DisplayDensityUtils",
                            "Unable to save forced display density setting for display "
                                    + displayId);
                } else if (displayDensityUtils.mPredicate.test(displayInfo)) {
                    if (((HashMap) displayDensityUtils.mValuesPerDisplay)
                            .containsKey(displayInfo.uniqueId)) {
                        WindowManagerGlobal.getWindowManagerService()
                                .setForcedDisplayDensityForUser(
                                        displayId,
                                        ((int[])
                                                        ((HashMap)
                                                                        displayDensityUtils
                                                                                .mValuesPerDisplay)
                                                                .get(displayInfo.uniqueId))
                                                [i],
                                        i2);
                    } else {
                        Log.w(
                                "DisplayDensityUtils",
                                "Unable to save forced display density setting for display "
                                        + displayInfo.uniqueId);
                    }
                }
            }
        } catch (RemoteException unused) {
            Log.w("DisplayDensityUtils", "Unable to save forced display density setting");
        }
    }
}
