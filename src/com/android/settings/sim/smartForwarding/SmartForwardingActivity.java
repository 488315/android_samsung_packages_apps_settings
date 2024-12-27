package com.android.settings.sim.smartForwarding;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.CallForwardingInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toolbar;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.network.SubscriptionUtil;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SmartForwardingActivity extends SettingsBaseActivity {
    public static final String LOG_TAG = SmartForwardingActivity.class.toString();
    public final ListeningExecutorService service =
            MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.sim.smartForwarding.SmartForwardingActivity$1, reason: invalid class name */
    public final class AnonymousClass1 implements FutureCallback {
        public final /* synthetic */ int $r8$classId = 0;
        public final /* synthetic */ Object val$dialog;

        public AnonymousClass1(ProgressDialog progressDialog) {
            this.val$dialog = progressDialog;
        }

        @Override // com.google.common.util.concurrent.FutureCallback
        public final void onFailure(Throwable th) {
            switch (this.$r8$classId) {
                case 0:
                    Log.e("SmartForwarding", "Enable Feature exception", th);
                    ((ProgressDialog) this.val$dialog).dismiss();
                    new AlertDialog.Builder(SmartForwardingActivity.this)
                            .setTitle(R.string.smart_forwarding_failed_title)
                            .setMessage(R.string.smart_forwarding_failed_text)
                            .setPositiveButton(
                                    R.string.smart_forwarding_missing_alert_dialog_text,
                                    new SmartForwardingActivity$$ExternalSyntheticLambda0(1))
                            .create()
                            .show();
                    break;
                default:
                    Log.e("SmartForwarding", "Disable Feature exception" + th);
                    break;
            }
        }

        @Override // com.google.common.util.concurrent.FutureCallback
        public final void onSuccess(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    EnableSmartForwardingTask.FeatureResult featureResult =
                            (EnableSmartForwardingTask.FeatureResult) obj;
                    Log.e("SmartForwarding", "Enable Feature result: " + featureResult.result);
                    boolean z = featureResult.result;
                    SmartForwardingActivity smartForwardingActivity = SmartForwardingActivity.this;
                    if (z) {
                        EnableSmartForwardingTask.SlotUTData[] slotUTDataArr =
                                featureResult.slotUTData;
                        int i = 0;
                        while (true) {
                            boolean z2 = true;
                            if (i < slotUTDataArr.length) {
                                EnableSmartForwardingTask.SlotUTData slotUTData = slotUTDataArr[i];
                                if (slotUTData.mQueryCallWaiting.result != 1) {
                                    z2 = false;
                                }
                                smartForwardingActivity
                                        .getSharedPreferences(
                                                "smart_forwarding_pref_" + slotUTData.subId, 0)
                                        .edit()
                                        .putBoolean("call_waiting_key", z2)
                                        .commit();
                                EnableSmartForwardingTask.SlotUTData slotUTData2 = slotUTDataArr[i];
                                int i2 = slotUTData2.subId;
                                CallForwardingInfo callForwardingInfo =
                                        slotUTData2.mQueryCallForwarding.result;
                                SharedPreferences.Editor edit =
                                        smartForwardingActivity
                                                .getSharedPreferences(
                                                        "smart_forwarding_pref_" + i2, 0)
                                                .edit();
                                edit.putBoolean(
                                                "call_forwarding_enabled_key",
                                                callForwardingInfo.isEnabled())
                                        .commit();
                                edit.putInt(
                                                "call_forwarding_reason_key",
                                                callForwardingInfo.getReason())
                                        .commit();
                                edit.putString(
                                                "call_forwarding_number_key",
                                                callForwardingInfo.getNumber())
                                        .commit();
                                edit.putInt(
                                                "call_forwarding_timekey",
                                                callForwardingInfo.getTimeoutSeconds())
                                        .commit();
                                i++;
                            } else {
                                SmartForwardingFragment smartForwardingFragment =
                                        (SmartForwardingFragment)
                                                smartForwardingActivity
                                                        .getSupportFragmentManager()
                                                        .findFragmentById(R.id.content_frame);
                                if (smartForwardingFragment != null) {
                                    ((TwoStatePreference)
                                                    smartForwardingFragment.findPreference(
                                                            "smart_forwarding_switch"))
                                            .setChecked(true);
                                }
                            }
                        }
                    } else {
                        smartForwardingActivity.getClass();
                        new AlertDialog.Builder(smartForwardingActivity)
                                .setTitle(R.string.smart_forwarding_failed_title)
                                .setMessage(
                                        featureResult.reason
                                                        == EnableSmartForwardingTask.FeatureResult
                                                                .FailedReason.SIM_NOT_ACTIVE
                                                ? R.string
                                                        .smart_forwarding_failed_not_activated_text
                                                : R.string.smart_forwarding_failed_text)
                                .setPositiveButton(
                                        R.string.smart_forwarding_missing_alert_dialog_text,
                                        new SmartForwardingActivity$$ExternalSyntheticLambda0(0))
                                .create()
                                .show();
                    }
                    ((ProgressDialog) this.val$dialog).dismiss();
                    break;
                default:
                    int activeModemCount =
                            ((TelephonyManager) this.val$dialog).getActiveModemCount();
                    for (int i3 = 0; i3 < activeModemCount; i3++) {
                        SmartForwardingActivity.this
                                .getSharedPreferences(
                                        "smart_forwarding_pref_"
                                                + SubscriptionManager.getSubscriptionId(i3),
                                        0)
                                .edit()
                                .clear()
                                .commit();
                    }
                    break;
            }
        }

        public AnonymousClass1(
                SubscriptionManager subscriptionManager, TelephonyManager telephonyManager) {
            this.val$dialog = telephonyManager;
        }
    }

    public final void enableSmartForwarding(String[] strArr) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.smart_forwarding_ongoing_title);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getText(R.string.smart_forwarding_ongoing_text));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ListenableFuture submit =
                ((MoreExecutors.ListeningDecorator) this.service)
                        .submit((Callable) new EnableSmartForwardingTask(this, strArr));
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(progressDialog);
        submit.addListener(
                new Futures.CallbackListener(submit, anonymousClass1), getMainExecutor());
    }

    @Override // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        if (!getResources().getBoolean(R.bool.config_show_sim_info)) {
            Log.d(LOG_TAG, "Not support on device without SIM.");
            finish();
            return;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        toolbar.setVisibility(0);
        setActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        BackStackRecord m =
                BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                        supportFragmentManager, supportFragmentManager);
        m.replace(R.id.content_frame, new SmartForwardingFragment(), null);
        m.commitInternal(false);
    }
}
