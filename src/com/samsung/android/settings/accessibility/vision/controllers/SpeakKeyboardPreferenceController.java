package com.samsung.android.settings.accessibility.vision.controllers;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SIPUtils;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SpeakKeyboardPreferenceController extends TogglePreferenceController
        implements AccessibilityObservableController, AccessibilityUsingFunction {
    private static final String DB_KEY_SIP_SPEAK_KEYBOARD_INPUT_ALOUD =
            "sip_speak_keyboard_input_aloud";
    private static final String SPEAK_KEYBOARD_INPUT_ALOUD_MODE = "speak_keyboard_input_aloud_mode";
    private static final String SPEAK_KEYBOARD_INPUT_ALOUD_ON = "speak_keyboard_input_aloud_on";
    private static final String TAG = "SpeakKeyboardPreferenceController";
    private static final int VALUE_CHARS = 0;
    private static final int VALUE_CHARS_AND_WORDS = 2;
    private static final int VALUE_ERROR = -1;
    private static final int VALUE_ON = 1;
    private static final int VALUE_WORDS = 1;

    public SpeakKeyboardPreferenceController(Context context, String str) {
        super(context, str);
    }

    private CharSequence getErrorSummary() {
        return getAvailabilityStatus() == 5
                ? Utils.isTalkBackEnabled(this.mContext)
                        ? this.mContext.getString(R.string.speak_keyboard_disalbed_talkback)
                        : this.mContext.getString(R.string.speak_keyboard_disabled)
                : ApnSettings.MVNO_NONE;
    }

    private CharSequence getSummaryOn() {
        CharSequence errorSummary = getErrorSummary();
        if (!TextUtils.isEmpty(errorSummary)) {
            return errorSummary;
        }
        int intFromSIPProvider =
                SIPUtils.getIntFromSIPProvider(
                        this.mContext,
                        SPEAK_KEYBOARD_INPUT_ALOUD_ON,
                        SPEAK_KEYBOARD_INPUT_ALOUD_MODE,
                        -1);
        if (intFromSIPProvider == 0) {
            return this.mContext.getString(R.string.speak_keyboard_mode_characters);
        }
        if (intFromSIPProvider == 1) {
            return this.mContext.getString(R.string.speak_keyboard_mode_words);
        }
        if (intFromSIPProvider == 2) {
            return this.mContext.getString(R.string.speak_keyboard_mode_characters_and_words);
        }
        Log.e(TAG, "unexpected value.");
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int currentInputMethodType = SIPUtils.getCurrentInputMethodType(this.mContext);
        if (currentInputMethodType == -1) {
            return 2;
        }
        if (currentInputMethodType == 0) {
            return 5;
        }
        if (currentInputMethodType == 1) {
            return Utils.isTalkBackEnabled(this.mContext) ? 5 : 0;
        }
        throw new IllegalStateException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        currentInputMethodType, "Unexpected value: "));
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
        return "SpeakKeyboardInputAloud";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        AccessibilityShortcutInfo accessibilityShortcutInfo =
                SecAccessibilityUtils.getAccessibilityShortcutInfo(
                        context, AccessibilityConstant.COMPONENT_NAME_SPEAK_KEYBOARD_INPUT_ALOUD);
        if (accessibilityShortcutInfo == null) {
            return null;
        }
        return accessibilityShortcutInfo.getActivityInfo().loadIcon(context.getPackageManager());
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return getThreadEnabled() ? getSummaryOn() : getErrorSummary();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        if (SIPUtils.getCurrentInputMethodType(this.mContext) != 1) {
            return List.of();
        }
        return List.of(
                Settings.Secure.getUriFor("enabled_accessibility_services"),
                Uri.parse(
                        SIPUtils.HONEYBOARD_KEYBOARD_SETTINGS_PROVIDER.toString()
                                + "/speak_keyboard_input_aloud_on"));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.speak_keyboard_input_aloud);
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
        return SIPUtils.getIntFromSIPProvider(
                        this.mContext,
                        SPEAK_KEYBOARD_INPUT_ALOUD_ON,
                        SPEAK_KEYBOARD_INPUT_ALOUD_ON,
                        -1)
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
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getA11ySettingsMetricsFeatureProvider()
                .action(
                        getMetricsCategory(),
                        "A11Y3902",
                        Map.of(
                                "From",
                                WizardManagerHelper.isUserSetupComplete(this.mContext)
                                        ? "Settings"
                                        : "SetupWizard"));
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                DB_KEY_SIP_SPEAK_KEYBOARD_INPUT_ALOUD,
                z ? 1 : 0);
        Context context = this.mContext;
        ComponentName componentName = SIPUtils.HONEYBOARD_HIGH_CONTRAST_KEYBOARD_SETTING_ACTIVITY;
        return SIPUtils.putValueToSIPProvider(
                        context, SPEAK_KEYBOARD_INPUT_ALOUD_ON, Boolean.toString(z))
                > 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        int availabilityStatus = getAvailabilityStatus();
        if (availabilityStatus == 2) {
            preference.setVisible(false);
        } else if (availabilityStatus == 5) {
            preference.setVisible(true);
            preference.setEnabled(false);
            preference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_secondary));
        } else {
            preference.setVisible(true);
            preference.setEnabled(true);
            preference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
