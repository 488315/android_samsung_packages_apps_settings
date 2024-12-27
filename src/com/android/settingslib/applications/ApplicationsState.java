package com.android.settingslib.applications;

import android.R;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.Application;
import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.InstallSourceInfo;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.hardware.usb.IUsbManager;
import android.multiuser.Flags;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.SparseArray;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.internal.util.ArrayUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.applications.manageapplications.ManageApplications;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdhms.SemAppRestrictionManager;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settingslib.applications.AppFileSizeFormatter;
import com.samsung.android.settingslib.applications.cachedb.AppListCachePackageData;
import com.samsung.android.settingslib.applications.cachedb.AppListCacheProviderContract;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ApplicationsState {
    public static final AnonymousClass1 ALPHA_COMPARATOR;
    public static final AnonymousClass2 EXTERNAL_SIZE_COMPARATOR;
    public static final AnonymousClass15 FILTER_ALL_ENABLED;
    public static final AnonymousClass9 FILTER_APPS_EXCEPT_GAMES;
    public static final AnonymousClass9 FILTER_APP_SETTING;
    public static final AnonymousClass9 FILTER_AUDIO = null;
    public static final AnonymousClass15 FILTER_DISABLED;
    public static final AnonymousClass9 FILTER_DOWNLOADED_AND_LAUNCHER;
    public static final AnonymousClass9 FILTER_DOWNLOADED_AND_LAUNCHER_AND_INSTANT;
    public static final AnonymousClass12 FILTER_DOWNLOADED_AND_LAUNCHER_NOT_QUIET;
    public static final AnonymousClass12 FILTER_ENABLED_NOT_QUIET;
    public static final AnonymousClass9 FILTER_EVERYTHING;
    public static final AnonymousClass9 FILTER_GAMES;
    public static final AnonymousClass9 FILTER_INSTALLED_APP_BY_USER;
    public static final AnonymousClass9 FILTER_INSTANT;
    public static final AnonymousClass30 FILTER_MANAGE_UNKNOWN_SOURCE_APPS;
    public static final AnonymousClass9 FILTER_MOVIES = null;
    public static final AnonymousClass24 FILTER_NOT_HIDE;
    public static final AnonymousClass7 FILTER_PERSONAL;
    public static final AnonymousClass9 FILTER_PHOTOS = null;
    public static final AnonymousClass9 FILTER_PRIVATE_PROFILE;
    public static final AnonymousClass9 FILTER_WITHOUT_DISABLED_UNTIL_USED;
    public static final AnonymousClass12 FILTER_WITH_DOMAIN_URLS;
    public static final AnonymousClass9 FILTER_WORK;
    public static final AnonymousClass2 INTERNAL_SIZE_COMPARATOR;
    public static final AnonymousClass2 LAST_UPDATED_COMPARATOR;
    public static final AnonymousClass2 LAST_USED_COMPARATOR;
    public static final Pattern REMOVE_DIACRITICALS_PATTERN = null;
    public static final AnonymousClass2 SIZE_COMPARATOR;
    public static HashMap mAppLabelCache;
    public static AppWidgetManager mAppWidgetManager;
    public static final ArrayList mNewAppListForAppLabelCache;
    public static PackageManager mPm;
    public static UsageStatsManager mUsageStatsManager;
    public static IUsbManager mUsbManager;
    static ApplicationsState sInstance;
    public static final Object sLock = new Object();
    public final long mAdminRetrieveFlags;
    public long mAppLoadStartTime;
    public final BackgroundHandler mBackgroundHandler;
    public PackageIntentReceiver mClonePackageIntentReceiver;
    public final Context mContext;
    public String mCurComputingSizePkg;
    public int mCurComputingSizeUserId;
    public UUID mCurComputingSizeUuid;
    public final IconDrawableFactory mDrawableFactory;
    public boolean mHaveDisabledApps;
    public final IPackageManager mIpm;
    public PackageIntentReceiver mPackageIntentReceiver;
    public boolean mResumed;
    public final long mRetrieveFlags;
    public boolean mSessionsChanged;
    public final StorageStatsManager mStats;
    public final HandlerThread mThread;
    public final UserManager mUm;
    public boolean mWorkHaveDisabledApp;
    public int mWorkUserId = -10000;
    public String mRefreshCandidatePkgName = ApnSettings.MVNO_NONE;
    public final ArrayList mSessions = new ArrayList();
    public final ArrayList mRebuildingSessions = new ArrayList();
    public InterestingConfigChanges mInterestingConfigChanges = new InterestingConfigChanges();
    public final SparseArray mEntriesMap = new SparseArray();
    public final ArrayList mAppEntries = new ArrayList();
    public List mApplications = new ArrayList();
    public long mCurId = 1;
    public final HashMap mSystemModules = new HashMap();
    public final ArrayList mActiveSessions = new ArrayList();
    public final MainHandler mMainHandler = new MainHandler(Looper.getMainLooper());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.applications.ApplicationsState$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        public final Collator sCollator = Collator.getInstance();

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            ApplicationInfo applicationInfo;
            int compare;
            AppEntry appEntry = (AppEntry) obj;
            AppEntry appEntry2 = (AppEntry) obj2;
            int compare2 = this.sCollator.compare(appEntry.label, appEntry2.label);
            if (compare2 != 0) {
                return compare2;
            }
            ApplicationInfo applicationInfo2 = appEntry.info;
            return (applicationInfo2 == null
                            || (applicationInfo = appEntry2.info) == null
                            || (compare =
                                            this.sCollator.compare(
                                                    applicationInfo2.packageName,
                                                    applicationInfo.packageName))
                                    == 0)
                    ? appEntry.info.uid - appEntry2.info.uid
                    : compare;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.applications.ApplicationsState$24, reason: invalid class name */
    public final class AnonymousClass24 implements AppFilter {
        public String[] mHidePackageNames;

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(AppEntry appEntry) {
            if (!ArrayUtils.contains(this.mHidePackageNames, appEntry.info.packageName)) {
                return true;
            }
            ApplicationInfo applicationInfo = appEntry.info;
            return applicationInfo.enabled && applicationInfo.enabledSetting != 4;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init(Context context) {
            this.mHidePackageNames =
                    context.getResources().getStringArray(R.array.supported_locales);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.applications.ApplicationsState$30, reason: invalid class name */
    public final class AnonymousClass30 implements AppFilter {
        public final /* synthetic */ int $r8$classId = 0;
        public Object mUnknownSourcePackageInfos;
        public Object mUnknownSourcePackageNames;

        public /* synthetic */ AnonymousClass30() {}

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(AppEntry appEntry) {
            switch (this.$r8$classId) {
                case 0:
                    HashSet hashSet = (HashSet) this.mUnknownSourcePackageNames;
                    if (hashSet == null || !hashSet.contains(appEntry.info.packageName)) {}
                    break;
                default:
                    if (!((AppFilter) this.mUnknownSourcePackageInfos).filterApp(appEntry)
                            || !((AppFilter) this.mUnknownSourcePackageNames)
                                    .filterApp(appEntry)) {}
                    break;
            }
            return false;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init(Context context) {
            switch (this.$r8$classId) {
                case 0:
                    this.mUnknownSourcePackageInfos =
                            context.getPackageManager().getUnknownSourcePackages(0);
                    this.mUnknownSourcePackageNames = new HashSet();
                    Iterator it = ((List) this.mUnknownSourcePackageInfos).iterator();
                    while (it.hasNext()) {
                        ((HashSet) this.mUnknownSourcePackageNames)
                                .add(((PackageInfo) it.next()).packageName);
                    }
                    break;
                default:
                    ((AppFilter) this.mUnknownSourcePackageInfos).init(context);
                    ((AppFilter) this.mUnknownSourcePackageNames).init(context);
                    break;
            }
        }

        public AnonymousClass30(AppFilter appFilter, AppFilter appFilter2) {
            this.mUnknownSourcePackageInfos = appFilter;
            this.mUnknownSourcePackageNames = appFilter2;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {
            switch (this.$r8$classId) {
                case 0:
                    break;
                default:
                    ((AppFilter) this.mUnknownSourcePackageInfos).init();
                    ((AppFilter) this.mUnknownSourcePackageNames).init();
                    break;
            }
        }

        private final void init$com$android$settingslib$applications$ApplicationsState$30() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.applications.ApplicationsState$7, reason: invalid class name */
    public final class AnonymousClass7 implements AppFilter {
        public int mCurrentUser;

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(AppEntry appEntry) {
            return UserHandle.getUserId(appEntry.info.uid) == this.mCurrentUser
                    || SemDualAppManager.isDualAppId(UserHandle.getUserId(appEntry.info.uid));
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {
            this.mCurrentUser = ActivityManager.getCurrentUser();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppEntry {
        public File apkFile;
        public long cacheSize;
        public long codeSize;
        public long dataSize;
        public long externalCacheSize;
        public long externalCodeSize;
        public long externalDataSize;
        public long externalSize;
        public String externalSizeStr;
        public Object extraInfo;
        public boolean hasLauncherEntry;
        public boolean hideInQuietMode;
        public Drawable icon;
        public final long id;
        public ApplicationInfo info;
        public long internalSize;
        public String internalSizeStr;
        public boolean isHomeApp;
        public String label;
        public String labelDescription;
        public long lastUpdated;
        public long lastUsed;
        String mProfileType;
        public boolean mounted;
        public final boolean showInPersonalTab;
        public long size;
        public long sizeLoadStart;
        public boolean sizeStale;
        public String sizeStr;

        public AppEntry(Context context, ApplicationInfo applicationInfo, long j) {
            if (applicationInfo == null) {
                this.apkFile = null;
            } else if (applicationInfo.sourceDir == null) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("SRC null : "),
                        applicationInfo.packageName,
                        "ApplicationsState");
                this.apkFile = null;
            } else {
                this.apkFile = new File(applicationInfo.sourceDir);
            }
            this.id = j;
            this.info = applicationInfo;
            this.size = -1L;
            this.sizeStale = true;
            ensureLabel(context);
            UserManager userManager = UserManager.get(context);
            if (applicationInfo != null) {
                this.mProfileType =
                        userManager.getUserInfo(UserHandle.getUserId(applicationInfo.uid)).userType;
                this.showInPersonalTab = shouldShowInPersonalTab(userManager, applicationInfo.uid);
                int i = applicationInfo.uid;
                boolean z = false;
                if (Flags.enablePrivateSpaceFeatures()
                        && Flags.handleInterleavedSettingsForPrivateSpace()) {
                    UserHandle of = UserHandle.of(UserHandle.getUserId(i));
                    Object obj = ApplicationsState.sLock;
                    z =
                            userManager.isQuietModeEnabled(of)
                                    && userManager.getUserProperties(of).getShowInQuietMode() == 1;
                }
                this.hideInQuietMode = z;
            }
        }

        public final boolean ensureIconLocked(
                Context context, IconDrawableFactory iconDrawableFactory) {
            Object obj = ApplicationsState.sLock;
            if (KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(context.getPackageName())) {
                return false;
            }
            if (this.icon == null) {
                boolean z = this.info.isArchived;
                File file = this.apkFile;
                if ((file != null && file.exists()) || z) {
                    this.icon = iconDrawableFactory.getBadgedIcon(this.info);
                    return true;
                }
                this.mounted = false;
                this.icon = context.getDrawable(17304561);
            } else if (!this.mounted) {
                boolean z2 = this.info.isArchived;
                File file2 = this.apkFile;
                if ((file2 != null && file2.exists()) || z2) {
                    this.mounted = true;
                    this.icon = iconDrawableFactory.getBadgedIcon(this.info);
                    return true;
                }
            }
            return false;
        }

        public final void ensureLabel(Context context) {
            if (this.id == -1) {
                this.label = "\u200b";
                return;
            }
            if (this.label == null || !this.mounted) {
                boolean z = this.info.isArchived;
                File file = this.apkFile;
                if ((file == null || !file.exists()) && !z) {
                    this.mounted = false;
                    this.label = this.info.packageName;
                    return;
                }
                boolean z2 = true;
                this.mounted = true;
                HashMap hashMap = ApplicationsState.mAppLabelCache;
                CharSequence charSequence = null;
                if (hashMap != null) {
                    AppListCachePackageData appListCachePackageData =
                            (AppListCachePackageData) hashMap.get(this.info.packageName);
                    if (appListCachePackageData != null) {
                        try {
                            if (this.info.sourceDir != null) {
                                if (appListCachePackageData.lastUpdateTime
                                        == new File(this.info.sourceDir).lastModified()) {
                                    charSequence = appListCachePackageData.label;
                                } else {
                                    Log.i(
                                            "ApplicationsState",
                                            this.info.packageName + " updated recently");
                                }
                            }
                        } catch (Exception e) {
                            Log.e("ApplicationsState", e.getMessage());
                        }
                    }
                    if (this.info.sourceDir == null) {
                        Log.i(
                                "ApplicationsState",
                                this.info.packageName + " info.sourceDir == null");
                    }
                }
                if (TextUtils.isEmpty(charSequence)) {
                    charSequence = this.info.loadLabel(context.getPackageManager());
                } else {
                    z2 = false;
                }
                if (ApplicationsState.mAppLabelCache != null && z2) {
                    Log.i(
                            "ApplicationsState",
                            "mNewAppListForAppLabelCache.add : " + this.info.packageName);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("package_name", this.info.packageName);
                    contentValues.put("app_title", charSequence.toString());
                    try {
                        if (this.info.sourceDir != null) {
                            contentValues.put(
                                    "last_updated",
                                    Long.valueOf(new File(this.info.sourceDir).lastModified()));
                        } else {
                            contentValues.put("last_updated", (Integer) 0);
                        }
                    } catch (Exception unused) {
                        contentValues.put("last_updated", (Integer) 0);
                    }
                    ApplicationsState.mNewAppListForAppLabelCache.add(contentValues);
                }
                this.label = charSequence != null ? charSequence.toString() : this.info.packageName;
            }
        }

        public final boolean isClonedProfile() {
            return "android.os.usertype.profile.CLONE".equals(this.mProfileType);
        }

        public boolean shouldShowInPersonalTab(UserManager userManager, int i) {
            int userId = UserHandle.getUserId(i);
            return userId == ActivityManager.getCurrentUser()
                    || userManager.getUserProperties(UserHandle.of(userId)).getShowInSettings()
                            == 0;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BackgroundHandler extends Handler {
        public int mIconLoaded;
        public boolean mRunning;
        public final AnonymousClass1 mStatsObserver;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settingslib.applications.ApplicationsState$BackgroundHandler$1, reason: invalid class name */
        public final class AnonymousClass1 extends IPackageStatsObserver.Stub {
            public AnonymousClass1() {}

            public final void onGetStatsCompleted(PackageStats packageStats, boolean z) {
                PackageStats packageStats2;
                long j;
                boolean z2;
                AnonymousClass1 anonymousClass1 = this;
                if (z) {
                    synchronized (ApplicationsState.this.mEntriesMap) {
                        try {
                            HashMap hashMap =
                                    (HashMap)
                                            ApplicationsState.this.mEntriesMap.get(
                                                    packageStats.userHandle);
                            if (hashMap == null) {
                                return;
                            }
                            AppEntry appEntry = (AppEntry) hashMap.get(packageStats.packageName);
                            if (appEntry != null) {
                                synchronized (appEntry) {
                                    try {
                                        appEntry.sizeStale = false;
                                        appEntry.sizeLoadStart = 0L;
                                        long j2 =
                                                packageStats.externalCodeSize
                                                        + packageStats.externalObbSize;
                                        long j3 =
                                                packageStats.externalDataSize
                                                        + packageStats.externalMediaSize;
                                        ApplicationsState.this.getClass();
                                        long j4 = packageStats.codeSize;
                                        long j5 = packageStats.dataSize;
                                        long j6 = packageStats.cacheSize;
                                        long j7 = j2 + j3 + ((j4 + j5) - j6);
                                        if (appEntry.size == j7
                                                && appEntry.cacheSize == j6
                                                && appEntry.codeSize == j4
                                                && appEntry.dataSize == j5
                                                && appEntry.externalCodeSize == j2
                                                && appEntry.externalDataSize == j3) {
                                            packageStats2 = packageStats;
                                            j = j3;
                                            if (appEntry.externalCacheSize
                                                    == packageStats2.externalCacheSize) {
                                                z2 = false;
                                                anonymousClass1 = this;
                                            }
                                        } else {
                                            packageStats2 = packageStats;
                                            j = j3;
                                        }
                                        appEntry.size = j7;
                                        appEntry.cacheSize = j6;
                                        appEntry.codeSize = j4;
                                        appEntry.dataSize = j5;
                                        appEntry.externalCodeSize = j2;
                                        appEntry.externalDataSize = j;
                                        appEntry.externalCacheSize =
                                                packageStats2.externalCacheSize;
                                        anonymousClass1 = this;
                                        appEntry.sizeStr =
                                                ApplicationsState.m1029$$Nest$mgetSizeStr(
                                                        ApplicationsState.this, j7);
                                        ApplicationsState.this.getClass();
                                        long j8 =
                                                (packageStats2.codeSize + packageStats2.dataSize)
                                                        - packageStats2.cacheSize;
                                        appEntry.internalSize = j8;
                                        appEntry.internalSizeStr =
                                                ApplicationsState.m1029$$Nest$mgetSizeStr(
                                                        ApplicationsState.this, j8);
                                        ApplicationsState.this.getClass();
                                        long j9 =
                                                packageStats2.externalCodeSize
                                                        + packageStats2.externalDataSize
                                                        + packageStats2.externalCacheSize
                                                        + packageStats2.externalMediaSize
                                                        + packageStats2.externalObbSize;
                                        appEntry.externalSize = j9;
                                        appEntry.externalSizeStr =
                                                ApplicationsState.m1029$$Nest$mgetSizeStr(
                                                        ApplicationsState.this, j9);
                                        z2 = true;
                                    } finally {
                                    }
                                }
                                if (z2) {
                                    ApplicationsState.this.mMainHandler.sendMessage(
                                            ApplicationsState.this.mMainHandler.obtainMessage(
                                                    4, packageStats2.packageName));
                                }
                            } else {
                                packageStats2 = packageStats;
                            }
                            String str = ApplicationsState.this.mCurComputingSizePkg;
                            if (str != null && str.equals(packageStats2.packageName)) {
                                BackgroundHandler backgroundHandler = BackgroundHandler.this;
                                ApplicationsState applicationsState = ApplicationsState.this;
                                if (applicationsState.mCurComputingSizeUserId
                                        == packageStats2.userHandle) {
                                    applicationsState.mCurComputingSizePkg = null;
                                    backgroundHandler.sendEmptyMessage(7);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        }

        public BackgroundHandler(Looper looper) {
            super(looper);
            this.mStatsObserver = new AnonymousClass1();
        }

        public final int getCombinedSessionFlags(List list) {
            int i;
            synchronized (ApplicationsState.this.mEntriesMap) {
                try {
                    Iterator it = list.iterator();
                    i = 0;
                    while (it.hasNext()) {
                        i |= ((Session) it.next()).mFlags;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:159:0x0219, code lost:

           r0 = move-exception;
        */
        /* JADX WARN: Code restructure failed: missing block: B:162:0x025a, code lost:

           throw r0;
        */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r19) {
            /*
                Method dump skipped, instructions count: 1272
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settingslib.applications.ApplicationsState.BackgroundHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callbacks {
        void onAllSizesComputed();

        void onLauncherInfoChanged();

        void onLoadEntriesCompleted();

        void onPackageIconChanged();

        void onPackageListChanged();

        void onPackageSizeChanged(String str);

        void onRebuildComplete(ArrayList arrayList);

        void onRunningStateChanged(boolean z);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            ApplicationsState applicationsState = ApplicationsState.this;
            synchronized (applicationsState.mEntriesMap) {
                try {
                    if (applicationsState.mSessionsChanged) {
                        applicationsState.mActiveSessions.clear();
                        for (int i2 = 0; i2 < applicationsState.mSessions.size(); i2++) {
                            Session session = (Session) applicationsState.mSessions.get(i2);
                            if (session.mResumed) {
                                applicationsState.mActiveSessions.add(new WeakReference(session));
                            }
                        }
                    }
                } finally {
                }
            }
            switch (message.what) {
                case 1:
                    ArrayList arrayList = ApplicationsState.mNewAppListForAppLabelCache;
                    if (!arrayList.isEmpty()) {
                        ApplicationsState applicationsState2 = ApplicationsState.this;
                        applicationsState2.getClass();
                        if (arrayList.isEmpty()) {
                            Log.e(
                                    "ApplicationsState",
                                    "bulkInsertAppLabelList : contentValuesList is empty");
                        } else {
                            Log.d(
                                    "ApplicationsState",
                                    "bulkInsertAppLabelList : build count = " + arrayList.size());
                            ContentValues[] contentValuesArr =
                                    (ContentValues[])
                                            arrayList.toArray(new ContentValues[arrayList.size()]);
                            arrayList.clear();
                            if (contentValuesArr.length == 0) {
                                Log.e(
                                        "ApplicationsState",
                                        "bulkInsertAppLabelList : contentValuesArray is empty");
                            } else {
                                try {
                                    Log.d(
                                            "ApplicationsState",
                                            "bulkInsertAppLabelList : insert count = "
                                                    + applicationsState2
                                                            .mContext
                                                            .getContentResolver()
                                                            .bulkInsert(
                                                                    AppListCacheProviderContract
                                                                            .URI_APP_LIST,
                                                                    contentValuesArr));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (UserHandle.myUserId() == 0) {
                        ApplicationsState applicationsState3 = ApplicationsState.this;
                        applicationsState3.getClass();
                        HashSet hashSet = new HashSet();
                        for (i = 0; i < applicationsState3.mAppEntries.size(); i++) {
                            hashSet.add(
                                    ((AppEntry) applicationsState3.mAppEntries.get(i))
                                            .info
                                            .packageName);
                        }
                        if (((ArrayList) applicationsState3.mApplications).size()
                                != hashSet.size()) {
                            Log.i(
                                    "ApplicationsState",
                                    "List is not matched size, so skip"
                                        + " removeRemovedAppLabelList()");
                        } else {
                            ArrayList arrayList2 = new ArrayList();
                            Iterator it = ApplicationsState.mAppLabelCache.entrySet().iterator();
                            while (it.hasNext()) {
                                AppListCachePackageData appListCachePackageData =
                                        (AppListCachePackageData)
                                                ((Map.Entry) it.next()).getValue();
                                if (!hashSet.contains(appListCachePackageData.packageName)) {
                                    Log.i(
                                            "ApplicationsState",
                                            appListCachePackageData.packageName
                                                    + " maybe deleted, so remove from applist label"
                                                    + " cache");
                                    arrayList2.add(appListCachePackageData.packageName);
                                }
                            }
                            ArrayList<ContentProviderOperation> arrayList3 = new ArrayList<>();
                            Iterator it2 = arrayList2.iterator();
                            while (it2.hasNext()) {
                                arrayList3.add(
                                        ContentProviderOperation.newDelete(
                                                        Uri.withAppendedPath(
                                                                AppListCacheProviderContract
                                                                        .URI_APP_LIST,
                                                                (String) it2.next()))
                                                .build());
                            }
                            try {
                                applicationsState3
                                        .mContext
                                        .getContentResolver()
                                        .applyBatch(
                                                "com.samsung.android.settings.applist", arrayList3);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    Session session2 = (Session) message.obj;
                    Iterator it3 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it3.hasNext()) {
                        Session session3 = (Session) ((WeakReference) it3.next()).get();
                        if (session3 != null && session3 == session2) {
                            session2.mCallbacks.onRebuildComplete(session2.mLastAppList);
                        }
                    }
                    return;
                case 2:
                    Iterator it4 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it4.hasNext()) {
                        Session session4 = (Session) ((WeakReference) it4.next()).get();
                        if (session4 != null) {
                            session4.mCallbacks.onPackageListChanged();
                        }
                    }
                    return;
                case 3:
                    Iterator it5 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it5.hasNext()) {
                        Session session5 = (Session) ((WeakReference) it5.next()).get();
                        if (session5 != null) {
                            session5.mCallbacks.onPackageIconChanged();
                        }
                    }
                    return;
                case 4:
                    Iterator it6 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it6.hasNext()) {
                        Session session6 = (Session) ((WeakReference) it6.next()).get();
                        if (session6 != null) {
                            session6.mCallbacks.onPackageSizeChanged((String) message.obj);
                        }
                    }
                    return;
                case 5:
                    Iterator it7 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it7.hasNext()) {
                        Session session7 = (Session) ((WeakReference) it7.next()).get();
                        if (session7 != null) {
                            session7.mCallbacks.onAllSizesComputed();
                        }
                    }
                    return;
                case 6:
                    Iterator it8 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it8.hasNext()) {
                        Session session8 = (Session) ((WeakReference) it8.next()).get();
                        if (session8 != null) {
                            session8.mCallbacks.onRunningStateChanged(message.arg1 != 0);
                        }
                    }
                    return;
                case 7:
                    Iterator it9 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it9.hasNext()) {
                        Session session9 = (Session) ((WeakReference) it9.next()).get();
                        if (session9 != null) {
                            session9.mCallbacks.onLauncherInfoChanged();
                        }
                    }
                    return;
                case 8:
                    Iterator it10 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it10.hasNext()) {
                        Session session10 = (Session) ((WeakReference) it10.next()).get();
                        if (session10 != null) {
                            session10.mCallbacks.onLoadEntriesCompleted();
                        }
                    }
                    return;
                case 9:
                    Iterator it11 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it11.hasNext()) {
                        Callbacks callbacks =
                                ((Session) ((WeakReference) it11.next()).get()).mCallbacks;
                        if (callbacks instanceof ManageApplications.ApplicationsAdapter) {
                            ManageApplications.ApplicationsAdapter applicationsAdapter =
                                    (ManageApplications.ApplicationsAdapter) callbacks;
                            if (applicationsAdapter.mLastSortMode == 2) {
                                applicationsAdapter.rebuild();
                            }
                        }
                    }
                    return;
                case 10:
                    Iterator it12 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it12.hasNext()) {
                        Callbacks callbacks2 =
                                ((Session) ((WeakReference) it12.next()).get()).mCallbacks;
                        if (callbacks2 instanceof ManageApplications.ApplicationsAdapter) {
                            ManageApplications.ApplicationsAdapter applicationsAdapter2 =
                                    (ManageApplications.ApplicationsAdapter) callbacks2;
                            if (applicationsAdapter2.mLastSortMode == 3) {
                                applicationsAdapter2.rebuild();
                            }
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PackageIntentReceiver extends BroadcastReceiver {
        public PackageIntentReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int i = 0;
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                while (i < ApplicationsState.this.mEntriesMap.size()) {
                    ApplicationsState applicationsState = ApplicationsState.this;
                    applicationsState.addPackage(
                            applicationsState.mEntriesMap.keyAt(i), encodedSchemeSpecificPart);
                    i++;
                }
                return;
            }
            if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                String encodedSchemeSpecificPart2 = intent.getData().getEncodedSchemeSpecificPart();
                while (i < ApplicationsState.this.mEntriesMap.size()) {
                    ApplicationsState applicationsState2 = ApplicationsState.this;
                    applicationsState2.removePackage(
                            applicationsState2.mEntriesMap.keyAt(i), encodedSchemeSpecificPart2);
                    i++;
                }
                return;
            }
            if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                String encodedSchemeSpecificPart3 = intent.getData().getEncodedSchemeSpecificPart();
                while (i < ApplicationsState.this.mEntriesMap.size()) {
                    ApplicationsState applicationsState3 = ApplicationsState.this;
                    int keyAt = applicationsState3.mEntriesMap.keyAt(i);
                    applicationsState3.removePackage(keyAt, encodedSchemeSpecificPart3);
                    applicationsState3.addPackage(keyAt, encodedSchemeSpecificPart3);
                    i++;
                }
                return;
            }
            if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action)
                    || "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action)) {
                String[] stringArrayExtra =
                        intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                if (stringArrayExtra == null
                        || stringArrayExtra.length == 0
                        || !"android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE"
                                .equals(action)) {
                    return;
                }
                for (String str : stringArrayExtra) {
                    for (int i2 = 0; i2 < ApplicationsState.this.mEntriesMap.size(); i2++) {
                        ApplicationsState applicationsState4 = ApplicationsState.this;
                        int keyAt2 = applicationsState4.mEntriesMap.keyAt(i2);
                        applicationsState4.removePackage(keyAt2, str);
                        applicationsState4.addPackage(keyAt2, str);
                    }
                }
                return;
            }
            if ("android.intent.action.USER_ADDED".equals(action)) {
                ApplicationsState applicationsState5 = ApplicationsState.this;
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                if (ArrayUtils.contains(
                        applicationsState5.mUm.getProfileIdsWithDisabled(UserHandle.myUserId()),
                        intExtra)) {
                    synchronized (applicationsState5.mEntriesMap) {
                        try {
                            applicationsState5.mEntriesMap.put(intExtra, new HashMap());
                            if (applicationsState5.mResumed) {
                                applicationsState5.doPauseLocked();
                                applicationsState5.doResumeIfNeededLocked();
                            }
                            if (!applicationsState5.mMainHandler.hasMessages(2)) {
                                applicationsState5.mMainHandler.sendEmptyMessage(2);
                            }
                        } finally {
                        }
                    }
                    return;
                }
                return;
            }
            if ("android.intent.action.USER_REMOVED".equals(action)) {
                ApplicationsState applicationsState6 = ApplicationsState.this;
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                synchronized (applicationsState6.mEntriesMap) {
                    try {
                        HashMap hashMap = (HashMap) applicationsState6.mEntriesMap.get(intExtra2);
                        if (hashMap != null) {
                            for (AppEntry appEntry : hashMap.values()) {
                                applicationsState6.mAppEntries.remove(appEntry);
                                ((ArrayList) applicationsState6.mApplications)
                                        .remove(appEntry.info);
                            }
                            applicationsState6.mEntriesMap.remove(intExtra2);
                            if (!applicationsState6.mMainHandler.hasMessages(2)) {
                                applicationsState6.mMainHandler.sendEmptyMessage(2);
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Session implements LifecycleObserver {
        public final Callbacks mCallbacks;
        public final boolean mHasLifecycle;
        public ArrayList mLastAppList;
        public Comparator mRebuildComparator;
        public AppFilter mRebuildFilter;
        public boolean mRebuildForeground;
        public boolean mRebuildRequested;
        public boolean mResumed;
        public final Object mRebuildSync = new Object();
        public int mFlags = 13;

        public Session(Callbacks callbacks, Lifecycle lifecycle) {
            this.mCallbacks = callbacks;
            if (lifecycle != null) {
                lifecycle.addObserver(this);
                this.mHasLifecycle = true;
            } else {
                this.mHasLifecycle = false;
            }
            if (KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(
                    ApplicationsState.this.mContext.getPackageName())) {
                this.mFlags &= -3;
            }
        }

        public final ArrayList getAllApps() {
            ArrayList arrayList;
            synchronized (ApplicationsState.this.mEntriesMap) {
                arrayList = new ArrayList(ApplicationsState.this.mAppEntries);
            }
            return arrayList;
        }

        public final void handleRebuildList() {
            ArrayList arrayList;
            if (this.mResumed) {
                synchronized (this.mRebuildSync) {
                    try {
                        if (this.mRebuildRequested) {
                            AppFilter appFilter = this.mRebuildFilter;
                            Comparator comparator = this.mRebuildComparator;
                            this.mRebuildRequested = false;
                            this.mRebuildFilter = null;
                            this.mRebuildComparator = null;
                            if (this.mRebuildForeground) {
                                Process.setThreadPriority(-2);
                                this.mRebuildForeground = false;
                            }
                            if (appFilter != null) {
                                appFilter.init(ApplicationsState.this.mContext);
                            }
                            synchronized (ApplicationsState.this.mEntriesMap) {
                                arrayList = new ArrayList(ApplicationsState.this.mAppEntries);
                            }
                            ArrayMap arrayMap = new ArrayMap();
                            ArrayList arrayList2 = new ArrayList();
                            Iterator it = arrayList.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                AppEntry appEntry = (AppEntry) it.next();
                                if (Flags.enablePrivateSpaceFeatures()
                                        && Flags.handleInterleavedSettingsForPrivateSpace()) {
                                    UserHandle of =
                                            UserHandle.of(UserHandle.getUserId(appEntry.info.uid));
                                    if (!arrayMap.containsKey(of)) {
                                        UserManager userManager = ApplicationsState.this.mUm;
                                        arrayMap.put(
                                                of,
                                                Boolean.valueOf(
                                                        userManager.isQuietModeEnabled(of)
                                                                && userManager
                                                                                .getUserProperties(
                                                                                        of)
                                                                                .getShowInQuietMode()
                                                                        == 1));
                                    }
                                    appFilter.refreshAppEntryOnRebuild(
                                            appEntry, ((Boolean) arrayMap.get(of)).booleanValue());
                                }
                                if (appEntry != null
                                        && (appFilter == null || appFilter.filterApp(appEntry))) {
                                    synchronized (ApplicationsState.this.mEntriesMap) {
                                        if (comparator != null) {
                                            try {
                                                appEntry.ensureLabel(
                                                        ApplicationsState.this.mContext);
                                            } finally {
                                            }
                                        }
                                        arrayList2.add(appEntry);
                                    }
                                }
                            }
                            if (comparator != null) {
                                try {
                                    synchronized (ApplicationsState.this.mEntriesMap) {
                                        Collections.sort(arrayList2, comparator);
                                    }
                                } catch (Exception unused) {
                                    Log.e("ApplicationsState", "sorting error");
                                }
                            }
                            synchronized (this.mRebuildSync) {
                                try {
                                    if (!this.mRebuildRequested) {
                                        this.mLastAppList = arrayList2;
                                        if (!ApplicationsState.this.mMainHandler.hasMessages(
                                                1, this)) {
                                            ApplicationsState.this.mMainHandler.sendMessage(
                                                    ApplicationsState.this.mMainHandler
                                                            .obtainMessage(1, this));
                                        }
                                    }
                                } finally {
                                }
                            }
                            Process.setThreadPriority(10);
                        }
                    } finally {
                    }
                }
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            if (!this.mHasLifecycle) {
                onPause();
            }
            synchronized (ApplicationsState.this.mEntriesMap) {
                ApplicationsState.this.mSessions.remove(this);
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
            synchronized (ApplicationsState.this.mEntriesMap) {
                try {
                    if (this.mResumed) {
                        int i = 0;
                        this.mResumed = false;
                        ApplicationsState applicationsState = ApplicationsState.this;
                        applicationsState.mSessionsChanged = true;
                        applicationsState.mBackgroundHandler.removeMessages(1, this);
                        ApplicationsState applicationsState2 = ApplicationsState.this;
                        if (applicationsState2.mResumed) {
                            while (true) {
                                if (i >= applicationsState2.mSessions.size()) {
                                    applicationsState2.doPauseLocked();
                                    break;
                                } else if (((Session) applicationsState2.mSessions.get(i))
                                        .mResumed) {
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                    }
                } finally {
                }
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            synchronized (ApplicationsState.this.mEntriesMap) {
                try {
                    if (!this.mResumed) {
                        this.mResumed = true;
                        ApplicationsState applicationsState = ApplicationsState.this;
                        applicationsState.mSessionsChanged = true;
                        applicationsState.doPauseLocked();
                        ApplicationsState.this.doResumeIfNeededLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void rebuild(AppFilter appFilter, Comparator comparator, boolean z) {
            synchronized (this.mRebuildSync) {
                synchronized (ApplicationsState.this.mRebuildingSessions) {
                    try {
                        ApplicationsState.this.mRebuildingSessions.add(this);
                        this.mRebuildRequested = true;
                        this.mRebuildFilter = appFilter;
                        this.mRebuildComparator = comparator;
                        this.mRebuildForeground = z;
                        if (!ApplicationsState.this.mBackgroundHandler.hasMessages(1)) {
                            ApplicationsState.this.mBackgroundHandler.sendMessage(
                                    ApplicationsState.this.mBackgroundHandler.obtainMessage(1));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mgetSizeStr, reason: not valid java name */
    public static String m1029$$Nest$mgetSizeStr(ApplicationsState applicationsState, long j) {
        if (j >= 0) {
            return AppFileSizeFormatter.formatFileSize(0, j, applicationsState.mContext);
        }
        applicationsState.getClass();
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v14, types: [com.android.settingslib.applications.ApplicationsState$12] */
    /* JADX WARN: Type inference failed for: r0v19, types: [com.android.settingslib.applications.ApplicationsState$12] */
    /* JADX WARN: Type inference failed for: r0v23, types: [com.android.settingslib.applications.ApplicationsState$12] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settingslib.applications.ApplicationsState$2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settingslib.applications.ApplicationsState$2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settingslib.applications.ApplicationsState$2] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settingslib.applications.ApplicationsState$2] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.settingslib.applications.ApplicationsState$2] */
    static {
        Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        mNewAppListForAppLabelCache = new ArrayList();
        ALPHA_COMPARATOR = new AnonymousClass1();
        final int i = 0;
        SIZE_COMPARATOR =
                new Comparator() { // from class:
                                   // com.android.settingslib.applications.ApplicationsState.2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i) {
                            case 0:
                                AppEntry appEntry = (AppEntry) obj;
                                AppEntry appEntry2 = (AppEntry) obj2;
                                long j = appEntry.size;
                                long j2 = appEntry2.size;
                                if (j < j2) {
                                    return 1;
                                }
                                if (j > j2) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry, appEntry2);
                            case 1:
                                AppEntry appEntry3 = (AppEntry) obj;
                                AppEntry appEntry4 = (AppEntry) obj2;
                                long j3 = appEntry3.internalSize;
                                long j4 = appEntry4.internalSize;
                                if (j3 < j4) {
                                    return 1;
                                }
                                if (j3 > j4) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry3, appEntry4);
                            case 2:
                                AppEntry appEntry5 = (AppEntry) obj;
                                AppEntry appEntry6 = (AppEntry) obj2;
                                long j5 = appEntry5.externalSize;
                                long j6 = appEntry6.externalSize;
                                if (j5 < j6) {
                                    return 1;
                                }
                                if (j5 > j6) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry5, appEntry6);
                            case 3:
                                AppEntry appEntry7 = (AppEntry) obj;
                                AppEntry appEntry8 = (AppEntry) obj2;
                                long j7 = appEntry7.lastUsed;
                                long j8 = appEntry8.lastUsed;
                                if (j7 < j8) {
                                    return 1;
                                }
                                if (j7 > j8) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry7, appEntry8);
                            default:
                                AppEntry appEntry9 = (AppEntry) obj;
                                AppEntry appEntry10 = (AppEntry) obj2;
                                long j9 = appEntry9.lastUpdated;
                                long j10 = appEntry10.lastUpdated;
                                if (j9 < j10) {
                                    return 1;
                                }
                                if (j9 > j10) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry9, appEntry10);
                        }
                    }
                };
        final int i2 = 1;
        INTERNAL_SIZE_COMPARATOR =
                new Comparator() { // from class:
                                   // com.android.settingslib.applications.ApplicationsState.2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i2) {
                            case 0:
                                AppEntry appEntry = (AppEntry) obj;
                                AppEntry appEntry2 = (AppEntry) obj2;
                                long j = appEntry.size;
                                long j2 = appEntry2.size;
                                if (j < j2) {
                                    return 1;
                                }
                                if (j > j2) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry, appEntry2);
                            case 1:
                                AppEntry appEntry3 = (AppEntry) obj;
                                AppEntry appEntry4 = (AppEntry) obj2;
                                long j3 = appEntry3.internalSize;
                                long j4 = appEntry4.internalSize;
                                if (j3 < j4) {
                                    return 1;
                                }
                                if (j3 > j4) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry3, appEntry4);
                            case 2:
                                AppEntry appEntry5 = (AppEntry) obj;
                                AppEntry appEntry6 = (AppEntry) obj2;
                                long j5 = appEntry5.externalSize;
                                long j6 = appEntry6.externalSize;
                                if (j5 < j6) {
                                    return 1;
                                }
                                if (j5 > j6) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry5, appEntry6);
                            case 3:
                                AppEntry appEntry7 = (AppEntry) obj;
                                AppEntry appEntry8 = (AppEntry) obj2;
                                long j7 = appEntry7.lastUsed;
                                long j8 = appEntry8.lastUsed;
                                if (j7 < j8) {
                                    return 1;
                                }
                                if (j7 > j8) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry7, appEntry8);
                            default:
                                AppEntry appEntry9 = (AppEntry) obj;
                                AppEntry appEntry10 = (AppEntry) obj2;
                                long j9 = appEntry9.lastUpdated;
                                long j10 = appEntry10.lastUpdated;
                                if (j9 < j10) {
                                    return 1;
                                }
                                if (j9 > j10) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry9, appEntry10);
                        }
                    }
                };
        final int i3 = 2;
        EXTERNAL_SIZE_COMPARATOR =
                new Comparator() { // from class:
                                   // com.android.settingslib.applications.ApplicationsState.2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i3) {
                            case 0:
                                AppEntry appEntry = (AppEntry) obj;
                                AppEntry appEntry2 = (AppEntry) obj2;
                                long j = appEntry.size;
                                long j2 = appEntry2.size;
                                if (j < j2) {
                                    return 1;
                                }
                                if (j > j2) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry, appEntry2);
                            case 1:
                                AppEntry appEntry3 = (AppEntry) obj;
                                AppEntry appEntry4 = (AppEntry) obj2;
                                long j3 = appEntry3.internalSize;
                                long j4 = appEntry4.internalSize;
                                if (j3 < j4) {
                                    return 1;
                                }
                                if (j3 > j4) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry3, appEntry4);
                            case 2:
                                AppEntry appEntry5 = (AppEntry) obj;
                                AppEntry appEntry6 = (AppEntry) obj2;
                                long j5 = appEntry5.externalSize;
                                long j6 = appEntry6.externalSize;
                                if (j5 < j6) {
                                    return 1;
                                }
                                if (j5 > j6) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry5, appEntry6);
                            case 3:
                                AppEntry appEntry7 = (AppEntry) obj;
                                AppEntry appEntry8 = (AppEntry) obj2;
                                long j7 = appEntry7.lastUsed;
                                long j8 = appEntry8.lastUsed;
                                if (j7 < j8) {
                                    return 1;
                                }
                                if (j7 > j8) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry7, appEntry8);
                            default:
                                AppEntry appEntry9 = (AppEntry) obj;
                                AppEntry appEntry10 = (AppEntry) obj2;
                                long j9 = appEntry9.lastUpdated;
                                long j10 = appEntry10.lastUpdated;
                                if (j9 < j10) {
                                    return 1;
                                }
                                if (j9 > j10) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry9, appEntry10);
                        }
                    }
                };
        final int i4 = 3;
        LAST_USED_COMPARATOR =
                new Comparator() { // from class:
                                   // com.android.settingslib.applications.ApplicationsState.2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i4) {
                            case 0:
                                AppEntry appEntry = (AppEntry) obj;
                                AppEntry appEntry2 = (AppEntry) obj2;
                                long j = appEntry.size;
                                long j2 = appEntry2.size;
                                if (j < j2) {
                                    return 1;
                                }
                                if (j > j2) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry, appEntry2);
                            case 1:
                                AppEntry appEntry3 = (AppEntry) obj;
                                AppEntry appEntry4 = (AppEntry) obj2;
                                long j3 = appEntry3.internalSize;
                                long j4 = appEntry4.internalSize;
                                if (j3 < j4) {
                                    return 1;
                                }
                                if (j3 > j4) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry3, appEntry4);
                            case 2:
                                AppEntry appEntry5 = (AppEntry) obj;
                                AppEntry appEntry6 = (AppEntry) obj2;
                                long j5 = appEntry5.externalSize;
                                long j6 = appEntry6.externalSize;
                                if (j5 < j6) {
                                    return 1;
                                }
                                if (j5 > j6) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry5, appEntry6);
                            case 3:
                                AppEntry appEntry7 = (AppEntry) obj;
                                AppEntry appEntry8 = (AppEntry) obj2;
                                long j7 = appEntry7.lastUsed;
                                long j8 = appEntry8.lastUsed;
                                if (j7 < j8) {
                                    return 1;
                                }
                                if (j7 > j8) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry7, appEntry8);
                            default:
                                AppEntry appEntry9 = (AppEntry) obj;
                                AppEntry appEntry10 = (AppEntry) obj2;
                                long j9 = appEntry9.lastUpdated;
                                long j10 = appEntry10.lastUpdated;
                                if (j9 < j10) {
                                    return 1;
                                }
                                if (j9 > j10) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry9, appEntry10);
                        }
                    }
                };
        final int i5 = 4;
        LAST_UPDATED_COMPARATOR =
                new Comparator() { // from class:
                                   // com.android.settingslib.applications.ApplicationsState.2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i5) {
                            case 0:
                                AppEntry appEntry = (AppEntry) obj;
                                AppEntry appEntry2 = (AppEntry) obj2;
                                long j = appEntry.size;
                                long j2 = appEntry2.size;
                                if (j < j2) {
                                    return 1;
                                }
                                if (j > j2) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry, appEntry2);
                            case 1:
                                AppEntry appEntry3 = (AppEntry) obj;
                                AppEntry appEntry4 = (AppEntry) obj2;
                                long j3 = appEntry3.internalSize;
                                long j4 = appEntry4.internalSize;
                                if (j3 < j4) {
                                    return 1;
                                }
                                if (j3 > j4) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry3, appEntry4);
                            case 2:
                                AppEntry appEntry5 = (AppEntry) obj;
                                AppEntry appEntry6 = (AppEntry) obj2;
                                long j5 = appEntry5.externalSize;
                                long j6 = appEntry6.externalSize;
                                if (j5 < j6) {
                                    return 1;
                                }
                                if (j5 > j6) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry5, appEntry6);
                            case 3:
                                AppEntry appEntry7 = (AppEntry) obj;
                                AppEntry appEntry8 = (AppEntry) obj2;
                                long j7 = appEntry7.lastUsed;
                                long j8 = appEntry8.lastUsed;
                                if (j7 < j8) {
                                    return 1;
                                }
                                if (j7 > j8) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry7, appEntry8);
                            default:
                                AppEntry appEntry9 = (AppEntry) obj;
                                AppEntry appEntry10 = (AppEntry) obj2;
                                long j9 = appEntry9.lastUpdated;
                                long j10 = appEntry10.lastUpdated;
                                if (j9 < j10) {
                                    return 1;
                                }
                                if (j9 > j10) {
                                    return -1;
                                }
                                return ApplicationsState.ALPHA_COMPARATOR.compare(
                                        appEntry9, appEntry10);
                        }
                    }
                };
        FILTER_PERSONAL = new AnonymousClass7();
        FILTER_WITHOUT_DISABLED_UNTIL_USED = new AnonymousClass9(13);
        FILTER_WORK = new AnonymousClass9(0);
        FILTER_PRIVATE_PROFILE = new AnonymousClass9(1);
        FILTER_DOWNLOADED_AND_LAUNCHER = new AnonymousClass9(2);
        final int i6 = 0;
        FILTER_DOWNLOADED_AND_LAUNCHER_NOT_QUIET =
                new AppFilter() { // from class:
                                  // com.android.settingslib.applications.ApplicationsState.12
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(AppEntry appEntry) {
                        switch (i6) {
                            case 0:
                                if (!appEntry.hideInQuietMode
                                        && !AppUtils.isInstant(appEntry.info)) {
                                    if (ApplicationsState.hasFlag(appEntry.info.flags, 128)
                                            || !ApplicationsState.hasFlag(appEntry.info.flags, 1)
                                            || appEntry.hasLauncherEntry
                                            || (ApplicationsState.hasFlag(appEntry.info.flags, 1)
                                                    && appEntry.isHomeApp)) {
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                ApplicationInfo applicationInfo = appEntry.info;
                                if (!applicationInfo.enabled
                                        || AppUtils.isInstant(applicationInfo)
                                        || appEntry.hideInQuietMode) {}
                                break;
                            default:
                                if (AppUtils.isInstant(appEntry.info)
                                        || !ApplicationsState.hasFlag(
                                                appEntry.info.privateFlags, 16)
                                        || appEntry.hideInQuietMode) {
                                    PackageManager packageManager = ApplicationsState.mPm;
                                    IUsbManager iUsbManager = ApplicationsState.mUsbManager;
                                    AppWidgetManager appWidgetManager =
                                            ApplicationsState.mAppWidgetManager;
                                    String str = appEntry.info.packageName;
                                    boolean hasBindAppWidgetPermission =
                                            appWidgetManager.hasBindAppWidgetPermission(str);
                                    if (AppUtils.hasPreferredActivities(packageManager, str)
                                            || str.equals(
                                                    packageManager
                                                            .getDefaultBrowserPackageNameAsUser(
                                                                    UserHandle.myUserId()))
                                            || AppUtils.hasUsbDefaults(iUsbManager, str)
                                            || hasBindAppWidgetPermission) {}
                                }
                                break;
                        }
                        return true;
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i7 = i6;
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void refreshAppEntryOnRebuild(AppEntry appEntry, boolean z) {
                        switch (i6) {
                            case 0:
                                appEntry.hideInQuietMode = z;
                                break;
                            case 1:
                                appEntry.hideInQuietMode = z;
                                break;
                            default:
                                appEntry.hideInQuietMode = z;
                                break;
                        }
                    }

                    private final void
                            init$com$android$settingslib$applications$ApplicationsState$12() {}

                    private final void
                            init$com$android$settingslib$applications$ApplicationsState$18() {}

                    private final void
                            init$com$android$settingslib$applications$ApplicationsState$23() {}
                };
        FILTER_DOWNLOADED_AND_LAUNCHER_AND_INSTANT = new AnonymousClass9(3);
        FILTER_DISABLED = new AnonymousClass15(0);
        FILTER_INSTANT = new AnonymousClass9(4);
        FILTER_ALL_ENABLED = new AnonymousClass15(1);
        final int i7 = 1;
        FILTER_ENABLED_NOT_QUIET =
                new AppFilter() { // from class:
                                  // com.android.settingslib.applications.ApplicationsState.12
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(AppEntry appEntry) {
                        switch (i7) {
                            case 0:
                                if (!appEntry.hideInQuietMode
                                        && !AppUtils.isInstant(appEntry.info)) {
                                    if (ApplicationsState.hasFlag(appEntry.info.flags, 128)
                                            || !ApplicationsState.hasFlag(appEntry.info.flags, 1)
                                            || appEntry.hasLauncherEntry
                                            || (ApplicationsState.hasFlag(appEntry.info.flags, 1)
                                                    && appEntry.isHomeApp)) {
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                ApplicationInfo applicationInfo = appEntry.info;
                                if (!applicationInfo.enabled
                                        || AppUtils.isInstant(applicationInfo)
                                        || appEntry.hideInQuietMode) {}
                                break;
                            default:
                                if (AppUtils.isInstant(appEntry.info)
                                        || !ApplicationsState.hasFlag(
                                                appEntry.info.privateFlags, 16)
                                        || appEntry.hideInQuietMode) {
                                    PackageManager packageManager = ApplicationsState.mPm;
                                    IUsbManager iUsbManager = ApplicationsState.mUsbManager;
                                    AppWidgetManager appWidgetManager =
                                            ApplicationsState.mAppWidgetManager;
                                    String str = appEntry.info.packageName;
                                    boolean hasBindAppWidgetPermission =
                                            appWidgetManager.hasBindAppWidgetPermission(str);
                                    if (AppUtils.hasPreferredActivities(packageManager, str)
                                            || str.equals(
                                                    packageManager
                                                            .getDefaultBrowserPackageNameAsUser(
                                                                    UserHandle.myUserId()))
                                            || AppUtils.hasUsbDefaults(iUsbManager, str)
                                            || hasBindAppWidgetPermission) {}
                                }
                                break;
                        }
                        return true;
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i72 = i7;
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void refreshAppEntryOnRebuild(AppEntry appEntry, boolean z) {
                        switch (i7) {
                            case 0:
                                appEntry.hideInQuietMode = z;
                                break;
                            case 1:
                                appEntry.hideInQuietMode = z;
                                break;
                            default:
                                appEntry.hideInQuietMode = z;
                                break;
                        }
                    }

                    private final void
                            init$com$android$settingslib$applications$ApplicationsState$12() {}

                    private final void
                            init$com$android$settingslib$applications$ApplicationsState$18() {}

                    private final void
                            init$com$android$settingslib$applications$ApplicationsState$23() {}
                };
        FILTER_EVERYTHING = new AnonymousClass9(5);
        FILTER_INSTALLED_APP_BY_USER = new AnonymousClass9(6);
        FILTER_APP_SETTING = new AnonymousClass9(7);
        final int i8 = 2;
        FILTER_WITH_DOMAIN_URLS =
                new AppFilter() { // from class:
                                  // com.android.settingslib.applications.ApplicationsState.12
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(AppEntry appEntry) {
                        switch (i8) {
                            case 0:
                                if (!appEntry.hideInQuietMode
                                        && !AppUtils.isInstant(appEntry.info)) {
                                    if (ApplicationsState.hasFlag(appEntry.info.flags, 128)
                                            || !ApplicationsState.hasFlag(appEntry.info.flags, 1)
                                            || appEntry.hasLauncherEntry
                                            || (ApplicationsState.hasFlag(appEntry.info.flags, 1)
                                                    && appEntry.isHomeApp)) {
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                ApplicationInfo applicationInfo = appEntry.info;
                                if (!applicationInfo.enabled
                                        || AppUtils.isInstant(applicationInfo)
                                        || appEntry.hideInQuietMode) {}
                                break;
                            default:
                                if (AppUtils.isInstant(appEntry.info)
                                        || !ApplicationsState.hasFlag(
                                                appEntry.info.privateFlags, 16)
                                        || appEntry.hideInQuietMode) {
                                    PackageManager packageManager = ApplicationsState.mPm;
                                    IUsbManager iUsbManager = ApplicationsState.mUsbManager;
                                    AppWidgetManager appWidgetManager =
                                            ApplicationsState.mAppWidgetManager;
                                    String str = appEntry.info.packageName;
                                    boolean hasBindAppWidgetPermission =
                                            appWidgetManager.hasBindAppWidgetPermission(str);
                                    if (AppUtils.hasPreferredActivities(packageManager, str)
                                            || str.equals(
                                                    packageManager
                                                            .getDefaultBrowserPackageNameAsUser(
                                                                    UserHandle.myUserId()))
                                            || AppUtils.hasUsbDefaults(iUsbManager, str)
                                            || hasBindAppWidgetPermission) {}
                                }
                                break;
                        }
                        return true;
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i72 = i8;
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void refreshAppEntryOnRebuild(AppEntry appEntry, boolean z) {
                        switch (i8) {
                            case 0:
                                appEntry.hideInQuietMode = z;
                                break;
                            case 1:
                                appEntry.hideInQuietMode = z;
                                break;
                            default:
                                appEntry.hideInQuietMode = z;
                                break;
                        }
                    }

                    private final void
                            init$com$android$settingslib$applications$ApplicationsState$12() {}

                    private final void
                            init$com$android$settingslib$applications$ApplicationsState$18() {}

                    private final void
                            init$com$android$settingslib$applications$ApplicationsState$23() {}
                };
        FILTER_NOT_HIDE = new AnonymousClass24();
        FILTER_GAMES = new AnonymousClass9(8);
        new AnonymousClass9(9);
        new AnonymousClass9(10);
        new AnonymousClass9(11);
        FILTER_MANAGE_UNKNOWN_SOURCE_APPS = new AnonymousClass30();
        FILTER_APPS_EXCEPT_GAMES = new AnonymousClass9(12);
    }

    public ApplicationsState(Application application, IPackageManager iPackageManager) {
        this.mContext = application;
        mPm = application.getPackageManager();
        this.mIpm = iPackageManager;
        UserManager userManager = (UserManager) application.getSystemService(UserManager.class);
        this.mUm = userManager;
        this.mStats = (StorageStatsManager) application.getSystemService(StorageStatsManager.class);
        for (int i : userManager.getProfileIdsWithDisabled(UserHandle.myUserId())) {
            this.mEntriesMap.put(i, new HashMap());
        }
        HandlerThread handlerThread = new HandlerThread("ApplicationsState.Loader");
        this.mThread = handlerThread;
        handlerThread.start();
        this.mBackgroundHandler = new BackgroundHandler(handlerThread.getLooper());
        this.mAdminRetrieveFlags = 4299195008L;
        this.mRetrieveFlags = 4295000704L;
        for (ModuleInfo moduleInfo : mPm.getInstalledModules(0)) {
            this.mSystemModules.put(
                    moduleInfo.getPackageName(), Boolean.valueOf(moduleInfo.isHidden()));
            if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags
                    .provideInfoOfApkInApex()) {
                Iterator it = moduleInfo.getApkInApexPackageNames().iterator();
                while (it.hasNext()) {
                    this.mSystemModules.put(
                            (String) it.next(), Boolean.valueOf(moduleInfo.isHidden()));
                }
            }
        }
        this.mDrawableFactory = IconDrawableFactory.newInstance(this.mContext);
        mUsageStatsManager = (UsageStatsManager) this.mContext.getSystemService("usagestats");
        mAppWidgetManager = AppWidgetManager.getInstance(this.mContext);
        mUsbManager = IUsbManager.Stub.asInterface(ServiceManager.getService("usb"));
        synchronized (this.mEntriesMap) {
            try {
                this.mEntriesMap.wait(1L);
            } catch (InterruptedException unused) {
            }
        }
    }

    public static ApplicationsState getInstance(Application application) {
        return getInstance(application, AppGlobals.getPackageManager());
    }

    public static boolean hasFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public final void addPackage(int i, String str) {
        try {
            synchronized (this.mEntriesMap) {
                try {
                    if (this.mResumed) {
                        if (indexOfApplicationInfoLocked(i, str) >= 0) {
                            return;
                        }
                        ApplicationInfo applicationInfo =
                                this.mIpm.getApplicationInfo(
                                        str,
                                        this.mUm.isUserAdmin(i)
                                                ? this.mAdminRetrieveFlags
                                                : this.mRetrieveFlags,
                                        i);
                        if (applicationInfo == null) {
                            return;
                        }
                        if (!applicationInfo.enabled) {
                            if (applicationInfo.enabledSetting != 3) {
                                return;
                            } else {
                                this.mHaveDisabledApps = true;
                            }
                        }
                        if (!this.mHaveDisabledApps
                                && (AppUtils.isAutoDisabled(applicationInfo)
                                        || AppUtils.isManualDisabled(applicationInfo))) {
                            this.mHaveDisabledApps = true;
                        }
                        AppUtils.isInstant(applicationInfo);
                        this.mApplications.add(applicationInfo);
                        if (!this.mBackgroundHandler.hasMessages(2)) {
                            this.mBackgroundHandler.sendEmptyMessage(2);
                        }
                        if (!this.mMainHandler.hasMessages(2)) {
                            this.mMainHandler.sendEmptyMessage(2);
                        }
                    }
                } finally {
                }
            }
        } catch (RemoteException unused) {
        }
    }

    public void clearEntries() {
        for (int i = 0; i < this.mEntriesMap.size(); i++) {
            ((HashMap) this.mEntriesMap.valueAt(i)).clear();
        }
        this.mAppEntries.clear();
    }

    public final void doPauseLocked() {
        this.mResumed = false;
        PackageIntentReceiver packageIntentReceiver = this.mPackageIntentReceiver;
        if (packageIntentReceiver != null) {
            ApplicationsState.this.mContext.unregisterReceiver(packageIntentReceiver);
            this.mPackageIntentReceiver = null;
        }
        PackageIntentReceiver packageIntentReceiver2 = this.mClonePackageIntentReceiver;
        if (packageIntentReceiver2 != null) {
            ApplicationsState.this.mContext.unregisterReceiver(packageIntentReceiver2);
            this.mClonePackageIntentReceiver = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:174:0x040b A[Catch: all -> 0x03e2, TryCatch #1 {, blocks: (B:165:0x03cb, B:167:0x03d3, B:169:0x03eb, B:171:0x03f3, B:172:0x0403, B:174:0x040b, B:175:0x0410, B:176:0x0411, B:183:0x03e5), top: B:164:0x03cb, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doResumeIfNeededLocked() {
        /*
            Method dump skipped, instructions count: 1266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.applications.ApplicationsState.doResumeIfNeededLocked():void");
    }

    public final void ensureIcon(AppEntry appEntry) {
        if (appEntry.icon != null) {
            return;
        }
        synchronized (appEntry) {
            appEntry.ensureIconLocked(this.mContext, this.mDrawableFactory);
        }
    }

    public final void ensureLabelDescription(AppEntry appEntry) {
        if (appEntry.labelDescription != null) {
            return;
        }
        synchronized (appEntry) {
            Context context = this.mContext;
            if (UserManager.get(context)
                    .isManagedProfile(UserHandle.getUserId(appEntry.info.uid))) {
                appEntry.labelDescription =
                        context.getString(
                                com.android.settings.R.string
                                        .accessibility_work_profile_app_description,
                                appEntry.label);
            } else {
                appEntry.labelDescription = appEntry.label;
            }
        }
    }

    public final AppEntry getEntry(int i, String str) {
        AppEntry appEntry;
        ApplicationInfo applicationInfo;
        if (TextUtils.isEmpty(str)) {
            Log.d("ApplicationsState", "packageName is empty");
            return null;
        }
        synchronized (this.mEntriesMap) {
            try {
                HashMap hashMap = (HashMap) this.mEntriesMap.get(i);
                appEntry = hashMap != null ? (AppEntry) hashMap.get(str) : null;
                if (appEntry == null) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= ((ArrayList) this.mApplications).size()) {
                            applicationInfo = null;
                            break;
                        }
                        applicationInfo =
                                (ApplicationInfo) ((ArrayList) this.mApplications).get(i2);
                        if (str.equals(applicationInfo.packageName)
                                && i == UserHandle.getUserId(applicationInfo.uid)) {
                            break;
                        }
                        i2++;
                    }
                    if (applicationInfo == null) {
                        try {
                            applicationInfo =
                                    this.mIpm.getApplicationInfo(
                                            str,
                                            GoodSettingsContract.PreferenceFlag.FLAG_NEED_TYPE,
                                            i);
                        } catch (RemoteException e) {
                            Log.w("ApplicationsState", "getEntry couldn't reach PackageManager", e);
                            return null;
                        }
                    }
                    if (applicationInfo != null) {
                        appEntry = getEntryLocked(applicationInfo);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return appEntry;
    }

    public final AppEntry getEntryLocked(ApplicationInfo applicationInfo) {
        int userId = UserHandle.getUserId(applicationInfo.uid);
        HashMap hashMap = (HashMap) this.mEntriesMap.get(userId);
        AppEntry appEntry =
                hashMap != null ? (AppEntry) hashMap.get(applicationInfo.packageName) : null;
        if (appEntry == null) {
            Boolean bool = (Boolean) this.mSystemModules.get(applicationInfo.packageName);
            if (bool == null ? false : bool.booleanValue()) {
                return null;
            }
            Context context = this.mContext;
            long j = this.mCurId;
            this.mCurId = 1 + j;
            appEntry = new AppEntry(context, applicationInfo, j);
            HashMap hashMap2 = (HashMap) this.mEntriesMap.get(userId);
            if (hashMap2 != null) {
                hashMap2.put(applicationInfo.packageName, appEntry);
                this.mAppEntries.add(appEntry);
            }
        } else if (appEntry.info != applicationInfo) {
            appEntry.info = applicationInfo;
        }
        return appEntry;
    }

    public final int indexOfApplicationInfoLocked(int i, String str) {
        for (int size = this.mApplications.size() - 1; size >= 0; size--) {
            ApplicationInfo applicationInfo = (ApplicationInfo) this.mApplications.get(size);
            if (applicationInfo.packageName.equals(str)
                    && UserHandle.getUserId(applicationInfo.uid) == i) {
                return size;
            }
        }
        return -1;
    }

    public final Session newSession(Callbacks callbacks, Lifecycle lifecycle) {
        Session session = new Session(callbacks, lifecycle);
        synchronized (this.mEntriesMap) {
            this.mSessions.add(session);
        }
        return session;
    }

    public final void removePackage(int i, String str) {
        synchronized (this.mEntriesMap) {
            try {
                int indexOfApplicationInfoLocked = indexOfApplicationInfoLocked(i, str);
                if (indexOfApplicationInfoLocked >= 0) {
                    AppEntry appEntry = (AppEntry) ((HashMap) this.mEntriesMap.get(i)).get(str);
                    if (appEntry != null) {
                        ((HashMap) this.mEntriesMap.get(i)).remove(str);
                        this.mAppEntries.remove(appEntry);
                    }
                    ApplicationInfo applicationInfo =
                            (ApplicationInfo) this.mApplications.get(indexOfApplicationInfoLocked);
                    this.mApplications.remove(indexOfApplicationInfoLocked);
                    if (!applicationInfo.enabled) {
                        this.mHaveDisabledApps = false;
                        Iterator it = this.mApplications.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            ApplicationInfo applicationInfo2 = (ApplicationInfo) it.next();
                            if (!applicationInfo2.enabled) {
                                this.mHaveDisabledApps = true;
                                break;
                            } else if (!this.mHaveDisabledApps
                                    && (AppUtils.isAutoDisabled(applicationInfo2)
                                            || AppUtils.isManualDisabled(applicationInfo2))) {
                                this.mHaveDisabledApps = true;
                            }
                        }
                    }
                    if (AppUtils.isInstant(applicationInfo)) {
                        Iterator it2 = this.mApplications.iterator();
                        while (it2.hasNext()
                                && !AppUtils.isInstant((ApplicationInfo) it2.next())) {}
                    }
                    if (!this.mMainHandler.hasMessages(2)) {
                        this.mMainHandler.sendEmptyMessage(2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setInterestingConfigChanges(InterestingConfigChanges interestingConfigChanges) {
        this.mInterestingConfigChanges = interestingConfigChanges;
    }

    public static ApplicationsState getInstance(
            Application application, IPackageManager iPackageManager) {
        ApplicationsState applicationsState;
        synchronized (sLock) {
            try {
                if (sInstance == null) {
                    sInstance = new ApplicationsState(application, iPackageManager);
                }
                applicationsState = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return applicationsState;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.applications.ApplicationsState$15, reason: invalid class name */
    public final class AnonymousClass15 implements AppFilter {
        public final /* synthetic */ int $r8$classId;
        public final Object mRestrictInfoMap;

        public AnonymousClass15(int i) {
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.mRestrictInfoMap = new HashMap();
                    break;
                default:
                    this.mRestrictInfoMap = new HashMap();
                    break;
            }
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(AppEntry appEntry) {
            List list;
            List list2;
            switch (this.$r8$classId) {
                case 0:
                    ApplicationInfo applicationInfo = appEntry.info;
                    return (!applicationInfo.enabled
                                    || ((list =
                                                            (List)
                                                                    ((HashMap)
                                                                                    this
                                                                                            .mRestrictInfoMap)
                                                                            .get(
                                                                                    applicationInfo
                                                                                            .packageName))
                                                    != null
                                            && list.contains(Integer.valueOf(appEntry.info.uid))))
                            && !AppUtils.isInstant(appEntry.info);
                case 1:
                    ApplicationInfo applicationInfo2 = appEntry.info;
                    return applicationInfo2.enabled
                            && !AppUtils.isInstant(applicationInfo2)
                            && ((list2 =
                                                    (List)
                                                            ((HashMap) this.mRestrictInfoMap)
                                                                    .get(appEntry.info.packageName))
                                            == null
                                    || !list2.contains(Integer.valueOf(appEntry.info.uid)));
                default:
                    return Objects.equals(appEntry.info.volumeUuid, (String) this.mRestrictInfoMap);
            }
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {
            switch (this.$r8$classId) {
                case 0:
                    ((HashMap) this.mRestrictInfoMap).clear();
                    List<SemAppRestrictionManager.AppRestrictionInfo> restrictedList =
                            new SemAppRestrictionManager().getRestrictedList(0);
                    if (restrictedList != null) {
                        for (SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo :
                                restrictedList) {
                            if (appRestrictionInfo.getRestrictionInfo() != null) {
                                if (((HashMap) this.mRestrictInfoMap)
                                                .get(appRestrictionInfo.getPackageName())
                                        == null) {
                                    ((HashMap) this.mRestrictInfoMap)
                                            .put(
                                                    appRestrictionInfo.getPackageName(),
                                                    new ArrayList());
                                }
                                ((List)
                                                ((HashMap) this.mRestrictInfoMap)
                                                        .get(appRestrictionInfo.getPackageName()))
                                        .add(Integer.valueOf(appRestrictionInfo.getUid()));
                            }
                        }
                        break;
                    }
                    break;
                case 1:
                    ((HashMap) this.mRestrictInfoMap).clear();
                    List<SemAppRestrictionManager.AppRestrictionInfo> restrictedList2 =
                            new SemAppRestrictionManager().getRestrictedList(0);
                    if (restrictedList2 != null) {
                        for (SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo2 :
                                restrictedList2) {
                            if (appRestrictionInfo2.getRestrictionInfo() != null) {
                                if (((HashMap) this.mRestrictInfoMap)
                                                .get(appRestrictionInfo2.getPackageName())
                                        == null) {
                                    ((HashMap) this.mRestrictInfoMap)
                                            .put(
                                                    appRestrictionInfo2.getPackageName(),
                                                    new ArrayList());
                                }
                                ((List)
                                                ((HashMap) this.mRestrictInfoMap)
                                                        .get(appRestrictionInfo2.getPackageName()))
                                        .add(Integer.valueOf(appRestrictionInfo2.getUid()));
                            }
                        }
                        break;
                    }
                    break;
            }
        }

        public AnonymousClass15(String str) {
            this.$r8$classId = 2;
            this.mRestrictInfoMap = str;
        }

        private final void
                init$com$android$settingslib$applications$ApplicationsState$VolumeFilter() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.applications.ApplicationsState$9, reason: invalid class name */
    public final class AnonymousClass9 implements AppFilter {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass9(int i) {
            this.$r8$classId = i;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(AppEntry appEntry) {
            String str;
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean filterApp;
            switch (this.$r8$classId) {
                case 0:
                    return (appEntry.showInPersonalTab
                                    || !"android.os.usertype.profile.MANAGED"
                                            .equals(appEntry.mProfileType)
                                    || SemDualAppManager.isDualAppId(
                                            UserHandle.getUserId(appEntry.info.uid)))
                            ? false
                            : true;
                case 1:
                    return !appEntry.showInPersonalTab
                            && com.android.internal.hidden_from_bootclasspath.android.os.Flags
                                    .allowPrivateProfile()
                            && Flags.enablePrivateSpaceFeatures()
                            && "android.os.usertype.profile.PRIVATE".equals(appEntry.mProfileType);
                case 2:
                    if (AppUtils.isInstant(appEntry.info)) {
                        return false;
                    }
                    return ApplicationsState.hasFlag(appEntry.info.flags, 128)
                            || !ApplicationsState.hasFlag(appEntry.info.flags, 1)
                            || appEntry.hasLauncherEntry
                            || (ApplicationsState.hasFlag(appEntry.info.flags, 1)
                                    && appEntry.isHomeApp);
                case 3:
                    return AppUtils.isInstant(appEntry.info)
                            || ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER.filterApp(appEntry);
                case 4:
                    return AppUtils.isInstant(appEntry.info);
                case 5:
                    return true;
                case 6:
                    if (!ApplicationsState.hasFlag(appEntry.info.flags, 1)) {
                        ApplicationInfo applicationInfo = appEntry.info;
                        if (applicationInfo.isArchived) {
                            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(
                                            "Filter installed app by user because of archived"
                                                + " [package name =  "),
                                    appEntry.info.packageName,
                                    "]",
                                    "ApplicationsState");
                        } else {
                            String str2 = applicationInfo.packageName;
                            try {
                                InstallSourceInfo installSourceInfo =
                                        ApplicationsState.mPm.getInstallSourceInfo(str2);
                                str = installSourceInfo.getInstallingPackageName();
                                String originatingPackageName =
                                        installSourceInfo.getOriginatingPackageName();
                                String initiatingPackageName =
                                        installSourceInfo.getInitiatingPackageName();
                                if (originatingPackageName != null
                                        && initiatingPackageName != null) {
                                    if ((ApplicationsState.mPm.getApplicationInfo(
                                                                    initiatingPackageName, 0)
                                                            .flags
                                                    & 1)
                                            != 0) {
                                        str = originatingPackageName;
                                    }
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.e(
                                        "ApplicationsState",
                                        "Exception while retrieving the package installer of "
                                                + str2,
                                        e);
                                str = null;
                            }
                            if (str != null) {
                                return true;
                            }
                        }
                    }
                    return false;
                case 7:
                    return ApplicationsState.mPm.resolveActivityAsUser(
                                    new Intent(
                                                    "com.sec.android.intent.action.SEC_APPLICATION_SETTINGS")
                                            .setPackage(appEntry.info.packageName),
                                    0,
                                    UserHandle.getUserId(appEntry.info.uid))
                            != null;
                case 8:
                    synchronized (appEntry.info) {
                        try {
                            z =
                                    ApplicationsState.hasFlag(appEntry.info.flags, 33554432)
                                            || appEntry.info.category == 0;
                        } finally {
                        }
                    }
                    return z;
                case 9:
                    synchronized (appEntry) {
                        z2 = true;
                        if (appEntry.info.category != 1) {
                            z2 = false;
                        }
                    }
                    return z2;
                case 10:
                    synchronized (appEntry) {
                        z3 = appEntry.info.category == 2;
                    }
                    return z3;
                case 11:
                    synchronized (appEntry) {
                        z4 = appEntry.info.category == 3;
                    }
                    return z4;
                case 12:
                    synchronized (appEntry) {
                        filterApp = ApplicationsState.FILTER_GAMES.filterApp(appEntry);
                    }
                    return !filterApp;
                default:
                    return appEntry.info.enabledSetting != 4;
            }
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {
            int i = this.$r8$classId;
        }

        private final void init$com$android$settingslib$applications$ApplicationsState$10() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$11() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$13() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$16() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$19() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$20() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$22() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$25() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$26() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$27() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$28() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$31() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$8() {}

        private final void init$com$android$settingslib$applications$ApplicationsState$9() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface AppFilter {
        boolean filterApp(AppEntry appEntry);

        void init();

        default void init(Context context) {
            init();
        }

        default void refreshAppEntryOnRebuild(AppEntry appEntry, boolean z) {}
    }
}
