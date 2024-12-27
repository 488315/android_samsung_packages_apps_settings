package com.samsung.android.settings.accessibility.hearing.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AmplifyAmbientSoundPreferenceController extends BasePreferenceController
        implements AccessibilityUsingFunction, A11yStatusLoggingProvider {
    private static final String ACTION_AMPLIFY_AMBIENT_SOUND_STOPSELF_EXTRA = "stopself";
    private static final String ACTION_AMPLIFY_AMBIENT_SOUND_TOGGLE =
            "com.samsung.accessibility.AMPLIFY_AMBIENT_SOUND_TOGGLE";
    private static final String ACTION_AMPLIFY_AMBIENT_SOUND_TOGGLE_EXTRA = "isEnabled";

    public AmplifyAmbientSoundPreferenceController(Context context, String str) {
        super(context, str);
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
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_REMOTE_MIC");
        Log.d("A11yUtils", "isSupportAmplifyAmbientSoundFeature supportedItems : " + string);
        return (string.contains("DSP") || string.contains("FW")) ? 0 : 3;
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
        return "com.samsung.android.settings.accessibility.hearing.AmplifyAmbientSoundPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "AmplifyAmbientSound";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_amplify_ambient_sound);
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
                        4008,
                        "A11YSE4027",
                        A11yStatusLoggingUtil.getShortcutStatusMap(
                                context,
                                AccessibilityConstant
                                        .COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT));
        return Map.of();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getUsingFunctionHighlightKey() {
        return AccessibilityConstant.COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT
                .flattenToString();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.amplify_ambient_sound_title);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public int getUsingFunctionType() {
        return 200;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        boolean hasValuesInSettings =
                AccessibilityUtil.hasValuesInSettings(
                        this.mContext,
                        543,
                        AccessibilityConstant.COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT);
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Boolean.valueOf(hasValuesInSettings));
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
        if ("recommend".equals(controlValue.mControlId)
                && !Boolean.parseBoolean(controlValue.getValue())) {
            AccessibilityUtil.optOutAllValuesFromSettings(
                    this.mContext,
                    543,
                    AccessibilityConstant.COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT);
            stopAmplifyAmbientSoundService();
        }
        return null;
    }

    public void stopAmplifyAmbientSoundService() {
        if (Settings.System.getInt(
                        this.mContext.getContentResolver(), "run_amplify_ambient_sound", 0)
                == 1) {
            this.mContext.sendBroadcast(
                    new Intent(ACTION_AMPLIFY_AMBIENT_SOUND_TOGGLE)
                            .putExtra(ACTION_AMPLIFY_AMBIENT_SOUND_TOGGLE_EXTRA, false)
                            .putExtra(ACTION_AMPLIFY_AMBIENT_SOUND_STOPSELF_EXTRA, true));
        } else {
            this.mContext.stopService(
                    new Intent()
                            .setComponent(
                                    AccessibilityConstant
                                            .COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SERVICE));
        }
        Settings.System.putInt(this.mContext.getContentResolver(), "amplify_ambient_sound", 0);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
