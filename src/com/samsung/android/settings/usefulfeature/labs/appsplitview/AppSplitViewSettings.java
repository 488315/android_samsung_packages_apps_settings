package com.samsung.android.settings.usefulfeature.labs.appsplitview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.apppickerview.widget.AbsAdapter;
import androidx.apppickerview.widget.AppPickerIconLoader;
import androidx.apppickerview.widget.AppPickerView;
import androidx.apppickerview.widget.CustomListAdapter;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AppSplitViewSettings extends SettingsPreferenceFragment {
    public AppPickerCustomListAdapter mAdapter;
    public AppPickerView mAppPickerView;
    public final Map mAppRows = new HashMap();
    public TextView mEmptyViewText;
    public ViewGroup mProgressBar;
    public MenuItem mSearchMenu;
    public List mSupportEmbedActivityPackages;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppPickerCustomListAdapter extends CustomListAdapter {
        public AppPickerCustomListAdapter(
                Context context, AppPickerIconLoader appPickerIconLoader) {
            super(context, 5, appPickerIconLoader);
        }

        @Override // androidx.apppickerview.widget.ListAdapter,
                  // androidx.recyclerview.widget.RecyclerView.Adapter
        public final AppPickerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i != -10) {
                return super.onCreateViewHolder(viewGroup, i);
            }
            View inflate =
                    LayoutInflater.from(this.mContext)
                            .inflate(R.layout.sec_labs_app_picker_desc_layout, viewGroup, false);
            AppSplitViewSettings appSplitViewSettings = AppSplitViewSettings.this;
            CustomViewHolder customViewHolder = new CustomViewHolder(inflate);
            TextView textView = (TextView) inflate.findViewById(R.id.description_summary);
            String string =
                    appSplitViewSettings
                            .getContext()
                            .getString(R.string.sec_app_split_view_description);
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            string, "\n\n");
            m.append(
                    appSplitViewSettings
                            .getContext()
                            .getString(R.string.sec_app_split_view_description_2));
            textView.setText(m.toString());
            return customViewHolder;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppRow {
        public String mPackage;
        public int mState;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomViewHolder extends AppPickerView.CustomViewItemViewHolder {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LauncherAppListLoader extends AsyncTask {
        public LauncherAppListLoader() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            HashMap hashMap;
            synchronized (this) {
                hashMap = new HashMap();
                try {
                    for (ApplicationInfo applicationInfo :
                            UsefulfeatureUtils.getSplitActivityApplications(
                                    AppSplitViewSettings.this.getContext().getPackageManager())) {
                        if (!hashMap.containsKey(applicationInfo.packageName)) {
                            hashMap.put(applicationInfo.packageName, makeAppRow(applicationInfo));
                        }
                    }
                } catch (Exception e) {
                    Log.e("AppSplitViewSettings", e.getMessage());
                }
            }
            return hashMap;
        }

        public final AppRow makeAppRow(ApplicationInfo applicationInfo) {
            AppRow appRow = new AppRow();
            String str = applicationInfo.packageName;
            appRow.mPackage = str;
            List list = AppSplitViewSettings.this.mSupportEmbedActivityPackages;
            if (list == null || !list.contains(str)) {
                appRow.mState =
                        MultiWindowManager.getInstance()
                                .getSplitActivityPackageEnabled(
                                        appRow.mPackage, UserHandle.getCallingUserId());
            } else {
                appRow.mState =
                        MultiWindowManager.getInstance()
                                        .getEmbedActivityPackageEnabled(
                                                appRow.mPackage, UserHandle.getCallingUserId())
                                ? 1
                                : 0;
            }
            return appRow;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Map map = (Map) obj;
            if (map.isEmpty()) {
                Log.i("AppSplitViewSettings", "empty list, so finishFragment");
                AppSplitViewSettings.this.finishFragment();
                return;
            }
            AppSplitViewSettings.this.mProgressBar.setVisibility(8);
            ((HashMap) AppSplitViewSettings.this.mAppRows).clear();
            ((HashMap) AppSplitViewSettings.this.mAppRows).putAll(map);
            AppSplitViewSettings.this.mAppPickerView.setVisibility(0);
            AppSplitViewSettings.this.mAdapter.resetPackages(
                    new ArrayList(((HashMap) AppSplitViewSettings.this.mAppRows).keySet()), false);
            AppSplitViewSettings appSplitViewSettings = AppSplitViewSettings.this;
            AppPickerView appPickerView = appSplitViewSettings.mAppPickerView;
            appPickerView.mAdapter = appSplitViewSettings.mAdapter;
            appPickerView.setAppPickerView(5, null, 2);
            AppSplitViewSettings.this.mAppPickerView.addCustomViewItem$1();
            AppSplitViewSettings.this.mAppPickerView.addSeparator();
            AppSplitViewSettings appSplitViewSettings2 = AppSplitViewSettings.this;
            AppPickerView appPickerView2 = appSplitViewSettings2.mAppPickerView;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(appSplitViewSettings2);
            AbsAdapter absAdapter = appPickerView2.mAdapter;
            if (absAdapter != null) {
                absAdapter.mOnBindListener = anonymousClass1;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            AppSplitViewSettings.this.mProgressBar.setVisibility(0);
            AppSplitViewSettings.this.mAppPickerView.setVisibility(8);
            AppSplitViewSettings.this.mSupportEmbedActivityPackages =
                    MultiWindowManager.getInstance().getSupportEmbedActivityPackages();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_app_split_view_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return LabsSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 68410;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        new LauncherAppListLoader()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
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
        MenuItem findItem = menu.findItem(R.id.search_apps);
        this.mSearchMenu = findItem;
        findItem.getIcon()
                .setColorFilter(
                        getResources().getColor(R.color.sec_search_magnifier_icon_tint_color, null),
                        PorterDuff.Mode.SRC_IN);
        final SearchView searchView = (SearchView) this.mSearchMenu.getActionView();
        LinearLayout linearLayout = (LinearLayout) searchView.findViewById(R.id.search_plate);
        if (linearLayout != null) {
            linearLayout.setPadding(
                    0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
        }
        searchView.setQueryHint(getString(R.string.sec_app_search_title));
        searchView.mOnQueryChangeListener = new AnonymousClass1(this);
        getActivity()
                .getOnBackInvokedDispatcher()
                .registerSystemOnBackInvokedCallback(
                        new OnBackInvokedCallback() { // from class:
                                                      // com.samsung.android.settings.usefulfeature.labs.appsplitview.AppSplitViewSettings.2
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                SearchView searchView2 = searchView;
                                if (searchView2 == null || !searchView2.hasFocus()) {
                                    AppSplitViewSettings.this.getActivity().finish();
                                    return;
                                }
                                MenuItem menuItem = AppSplitViewSettings.this.mSearchMenu;
                                if (menuItem != null) {
                                    menuItem.collapseActionView();
                                }
                            }
                        });
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.sec_labs_app_picker_view_layout, (ViewGroup) null);
        AppPickerView appPickerView =
                (AppPickerView) inflate.findViewById(R.id.sec_labs_app_picker_view);
        this.mAppPickerView = appPickerView;
        appPickerView.setNestedScrollingEnabled(true);
        this.mAppPickerView.semSetRoundedCorners(15);
        this.mAppPickerView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mAppPickerView.setVisibility(8);
        this.mAppPickerView.mIsCustomViewItemEnabled = true;
        this.mAdapter =
                new AppPickerCustomListAdapter(
                        getContext(), this.mAppPickerView.mAppPickerIconLoader);
        this.mEmptyViewText = (TextView) inflate.findViewById(android.R.id.empty);
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.loading_panel);
        this.mProgressBar = viewGroup2;
        viewGroup2.semSetRoundedCorners(3);
        this.mProgressBar.semSetRoundedCornerColor(
                3, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
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

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = this.mSearchMenu;
        if (menuItem != null) {
            menuItem.setVisible(true);
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
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.appsplitview.AppSplitViewSettings$1, reason: invalid class name */
    public final class AnonymousClass1
            implements SearchView.OnQueryTextListener,
                    AppPickerView.OnBindListener,
                    AppPickerView.OnSearchFilterListener {
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(Object obj) {
            this.this$0 = obj;
        }

        @Override // androidx.apppickerview.widget.AppPickerView.OnBindListener
        public void onBindViewHolder(AppPickerView.ViewHolder viewHolder, int i, String str) {
            AppSplitViewSettings appSplitViewSettings = (AppSplitViewSettings) this.this$0;
            if (!((HashMap) appSplitViewSettings.mAppRows).containsKey(str)) {
                Log.e("AppSplitViewSettings", "skipped unexpected package : " + str);
                return;
            }
            if ((viewHolder instanceof CustomViewHolder)
                    || (viewHolder instanceof AppPickerView.SeparatorViewHolder)) {
                return;
            }
            final AppRow appRow = (AppRow) ((HashMap) appSplitViewSettings.mAppRows).get(str);
            SwitchCompat switchCompat = viewHolder.mSwitch;
            switchCompat.setVisibility(0);
            switchCompat.setOnCheckedChangeListener(
                    new CompoundButton
                            .OnCheckedChangeListener() { // from class:
                                                         // com.samsung.android.settings.usefulfeature.labs.appsplitview.AppSplitViewSettings$3$1
                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                        public final void onCheckedChanged(
                                CompoundButton compoundButton, boolean z) {
                            List list =
                                    ((AppSplitViewSettings)
                                                    AppSplitViewSettings.AnonymousClass1.this
                                                            .this$0)
                                            .mSupportEmbedActivityPackages;
                            if (list == null || !list.contains(appRow.mPackage)) {
                                MultiWindowManager.getInstance()
                                        .setSplitActivityPackageEnabled(
                                                appRow.mPackage,
                                                z ? 1 : 0,
                                                UserHandle.getCallingUserId());
                                appRow.mState =
                                        MultiWindowManager.getInstance()
                                                .getSplitActivityPackageEnabled(
                                                        appRow.mPackage,
                                                        UserHandle.getCallingUserId());
                            } else {
                                MultiWindowManager.getInstance()
                                        .setEmbedActivityPackageEnabled(
                                                appRow.mPackage, z, UserHandle.getCallingUserId());
                                appRow.mState =
                                        MultiWindowManager.getInstance()
                                                        .getEmbedActivityPackageEnabled(
                                                                appRow.mPackage,
                                                                UserHandle.getCallingUserId())
                                                ? 1
                                                : 0;
                            }
                            ((AppSplitViewSettings)
                                            AppSplitViewSettings.AnonymousClass1.this.this$0)
                                    .getClass();
                            LoggingHelper.insertEventLogging(
                                    68410, z ? 68411 : 68412, appRow.mPackage);
                        }
                    });
            switchCompat.setChecked(appRow.mState != 0);
            viewHolder.mSummary.setVisibility(8);
            viewHolder.mSummary.setText((CharSequence) null);
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            AppSplitViewSettings appSplitViewSettings = (AppSplitViewSettings) this.this$0;
            AppPickerView appPickerView = appSplitViewSettings.mAppPickerView;
            if (appPickerView == null || appPickerView.getAdapter() == null) {
                return false;
            }
            appSplitViewSettings.mAppPickerView.setSearchFilter(str, new AnonymousClass1(this));
            return false;
        }

        @Override // androidx.apppickerview.widget.AppPickerView.OnSearchFilterListener
        public void onSearchFilterCompleted(int i) {
            AnonymousClass1 anonymousClass1 = (AnonymousClass1) this.this$0;
            TextView textView = ((AppSplitViewSettings) anonymousClass1.this$0).mEmptyViewText;
            if (textView != null) {
                textView.setVisibility(i == 0 ? 0 : 8);
                ((AppSplitViewSettings) anonymousClass1.this$0)
                        .mAppPickerView.setVisibility(i != 0 ? 0 : 8);
            }
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public void onQueryTextSubmit(String str) {}
    }
}
