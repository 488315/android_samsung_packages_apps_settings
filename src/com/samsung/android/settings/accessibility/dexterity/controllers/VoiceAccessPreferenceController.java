package com.samsung.android.settings.accessibility.dexterity.controllers;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.SearchIndexableData;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.accessibility.AccessibilityUtils;
import com.android.settingslib.search.SearchIndexableRaw;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.controller.GoogleAppPreferenceController;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class VoiceAccessPreferenceController extends AccessibilityServicePreferenceController
        implements AccessibilityUsingFunction,
                AccessibilityObservableController,
                GoogleAppPreferenceController,
                A11yStatusLoggingProvider {
    public VoiceAccessPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isVoiceAccessEnabled() {
        return AccessibilityUtils.getEnabledServicesFromSettings(this.mContext)
                .contains(AccessibilityConstant.COMPONENT_NAME_VOICE_ACCESS);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.setEnabled(
                    findPreference.isEnabled()
                            && !SecAccessibilityUtils.isDesktopDualModeMonitorDisplay(
                                    this.mContext));
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (AccessibilityRune.getFloatingFeatureBooleanValue(
                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")
                || Rune.isChinaModel()
                || !Utils.hasPackage(this.mContext, "com.google.android.gms")) {
            return 3;
        }
        if (SecAccessibilityUtils.isDesktopDualModeMonitorDisplay(this.mContext)) {
            return 5;
        }
        if (Utils.hasPackage(this.mContext, getComponentName().getPackageName())
                || Utils.hasPackage(this.mContext, "com.android.vending")) {
            return !WizardManagerHelper.isUserSetupComplete(this.mContext) ? 4 : 0;
        }
        return 3;
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
        return AccessibilityConstant.COMPONENT_NAME_VOICE_ACCESS;
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
        return "VoiceAccess";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        AccessibilityServiceInfo accessibilityServiceInfo =
                SecAccessibilityUtils.getAccessibilityServiceInfo(
                        context, AccessibilityConstant.COMPONENT_NAME_VOICE_ACCESS);
        if (accessibilityServiceInfo == null) {
            return null;
        }
        return accessibilityServiceInfo.getResolveInfo().loadIcon(context.getPackageManager());
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
    public /* bridge */ /* synthetic */ Intent getLearnMoreButtonIntent(Context context) {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        return Map.of("A11YS5004", isVoiceAccessEnabled() ? "On" : "Off");
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getText(
                isVoiceAccessEnabled() ? R.string.switch_on_text : R.string.switch_off_text);
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
        return context.getText(R.string.voice_access_title);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public int getUsingFunctionType() {
        return 1;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Boolean.valueOf(isVoiceAccessEnabled()));
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
                AccessibilityConstant.COMPONENT_NAME_VOICE_ACCESS,
                Boolean.parseBoolean(controlValue.getValue()));
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateDynamicRawDataToIndex(List<SearchIndexableRaw> list) {
        AccessibilityServiceInfo accessibilityServiceInfo;
        if (list == null
                || (accessibilityServiceInfo =
                                SecAccessibilityUtils.getAccessibilityServiceInfo(
                                        this.mContext, getComponentName()))
                        == null) {
            return;
        }
        SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(this.mContext);
        ((SearchIndexableData) searchIndexableRaw).key = getPreferenceKey();
        searchIndexableRaw.title =
                accessibilityServiceInfo
                        .getResolveInfo()
                        .loadLabel(this.mContext.getPackageManager())
                        .toString();
        searchIndexableRaw.screenTitle =
                this.mContext.getString(R.string.interaction_and_dexterity_title);
        searchIndexableRaw.keywords = this.mContext.getString(R.string.keyword_sec_voice_access);
        list.add(searchIndexableRaw);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityServicePreferenceController, com.samsung.android.settings.accessibility.base.controller.AccessibilityRestrictedPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
