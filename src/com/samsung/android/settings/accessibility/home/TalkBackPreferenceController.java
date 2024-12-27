package com.samsung.android.settings.accessibility.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TalkBackPreferenceController extends AccessibilityServicePreferenceController
        implements AccessibilityUsingFunction, A11yStatusLoggingProvider {
    private static final String TALKBACK_TUTORIAL_ACTION =
            "com.samsung.android.intent.action.TALKBACK_TUTORIAL";

    public TalkBackPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isTalkBackTurnedOn() {
        return AccessibilityUtils.getEnabledServicesFromSettings(this.mContext)
                .contains(AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return AccessibilityRune.getFloatingFeatureBooleanValue(
                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")
                ? 3
                : 0;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController
    public ComponentName getComponentName() {
        return AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Object getDefaultValue(Context context) {
        return Boolean.FALSE;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.home.SecAccessibilitySettings";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "TalkBack";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.accessibility_setting_list_ic_screen);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Intent getLearnMoreButtonIntent(Context context) {
        return new Intent(TALKBACK_TUTORIAL_ACTION).putExtra("fromA11y", true);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController
    public RestrictedPreference getRestrictedPreference(ComponentName componentName) {
        RestrictedPreference restrictedPreference = super.getRestrictedPreference(componentName);
        if (restrictedPreference != null) {
            if (WizardManagerHelper.isUserSetupComplete(this.mContext)) {
                restrictedPreference.setLayoutResource(R.layout.system_setting_icon_preference);
                restrictedPreference.setIcon(R.drawable.accessibility_setting_list_ic_screen);
                restrictedPreference.setFragment(TalkBackPreferenceFragment.class.getName());
            }
            restrictedPreference.setSummary((CharSequence) null);
        }
        return restrictedPreference;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getA11ySettingsMetricsFeatureProvider()
                .actionBackground(
                        2000,
                        "A11YSE2003",
                        A11yStatusLoggingUtil.getShortcutStatusMap(
                                context, AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK));
        return Map.of("A11YS2001", isTalkBackTurnedOn() ? "on" : "off");
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getUsingFunctionHighlightKey() {
        return getComponentName().flattenToString();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.talkback_title);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public int getUsingFunctionType() {
        return 1;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Boolean.valueOf(isTalkBackTurnedOn()));
        builder.mSummary = null;
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        if (!"recommend".equals(controlValue.mControlId)) {
            return super.setValue(controlValue);
        }
        AccessibilityUtils.setAccessibilityServiceState(
                this.mContext,
                AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK,
                Boolean.parseBoolean(controlValue.getValue()));
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
