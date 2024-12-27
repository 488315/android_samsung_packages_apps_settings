package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.notification.brief.widget.BriefPopupSecUnclickablePreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopUpStyleDescriptionController extends BasePreferenceController {
    private static final String DB_REMOVE_ANIMATIONS = "remove_animations";
    private static final String KEY_BRIEF_POPUP_STYLE_DESCRIPTION =
            "brief_popup_style_settings_description";
    private static final String TAG = "BriefPopUpStyleDescriptionController";
    PreferenceScreen mParentScreen;
    private BriefPopupSecUnclickablePreference mPreference;
    private String mPreferenceKey;

    public BriefPopUpStyleDescriptionController(Context context, String str) {
        super(context, KEY_BRIEF_POPUP_STYLE_DESCRIPTION);
        this.mPreferenceKey = str;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (BriefPopupSecUnclickablePreference)
                        preferenceScreen.findPreference(this.mPreferenceKey);
        this.mParentScreen = preferenceScreen;
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

    /* JADX WARN: Multi-variable type inference failed */
    public void updateVisible() {
        if (this.mPreference != null) {
            boolean z = false;
            byte b =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(), DB_REMOVE_ANIMATIONS, 0)
                            == 1;
            byte b2 =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "edge_lighting",
                                    !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                            == 1;
            PreferenceScreen preferenceScreen = this.mParentScreen;
            String preferenceKey = getPreferenceKey();
            if (b != false && b2 != false) {
                z = true;
            }
            setVisible(preferenceScreen, preferenceKey, z);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
