package com.android.settings.accessibility;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.accessibility.ShortcutPreference;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.utils.LocaleUtils;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.widget.SettingsMainSwitchPreference;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.widget.TopIntroPreference;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ToggleFeaturePreferenceFragment extends DashboardFragment implements ShortcutPreference.OnClickCallback, CompoundButton.OnCheckedChangeListener {
    public static final HashSet appInfoHidePackages;
    public ComponentName mComponentName;
    public CharSequence mDescription;
    public Dialog mDialog;
    public CheckBox mDirectTypeCheckBox;
    public AccessibilityFooterPreferenceController mFooterPreferenceController;
    public CheckBox mHardwareTypeCheckBox;
    public CharSequence mHtmlDescription;
    public ImageView mImageGetterCacheView;
    public Uri mImageUri;
    public CharSequence mPackageName;
    public String mPreferenceKey;
    public CheckBox mQuickSettingsTypeCheckBox;
    public com.android.settingslib.RestrictedPreferenceHelper mRestrictedHelper;
    public AccessibilitySettingsContentObserver mSettingsContentObserver;
    public Intent mSettingsIntent;
    public Preference mSettingsPreference;
    public CharSequence mSettingsTitle;
    public ShortcutPreference mShortcutPreference;
    public CheckBox mSoftwareTypeCheckBox;
    public SettingsMainSwitchBar mToggleServiceSwitchBar;
    public SettingsMainSwitchPreference mToggleServiceSwitchPreference;
    public TopIntroPreference mTopIntroPreference;
    public CharSequence mTopIntroTitle;
    public ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda3 mTouchExplorationStateChangeListener;
    public boolean isFromExclusive = false;
    public int mPackageNameResourceId = -1;
    public int mSavedCheckBoxValue = -1;
    public boolean mNeedsQSTooltipReshow = false;
    public int mNeedsQSTooltipType = 0;
    public final ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda6 mImageGetter = new Html.ImageGetter() { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda6
        @Override // android.text.Html.ImageGetter
        public final Drawable getDrawable(String str) {
            ToggleFeaturePreferenceFragment toggleFeaturePreferenceFragment = ToggleFeaturePreferenceFragment.this;
            HashSet hashSet = ToggleFeaturePreferenceFragment.appInfoHidePackages;
            toggleFeaturePreferenceFragment.getClass();
            if (str == null || !str.startsWith("R.drawable.")) {
                return null;
            }
            Uri parse = Uri.parse("android.resource://" + toggleFeaturePreferenceFragment.mComponentName.getPackageName() + "/drawable/" + str.substring(11));
            if (toggleFeaturePreferenceFragment.mImageGetterCacheView == null) {
                toggleFeaturePreferenceFragment.mImageGetterCacheView = new ImageView(toggleFeaturePreferenceFragment.getPrefContext());
            }
            toggleFeaturePreferenceFragment.mImageGetterCacheView.setAdjustViewBounds(true);
            toggleFeaturePreferenceFragment.mImageGetterCacheView.setImageURI(parse);
            if (toggleFeaturePreferenceFragment.mImageGetterCacheView.getDrawable() == null) {
                return null;
            }
            Drawable newDrawable = toggleFeaturePreferenceFragment.mImageGetterCacheView.getDrawable().mutate().getConstantState().newDrawable();
            toggleFeaturePreferenceFragment.mImageGetterCacheView.setImageURI(null);
            int intrinsicWidth = newDrawable.getIntrinsicWidth();
            int intrinsicHeight = newDrawable.getIntrinsicHeight();
            Context prefContext = toggleFeaturePreferenceFragment.getPrefContext();
            int i = AccessibilityUtil.$r8$clinit;
            int round = Math.round(TypedValue.applyDimension(1, r4.getConfiguration().screenHeightDp, prefContext.getResources().getDisplayMetrics())) / 2;
            if (intrinsicWidth > Math.round(TypedValue.applyDimension(1, r6.getConfiguration().screenWidthDp, toggleFeaturePreferenceFragment.getPrefContext().getResources().getDisplayMetrics())) || intrinsicHeight > round) {
                return null;
            }
            newDrawable.setBounds(0, 0, newDrawable.getIntrinsicWidth(), newDrawable.getIntrinsicHeight());
            return newDrawable;
        }
    };
    public final AnonymousClass1 softwareShortcutSettingObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment.1
        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            ToggleFeaturePreferenceFragment.this.updateShortcutPreference();
        }
    };

    static {
        HashSet hashSet = new HashSet();
        appInfoHidePackages = hashSet;
        hashSet.add("com.samsung.accessibility");
        hashSet.add("com.samsung.android.honeyboard");
        hashSet.add(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
    }

    public static boolean hasShortcutType$1(int i, int i2) {
        return (i & i2) == i2;
    }

    public void callOnAlertDialogCheckboxClicked(DialogInterface dialogInterface, int i) {
        if (this.mComponentName == null) {
            return;
        }
        int shortcutTypeCheckBoxValue = getShortcutTypeCheckBoxValue();
        saveNonEmptyUserShortcutType(shortcutTypeCheckBoxValue);
        AccessibilityUtil.optInAllValuesToSettings(getPrefContext(), shortcutTypeCheckBoxValue, this.mComponentName);
        AccessibilityUtil.optOutAllValuesFromSettings(getPrefContext(), ~shortcutTypeCheckBoxValue, this.mComponentName);
        this.mShortcutPreference.setChecked(shortcutTypeCheckBoxValue != 0);
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
        if (this.mHardwareTypeCheckBox.isChecked()) {
            Settings.Secure.putInt(getPrefContext().getContentResolver(), "skip_accessibility_shortcut_dialog_timeout_restriction", 1);
        }
    }

    public Preference createAppInfoPreference() {
        ComponentName componentName;
        if (WizardManagerHelper.isAnySetupWizard(getIntent()) || !WizardManagerHelper.isUserSetupComplete(getContext()) || (componentName = this.mComponentName) == null) {
            return null;
        }
        String packageName = componentName.getPackageName();
        if (appInfoHidePackages.contains(packageName) || !getPrefContext().getPackageManager().isPackageAvailable(packageName)) {
            return null;
        }
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setPackage(getContext().getPackageName());
        intent.setData(Uri.parse("package:" + packageName));
        Preference preference = new Preference(getPrefContext());
        preference.setTitle(getString(R.string.application_info_label));
        preference.setIconSpaceReserved(false);
        preference.setIntent(intent);
        preference.setKey("app_info");
        return preference;
    }

    public void createFooterPreference(PreferenceScreen preferenceScreen, CharSequence charSequence, String str) {
        AccessibilityFooterPreference accessibilityFooterPreference = new AccessibilityFooterPreference(preferenceScreen.getContext());
        accessibilityFooterPreference.setTitle(charSequence);
        preferenceScreen.addPreference(accessibilityFooterPreference);
        AccessibilityFooterPreferenceController accessibilityFooterPreferenceController = new AccessibilityFooterPreferenceController(preferenceScreen.getContext(), accessibilityFooterPreference.getKey());
        this.mFooterPreferenceController = accessibilityFooterPreferenceController;
        accessibilityFooterPreferenceController.setIntroductionTitle(str);
        this.mFooterPreferenceController.displayPreference(preferenceScreen);
    }

    public int getDefaultShortcutTypes() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.DialogCreatable
    public int getDialogMetricsCategory(int i) {
        if (i == 1) {
            return 1812;
        }
        if (i == 1008) {
            return 1810;
        }
        if (i == 9000) {
            return 1812;
        }
        switch (i) {
            case 9002:
                return 9999;
            case 9003:
                return EnterpriseContainerConstants.SYSTEM_SIGNED_APP;
            case 9004:
                return 10001;
            case 9005:
                return 10002;
            default:
                return 0;
        }
    }

    public String getExclusiveTaskName() {
        return null;
    }

    public int getMetricsCategory() {
        return 4;
    }

    public List getPreferenceOrderList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("top_intro");
        arrayList.add("animated_image");
        arrayList.add("use_service");
        arrayList.add("shortcut_preference");
        arrayList.add("settings");
        arrayList.add("app_info");
        arrayList.add("general_categories");
        arrayList.add("html_description");
        return arrayList;
    }

    public String getShortcutPreferenceKey() {
        return "shortcut_preference";
    }

    public CharSequence getShortcutTitle() {
        return getString(R.string.accessibility_shortcut_title, this.mPackageName);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    public int getShortcutTypeCheckBoxValue() {
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

    public CharSequence getShortcutTypeSummary(Context context) {
        ShortcutPreference shortcutPreference = this.mShortcutPreference;
        if (!shortcutPreference.mSettingsEditable) {
            return context.getText(R.string.accessibility_shortcut_edit_dialog_title_hardware);
        }
        if (!shortcutPreference.mChecked) {
            return null;
        }
        int retrieveUserShortcutType = PreferredShortcuts.retrieveUserShortcutType(context, getDefaultShortcutTypes(), this.mComponentName.flattenToString());
        ArrayList arrayList = new ArrayList();
        if (hasShortcutType$1(retrieveUserShortcutType, 16)) {
            arrayList.add(context.getText(R.string.accessibility_feature_shortcut_setting_summary_quick_settings));
        }
        if (hasShortcutType$1(retrieveUserShortcutType, 1)) {
            boolean isFloatingMenuEnabled = AccessibilityUtil.isFloatingMenuEnabled(context);
            int i = R.string.accessibility_shortcut_summary_software;
            if (!isFloatingMenuEnabled && AccessibilityUtil.isGestureNavigateEnabled(context)) {
                i = R.string.accessibility_shortcut_summary_accessibility_gesture;
            }
            arrayList.add(context.getText(i));
        }
        if (hasShortcutType$1(retrieveUserShortcutType, 512)) {
            arrayList.add(context.getText(R.string.accessibility_shortcut_summary_direct));
        }
        if (hasShortcutType$1(retrieveUserShortcutType, 2)) {
            arrayList.add(context.getText(R.string.accessibility_shortcut_summary_hardware));
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return CaseMap.toTitle().wholeString().noLowercase().apply(Locale.getDefault(), null, LocaleUtils.getConcatenatedString(arrayList));
    }

    public int getUserPreferredShortcutTypes() {
        return PreferredShortcuts.retrieveUserShortcutType(getPrefContext(), getDefaultShortcutTypes(), this.mComponentName.flattenToString());
    }

    public void initSettingsPreference() {
        if (this.mSettingsTitle == null || this.mSettingsIntent == null) {
            return;
        }
        Preference preference = new Preference(getPrefContext());
        this.mSettingsPreference = preference;
        preference.setTitle(this.mSettingsTitle);
        this.mSettingsPreference.setIconSpaceReserved(false);
        this.mSettingsPreference.setIntent(this.mSettingsIntent);
        this.mSettingsPreference.setKey("settings");
        getPreferenceScreen().addPreference(this.mSettingsPreference);
    }

    public void initShortcutPreference() {
        ShortcutPreference shortcutPreference = new ShortcutPreference(getPrefContext());
        this.mShortcutPreference = shortcutPreference;
        shortcutPreference.setPersistent();
        this.mShortcutPreference.setKey(getShortcutPreferenceKey());
        ShortcutPreference shortcutPreference2 = this.mShortcutPreference;
        shortcutPreference2.mClickCallback = this;
        shortcutPreference2.setTitle(getShortcutTitle());
        ShortcutPreference shortcutPreference3 = this.mShortcutPreference;
        shortcutPreference3.mComponentName = this.mComponentName;
        shortcutPreference3.mExclusiveTaskName = getExclusiveTaskName();
        ShortcutPreference shortcutPreference4 = this.mShortcutPreference;
        shortcutPreference4.mLabel = this.mPackageName;
        shortcutPreference4.mResourceId = this.mPackageNameResourceId;
        getPreferenceScreen().addPreference(this.mShortcutPreference);
    }

    public void initTopIntroPreference() {
        if (TextUtils.isEmpty(this.mTopIntroTitle)) {
            return;
        }
        TopIntroPreference topIntroPreference = new TopIntroPreference(getPrefContext());
        this.mTopIntroPreference = topIntroPreference;
        topIntroPreference.setKey("top_intro");
        this.mTopIntroPreference.setTitle(this.mTopIntroTitle);
    }

    public boolean isAnySetupWizard() {
        return WizardManagerHelper.isAnySetupWizard(getIntent());
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

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.isFromExclusive) {
            this.isFromExclusive = false;
        } else if (z && AccessibilityExclusiveUtil.isExclusiveTaskEnabled(getPrefContext(), getExclusiveTaskName())) {
            showDialog(9002);
        } else {
            onPreferenceToggled(this.mPreferenceKey, z);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        onProcessArguments(getArguments());
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
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver = new AccessibilitySettingsContentObserver(new Handler());
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
        registerKeysToObserverCallback(accessibilitySettingsContentObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.DialogCreatable
    public Dialog onCreateDialog(final int i) {
        if (i == 1) {
            boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
            final int i2 = 0;
            AlertDialog createDialog = AccessibilityDialogUtils.createDialog(isAnySetupWizard ? 1 : 0, getPrefContext(), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda0
                public final /* synthetic */ ToggleFeaturePreferenceFragment f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    int i4 = i2;
                    ToggleFeaturePreferenceFragment toggleFeaturePreferenceFragment = this.f$0;
                    switch (i4) {
                        case 0:
                            toggleFeaturePreferenceFragment.callOnAlertDialogCheckboxClicked(dialogInterface, i3);
                            break;
                        default:
                            HashSet hashSet = ToggleFeaturePreferenceFragment.appInfoHidePackages;
                            toggleFeaturePreferenceFragment.getClass();
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
                this.mDialog = AccessibilityShortcutsTutorial.createAccessibilityTutorialDialogForSetupWizard(getUserPreferredShortcutTypes(), getPrefContext(), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ ToggleFeaturePreferenceFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        int i4 = i3;
                        ToggleFeaturePreferenceFragment toggleFeaturePreferenceFragment = this.f$0;
                        switch (i4) {
                            case 0:
                                toggleFeaturePreferenceFragment.callOnAlertDialogCheckboxClicked(dialogInterface, i32);
                                break;
                            default:
                                HashSet hashSet = ToggleFeaturePreferenceFragment.appInfoHidePackages;
                                toggleFeaturePreferenceFragment.getClass();
                                dialogInterface.dismiss();
                                break;
                        }
                    }
                }, this.mPackageName);
            } else {
                final int i4 = 1;
                this.mDialog = AccessibilityShortcutsTutorial.createAccessibilityTutorialDialog(getUserPreferredShortcutTypes(), getPrefContext(), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ ToggleFeaturePreferenceFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        int i42 = i4;
                        ToggleFeaturePreferenceFragment toggleFeaturePreferenceFragment = this.f$0;
                        switch (i42) {
                            case 0:
                                toggleFeaturePreferenceFragment.callOnAlertDialogCheckboxClicked(dialogInterface, i32);
                                break;
                            default:
                                HashSet hashSet = ToggleFeaturePreferenceFragment.appInfoHidePackages;
                                toggleFeaturePreferenceFragment.getClass();
                                dialogInterface.dismiss();
                                break;
                        }
                    }
                }, this.mPackageName);
            }
            this.mDialog.setCanceledOnTouchOutside(false);
            return this.mDialog;
        }
        if (i == 9000) {
            this.mShortcutPreference.setChecked(false);
            final int i5 = 0;
            AlertDialog showEditShortcutDialog = AccessibilityDialogUtils.showEditShortcutDialog(getPrefContext(), 90, SecAccessibilityUtils.isStartStopShortcut(getPrefContext(), getShortcutPreferenceKey()) ? getText(R.string.accessibility_tutorial_dialog_title_start_stop) : getText(R.string.accessibility_tutorial_dialog_title), new DialogInterface.OnClickListener(this) { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda0
                public final /* synthetic */ ToggleFeaturePreferenceFragment f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i32) {
                    int i42 = i5;
                    ToggleFeaturePreferenceFragment toggleFeaturePreferenceFragment = this.f$0;
                    switch (i42) {
                        case 0:
                            toggleFeaturePreferenceFragment.callOnAlertDialogCheckboxClicked(dialogInterface, i32);
                            break;
                        default:
                            HashSet hashSet = ToggleFeaturePreferenceFragment.appInfoHidePackages;
                            toggleFeaturePreferenceFragment.getClass();
                            dialogInterface.dismiss();
                            break;
                    }
                }
            }, this.mComponentName.flattenToString(), this.mPackageName);
            this.mDialog = showEditShortcutDialog;
            setupEditShortcutDialog(showEditShortcutDialog);
            return this.mDialog;
        }
        if (i != 9002 && i != 9003) {
            throw new IllegalArgumentException(SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unsupported dialogId "));
        }
        if (i == 9002) {
            this.mToggleServiceSwitchBar.setCheckedInternal(false);
        } else {
            this.mShortcutPreference.setChecked(false);
        }
        AlertDialog createExclusiveDialog = AccessibilityDialogUtils.createExclusiveDialog(getPrefContext(), getExclusiveTaskName(), new DialogInterface.OnClickListener() { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i6) {
                ToggleFeaturePreferenceFragment toggleFeaturePreferenceFragment = ToggleFeaturePreferenceFragment.this;
                if (i == 9002) {
                    toggleFeaturePreferenceFragment.isFromExclusive = true;
                    toggleFeaturePreferenceFragment.onPreferenceToggled(toggleFeaturePreferenceFragment.mPreferenceKey, true);
                    toggleFeaturePreferenceFragment.mDialog.dismiss();
                    return;
                }
                HashSet hashSet = ToggleFeaturePreferenceFragment.appInfoHidePackages;
                int retrieveUserShortcutType = PreferredShortcuts.retrieveUserShortcutType(toggleFeaturePreferenceFragment.getPrefContext(), 0, toggleFeaturePreferenceFragment.mComponentName.flattenToString());
                if (retrieveUserShortcutType == 0) {
                    toggleFeaturePreferenceFragment.showDialog(9000);
                    return;
                }
                Context prefContext = toggleFeaturePreferenceFragment.getPrefContext();
                toggleFeaturePreferenceFragment.mShortcutPreference.setChecked(true);
                AccessibilityUtil.optInAllValuesToSettings(prefContext, retrieveUserShortcutType, toggleFeaturePreferenceFragment.mComponentName);
                toggleFeaturePreferenceFragment.mShortcutPreference.setSummary(toggleFeaturePreferenceFragment.getShortcutTypeSummary(prefContext));
            }
        }, null);
        this.mDialog = createExclusiveDialog;
        return createExclusiveDialog;
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda3] */
    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        initTopIntroPreference();
        if (this.mImageUri != null) {
            DescriptionPreference descriptionPreference = new DescriptionPreference(getPrefContext());
            Uri uri = this.mImageUri;
            if (uri != descriptionPreference.mImageUri) {
                descriptionPreference.mImageUri = uri;
                descriptionPreference.notifyChanged();
            }
            descriptionPreference.setKey("animated_image");
            getPreferenceScreen().addPreference(descriptionPreference);
        }
        SettingsMainSwitchPreference settingsMainSwitchPreference = new SettingsMainSwitchPreference(getPrefContext());
        this.mToggleServiceSwitchPreference = settingsMainSwitchPreference;
        settingsMainSwitchPreference.setKey("use_service");
        FragmentActivity activity = getActivity();
        if (activity instanceof SettingsActivity) {
            SettingsMainSwitchBar settingsMainSwitchBar = ((SettingsActivity) activity).mMainSwitch;
            this.mToggleServiceSwitchBar = settingsMainSwitchBar;
            settingsMainSwitchBar.show();
            this.mRestrictedHelper = new com.android.settingslib.RestrictedPreferenceHelper(activity, null, null);
            if (getArguments().containsKey("checked")) {
                this.mToggleServiceSwitchBar.setCheckedInternal(getArguments().getBoolean("checked"));
            }
        }
        PreferenceCategory preferenceCategory = new PreferenceCategory(getPrefContext());
        preferenceCategory.setKey("general_categories");
        preferenceCategory.setTitle(R.string.accessibility_screen_option);
        initShortcutPreference();
        initSettingsPreference();
        Preference createAppInfoPreference = createAppInfoPreference();
        if (createAppInfoPreference != null) {
            getPreferenceScreen().addPreference(createAppInfoPreference);
        }
        if (!TextUtils.isEmpty(this.mHtmlDescription)) {
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            Spanned fromHtml = TextUtils.isEmpty(this.mTopIntroTitle) ? Html.fromHtml(this.mHtmlDescription.toString(), 63, this.mImageGetter, null) : Html.fromHtml(((Object) this.mTopIntroTitle) + "<br/><br/>" + this.mHtmlDescription.toString(), 63, this.mImageGetter, null);
            AccessibilityFooterPreference accessibilityFooterPreference = new AccessibilityFooterPreference(preferenceScreen.getContext());
            accessibilityFooterPreference.setTitle(fromHtml);
            preferenceScreen.addPreference(accessibilityFooterPreference);
            String string = getString(R.string.accessibility_introduction_title, this.mPackageName);
            AccessibilityFooterPreferenceController accessibilityFooterPreferenceController = new AccessibilityFooterPreferenceController(preferenceScreen.getContext(), accessibilityFooterPreference.getKey());
            this.mFooterPreferenceController = accessibilityFooterPreferenceController;
            accessibilityFooterPreferenceController.setIntroductionTitle(string);
            this.mFooterPreferenceController.displayPreference(preferenceScreen);
        }
        if (!TextUtils.isEmpty(this.mDescription)) {
            createFooterPreference(getPreferenceScreen(), this.mDescription, getString(R.string.accessibility_introduction_title, this.mPackageName));
        }
        onInstallSwitchPreferenceToggleSwitch();
        this.mTouchExplorationStateChangeListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda3
            @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
            public final void onTouchExplorationStateChanged(boolean z) {
                ToggleFeaturePreferenceFragment toggleFeaturePreferenceFragment = ToggleFeaturePreferenceFragment.this;
                HashSet hashSet = ToggleFeaturePreferenceFragment.appInfoHidePackages;
                toggleFeaturePreferenceFragment.removeDialog(1);
                toggleFeaturePreferenceFragment.mShortcutPreference.setSummary(toggleFeaturePreferenceFragment.getShortcutTypeSummary(toggleFeaturePreferenceFragment.getPrefContext()));
            }
        };
        List preferenceOrderList = getPreferenceOrderList();
        PreferenceScreen preferenceScreen2 = getPreferenceScreen();
        preferenceScreen2.mOrderingAsAdded = false;
        ArrayList arrayList = (ArrayList) preferenceOrderList;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Preference findPreference = preferenceScreen2.findPreference((CharSequence) arrayList.get(i));
            if (findPreference != null) {
                findPreference.setOrder(i);
            }
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mToggleServiceSwitchBar.removeOnSwitchChangeListener(this);
        onRemoveSwitchPreferenceToggleSwitch();
    }

    public void onInstallSwitchPreferenceToggleSwitch() {
        updateSwitchBarToggleSwitch();
        this.mToggleServiceSwitchBar.addOnSwitchChangeListener(this);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.mToggleServiceSwitchBar.setSessionDescription(activity.getTitle().toString());
        }
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
        getContentResolver().unregisterContentObserver(this.softwareShortcutSettingObserver);
        super.onPause();
    }

    public void onProcessArguments(Bundle bundle) {
        this.mPreferenceKey = bundle.getString("preference_key");
        if (bundle.containsKey("resolve_info")) {
            getActivity().setTitle(((ResolveInfo) bundle.getParcelable("resolve_info")).loadLabel(getPackageManager()).toString());
        } else if (bundle.containsKey(UniversalCredentialUtil.AGENT_TITLE)) {
            getActivity().setTitle(bundle.getString(UniversalCredentialUtil.AGENT_TITLE));
        }
        if (bundle.containsKey(UniversalCredentialUtil.AGENT_SUMMARY)) {
            this.mDescription = bundle.getCharSequence(UniversalCredentialUtil.AGENT_SUMMARY);
        }
        if (bundle.containsKey("html_description")) {
            this.mHtmlDescription = bundle.getCharSequence("html_description");
        }
        if (bundle.containsKey("intro")) {
            this.mTopIntroTitle = bundle.getCharSequence("intro");
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mMetricsFeatureProvider.visible(null, 0, getMetricsCategory(), 0);
        ((AccessibilityManager) getPrefContext().getSystemService(AccessibilityManager.class)).addTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener);
        this.mSettingsContentObserver.register(getContentResolver());
        updateShortcutPreferenceData();
        updateShortcutPreference();
        Dialog dialog = this.mDialog;
        if (dialog != null && dialog.isShowing()) {
            Context context = getContext();
            View findViewById = this.mDialog.findViewById(R.id.container_layout);
            if (findViewById != null) {
                AccessibilityDialogUtils.initQuickSettingsShortcutForSec(context, findViewById);
                AccessibilityDialogUtils.initSoftwareShortcutForSec(context, findViewById);
                ((CheckBox) findViewById.findViewById(R.id.direct_shortcut).findViewById(R.id.checkbox)).setText(context.getText(R.string.accessibility_shortcut_press_side_volume_up));
                ((CheckBox) findViewById.findViewById(R.id.hardware_shortcut).findViewById(R.id.checkbox)).setText(context.getText(R.string.accessibility_shortcut_press_hold_volume_up_down));
            }
        }
        com.android.settingslib.RestrictedPreferenceHelper restrictedPreferenceHelper = this.mRestrictedHelper;
        String str = restrictedPreferenceHelper.mAttrUserRestriction;
        this.mToggleServiceSwitchBar.setDisabledByAdmin(str != null ? RestrictedLockUtilsInternal.checkIfRestrictionEnforced(restrictedPreferenceHelper.mContext, UserHandle.myUserId(), str) : null);
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_button_targets"), false, this.softwareShortcutSettingObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
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

    public void onToggleClicked(ShortcutPreference shortcutPreference) {
        if (this.mComponentName == null) {
            return;
        }
        int userPreferredShortcutTypes = getUserPreferredShortcutTypes();
        if (!shortcutPreference.mChecked) {
            AccessibilityUtil.optOutAllValuesFromSettings(getPrefContext(), userPreferredShortcutTypes, this.mComponentName);
        } else if (!((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked() && AccessibilityExclusiveUtil.isExclusiveTaskEnabled(getPrefContext(), getExclusiveTaskName())) {
            showDialog(9003);
            return;
        } else {
            if (userPreferredShortcutTypes == 0) {
                showDialog(9000);
                return;
            }
            AccessibilityUtil.optInAllValuesToSettings(getPrefContext(), userPreferredShortcutTypes, this.mComponentName);
        }
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mNeedsQSTooltipReshow) {
            view.post(new Runnable() { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ToggleFeaturePreferenceFragment toggleFeaturePreferenceFragment = ToggleFeaturePreferenceFragment.this;
                    HashSet hashSet = ToggleFeaturePreferenceFragment.appInfoHidePackages;
                    FragmentActivity activity = toggleFeaturePreferenceFragment.getActivity();
                    if (activity != null) {
                        activity.isFinishing();
                    }
                }
            });
        }
        writeConfigDefaultAccessibilityServiceIntoShortcutTargetServiceIfNeeded(getContext());
    }

    public void saveNonEmptyUserShortcutType(int i) {
        if (i == 0) {
            return;
        }
        PreferredShortcuts.saveUserShortcutType(getPrefContext(), new PreferredShortcut(this.mComponentName.flattenToString(), i));
    }

    public final void setDialogTextAreaClickListener$1(View view, final CheckBox checkBox) {
        view.findViewById(R.id.container).setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.accessibility.ToggleFeaturePreferenceFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ToggleFeaturePreferenceFragment toggleFeaturePreferenceFragment = ToggleFeaturePreferenceFragment.this;
                CheckBox checkBox2 = checkBox;
                HashSet hashSet = ToggleFeaturePreferenceFragment.appInfoHidePackages;
                toggleFeaturePreferenceFragment.getClass();
                checkBox2.toggle();
                AlertDialog alertDialog = (AlertDialog) toggleFeaturePreferenceFragment.mDialog;
                if (alertDialog.getButton(-1) != null) {
                    alertDialog.getButton(-1).setEnabled(toggleFeaturePreferenceFragment.getShortcutTypeCheckBoxValue() != 0);
                }
            }
        });
    }

    public void setupEditShortcutDialog(Dialog dialog) {
        View findViewById = dialog.findViewById(R.id.software_shortcut);
        CheckBox checkBox = (CheckBox) findViewById.findViewById(R.id.checkbox);
        this.mSoftwareTypeCheckBox = checkBox;
        setDialogTextAreaClickListener$1(findViewById, checkBox);
        View findViewById2 = dialog.findViewById(R.id.hardware_shortcut);
        CheckBox checkBox2 = (CheckBox) findViewById2.findViewById(R.id.checkbox);
        this.mHardwareTypeCheckBox = checkBox2;
        setDialogTextAreaClickListener$1(findViewById2, checkBox2);
        View findViewById3 = dialog.findViewById(R.id.direct_shortcut);
        CheckBox checkBox3 = (CheckBox) findViewById3.findViewById(R.id.checkbox);
        this.mDirectTypeCheckBox = checkBox3;
        setDialogTextAreaClickListener$1(findViewById3, checkBox3);
        View findViewById4 = dialog.findViewById(R.id.quick_settings_shortcut);
        CheckBox checkBox4 = (CheckBox) findViewById4.findViewById(R.id.checkbox);
        this.mQuickSettingsTypeCheckBox = checkBox4;
        setDialogTextAreaClickListener$1(findViewById4, checkBox4);
        AlertDialog alertDialog = (AlertDialog) dialog;
        if (alertDialog.getButton(-1) != null) {
            alertDialog.getButton(-1).setEnabled(getShortcutTypeCheckBoxValue() != 0);
        }
        int i = this.mSavedCheckBoxValue;
        this.mSavedCheckBoxValue = -1;
        if (i == -1) {
            i = this.mShortcutPreference.mChecked ? getUserPreferredShortcutTypes() : 0;
        }
        this.mSoftwareTypeCheckBox.setChecked(hasShortcutType$1(i, 1));
        this.mHardwareTypeCheckBox.setChecked(hasShortcutType$1(i, 2));
        this.mDirectTypeCheckBox.setChecked(hasShortcutType$1(i, 512));
        this.mQuickSettingsTypeCheckBox.setChecked(hasShortcutType$1(i, 16));
    }

    public void updateShortcutPreference() {
        if (this.mComponentName == null) {
            return;
        }
        this.mShortcutPreference.setChecked(AccessibilityUtil.hasValuesInSettings(getPrefContext(), getUserPreferredShortcutTypes(), this.mComponentName));
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
    }

    public void updateShortcutPreferenceData() {
        int userShortcutTypesFromSettings;
        if (this.mComponentName == null || (userShortcutTypesFromSettings = AccessibilityUtil.getUserShortcutTypesFromSettings(getPrefContext(), this.mComponentName)) == 0) {
            return;
        }
        PreferredShortcuts.saveUserShortcutType(getPrefContext(), new PreferredShortcut(this.mComponentName.flattenToString(), userShortcutTypesFromSettings));
    }

    public void writeConfigDefaultAccessibilityServiceIntoShortcutTargetServiceIfNeeded(Context context) {
        if (this.mComponentName == null) {
            return;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(getString(android.R.string.data_usage_warning_title));
        if (this.mComponentName.equals(unflattenFromString) && Settings.Secure.getString(context.getContentResolver(), "accessibility_shortcut_target_service") == null) {
            Settings.Secure.putString(context.getContentResolver(), "accessibility_shortcut_target_service", unflattenFromString.flattenToString());
        }
    }

    public void onRemoveSwitchPreferenceToggleSwitch() {
    }

    public void updateSwitchBarToggleSwitch() {
    }

    public void registerKeysToObserverCallback(AccessibilitySettingsContentObserver accessibilitySettingsContentObserver) {
    }

    public void onPreferenceToggled(String str, boolean z) {
    }
}
