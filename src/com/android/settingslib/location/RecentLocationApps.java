package com.android.settingslib.location;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.PermissionChecker;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.IconDrawableFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RecentLocationApps {
    static final String ANDROID_SYSTEM_PACKAGE_NAME = "android";
    public final Context mContext;
    public final IconDrawableFactory mDrawableFactory;
    public final PackageManager mPackageManager;
    static final int[] LOCATION_REQUEST_OPS = {41, 42};
    static final int[] LOCATION_PERMISSION_OPS = {1, 0};

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.location.RecentLocationApps$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Long.compare(
                    ((Request) obj).requestFinishTime, ((Request) obj2).requestFinishTime);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Request {
        public final Drawable icon;
        public final CharSequence label;
        public final String packageName;
        public final long requestFinishTime;
        public final UserHandle userHandle;

        public Request(
                String str,
                UserHandle userHandle,
                Drawable drawable,
                CharSequence charSequence,
                long j) {
            this.packageName = str;
            this.userHandle = userHandle;
            this.icon = drawable;
            this.label = charSequence;
            this.requestFinishTime = j;
        }
    }

    public RecentLocationApps(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mDrawableFactory = IconDrawableFactory.newInstance(context);
    }

    public final List getAppListSorted(boolean z) {
        PackageManager packageManager;
        List list;
        int i;
        PackageManager packageManager2 = this.mContext.getPackageManager();
        List packagesForOps =
                ((AppOpsManager) this.mContext.getSystemService("appops"))
                        .getPackagesForOps(LOCATION_REQUEST_OPS);
        int size = packagesForOps != null ? packagesForOps.size() : 0;
        ArrayList arrayList = new ArrayList(size);
        long currentTimeMillis = System.currentTimeMillis();
        List<UserHandle> userProfiles =
                ((UserManager) this.mContext.getSystemService("user")).getUserProfiles();
        int i2 = 0;
        while (i2 < size) {
            AppOpsManager.PackageOps packageOps = (AppOpsManager.PackageOps) packagesForOps.get(i2);
            String packageName = packageOps.getPackageName();
            int uid = packageOps.getUid();
            UserHandle userHandleForUid = UserHandle.getUserHandleForUid(uid);
            boolean z2 = uid == 5023 && "com.samsung.android.networkdiagnostic".equals(packageName);
            boolean z3 = uid == 5022 && "com.samsung.android.ipsgeofence".equals(packageName);
            if (z2
                    || z3
                    || !userProfiles.contains(userHandleForUid)
                    || (uid == 5013 && "com.sec.location.nsflp2".equals(packageName))) {
                packageManager = packageManager2;
                list = packagesForOps;
                i = size;
            } else {
                if (!z) {
                    int[] iArr = LOCATION_PERMISSION_OPS;
                    int length = iArr.length;
                    int i3 = 0;
                    while (i3 < length) {
                        list = packagesForOps;
                        String opToPermission = AppOpsManager.opToPermission(iArr[i3]);
                        int[] iArr2 = iArr;
                        int permissionFlags =
                                packageManager2.getPermissionFlags(
                                        opToPermission, packageName, userHandleForUid);
                        packageManager = packageManager2;
                        i = size;
                        if (PermissionChecker.checkPermissionForPreflight(
                                        this.mContext, opToPermission, -1, uid, packageName)
                                == 0) {
                            if ((permissionFlags & 256) == 0) {
                                break;
                            }
                            i3++;
                            iArr = iArr2;
                            packagesForOps = list;
                            packageManager2 = packageManager;
                            size = i;
                        } else {
                            if ((permissionFlags & 512) == 0) {
                                break;
                            }
                            i3++;
                            iArr = iArr2;
                            packagesForOps = list;
                            packageManager2 = packageManager;
                            size = i;
                        }
                    }
                }
                packageManager = packageManager2;
                list = packagesForOps;
                i = size;
                String packageName2 = packageOps.getPackageName();
                long j = currentTimeMillis - 86400000;
                long j2 = 0;
                boolean z4 = false;
                boolean z5 = false;
                for (AppOpsManager.OpEntry opEntry : packageOps.getOps()) {
                    if (opEntry.isRunning() || opEntry.getTime() >= j) {
                        j2 = opEntry.getDuration() + opEntry.getTime();
                        int op = opEntry.getOp();
                        if (op == 41) {
                            z5 = true;
                        } else if (op == 42) {
                            z4 = true;
                        }
                    }
                }
                Request request = null;
                if (z4 || z5) {
                    int userId = UserHandle.getUserId(packageOps.getUid());
                    try {
                        ApplicationInfo applicationInfoAsUser =
                                this.mPackageManager.getApplicationInfoAsUser(
                                        packageName2, 128, userId);
                        if (applicationInfoAsUser == null) {
                            Log.w(
                                    "RecentLocationApps",
                                    "Null application info retrieved for package "
                                            + packageName2
                                            + ", userId "
                                            + userId);
                        } else {
                            UserHandle userHandle = new UserHandle(userId);
                            Drawable badgedIcon =
                                    this.mDrawableFactory.getBadgedIcon(
                                            applicationInfoAsUser, userId);
                            CharSequence applicationLabel =
                                    this.mPackageManager.getApplicationLabel(applicationInfoAsUser);
                            applicationLabel
                                    .toString()
                                    .contentEquals(
                                            this.mPackageManager.getUserBadgedLabel(
                                                    applicationLabel, userHandle));
                            request =
                                    new Request(
                                            packageName2,
                                            userHandle,
                                            badgedIcon,
                                            applicationLabel,
                                            j2);
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.w(
                                "RecentLocationApps",
                                "package name not found for "
                                        + packageName2
                                        + ", userId "
                                        + userId);
                    }
                } else if (Log.isLoggable("RecentLocationApps", 2)) {
                    Log.v(
                            "RecentLocationApps",
                            packageName2 + " hadn't used location within the time interval.");
                }
                if (request != null) {
                    arrayList.add(request);
                }
            }
            i2++;
            packagesForOps = list;
            packageManager2 = packageManager;
            size = i;
        }
        Collections.sort(arrayList, Collections.reverseOrder(new AnonymousClass1()));
        return arrayList;
    }
}
