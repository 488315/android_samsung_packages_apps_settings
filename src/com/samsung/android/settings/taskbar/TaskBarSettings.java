package com.samsung.android.settings.taskbar;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.DisplaySettings;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.SecDisplayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TaskBarSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
    public final Uri TASK_BAR_REFRESH_URI = Settings.Global.getUriFor("task_bar_type");
    public final AnonymousClass1 mRefreshObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.taskbar.TaskBarSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    Log.i("TaskBarSettings", "onChange: refresh taskbar style");
                    TaskBarSettings taskBarSettings = TaskBarSettings.this;
                    taskBarSettings.updateSummaryForTaskbarStyle(
                            taskBarSettings.taskbarStylePreference);
                }
            };
    public SettingsMainSwitchBar mTaskBarSwitch;
    public SecSwitchPreferenceScreen recentAppSwitch;
    public Preference taskbarStylePreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.taskbar.TaskBarSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!Rune.supportTaskBar(context)
                    || Rune.isSamsungDexMode(context)
                    || Utils.getEnterprisePolicyEnabled(
                                    context,
                                    "content://com.sec.knox.provider2/KioskMode",
                                    "isNavigationBarHidden")
                            == 1
                    || TaskBarUtils.isSmartViewEnabled(context)
                    || !TaskBarUtils.isSamsungLauncher(context)
                    || Settings.System.getInt(context.getContentResolver(), "easy_mode_switch", 1)
                            == 0
                    || TaskBarUtils.isFrontDisplay(context)
                    || (Rune.supportNavigationBarForHardKey()
                            && context.getResources()
                                            .getInteger(R.integer.config_pinnerWebviewPinBytes)
                                    == 0)) {
                ArrayList arrayList = (ArrayList) nonIndexableKeys;
                arrayList.add("recent_app_switch");
                arrayList.add("task_bar_setting");
                arrayList.add("pref_taskbar_style");
            }
            if (Rune.SUPPORT_FLOATING_TASKBAR
                    && Settings.Global.getInt(
                                    context.getContentResolver(),
                                    "navigation_bar_gesture_while_hidden",
                                    0)
                            <= 0) {
                ((ArrayList) nonIndexableKeys).add("pref_taskbar_style");
            }
            ((ArrayList) nonIndexableKeys).add("task_bar_setting");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = com.android.settings.R.xml.sec_task_bar_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    public final boolean isEnabled$6() {
        return Settings.Global.getInt(
                        getContentResolver(), TaskBarPreferenceController.KEY_TASK_BAR_SETTING, 1)
                == 1;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mTaskBarSwitch = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(isEnabled$6());
            this.mTaskBarSwitch.addOnSwitchChangeListener(this);
            this.mTaskBarSwitch.show();
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Log.i("TaskBarSettings", "TaskBar Switch is clicked to -> " + z);
        Settings.Global.putInt(
                getContentResolver(), TaskBarPreferenceController.KEY_TASK_BAR_SETTING, z ? 1 : 0);
        updateSwitchPreference(z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (TaskBarUtils.isFrontDisplay(getContext())
                || TaskBarUtils.isSmartViewEnabled(getContext())) {
            finish();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        LinearLayout linearLayout;
        final int i = 1;
        final int i2 = 0;
        super.onCreate(bundle);
        addPreferencesFromResource(com.android.settings.R.xml.sec_task_bar_settings);
        if (getIntent() != null && getIntent().getBooleanExtra("from_tips", false)) {
            String stringExtra = getActivity().getIntent().getStringExtra("recent_app_switch");
            if (getListView() != null) {
                final RecyclerView listView = getListView();
                final int preferenceAdapterPosition =
                        ((PreferenceGroupAdapter) listView.getAdapter())
                                .getPreferenceAdapterPosition(stringExtra);
                if (preferenceAdapterPosition >= 0) {
                    listView.scrollToPosition(preferenceAdapterPosition);
                    listView.postDelayed(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.taskbar.TaskBarSettings$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    View view;
                                    TaskBarSettings taskBarSettings = TaskBarSettings.this;
                                    RecyclerView recyclerView = listView;
                                    int i3 = preferenceAdapterPosition;
                                    BaseSearchIndexProvider baseSearchIndexProvider =
                                            TaskBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                                    taskBarSettings.getClass();
                                    RecyclerView.ViewHolder findViewHolderForAdapterPosition =
                                            recyclerView.findViewHolderForAdapterPosition(i3);
                                    if (findViewHolderForAdapterPosition == null
                                            || (view = findViewHolderForAdapterPosition.itemView)
                                                    == null) {
                                        return;
                                    }
                                    if (view.getBackground() != null) {
                                        view.getBackground()
                                                .setHotspot(
                                                        view.getWidth() / 2, view.getHeight() / 2);
                                    }
                                    view.setPressed(true);
                                    view.setPressed(false);
                                }
                            },
                            600L);
                }
            }
        }
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("task_bar_help_preview");
        if (layoutPreference != null
                && (linearLayout =
                                (LinearLayout)
                                        layoutPreference.mRootView.findViewById(
                                                com.android.settings.R.id.help_vi_container))
                        != null) {
            linearLayout.semSetRoundedCorners(15);
            linearLayout.semSetRoundedCornerColor(
                    15,
                    getContext()
                            .getResources()
                            .getColor(com.android.settings.R.color.sec_widget_round_and_bgcolor));
            LottieAnimationView lottieAnimationView =
                    (LottieAnimationView)
                            linearLayout.findViewById(com.android.settings.R.id.task_bar_help_vi);
            if (Utils.isTablet()) {
                if ((getContext().getResources().getConfiguration().uiMode & 48) == 32) {
                    lottieAnimationView.setAnimation("Taskbar_settings_tips_Tablet_dark.json");
                } else {
                    lottieAnimationView.setAnimation("Taskbar_settings_tips_Tablet_light.json");
                }
            } else if ((getContext().getResources().getConfiguration().uiMode & 48) == 32) {
                lottieAnimationView.setAnimation("Taskbar_settings_tips_Fold_dark.json");
            }
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) findPreference("recent_app_switch");
        this.recentAppSwitch = secSwitchPreferenceScreen;
        if (secSwitchPreferenceScreen != null) {
            secSwitchPreferenceScreen.setEnabled(
                    Settings.Global.getInt(getContentResolver(), "taskbar_recent_apps_enabled", 1)
                            == 1);
            this.recentAppSwitch.setChecked(isEnabled$6());
            updateRecentAppsPreferenceSummary(this.recentAppSwitch);
            this.recentAppSwitch.setOnPreferenceClickListener(
                    new Preference.OnPreferenceClickListener(this) { // from class:
                        // com.samsung.android.settings.taskbar.TaskBarSettings$$ExternalSyntheticLambda0
                        public final /* synthetic */ TaskBarSettings f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            TaskBarSettings taskBarSettings = this.f$0;
                            switch (i2) {
                                case 0:
                                    BaseSearchIndexProvider baseSearchIndexProvider =
                                            TaskBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                                    taskBarSettings.getClass();
                                    Intent intent = new Intent();
                                    intent.setClassName(
                                            taskBarSettings.getActivity(),
                                            "com.android.settings.Settings$TaskBarRecentAppsActivity");
                                    Log.d(
                                            "TaskBarSettings",
                                            "Activity= " + taskBarSettings.getActivity());
                                    taskBarSettings.getActivity().startActivity(intent);
                                    break;
                                default:
                                    BaseSearchIndexProvider baseSearchIndexProvider2 =
                                            TaskBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                                    taskBarSettings.getClass();
                                    Intent intent2 = new Intent();
                                    intent2.setClassName(
                                            taskBarSettings.getActivity(),
                                            "com.android.settings.Settings$TaskbarStyleSettingsActivity");
                                    Log.d(
                                            "TaskBarSettings",
                                            "Activity= " + taskBarSettings.getActivity());
                                    taskBarSettings.getActivity().startActivity(intent2);
                                    break;
                            }
                            return true;
                        }
                    });
            this.recentAppSwitch.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.samsung.android.settings.taskbar.TaskBarSettings$$ExternalSyntheticLambda1
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    TaskBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                            TaskBarSettings taskBarSettings = TaskBarSettings.this;
                            taskBarSettings.getClass();
                            boolean booleanValue = ((Boolean) obj).booleanValue();
                            Log.i(
                                    "TaskBarSettings",
                                    "Recent app Switch is clicked to -> " + booleanValue);
                            Settings.Global.putInt(
                                    taskBarSettings.getContentResolver(),
                                    "taskbar_recent_apps_enabled",
                                    booleanValue ? 1 : 0);
                            taskBarSettings.updateRecentAppsPreferenceSummary(
                                    taskBarSettings.recentAppSwitch);
                            return true;
                        }
                    });
        }
        Context context = getContext();
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        int integer = context.getResources().getInteger(R.integer.config_pinnerWebviewPinBytes);
        if ((integer == 2 || integer == 3) && Rune.SUPPORT_FLOATING_TASKBAR) {
            Preference findPreference = findPreference("pref_taskbar_style");
            this.taskbarStylePreference = findPreference;
            if (findPreference != null) {
                findPreference.setVisible(true);
                this.taskbarStylePreference.setOnPreferenceClickListener(
                        new Preference.OnPreferenceClickListener(this) { // from class:
                            // com.samsung.android.settings.taskbar.TaskBarSettings$$ExternalSyntheticLambda0
                            public final /* synthetic */ TaskBarSettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference) {
                                TaskBarSettings taskBarSettings = this.f$0;
                                switch (i) {
                                    case 0:
                                        BaseSearchIndexProvider baseSearchIndexProvider =
                                                TaskBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                                        taskBarSettings.getClass();
                                        Intent intent = new Intent();
                                        intent.setClassName(
                                                taskBarSettings.getActivity(),
                                                "com.android.settings.Settings$TaskBarRecentAppsActivity");
                                        Log.d(
                                                "TaskBarSettings",
                                                "Activity= " + taskBarSettings.getActivity());
                                        taskBarSettings.getActivity().startActivity(intent);
                                        break;
                                    default:
                                        BaseSearchIndexProvider baseSearchIndexProvider2 =
                                                TaskBarSettings.SEARCH_INDEX_DATA_PROVIDER;
                                        taskBarSettings.getClass();
                                        Intent intent2 = new Intent();
                                        intent2.setClassName(
                                                taskBarSettings.getActivity(),
                                                "com.android.settings.Settings$TaskbarStyleSettingsActivity");
                                        Log.d(
                                                "TaskBarSettings",
                                                "Activity= " + taskBarSettings.getActivity());
                                        taskBarSettings.getActivity().startActivity(intent2);
                                        break;
                                }
                                return true;
                            }
                        });
            }
            updateSummaryForTaskbarStyle(this.taskbarStylePreference);
        }
        updateSwitchPreference(isEnabled$6());
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mTaskBarSwitch;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mTaskBarSwitch.hide();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        updateSwitchPreference(isEnabled$6());
        super.onResume();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getContext()
                .getContentResolver()
                .registerContentObserver(this.TASK_BAR_REFRESH_URI, false, this.mRefreshObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getContext().getContentResolver().unregisterContentObserver(this.mRefreshObserver);
    }

    public final void updateRecentAppsPreferenceSummary(
            SecSwitchPreferenceScreen secSwitchPreferenceScreen) {
        if (secSwitchPreferenceScreen != null) {
            if (Settings.Global.getInt(getContentResolver(), "taskbar_recent_apps_enabled", 1)
                    != 1) {
                secSwitchPreferenceScreen.setSummary(ApnSettings.MVNO_NONE);
                return;
            }
            int i = Settings.Global.getInt(getContentResolver(), "taskbar_max_recent_count", 2);
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "appCOuntvalue= ", "TaskBarSettings");
            if (i == 0) {
                secSwitchPreferenceScreen.setSummary(
                        com.android.settings.R.string.taskbar_max_recent_count_zero);
            } else if (i == 2) {
                secSwitchPreferenceScreen.setSummary(
                        getContext()
                                .getResources()
                                .getQuantityString(
                                        com.android.settings.R.plurals.number_of_recent_apps,
                                        2,
                                        2));
            } else if (i == 3) {
                secSwitchPreferenceScreen.setSummary(
                        getContext()
                                .getResources()
                                .getQuantityString(
                                        com.android.settings.R.plurals.number_of_recent_apps,
                                        2,
                                        3));
            } else if (i == 4) {
                secSwitchPreferenceScreen.setSummary(
                        getContext()
                                .getResources()
                                .getQuantityString(
                                        com.android.settings.R.plurals.number_of_recent_apps,
                                        2,
                                        4));
            }
            SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, true);
        }
    }

    public final void updateSummaryForTaskbarStyle(Preference preference) {
        if (preference != null) {
            if (Settings.Global.getInt(getContentResolver(), "task_bar_type", 0) == 0) {
                preference.setSummary(com.android.settings.R.string.floating_taskbar_text);
            } else {
                preference.setSummary(com.android.settings.R.string.fixed_taskbar_text);
            }
            SecPreferenceUtils.applySummaryColor(preference, true);
        }
    }

    public final void updateSwitchPreference(boolean z) {
        Log.d("TaskBarSettings", "Updating Switches :");
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) findPreference("recent_app_switch");
        if (secSwitchPreferenceScreen != null) {
            secSwitchPreferenceScreen.setEnabled(z);
            updateRecentAppsPreferenceSummary(secSwitchPreferenceScreen);
            secSwitchPreferenceScreen.setChecked(
                    Settings.Global.getInt(getContentResolver(), "taskbar_recent_apps_enabled", 1)
                            == 1);
        }
        Context context = getContext();
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        int integer = context.getResources().getInteger(R.integer.config_pinnerWebviewPinBytes);
        if (integer == 2 || integer == 3) {
            Preference findPreference = findPreference("pref_taskbar_style");
            findPreference.setEnabled(z);
            updateSummaryForTaskbarStyle(findPreference);
        }
    }
}
