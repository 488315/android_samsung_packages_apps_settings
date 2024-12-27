package com.samsung.android.settings.wifi.develop.others;

import android.app.ProgressDialog;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RestartSubsystemFragment extends SettingsPreferenceFragment {
    public FragmentActivity mActivity;
    public View mContentView;
    public Handler mHandler;
    public ProgressDialog mProgressDialog;
    public Button mRestartWifiButton;
    public WifiManager mWifiManager;
    public AnonymousClass1 mBtnClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.samsung.android.settings.wifi.develop.others.RestartSubsystemFragment.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RestartSubsystemFragment.this.mRestartWifiButton.setClickable(false);
                    RestartSubsystemFragment restartSubsystemFragment =
                            RestartSubsystemFragment.this;
                    if (!restartSubsystemFragment.mWifiManager.isWifiEnabled()) {
                        FragmentActivity fragmentActivity = restartSubsystemFragment.mActivity;
                        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                        if ((Settings.Secure.getInt(
                                                        fragmentActivity.getContentResolver(),
                                                        "location_mode",
                                                        0)
                                                != 3
                                        || !restartSubsystemFragment.mWifiManager
                                                .isScanAlwaysAvailable())
                                && !restartSubsystemFragment.mWifiManager.isWifiApEnabled()) {
                            Toast.makeText(
                                            RestartSubsystemFragment.this.getContext(),
                                            R.string.sec_cannot_restart_wifi_subsystem,
                                            1)
                                    .show();
                            RestartSubsystemFragment.this.mRestartWifiButton.setClickable(true);
                            Settings.Global.putInt(
                                    RestartSubsystemFragment.this.getContext().getContentResolver(),
                                    "sem_wifi_abtest_user_activation",
                                    0);
                        }
                    }
                    RestartSubsystemFragment restartSubsystemFragment2 =
                            RestartSubsystemFragment.this;
                    restartSubsystemFragment2.mWifiManager.registerSubsystemRestartTrackingCallback(
                            new HandlerExecutor(restartSubsystemFragment2.mHandler.getLooper()),
                            restartSubsystemFragment2.mWifiSubsystemRestartTrackingCallback);
                    restartSubsystemFragment2.mWifiManager.restartWifiSubsystem();
                    Settings.Global.putInt(
                            RestartSubsystemFragment.this.getContext().getContentResolver(),
                            "sem_wifi_abtest_user_activation",
                            0);
                }
            };
    public final AnonymousClass2 mWifiSubsystemRestartTrackingCallback =
            new WifiManager
                    .SubsystemRestartTrackingCallback() { // from class:
                                                          // com.samsung.android.settings.wifi.develop.others.RestartSubsystemFragment.2
                @Override // android.net.wifi.WifiManager.SubsystemRestartTrackingCallback
                public final void onSubsystemRestarted() {
                    ProgressDialog progressDialog = RestartSubsystemFragment.this.mProgressDialog;
                    if (progressDialog != null && progressDialog.isShowing()) {
                        RestartSubsystemFragment.this.mProgressDialog.dismiss();
                    }
                    RestartSubsystemFragment.this.mRestartWifiButton.setClickable(true);
                }

                @Override // android.net.wifi.WifiManager.SubsystemRestartTrackingCallback
                public final void onSubsystemRestarting() {
                    RestartSubsystemFragment restartSubsystemFragment =
                            RestartSubsystemFragment.this;
                    FragmentActivity fragmentActivity = restartSubsystemFragment.mActivity;
                    ProgressDialog progressDialog = new ProgressDialog(fragmentActivity);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle(R.string.sec_restart_wifi_subsystem_title);
                    progressDialog.setMessage(
                            fragmentActivity.getString(R.string.main_clear_progress_text));
                    restartSubsystemFragment.mProgressDialog = progressDialog;
                    RestartSubsystemFragment.this.mProgressDialog.show();
                }
            };

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = getActivity();
        HandlerThread handlerThread = new HandlerThread("RestartWifiSubsystemHandler");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        this.mWifiManager = (WifiManager) getContext().getSystemService(ImsProfile.PDN_WIFI);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.sec_restart_wifi_subsystem, (ViewGroup) null);
        this.mContentView = inflate;
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mBtnClickListener != null) {
            this.mBtnClickListener = null;
        }
        super.onDestroy();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Button button =
                (Button) this.mContentView.findViewById(R.id.execute_restart_wifi_subsystem);
        this.mRestartWifiButton = button;
        button.setOnClickListener(this.mBtnClickListener);
    }
}
