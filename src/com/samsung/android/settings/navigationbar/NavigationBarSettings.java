package com.samsung.android.settings.navigationbar;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.preference.Preference;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.DisplaySettings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.base.widget.InlineCuePreference;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.settings.widget.SecRelativeLinkView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass3();
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass4();
    public SecSwitchPreference mCircleToSearch;
    public Context mContext;
    public SecPreferenceScreen mDetailedSettingPreference;
    public NavigationBarGesturePreference mGesturePreference;
    public Configuration mLastConfiguration;
    public SecRelativeLinkView mRelativeLinkView;
    public SecSwitchPreference mSwipeToOpenAssistantApp;
    public final AnonymousClass1 mOneHandModeObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.navigationbar.NavigationBarSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    NavigationBarSettings navigationBarSettings = NavigationBarSettings.this;
                    SecSwitchPreference secSwitchPreference = navigationBarSettings.mCircleToSearch;
                    if (secSwitchPreference != null) {
                        Context context = navigationBarSettings.mContext;
                        boolean z2 = false;
                        if (context != null
                                && Settings.System.getInt(
                                                context.getContentResolver(),
                                                "any_screen_running",
                                                0)
                                        == 1) {
                            z2 = true;
                        }
                        secSwitchPreference.setEnabled(!z2);
                    }
                }
            };
    public final IntentFilter mIntentFilter =
            new IntentFilter("android.intent.action.OVERLAY_CHANGED");
    public final AnonymousClass2 mOverlayChangedReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.navigationbar.NavigationBarSettings.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    NavigationBarSettingsUtil.sIsForceUpdate = true;
                    NavigationBarSettings navigationBarSettings = NavigationBarSettings.this;
                    navigationBarSettings.onConfigurationChanged(
                            navigationBarSettings.getContext().getResources().getConfiguration());
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.navigationbar.NavigationBarSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            String stringForUser;
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (NavigationBarSettingsUtil.isSupportTouch2Search()) {
                ((ArrayList) nonIndexableKeys).add("touch_and_hold_to_search");
            } else if (NavigationBarSettingsUtil.isSupportSearcle()) {
                ((ArrayList) nonIndexableKeys).add("touch_and_hold_to_search_chn");
            } else {
                ArrayList arrayList = (ArrayList) nonIndexableKeys;
                arrayList.add("touch_and_hold_to_search");
                arrayList.add("touch_and_hold_to_search_chn");
            }
            if (!(!SemFloatingFeature.getInstance()
                            .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_AI_AGENT"))
                    || (stringForUser =
                                    Settings.Secure.getStringForUser(
                                            context.getContentResolver(),
                                            "assistant",
                                            context.getUserId()))
                            == null
                    || ComponentName.unflattenFromString(stringForUser) == null) {
                ((ArrayList) nonIndexableKeys).add("swipe_to_open_assistant_app");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            if (!Rune.supportNavigationBarForHardKey()) {
                return super.getRawDataToIndex(context);
            }
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            int i =
                    Settings.Global.getInt(
                            context.getContentResolver(), "navigation_bar_gesture_while_hidden", 0);
            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                    NavigationBarSettings.SEARCH_INDEX_DATA_PROVIDER;
            if (i == 1) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).intentTargetPackage =
                        context.getPackageName();
                ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                        NavigationBarGestureDetailedSettings.class.getName();
                searchIndexableRaw.title =
                        String.valueOf(R.string.navigationbar_gesture_more_options);
                searchIndexableRaw.screenTitle =
                        resources.getString(R.string.navigationbar_gesture_category_name);
                ((SearchIndexableData) searchIndexableRaw).key = "gesture_detailed";
                arrayList.add(searchIndexableRaw);
            }
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw2).intentTargetClass =
                    NavigationBarSettings.class.getName();
            searchIndexableRaw2.title = String.valueOf(R.string.navigationbar_assistant_app_title);
            searchIndexableRaw2.screenTitle =
                    resources.getString(R.string.navigationbar_gesture_category_name);
            ((SearchIndexableData) searchIndexableRaw2).key = "swipe_to_open_assistant_app";
            arrayList.add(searchIndexableRaw2);
            if (NavigationBarSettingsUtil.isSupportSearcle()) {
                SearchIndexableRaw searchIndexableRaw3 = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw3).intentTargetClass =
                        NavigationBarSettings.class.getName();
                searchIndexableRaw3.screenTitle =
                        resources.getString(R.string.navigationbar_gesture_category_name);
                searchIndexableRaw3.title =
                        NavigationBarSettingsUtil.isSupportTouch2Search()
                                ? String.valueOf(R.string.navigationbar_touch_to_search_title)
                                : String.valueOf(R.string.navigationbar_circle_to_search_title);
                ((SearchIndexableData) searchIndexableRaw3).key =
                        NavigationBarSettingsUtil.isSupportTouch2Search()
                                ? "touch_and_hold_to_search_chn"
                                : "touch_and_hold_to_search";
                arrayList.add(searchIndexableRaw3);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            if (Rune.supportNavigationBarForHardKey()) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = NavigationBarSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.samsung_navigationbar_settings_new;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Rune.supportNavigationBar() && Rune.isNavigationBarEnabled(context);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.navigationbar.NavigationBarSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            int i =
                    Settings.Global.getInt(
                            context.getContentResolver(), "navigation_bar_gesture_detail_type", 1);
            int i2 = 0;
            if (Settings.Global.getInt(
                            context.getContentResolver(), "navigation_bar_gesture_while_hidden", 0)
                    != 0) {
                i2 = i != 0 ? 2 : 1;
            }
            String valueOf = String.valueOf(74900);
            String valueOf2 = String.valueOf(i2);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            return Arrays.asList(statusData);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return Rune.supportNavigationBarForHardKey()
                ? R.string.navigationbar_gesture_category_name
                : R.string.navigationbar_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7470;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    public final void initPreferences$9() {
        SecSwitchPreference secSwitchPreference;
        addPreferencesFromResource(R.xml.samsung_navigationbar_settings_new);
        if (Rune.supportNavigationBarForHardKey()) {
            getActivity().setTitle(R.string.navigationbar_gesture_category_name);
        }
        this.mGesturePreference =
                (NavigationBarGesturePreference) findPreference("gesture_preview");
        this.mDetailedSettingPreference = (SecPreferenceScreen) findPreference("gesture_detailed");
        this.mSwipeToOpenAssistantApp =
                (SecSwitchPreference) findPreference("swipe_to_open_assistant_app");
        Context context = this.mContext;
        boolean z = false;
        if ((SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0) >= 34
                        || NavigationBarSettingsUtil.isSupportLegacyFeatures(context))
                ? false
                : context.getSharedPreferences("NavigationBarPref", 0)
                        .getBoolean("pref_key_simplified_gesture_inline_cue_disabled", true)) {
            final PreferenceScreen preferenceScreen = getPreferenceScreen();
            final PreferenceCategory preferenceCategory = new PreferenceCategory(getPrefContext());
            preferenceScreen.addPreference(preferenceCategory);
            InlineCuePreference inlineCuePreference =
                    new InlineCuePreference(getPrefContext(), null);
            inlineCuePreference.setSummary(
                    getString(R.string.navigationbar_swipe_from_bottom_no_longer_supported));
            String string = getString(R.string.okay);
            View.OnClickListener onClickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.navigationbar.NavigationBarSettings$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            NavigationBarSettings navigationBarSettings =
                                    NavigationBarSettings.this;
                            PreferenceScreen preferenceScreen2 = preferenceScreen;
                            PreferenceCategory preferenceCategory2 = preferenceCategory;
                            SharedPreferences.Editor edit =
                                    navigationBarSettings
                                            .mContext
                                            .getSharedPreferences("NavigationBarPref", 0)
                                            .edit();
                            edit.putBoolean(
                                    "pref_key_simplified_gesture_inline_cue_disabled", false);
                            edit.apply();
                            preferenceScreen2.removePreference(preferenceCategory2);
                        }
                    };
            inlineCuePreference.firstButtonTitle = string;
            inlineCuePreference.firstButtonClickListener = onClickListener;
            inlineCuePreference.notifyChanged();
            preferenceCategory.addPreference(inlineCuePreference);
            preferenceCategory.addPreference(new SecInsetCategoryPreference(getPrefContext()));
        }
        if (NavigationBarSettingsUtil.isSupportTouch2Search()) {
            secSwitchPreference = (SecSwitchPreference) findPreference("touch_and_hold_to_search");
            this.mCircleToSearch =
                    (SecSwitchPreference) findPreference("touch_and_hold_to_search_chn");
        } else {
            secSwitchPreference =
                    (SecSwitchPreference) findPreference("touch_and_hold_to_search_chn");
            this.mCircleToSearch = (SecSwitchPreference) findPreference("touch_and_hold_to_search");
        }
        if (secSwitchPreference != null) {
            secSwitchPreference.setVisible(false);
        }
        boolean z2 = NavigationBarSettingsUtil.getNavBarMode(this.mContext) == 0;
        boolean z3 =
                !z2
                        && Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "navigation_bar_gesture_detail_type",
                                        1)
                                == 0;
        if (NavigationBarSettingsUtil.isSupportSearcle()) {
            this.mCircleToSearch.setVisible(true);
            SecSwitchPreference secSwitchPreference2 = this.mCircleToSearch;
            Context context2 = this.mContext;
            if (context2 != null
                    && Settings.System.getInt(
                                    context2.getContentResolver(), "any_screen_running", 0)
                            == 1) {
                z = true;
            }
            secSwitchPreference2.setEnabled(!z);
        } else {
            this.mCircleToSearch.setVisible(false);
            if (z2) {
                this.mSwipeToOpenAssistantApp.setSummary(
                        R.string.navigationbar_swipe_to_open_assistant_app_summary_for_button);
            }
        }
        if (z3) {
            this.mSwipeToOpenAssistantApp.setSummary(ApnSettings.MVNO_NONE);
        }
        final int i = 0;
        this.mSwipeToOpenAssistantApp.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.navigationbar.NavigationBarSettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ NavigationBarSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(
                            androidx.preference.Preference preference, Object obj) {
                        NavigationBarSettings navigationBarSettings = this.f$0;
                        switch (i) {
                            case 0:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                        NavigationBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarSettings.getClass();
                                boolean booleanValue = ((Boolean) obj).booleanValue();
                                Settings.Secure.putInt(
                                        navigationBarSettings.mContext.getContentResolver(),
                                        "assist_touch_gesture_enabled",
                                        booleanValue ? 1 : 0);
                                Settings.Secure.putInt(
                                        navigationBarSettings.mContext.getContentResolver(),
                                        "assist_long_press_home_enabled",
                                        booleanValue ? 1 : 0);
                                navigationBarSettings.mSwipeToOpenAssistantApp.setChecked(
                                        booleanValue);
                                LoggingHelper.insertEventLogging(
                                        7470, 749004, booleanValue ? 1L : 0L);
                                break;
                            default:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider2 =
                                        NavigationBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarSettings.getClass();
                                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                                Settings.Secure.putInt(
                                        navigationBarSettings.mContext.getContentResolver(),
                                        "touch_and_hold_to_search",
                                        booleanValue2 ? 1 : 0);
                                navigationBarSettings.mCircleToSearch.setChecked(booleanValue2);
                                LoggingHelper.insertEventLogging(
                                        7470, 749005, booleanValue2 ? 1L : 0L);
                                break;
                        }
                        return false;
                    }
                });
        final int i2 = 1;
        this.mCircleToSearch.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.navigationbar.NavigationBarSettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ NavigationBarSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(
                            androidx.preference.Preference preference, Object obj) {
                        NavigationBarSettings navigationBarSettings = this.f$0;
                        switch (i2) {
                            case 0:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                        NavigationBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarSettings.getClass();
                                boolean booleanValue = ((Boolean) obj).booleanValue();
                                Settings.Secure.putInt(
                                        navigationBarSettings.mContext.getContentResolver(),
                                        "assist_touch_gesture_enabled",
                                        booleanValue ? 1 : 0);
                                Settings.Secure.putInt(
                                        navigationBarSettings.mContext.getContentResolver(),
                                        "assist_long_press_home_enabled",
                                        booleanValue ? 1 : 0);
                                navigationBarSettings.mSwipeToOpenAssistantApp.setChecked(
                                        booleanValue);
                                LoggingHelper.insertEventLogging(
                                        7470, 749004, booleanValue ? 1L : 0L);
                                break;
                            default:
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider2 =
                                        NavigationBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                                navigationBarSettings.getClass();
                                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                                Settings.Secure.putInt(
                                        navigationBarSettings.mContext.getContentResolver(),
                                        "touch_and_hold_to_search",
                                        booleanValue2 ? 1 : 0);
                                navigationBarSettings.mCircleToSearch.setChecked(booleanValue2);
                                LoggingHelper.insertEventLogging(
                                        7470, 749005, booleanValue2 ? 1L : 0L);
                                break;
                        }
                        return false;
                    }
                });
        if (this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
        }
        updateRelativeLinks();
        this.mRelativeLinkView.create(this);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0022, code lost:

       if (r0.semDisplayDeviceType == r4.semDisplayDeviceType) goto L12;
    */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onConfigurationChanged(android.content.res.Configuration r4) {
        /*
            r3 = this;
            super.onConfigurationChanged(r4)
            android.content.res.Configuration r0 = r3.mLastConfiguration
            int r0 = r0.orientation
            int r1 = r4.orientation
            if (r0 == r1) goto L12
            r0 = 0
            r3.setPreferenceScreen(r0)
            r3.initPreferences$9()
        L12:
            boolean r0 = com.samsung.android.settings.navigationbar.NavigationBarSettingsUtil.sIsForceUpdate
            if (r0 != 0) goto L24
            android.content.res.Configuration r0 = r3.mLastConfiguration
            int r1 = r0.orientation
            int r2 = r4.orientation
            if (r1 != r2) goto L24
            int r0 = r0.semDisplayDeviceType
            int r1 = r4.semDisplayDeviceType
            if (r0 == r1) goto L2a
        L24:
            r0 = 0
            com.samsung.android.settings.navigationbar.NavigationBarSettingsUtil.sIsForceUpdate = r0
            r3.updatePreferences$10()
        L2a:
            android.content.res.Configuration r3 = r3.mLastConfiguration
            r3.updateFrom(r4)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.navigationbar.NavigationBarSettings.onConfigurationChanged(android.content.res.Configuration):void");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.secD("NavigationBarSettings", "onCreate()");
        super.onCreate(bundle);
        this.mContext = getActivity().getApplicationContext();
        Configuration configuration = new Configuration();
        this.mLastConfiguration = configuration;
        configuration.updateFrom(this.mContext.getResources().getConfiguration());
        initPreferences$9();
        if (bundle == null || !bundle.getBoolean("isOnehandModeDialogShown")) {
            return;
        }
        new Handler()
                .post(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.navigationbar.NavigationBarSettings$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                NavigationBarSettings.this.mGesturePreference
                                        .showOneHandModeDisablePopup();
                            }
                        });
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(android.preference.Preference preference, Object obj) {
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updatePreferences$10();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mGesturePreference.getClass();
        AlertDialog alertDialog = NavigationBarGesturePreference.sOnehandModeDisableDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        bundle.putBoolean("isOnehandModeDialogShown", true);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("any_screen_running"),
                        false,
                        this.mOneHandModeObserver);
        this.mIntentFilter.addDataScheme("package");
        this.mIntentFilter.addDataSchemeSpecificPart(
                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME, 0);
        this.mContext.registerReceiverAsUser(
                this.mOverlayChangedReceiver, UserHandle.ALL, this.mIntentFilter, null, null);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mContext.getContentResolver().unregisterContentObserver(this.mOneHandModeObserver);
        this.mContext.unregisterReceiver(this.mOverlayChangedReceiver);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updatePreferences$10() {
        String stringForUser;
        if (!Rune.isNavigationBarEnabled(this.mContext)) {
            Log.secD(
                    "NavigationBarSettings",
                    "Navigation bar settings can't open in DeX mode or non-primary user.");
            finish();
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int navBarMode = NavigationBarSettingsUtil.getNavBarMode(this.mContext);
        preferenceScreen.addPreference(this.mGesturePreference);
        if (Rune.supportNavigationBarForHardKey() && navBarMode == 0) {
            preferenceScreen.removePreference(this.mDetailedSettingPreference);
        } else {
            preferenceScreen.addPreference(this.mDetailedSettingPreference);
        }
        preferenceScreen.addPreference(this.mSwipeToOpenAssistantApp);
        preferenceScreen.addPreference(this.mCircleToSearch);
        updateRelativeLinks();
        this.mGesturePreference.updatePreviewResources();
        Context context = getContext();
        if (((!(SemFloatingFeature.getInstance()
                                                .getBoolean(
                                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_AI_AGENT")
                                        ^ true)
                                || (stringForUser =
                                                Settings.Secure.getStringForUser(
                                                        context.getContentResolver(),
                                                        "assistant",
                                                        context.getUserId()))
                                        == null
                                || ComponentName.unflattenFromString(stringForUser) == null)
                        ? false
                        : true)
                == true) {
            this.mSwipeToOpenAssistantApp.setChecked(
                    Settings.Secure.getInt(
                                    this.mContext.getContentResolver(),
                                    "assist_touch_gesture_enabled",
                                    getContext()
                                                    .getResources()
                                                    .getBoolean(
                                                            android.R.bool
                                                                    .config_autoBrightnessResetAmbientLuxAfterWarmUp)
                                            ? 1
                                            : 0)
                            == 1);
            getPreferenceScreen().addPreference(this.mSwipeToOpenAssistantApp);
        } else {
            getPreferenceScreen().removePreference(this.mSwipeToOpenAssistantApp);
        }
        this.mCircleToSearch.setChecked(
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "touch_and_hold_to_search", 1)
                        == 1);
        getPreferenceScreen().addPreference(this.mCircleToSearch);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:14|15|16|(1:18)(1:54)|19|(9:21|(1:23)(1:36)|24|25|(1:29)|30|(1:32)|33|34)|37|38|39|(1:41)(1:51)|42|(1:44)(1:50)|45|(8:48|24|25|(2:27|29)|30|(0)|33|34)) */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0145, code lost:

       android.util.Log.e("NavigationBarSettingsUtil", "Spay is not provisioned.");
    */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x019b A[LOOP:0: B:31:0x0199->B:32:0x019b, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRelativeLinks() {
        /*
            Method dump skipped, instructions count: 422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.navigationbar.NavigationBarSettings.updateRelativeLinks():void");
    }
}
