package com.samsung.android.settings.activekey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class XcoverTopKeySettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public PreferenceCategory mActiveKeySettingCategory;
    public SecPreferenceCategory mCustomizeCategory;
    public SecPreferenceScreen mLongPress;
    public SwitchPreference mOnLockScreen;
    public int mPressType;
    public SecInsetCategoryPreference mPreviewCategory;
    public LayoutPreference mPreviewPreference;
    public SecPreferenceScreen mShortPress;
    public SecSwitchPreferenceScreen mXcoverTopKeyDedicatedAppSwitch;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.activekey.XcoverTopKeySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!UsefulfeatureUtils.hasDedicatedAppEnable$1(context)) {
                ((ArrayList) nonIndexableKeys).add("xcover_top_key_dedicated_app");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_xcovertopkey_gridview_settings;
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (!UsefulfeatureUtils.hasActiveKey()) {
                return false;
            }
            UsefulfeatureUtils.hasActiveKey();
            return false;
        }
    }

    public final void dedicatedAppUpdate$1(boolean z) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mXcoverTopKeyDedicatedAppSwitch;
        if (secSwitchPreferenceScreen != null) {
            if (!z) {
                secSwitchPreferenceScreen.setSummary(R.string.switch_off_text);
                return;
            }
            Context context = getContext();
            int i = this.mPressType;
            String string =
                    Settings.System.getString(
                            context.getContentResolver(),
                            (i == 0 || i == 1)
                                    ? "dedicated_app_label_xcover"
                                    : i != 2
                                            ? i != 3
                                                    ? ApnSettings.MVNO_NONE
                                                    : "dedicated_app_label_side"
                                            : "dedicated_app_label_top");
            String dedicatedApp = DedicatedAppInfo.getDedicatedApp(getContext(), this.mPressType);
            if (!TextUtils.isEmpty(dedicatedApp) && Utils.hasPackage(getContext(), dedicatedApp)) {
                this.mXcoverTopKeyDedicatedAppSwitch.setSummary(string);
                return;
            }
            String str =
                    (String) DedicatedAppInfo.loadAppList(getContext(), this.mPressType).get(0);
            String charSequence = Utils.getApplicationLabel(getContext(), str).toString();
            this.mXcoverTopKeyDedicatedAppSwitch.setSummary(charSequence);
            DedicatedAppInfo.saveDedicatedApp(getContext(), this.mPressType, str);
            DedicatedAppInfo.saveDedicatedAppLabel(getContext(), this.mPressType, charSequence);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 67210;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_xcovertopkey_gridview_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        String str;
        String str2;
        String str3;
        super.onCreate(bundle);
        getActivity().setTitle(R.string.xcover_top_key_title);
        this.mPressType = 2;
        if (Settings.System.getInt(
                        getActivity().getContentResolver(), "dedicated_app_top_switch", 0)
                != 1) {
            if (UsefulfeatureUtils.hasVzwPttEnable(getActivity())) {
                str = "com.verizon.pushtotalkplus";
                str2 = "Push To Talk+";
                str3 = "vzwptt_top_key_first_check";
            } else if (UsefulfeatureUtils.hasEPttEnable(getActivity())) {
                str = "com.att.eptt";
                str2 = "AT&T EPTT";
                str3 = "eptt_top_key_first_check";
            } else if (UsefulfeatureUtils.hasBMCPttEnable(getActivity())) {
                str = "com.bell.ptt";
                str2 = "Push-to-Talk";
                str3 = "bmc_top_key_ptt_first_check";
            } else if (UsefulfeatureUtils.hasTMOPttEnable(getActivity())) {
                str = "com.sprint.sdcplus";
                str2 = "TDC";
                str3 = "tmo_top_key_ptt_first_check";
            } else {
                str = ApnSettings.MVNO_NONE;
                str2 = ApnSettings.MVNO_NONE;
                str3 = str2;
            }
            if (!TextUtils.isEmpty(str3)
                    && Settings.System.getInt(getActivity().getContentResolver(), str3, 0) != 1) {
                Settings.System.putInt(getActivity().getContentResolver(), str3, 1);
                DedicatedAppInfo.setDedicatedAppSwitch(getActivity(), this.mPressType, true);
                DedicatedAppInfo.saveDedicatedApp(getActivity(), this.mPressType, str);
                DedicatedAppInfo.saveDedicatedAppLabel(getActivity(), this.mPressType, str2);
            }
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) findPreference("xcover_top_key_dedicated_app");
        this.mXcoverTopKeyDedicatedAppSwitch = secSwitchPreferenceScreen;
        secSwitchPreferenceScreen.setOnPreferenceChangeListener(this);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen2 = this.mXcoverTopKeyDedicatedAppSwitch;
        secSwitchPreferenceScreen2.getClass();
        SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen2, true);
        this.mPreviewCategory =
                (SecInsetCategoryPreference) findPreference("xcover_top_key_anyscreen_category");
        this.mPreviewPreference = (LayoutPreference) findPreference("xcover_top_key_anyscreen");
        this.mCustomizeCategory =
                (SecPreferenceCategory) findPreference("xcover_top_customize_category_key");
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) findPreference("xcover_top_short_press_key");
        this.mShortPress = secPreferenceScreen;
        secPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
        SecPreferenceScreen secPreferenceScreen2 =
                (SecPreferenceScreen) findPreference("xcover_top_long_press_key");
        this.mLongPress = secPreferenceScreen2;
        secPreferenceScreen2.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen2, true);
        this.mActiveKeySettingCategory =
                (PreferenceCategory) findPreference("xcover_top_key_setting_category_key");
        SwitchPreference switchPreference =
                (SwitchPreference) findPreference("xcover_top_key_on_lockscreen_key");
        this.mOnLockScreen = switchPreference;
        switchPreference.setOnPreferenceChangeListener(this);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("XcoverTopKeySettings", "onPause() ");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (preference.getKey().equals("xcover_top_key_on_lockscreen_key")) {
            Settings.System.putInt(
                    getActivity().getContentResolver(),
                    "xcover_top_key_on_lockscreen",
                    booleanValue ? 1 : 0);
            LoggingHelper.insertEventLogging(67210, 67215, booleanValue);
            return true;
        }
        if (!preference.getKey().equals("xcover_top_key_dedicated_app")) {
            return false;
        }
        Settings.System.putInt(
                getActivity().getContentResolver(),
                "dedicated_app_top_switch",
                booleanValue ? 1 : 0);
        LoggingHelper.insertEventLogging(67210, 67212, booleanValue);
        if (DedicatedAppInfo.getDedicatedAppState(getContext(), this.mPressType)) {
            DedicatedAppInfo.setB2BDeltaKeyCustomizationInfo(
                    getContext(),
                    this.mPressType,
                    DedicatedAppInfo.getDedicatedApp(getContext(), this.mPressType),
                    booleanValue);
        }
        pttUIUpdate$1(booleanValue);
        dedicatedAppUpdate$1(booleanValue);
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        String str;
        String str2;
        super.onResume();
        if (UsefulfeatureUtils.hasDedicatedAppEnable$1(getActivity())) {
            getPreferenceScreen().addPreference(this.mXcoverTopKeyDedicatedAppSwitch);
            boolean z =
                    Settings.System.getInt(
                                    getActivity().getContentResolver(),
                                    "dedicated_app_top_switch",
                                    0)
                            != 0;
            this.mXcoverTopKeyDedicatedAppSwitch.setChecked(z);
            dedicatedAppUpdate$1(z);
            this.mPreviewCategory.setHeight(
                    (int)
                            getContext()
                                    .getResources()
                                    .getDimension(R.dimen.sec_widget_inset_category_height));
            this.mPreviewCategory.seslSetSubheaderRoundedBackground(12);
            pttUIUpdate$1(z);
        } else {
            getPreferenceScreen().removePreference(this.mXcoverTopKeyDedicatedAppSwitch);
            this.mPreviewCategory.setHeight(1);
            this.mPreviewCategory.seslSetSubheaderRoundedBackground(12);
            pttUIUpdate$1(false);
            if (DedicatedAppInfo.getDedicatedAppSwitch(getContext(), this.mPressType)
                    || DedicatedAppInfo.getDedicatedAppState(getContext(), this.mPressType)) {
                DedicatedAppInfo.setDedicatedAppSwitch(getContext(), this.mPressType, false);
                DedicatedAppInfo.saveDedicatedApp(
                        getContext(), this.mPressType, ApnSettings.MVNO_NONE);
                DedicatedAppInfo.saveDedicatedAppLabel(
                        getContext(), this.mPressType, ApnSettings.MVNO_NONE);
            }
        }
        ((TextView) this.mPreviewPreference.mRootView.findViewById(R.id.active_key_main_text))
                .setText(getString(R.string.xcover_top_key_summary));
        LinearLayout linearLayout =
                (LinearLayout)
                        this.mPreviewPreference.mRootView.findViewById(
                                R.id.active_key_image_container);
        linearLayout.semSetRoundedCorners(15);
        linearLayout.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        ((ImageView) this.mPreviewPreference.mRootView.findViewById(R.id.active_key_image))
                .setImageDrawable(
                        getResources()
                                .getDrawable(
                                        R.drawable.sec_setting_topkey_image_xcoverpro,
                                        getActivity().getTheme()));
        Intent intent = new Intent();
        intent.setAction("com.samsung.settings.ACTIVE_KEY_CLEAR");
        intent.addFlags(16777216);
        getActivity().sendBroadcast(intent);
        FragmentActivity activity = getActivity();
        ActiveKeyInfo.ActiveKeyAppInfo activeKeyAppInfo =
                ActiveKeyInfo.getActiveKeyAppInfo(activity, 3);
        if (activeKeyAppInfo == null
                || (str2 = activeKeyAppInfo.mDB) == null
                || ApnSettings.MVNO_NONE.equals(str2)) {
            this.mShortPress.setSummary(R.string.switch_off_text);
        } else {
            CharSequence charSequence = activeKeyAppInfo.mLabel;
            if (charSequence == null || ApnSettings.MVNO_NONE.equals(charSequence)) {
                this.mShortPress.setSummary(R.string.lock_app_shortcut_deleted_app);
            } else if (activeKeyAppInfo.mIsEnabled) {
                this.mShortPress.setSummary(activeKeyAppInfo.mLabel);
            } else {
                this.mShortPress.setSummary(
                        getResources()
                                .getString(
                                        R.string.lock_app_shortcut_disabled_app,
                                        activeKeyAppInfo.mLabel));
            }
        }
        ActiveKeyInfo.ActiveKeyAppInfo activeKeyAppInfo2 =
                ActiveKeyInfo.getActiveKeyAppInfo(activity, 4);
        if (activeKeyAppInfo2 == null
                || (str = activeKeyAppInfo2.mDB) == null
                || ApnSettings.MVNO_NONE.equals(str)) {
            this.mLongPress.setSummary(R.string.switch_off_text);
        } else {
            CharSequence charSequence2 = activeKeyAppInfo2.mLabel;
            if (charSequence2 == null || ApnSettings.MVNO_NONE.equals(charSequence2)) {
                this.mLongPress.setSummary(R.string.lock_app_shortcut_deleted_app);
            } else if (activeKeyAppInfo2.mIsEnabled) {
                this.mLongPress.setSummary(activeKeyAppInfo2.mLabel);
            } else {
                this.mLongPress.setSummary(
                        getResources()
                                .getString(
                                        R.string.lock_app_shortcut_disabled_app,
                                        activeKeyAppInfo2.mLabel));
            }
        }
        this.mOnLockScreen.setChecked(
                Settings.System.getInt(
                                getActivity().getContentResolver(),
                                "xcover_top_key_on_lockscreen",
                                0)
                        != 0);
        Log.d("XcoverTopKeySettings", "onResume() ");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        Log.d("XcoverTopKeySettings", "onStop() ");
    }

    public final void pttUIUpdate$1(boolean z) {
        if (z) {
            getPreferenceScreen().removePreference(this.mPreviewCategory);
            getPreferenceScreen().removePreference(this.mPreviewPreference);
            getPreferenceScreen().removePreference(this.mCustomizeCategory);
            getPreferenceScreen().removePreference(this.mShortPress);
            getPreferenceScreen().removePreference(this.mLongPress);
            getPreferenceScreen().removePreference(this.mActiveKeySettingCategory);
            getPreferenceScreen().removePreference(this.mOnLockScreen);
            return;
        }
        getPreferenceScreen().addPreference(this.mPreviewCategory);
        getPreferenceScreen().addPreference(this.mPreviewPreference);
        getPreferenceScreen().addPreference(this.mCustomizeCategory);
        this.mCustomizeCategory.addPreference(this.mShortPress);
        this.mCustomizeCategory.addPreference(this.mLongPress);
        getPreferenceScreen().addPreference(this.mActiveKeySettingCategory);
        this.mActiveKeySettingCategory.addPreference(this.mOnLockScreen);
    }
}
