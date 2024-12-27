package com.android.settings.wifi;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkRequestSingleSsidDialogFragment extends NetworkRequestDialogBaseFragment {
    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        boolean z;
        Bundle arguments = getArguments();
        String str = ApnSettings.MVNO_NONE;
        if (arguments != null) {
            z = getArguments().getBoolean("DIALOG_IS_TRYAGAIN", true);
            str = getArguments().getString("DIALOG_REQUEST_SSID", ApnSettings.MVNO_NONE);
        } else {
            z = false;
        }
        Context context = getContext();
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.network_request_dialog_title, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.network_request_title_text))
                .setText(getString(R.string.network_connection_request_dialog_title));
        ((TextView) inflate.findViewById(R.id.network_request_summary_text))
                .setText(
                        getString(
                                R.string.network_connection_request_dialog_summary, this.mAppName));
        ((ProgressBar) inflate.findViewById(R.id.network_request_title_progress)).setVisibility(8);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mCustomTitleView = inflate;
        alertParams.mMessage = str;
        final int i = 0;
        builder.setPositiveButton(
                z ? R.string.network_connection_timeout_dialog_ok : R.string.wifi_connect,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.android.settings.wifi.NetworkRequestSingleSsidDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ NetworkRequestSingleSsidDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        WifiManager.NetworkRequestUserSelectionCallback
                                networkRequestUserSelectionCallback;
                        int i3 = i;
                        NetworkRequestSingleSsidDialogFragment
                                networkRequestSingleSsidDialogFragment = this.f$0;
                        switch (i3) {
                            case 0:
                                NetworkRequestDialogActivity networkRequestDialogActivity =
                                        networkRequestSingleSsidDialogFragment.mActivity;
                                if (networkRequestDialogActivity != null
                                        && (networkRequestUserSelectionCallback =
                                                        networkRequestDialogActivity
                                                                .mUserSelectionCallback)
                                                != null) {
                                    networkRequestUserSelectionCallback.select(
                                            networkRequestDialogActivity.mMatchedConfig);
                                    networkRequestDialogActivity.showProgressDialog$1(
                                            networkRequestDialogActivity.getString(
                                                    R.string
                                                            .network_connection_connecting_message));
                                    break;
                                }
                                break;
                            default:
                                networkRequestSingleSsidDialogFragment.onCancel(dialogInterface);
                                break;
                        }
                    }
                });
        final int i2 = 1;
        builder.setNeutralButton(
                R.string.cancel,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.android.settings.wifi.NetworkRequestSingleSsidDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ NetworkRequestSingleSsidDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        WifiManager.NetworkRequestUserSelectionCallback
                                networkRequestUserSelectionCallback;
                        int i3 = i2;
                        NetworkRequestSingleSsidDialogFragment
                                networkRequestSingleSsidDialogFragment = this.f$0;
                        switch (i3) {
                            case 0:
                                NetworkRequestDialogActivity networkRequestDialogActivity =
                                        networkRequestSingleSsidDialogFragment.mActivity;
                                if (networkRequestDialogActivity != null
                                        && (networkRequestUserSelectionCallback =
                                                        networkRequestDialogActivity
                                                                .mUserSelectionCallback)
                                                != null) {
                                    networkRequestUserSelectionCallback.select(
                                            networkRequestDialogActivity.mMatchedConfig);
                                    networkRequestDialogActivity.showProgressDialog$1(
                                            networkRequestDialogActivity.getString(
                                                    R.string
                                                            .network_connection_connecting_message));
                                    break;
                                }
                                break;
                            default:
                                networkRequestSingleSsidDialogFragment.onCancel(dialogInterface);
                                break;
                        }
                    }
                });
        setCancelable(false);
        return builder.create();
    }
}
