package com.samsung.android.settings.accessories.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.accessories.AccessoriesUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LedCoverUserManualController extends BasePreferenceController
        implements PreferenceControllerMixin {
    private static final String KEY_LED_COVER_USER_MANUAL = "led_cover_user_manual";
    private SecPreferenceScreen mPreference;

    public LedCoverUserManualController(Context context) {
        this(context, KEY_LED_COVER_USER_MANUAL);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(KEY_LED_COVER_USER_MANUAL);
        if (UserHandle.myUserId() == 0) {
            this.mPreference.setEnabled(true);
        } else if (Utils.hasPackage(this.mContext, LedIconEditorController.PACKAGE_NAME_LED_VIEW)) {
            this.mPreference.setEnabled(true);
        } else {
            this.mPreference.setEnabled(false);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (UsefulfeatureUtils.isCoverVerified(this.mContext)
                && AccessoriesUtils.hasCoverSettingLEDCover(this.mContext)) {
            return (!Utils.isIntentAvailable(
                                    this.mContext,
                                    DisplaySettings$$ExternalSyntheticOutline0.m(
                                            LedIconEditorController.PACKAGE_NAME_LED_VIEW,
                                            "com.samsung.android.app.ledcover.quickstartguide.QuickStartGuideActivity"))
                            || Utils.isTablet()
                            || SemPersonaManager.isKnoxId(UserHandle.myUserId()))
                    ? 3
                    : 0;
        }
        return 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (preference.equals(this.mPreference)) {
            Intent m =
                    DisplaySettings$$ExternalSyntheticOutline0.m(
                            LedIconEditorController.PACKAGE_NAME_LED_VIEW,
                            "com.samsung.android.app.ledcover.quickstartguide.QuickStartGuideActivity");
            if (Utils.isIntentAvailable(this.mContext, m)) {
                this.mContext.startActivity(m);
            }
        }
        return super.handlePreferenceTreeClick(preference);
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public LedCoverUserManualController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
