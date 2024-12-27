package com.samsung.android.settings.wifi.intelligent;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.develop.WifiConnectivityLabsController;
import com.samsung.android.settings.wifi.develop.WifiDevelopmentSettingsEnabler;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class IntelligentWifiVersionPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume {
    private static final String KEY_INTELLIGENT_WIFI_VERSION = "intelligent_wifi_version";
    private static final String TAG = "IntelligentWifiVersionPreferenceController";
    static final int TAPS_TO_BE_A_DEVELOPER = 7;
    private static final int WIFI_DEVELOPER_ALREADY_ON = 3;
    private static final int WIFI_DEVELOPER_DEFAULT = 0;
    private static final int WIFI_DEVELOPER_ON = 2;
    private static final int WIFI_DEVELOPER_READY = 1;
    private int mDevHitCountdown;
    private Toast mDevHitToast;
    private boolean mIsActivated;
    private OnDeveloperOptionEnabledListener mListener;

    public IntelligentWifiVersionPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void enableWifiDevelopmentSettings() {
        Log.d(TAG, "enable Wi-Fi development settings");
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "sem_wifi_developer_option_visible", 1);
        IntelligentWifiSettings intelligentWifiSettings = (IntelligentWifiSettings) this.mListener;
        ((WifiConnectivityLabsController)
                        intelligentWifiSettings.use(WifiConnectivityLabsController.class))
                .displayPreference(intelligentWifiSettings.getPreferenceScreen());
        RecyclerView.Adapter adapter = intelligentWifiSettings.getListView().getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void showToast(int i) {
        if (i == 0) {
            return;
        }
        String string =
                i == 1
                        ? this.mContext
                                .getResources()
                                .getString(
                                        R.string.show_wifi_dev_countdown,
                                        Integer.valueOf(this.mDevHitCountdown))
                        : i == 2
                                ? this.mContext.getResources().getString(R.string.show_wifi_dev_on)
                                : i == 3
                                        ? this.mContext
                                                .getResources()
                                                .getString(R.string.show_wifi_dev_already)
                                        : ApnSettings.MVNO_NONE;
        Toast toast = this.mDevHitToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this.mContext, string, 1);
        this.mDevHitToast = makeText;
        makeText.show();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return "7.0.0";
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        int i = this.mDevHitCountdown - 1;
        this.mDevHitCountdown = i;
        int i2 = 3;
        if (!this.mIsActivated) {
            if (i > 0) {
                i2 = i <= 3 ? 1 : 0;
            } else if (i == 0) {
                enableWifiDevelopmentSettings();
                SALogging.insertSALog("WIFI_210", "2005");
                i2 = 2;
            }
        }
        showToast(i2);
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

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        Log.d(
                TAG,
                "onResume called. isWifiDevelopmentSettingsEnabled="
                        + WifiDevelopmentSettingsEnabler.isWifiDevelopmentSettingsEnabled(
                                this.mContext));
        this.mIsActivated =
                WifiDevelopmentSettingsEnabler.isWifiDevelopmentSettingsEnabled(this.mContext);
        this.mDevHitCountdown =
                WifiDevelopmentSettingsEnabler.isWifiDevelopmentSettingsEnabled(this.mContext)
                        ? -1
                        : 7;
        this.mDevHitToast = null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setOnDeveloperOptionEnabledListener(
            OnDeveloperOptionEnabledListener onDeveloperOptionEnabledListener) {
        this.mListener = onDeveloperOptionEnabledListener;
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
