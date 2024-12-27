package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecurityDashboardStatusPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardScreenLockPreferenceController
        extends SecurityDashboardPreferenceController {
    private static final String TAG = "SecurityDashboardScreenLockPreferenceController";
    private Fragment mParentFragment;
    private SecurityDashboardStatusPreference mStatusPreference;

    public SecurityDashboardScreenLockPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void updateMenuStatus() {
        boolean isScreenLockEnabled = SecurityDashboardUtils.isScreenLockEnabled(this.mContext);
        SecurityDashboardPreferenceController.mIsScreenLockEnabled = isScreenLockEnabled;
        SecurityDashboardConstants$Status securityDashboardConstants$Status =
                isScreenLockEnabled
                        ? SecurityDashboardConstants$Status.OK
                        : SecurityDashboardConstants$Status.WARNING;
        this.mPreference.setStatus(securityDashboardConstants$Status);
        if (SecurityDashboardPreferenceController.mIsScreenLockEnabled) {
            this.mPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(R.color.security_dashboard_menu_subtext_default_color, null));
            this.mPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.security_dashboard_secure_lock_set_description),
                    false);
        } else {
            this.mPreference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(
                                    R.color.security_dashboard_menu_subtext_suggestion_color,
                                    null));
            this.mPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(
                                    R.string.security_dashboard_secure_lock_not_set_description));
        }
        SecurityDashboardStatusPreference securityDashboardStatusPreference =
                this.mStatusPreference;
        if (securityDashboardStatusPreference != null) {
            securityDashboardStatusPreference.setMenuStatus(
                    "key_screen_lock", "SL", securityDashboardConstants$Status);
        }
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mStatusPreference =
                (SecurityDashboardStatusPreference)
                        preferenceScreen.findPreference("security_dashboard_status");
        if (Rune.isLDUModel() || Rune.isShopDemo(this.mContext)) {
            this.mPreference.setIntent(null);
        }
        updateMenuStatus();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (LockUtils.isLockSettingsBlockonDexMode(this.mContext)
                        || KnoxUtils.checkKnoxCustomSettingsHiddenItem(16)
                        || SecurityDashboardUtils.isMDMEnabled(this.mContext))
                ? 2
                : 0;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if ("key_screen_lock".equals(preference.getKey())) {
            if (Rune.isLDUModel() || Rune.isShopDemo(this.mContext)) {
                Toast.makeText(this.mContext, R.string.security_dashboard_retail_mode_toast, 0)
                        .show();
                return true;
            }
            SecurityDashboardUtils.launchChooseLockScreen(this.mContext);
            LoggingHelper.insertEventLogging(9032, 56030);
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(Fragment fragment) {
        this.mParentFragment = fragment;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (SecurityDashboardUtils.isMDMEnabled(this.mContext)) {
            list.add(getPreferenceKey());
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateMenuStatus();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
