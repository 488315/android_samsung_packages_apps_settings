package com.android.settings.network;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.ConnectivitySettingsManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.util.ArrayUtils;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateDnsPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnStart, OnStop {
    private static final String KEY_PRIVATE_DNS_SETTINGS = "private_dns_settings";
    private static final Uri[] SETTINGS_URIS = {
        Settings.Global.getUriFor("private_dns_mode"),
        Settings.Global.getUriFor("private_dns_default_mode"),
        Settings.Global.getUriFor("private_dns_specifier")
    };
    private final ConnectivityManager mConnectivityManager;
    private final Handler mHandler;
    private LinkProperties mLatestLinkProperties;
    private final ConnectivityManager.NetworkCallback mNetworkCallback;
    private Preference mPreference;
    private final ContentObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PrivateDnsSettingsObserver extends ContentObserver {
        public PrivateDnsSettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (PrivateDnsPreferenceController.this.mPreference != null) {
                PrivateDnsPreferenceController privateDnsPreferenceController =
                        PrivateDnsPreferenceController.this;
                privateDnsPreferenceController.updateState(
                        privateDnsPreferenceController.mPreference);
            }
        }
    }

    public PrivateDnsPreferenceController(Context context) {
        super(context, KEY_PRIVATE_DNS_SETTINGS);
        this.mNetworkCallback =
                new ConnectivityManager
                        .NetworkCallback() { // from class:
                                             // com.android.settings.network.PrivateDnsPreferenceController.1
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLinkPropertiesChanged(
                            Network network, LinkProperties linkProperties) {
                        PrivateDnsPreferenceController.this.mLatestLinkProperties = linkProperties;
                        if (PrivateDnsPreferenceController.this.mPreference != null) {
                            PrivateDnsPreferenceController privateDnsPreferenceController =
                                    PrivateDnsPreferenceController.this;
                            privateDnsPreferenceController.updateState(
                                    privateDnsPreferenceController.mPreference);
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        PrivateDnsPreferenceController.this.mLatestLinkProperties = null;
                        if (PrivateDnsPreferenceController.this.mPreference != null) {
                            PrivateDnsPreferenceController privateDnsPreferenceController =
                                    PrivateDnsPreferenceController.this;
                            privateDnsPreferenceController.updateState(
                                    privateDnsPreferenceController.mPreference);
                        }
                    }
                };
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mSettingsObserver = new PrivateDnsSettingsObserver(handler);
        this.mConnectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
    }

    private boolean isManagedByAdmin() {
        return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "disallow_config_private_dns")
                != null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mContext.getResources().getBoolean(R.bool.config_show_private_dns_settings)) {
            return ((UserManager) this.mContext.getSystemService(UserManager.class)).isAdminUser()
                    ? 0
                    : 4;
        }
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_PRIVATE_DNS_SETTINGS;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Resources resources = this.mContext.getResources();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int privateDnsMode = ConnectivitySettingsManager.getPrivateDnsMode(this.mContext);
        LinkProperties linkProperties = this.mLatestLinkProperties;
        boolean z =
                !ArrayUtils.isEmpty(
                        linkProperties == null
                                ? null
                                : linkProperties.getValidatedPrivateDnsServers());
        if (privateDnsMode == 1) {
            return resources.getString(R.string.private_dns_mode_off);
        }
        if (privateDnsMode == 2) {
            return z
                    ? resources.getString(R.string.private_dns_mode_on)
                    : resources.getString(R.string.private_dns_mode_opportunistic);
        }
        if (privateDnsMode != 3) {
            return ApnSettings.MVNO_NONE;
        }
        if (!z) {
            return resources.getString(R.string.private_dns_mode_provider_failure);
        }
        Map map = PrivateDnsModeDialogPreference.PRIVATE_DNS_MAP;
        return Settings.Global.getString(contentResolver, "private_dns_specifier");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        for (Uri uri : SETTINGS_URIS) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(uri, false, this.mSettingsObserver);
        }
        Network activeNetwork = this.mConnectivityManager.getActiveNetwork();
        if (activeNetwork != null) {
            this.mLatestLinkProperties = this.mConnectivityManager.getLinkProperties(activeNetwork);
        }
        this.mConnectivityManager.registerDefaultNetworkCallback(
                this.mNetworkCallback, this.mHandler);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(!isManagedByAdmin());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
