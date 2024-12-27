package com.android.settings.applications;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.os.UserManager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppLister extends AsyncTask {
    public final PackageManager mPm;
    public final UserManager mUm;

    public AppLister(PackageManager packageManager, UserManager userManager) {
        this.mPm = packageManager;
        this.mUm = userManager;
    }

    @Override // android.os.AsyncTask
    public final Object doInBackground(Object[] objArr) {
        ArrayList arrayList = new ArrayList();
        for (UserInfo userInfo : this.mUm.getProfiles(UserHandle.myUserId())) {
            for (ApplicationInfo applicationInfo :
                    this.mPm.getInstalledApplicationsAsUser(
                            PackageManager.ApplicationInfoFlags.of(
                                    (userInfo.isAdmin() ? 4194304 : 0) | 4295000576L),
                            userInfo.id)) {
                if (includeInCount(applicationInfo)) {
                    arrayList.add(new UserAppInfo(userInfo, applicationInfo));
                }
            }
        }
        return arrayList;
    }

    public abstract boolean includeInCount(ApplicationInfo applicationInfo);

    public abstract void onAppListBuilt(List list);

    @Override // android.os.AsyncTask
    public final void onPostExecute(Object obj) {
        onAppListBuilt((List) obj);
    }
}
