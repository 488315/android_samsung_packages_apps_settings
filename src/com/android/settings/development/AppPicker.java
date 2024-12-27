package com.android.settings.development;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppPicker extends ListActivity {
    public static final AnonymousClass1 sDisplayNameComparator =
            new Comparator() { // from class: com.android.settings.development.AppPicker.1
                public final Collator collator = Collator.getInstance();

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.collator.compare(
                            ((MyApplicationInfo) obj).label, ((MyApplicationInfo) obj2).label);
                }
            };
    public AppListAdapter mAdapter;
    public boolean mDebuggableOnly;
    public boolean mIncludeNothing;
    public boolean mNonSystemOnly;
    public String mPermissionName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppListAdapter extends ArrayAdapter {
        public final LayoutInflater mInflater;
        public final List mPackageInfoList;

        public AppListAdapter(Context context) {
            super(context, 0);
            this.mPackageInfoList = new ArrayList();
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            List<ApplicationInfo> installedApplications =
                    context.getPackageManager().getInstalledApplications(0);
            for (int i = 0; i < installedApplications.size(); i++) {
                ApplicationInfo applicationInfo = installedApplications.get(i);
                if (applicationInfo.uid != 1000
                        && ((!AppPicker.this.mDebuggableOnly
                                        || (applicationInfo.flags & 2) != 0
                                        || !"user".equals(Build.TYPE))
                                && (!AppPicker.this.mNonSystemOnly
                                        || !applicationInfo.isSystemApp()))) {
                    if (AppPicker.this.mPermissionName != null) {
                        try {
                            String[] strArr =
                                    AppPicker.this
                                            .getPackageManager()
                                            .getPackageInfo(applicationInfo.packageName, 4096)
                                            .requestedPermissions;
                            if (strArr != null) {
                                for (String str : strArr) {
                                    if (!str.equals(AppPicker.this.mPermissionName)) {}
                                }
                            }
                        } catch (PackageManager.NameNotFoundException unused) {
                        }
                    }
                    MyApplicationInfo myApplicationInfo = new MyApplicationInfo();
                    myApplicationInfo.info = applicationInfo;
                    myApplicationInfo.label =
                            applicationInfo
                                    .loadLabel(AppPicker.this.getPackageManager())
                                    .toString();
                    ((ArrayList) this.mPackageInfoList).add(myApplicationInfo);
                    break;
                }
            }
            Collections.sort(this.mPackageInfoList, AppPicker.sDisplayNameComparator);
            if (AppPicker.this.mIncludeNothing) {
                MyApplicationInfo myApplicationInfo2 = new MyApplicationInfo();
                myApplicationInfo2.label = context.getText(R.string.no_application);
                ((ArrayList) this.mPackageInfoList).add(0, myApplicationInfo2);
            }
            addAll(this.mPackageInfoList);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            AppViewHolder createOrRecycle = AppViewHolder.createOrRecycle(this.mInflater, view);
            View view2 = createOrRecycle.rootView;
            MyApplicationInfo myApplicationInfo = (MyApplicationInfo) getItem(i);
            createOrRecycle.appName.setText(myApplicationInfo.label);
            ApplicationInfo applicationInfo = myApplicationInfo.info;
            if (applicationInfo != null) {
                createOrRecycle.appIcon.setImageDrawable(
                        applicationInfo.loadIcon(AppPicker.this.getPackageManager()));
                createOrRecycle.summary.setText(myApplicationInfo.info.packageName);
            } else {
                createOrRecycle.appIcon.setImageDrawable(null);
                createOrRecycle.summary.setText(ApnSettings.MVNO_NONE);
            }
            createOrRecycle.disabled.setVisibility(8);
            createOrRecycle.widget.setVisibility(8);
            return view2;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MyApplicationInfo {
        public ApplicationInfo info;
        public CharSequence label;
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        setResult(0);
        super.onBackPressed();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.mPermissionName =
                getIntent().getStringExtra("com.android.settings.extra.REQUESTIING_PERMISSION");
        this.mDebuggableOnly =
                getIntent().getBooleanExtra("com.android.settings.extra.DEBUGGABLE", false);
        this.mNonSystemOnly =
                getIntent().getBooleanExtra("com.android.settings.extra.NON_SYSTEM", false);
        this.mIncludeNothing =
                getIntent().getBooleanExtra("com.android.settings.extra.INCLUDE_NOTHING", true);
        AppListAdapter appListAdapter = new AppListAdapter(this);
        this.mAdapter = appListAdapter;
        if (appListAdapter.getCount() > 0) {
            setListAdapter(this.mAdapter);
        } else {
            setResult(-2);
            finish();
        }
    }

    @Override // android.app.ListActivity
    public final void onListItemClick(ListView listView, View view, int i, long j) {
        MyApplicationInfo myApplicationInfo = (MyApplicationInfo) this.mAdapter.getItem(i);
        Intent intent = new Intent();
        ApplicationInfo applicationInfo = myApplicationInfo.info;
        if (applicationInfo != null) {
            intent.setAction(applicationInfo.packageName);
        }
        setResult(-1, intent);
        finish();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            setResult(0);
            finish();
        }
        return true;
    }
}
