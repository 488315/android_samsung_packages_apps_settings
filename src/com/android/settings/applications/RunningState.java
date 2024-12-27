package com.android.settings.applications;

import android.R;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.format.Formatter;
import android.util.SparseArray;

import com.android.settingslib.Utils;
import com.android.settingslib.applications.InterestingConfigChanges;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RunningState {
    public static final Object sGlobalLock = new Object();
    public static RunningState sInstance;
    public final ActivityManager mAm;
    public final Context mApplicationContext;
    public final AnonymousClass1 mBackgroundComparator;
    public final BackgroundHandler mBackgroundHandler;
    public ArrayList mBackgroundItems;
    public long mBackgroundProcessMemory;
    public final AnonymousClass2 mHandler;
    public boolean mHaveData;
    public final boolean mHideManagedProfiles;
    public ArrayList mMergedItems;
    public final int mMyUserId;
    public final PackageManager mPm;
    public OnRefreshUiListener mRefreshUiListener;
    public boolean mResumed;
    public final AnonymousClass1 mServiceProcessComparator;
    public long mServiceProcessMemory;
    public final UserManager mUm;
    public final UserManagerBroadcastReceiver mUmBroadcastReceiver;
    public ArrayList mUserBackgroundItems;
    public boolean mWatchingBackgroundItems;
    public final InterestingConfigChanges mInterestingConfigChanges =
            new InterestingConfigChanges();
    public final SparseArray mServiceProcessesByName = new SparseArray();
    public final SparseArray mServiceProcessesByPid = new SparseArray();
    public final ArrayList mInterestingProcesses = new ArrayList();
    public final SparseArray mRunningProcesses = new SparseArray();
    public final ArrayList mProcessItems = new ArrayList();
    public final ArrayList mAllProcessItems = new ArrayList();
    public final SparseArray mOtherUserMergedItems = new SparseArray();
    public final SparseArray mOtherUserBackgroundItems = new SparseArray();
    public final SparseArray mTmpAppProcesses = new SparseArray();
    public int mSequence = 0;
    public final Object mLock = new Object();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppProcessInfo {
        public boolean hasForegroundServices;
        public boolean hasServices;
        public final ActivityManager.RunningAppProcessInfo info;

        public AppProcessInfo(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
            this.info = runningAppProcessInfo;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BackgroundHandler extends Handler {
        public BackgroundHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:399:0x074e  */
        /* JADX WARN: Removed duplicated region for block: B:403:0x075e  */
        /* JADX WARN: Removed duplicated region for block: B:412:0x0779  */
        /* JADX WARN: Removed duplicated region for block: B:417:0x07cc A[LOOP:25: B:415:0x07c4->B:417:0x07cc, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:421:0x07dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:429:0x07f4 A[Catch: all -> 0x07ed, TryCatch #5 {all -> 0x07ed, blocks: (B:422:0x07dd, B:424:0x07e3, B:427:0x07f0, B:429:0x07f4, B:430:0x07fc), top: B:421:0x07dd }] */
        /* JADX WARN: Removed duplicated region for block: B:458:0x07c2  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r27) {
            /*
                Method dump skipped, instructions count: 2110
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.applications.RunningState.BackgroundHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class BaseItem {
        public long mActiveSince;
        public boolean mBackground;
        public int mCurSeq;
        public String mCurSizeStr;
        public String mDescription;
        public CharSequence mDisplayLabel;
        public final boolean mIsProcess;
        public String mLabel;
        public PackageItemInfo mPackageInfo;
        public long mSize;
        public String mSizeStr;
        public final int mUserId;

        public BaseItem(int i, boolean z) {
            this.mIsProcess = z;
            this.mUserId = i;
        }

        public Drawable loadIcon(Context context, RunningState runningState) {
            PackageItemInfo packageItemInfo = this.mPackageInfo;
            if (packageItemInfo == null) {
                return null;
            }
            return runningState.mPm.getUserBadgedIcon(
                    packageItemInfo.loadUnbadgedIcon(runningState.mPm),
                    new UserHandle(this.mUserId));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MergedItem extends BaseItem {
        public final ArrayList mChildren;
        public int mLastNumProcesses;
        public int mLastNumServices;
        public final ArrayList mOtherProcesses;
        public ProcessItem mProcess;
        public final ArrayList mServices;
        public UserState mUser;

        public MergedItem(int i) {
            super(i, false);
            this.mOtherProcesses = new ArrayList();
            this.mServices = new ArrayList();
            this.mChildren = new ArrayList();
            this.mLastNumProcesses = -1;
            this.mLastNumServices = -1;
        }

        @Override // com.android.settings.applications.RunningState.BaseItem
        public final Drawable loadIcon(Context context, RunningState runningState) {
            UserState userState = this.mUser;
            if (userState == null) {
                return super.loadIcon(context, runningState);
            }
            Drawable drawable = userState.mIcon;
            if (drawable == null) {
                return context.getDrawable(R.drawable.ic_menu_redo_material);
            }
            Drawable.ConstantState constantState = drawable.getConstantState();
            return constantState == null ? this.mUser.mIcon : constantState.newDrawable();
        }

        public final void setDescription(Context context, int i, int i2) {
            if (this.mLastNumProcesses == i && this.mLastNumServices == i2) {
                return;
            }
            this.mLastNumProcesses = i;
            this.mLastNumServices = i2;
            this.mDescription =
                    context.getResources()
                            .getString(
                                    i != 1
                                            ? i2 != 1
                                                    ? com.android.settings.R.string
                                                            .running_processes_item_description_p_p
                                                    : com.android.settings.R.string
                                                            .running_processes_item_description_p_s
                                            : i2 != 1
                                                    ? com.android.settings.R.string
                                                            .running_processes_item_description_s_p
                                                    : com.android.settings.R.string
                                                            .running_processes_item_description_s_s,
                                    Integer.valueOf(i),
                                    Integer.valueOf(i2));
        }

        public final void update(Context context, boolean z) {
            this.mBackground = z;
            int i = 0;
            if (this.mUser == null) {
                ProcessItem processItem = this.mProcess;
                this.mPackageInfo = processItem.mPackageInfo;
                this.mDisplayLabel = processItem.mDisplayLabel;
                this.mLabel = processItem.mLabel;
                if (!z) {
                    setDescription(
                            context,
                            this.mOtherProcesses.size() + (processItem.mPid > 0 ? 1 : 0),
                            this.mServices.size());
                }
                this.mActiveSince = -1L;
                while (i < this.mServices.size()) {
                    long j = ((ServiceItem) this.mServices.get(i)).mActiveSince;
                    if (j >= 0 && this.mActiveSince < j) {
                        this.mActiveSince = j;
                    }
                    i++;
                }
                return;
            }
            this.mPackageInfo = ((MergedItem) this.mChildren.get(0)).mProcess.mPackageInfo;
            UserState userState = this.mUser;
            String str = userState != null ? userState.mLabel : null;
            this.mLabel = str;
            this.mDisplayLabel = str;
            this.mActiveSince = -1L;
            int i2 = 0;
            int i3 = 0;
            while (i < this.mChildren.size()) {
                MergedItem mergedItem = (MergedItem) this.mChildren.get(i);
                i2 += mergedItem.mLastNumProcesses;
                i3 += mergedItem.mLastNumServices;
                long j2 = mergedItem.mActiveSince;
                if (j2 >= 0 && this.mActiveSince < j2) {
                    this.mActiveSince = j2;
                }
                i++;
            }
            if (this.mBackground) {
                return;
            }
            setDescription(context, i2, i3);
        }

        public final void updateSize(Context context) {
            int i = 0;
            if (this.mUser != null) {
                this.mSize = 0L;
                while (i < this.mChildren.size()) {
                    MergedItem mergedItem = (MergedItem) this.mChildren.get(i);
                    mergedItem.updateSize(context);
                    this.mSize += mergedItem.mSize;
                    i++;
                }
            } else {
                this.mSize = this.mProcess.mSize;
                while (i < this.mOtherProcesses.size()) {
                    this.mSize += ((ProcessItem) this.mOtherProcesses.get(i)).mSize;
                    i++;
                }
            }
            String formatShortFileSize = Formatter.formatShortFileSize(context, this.mSize);
            if (formatShortFileSize.equals(this.mSizeStr)) {
                return;
            }
            this.mSizeStr = formatShortFileSize;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnRefreshUiListener {
        void onRefreshUi(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ProcessItem extends BaseItem {
        public long mActiveSince;
        public ProcessItem mClient;
        public final SparseArray mDependentProcesses;
        public boolean mInteresting;
        public boolean mIsStarted;
        public boolean mIsSystem;
        public int mLastNumDependentProcesses;
        public MergedItem mMergedItem;
        public int mPid;
        public final String mProcessName;
        public ActivityManager.RunningAppProcessInfo mRunningProcessInfo;
        public int mRunningSeq;
        public final HashMap mServices;
        public final int mUid;

        public ProcessItem(Context context, int i, String str) {
            super(UserHandle.getUserId(i), true);
            this.mServices = new HashMap();
            this.mDependentProcesses = new SparseArray();
            this.mDescription =
                    context.getResources()
                            .getString(com.android.settings.R.string.service_process_name, str);
            this.mUid = i;
            this.mProcessName = str;
        }

        public final void addDependentProcesses(ArrayList arrayList, ArrayList arrayList2) {
            int size = this.mDependentProcesses.size();
            for (int i = 0; i < size; i++) {
                ProcessItem processItem = (ProcessItem) this.mDependentProcesses.valueAt(i);
                processItem.addDependentProcesses(arrayList, arrayList2);
                arrayList.add(processItem);
                if (processItem.mPid > 0) {
                    arrayList2.add(processItem);
                }
            }
        }

        public final boolean buildDependencyChain(PackageManager packageManager, int i) {
            int size = this.mDependentProcesses.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                ProcessItem processItem = (ProcessItem) this.mDependentProcesses.valueAt(i2);
                if (processItem.mClient != this) {
                    processItem.mClient = this;
                    z = true;
                }
                processItem.mCurSeq = i;
                processItem.ensureLabel(packageManager);
                z |= processItem.buildDependencyChain(packageManager, i);
            }
            if (this.mLastNumDependentProcesses == this.mDependentProcesses.size()) {
                return z;
            }
            this.mLastNumDependentProcesses = this.mDependentProcesses.size();
            return true;
        }

        public final void ensureLabel(PackageManager packageManager) {
            CharSequence text;
            int i = this.mUid;
            if (this.mLabel != null) {
                return;
            }
            try {
                ApplicationInfo applicationInfo =
                        packageManager.getApplicationInfo(this.mProcessName, 4194304);
                if (applicationInfo.uid == i) {
                    CharSequence loadLabel = applicationInfo.loadLabel(packageManager);
                    this.mDisplayLabel = loadLabel;
                    this.mLabel = loadLabel.toString();
                    this.mPackageInfo = applicationInfo;
                    return;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
            String[] packagesForUid = packageManager.getPackagesForUid(i);
            if (packagesForUid.length == 1) {
                try {
                    ApplicationInfo applicationInfo2 =
                            packageManager.getApplicationInfo(packagesForUid[0], 4194304);
                    CharSequence loadLabel2 = applicationInfo2.loadLabel(packageManager);
                    this.mDisplayLabel = loadLabel2;
                    this.mLabel = loadLabel2.toString();
                    this.mPackageInfo = applicationInfo2;
                    return;
                } catch (PackageManager.NameNotFoundException unused2) {
                }
            }
            for (String str : packagesForUid) {
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                    int i2 = packageInfo.sharedUserLabel;
                    if (i2 != 0
                            && (text = packageManager.getText(str, i2, packageInfo.applicationInfo))
                                    != null) {
                        this.mDisplayLabel = text;
                        this.mLabel = text.toString();
                        this.mPackageInfo = packageInfo.applicationInfo;
                        return;
                    }
                } catch (PackageManager.NameNotFoundException unused3) {
                }
            }
            if (this.mServices.size() > 0) {
                ApplicationInfo applicationInfo3 =
                        ((ServiceItem) this.mServices.values().iterator().next())
                                .mServiceInfo
                                .applicationInfo;
                this.mPackageInfo = applicationInfo3;
                CharSequence loadLabel3 = applicationInfo3.loadLabel(packageManager);
                this.mDisplayLabel = loadLabel3;
                this.mLabel = loadLabel3.toString();
                return;
            }
            try {
                ApplicationInfo applicationInfo4 =
                        packageManager.getApplicationInfo(packagesForUid[0], 4194304);
                CharSequence loadLabel4 = applicationInfo4.loadLabel(packageManager);
                this.mDisplayLabel = loadLabel4;
                this.mLabel = loadLabel4.toString();
                this.mPackageInfo = applicationInfo4;
            } catch (PackageManager.NameNotFoundException unused4) {
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ServiceItem extends BaseItem {
        public MergedItem mMergedItem;
        public ActivityManager.RunningServiceInfo mRunningService;
        public ServiceInfo mServiceInfo;
        public boolean mShownAsStarted;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UserManagerBroadcastReceiver extends BroadcastReceiver {
        public volatile boolean usersChanged;

        public UserManagerBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            synchronized (RunningState.this.mLock) {
                try {
                    RunningState runningState = RunningState.this;
                    if (runningState.mResumed) {
                        runningState.mHaveData = false;
                        runningState.mBackgroundHandler.removeMessages(1);
                        RunningState.this.mBackgroundHandler.sendEmptyMessage(1);
                        RunningState.this.mBackgroundHandler.removeMessages(2);
                        RunningState.this.mBackgroundHandler.sendEmptyMessage(2);
                    } else {
                        this.usersChanged = true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UserState {
        public Drawable mIcon;
        public String mLabel;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.applications.RunningState$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settings.applications.RunningState$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.settings.applications.RunningState$2] */
    public RunningState(Context context) {
        final int i = 1;
        this.mServiceProcessComparator =
                new Comparator(
                        this) { // from class: com.android.settings.applications.RunningState.1
                    public final /* synthetic */ RunningState this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i) {
                            case 0:
                                MergedItem mergedItem = (MergedItem) obj;
                                MergedItem mergedItem2 = (MergedItem) obj2;
                                int i2 = mergedItem.mUserId;
                                int i3 = mergedItem2.mUserId;
                                if (i2 == i3) {
                                    ProcessItem processItem = mergedItem.mProcess;
                                    ProcessItem processItem2 = mergedItem2.mProcess;
                                    if (processItem == processItem2) {
                                        String str = mergedItem.mLabel;
                                        String str2 = mergedItem2.mLabel;
                                        if (str != str2) {
                                            if (str != null) {
                                                return str.compareTo(str2);
                                            }
                                            return -1;
                                        }
                                    } else {
                                        if (processItem == null) {
                                            return -1;
                                        }
                                        if (processItem2 != null) {
                                            ActivityManager.RunningAppProcessInfo
                                                    runningAppProcessInfo =
                                                            processItem.mRunningProcessInfo;
                                            ActivityManager.RunningAppProcessInfo
                                                    runningAppProcessInfo2 =
                                                            processItem2.mRunningProcessInfo;
                                            boolean z = runningAppProcessInfo.importance >= 400;
                                            if (z == (runningAppProcessInfo2.importance >= 400)) {
                                                boolean z2 = (runningAppProcessInfo.flags & 4) != 0;
                                                if (z2
                                                        == ((runningAppProcessInfo2.flags & 4)
                                                                != 0)) {
                                                    int i4 = runningAppProcessInfo.lru;
                                                    int i5 = runningAppProcessInfo2.lru;
                                                    if (i4 == i5) {
                                                        String str3 = processItem.mLabel;
                                                        String str4 = processItem2.mLabel;
                                                        if (str3 != str4) {
                                                            if (str3 != null) {
                                                                if (str4 == null) {
                                                                    return -1;
                                                                }
                                                                return str3.compareTo(str4);
                                                            }
                                                        }
                                                    } else if (i4 < i5) {
                                                        return -1;
                                                    }
                                                } else if (z2) {
                                                    return -1;
                                                }
                                            } else if (!z) {
                                                return -1;
                                            }
                                        }
                                    }
                                    return 0;
                                }
                                int i6 = this.this$0.mMyUserId;
                                if (i2 == i6) {
                                    return -1;
                                }
                                if (i3 != i6 && i2 < i3) {
                                    return -1;
                                }
                                return 1;
                            default:
                                ProcessItem processItem3 = (ProcessItem) obj;
                                ProcessItem processItem4 = (ProcessItem) obj2;
                                int i7 = processItem3.mUserId;
                                int i8 = processItem4.mUserId;
                                if (i7 != i8) {
                                    int i9 = this.this$0.mMyUserId;
                                    if (i7 != i9 && (i8 == i9 || i7 >= i8)) {
                                        return 1;
                                    }
                                } else {
                                    boolean z3 = processItem3.mIsStarted;
                                    if (z3 == processItem4.mIsStarted) {
                                        boolean z4 = processItem3.mIsSystem;
                                        if (z4 == processItem4.mIsSystem) {
                                            long j = processItem3.mActiveSince;
                                            long j2 = processItem4.mActiveSince;
                                            if (j == j2) {
                                                return 0;
                                            }
                                            if (j <= j2) {
                                                return 1;
                                            }
                                        } else if (z4) {
                                            return 1;
                                        }
                                    } else if (!z3) {
                                        return 1;
                                    }
                                }
                                return -1;
                        }
                    }
                };
        final int i2 = 0;
        this.mBackgroundComparator =
                new Comparator(
                        this) { // from class: com.android.settings.applications.RunningState.1
                    public final /* synthetic */ RunningState this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i2) {
                            case 0:
                                MergedItem mergedItem = (MergedItem) obj;
                                MergedItem mergedItem2 = (MergedItem) obj2;
                                int i22 = mergedItem.mUserId;
                                int i3 = mergedItem2.mUserId;
                                if (i22 == i3) {
                                    ProcessItem processItem = mergedItem.mProcess;
                                    ProcessItem processItem2 = mergedItem2.mProcess;
                                    if (processItem == processItem2) {
                                        String str = mergedItem.mLabel;
                                        String str2 = mergedItem2.mLabel;
                                        if (str != str2) {
                                            if (str != null) {
                                                return str.compareTo(str2);
                                            }
                                            return -1;
                                        }
                                    } else {
                                        if (processItem == null) {
                                            return -1;
                                        }
                                        if (processItem2 != null) {
                                            ActivityManager.RunningAppProcessInfo
                                                    runningAppProcessInfo =
                                                            processItem.mRunningProcessInfo;
                                            ActivityManager.RunningAppProcessInfo
                                                    runningAppProcessInfo2 =
                                                            processItem2.mRunningProcessInfo;
                                            boolean z = runningAppProcessInfo.importance >= 400;
                                            if (z == (runningAppProcessInfo2.importance >= 400)) {
                                                boolean z2 = (runningAppProcessInfo.flags & 4) != 0;
                                                if (z2
                                                        == ((runningAppProcessInfo2.flags & 4)
                                                                != 0)) {
                                                    int i4 = runningAppProcessInfo.lru;
                                                    int i5 = runningAppProcessInfo2.lru;
                                                    if (i4 == i5) {
                                                        String str3 = processItem.mLabel;
                                                        String str4 = processItem2.mLabel;
                                                        if (str3 != str4) {
                                                            if (str3 != null) {
                                                                if (str4 == null) {
                                                                    return -1;
                                                                }
                                                                return str3.compareTo(str4);
                                                            }
                                                        }
                                                    } else if (i4 < i5) {
                                                        return -1;
                                                    }
                                                } else if (z2) {
                                                    return -1;
                                                }
                                            } else if (!z) {
                                                return -1;
                                            }
                                        }
                                    }
                                    return 0;
                                }
                                int i6 = this.this$0.mMyUserId;
                                if (i22 == i6) {
                                    return -1;
                                }
                                if (i3 != i6 && i22 < i3) {
                                    return -1;
                                }
                                return 1;
                            default:
                                ProcessItem processItem3 = (ProcessItem) obj;
                                ProcessItem processItem4 = (ProcessItem) obj2;
                                int i7 = processItem3.mUserId;
                                int i8 = processItem4.mUserId;
                                if (i7 != i8) {
                                    int i9 = this.this$0.mMyUserId;
                                    if (i7 != i9 && (i8 == i9 || i7 >= i8)) {
                                        return 1;
                                    }
                                } else {
                                    boolean z3 = processItem3.mIsStarted;
                                    if (z3 == processItem4.mIsStarted) {
                                        boolean z4 = processItem3.mIsSystem;
                                        if (z4 == processItem4.mIsSystem) {
                                            long j = processItem3.mActiveSince;
                                            long j2 = processItem4.mActiveSince;
                                            if (j == j2) {
                                                return 0;
                                            }
                                            if (j <= j2) {
                                                return 1;
                                            }
                                        } else if (z4) {
                                            return 1;
                                        }
                                    } else if (!z3) {
                                        return 1;
                                    }
                                }
                                return -1;
                        }
                    }
                };
        new ArrayList();
        this.mMergedItems = new ArrayList();
        this.mBackgroundItems = new ArrayList();
        this.mUserBackgroundItems = new ArrayList();
        this.mHandler =
                new Handler() { // from class: com.android.settings.applications.RunningState.2
                    public int mNextUpdate = 0;

                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        int i3 = message.what;
                        if (i3 == 3) {
                            this.mNextUpdate = message.arg1 != 0 ? 2 : 1;
                            return;
                        }
                        if (i3 != 4) {
                            return;
                        }
                        synchronized (RunningState.this.mLock) {
                            try {
                                if (RunningState.this.mResumed) {
                                    removeMessages(4);
                                    sendMessageDelayed(obtainMessage(4), 1000L);
                                    OnRefreshUiListener onRefreshUiListener =
                                            RunningState.this.mRefreshUiListener;
                                    if (onRefreshUiListener != null) {
                                        onRefreshUiListener.onRefreshUi(this.mNextUpdate);
                                        this.mNextUpdate = 0;
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        UserManagerBroadcastReceiver userManagerBroadcastReceiver =
                new UserManagerBroadcastReceiver();
        this.mUmBroadcastReceiver = userManagerBroadcastReceiver;
        Context applicationContext = context.getApplicationContext();
        this.mApplicationContext = applicationContext;
        this.mAm = (ActivityManager) applicationContext.getSystemService(ActivityManager.class);
        this.mPm = applicationContext.getPackageManager();
        UserManager userManager =
                (UserManager) applicationContext.getSystemService(UserManager.class);
        this.mUm = userManager;
        int myUserId = UserHandle.myUserId();
        this.mMyUserId = myUserId;
        UserInfo userInfo = userManager.getUserInfo(myUserId);
        this.mHideManagedProfiles = userInfo == null || !userInfo.canHaveProfile();
        this.mResumed = false;
        HandlerThread handlerThread = new HandlerThread("RunningState:Background");
        handlerThread.start();
        this.mBackgroundHandler = new BackgroundHandler(handlerThread.getLooper());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        intentFilter.addAction("android.intent.action.USER_STARTED");
        intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
        applicationContext.registerReceiverAsUser(
                userManagerBroadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    }

    public static RunningState getInstance(Context context) {
        RunningState runningState;
        synchronized (sGlobalLock) {
            try {
                if (sInstance == null) {
                    sInstance = new RunningState(context);
                }
                runningState = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return runningState;
    }

    public static boolean isInterestingProcess(
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
        int i;
        int i2 = runningAppProcessInfo.flags;
        if ((i2 & 1) != 0) {
            return true;
        }
        return (i2 & 2) == 0
                && (i = runningAppProcessInfo.importance) >= 100
                && i < 350
                && runningAppProcessInfo.importanceReasonCode == 0;
    }

    public static CharSequence makeLabel(
            PackageManager packageManager, String str, PackageItemInfo packageItemInfo) {
        CharSequence loadLabel;
        if (packageItemInfo != null
                && ((packageItemInfo.labelRes != 0 || packageItemInfo.nonLocalizedLabel != null)
                        && (loadLabel = packageItemInfo.loadLabel(packageManager)) != null)) {
            return loadLabel;
        }
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf >= 0 ? str.substring(lastIndexOf + 1, str.length()) : str;
    }

    public final void addOtherUserItem(
            Context context, ArrayList arrayList, SparseArray sparseArray, MergedItem mergedItem) {
        MergedItem mergedItem2 = (MergedItem) sparseArray.get(mergedItem.mUserId);
        if (mergedItem2 == null || mergedItem2.mCurSeq != this.mSequence) {
            UserManager userManager = this.mUm;
            int i = mergedItem.mUserId;
            UserInfo userInfo = userManager.getUserInfo(i);
            if (userInfo == null) {
                return;
            }
            if (this.mHideManagedProfiles && userInfo.isManagedProfile()) {
                return;
            }
            if (mergedItem2 == null) {
                mergedItem2 = new MergedItem(i);
                sparseArray.put(i, mergedItem2);
            } else {
                mergedItem2.mChildren.clear();
            }
            mergedItem2.mCurSeq = this.mSequence;
            UserState userState = new UserState();
            mergedItem2.mUser = userState;
            userState.mIcon = Utils.getUserIcon(context, this.mUm, userInfo);
            mergedItem2.mUser.mLabel = Utils.getUserLabel(context, userInfo);
            arrayList.add(mergedItem2);
        }
        mergedItem2.mChildren.add(mergedItem);
    }

    public final void pause() {
        synchronized (this.mLock) {
            this.mResumed = false;
            this.mRefreshUiListener = null;
            removeMessages(4);
        }
    }

    public final void resume(OnRefreshUiListener onRefreshUiListener) {
        synchronized (this.mLock) {
            try {
                this.mResumed = true;
                this.mRefreshUiListener = onRefreshUiListener;
                UserManagerBroadcastReceiver userManagerBroadcastReceiver =
                        this.mUmBroadcastReceiver;
                boolean z = userManagerBroadcastReceiver.usersChanged;
                userManagerBroadcastReceiver.usersChanged = false;
                boolean applyNewConfig =
                        this.mInterestingConfigChanges.applyNewConfig(
                                this.mApplicationContext.getResources());
                if (z || applyNewConfig) {
                    this.mHaveData = false;
                    this.mBackgroundHandler.removeMessages(1);
                    this.mBackgroundHandler.removeMessages(2);
                    this.mBackgroundHandler.sendEmptyMessage(1);
                }
                if (!this.mBackgroundHandler.hasMessages(2)) {
                    this.mBackgroundHandler.sendEmptyMessage(2);
                }
                sendEmptyMessage(4);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateNow() {
        synchronized (this.mLock) {
            this.mBackgroundHandler.removeMessages(2);
            this.mBackgroundHandler.sendEmptyMessage(2);
        }
    }
}
