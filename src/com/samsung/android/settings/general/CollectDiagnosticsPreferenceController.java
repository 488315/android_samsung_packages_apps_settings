package com.samsung.android.settings.general;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CollectDiagnosticsPreferenceController extends BasePreferenceController {
    private final String ACTION_SETTING_DIAGNOSTICS;
    private final String LOG_TAG;
    private boolean mHasCorrectMyAccount;

    public CollectDiagnosticsPreferenceController(Context context, String str) {
        super(context, str);
        this.LOG_TAG = "CollectDiagnosticsPreferenceController";
        this.mHasCorrectMyAccount = false;
        this.ACTION_SETTING_DIAGNOSTICS = "com.tmobile.device.settings";
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0094  */
    @Override // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus() {
        /*
            r13 = this;
            java.lang.String r0 = "CollectDiagnosticsPreferenceController"
            java.lang.String r1 = "hasMyAccountEnabled: "
            java.lang.String r2 = "mHasCorrectMyAccount = "
            java.lang.String r3 = "isSkipMyAccount: rawVersion = "
            android.content.Context r4 = r13.mContext
            android.content.pm.PackageManager r4 = r4.getPackageManager()
            r5 = 0
            r6 = 1
            java.lang.String r7 = "com.carrieriq.tmobile.IQToggle"
            r8 = 128(0x80, float:1.794E-43)
            r4.getApplicationInfo(r7, r8)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L19
            r7 = r6
            goto L1f
        L19:
            java.lang.String r7 = "Package not found : IQToggle"
            android.util.Log.i(r0, r7)
            r7 = r5
        L1f:
            r8 = 3
            java.lang.String r9 = "com.tmobile.echolocate"
            android.content.pm.PackageInfo r4 = r4.getPackageInfo(r9, r5)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            java.lang.String r9 = r4.versionName     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            java.lang.String r10 = r9.substring(r5, r8)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            float r11 = java.lang.Float.parseFloat(r10)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            r12.<init>(r3)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            r12.append(r9)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            java.lang.String r3 = ", stringVerson = "
            r12.append(r3)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            r12.append(r10)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            java.lang.String r3 = ", numVersion = "
            r12.append(r3)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            r12.append(r11)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            java.lang.String r3 = r12.toString()     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            android.util.Log.i(r0, r3)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            r3 = 1090728755(0x41033333, float:8.2)
            int r3 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r3 < 0) goto L57
            goto L58
        L57:
            r6 = r5
        L58:
            r13.mHasCorrectMyAccount = r6     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            r3.<init>(r2)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            boolean r2 = r13.mHasCorrectMyAccount     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            r3.append(r2)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            java.lang.String r2 = r3.toString()     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            android.util.Log.i(r0, r2)     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            android.content.pm.ApplicationInfo r2 = r4.applicationInfo     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            boolean r2 = r2.enabled     // Catch: java.lang.NumberFormatException -> L7f android.content.pm.PackageManager.NameNotFoundException -> L81
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.NumberFormatException -> L83 android.content.pm.PackageManager.NameNotFoundException -> L8b
            r3.<init>(r1)     // Catch: java.lang.NumberFormatException -> L83 android.content.pm.PackageManager.NameNotFoundException -> L8b
            r3.append(r2)     // Catch: java.lang.NumberFormatException -> L83 android.content.pm.PackageManager.NameNotFoundException -> L8b
            java.lang.String r1 = r3.toString()     // Catch: java.lang.NumberFormatException -> L83 android.content.pm.PackageManager.NameNotFoundException -> L8b
            android.util.Log.i(r0, r1)     // Catch: java.lang.NumberFormatException -> L83 android.content.pm.PackageManager.NameNotFoundException -> L8b
            goto L92
        L7f:
            r2 = r5
            goto L83
        L81:
            r2 = r5
            goto L8b
        L83:
            r13.mHasCorrectMyAccount = r5
            java.lang.String r1 = "NumberFormatException : MyAccount version"
            android.util.Log.i(r0, r1)
            goto L92
        L8b:
            r13.mHasCorrectMyAccount = r5
            java.lang.String r1 = "NameNotFoundException : MyAccount"
            android.util.Log.i(r0, r1)
        L92:
            if (r7 != 0) goto L98
            boolean r13 = r13.mHasCorrectMyAccount
            if (r13 == 0) goto L9a
        L98:
            if (r2 != 0) goto L9b
        L9a:
            return r8
        L9b:
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.general.CollectDiagnosticsPreferenceController.getAvailabilityStatus():int");
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        Log.i("CollectDiagnosticsPreferenceController", "Collect Diagnostics Preference triggered");
        Intent intent = new Intent("com.tmobile.device.settings", (Uri) null);
        intent.setComponent(
                this.mHasCorrectMyAccount
                        ? new ComponentName(
                                "com.tmobile.echolocate", "com.tmobile.echolocate.ui.OOBEActivity")
                        : new ComponentName(
                                "com.carrieriq.tmobile.IQToggle",
                                "com.carrieriq.tmobile.IQToggle.ui"));
        try {
            this.mContext.startActivity(intent);
            return false;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
