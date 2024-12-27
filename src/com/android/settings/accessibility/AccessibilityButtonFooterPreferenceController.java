package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.MessageFormat;
import android.text.Html;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilityButtonFooterPreferenceController
        extends AccessibilityFooterPreferenceController {
    public AccessibilityButtonFooterPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        ((AccessibilityFooterPreference) preferenceScreen.findPreference(getPreferenceKey()))
                .setTitle(
                        Html.fromHtml(
                                MessageFormat.format(
                                        this.mContext.getString(
                                                AccessibilityUtil.isGestureNavigateEnabled(
                                                                this.mContext)
                                                        ? R.string
                                                                .accessibility_button_gesture_description
                                                        : R.string
                                                                .accessibility_button_description),
                                        1,
                                        2,
                                        3),
                                63));
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController
    public String getIntroductionTitle() {
        return this.mContext.getString(R.string.accessibility_button_about_title);
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController
    public String getLearnMoreText() {
        return this.mContext.getString(
                R.string.accessibility_button_gesture_footer_learn_more_content_description);
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.accessibility.AccessibilityFooterPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
