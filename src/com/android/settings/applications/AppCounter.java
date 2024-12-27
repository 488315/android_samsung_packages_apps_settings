package com.android.settings.applications;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.FeatureFlags;

import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppCounter extends AsyncTask {
    public final FeatureFlags mFf;
    public final PackageManager mPm;
    public final UserManager mUm;

    public AppCounter(Context context, PackageManager packageManager, FeatureFlags featureFlags) {
        this.mPm = packageManager;
        this.mUm = (UserManager) context.getSystemService(UserManager.class);
        this.mFf = featureFlags;
    }

    @Override // android.os.AsyncTask
    public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
        return doInBackground();
    }

    public abstract boolean includeInCount(ApplicationInfo applicationInfo);

    public abstract void onCountComplete(int i);

    @Override // android.os.AsyncTask
    public final void onPostExecute(Object obj) {
        onCountComplete(((Integer) obj).intValue());
    }

    public final Integer doInBackground() {
        int i = 0;
        for (UserInfo userInfo : this.mUm.getProfiles(UserHandle.myUserId())) {
            Iterator it =
                    this.mPm
                            .getInstalledApplicationsAsUser(
                                    PackageManager.ApplicationInfoFlags.of(
                                            (this.mFf.archiving()
                                                            ? GoodSettingsContract.PreferenceFlag
                                                                    .FLAG_NEED_TYPE
                                                            : 0L)
                                                    | 33280
                                                    | (userInfo.isAdmin() ? 4194304 : 0)),
                                    userInfo.id)
                            .iterator();
            while (it.hasNext()) {
                if (includeInCount((ApplicationInfo) it.next())) {
                    i++;
                }
            }
        }
        return Integer.valueOf(i);
    }
}
