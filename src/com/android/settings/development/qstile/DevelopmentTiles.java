package com.android.settings.development.qstile;

import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.SensorPrivacyManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.service.quicksettings.TileService;
import android.sysprop.DisplayProperties;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import android.widget.Toast;

import com.android.internal.app.LocalePicker;
import com.android.internal.statusbar.IStatusBarService;
import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.development.WirelessDebuggingPreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.development.DevelopmentSettingsEnabler;
import com.android.settingslib.development.SystemPropPoker;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DevelopmentTiles extends TileService {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AnimationSpeed extends DevelopmentTiles {
        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final boolean isEnabled() {
            try {
                return WindowManagerGlobal.getWindowManagerService().getAnimationScale(0) != 1.0f;
            } catch (RemoteException unused) {
                return false;
            }
        }

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final void setIsEnabled(boolean z) {
            IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
            float f = z ? 10.0f : 1.0f;
            try {
                windowManagerService.setAnimationScale(0, f);
                windowManagerService.setAnimationScale(1, f);
                windowManagerService.setAnimationScale(2, f);
            } catch (RemoteException unused) {
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class CustomBugreport extends DevelopmentTiles {
        public Context mContext;

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final boolean isEnabled() {
            return Settings.Secure.getInt(
                            this.mContext.getContentResolver(),
                            "development_custom_bugreport_writer",
                            0)
                    == 1;
        }

        @Override // android.app.Service
        public final void onCreate() {
            super.onCreate();
            this.mContext = getApplicationContext();
        }

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final void setIsEnabled(boolean z) {
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(),
                    "development_custom_bugreport_writer",
                    z ? 1 : 0);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ForceRTL extends DevelopmentTiles {
        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final boolean isEnabled() {
            return Settings.Global.getInt(getContentResolver(), "debug.force_rtl", 0) != 0;
        }

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final void setIsEnabled(boolean z) {
            Settings.Global.putInt(getContentResolver(), "debug.force_rtl", z ? 1 : 0);
            DisplayProperties.debug_force_rtl(Boolean.valueOf(z));
            LocalePicker.updateLocales(getResources().getConfiguration().getLocales());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class GPUProfiling extends DevelopmentTiles {
        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final boolean isEnabled() {
            return SystemProperties.get("debug.hwui.profile").equals("visual_bars");
        }

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final void setIsEnabled(boolean z) {
            SystemProperties.set("debug.hwui.profile", z ? "visual_bars" : ApnSettings.MVNO_NONE);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SensorsOff extends DevelopmentTiles {
        public Context mContext;
        public boolean mIsEnabled;
        public KeyguardManager mKeyguardManager;
        public SettingsMetricsFeatureProvider mMetricsFeatureProvider;
        public SensorPrivacyManager mSensorPrivacyManager;

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final boolean isEnabled() {
            return this.mIsEnabled;
        }

        @Override // android.app.Service
        public final void onCreate() {
            super.onCreate();
            Context applicationContext = getApplicationContext();
            this.mContext = applicationContext;
            SensorPrivacyManager sensorPrivacyManager =
                    (SensorPrivacyManager) applicationContext.getSystemService("sensor_privacy");
            this.mSensorPrivacyManager = sensorPrivacyManager;
            this.mIsEnabled = sensorPrivacyManager.isAllSensorPrivacyEnabled();
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
            this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        }

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final void setIsEnabled(boolean z) {
            if (this.mKeyguardManager.isKeyguardLocked()) {
                return;
            }
            this.mMetricsFeatureProvider.action(getApplicationContext(), 1598, z);
            this.mIsEnabled = z;
            this.mSensorPrivacyManager.setAllSensorPrivacy(z);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ShowLayout extends DevelopmentTiles {
        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final boolean isEnabled() {
            return ((Boolean) DisplayProperties.debug_layout().orElse(Boolean.FALSE))
                    .booleanValue();
        }

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final void setIsEnabled(boolean z) {
            DisplayProperties.debug_layout(Boolean.valueOf(z));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ShowTaps extends DevelopmentTiles {
        public Context mContext;

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final boolean isEnabled() {
            return Settings.System.getInt(this.mContext.getContentResolver(), "show_touches", 0)
                    == 1;
        }

        @Override // android.app.Service
        public final void onCreate() {
            super.onCreate();
            this.mContext = getApplicationContext();
        }

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final void setIsEnabled(boolean z) {
            Settings.System.putInt(this.mContext.getContentResolver(), "show_touches", z ? 1 : 0);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class WirelessDebugging extends DevelopmentTiles {
        public Context mContext;
        public KeyguardManager mKeyguardManager;
        public final AnonymousClass1 mSettingsObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.development.qstile.DevelopmentTiles.WirelessDebugging.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        WirelessDebugging.this.refresh();
                    }
                };
        public Toast mToast;

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final boolean isEnabled() {
            return Settings.Global.getInt(getContentResolver(), "adb_wifi_enabled", 0) != 0;
        }

        @Override // android.app.Service
        public final void onCreate() {
            super.onCreate();
            Context applicationContext = getApplicationContext();
            this.mContext = applicationContext;
            this.mKeyguardManager =
                    (KeyguardManager) applicationContext.getSystemService("keyguard");
            this.mToast = Toast.makeText(this.mContext, R.string.adb_wireless_no_network_msg, 1);
        }

        @Override // com.android.settings.development.qstile.DevelopmentTiles,
                  // android.service.quicksettings.TileService
        public final void onStartListening() {
            super.onStartListening();
            getContentResolver()
                    .registerContentObserver(
                            Settings.Global.getUriFor("adb_wifi_enabled"),
                            false,
                            this.mSettingsObserver);
        }

        @Override // android.service.quicksettings.TileService
        public final void onStopListening() {
            super.onStopListening();
            getContentResolver().unregisterContentObserver(this.mSettingsObserver);
        }

        @Override // com.android.settings.development.qstile.DevelopmentTiles
        public final void setIsEnabled(boolean z) {
            if (z && this.mKeyguardManager.isKeyguardLocked()) {
                return;
            }
            if (!z || WirelessDebuggingPreferenceController.isWifiConnected(this.mContext)) {
                Settings.Global.putInt(getContentResolver(), "adb_wifi_enabled", z ? 1 : 0);
            } else {
                sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
                this.mToast.show();
            }
        }
    }

    public abstract boolean isEnabled();

    @Override // android.service.quicksettings.TileService
    public final void onClick() {
        setIsEnabled(getQsTile().getState() == 1);
        SystemPropPoker.sInstance.poke();
        refresh();
    }

    @Override // android.service.quicksettings.TileService
    public void onStartListening() {
        super.onStartListening();
        refresh();
    }

    public final void refresh() {
        int i;
        if (DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(this)) {
            i = isEnabled() ? 2 : 1;
        } else {
            i = 0;
            if (isEnabled()) {
                setIsEnabled(false);
                SystemPropPoker.sInstance.poke();
            }
            ComponentName componentName = new ComponentName(getPackageName(), getClass().getName());
            try {
                getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
                IStatusBarService asInterface =
                        IStatusBarService.Stub.asInterface(
                                ServiceManager.checkService("statusbar"));
                if (asInterface != null) {
                    asInterface.remTile(componentName);
                }
            } catch (RemoteException e) {
                Log.e(
                        "DevelopmentTiles",
                        "Failed to modify QS tile for component " + componentName.toString(),
                        e);
            }
        }
        getQsTile().setState(i);
        getQsTile().updateTile();
    }

    public abstract void setIsEnabled(boolean z);
}
