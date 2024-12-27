package com.android.settings.wifi.tether;

import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.text.BidiFormatter;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.network.tether.TetheringManagerModel;
import com.android.settings.widget.GenericSwitchController;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTetherPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                OnStart,
                OnStop,
                SwitchWidgetController.OnSwitchChangeListener {
    boolean mIsDataSaverEnabled;
    public final boolean mIsWifiTetheringAllow;
    PrimarySwitchPreference mPreference;
    public int mSoftApState;
    SwitchWidgetController mSwitch;
    TetheringManagerModel mTetheringManagerModel;
    public final WifiManager mWifiManager;
    WifiTetherSoftApManager mWifiTetherSoftApManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.tether.WifiTetherPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    public WifiTetherPreferenceController(
            Context context,
            Lifecycle lifecycle,
            WifiManager wifiManager,
            boolean z,
            boolean z2,
            TetheringManagerModel tetheringManagerModel) {
        super(context);
        this.mIsWifiTetheringAllow = z2;
        if (z2) {
            this.mTetheringManagerModel = tetheringManagerModel;
            this.mWifiManager = wifiManager;
            if (lifecycle != null) {
                lifecycle.addObserver(this);
            }
            if (z) {
                initWifiTetherSoftApManager();
            }
        }
    }

    public final boolean canEnabled() {
        return this.mIsWifiTetheringAllow && !this.mIsDataSaverEnabled;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PrimarySwitchPreference primarySwitchPreference =
                (PrimarySwitchPreference) preferenceScreen.findPreference("wifi_tether");
        this.mPreference = primarySwitchPreference;
        if (primarySwitchPreference == null) {
            return;
        }
        if (this.mSwitch == null) {
            GenericSwitchController genericSwitchController =
                    new GenericSwitchController(primarySwitchPreference);
            this.mSwitch = genericSwitchController;
            genericSwitchController.mListener = this;
            updateSwitch();
        }
        this.mPreference.setEnabled(canEnabled());
        if (this.mIsWifiTetheringAllow) {
            return;
        }
        this.mPreference.setSummary(R.string.not_allowed_by_ent);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "wifi_tether";
    }

    public void handleWifiApStateChanged(int i, int i2) {
        switch (i) {
            case 10:
                this.mPreference.setSummary(R.string.wifi_tether_stopping);
                break;
            case 11:
                this.mSwitch.setChecked(false);
                this.mPreference.setSummary(R.string.wifi_hotspot_off_subtext);
                break;
            case 12:
                this.mPreference.setSummary(R.string.wifi_tether_starting);
                break;
            case 13:
                this.mSwitch.setChecked(true);
                SoftApConfiguration softApConfiguration =
                        this.mWifiManager.getSoftApConfiguration();
                if (softApConfiguration != null) {
                    this.mPreference.setSummary(
                            this.mContext.getString(
                                    R.string.wifi_tether_enabled_subtext,
                                    BidiFormatter.getInstance()
                                            .unicodeWrap(softApConfiguration.getSsid())));
                    break;
                }
                break;
            default:
                if (i2 != 1) {
                    this.mPreference.setSummary(R.string.wifi_error);
                    break;
                } else {
                    this.mPreference.setSummary(R.string.wifi_sap_no_channel_error);
                    break;
                }
        }
    }

    public void initWifiTetherSoftApManager() {
        WifiManager wifiManager = this.mWifiManager;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        WifiTetherSoftApManager wifiTetherSoftApManager = new WifiTetherSoftApManager();
        wifiTetherSoftApManager.mWifiManager = wifiManager;
        wifiTetherSoftApManager.mWifiTetherSoftApCallback = anonymousClass1;
        WifiTetherSoftApManager.WifiManagerSoftApCallback wifiManagerSoftApCallback =
                new WifiTetherSoftApManager.WifiManagerSoftApCallback();
        wifiManagerSoftApCallback.mMyClass = new WeakReference(wifiTetherSoftApManager);
        wifiTetherSoftApManager.mSoftApCallback = wifiManagerSoftApCallback;
        wifiTetherSoftApManager.mHandler = new Handler();
        this.mWifiTetherSoftApManager = wifiTetherSoftApManager;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (WifiUtils.canShowWifiHotspot(this.mContext)) {
            StringBuilder sb = Utils.sBuilder;
            if (!ActivityManager.isUserAMonkey()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        if (this.mPreference != null) {
            WifiTetherSoftApManager wifiTetherSoftApManager = this.mWifiTetherSoftApManager;
            if (wifiTetherSoftApManager != null) {
                wifiTetherSoftApManager.mWifiManager.registerSoftApCallback(
                        new HandlerExecutor(wifiTetherSoftApManager.mHandler),
                        wifiTetherSoftApManager.mSoftApCallback);
            }
            SwitchWidgetController switchWidgetController = this.mSwitch;
            if (switchWidgetController != null) {
                switchWidgetController.startListening();
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        if (this.mPreference != null) {
            WifiTetherSoftApManager wifiTetherSoftApManager = this.mWifiTetherSoftApManager;
            if (wifiTetherSoftApManager != null) {
                wifiTetherSoftApManager.mWifiManager.unregisterSoftApCallback(
                        wifiTetherSoftApManager.mSoftApCallback);
            }
            SwitchWidgetController switchWidgetController = this.mSwitch;
            if (switchWidgetController != null) {
                switchWidgetController.stopListening();
            }
        }
    }

    @Override // com.android.settings.widget.SwitchWidgetController.OnSwitchChangeListener
    public final boolean onSwitchToggled(boolean z) {
        if (!z) {
            this.mTetheringManagerModel.mTetheringManager.stopTethering(0);
            return true;
        }
        TetheringManagerModel tetheringManagerModel = this.mTetheringManagerModel;
        tetheringManagerModel.mTetheringManager.startTethering(
                0,
                tetheringManagerModel.getApplication().getMainExecutor(),
                tetheringManagerModel.mStartTetheringCallback);
        return true;
    }

    public final void setDataSaverEnabled(boolean z) {
        this.mIsDataSaverEnabled = z;
        PrimarySwitchPreference primarySwitchPreference = this.mPreference;
        if (primarySwitchPreference != null) {
            primarySwitchPreference.setEnabled(canEnabled());
        }
        SwitchWidgetController switchWidgetController = this.mSwitch;
        if (switchWidgetController != null) {
            switchWidgetController.setEnabled(canEnabled());
        }
    }

    public void updateSwitch() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null) {
            return;
        }
        int wifiApState = wifiManager.getWifiApState();
        this.mSwitch.setEnabled(canEnabled());
        this.mSwitch.setChecked(wifiApState == 13);
        handleWifiApStateChanged(wifiApState, 0);
    }
}
