package com.samsung.android.settings.usefulfeature.motionandgestures.smartalert;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.Utils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.motionandgestures.MotionAndGestureSettings;
import com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SmartAlertPreferenceController extends SecMotionAndGestureBaseController {
    private SecSwitchPreference mPreference;

    public SmartAlertPreferenceController(Context context, String str) {
        super(context, str);
    }

    private int getSummaryForSmartAlertSwitch() {
        ApplicationInfo applicationInfo;
        if (Utils.isWifiOnly(this.mContext)
                || !com.android.settings.Utils.isVoiceCapable(this.mContext)) {
            return R.string.pick_up_guide_content_wifi;
        }
        Context context = this.mContext;
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        String str = "com.android.mms";
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_MESSAGE_CONFIG_PACKAGE_NAME",
                                "com.android.mms");
        if (!"com.android.mms".equals(string)
                && com.android.settings.Utils.hasPackage(context, string)) {
            str = string;
        }
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        return (applicationInfo == null || !applicationInfo.enabled)
                ? R.string.pick_up_guide_content_except_message
                : R.string.pick_up_guide_content;
    }

    private boolean isSmartAlertEnabled() {
        Boolean bool = Boolean.FALSE;
        if (Settings.System.getInt(this.mContext.getContentResolver(), "access_control_enabled", 0)
                == 1) {
            bool = Boolean.TRUE;
        }
        return bool.booleanValue();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreference;
        secSwitchPreference.setSummary(getSummaryForSmartAlertSwitch());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (UsefulfeatureUtils.isSupportMotionFeature(this.mContext, getPreferenceKey())) {
            return isSmartAlertEnabled() ? 5 : 0;
        }
        return 3;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", getPreferenceKey());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = MotionAndGestureSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 7604;
        launchRequest.mArguments = bundle;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher, null, R.string.motion_and_gesture_title, 268468224);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_advanced_features;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController
    public ArrayList<Uri> getUriListRequiringObservation() {
        ArrayList<Uri> arrayList = new ArrayList<>();
        arrayList.add(Settings.System.getUriFor("access_control_enabled"));
        arrayList.add(Settings.System.getUriFor("motion_pick_up"));
        return arrayList;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return (isSmartAlertEnabled()
                        || Settings.System.getInt(
                                        this.mContext.getContentResolver(), "motion_pick_up", 0)
                                == 0)
                ? false
                : true;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LoggingHelper.insertEventLogging(7604, 4372, z);
        Settings.System.putInt(this.mContext.getContentResolver(), "motion_pick_up", z ? 1 : 0);
        return true;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController
    public void updatePreference() {
        if (this.mPreference != null) {
            if (isSmartAlertEnabled()) {
                this.mPreference.setEnabled(false);
            } else {
                this.mPreference.setEnabled(true);
            }
            this.mPreference.setChecked(getThreadEnabled());
        }
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
