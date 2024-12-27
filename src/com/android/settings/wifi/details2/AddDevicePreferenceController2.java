package com.android.settings.wifi.details2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.wifi.dpp.WifiDppConfiguratorActivity;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.settings.ImsProfile;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AddDevicePreferenceController2 extends BasePreferenceController {
    private static final String KEY_ADD_DEVICE = "add_device_to_network";
    private static final String TAG = "AddDevicePreferenceController2";
    private WifiEntry mWifiEntry;
    private WifiManager mWifiManager;

    public AddDevicePreferenceController2(Context context) {
        super(context, KEY_ADD_DEVICE);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: launchWifiDppConfiguratorQrCodeScanner, reason: merged with bridge method [inline-methods] */
    public void lambda$handlePreferenceTreeClick$0() {
        Context context = this.mContext;
        WifiManager wifiManager = this.mWifiManager;
        WifiEntry wifiEntry = this.mWifiEntry;
        Duration duration = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
        Intent intent = new Intent(context, (Class<?>) WifiDppConfiguratorActivity.class);
        if (wifiEntry.canEasyConnect()) {
            intent.setAction("android.settings.WIFI_DPP_CONFIGURATOR_QR_CODE_SCANNER");
            WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
            WifiDppUtils.setConfiguratorIntentExtra(intent, wifiManager, wifiConfiguration);
            int i = wifiConfiguration.networkId;
            if (i == -1) {
                throw new IllegalArgumentException("Invalid network ID");
            }
            intent.putExtra("networkId", i);
        } else {
            intent = null;
        }
        if (intent == null) {
            Log.e(TAG, "Launch Wi-Fi QR code scanner with a wrong Wi-Fi network!");
        } else {
            this.mContext.startActivity(intent);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mWifiEntry.canEasyConnect() ? 0 : 2;
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
        if (!KEY_ADD_DEVICE.equals(preference.getKey())) {
            return false;
        }
        WifiDppUtils.showLockScreen(
                this.mContext,
                new Runnable() { // from class:
                                 // com.android.settings.wifi.details2.AddDevicePreferenceController2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AddDevicePreferenceController2.this.lambda$handlePreferenceTreeClick$0();
                    }
                });
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

    public void setWifiEntry(WifiEntry wifiEntry) {
        this.mWifiEntry = wifiEntry;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
