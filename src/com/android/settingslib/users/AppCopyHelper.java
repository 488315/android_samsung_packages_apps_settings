package com.android.settingslib.users;

import android.app.AppGlobals;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;

import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppCopyHelper {
    public final IPackageManager mIPm;
    public final PackageManager mPackageManager;
    public final ArraySet mSelectedPackages;
    public final UserHandle mUser;
    public List mVisibleApps;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppLabelComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((SelectableAppInfo) obj)
                    .appName
                    .toString()
                    .toLowerCase()
                    .compareTo(((SelectableAppInfo) obj2).appName.toString().toLowerCase());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class Injector {
        public final Context mContext;
        public final UserHandle mUser;

        public Injector(Context context, UserHandle userHandle) {
            this.mContext = context;
            this.mUser = userHandle;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SelectableAppInfo {
        public CharSequence appName;
        public Drawable icon;
        public String packageName;

        public final String toString() {
            return this.packageName
                    + ": appName="
                    + ((Object) this.appName)
                    + "; icon="
                    + this.icon;
        }
    }

    public AppCopyHelper(Context context, UserHandle userHandle) {
        this(new Injector(context, userHandle));
    }

    public final void addSystemApps(Intent intent, List list) {
        ApplicationInfo applicationInfo;
        for (ResolveInfo resolveInfo : this.mPackageManager.queryIntentActivities(intent, 0)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null && (applicationInfo = activityInfo.applicationInfo) != null) {
                int i = applicationInfo.flags;
                if ((i & 1) != 0 || (i & 128) != 0) {
                    SelectableAppInfo selectableAppInfo = new SelectableAppInfo();
                    selectableAppInfo.packageName = activityInfo.packageName;
                    selectableAppInfo.appName = applicationInfo.loadLabel(this.mPackageManager);
                    selectableAppInfo.icon =
                            resolveInfo.activityInfo.loadIcon(this.mPackageManager);
                    list.add(selectableAppInfo);
                }
            }
        }
    }

    public final void installSelectedApps() {
        for (int i = 0; i < this.mSelectedPackages.size(); i++) {
            String str = (String) this.mSelectedPackages.valueAt(i);
            int identifier = this.mUser.getIdentifier();
            try {
                ApplicationInfo applicationInfo =
                        this.mIPm.getApplicationInfo(str, 4194304L, identifier);
                if (applicationInfo == null
                        || !applicationInfo.enabled
                        || (applicationInfo.flags & 8388608) == 0) {
                    Log.i("AppCopyHelper", "Installing " + str);
                    this.mIPm.installExistingPackageAsUser(
                            str, this.mUser.getIdentifier(), 4194304, 0, (List) null);
                }
                if (applicationInfo != null
                        && (applicationInfo.privateFlags & 1) != 0
                        && (applicationInfo.flags & 8388608) != 0) {
                    Log.i("AppCopyHelper", "Unhiding " + str);
                    this.mIPm.setApplicationHiddenSettingAsUser(str, false, identifier);
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public AppCopyHelper(Injector injector) {
        this.mSelectedPackages = new ArraySet();
        this.mPackageManager = injector.mContext.getPackageManager();
        this.mIPm = AppGlobals.getPackageManager();
        this.mUser = injector.mUser;
    }
}
