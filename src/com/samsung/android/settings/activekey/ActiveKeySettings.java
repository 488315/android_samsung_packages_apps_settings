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
public class ActiveKeySettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public PreferenceCategory mActiveKeySettingCategory;
    public SecPreferenceCategory mCustomizeCategory;
    public SecPreferenceScreen mLongPress;
    public SwitchPreference mOnLockScreen;
    public SecInsetCategoryPreference mPreviewCategory;
    public LayoutPreference mPreviewPreference;
    public SecPreferenceScreen mShortPress;
    public SecSwitchPreferenceScreen mXcoverKeyDedicatedAppSwitch;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.activekey.ActiveKeySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!UsefulfeatureUtils.hasDedicatedAppEnable$1(context)) {
                ((ArrayList) nonIndexableKeys).add("xcover_key_dedicated_app");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            UsefulfeatureUtils.hasActiveKey();
            searchIndexableResource.xmlResId = R.xml.sec_activekey_gridview_settings;
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return UsefulfeatureUtils.hasActiveKey();
        }
    }

    public final void dedicatedAppUpdate(boolean z) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mXcoverKeyDedicatedAppSwitch;
        if (secSwitchPreferenceScreen != null) {
            if (!z) {
                secSwitchPreferenceScreen.setSummary(R.string.switch_off_text);
                return;
            }
            String string =
                    Settings.System.getString(
                            getContext().getContentResolver(), "dedicated_app_label_xcover");
            String dedicatedApp = DedicatedAppInfo.getDedicatedApp(getContext(), 0);
            if (!TextUtils.isEmpty(dedicatedApp) && Utils.hasPackage(getContext(), dedicatedApp)) {
                this.mXcoverKeyDedicatedAppSwitch.setSummary(string);
                return;
            }
            String str = (String) DedicatedAppInfo.loadAppList(getContext(), 0).get(0);
            String charSequence = Utils.getApplicationLabel(getContext(), str).toString();
            this.mXcoverKeyDedicatedAppSwitch.setSummary(charSequence);
            DedicatedAppInfo.saveDedicatedApp(getContext(), 0, str);
            DedicatedAppInfo.saveDedicatedAppLabel(getContext(), 0, charSequence);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 67200;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        UsefulfeatureUtils.hasActiveKey();
        return R.xml.sec_activekey_gridview_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        String str;
        boolean z;
        super.onCreate(bundle);
        UsefulfeatureUtils.hasActiveKey();
        getActivity().setTitle(R.string.active_key_title);
        boolean hasVzwPttEnable = UsefulfeatureUtils.hasVzwPttEnable(getActivity());
        String str2 = ApnSettings.MVNO_NONE;
        if (hasVzwPttEnable) {
            if (Settings.System.getInt(
                            getActivity().getContentResolver(), "verizon_dedicated_ptt_switch", 0)
                    != 0) {
                Settings.System.putInt(
                        getActivity().getContentResolver(), "verizon_dedicated_ptt_switch", 0);
                str2 = "com.verizon.pushtotalkplus";
                str = "Push To Talk+";
                z = true;
            }
            z = false;
            str = ApnSettings.MVNO_NONE;
        } else if (UsefulfeatureUtils.hasEPttEnable(getActivity())) {
            if (Settings.System.getInt(getActivity().getContentResolver(), "eptt_first_check", 0)
                    != 1) {
                Settings.System.putInt(getActivity().getContentResolver(), "eptt_first_check", 1);
                if (Settings.System.getInt(
                                getActivity().getContentResolver(),
                                "dedicated_app_xcover_switch",
                                0)
                        != 1) {
                    str2 = "com.att.eptt";
                    str = "AT&T EPTT";
                    z = true;
                }
            }
            z = false;
            str = ApnSettings.MVNO_NONE;
        } else {
            if (UsefulfeatureUtils.hasBMCPttEnable(getActivity())) {
                if (Settings.System.getInt(
                                getActivity().getContentResolver(), "bmc_ptt_first_check", 0)
                        != 1) {
                    Settings.System.putInt(
                            getActivity().getContentResolver(), "bmc_ptt_first_check", 1);
                    if (Settings.System.getInt(
                                    getActivity().getContentResolver(),
                                    "dedicated_app_xcover_switch",
                                    0)
                            != 1) {
                        str2 = "com.bell.ptt";
                        str = "Push-to-Talk";
                        z = true;
                    } else {
                        z = false;
                        str = ApnSettings.MVNO_NONE;
                    }
                    String string =
                            Settings.System.getString(
                                    getActivity().getContentResolver(), "short_press_app");
                    String string2 =
                            Settings.System.getString(
                                    getActivity().getContentResolver(), "long_press_app");
                    if (!TextUtils.isEmpty(string)
                            && "com.bell.ptt/com.bell.ptt.StartupActivity".equals(string)
                            && !TextUtils.isEmpty(string2)
                            && "com.bell.ptt/com.bell.ptt.StartupActivity".equals(string2)) {
                        UsefulfeatureUtils.hasActiveKey();
                        UsefulfeatureUtils.hasActiveKey();
                        Settings.System.putString(
                                getActivity().getContentResolver(),
                                "short_press_app",
                                "com.sec.android.app.camera/com.sec.android.app.camera.Camera");
                        Settings.System.putString(
                                getActivity().getContentResolver(),
                                "long_press_app",
                                "com.samsung.android.calendar/com.samsung.android.app.calendar.activity.MainActivity");
                        ActiveKeyInfo.setExtraKeyCustomizationInfo(
                                0,
                                "com.sec.android.app.camera/com.sec.android.app.camera.Camera",
                                !TextUtils.isEmpty(
                                        "com.sec.android.app.camera/com.sec.android.app.camera.Camera"));
                        ActiveKeyInfo.setExtraKeyCustomizationInfo(
                                1,
                                "com.samsung.android.calendar/com.samsung.android.app.calendar.activity.MainActivity",
                                true);
                    }
                }
            } else if (UsefulfeatureUtils.hasTMOPttEnable(getActivity())
                    && Settings.System.getInt(
                                    getActivity().getContentResolver(), "tmo_ptt_first_check", 0)
                            != 1) {
                Settings.System.putInt(
                        getActivity().getContentResolver(), "tmo_ptt_first_check", 1);
                if (Settings.System.getInt(
                                getActivity().getContentResolver(),
                                "dedicated_app_xcover_switch",
                                0)
                        != 1) {
                    str2 = "com.sprint.sdcplus";
                    str = "TDC";
                    z = true;
                }
            }
            z = false;
            str = ApnSettings.MVNO_NONE;
        }
        if (z) {
            DedicatedAppInfo.setDedicatedAppSwitch(getActivity(), 0, z);
            DedicatedAppInfo.saveDedicatedApp(getActivity(), 0, str2);
            DedicatedAppInfo.saveDedicatedAppLabel(getActivity(), 0, str);
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) findPreference("xcover_key_dedicated_app");
        this.mXcoverKeyDedicatedAppSwitch = secSwitchPreferenceScreen;
        secSwitchPreferenceScreen.setOnPreferenceChangeListener(this);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen2 = this.mXcoverKeyDedicatedAppSwitch;
        secSwitchPreferenceScreen2.getClass();
        SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen2, true);
        this.mPreviewCategory =
                (SecInsetCategoryPreference) findPreference("active_key_anyscreen_category");
        this.mPreviewPreference = (LayoutPreference) findPreference("active_key_anyscreen");
        this.mCustomizeCategory = (SecPreferenceCategory) findPreference("customize_category_key");
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) findPreference("short_press_key");
        this.mShortPress = secPreferenceScreen;
        secPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
        SecPreferenceScreen secPreferenceScreen2 =
                (SecPreferenceScreen) findPreference("long_press_key");
        this.mLongPress = secPreferenceScreen2;
        secPreferenceScreen2.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen2, true);
        this.mActiveKeySettingCategory =
                (PreferenceCategory) findPreference("active_key_setting_category_key");
        SwitchPreference switchPreference =
                (SwitchPreference) findPreference("active_key_on_lockscreen_key");
        this.mOnLockScreen = switchPreference;
        switchPreference.setOnPreferenceChangeListener(this);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("ActiveKeySettings", "onPause() ");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (preference.getKey().equals("active_key_on_lockscreen_key")) {
            Settings.System.putInt(
                    getActivity().getContentResolver(),
                    "active_key_on_lockscreen",
                    booleanValue ? 1 : 0);
            LoggingHelper.insertEventLogging(67200, 67205, booleanValue);
            return true;
        }
        if (!preference.getKey().equals("xcover_key_dedicated_app")) {
            return false;
        }
        Settings.System.putInt(
                getActivity().getContentResolver(),
                "dedicated_app_xcover_switch",
                booleanValue ? 1 : 0);
        LoggingHelper.insertEventLogging(67200, 67202, booleanValue);
        if (DedicatedAppInfo.getDedicatedAppState(getContext(), 0)) {
            DedicatedAppInfo.setB2BDeltaKeyCustomizationInfo(
                    getContext(),
                    0,
                    DedicatedAppInfo.getDedicatedApp(getContext(), 0),
                    booleanValue);
        }
        dedicatedAppUpdate(booleanValue);
        pttUIUpdate(booleanValue);
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
            getPreferenceScreen().addPreference(this.mXcoverKeyDedicatedAppSwitch);
            boolean z =
                    Settings.System.getInt(
                                    getActivity().getContentResolver(),
                                    "dedicated_app_xcover_switch",
                                    0)
                            != 0;
            this.mXcoverKeyDedicatedAppSwitch.setChecked(z);
            dedicatedAppUpdate(z);
            this.mPreviewCategory.setHeight(
                    (int)
                            getContext()
                                    .getResources()
                                    .getDimension(R.dimen.sec_widget_inset_category_height));
            this.mPreviewCategory.seslSetSubheaderRoundedBackground(12);
            pttUIUpdate(z);
        } else {
            getPreferenceScreen().removePreference(this.mXcoverKeyDedicatedAppSwitch);
            this.mPreviewCategory.setHeight(1);
            this.mPreviewCategory.seslSetSubheaderRoundedBackground(12);
            pttUIUpdate(false);
            if (DedicatedAppInfo.getDedicatedAppSwitch(getContext(), 0)
                    || DedicatedAppInfo.getDedicatedAppState(getContext(), 0)) {
                DedicatedAppInfo.setDedicatedAppSwitch(getContext(), 0, false);
                DedicatedAppInfo.saveDedicatedApp(getContext(), 0, ApnSettings.MVNO_NONE);
                DedicatedAppInfo.saveDedicatedAppLabel(getContext(), 0, ApnSettings.MVNO_NONE);
            }
        }
        TextView textView =
                (TextView)
                        this.mPreviewPreference.mRootView.findViewById(R.id.active_key_main_text);
        UsefulfeatureUtils.hasActiveKey();
        textView.setText(
                getString(R.string.active_key_summary)
                        + " "
                        + getString(R.string.capture_by_active_key_summary));
        LinearLayout linearLayout =
                (LinearLayout)
                        this.mPreviewPreference.mRootView.findViewById(
                                R.id.active_key_image_container);
        linearLayout.semSetRoundedCorners(15);
        linearLayout.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        ImageView imageView =
                (ImageView) this.mPreviewPreference.mRootView.findViewById(R.id.active_key_image);
        UsefulfeatureUtils.hasActiveKey();
        if (Utils.isTablet()) {
            imageView.setImageDrawable(
                    getResources()
                            .getDrawable(
                                    R.drawable.sec_setting_activekey_image_tablet,
                                    getActivity().getTheme()));
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.settings.ACTIVE_KEY_CLEAR");
        intent.addFlags(16777216);
        getActivity().sendBroadcast(intent);
        FragmentActivity activity = getActivity();
        ActiveKeyInfo.ActiveKeyAppInfo activeKeyAppInfo =
                ActiveKeyInfo.getActiveKeyAppInfo(activity, 0);
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
                ActiveKeyInfo.getActiveKeyAppInfo(activity, 1);
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
                                getActivity().getContentResolver(), "active_key_on_lockscreen", 0)
                        != 0);
        Log.d("ActiveKeySettings", "onResume() ");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        Log.d("ActiveKeySettings", "onStop() ");
    }

    public final void pttUIUpdate(boolean z) {
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
