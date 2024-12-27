package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractZenModePreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnResume, OnPause, OnDestroy {
    public static ZenModeConfigWrapper mZenModeConfigWrapper;
    public final String KEY;
    public final ZenModeBackend mBackend;
    public String mCurrentETag;
    public int mCurrentZen;
    public int mDuration;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final NotificationManager mNotificationManager;
    public PreferenceScreen mScreen;
    protected SettingObserver mSettingObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingObserver extends ContentObserver {
        public final Uri ZEN_MODE_CONFIG_ETAG_URI;
        public final Uri ZEN_MODE_DURATION_URI;
        public final Uri ZEN_MODE_URI;
        public Preference mPreference;

        public SettingObserver() {
            super(new Handler());
            this.ZEN_MODE_URI = Settings.Global.getUriFor("zen_mode");
            this.ZEN_MODE_CONFIG_ETAG_URI = Settings.Global.getUriFor("zen_mode_config_etag");
            this.ZEN_MODE_DURATION_URI = Settings.Secure.getUriFor("zen_duration");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            boolean z2;
            super.onChange(z, uri);
            if (uri == null
                    || this.ZEN_MODE_URI.equals(uri)
                    || this.ZEN_MODE_CONFIG_ETAG_URI.equals(uri)
                    || this.ZEN_MODE_DURATION_URI.equals(uri)) {
                if (this.ZEN_MODE_URI.equals(uri)) {
                    int zenMode = AbstractZenModePreferenceController.this.getZenMode();
                    AbstractZenModePreferenceController abstractZenModePreferenceController =
                            AbstractZenModePreferenceController.this;
                    int i = abstractZenModePreferenceController.mCurrentZen;
                    z2 = i != zenMode;
                    if (!z2) {
                        zenMode = i;
                    }
                    abstractZenModePreferenceController.mCurrentZen = zenMode;
                } else if (this.ZEN_MODE_CONFIG_ETAG_URI.equals(uri)) {
                    String string =
                            Settings.Global.getString(
                                    ((AbstractPreferenceController)
                                                    AbstractZenModePreferenceController.this)
                                            .mContext.getContentResolver(),
                                    "zen_mode_config_etag");
                    z2 = !AbstractZenModePreferenceController.this.mCurrentETag.equals(string);
                    AbstractZenModePreferenceController abstractZenModePreferenceController2 =
                            AbstractZenModePreferenceController.this;
                    if (!z2) {
                        string = abstractZenModePreferenceController2.mCurrentETag;
                    }
                    abstractZenModePreferenceController2.mCurrentETag = string;
                } else {
                    int zenDuration = AbstractZenModePreferenceController.this.getZenDuration();
                    AbstractZenModePreferenceController abstractZenModePreferenceController3 =
                            AbstractZenModePreferenceController.this;
                    int i2 = abstractZenModePreferenceController3.mDuration;
                    z2 = i2 != zenDuration;
                    if (!z2) {
                        zenDuration = i2;
                    }
                    abstractZenModePreferenceController3.mDuration = zenDuration;
                }
                if (z2) {
                    ZenModeBackend zenModeBackend =
                            AbstractZenModePreferenceController.this.mBackend;
                    NotificationManager notificationManager = zenModeBackend.mNotificationManager;
                    if (notificationManager != null) {
                        zenModeBackend.mPolicy = notificationManager.getNotificationPolicy();
                    }
                    ZenModeBackend zenModeBackend2 =
                            AbstractZenModePreferenceController.this.mBackend;
                    zenModeBackend2.mZenMode =
                            Settings.Global.getInt(
                                    zenModeBackend2.mContext.getContentResolver(),
                                    "zen_mode",
                                    zenModeBackend2.mZenMode);
                    AbstractZenModePreferenceController abstractZenModePreferenceController4 =
                            AbstractZenModePreferenceController.this;
                    PreferenceScreen preferenceScreen =
                            abstractZenModePreferenceController4.mScreen;
                    if (preferenceScreen != null) {
                        abstractZenModePreferenceController4.displayPreference(preferenceScreen);
                    }
                    AbstractZenModePreferenceController.this.updateState(this.mPreference);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class ZenModeConfigWrapper {
        public final Context mContext;

        public ZenModeConfigWrapper(Context context) {
            this.mContext = context;
        }
    }

    public AbstractZenModePreferenceController(Context context, String str, Lifecycle lifecycle) {
        super(context);
        this.mCurrentZen = -1;
        this.mCurrentETag = ApnSettings.MVNO_NONE;
        this.mDuration = -1;
        mZenModeConfigWrapper = new ZenModeConfigWrapper(context);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.KEY = str;
        this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mBackend = ZenModeBackend.getInstance(context);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
        Preference findPreference = preferenceScreen.findPreference(this.KEY);
        if (findPreference != null) {
            if (this.mSettingObserver == null) {
                this.mSettingObserver = new SettingObserver();
            }
            this.mSettingObserver.mPreference = findPreference;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return this.KEY;
    }

    public final int getZenDuration() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "zen_duration", 0);
    }

    public final int getZenMode() {
        return Settings.Global.getInt(
                this.mContext.getContentResolver(), "zen_mode", this.mBackend.mZenMode);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        mZenModeConfigWrapper = null;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            settingObserver.getClass();
            contentResolver.unregisterContentObserver(settingObserver);
        }
    }

    public void onResume() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            contentResolver.registerContentObserver(
                    settingObserver.ZEN_MODE_URI, false, settingObserver, -1);
            contentResolver.registerContentObserver(
                    settingObserver.ZEN_MODE_CONFIG_ETAG_URI, false, settingObserver, -1);
            contentResolver.registerContentObserver(
                    settingObserver.ZEN_MODE_DURATION_URI, false, settingObserver, -1);
            this.mSettingObserver.onChange(false, null);
        }
    }
}
