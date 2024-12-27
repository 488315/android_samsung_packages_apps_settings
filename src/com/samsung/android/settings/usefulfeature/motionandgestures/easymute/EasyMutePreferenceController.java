package com.samsung.android.settings.usefulfeature.motionandgestures.easymute;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settingslib.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class EasyMutePreferenceController extends SecMotionAndGestureBaseController {
    private SecSwitchPreference mPreference;

    public EasyMutePreferenceController(Context context, String str) {
        super(context, str);
    }

    public static int getSummaryForMutePauseSwitch(Context context) {
        return (Utils.isWifiOnly(context) || !com.android.settings.Utils.isVoiceCapable(context))
                ? R.string.mute_guide_content_wifi
                : UsefulfeatureUtils.isSupportMotion(context, 2097152)
                        ? R.string.mute_guide_content
                        : R.string.mute_guide_content_turnover;
    }

    public static int getTitleForMutePauseSwitch(Context context) {
        return com.android.settings.Utils.isTablet()
                ? R.string.mute_title_cover_screen
                : UsefulfeatureUtils.isSupportMotion(context, 2097152)
                        ? R.string.mute_title
                        : R.string.mute_title_turn_over;
    }

    private boolean isEasyMuteEnabled() {
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
        secSwitchPreference.setTitle(getTitleForMutePauseSwitch(this.mContext));
        this.mPreference.setSummary(getSummaryForMutePauseSwitch(this.mContext));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (UsefulfeatureUtils.isSupportMotionFeature(this.mContext, getPreferenceKey())) {
            return isEasyMuteEnabled() ? 5 : 0;
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
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$MotionAndGestureSettingsActivity");
        intent.addFlags(268468224);
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", "easy_mute");
        intent.putExtra(":settings:show_fragment_args", bundle);
        return intent;
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
        arrayList.add(Settings.System.getUriFor("motion_merged_mute_pause"));
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
        return (isEasyMuteEnabled()
                        || Settings.System.getInt(
                                        this.mContext.getContentResolver(),
                                        "motion_merged_mute_pause",
                                        0)
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
        Settings.System.putInt(
                this.mContext.getContentResolver(), "motion_merged_mute_pause", z ? 1 : 0);
        LoggingHelper.insertEventLogging(7604, 4373, z);
        if (!com.android.settings.Utils.isTablet()) {
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "motion_overturn", z ? 1 : 0);
        }
        if (!UsefulfeatureUtils.isSupportMotion(this.mContext, 2097152)) {
            return true;
        }
        Settings.System.putInt(this.mContext.getContentResolver(), "surface_palm_touch", z ? 1 : 0);
        return true;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController
    public void updatePreference() {
        if (this.mPreference != null) {
            if (isEasyMuteEnabled()) {
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
