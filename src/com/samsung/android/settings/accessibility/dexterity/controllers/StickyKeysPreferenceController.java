package com.samsung.android.settings.accessibility.dexterity.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputSettings;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class StickyKeysPreferenceController extends TogglePreferenceController
        implements AccessibilityObservableController,
                AccessibilityUsingFunction,
                A11yStatusLoggingProvider {
    private Preference mPreference;

    public StickyKeysPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showExclusiveDialog$0(
            DialogInterface dialogInterface, int i) {
        Settings.Secure.putInt(this.mContext.getContentResolver(), "sticky_keys_enabled", 1);
        updateState(this.mPreference);
    }

    private boolean showExclusiveDialog() {
        AlertDialog createExclusiveDialog =
                AccessibilityDialogUtils.createExclusiveDialog(
                        this.mContext,
                        "sticky_keys",
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.accessibility.dexterity.controllers.StickyKeysPreferenceController$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                StickyKeysPreferenceController.this.lambda$showExclusiveDialog$0(
                                        dialogInterface, i);
                            }
                        },
                        null);
        if (createExclusiveDialog == null) {
            return false;
        }
        createExclusiveDialog.show();
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return "com.samsung.android.settings.accessibility.dexterity.InteractionAndDexterityFragment";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "StickyKeys";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.accessibility_setting_list_ic_interaction);
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
        return Map.of("A11YS5001", getThreadEnabled() ? "On" : "Off");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.Secure.getUriFor("accessibility_sticky_keys"));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.sticky_keys);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public int getUsingFunctionType() {
        return 1;
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
        return InputSettings.isAccessibilityStickyKeysEnabled(this.mContext);
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
        if (z && showExclusiveDialog()) {
            return false;
        }
        InputSettings.setAccessibilityStickyKeysEnabled(this.mContext, z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setCheckedForAction(boolean z) {
        if (!z
                || !AccessibilityExclusiveUtil.isExclusiveTaskEnabled(
                        this.mContext, "sticky_keys")) {
            return super.setCheckedForAction(z);
        }
        AccessibilityDialogUtils.startDialogActivity(this.mContext, 2, "sticky_keys");
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        if (!"dialogActivity".equals(controlValue.mControlId)) {
            return super.setValue(controlValue);
        }
        InputSettings.setAccessibilityStickyKeysEnabled(
                this.mContext, Boolean.parseBoolean(controlValue.getValue()));
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        return builder.build();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
