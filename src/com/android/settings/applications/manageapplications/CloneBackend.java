package com.android.settings.applications.manageapplications;

import android.content.Context;

import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CloneBackend {
    public static CloneBackend sInstance;
    public int mCloneUserId;
    public Context mContext;

    public static CloneBackend getInstance(Context context) {
        if (sInstance == null) {
            CloneBackend cloneBackend = new CloneBackend();
            cloneBackend.mContext = context;
            cloneBackend.mCloneUserId = Utils.getCloneUserId(context);
            sInstance = cloneBackend;
        }
        return sInstance;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int installCloneApp(java.lang.String r11) {
        /*
            r10 = this;
            java.lang.String r0 = "cloneUser"
            int r1 = r10.mCloneUserId
            java.lang.String r2 = "CloneBackend"
            r3 = 0
            r4 = -1
            if (r1 != r4) goto L4c
            android.content.Context r1 = r10.mContext
            java.lang.Class<android.os.UserManager> r5 = android.os.UserManager.class
            java.lang.Object r1 = r1.getSystemService(r5)
            android.os.UserManager r1 = (android.os.UserManager) r1
            r5 = 1
            java.lang.String r6 = "android.os.usertype.profile.CLONE"
            java.util.HashSet r7 = new java.util.HashSet     // Catch: java.lang.Exception -> L3c
            r7.<init>()     // Catch: java.lang.Exception -> L3c
            android.os.UserHandle r0 = r1.createProfile(r0, r6, r7)     // Catch: java.lang.Exception -> L3c
            if (r0 == 0) goto L39
            int r0 = r0.getIdentifier()
            r10.mCloneUserId = r0
            boolean r0 = com.android.settings.applications.manageapplications.ManageApplications.DEBUG
            if (r0 == 0) goto L4d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Created clone user "
            r0.<init>(r1)
            int r1 = r10.mCloneUserId
            androidx.preference.Preference$$ExternalSyntheticOutline0.m(r0, r1, r2)
            goto L4d
        L39:
            r10.mCloneUserId = r4
            goto L4c
        L3c:
            r10 = move-exception
            boolean r11 = com.android.settings.applications.manageapplications.ManageApplications.DEBUG
            if (r11 == 0) goto L4b
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = "Error occurred creating clone user"
            r11.<init>(r0)
            com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0.m(r10, r11, r2)
        L4b:
            return r5
        L4c:
            r5 = r3
        L4d:
            int r0 = r10.mCloneUserId
            java.lang.String r1 = "Package "
            if (r0 <= 0) goto Lb4
            if (r5 == 0) goto L7b
            android.app.IActivityManager r0 = android.app.ActivityManagerNative.getDefault()
            int r4 = r10.mCloneUserId     // Catch: android.os.RemoteException -> L5f
            r0.startProfile(r4)     // Catch: android.os.RemoteException -> L5f
            goto L7b
        L5f:
            r10 = move-exception
            boolean r11 = com.android.settings.applications.manageapplications.ManageApplications.DEBUG
            if (r11 == 0) goto L79
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = "Error starting clone user "
            r11.<init>(r0)
            java.lang.String r10 = r10.getMessage()
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            android.util.Log.e(r2, r10)
        L79:
            r10 = 2
            return r10
        L7b:
            r0 = 3
            android.content.pm.IPackageManager r4 = android.app.AppGlobals.getPackageManager()     // Catch: android.os.RemoteException -> L98
            int r6 = r10.mCloneUserId     // Catch: android.os.RemoteException -> L98
            r8 = 4
            r9 = 0
            r7 = 4194304(0x400000, float:5.877472E-39)
            r5 = r11
            int r10 = r4.installExistingPackageAsUser(r5, r6, r7, r8, r9)     // Catch: android.os.RemoteException -> L98
            r4 = -3
            if (r10 != r4) goto Lb4
            boolean r10 = com.android.settings.applications.manageapplications.ManageApplications.DEBUG
            if (r10 == 0) goto L97
            java.lang.String r10 = " doesn't exist."
            androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(r1, r11, r10, r2)
        L97:
            return r0
        L98:
            r10 = move-exception
            boolean r1 = com.android.settings.applications.manageapplications.ManageApplications.DEBUG
            if (r1 == 0) goto Lb3
            java.lang.String r1 = "Error installing package"
            java.lang.String r3 = " in clone user."
            java.lang.StringBuilder r11 = androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0.m(r1, r11, r3)
            java.lang.String r10 = r10.getMessage()
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            android.util.Log.e(r2, r10)
        Lb3:
            return r0
        Lb4:
            boolean r10 = com.android.settings.applications.manageapplications.ManageApplications.DEBUG
            if (r10 == 0) goto Lcc
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r1)
            r10.append(r11)
            java.lang.String r11 = " cloned successfully."
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            android.util.Log.i(r2, r10)
        Lcc:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.manageapplications.CloneBackend.installCloneApp(java.lang.String):int");
    }
}
