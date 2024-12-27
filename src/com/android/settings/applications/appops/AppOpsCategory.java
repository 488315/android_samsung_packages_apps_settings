package com.android.settings.applications.appops;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppOpsCategory extends ListFragment implements LoaderManager.LoaderCallbacks {
    public AppListAdapter mAdapter;
    public AppOpsState mState;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppListAdapter extends BaseAdapter {
        public final LayoutInflater mInflater;
        public List mList;
        public final Resources mResources;

        public AppListAdapter(FragmentActivity fragmentActivity) {
            this.mResources = fragmentActivity.getResources();
            this.mInflater = (LayoutInflater) fragmentActivity.getSystemService("layout_inflater");
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            List list = this.mList;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return (AppOpsState.AppOpEntry) this.mList.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.app_ops_item, viewGroup, false);
            }
            AppOpsState.AppOpEntry appOpEntry = (AppOpsState.AppOpEntry) this.mList.get(i);
            ImageView imageView = (ImageView) view.findViewById(R.id.app_icon);
            AppOpsState.AppEntry appEntry = appOpEntry.mApp;
            Drawable drawable = appEntry.mIcon;
            AppOpsState appOpsState = appEntry.mState;
            if (drawable == null) {
                if (appEntry.mApkFile.exists()) {
                    drawable = appEntry.mInfo.loadIcon(appOpsState.mPm);
                    appEntry.mIcon = drawable;
                } else {
                    appEntry.mMounted = false;
                    drawable =
                            appOpsState.mContext.getDrawable(android.R.drawable.sym_def_app_icon);
                }
            } else if (!appEntry.mMounted) {
                if (appEntry.mApkFile.exists()) {
                    appEntry.mMounted = true;
                    drawable = appEntry.mInfo.loadIcon(appOpsState.mPm);
                    appEntry.mIcon = drawable;
                }
                drawable = appOpsState.mContext.getDrawable(android.R.drawable.sym_def_app_icon);
            }
            imageView.setImageDrawable(drawable);
            ((TextView) view.findViewById(R.id.app_name)).setText(appOpEntry.mApp.mLabel);
            ((TextView) view.findViewById(R.id.op_name))
                    .setText(
                            ((AppOpsManager.OpEntry) appOpEntry.mOps.get(0)).isRunning()
                                    ? this.mResources.getText(R.string.app_ops_running)
                                    : appOpEntry.getTime() > 0
                                            ? DateUtils.getRelativeTimeSpanString(
                                                    appOpEntry.getTime(),
                                                    System.currentTimeMillis(),
                                                    60000L,
                                                    262144)
                                            : ApnSettings.MVNO_NONE);
            view.findViewById(R.id.op_time).setVisibility(8);
            CompoundButton compoundButton = (CompoundButton) view.findViewById(R.id.op_switch);
            int i2 = appOpEntry.mOverriddenPrimaryMode;
            if (i2 < 0) {
                i2 = ((AppOpsManager.OpEntry) appOpEntry.mOps.get(0)).getMode();
            }
            compoundButton.setChecked(i2 == 0);
            return view;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppListLoader extends AsyncTaskLoader {
        public List mApps;
        public final InterestingConfigChanges mLastConfig;
        public PackageIntentReceiver mPackageObserver;
        public final AppOpsState mState;
        public final AppOpsState.OpsTemplate mTemplate;

        public AppListLoader(
                FragmentActivity fragmentActivity,
                AppOpsState appOpsState,
                AppOpsState.OpsTemplate opsTemplate) {
            super(fragmentActivity);
            this.mLastConfig = new InterestingConfigChanges();
            this.mState = appOpsState;
            this.mTemplate = opsTemplate;
        }

        @Override // androidx.loader.content.Loader
        public final void deliverResult(Object obj) {
            List list = (List) obj;
            this.mApps = list;
            if (this.mStarted) {
                super.deliverResult(list);
            }
        }

        @Override // androidx.loader.content.AsyncTaskLoader
        public final Object loadInBackground() {
            int[] iArr;
            List<PackageInfo> list;
            AppOpsState appOpsState;
            int i;
            List<PackageInfo> list2;
            AppOpsState appOpsState2;
            int i2;
            String opToPermission;
            AppOpsState.AnonymousClass2 anonymousClass2 = AppOpsState.LABEL_COMPARATOR;
            AppOpsState appOpsState3 = this.mState;
            Context context = appOpsState3.mContext;
            HashMap hashMap = new HashMap();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            int[] iArr2 = new int[149];
            int i3 = 0;
            int i4 = 0;
            while (true) {
                AppOpsState.OpsTemplate opsTemplate = this.mTemplate;
                iArr = opsTemplate.ops;
                if (i4 >= iArr.length) {
                    break;
                }
                if (opsTemplate.showPerms[i4]
                        && (opToPermission = AppOpsManager.opToPermission(iArr[i4])) != null
                        && !arrayList2.contains(opToPermission)) {
                    arrayList2.add(opToPermission);
                    arrayList3.add(Integer.valueOf(opsTemplate.ops[i4]));
                    iArr2[opsTemplate.ops[i4]] = i4;
                }
                i4++;
            }
            List packagesForOps = appOpsState3.mAppOps.getPackagesForOps(iArr);
            ArrayList arrayList4 = null;
            if (packagesForOps != null) {
                for (int i5 = 0; i5 < packagesForOps.size(); i5++) {
                    AppOpsManager.PackageOps packageOps =
                            (AppOpsManager.PackageOps) packagesForOps.get(i5);
                    AppOpsState.AppEntry appEntry =
                            appOpsState3.getAppEntry(
                                    (FragmentActivity) context,
                                    hashMap,
                                    packageOps.getPackageName(),
                                    null);
                    if (appEntry != null) {
                        for (int i6 = 0; i6 < packageOps.getOps().size(); i6++) {
                            AppOpsState.addOp(
                                    arrayList,
                                    appEntry,
                                    (AppOpsManager.OpEntry) packageOps.getOps().get(i6),
                                    true,
                                    0);
                        }
                    }
                }
            }
            String[] strArr = new String[arrayList2.size()];
            arrayList2.toArray(strArr);
            List<PackageInfo> packagesHoldingPermissions =
                    appOpsState3.mPm.getPackagesHoldingPermissions(strArr, 0);
            int i7 = 0;
            while (i7 < packagesHoldingPermissions.size()) {
                PackageInfo packageInfo = packagesHoldingPermissions.get(i7);
                AppOpsState.AppEntry appEntry2 =
                        appOpsState3.getAppEntry(
                                (FragmentActivity) context,
                                hashMap,
                                packageInfo.packageName,
                                packageInfo.applicationInfo);
                if (appEntry2 != null && packageInfo.requestedPermissions != null) {
                    int i8 = i3;
                    ArrayList arrayList5 = arrayList4;
                    while (i8 < packageInfo.requestedPermissions.length) {
                        int[] iArr3 = packageInfo.requestedPermissionsFlags;
                        if (iArr3 == null || (iArr3[i8] & 2) != 0) {
                            int i9 = i3;
                            while (i9 < arrayList2.size()) {
                                if (((String) arrayList2.get(i9))
                                        .equals(packageInfo.requestedPermissions[i8])) {
                                    if (appEntry2.mOps.indexOfKey(
                                                    ((Integer) arrayList3.get(i9)).intValue())
                                            < 0) {
                                        if (arrayList5 == null) {
                                            arrayList5 = new ArrayList();
                                            list2 = packagesHoldingPermissions;
                                            new AppOpsManager.PackageOps(
                                                    packageInfo.packageName,
                                                    packageInfo.applicationInfo.uid,
                                                    arrayList5);
                                        } else {
                                            list2 = packagesHoldingPermissions;
                                        }
                                        appOpsState2 = appOpsState3;
                                        i2 = 0;
                                        AppOpsManager.OpEntry opEntry =
                                                new AppOpsManager.OpEntry(
                                                        ((Integer) arrayList3.get(i9)).intValue(),
                                                        0,
                                                        Collections.emptyMap());
                                        arrayList5.add(opEntry);
                                        AppOpsState.addOp(arrayList, appEntry2, opEntry, true, 0);
                                        i9++;
                                        i3 = i2;
                                        packagesHoldingPermissions = list2;
                                        appOpsState3 = appOpsState2;
                                    }
                                }
                                list2 = packagesHoldingPermissions;
                                appOpsState2 = appOpsState3;
                                i2 = 0;
                                i9++;
                                i3 = i2;
                                packagesHoldingPermissions = list2;
                                appOpsState3 = appOpsState2;
                            }
                            list = packagesHoldingPermissions;
                            appOpsState = appOpsState3;
                            i = i3;
                        } else {
                            list = packagesHoldingPermissions;
                            appOpsState = appOpsState3;
                            i = i3;
                        }
                        i8++;
                        i3 = i;
                        packagesHoldingPermissions = list;
                        appOpsState3 = appOpsState;
                    }
                }
                i7++;
                i3 = i3;
                packagesHoldingPermissions = packagesHoldingPermissions;
                appOpsState3 = appOpsState3;
                arrayList4 = null;
            }
            Collections.sort(arrayList, anonymousClass2);
            return arrayList;
        }

        @Override // androidx.loader.content.AsyncTaskLoader
        public final /* bridge */ /* synthetic */ void onCanceled(Object obj) {}

        @Override // androidx.loader.content.Loader
        public final void onReset() {
            onCancelLoad();
            if (this.mApps != null) {
                this.mApps = null;
            }
            PackageIntentReceiver packageIntentReceiver = this.mPackageObserver;
            if (packageIntentReceiver != null) {
                this.mContext.unregisterReceiver(packageIntentReceiver);
                this.mPackageObserver = null;
            }
        }

        @Override // androidx.loader.content.Loader
        public final void onStartLoading() {
            boolean z;
            onContentChanged();
            List list = this.mApps;
            if (list != null) {
                this.mApps = list;
                if (this.mStarted) {
                    super.deliverResult(list);
                }
            }
            if (this.mPackageObserver == null) {
                this.mPackageObserver = new PackageIntentReceiver(this);
            }
            Resources resources = this.mContext.getResources();
            InterestingConfigChanges interestingConfigChanges = this.mLastConfig;
            int updateFrom =
                    interestingConfigChanges.mLastConfiguration.updateFrom(
                            resources.getConfiguration());
            if (interestingConfigChanges.mLastDensity == resources.getDisplayMetrics().densityDpi
                    && (updateFrom & 772) == 0) {
                z = false;
            } else {
                interestingConfigChanges.mLastDensity = resources.getDisplayMetrics().densityDpi;
                z = true;
            }
            boolean z2 = this.mContentChanged;
            this.mContentChanged = false;
            this.mProcessingChange |= z2;
            if (z2 || this.mApps == null || z) {
                onForceLoad();
            }
        }

        @Override // androidx.loader.content.Loader
        public final void onStopLoading() {
            onCancelLoad();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InterestingConfigChanges {
        public final Configuration mLastConfiguration = new Configuration();
        public int mLastDensity;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PackageIntentReceiver extends BroadcastReceiver {
        public final AppListLoader mLoader;

        public PackageIntentReceiver(AppListLoader appListLoader) {
            this.mLoader = appListLoader;
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addDataScheme("package");
            appListLoader.mContext.registerReceiver(this, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
            appListLoader.mContext.registerReceiver(this, intentFilter2);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            this.mLoader.onContentChanged();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ensureList();
        TextView textView = this.mStandardEmptyView;
        if (textView == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        textView.setText("No applications");
        if (this.mEmptyText == null) {
            this.mList.setEmptyView(this.mStandardEmptyView);
        }
        this.mEmptyText = "No applications";
        setHasOptionsMenu(true);
        AppListAdapter appListAdapter = new AppListAdapter(getActivity());
        this.mAdapter = appListAdapter;
        setListAdapter(appListAdapter);
        setListShown(false, true);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mState = new AppOpsState(getActivity());
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader onCreateLoader(int i, Bundle bundle) {
        Bundle arguments = getArguments();
        return new AppListLoader(
                getActivity(),
                this.mState,
                arguments != null
                        ? (AppOpsState.OpsTemplate) arguments.getParcelable("template")
                        : null);
    }

    @Override // androidx.fragment.app.ListFragment
    public final void onListItemClick(View view, int i) {
        AppOpsState.AppOpEntry appOpEntry = (AppOpsState.AppOpEntry) this.mAdapter.mList.get(i);
        if (appOpEntry != null) {
            CompoundButton compoundButton = (CompoundButton) view.findViewById(R.id.op_switch);
            boolean isChecked = compoundButton.isChecked();
            compoundButton.setChecked(!isChecked);
            AppOpsManager.OpEntry opEntry = (AppOpsManager.OpEntry) appOpEntry.mOps.get(0);
            AppOpsManager appOpsManager = this.mState.mAppOps;
            int op = opEntry.getOp();
            ApplicationInfo applicationInfo = appOpEntry.mApp.mInfo;
            appOpsManager.setMode(
                    op, applicationInfo.uid, applicationInfo.packageName, isChecked ? 1 : 0);
            appOpEntry.mOverriddenPrimaryMode = isChecked ? 1 : 0;
        }
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader loader, Object obj) {
        AppListAdapter appListAdapter = this.mAdapter;
        appListAdapter.mList = (List) obj;
        appListAdapter.notifyDataSetChanged();
        if (isResumed()) {
            setListShown(true, true);
        } else {
            setListShown(true, false);
        }
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {
        AppListAdapter appListAdapter = this.mAdapter;
        appListAdapter.mList = null;
        appListAdapter.notifyDataSetChanged();
    }
}
