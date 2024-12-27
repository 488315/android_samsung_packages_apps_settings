package com.samsung.android.settings.security;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$EventBuilder;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecureWifiPreferenceController extends BasePreferenceController {
    private static final String ACTION_SECURE_WIFI = "com.samsung.android.fast.ACTION_SECURE_WIFI";
    public static final String KEY_IS_FROM_MAIN_MENU = "isFromMainMenu";
    public static final String KEY_IS_FROM_RELATIVE = "isFromRelative";
    private static final String KEY_SECURE_WIFI = "secure_wifi";
    private static final String SECURE_WIFI_PACKAGE = "com.samsung.android.fast";
    private SecureWifiManager mSecureWifiManager;

    public SecureWifiPreferenceController(Context context) {
        this(context, KEY_SECURE_WIFI);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Utils.isSupportSecureWiFi(this.mContext) ? 0 : 3;
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
        if (!preference.getKey().equals(getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        SecureWifiManager secureWifiManager = this.mSecureWifiManager;
        if (secureWifiManager != null) {
            secureWifiManager.getClass();
            SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
            LogBuilders$EventBuilder logBuilders$EventBuilder =
                    (LogBuilders$EventBuilder)
                            new LogBuilders$EventBuilder().setScreenView("SWIFI001");
            logBuilders$EventBuilder.setEventName("0010");
            samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
        }
        Intent intent = new Intent(ACTION_SECURE_WIFI);
        intent.setPackage(SECURE_WIFI_PACKAGE);
        intent.putExtra(KEY_IS_FROM_MAIN_MENU, true);
        try {
            ((Activity) this.mContext).startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
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

    public SecureWifiPreferenceController(Context context, String str) {
        super(context, str);
        this.mSecureWifiManager = new SecureWifiManager();
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
