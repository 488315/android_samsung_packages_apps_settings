package com.android.settings.accessibility;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CheckBox;
import android.widget.ScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceCategory;
import com.android.settings.R;
import com.android.settings.accessibility.AccessibilitySettingsContentObserver;
import com.android.settings.accessibility.ShortcutPreference;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.gestures.OneHandedSettings;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.utils.LocaleUtils;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilityShortcutPreferenceFragment extends RestrictedDashboardFragment implements ShortcutPreference.OnClickCallback {
    public AlertDialog mDialog;
    public CheckBox mDirectTypeCheckBox;
    public CheckBox mHardwareTypeCheckBox;
    public boolean mNeedsQSTooltipReshow;
    public int mNeedsQSTooltipType;
    public int mPackageNameResourceId;
    public CheckBox mQuickSettingsTypeCheckBox;
    public int mSavedCheckBoxValue;
    public AccessibilitySettingsContentObserver mSettingsContentObserver;
    public ShortcutPreference mShortcutPreference;
    public CheckBox mSoftwareTypeCheckBox;
    public AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda0 mTouchExplorationStateChangeListener;

    public AccessibilityShortcutPreferenceFragment(String str) {
        super(str);
        this.mPackageNameResourceId = -1;
        this.mSavedCheckBoxValue = -1;
        this.mNeedsQSTooltipReshow = false;
        this.mNeedsQSTooltipType = 0;
    }

    public static boolean hasShortcutType$2(int i, int i2) {
        return (i & i2) == i2;
    }

    public abstract ComponentName getComponentName();

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.DialogCreatable
    public int getDialogMetricsCategory(int i) {
        if (i == 1) {
            return 1812;
        }
        if (i != 1008) {
            return i != 9000 ? 0 : 1812;
        }
        return 1810;
    }

    public abstract CharSequence getLabelName();

    public String getShortcutPreferenceKey() {
        return "shortcut_preference";
    }

    public CharSequence getShortcutTitle() {
        return getString(R.string.accessibility_shortcut_title, getLabelName());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
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
        if (this.mDirectTypeCheckBox.isChecked()) {
            r0 = (z ? 1 : 0) | 512;
        }
        return this.mQuickSettingsTypeCheckBox.isChecked() ? r0 | 16 : r0;
    }

    public final CharSequence getShortcutTypeSummary(Context context) {
        ShortcutPreference shortcutPreference = this.mShortcutPreference;
        if (!shortcutPreference.mSettingsEditable) {
            return context.getText(R.string.accessibility_shortcut_edit_dialog_title_hardware);
        }
        if (!shortcutPreference.mChecked) {
            return null;
        }
        int userPreferredShortcutTypes = getUserPreferredShortcutTypes();
        ArrayList arrayList = new ArrayList();
        if (hasShortcutType$2(userPreferredShortcutTypes, 16)) {
            arrayList.add(context.getText(R.string.accessibility_feature_shortcut_setting_summary_quick_settings));
        }
        if (hasShortcutType$2(userPreferredShortcutTypes, 1)) {
            arrayList.add(AccessibilityUtil.isFloatingMenuEnabled(context) ? context.getText(R.string.accessibility_shortcut_summary_software) : AccessibilityUtil.isGestureNavigateEnabled(context) ? context.getText(R.string.accessibility_shortcut_summary_accessibility_gesture) : context.getText(R.string.accessibility_shortcut_summary_software));
        }
        if (hasShortcutType$2(userPreferredShortcutTypes, 512)) {
            arrayList.add(context.getText(R.string.accessibility_shortcut_summary_direct));
        }
        if (hasShortcutType$2(userPreferredShortcutTypes, 2)) {
            arrayList.add(context.getText(R.string.accessibility_shortcut_summary_hardware));
        }
        return CaseMap.toTitle().wholeString().noLowercase().apply(Locale.getDefault(), null, LocaleUtils.getConcatenatedString(arrayList));
    }

    public final int getUserPreferredShortcutTypes() {
        return PreferredShortcuts.retrieveUserShortcutType(getPrefContext(), 0, getComponentName().flattenToString());
    }

    public void initGeneralCategory() {
        PreferenceCategory preferenceCategory = new PreferenceCategory(getPrefContext());
        preferenceCategory.setKey("general_categories");
        preferenceCategory.setTitle(getContext().getString(R.string.accessibility_screen_option));
        getPreferenceScreen().addPreference(preferenceCategory);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        enableAutoFlowLogging(false);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getA11ySettingsMetricsFeatureProvider();
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            if (bundle.containsKey("shortcut_type")) {
                this.mSavedCheckBoxValue = bundle.getInt("shortcut_type", -1);
            }
            if (bundle.containsKey("qs_tooltip_reshow")) {
                this.mNeedsQSTooltipReshow = bundle.getBoolean("qs_tooltip_reshow");
            }
            if (bundle.containsKey("qs_tooltip_type")) {
                this.mNeedsQSTooltipType = bundle.getInt("qs_tooltip_type");
            }
        }
        if (getPreferenceScreenResId() <= 0) {
            setPreferenceScreen(getPreferenceManager().createPreferenceScreen(getPrefContext()));
        }
        if (this instanceof OneHandedSettings) {
            initGeneralCategory();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add("accessibility_button_targets");
        arrayList.add("accessibility_shortcut_target_service");
        arrayList.add("accessibility_qs_targets");
        arrayList.add("accessibility_direct_access_target_service");
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver = new AccessibilitySettingsContentObserver(new Handler());
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(arrayList, new AccessibilitySettingsContentObserver.ContentObserverCallback() { // from class: com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda3
            @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
            public final void onChange(String str) {
                AccessibilityShortcutPreferenceFragment accessibilityShortcutPreferenceFragment = AccessibilityShortcutPreferenceFragment.this;
                accessibilityShortcutPreferenceFragment.updateShortcutPreferenceData();
                accessibilityShortcutPreferenceFragment.updateShortcutPreference();
            }
        });
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i == 1) {
            boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
            final int i2 = 0;
            AlertDialog createDialog = AccessibilityDialogUtils.createDialog(isAnySetupWizard ? 1 : 0, getPrefContext(), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda4
                public final /* synthetic */ AccessibilityShortcutPreferenceFragment f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    int i4 = i2;
                    AccessibilityShortcutPreferenceFragment accessibilityShortcutPreferenceFragment = this.f$0;
                    switch (i4) {
                        case 0:
                            if (accessibilityShortcutPreferenceFragment.getComponentName() != null) {
                                int shortcutTypeCheckBoxValue = accessibilityShortcutPreferenceFragment.getShortcutTypeCheckBoxValue();
                                accessibilityShortcutPreferenceFragment.saveNonEmptyUserShortcutType(shortcutTypeCheckBoxValue);
                                AccessibilityUtil.optInAllValuesToSettings(accessibilityShortcutPreferenceFragment.getPrefContext(), shortcutTypeCheckBoxValue, accessibilityShortcutPreferenceFragment.getComponentName());
                                AccessibilityUtil.optOutAllValuesFromSettings(accessibilityShortcutPreferenceFragment.getPrefContext(), ~shortcutTypeCheckBoxValue, accessibilityShortcutPreferenceFragment.getComponentName());
                                accessibilityShortcutPreferenceFragment.mShortcutPreference.setChecked(shortcutTypeCheckBoxValue != 0);
                                accessibilityShortcutPreferenceFragment.mShortcutPreference.setSummary(accessibilityShortcutPreferenceFragment.getShortcutTypeSummary(accessibilityShortcutPreferenceFragment.getPrefContext()));
                                if (accessibilityShortcutPreferenceFragment.mHardwareTypeCheckBox.isChecked()) {
                                    Settings.Secure.putInt(accessibilityShortcutPreferenceFragment.getPrefContext().getContentResolver(), "skip_accessibility_shortcut_dialog_timeout_restriction", 1);
                                    break;
                                }
                            }
                            break;
                        default:
                            accessibilityShortcutPreferenceFragment.getClass();
                            dialogInterface.dismiss();
                            break;
                    }
                }
            }, getShortcutTitle());
            createDialog.show();
            ((ScrollView) createDialog.findViewById(R.id.container_layout)).setScrollIndicators(3, 3);
            this.mDialog = createDialog;
            setupEditShortcutDialog(createDialog);
            return this.mDialog;
        }
        if (i == 1008) {
            if (WizardManagerHelper.isAnySetupWizard(getIntent())) {
                final int i3 = 1;
                this.mDialog = AccessibilityShortcutsTutorial.createAccessibilityTutorialDialogForSetupWizard(getUserPreferredShortcutTypes(), getPrefContext(), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda4
                    public final /* synthetic */ AccessibilityShortcutPreferenceFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        int i4 = i3;
                        AccessibilityShortcutPreferenceFragment accessibilityShortcutPreferenceFragment = this.f$0;
                        switch (i4) {
                            case 0:
                                if (accessibilityShortcutPreferenceFragment.getComponentName() != null) {
                                    int shortcutTypeCheckBoxValue = accessibilityShortcutPreferenceFragment.getShortcutTypeCheckBoxValue();
                                    accessibilityShortcutPreferenceFragment.saveNonEmptyUserShortcutType(shortcutTypeCheckBoxValue);
                                    AccessibilityUtil.optInAllValuesToSettings(accessibilityShortcutPreferenceFragment.getPrefContext(), shortcutTypeCheckBoxValue, accessibilityShortcutPreferenceFragment.getComponentName());
                                    AccessibilityUtil.optOutAllValuesFromSettings(accessibilityShortcutPreferenceFragment.getPrefContext(), ~shortcutTypeCheckBoxValue, accessibilityShortcutPreferenceFragment.getComponentName());
                                    accessibilityShortcutPreferenceFragment.mShortcutPreference.setChecked(shortcutTypeCheckBoxValue != 0);
                                    accessibilityShortcutPreferenceFragment.mShortcutPreference.setSummary(accessibilityShortcutPreferenceFragment.getShortcutTypeSummary(accessibilityShortcutPreferenceFragment.getPrefContext()));
                                    if (accessibilityShortcutPreferenceFragment.mHardwareTypeCheckBox.isChecked()) {
                                        Settings.Secure.putInt(accessibilityShortcutPreferenceFragment.getPrefContext().getContentResolver(), "skip_accessibility_shortcut_dialog_timeout_restriction", 1);
                                        break;
                                    }
                                }
                                break;
                            default:
                                accessibilityShortcutPreferenceFragment.getClass();
                                dialogInterface.dismiss();
                                break;
                        }
                    }
                }, getLabelName());
            } else {
                final int i4 = 1;
                this.mDialog = AccessibilityShortcutsTutorial.createAccessibilityTutorialDialog(getUserPreferredShortcutTypes(), getPrefContext(), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda4
                    public final /* synthetic */ AccessibilityShortcutPreferenceFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        int i42 = i4;
                        AccessibilityShortcutPreferenceFragment accessibilityShortcutPreferenceFragment = this.f$0;
                        switch (i42) {
                            case 0:
                                if (accessibilityShortcutPreferenceFragment.getComponentName() != null) {
                                    int shortcutTypeCheckBoxValue = accessibilityShortcutPreferenceFragment.getShortcutTypeCheckBoxValue();
                                    accessibilityShortcutPreferenceFragment.saveNonEmptyUserShortcutType(shortcutTypeCheckBoxValue);
                                    AccessibilityUtil.optInAllValuesToSettings(accessibilityShortcutPreferenceFragment.getPrefContext(), shortcutTypeCheckBoxValue, accessibilityShortcutPreferenceFragment.getComponentName());
                                    AccessibilityUtil.optOutAllValuesFromSettings(accessibilityShortcutPreferenceFragment.getPrefContext(), ~shortcutTypeCheckBoxValue, accessibilityShortcutPreferenceFragment.getComponentName());
                                    accessibilityShortcutPreferenceFragment.mShortcutPreference.setChecked(shortcutTypeCheckBoxValue != 0);
                                    accessibilityShortcutPreferenceFragment.mShortcutPreference.setSummary(accessibilityShortcutPreferenceFragment.getShortcutTypeSummary(accessibilityShortcutPreferenceFragment.getPrefContext()));
                                    if (accessibilityShortcutPreferenceFragment.mHardwareTypeCheckBox.isChecked()) {
                                        Settings.Secure.putInt(accessibilityShortcutPreferenceFragment.getPrefContext().getContentResolver(), "skip_accessibility_shortcut_dialog_timeout_restriction", 1);
                                        break;
                                    }
                                }
                                break;
                            default:
                                accessibilityShortcutPreferenceFragment.getClass();
                                dialogInterface.dismiss();
                                break;
                        }
                    }
                }, getLabelName());
            }
            this.mDialog.setCanceledOnTouchOutside(false);
            return this.mDialog;
        }
        if (i != 9000) {
            throw new IllegalArgumentException(SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unsupported dialogId "));
        }
        this.mShortcutPreference.setChecked(false);
        final int i5 = 0;
        AlertDialog showEditShortcutDialog = AccessibilityDialogUtils.showEditShortcutDialog(getPrefContext(), 90, SecAccessibilityUtils.isStartStopShortcut(getPrefContext(), getShortcutPreferenceKey()) ? getText(R.string.accessibility_tutorial_dialog_title_start_stop) : getText(R.string.accessibility_tutorial_dialog_title), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda4
            public final /* synthetic */ AccessibilityShortcutPreferenceFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i32) {
                int i42 = i5;
                AccessibilityShortcutPreferenceFragment accessibilityShortcutPreferenceFragment = this.f$0;
                switch (i42) {
                    case 0:
                        if (accessibilityShortcutPreferenceFragment.getComponentName() != null) {
                            int shortcutTypeCheckBoxValue = accessibilityShortcutPreferenceFragment.getShortcutTypeCheckBoxValue();
                            accessibilityShortcutPreferenceFragment.saveNonEmptyUserShortcutType(shortcutTypeCheckBoxValue);
                            AccessibilityUtil.optInAllValuesToSettings(accessibilityShortcutPreferenceFragment.getPrefContext(), shortcutTypeCheckBoxValue, accessibilityShortcutPreferenceFragment.getComponentName());
                            AccessibilityUtil.optOutAllValuesFromSettings(accessibilityShortcutPreferenceFragment.getPrefContext(), ~shortcutTypeCheckBoxValue, accessibilityShortcutPreferenceFragment.getComponentName());
                            accessibilityShortcutPreferenceFragment.mShortcutPreference.setChecked(shortcutTypeCheckBoxValue != 0);
                            accessibilityShortcutPreferenceFragment.mShortcutPreference.setSummary(accessibilityShortcutPreferenceFragment.getShortcutTypeSummary(accessibilityShortcutPreferenceFragment.getPrefContext()));
                            if (accessibilityShortcutPreferenceFragment.mHardwareTypeCheckBox.isChecked()) {
                                Settings.Secure.putInt(accessibilityShortcutPreferenceFragment.getPrefContext().getContentResolver(), "skip_accessibility_shortcut_dialog_timeout_restriction", 1);
                                break;
                            }
                        }
                        break;
                    default:
                        accessibilityShortcutPreferenceFragment.getClass();
                        dialogInterface.dismiss();
                        break;
                }
            }
        }, getComponentName().flattenToString(), getLabelName());
        this.mDialog = showEditShortcutDialog;
        setupEditShortcutDialog(showEditShortcutDialog);
        return this.mDialog;
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda0] */
    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ShortcutPreference shortcutPreference = new ShortcutPreference(getPrefContext());
        this.mShortcutPreference = shortcutPreference;
        shortcutPreference.setPersistent();
        this.mShortcutPreference.setKey(getShortcutPreferenceKey());
        ShortcutPreference shortcutPreference2 = this.mShortcutPreference;
        shortcutPreference2.mClickCallback = this;
        shortcutPreference2.setTitle(getShortcutTitle());
        this.mShortcutPreference.mComponentName = getComponentName();
        this.mShortcutPreference.mLabel = getLabelName();
        this.mShortcutPreference.mResourceId = this.mPackageNameResourceId;
        getPreferenceScreen().addPreference(this.mShortcutPreference);
        this.mTouchExplorationStateChangeListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda0
            @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
            public final void onTouchExplorationStateChanged(boolean z) {
                AccessibilityShortcutPreferenceFragment accessibilityShortcutPreferenceFragment = AccessibilityShortcutPreferenceFragment.this;
                accessibilityShortcutPreferenceFragment.removeDialog(1);
                accessibilityShortcutPreferenceFragment.mShortcutPreference.setSummary(accessibilityShortcutPreferenceFragment.getShortcutTypeSummary(accessibilityShortcutPreferenceFragment.getPrefContext()));
            }
        };
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.mMetricsFeatureProvider.clicked(getMetricsCategory(), "A11Y0001");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public void onPause() {
        ((AccessibilityManager) getPrefContext().getSystemService(AccessibilityManager.class)).removeTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener);
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver = this.mSettingsContentObserver;
        ContentResolver contentResolver = getContentResolver();
        accessibilitySettingsContentObserver.getClass();
        contentResolver.unregisterContentObserver(accessibilitySettingsContentObserver);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mMetricsFeatureProvider.visible(null, 0, getMetricsCategory(), 0);
        ((AccessibilityManager) getPrefContext().getSystemService(AccessibilityManager.class)).addTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener);
        this.mSettingsContentObserver.register(getContentResolver());
        updateShortcutPreferenceData();
        updateShortcutPreference();
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        Context context = getContext();
        View findViewById = this.mDialog.findViewById(R.id.container_layout);
        if (findViewById != null) {
            AccessibilityDialogUtils.initQuickSettingsShortcutForSec(context, findViewById);
            AccessibilityDialogUtils.initSoftwareShortcutForSec(context, findViewById);
            View findViewById2 = findViewById.findViewById(R.id.direct_shortcut);
            ((CheckBox) findViewById2.findViewById(R.id.checkbox)).setText(context.getText(R.string.accessibility_shortcut_press_side_volume_up));
            View findViewById3 = findViewById.findViewById(R.id.hardware_shortcut);
            ((CheckBox) findViewById3.findViewById(R.id.checkbox)).setText(context.getText(R.string.accessibility_shortcut_press_hold_volume_up_down));
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        int shortcutTypeCheckBoxValue = getShortcutTypeCheckBoxValue();
        if (shortcutTypeCheckBoxValue != -1) {
            bundle.putInt("shortcut_type", shortcutTypeCheckBoxValue);
        }
        if (this.mNeedsQSTooltipReshow) {
            bundle.putBoolean("qs_tooltip_reshow", true);
            bundle.putInt("qs_tooltip_type", this.mNeedsQSTooltipType);
        }
        super.onSaveInstanceState(bundle);
    }

    @Override // com.android.settings.accessibility.ShortcutPreference.OnClickCallback
    public final void onToggleClicked(ShortcutPreference shortcutPreference) {
        if (getComponentName() == null) {
            return;
        }
        int userPreferredShortcutTypes = getUserPreferredShortcutTypes();
        if (userPreferredShortcutTypes == 0) {
            showDialog(9000);
            return;
        }
        if (shortcutPreference.mChecked) {
            AccessibilityUtil.optInAllValuesToSettings(getPrefContext(), userPreferredShortcutTypes, getComponentName());
        } else {
            AccessibilityUtil.optOutAllValuesFromSettings(getPrefContext(), userPreferredShortcutTypes, getComponentName());
        }
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mNeedsQSTooltipReshow) {
            view.post(new Runnable() { // from class: com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FragmentActivity activity = AccessibilityShortcutPreferenceFragment.this.getActivity();
                    if (activity != null) {
                        activity.isFinishing();
                    }
                }
            });
        }
    }

    public void saveNonEmptyUserShortcutType(int i) {
        if (i == 0) {
            return;
        }
        PreferredShortcuts.saveUserShortcutType(getPrefContext(), new PreferredShortcut(getComponentName().flattenToString(), i));
    }

    public final void setDialogTextAreaClickListener$2(View view, final CheckBox checkBox) {
        view.findViewById(R.id.container).setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AccessibilityShortcutPreferenceFragment accessibilityShortcutPreferenceFragment = AccessibilityShortcutPreferenceFragment.this;
                CheckBox checkBox2 = checkBox;
                accessibilityShortcutPreferenceFragment.getClass();
                checkBox2.toggle();
                AlertDialog alertDialog = accessibilityShortcutPreferenceFragment.mDialog;
                if (alertDialog.getButton(-1) != null) {
                    alertDialog.getButton(-1).setEnabled(accessibilityShortcutPreferenceFragment.getShortcutTypeCheckBoxValue() != 0);
                }
            }
        });
    }

    public void setupEditShortcutDialog(Dialog dialog) {
        View findViewById = dialog.findViewById(R.id.software_shortcut);
        CheckBox checkBox = (CheckBox) findViewById.findViewById(R.id.checkbox);
        this.mSoftwareTypeCheckBox = checkBox;
        setDialogTextAreaClickListener$2(findViewById, checkBox);
        View findViewById2 = dialog.findViewById(R.id.hardware_shortcut);
        CheckBox checkBox2 = (CheckBox) findViewById2.findViewById(R.id.checkbox);
        this.mHardwareTypeCheckBox = checkBox2;
        setDialogTextAreaClickListener$2(findViewById2, checkBox2);
        View findViewById3 = dialog.findViewById(R.id.direct_shortcut);
        CheckBox checkBox3 = (CheckBox) findViewById3.findViewById(R.id.checkbox);
        this.mDirectTypeCheckBox = checkBox3;
        setDialogTextAreaClickListener$2(findViewById3, checkBox3);
        View findViewById4 = dialog.findViewById(R.id.quick_settings_shortcut);
        CheckBox checkBox4 = (CheckBox) findViewById4.findViewById(R.id.checkbox);
        this.mQuickSettingsTypeCheckBox = checkBox4;
        setDialogTextAreaClickListener$2(findViewById4, checkBox4);
        AlertDialog alertDialog = (AlertDialog) dialog;
        if (alertDialog.getButton(-1) != null) {
            alertDialog.getButton(-1).setEnabled(getShortcutTypeCheckBoxValue() != 0);
        }
        int i = this.mSavedCheckBoxValue;
        this.mSavedCheckBoxValue = -1;
        if (i == -1) {
            i = this.mShortcutPreference.mChecked ? getUserPreferredShortcutTypes() : 0;
        }
        this.mSoftwareTypeCheckBox.setChecked(hasShortcutType$2(i, 1));
        this.mHardwareTypeCheckBox.setChecked(hasShortcutType$2(i, 2));
        this.mDirectTypeCheckBox.setChecked(hasShortcutType$2(i, 512));
    }

    public final void updateShortcutPreference() {
        if (getComponentName() == null) {
            return;
        }
        this.mShortcutPreference.setChecked(AccessibilityUtil.hasValuesInSettings(getPrefContext(), getUserPreferredShortcutTypes(), getComponentName()));
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
    }

    public final void updateShortcutPreferenceData() {
        int userShortcutTypesFromSettings;
        if (getComponentName() == null || (userShortcutTypesFromSettings = AccessibilityUtil.getUserShortcutTypesFromSettings(getPrefContext(), getComponentName())) == 0) {
            return;
        }
        PreferredShortcuts.saveUserShortcutType(getPrefContext(), new PreferredShortcut(getComponentName().flattenToString(), userShortcutTypesFromSettings));
    }
}
