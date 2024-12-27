package com.android.settings.display.darkmode;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DarkModeCustomBedtimePreferenceController extends BasePreferenceController {
    private BedtimeSettings mBedtimeSettings;
    private FooterPreference mFooterPreference;
    private final UiModeManager mUiModeManager;

    public DarkModeCustomBedtimePreferenceController(Context context, String str) {
        super(context, str);
        this.mUiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        this.mBedtimeSettings = new BedtimeSettings(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$0(View view) {
        Intent bedtimeSettingsIntent = this.mBedtimeSettings.getBedtimeSettingsIntent();
        if (bedtimeSettingsIntent != null) {
            view.getContext().startActivity(bedtimeSettingsIntent);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        FooterPreference footerPreference =
                (FooterPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mFooterPreference = footerPreference;
        footerPreference.setLearnMoreAction(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.display.darkmode.DarkModeCustomBedtimePreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DarkModeCustomBedtimePreferenceController.this.lambda$displayPreference$0(
                                view);
                    }
                });
        this.mFooterPreference.setLearnMoreText(
                this.mContext.getString(R.string.dark_ui_bedtime_footer_action));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mBedtimeSettings.getBedtimeSettingsIntent() == null ? 3 : 1;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (this.mUiModeManager.getNightModeCustomType() != 1) {
            preference.setVisible(false);
        } else {
            preference.setVisible(true);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}