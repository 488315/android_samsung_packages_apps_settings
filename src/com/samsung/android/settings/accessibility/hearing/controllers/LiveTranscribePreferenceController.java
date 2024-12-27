package com.samsung.android.settings.accessibility.hearing.controllers;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.SearchIndexableData;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController;
import com.samsung.android.settings.accessibility.base.controller.GoogleAppPreferenceController;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LiveTranscribePreferenceController extends AccessibilityShortcutPreferenceController
        implements GoogleAppPreferenceController {
    public LiveTranscribePreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (AccessibilityRune.getFloatingFeatureBooleanValue(
                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")
                || Rune.isChinaModel()
                || !Utils.hasPackage(this.mContext, "com.google.android.gms")) {
            return 3;
        }
        return (Utils.hasPackage(this.mContext, getComponentName().getPackageName())
                        || Utils.hasPackage(this.mContext, "com.android.vending"))
                ? 0
                : 3;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController
    public ComponentName getComponentName() {
        return AccessibilityConstant.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateDynamicRawDataToIndex(List<SearchIndexableRaw> list) {
        AccessibilityShortcutInfo accessibilityShortcutInfo;
        if (list == null
                || (accessibilityShortcutInfo =
                                SecAccessibilityUtils.getAccessibilityShortcutInfo(
                                        this.mContext, getComponentName()))
                        == null) {
            return;
        }
        SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(this.mContext);
        ((SearchIndexableData) searchIndexableRaw).key = getPreferenceKey();
        searchIndexableRaw.title =
                accessibilityShortcutInfo
                        .getActivityInfo()
                        .loadLabel(this.mContext.getPackageManager())
                        .toString();
        searchIndexableRaw.screenTitle =
                this.mContext.getString(R.string.hearing_enhancements_title);
        searchIndexableRaw.keywords = this.mContext.getString(R.string.keyword_sec_live_transcribe);
        list.add(searchIndexableRaw);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityShortcutPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
