package com.samsung.android.settings.wifi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.wifi.WifiSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiPickerDialogFragment extends InstrumentedDialogFragment {
    public WifiPickerDialog$$ExternalSyntheticLambda0 mListener;
    public ProgressBar mProgressBar;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 103;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateHeight$1$1();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder view =
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.wifi_access_points)
                        .setView(
                                getActivity()
                                        .getLayoutInflater()
                                        .inflate(
                                                R.layout.sec_wifi_picker_dialog, (ViewGroup) null));
        View inflate =
                getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.sec_bluetooth_custom_title_view, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.title_header)).setText(R.string.wifi_access_points);
        this.mProgressBar = (ProgressBar) inflate.findViewById(R.id.progress);
        AlertDialog create =
                view.setCustomTitle(inflate)
                        .setNegativeButton(
                                R.string.cancel,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.settings.wifi.WifiPickerDialogFragment$$ExternalSyntheticLambda0
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        WifiPickerDialogFragment.this.onCancel(dialogInterface);
                                    }
                                })
                        .create();
        create.getWindow()
                .setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.sec_bluetooth_scandialog_bg));
        return create;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        Log.d("WifiPickerDialog", "onDismiss");
        super.onDismiss(dialogInterface);
        WifiPickerDialog$$ExternalSyntheticLambda0 wifiPickerDialog$$ExternalSyntheticLambda0 =
                this.mListener;
        if (wifiPickerDialog$$ExternalSyntheticLambda0 != null) {
            wifiPickerDialog$$ExternalSyntheticLambda0.f$0.finish();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mProgressBar == null || getFragmentManager() == null) {
            return;
        }
        Fragment findFragmentById =
                getFragmentManager().findFragmentById(R.id.wifi_picker_dialog_fragment);
        if (findFragmentById instanceof WifiSettings) {
            ((WifiSettings) findFragmentById).mExteranlProgressBar = this.mProgressBar;
        }
        updateHeight$1$1();
    }

    public final void updateHeight$1$1() {
        Fragment findFragmentById =
                getFragmentManager().findFragmentById(R.id.wifi_picker_dialog_fragment);
        if (findFragmentById != null) {
            View view = findFragmentById.getView();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            view.setLayoutParams(new FrameLayout.LayoutParams(-1, displayMetrics.heightPixels / 2));
        }
    }
}
