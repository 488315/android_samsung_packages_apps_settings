package com.android.settings.sim;

import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.SidecarFragment;
import com.android.settings.network.SwitchToEuiccSubscriptionSidecar;
import com.android.settings.network.telephony.AlertDialogFragment;
import com.android.settings.network.telephony.ConfirmDialogFragment;
import com.android.settings.network.telephony.SubscriptionActionDialogActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SwitchToEsimConfirmDialogActivity extends SubscriptionActionDialogActivity
        implements SidecarFragment.Listener, ConfirmDialogFragment.OnConfirmListener {
    public SubscriptionInfo mSubToEnabled = null;
    public SwitchToEuiccSubscriptionSidecar mSwitchToEuiccSubscriptionSidecar;

    @Override // com.android.settings.network.telephony.ConfirmDialogFragment.OnConfirmListener
    public final void onConfirm(int i, int i2, boolean z) {
        if (!z) {
            AlertDialogFragment.show(
                    this,
                    getString(R.string.switch_sim_dialog_no_switch_title),
                    getString(R.string.switch_sim_dialog_no_switch_text));
            return;
        }
        Log.i("SwitchToEsimConfirmDialogActivity", "User confirmed to switch to embedded slot.");
        this.mSwitchToEuiccSubscriptionSidecar.run(
                this.mSubToEnabled.getSubscriptionId(), -1, null);
        showProgressDialog(
                getString(
                        R.string.sim_action_switch_sub_dialog_progress,
                        new Object[] {this.mSubToEnabled.getDisplayName()}),
                false);
    }

    @Override // com.android.settings.network.telephony.SubscriptionActionDialogActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSubToEnabled = (SubscriptionInfo) getIntent().getParcelableExtra("sub_to_enable");
        this.mSwitchToEuiccSubscriptionSidecar =
                SwitchToEuiccSubscriptionSidecar.get(getFragmentManager());
        SubscriptionInfo subscriptionInfo = this.mSubToEnabled;
        if (subscriptionInfo == null) {
            Log.e("SwitchToEsimConfirmDialogActivity", "Cannot find SIM to enable.");
            finish();
        } else if (bundle == null) {
            ConfirmDialogFragment.show(
                    this,
                    1,
                    getString(
                            R.string.switch_sim_dialog_title,
                            new Object[] {subscriptionInfo.getDisplayName()}),
                    getString(
                            R.string.switch_sim_dialog_text,
                            new Object[] {this.mSubToEnabled.getDisplayName()}),
                    getString(R.string.okay),
                    getString(R.string.cancel));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        this.mSwitchToEuiccSubscriptionSidecar.removeListener(this);
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mSwitchToEuiccSubscriptionSidecar.addListener(this);
    }

    @Override // com.android.settings.SidecarFragment.Listener
    public final void onStateChange(SidecarFragment sidecarFragment) {
        SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar =
                this.mSwitchToEuiccSubscriptionSidecar;
        if (sidecarFragment == switchToEuiccSubscriptionSidecar) {
            int i = switchToEuiccSubscriptionSidecar.mState;
            if (i == 2) {
                switchToEuiccSubscriptionSidecar.setState(0, 0);
                Log.i("SwitchToEsimConfirmDialogActivity", "Successfully switched to eSIM slot.");
                dismissProgressDialog();
                finish();
                return;
            }
            if (i != 3) {
                return;
            }
            switchToEuiccSubscriptionSidecar.setState(0, 0);
            Log.e("SwitchToEsimConfirmDialogActivity", "Failed switching to eSIM slot.");
            dismissProgressDialog();
            finish();
        }
    }
}
