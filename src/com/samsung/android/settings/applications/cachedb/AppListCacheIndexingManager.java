package com.samsung.android.settings.applications.cachedb;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;

import com.samsung.android.settingslib.applications.cachedb.AppListCacheProviderContract;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppListCacheIndexingManager {
    public static AppListCacheIndexingManager sAppListCacheIndexingManager;
    public Context mContext;
    public Future mIndexingFuture;
    public ExecutorService mWorkerExecutor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.applications.cachedb.AppListCacheIndexingManager$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppListCacheIndexingManager this$0;

        public /* synthetic */ AnonymousClass1(
                AppListCacheIndexingManager appListCacheIndexingManager, int i) {
            this.$r8$classId = i;
            this.this$0 = appListCacheIndexingManager;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    long currentTimeMillis = System.currentTimeMillis();
                    Log.d(
                            "AppListCacheIndexingManager",
                            "loadAppListFromPackageManager START LOADING");
                    PackageManager packageManager = this.this$0.mContext.getPackageManager();
                    List<ApplicationInfo> installedApplications =
                            packageManager.getInstalledApplications(
                                    PackageManager.ApplicationInfoFlags.of(4303389184L));
                    ArrayList arrayList = new ArrayList();
                    HashMap hashMap = new HashMap();
                    for (ApplicationInfo applicationInfo : installedApplications) {
                        if (this.this$0.mIndexingFuture.isCancelled()) {
                            Log.d(
                                    "AppListCacheIndexingManager",
                                    "loadAppListFromPackageManager cancel()");
                            break;
                        } else {
                            Boolean bool = (Boolean) hashMap.get(applicationInfo.packageName);
                            if (bool == null || !bool.booleanValue()) {
                                arrayList.add(
                                        AppListCacheIndexingManager
                                                .m1107$$Nest$mgetContentValuesFromAppInfo(
                                                        this.this$0,
                                                        applicationInfo.loadLabel(packageManager),
                                                        applicationInfo));
                            } else {
                                AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                                        new StringBuilder(
                                                "loadAppListFromPackageManager hideAppList : "),
                                        applicationInfo.packageName,
                                        " is skipped for app list cache",
                                        "AppListCacheIndexingManager");
                            }
                        }
                    }
                    if (arrayList.isEmpty()) {
                        Log.e(
                                "AppListCacheIndexingManager",
                                "bulkInsertPackage : contentValuesList is empty");
                        break;
                    } else {
                        Log.d(
                                "AppListCacheIndexingManager",
                                "loadAppListFromPackageManager : build count = "
                                        + arrayList.size());
                        ContentValues[] contentValuesArr =
                                (ContentValues[])
                                        arrayList.toArray(new ContentValues[arrayList.size()]);
                        if (contentValuesArr.length == 0) {
                            Log.e(
                                    "AppListCacheIndexingManager",
                                    "loadAppListFromPackageManager : contentValuesArray is empty");
                            break;
                        } else {
                            try {
                                Log.d(
                                        "AppListCacheIndexingManager",
                                        "loadAppListFromPackageManager : insert count = "
                                                + this.this$0
                                                        .mContext
                                                        .getContentResolver()
                                                        .bulkInsert(
                                                                AppListCacheProviderContract
                                                                        .URI_APP_LIST,
                                                                contentValuesArr));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Context context = this.this$0.mContext;
                            String locale = Locale.getDefault().toString();
                            DialogFragment$$ExternalSyntheticOutline0.m(
                                    "updateLocale : ", locale, "AppListCacheManager");
                            context.getSharedPreferences("AppListPreference", 0)
                                    .edit()
                                    .putString("CachedLocale", locale)
                                    .commit();
                            Log.d(
                                    "AppListCacheIndexingManager",
                                    "loadAppListFromPackageManager FINISH LOADING took "
                                            + (System.currentTimeMillis() - currentTimeMillis));
                            break;
                        }
                    }
                    break;
                default:
                    Context context2 = this.this$0.mContext;
                    if (context2 == null) {
                        Log.e(
                                "AppListCacheIndexingManager",
                                "deletePackageAll : context or packageName is null");
                        break;
                    } else {
                        try {
                            Log.d(
                                    "AppListCacheIndexingManager",
                                    "deletePackageAll : rowCount = "
                                            + context2.getContentResolver()
                                                    .delete(
                                                            AppListCacheProviderContract
                                                                    .URI_APP_LIST,
                                                            null,
                                                            null));
                            break;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.applications.cachedb.AppListCacheIndexingManager$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ String val$packageName;

        public AnonymousClass2(String str) {
            this.val$packageName = str;
        }

        @Override // java.lang.Runnable
        public final void run() {
            PackageManager packageManager =
                    AppListCacheIndexingManager.this.mContext.getPackageManager();
            try {
                ApplicationInfo applicationInfo =
                        packageManager.getApplicationInfo(this.val$packageName, 0);
                if (applicationInfo == null) {
                    Log.e("AppListCacheIndexingManager", "insertPackage : appInfo is null");
                    return;
                }
                CharSequence loadLabel = applicationInfo.loadLabel(packageManager);
                if (TextUtils.isEmpty(loadLabel)) {
                    Log.e("AppListCacheIndexingManager", "insertPackage : label is empty");
                } else {
                    Log.d(
                            "AppListCacheIndexingManager",
                            "insertPackage() "
                                    .concat(
                                            AppListCacheIndexingManager.this
                                                                    .mContext
                                                                    .getContentResolver()
                                                                    .insert(
                                                                            AppListCacheProviderContract
                                                                                    .URI_APP_LIST,
                                                                            AppListCacheIndexingManager
                                                                                    .m1107$$Nest$mgetContentValuesFromAppInfo(
                                                                                            AppListCacheIndexingManager
                                                                                                    .this,
                                                                                            loadLabel,
                                                                                            applicationInfo))
                                                            == null
                                                    ? "fail"
                                                    : "success"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class AppListCacheIndexingManagerHolder {
        public static final AppListCacheIndexingManager INSTANCE;

        static {
            AppListCacheIndexingManager appListCacheIndexingManager =
                    new AppListCacheIndexingManager();
            appListCacheIndexingManager.mWorkerExecutor =
                    Executors.newSingleThreadExecutor(new AppListCacheThreadFactory());
            INSTANCE = appListCacheIndexingManager;
        }
    }

    /* renamed from: -$$Nest$mgetContentValuesFromAppInfo, reason: not valid java name */
    public static ContentValues m1107$$Nest$mgetContentValuesFromAppInfo(
            AppListCacheIndexingManager appListCacheIndexingManager,
            CharSequence charSequence,
            ApplicationInfo applicationInfo) {
        appListCacheIndexingManager.getClass();
        ContentValues contentValues = new ContentValues();
        contentValues.put("package_name", applicationInfo.packageName);
        contentValues.put("app_title", charSequence.toString());
        try {
            if (applicationInfo.sourceDir != null) {
                contentValues.put(
                        "last_updated",
                        Long.valueOf(new File(applicationInfo.sourceDir).lastModified()));
            } else {
                contentValues.put("last_updated", (Integer) 0);
            }
        } catch (Exception unused) {
            contentValues.put("last_updated", (Integer) 0);
        }
        return contentValues;
    }

    public static AppListCacheIndexingManager getInstance(Context context) {
        AppListCacheIndexingManager appListCacheIndexingManager =
                AppListCacheIndexingManagerHolder.INSTANCE;
        sAppListCacheIndexingManager = appListCacheIndexingManager;
        Context applicationContext = context.getApplicationContext();
        if (appListCacheIndexingManager.mContext == null) {
            appListCacheIndexingManager.mContext = applicationContext;
        }
        return sAppListCacheIndexingManager;
    }

    public final void startAsyncIndexing() {
        Log.i("AppListCacheIndexingManager", "## startAsyncIndexing() START");
        Future future = this.mIndexingFuture;
        if (future != null && !future.isDone()) {
            this.mIndexingFuture.cancel(true);
            Log.d(
                    "AppListCacheIndexingManager",
                    "loadAppListFromPackageManager Cancel previous indexing");
        }
        this.mIndexingFuture = this.mWorkerExecutor.submit(new AnonymousClass1(this, 0));
    }
}
