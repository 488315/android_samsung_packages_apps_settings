package com.samsung.android.settings.wifi.intelligent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MobileWipsAlertDialogFragment extends InstrumentedDialogFragment {
    public AlertDialog mDialog = null;
    public final AnonymousClass1 mAlertDialogReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.intelligent.MobileWipsAlertDialogFragment.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (MobileWipsAlertDialogFragment.this.mDialog == null
                            || !"com.samsung.android.settings.wifi.intelligent.MOBILE_WIPS_ALERT_DIALOG_FINISH"
                                    .equals(intent.getAction())) {
                        return;
                    }
                    Log.d("MobileWipsAlertDialogFragment", "onReceive: Close alert dialog");
                    MobileWipsAlertDialogFragment mobileWipsAlertDialogFragment =
                            MobileWipsAlertDialogFragment.this;
                    mobileWipsAlertDialogFragment.onDismiss(mobileWipsAlertDialogFragment.mDialog);
                }
            };

    public final void closeDialogWithUserResponse(int i) {
        FragmentActivity activity = getActivity();
        Intent intent =
                new Intent(
                        "com.samsung.android.settings.wifi.intelligent.MOBILE_WIPS_ALERT_DIALOG_FINISHED");
        intent.putExtra("type", getActivity().getIntent().getIntExtra("type", -1));
        intent.putExtra("bssid", getActivity().getIntent().getStringExtra("bssid"));
        intent.putExtra("ssid", getActivity().getIntent().getStringExtra("ssid"));
        intent.putExtra("userResponse", i);
        activity.sendBroadcast(intent, "com.samsung.permission.WIFI_WIPS");
        getActivity().finish();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 103;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        Log.d("MobileWipsAlertDialogFragment", "onCancel: Dialog cancel");
        closeDialogWithUserResponse(3);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        final String stringExtra = getActivity().getIntent().getStringExtra("ssid");
        AlertDialog create =
                new AlertDialog.Builder(getActivity())
                        .setTitle(
                                getActivity()
                                        .getResources()
                                        .getString(R.string.wifi_mobile_wips_detection_popup_title))
                        .setMessage(
                                getActivity()
                                        .getResources()
                                        .getString(
                                                R.string.wifi_mobile_wips_detection_popup_content,
                                                stringExtra))
                        .setPositiveButton(
                                getActivity()
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .wifi_mobile_wips_detection_popup_add_to_exceptions),
                                new DialogInterface.OnClickListener() { // from class:
                                    // com.samsung.android.settings.wifi.intelligent.MobileWipsAlertDialogFragment$$ExternalSyntheticLambda0
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        MobileWipsAlertDialogFragment
                                                mobileWipsAlertDialogFragment =
                                                        MobileWipsAlertDialogFragment.this;
                                        mobileWipsAlertDialogFragment.getClass();
                                        Log.d(
                                                "MobileWipsAlertDialogFragment",
                                                "onClick(PositiveButton): Add to exceptions");
                                        mobileWipsAlertDialogFragment.closeDialogWithUserResponse(
                                                0);
                                    }
                                })
                        .setNegativeButton(
                                getActivity()
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .wifi_mobile_wips_detection_popup_disconnect),
                                new DialogInterface.OnClickListener() { // from class:
                                    // com.samsung.android.settings.wifi.intelligent.MobileWipsAlertDialogFragment$$ExternalSyntheticLambda1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        MobileWipsAlertDialogFragment
                                                mobileWipsAlertDialogFragment =
                                                        MobileWipsAlertDialogFragment.this;
                                        String str = stringExtra;
                                        mobileWipsAlertDialogFragment.getClass();
                                        Log.d(
                                                "MobileWipsAlertDialogFragment",
                                                "onClick(NegativeButton): Disconnect");
                                        WifiManager wifiManager =
                                                (WifiManager)
                                                        mobileWipsAlertDialogFragment
                                                                .getActivity()
                                                                .getSystemService(
                                                                        ImsProfile.PDN_WIFI);
                                        if (str != null) {
                                            Log.d(
                                                    "MobileWipsAlertDialogFragment",
                                                    "disableEphemeralNetwork( " + str + " )");
                                            wifiManager.disableEphemeralNetwork(str);
                                        } else {
                                            Log.e(
                                                    "MobileWipsAlertDialogFragment",
                                                    "wifiInfo is null or wifiInfo.getSSID() is"
                                                        + " null");
                                        }
                                        FragmentActivity activity =
                                                mobileWipsAlertDialogFragment.getActivity();
                                        ((SemWifiManager)
                                                        activity.getSystemService(
                                                                WiFiManagerExt.SEM_WIFI_SERVICE))
                                                .reportIssue(
                                                        100,
                                                        WifiIssueDetectorUtil.ReportUtil
                                                                .getReportDataForWifiManagerApi(
                                                                        -1,
                                                                        "disconnect",
                                                                        activity.getPackageManager()
                                                                                .getNameForUid(
                                                                                        activity
                                                                                                .getUserId()),
                                                                        activity.getPackageName()));
                                        wifiManager.disconnect();
                                        mobileWipsAlertDialogFragment.closeDialogWithUserResponse(
                                                1);
                                    }
                                })
                        .create();
        this.mDialog = create;
        create.getWindow().setType(2008);
        this.mDialog.getWindow().setGravity(80);
        return this.mDialog;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        Log.d("MobileWipsAlertDialogFragment", "onDismiss: Dialog dismissed");
        closeDialogWithUserResponse(2);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        Log.d("MobileWipsAlertDialogFragment", "onStart: Alert dialog start");
        getActivity()
                .getApplicationContext()
                .registerReceiver(
                        this.mAlertDialogReceiver,
                        new IntentFilter(
                                "com.samsung.android.settings.wifi.intelligent.MOBILE_WIPS_ALERT_DIALOG_FINISH"),
                        "com.samsung.permission.WIFI_WIPS",
                        null,
                        2);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        Log.d("MobileWipsAlertDialogFragment", "onStop: Alert dialog stop");
        getActivity().getApplicationContext().unregisterReceiver(this.mAlertDialogReceiver);
    }
}
