package com.android.settings;

import android.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivitySettingsManager;
import android.net.NetworkPolicyManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.network.ResetNetworkOperationBuilder;
import com.android.settings.network.ResetNetworkOperationBuilder$$ExternalSyntheticLambda7;
import com.android.settings.network.ResetNetworkRestrictionViewBuilder;

import com.samsung.android.settings.general.ResetSettingsPreferenceFragment;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class ResetNetworkConfirm extends ResetSettingsPreferenceFragment {
    Activity mActivity;
    public AlertDialog mAlertDialog;
    View mContentView;
    boolean mEraseEsim;
    ResetNetworkRequest mResetNetworkRequest;
    ResetNetworkTask mResetNetworkTask;
    ResetSubscriptionContract mResetSubscriptionContract;
    public int mSubId = -1;
    public boolean mIsUnderProgress = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.ResetNetworkConfirm$1, reason: invalid class name */
    public final class AnonymousClass1 extends ResetSubscriptionContract {
        public AnonymousClass1(Context context, ResetNetworkRequest resetNetworkRequest) {
            super(context, resetNetworkRequest);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ResetNetworkTask extends AsyncTask {
        public final Context mContext;

        public ResetNetworkTask(Context context) {
            this.mContext = context;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v6, types: [com.android.settings.ResetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0] */
        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            ResetNetworkRequest resetNetworkRequest = ResetNetworkConfirm.this.mResetNetworkRequest;
            String str = resetNetworkRequest.mResetEsimPackageName;
            ResetNetworkOperationBuilder resetNetworkOperationBuilder =
                    resetNetworkRequest.toResetNetworkOperationBuilder(
                            this.mContext, Looper.getMainLooper());
            if (str != null) {
                resetNetworkOperationBuilder.resetEsim(
                        str,
                        new Consumer() { // from class:
                                         // com.android.settings.ResetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                atomicBoolean.set(((Boolean) obj).booleanValue());
                            }
                        });
            }
            ((ArrayList) resetNetworkOperationBuilder.mResetSequence)
                    .forEach(new ResetNetworkOperationBuilder$$ExternalSyntheticLambda7(2));
            boolean z = atomicBoolean.get();
            ResetNetworkConfirm resetNetworkConfirm = ResetNetworkConfirm.this;
            resetNetworkConfirm.getClass();
            Log.i("ResetNetworkConfirm", "resetNetwork is started.");
            Intent intent = new Intent("com.samsung.intent.action.SETTINGS_NETWORK_RESET");
            intent.putExtra("subId", resetNetworkConfirm.mSubId);
            intent.addFlags(16777216);
            resetNetworkConfirm.mActivity.sendOrderedBroadcast(
                    intent, "com.sec.android.settings.permission.NETWORK_RESET");
            Log.i("ResetNetworkConfirm", "resetNetwork is done");
            Log.d(
                    "ResetNetworkTask",
                    "network factoryReset complete. succeeded: " + String.valueOf(z));
            return Boolean.valueOf(z);
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            ResetNetworkConfirm.this.mIsUnderProgress = false;
            if (((Boolean) obj).booleanValue()) {
                Toast.makeText(this.mContext, R.string.reset_network_complete_toast, 0).show();
                return;
            }
            ResetNetworkConfirm resetNetworkConfirm = ResetNetworkConfirm.this;
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setTitle(R.string.reset_esim_error_title);
            builder.setMessage(R.string.reset_esim_error_msg);
            builder.setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null);
            resetNetworkConfirm.mAlertDialog = builder.show();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 84;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final String getResetButtonTitle() {
        return getString(R.string.reset);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            bundle = arguments;
        }
        this.mSubId = bundle.getInt("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
        ResetNetworkRequest resetNetworkRequest = new ResetNetworkRequest();
        resetNetworkRequest.mResetOptions = 0;
        resetNetworkRequest.mResetTelephonyManager = -1;
        resetNetworkRequest.mResetApn = -1;
        resetNetworkRequest.mSubscriptionIdToResetIms = -1;
        resetNetworkRequest.mResetOptions = bundle.getInt("resetNetworkOptions", 0);
        resetNetworkRequest.mResetEsimPackageName = bundle.getString("resetEsimPackage");
        resetNetworkRequest.mResetTelephonyManager =
                bundle.getInt("resetTelephonyNetPolicySubId", -1);
        resetNetworkRequest.mResetApn = bundle.getInt("resetApnSubId", -1);
        resetNetworkRequest.mSubscriptionIdToResetIms = bundle.getInt("resetImsSubId", -1);
        this.mResetNetworkRequest = resetNetworkRequest;
        this.mActivity = getActivity();
        this.mResetSubscriptionContract =
                new AnonymousClass1(getContext(), this.mResetNetworkRequest);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View build = new ResetNetworkRestrictionViewBuilder(this.mActivity).build();
        if (build != null) {
            this.mResetSubscriptionContract.close();
            Log.w("ResetNetworkConfirm", "Access deny.");
            return build;
        }
        View inflate = layoutInflater.inflate(R.layout.sec_reset_network_confirm, (ViewGroup) null);
        this.mContentView = inflate;
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        ResetNetworkTask resetNetworkTask = this.mResetNetworkTask;
        if (resetNetworkTask != null) {
            resetNetworkTask.cancel(true);
            this.mResetNetworkTask = null;
        }
        ResetSubscriptionContract resetSubscriptionContract = this.mResetSubscriptionContract;
        if (resetSubscriptionContract != null) {
            resetSubscriptionContract.close();
            this.mResetSubscriptionContract = null;
        }
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final void onResetButtonClick() {
        if (this.mIsUnderProgress) {
            Log.w("ResetNetworkConfirm", "resetMobileNetwork already under progress. Skip request");
            return;
        }
        this.mIsUnderProgress = true;
        LoggingHelper.insertEventLogging(84, 4664);
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        Integer anyMissingSubscriptionId =
                this.mResetSubscriptionContract.getAnyMissingSubscriptionId();
        if (anyMissingSubscriptionId != null) {
            Log.w("ResetNetworkConfirm", "subId " + anyMissingSubscriptionId + " no longer active");
            getActivity().onBackPressed();
            return;
        }
        Log.i("ResetNetworkConfirm", "secResetNetwork is started.");
        Log.i("ResetNetworkConfirm", "Reset Allowed Network for apps");
        NetworkPolicyManager networkPolicyManager =
                (NetworkPolicyManager) getContext().getSystemService("netpolicy");
        int[] allFirewallRuleMobileData = networkPolicyManager.getAllFirewallRuleMobileData();
        Set mobileDataPreferredUids =
                ConnectivitySettingsManager.getMobileDataPreferredUids(getContext());
        mobileDataPreferredUids.clear();
        for (int i : allFirewallRuleMobileData) {
            networkPolicyManager.setFirewallRuleMobileData(i, true);
        }
        ConnectivitySettingsManager.setMobileDataPreferredUids(
                getContext(), mobileDataPreferredUids);
        Log.i("ResetNetworkConfirm", "secResetNetwork is done");
        ResetNetworkTask resetNetworkTask = new ResetNetworkTask(this.mActivity);
        this.mResetNetworkTask = resetNetworkTask;
        resetNetworkTask.execute(new Void[0]);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        ResetNetworkRequest resetNetworkRequest = this.mResetNetworkRequest;
        bundle.putInt("resetNetworkOptions", resetNetworkRequest.mResetOptions);
        bundle.putString("resetEsimPackage", resetNetworkRequest.mResetEsimPackageName);
        bundle.putInt("resetTelephonyNetPolicySubId", resetNetworkRequest.mResetTelephonyManager);
        bundle.putInt("resetApnSubId", resetNetworkRequest.mResetApn);
        bundle.putInt("resetImsSubId", resetNetworkRequest.mSubscriptionIdToResetIms);
    }

    public void setSubtitle() {
        if (this.mResetNetworkRequest.mResetEsimPackageName != null) {
            ((TextView) this.mContentView.findViewById(R.id.reset_network_confirm))
                    .setText(R.string.reset_network_final_desc_esim);
        }
    }
}
