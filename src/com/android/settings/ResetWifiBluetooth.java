package com.android.settings;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.preference.Preference;

import com.android.settings.network.ResetNetworkOperationBuilder;
import com.android.settings.network.ResetNetworkOperationBuilder$$ExternalSyntheticLambda0;
import com.android.settings.network.ResetNetworkRestrictionViewBuilder;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.system.ResetDashboardFragment;

import com.samsung.android.settings.general.ResetSettingsPreferenceFragment;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class ResetWifiBluetooth extends ResetSettingsPreferenceFragment {
    Activity mActivity;
    public View mContentView;
    public Context mContext;
    public ExecutorService mExecutorService;
    public boolean mIsUnderProgress = false;
    ResetNetworkRequest mResetNetworkRequest;

    public void endOfReset(Exception exc) {
        this.mIsUnderProgress = false;
        ExecutorService executorService = this.mExecutorService;
        if (executorService != null) {
            executorService.shutdown();
            this.mExecutorService = null;
        }
        if (exc != null) {
            Log.e("ResetWifiBluetooth", "Exception during reset", exc);
            return;
        }
        Toast.makeText(this.mContext, R.string.reset_bluetooth_wifi_complete_toast, 0).show();
        Log.i("ResetWifiBluetooth", "sendBluetoothWifiResetIntent is started.");
        Intent intent = new Intent("com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET");
        intent.addFlags(16777216);
        this.mActivity.sendOrderedBroadcast(
                intent, "com.sec.android.settings.permission.WIFI_BLUETOOTH_RESET");
        Log.i("ResetWifiBluetooth", "sendBluetoothWifiResetIntent is done");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return ResetDashboardFragment.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1998;
    }

    public ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(R.string.main_clear_progress_text));
        return progressDialog;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final String getResetButtonTitle() {
        return getString(R.string.reset_network_button_text);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_general";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().setTitle(R.string.sec_reset_wifi_bluetooth_settings);
        this.mActivity = getActivity();
        this.mContext = getContext();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View build = new ResetNetworkRestrictionViewBuilder(getActivity()).build();
        if (build != null) {
            Log.w("ResetWifiBluetooth", "Access deny.");
            return build;
        }
        View inflate = layoutInflater.inflate(R.layout.sec_reset_wifi_bluetooth, (ViewGroup) null);
        this.mContentView = inflate;
        return inflate;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final void onResetButtonClick() {
        if (this.mIsUnderProgress) {
            Log.w("ResetWifiBluetooth", "resetWifiBluetooth already under progress. Skip request");
            return;
        }
        this.mIsUnderProgress = true;
        LoggingHelper.insertEventLogging(1998, 4668);
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        Log.i("ResetWifiBluetooth", "secResetWifiAndBluetooth is started.");
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        this.mExecutorService = newSingleThreadExecutor;
        newSingleThreadExecutor.execute(
                new Runnable() { // from class:
                                 // com.android.settings.ResetWifiBluetooth$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        final ResetWifiBluetooth resetWifiBluetooth = ResetWifiBluetooth.this;
                        resetWifiBluetooth.getClass();
                        final AtomicReference atomicReference = new AtomicReference();
                        try {
                            resetWifiBluetooth.resetOperation().run();
                        } catch (Exception e) {
                            atomicReference.set(e);
                        }
                        resetWifiBluetooth
                                .mContext
                                .getMainExecutor()
                                .execute(
                                        new Runnable() { // from class:
                                                         // com.android.settings.ResetWifiBluetooth$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ResetWifiBluetooth resetWifiBluetooth2 =
                                                        ResetWifiBluetooth.this;
                                                AtomicReference atomicReference2 = atomicReference;
                                                resetWifiBluetooth2.getClass();
                                                resetWifiBluetooth2.endOfReset(
                                                        (Exception) atomicReference2.get());
                                            }
                                        });
                    }
                });
        SharedPreferences.Editor edit =
                getContext().getSharedPreferences("bluetooth_blocking_device", 0).edit();
        edit.remove("blocking_device_list");
        edit.commit();
        Settings.Secure.putInt(getContext().getContentResolver(), "bluetooth_bubble_tips_count", 0);
        SharedPreferences.Editor edit2 =
                getContext().getSharedPreferences("SettingsSCloudWifi", 0).edit();
        edit2.clear();
        edit2.commit();
        Settings.Global.putString(
                getContext().getContentResolver(),
                "airplane_mode_toggleable_radios",
                "bluetooth,wifi,nfc");
        Settings.Global.putInt(
                getContext().getContentResolver(), "sem_wifi_carrier_network_offload_enabled", 1);
        SemWifiManager semWifiManager =
                (SemWifiManager) this.mActivity.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        if (semWifiManager != null) {
            Log.i("ResetWifiBluetooth", "mSemWifiManager Reset is started.");
            semWifiManager.factoryReset();
        }
        Log.i("ResetWifiBluetooth", "secResetWifiAndBluetooth is done");
    }

    public Runnable resetOperation() throws Exception {
        if (SubscriptionUtil.isSimHardwareVisible(this.mContext)) {
            return new ResetNetworkOperationBuilder$$ExternalSyntheticLambda0(
                    new ResetNetworkRequest(28)
                            .toResetNetworkOperationBuilder(this.mContext, Looper.getMainLooper()),
                    1);
        }
        ResetNetworkOperationBuilder resetNetworkOperationBuilder =
                new ResetNetworkRequest(31)
                        .toResetNetworkOperationBuilder(this.mContext, Looper.getMainLooper());
        resetNetworkOperationBuilder.resetTelephonyAndNetworkPolicyManager(
                Preference.DEFAULT_ORDER);
        return new ResetNetworkOperationBuilder$$ExternalSyntheticLambda0(
                resetNetworkOperationBuilder, 1);
    }
}
