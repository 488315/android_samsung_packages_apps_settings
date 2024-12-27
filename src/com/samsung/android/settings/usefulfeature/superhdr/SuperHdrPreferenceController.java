package com.samsung.android.settings.usefulfeature.superhdr;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SuperHdrPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final int SETTING_VALUE_OFF = 0;
    private static final int SETTING_VALUE_ON = 1;
    private static final String SUPER_HDR_KEY = "sec_superhdr";
    private static final boolean SUPER_HDR_NO = false;
    private static final boolean SUPER_HDR_YES = true;
    private static final String SURFACE_COMPOSER_INTERFACE_KEY = "android.ui.ISurfaceComposer";
    private static final String SURFACE_FLINGER_SERVICE_KEY = "SurfaceFlinger";
    private static final int SURFACE_FLINGER_SUPER_HDR_CODE = 1138;
    private Handler mHandler;
    private SecSwitchPreference mPreference;
    private SettingsObserver mSettingsObserver;
    private final IBinder mSurfaceFlinger;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri LOW_POWER_MODE_ENABLED;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.LOW_POWER_MODE_ENABLED = Settings.Global.getUriFor("low_power");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (SuperHdrPreferenceController.this.mPreference != null) {
                if (Utils.isMediumPowerSavingModeEnabled(
                        ((AbstractPreferenceController) SuperHdrPreferenceController.this)
                                .mContext)) {
                    SuperHdrPreferenceController.this.mPreference.setEnabled(false);
                    SuperHdrPreferenceController.this.mPreference.setChecked(false);
                } else {
                    SuperHdrPreferenceController.this.mPreference.setEnabled(true);
                    SuperHdrPreferenceController.this.mPreference.setChecked(
                            SuperHdrPreferenceController.this.getSelectedValue());
                }
            }
        }

        public final void setListening(boolean z) {
            if (z) {
                ((AbstractPreferenceController) SuperHdrPreferenceController.this)
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(this.LOW_POWER_MODE_ENABLED, false, this);
            } else {
                ((AbstractPreferenceController) SuperHdrPreferenceController.this)
                        .mContext
                        .getContentResolver()
                        .unregisterContentObserver(this);
            }
        }
    }

    public SuperHdrPreferenceController(Context context, String str) {
        super(context, str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
        this.mSurfaceFlinger = ServiceManager.getService(SURFACE_FLINGER_SERVICE_KEY);
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private void writeSuperHdrSetting(boolean z) {
        try {
            if (this.mSurfaceFlinger != null) {
                Parcel obtain = Parcel.obtain();
                obtain.writeInterfaceToken(SURFACE_COMPOSER_INTERFACE_KEY);
                obtain.writeInt(z ? 1 : 0);
                this.mSurfaceFlinger.transact(SURFACE_FLINGER_SUPER_HDR_CODE, obtain, null, 0);
                Settings.System.putInt(getContentResolver(), SUPER_HDR_KEY, z ? 1 : 0);
                obtain.recycle();
            }
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreference;
        refreshSummary(secSwitchPreference);
        super.displayPreference(preferenceScreen);
        if (this.mPreference != null) {
            if (Utils.isMediumPowerSavingModeEnabled(this.mContext)) {
                this.mPreference.setEnabled(false);
            } else {
                this.mPreference.setEnabled(true);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_PHOTOHDR")) {
            return Utils.isMediumPowerSavingModeEnabled(this.mContext) ? 5 : 0;
        }
        return 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return SUPER_HDR_KEY;
    }

    public boolean getSelectedValue() {
        return Settings.System.getInt(getContentResolver(), SUPER_HDR_KEY, 1) == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_advanced_features;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getInt(getContentResolver(), SUPER_HDR_KEY, 1) == 1
                && !Utils.isMediumPowerSavingModeEnabled(this.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(false);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mPreference != null) {
            if (Utils.isMediumPowerSavingModeEnabled(this.mContext)) {
                this.mPreference.setEnabled(false);
            } else {
                this.mPreference.setEnabled(true);
            }
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(true);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        writeSuperHdrSetting(z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
