package com.samsung.android.settings.wifi;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiSetupWizardDialogBuilder {
    public final Activity mActivity;
    public boolean mFlagWarningDialog = false;
    public final boolean mIsNetworkRequiredByFrp;
    public final boolean mIsNetworkRequiredByKme;

    public WifiSetupWizardDialogBuilder(Activity activity) {
        this.mActivity = activity;
        activity.getIntent().getBooleanExtra("is_network_required_by_kme", false);
        this.mIsNetworkRequiredByFrp =
                activity.getIntent().getBooleanExtra("is_network_required", false);
    }

    public final AlertDialog.Builder makeCommonAlertDialogBuilder(boolean z, boolean z2) {
        Activity activity = this.mActivity;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.wifi_skip_title);
        builder.P.mMessage =
                activity.getString(R.string.wifi_and_mobile_skipped_message_header)
                        + "\n\n- "
                        + activity.getString(
                                R.string.wifi_and_mobile_skipped_message_connect_internet)
                        + "\n- "
                        + activity.getString(
                                R.string.wifi_and_mobile_skipped_message_software_update)
                        + "\n- "
                        + activity.getString(
                                R.string.wifi_and_mobile_skipped_message_devices_protection);
        int i = z2 ? R.string.wifi_continue_button : R.string.wifi_skip_button;
        if (z) {
            builder.setPositiveButton(
                    i,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.WifiSetupWizardDialogBuilder$$ExternalSyntheticLambda1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            WifiSetupWizardDialogBuilder wifiSetupWizardDialogBuilder =
                                    WifiSetupWizardDialogBuilder.this;
                            wifiSetupWizardDialogBuilder.getClass();
                            Log.d("WifiSetupWizard", "Wi-Fi setup skipped");
                            ComponentCallbacks2 componentCallbacks2 =
                                    wifiSetupWizardDialogBuilder.mActivity;
                            if (componentCallbacks2 instanceof IWifiSetupWizardButtonAction) {
                                ((WifiSetupWizardActivity)
                                                ((IWifiSetupWizardButtonAction)
                                                        componentCallbacks2))
                                        .mainAction(true);
                            }
                        }
                    });
        }
        builder.setNegativeButton(
                R.string.wifi_dont_skip,
                new WifiSetupWizardDialogBuilder$$ExternalSyntheticLambda0(1));
        return builder;
    }
}
