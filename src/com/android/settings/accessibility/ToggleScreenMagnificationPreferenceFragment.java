package com.android.settings.accessibility;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CheckBox;
import android.widget.ScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.DialogCreatable;
import com.android.settings.R;
import com.android.settings.accessibility.AccessibilitySettingsContentObserver;
import com.android.settings.accessibility.MagnificationModePreferenceController;
import com.android.settings.utils.LocaleUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.IllustrationPreference;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutContentObserver;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleScreenMagnificationPreferenceFragment extends ToggleFeaturePreferenceFragment implements MagnificationModePreferenceController.DialogHelper {
    public static final TextUtils.SimpleStringSplitter sStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
    public DialogCreatable mDialogDelegate;
    public CheckBox mDirectTypeCheckBox;
    public CheckBox mHardwareTypeCheckBox;
    public boolean mInSetupWizard;
    public CheckBox mSoftwareTypeCheckBox;
    public ToggleScreenMagnificationPreferenceFragment$$ExternalSyntheticLambda4 mTouchExplorationStateChangeListener;
    public CheckBox mTripleTapTypeCheckBox;
    public ShortcutContentObserver.AnonymousClass1 shortcutContentObserver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    public static int getUserShortcutTypeFromSettings(Context context) {
        boolean hasMagnificationValuesInSettings = hasMagnificationValuesInSettings(context, 1);
        boolean z = hasMagnificationValuesInSettings;
        if (hasMagnificationValuesInSettings(context, 2)) {
            z = (hasMagnificationValuesInSettings ? 1 : 0) | 2;
        }
        ?? r0 = z;
        if (hasMagnificationValuesInSettings(context, 4)) {
            r0 = (z ? 1 : 0) | 4;
        }
        return hasMagnificationValuesInSettings(context, 512) ? r0 | 512 : r0;
    }

    public static boolean hasMagnificationValueInSettings(Context context, int i) {
        TextUtils.SimpleStringSplitter simpleStringSplitter;
        if (i == 4) {
            return Settings.Secure.getInt(context.getContentResolver(), "accessibility_display_magnification_enabled", 0) == 1;
        }
        int i2 = AccessibilityUtil.$r8$clinit;
        String string = Settings.Secure.getString(context.getContentResolver(), ShortcutUtils.convertToKey(i));
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        sStringColonSplitter.setString(string);
        do {
            simpleStringSplitter = sStringColonSplitter;
            if (!simpleStringSplitter.hasNext()) {
                return false;
            }
        } while (!"com.android.server.accessibility.MagnificationController".equals(simpleStringSplitter.next()));
        return true;
    }

    @VisibleForTesting
    public static boolean hasMagnificationValuesInSettings(Context context, int i) {
        boolean hasMagnificationValueInSettings = (i & 1) == 1 ? hasMagnificationValueInSettings(context, 1) : false;
        if ((i & 2) == 2) {
            hasMagnificationValueInSettings |= hasMagnificationValueInSettings(context, 2);
        }
        if ((i & 4) == 4) {
            hasMagnificationValueInSettings |= hasMagnificationValueInSettings(context, 4);
        }
        return (i & 512) == 512 ? hasMagnificationValueInSettings | hasMagnificationValueInSettings(context, 512) : hasMagnificationValueInSettings;
    }

    public static boolean hasShortcutType(int i, int i2) {
        return (i & i2) == i2;
    }

    @VisibleForTesting
    public static void optInAllMagnificationValuesToSettings(Context context, int i) {
        if ((i & 1) == 1) {
            optInMagnificationValueToSettings(context, 1);
        }
        if ((i & 2) == 2) {
            optInMagnificationValueToSettings(context, 2);
        }
        if ((i & 4) == 4) {
            optInMagnificationValueToSettings(context, 4);
        }
        if ((i & 512) == 512) {
            optInMagnificationValueToSettings(context, 512);
        }
        if ((i & 16) == 16) {
            optInMagnificationValueToSettings(context, 16);
        }
    }

    public static void optInMagnificationValueToSettings(Context context, int i) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(true, i, Set.of("com.android.server.accessibility.MagnificationController"), UserHandle.myUserId());
        }
    }

    @VisibleForTesting
    public static void optOutAllMagnificationValuesFromSettings(Context context, int i) {
        if ((i & 1) == 1) {
            optOutMagnificationValueFromSettings(context, 1);
        }
        if ((i & 2) == 2) {
            optOutMagnificationValueFromSettings(context, 2);
        }
        if ((i & 4) == 4) {
            optOutMagnificationValueFromSettings(context, 4);
        }
        if ((i & 512) == 512) {
            optOutMagnificationValueFromSettings(context, 512);
        }
        if ((i & 16) == 16) {
            optOutMagnificationValueFromSettings(context, 16);
        }
    }

    public static void optOutMagnificationValueFromSettings(Context context, int i) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(false, i, Set.of("com.android.server.accessibility.MagnificationController"), UserHandle.myUserId());
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void callOnAlertDialogCheckboxClicked(DialogInterface dialogInterface, int i) {
        int shortcutTypeCheckBoxValue = getShortcutTypeCheckBoxValue();
        saveNonEmptyUserShortcutType(shortcutTypeCheckBoxValue);
        optInAllMagnificationValuesToSettings(getPrefContext(), shortcutTypeCheckBoxValue);
        optOutAllMagnificationValuesFromSettings(getPrefContext(), ~shortcutTypeCheckBoxValue);
        this.mShortcutPreference.setChecked(shortcutTypeCheckBoxValue != 0);
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
        if (this.mHardwareTypeCheckBox.isChecked()) {
            Context prefContext = getPrefContext();
            int i2 = AccessibilityUtil.$r8$clinit;
            Settings.Secure.putInt(prefContext.getContentResolver(), "skip_accessibility_shortcut_dialog_timeout_restriction", 1);
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        int dialogMetricsCategory;
        DialogCreatable dialogCreatable = this.mDialogDelegate;
        if (dialogCreatable != null && (dialogMetricsCategory = dialogCreatable.getDialogMetricsCategory(i)) != 0) {
            return dialogMetricsCategory;
        }
        if (i == 1001 || i == 9001) {
            return 1813;
        }
        return i != 1006 ? i != 1007 ? super.getDialogMetricsCategory(i) : VolteConstants.ErrorCode.CALL_RING_TIMER_EXPIRED : VolteConstants.ErrorCode.FAILED_TO_GO_READY;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getExclusiveTaskName() {
        return "magnification_shortcut";
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public String getTAG() {
        return "ToggleScreenMagnificationPreferenceFragment";
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment, com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return 7;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    public int getPreferenceScreenResId() {
        return 0;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final String getShortcutPreferenceKey() {
        return AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToString();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final CharSequence getShortcutTitle() {
        return getString(R.string.accessibility_shortcut_title, getText(R.string.accessibility_screen_magnification_title));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final int getShortcutTypeCheckBoxValue() {
        CheckBox checkBox = this.mSoftwareTypeCheckBox;
        if (checkBox == null || this.mHardwareTypeCheckBox == null) {
            return -1;
        }
        boolean isChecked = checkBox.isChecked();
        boolean z = isChecked;
        if (this.mHardwareTypeCheckBox.isChecked()) {
            z = (isChecked ? 1 : 0) | 2;
        }
        ?? r0 = z;
        if (this.mTripleTapTypeCheckBox.isChecked()) {
            r0 = (z ? 1 : 0) | 4;
        }
        return this.mDirectTypeCheckBox.isChecked() ? r0 | 512 : r0;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final CharSequence getShortcutTypeSummary(Context context) {
        if (!this.mShortcutPreference.mChecked) {
            return null;
        }
        int retrieveUserShortcutType = PreferredShortcuts.retrieveUserShortcutType(context, 0, "com.android.server.accessibility.MagnificationController");
        ArrayList arrayList = new ArrayList();
        if (hasShortcutType(retrieveUserShortcutType, 16)) {
            arrayList.add(context.getText(R.string.accessibility_feature_shortcut_setting_summary_quick_settings));
        }
        boolean hasShortcutType = hasShortcutType(retrieveUserShortcutType, 1);
        int i = R.string.accessibility_shortcut_summary_accessibility_gesture;
        if (hasShortcutType) {
            arrayList.add(context.getText((!AccessibilityUtil.isFloatingMenuEnabled(context) && AccessibilityUtil.isGestureNavigateEnabled(context)) ? R.string.accessibility_shortcut_summary_accessibility_gesture : R.string.accessibility_shortcut_summary_software));
        }
        if (hasShortcutType(retrieveUserShortcutType, 512)) {
            arrayList.add(context.getText(R.string.accessibility_shortcut_summary_direct));
        }
        if (hasShortcutType(retrieveUserShortcutType, 2)) {
            arrayList.add(context.getText(R.string.accessibility_shortcut_summary_hardware));
        }
        if (hasShortcutType(retrieveUserShortcutType, 4)) {
            arrayList.add(context.getText(R.string.accessibility_shortcut_summary_triple_tap));
        }
        if (arrayList.isEmpty()) {
            if (AccessibilityUtil.isFloatingMenuEnabled(context) || !AccessibilityUtil.isGestureNavigateEnabled(context)) {
                i = R.string.accessibility_shortcut_summary_software;
            }
            arrayList.add(context.getText(i));
        }
        return CaseMap.toTitle().wholeString().noLowercase().apply(Locale.getDefault(), null, LocaleUtils.getConcatenatedString(arrayList));
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final int getUserPreferredShortcutTypes() {
        return PreferredShortcuts.retrieveUserShortcutType(getPrefContext(), 0, "com.android.server.accessibility.MagnificationController");
    }

    public final void handleMagnificationEnabled(int i, boolean z) {
        if (i == 0) {
            super.showDialog(9001);
            return;
        }
        if (z) {
            optInAllMagnificationValuesToSettings(getPrefContext(), i);
        } else {
            optOutAllMagnificationValuesFromSettings(getPrefContext(), i);
        }
        this.mShortcutPreference.setChecked(z);
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void initSettingsPreference() {
        if (getContext().getResources().getBoolean(android.R.bool.config_navBarDefaultTransparent) && getContext().getPackageManager().hasSystemFeature("android.software.window_magnification")) {
            Preference preference = new Preference(getPrefContext());
            this.mSettingsPreference = preference;
            preference.setTitle(R.string.magnification_mode_title);
            this.mSettingsPreference.setKey("screen_magnification_mode");
            this.mSettingsPreference.setPersistent();
            MagnificationModePreferenceController magnificationModePreferenceController = new MagnificationModePreferenceController(getContext(), "screen_magnification_mode");
            magnificationModePreferenceController.setDialogHelper(this);
            getSettingsLifecycle().addObserver(magnificationModePreferenceController);
            SwitchPreferenceCompat switchPreferenceCompat = new SwitchPreferenceCompat(getPrefContext());
            switchPreferenceCompat.setTitle(R.string.accessibility_screen_magnification_follow_typing_title);
            switchPreferenceCompat.setSummary(R.string.accessibility_screen_magnification_follow_typing_summary);
            switchPreferenceCompat.setKey("magnification_follow_typing");
            MagnificationFollowTypingPreferenceController magnificationFollowTypingPreferenceController = new MagnificationFollowTypingPreferenceController(getContext(), "magnification_follow_typing");
            magnificationFollowTypingPreferenceController.setInSetupWizard(this.mInSetupWizard);
            magnificationFollowTypingPreferenceController.displayPreference(getPreferenceScreen());
            addPreferenceController(magnificationFollowTypingPreferenceController);
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void initShortcutPreference() {
        ShortcutPreference shortcutPreference = new ShortcutPreference(getPrefContext());
        this.mShortcutPreference = shortcutPreference;
        shortcutPreference.setPersistent();
        ShortcutPreference shortcutPreference2 = this.mShortcutPreference;
        ComponentName componentName = AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME;
        shortcutPreference2.setKey(componentName.flattenToString());
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
        ShortcutPreference shortcutPreference3 = this.mShortcutPreference;
        shortcutPreference3.mClickCallback = this;
        shortcutPreference3.setTitle(getShortcutTitle());
        ShortcutPreference shortcutPreference4 = this.mShortcutPreference;
        shortcutPreference4.mComponentName = componentName;
        shortcutPreference4.mExclusiveTaskName = "magnification_shortcut";
        shortcutPreference4.mLabel = this.mPackageName;
        getPreferenceScreen().addPreference(this.mShortcutPreference);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().setTitle(R.string.accessibility_screen_magnification_title);
        this.mInSetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        final int i2 = 1;
        final int i3 = 0;
        DialogCreatable dialogCreatable = this.mDialogDelegate;
        if (dialogCreatable != null) {
            Dialog onCreateDialog = dialogCreatable.onCreateDialog(i);
            this.mDialog = onCreateDialog;
            if (onCreateDialog != null) {
                return onCreateDialog;
            }
        }
        if (i == 1001) {
            AlertDialog createDialog = AccessibilityDialogUtils.createDialog(this.mInSetupWizard ? 3 : 2, getPrefContext(), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment$$ExternalSyntheticLambda1
                public final /* synthetic */ ToggleScreenMagnificationPreferenceFragment f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    int i5 = i3;
                    ToggleScreenMagnificationPreferenceFragment toggleScreenMagnificationPreferenceFragment = this.f$0;
                    switch (i5) {
                        case 0:
                            toggleScreenMagnificationPreferenceFragment.callOnAlertDialogCheckboxClicked(dialogInterface, i4);
                            break;
                        default:
                            TextUtils.SimpleStringSplitter simpleStringSplitter = ToggleScreenMagnificationPreferenceFragment.sStringColonSplitter;
                            toggleScreenMagnificationPreferenceFragment.handleMagnificationEnabled(PreferredShortcuts.retrieveUserShortcutType(toggleScreenMagnificationPreferenceFragment.getPrefContext(), 0, "com.android.server.accessibility.MagnificationController"), true);
                            toggleScreenMagnificationPreferenceFragment.mDialog.dismiss();
                            break;
                    }
                }
            }, getShortcutTitle());
            createDialog.show();
            ((ScrollView) createDialog.findViewById(R.id.container_layout)).setScrollIndicators(3, 3);
            this.mDialog = createDialog;
            setupMagnificationEditShortcutDialog(createDialog);
            return this.mDialog;
        }
        if (i == 1007) {
            Context prefContext = getPrefContext();
            AccessibilityShortcutsTutorial$$ExternalSyntheticLambda6 accessibilityShortcutsTutorial$$ExternalSyntheticLambda6 = AccessibilityShortcutsTutorial.ON_CLICK_LISTENER;
            AlertDialog.Builder builder = new AlertDialog.Builder(prefContext);
            builder.setView(AccessibilityShortcutsTutorial.createTutorialDialogContentView(prefContext, 1));
            builder.setPositiveButton(R.string.accessibility_tutorial_dialog_button, AccessibilityShortcutsTutorial.ON_CLICK_LISTENER);
            AlertDialog create = builder.create();
            create.requestWindowFeature(1);
            create.setCanceledOnTouchOutside(false);
            create.show();
            return create;
        }
        if (i == 9001) {
            this.mShortcutPreference.setChecked(false);
            AlertDialog showEditShortcutDialog = AccessibilityDialogUtils.showEditShortcutDialog(getPrefContext(), 91, getText(R.string.accessibility_tutorial_dialog_title), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment$$ExternalSyntheticLambda1
                public final /* synthetic */ ToggleScreenMagnificationPreferenceFragment f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    int i5 = i3;
                    ToggleScreenMagnificationPreferenceFragment toggleScreenMagnificationPreferenceFragment = this.f$0;
                    switch (i5) {
                        case 0:
                            toggleScreenMagnificationPreferenceFragment.callOnAlertDialogCheckboxClicked(dialogInterface, i4);
                            break;
                        default:
                            TextUtils.SimpleStringSplitter simpleStringSplitter = ToggleScreenMagnificationPreferenceFragment.sStringColonSplitter;
                            toggleScreenMagnificationPreferenceFragment.handleMagnificationEnabled(PreferredShortcuts.retrieveUserShortcutType(toggleScreenMagnificationPreferenceFragment.getPrefContext(), 0, "com.android.server.accessibility.MagnificationController"), true);
                            toggleScreenMagnificationPreferenceFragment.mDialog.dismiss();
                            break;
                    }
                }
            }, "com.android.server.accessibility.MagnificationController", this.mPackageName);
            this.mDialog = showEditShortcutDialog;
            setupMagnificationEditShortcutDialog(showEditShortcutDialog);
            return this.mDialog;
        }
        if (i != 9003) {
            return super.onCreateDialog(i);
        }
        this.mShortcutPreference.setChecked(false);
        AlertDialog createExclusiveDialog = AccessibilityDialogUtils.createExclusiveDialog(getPrefContext(), "magnification_shortcut", new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment$$ExternalSyntheticLambda1
            public final /* synthetic */ ToggleScreenMagnificationPreferenceFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i4) {
                int i5 = i2;
                ToggleScreenMagnificationPreferenceFragment toggleScreenMagnificationPreferenceFragment = this.f$0;
                switch (i5) {
                    case 0:
                        toggleScreenMagnificationPreferenceFragment.callOnAlertDialogCheckboxClicked(dialogInterface, i4);
                        break;
                    default:
                        TextUtils.SimpleStringSplitter simpleStringSplitter = ToggleScreenMagnificationPreferenceFragment.sStringColonSplitter;
                        toggleScreenMagnificationPreferenceFragment.handleMagnificationEnabled(PreferredShortcuts.retrieveUserShortcutType(toggleScreenMagnificationPreferenceFragment.getPrefContext(), 0, "com.android.server.accessibility.MagnificationController"), true);
                        toggleScreenMagnificationPreferenceFragment.mDialog.dismiss();
                        break;
                }
            }
        }, null);
        this.mDialog = createExclusiveDialog;
        return createExclusiveDialog;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment$$ExternalSyntheticLambda4] */
    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment, com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mPackageName = getString(R.string.accessibility_screen_magnification_title);
        this.mTouchExplorationStateChangeListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment$$ExternalSyntheticLambda4
            @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
            public final void onTouchExplorationStateChanged(boolean z) {
                ToggleScreenMagnificationPreferenceFragment toggleScreenMagnificationPreferenceFragment = ToggleScreenMagnificationPreferenceFragment.this;
                TextUtils.SimpleStringSplitter simpleStringSplitter = ToggleScreenMagnificationPreferenceFragment.sStringColonSplitter;
                toggleScreenMagnificationPreferenceFragment.removeDialog(1);
                toggleScreenMagnificationPreferenceFragment.mShortcutPreference.setSummary(toggleScreenMagnificationPreferenceFragment.getShortcutTypeSummary(toggleScreenMagnificationPreferenceFragment.getPrefContext()));
            }
        };
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        Context prefContext = getPrefContext();
        int i = ShortcutContentObserver.$r8$clinit;
        this.shortcutContentObserver = new ShortcutContentObserver.AnonymousClass1(new Handler(), prefContext, 0);
        return onCreateView;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onInstallSwitchPreferenceToggleSwitch() {
        this.mToggleServiceSwitchBar.hide();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        ((AccessibilityManager) getPrefContext().getSystemService(AccessibilityManager.class)).removeTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener);
        ShortcutContentObserver.AnonymousClass1 anonymousClass1 = this.shortcutContentObserver;
        if (anonymousClass1 != null) {
            getPrefContext().getContentResolver().unregisterContentObserver(anonymousClass1);
        }
        super.onPause();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {
        if (z) {
            TextUtils.equals("accessibility_display_magnification_navbar_enabled", str);
        }
        Settings.Secure.putInt(getContentResolver(), str, z ? 1 : 0);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onProcessArguments(Bundle bundle) {
        getContext();
        if (!bundle.containsKey("preference_key")) {
            bundle.putString("preference_key", "accessibility_display_magnification_enabled");
        }
        super.onProcessArguments(bundle);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        IllustrationPreference illustrationPreference = (IllustrationPreference) getPreferenceScreen().findPreference("animated_image");
        if (illustrationPreference != null) {
            illustrationPreference.mLottieDynamicColor = true;
            illustrationPreference.notifyChanged();
        }
        ((AccessibilityManager) getPrefContext().getSystemService(AccessibilityManager.class)).addTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener);
        ShortcutContentObserver.AnonymousClass1 anonymousClass1 = this.shortcutContentObserver;
        if (anonymousClass1 != null) {
            anonymousClass1.register(getPrefContext().getContentResolver());
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment, com.android.settings.accessibility.ShortcutPreference.OnClickCallback
    public final void onToggleClicked(ShortcutPreference shortcutPreference) {
        int userPreferredShortcutTypes = getUserPreferredShortcutTypes();
        if (AccessibilityExclusiveUtil.isExclusiveTaskEnabled(getPrefContext(), "magnification_shortcut")) {
            super.showDialog(9003);
        } else {
            handleMagnificationEnabled(userPreferredShortcutTypes, shortcutPreference.mChecked);
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void registerKeysToObserverCallback(AccessibilitySettingsContentObserver accessibilitySettingsContentObserver) {
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(List.of("accessibility_magnification_follow_typing_enabled", "accessibility_magnification_always_on_enabled", "accessibility_magnification_joystick_enabled"), new AccessibilitySettingsContentObserver.ContentObserverCallback() { // from class: com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment$$ExternalSyntheticLambda0
            @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
            public final void onChange(String str) {
                TextUtils.SimpleStringSplitter simpleStringSplitter = ToggleScreenMagnificationPreferenceFragment.sStringColonSplitter;
                final ToggleScreenMagnificationPreferenceFragment toggleScreenMagnificationPreferenceFragment = ToggleScreenMagnificationPreferenceFragment.this;
                toggleScreenMagnificationPreferenceFragment.getClass();
                ArrayList arrayList = new ArrayList();
                toggleScreenMagnificationPreferenceFragment.getPreferenceControllers().forEach(new AccessibilitySettings$$ExternalSyntheticLambda3(0, arrayList));
                arrayList.forEach(new Consumer() { // from class: com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ToggleScreenMagnificationPreferenceFragment toggleScreenMagnificationPreferenceFragment2 = ToggleScreenMagnificationPreferenceFragment.this;
                        AbstractPreferenceController abstractPreferenceController = (AbstractPreferenceController) obj;
                        TextUtils.SimpleStringSplitter simpleStringSplitter2 = ToggleScreenMagnificationPreferenceFragment.sStringColonSplitter;
                        toggleScreenMagnificationPreferenceFragment2.getClass();
                        abstractPreferenceController.updateState(toggleScreenMagnificationPreferenceFragment2.findPreference(abstractPreferenceController.getPreferenceKey()));
                    }
                });
            }
        });
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    @VisibleForTesting
    public void saveNonEmptyUserShortcutType(int i) {
        if (i == 0) {
            return;
        }
        PreferredShortcuts.saveUserShortcutType(getPrefContext(), new PreferredShortcut("com.android.server.accessibility.MagnificationController", i));
    }

    public final void setDialogTextAreaClickListener(View view, final CheckBox checkBox) {
        view.findViewById(R.id.container).setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.accessibility.ToggleScreenMagnificationPreferenceFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ToggleScreenMagnificationPreferenceFragment toggleScreenMagnificationPreferenceFragment = ToggleScreenMagnificationPreferenceFragment.this;
                CheckBox checkBox2 = checkBox;
                TextUtils.SimpleStringSplitter simpleStringSplitter = ToggleScreenMagnificationPreferenceFragment.sStringColonSplitter;
                toggleScreenMagnificationPreferenceFragment.getClass();
                checkBox2.toggle();
                AlertDialog alertDialog = (AlertDialog) toggleScreenMagnificationPreferenceFragment.mDialog;
                if (alertDialog.getButton(-1) != null) {
                    alertDialog.getButton(-1).setEnabled(toggleScreenMagnificationPreferenceFragment.getShortcutTypeCheckBoxValue() != 0);
                }
            }
        });
    }

    @VisibleForTesting
    public void setupMagnificationEditShortcutDialog(Dialog dialog) {
        View findViewById = dialog.findViewById(R.id.software_shortcut);
        CheckBox checkBox = (CheckBox) findViewById.findViewById(R.id.checkbox);
        this.mSoftwareTypeCheckBox = checkBox;
        setDialogTextAreaClickListener(findViewById, checkBox);
        View findViewById2 = dialog.findViewById(R.id.hardware_shortcut);
        CheckBox checkBox2 = (CheckBox) findViewById2.findViewById(R.id.checkbox);
        this.mHardwareTypeCheckBox = checkBox2;
        setDialogTextAreaClickListener(findViewById2, checkBox2);
        View findViewById3 = dialog.findViewById(R.id.triple_tap_shortcut);
        CheckBox checkBox3 = (CheckBox) findViewById3.findViewById(R.id.checkbox);
        this.mTripleTapTypeCheckBox = checkBox3;
        setDialogTextAreaClickListener(findViewById3, checkBox3);
        View findViewById4 = dialog.findViewById(R.id.advanced_shortcut);
        if (this.mTripleTapTypeCheckBox.isChecked()) {
            findViewById4.setVisibility(8);
            findViewById3.setVisibility(0);
        }
        View findViewById5 = dialog.findViewById(R.id.direct_shortcut);
        CheckBox checkBox4 = (CheckBox) findViewById5.findViewById(R.id.checkbox);
        this.mDirectTypeCheckBox = checkBox4;
        setDialogTextAreaClickListener(findViewById5, checkBox4);
        AlertDialog alertDialog = (AlertDialog) dialog;
        if (alertDialog.getButton(-1) != null) {
            alertDialog.getButton(-1).setEnabled(getShortcutTypeCheckBoxValue() != 0);
        }
        int i = this.mSavedCheckBoxValue;
        this.mSavedCheckBoxValue = -1;
        if (i == -1) {
            i = this.mShortcutPreference.mChecked ? getUserPreferredShortcutTypes() : 0;
        }
        this.mSoftwareTypeCheckBox.setChecked(hasShortcutType(i, 1));
        this.mHardwareTypeCheckBox.setChecked(hasShortcutType(i, 2));
        this.mTripleTapTypeCheckBox.setChecked(hasShortcutType(i, 4));
        this.mDirectTypeCheckBox.setChecked(hasShortcutType(i, 512));
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateShortcutPreference() {
        this.mShortcutPreference.setChecked(hasMagnificationValuesInSettings(getPrefContext(), getUserPreferredShortcutTypes()));
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateShortcutPreferenceData() {
        int userShortcutTypeFromSettings = getUserShortcutTypeFromSettings(getPrefContext());
        if (userShortcutTypeFromSettings != 0) {
            PreferredShortcuts.saveUserShortcutType(getPrefContext(), new PreferredShortcut("com.android.server.accessibility.MagnificationController", userShortcutTypeFromSettings));
        }
    }
}
