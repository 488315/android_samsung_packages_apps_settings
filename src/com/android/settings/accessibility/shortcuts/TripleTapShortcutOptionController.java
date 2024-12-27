package com.android.settings.accessibility.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TripleTapShortcutOptionController extends ShortcutOptionPreferenceController {
    private boolean mIsExpanded;

    public TripleTapShortcutOptionController(Context context, String str) {
        super(context, str);
        this.mIsExpanded = false;
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
                    R.string.accessibility_shortcut_edit_screen_title_triple_tap);
            shortcutOptionPreference.setSummary(
                    this.mContext.getString(
                            R.string.accessibility_shortcut_edit_screen_summary_triple_tap, 3));
            if (R.raw.accessibility_shortcut_type_tripletap
                    != shortcutOptionPreference.mIntroImageRawResId) {
                shortcutOptionPreference.mIntroImageRawResId =
                        R.raw.accessibility_shortcut_type_tripletap;
                shortcutOptionPreference.mIntroImageResId = 0;
                shortcutOptionPreference.notifyChanged();
            }
        }
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public void enableShortcutForTargets(boolean z) {
        super.enableShortcutForTargets(z);
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (isExpanded() && isShortcutAvailable()) ? 1 : 2;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public int getShortcutType() {
        return 4;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public boolean isChecked() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "accessibility_display_magnification_enabled",
                        0)
                == 1;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isExpanded() {
        return this.mIsExpanded;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public boolean isShortcutAvailable() {
        Set<String> shortcutTargets = getShortcutTargets();
        return shortcutTargets.size() == 1
                && shortcutTargets.contains(
                        "com.android.server.accessibility.MagnificationController");
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setExpanded(boolean z) {
        this.mIsExpanded = z;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
