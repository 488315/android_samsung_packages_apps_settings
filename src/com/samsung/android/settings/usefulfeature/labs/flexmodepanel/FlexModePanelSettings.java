package com.samsung.android.settings.usefulfeature.labs.flexmodepanel;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.widget.CompoundButton;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.utils.applications.AppUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.usefulfeature.Usefulfeature;
import com.samsung.android.settings.widget.SecHelpAnimationLayoutPreference;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.samsung.android.util.SemLog;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FlexModePanelSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static boolean mEnableSwitch = false;
    public LauncherApps mLauncherApps;
    public SecPreference mPreference;
    public SecRelativeLinkView mRelativeLinkView;
    public SecDropDownPreference mScrollWheelPreference;
    public SemWindowManager mSemWindowManager;
    public SettingsMainSwitchBar mSwitchBar;
    public final AnonymousClass1 mTalkbackObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    FlexModePanelSettings flexModePanelSettings = FlexModePanelSettings.this;
                    boolean z2 = FlexModePanelSettings.mEnableSwitch;
                    flexModePanelSettings.updateSwitchAndPreferenceState$3();
                }
            };
    public SecPreference mTouchpadHelpPreference;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass3();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            String valueOf = String.valueOf(68518);
            String str =
                    FlexModePanelSettings.mEnableSwitch ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = FlexModePanelSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_flex_mode_panel_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            return false;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_flex_mode_panel_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return Usefulfeature.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 68500;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_flex_mode_panel_settings;
    }

    public final String getSummary$1$1() {
        ArrayList arrayList = new ArrayList();
        List<LauncherActivityInfo> activityList =
                this.mLauncherApps.getActivityList(
                        null, new UserHandle(UserHandle.getCallingUserId()));
        SemLog.i("FlexModePanelSettings", "LauncherActivityInfo.size : " + activityList.size());
        Iterator<LauncherActivityInfo> it = activityList.iterator();
        while (it.hasNext()) {
            ApplicationInfo applicationInfo = it.next().getApplicationInfo();
            if ((this.mSemWindowManager.getSupportsFlexPanel(
                                    UserHandle.getCallingUserId(), applicationInfo.packageName)
                            & 1)
                    == 1) {
                arrayList.add(applicationInfo.packageName);
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            return getContext()
                    .getResources()
                    .getString(
                            R.string.sec_flex_mode_panel_auto_show_panel_when_folded_summary_none);
        }
        int i = 0;
        if (size >= 4) {
            String[] strArr = new String[2];
            Iterator it2 = arrayList.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                strArr[i2] =
                        AppUtils.getApplicationLabel(
                                        getContext().getPackageManager(), (String) it2.next())
                                .toString();
                if (i2 >= 1) {
                    break;
                }
                i2++;
            }
            return getContext()
                    .getResources()
                    .getQuantityString(
                            R.plurals.sec_flex_mode_panel_auto_show_panel_when_folded_summary_more,
                            size,
                            strArr[0],
                            strArr[1],
                            Integer.valueOf(size - 2));
        }
        Iterator it3 = arrayList.iterator();
        String str = ApnSettings.MVNO_NONE;
        while (it3.hasNext()) {
            String str2 = (String) it3.next();
            StringBuilder m =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
            m.append((Object) AppUtils.getApplicationLabel(getContext().getPackageManager(), str2));
            str = m.toString();
            if (i < size - 1) {
                str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ", ");
            }
            i++;
        }
        return str;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    public final void initPreference$13() {
        setAutoRemoveInsetCategory(false);
        SecHelpAnimationLayoutPreference secHelpAnimationLayoutPreference =
                (SecHelpAnimationLayoutPreference) findPreference("flex_mode_panel_preview");
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        secHelpAnimationLayoutPreference.setDescText(
                getContext().getString(R.string.sec_flex_mode_panel_description));
        Utils.isNightMode(getContext());
        SecPreference secPreference =
                (SecPreference)
                        findPreference("flex_mode_apps_to_show_flex_mode_panel_automatically");
        this.mPreference = secPreference;
        secPreference.setSummary(getSummary$1$1());
        SecPreference secPreference2 = this.mPreference;
        secPreference2.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference2, true);
        this.mTouchpadHelpPreference =
                (SecPreference) findPreference("flex_mode_touchpad_gesture_help");
        if (CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL) {
            this.mScrollWheelPreference =
                    (SecDropDownPreference) findPreference("flex_mode_scroll_wheel_pos");
            int i = Settings.Global.getInt(getContentResolver(), "flex_mode_scroll_wheel_pos", 2);
            String str2 =
                    i == -1
                            ? getContext().getResources().getStringArray(R.array.flex_entries)[2]
                            : getContext().getResources().getStringArray(R.array.flex_entries)[i];
            this.mScrollWheelPreference.setSummary(str2);
            this.mScrollWheelPreference.setValue(str2);
            SecDropDownPreference secDropDownPreference = this.mScrollWheelPreference;
            secDropDownPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secDropDownPreference, true);
            this.mScrollWheelPreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelSettings$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            FlexModePanelSettings flexModePanelSettings =
                                    FlexModePanelSettings.this;
                            int findIndexOfValue =
                                    flexModePanelSettings.mScrollWheelPreference.findIndexOfValue(
                                            obj.toString());
                            String obj2 = obj.toString();
                            flexModePanelSettings.mScrollWheelPreference.setValue(obj2);
                            preference.setSummary(obj2);
                            Settings.Global.putInt(
                                    preference.getContext().getContentResolver(),
                                    "flex_mode_scroll_wheel_pos",
                                    findIndexOfValue);
                            return false;
                        }
                    });
        }
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
        SecDropDownPreference secDropDownPreference;
        Settings.Global.putInt(
                getContext().getContentResolver(), "flex_mode_panel_enabled", z ? 1 : 0);
        mEnableSwitch = z;
        SecPreference secPreference = this.mPreference;
        if (secPreference != null) {
            secPreference.setEnabled(z);
            LoggingHelper.insertEventLogging(String.valueOf(68500), 68519);
        }
        SecPreference secPreference2 = this.mTouchpadHelpPreference;
        if (secPreference2 != null) {
            secPreference2.setEnabled(z);
            LoggingHelper.insertEventLogging(String.valueOf(68500), 68526);
        }
        if (!CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL
                || (secDropDownPreference = this.mScrollWheelPreference) == null) {
            return;
        }
        secDropDownPreference.setEnabled(z);
        if (z
                && Settings.Global.getInt(getContentResolver(), "flex_mode_scroll_wheel_pos", 2)
                        == -1) {
            Settings.Global.putInt(
                    this.mScrollWheelPreference.getContext().getContentResolver(),
                    "flex_mode_scroll_wheel_pos",
                    2);
        }
        LoggingHelper.insertEventLogging(String.valueOf(68500), 68525);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getPreferenceScreen().removeAll();
        addPreferencesFromResource(R.xml.sec_flex_mode_panel_settings);
        initPreference$13();
        updateSwitchAndPreferenceState$3();
        setLinkedDataView$14();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSemWindowManager = SemWindowManager.getInstance();
        this.mLauncherApps = (LauncherApps) getContext().getSystemService("launcherapps");
        initPreference$13();
        setLinkedDataView$14();
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("enabled_accessibility_services"),
                        false,
                        this.mTalkbackObserver);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
        getContentResolver().unregisterContentObserver(this.mTalkbackObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateSwitchAndPreferenceState$3();
        this.mPreference.setSummary(getSummary$1$1());
    }

    public final void setLinkedDataView$14() {
        if (this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData.flowId = "770100";
            settingsPreferenceFragmentLinkData.callerMetric = 68500;
            settingsPreferenceFragmentLinkData.titleRes = R.string.pointer_settings_category;
            settingsPreferenceFragmentLinkData.intent =
                    DisplaySettings$$ExternalSyntheticOutline0.m(
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                            "com.android.settings.Settings$MouseAndTrackpadSettingsActivity");
            this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
            LoggingHelper.insertEventLogging(68500, 68517);
        }
        this.mRelativeLinkView.create(this);
    }

    public final void updateSwitchAndPreferenceState$3() {
        boolean z = Settings.Global.getInt(getContentResolver(), "flex_mode_panel_enabled", 0) != 0;
        mEnableSwitch = z;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(z);
            this.mSwitchBar.show();
        }
        SecPreference secPreference = this.mPreference;
        if (secPreference != null) {
            secPreference.setEnabled(mEnableSwitch);
        }
        SecPreference secPreference2 = this.mTouchpadHelpPreference;
        if (secPreference2 != null) {
            secPreference2.setEnabled(mEnableSwitch);
        }
        if (!CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL || this.mScrollWheelPreference == null) {
            return;
        }
        if (Utils.isTalkBackEnabled(getContext())) {
            this.mScrollWheelPreference.setVisible(false);
        } else {
            this.mScrollWheelPreference.setVisible(true);
            this.mScrollWheelPreference.setEnabled(mEnableSwitch);
        }
    }
}
