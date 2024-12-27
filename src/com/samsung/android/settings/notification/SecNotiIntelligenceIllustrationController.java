package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecHelpAnimationLayoutPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecNotiIntelligenceIllustrationController extends BasePreferenceController {
    private static final String AUTO_GROUPING = "auto_group_main";
    private static final String ILLUSTRATION_ID = "illustraion_identifier";
    private static final String MAIN_SETTINGS = "main_page";
    private static final String PRIORITY_CONVERSATION = "priority_converstations";
    private static final String SUMMARIZE_CONTENT = "summarize_content";
    private String mCurrentId;
    private SecHelpAnimationLayoutPreference mPreference;

    public SecNotiIntelligenceIllustrationController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecHelpAnimationLayoutPreference)
                        preferenceScreen.findPreference(this.mPreferenceKey);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return super.getPreferenceKey();
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
        String string =
                this.mPreference.getExtras().getString(ILLUSTRATION_ID, ApnSettings.MVNO_NONE);
        this.mCurrentId = string;
        string.getClass();
        switch (string) {
            case "main_page":
                this.mPreference.setAnimationResource("ContinueAppsOnOtherDevices.json");
                this.mPreference.setDescText(
                        this.mContext.getString(
                                R.string.notification_intelligence_settings_description_text));
                break;
            case "priority_converstations":
                this.mPreference.setAnimationResource("onehandedmode_doubletap.json");
                this.mPreference.setDescText(
                        this.mContext.getString(
                                R.string
                                        .noti_intelligence_priority_converstation_description_text));
                break;
            case "auto_group_main":
                this.mPreference.setAnimationResource("Bubbles_light.json");
                this.mPreference.setDescText(
                        this.mContext.getString(
                                R.string.noti_intelligence_auto_grouping_description_text));
                break;
            case "summarize_content":
                this.mPreference.setAnimationResource("Auto_Switch_Buds_Dark.json");
                this.mPreference.setDescText(
                        this.mContext.getString(
                                R.string.noti_intelligence_summarize_content_description_text));
                break;
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
