package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SeslContentViewInsetsCallback;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.WifiTrackerLibProviderImpl;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.wifitrackerlib.NetworkDetailsTracker;
import com.android.wifitrackerlib.Utils;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiNetworkDetailsFragment extends DashboardFragment {
    public WifiImeHelper mImeHelper;
    public MenuItem mInfoMenu;
    public NetworkDetailsTracker mNetworkDetailsTracker;
    public WifiConfiguration mWifiConfig;
    public WifiDetailNavigationController mWifiDetailNavigationController;
    public WifiDetailPreferenceController mWifiDetailPreferenceController;
    public WifiEntry mWifiEntry;
    public HandlerThread mWorkerThread;
    public final List mControllers = new ArrayList();
    public boolean mInManageNetwork = false;
    public boolean mIsSetupWizardFinish = false;
    public final WifiNetworkDetailsFragment$$ExternalSyntheticLambda0 wifiExpandListener =
            new WifiExpandablePreferenceController
                    .WifiExpandListener() { // from class:
                                            // com.samsung.android.settings.wifi.details.WifiNetworkDetailsFragment$$ExternalSyntheticLambda0
                @Override // com.samsung.android.settings.wifi.details.WifiExpandablePreferenceController.WifiExpandListener
                public final void onSpread() {
                    ((ArrayList) WifiNetworkDetailsFragment.this.mControllers)
                            .forEach(new WifiNetworkDetailsFragment$$ExternalSyntheticLambda1());
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.details.WifiNetworkDetailsFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        SemWifiManager semWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        if (this.mNetworkDetailsTracker == null) {
            Context context2 = getContext();
            HandlerThread handlerThread =
                    new HandlerThread(
                            "WifiNetworkDetailsFrg{"
                                    + Integer.toHexString(System.identityHashCode(this))
                                    + "}",
                            10);
            this.mWorkerThread = handlerThread;
            handlerThread.start();
            SimpleClock anonymousClass2 = new AnonymousClass2(ZoneOffset.UTC);
            if (TextUtils.isEmpty(getArguments().getString("key_chosen_wifientry_key"))) {
                finish();
            }
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                    featureFactoryImpl.getWifiTrackerLibProvider();
            Lifecycle settingsLifecycle = getSettingsLifecycle();
            Handler handler = new Handler(Looper.getMainLooper());
            Handler threadHandler = this.mWorkerThread.getThreadHandler();
            String string = getArguments().getString("key_chosen_wifientry_key");
            wifiTrackerLibProvider.getClass();
            this.mNetworkDetailsTracker =
                    WifiTrackerLibProviderImpl.createNetworkDetailsTracker(
                            settingsLifecycle,
                            context2,
                            handler,
                            threadHandler,
                            anonymousClass2,
                            string);
        }
        if (wifiManager.getConnectionInfo() != null) {
            WifiEntry wifiEntry = this.mNetworkDetailsTracker.getWifiEntry();
            this.mWifiEntry = wifiEntry;
            wifiEntry.forceUpdateNetworkInfo(
                    wifiManager.getConnectionInfo(),
                    connectivityManager.getNetworkInfo(wifiManager.getCurrentNetwork()));
            this.mWifiEntry.semUpdateConfig(this.mWifiConfig);
        }
        WifiDetailPreferenceController wifiDetailPreferenceController =
                new WifiDetailPreferenceController(
                        this.mWifiEntry,
                        connectivityManager,
                        context,
                        this,
                        new Handler(Looper.getMainLooper()),
                        getSettingsLifecycle(),
                        wifiManager,
                        semWifiManager,
                        new WifiDetailPreferenceController.IconInjector(context),
                        this.mInManageNetwork);
        this.mWifiDetailPreferenceController = wifiDetailPreferenceController;
        ((ArrayList) this.mControllers).add(wifiDetailPreferenceController);
        ((ArrayList) this.mControllers)
                .add(
                        new WifiAutoConnectPreferenceController(
                                context, this.mWifiEntry, this, wifiManager, "WIFI_106"));
        ((ArrayList) this.mControllers)
                .add(
                        new WifiManageRouterPreferenceController(
                                this.mWifiEntry, connectivityManager, context));
        WifiExpandablePreferenceController wifiExpandablePreferenceController =
                new WifiExpandablePreferenceController(
                        context, this, getSettingsLifecycle(), this.mImeHelper, "WIFI_106");
        wifiExpandablePreferenceController.mExpandListener = this.wifiExpandListener;
        ((ArrayList) this.mControllers).add(wifiExpandablePreferenceController);
        if (!this.mInManageNetwork) {
            WifiNetworkConfigPreferenceController wifiNetworkConfigPreferenceController =
                    new WifiNetworkConfigPreferenceController(
                            this.mWifiEntry,
                            connectivityManager,
                            context,
                            this,
                            this.mImeHelper,
                            "WIFI_106");
            wifiNetworkConfigPreferenceController.mListener = this;
            ((ArrayList) this.mControllers).add(wifiNetworkConfigPreferenceController);
            ((ArrayList) this.mControllers)
                    .add(
                            new WifiMeteredPreferenceController(
                                    context, this.mWifiEntry, this, "WIFI_106"));
        }
        ((ArrayList) this.mControllers)
                .add(
                        new WifiPrivacyPreferenceController(
                                context, this.mWifiEntry, this, "WIFI_106"));
        ((ArrayList) this.mControllers)
                .add(
                        new WifiDhcpNameSharingPreferenceController(
                                context, this.mWifiEntry, this, wifiManager, "WIFI_106"));
        this.mWifiDetailNavigationController =
                new WifiDetailNavigationController(
                        this.mWifiEntry,
                        getContext(),
                        this,
                        this.mInManageNetwork,
                        getSettingsLifecycle(),
                        this.mMetricsFeatureProvider,
                        "WIFI_106");
        return this.mControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiNetworkDetailsFrg";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 849;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return this.mInManageNetwork
                ? R.xml.sec_wifi_network_details_manage_network_fragment
                : R.xml.sec_wifi_network_details_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        WifiDetailNavigationController wifiDetailNavigationController;
        super.onActivityResult(i, i2, intent);
        WifiDetailPreferenceController wifiDetailPreferenceController =
                this.mWifiDetailPreferenceController;
        if (wifiDetailPreferenceController == null
                || (wifiDetailNavigationController = this.mWifiDetailNavigationController)
                        == null) {
            return;
        }
        if (i != 55) {
            if (i == 155) {
                if (i2 == -1) {
                    wifiDetailPreferenceController.showPassword$1();
                    return;
                }
                return;
            } else if (i != 255) {
                return;
            }
        }
        if (i2 != -1) {
            wifiDetailNavigationController.initBottom();
        } else if (i == 55) {
            wifiDetailNavigationController.launchWifiDppConfiguratorActivity$1$1();
        } else {
            if (i != 255) {
                return;
            }
            wifiDetailNavigationController.shareNetwork$1();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Bundle arguments = getArguments();
        this.mIsSetupWizardFinish =
                Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
        this.mImeHelper = new WifiImeHelper(context);
        if (arguments != null) {
            this.mInManageNetwork = arguments.getBoolean("manage_network", false);
            this.mWifiConfig =
                    (WifiConfiguration) arguments.getParcelable("key_chosen_wifientry_config");
        } else {
            finish();
        }
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.getInt("wifi_tips_notification", 0) == 1) {
            SALogging.insertSALog("WIFI_106", "1029");
        }
        super.onAttach(context);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar == null) {
            return;
        }
        if (!this.mIsSetupWizardFinish) {
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setHomeButtonEnabled();
            return;
        }
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry != null) {
            if (wifiEntry.getSecurity$1() != 0 && wifiEntry.getSecurity$1() != 1) {
                if (!Utils.defaultSsidList.contains(this.mWifiEntry.getTitle().toLowerCase())) {
                    return;
                }
            }
            MenuItem add = menu.add(0, 2, 0, R.string.wifi_detail_information_label);
            this.mInfoMenu = add;
            add.setShowAsAction(2);
            this.mInfoMenu.setIcon(R.drawable.sec_wifi_detail_info);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        FragmentActivity activity = getActivity();
        if (activity instanceof SettingsBaseActivity) {
            ((SettingsBaseActivity) activity).disableExtendedAppBar();
        }
        activity.setTitle((CharSequence) null);
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mWorkerThread.quitSafely();
        super.onDestroy();
        this.mImeHelper.hideSoftKeyboard(getListView());
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        String str;
        if (menuItem.getItemId() != 2) {
            return super.onOptionsItemSelected(menuItem);
        }
        WifiEntry wifiEntry = this.mWifiEntry;
        Context context = getContext();
        int security$1 = wifiEntry.getSecurity$1();
        String str2 = ApnSettings.MVNO_NONE;
        if (security$1 == 0) {
            str = context.getString(R.string.wifi_open_warning_summary);
        } else if (wifiEntry.getSecurity$1() == 1) {
            str = context.getString(R.string.wifi_wep_warning_summary);
        } else {
            if (Utils.defaultSsidList.contains(wifiEntry.getTitle().toLowerCase())) {
                str = context.getString(R.string.wifi_default_ssid_warning_summary);
            } else {
                Log.d("WifiDetailWarning", "Title mismatch " + wifiEntry);
                str = ApnSettings.MVNO_NONE;
            }
        }
        if (wifiEntry.getSecurity$1() == 0) {
            str2 = context.getString(R.string.wifi_open_warning);
        } else if (wifiEntry.getSecurity$1() == 1) {
            str2 = context.getString(R.string.wifi_wep_warning);
        } else {
            if (Utils.defaultSsidList.contains(wifiEntry.getTitle().toLowerCase())) {
                str2 = context.getString(R.string.wifi_default_ssid_warning);
            } else {
                Log.d("WifiDetailWarning", "Message mismatch " + wifiEntry);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = str;
        alertParams.mMessage = str2;
        builder.setPositiveButton(R.string.common_ok, (DialogInterface.OnClickListener) null);
        builder.create().show();
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SALogging.insertSALog("WIFI_106");
        WifiDetailNavigationController wifiDetailNavigationController =
                this.mWifiDetailNavigationController;
        if (wifiDetailNavigationController.mBottomMode == 0) {
            wifiDetailNavigationController.initBottom();
        }
        this.mImeHelper.init(getActivity().getCurrentFocus(), getPreferenceScreen());
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getActivity().getWindow().setDecorFitsSystemWindows(false);
        View findViewById = getActivity().findViewById(android.R.id.content);
        SeslContentViewInsetsCallback seslContentViewInsetsCallback =
                new SeslContentViewInsetsCallback(
                        WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(),
                        WindowInsets.Type.ime());
        findViewById.setWindowInsetsAnimationCallback(seslContentViewInsetsCallback);
        findViewById.setOnApplyWindowInsetsListener(seslContentViewInsetsCallback);
        getListView().mDrawLastRoundedCorner = false;
        getListView().seslSetFillBottomEnabled(true);
        getListView()
                .setOnFocusChangeListener(
                        new View
                                .OnFocusChangeListener() { // from class:
                                                           // com.samsung.android.settings.wifi.details.WifiNetworkDetailsFragment.1
                            @Override // android.view.View.OnFocusChangeListener
                            public final void onFocusChange(View view2, boolean z) {
                                if (z) {
                                    WifiNetworkDetailsFragment.this.mImeHelper.hideSoftKeyboard(
                                            view2);
                                }
                            }
                        });
    }
}
