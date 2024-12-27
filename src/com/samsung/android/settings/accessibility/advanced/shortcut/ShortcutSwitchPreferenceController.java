package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.accessibility.dialog.AccessibilityServiceWarning;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settings.accessibility.PreferredShortcut;
import com.android.settings.accessibility.PreferredShortcuts;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShortcutSwitchPreferenceController extends TogglePreferenceController {
    static final String CATEGORY_ADVANCED = "advanced";
    static final String CATEGORY_DEXTERITY = "dexterity";
    static final String CATEGORY_HEARING = "hearing";
    static final String CATEGORY_NONE = "NONE";
    static final String CATEGORY_OTHERS = "others";
    static final String CATEGORY_TALKBACK = "talkback";
    static final String CATEGORY_VISIBILITY = "visibility";
    private static final String TAG = "ShortcutSwitchPreferenceController";
    private final boolean isFromQuickSettings;
    private boolean isVisible;
    private AccessibilityDialogUtils.TTSLanguageDialogInfo languageDialogInfo;
    private final AppOpsManager mAppOps;
    private final DevicePolicyManager mDpm;
    private Dialog mWarningDialog;
    private final ShortcutCandidate shortcutCandidate;
    private final ShortcutSwitchPreference shortcutSwitchPreference;
    private final int userShortcutType;

    public ShortcutSwitchPreferenceController(
            Context context, ShortcutCandidate shortcutCandidate, int i, boolean z) {
        super(context, shortcutCandidate.getKey());
        this.isVisible = true;
        this.userShortcutType = i;
        this.shortcutCandidate = shortcutCandidate;
        this.shortcutSwitchPreference =
                new ShortcutSwitchPreference(this.mContext, shortcutCandidate, i);
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mDpm = (DevicePolicyManager) context.getSystemService("device_policy");
        this.isFromQuickSettings = z;
    }

    private boolean checkExclusiveDialog(String str) {
        AlertDialog createExclusiveDialog =
                AccessibilityDialogUtils.createExclusiveDialog(
                        this.mContext,
                        str,
                        new ShortcutSwitchPreferenceController$$ExternalSyntheticLambda1(this, 1),
                        null);
        if (createExclusiveDialog == null) {
            return false;
        }
        createExclusiveDialog.show();
        return true;
    }

    private Intent createUninstallPackageActivityIntent(
            AccessibilityServiceInfo accessibilityServiceInfo) {
        if (accessibilityServiceInfo == null) {
            Log.w(TAG, "createUnInstallIntent -- invalid a11yServiceInfo");
            return null;
        }
        return new Intent(
                "android.intent.action.UNINSTALL_PACKAGE",
                Uri.parse(
                        "package:"
                                + accessibilityServiceInfo.getResolveInfo()
                                        .serviceInfo
                                        .applicationInfo
                                        .packageName));
    }

    private static ComponentName getComponentName(String str) {
        if (str == null) {
            return null;
        }
        switch (str) {
            case "universal_switch":
                return AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH;
            case "talkback":
                return AccessibilityConstant.COMPONENT_NAME_GOOGLE_TALKBACK;
            case "talkback_se":
                return AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK;
            case "assistant_menu":
                return AccessibilityConstant.COMPONENT_NAME_ASSISTANT_MENU;
            default:
                return ComponentName.unflattenFromString(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkExclusiveDialog$6(
            DialogInterface dialogInterface, int i) {
        setChecked(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$0(View view) {
        onAllowButtonFromEnableToggleClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$1(View view) {
        onDenyButtonFromEnableToggleClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$2(
            AccessibilityServiceInfo accessibilityServiceInfo, View view) {
        onDialogButtonFromUninstallClicked(accessibilityServiceInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$displayPreference$3(Preference preference, Object obj) {
        String exclusiveFeature = this.shortcutCandidate.getExclusiveFeature();
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (booleanValue) {
            ComponentName componentName = getComponentName(exclusiveFeature);
            if (componentName != null) {
                final AccessibilityServiceInfo accessibilityServiceInfo =
                        SecAccessibilityUtils.getAccessibilityServiceInfo(
                                this.mContext, componentName);
                if (accessibilityServiceInfo != null) {
                    this.languageDialogInfo =
                            new AccessibilityDialogUtils.TTSLanguageDialogInfo(
                                    this.mContext, accessibilityServiceInfo);
                    if (AccessibilityUtils.getEnabledServicesFromSettings(this.mContext)
                            .contains(componentName)) {
                        return setChecked(true);
                    }
                    if (!((AccessibilityManager)
                                    this.mContext.getSystemService(AccessibilityManager.class))
                            .isAccessibilityServiceWarningRequired(accessibilityServiceInfo)) {
                        if (checkExclusiveDialog(exclusiveFeature)) {
                            return false;
                        }
                        return setChecked(true);
                    }
                    final int i = 0;
                    final int i2 = 1;
                    android.app.AlertDialog createAccessibilityServiceWarningDialog =
                            AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(
                                    this.mContext,
                                    accessibilityServiceInfo,
                                    new View.OnClickListener(
                                            this) { // from class:
                                                    // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceController$$ExternalSyntheticLambda3
                                        public final /* synthetic */
                                        ShortcutSwitchPreferenceController f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            int i3 = i;
                                            ShortcutSwitchPreferenceController
                                                    shortcutSwitchPreferenceController = this.f$0;
                                            switch (i3) {
                                                case 0:
                                                    shortcutSwitchPreferenceController
                                                            .lambda$displayPreference$0(view);
                                                    break;
                                                default:
                                                    shortcutSwitchPreferenceController
                                                            .lambda$displayPreference$1(view);
                                                    break;
                                            }
                                        }
                                    },
                                    new View.OnClickListener(
                                            this) { // from class:
                                                    // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceController$$ExternalSyntheticLambda3
                                        public final /* synthetic */
                                        ShortcutSwitchPreferenceController f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            int i3 = i2;
                                            ShortcutSwitchPreferenceController
                                                    shortcutSwitchPreferenceController = this.f$0;
                                            switch (i3) {
                                                case 0:
                                                    shortcutSwitchPreferenceController
                                                            .lambda$displayPreference$0(view);
                                                    break;
                                                default:
                                                    shortcutSwitchPreferenceController
                                                            .lambda$displayPreference$1(view);
                                                    break;
                                            }
                                        }
                                    },
                                    new View
                                            .OnClickListener() { // from class:
                                                                 // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceController$$ExternalSyntheticLambda5
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            ShortcutSwitchPreferenceController.this
                                                    .lambda$displayPreference$2(
                                                            accessibilityServiceInfo, view);
                                        }
                                    });
                    this.mWarningDialog = createAccessibilityServiceWarningDialog;
                    createAccessibilityServiceWarningDialog.show();
                    return false;
                }
            } else if (exclusiveFeature != null && checkExclusiveDialog(exclusiveFeature)) {
                return false;
            }
        }
        return setChecked(booleanValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAllowButtonFromEnableToggleClicked$4(
            DialogInterface dialogInterface, int i) {
        if (showExclusiveDialog()) {
            return;
        }
        setChecked(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showExclusiveDialog$5(
            DialogInterface dialogInterface, int i) {
        setChecked(true);
    }

    private void onAllowButtonFromEnableToggleClicked() {
        AccessibilityDialogUtils.TTSLanguageDialogInfo tTSLanguageDialogInfo =
                this.languageDialogInfo;
        if (tTSLanguageDialogInfo != null && tTSLanguageDialogInfo.shouldShowDialog) {
            AccessibilityDialogUtils.createTTSLanguageCheckDialog(
                            this.mContext,
                            tTSLanguageDialogInfo,
                            new ShortcutSwitchPreferenceController$$ExternalSyntheticLambda1(
                                    this, 0))
                    .show();
        } else if (!showExclusiveDialog()) {
            setChecked(true);
        }
        Dialog dialog = this.mWarningDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void onDenyButtonFromEnableToggleClicked() {
        this.mWarningDialog.dismiss();
    }

    private void onDialogButtonFromUninstallClicked(
            AccessibilityServiceInfo accessibilityServiceInfo) {
        Intent createUninstallPackageActivityIntent =
                createUninstallPackageActivityIntent(accessibilityServiceInfo);
        if (createUninstallPackageActivityIntent == null) {
            return;
        }
        this.mContext.startActivity(createUninstallPackageActivityIntent);
    }

    private void setRestrictedPreferenceEnabled(Context context) {
        AccessibilityServiceInfo accessibilityServiceInfo;
        ComponentName unflattenFromString =
                ComponentName.unflattenFromString(this.shortcutCandidate.getKey());
        if (unflattenFromString == null
                || (accessibilityServiceInfo =
                                SecAccessibilityUtils.getAccessibilityServiceInfo(
                                        this.mContext, unflattenFromString))
                        == null) {
            return;
        }
        boolean contains =
                AccessibilityUtils.getEnabledServicesFromSettings(context)
                        .contains(unflattenFromString);
        String packageName = unflattenFromString.getPackageName();
        List permittedAccessibilityServices =
                this.mDpm.getPermittedAccessibilityServices(UserHandle.myUserId());
        boolean z = true;
        boolean z2 =
                permittedAccessibilityServices == null
                        || permittedAccessibilityServices.contains(packageName);
        int i = accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.uid;
        if (Flags.enhancedConfirmationModeApisEnabled()
                && android.security.Flags.extendEcmToAllSettings()) {
            boolean isEnhancedConfirmationRestricted =
                    RestrictedLockUtilsInternal.isEnhancedConfirmationRestricted(
                            this.mContext, packageName);
            if (isEnhancedConfirmationRestricted) {
                z2 = false;
            }
            if (z2 || contains) {
                this.shortcutSwitchPreference.setDisabledByAdmin(null);
                this.shortcutSwitchPreference.setDisabledByAppOps(-1, null);
                return;
            }
            RestrictedLockUtils.EnforcedAdmin checkIfAccessibilityServiceDisallowed =
                    RestrictedLockUtilsInternal.checkIfAccessibilityServiceDisallowed(
                            this.mContext, UserHandle.myUserId(), packageName);
            if (checkIfAccessibilityServiceDisallowed != null) {
                this.shortcutSwitchPreference.setDisabledByAdmin(
                        checkIfAccessibilityServiceDisallowed);
                return;
            } else {
                if (isEnhancedConfirmationRestricted) {
                    return;
                }
                this.shortcutSwitchPreference.setEnabled(false);
                return;
            }
        }
        if (z2) {
            try {
                int noteOpNoThrow = this.mAppOps.noteOpNoThrow(119, i, packageName);
                boolean z3 =
                        this.mContext
                                .getResources()
                                .getBoolean(R.bool.config_focusScrollContainersInTouchMode);
                Log.i(
                        TAG,
                        "ecmEnabled : "
                                + z3
                                + ", packageName : "
                                + packageName
                                + ", mode : "
                                + noteOpNoThrow);
                if (z3 && noteOpNoThrow != 0) {
                    z = false;
                }
                z2 = z;
            } catch (Exception e) {
                Log.e(TAG, "exception", e);
            }
        } else {
            z = false;
        }
        if (z2
                || contains
                || AccessibilityUtil.hasValuesInSettings(
                        context, this.userShortcutType, unflattenFromString)) {
            this.shortcutSwitchPreference.setDisabledByAdmin(null);
            this.shortcutSwitchPreference.setDisabledByAppOps(-1, null);
            return;
        }
        RestrictedLockUtils.EnforcedAdmin checkIfAccessibilityServiceDisallowed2 =
                RestrictedLockUtilsInternal.checkIfAccessibilityServiceDisallowed(
                        context, UserHandle.myUserId(), packageName);
        if (checkIfAccessibilityServiceDisallowed2 != null) {
            this.shortcutSwitchPreference.setDisabledByAdmin(
                    checkIfAccessibilityServiceDisallowed2);
        } else if (z) {
            this.shortcutSwitchPreference.setEnabled(false);
        } else {
            this.shortcutSwitchPreference.setDisabledByAppOps(i, packageName);
        }
    }

    private boolean showExclusiveDialog() {
        String exclusiveFeature = this.shortcutCandidate.getExclusiveFeature();
        if (exclusiveFeature == null) {
            return false;
        }
        if (exclusiveFeature.equals(
                AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK.flattenToString())) {
            exclusiveFeature = "talkback_se";
        }
        AlertDialog createExclusiveDialog =
                AccessibilityDialogUtils.createExclusiveDialog(
                        this.mContext,
                        exclusiveFeature,
                        new ShortcutSwitchPreferenceController$$ExternalSyntheticLambda1(this, 2),
                        null);
        if (createExclusiveDialog == null) {
            return false;
        }
        createExclusiveDialog.show();
        return true;
    }

    private void updateShortcutPreferenceData() {
        int valuesInSettings =
                SecAccessibilityUtils.getValuesInSettings(
                        this.mContext, this.shortcutCandidate.getKey());
        if (valuesInSettings != 0) {
            PreferredShortcuts.saveUserShortcutType(
                    this.mContext,
                    new PreferredShortcut(this.shortcutCandidate.getKey(), valuesInSettings));
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.shortcutSwitchPreference.setPersistent();
        this.shortcutSwitchPreference.setKey(this.shortcutCandidate.getKey());
        this.shortcutSwitchPreference.setTitle(this.shortcutCandidate.getValue());
        this.shortcutSwitchPreference.setIcon(this.shortcutCandidate.getIcon());
        this.shortcutSwitchPreference.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        boolean lambda$displayPreference$3;
                        lambda$displayPreference$3 =
                                ShortcutSwitchPreferenceController.this.lambda$displayPreference$3(
                                        preference, obj);
                        return lambda$displayPreference$3;
                    }
                });
        String category = this.shortcutCandidate.getCategory();
        if ("NONE".equals(category)
                || (this.isFromQuickSettings && CATEGORY_VISIBILITY.equals(category))) {
            preferenceScreen.addPreference(this.shortcutSwitchPreference);
            return;
        }
        if (category == null) {
            Log.w(TAG, "categoryKey is null.");
            preferenceScreen.addPreference(this.shortcutSwitchPreference);
            return;
        }
        Preference findPreference = preferenceScreen.findPreference(category);
        if (findPreference instanceof PreferenceCategory) {
            ((PreferenceCategory) findPreference).addPreference(this.shortcutSwitchPreference);
            return;
        }
        if (findPreference != null) {
            Log.w(TAG, "preference is not instance of PreferenceCategory.");
            preferenceScreen.addPreference(this.shortcutSwitchPreference);
        } else {
            PreferenceCategory preferenceCategory = new PreferenceCategory(this.mContext);
            preferenceCategory.setKey(category);
            preferenceScreen.addPreference(preferenceCategory);
            preferenceCategory.addPreference(this.shortcutSwitchPreference);
        }
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
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
        Context context = this.mContext;
        int i = this.userShortcutType;
        String key = this.shortcutCandidate.getKey();
        String shortcutService = SecAccessibilityUtils.getShortcutService(context, i);
        if (TextUtils.isEmpty(shortcutService)) {
            return false;
        }
        return new HashSet(Arrays.asList(shortcutService.split(String.valueOf(':')))).contains(key);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
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
        this.shortcutSwitchPreference.setChecked(z);
        Context context = this.mContext;
        int i = this.userShortcutType;
        String key = this.shortcutCandidate.getKey();
        if (z) {
            SecAccessibilityUtils.setUserShortcutType(
                    context, SecAccessibilityUtils.getValuesInSettings(context, key) | i, key);
            SecAccessibilityUtils.optInAllValuesToSettings(context, i, key);
        } else {
            HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
            int retrieveUserShortcutType =
                    PreferredShortcuts.retrieveUserShortcutType(context, 0, key) & (~i);
            if (retrieveUserShortcutType != 0) {
                SecAccessibilityUtils.setUserShortcutType(context, retrieveUserShortcutType, key);
            }
            SecAccessibilityUtils.optOutAllValuesFromSettings(context, i, key);
        }
        SecAccessibilityUtils.setAccessibilityServiceState(context, key, z);
        updateShortcutPreferenceData();
        return true;
    }

    public void setVisible(boolean z) {
        this.isVisible = z;
        updateState(this.shortcutSwitchPreference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (!this.isVisible) {
            preference.setVisible(false);
            return;
        }
        preference.setVisible(true);
        setRestrictedPreferenceEnabled(this.mContext);
        this.shortcutSwitchPreference.updateCheckBox();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
