package com.samsung.android.settings.wifi.mobileap.smartpriority;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LifecycleObserver;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApSmartPrioritySpeedController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnStop, OnPause, OnDestroy {
    private static final int CMD_CHECK_MOBILEDATA_SPEED = 1;
    private static final int CMD_CHECK_MOBILEDATA_SPEED_TIME_INTERVAL = 20000;
    private static final boolean DBG = Utils.MHSDBG;
    private static final String PREFERENCE_PREFIX = "DownstreamBandwidth : ";
    private static final String TAG = "WifiApSmartPrioritySpeedController";
    ConnectivityManager mConnectivityManager;
    Context mContext;
    SemWifiApHandler mSemWifiApHandler;
    private Preference mWifiApSmartPrioritySpeedPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SemWifiApHandler extends Handler {
        public SemWifiApHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 1) {
                return;
            }
            WifiApSmartPrioritySpeedController wifiApSmartPrioritySpeedController =
                    WifiApSmartPrioritySpeedController.this;
            wifiApSmartPrioritySpeedController.refreshTitle();
            wifiApSmartPrioritySpeedController.mSemWifiApHandler.sendEmptyMessageDelayed(1, 20000L);
        }
    }

    public WifiApSmartPrioritySpeedController(Context context, String str) {
        super(context, str);
        this.mContext = context;
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mSemWifiApHandler = new SemWifiApHandler(Looper.getMainLooper());
    }

    private String getLinkDownStreamBandwidthMbps() {
        Network activeNetwork = this.mConnectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            Log.i(TAG, "activeNetwork is null");
            return "0.00";
        }
        NetworkCapabilities networkCapabilities =
                this.mConnectivityManager.getNetworkCapabilities(activeNetwork);
        if (networkCapabilities == null) {
            Log.i(TAG, "nc is null");
            return "0.00";
        }
        int linkDownstreamBandwidthKbps =
                networkCapabilities.getLinkDownstreamBandwidthKbps() / 1000;
        Log.i(
                TAG,
                "uploadSpeed in Mbps:"
                        + (networkCapabilities.getLinkUpstreamBandwidthKbps() / 1000));
        Log.i(TAG, "downloadSpeed in Mbps :" + linkDownstreamBandwidthKbps);
        return linkDownstreamBandwidthKbps + " Mbps";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshTitle() {
        this.mWifiApSmartPrioritySpeedPreference.setTitle(
                PREFERENCE_PREFIX + getLinkDownStreamBandwidthMbps());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        return TextUtils.equals(preference.getKey(), getPreferenceKey());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        if (this.mSemWifiApHandler.hasMessages(1)) {
            this.mSemWifiApHandler.removeMessages(1);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mSemWifiApHandler.hasMessages(1)) {
            this.mSemWifiApHandler.removeMessages(1);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        refreshTitle();
        this.mSemWifiApHandler.sendEmptyMessageDelayed(1, 20000L);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        if (this.mSemWifiApHandler.hasMessages(1)) {
            this.mSemWifiApHandler.removeMessages(1);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
