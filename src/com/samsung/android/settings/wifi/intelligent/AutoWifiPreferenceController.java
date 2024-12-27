package com.samsung.android.settings.wifi.intelligent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.location.ILocationManager;
import android.net.wifi.WifiManager;
import android.os.Debug;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.gls.GlsIntent;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AutoWifiPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static boolean DBG = Debug.semIsProductDev();
    private static final String KEY_AUTO_WIFI = "auto_wifi";
    private static final String TAG = "ConfigureWifiSettings.AutoWifi";
    private SwitchPreferenceCompat mAutoWifi;
    private IntentFilter mFilter;
    private final BroadcastReceiver mReceiver;
    private final SemWifiManager mSemWifiManager;
    private final TelephonyManager mTelephonyManager;
    private final WifiManager mWifiManager;
    private boolean mWifiScanState;

    public AutoWifiPreferenceController(Context context, String str) {
        super(context, str);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.intelligent.AutoWifiPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        Log.v(AutoWifiPreferenceController.TAG, "Broadcast action : " + action);
                        if ("android.intent.action.AIRPLANE_MODE".equals(action)
                                || "android.intent.action.SIM_STATE_CHANGED".equals(action)
                                || "android.location.MODE_CHANGED".equals(action)
                                || "android.location.PROVIDERS_CHANGED".equals(action)) {
                            AutoWifiPreferenceController autoWifiPreferenceController =
                                    AutoWifiPreferenceController.this;
                            autoWifiPreferenceController.updateState(
                                    autoWifiPreferenceController.mAutoWifi);
                        } else if ("com.samsung.android.wifi.AUTO_WIFI_SCAN_STATE_CHANGED"
                                .equals(action)) {
                            AutoWifiPreferenceController.this.mWifiScanState =
                                    intent.getBooleanExtra("autoWifiScanAvailable", true);
                            AutoWifiPreferenceController autoWifiPreferenceController2 =
                                    AutoWifiPreferenceController.this;
                            autoWifiPreferenceController2.updateState(
                                    autoWifiPreferenceController2.mAutoWifi);
                        }
                    }
                };
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SwitchPreferenceCompat switchPreferenceCompat =
                (SwitchPreferenceCompat) preferenceScreen.findPreference(getPreferenceKey());
        this.mAutoWifi = switchPreferenceCompat;
        updateState(switchPreferenceCompat);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!this.mSemWifiManager.isSupportedAutoWifi() || Utils.isWifiOnly(this.mContext)) {
            return 3;
        }
        if (this.mSemWifiManager.isAvailableAutoWifiScan()) {
            this.mWifiScanState = true;
            return 0;
        }
        this.mWifiScanState = false;
        return 5;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_connections;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Resources resources = this.mContext.getResources();
        if (!SemWifiUtils.isSimCardReady(this.mTelephonyManager)) {
            return resources.getString(R.string.wifi_autowifi_insert_sim_card_to_use);
        }
        boolean z = false;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0)
                != 0) {
            return resources.getString(R.string.wifi_autowifi_turn_off_airplane_mode_to_use);
        }
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "location_mode", 0) != 3) {
            return resources.getString(R.string.wifi_autowifi_turn_on_location_to_use);
        }
        ILocationManager asInterface =
                ILocationManager.Stub.asInterface(
                        ServiceManager.getService(GlsIntent.Extras.EXTRA_LOCATION));
        int userId = UserHandle.getUserId(-2);
        if (asInterface != null) {
            try {
                z = asInterface.isProviderEnabledForUser("network", userId);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return !z
                ? resources.getString(R.string.wifi_autowifi_turn_on_location_provider)
                : !this.mWifiManager.isScanAlwaysAvailable()
                        ? resources.getString(R.string.wifi_autowifi_turn_on_improve_accuracy)
                        : resources.getString(R.string.wifi_autowifi_summary);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return SemWifiUtils.isAutoWifiEnabled(this.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.mFilter.addAction("android.intent.action.AIRPLANE_MODE");
        this.mFilter.addAction("android.location.MODE_CHANGED");
        this.mFilter.addAction("com.samsung.android.wifi.AUTO_WIFI_SCAN_STATE_CHANGED");
        this.mFilter.addAction("android.location.PROVIDERS_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, this.mFilter, 2);
        updateState(this.mAutoWifi);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "sem_auto_wifi_control_enabled", z ? 1 : 0);
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_200", "1215", (String) null);
        if (z) {
            return true;
        }
        Log.i(TAG, "Users turn off Auto Wi-Fi. Clear Data");
        Settings.Global.putString(
                this.mContext.getContentResolver(),
                "sem_auto_wifi_added_removed_list",
                ApnSettings.MVNO_NONE);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        Log.d(TAG, "AutoWifi updateState : " + Boolean.toString(this.mWifiScanState));
        boolean isAutoWifiEnabled = SemWifiUtils.isAutoWifiEnabled(this.mContext);
        preference.setDotVisibility(
                (WifiBadgeUtils.hasNewFavoriteNetwork(this.mContext) && isAutoWifiEnabled)
                        || (!isAutoWifiEnabled
                                && WifiBadgeUtils.isAbTestCurrentlyAvailable(this.mContext)
                                && WifiBadgeUtils.getABTestParam(this.mContext).equals("redDot")));
        preference.setEnabled(this.mWifiScanState);
        refreshSummary(preference);
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
