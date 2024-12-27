package com.android.settings.applications;

import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.android.settingslib.applications.ApplicationsState;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppStateAppOpsBridge extends AppStateBaseBridge {
    public final AppOpsManager mAppOpsManager;
    public final int[] mAppOpsOpCodes;
    public final Context mContext;
    public final IPackageManager mIPackageManager;
    public final String[] mPermissions;
    public final List mProfiles;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PermissionState {
        public int appOpMode = 3;
        public PackageInfo packageInfo;
        public final String packageName;
        public boolean permissionDeclared;
        public boolean staticPermissionGranted;
        public final UserHandle userHandle;

        public PermissionState(String str, UserHandle userHandle) {
            this.packageName = str;
            this.userHandle = userHandle;
        }

        public final boolean isPermissible() {
            int i = this.appOpMode;
            return i == 3 ? this.staticPermissionGranted : i == 0;
        }
    }

    public AppStateAppOpsBridge(
            Context context,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback,
            int i,
            String[] strArr) {
        this(context, applicationsState, callback, i, strArr, AppGlobals.getPackageManager());
    }

    public PermissionState getPermissionInfo(int i, String str) {
        UserHandle userHandle = new UserHandle(UserHandle.getUserId(i));
        PermissionState permissionState = new PermissionState(str, userHandle);
        try {
            PackageInfo packageInfo =
                    this.mIPackageManager.getPackageInfo(str, 4198400L, userHandle.getIdentifier());
            permissionState.packageInfo = packageInfo;
            if (packageInfo != null) {
                String[] strArr = packageInfo.requestedPermissions;
                int[] iArr = packageInfo.requestedPermissionsFlags;
                if (strArr != null) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= strArr.length) {
                            break;
                        }
                        String str2 = strArr[i2];
                        String[] strArr2 = this.mPermissions;
                        int length = strArr2.length;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= length) {
                                break;
                            }
                            if (str2.equals(strArr2[i3])) {
                                permissionState.permissionDeclared = true;
                                if ((iArr[i2] & 2) != 0) {
                                    permissionState.staticPermissionGranted = true;
                                    break;
                                }
                            } else {
                                i3++;
                            }
                        }
                        i2++;
                    }
                }
            }
            List opsForPackage = this.mAppOpsManager.getOpsForPackage(i, str, this.mAppOpsOpCodes);
            if (opsForPackage != null
                    && opsForPackage.size() > 0
                    && ((AppOpsManager.PackageOps) opsForPackage.get(0)).getOps().size() > 0) {
                permissionState.appOpMode =
                        ((AppOpsManager.OpEntry)
                                        ((AppOpsManager.PackageOps) opsForPackage.get(0))
                                                .getOps()
                                                .get(0))
                                .getMode();
            }
        } catch (RemoteException e) {
            Log.w(
                    "AppStateAppOpsBridge",
                    "PackageManager is dead. Can't get package info " + str,
                    e);
        }
        return permissionState;
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x008a, code lost:

       if (r4.size() == 0) goto L29;
    */
    /* JADX WARN: Removed duplicated region for block: B:49:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01cf  */
    @Override // com.android.settings.applications.AppStateBaseBridge
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void loadAllExtraInfo() {
        /*
            Method dump skipped, instructions count: 506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.AppStateAppOpsBridge.loadAllExtraInfo():void");
    }

    public AppStateAppOpsBridge(
            Context context,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback,
            int i,
            String[] strArr,
            IPackageManager iPackageManager) {
        this(context, applicationsState, callback, new int[] {i}, strArr, iPackageManager);
    }

    public AppStateAppOpsBridge(
            Context context,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback,
            int[] iArr,
            String[] strArr,
            IPackageManager iPackageManager) {
        super(applicationsState, callback);
        this.mContext = context;
        this.mIPackageManager = iPackageManager;
        this.mProfiles = UserManager.get(context).getUserProfiles();
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        this.mAppOpsOpCodes = iArr;
        this.mPermissions = strArr;
    }
}
