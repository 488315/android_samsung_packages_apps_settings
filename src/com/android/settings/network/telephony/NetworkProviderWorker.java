package com.android.settings.network.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.network.InternetUpdater;
import com.android.settings.network.MobileDataContentObserver;
import com.android.settings.network.MobileDataEnabledListener;
import com.android.settings.network.SubscriptionsChangeListener;
import com.android.settings.wifi.slice.WifiScanWorker;
import com.android.settingslib.mobile.MobileMappings;

import java.util.Collections;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkProviderWorker extends WifiScanWorker
        implements SignalStrengthListener.Callback,
                MobileDataEnabledListener.Client,
                DataConnectivityListener.Client,
                InternetUpdater.InternetChangeListener,
                SubscriptionsChangeListener.SubscriptionsChangeListenerClient {
    public static final /* synthetic */ int $r8$clinit = 0;
    public MobileMappings.Config mConfig;
    public final AnonymousClass1 mConnectionChangeReceiver;
    public final DataConnectivityListener mConnectivityListener;
    public final Context mContext;
    public final MobileDataEnabledListener mDataEnabledListener;
    public int mDefaultDataSubId;
    public final Handler mHandler;
    public int mInternetType;
    public DataContentObserver mMobileDataObserver;
    public final SignalStrengthListener mSignalStrengthListener;
    public final SubscriptionsChangeListener mSubscriptionsListener;
    final NetworkProviderTelephonyCallback mTelephonyCallback;
    public TelephonyDisplayInfo mTelephonyDisplayInfo;
    public TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataContentObserver extends ContentObserver {
        public final NetworkProviderWorker mNetworkProviderWorker;

        public DataContentObserver(Handler handler, NetworkProviderWorker networkProviderWorker) {
            super(handler);
            Log.d("NetworkProviderWorker", "DataContentObserver: init");
            this.mNetworkProviderWorker = networkProviderWorker;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Log.d("NetworkProviderWorker", "DataContentObserver: onChange");
            this.mNetworkProviderWorker.notifySliceChange();
        }

        public final void register(Context context, int i) {
            Uri observableUri = MobileDataContentObserver.getObservableUri(context, i);
            Log.d("NetworkProviderWorker", "DataContentObserver: register uri:" + observableUri);
            context.getContentResolver().registerContentObserver(observableUri, false, this);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NetworkProviderTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.DataConnectionStateListener,
                    TelephonyCallback.DisplayInfoListener,
                    TelephonyCallback.ServiceStateListener {
        public NetworkProviderTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
        public final void onDataConnectionStateChanged(int i, int i2) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "onDataConnectionStateChanged: networkType=",
                    " state=",
                    i2,
                    i,
                    "NetworkProviderWorker");
            NetworkProviderWorker.this.notifySliceChange();
        }

        @Override // android.telephony.TelephonyCallback.DisplayInfoListener
        public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            Log.d(
                    "NetworkProviderWorker",
                    "onDisplayInfoChanged: telephonyDisplayInfo=" + telephonyDisplayInfo);
            NetworkProviderWorker networkProviderWorker = NetworkProviderWorker.this;
            networkProviderWorker.mTelephonyDisplayInfo = telephonyDisplayInfo;
            networkProviderWorker.notifySliceChange();
        }

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            Log.d(
                    "NetworkProviderWorker",
                    "onServiceStateChanged voiceState="
                            + serviceState.getState()
                            + " dataState="
                            + serviceState.getDataRegistrationState());
            NetworkProviderWorker.this.notifySliceChange();
        }
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.settings.network.telephony.NetworkProviderWorker$1] */
    public NetworkProviderWorker(Context context, Uri uri) {
        super(context, uri);
        this.mDefaultDataSubId = -1;
        this.mConnectionChangeReceiver =
                new BroadcastReceiver() { // from class:
                    // com.android.settings.network.telephony.NetworkProviderWorker.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (intent.getAction()
                                .equals(
                                        "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                            Log.d(
                                    "NetworkProviderWorker",
                                    "ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                            NetworkProviderWorker networkProviderWorker =
                                    NetworkProviderWorker.this;
                            int i = NetworkProviderWorker.$r8$clinit;
                            networkProviderWorker.updateListener$1();
                        }
                    }
                };
        this.mConfig = null;
        this.mTelephonyDisplayInfo = new TelephonyDisplayInfo(0, 0);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mMobileDataObserver = new DataContentObserver(handler, this);
        this.mContext = context;
        this.mDefaultDataSubId = getDefaultDataSubscriptionId();
        Log.d("NetworkProviderWorker", "Init, SubId: " + this.mDefaultDataSubId);
        this.mTelephonyManager =
                ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(this.mDefaultDataSubId);
        this.mTelephonyCallback = new NetworkProviderTelephonyCallback();
        this.mSubscriptionsListener = new SubscriptionsChangeListener(context, this);
        this.mDataEnabledListener = new MobileDataEnabledListener(context, this);
        this.mConnectivityListener = new DataConnectivityListener(context, this);
        this.mSignalStrengthListener = new SignalStrengthListener(context, this);
        this.mConfig = getConfig(context);
        this.mInternetType = new InternetUpdater(context, getLifecycle(), this).mInternetType;
    }

    @Override // com.android.settings.wifi.slice.WifiScanWorker, java.io.Closeable,
              // java.lang.AutoCloseable
    public final void close() {
        this.mMobileDataObserver = null;
        super.close();
    }

    @Override // com.android.settings.wifi.slice.WifiScanWorker
    public final int getApRowCount() {
        return 6;
    }

    public MobileMappings.Config getConfig(Context context) {
        return MobileMappings.Config.readConfig(context);
    }

    public int getDefaultDataSubscriptionId() {
        return SubscriptionManager.getDefaultDataSubscriptionId();
    }

    @Override // com.android.settings.network.InternetUpdater.InternetChangeListener,
              // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public final void onAirplaneModeChanged(boolean z) {
        Log.d("NetworkProviderWorker", "onAirplaneModeChanged");
        notifySliceChange();
    }

    @Override // com.android.settings.network.telephony.DataConnectivityListener.Client
    public final void onDataConnectivityChange() {
        Log.d("NetworkProviderWorker", "onDataConnectivityChange");
        notifySliceChange();
    }

    @Override // com.android.settings.network.InternetUpdater.InternetChangeListener
    public final void onInternetTypeChanged(int i) {
        int i2 = this.mInternetType;
        if (i2 == i) {
            return;
        }
        boolean z = i2 == 4 || i == 4;
        this.mInternetType = i;
        if (z) {
            notifySliceChange();
        }
    }

    @Override // com.android.settings.network.MobileDataEnabledListener.Client
    public final void onMobileDataEnabledChange() {
        Log.d("NetworkProviderWorker", "onMobileDataEnabledChange");
        notifySliceChange();
    }

    @Override // com.android.settings.network.telephony.SignalStrengthListener.Callback
    public final void onSignalStrengthChanged() {
        Log.d("NetworkProviderWorker", "onSignalStrengthChanged");
        notifySliceChange();
    }

    @Override // com.android.settings.wifi.slice.WifiScanWorker,
              // com.android.settings.slices.SliceBackgroundWorker
    public final void onSlicePinned() {
        Log.d("NetworkProviderWorker", "onSlicePinned");
        this.mMobileDataObserver.register(this.mContext, this.mDefaultDataSubId);
        this.mSubscriptionsListener.start();
        this.mDataEnabledListener.start(this.mDefaultDataSubId);
        DataConnectivityListener dataConnectivityListener = this.mConnectivityListener;
        dataConnectivityListener.mConnectivityManager.registerNetworkCallback(
                dataConnectivityListener.mNetworkRequest,
                dataConnectivityListener,
                dataConnectivityListener.mContext.getMainThreadHandler());
        this.mSignalStrengthListener.resume();
        TelephonyManager telephonyManager = this.mTelephonyManager;
        Handler handler = this.mHandler;
        Objects.requireNonNull(handler);
        telephonyManager.registerTelephonyCallback(
                new MediaRoute2Provider$$ExternalSyntheticLambda0(handler),
                this.mTelephonyCallback);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        this.mContext.registerReceiver(this.mConnectionChangeReceiver, intentFilter);
        super.onSlicePinned();
    }

    @Override // com.android.settings.wifi.slice.WifiScanWorker,
              // com.android.settings.slices.SliceBackgroundWorker
    public final void onSliceUnpinned() {
        Log.d("NetworkProviderWorker", "onSliceUnpinned");
        DataContentObserver dataContentObserver = this.mMobileDataObserver;
        Context context = this.mContext;
        dataContentObserver.getClass();
        Log.d("NetworkProviderWorker", "DataContentObserver: unregister");
        context.getContentResolver().unregisterContentObserver(dataContentObserver);
        this.mSubscriptionsListener.stop();
        this.mDataEnabledListener.stop();
        DataConnectivityListener dataConnectivityListener = this.mConnectivityListener;
        dataConnectivityListener.mConnectivityManager.unregisterNetworkCallback(
                dataConnectivityListener);
        this.mSignalStrengthListener.pause();
        this.mTelephonyManager.unregisterTelephonyCallback(this.mTelephonyCallback);
        AnonymousClass1 anonymousClass1 = this.mConnectionChangeReceiver;
        if (anonymousClass1 != null) {
            this.mContext.unregisterReceiver(anonymousClass1);
        }
        super.onSliceUnpinned();
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public final void onSubscriptionsChanged() {
        Log.d("NetworkProviderWorker", "onSubscriptionsChanged");
        updateListener$1();
    }

    public final void updateListener$1() {
        int defaultDataSubscriptionId = getDefaultDataSubscriptionId();
        if (this.mDefaultDataSubId == defaultDataSubscriptionId) {
            Log.d("NetworkProviderWorker", "DDS: no change");
            return;
        }
        this.mDefaultDataSubId = defaultDataSubscriptionId;
        Log.d("NetworkProviderWorker", "DDS: defaultDataSubId:" + this.mDefaultDataSubId);
        if (SubscriptionManager.isUsableSubscriptionId(defaultDataSubscriptionId)) {
            this.mTelephonyManager.unregisterTelephonyCallback(this.mTelephonyCallback);
            DataContentObserver dataContentObserver = this.mMobileDataObserver;
            Context context = this.mContext;
            dataContentObserver.getClass();
            Log.d("NetworkProviderWorker", "DataContentObserver: unregister");
            context.getContentResolver().unregisterContentObserver(dataContentObserver);
            this.mSignalStrengthListener.updateSubscriptionIds(
                    Collections.singleton(Integer.valueOf(defaultDataSubscriptionId)));
            TelephonyManager createForSubscriptionId =
                    this.mTelephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
            this.mTelephonyManager = createForSubscriptionId;
            Handler handler = this.mHandler;
            Objects.requireNonNull(handler);
            createForSubscriptionId.registerTelephonyCallback(
                    new MediaRoute2Provider$$ExternalSyntheticLambda0(handler),
                    this.mTelephonyCallback);
            this.mMobileDataObserver.register(this.mContext, defaultDataSubscriptionId);
            this.mConfig = getConfig(this.mContext);
        } else {
            this.mSignalStrengthListener.updateSubscriptionIds(Collections.emptySet());
        }
        notifySliceChange();
    }
}
