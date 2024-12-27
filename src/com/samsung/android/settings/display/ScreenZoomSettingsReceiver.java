package com.samsung.android.settings.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenZoomSettingsReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int i = 0;
        if ("com.samsung.intent.action.UPDATE_SCREEN_ZOOM".equals(action)) {
            int[] screenZoomInfo = SecDisplayUtils.getScreenZoomInfo(context);
            if (screenZoomInfo == null) {
                Log.e("ScreenZoomSettingsReceiver", "screenZoomInfo is null, so return");
                return;
            }
            int i2 = screenZoomInfo[1];
            int i3 = screenZoomInfo[0];
            DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                    "current zoomIndex=", " zoomRange=", i2, i3, "ScreenZoomSettingsReceiver");
            int currentDensity = SecDisplayUtils.getCurrentDensity(context);
            int i4 = SystemProperties.getInt("ro.sf.lcd_density", 160);
            DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                    "isDefaultScreenZoom currentDensity : ",
                    ", deviceDensity: ",
                    currentDensity,
                    i4,
                    "SecDisplayUtils");
            if (currentDensity > i4) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        i2, "No update! zoomIndex: ", "ScreenZoomSettingsReceiver");
                return;
            }
            int i5 = i2 + 1;
            screenZoomInfo[1] = i5;
            screenZoomInfo[0] = i3;
            Log.i("ScreenZoomSettingsReceiver", "Update screen zoom. index=" + i5);
            SecDisplayUtils.setScreenZoomInfo(context, screenZoomInfo);
            return;
        }
        if ("com.samsung.intent.action.SET_DEFAULT_SCREEN_ZOOM".equals(action)) {
            Log.i("ScreenZoomSettingsReceiver", "Set default screen zoom");
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            try {
                try {
                    IWindowManager windowManagerService =
                            WindowManagerGlobal.getWindowManagerService();
                    String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                    int screenResolution = SecDisplayUtils.getScreenResolution(context);
                    i =
                            (int)
                                    (windowManagerService.getInitialDisplayDensity(0)
                                            * new float[] {0.5f, 0.75f, 1.0f}[screenResolution]);
                    Log.i(
                            "SecDisplayUtils",
                            "setDefaultScreenZoom: baseDensity="
                                    + i
                                    + " resolution index="
                                    + screenResolution);
                    if (i == 0) {
                        return;
                    }
                } catch (RemoteException e) {
                    Log.e(
                            "SecDisplayUtils",
                            "setDefaultScreenZoom: Can't call getInitialDisplayDensity() on"
                                + " IWindowManager. Remote exception="
                                    + e);
                    if (i == 0) {
                        return;
                    }
                }
                SecDisplayUtils.applyForcedDisplayDensity(-1, -1, i);
            } catch (Throwable th) {
                if (i != 0) {
                    SecDisplayUtils.applyForcedDisplayDensity(-1, -1, i);
                }
                throw th;
            }
        }
    }
}
