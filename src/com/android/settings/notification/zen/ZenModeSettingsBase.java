package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.dashboard.RestrictedDashboardFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ZenModeSettingsBase extends RestrictedDashboardFragment {
    public static final boolean DEBUG = Log.isLoggable("ZenModeSettings", 3);
    public ZenModeBackend mBackend;
    public Context mContext;
    public String mCurrentETag;
    public final Handler mHandler;
    public final SettingsObserver mSettingsObserver;
    public int mZenMode;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri ZEN_MODE_CONFIG_ETAG_URI;
        public final Uri ZEN_MODE_URI;

        public SettingsObserver() {
            super(ZenModeSettingsBase.this.mHandler);
            this.ZEN_MODE_URI = Settings.Global.getUriFor("zen_mode");
            this.ZEN_MODE_CONFIG_ETAG_URI = Settings.Global.getUriFor("zen_mode_config_etag");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (this.ZEN_MODE_URI.equals(uri)) {
                ZenModeSettingsBase zenModeSettingsBase = ZenModeSettingsBase.this;
                boolean z2 = ZenModeSettingsBase.DEBUG;
                zenModeSettingsBase.updateZenMode(true);
            }
            if (this.ZEN_MODE_CONFIG_ETAG_URI.equals(uri)) {
                String string =
                        Settings.Global.getString(
                                ZenModeSettingsBase.this.mContext.getContentResolver(),
                                "zen_mode_config_etag");
                boolean z3 = !ZenModeSettingsBase.this.mCurrentETag.equals(string);
                if (z3) {
                    ZenModeSettingsBase zenModeSettingsBase2 = ZenModeSettingsBase.this;
                    if (!z3) {
                        string = zenModeSettingsBase2.mCurrentETag;
                    }
                    zenModeSettingsBase2.mCurrentETag = string;
                    ZenModeBackend zenModeBackend = zenModeSettingsBase2.mBackend;
                    NotificationManager notificationManager = zenModeBackend.mNotificationManager;
                    if (notificationManager != null) {
                        zenModeBackend.mPolicy = notificationManager.getNotificationPolicy();
                    }
                    ZenModeSettingsBase.this.onZenModeConfigChanged();
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ZenModeDependentListener {}

    public ZenModeSettingsBase() {
        super("no_adjust_volume");
        this.mHandler = new Handler();
        this.mSettingsObserver = new SettingsObserver();
        this.mCurrentETag = ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public String getTAG() {
        return "ZenModeSettings";
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        setBackend(ZenModeBackend.getInstance(context));
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        updateZenMode(false);
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mBackend = null;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        SettingsObserver settingsObserver = this.mSettingsObserver;
        ZenModeSettingsBase.this.getContentResolver().unregisterContentObserver(settingsObserver);
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        updateZenMode(true);
        SettingsObserver settingsObserver = this.mSettingsObserver;
        ZenModeSettingsBase.this
                .getContentResolver()
                .registerContentObserver(settingsObserver.ZEN_MODE_URI, false, settingsObserver);
        ZenModeSettingsBase.this
                .getContentResolver()
                .registerContentObserver(
                        settingsObserver.ZEN_MODE_CONFIG_ETAG_URI, false, settingsObserver);
        if (isUiRestricted()) {
            if (isUiRestrictedByOnlyAdmin()) {
                getPreferenceScreen().removeAll();
            } else {
                finish();
            }
        }
    }

    @VisibleForTesting
    public void setBackend(ZenModeBackend zenModeBackend) {
        this.mBackend = zenModeBackend;
    }

    public final void updateZenMode(boolean z) {
        int i = Settings.Global.getInt(getContentResolver(), "zen_mode", this.mZenMode);
        if (i == this.mZenMode) {
            return;
        }
        this.mZenMode = i;
        if (DEBUG) {
            Log.d("ZenModeSettings", "updateZenMode mZenMode=" + this.mZenMode + " " + z);
        }
    }

    public void onZenModeConfigChanged() {}
}
