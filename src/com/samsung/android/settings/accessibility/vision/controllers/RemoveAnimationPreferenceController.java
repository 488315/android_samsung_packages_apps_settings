package com.samsung.android.settings.accessibility.vision.controllers;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RemoveAnimationPreferenceController extends TogglePreferenceController
        implements AccessibilityObservableController,
                AccessibilityUsingFunction,
                A11yStatusLoggingProvider {
    private static final String ANIMATION_OFF_VALUE = "0.0";
    private static final String ANIMATION_ON_VALUE = "1";
    private static final String[] TOGGLE_ANIMATION_TARGETS = {
        "window_animation_scale", "transition_animation_scale", "animator_duration_scale"
    };

    public RemoveAnimationPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setChecked$0(DialogInterface dialogInterface, int i) {
        writeAnimationScaleOption(true);
        setRemoveAnimation(1);
    }

    private void setRemoveAnimation(int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), "remove_animations", i);
    }

    private void writeAnimationScaleOption(boolean z) {
        String str = z ? ANIMATION_OFF_VALUE : "1";
        for (String str2 : TOGGLE_ANIMATION_TARGETS) {
            Settings.Global.putString(this.mContext.getContentResolver(), str2, str);
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    public boolean getAnimationStatus() {
        return Settings.Global.getFloat(
                                this.mContext.getContentResolver(), "window_animation_scale", 1.0f)
                        == 0.0f
                || Settings.Global.getFloat(
                                this.mContext.getContentResolver(),
                                "transition_animation_scale",
                                1.0f)
                        == 0.0f
                || Settings.Global.getFloat(
                                this.mContext.getContentResolver(), "animator_duration_scale", 1.0f)
                        == 0.0f;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return ActivityManager.semGetCurrentUser() == 0 ? 0 : 4;
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
        return "com.samsung.android.settings.accessibility.vision.VisibilityEnhancementsFragment";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "RemoveAnimations";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.accessibility_setting_list_ic_visibility);
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
        return Map.of("A11YS3150", A11yStatusLoggingUtil.getSwitchStatus(getThreadEnabled()));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.Global.getUriFor("remove_animations"));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return AccessibilityRune.isAtLeastOneUI_6_1()
                ? context.getText(R.string.reduce_animations_title)
                : context.getText(R.string.accessibility_disable_animations);
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
        return Settings.Global.getInt(this.mContext.getContentResolver(), "remove_animations", 0)
                == 1;
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
        if (z
                && (createExclusiveDialog =
                                AccessibilityDialogUtils.createExclusiveDialog(
                                        this.mContext,
                                        "remove_animation",
                                        new DialogInterface
                                                .OnClickListener() { // from class:
                                                                     // com.samsung.android.settings.accessibility.vision.controllers.RemoveAnimationPreferenceController$$ExternalSyntheticLambda0
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public final void onClick(
                                                    DialogInterface dialogInterface, int i) {
                                                RemoveAnimationPreferenceController.this
                                                        .lambda$setChecked$0(dialogInterface, i);
                                            }
                                        },
                                        null))
                        != null) {
            createExclusiveDialog.show();
            return false;
        }
        writeAnimationScaleOption(z);
        setRemoveAnimation(z ? 1 : 0);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getA11ySettingsMetricsFeatureProvider()
                .action(
                        getMetricsCategory(),
                        "A11Y3150",
                        Map.of(
                                "From",
                                WizardManagerHelper.isUserSetupComplete(this.mContext)
                                        ? "Settings"
                                        : "SetupWizard"));
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        setRemoveAnimation(getAnimationStatus() ? 1 : 0);
        if (AccessibilityRune.isAtLeastOneUI_6_1()) {
            preference.setTitle(R.string.reduce_animations_title);
        } else {
            preference.setTitle(R.string.accessibility_disable_animations);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
