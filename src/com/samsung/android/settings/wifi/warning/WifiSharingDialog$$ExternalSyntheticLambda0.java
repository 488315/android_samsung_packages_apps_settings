package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiSharingDialog$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiSharingDialog f$0;

    public /* synthetic */ WifiSharingDialog$$ExternalSyntheticLambda0(
            WifiSharingDialog wifiSharingDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiSharingDialog;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        WifiSharingDialog wifiSharingDialog = this.f$0;
        switch (i2) {
            case 0:
                wifiSharingDialog.dismissApDisableDialog();
                if (wifiSharingDialog.mWifiManager != null) {
                    Settings.System.putInt(
                            wifiSharingDialog.mContext.getContentResolver(),
                            "wifi_sharing_lite_popup_status",
                            1);
                    Settings.Secure.putInt(
                            wifiSharingDialog.mContext.getContentResolver(),
                            "wifiap_wifi_tile_clicked",
                            1);
                    wifiSharingDialog.mWifiManager.setWifiEnabled(true);
                }
                wifiSharingDialog.mActivity.finish();
                break;
            default:
                wifiSharingDialog.dismissApDisableDialog();
                Log.d("WifiWarningControlDialog", "startActivity for WifiSharingSettings");
                SubSettingLauncher subSettingLauncher =
                        new SubSettingLauncher(wifiSharingDialog.mContext);
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mSourceMetricsCategory = 3400;
                launchRequest.mDestinationName = WifiApEditSettings.class.getCanonicalName();
                subSettingLauncher.addFlags(268435456);
                subSettingLauncher.setTitleRes(
                        WifiApUtils.getStringID(R.string.wifi_ap_menu_configure_hotspot), null);
                subSettingLauncher.launch();
                wifiSharingDialog.mActivity.finish();
                break;
        }
    }
}
