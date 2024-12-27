package com.android.settings.accessibility.shortcuts;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.service.quicksettings.TileService;
import android.util.ArraySet;
import android.view.accessibility.AccessibilityManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class QuickSettingsShortcutOptionController extends ShortcutOptionPreferenceController {
    public QuickSettingsShortcutOptionController(Context context, String str) {
        super(context, str);
    }

    private boolean allTargetsHasQsTile() {
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class);
        if (accessibilityManager == null) {
            return false;
        }
        Map a11yFeatureToTileMap =
                accessibilityManager.getA11yFeatureToTileMap(UserHandle.myUserId());
        if (a11yFeatureToTileMap.isEmpty()) {
            return false;
        }
        Iterator<String> it = getShortcutTargets().iterator();
        while (it.hasNext()) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(it.next());
            if (unflattenFromString == null
                    || !a11yFeatureToTileMap.containsKey(unflattenFromString)) {
                return false;
            }
        }
        return true;
    }

    private boolean allTargetsHasValidQsTileUseCase() {
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class);
        if (accessibilityManager == null) {
            return false;
        }
        List<AccessibilityServiceInfo> installedAccessibilityServiceList =
                accessibilityManager.getInstalledAccessibilityServiceList();
        ArraySet arraySet = new ArraySet();
        for (AccessibilityServiceInfo accessibilityServiceInfo :
                installedAccessibilityServiceList) {
            if (AccessibilityUtils.getAccessibilityServiceFragmentType(accessibilityServiceInfo)
                    != 1) {
                arraySet.add(accessibilityServiceInfo.getComponentName().flattenToString());
            }
        }
        Iterator<String> it = getShortcutTargets().iterator();
        while (it.hasNext()) {
            if (arraySet.contains(it.next())) {
                return false;
            }
        }
        return true;
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
                    R.string.accessibility_shortcut_edit_dialog_title_quick_settings);
            shortcutOptionPreference.setIntroImageResId(
                    R.drawable.accessibility_shortcut_type_quick_settings);
        }
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
        return 16;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return StringUtil.getIcuPluralsString(
                this.mContext,
                AccessibilityUtil.isTouchExploreEnabled(this.mContext) ? 2 : 1,
                isInSetupWizard()
                        ? R.string.accessibility_shortcut_edit_dialog_summary_quick_settings_suw
                        : R.string.accessibility_shortcut_edit_dialog_summary_quick_settings);
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
        return TileService.isQuickSettingsSupported()
                && allTargetsHasQsTile()
                && allTargetsHasValidQsTileUseCase();
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
