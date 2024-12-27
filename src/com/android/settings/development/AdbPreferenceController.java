package com.android.settings.development;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.connecteddevice.usb.UsbBackend;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.development.AbstractEnableAdbPreferenceController;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdbPreferenceController extends AbstractEnableAdbPreferenceController
        implements LifecycleObserver, OnResume, OnPause, PreferenceControllerMixin {
    public EnterpriseDeviceManager mEDM;
    public final DevelopmentSettingsDashboardFragment mFragment;
    public final AnonymousClass1 mRampartObserver;
    public RestrictionPolicy mRestrictionPolicy;

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.settings.development.AdbPreferenceController$1] */
    public AdbPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment,
            Lifecycle lifecycle) {
        super(context);
        this.mEDM = null;
        this.mRestrictionPolicy = null;
        this.mRampartObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.development.AdbPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        super.onChange(z);
                        AdbPreferenceController adbPreferenceController =
                                AdbPreferenceController.this;
                        adbPreferenceController.updateState(
                                ((AbstractEnableAdbPreferenceController) adbPreferenceController)
                                        .mPreference);
                    }
                };
        this.mFragment = developmentSettingsDashboardFragment;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        writeAdbSetting(false);
        ((AbstractEnableAdbPreferenceController) this).mPreference.setChecked(false);
    }

    @Override // com.android.settingslib.development.AbstractEnableAdbPreferenceController,
              // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager != null && this.mRestrictionPolicy == null) {
            this.mRestrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
        }
        if (!this.mRestrictionPolicy.isUsbDebuggingEnabled()) {
            enablePreference(false);
        }
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "rampart_blocked_adb_cmd", 0)
                != 1) {
            ((AbstractEnableAdbPreferenceController) this)
                    .mPreference.setSummary(R.string.enable_adb_summary);
        } else {
            enablePreference(false);
            ((AbstractEnableAdbPreferenceController) this)
                    .mPreference.setSummary(R.string.restricted_by_auto_blocker);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mRampartObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("rampart_blocked_adb_cmd"),
                        false,
                        this.mRampartObserver);
    }

    @Override // com.android.settingslib.development.AbstractEnableAdbPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        if (Rune.isChinaModel()
                && new UsbBackend(this.mContext).mUsbManager.getCurrentFunctions() == 262144) {
            enablePreference(false);
            ((AbstractEnableAdbPreferenceController) this).mPreference.setChecked(false);
        }
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "rampart_blocked_adb_cmd", 0)
                == 1) {
            ((AbstractEnableAdbPreferenceController) this)
                    .mPreference.setSummary(R.string.restricted_by_auto_blocker);
        } else {
            ((AbstractEnableAdbPreferenceController) this)
                    .mPreference.setSummary(R.string.enable_adb_summary);
        }
    }
}
