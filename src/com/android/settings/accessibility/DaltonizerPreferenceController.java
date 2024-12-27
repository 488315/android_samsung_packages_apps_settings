package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemProperties;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DaltonizerPreferenceController extends BasePreferenceController
        implements AccessibilityUsingFunction,
                AccessibilityObservableController,
                A11yStatusLoggingProvider {
    private static final String DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";
    public final Map<Integer, Integer> mAccessibilityDaltonizerKeyToValueMap;

    public DaltonizerPreferenceController(Context context, String str) {
        super(context, str);
        this.mAccessibilityDaltonizerKeyToValueMap = new HashMap();
    }

    private CharSequence getEnabledSummaryFromArray() {
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), DALTONIZER_ENABLED, 0)
                != 1) {
            return this.mContext.getText(R.string.switch_off_text);
        }
        String[] stringArray =
                this.mContext.getResources().getStringArray(R.array.color_correction_mode_keys);
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "accessibility_display_daltonizer", 12);
        getDaltonizerindexToKeyMap();
        return stringArray[
                this.mAccessibilityDaltonizerKeyToValueMap.get(Integer.valueOf(i)).intValue()];
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!AccessibilityRune.getFloatingFeatureBooleanValue(
                "SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW")) {
            return 0;
        }
        if (SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 20) < 33
                || Rune.isShopDemo(this.mContext)) {
            return 3;
        }
        return SecAccessibilityUtils.isDesktopDualModeMonitorDisplay(this.mContext) ? 5 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getControlType() {
        return 0;
    }

    public Map<Integer, Integer> getDaltonizerindexToKeyMap() {
        if (this.mAccessibilityDaltonizerKeyToValueMap.size() == 0) {
            int[] intArray =
                    this.mContext.getResources().getIntArray(R.array.daltonizer_type_values);
            int length = intArray.length;
            for (int i = 0; i < length; i++) {
                this.mAccessibilityDaltonizerKeyToValueMap.put(
                        Integer.valueOf(intArray[i]), Integer.valueOf(i));
            }
        }
        return this.mAccessibilityDaltonizerKeyToValueMap;
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
        return "ColorCorrection";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_color_correction);
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

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        boolean z =
                Settings.Secure.getInt(context.getContentResolver(), DALTONIZER_ENABLED, 0) == 1;
        int i =
                Settings.Secure.getInt(
                        context.getContentResolver(), "accessibility_display_daltonizer", 12);
        int i2 =
                Settings.Secure.getInt(
                        context.getContentResolver(),
                        "accessibility_display_daltonizer_saturation_level",
                        7);
        String str = i == 0 ? "Grayscale" : i == 11 ? "Protan" : i == 13 ? "Tritan" : "Deutan";
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getA11ySettingsMetricsFeatureProvider()
                .actionBackground(
                        3070,
                        "A11YSE3070",
                        Map.of(
                                "Switch",
                                A11yStatusLoggingUtil.getSwitchStatus(z),
                                "ColorTypes",
                                str,
                                "Intensity",
                                Integer.toString(i2)));
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl2
                .getA11ySettingsMetricsFeatureProvider()
                .actionBackground(
                        3070,
                        "A11YSE3073",
                        A11yStatusLoggingUtil.getShortcutStatusMap(
                                context,
                                AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME));
        return Map.of();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return getEnabledSummaryFromArray();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.Secure.getUriFor(DALTONIZER_ENABLED));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.accessibility_display_daltonizer_preference_title);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public int getUsingFunctionType() {
        return 1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        boolean z =
                Settings.Secure.getInt(this.mContext.getContentResolver(), DALTONIZER_ENABLED, 0)
                        == 1;
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Boolean.valueOf(z));
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
        if (!"recommend".equals(controlValue.mControlId)) {
            return null;
        }
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                DALTONIZER_ENABLED,
                Boolean.parseBoolean(controlValue.getValue()) ? 1 : 0);
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        return builder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
