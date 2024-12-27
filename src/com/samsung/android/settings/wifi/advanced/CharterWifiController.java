package com.samsung.android.settings.wifi.advanced;

import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Debug;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.IMSParameter;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class CharterWifiController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnDestroy, OnPause {
    private static final String CHARTER_SALES_CODE = "CHA";
    private static final String CHA_GID1 = "BA01490000000000";
    private static boolean DBG = Debug.semIsProductDev();
    private static final String KEY_CHARTER_WIFI_NETWORKS = "optimize_charter_wifi_networks";
    private static final String MODEL_NAME;
    private static final String PROVIDER_ACTION_START =
            "content://com.spectrum.cm.ServiceProvider/start_service";
    private static final String PROVIDER_ACTION_STATUS =
            "content://com.spectrum.cm.ServiceProvider/query_service";
    private static final String PROVIDER_ACTION_STOP =
            "content://com.spectrum.cm.ServiceProvider/stop_service";
    private static final String PROVIDER_BASE_URL = "content://com.spectrum.cm.ServiceProvider";
    private static final int QUERY_TOKEN = 0;
    private static final String SIM_STATE_ABSENT = "ABSENT";
    private static final String SIM_STATE_ACTION =
            "android.telephony.action.SIM_CARD_STATE_CHANGED";
    private static final String SIM_STATE_KEY = "ss";
    private static final String TAG = "CharterWifiController";
    private static boolean mIsCharterWifiNotSupported;
    private SecSwitchPreference mCharterSwitch;
    private Context mContext;
    private final IntentFilter mFilter;
    private boolean mIsAvailable;
    private AsyncQueryHandler mQueryHandler;
    private final BroadcastReceiver mReceiver;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class CHA_RESULT {
        public static final /* synthetic */ CHA_RESULT[] $VALUES = {
            new CHA_RESULT("STOPPED", 0),
            new CHA_RESULT("STARTED", 1),
            new CHA_RESULT("SERVICE_ERROR", 2)
        };

        /* JADX INFO: Fake field, exist only in values array */
        CHA_RESULT EF5;

        public static CHA_RESULT valueOf(String str) {
            return (CHA_RESULT) Enum.valueOf(CHA_RESULT.class, str);
        }

        public static CHA_RESULT[] values() {
            return (CHA_RESULT[]) $VALUES.clone();
        }
    }

    static {
        String str = SystemProperties.get("ro.product.model");
        MODEL_NAME = str;
        mIsCharterWifiNotSupported = str.contains("U1");
    }

    public CharterWifiController(Context context, String str) {
        super(context, str);
        this.mIsAvailable = true;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.advanced.CharterWifiController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        Log.d(CharterWifiController.TAG, "onReceive");
                        if (!CharterWifiController.CHARTER_SALES_CODE.equalsIgnoreCase(
                                        Utils.getSalesCode())
                                && !CharterWifiController.mIsCharterWifiNotSupported) {
                            Log.d(
                                    CharterWifiController.TAG,
                                    "Feature is not supported in other carriers...");
                        } else if (intent != null
                                && intent.getAction().equals(CharterWifiController.SIM_STATE_ACTION)
                                && CharterWifiController.this.isSimAbsent()) {
                            Log.d(
                                    CharterWifiController.TAG,
                                    "SIM ABSENT detected!!! Turn off Charter's setting..");
                            CharterWifiController.this.setCharterSetting(false);
                        }
                    }
                };
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction(SIM_STATE_ACTION);
        String salesCode = Utils.getSalesCode();
        Log.d(TAG, "salesCode : " + salesCode);
        Log.d(TAG, "mIsCharterWifiNotSupported  : " + mIsCharterWifiNotSupported);
        if (!CHARTER_SALES_CODE.equals(salesCode) || mIsCharterWifiNotSupported) {
            this.mIsAvailable = false;
        } else {
            this.mIsAvailable = true;
        }
    }

    private void callContentProvider(String str) {
        enableCharterSetting(false);
        Uri parse = Uri.parse(str);
        AsyncQueryHandler asyncQueryHandler =
                new AsyncQueryHandler(
                        this.mContext
                                .getContentResolver()) { // from class:
                                                         // com.samsung.android.settings.wifi.advanced.CharterWifiController.2
                    @Override // android.content.AsyncQueryHandler
                    public final void onQueryComplete(int i, Object obj, Cursor cursor) {
                        if (cursor == null || cursor.getCount() != 1) {
                            Log.d(
                                    CharterWifiController.TAG,
                                    "Error in starting connection Service!");
                            if (cursor != null) {
                                cursor.close();
                                return;
                            }
                            return;
                        }
                        cursor.moveToFirst();
                        int ordinal =
                                CHA_RESULT
                                        .values()[
                                        cursor.getInt(
                                                cursor.getColumnIndex(IMSParameter.CALL.STATUS))]
                                        .ordinal();
                        if (ordinal == 0) {
                            CharterWifiController.this.enableCharterSetting(true);
                            CharterWifiController.this.setCharterSetting(false);
                            Log.d(CharterWifiController.TAG, "Connection service is stopped...");
                        } else if (ordinal == 1) {
                            CharterWifiController.this.enableCharterSetting(true);
                            CharterWifiController.this.setCharterSetting(true);
                            Log.d(CharterWifiController.TAG, "Connection service is started...");
                        } else if (ordinal == 2) {
                            Log.d(CharterWifiController.TAG, "Error...");
                        }
                        cursor.close();
                    }
                };
        this.mQueryHandler = asyncQueryHandler;
        asyncQueryHandler.startQuery(0, null, parse, null, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableCharterSetting(boolean z) {
        if (this.mCharterSwitch != null) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "enableCharterSetting -----> Enabled : ", TAG, z);
            this.mCharterSwitch.setEnabled(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSimAbsent() {
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        return telephonyManager.getSimState() == 1
                || !CHA_GID1.equalsIgnoreCase(telephonyManager.getGroupIdLevel1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCharterSetting(boolean z) {
        if (this.mCharterSwitch != null) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "setCharterSetting -----> Checked : ", TAG, z);
            this.mCharterSwitch.setChecked(z);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mCharterSwitch =
                (SecSwitchPreference) preferenceScreen.findPreference(KEY_CHARTER_WIFI_NETWORKS);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsAvailable ? 0 : 3;
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
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
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
        SecSwitchPreference secSwitchPreference = this.mCharterSwitch;
        if (secSwitchPreference != null) {
            return secSwitchPreference.isChecked();
        }
        return false;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        AsyncQueryHandler asyncQueryHandler = this.mQueryHandler;
        if (asyncQueryHandler != null) {
            asyncQueryHandler.cancelOperation(0);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mCharterSwitch != null) {
            this.mContext.unregisterReceiver(this.mReceiver);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mCharterSwitch != null) {
            callContentProvider(PROVIDER_ACTION_STATUS);
            this.mContext.registerReceiver(this.mReceiver, this.mFilter);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("setChecked isChecked : ", TAG, z);
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_200", "1239");
        if (!z || !isSimAbsent()) {
            callContentProvider(z ? PROVIDER_ACTION_START : PROVIDER_ACTION_STOP);
            return true;
        }
        Context context = this.mContext;
        Toast.makeText(
                        context,
                        context.getResources().getString(R.string.optimize_charter_no_sim_message),
                        1)
                .show();
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
