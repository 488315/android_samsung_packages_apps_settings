package com.android.settings.wifi.repository;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;

import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.lifecycle.MutableLiveData;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.samsung.android.wifi.SemWifiApCust;
import com.sec.ims.settings.ImsProfile;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SharedConnectivityRepository {
    public static Context mAppContext;
    public final SharedConnectivityRepository$$ExternalSyntheticLambda1 mLaunchSettingsRunnable;
    public final SharedConnectivityManager mManager;
    MutableLiveData mSettingsState;
    public final SharedConnectivityRepository$$ExternalSyntheticLambda0 mWorkerExecutor;
    public final HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ClientCallback implements SharedConnectivityClientCallback {
        public ClientCallback() {}

        public final void onHotspotNetworkConnectionStatusChanged(
                HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
            SharedConnectivityRepository.this.getClass();
            SharedConnectivityRepository.log(
                    "onHotspotNetworkConnectionStatusChanged(), status:"
                            + hotspotNetworkConnectionStatus);
        }

        public final void onHotspotNetworksUpdated(List list) {
            SharedConnectivityRepository.this.getClass();
            SharedConnectivityRepository.log("onHotspotNetworksUpdated(), networks:" + list);
        }

        public final void onKnownNetworkConnectionStatusChanged(
                KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
            SharedConnectivityRepository.this.getClass();
            SharedConnectivityRepository.log(
                    "onKnownNetworkConnectionStatusChanged(), status:"
                            + knownNetworkConnectionStatus);
        }

        public final void onKnownNetworksUpdated(List list) {
            SharedConnectivityRepository.this.getClass();
            SharedConnectivityRepository.log("onKnownNetworksUpdated(), networks:" + list);
        }

        public final void onRegisterCallbackFailed(Exception exc) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "onRegisterCallbackFailed(), e:", exc, "SharedConnectivityRepository");
        }

        public final void onServiceConnected() {
            SharedConnectivitySettingsState settingsState =
                    SharedConnectivityRepository.this.mManager.getSettingsState();
            Log.d(
                    "SharedConnectivityRepository",
                    "onServiceConnected(), Manager#getSettingsState:" + settingsState);
            SharedConnectivityRepository.this.mSettingsState.postValue(settingsState);
        }

        public final void onServiceDisconnected() {
            SharedConnectivityRepository.this.getClass();
            SharedConnectivityRepository.log("onServiceDisconnected()");
        }

        public final void onSharedConnectivitySettingsChanged(
                SharedConnectivitySettingsState sharedConnectivitySettingsState) {
            Log.d(
                    "SharedConnectivityRepository",
                    "onSharedConnectivitySettingsChanged(), state:"
                            + sharedConnectivitySettingsState);
            SharedConnectivityRepository.this.mSettingsState.postValue(
                    sharedConnectivitySettingsState);
        }
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.settings.wifi.repository.SharedConnectivityRepository$$ExternalSyntheticLambda1] */
    public SharedConnectivityRepository(Context context, boolean z) {
        ClientCallback clientCallback = new ClientCallback();
        HandlerThread handlerThread = new HandlerThread("SharedConnectivityRepository");
        this.mWorkerThread = handlerThread;
        SharedConnectivityRepository$$ExternalSyntheticLambda0
                sharedConnectivityRepository$$ExternalSyntheticLambda0 =
                        new SharedConnectivityRepository$$ExternalSyntheticLambda0(this);
        this.mWorkerExecutor = sharedConnectivityRepository$$ExternalSyntheticLambda0;
        this.mLaunchSettingsRunnable =
                new Runnable() { // from class:
                                 // com.android.settings.wifi.repository.SharedConnectivityRepository$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SharedConnectivityRepository.this.handleLaunchSettings();
                    }
                };
        this.mSettingsState = new MutableLiveData();
        mAppContext = context;
        if (z) {
            SharedConnectivityManager sharedConnectivityManager =
                    (SharedConnectivityManager)
                            context.getSystemService(SharedConnectivityManager.class);
            this.mManager = sharedConnectivityManager;
            if (sharedConnectivityManager == null) {
                Log.w("SharedConnectivityRepository", "Failed to get SharedConnectivityManager");
            } else {
                handlerThread.start();
                sharedConnectivityManager.registerCallback(
                        sharedConnectivityRepository$$ExternalSyntheticLambda0, clientCallback);
            }
        }
    }

    public static boolean isDeviceConfigEnabled() {
        Context context;
        int i;
        boolean z =
                !Rune.isChinaModel()
                        && SystemProperties.getInt(
                                        TouchPadGestureSettingsController.FIRST_API_LEVEL, -1)
                                >= 35;
        if (SemWifiApCust.DBG
                && (context = mAppContext) != null
                && (i =
                                Settings.Secure.getInt(
                                        context.getContentResolver(),
                                        "vendor.wifiap.ih.enable",
                                        -1))
                        >= 0) {
            z = i == 1;
        }
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m(
                        "Instant Hotspot isDeviceConfigEnabled ", " rollout : ", z);
        m.append(
                DeviceConfig.getBoolean(ImsProfile.PDN_WIFI, "shared_connectivity_enabled", false));
        Log.i("SharedConnectivityRepository", m.toString());
        return z;
    }

    public static void log(String str) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getWifiFeatureProvider().verboseLog("SharedConnectivityRepository", str);
    }

    public final MutableLiveData getSettingsState() {
        return this.mSettingsState;
    }

    public void handleLaunchSettings() {
        SharedConnectivityManager sharedConnectivityManager = this.mManager;
        if (sharedConnectivityManager == null) {
            return;
        }
        SharedConnectivitySettingsState settingsState =
                sharedConnectivityManager.getSettingsState();
        log("handleLaunchSettings(), state:" + settingsState);
        if (settingsState == null) {
            Log.e(
                    "SharedConnectivityRepository",
                    "No SettingsState to launch Instant Hotspot settings");
            return;
        }
        PendingIntent instantTetherSettingsPendingIntent =
                settingsState.getInstantTetherSettingsPendingIntent();
        if (instantTetherSettingsPendingIntent == null) {
            Log.e(
                    "SharedConnectivityRepository",
                    "No PendingIntent to launch Instant Hotspot settings");
        } else {
            sendSettingsIntent(instantTetherSettingsPendingIntent);
        }
    }

    public void sendSettingsIntent(PendingIntent pendingIntent) {
        try {
            log("sendSettingsIntent(), sent intent:" + pendingIntent);
            pendingIntent.send(
                    ActivityOptions.makeBasic()
                            .setPendingIntentBackgroundActivityStartMode(1)
                            .toBundle());
        } catch (PendingIntent.CanceledException e) {
            Log.e("SharedConnectivityRepository", "Failed to launch Instant Hotspot settings", e);
        }
    }
}
