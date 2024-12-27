package com.samsung.android.settings.display;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.SemUserInfo;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.apppickerview.widget.AbsAdapter;
import androidx.apppickerview.widget.AppPickerIconLoader;
import androidx.apppickerview.widget.AppPickerView;
import androidx.apppickerview.widget.CustomListAdapter;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.reflect.view.SeslViewReflector;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settingslib.applications.cachedb.AppListCacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CameraCutoutFragment extends SettingsPreferenceFragment {
    public HashMap mAppLabelCache;
    public NestedScrollView mAppNestedView;
    public AppPickerView mAppPickerView;
    public FragmentActivity mContext;
    public TextView mEmptyViewText;
    public ExecutorService mExecutor;
    public Handler mHandler;
    public LauncherApps mLauncherApps;
    public View mLoadingPanel;
    public View mPreviewImageLayout;
    public MenuItem mSearchMenu;
    public SemUserInfo mUserInfo;
    public final List mPackageList = new ArrayList();
    public List mResultList = new ArrayList();
    public final List mPackageListTemp = new ArrayList();
    public final ArrayMap mRows = new ArrayMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.CameraCutoutFragment$1, reason: invalid class name */
    public final class AnonymousClass1
            implements SearchView.OnQueryTextListener,
                    AppPickerView.OnBindListener,
                    AppPickerView.OnSearchFilterListener {
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(Object obj) {
            this.this$0 = obj;
        }

        @Override // androidx.apppickerview.widget.AppPickerView.OnBindListener
        public void onBindViewHolder(final AppPickerView.ViewHolder viewHolder, int i, String str) {
            if ((viewHolder instanceof AppPickerView.CustomViewItemViewHolder)
                    || (viewHolder instanceof AppPickerView.SeparatorViewHolder)) {
                return;
            }
            CameraCutoutFragment cameraCutoutFragment = (CameraCutoutFragment) this.this$0;
            final AppRow appRow = (AppRow) cameraCutoutFragment.mRows.get(str);
            if (appRow == null) {
                return;
            }
            ViewGroup viewGroup = (ViewGroup) appRow.mSpinner.getParent();
            if (viewGroup == null) {
                if (viewHolder.mAppIconContainer.getChildAt(0) instanceof AppCompatSpinner) {
                    viewHolder.mAppIconContainer.removeViewAt(0);
                }
                viewHolder.mAppIconContainer.addView(appRow.mSpinner, 0);
                appRow.mSpinner.getLayoutParams().width = 0;
                int dimensionPixelSize =
                        cameraCutoutFragment
                                .mContext
                                .getResources()
                                .getDimensionPixelSize(R.dimen.sec_widget_list_app_icon_size);
                int dimensionPixelSize2 =
                        cameraCutoutFragment
                                .mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_full_screen_apps_menu_popup_top_margin);
                appRow.mSpinner.setDropDownHorizontalOffset(dimensionPixelSize);
                appRow.mSpinner.setDropDownVerticalOffset(dimensionPixelSize2);
            } else if (viewGroup != viewHolder.mAppIconContainer) {
                viewGroup.removeView(appRow.mSpinner);
                viewHolder.mAppIconContainer.addView(appRow.mSpinner, 0);
                appRow.mSpinner.getLayoutParams().width = 0;
            }
            viewHolder.itemView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.display.CameraCutoutFragment$3$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            CameraCutoutFragment.AppRow.this.mSpinner.performClick();
                        }
                    });
            appRow.mSpinner.setOnItemClickListenerInt(
                    new AdapterView
                            .OnItemClickListener() { // from class:
                                                     // com.samsung.android.settings.display.CameraCutoutFragment$3$2
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(
                                AdapterView adapterView, View view, int i2, long j) {
                            MainClearConfirm$$ExternalSyntheticOutline0.m(
                                    i2, "Click position : ", "CameraCutoutFragment");
                            viewHolder.mSummary.setText((CharSequence) appRow.mOptionList.get(i2));
                            if (i2 == 0) {
                                appRow.mType = 1;
                            } else if (i2 == 1) {
                                appRow.mType = 2;
                            } else {
                                appRow.mType = 0;
                            }
                            CameraCutoutFragment cameraCutoutFragment2 =
                                    (CameraCutoutFragment)
                                            CameraCutoutFragment.AnonymousClass1.this.this$0;
                            String str2 = appRow.mPackage;
                            int identifier =
                                    cameraCutoutFragment2.mUserInfo.getUserHandle().getIdentifier();
                            int i3 = appRow.mType;
                            cameraCutoutFragment2.getClass();
                            Log.d(
                                    "CameraCutoutFragment",
                                    "setCameraCutoutPackage : "
                                            + str2
                                            + " / "
                                            + i3
                                            + " for user: "
                                            + identifier);
                            try {
                                ActivityTaskManager.getService()
                                        .setCutoutPolicy(identifier, str2, i3);
                            } catch (RemoteException unused) {
                                Log.d(
                                        "CameraCutoutFragment",
                                        "setCameraCutoutPackage() RemoteException");
                            }
                        }
                    });
            viewHolder.mSummary.setVisibility(0);
            viewHolder.mSummary.setTextColor(
                    cameraCutoutFragment
                            .mContext
                            .getResources()
                            .getColorStateList(R.color.sec_preference_summary_primary_color));
            int i2 = appRow.mType;
            if (i2 == 1) {
                viewHolder.mSummary.setText((CharSequence) appRow.mOptionList.get(0));
            } else if (i2 == 2) {
                viewHolder.mSummary.setText((CharSequence) appRow.mOptionList.get(1));
            } else {
                viewHolder.mSummary.setText((CharSequence) appRow.mOptionList.get(2));
            }
            cameraCutoutFragment.mExecutor.execute(
                    new LoadAppIconTask(cameraCutoutFragment, viewHolder, appRow, 0));
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            CameraCutoutFragment cameraCutoutFragment = (CameraCutoutFragment) this.this$0;
            if (cameraCutoutFragment.mPreviewImageLayout != null) {
                if (str.length() > 0) {
                    cameraCutoutFragment.mPreviewImageLayout.setVisibility(8);
                } else {
                    cameraCutoutFragment.mPreviewImageLayout.setVisibility(0);
                }
            }
            AppPickerView appPickerView = cameraCutoutFragment.mAppPickerView;
            if (appPickerView != null && appPickerView.getAdapter() != null) {
                cameraCutoutFragment.mAppPickerView.setSearchFilter(str, new AnonymousClass1(this));
            }
            return false;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public void onQueryTextSubmit(String str) {
            CameraCutoutFragment cameraCutoutFragment = (CameraCutoutFragment) this.this$0;
            AppPickerView appPickerView = cameraCutoutFragment.mAppPickerView;
            if (appPickerView == null || appPickerView.getAdapter() == null) {
                return;
            }
            cameraCutoutFragment.mAppPickerView.setSearchFilter(str, null);
        }

        @Override // androidx.apppickerview.widget.AppPickerView.OnSearchFilterListener
        public void onSearchFilterCompleted(int i) {
            AnonymousClass1 anonymousClass1 = (AnonymousClass1) this.this$0;
            ((CameraCutoutFragment) anonymousClass1.this$0)
                    .mAppPickerView.setVisibility(i != 0 ? 0 : 8);
            CameraCutoutFragment cameraCutoutFragment =
                    (CameraCutoutFragment) anonymousClass1.this$0;
            TextView textView = cameraCutoutFragment.mEmptyViewText;
            if (textView != null) {
                if (i != 0) {
                    textView.setVisibility(8);
                    cameraCutoutFragment.mAppNestedView.setVisibility(0);
                } else {
                    textView.setText(R.string.sec_app_search_no_result);
                    cameraCutoutFragment.mEmptyViewText.setVisibility(0);
                    cameraCutoutFragment.mAppNestedView.setVisibility(8);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppRow {
        public ArrayAdapter mAdapter;
        public CharSequence mLabel;
        public LauncherActivityInfo mLauncherActivityInfo;
        public final ArrayList mOptionList = new ArrayList();
        public String mPackage;
        public AppCompatSpinner mSpinner;
        public int mType;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomListViewAdapter extends CustomListAdapter {
        public final Context mCustomListContext;

        public CustomListViewAdapter(Context context, AppPickerIconLoader appPickerIconLoader) {
            super(context, 0, appPickerIconLoader);
            this.mCustomListContext = context;
        }

        @Override // androidx.apppickerview.widget.ListAdapter,
                  // androidx.recyclerview.widget.RecyclerView.Adapter
        public final AppPickerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i != -10) {
                return super.onCreateViewHolder(viewGroup, i);
            }
            View inflate =
                    LayoutInflater.from(this.mCustomListContext)
                            .inflate(R.layout.full_screen_camera_cutout_preview, viewGroup, false);
            if (Utils.isTablet()) {
                float measuredWidth =
                        inflate.getMeasuredWidth()
                                / this.mCustomListContext
                                        .getResources()
                                        .getDisplayMetrics()
                                        .density;
                inflate =
                        measuredWidth < 340.0f
                                ? LayoutInflater.from(this.mCustomListContext)
                                        .inflate(
                                                R.layout
                                                        .full_screen_camera_cutout_preview_for_tablet,
                                                viewGroup,
                                                false)
                                : measuredWidth < 542.0f
                                        ? LayoutInflater.from(this.mCustomListContext)
                                                .inflate(
                                                        R.layout
                                                                .full_screen_camera_cutout_preview_for_tablet_2,
                                                        viewGroup,
                                                        false)
                                        : LayoutInflater.from(this.mCustomListContext)
                                                .inflate(
                                                        R.layout
                                                                .full_screen_camera_cutout_preview_for_tablet_3,
                                                        viewGroup,
                                                        false);
            }
            CameraCutoutFragment.this.mPreviewImageLayout =
                    (LinearLayout) inflate.findViewById(R.id.image_layout);
            LinearLayout linearLayout =
                    (LinearLayout) inflate.findViewById(R.id.full_screen_image_container);
            if (linearLayout != null) {
                linearLayout.semSetRoundedCorners(15);
                linearLayout.semSetRoundedCornerColor(
                        15, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
            }
            return new AppPickerView.CustomViewItemViewHolder(inflate);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CutoutAppListLoader extends AsyncTask {
        public CutoutAppListLoader() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            List list;
            if (CameraCutoutFragment.this.getActivity() == null) {
                return new ArrayList();
            }
            synchronized (this) {
                try {
                    ((ArrayList) CameraCutoutFragment.this.mPackageListTemp).clear();
                    CameraCutoutFragment.this.mRows.clear();
                    CameraCutoutFragment cameraCutoutFragment = CameraCutoutFragment.this;
                    cameraCutoutFragment.getClass();
                    cameraCutoutFragment.getActivity();
                    PackageManager packageManager =
                            CameraCutoutFragment.this.getActivity().getPackageManager();
                    CameraCutoutFragment cameraCutoutFragment2 = CameraCutoutFragment.this;
                    List<LauncherActivityInfo> activityList =
                            cameraCutoutFragment2.mLauncherApps.getActivityList(
                                    null, cameraCutoutFragment2.mUserInfo.getUserHandle());
                    Log.d("CameraCutoutFragment", "  launchable activities:");
                    for (LauncherActivityInfo launcherActivityInfo : activityList) {
                        Log.d(
                                "CameraCutoutFragment",
                                "    " + launcherActivityInfo.getComponentName().toString());
                        ApplicationInfo applicationInfo = launcherActivityInfo.getApplicationInfo();
                        String str = applicationInfo.packageName;
                        if (!KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(str)
                                && !"com.samsung.knox.securefolder".equals(str)
                                && !CameraCutoutFragment.this.mRows.containsKey(str)) {
                            AppRow m1202$$Nest$mloadAppRow =
                                    CameraCutoutFragment.m1202$$Nest$mloadAppRow(
                                            CameraCutoutFragment.this,
                                            packageManager,
                                            applicationInfo,
                                            launcherActivityInfo);
                            ((ArrayList) CameraCutoutFragment.this.mPackageListTemp).add(str);
                            CameraCutoutFragment cameraCutoutFragment3 = CameraCutoutFragment.this;
                            cameraCutoutFragment3.getClass();
                            cameraCutoutFragment3.mRows.put(str, m1202$$Nest$mloadAppRow);
                        }
                    }
                    Log.d("CameraCutoutFragment", "Finish getting ApplicationInfo");
                    list = CameraCutoutFragment.this.mPackageListTemp;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return list;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            CameraCutoutFragment.this.mLoadingPanel.setVisibility(8);
            ((ArrayList) CameraCutoutFragment.this.mPackageList).clear();
            CameraCutoutFragment cameraCutoutFragment = CameraCutoutFragment.this;
            ((ArrayList) cameraCutoutFragment.mPackageList)
                    .addAll(cameraCutoutFragment.mPackageListTemp);
            CameraCutoutFragment cameraCutoutFragment2 = CameraCutoutFragment.this;
            cameraCutoutFragment2.mResultList = (List) obj;
            cameraCutoutFragment2.mAppPickerView.setVisibility(0);
            CameraCutoutFragment cameraCutoutFragment3 = CameraCutoutFragment.this;
            CustomListViewAdapter customListViewAdapter =
                    cameraCutoutFragment3
                    .new CustomListViewAdapter(
                            cameraCutoutFragment3.mContext,
                            cameraCutoutFragment3.mAppPickerView.mAppPickerIconLoader);
            customListViewAdapter.resetPackages(CameraCutoutFragment.this.mPackageList, false);
            AppPickerView appPickerView = CameraCutoutFragment.this.mAppPickerView;
            appPickerView.mAdapter = customListViewAdapter;
            appPickerView.setAppPickerView(0, null, 2);
            CameraCutoutFragment.this.mAppPickerView.addCustomViewItem$1();
            CameraCutoutFragment.this.mAppPickerView.addSeparator();
            CameraCutoutFragment cameraCutoutFragment4 = CameraCutoutFragment.this;
            AppPickerView appPickerView2 = cameraCutoutFragment4.mAppPickerView;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(cameraCutoutFragment4);
            AbsAdapter absAdapter = appPickerView2.mAdapter;
            if (absAdapter != null) {
                absAdapter.mOnBindListener = anonymousClass1;
            }
            if (((ArrayList) cameraCutoutFragment4.mPackageList).isEmpty()) {
                CameraCutoutFragment.this.mAppPickerView.setVisibility(8);
                MenuItem menuItem = CameraCutoutFragment.this.mSearchMenu;
                if (menuItem != null) {
                    menuItem.setVisible(false);
                }
            } else {
                MenuItem menuItem2 = CameraCutoutFragment.this.mSearchMenu;
                if (menuItem2 != null) {
                    menuItem2.setVisible(true);
                }
            }
            CameraCutoutFragment.this.updateEmptyTextViewState();
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            CameraCutoutFragment.this.mLoadingPanel.setVisibility(0);
            CameraCutoutFragment.this.mAppPickerView.setVisibility(8);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LoadAppIconTask implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final Object holder;
        public final Object row;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ LoadAppIconTask(Object obj, Object obj2, Object obj3, int i) {
            this.$r8$classId = i;
            this.this$0 = obj;
            this.holder = obj2;
            this.row = obj3;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    ((CameraCutoutFragment) this.this$0)
                            .mHandler.post(
                                    new LoadAppIconTask(
                                            this,
                                            ((AppRow) this.row)
                                                    .mLauncherActivityInfo
                                                            .semGetBadgedIconForIconTray(
                                                                    ((CameraCutoutFragment)
                                                                                    this.this$0)
                                                                            .mContext
                                                                            .getResources()
                                                                            .getDisplayMetrics()
                                                                            .densityDpi),
                                            ((AppRow) this.row).mLauncherActivityInfo.getLabel(),
                                            1));
                    break;
                default:
                    ((AppPickerView.ViewHolder) ((LoadAppIconTask) this.this$0).holder)
                            .mAppIcon.setImageDrawable((Drawable) this.holder);
                    ((AppPickerView.ViewHolder) ((LoadAppIconTask) this.this$0).holder)
                            .mAppName.setText((CharSequence) this.row);
                    break;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x013c  */
    /* renamed from: -$$Nest$mloadAppRow, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.settings.display.CameraCutoutFragment.AppRow
            m1202$$Nest$mloadAppRow(
                    com.samsung.android.settings.display.CameraCutoutFragment r5,
                    android.content.pm.PackageManager r6,
                    android.content.pm.ApplicationInfo r7,
                    android.content.pm.LauncherActivityInfo r8) {
        /*
            Method dump skipped, instructions count: 331
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.display.CameraCutoutFragment.m1202$$Nest$mloadAppRow(com.samsung.android.settings.display.CameraCutoutFragment,"
                    + " android.content.pm.PackageManager, android.content.pm.ApplicationInfo,"
                    + " android.content.pm.LauncherActivityInfo):com.samsung.android.settings.display.CameraCutoutFragment$AppRow");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.full_screen_apps_camera_cutout;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 48002;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        setHasOptionsMenu(true);
        super.onActivityCreated(bundle);
        new CutoutAppListLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        if (bundle != null) {
            this.mUserInfo = bundle.getParcelable("userInfo");
        }
        this.mAppLabelCache = AppListCacheManager.getAppLabelCache(this.mContext);
        this.mLauncherApps = (LauncherApps) this.mContext.getSystemService("launcherapps");
        getActivity().setTitle(R.string.full_screen_apps_camera_cutout);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menu.clear();
        menuInflater.inflate(R.menu.sec_full_screen_apps, menu);
        MenuItem findItem = menu.findItem(R.id.search_apps);
        this.mSearchMenu = findItem;
        findItem.getIcon()
                .setColorFilter(
                        this.mContext
                                .getResources()
                                .getColor(R.color.sec_search_magnifier_icon_tint_color, null),
                        PorterDuff.Mode.SRC_IN);
        final SearchView searchView = (SearchView) this.mSearchMenu.getActionView();
        LinearLayout linearLayout = (LinearLayout) searchView.findViewById(R.id.search_plate);
        if (linearLayout != null) {
            linearLayout.setPadding(
                    0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
        }
        searchView.setQueryHint(
                this.mContext.getResources().getString(R.string.sec_app_search_title));
        searchView.mOnQueryChangeListener = new AnonymousClass1(this);
        getActivity()
                .getOnBackInvokedDispatcher()
                .registerSystemOnBackInvokedCallback(
                        new OnBackInvokedCallback() { // from class:
                                                      // com.samsung.android.settings.display.CameraCutoutFragment.2
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                SearchView searchView2 = searchView;
                                if (searchView2 == null || !searchView2.hasFocus()) {
                                    CameraCutoutFragment.this.getActivity().finish();
                                    return;
                                }
                                MenuItem menuItem = CameraCutoutFragment.this.mSearchMenu;
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
        View inflate = layoutInflater.inflate(R.layout.sec_camera_cutout_apps, (ViewGroup) null);
        Resources resources = this.mContext.getResources();
        AppPickerView appPickerView =
                (AppPickerView) inflate.findViewById(R.id.sec_hide_camera_cutout_app_picker_view);
        this.mAppPickerView = appPickerView;
        appPickerView.setNestedScrollingEnabled(false);
        this.mAppPickerView.semSetRoundedCorners(15);
        this.mAppPickerView.semSetRoundedCornerColor(
                15, resources.getColor(R.color.sec_widget_round_and_bgcolor));
        this.mAppPickerView.setVisibility(8);
        this.mAppPickerView.mIsCustomViewItemEnabled = true;
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        this.mAppPickerView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        this.mAppPickerView.setScrollBarStyle(33554432);
        this.mAppPickerView.seslSetFillHorizontalPaddingEnabled(true);
        NestedScrollView nestedScrollView =
                (NestedScrollView) inflate.findViewById(R.id.app_nested_view);
        this.mAppNestedView = nestedScrollView;
        nestedScrollView.semSetRoundedCorners(15);
        this.mAppNestedView.semSetRoundedCornerColor(
                15, resources.getColor(R.color.sec_widget_round_and_bgcolor));
        int dimensionPixelSize =
                resources.getDimensionPixelSize(R.dimen.sec_preference_horizontal_padding);
        this.mAppNestedView.semSetRoundedCorners(
                15,
                new Pair(Integer.valueOf(dimensionPixelSize), Integer.valueOf(dimensionPixelSize)));
        int dimensionPixelSize2 =
                resources.getDimensionPixelSize(R.dimen.sec_nested_view_scroll_padding);
        int dimensionPixelSize3 =
                resources.getDimensionPixelSize(R.dimen.sec_nested_view_scroll_end_padding);
        NestedScrollView nestedScrollView2 = this.mAppNestedView;
        nestedScrollView2.mScrollbarTopPadding = dimensionPixelSize2;
        nestedScrollView2.mScrollbarBottomPadding = dimensionPixelSize3 + dimensionPixelSize2;
        SeslViewReflector.semSetScrollBarTopPadding(nestedScrollView2, dimensionPixelSize2);
        SeslViewReflector.semSetScrollBarBottomPadding(
                nestedScrollView2, nestedScrollView2.mScrollbarBottomPadding);
        ((Toolbar) getActivity().findViewById(R.id.action_bar))
                .setBackInvokedCallbackEnabled(false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mUserInfo = arguments.getParcelable("userInfo");
        }
        this.mEmptyViewText = (TextView) inflate.findViewById(android.R.id.empty);
        View findViewById = inflate.findViewById(R.id.loading_panel);
        this.mLoadingPanel = findViewById;
        findViewById.semSetRoundedCorners(3);
        this.mLoadingPanel.semSetRoundedCornerColor(
                3, resources.getColor(R.color.sec_widget_round_and_bgcolor));
        return inflate;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mLoadingPanel.getVisibility() == 0) {
            this.mLoadingPanel.setVisibility(8);
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
        if ((Rune.isSamsungDexMode(this.mContext) || Utils.isDesktopStandaloneMode(this.mContext))
                && !Utils.isNewDexMode(this.mContext)) {
            finish();
        }
        super.onResume();
        MenuItem menuItem = this.mSearchMenu;
        if (menuItem != null) {
            menuItem.collapseActionView();
        }
        if (this.mEmptyViewText != null) {
            updateEmptyTextViewState();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("userInfo", this.mUserInfo);
    }

    public final void updateEmptyTextViewState() {
        if (this.mResultList.size() != 0) {
            this.mEmptyViewText.setVisibility(8);
            this.mAppNestedView.setVisibility(0);
        } else {
            this.mEmptyViewText.setVisibility(0);
            this.mEmptyViewText.setText(R.string.no_items);
            this.mAppNestedView.setVisibility(8);
        }
    }
}
