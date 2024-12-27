package com.android.settings.wifi.details;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.WifiDialog2;
import com.android.settings.wifi.WifiTrackerLibProviderImpl;
import com.android.settings.wifi.details2.AddDevicePreferenceController2;
import com.android.settings.wifi.details2.CertificateDetailsPreferenceController;
import com.android.settings.wifi.details2.ServerNamePreferenceController;
import com.android.settings.wifi.details2.WifiAutoConnectPreferenceController2;
import com.android.settings.wifi.details2.WifiDetailPreferenceController2;
import com.android.settings.wifi.details2.WifiMeteredPreferenceController2;
import com.android.settings.wifi.details2.WifiPrivacyPreferenceController;
import com.android.settings.wifi.details2.WifiPrivacyPreferenceController2;
import com.android.settings.wifi.details2.WifiSecondSummaryController2;
import com.android.settings.wifi.details2.WifiSubscriptionDetailPreferenceController2;
import com.android.settings.wifi.factory.WifiFeatureProvider;
import com.android.settings.wifi.repository.SharedConnectivityRepository;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.wifi.WifiUtils;
import com.android.wifitrackerlib.NetworkDetailsTracker;
import com.android.wifitrackerlib.WifiEntry;

import com.sec.ims.volte2.data.VolteConstants;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiNetworkDetailsFragment extends RestrictedDashboardFragment
        implements WifiDialog2.WifiDialog2Listener {
    List<AbstractPreferenceController> mControllers;
    public final boolean mIsInstantHotspotFeatureEnabled;
    boolean mIsUiRestricted;
    NetworkDetailsTracker mNetworkDetailsTracker;
    WifiDetailPreferenceController2 mWifiDetailPreferenceController2;
    public final List mWifiDialogListeners;
    WifiNetworkDetailsViewModel mWifiNetworkDetailsViewModel;
    public HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.details.WifiNetworkDetailsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    public WifiNetworkDetailsFragment() {
        super("no_config_wifi");
        this.mWifiDialogListeners = new ArrayList();
        this.mIsInstantHotspotFeatureEnabled = SharedConnectivityRepository.isDeviceConfigEnabled();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        this.mControllers = new ArrayList();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        setupNetworksDetailTracker();
        WifiEntry wifiEntry = this.mNetworkDetailsTracker.getWifiEntry();
        if (this.mIsInstantHotspotFeatureEnabled) {
            getWifiNetworkDetailsViewModel().setWifiEntry(wifiEntry);
        }
        WifiSecondSummaryController2 wifiSecondSummaryController2 =
                new WifiSecondSummaryController2(context);
        wifiSecondSummaryController2.setWifiEntry(wifiEntry);
        this.mControllers.add(wifiSecondSummaryController2);
        WifiDetailPreferenceController2 newInstance =
                WifiDetailPreferenceController2.newInstance(
                        wifiEntry,
                        connectivityManager,
                        context,
                        this,
                        new Handler(Looper.getMainLooper()),
                        getSettingsLifecycle(),
                        (WifiManager) context.getSystemService(WifiManager.class),
                        this.mMetricsFeatureProvider);
        this.mWifiDetailPreferenceController2 = newInstance;
        this.mControllers.add(newInstance);
        WifiAutoConnectPreferenceController2 wifiAutoConnectPreferenceController2 =
                new WifiAutoConnectPreferenceController2(context);
        wifiAutoConnectPreferenceController2.setWifiEntry(wifiEntry);
        this.mControllers.add(wifiAutoConnectPreferenceController2);
        AddDevicePreferenceController2 addDevicePreferenceController2 =
                new AddDevicePreferenceController2(context);
        addDevicePreferenceController2.setWifiEntry(wifiEntry);
        this.mControllers.add(addDevicePreferenceController2);
        this.mControllers.add(new WifiMeteredPreferenceController2(context, wifiEntry));
        WifiPrivacyPreferenceController2 wifiPrivacyPreferenceController2 =
                new WifiPrivacyPreferenceController2(context);
        wifiPrivacyPreferenceController2.setWifiEntry(wifiEntry);
        this.mControllers.add(wifiPrivacyPreferenceController2);
        WifiSubscriptionDetailPreferenceController2 wifiSubscriptionDetailPreferenceController2 =
                new WifiSubscriptionDetailPreferenceController2(context);
        wifiSubscriptionDetailPreferenceController2.setWifiEntry(wifiEntry);
        this.mControllers.add(wifiSubscriptionDetailPreferenceController2);
        ((ArrayList) this.mWifiDialogListeners).add(this.mWifiDetailPreferenceController2);
        return this.mControllers;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i == 1) {
            return VolteConstants.ErrorCode.DECLINE;
        }
        return 0;
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

    public Drawable getMobileDataIcon(int i) {
        return MobileNetworkUtils.getSignalStrengthIcon(getContext(), i, 5, 0, false, false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.wifi_network_details_fragment2;
    }

    public final WifiNetworkDetailsViewModel getWifiNetworkDetailsViewModel() {
        if (this.mWifiNetworkDetailsViewModel == null) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            WifiFeatureProvider wifiFeatureProvider = featureFactoryImpl.getWifiFeatureProvider();
            wifiFeatureProvider.getClass();
            ViewModelStore store = getViewModelStore();
            ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
            CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
            Intrinsics.checkNotNullParameter(store, "store");
            Intrinsics.checkNotNullParameter(factory, "factory");
            ViewModelProviderImpl m =
                    ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                            defaultViewModelCreationExtras,
                            "defaultCreationExtras",
                            store,
                            factory,
                            defaultViewModelCreationExtras);
            KClass modelClass = JvmClassMappingKt.getKotlinClass(WifiNetworkDetailsViewModel.class);
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            String qualifiedName = modelClass.getQualifiedName();
            if (qualifiedName == null) {
                throw new IllegalArgumentException(
                        "Local and anonymous classes can not be ViewModels".toString());
            }
            WifiNetworkDetailsViewModel wifiNetworkDetailsViewModel =
                    (WifiNetworkDetailsViewModel)
                            m.getViewModel$lifecycle_viewmodel_release(
                                    modelClass,
                                    "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                            .concat(qualifiedName));
            wifiFeatureProvider.verboseLog(
                    "WifiFeatureProvider",
                    "getWifiNetworkDetailsViewModel():" + wifiNetworkDetailsViewModel);
            this.mWifiNetworkDetailsViewModel = wifiNetworkDetailsViewModel;
            wifiNetworkDetailsViewModel.mHotspotNetworkData.observe(
                    this,
                    new Observer() { // from class:
                                     // com.android.settings.wifi.details.WifiNetworkDetailsFragment$$ExternalSyntheticLambda0
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            WifiNetworkDetailsFragment.this.onHotspotNetworkChanged(
                                    (WifiNetworkDetailsViewModel.HotspotNetworkData) obj);
                        }
                    });
        }
        return this.mWifiNetworkDetailsViewModel;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        String string = getArguments().getString("key_chosen_wifientry_key");
        setupNetworksDetailTracker();
        ((WifiPrivacyPreferenceController) use(WifiPrivacyPreferenceController.class))
                .setWifiEntryKey(string);
        ((CertificateDetailsPreferenceController) use(CertificateDetailsPreferenceController.class))
                .setWifiEntry(this.mNetworkDetailsTracker.getWifiEntry());
        ((ServerNamePreferenceController) use(ServerNamePreferenceController.class))
                .setWifiEntry(this.mNetworkDetailsTracker.getWifiEntry());
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mOnlyAvailableForAdmins = true;
        this.mIsUiRestricted = isUiRestricted();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (getActivity() == null || this.mWifiDetailPreferenceController2 == null) {
            return null;
        }
        WifiEntry wifiEntry = this.mNetworkDetailsTracker.getWifiEntry();
        FragmentActivity context = getActivity();
        Intrinsics.checkNotNullParameter(context, "context");
        return new WifiDialog2(context, this, wifiEntry, 2, 0, true, 128);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        WifiEntry wifiEntry;
        if (!this.mIsUiRestricted) {
            NetworkDetailsTracker networkDetailsTracker = this.mNetworkDetailsTracker;
            if ((networkDetailsTracker == null
                            || (wifiEntry = networkDetailsTracker.getWifiEntry()) == null)
                    ? false
                    : wifiEntry.isSaved()) {
                MenuItem add = menu.add(0, 1, 0, R.string.wifi_modify);
                add.setIcon(android.R.drawable.ic_popup_sync_4);
                add.setShowAsAction(2);
            }
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mWorkerThread.quit();
        super.onDestroy();
    }

    public void onHotspotNetworkChanged(
            WifiNetworkDetailsViewModel.HotspotNetworkData hotspotNetworkData) {
        Preference preference;
        Preference preference2;
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen == null) {
            return;
        }
        if (hotspotNetworkData == null) {
            preferenceScreen.findPreference("hotspot_device_details_category").setVisible(false);
            preferenceScreen.findPreference("hotspot_connection_category").setVisible(false);
            WifiDetailPreferenceController2 wifiDetailPreferenceController2 =
                    this.mWifiDetailPreferenceController2;
            if (wifiDetailPreferenceController2 == null
                    || (preference2 = wifiDetailPreferenceController2.mSignalStrengthPref)
                            == null) {
                return;
            }
            preference2.setTitle(R.string.wifi_signal);
            return;
        }
        preferenceScreen.findPreference("hotspot_device_details_category").setVisible(true);
        updateInternetSource(
                hotspotNetworkData.mNetworkType, hotspotNetworkData.mUpstreamConnectionStrength);
        updateBattery(hotspotNetworkData.mIsBatteryCharging, hotspotNetworkData.mBatteryPercentage);
        preferenceScreen.findPreference("hotspot_connection_category").setVisible(true);
        WifiDetailPreferenceController2 wifiDetailPreferenceController22 =
                this.mWifiDetailPreferenceController2;
        if (wifiDetailPreferenceController22 == null
                || (preference = wifiDetailPreferenceController22.mSignalStrengthPref) == null) {
            return;
        }
        preference.setTitle(R.string.hotspot_connection_strength);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (this.mWifiDetailPreferenceController2.canModifyNetwork()) {
            showDialog(1);
        } else {
            RestrictedLockUtils.EnforcedAdmin deviceOwner =
                    RestrictedLockUtilsInternal.getDeviceOwner(getContext());
            if (deviceOwner == null) {
                DevicePolicyManager devicePolicyManager =
                        (DevicePolicyManager) getContext().getSystemService("device_policy");
                int managedProfileId =
                        Utils.getManagedProfileId(
                                (UserManager) getContext().getSystemService("user"),
                                UserHandle.myUserId());
                if (managedProfileId != -10000) {
                    deviceOwner =
                            new RestrictedLockUtils.EnforcedAdmin(
                                    devicePolicyManager.getProfileOwnerAsUser(managedProfileId),
                                    null,
                                    UserHandle.of(managedProfileId));
                }
            }
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(getContext(), deviceOwner);
        }
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mIsUiRestricted) {
            restrictUi();
        }
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onSubmit(WifiDialog2 wifiDialog2) {
        Iterator it = ((ArrayList) this.mWifiDialogListeners).iterator();
        while (it.hasNext()) {
            ((WifiDialog2.WifiDialog2Listener) it.next()).onSubmit(wifiDialog2);
        }
    }

    public final void refreshPreferences() {
        updatePreferenceStates();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        for (AbstractPreferenceController abstractPreferenceController : this.mControllers) {
            if (!(abstractPreferenceController instanceof WifiDetailPreferenceController2)) {
                abstractPreferenceController.displayPreference(preferenceScreen);
            }
        }
        if (this.mIsInstantHotspotFeatureEnabled) {
            getWifiNetworkDetailsViewModel()
                    .setWifiEntry(this.mNetworkDetailsTracker.getWifiEntry());
        }
    }

    public void restrictUi() {
        WifiEntry wifiEntry;
        NetworkDetailsTracker networkDetailsTracker = this.mNetworkDetailsTracker;
        if (networkDetailsTracker != null
                && (wifiEntry = networkDetailsTracker.getWifiEntry()) != null) {
            wifiEntry.setListener(null);
        }
        if (!isUiRestrictedByOnlyAdmin()) {
            this.mEmptyTextView.setText(R.string.wifi_empty_list_user_restricted);
        }
        getPreferenceScreen().removeAll();
    }

    public final void setupNetworksDetailTracker() {
        if (this.mNetworkDetailsTracker != null) {
            return;
        }
        Context context = getContext();
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiNetworkDetailsFrg{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        SimpleClock anonymousClass1 = new AnonymousClass1(ZoneOffset.UTC);
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
                        context,
                        handler,
                        threadHandler,
                        anonymousClass1,
                        string);
    }

    public void updateBattery(boolean z, int i) {
        getPreferenceScreen()
                .findPreference("hotspot_device_details_battery")
                .setSummary(
                        z
                                ? getString(
                                        R.string.hotspot_battery_charging_summary,
                                        com.android.settingslib.Utils.formatPercentage(i))
                                : com.android.settingslib.Utils.formatPercentage(i));
    }

    public void updateInternetSource(int i, int i2) {
        Drawable drawable;
        Preference findPreference =
                getPreferenceScreen().findPreference("hotspot_device_details_internet_source");
        if (i == 2) {
            findPreference.setSummary(R.string.internet_source_wifi);
            Context context = getContext();
            if (i2 < 0) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i2, "Wi-Fi level is out of range! level:", "WifiUtils");
                i2 = 0;
            } else if (i2 >= 5) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i2, "Wi-Fi level is out of range! level:", "WifiUtils");
                i2 = 4;
            }
            drawable = context.getDrawable(WifiUtils.WIFI_PIE[i2]);
        } else if (i == 1) {
            findPreference.setSummary(R.string.internet_source_mobile_data);
            drawable = getMobileDataIcon(i2);
        } else if (i == 3) {
            findPreference.setSummary(R.string.internet_source_ethernet);
            drawable = getContext().getDrawable(R.drawable.ic_settings_ethernet);
        } else {
            findPreference.setSummary(R.string.summary_placeholder);
            drawable = null;
        }
        if (drawable != null) {
            drawable.setTintList(
                    com.android.settingslib.Utils.getColorAttr(
                            getContext(), android.R.attr.colorControlNormal));
        }
        findPreference.setIcon(drawable);
    }
}
