package com.samsung.android.settings.accessibility.hearing.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.settings.accessibility.HearingAidHelper;

import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSecondDepthHearingAidCompatibilityPreferenceController
        extends SecHearingAidCompatibilityPreferenceController {
    public SecSecondDepthHearingAidCompatibilityPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        HearingAidHelper hearingAidHelper;
        return ((AccessibilityRune.getFloatingFeatureBooleanValue(
                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")
                                || (hearingAidHelper = this.mHelper) == null
                                || !hearingAidHelper.isHearingAidSupported())
                        && SecAccessibilityUtils.isHearingAidSupported(this.mContext))
                ? 0
                : 3;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment";
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ Intent getLearnMoreButtonIntent(Context context) {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ int getUsingFunctionType() {
        return super.getUsingFunctionType();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.accessibility.hearing.controllers.SecHearingAidCompatibilityPreferenceController, com.android.settings.accessibility.HearingAidCompatibilityPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
