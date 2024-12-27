package com.samsung.android.settings.display;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.SemUserInfo;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.DisplaySettings;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settingslib.applications.cachedb.AppListCacheManager;
import com.samsung.android.view.SemWindowManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AspectRatioFragment extends SettingsPreferenceFragment {
    public HashMap mAppLabelCache;
    public View mAppNestView;
    public AppPickerView mAppPickerView;
    public View mAspectRatioLayout;
    public RequestHighlight mAsyncTask;
    public FragmentActivity mContext;
    public TextView mEmptyViewText;
    public ExecutorService mExecutor;
    public String mHighlightKey;
    public LauncherApps mLauncherApps;
    public View mLoadingPanel;
    public MenuItem mSearchMenu;
    public SemUserInfo mUserInfo;
    public AppPickerView.ViewHolder mViewHolder;
    public IWindowManager mWindowManager;
    public final List mPackageList = new ArrayList();
    public List mResultList = new ArrayList();
    public final List mPackageListTemp = new ArrayList();
    public final Map mSystemDefaultValues = new ConcurrentHashMap();
    public final ArrayMap mAppRows = new ArrayMap();
    public int mHighlightPosition = -1;
    public boolean mHighlightRequest = false;
    public boolean mIsSpinnerItemClicked = false;
    public int mItemCount = 0;
    public final AspectRatioFragment$$ExternalSyntheticLambda1 mHighlightViewRunnable =
            new AspectRatioFragment$$ExternalSyntheticLambda1(0, this);
    public final AnonymousClass6 mHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.display.AspectRatioFragment.6
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 1) {
                        return;
                    }
                    new RequestHighlight(AspectRatioFragment.this, 0)
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.AspectRatioFragment$1, reason: invalid class name */
    public final class AnonymousClass1 implements SearchView.OnQueryTextListener {
        public AnonymousClass1() {}

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public final boolean onQueryTextChange(String str) {
            AspectRatioFragment aspectRatioFragment = AspectRatioFragment.this;
            AppPickerView appPickerView = aspectRatioFragment.mAppPickerView;
            if (appPickerView == null || appPickerView.getAdapter() == null) {
                return false;
            }
            aspectRatioFragment.mAppPickerView.setSearchFilter(str, new AnonymousClass5(this, str));
            return false;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public final void onQueryTextSubmit(String str) {
            AspectRatioFragment aspectRatioFragment = AspectRatioFragment.this;
            AppPickerView appPickerView = aspectRatioFragment.mAppPickerView;
            if (appPickerView == null || appPickerView.getAdapter() == null) {
                return;
            }
            aspectRatioFragment.mAppPickerView.setSearchFilter(str, null);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.AspectRatioFragment$5, reason: invalid class name */
    public final class AnonymousClass5
            implements AppPickerView.OnSearchFilterListener, AppPickerView.OnBindListener {
        public final /* synthetic */ Object this$0;
        public final /* synthetic */ Object val$rows;

        public /* synthetic */ AnonymousClass5(Object obj, Object obj2) {
            this.this$0 = obj;
            this.val$rows = obj2;
        }

        @Override // androidx.apppickerview.widget.AppPickerView.OnBindListener
        public void onBindViewHolder(AppPickerView.ViewHolder viewHolder, int i, String str) {
            final AppRow appRow;
            int size = ((ArrayMap) this.val$rows).size() + 2;
            AspectRatioFragment aspectRatioFragment = (AspectRatioFragment) this.this$0;
            if (i < size) {
                if (!((ArrayMap) this.val$rows).containsKey(str)) {
                    Log.e("AspectRatioFragment", "skipped unexpected package : " + str);
                    return;
                }
                if ((viewHolder instanceof AppPickerView.CustomViewItemViewHolder)
                        || (viewHolder instanceof AppPickerView.SeparatorViewHolder)
                        || (appRow = (AppRow) ((ArrayMap) this.val$rows).get(str)) == null) {
                    return;
                }
                ((LinearLayout) viewHolder.mAppIconContainer.getParent())
                        .setContentDescription(null);
                if (appRow.mType != 3) {
                    viewHolder.mWidgetContainer.setVisibility(0);
                    View view = viewHolder.itemView;
                    final SwitchCompat switchCompat = viewHolder.mSwitch;
                    view.setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.display.AspectRatioFragment.5.1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    switchCompat.toggle();
                                }
                            });
                    switchCompat.setVisibility(0);
                    switchCompat.setOnCheckedChangeListener(
                            new CompoundButton
                                    .OnCheckedChangeListener() { // from class:
                                                                 // com.samsung.android.settings.display.AspectRatioFragment.5.2
                                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                public final void onCheckedChanged(
                                        CompoundButton compoundButton, boolean z) {
                                    AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                            "Click switch : ", "AspectRatioFragment", z);
                                    AppRow appRow2 = appRow;
                                    appRow2.mType = z ? 1 : 0;
                                    AspectRatioFragment aspectRatioFragment2 =
                                            (AspectRatioFragment) AnonymousClass5.this.this$0;
                                    String str2 = appRow2.mPackage;
                                    int i2 = appRow2.mUid;
                                    aspectRatioFragment2.getClass();
                                    Log.d(
                                            "AspectRatioFragment",
                                            "setMaxAspectPackage : "
                                                    + str2
                                                    + " / "
                                                    + z
                                                    + " for uid: "
                                                    + i2);
                                    try {
                                        aspectRatioFragment2.mWindowManager.setMaxAspectRatioPolicy(
                                                str2, i2, z, -1);
                                    } catch (RemoteException unused) {
                                        Log.d(
                                                "AspectRatioFragment",
                                                "setMaxAspectPackage() RemoteException");
                                    }
                                }
                            });
                    switchCompat.setChecked(appRow.mType == 1);
                }
                aspectRatioFragment.mExecutor.execute(
                        aspectRatioFragment.new LoadAppIconTask(viewHolder, appRow));
            }
            if (aspectRatioFragment.mHighlightRequest
                    && !TextUtils.isEmpty(aspectRatioFragment.mHighlightKey)
                    && aspectRatioFragment.mHighlightKey.equals(str)) {
                aspectRatioFragment.mViewHolder = viewHolder;
                synchronized (aspectRatioFragment.mAppPickerView) {
                    aspectRatioFragment.mAppPickerView.removeCallbacks(
                            aspectRatioFragment.mHighlightViewRunnable);
                    aspectRatioFragment.mAppPickerView.postDelayed(
                            aspectRatioFragment.mHighlightViewRunnable, 600L);
                }
            }
        }

        @Override // androidx.apppickerview.widget.AppPickerView.OnSearchFilterListener
        public void onSearchFilterCompleted(int i) {
            boolean z = ((String) this.val$rows).isEmpty() && i == 2;
            AnonymousClass1 anonymousClass1 = (AnonymousClass1) this.this$0;
            AspectRatioFragment.this.mAppPickerView.setVisibility((i == 0 || z) ? 8 : 0);
            AspectRatioFragment aspectRatioFragment = AspectRatioFragment.this;
            if (aspectRatioFragment.mEmptyViewText != null) {
                if (aspectRatioFragment.mAppPickerView.getVisibility() != 8) {
                    aspectRatioFragment.mEmptyViewText.setVisibility(8);
                    aspectRatioFragment.mAppNestView.setVisibility(0);
                } else {
                    aspectRatioFragment.mEmptyViewText.setText(R.string.sec_app_search_no_result);
                    aspectRatioFragment.mEmptyViewText.setVisibility(0);
                    aspectRatioFragment.mAppNestView.setVisibility(8);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppRow {
        public CharSequence mLabel;
        public LauncherActivityInfo mLauncherActivityInfo;
        public String mPackage;
        public int mType;
        public int mUid;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FullScreenAppsListAdapter extends CustomListAdapter {
        public FullScreenAppsListAdapter(Context context, AppPickerIconLoader appPickerIconLoader) {
            super(context, 0, appPickerIconLoader);
        }

        @Override // androidx.apppickerview.widget.ListAdapter,
                  // androidx.recyclerview.widget.RecyclerView.Adapter
        public final AppPickerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i != -10) {
                return super.onCreateViewHolder(viewGroup, i);
            }
            AspectRatioFragment.this.getClass();
            View inflate =
                    LayoutInflater.from(this.mContext)
                            .inflate(R.layout.full_screen_aspect_ratio_preview, viewGroup, false);
            View findViewById = inflate.findViewById(R.id.full_screen_image_container);
            if (findViewById != null) {
                findViewById.semSetRoundedCorners(15);
                findViewById.semSetRoundedCornerColor(
                        15, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
            }
            TextView textView = (TextView) inflate.findViewById(R.id.full_screen_desc);
            AspectRatioFragment.this.getClass();
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            this.mContext
                                    .getResources()
                                    .getString(R.string.full_screen_apps_summary),
                            " ");
            m.append(
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_apps_running_in_background));
            textView.setText(m.toString());
            return new AppPickerView.CustomViewItemViewHolder(inflate);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LoadAppIconTask implements Runnable {
        public final AppPickerView.ViewHolder holder;
        public final AppRow row;

        public LoadAppIconTask(AppPickerView.ViewHolder viewHolder, AppRow appRow) {
            this.holder = viewHolder;
            this.row = appRow;
        }

        @Override // java.lang.Runnable
        public final void run() {
            final Drawable badgedIcon =
                    this.row.mLauncherActivityInfo.getBadgedIcon(
                            AspectRatioFragment.this
                                    .mContext
                                    .getResources()
                                    .getDisplayMetrics()
                                    .densityDpi);
            final CharSequence label = this.row.mLauncherActivityInfo.getLabel();
            post(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.display.AspectRatioFragment$LoadAppIconTask$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AspectRatioFragment.LoadAppIconTask loadAppIconTask =
                                    AspectRatioFragment.LoadAppIconTask.this;
                            Drawable drawable = badgedIcon;
                            CharSequence charSequence = label;
                            loadAppIconTask.holder.mAppIcon.setImageDrawable(drawable);
                            loadAppIconTask.holder.mAppName.setText(charSequence);
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RequestHighlight extends AsyncTask {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AspectRatioFragment this$0;

        public /* synthetic */ RequestHighlight(AspectRatioFragment aspectRatioFragment, int i) {
            this.$r8$classId = i;
            this.this$0 = aspectRatioFragment;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            List list;
            switch (this.$r8$classId) {
                case 0:
                    for (int i = 0;
                            i < this.this$0.mAppPickerView.getAdapter().getItemCount();
                            i++) {
                        if (this.this$0.mAppPickerView.getAppLabelInfo(i) != null) {
                            AspectRatioFragment aspectRatioFragment = this.this$0;
                            if (aspectRatioFragment.mHighlightKey.equals(
                                    aspectRatioFragment.mAppPickerView.getAppLabelInfo(i)
                                            .mPackageName)) {
                                return Integer.valueOf(i);
                            }
                        }
                    }
                    return -1;
                default:
                    if (this.this$0.getActivity() == null) {
                        return new ArrayList();
                    }
                    synchronized (this) {
                        try {
                            ((ArrayList) this.this$0.mPackageListTemp).clear();
                            ((ConcurrentHashMap) this.this$0.mSystemDefaultValues).clear();
                            this.this$0.mAppRows.clear();
                            AspectRatioFragment aspectRatioFragment2 = this.this$0;
                            aspectRatioFragment2.mItemCount = 0;
                            PackageManager packageManager =
                                    aspectRatioFragment2.getActivity().getPackageManager();
                            AspectRatioFragment aspectRatioFragment3 = this.this$0;
                            List<LauncherActivityInfo> activityList =
                                    aspectRatioFragment3.mLauncherApps.getActivityList(
                                            null, aspectRatioFragment3.mUserInfo.getUserHandle());
                            Log.d("AspectRatioFragment", "  launchable activities:");
                            for (LauncherActivityInfo launcherActivityInfo : activityList) {
                                Log.d(
                                        "AspectRatioFragment",
                                        "    "
                                                + launcherActivityInfo
                                                        .getComponentName()
                                                        .toString());
                                ApplicationInfo applicationInfo =
                                        launcherActivityInfo.getApplicationInfo();
                                String str = applicationInfo.packageName;
                                if (!KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(str)
                                        && !this.this$0.mAppRows.containsKey(str)) {
                                    this.this$0.getClass();
                                    AppRow m1201$$Nest$mloadAppRow =
                                            AspectRatioFragment.m1201$$Nest$mloadAppRow(
                                                    this.this$0,
                                                    packageManager,
                                                    applicationInfo,
                                                    launcherActivityInfo);
                                    this.this$0.getClass();
                                    int i2 = m1201$$Nest$mloadAppRow.mType;
                                    if (i2 != 2 && i2 != 3 && i2 != 4) {
                                        ((ArrayList) this.this$0.mPackageListTemp).add(str);
                                        AspectRatioFragment aspectRatioFragment4 = this.this$0;
                                        aspectRatioFragment4.mItemCount++;
                                        aspectRatioFragment4.mAppRows.put(
                                                str, m1201$$Nest$mloadAppRow);
                                    }
                                }
                            }
                            list = this.this$0.mPackageListTemp;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return list;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    Integer num = (Integer) obj;
                    super.onPostExecute(num);
                    if (num.intValue() >= 0) {
                        this.this$0.mHighlightPosition = num.intValue();
                        Preference$$ExternalSyntheticOutline0.m(
                                new StringBuilder("mHighlightPosition = "),
                                this.this$0.mHighlightPosition,
                                "AspectRatioFragment");
                        this.this$0.mAppPickerView.setItemAnimator(null);
                        this.this$0.mAppPickerView.smoothScrollToPosition(num.intValue());
                        this.this$0.mAppPickerView.postDelayed(
                                new AspectRatioFragment$$ExternalSyntheticLambda3(1, this, num),
                                600L);
                        break;
                    }
                    break;
                default:
                    List list = (List) obj;
                    this.this$0.mLoadingPanel.setVisibility(8);
                    ((ArrayList) this.this$0.mPackageList).clear();
                    AspectRatioFragment aspectRatioFragment = this.this$0;
                    ((ArrayList) aspectRatioFragment.mPackageList)
                            .addAll(aspectRatioFragment.mPackageListTemp);
                    AspectRatioFragment aspectRatioFragment2 = this.this$0;
                    aspectRatioFragment2.mResultList = list;
                    aspectRatioFragment2.mAppPickerView.setVisibility(0);
                    for (int i = 0; i < this.this$0.mAppPickerView.getItemDecorationCount(); i++) {
                        this.this$0.mAppPickerView.removeItemDecorationAt(i);
                    }
                    AspectRatioFragment aspectRatioFragment3 = this.this$0;
                    FullScreenAppsListAdapter fullScreenAppsListAdapter =
                            aspectRatioFragment3
                            .new FullScreenAppsListAdapter(
                                    aspectRatioFragment3.mContext,
                                    aspectRatioFragment3.mAppPickerView.mAppPickerIconLoader);
                    fullScreenAppsListAdapter.resetPackages(this.this$0.mPackageList, false);
                    AppPickerView appPickerView = this.this$0.mAppPickerView;
                    appPickerView.mAdapter = fullScreenAppsListAdapter;
                    appPickerView.setAppPickerView(0, null, 2);
                    this.this$0.mAppPickerView.addCustomViewItem$1();
                    this.this$0.mAppPickerView.addSeparator();
                    this.this$0.getClass();
                    AspectRatioFragment aspectRatioFragment4 = this.this$0;
                    AppPickerView appPickerView2 = aspectRatioFragment4.mAppPickerView;
                    AnonymousClass5 anonymousClass5 =
                            new AnonymousClass5(
                                    aspectRatioFragment4, aspectRatioFragment4.mAppRows);
                    AbsAdapter absAdapter = appPickerView2.mAdapter;
                    if (absAdapter != null) {
                        absAdapter.mOnBindListener = anonymousClass5;
                    }
                    if (((ArrayList) aspectRatioFragment4.mPackageList).isEmpty()) {
                        this.this$0.mAppPickerView.setVisibility(8);
                        MenuItem menuItem = this.this$0.mSearchMenu;
                        if (menuItem != null) {
                            menuItem.setVisible(false);
                        }
                    } else {
                        MenuItem menuItem2 = this.this$0.mSearchMenu;
                        if (menuItem2 != null) {
                            menuItem2.setVisible(true);
                        }
                        if (!TextUtils.isEmpty(this.this$0.mHighlightKey)) {
                            sendEmptyMessage(1);
                        }
                    }
                    if (list.size() == 0) {
                        this.this$0.mEmptyViewText.setVisibility(0);
                        this.this$0.mAppNestView.setVisibility(8);
                        AspectRatioFragment aspectRatioFragment5 = this.this$0;
                        aspectRatioFragment5.mEmptyViewText.setTextAppearance(
                                aspectRatioFragment5.mContext, R.style.no_item_text_style);
                        final AspectRatioFragment aspectRatioFragment6 = this.this$0;
                        View view = aspectRatioFragment6.mAspectRatioLayout;
                        if (view != null) {
                            view.getViewTreeObserver()
                                    .addOnGlobalLayoutListener(
                                            new ViewTreeObserver
                                                    .OnGlobalLayoutListener() { // from class:
                                                                                // com.samsung.android.settings.display.AspectRatioFragment.7
                                                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                                                public final void onGlobalLayout() {
                                                    View view2 =
                                                            AspectRatioFragment.this
                                                                    .mAspectRatioLayout;
                                                    if (view2 != null) {
                                                        view2.getViewTreeObserver()
                                                                .removeGlobalOnLayoutListener(this);
                                                        AspectRatioFragment.this.mEmptyViewText
                                                                .setMaxWidth(
                                                                        (AspectRatioFragment.this
                                                                                                .mAspectRatioLayout
                                                                                                .getMeasuredWidth()
                                                                                        / 4)
                                                                                * 3);
                                                        AspectRatioFragment.this.mEmptyViewText
                                                                .setGravity(17);
                                                    }
                                                }
                                            });
                            break;
                        }
                    } else {
                        this.this$0.mEmptyViewText.setVisibility(8);
                        this.this$0.mAppNestView.setVisibility(0);
                        break;
                    }
                    break;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            switch (this.$r8$classId) {
                case 0:
                    super.onPreExecute();
                    break;
                default:
                    this.this$0.mLoadingPanel.setVisibility(0);
                    this.this$0.mAppPickerView.setVisibility(8);
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mloadAppRow, reason: not valid java name */
    public static AppRow m1201$$Nest$mloadAppRow(
            AspectRatioFragment aspectRatioFragment,
            PackageManager packageManager,
            ApplicationInfo applicationInfo,
            LauncherActivityInfo launcherActivityInfo) {
        aspectRatioFragment.getClass();
        AppRow appRow = new AppRow();
        new ArrayList();
        new ArrayList();
        String str = applicationInfo.packageName;
        appRow.mPackage = str;
        appRow.mUid = applicationInfo.uid;
        appRow.mLauncherActivityInfo = launcherActivityInfo;
        try {
            HashMap hashMap = aspectRatioFragment.mAppLabelCache;
            CharSequence charSequence = hashMap == null ? null : (CharSequence) hashMap.get(str);
            if (TextUtils.isEmpty(charSequence)) {
                appRow.mLabel = applicationInfo.loadLabel(packageManager);
            } else {
                appRow.mLabel = charSequence;
            }
        } catch (RuntimeException e) {
            Log.e(
                    "AspectRatioFragment",
                    "Error loading application label for " + appRow.mPackage,
                    e);
            appRow.mLabel = appRow.mPackage;
        }
        if (TextUtils.isEmpty(appRow.mLabel)) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("AppLabel is still empty, set packageName = "),
                    appRow.mPackage,
                    "AspectRatioFragment");
            appRow.mLabel = appRow.mPackage;
        }
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.clear();
        concurrentHashMap.put(applicationInfo.packageName, Float.valueOf(0.0f));
        String str2 = appRow.mPackage;
        int i = appRow.mUid;
        int i2 = 0;
        try {
            i2 = aspectRatioFragment.mWindowManager.getMaxAspectRatioPolicy(str2, i);
            Log.d(
                    "AspectRatioFragment",
                    "getMaxAspectRatioPolicy() : " + str2 + " / " + i2 + " for uid: " + i);
        } catch (RemoteException unused) {
            Log.d("AspectRatioFragment", "getMaxAspectRatioPolicy() RemoteException");
        }
        appRow.mType = i2;
        return appRow;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.full_screen_apps_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 48001;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        setHasOptionsMenu(true);
        super.onActivityCreated(bundle);
        RequestHighlight requestHighlight = new RequestHighlight(this, 1);
        this.mAsyncTask = requestHighlight;
        requestHighlight.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        if (SemWindowManager.isSupportAspectRatioMode(activity)) {
            return;
        }
        finishFragment();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        Bundle arguments = getArguments();
        if (arguments != null) {
            SemUserInfo parcelable = arguments.getParcelable("userInfo");
            this.mUserInfo = parcelable;
            if (parcelable == null) {
                this.mUserInfo =
                        UserManager.get(this.mContext).semGetSemUserInfo(UserHandle.myUserId());
            }
            String string = arguments.getString(":settings:fragment_args_key");
            this.mHighlightKey = string;
            if (string != null) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("mHighlightKey = "),
                        this.mHighlightKey,
                        "AspectRatioFragment");
            }
            arguments.clear();
        }
        this.mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        this.mLauncherApps = (LauncherApps) this.mContext.getSystemService("launcherapps");
        this.mAppLabelCache = AppListCacheManager.getAppLabelCache(this.mContext);
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menu.clear();
        menuInflater.inflate(R.menu.sec_front_screen_apps, menu);
        MenuItem findItem = menu.findItem(R.id.search_apps);
        this.mSearchMenu = findItem;
        findItem.getIcon()
                .setColorFilter(
                        this.mContext
                                .getResources()
                                .getColor(R.color.sec_search_magnifier_icon_tint_color),
                        PorterDuff.Mode.SRC_IN);
        final SearchView searchView = (SearchView) this.mSearchMenu.getActionView();
        LinearLayout linearLayout = (LinearLayout) searchView.findViewById(R.id.search_plate);
        if (linearLayout != null) {
            linearLayout.setPadding(
                    0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
        }
        searchView.setQueryHint(getString(R.string.sec_app_search_title));
        searchView.mOnQueryChangeListener = new AnonymousClass1();
        searchView.mOnQueryTextFocusChangeListener =
                new View
                        .OnFocusChangeListener() { // from class:
                                                   // com.samsung.android.settings.display.AspectRatioFragment.2
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        if (z) {
                            View childAt = AspectRatioFragment.this.mAppPickerView.getChildAt(0);
                            if (childAt instanceof LinearLayout) {
                                AspectRatioFragment.this.mAppPickerView.mIsCustomViewItemEnabled =
                                        false;
                            } else {
                                Log.w("AspectRatioFragment", "Unknown child=" + childAt);
                            }
                        } else {
                            AspectRatioFragment.this.mAppPickerView.mIsCustomViewItemEnabled = true;
                        }
                        TextView textView = AspectRatioFragment.this.mEmptyViewText;
                        if (textView != null) {
                            if (!z && textView.getVisibility() == 0) {
                                Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
                                String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                                AspectRatioFragment.this.mEmptyViewText.setText(
                                        R.string.full_screen_apps_optimized_for_mobile);
                            } else if (z) {
                                AspectRatioFragment aspectRatioFragment = AspectRatioFragment.this;
                                if (aspectRatioFragment.mItemCount == 0) {
                                    aspectRatioFragment.mEmptyViewText.setText(
                                            R.string.sec_app_search_no_result);
                                }
                            }
                        }
                    }
                };
        getActivity()
                .getOnBackInvokedDispatcher()
                .registerSystemOnBackInvokedCallback(
                        new OnBackInvokedCallback() { // from class:
                                                      // com.samsung.android.settings.display.AspectRatioFragment.3
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                SearchView searchView2 = searchView;
                                if (searchView2 == null || !searchView2.hasFocus()) {
                                    AspectRatioFragment.this.getActivity().finish();
                                    return;
                                }
                                MenuItem menuItem = AspectRatioFragment.this.mSearchMenu;
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
                layoutInflater.inflate(R.layout.sec_aspect_ratio_view_layout, (ViewGroup) null);
        this.mAspectRatioLayout = inflate;
        AppPickerView appPickerView =
                (AppPickerView) inflate.findViewById(R.id.sec_aspect_ratio_app_picker_view);
        this.mAppPickerView = appPickerView;
        appPickerView.setNestedScrollingEnabled(false);
        AppPickerView appPickerView2 = this.mAppPickerView;
        appPickerView2.mIsCustomViewItemEnabled = true;
        appPickerView2.semSetRoundedCorners(15);
        this.mAppPickerView.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mAppPickerView.setVisibility(8);
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        this.mAppPickerView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        this.mAppPickerView.setScrollBarStyle(33554432);
        this.mAppPickerView.seslSetFillHorizontalPaddingEnabled(true);
        View findViewById = this.mAspectRatioLayout.findViewById(R.id.app_nested);
        this.mAppNestView = findViewById;
        findViewById.semSetRoundedCorners(15);
        this.mAppNestView.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mAppNestView.semSetRoundedCornerOffset(
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_preference_horizontal_padding));
        ((Toolbar) getActivity().findViewById(R.id.action_bar))
                .setBackInvokedCallbackEnabled(false);
        TextView textView = (TextView) this.mAspectRatioLayout.findViewById(android.R.id.empty);
        this.mEmptyViewText = textView;
        if (textView != null) {
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            textView.setText(R.string.full_screen_apps_optimized_for_mobile);
        }
        final AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        if (appBarLayout != null) {
            appBarLayout.addOnOffsetChangedListener(
                    new AppBarLayout
                            .OnOffsetChangedListener() { // from class:
                                                         // com.samsung.android.settings.display.AspectRatioFragment$$ExternalSyntheticLambda0
                        @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
                        public final void onOffsetChanged(AppBarLayout appBarLayout2, int i) {
                            int totalScrollRange;
                            AspectRatioFragment aspectRatioFragment = AspectRatioFragment.this;
                            TextView textView2 = aspectRatioFragment.mEmptyViewText;
                            if (textView2 == null || textView2.getVisibility() != 0) {
                                return;
                            }
                            int height =
                                    ((Toolbar)
                                                    aspectRatioFragment
                                                            .getActivity()
                                                            .findViewById(R.id.action_bar))
                                            .getHeight();
                            Rect rect = new Rect();
                            AppBarLayout appBarLayout3 = appBarLayout;
                            appBarLayout3.getWindowVisibleDisplayFrame(rect);
                            ViewGroup.LayoutParams layoutParams = textView2.getLayoutParams();
                            int i2 = (rect.bottom - rect.top) - height;
                            if (Math.abs(i) >= appBarLayout3.getTotalScrollRange()) {
                                Log.i("AspectRatioFragment", "new height MATCH_PARENT");
                                totalScrollRange = -1;
                            } else {
                                totalScrollRange =
                                        i2 - (appBarLayout3.getTotalScrollRange() - Math.abs(i));
                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                        totalScrollRange, "new height : ", "AspectRatioFragment");
                            }
                            if (layoutParams.height != totalScrollRange) {
                                layoutParams.height = totalScrollRange;
                                textView2.post(
                                        new AspectRatioFragment$$ExternalSyntheticLambda3(
                                                0, textView2, layoutParams));
                            }
                        }
                    });
        }
        View findViewById2 = this.mAspectRatioLayout.findViewById(R.id.loading_panel);
        this.mLoadingPanel = findViewById2;
        findViewById2.semSetRoundedCorners(3);
        this.mLoadingPanel.semSetRoundedCornerColor(
                3, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        return this.mAspectRatioLayout;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mIsSpinnerItemClicked = false;
        if (this.mAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            Log.d("AspectRatioFragment", "App list load canceled");
            this.mAsyncTask.cancel(true);
        }
        this.mResultList.clear();
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
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = this.mSearchMenu;
        if (menuItem != null) {
            menuItem.setVisible(this.mResultList.size() != 0);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        if (Rune.isSamsungDexMode(this.mContext) || Utils.isDesktopStandaloneMode(this.mContext)) {
            finish();
        }
        super.onResume();
        MenuItem menuItem = this.mSearchMenu;
        if (menuItem != null) {
            menuItem.collapseActionView();
        }
        if (this.mEmptyViewText != null) {
            if (this.mResultList.size() == 0) {
                this.mEmptyViewText.setVisibility(0);
                this.mAppNestView.setVisibility(8);
            } else {
                this.mEmptyViewText.setVisibility(8);
                this.mAppNestView.setVisibility(0);
            }
        }
        if (this.mIsSpinnerItemClicked) {
            if (this.mAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                this.mAsyncTask.cancel(true);
            }
            RequestHighlight requestHighlight = new RequestHighlight(this, 1);
            this.mAsyncTask = requestHighlight;
            requestHighlight.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("userInfo", this.mUserInfo);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle == null || !bundle.containsKey("userInfo")) {
            return;
        }
        this.mUserInfo = (SemUserInfo) bundle.getParcelable("userInfo", SemUserInfo.class);
    }
}
