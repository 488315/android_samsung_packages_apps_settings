package com.android.settings.network.telephony;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SubscriptionActionDialogActivity extends FragmentActivity {
    public SubscriptionManager mSubscriptionManager;

    public final void dismissProgressDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        int i = ProgressDialogFragment.$r8$clinit;
        DialogFragment dialogFragment =
                (DialogFragment) fragmentManager.findFragmentByTag("ProgressDialogFragment");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
        setProgressState(0);
    }

    @Override // android.app.Activity
    public final void finish() {
        setProgressState(0);
        super.finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSubscriptionManager =
                ((SubscriptionManager) getSystemService(SubscriptionManager.class))
                        .createForAllUserProfiles();
        setProgressState(0);
    }

    public final void setProgressState(int i) {
        getSharedPreferences("sim_action_dialog_prefs", 0)
                .edit()
                .putInt("progress_state", i)
                .apply();
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "setProgressState:", "SubscriptionActionDialogActivity");
    }

    public final void showProgressDialog(String str, boolean z) {
        FragmentManager fragmentManager = getFragmentManager();
        int i = ProgressDialogFragment.$r8$clinit;
        ProgressDialogFragment progressDialogFragment =
                (ProgressDialogFragment)
                        fragmentManager.findFragmentByTag("ProgressDialogFragment");
        if (progressDialogFragment == null
                || !TextUtils.equals(
                        progressDialogFragment
                                .getArguments()
                                .getString(UniversalCredentialUtil.AGENT_TITLE),
                        str)) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            if (progressDialogFragment != null) {
                beginTransaction.remove(progressDialogFragment);
            }
            ProgressDialogFragment progressDialogFragment2 = new ProgressDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(UniversalCredentialUtil.AGENT_TITLE, str);
            progressDialogFragment2.setArguments(bundle);
            progressDialogFragment2.show(beginTransaction, "ProgressDialogFragment");
        }
        if (z) {
            setProgressState(1);
        }
    }
}
