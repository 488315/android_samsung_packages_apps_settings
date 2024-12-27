package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.util.SeslKoreanGeneralizer;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.TwoStatePreference;

import com.android.internal.accessibility.dialog.AccessibilityServiceWarning;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.accessibility.PreferredShortcut;
import com.android.settings.accessibility.PreferredShortcuts;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.CheckBoxPickerFragment;
import com.samsung.android.settings.accessibility.base.widget.DividerItemDecorator;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityShortcutEditFragment extends CheckBoxPickerFragment
        implements CompoundButton.OnCheckedChangeListener {
    public String exclusiveTaskFeature;
    public String label;
    public AccessibilityDialogUtils.TTSLanguageDialogInfo languageDialogInfo;
    public AccessibilityServiceInfo mAccessibilityServiceInfo;
    public Context mContext;
    public Dialog mWarningDialog;
    public ShortcutContentObserver shortcutContentObserver;
    public String shortcutKey;
    public SettingsMainSwitchBar switchBar;
    public final ShortcutEditPreferenceType[] typeArray = {
        ShortcutEditPreferenceType.QUICK_SETTINGS,
        ShortcutEditPreferenceType.SOFTWARE,
        ShortcutEditPreferenceType.DIRECT,
        ShortcutEditPreferenceType.HARDWARE,
        ShortcutEditPreferenceType.TRIPLETAP
    };
    public final Map shortcutPreferences = new HashMap();
    public final Set availableShortcutTypes = new HashSet();
    public int userShortcutType = 0;
    public final AnonymousClass1 softwareShortcutSettingObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityShortcutEditFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    AccessibilityShortcutEditFragment.this.updateState$4();
                }
            };
    public final AtomicBoolean mIsDialogShown = new AtomicBoolean(false);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ShortcutEditPreference extends CheckBoxPreference {
        public boolean isSummaryDescriptionSet;
        public String summaryDescription;
        public TextView summaryView;
        public ShortcutEditPreferenceType type;

        public ShortcutEditPreference(Context context) {
            super(context, null);
            this.type = null;
            this.summaryView = null;
            this.isSummaryDescriptionSet = false;
            this.summaryDescription = null;
            setLayoutResource(R.layout.preference_accessibility_shortcut_edit);
        }

        @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            Context context = getContext();
            if (this.type == ShortcutEditPreferenceType.SOFTWARE
                    && Utils.isDeviceProvisioned(context)) {
                preferenceViewHolder.itemView.findViewById(R.id.divider).setVisibility(0);
                ImageButton imageButton =
                        (ImageButton) preferenceViewHolder.itemView.findViewById(R.id.image_button);
                imageButton.setVisibility(0);
                imageButton.setOnClickListener(
                        new AccessibilityShortcutEditFragment$$ExternalSyntheticLambda2(
                                3, context));
            }
            TextView textView =
                    (TextView) preferenceViewHolder.itemView.findViewById(android.R.id.summary);
            this.summaryView = textView;
            if (textView == null || !this.isSummaryDescriptionSet) {
                return;
            }
            textView.setContentDescription(this.summaryDescription);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum ShortcutEditPreferenceType {
        QUICK_SETTINGS(
                "quick_settings",
                16,
                R.string.accessibility_feature_shortcut_setting_summary_quick_settings,
                "isSupportQuickSettings",
                true),
        SOFTWARE(
                "software",
                1,
                R.string.accessibility_shortcut_summary_software,
                "isSupportSoftware",
                true),
        DIRECT(
                "direct",
                512,
                R.string.accessibility_shortcut_summary_direct,
                "isSupportDirect",
                true),
        HARDWARE(
                "hardware",
                2,
                R.string.accessibility_shortcut_edit_title_hardware,
                "isSupportHardware",
                true),
        TRIPLETAP(
                "tripletap",
                4,
                R.string.accessibility_shortcut_edit_title_triple_tap,
                "isSupportTriple",
                false);

        final boolean argumentDefault;
        final String argumentKey;
        final String preferenceKey;
        final int shortcutType;
        final int titleRes;

        ShortcutEditPreferenceType(String str, int i, int i2, String str2, boolean z) {
            this.preferenceKey = str;
            this.shortcutType = i;
            this.titleRes = i2;
            this.argumentKey = str2;
            this.argumentDefault = z;
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.TwoStateCandidateListFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        Context context = this.mContext;
        if (context == null) {
            return arrayList;
        }
        Set set = this.availableShortcutTypes;
        ShortcutEditPreferenceType shortcutEditPreferenceType =
                ShortcutEditPreferenceType.QUICK_SETTINGS;
        if (((HashSet) set).contains(shortcutEditPreferenceType)
                && SecAccessibilityUtils.isSupportQuickSettings(this.shortcutKey)) {
            arrayList.add(
                    new CheckBoxPickerFragment.DetailCandidateInfo(
                            new CheckBoxPickerFragment.DetailCandidateInfo.Builder(
                                    shortcutEditPreferenceType.preferenceKey,
                                    context.getString(shortcutEditPreferenceType.titleRes))));
        }
        Set set2 = this.availableShortcutTypes;
        ShortcutEditPreferenceType shortcutEditPreferenceType2 =
                ShortcutEditPreferenceType.SOFTWARE;
        if (((HashSet) set2).contains(shortcutEditPreferenceType2)) {
            AccessibilityButtonMode mode = AccessibilityButtonGestureUtil.getMode(context);
            CheckBoxPickerFragment.DetailCandidateInfo.Builder builder =
                    new CheckBoxPickerFragment.DetailCandidateInfo.Builder(
                            shortcutEditPreferenceType2.preferenceKey,
                            context.getString(
                                    mode.isButton
                                            ? R.string.accessibility_shortcut_summary_software
                                            : R.string
                                                    .accessibility_shortcut_summary_accessibility_gesture));
            int ordinal = mode.ordinal();
            builder.mIcon =
                    context.getDrawable(
                            ordinal != 0
                                    ? ordinal != 1
                                            ? ordinal != 2
                                                    ? R.drawable.use_accessibility_button_floating
                                                    : R.drawable.use_accessibility_gesture_3
                                            : R.drawable.use_accessibility_gesture_2
                                    : R.drawable.use_accessibility_button);
            builder.mSummary =
                    SecAccessibilityUtils.getAccessibilityButtonShortcutSummary(
                            context, this.shortcutKey, this.label);
            arrayList.add(new CheckBoxPickerFragment.DetailCandidateInfo(builder));
        }
        Set set3 = this.availableShortcutTypes;
        ShortcutEditPreferenceType shortcutEditPreferenceType3 = ShortcutEditPreferenceType.DIRECT;
        if (((HashSet) set3).contains(shortcutEditPreferenceType3)) {
            arrayList.add(
                    new CheckBoxPickerFragment.DetailCandidateInfo(
                            new CheckBoxPickerFragment.DetailCandidateInfo.Builder(
                                    shortcutEditPreferenceType3.preferenceKey,
                                    context.getString(shortcutEditPreferenceType3.titleRes))));
        }
        Set set4 = this.availableShortcutTypes;
        ShortcutEditPreferenceType shortcutEditPreferenceType4 =
                ShortcutEditPreferenceType.HARDWARE;
        if (((HashSet) set4).contains(shortcutEditPreferenceType4)) {
            arrayList.add(
                    new CheckBoxPickerFragment.DetailCandidateInfo(
                            new CheckBoxPickerFragment.DetailCandidateInfo.Builder(
                                    shortcutEditPreferenceType4.preferenceKey,
                                    context.getString(shortcutEditPreferenceType4.titleRes))));
        }
        Set set5 = this.availableShortcutTypes;
        ShortcutEditPreferenceType shortcutEditPreferenceType5 =
                ShortcutEditPreferenceType.TRIPLETAP;
        if (((HashSet) set5).contains(shortcutEditPreferenceType5)) {
            arrayList.add(
                    new CheckBoxPickerFragment.DetailCandidateInfo(
                            new CheckBoxPickerFragment.DetailCandidateInfo.Builder(
                                    shortcutEditPreferenceType5.preferenceKey,
                                    context.getString(shortcutEditPreferenceType5.titleRes))));
        }
        return arrayList;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.TwoStateCandidateListFragment
    public final CharSequence getDescription() {
        Context context = this.mContext;
        if (context != null) {
            String string =
                    SecAccessibilityUtils.isStartStopShortcut(context, this.shortcutKey)
                            ? context.getString(
                                    R.string.accessibility_shortcut_edit_description_start_stop,
                                    this.label)
                            : context.getString(
                                    R.string.accessibility_shortcut_edit_description, this.label);
            return Locale.getDefault().getLanguage().equals("ko")
                    ? new SeslKoreanGeneralizer().naturalizeText(string)
                    : string;
        }
        Log.w("AccessibilityShortcutEditFragment", "context is null. should not happen.");
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AccessibilityShortcutEditFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.CheckBoxPickerFragment,
              // com.samsung.android.settings.accessibility.base.widget.TwoStateCandidateListFragment
    public final CheckBoxPreference getPrefInstance() {
        return new ShortcutEditPreference(this.mContext);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        FragmentActivity activity = getActivity();
        if (activity instanceof SettingsActivity) {
            SettingsMainSwitchBar settingsMainSwitchBar = ((SettingsActivity) activity).mMainSwitch;
            this.switchBar = settingsMainSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.show();
                this.switchBar.setSessionDescription(
                        getString(R.string.accessibility_shortcut_title, this.label));
                this.switchBar.setCheckedInternal(
                        SecAccessibilityUtils.hasValuesInSettings(this.mContext, this.shortcutKey));
                this.switchBar.addOnSwitchChangeListener(this);
                final SettingsActivity settingsActivity = (SettingsActivity) activity;
                this.switchBar.setOnBeforeCheckedChangeListener(
                        new SettingsMainSwitchBar
                                .OnBeforeCheckedChangeListener() { // from class:
                                                                   // com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityShortcutEditFragment$$ExternalSyntheticLambda0
                            @Override // com.android.settings.widget.SettingsMainSwitchBar.OnBeforeCheckedChangeListener
                            public final boolean onBeforeCheckedChanged(boolean z) {
                                AccessibilityShortcutEditFragment
                                        accessibilityShortcutEditFragment =
                                                AccessibilityShortcutEditFragment.this;
                                if (!z) {
                                    accessibilityShortcutEditFragment.getClass();
                                    return false;
                                }
                                AccessibilityServiceInfo accessibilityServiceInfo =
                                        accessibilityShortcutEditFragment.mAccessibilityServiceInfo;
                                if (accessibilityServiceInfo != null) {
                                    ResolveInfo resolveInfo =
                                            accessibilityServiceInfo.getResolveInfo();
                                    if (AccessibilityUtils.getEnabledServicesFromSettings(
                                                            settingsActivity)
                                                    .contains(
                                                            new ComponentName(
                                                                    resolveInfo
                                                                            .serviceInfo
                                                                            .packageName,
                                                                    resolveInfo.serviceInfo.name))
                                            || !((AccessibilityManager)
                                                            accessibilityShortcutEditFragment
                                                                    .mContext.getSystemService(
                                                                    AccessibilityManager.class))
                                                    .isAccessibilityServiceWarningRequired(
                                                            accessibilityShortcutEditFragment
                                                                    .mAccessibilityServiceInfo)) {
                                        return false;
                                    }
                                    accessibilityShortcutEditFragment.showPopupDialog$1(1002);
                                } else {
                                    if (!AccessibilityExclusiveUtil.isExclusiveTaskEnabled(
                                            accessibilityShortcutEditFragment.mContext,
                                            accessibilityShortcutEditFragment
                                                    .exclusiveTaskFeature)) {
                                        return false;
                                    }
                                    accessibilityShortcutEditFragment.showPopupDialog$1(9002);
                                }
                                return true;
                            }
                        });
            }
        }
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.CheckBoxPickerFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        this.mContext = context;
        int i = arguments.getInt("label_resource_id", -1);
        if (i != -1) {
            this.label = this.mContext.getString(i);
        } else {
            this.label = arguments.getString("label", ApnSettings.MVNO_NONE);
        }
        this.shortcutKey = arguments.getString(IMSParameter.CALL.ACTION, ApnSettings.MVNO_NONE);
        this.exclusiveTaskFeature = arguments.getString("exclusive_feature", ApnSettings.MVNO_NONE);
        arguments.getInt("defaultType", 0);
        Context context2 = this.mContext;
        String str = this.shortcutKey;
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        this.userShortcutType = PreferredShortcuts.retrieveUserShortcutType(context2, 0, str);
        ((HashSet) this.availableShortcutTypes).clear();
        for (ShortcutEditPreferenceType shortcutEditPreferenceType : this.typeArray) {
            if (arguments.getBoolean(
                    shortcutEditPreferenceType.argumentKey,
                    shortcutEditPreferenceType.argumentDefault)) {
                ((HashSet) this.availableShortcutTypes).add(shortcutEditPreferenceType);
            }
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(this.shortcutKey);
        if (unflattenFromString == null) {
            this.mAccessibilityServiceInfo = null;
            return;
        }
        AccessibilityServiceInfo accessibilityServiceInfo =
                SecAccessibilityUtils.getAccessibilityServiceInfo(
                        this.mContext, unflattenFromString);
        this.mAccessibilityServiceInfo = accessibilityServiceInfo;
        if (accessibilityServiceInfo != null) {
            this.languageDialogInfo =
                    new AccessibilityDialogUtils.TTSLanguageDialogInfo(
                            this.mContext, accessibilityServiceInfo);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mAccessibilityServiceInfo == null) {
            if (z) {
                SecAccessibilityUtils.optInAllValuesToSettings(
                        this.mContext, this.userShortcutType, this.shortcutKey);
            } else {
                SecAccessibilityUtils.optOutAllValuesFromSettings(
                        this.mContext, this.userShortcutType, this.shortcutKey);
            }
            SecAccessibilityUtils.setAccessibilityServiceState(
                    this.mContext,
                    this.shortcutKey,
                    ((SeslSwitchBar) this.switchBar).mSwitch.isChecked());
            updateCheckEnableStatus(z);
            return;
        }
        if (z) {
            SecAccessibilityUtils.optInAllValuesToSettings(
                    this.mContext, this.userShortcutType, this.shortcutKey);
            updateCheckEnableStatus(true);
        } else {
            SecAccessibilityUtils.optOutAllValuesFromSettings(
                    this.mContext, this.userShortcutType, this.shortcutKey);
            SecAccessibilityUtils.setAccessibilityServiceState(
                    this.mContext,
                    this.shortcutKey,
                    ((SeslSwitchBar) this.switchBar).mSwitch.isChecked());
            updateCheckEnableStatus(false);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (AccessibilityConstant.COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT
                .flattenToShortString()
                .equals(this.shortcutKey)) {
            Context context = this.mContext;
            int i = ShortcutContentObserver.$r8$clinit;
            this.shortcutContentObserver =
                    new ShortcutContentObserver.AnonymousClass1(new Handler(), context, 2);
        } else if (AccessibilityConstant.COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT
                .flattenToString()
                .equals(this.shortcutKey)) {
            Context context2 = this.mContext;
            int i2 = ShortcutContentObserver.$r8$clinit;
            this.shortcutContentObserver =
                    new ShortcutContentObserver.AnonymousClass1(
                            new Handler(context2.getMainLooper()), context2, 1);
        } else if ("com.android.server.accessibility.MagnificationController"
                .equals(this.shortcutKey)) {
            Context context3 = this.mContext;
            int i3 = ShortcutContentObserver.$r8$clinit;
            this.shortcutContentObserver =
                    new ShortcutContentObserver.AnonymousClass1(new Handler(), context3, 0);
        }
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getString(R.string.accessibility_shortcut_title, this.label));
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i == 1002) {
            AlertDialog createAccessibilityServiceWarningDialog =
                    AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(
                            this.mContext,
                            this.mAccessibilityServiceInfo,
                            new AccessibilityShortcutEditFragment$$ExternalSyntheticLambda2(
                                    0, this),
                            new AccessibilityShortcutEditFragment$$ExternalSyntheticLambda2(
                                    1, this),
                            new AccessibilityShortcutEditFragment$$ExternalSyntheticLambda2(
                                    2, this));
            this.mWarningDialog = createAccessibilityServiceWarningDialog;
            return createAccessibilityServiceWarningDialog;
        }
        if (i == 9002) {
            final int i2 = 1;
            androidx.appcompat.app.AlertDialog createExclusiveDialog =
                    AccessibilityDialogUtils.createExclusiveDialog(
                            this.mContext,
                            this.exclusiveTaskFeature,
                            new DialogInterface.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityShortcutEditFragment$$ExternalSyntheticLambda5
                                public final /* synthetic */ AccessibilityShortcutEditFragment f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i3) {
                                    int i4 = i2;
                                    AccessibilityShortcutEditFragment
                                            accessibilityShortcutEditFragment = this.f$0;
                                    switch (i4) {
                                        case 0:
                                            accessibilityShortcutEditFragment.mIsDialogShown.set(
                                                    false);
                                            if (AccessibilityExclusiveUtil.isExclusiveTaskEnabled(
                                                    accessibilityShortcutEditFragment.mContext,
                                                    accessibilityShortcutEditFragment
                                                            .exclusiveTaskFeature)) {
                                                accessibilityShortcutEditFragment.showPopupDialog$1(
                                                        9002);
                                            } else {
                                                accessibilityShortcutEditFragment.switchBar
                                                        .setCheckedInternal(true);
                                                accessibilityShortcutEditFragment.mWarningDialog
                                                        .dismiss();
                                            }
                                            accessibilityShortcutEditFragment.mWarningDialog
                                                    .dismiss();
                                            break;
                                        default:
                                            accessibilityShortcutEditFragment.switchBar
                                                    .setCheckedInternal(true);
                                            accessibilityShortcutEditFragment.mWarningDialog
                                                    .dismiss();
                                            break;
                                    }
                                }
                            },
                            null);
            this.mWarningDialog = createExclusiveDialog;
            return createExclusiveDialog;
        }
        if (i != 9004) {
            return super.onCreateDialog(i);
        }
        final int i3 = 0;
        androidx.appcompat.app.AlertDialog createTTSLanguageCheckDialog =
                AccessibilityDialogUtils.createTTSLanguageCheckDialog(
                        this.mContext,
                        this.languageDialogInfo,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityShortcutEditFragment$$ExternalSyntheticLambda5
                            public final /* synthetic */ AccessibilityShortcutEditFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                int i4 = i3;
                                AccessibilityShortcutEditFragment
                                        accessibilityShortcutEditFragment = this.f$0;
                                switch (i4) {
                                    case 0:
                                        accessibilityShortcutEditFragment.mIsDialogShown.set(false);
                                        if (AccessibilityExclusiveUtil.isExclusiveTaskEnabled(
                                                accessibilityShortcutEditFragment.mContext,
                                                accessibilityShortcutEditFragment
                                                        .exclusiveTaskFeature)) {
                                            accessibilityShortcutEditFragment.showPopupDialog$1(
                                                    9002);
                                        } else {
                                            accessibilityShortcutEditFragment.switchBar
                                                    .setCheckedInternal(true);
                                            accessibilityShortcutEditFragment.mWarningDialog
                                                    .dismiss();
                                        }
                                        accessibilityShortcutEditFragment.mWarningDialog.dismiss();
                                        break;
                                    default:
                                        accessibilityShortcutEditFragment.switchBar
                                                .setCheckedInternal(true);
                                        accessibilityShortcutEditFragment.mWarningDialog.dismiss();
                                        break;
                                }
                            }
                        });
        this.mWarningDialog = createTTSLanguageCheckDialog;
        return createTTSLanguageCheckDialog;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.TwoStateCandidateListFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment, androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        for (ShortcutEditPreferenceType shortcutEditPreferenceType : this.typeArray) {
            ShortcutEditPreference shortcutEditPreference =
                    (ShortcutEditPreference)
                            preferenceScreen.findPreference(
                                    shortcutEditPreferenceType.preferenceKey);
            if (shortcutEditPreference != null) {
                shortcutEditPreference.type = shortcutEditPreferenceType;
                ((HashMap) this.shortcutPreferences)
                        .put(shortcutEditPreferenceType, shortcutEditPreference);
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        setDivider(null);
        int dimensionPixelSize =
                getResources().getDimensionPixelSize(R.dimen.radio_button_list_divider_padding);
        getListView()
                .addItemDecoration(
                        new DividerItemDecorator(
                                getContext(),
                                getResources().getDimensionPixelSize(R.dimen.controller_item_size)
                                        + dimensionPixelSize));
        return onCreateView;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.switchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.switchBar.setOnBeforeCheckedChangeListener(null);
            this.switchBar.hide();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        ShortcutContentObserver shortcutContentObserver = this.shortcutContentObserver;
        if (shortcutContentObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(shortcutContentObserver);
        }
        getContentResolver().unregisterContentObserver(this.softwareShortcutSettingObserver);
        if (((SeslSwitchBar) this.switchBar).mSwitch.isChecked() && this.userShortcutType == 0) {
            Toast.makeText(this.mContext, R.string.accessibility_shortcut_turned_off_no_options, 0)
                    .show();
            this.switchBar.setChecked(false);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(obj instanceof Boolean) || !(preference instanceof ShortcutEditPreference)) {
            Log.w("AccessibilityShortcutEditFragment", "Invalid instance.");
            return false;
        }
        ShortcutEditPreference shortcutEditPreference = (ShortcutEditPreference) preference;
        if (((Boolean) obj).booleanValue()) {
            this.userShortcutType =
                    shortcutEditPreference.type.shortcutType | this.userShortcutType;
        } else {
            int i = this.userShortcutType;
            int i2 = shortcutEditPreference.type.shortcutType;
            if (((~i2) & i) == 0) {
                shortcutEditPreference.setChecked(true);
                Toast.makeText(this.mContext, R.string.accessibility_shortcut_warning_toast, 0)
                        .show();
                return false;
            }
            this.userShortcutType = (~i2) & i;
        }
        if (this.userShortcutType != 0) {
            PreferredShortcuts.saveUserShortcutType(
                    this.mContext, new PreferredShortcut(this.shortcutKey, this.userShortcutType));
        }
        setShortcutState();
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        boolean hasValuesInSettings =
                SecAccessibilityUtils.hasValuesInSettings(this.mContext, this.shortcutKey);
        if (SecAccessibilityUtils.needTalkbackDefaultShortcut(
                this.mContext, ComponentName.unflattenFromString(this.shortcutKey))) {
            this.userShortcutType = FileType.SFF;
            this.switchBar.setCheckedInternal(true);
            updateCheckEnableStatus(true);
            for (ShortcutEditPreferenceType shortcutEditPreferenceType : this.typeArray) {
                ShortcutEditPreference shortcutEditPreference =
                        (ShortcutEditPreference)
                                ((HashMap) this.shortcutPreferences)
                                        .get(shortcutEditPreferenceType);
                if (shortcutEditPreference != null) {
                    shortcutEditPreference.setChecked(
                            (shortcutEditPreferenceType.shortcutType & this.userShortcutType) != 0);
                }
            }
        } else {
            this.userShortcutType =
                    PreferredShortcuts.retrieveUserShortcutType(this.mContext, 0, this.shortcutKey);
            this.switchBar.setCheckedInternal(hasValuesInSettings);
            updateCheckEnableStatus(hasValuesInSettings);
            updateState$4();
        }
        ShortcutContentObserver shortcutContentObserver = this.shortcutContentObserver;
        if (shortcutContentObserver != null) {
            shortcutContentObserver.register(this.mContext.getContentResolver());
        }
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("accessibility_button_mode"),
                        false,
                        this.softwareShortcutSettingObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("accessibility_button_targets"),
                        false,
                        this.softwareShortcutSettingObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("enabled_accessibility_services"),
                        false,
                        this.softwareShortcutSettingObserver);
    }

    public final void setShortcutState() {
        if (this.shortcutKey == null) {
            return;
        }
        if (((SeslSwitchBar) this.switchBar).mSwitch.isChecked()) {
            SecAccessibilityUtils.optInAllValuesToSettings(
                    this.mContext, this.userShortcutType, this.shortcutKey);
            SecAccessibilityUtils.optOutAllValuesFromSettings(
                    this.mContext, ~this.userShortcutType, this.shortcutKey);
        }
        SecAccessibilityUtils.setAccessibilityServiceState(this.mContext, this.shortcutKey, true);
    }

    public final void showPopupDialog$1(int i) {
        if (this.mIsDialogShown.compareAndSet(false, true)) {
            showDialog(i);
            setOnDismissListener(
                    new DialogInterface
                            .OnDismissListener() { // from class:
                                                   // com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityShortcutEditFragment$$ExternalSyntheticLambda7
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            AccessibilityShortcutEditFragment.this.mIsDialogShown.compareAndSet(
                                    true, false);
                        }
                    });
        }
    }

    public final void updateCheckEnableStatus(boolean z) {
        for (CheckBoxPreference checkBoxPreference :
                ((HashMap) this.shortcutPreferences).values()) {
            if (checkBoxPreference != null) {
                checkBoxPreference.setEnabled(z);
            }
        }
    }

    public final void updateSoftwareShortcutPreference(
            ShortcutEditPreference shortcutEditPreference, Context context) {
        AccessibilityButtonMode mode = AccessibilityButtonGestureUtil.getMode(context);
        shortcutEditPreference.setTitle(
                context.getString(
                        mode.isButton
                                ? R.string.accessibility_shortcut_summary_software
                                : R.string.accessibility_shortcut_summary_accessibility_gesture));
        shortcutEditPreference.setSummary(
                SecAccessibilityUtils.getAccessibilityButtonShortcutSummary(
                        context, this.shortcutKey, this.label));
        if (AccessibilityButtonMode.NAVIGATION_BAR_BUTTON.equals(mode)) {
            String string =
                    context.getString(
                            R.string.accessibility_shortcut_edit_description_software_start_stop,
                            this.label,
                            context.getString(R.string.accessibility_settings));
            shortcutEditPreference.isSummaryDescriptionSet = true;
            if (!Objects.equals(shortcutEditPreference.summaryDescription, string)) {
                shortcutEditPreference.summaryDescription = string;
                TextView textView = shortcutEditPreference.summaryView;
                if (textView != null && shortcutEditPreference.isSummaryDescriptionSet) {
                    textView.setContentDescription(string);
                }
            }
        }
        int ordinal = mode.ordinal();
        shortcutEditPreference.setIcon(
                context.getDrawable(
                        ordinal != 0
                                ? ordinal != 1
                                        ? ordinal != 2
                                                ? R.drawable.use_accessibility_button_floating
                                                : R.drawable.use_accessibility_gesture_3
                                        : R.drawable.use_accessibility_gesture_2
                                : R.drawable.use_accessibility_button));
    }

    public final void updateState$4() {
        final Context context = this.mContext;
        for (ShortcutEditPreferenceType shortcutEditPreferenceType : this.typeArray) {
            ShortcutEditPreference shortcutEditPreference =
                    (ShortcutEditPreference)
                            ((HashMap) this.shortcutPreferences).get(shortcutEditPreferenceType);
            if (shortcutEditPreference != null) {
                shortcutEditPreference.setChecked(
                        (shortcutEditPreferenceType.shortcutType & this.userShortcutType) != 0);
            }
        }
        final ShortcutEditPreference shortcutEditPreference2 =
                (ShortcutEditPreference)
                        ((HashMap) this.shortcutPreferences)
                                .get(ShortcutEditPreferenceType.SOFTWARE);
        if (shortcutEditPreference2 != null && context != null) {
            updateSoftwareShortcutPreference(shortcutEditPreference2, context);
            new Handler(Looper.getMainLooper())
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityShortcutEditFragment$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AccessibilityShortcutEditFragment.this
                                            .updateSoftwareShortcutPreference(
                                                    shortcutEditPreference2, context);
                                }
                            },
                            1000L);
        }
        setShortcutState();
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.CheckBoxPickerFragment,
              // com.samsung.android.settings.accessibility.base.widget.TwoStateCandidateListFragment
    public final TwoStatePreference getPrefInstance() {
        return new ShortcutEditPreference(this.mContext);
    }
}
