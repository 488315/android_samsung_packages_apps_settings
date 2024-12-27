package com.android.settings.development.graphicsdriver;

import android.content.Context;
import android.content.Intent;
import android.os.GraphicsEnvironment;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentManagerImpl;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settings.development.RebootConfirmationDialogFragment;
import com.android.settings.development.RebootConfirmationDialogHost;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GraphicsDriverEnableAngleAsSystemDriverController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener,
                PreferenceControllerMixin,
                RebootConfirmationDialogHost {
    static final String ANGLE_DRIVER_SUFFIX = "angle";
    static final String PROPERTY_DEBUG_ANGLE_DEVELOPER_OPTION =
            "debug.graphics.angle.developeroption.enable";
    static final String PROPERTY_PERSISTENT_GRAPHICS_EGL = "persist.graphics.egl";
    public final DevelopmentSettingsDashboardFragment mFragment;
    public boolean mShouldToggleSwitchBackOnRebootDialogDismiss;
    public final Injector.AnonymousClass1 mSystemProperties;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class Injector {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.development.graphicsdriver.GraphicsDriverEnableAngleAsSystemDriverController$Injector$1, reason: invalid class name */
        public final class AnonymousClass1 {}
    }

    public GraphicsDriverEnableAngleAsSystemDriverController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment,
            Injector injector) {
        super(context);
        this.mFragment = developmentSettingsDashboardFragment;
        injector.getClass();
        this.mSystemProperties = new Injector.AnonymousClass1();
        this.mShouldToggleSwitchBackOnRebootDialogDismiss = true;
        Log.v(
                "GraphicsEnableAngleCtrl",
                "Value of persist.graphics.egl is: "
                        + SystemProperties.get(
                                PROPERTY_PERSISTENT_GRAPHICS_EGL, ApnSettings.MVNO_NONE));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enable_angle_as_system_driver";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        GraphicsEnvironment.getInstance().toggleAngleAsSystemDriver(false);
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        GraphicsEnvironment.getInstance().toggleAngleAsSystemDriver(((Boolean) obj).booleanValue());
        showRebootDialog();
        return true;
    }

    @Override // com.android.settings.development.RebootConfirmationDialogHost
    public final void onRebootCancelled() {
        this.mShouldToggleSwitchBackOnRebootDialogDismiss = true;
    }

    @Override // com.android.settings.development.RebootConfirmationDialogHost
    public final void onRebootConfirmed(Context context) {
        this.mShouldToggleSwitchBackOnRebootDialogDismiss = false;
        rebootDevice(context);
    }

    @Override // com.android.settings.development.RebootConfirmationDialogHost
    public final void onRebootDialogDismissed() {
        if (this.mShouldToggleSwitchBackOnRebootDialogDismiss) {
            this.mSystemProperties.getClass();
            String str =
                    SystemProperties.get(PROPERTY_PERSISTENT_GRAPHICS_EGL, ApnSettings.MVNO_NONE);
            if (TextUtils.equals(ANGLE_DRIVER_SUFFIX, str)) {
                GraphicsEnvironment.getInstance().toggleAngleAsSystemDriver(false);
                ((TwoStatePreference) this.mPreference).setChecked(false);
            } else if (TextUtils.isEmpty(str)) {
                GraphicsEnvironment.getInstance().toggleAngleAsSystemDriver(true);
                ((TwoStatePreference) this.mPreference).setChecked(true);
            } else {
                Log.e("GraphicsEnableAngleCtrl", "Invalid persist.graphics.egl property value");
            }
        }
        this.mShouldToggleSwitchBackOnRebootDialogDismiss = true;
    }

    public void rebootDevice(Context context) {
        context.startActivity(
                new Intent("android.intent.action.REBOOT")
                        .setPackage(RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
    }

    public void showRebootDialog() {
        FragmentManagerImpl supportFragmentManager =
                this.mFragment.getActivity().getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("FreeformPrefRebootDlg") == null) {
            new RebootConfirmationDialogFragment(
                            R.string.reboot_dialog_enable_angle_as_system_driver,
                            R.string.cancel,
                            this)
                    .show(supportFragmentManager, "FreeformPrefRebootDlg");
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        Injector.AnonymousClass1 anonymousClass1 = this.mSystemProperties;
        anonymousClass1.getClass();
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        TextUtils.equals(
                                ANGLE_DRIVER_SUFFIX,
                                SystemProperties.get(
                                        PROPERTY_PERSISTENT_GRAPHICS_EGL, ApnSettings.MVNO_NONE)));
        anonymousClass1.getClass();
        if (SystemProperties.getBoolean(PROPERTY_DEBUG_ANGLE_DEVELOPER_OPTION, false)
                || ((TwoStatePreference) this.mPreference).isChecked()) {
            return;
        }
        this.mPreference.setEnabled(false);
    }

    public GraphicsDriverEnableAngleAsSystemDriverController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        this(context, developmentSettingsDashboardFragment, new Injector());
    }
}
