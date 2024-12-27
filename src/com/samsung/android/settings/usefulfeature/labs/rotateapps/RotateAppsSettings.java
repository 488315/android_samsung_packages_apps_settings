package com.samsung.android.settings.usefulfeature.labs.rotateapps;

import android.content.Context;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.widget.AppPickerEvent$OnItemClickEventListener;
import androidx.picker.widget.AppPickerState$OnStateChangeListener;
import androidx.picker.widget.SeslAppPickerView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.core.CompatChangeableApps;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RotateAppsSettings extends SettingsPreferenceFragment {
    public SeslAppPickerView mAppPickerView;
    public TextView mDescription;
    public TextView mEmptyViewText;
    public ViewGroup mProgressBar;
    public MenuItem mSearchMenu;
    public SearchView mSearchView;
    public AppInfo mSelectedAppInfo = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LauncherAppListLoader extends AsyncTask {
        public LauncherAppListLoader() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            ArrayList arrayList;
            synchronized (this) {
                arrayList = new ArrayList();
                try {
                    CompatChangeableApps compatChangeableApps =
                            new CompatChangeableApps(UserHandle.getCallingUserId());
                    for (String str : compatChangeableApps.getCompatChangeablePackageNameList()) {
                        if (!compatChangeableApps.isOrientationOverrideDisallowed(str)) {
                            int uid = compatChangeableApps.getUid(str);
                            AppInfo.Companion companion = AppInfo.Companion;
                            AppData.ListSwitchAppDataBuilder listSwitchAppDataBuilder =
                                    new AppData.ListSwitchAppDataBuilder(
                                            AppInfo.Companion.obtain(
                                                    uid, str, ApnSettings.MVNO_NONE));
                            RotateAppsSettings rotateAppsSettings = RotateAppsSettings.this;
                            rotateAppsSettings.getClass();
                            boolean z = true;
                            AppData.ListSwitchAppDataBuilder subLabel =
                                    listSwitchAppDataBuilder.setSubLabel(
                                            rotateAppsSettings
                                                    .getContext()
                                                    .getString(
                                                            UsefulfeatureUtils
                                                                                    .getPolicyFromLegacyFlag(
                                                                                            UsefulfeatureUtils
                                                                                                    .getPackageOrientation(
                                                                                                            str))
                                                                            == 7
                                                                    ? R.string
                                                                            .sec_set_aspect_ratio_for_each_app_full_screen
                                                                    : R.string
                                                                            .sec_set_aspect_ratio_for_each_app_app_default),
                                            true);
                            RotateAppsSettings.this.getClass();
                            int policyFromLegacyFlag =
                                    UsefulfeatureUtils.getPolicyFromLegacyFlag(
                                            UsefulfeatureUtils.getPackageOrientation(str));
                            if (policyFromLegacyFlag != 7 && policyFromLegacyFlag != 31) {
                                z = false;
                            }
                            arrayList.add(subLabel.setSelected(z).build());
                        }
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                SemLog.i(
                        "RotateAppsSettings",
                        "Supported Rotated Apps : Count = " + arrayList.size());
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            RotateAppsSettings.this.mProgressBar.setVisibility(8);
            RotateAppsSettings.this.mAppPickerView.setVisibility(0);
            RotateAppsSettings.this.mAppPickerView.submitList((List) obj);
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            RotateAppsSettings.this.mProgressBar.setVisibility(0);
            RotateAppsSettings.this.mAppPickerView.setVisibility(8);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_rotate_all_apps_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return LabsSettings.class.getName();
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final RecyclerView getListViewWithSpacing() {
        return this.mAppPickerView;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 68210;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getContext();
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getContext();
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.sec_labs_applist_menu, menu);
        ((Toolbar) getActivity().findViewById(R.id.action_bar))
                .setBackInvokedCallbackEnabled(false);
        getActivity()
                .getOnBackInvokedDispatcher()
                .registerSystemOnBackInvokedCallback(
                        new OnBackInvokedCallback() { // from class:
                                                      // com.samsung.android.settings.usefulfeature.labs.rotateapps.RotateAppsSettings.3
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                SearchView searchView = RotateAppsSettings.this.mSearchView;
                                if (searchView == null || !searchView.hasFocus()) {
                                    RotateAppsSettings.this.getActivity().finish();
                                    return;
                                }
                                MenuItem menuItem = RotateAppsSettings.this.mSearchMenu;
                                if (menuItem != null) {
                                    menuItem.collapseActionView();
                                }
                            }
                        });
        MenuItem findItem = menu.findItem(R.id.search_apps);
        this.mSearchMenu = findItem;
        SearchView searchView = (SearchView) findItem.getActionView();
        this.mSearchView = searchView;
        LinearLayout linearLayout = (LinearLayout) searchView.findViewById(R.id.search_plate);
        if (linearLayout != null) {
            linearLayout.setPadding(
                    0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
        }
        this.mSearchView.setQueryHint(getString(R.string.sec_app_search_title));
        this.mSearchView.mOnQueryChangeListener = new AnonymousClass1();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.sec_labs_apppicker_view_layout, (ViewGroup) null);
        SeslAppPickerView seslAppPickerView =
                (SeslAppPickerView) inflate.findViewById(R.id.sec_labs_app_picker_view);
        this.mAppPickerView = seslAppPickerView;
        seslAppPickerView.setVisibility(8);
        this.mAppPickerView.setAppListOrder(1);
        this.mEmptyViewText = (TextView) inflate.findViewById(android.R.id.empty);
        this.mAppPickerView.setOnItemClickEventListener(new AnonymousClass1());
        this.mAppPickerView.mOnStateChangeListener = new AnonymousClass1();
        this.mProgressBar = (ViewGroup) inflate.findViewById(R.id.loading_panel);
        TextView textView = (TextView) inflate.findViewById(R.id.description_summary);
        this.mDescription = textView;
        StringBuilder sb = new StringBuilder();
        if (!Utils.isTablet()) {
            sb.append(getContext().getString(R.string.sec_rotate_all_apps_description));
            sb.append("\n\n");
        }
        sb.append(getContext().getString(R.string.sec_rotate_all_apps_description_2));
        textView.setText(sb.toString());
        new LauncherAppListLoader()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
        return inflate;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mProgressBar.getVisibility() == 0) {
            this.mProgressBar.setVisibility(8);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getContext();
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        AppInfo appInfo = this.mSelectedAppInfo;
        if (appInfo != null) {
            AppData appData = this.mAppPickerView.getAppData(appInfo);
            if (appData instanceof AppInfoData) {
                String str2 = this.mSelectedAppInfo.packageName;
                SeslAppPickerView seslAppPickerView = this.mAppPickerView;
                AppData.ListSwitchAppDataBuilder listSwitchAppDataBuilder =
                        new AppData.ListSwitchAppDataBuilder((AppInfoData) appData);
                boolean z2 = true;
                AppData.ListSwitchAppDataBuilder subLabel =
                        listSwitchAppDataBuilder.setSubLabel(
                                getContext()
                                        .getString(
                                                UsefulfeatureUtils.getPolicyFromLegacyFlag(
                                                                        UsefulfeatureUtils
                                                                                .getPackageOrientation(
                                                                                        str2))
                                                                == 7
                                                        ? R.string
                                                                .sec_set_aspect_ratio_for_each_app_full_screen
                                                        : R.string
                                                                .sec_set_aspect_ratio_for_each_app_app_default),
                                true);
                int policyFromLegacyFlag =
                        UsefulfeatureUtils.getPolicyFromLegacyFlag(
                                UsefulfeatureUtils.getPackageOrientation(str2));
                if (policyFromLegacyFlag != 7 && policyFromLegacyFlag != 31) {
                    z2 = false;
                }
                seslAppPickerView.updateItem(subLabel.setSelected(z2).build());
            }
            this.mSelectedAppInfo = null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.rotateapps.RotateAppsSettings$1, reason: invalid class name */
    public final class AnonymousClass1
            implements AppPickerEvent$OnItemClickEventListener,
                    AppPickerState$OnStateChangeListener,
                    SearchView.OnQueryTextListener {
        public /* synthetic */ AnonymousClass1() {}

        @Override // androidx.picker.widget.AppPickerEvent$OnItemClickEventListener
        public boolean onClick(View view, AppInfo appInfo) {
            Bundle bundle = new Bundle();
            bundle.putString("packageName", appInfo.packageName);
            RotateAppsSettings rotateAppsSettings = RotateAppsSettings.this;
            SubSettingLauncher subSettingLauncher =
                    new SubSettingLauncher(rotateAppsSettings.getContext());
            String name = RotateAppDetailSettings.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            subSettingLauncher.setTitleRes(R.string.sec_rotate_all_apps_title, null);
            launchRequest.mArguments = bundle;
            launchRequest.mSourceMetricsCategory = 68210;
            subSettingLauncher.launch();
            rotateAppsSettings.mSelectedAppInfo = appInfo;
            return true;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            RotateAppsSettings rotateAppsSettings = RotateAppsSettings.this;
            SeslAppPickerView seslAppPickerView = rotateAppsSettings.mAppPickerView;
            if (seslAppPickerView == null
                    || seslAppPickerView.getAdapter() == null
                    || rotateAppsSettings.mDescription == null) {
                return true;
            }
            rotateAppsSettings.mAppPickerView.setSearchFilter(
                    str,
                    new SeslAppPickerView
                            .OnSearchFilterListener() { // from class:
                                                        // com.samsung.android.settings.usefulfeature.labs.rotateapps.RotateAppsSettings$4$$ExternalSyntheticLambda0
                        @Override // androidx.picker.widget.SeslAppPickerView.OnSearchFilterListener
                        public final void onSearchFilterCompleted(int i) {
                            RotateAppsSettings rotateAppsSettings2 = RotateAppsSettings.this;
                            TextView textView = rotateAppsSettings2.mEmptyViewText;
                            if (textView != null) {
                                textView.setVisibility(i == 0 ? 0 : 8);
                                rotateAppsSettings2.mAppPickerView.setVisibility(i != 0 ? 0 : 8);
                                rotateAppsSettings2.mDescription.setVisibility(i != 0 ? 0 : 8);
                            }
                        }
                    });
            return true;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public void onQueryTextSubmit(String str) {
            RotateAppsSettings.this.mAppPickerView.setSearchFilter(str, null);
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateChanged(AppInfo appInfo, boolean z) {
            String str = appInfo.packageName;
            int switchOrientationPolicyFlag =
                    UsefulfeatureUtils.setSwitchOrientationPolicyFlag(
                            UsefulfeatureUtils.getPolicyFromLegacyFlag(
                                    UsefulfeatureUtils.getPackageOrientation(str)),
                            z);
            UsefulfeatureUtils.setPackageOrientation(switchOrientationPolicyFlag, str);
            if (str.equalsIgnoreCase("com.android.samsung.utilityapp")) {
                UsefulfeatureUtils.setPackageOrientation(
                        switchOrientationPolicyFlag, "com.android.samsung.batteryusage");
                UsefulfeatureUtils.setPackageOrientation(
                        switchOrientationPolicyFlag, "com.samsung.android.statsd");
                UsefulfeatureUtils.setPackageOrientation(
                        switchOrientationPolicyFlag, "com.samsung.android.appbooster");
                UsefulfeatureUtils.setPackageOrientation(
                        switchOrientationPolicyFlag, "com.samsung.android.thermalguardian");
                UsefulfeatureUtils.setPackageOrientation(
                        switchOrientationPolicyFlag, "com.samsung.android.memoryguardian");
                UsefulfeatureUtils.setPackageOrientation(
                        switchOrientationPolicyFlag, "com.samsung.android.mediaguardian");
            }
            RotateAppsSettings.this.getClass();
            LoggingHelper.insertEventLogging(68210, 68211, z ? 1L : 0L, str);
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateAllChanged(boolean z) {}
    }
}
