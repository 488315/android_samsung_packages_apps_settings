package com.android.settings.notification.modes;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.dashboard.RestrictedDashboardFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ZenModesFragmentBase extends RestrictedDashboardFragment {
    public ZenModesBackend mBackend;
    public Context mContext;
    public final Handler mHandler;
    public final SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public static final Uri ZEN_MODE_URI = Settings.Global.getUriFor("zen_mode");
        public static final Uri ZEN_MODE_CONFIG_ETAG_URI =
                Settings.Global.getUriFor("zen_mode_config_etag");

        public SettingsObserver() {
            super(ZenModesFragmentBase.this.mHandler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (ZEN_MODE_URI.equals(uri) || ZEN_MODE_CONFIG_ETAG_URI.equals(uri)) {
                ZenModesFragmentBase.this.updateZenModeState();
            }
        }
    }

    static {
        Log.isLoggable("ZenModesSettings", 3);
    }

    public ZenModesFragmentBase() {
        super("no_adjust_volume");
        this.mHandler = new Handler();
        this.mSettingsObserver = new SettingsObserver();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public String getLogTag() {
        return "ZenModesSettings";
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        this.mContext = context;
        if (ZenModesBackend.sInstance == null) {
            ZenModesBackend.sInstance = new ZenModesBackend(context.getApplicationContext());
        }
        this.mBackend = ZenModesBackend.sInstance;
        super.onAttach(context);
        SettingsObserver settingsObserver = this.mSettingsObserver;
        ZenModesFragmentBase.this
                .getContentResolver()
                .registerContentObserver(SettingsObserver.ZEN_MODE_URI, false, settingsObserver);
        ZenModesFragmentBase.this
                .getContentResolver()
                .registerContentObserver(
                        SettingsObserver.ZEN_MODE_CONFIG_ETAG_URI, false, settingsObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        super.onDetach();
        SettingsObserver settingsObserver = this.mSettingsObserver;
        ZenModesFragmentBase.this.getContentResolver().unregisterContentObserver(settingsObserver);
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        updateZenModeState();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (isUiRestricted()) {
            if (isUiRestrictedByOnlyAdmin()) {
                getPreferenceScreen().removeAll();
            } else {
                finish();
            }
        }
    }

    public abstract void updateZenModeState();
}
