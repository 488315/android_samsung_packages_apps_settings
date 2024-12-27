package com.samsung.android.settings.usefulfeature.onehandedmode;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.Usefulfeature;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.atttvmode.AttTvModePreferenceController;
import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.samsung.android.settings.widget.SecHelpAnimationLayoutPreference;
import com.samsung.android.settings.widget.SecRadioButtonPreference;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class NewOneHandOperationSettings extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener,
                CompoundButton.OnCheckedChangeListener {
    public SecRadioButtonPreference mButtonType;
    public FragmentActivity mContext;
    public int mDoubleTapNum;
    public SecRadioButtonPreference mGestureType;
    public SecHelpAnimationLayoutPreference mPreviewPreference;
    public boolean mShowHardkey;
    public SettingsMainSwitchBar mSwitchBar;
    public static final Uri[] SETTINGS_URIS = {
        Settings.System.getUriFor("any_screen_enabled"),
        Settings.Global.getUriFor("navigation_bar_gesture_while_hidden")
    };
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass5();
    public AlertDialog mTalkbackDisableDialog = null;
    public final AnonymousClass1 mSettingsObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.usefulfeature.onehandedmode.NewOneHandOperationSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    NewOneHandOperationSettings newOneHandOperationSettings =
                            NewOneHandOperationSettings.this;
                    Uri[] uriArr = NewOneHandOperationSettings.SETTINGS_URIS;
                    newOneHandOperationSettings.updateSwitchAndPreferenceState$1();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.onehandedmode.NewOneHandOperationSettings$5, reason: invalid class name */
    public final class AnonymousClass5 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            hashMap.put("reduce_screen_size", GtsGroup.GROUP_KEY_ADVANCED.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
            if (!(!Utils.isTablet()) || !UsefulfeatureUtils.isEnabledOneHandOperation(context)) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_new_one_hand_operation_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.onehand_settings_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return Usefulfeature.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4352;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    public final void initPreference$7$1() {
        addPreferencesFromResource(R.xml.sec_new_one_hand_operation_settings);
        this.mContext = getActivity();
        setAutoRemoveInsetCategory(false);
        SecHelpAnimationLayoutPreference secHelpAnimationLayoutPreference =
                (SecHelpAnimationLayoutPreference) findPreference("onehand_mode_preview");
        this.mPreviewPreference = secHelpAnimationLayoutPreference;
        secHelpAnimationLayoutPreference.setDescText(
                this.mContext.getString(R.string.onehand_settings_text_1));
        this.mGestureType = (SecRadioButtonPreference) findPreference("gesture_type");
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference) findPreference("button_type");
        this.mButtonType = secRadioButtonPreference;
        SecRadioButtonPreference secRadioButtonPreference2 = this.mGestureType;
        secRadioButtonPreference2.mListener = this;
        secRadioButtonPreference.mListener = this;
        secRadioButtonPreference2.setSummary(R.string.onehand_settings_using_gesture_summary);
        this.mButtonType.seslSetRoundedBg(12);
        setHasOptionsMenu(true);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (UsefulfeatureUtils.isEnabledOneHandOperation(this.mContext)) {
            if (!z) {
                setOneHandOperation(z ? 1 : 0);
                reduceSizeBroadcast(0);
            } else {
                if (SemFloatingFeature.getInstance()
                                .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_NFC_HW_KEYBOARD")
                        && getResources().getConfiguration().semMobileKeyboardCovered == 1) {
                    this.mSwitchBar.setChecked(
                            Settings.System.getInt(getContentResolver(), "any_screen_enabled", 0)
                                    != 0);
                    Toast.makeText(
                                    getActivity(),
                                    getResources().getString(R.string.onehand_settings_cover_toast),
                                    1)
                            .show();
                    return;
                }
                if (UsefulfeatureUtils.isReadyOneHandedOperationStatusEnable(this.mContext)) {
                    setOneHandOperation(z ? 1 : 0);
                    reduceSizeBroadcast(1);
                } else {
                    AlertDialog alertDialog = this.mTalkbackDisableDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                        this.mTalkbackDisableDialog = null;
                    }
                    ViewGroup viewGroup =
                            (ViewGroup)
                                    ((LayoutInflater)
                                                    new ContextThemeWrapper(
                                                                    this.mContext,
                                                                    android.R.style
                                                                            .Theme
                                                                            .DeviceDefault
                                                                            .Light
                                                                            .Dialog)
                                                            .getSystemService("layout_inflater"))
                                            .inflate(
                                                    R.layout.sec_accessibility_exclusive_popup,
                                                    (ViewGroup) null);
                    TextView textView = (TextView) viewGroup.findViewById(R.id.dialog_desc_string);
                    TextView textView2 =
                            (TextView) viewGroup.findViewById(R.id.dialog_list_desc_string);
                    textView.setText(
                            this.mContext.getString(R.string.onehand_settings_dialog_text));
                    textView2.setText(
                            UsefulfeatureUtils.oneHandOperationDisablepopupMessage(this.mContext));
                    final int i = 1;
                    AlertDialog.Builder positiveButton =
                            new AlertDialog.Builder(this.mContext)
                                    .setTitle(
                                            this.mContext.getString(
                                                    R.string.onehand_settings_dialog_title))
                                    .setView(viewGroup)
                                    .setPositiveButton(
                                            R.string.ok,
                                            new DialogInterface.OnClickListener(
                                                    this) { // from class:
                                                            // com.samsung.android.settings.usefulfeature.onehandedmode.NewOneHandOperationSettings.3
                                                public final /* synthetic */
                                                NewOneHandOperationSettings this$0;

                                                {
                                                    this.this$0 = this;
                                                }

                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i2) {
                                                    switch (i) {
                                                        case 0:
                                                            NewOneHandOperationSettings
                                                                    newOneHandOperationSettings =
                                                                            this.this$0;
                                                            Uri[] uriArr =
                                                                    NewOneHandOperationSettings
                                                                            .SETTINGS_URIS;
                                                            Settings.System.putInt(
                                                                    newOneHandOperationSettings
                                                                            .getContentResolver(),
                                                                    "any_screen_enabled",
                                                                    0);
                                                            this.this$0.reduceSizeBroadcast(0);
                                                            break;
                                                        default:
                                                            if (UsefulfeatureUtils
                                                                    .isEnabledOneHandOperation(
                                                                            this.this$0.mContext)) {
                                                                UsefulfeatureUtils
                                                                        .turnOffFunctionsConflictWithOneHandedMode(
                                                                                this.this$0
                                                                                        .mContext);
                                                                this.this$0.setOneHandOperation(1);
                                                                this.this$0.reduceSizeBroadcast(1);
                                                                break;
                                                            }
                                                            break;
                                                    }
                                                }
                                            });
                    final int i2 = 0;
                    this.mTalkbackDisableDialog =
                            positiveButton
                                    .setNegativeButton(
                                            R.string.cancel,
                                            new DialogInterface.OnClickListener(
                                                    this) { // from class:
                                                            // com.samsung.android.settings.usefulfeature.onehandedmode.NewOneHandOperationSettings.3
                                                public final /* synthetic */
                                                NewOneHandOperationSettings this$0;

                                                {
                                                    this.this$0 = this;
                                                }

                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i22) {
                                                    switch (i2) {
                                                        case 0:
                                                            NewOneHandOperationSettings
                                                                    newOneHandOperationSettings =
                                                                            this.this$0;
                                                            Uri[] uriArr =
                                                                    NewOneHandOperationSettings
                                                                            .SETTINGS_URIS;
                                                            Settings.System.putInt(
                                                                    newOneHandOperationSettings
                                                                            .getContentResolver(),
                                                                    "any_screen_enabled",
                                                                    0);
                                                            this.this$0.reduceSizeBroadcast(0);
                                                            break;
                                                        default:
                                                            if (UsefulfeatureUtils
                                                                    .isEnabledOneHandOperation(
                                                                            this.this$0.mContext)) {
                                                                UsefulfeatureUtils
                                                                        .turnOffFunctionsConflictWithOneHandedMode(
                                                                                this.this$0
                                                                                        .mContext);
                                                                this.this$0.setOneHandOperation(1);
                                                                this.this$0.reduceSizeBroadcast(1);
                                                                break;
                                                            }
                                                            break;
                                                    }
                                                }
                                            })
                                    .setOnDismissListener(
                                            new DialogInterface
                                                    .OnDismissListener() { // from class:
                                                                           // com.samsung.android.settings.usefulfeature.onehandedmode.NewOneHandOperationSettings.2
                                                @Override // android.content.DialogInterface.OnDismissListener
                                                public final void onDismiss(
                                                        DialogInterface dialogInterface) {
                                                    NewOneHandOperationSettings
                                                            newOneHandOperationSettings =
                                                                    NewOneHandOperationSettings
                                                                            .this;
                                                    Uri[] uriArr =
                                                            NewOneHandOperationSettings
                                                                    .SETTINGS_URIS;
                                                    newOneHandOperationSettings
                                                            .updateSwitchAndPreferenceState$1();
                                                }
                                            })
                                    .show();
                }
            }
            LoggingHelper.insertEventLogging(4352, 4353, z);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getPreferenceScreen().removeAll();
        initPreference$7$1();
        updateSwitchAndPreferenceState$1();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initPreference$7$1();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (Utils.isSupportHelpMenu(getActivity())) {
            menu.add(0, 1, 0, R.string.help_title).setShowAsAction(2);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 1) {
            Intent intent = new Intent("com.samsung.helphub.HELP");
            intent.putExtra("helphub:section", "one_handed_operation");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        AlertDialog alertDialog = this.mTalkbackDisableDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mTalkbackDisableDialog.dismiss();
            this.mTalkbackDisableDialog = null;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        updateRadioButtons$3(secRadioButtonPreference);
        LoggingHelper.insertEventLogging(
                4352,
                4354,
                secRadioButtonPreference.equals(this.mGestureType)
                        ? "1000"
                        : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (!UsefulfeatureUtils.isEnabledOneHandOperation(this.mContext)) {
            getActivity().finish();
        }
        updateSwitchAndPreferenceState$1();
        for (Uri uri : SETTINGS_URIS) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(uri, false, this.mSettingsObserver);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_end)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_size)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_start)),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }

    public final void reduceSizeBroadcast(int i) {
        Intent intent = new Intent("com.samsung.intent.action.ONEHAND_REDUCE_SCREEN_STATUS");
        intent.putExtra("is_enabled", i);
        getActivity().sendBroadcast(intent);
    }

    public final void setOneHandOperation(int i) {
        boolean z = false;
        boolean z2 = i == 1;
        Settings.System.putInt(getContentResolver(), "any_screen_enabled", i);
        boolean z3 =
                Settings.System.getInt(getContentResolver(), "one_handed_op_wakeup_type", 1) != 0;
        boolean z4 =
                Settings.Global.getInt(
                                getContentResolver(), "navigation_bar_gesture_while_hidden", 0)
                        != 0;
        if (this.mShowHardkey && z2 && z4 && z3) {
            Settings.System.putInt(getContentResolver(), "one_handed_op_wakeup_type", 0);
            updateRadioButtons$3(this.mGestureType);
            Toast.makeText(
                            this.mContext,
                            R.string.onehand_settings_change_gesture_by_navibar_toast,
                            1)
                    .show();
        }
        if (Settings.System.getInt(
                                this.mContext.getContentResolver(), "one_handed_op_wakeup_type", 0)
                        == 1
                && z2) {
            z = true;
        }
        UsefulfeatureUtils.setOneHandModeKeyCustomizationInfo(z);
    }

    public final void updateRadioButtons$3(SecRadioButtonPreference secRadioButtonPreference) {
        int i = Settings.System.getInt(getContentResolver(), "one_handed_op_wakeup_type", 1);
        if (secRadioButtonPreference == this.mGestureType) {
            this.mButtonType.setChecked(false);
            this.mGestureType.setChecked(true);
            if (i != 0) {
                Settings.System.putInt(getContentResolver(), "one_handed_op_wakeup_type", 0);
                UsefulfeatureUtils.setOneHandModeKeyCustomizationInfo(false);
            }
        } else {
            SecRadioButtonPreference secRadioButtonPreference2 = this.mButtonType;
            if (secRadioButtonPreference == secRadioButtonPreference2) {
                secRadioButtonPreference2.setChecked(true);
                this.mGestureType.setChecked(false);
                if (i != 1) {
                    Settings.System.putInt(getContentResolver(), "one_handed_op_wakeup_type", 1);
                    UsefulfeatureUtils.setOneHandModeKeyCustomizationInfo(
                            Settings.System.getInt(getContentResolver(), "any_screen_enabled", 0)
                                    != 0);
                }
            }
        }
        int i2 = Settings.System.getInt(getContentResolver(), "one_handed_op_wakeup_type", 1);
        if (i2 != 0) {
            if (i2 != 1) {
                return;
            }
            if (Utils.isNightMode(this.mContext)) {
                this.mPreviewPreference.setAnimationResource("onehandedmode_doubletap_dark.json");
                return;
            } else {
                this.mPreviewPreference.setAnimationResource("onehandedmode_doubletap.json");
                return;
            }
        }
        if (Utils.isNightMode(this.mContext)) {
            if (Settings.Global.getInt(
                            getContext().getContentResolver(),
                            "navigation_bar_gesture_while_hidden",
                            0)
                    == 1) {
                this.mPreviewPreference.setAnimationResource(
                        "onehandedmode_swipe_gesture_hint_dark.json");
                return;
            } else {
                this.mPreviewPreference.setAnimationResource("onehandedmode_swipe_dark.json");
                return;
            }
        }
        if (Settings.Global.getInt(
                        getContext().getContentResolver(), "navigation_bar_gesture_while_hidden", 0)
                == 1) {
            this.mPreviewPreference.setAnimationResource(
                    "onehandedmode_swipe_gesture_hint_light.json");
        } else {
            this.mPreviewPreference.setAnimationResource("onehandedmode_swipe.json");
        }
    }

    public final void updateSwitchAndPreferenceState$1() {
        boolean z = Settings.System.getInt(getContentResolver(), "any_screen_enabled", 0) != 0;
        if (this.mSwitchBar != null) {
            if (UsefulfeatureUtils.isEnabledOneHandOperation(this.mContext)) {
                this.mSwitchBar.setEnabled(true);
            } else {
                this.mSwitchBar.setEnabled(false);
                z = false;
            }
            AlertDialog alertDialog = this.mTalkbackDisableDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                this.mSwitchBar.setChecked(z);
            }
            this.mSwitchBar.show();
        }
        this.mGestureType.setEnabled(z);
        this.mButtonType.setEnabled(z);
        this.mShowHardkey =
                this.mContext
                        .getResources()
                        .getBoolean(android.R.bool.config_sms_decode_gsm_8bit_data);
        this.mDoubleTapNum =
                Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        AttTvModePreferenceController.POWER_DOUBLE_TAP_DB,
                        3);
        if (Settings.Global.getInt(getContentResolver(), "navigation_bar_gesture_while_hidden", 0)
                == 1) {
            this.mButtonType.setSummary(
                    R.string.onehand_settings_not_available_while_using_gesture);
        } else {
            int i = this.mDoubleTapNum;
            if (i != 0 && i != 1) {
                this.mButtonType.setSummary(
                        R.string.onehand_settings_using_button_summary_doubletap);
            } else if (this.mShowHardkey) {
                this.mButtonType.setSummary(R.string.onehand_settings_using_button_summary);
            } else {
                this.mButtonType.setSummary(R.string.onehand_settings_using_button_summary_hardkey);
            }
        }
        if (Settings.Global.getInt(getContentResolver(), "navigation_bar_gesture_while_hidden", 0)
                == 1) {
            this.mButtonType.setEnabled(false);
        }
        updateRadioButtons$3(
                Settings.System.getInt(getContentResolver(), "one_handed_op_wakeup_type", 1) == 0
                        ? this.mGestureType
                        : this.mButtonType);
    }
}
