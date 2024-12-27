package com.android.settings.network;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.system.ResetDashboardFragment;
import com.android.settingslib.core.lifecycle.ObservableDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EuiccRacConnectivityDialogFragment extends ObservableDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        if (getTargetFragment() instanceof ResetDashboardFragment) {
            this.mMetricsFeatureProvider.action(getActivity(), 1917, 0);
        } else {
            Log.e("EuiccRacConnectivityDlg", "getTargetFragment return unexpected type");
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Fragment targetFragment = getTargetFragment();
        if (!(targetFragment instanceof ResetDashboardFragment)) {
            Log.e("EuiccRacConnectivityDlg", "getTargetFragment return unexpected type");
            return;
        }
        if (i != -2) {
            this.mMetricsFeatureProvider.action(getActivity(), 1917, 0);
            return;
        }
        this.mMetricsFeatureProvider.action(getActivity(), 1917, 1);
        ResetDashboardFragment resetDashboardFragment = (ResetDashboardFragment) targetFragment;
        if (resetDashboardFragment.getActivity() == null) {
            return;
        }
        EraseEuiccDataDialogFragment eraseEuiccDataDialogFragment =
                new EraseEuiccDataDialogFragment();
        eraseEuiccDataDialogFragment.setTargetFragment(resetDashboardFragment, 0);
        eraseEuiccDataDialogFragment.show(
                resetDashboardFragment.getActivity().getSupportFragmentManager(),
                "EraseEuiccDataDlg");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        String string = getString(R.string.wifi_warning_dialog_title);
        String string2 = getString(R.string.wifi_warning_dialog_text);
        AlertDialog.Builder negativeButton =
                new AlertDialog.Builder(getContext())
                        .setOnDismissListener(this)
                        .setPositiveButton(R.string.wifi_warning_return_button, this)
                        .setNegativeButton(R.string.wifi_warning_continue_button, this);
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
            negativeButton.setView(inflate);
        } else {
            if (!TextUtils.isEmpty(string)) {
                negativeButton.setTitle(string);
            }
            if (!TextUtils.isEmpty(string2)) {
                negativeButton.setMessage(string2);
            }
        }
        AlertDialog create = negativeButton.create();
        create.setCanceledOnTouchOutside(false);
        return create;
    }
}
