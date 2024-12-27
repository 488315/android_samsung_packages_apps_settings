package com.samsung.android.settings.accessibility.dexterity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.text.BidiFormatter;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.accessibility.dialog.AccessibilityServiceWarning;
import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AssistantMenuPreferenceController extends TogglePreferenceController
        implements AccessibilityUsingFunction, AccessibilityObservableController {
    private static final String TAG = "AssistantMenuPreferenceController";
    private AccessibilityServiceInfo mAccessibilityServiceInfo;
    private Dialog mEnableWarningDialog;
    private Preference mPreference;

    public AssistantMenuPreferenceController(Context context, String str) {
        super(context, str);
        this.mAccessibilityServiceInfo =
                SecAccessibilityUtils.getAccessibilityServiceInfo(
                        context, AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU);
    }

    private Dialog createDisableDialog(
            Context context, DialogInterface.OnClickListener onClickListener) {
        if (this.mAccessibilityServiceInfo == null) {
            return null;
        }
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        return new AlertDialog.Builder(context)
                .setMessage(
                        context.getString(
                                R.string.sec_disable_service_message,
                                BidiFormatter.getInstance(locale)
                                        .unicodeWrap(
                                                this.mAccessibilityServiceInfo
                                                        .getResolveInfo()
                                                        .loadLabel(context.getPackageManager()))))
                .setCancelable(true)
                .setPositiveButton(R.string.accessibility_turn_off, onClickListener)
                .setNegativeButton(R.string.common_cancel, onClickListener)
                .create();
    }

    private Dialog createEnableServiceDialog(
            Context context,
            View.OnClickListener onClickListener,
            View.OnClickListener onClickListener2) {
        AccessibilityServiceInfo accessibilityServiceInfo = this.mAccessibilityServiceInfo;
        if (accessibilityServiceInfo != null) {
            return AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(
                    context,
                    accessibilityServiceInfo,
                    onClickListener,
                    onClickListener2,
                    new AssistantMenuPreferenceController$$ExternalSyntheticLambda4());
        }
        return null;
    }

    private void dismissEnableWarningDialog() {
        Dialog dialog = this.mEnableWarningDialog;
        if (dialog != null) {
            dialog.dismiss();
            this.mEnableWarningDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setChecked$0(View view) {
        onAllowButtonFromEnableToggleClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setChecked$1(View view) {
        dismissEnableWarningDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setChecked$2(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            AccessibilityUtils.setAccessibilityServiceState(
                    this.mContext, AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU, false);
            updateState(this.mPreference);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showExclusiveDialog$3(
            DialogInterface dialogInterface, int i) {
        AccessibilityUtils.setAccessibilityServiceState(
                this.mContext, AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU, true);
        updateState(this.mPreference);
    }

    private void onAllowButtonFromEnableToggleClicked() {
        dismissEnableWarningDialog();
        if (showExclusiveDialog()) {
            return;
        }
        AccessibilityUtils.setAccessibilityServiceState(
                this.mContext, AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU, true);
        updateState(this.mPreference);
    }

    private boolean showExclusiveDialog() {
        androidx.appcompat.app.AlertDialog createExclusiveDialog =
                AccessibilityDialogUtils.createExclusiveDialog(
                        this.mContext,
                        "assistant_menu",
                        new AssistantMenuPreferenceController$$ExternalSyntheticLambda2(this, 1),
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
        return SecAccessibilityUtils.isDesktopDualModeMonitorDisplay(this.mContext) ? 5 : 0;
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
        return "AssistantMenu";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_assistant_menu);
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
        return this.mContext.getText(R.string.assistant_menu_summary_button_text);
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
        return context.getText(R.string.assistant_menu_title);
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
        return AccessibilityUtils.getEnabledServicesFromSettings(this.mContext)
                .contains(AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU);
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
        if (!z) {
            Dialog createDisableDialog =
                    createDisableDialog(
                            this.mContext,
                            new AssistantMenuPreferenceController$$ExternalSyntheticLambda2(
                                    this, 0));
            if (createDisableDialog != null) {
                createDisableDialog.show();
            }
            return false;
        }
        Context context = this.mContext;
        ComponentName componentName = AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU;
        if (AccessibilityUtil.hasValuesInSettings(context, 543, componentName)) {
            if (showExclusiveDialog()) {
                return false;
            }
            AccessibilityUtils.setAccessibilityServiceState(this.mContext, componentName, true);
            updateState(this.mPreference);
            return true;
        }
        if (!((AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class))
                .isAccessibilityServiceWarningRequired(this.mAccessibilityServiceInfo)) {
            AccessibilityUtils.setAccessibilityServiceState(this.mContext, componentName, true);
            updateState(this.mPreference);
            return true;
        }
        final int i = 0;
        final int i2 = 1;
        Dialog createEnableServiceDialog =
                createEnableServiceDialog(
                        this.mContext,
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.dexterity.AssistantMenuPreferenceController$$ExternalSyntheticLambda0
                            public final /* synthetic */ AssistantMenuPreferenceController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i3 = i;
                                AssistantMenuPreferenceController
                                        assistantMenuPreferenceController = this.f$0;
                                switch (i3) {
                                    case 0:
                                        assistantMenuPreferenceController.lambda$setChecked$0(view);
                                        break;
                                    default:
                                        assistantMenuPreferenceController.lambda$setChecked$1(view);
                                        break;
                                }
                            }
                        },
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.dexterity.AssistantMenuPreferenceController$$ExternalSyntheticLambda0
                            public final /* synthetic */ AssistantMenuPreferenceController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i3 = i2;
                                AssistantMenuPreferenceController
                                        assistantMenuPreferenceController = this.f$0;
                                switch (i3) {
                                    case 0:
                                        assistantMenuPreferenceController.lambda$setChecked$0(view);
                                        break;
                                    default:
                                        assistantMenuPreferenceController.lambda$setChecked$1(view);
                                        break;
                                }
                            }
                        });
        this.mEnableWarningDialog = createEnableServiceDialog;
        if (createEnableServiceDialog != null) {
            createEnableServiceDialog.show();
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setCheckedForAction(boolean z) {
        if (z) {
            Context context = this.mContext;
            ComponentName componentName = AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU;
            if (AccessibilityUtil.hasValuesInSettings(context, 543, componentName)) {
                if (!AccessibilityExclusiveUtil.isExclusiveTaskEnabled(
                        this.mContext, "assistant_menu")) {
                    AccessibilityUtils.setAccessibilityServiceState(
                            this.mContext, componentName, true);
                    updateState(this.mPreference);
                    return true;
                }
                AccessibilityDialogUtils.startDialogActivity(this.mContext, 2, "assistant_menu");
            } else {
                if (!((AccessibilityManager)
                                this.mContext.getSystemService(AccessibilityManager.class))
                        .isAccessibilityServiceWarningRequired(this.mAccessibilityServiceInfo)) {
                    AccessibilityUtils.setAccessibilityServiceState(
                            this.mContext, componentName, true);
                    updateState(this.mPreference);
                    return true;
                }
                AccessibilityDialogUtils.startDialogActivity(
                        this.mContext, 0, componentName.flattenToString());
            }
        } else {
            AccessibilityDialogUtils.startDialogActivity(
                    this.mContext,
                    1,
                    AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU.flattenToString());
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        if (!"recommend".equals(controlValue.mControlId)
                && !"dialogActivity".equals(controlValue.mControlId)) {
            return super.setValue(controlValue);
        }
        AccessibilityUtils.setAccessibilityServiceState(
                this.mContext,
                AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU,
                Boolean.parseBoolean(controlValue.getValue()));
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$createEnableServiceDialog$4(View view) {}
}
