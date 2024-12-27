package com.samsung.android.settings.usefulfeature.labs.flexmodepanel;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.apppickerview.widget.AbsAdapter;
import androidx.apppickerview.widget.AppPickerView;
import androidx.apppickerview.widget.AppPickerView.AnonymousClass5;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FlexModePanelDetailsSettings extends SettingsPreferenceFragment {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass7();
    public static int enableCount;
    public static boolean mAllAppsChecked;
    public AppPickerView mAppPickerView;
    public TextView mEmptyViewText;
    public LauncherApps mLauncherApps;
    public ViewGroup mListContainer;
    public ProgressBar mProgressBar;
    public MenuItem mSearchMenu;
    public SearchView mSearchView;
    public SemWindowManager mSemWindowManager;
    public boolean mFragmentVisible = false;
    public final ArrayMap mRows = new ArrayMap();
    public final List mPackageList = new ArrayList();
    public final List mPackageListTemp = new ArrayList();
    public int mValidItemCount = 0;
    public boolean mIsFirstLoading = false;
    public final HashMap mStateMap = new HashMap();
    public final AnonymousClass4 mCollectAppsRunnable = new Runnable() { // from class:
                // com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelDetailsSettings.4
                /* JADX WARN: Code restructure failed: missing block: B:20:0x009e, code lost:

                   r9.this$0.mRows.put(r5, r6);
                   ((java.util.ArrayList) r9.this$0.mPackageListTemp).add(r5);
                   r9.this$0.mValidItemCount++;
                */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        Method dump skipped, instructions count: 231
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelDetailsSettings.AnonymousClass4.run():void");
                }
            };
    public final AnonymousClass5 mAllAppsCheckedChangeListener =
            new CompoundButton
                    .OnCheckedChangeListener() { // from class:
                                                 // com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelDetailsSettings.5
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (FlexModePanelDetailsSettings.mAllAppsChecked != z) {
                        FlexModePanelDetailsSettings.mAllAppsChecked = z;
                        if (compoundButton.isPressed()) {
                            FlexModePanelDetailsSettings.mAllAppsChecked = z;
                        }
                        Set keySet = FlexModePanelDetailsSettings.this.mStateMap.keySet();
                        Iterator it = keySet.iterator();
                        while (true) {
                            boolean z2 = false;
                            if (!it.hasNext()) {
                                break;
                            }
                            String str = (String) it.next();
                            if (FlexModePanelDetailsSettings.this.mStateMap.get(str) != null
                                    && ((Boolean)
                                                    FlexModePanelDetailsSettings.this.mStateMap.get(
                                                            str))
                                            .booleanValue()) {
                                z2 = true;
                            }
                            if (z2 != z) {
                                FlexModePanelDetailsSettings.this.mStateMap.put(
                                        str, Boolean.valueOf(z));
                                FlexModePanelDetailsSettings.this.mSemWindowManager
                                        .setSupportsFlexPanel(
                                                UserHandle.getCallingUserId(), str, z);
                            }
                        }
                        if (z) {
                            FlexModePanelDetailsSettings.enableCount = keySet.size() - 1;
                        } else {
                            FlexModePanelDetailsSettings.enableCount = 0;
                        }
                        FlexModePanelDetailsSettings.this.getClass();
                        LoggingHelper.insertEventLogging(68519, 68520, z ? 1L : 0L);
                        AppPickerView appPickerView =
                                FlexModePanelDetailsSettings.this.mAppPickerView;
                        appPickerView.getClass();
                        appPickerView.post(new AppPickerView.AnonymousClass3(appPickerView, 0));
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelDetailsSettings$1, reason: invalid class name */
    public final class AnonymousClass1 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass1() {}

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            ViewGroup viewGroup = FlexModePanelDetailsSettings.this.mListContainer;
            if (viewGroup != null) {
                viewGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                FlexModePanelDetailsSettings.this.mEmptyViewText.setMaxWidth(
                        (FlexModePanelDetailsSettings.this.mListContainer.getMeasuredWidth() / 4)
                                * 3);
                FlexModePanelDetailsSettings.this.mEmptyViewText.setGravity(17);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelDetailsSettings$7, reason: invalid class name */
    public final class AnonymousClass7 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            String valueOf = String.valueOf(68521);
            String str =
                    FlexModePanelDetailsSettings.mAllAppsChecked
                            ? "1"
                            : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            String valueOf2 = String.valueOf(68524);
            String valueOf3 = String.valueOf(FlexModePanelDetailsSettings.enableCount);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf3;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppRow {
        public String mPackage;
        public int mType;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CreatePreferenceNewTask extends AsyncTask {
        public CreatePreferenceNewTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            return FlexModePanelDetailsSettings.this.getActivity() == null
                    ? new ArrayList()
                    : FlexModePanelDetailsSettings.this.mPackageListTemp;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            boolean z;
            List list = (List) obj;
            FlexModePanelDetailsSettings flexModePanelDetailsSettings =
                    FlexModePanelDetailsSettings.this;
            synchronized (flexModePanelDetailsSettings) {
                z = flexModePanelDetailsSettings.mFragmentVisible;
            }
            if (z && list.size() > 0) {
                FlexModePanelDetailsSettings flexModePanelDetailsSettings2 =
                        FlexModePanelDetailsSettings.this;
                if (!flexModePanelDetailsSettings2.mPackageList.containsAll(
                                flexModePanelDetailsSettings2.mPackageListTemp)
                        || ((ArrayList) FlexModePanelDetailsSettings.this.mPackageList).size()
                                != ((ArrayList) FlexModePanelDetailsSettings.this.mPackageListTemp)
                                        .size()) {
                    ((ArrayList) FlexModePanelDetailsSettings.this.mPackageList).clear();
                    FlexModePanelDetailsSettings flexModePanelDetailsSettings3 =
                            FlexModePanelDetailsSettings.this;
                    ((ArrayList) flexModePanelDetailsSettings3.mPackageList)
                            .addAll(flexModePanelDetailsSettings3.mPackageListTemp);
                    FlexModePanelDetailsSettings flexModePanelDetailsSettings4 =
                            FlexModePanelDetailsSettings.this;
                    flexModePanelDetailsSettings4.mAppPickerView =
                            (AppPickerView)
                                    ((LayoutPreference)
                                                    flexModePanelDetailsSettings4.findPreference(
                                                            "front_screen_apps_picker"))
                                            .mRootView.findViewById(
                                                    R.id.front_screen_app_picker_view);
                    FlexModePanelDetailsSettings.this.mAppPickerView.setNestedScrollingEnabled(
                            false);
                    FlexModePanelDetailsSettings flexModePanelDetailsSettings5 =
                            FlexModePanelDetailsSettings.this;
                    if (flexModePanelDetailsSettings5.mIsFirstLoading) {
                        AppPickerView appPickerView = flexModePanelDetailsSettings5.mAppPickerView;
                        appPickerView.mAdapter.resetPackages(
                                flexModePanelDetailsSettings5.mPackageList, true);
                        appPickerView.mSeparators.clear();
                    } else {
                        flexModePanelDetailsSettings5.mAppPickerView.setAppPickerView(
                                6, flexModePanelDetailsSettings5.mPackageList, 2);
                        FlexModePanelDetailsSettings.this.mIsFirstLoading = true;
                    }
                    ((SimpleItemAnimator)
                                            FlexModePanelDetailsSettings.this.mAppPickerView
                                                    .getItemAnimator())
                                    .mSupportsChangeAnimations =
                            false;
                    FlexModePanelDetailsSettings flexModePanelDetailsSettings6 =
                            FlexModePanelDetailsSettings.this;
                    AppPickerView appPickerView2 = flexModePanelDetailsSettings6.mAppPickerView;
                    AnonymousClass2 anonymousClass2 =
                            new AnonymousClass2(flexModePanelDetailsSettings6);
                    AbsAdapter absAdapter = appPickerView2.mAdapter;
                    if (absAdapter != null) {
                        absAdapter.mOnBindListener = anonymousClass2;
                    }
                    Log.secD(
                            "FlexModePanelDetailsSettings",
                            "Refreshed " + list.size() + " displayed items");
                }
            }
            if (FlexModePanelDetailsSettings.this.mProgressBar.getVisibility() == 0) {
                FlexModePanelDetailsSettings.this.mProgressBar.setVisibility(8);
            }
            FlexModePanelDetailsSettings.this.highlightPreferenceIfNeeded();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SwitchOnPreferenceChangeListener
            implements CompoundButton.OnCheckedChangeListener {
        public final AppRow mRow;

        public SwitchOnPreferenceChangeListener(AppRow appRow) {
            this.mRow = appRow;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            FlexModePanelDetailsSettings.this.mSemWindowManager.setSupportsFlexPanel(
                    UserHandle.getCallingUserId(), this.mRow.mPackage, z);
            FlexModePanelDetailsSettings.this.mStateMap.put(this.mRow.mPackage, Boolean.valueOf(z));
            compoundButton.setChecked(z);
            if (z) {
                boolean z2 = true;
                for (String str : FlexModePanelDetailsSettings.this.mStateMap.keySet()) {
                    if (!"all_apps".equals(str)) {
                        z2 =
                                FlexModePanelDetailsSettings.this.mStateMap.get(str) != null
                                        && ((Boolean)
                                                        FlexModePanelDetailsSettings.this.mStateMap
                                                                .get(str))
                                                .booleanValue();
                        if (!z2) {
                            break;
                        }
                    }
                }
                if (z2) {
                    int size = FlexModePanelDetailsSettings.this.mStateMap.size();
                    FlexModePanelDetailsSettings flexModePanelDetailsSettings =
                            FlexModePanelDetailsSettings.this;
                    if (size >= flexModePanelDetailsSettings.mValidItemCount) {
                        FlexModePanelDetailsSettings.mAllAppsChecked = true;
                        flexModePanelDetailsSettings.mStateMap.put("all_apps", Boolean.TRUE);
                        AppPickerView appPickerView =
                                FlexModePanelDetailsSettings.this.mAppPickerView;
                        appPickerView.getClass();
                        appPickerView.post(appPickerView.new AnonymousClass5(0));
                    }
                }
                FlexModePanelDetailsSettings.enableCount++;
            } else {
                FlexModePanelDetailsSettings.mAllAppsChecked = false;
                FlexModePanelDetailsSettings.this.mStateMap.put("all_apps", Boolean.FALSE);
                AppPickerView appPickerView2 = FlexModePanelDetailsSettings.this.mAppPickerView;
                appPickerView2.getClass();
                appPickerView2.post(appPickerView2.new AnonymousClass5(0));
                FlexModePanelDetailsSettings.enableCount--;
            }
            if (z) {
                FlexModePanelDetailsSettings.this.getClass();
                SALogging.insertSALog(
                        String.valueOf(68519), String.valueOf(68512), this.mRow.mPackage);
            } else {
                FlexModePanelDetailsSettings.this.getClass();
                SALogging.insertSALog(
                        String.valueOf(68519), String.valueOf(68513), this.mRow.mPackage);
            }
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_flex_mode_panel_auto_show_panel_when_folded;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return FlexModePanelSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 68519;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        if (getListView() != null) {
            getListView().seslSetGoToTopEnabled(true);
        }
        super.onActivityCreated(bundle);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ViewGroup viewGroup = this.mListContainer;
        if (viewGroup != null) {
            viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass1());
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSemWindowManager = SemWindowManager.getInstance();
        this.mLauncherApps = (LauncherApps) getContext().getSystemService("launcherapps");
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
        SearchView searchView = (SearchView) this.mSearchMenu.getActionView();
        this.mSearchView = searchView;
        LinearLayout linearLayout = (LinearLayout) searchView.findViewById(R.id.search_plate);
        if (linearLayout != null) {
            linearLayout.setPadding(
                    0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
        }
        this.mSearchView.setQueryHint(getString(R.string.sec_app_search_title));
        this.mSearchView.mOnQueryChangeListener = new AnonymousClass2(this);
        getActivity()
                .getOnBackInvokedDispatcher()
                .registerSystemOnBackInvokedCallback(
                        new OnBackInvokedCallback() { // from class:
                                                      // com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelDetailsSettings.3
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                SearchView searchView2 =
                                        FlexModePanelDetailsSettings.this.mSearchView;
                                if (searchView2 == null || !searchView2.hasFocus()) {
                                    FlexModePanelDetailsSettings.this.getActivity().finish();
                                    return;
                                }
                                MenuItem menuItem = FlexModePanelDetailsSettings.this.mSearchMenu;
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
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        ProgressBar progressBar = new ProgressBar(getContext());
        this.mProgressBar = progressBar;
        progressBar.setIndeterminate(true);
        this.mProgressBar.setScrollBarStyle(R.style.LoadingTheme);
        this.mProgressBar.setVisibility(8);
        this.mListContainer = (ViewGroup) onCreateView.findViewById(android.R.id.list_container);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 17;
        this.mListContainer.addView(this.mProgressBar, layoutParams);
        TextView textView = (TextView) onCreateView.findViewById(android.R.id.empty);
        this.mEmptyViewText = textView;
        if (textView != null) {
            textView.setTextAppearance(getContext(), R.style.no_item_text_style);
            this.mEmptyViewText.setText(R.string.sec_app_search_no_result);
            ViewGroup viewGroup2 = this.mListContainer;
            if (viewGroup2 != null) {
                viewGroup2.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass1());
            }
        }
        addPreferencesFromResource(R.xml.sec_flex_mode_panel_details_settings);
        return onCreateView;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        synchronized (this) {
            this.mFragmentVisible = false;
        }
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
        MenuItem menuItem = this.mSearchMenu;
        if (menuItem != null && this.mEmptyViewText != null) {
            menuItem.collapseActionView();
            this.mEmptyViewText.setVisibility(8);
        }
        AppPickerView appPickerView = this.mAppPickerView;
        if (appPickerView != null) {
            appPickerView.mDrawLastRoundedCorner = true;
        }
        if (this.mProgressBar.getVisibility() == 0) {
            this.mProgressBar.setVisibility(8);
        }
        this.mProgressBar.setVisibility(0);
        ((ArrayList) this.mPackageList).clear();
        mAllAppsChecked = false;
        AsyncTask.execute(this.mCollectAppsRunnable);
        synchronized (this) {
            this.mFragmentVisible = true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.flexmodepanel.FlexModePanelDetailsSettings$2, reason: invalid class name */
    public final class AnonymousClass2
            implements SearchView.OnQueryTextListener,
                    AppPickerView.OnBindListener,
                    AppPickerView.OnSearchFilterListener {
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass2(Object obj) {
            this.this$0 = obj;
        }

        @Override // androidx.apppickerview.widget.AppPickerView.OnBindListener
        public void onBindViewHolder(AppPickerView.ViewHolder viewHolder, int i, String str) {
            synchronized (((FlexModePanelDetailsSettings) this.this$0).mPackageListTemp) {
                try {
                    AppRow appRow =
                            (AppRow) ((FlexModePanelDetailsSettings) this.this$0).mRows.get(str);
                    SwitchCompat switchCompat = viewHolder.mSwitch;
                    switchCompat.requestLayout();
                    FlexModePanelDetailsSettings flexModePanelDetailsSettings =
                            (FlexModePanelDetailsSettings) this.this$0;
                    SwitchOnPreferenceChangeListener switchOnPreferenceChangeListener =
                            flexModePanelDetailsSettings
                            .new SwitchOnPreferenceChangeListener(appRow);
                    if (viewHolder instanceof AppPickerView.HeaderViewHolder) {
                        viewHolder.mAppName.setText(
                                flexModePanelDetailsSettings
                                        .getContext()
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .sec_flex_mode_panel_auto_show_panel_when_folded_all_supported_apps));
                        ((FlexModePanelDetailsSettings) this.this$0)
                                .mStateMap.putIfAbsent(
                                        str,
                                        Boolean.valueOf(
                                                FlexModePanelDetailsSettings.mAllAppsChecked));
                        if (!FlexModePanelDetailsSettings.mAllAppsChecked) {
                            if (((FlexModePanelDetailsSettings) this.this$0).mStateMap.get(str)
                                            != null
                                    && ((Boolean)
                                                    ((FlexModePanelDetailsSettings) this.this$0)
                                                            .mStateMap.get(str))
                                            .booleanValue()) {}
                            switchCompat.setChecked(r5);
                            switchCompat.setOnCheckedChangeListener(
                                    ((FlexModePanelDetailsSettings) this.this$0)
                                            .mAllAppsCheckedChangeListener);
                        }
                        r5 = true;
                        switchCompat.setChecked(r5);
                        switchCompat.setOnCheckedChangeListener(
                                ((FlexModePanelDetailsSettings) this.this$0)
                                        .mAllAppsCheckedChangeListener);
                    } else {
                        switchCompat.setOnCheckedChangeListener(switchOnPreferenceChangeListener);
                        if (appRow != null) {
                            if ((appRow.mType & 16) != 16) {
                                viewHolder.mSummary.setVisibility(8);
                                viewHolder.mSummary.setText(ApnSettings.MVNO_NONE);
                            } else {
                                viewHolder.mSummary.setVisibility(0);
                                viewHolder.mSummary.setText(
                                        R.string
                                                .sec_flex_mode_panel_auto_show_panel_when_folded_summary_provides_custom_layout);
                            }
                            r5 =
                                    (((FlexModePanelDetailsSettings) this.this$0)
                                                            .mSemWindowManager.getSupportsFlexPanel(
                                                                    UserHandle.getCallingUserId(),
                                                                    appRow.mPackage)
                                                    & 1)
                                            == 1;
                            switchCompat.setChecked(r5);
                            ((FlexModePanelDetailsSettings) this.this$0)
                                    .mStateMap.put(str, Boolean.valueOf(r5));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            FlexModePanelDetailsSettings flexModePanelDetailsSettings =
                    (FlexModePanelDetailsSettings) this.this$0;
            AppPickerView appPickerView = flexModePanelDetailsSettings.mAppPickerView;
            if (appPickerView == null || appPickerView.getAdapter() == null) {
                return false;
            }
            flexModePanelDetailsSettings.getClass();
            SALogging.insertSALog(String.valueOf(68519), String.valueOf(68511), str);
            flexModePanelDetailsSettings.mAppPickerView.setSearchFilter(
                    str, new AnonymousClass2(this));
            return false;
        }

        @Override // androidx.apppickerview.widget.AppPickerView.OnSearchFilterListener
        public void onSearchFilterCompleted(int i) {
            AnonymousClass2 anonymousClass2 = (AnonymousClass2) this.this$0;
            TextView textView =
                    ((FlexModePanelDetailsSettings) anonymousClass2.this$0).mEmptyViewText;
            if (textView != null) {
                textView.setVisibility(i == 0 ? 0 : 8);
                ((FlexModePanelDetailsSettings) anonymousClass2.this$0)
                        .mAppPickerView.setVisibility(i != 0 ? 0 : 8);
            }
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public void onQueryTextSubmit(String str) {}
    }
}
