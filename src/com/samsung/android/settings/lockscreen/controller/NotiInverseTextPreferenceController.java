package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockScreenNotificationSettings;
import com.samsung.android.settings.lockscreen.SecConceptBehavior;
import com.samsung.android.settings.lockscreen.SecConceptControllerBehavior;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotiInverseTextPreferenceController extends BasePreferenceController
        implements SecConceptControllerBehavior, Preference.OnPreferenceChangeListener {
    static final String KEY = "noti_inverse_text";
    SecConceptBehavior mContextDashBoard;
    LockPatternUtils mLockPatternUtils;
    PreferenceScreen mParentScreen;
    SwitchPreferenceCompat mPreference;

    public NotiInverseTextPreferenceController(
            Context context, SecConceptBehavior secConceptBehavior) {
        super(context, KEY);
        this.mContextDashBoard = secConceptBehavior;
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    private void updateVisibility(int i) {
        if (i == 0) {
            setVisible(this.mParentScreen, getPreferenceKey(), true);
        } else {
            setVisible(this.mParentScreen, getPreferenceKey(), false);
        }
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public void accept(String str, Object obj) {
        boolean z;
        str.getClass();
        boolean z2 = false;
        if (!str.equals("lock_screen_show_notifications")) {
            if (str.equals("notification_details")) {
                updateVisibility(0);
                return;
            }
            return;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean z3 =
                Settings.System.getString(
                                this.mContext.getContentResolver(),
                                "current_sec_active_themepackage")
                        != null;
        try {
            Resources resourcesForApplication =
                    this.mContext
                            .getPackageManager()
                            .getResourcesForApplication("com.android.systemui");
            z =
                    resourcesForApplication.getBoolean(
                            resourcesForApplication.getIdentifier(
                                    "theme_designer_quick_panel_turned_on",
                                    "bool",
                                    "com.android.systemui"));
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            z = false;
        }
        SwitchPreferenceCompat switchPreferenceCompat = this.mPreference;
        if (switchPreferenceCompat != null) {
            if (booleanValue && !z3 && !z) {
                z2 = true;
            }
            switchPreferenceCompat.setEnabled(z2);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SwitchPreferenceCompat) preferenceScreen.findPreference(getPreferenceKey());
        this.mParentScreen = preferenceScreen;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId())
                        || ((LockScreenNotificationSettings) this.mContextDashBoard).mNeedRedaction)
                ? 0
                : 2;
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        Boolean bool = (Boolean) obj;
        boolean booleanValue = bool.booleanValue();
        this.mPreference.setChecked(booleanValue);
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "notification_text_color_inversion",
                booleanValue ? 1 : 0);
        LoggingHelper.insertEventLogging(4435, 4455, booleanValue ? 1L : 0L);
        ((LockScreenNotificationSettings) this.mContextDashBoard)
                .notifyChange(bool, getPreferenceKey());
        return true;
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
        this.mPreference.setChecked(
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "notification_text_color_inversion",
                                1)
                        == 1);
        this.mPreference.setEnabled(
                !(Settings.System.getString(
                                this.mContext.getContentResolver(),
                                "current_sec_active_themepackage")
                        != null));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public /* bridge */ /* synthetic */ void updateConfigurationChanged(
            Configuration configuration) {}
}
