package com.android.settings.wifi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManager;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class NetworkRequestDialogBaseFragment extends InstrumentedDialogFragment {
    static final String EXTRA_APP_NAME = "com.android.settings.wifi.extra.APP_NAME";
    public NetworkRequestDialogActivity mActivity = null;
    public String mAppName = ApnSettings.MVNO_NONE;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1373;
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NetworkRequestDialogActivity) {
            this.mActivity = (NetworkRequestDialogActivity) context;
        }
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            this.mAppName = intent.getStringExtra(EXTRA_APP_NAME);
        }
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        NetworkRequestDialogActivity networkRequestDialogActivity = this.mActivity;
        if (networkRequestDialogActivity != null) {
            networkRequestDialogActivity.dismissDialogs();
            WifiManager.NetworkRequestUserSelectionCallback networkRequestUserSelectionCallback =
                    networkRequestDialogActivity.mUserSelectionCallback;
            if (networkRequestUserSelectionCallback != null) {
                networkRequestUserSelectionCallback.reject();
            }
            networkRequestDialogActivity.finish();
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final void show(FragmentManager fragmentManager, String str) {
        try {
            fragmentManager.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(fragmentManager);
            backStackRecord.doAddOp(0, this, str, 1);
            backStackRecord.commitInternal(true);
        } catch (IllegalStateException unused) {
            if (getActivity() != null) {
                getActivity().finish();
            }
        }
    }

    public void onMatch(List list) {}

    public void onUserSelectionCallbackRegistration(
            WifiManager.NetworkRequestUserSelectionCallback networkRequestUserSelectionCallback) {}
}
