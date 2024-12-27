package com.samsung.android.settings.connection.moreconnection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EmergencyAlertsPreferenceController extends BasePreferenceController {
    public static final String AOSP_CMAS_MODULE_PACKAGE =
            "com.android.cellbroadcastreceiver.module";
    public static final String AOSP_CMAS_PACKAGE = "com.android.cellbroadcastreceiver";
    public static final String AOSP_GOOGLE_CMAS_PACKAGE =
            "com.google.android.cellbroadcastreceiver";
    public static final String CLASS_CELL_BROADCAST_SETTINGS =
            "com.android.cellbroadcastreceiver.CellBroadcastSettings";
    private static final String TAG = "EmergencyAlertsPreferenceController";
    private final String mPrefKey;

    public EmergencyAlertsPreferenceController(Context context, String str) {
        super(context, str);
        this.mPrefKey = str;
    }

    public static boolean isEnabledPackage(Context context, String str) {
        try {
            int applicationEnabledSetting =
                    context.getPackageManager().getApplicationEnabledSetting(str);
            if (applicationEnabledSetting == 2 || applicationEnabledSetting == 3) {
                Log.d(TAG, str + " is disabled");
                return false;
            }
            Log.d(TAG, str + " is enabled");
            return true;
        } catch (IllegalArgumentException unused) {
            Log.d(TAG, str + " is not installed");
            return false;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Context context = this.mContext;
        int i = ConnectionsUtils.$r8$clinit;
        if (!SemCscFeature.getInstance()
                .getBoolean("CscFeature_Setting_SupportEmergencyAlertOption")) {
            return 2;
        }
        try {
            context.getPackageManager().getPackageInfo(AOSP_GOOGLE_CMAS_PACKAGE, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            try {
                context.getPackageManager().getPackageInfo(AOSP_CMAS_PACKAGE, 0);
            } catch (PackageManager.NameNotFoundException unused2) {
                try {
                    context.getPackageManager().getPackageInfo(AOSP_CMAS_MODULE_PACKAGE, 0);
                } catch (PackageManager.NameNotFoundException unused3) {
                    return 2;
                }
            }
        }
        return 0;
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
        if (this.mPrefKey.equals(preference.getKey())) {
            Intent intent = new Intent();
            if (isEnabledPackage(this.mContext, AOSP_GOOGLE_CMAS_PACKAGE)) {
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                        AOSP_GOOGLE_CMAS_PACKAGE, CLASS_CELL_BROADCAST_SETTINGS, intent);
            } else if (isEnabledPackage(this.mContext, AOSP_CMAS_PACKAGE)) {
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                        AOSP_CMAS_PACKAGE, CLASS_CELL_BROADCAST_SETTINGS, intent);
            } else if (isEnabledPackage(this.mContext, AOSP_CMAS_MODULE_PACKAGE)) {
                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                        AOSP_CMAS_MODULE_PACKAGE, CLASS_CELL_BROADCAST_SETTINGS, intent);
            } else {
                Log.d(TAG, "Activity not found");
            }
            this.mContext.startActivity(intent);
            return true;
        }
        return false;
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
