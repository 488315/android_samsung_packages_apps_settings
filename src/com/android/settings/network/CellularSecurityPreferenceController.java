package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.safetycenter.SafetyCenterManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.network.telephony.CellularSecuritySettingsFragment;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CellularSecurityPreferenceController extends BasePreferenceController {
    private static final String LOG_TAG = "CellularSecurityPreferenceController";
    private TelephonyManager mTelephonyManager;

    public CellularSecurityPreferenceController(Context context, String str) {
        super(context, str);
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
    }

    public boolean areNotificationsEnabled() {
        if (this.mTelephonyManager == null) {
            Log.w(LOG_TAG, "Telephony manager not yet initialized");
            this.mTelephonyManager =
                    (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        }
        return this.mTelephonyManager.isNullCipherNotificationsEnabled()
                && this.mTelephonyManager.isCellularIdentifierDisclosureNotificationsEnabled();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0071 A[ADDED_TO_REGION] */
    @Override // com.android.settings.core.BasePreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus() {
        /*
            r7 = this;
            android.content.Context r0 = r7.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            java.lang.String r1 = "android.hardware.telephony"
            boolean r0 = r0.hasSystemFeature(r1)
            r1 = 3
            if (r0 == 0) goto L76
            android.telephony.TelephonyManager r0 = r7.mTelephonyManager
            java.lang.String r2 = "CellularSecurityPreferenceController"
            if (r0 != 0) goto L26
            java.lang.String r0 = "Telephony manager not yet initialized"
            android.util.Log.w(r2, r0)
            android.content.Context r0 = r7.mContext
            java.lang.Class<android.telephony.TelephonyManager> r3 = android.telephony.TelephonyManager.class
            java.lang.Object r0 = r0.getSystemService(r3)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            r7.mTelephonyManager = r0
        L26:
            r0 = 1
            r3 = 0
            android.telephony.TelephonyManager r4 = r7.mTelephonyManager     // Catch: java.lang.Exception -> L2f java.lang.UnsupportedOperationException -> L31
            r4.isNullCipherAndIntegrityPreferenceEnabled()     // Catch: java.lang.Exception -> L2f java.lang.UnsupportedOperationException -> L31
            r4 = r0
            goto L54
        L2f:
            r4 = move-exception
            goto L33
        L31:
            r4 = move-exception
            goto L3e
        L33:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Failed isNullCipherAndIntegrityEnabled. Setting availability to CONDITIONALLY_UNAVAILABLE. Exception: "
            r5.<init>(r6)
            com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0.m(r4, r5, r2)
            goto L53
        L3e:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Null cipher enablement is unsupported, hiding divider: "
            r5.<init>(r6)
            java.lang.String r4 = r4.getMessage()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            android.util.Log.i(r2, r4)
        L53:
            r4 = r3
        L54:
            r7.areNotificationsEnabled()     // Catch: java.lang.UnsupportedOperationException -> L58
            goto L6f
        L58:
            r7 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r5 = "Cellular security notifications are unsupported, hiding divider: "
            r0.<init>(r5)
            java.lang.String r7 = r7.getMessage()
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            android.util.Log.i(r2, r7)
            r0 = r3
        L6f:
            if (r4 != 0) goto L75
            if (r0 == 0) goto L74
            goto L75
        L74:
            return r1
        L75:
            return r3
        L76:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.CellularSecurityPreferenceController.getAvailabilityStatus():int");
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
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        if (isSafetyCenterSupported()) {
            Intent intent = new Intent("android.intent.action.SAFETY_CENTER");
            intent.putExtra(
                    "android.safetycenter.extra.SAFETY_SOURCES_GROUP_ID",
                    "AndroidCellularNetworkSecuritySources");
            this.mContext.startActivity(intent);
            return true;
        }
        Bundle m =
                AbsAdapter$1$$ExternalSyntheticOutline0.m(
                        "cellular_security", ApnSettings.MVNO_NONE);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = CellularSecuritySettingsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = m;
        launchRequest.mSourceMetricsCategory = 2075;
        subSettingLauncher.launch();
        return true;
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

    public boolean isSafetyCenterSupported() {
        SafetyCenterManager safetyCenterManager =
                (SafetyCenterManager) this.mContext.getSystemService(SafetyCenterManager.class);
        if (safetyCenterManager == null) {
            return false;
        }
        return safetyCenterManager.isSafetyCenterEnabled();
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
