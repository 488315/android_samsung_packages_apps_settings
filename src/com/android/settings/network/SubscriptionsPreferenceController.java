package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;

import androidx.collection.ArrayMap;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.network.telephony.DataConnectivityListener;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.SignalStrengthListener;
import com.android.settings.network.telephony.TelephonyDisplayInfoListener;
import com.android.settings.widget.MutableGearPreference;
import com.android.settings.wifi.WifiPickerTrackerHelper;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.mobile.MobileMappings;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SubscriptionsPreferenceController extends AbstractPreferenceController
        implements LifecycleObserver,
                SubscriptionsChangeListener.SubscriptionsChangeListenerClient,
                MobileDataEnabledListener.Client,
                DataConnectivityListener.Client,
                SignalStrengthListener.Callback,
                TelephonyCallback.CarrierNetworkListener,
                WifiPickerTracker.WifiPickerTrackerCallback {
    public boolean mCarrierNetworkChangeMode;
    public MobileMappings.Config mConfig;
    final BroadcastReceiver mConnectionChangeReceiver;
    public final DataConnectivityListener mConnectivityListener;
    public final MobileDataEnabledListener mDataEnabledListener;
    public PreferenceGroup mPreferenceGroup;
    public final String mPreferenceGroupKey;
    public final SignalStrengthListener mSignalStrengthListener;
    public final int mStartOrder;
    public MutableGearPreference mSubsGearPref;
    public final SubsPrefCtrlInjector mSubsPrefCtrlInjector;
    public final SubscriptionManager mSubscriptionManager;
    public final ArrayMap mSubscriptionPreferences;
    public final SubscriptionsChangeListener mSubscriptionsListener;
    public TelephonyDisplayInfo mTelephonyDisplayInfo;
    public final TelephonyDisplayInfoListener mTelephonyDisplayInfoListener;
    public final TelephonyManager mTelephonyManager;
    public final NetworkMobileProviderController mUpdateListener;
    public final WifiManager mWifiManager;
    WifiPickerTrackerHelper mWifiPickerTrackerHelper;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SubsPrefCtrlInjector {}

    /* JADX WARN: Code restructure failed: missing block: B:51:0x014b, code lost:

       if (r13 != false) goto L79;
    */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean $r8$lambda$BR291_GeOIF2WODPNqYWY3PKUzY(
            com.android.settings.network.SubscriptionsPreferenceController r13,
            android.telephony.SubscriptionInfo r14) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.SubscriptionsPreferenceController.$r8$lambda$BR291_GeOIF2WODPNqYWY3PKUzY(com.android.settings.network.SubscriptionsPreferenceController,"
                    + " android.telephony.SubscriptionInfo):boolean");
    }

    public SubscriptionsPreferenceController(
            Context context,
            Lifecycle lifecycle,
            NetworkMobileProviderController networkMobileProviderController) {
        super(context);
        this.mConnectionChangeReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.network.SubscriptionsPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        if (!action.equals(
                                "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                            if (action.equals("android.net.wifi.supplicant.CONNECTION_CHANGE")) {
                                SubscriptionsPreferenceController.this.update$2$1();
                            }
                        } else {
                            SubscriptionsPreferenceController subscriptionsPreferenceController =
                                    SubscriptionsPreferenceController.this;
                            SubsPrefCtrlInjector subsPrefCtrlInjector =
                                    subscriptionsPreferenceController.mSubsPrefCtrlInjector;
                            Context context3 =
                                    ((AbstractPreferenceController)
                                                    subscriptionsPreferenceController)
                                            .mContext;
                            subsPrefCtrlInjector.getClass();
                            subscriptionsPreferenceController.mConfig =
                                    MobileMappings.Config.readConfig(context3);
                            SubscriptionsPreferenceController.this.update$2$1();
                        }
                    }
                };
        this.mConfig = null;
        this.mTelephonyDisplayInfo = new TelephonyDisplayInfo(0, 0);
        this.mUpdateListener = networkMobileProviderController;
        this.mPreferenceGroupKey = NetworkMobileProviderController.PREF_KEY_PROVIDER_MOBILE_NETWORK;
        this.mStartOrder = 10;
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mSubscriptionManager =
                ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                        .createForAllUserProfiles();
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mSubscriptionPreferences = new ArrayMap(0);
        this.mSubscriptionsListener = new SubscriptionsChangeListener(context, this);
        this.mDataEnabledListener = new MobileDataEnabledListener(context, this);
        this.mConnectivityListener = new DataConnectivityListener(context, this);
        this.mSignalStrengthListener = new SignalStrengthListener(context, this);
        TelephonyDisplayInfoListener telephonyDisplayInfoListener =
                new TelephonyDisplayInfoListener();
        telephonyDisplayInfoListener.mBaseTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        telephonyDisplayInfoListener.mCallback = this;
        telephonyDisplayInfoListener.mListeners = new HashMap();
        telephonyDisplayInfoListener.mDisplayInfos = new HashMap();
        this.mTelephonyDisplayInfoListener = telephonyDisplayInfoListener;
        lifecycle.addObserver(this);
        this.mWifiPickerTrackerHelper = new WifiPickerTrackerHelper(lifecycle, context, this);
        this.mSubsPrefCtrlInjector = new SubsPrefCtrlInjector();
        this.mConfig = MobileMappings.Config.readConfig(this.mContext);
    }

    public void connectCarrierNetwork() {
        WifiPickerTrackerHelper wifiPickerTrackerHelper;
        MergedCarrierEntry mergedCarrierEntry;
        if (MobileNetworkUtils.isMobileDataEnabled(this.mContext)
                && (wifiPickerTrackerHelper = this.mWifiPickerTrackerHelper) != null
                && (mergedCarrierEntry =
                                wifiPickerTrackerHelper.mWifiPickerTracker.getMergedCarrierEntry())
                        != null
                && mergedCarrierEntry.canConnect()) {
            mergedCarrierEntry.connect(null);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(this.mPreferenceGroupKey);
        update$2$1();
    }

    public Drawable getIcon(int i) {
        SubscriptionInfo defaultDataSubscriptionInfo;
        MergedCarrierEntry mergedCarrierEntry;
        TelephonyManager createForSubscriptionId =
                this.mTelephonyManager.createForSubscriptionId(i);
        SignalStrength signalStrength = createForSubscriptionId.getSignalStrength();
        boolean z = false;
        int level = signalStrength == null ? 0 : signalStrength.getLevel();
        WifiPickerTrackerHelper wifiPickerTrackerHelper = this.mWifiPickerTrackerHelper;
        boolean z2 =
                wifiPickerTrackerHelper != null && wifiPickerTrackerHelper.isCarrierNetworkActive();
        int i2 = 5;
        if (z2) {
            WifiPickerTrackerHelper wifiPickerTrackerHelper2 = this.mWifiPickerTrackerHelper;
            if (wifiPickerTrackerHelper2 == null
                    || (mergedCarrierEntry =
                                    wifiPickerTrackerHelper2.mWifiPickerTracker
                                            .getMergedCarrierEntry())
                            == null
                    || (level = mergedCarrierEntry.mLevel) < 0) {
                level = 0;
            }
        } else if (shouldInflateSignalStrength(i)) {
            level++;
            i2 = 6;
        }
        int i3 = level;
        int i4 = i2;
        Drawable drawable =
                this.mContext.getDrawable(R.drawable.ic_signal_strength_zero_bar_no_internet);
        ServiceState serviceState = createForSubscriptionId.getServiceState();
        NetworkRegistrationInfo networkRegistrationInfo =
                serviceState == null ? null : serviceState.getNetworkRegistrationInfo(2, 1);
        boolean isRegistered =
                networkRegistrationInfo == null ? false : networkRegistrationInfo.isRegistered();
        boolean z3 = serviceState != null && serviceState.getState() == 0;
        if (createForSubscriptionId.isDataEnabled()
                || (((defaultDataSubscriptionInfo =
                                                this.mSubscriptionManager
                                                        .getDefaultDataSubscriptionInfo())
                                        == null
                                || defaultDataSubscriptionInfo.getSubscriptionId() != i)
                        && createForSubscriptionId.isMobileDataPolicyEnabled(3))) {
            z = true;
        }
        if (isRegistered || z3 || z2) {
            SubsPrefCtrlInjector subsPrefCtrlInjector = this.mSubsPrefCtrlInjector;
            boolean z4 = this.mCarrierNetworkChangeMode;
            subsPrefCtrlInjector.getClass();
            drawable = MobileNetworkUtils.getSignalStrengthIcon(this.mContext, i3, i4, 0, !z, z4);
        }
        SubsPrefCtrlInjector subsPrefCtrlInjector2 = this.mSubsPrefCtrlInjector;
        Context context = this.mContext;
        subsPrefCtrlInjector2.getClass();
        if (MobileNetworkUtils.activeNetworkIsCellular(context) || z2) {
            drawable.setTint(
                    Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorAccent));
        }
        return drawable;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        List activeSubscriptions;
        WifiPickerTrackerHelper wifiPickerTrackerHelper;
        return (!(Settings.Global.getInt(
                                        this.mSubscriptionsListener.mContext.getContentResolver(),
                                        "airplane_mode_on",
                                        0)
                                != 0)
                        || (this.mWifiManager.isWifiEnabled()
                                && (wifiPickerTrackerHelper = this.mWifiPickerTrackerHelper) != null
                                && wifiPickerTrackerHelper.isCarrierNetworkActive()))
                && (activeSubscriptions =
                                SubscriptionUtil.getActiveSubscriptions(this.mSubscriptionManager))
                        != null
                && activeSubscriptions.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.settings.network.SubscriptionsPreferenceController$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return SubscriptionsPreferenceController
                                                        .$r8$lambda$BR291_GeOIF2WODPNqYWY3PKUzY(
                                                                SubscriptionsPreferenceController
                                                                        .this,
                                                                (SubscriptionInfo) obj);
                                            }
                                        })
                                .count()
                        >= 1;
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public final void onAirplaneModeChanged(boolean z) {
        update$2$1();
    }

    @Override // android.telephony.TelephonyCallback.CarrierNetworkListener
    public final void onCarrierNetworkChange(boolean z) {
        this.mCarrierNetworkChangeMode = z;
        update$2$1();
    }

    @Override // com.android.settings.network.telephony.DataConnectivityListener.Client
    public final void onDataConnectivityChange() {
        update$2$1();
    }

    @Override // com.android.settings.network.MobileDataEnabledListener.Client
    public final void onMobileDataEnabledChange() {
        update$2$1();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mSubscriptionsListener.stop();
        this.mDataEnabledListener.stop();
        DataConnectivityListener dataConnectivityListener = this.mConnectivityListener;
        dataConnectivityListener.mConnectivityManager.unregisterNetworkCallback(
                dataConnectivityListener);
        this.mSignalStrengthListener.pause();
        TelephonyDisplayInfoListener telephonyDisplayInfoListener =
                this.mTelephonyDisplayInfoListener;
        for (Integer num : ((HashMap) telephonyDisplayInfoListener.mListeners).keySet()) {
            telephonyDisplayInfoListener
                    .mBaseTelephonyManager
                    .createForSubscriptionId(num.intValue())
                    .listen(
                            (PhoneStateListener)
                                    ((HashMap) telephonyDisplayInfoListener.mListeners).get(num),
                            0);
        }
        BroadcastReceiver broadcastReceiver = this.mConnectionChangeReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        this.mSubscriptionsListener.start();
        MobileDataEnabledListener mobileDataEnabledListener = this.mDataEnabledListener;
        this.mSubsPrefCtrlInjector.getClass();
        mobileDataEnabledListener.start(SubscriptionManager.getDefaultDataSubscriptionId());
        DataConnectivityListener dataConnectivityListener = this.mConnectivityListener;
        dataConnectivityListener.mConnectivityManager.registerNetworkCallback(
                dataConnectivityListener.mNetworkRequest,
                dataConnectivityListener,
                dataConnectivityListener.mContext.getMainThreadHandler());
        this.mSignalStrengthListener.resume();
        TelephonyDisplayInfoListener telephonyDisplayInfoListener =
                this.mTelephonyDisplayInfoListener;
        for (Integer num : ((HashMap) telephonyDisplayInfoListener.mListeners).keySet()) {
            telephonyDisplayInfoListener
                    .mBaseTelephonyManager
                    .createForSubscriptionId(num.intValue())
                    .listen(
                            (PhoneStateListener)
                                    ((HashMap) telephonyDisplayInfoListener.mListeners).get(num),
                            1048576);
        }
        this.mContext.registerReceiver(
                this.mConnectionChangeReceiver,
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED",
                        "android.net.wifi.supplicant.CONNECTION_CHANGE"));
        update$2$1();
    }

    @Override // com.android.settings.network.telephony.SignalStrengthListener.Callback
    public final void onSignalStrengthChanged() {
        update$2$1();
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public final void onSubscriptionsChanged() {
        this.mSubsPrefCtrlInjector.getClass();
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        MobileDataEnabledListener mobileDataEnabledListener = this.mDataEnabledListener;
        if (defaultDataSubscriptionId != mobileDataEnabledListener.mSubId) {
            mobileDataEnabledListener.stop();
            this.mDataEnabledListener.start(defaultDataSubscriptionId);
        }
        update$2$1();
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onWifiEntriesChanged() {
        update$2$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        update$2$1();
    }

    public boolean shouldInflateSignalStrength(int i) {
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) this.mContext.getSystemService(CarrierConfigManager.class);
        PersistableBundle configForSubId =
                carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(i) : null;
        return configForSubId != null
                && configForSubId.getBoolean("inflate_signal_strength_bool", false);
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update$2$1() {
        /*
            Method dump skipped, instructions count: 501
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.SubscriptionsPreferenceController.update$2$1():void");
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedNetworksChanged() {}

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedSubscriptionsChanged() {}
}
