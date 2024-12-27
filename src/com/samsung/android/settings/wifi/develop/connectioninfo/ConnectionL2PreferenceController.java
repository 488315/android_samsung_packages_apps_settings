package com.samsung.android.settings.wifi.develop.connectioninfo;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectionL2PreferenceController extends AbstractPreferenceController
        implements LifecycleEventObserver {
    public ConnectionInfoRepo mConnectionInfoRepo;
    public ConnectionInfoRepoCallback mConnectionInfoRepoCallback;
    public Preference mCountryCodePref;
    public Preference mFrequencyPref;
    public Preference mGenerationPref;
    public Preference mKVRPref;
    public Preference mMultiLinkPref;
    public Preference mSecurityTypePref;
    public Preference mSsidBssidPref;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSsidBssidPref = preferenceScreen.findPreference("ssid_bssid");
        this.mKVRPref = preferenceScreen.findPreference("kvr");
        this.mCountryCodePref = preferenceScreen.findPreference("country_code");
        this.mSecurityTypePref = preferenceScreen.findPreference("security_type");
        this.mFrequencyPref = preferenceScreen.findPreference("frequency");
        this.mGenerationPref = preferenceScreen.findPreference("generation");
        this.mMultiLinkPref = preferenceScreen.findPreference("multi_link");
        updateView$1();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        ConnectionInfoRepo.InfoUpdatedCallback infoUpdatedCallback;
        Lifecycle.Event event2 = Lifecycle.Event.ON_RESUME;
        ConnectionInfoRepoCallback connectionInfoRepoCallback = this.mConnectionInfoRepoCallback;
        ConnectionInfoRepo connectionInfoRepo = this.mConnectionInfoRepo;
        if (event == event2) {
            connectionInfoRepo.mConnectionInfoUpdatedCallback = connectionInfoRepoCallback;
            updateView$1();
        } else if (event == Lifecycle.Event.ON_PAUSE
                && (infoUpdatedCallback = connectionInfoRepo.mConnectionInfoUpdatedCallback) != null
                && infoUpdatedCallback.equals(connectionInfoRepoCallback)) {
            connectionInfoRepo.mConnectionInfoUpdatedCallback = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateView$1() {
        /*
            Method dump skipped, instructions count: 570
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionL2PreferenceController.updateView$1():void");
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectionInfoRepoCallback
            implements ConnectionInfoRepo.InfoUpdatedCallback {
        public ConnectionInfoRepoCallback() {}

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void allConnectionInfoUpdated() {
            ConnectionL2PreferenceController.this.updateView$1();
        }

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void scanResultInfoUpdated() {
            ConnectionL2PreferenceController.this.updateView$1();
        }

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void wifiInfoUpdated() {
            ConnectionL2PreferenceController.this.updateView$1();
        }

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void tcpMonitorInfoUpdated() {}
    }
}
