package com.samsung.android.settings.accessibility.dexterity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class UniversalSwitchPreferenceController extends BasePreferenceController
        implements AccessibilityUsingFunction, AccessibilityObservableController {
    public UniversalSwitchPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isUniversalSwitchEnabled() {
        return AccessibilityUtils.getEnabledServicesFromSettings(this.mContext)
                .contains(AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (AccessibilityRune.getFloatingFeatureBooleanValue(
                "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")) {
            return 3;
        }
        if (ActivityManager.semGetCurrentUser() != 0) {
            return 4;
        }
        return SecAccessibilityUtils.isDexMode(this.mContext) ? 5 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Object getDefaultValue(Context context) {
        return Boolean.FALSE;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.dexterity.InteractionAndDexterityFragment";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "UniversalSwitch";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_universal_switch);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ Intent getLearnMoreButtonIntent(Context context) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getText(
                isUniversalSwitchEnabled()
                        ? R.string.switch_on_text
                        : R.string.accessibility_toggle_universal_input_preference_summary);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.Secure.getUriFor("enabled_accessibility_services"));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.universal_switch_title);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public int getUsingFunctionType() {
        return 1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        boolean contains =
                AccessibilityUtils.getEnabledServicesFromSettings(this.mContext)
                        .contains(AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH);
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Boolean.valueOf(contains));
        builder.mSummary = null;
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        if ("recommend".equals(controlValue.mControlId)) {
            AccessibilityUtils.setAccessibilityServiceState(
                    this.mContext,
                    AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH,
                    Boolean.parseBoolean(controlValue.getValue()));
        }
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference != null) {
            preference.seslSetSummaryColor(
                    isUniversalSwitchEnabled()
                            ? this.mContext.getColorStateList(R.color.text_color_primary_dark)
                            : this.mContext.getColorStateList(R.color.text_color_secondary));
        }
        refreshSummary(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
