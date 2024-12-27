package com.android.settings.accessibility.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityUtil;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NavButtonShortcutOptionController extends SoftwareShortcutOptionPreferenceController {
    public NavButtonShortcutOptionController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getSummary(int i) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) getSummaryStringWithIcon(i));
        if (!isInSetupWizard()) {
            spannableStringBuilder.append((CharSequence) "\n\n");
            spannableStringBuilder.append(getCustomizeAccessibilityButtonLink());
        }
        return spannableStringBuilder;
    }

    private SpannableString getSummaryStringWithIcon(int i) {
        String string =
                this.mContext.getString(
                        R.string.accessibility_shortcut_edit_dialog_summary_software);
        SpannableString valueOf = SpannableString.valueOf(string);
        int indexOf = string.indexOf("%s");
        Drawable drawable = this.mContext.getDrawable(R.drawable.ic_accessibility_new);
        ImageSpan imageSpan = new ImageSpan(drawable);
        imageSpan.setContentDescription(ApnSettings.MVNO_NONE);
        drawable.setBounds(0, 0, i, i);
        valueOf.setSpan(imageSpan, indexOf, indexOf + 2, 33);
        return valueOf;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference instanceof ShortcutOptionPreference) {
            ShortcutOptionPreference shortcutOptionPreference =
                    (ShortcutOptionPreference) findPreference;
            shortcutOptionPreference.setTitle(
                    R.string.accessibility_shortcut_edit_dialog_title_software);
            shortcutOptionPreference.setIntroImageResId(
                    R.drawable.accessibility_shortcut_type_navbar);
            shortcutOptionPreference.setSummaryProvider(
                    new Preference
                            .SummaryProvider() { // from class:
                                                 // com.android.settings.accessibility.shortcuts.NavButtonShortcutOptionController.1
                        @Override // androidx.preference.Preference.SummaryProvider
                        public final CharSequence provideSummary(Preference preference) {
                            return NavButtonShortcutOptionController.this.getSummary(
                                    ((ShortcutOptionPreference) preference).mSummaryTextLineHeight);
                        }
                    });
        }
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public boolean isShortcutAvailable() {
        return (AccessibilityUtil.isFloatingMenuEnabled(this.mContext)
                        || AccessibilityUtil.isGestureNavigateEnabled(this.mContext))
                ? false
                : true;
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController, com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
