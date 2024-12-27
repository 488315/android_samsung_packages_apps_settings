package com.samsung.android.settings.accessibility.vision.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.accessibility.SIPUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.configuration.DATA;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HighContrastKeyboardPreferenceController extends TogglePreferenceController
        implements AccessibilityObservableController, AccessibilityUsingFunction {
    private static final String HAS_HIGH_CONTRAST_THEME_PICKER = "has_high_contrast_theme_picker";
    private static final String HIGH_CONTRAST_KEYBOARD = "high_contrast_keyboard";
    private static final String HIGH_CONTRAST_KEYBOARD_URI =
            "content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider/highcontrast";
    private static final String HIGH_CONTRAST_THEME_NAME = "high_contrast_theme_name";
    private static final String TAG = "HighContrastKeyboardPreferenceController";
    private static final String USE_ONE_HAND_OPERATION = "use_one_hand_operation";
    private AlertDialog mDialog;
    private Preference mPreference;

    public HighContrastKeyboardPreferenceController(Context context, String str) {
        super(context, str);
        this.mDialog = null;
        this.mPreference = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableHighContrastKeyboard(boolean z) {
        SIPUtils.putValueToSIPProvider(
                this.mContext, HIGH_CONTRAST_KEYBOARD, z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setSummary(getSummary());
        }
    }

    private CharSequence getPreferenceSummary(boolean z) {
        if (getAvailabilityStatus() == 5) {
            return this.mContext.getText(
                    R.string.accessibility_toggle_high_keyboard_contrast_disabled_summary);
        }
        if (z) {
            return getSummaryFromKeyboard(this.mContext);
        }
        return null;
    }

    private String getSummaryFromKeyboard(Context context) {
        return (String)
                Optional.ofNullable(
                                SIPUtils.getValueFromSIPProvider(
                                        context,
                                        HIGH_CONTRAST_THEME_NAME,
                                        HIGH_CONTRAST_THEME_NAME))
                        .orElse(null);
    }

    private void showExclusiveDialog() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mDialog = null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(
                R.string.accessibility_toggle_high_keyboard_contrast_one_hand_exclusive_title);
        builder.setMessage(
                R.string.accessibility_toggle_high_keyboard_contrast_one_hand_exclusive_msg);
        builder.setPositiveButton(
                android.R.string.ok,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.vision.controllers.HighContrastKeyboardPreferenceController.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        HighContrastKeyboardPreferenceController.this.enableHighContrastKeyboard(
                                true);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new AnonymousClass2());
        builder.P.mOnDismissListener = new AnonymousClass1();
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.show();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        if (findPreference != null) {
            findPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
            this.mPreference.setIntent(
                    new Intent()
                            .setComponent(
                                    SIPUtils.HONEYBOARD_HIGH_CONTRAST_KEYBOARD_SETTING_ACTIVITY));
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int currentInputMethodType = SIPUtils.getCurrentInputMethodType(this.mContext);
        if (currentInputMethodType == -1) {
            return 3;
        }
        if (currentInputMethodType == 0) {
            return 5;
        }
        if (currentInputMethodType == 1) {
            return SIPUtils.getIntFromSIPProvider(
                                    this.mContext,
                                    HAS_HIGH_CONTRAST_THEME_PICKER,
                                    HAS_HIGH_CONTRAST_THEME_PICKER,
                                    0)
                            == 1
                    ? 0
                    : 3;
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
        return "HighContrastKeyboard";
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return getPreferenceSummary(getThreadEnabled());
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return SIPUtils.getCurrentInputMethodType(this.mContext) == 1
                ? List.of(
                        Settings.Secure.getUriFor("enabled_accessibility_services"),
                        Uri.parse(HIGH_CONTRAST_KEYBOARD_URI))
                : List.of();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(
                R.string.accessibility_toggle_high_keyboard_contrast_preference_title);
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
                        this.mContext, HIGH_CONTRAST_KEYBOARD, HIGH_CONTRAST_KEYBOARD, 0)
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
        boolean z2 =
                SIPUtils.getIntFromSIPProvider(
                                this.mContext, USE_ONE_HAND_OPERATION, USE_ONE_HAND_OPERATION, 0)
                        == 1;
        Utils$$ExternalSyntheticOutline0.m653m(
                "High Contrast Keyboard is toggled value : ",
                z,
                ", oneHandInputKeyboard : ",
                z2,
                TAG);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getA11ySettingsMetricsFeatureProvider()
                .action(
                        getMetricsCategory(),
                        "A11Y3031",
                        Map.of(
                                "From",
                                WizardManagerHelper.isUserSetupComplete(this.mContext)
                                        ? "Settings"
                                        : "SetupWizard"));
        if (z2 && z) {
            showExclusiveDialog();
            return false;
        }
        enableHighContrastKeyboard(z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateSummary(preference);
        this.mPreference.setEnabled(getAvailabilityStatus() == 0);
    }

    public void updateSummary(Preference preference) {
        preference.setSummary(getPreferenceSummary(getThreadEnabled()));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.vision.controllers.HighContrastKeyboardPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnDismissListener {
        @Override // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.vision.controllers.HighContrastKeyboardPreferenceController$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {}
    }
}
