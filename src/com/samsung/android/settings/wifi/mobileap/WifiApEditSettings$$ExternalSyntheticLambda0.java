package com.samsung.android.settings.wifi.mobileap;

import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureBroadcastChannelDropDownController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigurePassWordPreference;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigurePmfController;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSSIDPreference;
import com.samsung.android.settings.wifi.mobileap.hotspotlabs.WifiApHotspotLabsController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiApEditSettings$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiApEditSettings f$0;

    public /* synthetic */ WifiApEditSettings$$ExternalSyntheticLambda0(
            WifiApEditSettings wifiApEditSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiApEditSettings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiApEditSettings wifiApEditSettings = this.f$0;
        switch (i) {
            case 0:
                WifiApConfigurePassWordPreference wifiApConfigurePassWordPreference =
                        wifiApEditSettings.mPassWordPreference;
                wifiApConfigurePassWordPreference.updateState(
                        wifiApConfigurePassWordPreference.mWifiApConfigureSettings
                                .getSecurityType());
                WifiApConfigureSSIDPreference wifiApConfigureSSIDPreference =
                        wifiApEditSettings.mSSIDPreference;
                wifiApConfigureSSIDPreference.updateState(
                        wifiApConfigureSSIDPreference.mWifiApConfigureSettings.getSecurityType());
                wifiApEditSettings.updateSaveButtonEnabling();
                break;
            case 1:
                wifiApEditSettings.mSupportWifi6Controller.updateState();
                break;
            case 2:
                WifiApConfigurePmfController wifiApConfigurePmfController =
                        wifiApEditSettings.mPmfController;
                wifiApConfigurePmfController.updateState(
                        wifiApEditSettings.mPreferenceScreen.findPreference(
                                wifiApConfigurePmfController.getPreferenceKey()));
                break;
            case 3:
                WifiApConfigureBroadcastChannelDropDownController
                        wifiApConfigureBroadcastChannelDropDownController =
                                wifiApEditSettings.mBroadcastChannelDropDownController;
                wifiApConfigureBroadcastChannelDropDownController.updateState(
                        wifiApEditSettings.mPreferenceScreen.findPreference(
                                wifiApConfigureBroadcastChannelDropDownController
                                        .getPreferenceKey()));
                break;
            case 4:
                wifiApEditSettings.mWifiSharingController.updateState();
                break;
            case 5:
                wifiApEditSettings.mSecurityDropDownController.updateState();
                wifiApEditSettings.updateSaveButtonEnabling();
                break;
            default:
                WifiApHotspotLabsController wifiApHotspotLabsController =
                        wifiApEditSettings.mWifiApHotspotLabsController;
                wifiApHotspotLabsController.updateState(
                        wifiApEditSettings.mPreferenceScreen.findPreference(
                                wifiApHotspotLabsController.getPreferenceKey()));
                break;
        }
    }
}
