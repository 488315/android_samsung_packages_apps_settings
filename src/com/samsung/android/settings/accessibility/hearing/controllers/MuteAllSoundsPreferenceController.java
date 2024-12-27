package com.samsung.android.settings.accessibility.hearing.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils$$ExternalSyntheticLambda2;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil;
import com.samsung.android.settings.accessibility.recommend.RecommendedForYouUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MuteAllSoundsPreferenceController extends TogglePreferenceController
        implements AccessibilityObservableController,
                AccessibilityUsingFunction,
                A11yStatusLoggingProvider {
    public MuteAllSoundsPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return AccessibilityRune.isSupportAudioFeature("allsoundmute") ? 0 : 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Object getDefaultValue(Context context) {
        return Boolean.FALSE;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "MuteAllSounds";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_mute_all_sounds);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ Intent getLearnMoreButtonIntent(Context context) {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        if (getAvailabilityStatus() == 3) {
            return Map.of();
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getA11ySettingsMetricsFeatureProvider()
                .actionBackground(
                        4011,
                        "A11YSE4024",
                        A11yStatusLoggingUtil.getShortcutStatusMap(
                                context,
                                AccessibilityConstant.COMPONENT_NAME_MUTE_ALL_SOUNDS_SHORTCUT));
        return Map.of("A11YS4006", getThreadEnabled() ? "On" : "Off");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.Global.getUriFor("all_sound_off"));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.mute_all_sounds_title);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ int getUsingFunctionType() {
        return super.getUsingFunctionType();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "all_sound_off", 0) != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AlertDialog createExclusiveDialog;
        Context context = this.mContext;
        if (SecAccessibilityUtils.checkAutoAnsweringMemo(z, context, null, null)) {
            if (!z
                    || (createExclusiveDialog =
                                    AccessibilityDialogUtils.createExclusiveDialog(
                                            context,
                                            "mute_all_sound",
                                            new SecAccessibilityUtils$$ExternalSyntheticLambda2(
                                                    context, 0),
                                            null))
                            == null) {
                SecAccessibilityUtils.enableMuteAllSounds(context, z);
            } else {
                createExclusiveDialog.show();
            }
        }
        RecommendedForYouUtils.updateFeatureConditionForProfile(
                this.mContext, "enable_time_mute_all_sounds", "use_count_mute_all_sounds", z);
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
