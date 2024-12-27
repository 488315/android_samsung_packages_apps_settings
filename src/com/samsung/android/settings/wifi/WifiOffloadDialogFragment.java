package com.samsung.android.settings.wifi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.window.OnBackInvokedCallback;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiOffloadDialogFragment extends InstrumentedDialogFragment {
    public WifiOffloadDialog.AnonymousClass1 mListener;
    public View mView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 103;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        Log.d("WifiOffloadDialog", "onCancel ");
        WifiOffloadDialog.m1317$$Nest$mdoFinish(WifiOffloadDialog.this, false);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateHeight$1();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        ViewGroup viewGroup;
        AlertDialog.Builder title =
                new AlertDialog.Builder(getActivity()).setTitle(R.string.wifi_on_dialog_summary);
        View view = this.mView;
        if (view != null && (viewGroup = (ViewGroup) view.getParent()) != null) {
            viewGroup.removeView(this.mView);
        }
        try {
            this.mView =
                    getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.sec_wifi_offload, (ViewGroup) null);
        } catch (InflateException unused) {
            Log.d("WifiOffloadDialog", "InflateException : view already exists");
        }
        final int i = 1;
        AlertDialog.Builder positiveButton =
                title.setView(this.mView)
                        .setPositiveButton(
                                R.string.do_not_show_again,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.wifi.WifiOffloadDialogFragment.1
                                    public final /* synthetic */ WifiOffloadDialogFragment this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i2) {
                                        switch (i) {
                                            case 0:
                                                WifiOffloadDialog.m1317$$Nest$mdoFinish(
                                                        WifiOffloadDialog.this, true);
                                                break;
                                            default:
                                                WifiOffloadDialog wifiOffloadDialog =
                                                        WifiOffloadDialog.this;
                                                Settings.System.putInt(
                                                        wifiOffloadDialog.getContentResolver(),
                                                        "wifi_offload_network_notify",
                                                        0);
                                                WifiOffloadDialog.m1317$$Nest$mdoFinish(
                                                        wifiOffloadDialog, false);
                                                break;
                                        }
                                    }
                                });
        final int i2 = 0;
        AlertDialog create =
                positiveButton
                        .setNegativeButton(
                                R.string.notifyme_later,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.wifi.WifiOffloadDialogFragment.1
                                    public final /* synthetic */ WifiOffloadDialogFragment this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i22) {
                                        switch (i2) {
                                            case 0:
                                                WifiOffloadDialog.m1317$$Nest$mdoFinish(
                                                        WifiOffloadDialog.this, true);
                                                break;
                                            default:
                                                WifiOffloadDialog wifiOffloadDialog =
                                                        WifiOffloadDialog.this;
                                                Settings.System.putInt(
                                                        wifiOffloadDialog.getContentResolver(),
                                                        "wifi_offload_network_notify",
                                                        0);
                                                WifiOffloadDialog.m1317$$Nest$mdoFinish(
                                                        wifiOffloadDialog, false);
                                                break;
                                        }
                                    }
                                })
                        .create();
        create.getOnBackInvokedDispatcher()
                .registerOnBackInvokedCallback(
                        0,
                        new OnBackInvokedCallback() { // from class:
                                                      // com.samsung.android.settings.wifi.WifiOffloadDialogFragment.3
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                Log.d("WifiOffloadDialog", "back key pressed");
                                WifiOffloadDialog.m1317$$Nest$mdoFinish(
                                        WifiOffloadDialog.this, true);
                            }
                        });
        return create;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        Log.d("WifiOffloadDialog", "onDismiss");
        super.onDismiss(dialogInterface);
        WifiOffloadDialog.AnonymousClass1 anonymousClass1 = this.mListener;
        if (anonymousClass1 != null) {
            WifiOffloadDialog.m1317$$Nest$mdoFinish(WifiOffloadDialog.this, false);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        updateHeight$1();
    }

    public final void updateHeight$1() {
        View view =
                getFragmentManager().findFragmentById(R.id.wifi_picker_dialog_fragment).getView();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, displayMetrics.heightPixels / 2));
    }
}
