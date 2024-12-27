package com.android.settings.accessibility.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VolumeKeysShortcutOptionController extends ShortcutOptionPreferenceController {
    public VolumeKeysShortcutOptionController(Context context, String str) {
        super(context, str);
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
                    R.string.accessibility_shortcut_edit_dialog_title_hardware);
            shortcutOptionPreference.setSummary(
                    R.string.accessibility_shortcut_edit_dialog_summary_hardware);
            shortcutOptionPreference.setIntroImageResId(
                    R.drawable.accessibility_shortcut_type_volume_keys);
        }
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public void enableShortcutForTargets(boolean z) {
        super.enableShortcutForTargets(z);
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
        return 2;
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

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public boolean isShortcutAvailable() {
        return true;
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
