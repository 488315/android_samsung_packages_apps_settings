package com.android.settings.sim;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SidecarFragment;
import com.android.settings.network.EnableMultiSimSidecar;
import com.android.settings.network.telephony.AlertDialogFragment;
import com.android.settings.network.telephony.ConfirmDialogFragment;
import com.android.settings.network.telephony.SubscriptionActionDialogActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DsdsDialogActivity extends SubscriptionActionDialogActivity
        implements SidecarFragment.Listener, ConfirmDialogFragment.OnConfirmListener {
    public EnableMultiSimSidecar mEnableMultiSimSidecar;

    @Override // com.android.settings.network.telephony.ConfirmDialogFragment.OnConfirmListener
    public final void onConfirm(int i, int i2, boolean z) {
        if (!z) {
            Log.i("DsdsDialogActivity", "User cancel the dialog to enable DSDS.");
            Intent intent = new Intent(this, (Class<?>) ChooseSimActivity.class);
            intent.putExtra("has_psim", true);
            startActivity(intent);
            finish();
            return;
        }
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(TelephonyManager.class);
        if (i != 1) {
            if (i != 2) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i, "Unrecognized confirmation dialog tag: ", "DsdsDialogActivity");
                return;
            }
            Log.i("DsdsDialogActivity", "User confirmed reboot to enable DSDS.");
            SimActivationNotifier.setShowSimSettingsNotification(this, true);
            telephonyManager.switchMultiSimConfig(2);
            return;
        }
        if (telephonyManager.doesSwitchMultiSimConfigTriggerReboot()) {
            Log.i("DsdsDialogActivity", "Device does not support reboot free DSDS.");
            ConfirmDialogFragment.show(
                    this,
                    2,
                    getString(R.string.sim_action_restart_title),
                    getString(R.string.sim_action_enable_dsds_text),
                    getString(R.string.sim_action_reboot),
                    getString(R.string.cancel));
        } else {
            Log.i("DsdsDialogActivity", "Enabling DSDS without rebooting.");
            showProgressDialog(
                    getString(R.string.sim_action_enabling_sim_without_carrier_name), false);
            this.mEnableMultiSimSidecar.run$1();
        }
    }

    @Override // com.android.settings.network.telephony.SubscriptionActionDialogActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        int i = EnableMultiSimSidecar.$r8$clinit;
        this.mEnableMultiSimSidecar =
                (EnableMultiSimSidecar)
                        SidecarFragment.get(
                                fragmentManager,
                                "EnableMultiSimSidecar",
                                EnableMultiSimSidecar.class);
        if (bundle == null) {
            ConfirmDialogFragment.show(
                    this,
                    1,
                    getString(R.string.sim_action_enable_dsds_title),
                    getString(R.string.sim_action_enable_dsds_text),
                    getString(R.string.sim_action_yes),
                    getString(R.string.sim_action_no_thanks));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        this.mEnableMultiSimSidecar.removeListener(this);
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mEnableMultiSimSidecar.addListener(this);
    }

    @Override // com.android.settings.SidecarFragment.Listener
    public final void onStateChange(SidecarFragment sidecarFragment) {
        EnableMultiSimSidecar enableMultiSimSidecar = this.mEnableMultiSimSidecar;
        if (sidecarFragment == enableMultiSimSidecar) {
            int i = sidecarFragment.mState;
            if (i == 2) {
                enableMultiSimSidecar.setState(0, 0);
                Log.i("DsdsDialogActivity", "Enabled DSDS successfully");
                dismissProgressDialog();
                finish();
                return;
            }
            if (i != 3) {
                return;
            }
            enableMultiSimSidecar.setState(0, 0);
            Log.e("DsdsDialogActivity", "Failed to enable DSDS");
            dismissProgressDialog();
            AlertDialogFragment.show(
                    this,
                    getString(R.string.dsds_activation_failure_title),
                    getString(R.string.dsds_activation_failure_body_msg2));
        }
    }
}
