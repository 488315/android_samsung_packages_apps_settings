package com.android.settings.network.telephony;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WarningDialogFragment extends BaseDialogFragment
        implements DialogInterface.OnClickListener {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnConfirmListener {}

    public final void informCaller(boolean z) {
        try {
            OnConfirmListener onConfirmListener =
                    (OnConfirmListener) getListener(OnConfirmListener.class);
            if (onConfirmListener == null) {
                return;
            }
            int i = getArguments().getInt("in_caller_tag");
            EuiccRacConnectivityDialogActivity euiccRacConnectivityDialogActivity =
                    (EuiccRacConnectivityDialogActivity) onConfirmListener;
            if (i == 1915 || i == 1916) {
                euiccRacConnectivityDialogActivity.mMetricsFeatureProvider.action(
                        euiccRacConnectivityDialogActivity, i, z ? 1 : 0);
            }
            if (!z) {
                euiccRacConnectivityDialogActivity.finish();
                return;
            }
            euiccRacConnectivityDialogActivity.finish();
            if (i == 1915) {
                Log.i(
                        "EuiccRacConnectivityDialogActivity",
                        "Show dialogue activity that handles deleting eSIM profile");
                int i2 = euiccRacConnectivityDialogActivity.mSubId;
                int i3 = DeleteEuiccSubscriptionDialogActivity.$r8$clinit;
                Intent intent =
                        new Intent(
                                euiccRacConnectivityDialogActivity,
                                (Class<?>) DeleteEuiccSubscriptionDialogActivity.class);
                intent.putExtra("sub_id", i2);
                euiccRacConnectivityDialogActivity.startActivity(intent);
                return;
            }
            if (i != 1916) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(
                                i,
                                "Unrecognized confirmation dialog tag: ",
                                "EuiccRacConnectivityDialogActivity");
            } else if (euiccRacConnectivityDialogActivity.mResetMobileNetworkIntent != null) {
                Log.i(
                        "EuiccRacConnectivityDialogActivity",
                        "Show fragment activity that handles mobile network settings reset");
                new SubSettingLauncher(euiccRacConnectivityDialogActivity)
                        .launchWithIntent(
                                euiccRacConnectivityDialogActivity.mResetMobileNetworkIntent);
            }
        } catch (IllegalArgumentException e) {
            Log.e("WarningDialogFragment", "Do nothing and return.", e);
        }
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        informCaller(false);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "dialog onClick =", "WarningDialogFragment");
        informCaller(i == -2);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        String string = getArguments().getString(UniversalCredentialUtil.AGENT_TITLE);
        String string2 = getArguments().getString("msg");
        String string3 = getArguments().getString("pos_button_string");
        String string4 = getArguments().getString("neg_button_string");
        Log.i("WarningDialogFragment", "Showing dialog with title =" + string);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton(string4, this);
        builder.setNegativeButton(string3, this);
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(R.layout.sim_warning_dialog_wifi_connectivity, (ViewGroup) null);
        if (inflate != null) {
            TextView textView = (TextView) inflate.findViewById(R.id.title);
            if (!TextUtils.isEmpty(string) && textView != null) {
                textView.setText(string);
                textView.setVisibility(0);
            }
            TextView textView2 = (TextView) inflate.findViewById(R.id.msg);
            if (!TextUtils.isEmpty(string2) && textView2 != null) {
                textView2.setText(string2);
                textView2.setVisibility(0);
            }
            builder.setView(inflate);
        } else {
            boolean isEmpty = TextUtils.isEmpty(string);
            AlertController.AlertParams alertParams = builder.P;
            if (!isEmpty) {
                alertParams.mTitle = string;
            }
            if (!TextUtils.isEmpty(string2)) {
                alertParams.mMessage = string2;
            }
        }
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        return create;
    }
}
