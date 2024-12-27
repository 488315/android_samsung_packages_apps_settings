package com.android.settings.applications.appops;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import com.android.settings.R;
import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppOpsState {
    public static final AnonymousClass2 LABEL_COMPARATOR;
    public static final OpsTemplate RUN_IN_BACKGROUND_TEMPLATE;
    public final AppOpsManager mAppOps;
    public final Context mContext;
    public final PackageManager mPm;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppEntry {
        public final File mApkFile;
        public Drawable mIcon;
        public final ApplicationInfo mInfo;
        public String mLabel;
        public boolean mMounted;
        public final AppOpsState mState;
        public final SparseArray mOps = new SparseArray();
        public final SparseArray mOpSwitches = new SparseArray();

        public AppEntry(AppOpsState appOpsState, ApplicationInfo applicationInfo) {
            this.mState = appOpsState;
            this.mInfo = applicationInfo;
            this.mApkFile = new File(applicationInfo.sourceDir);
        }

        public final String toString() {
            return this.mLabel;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppOpEntry {
        public final AppEntry mApp;
        public final ArrayList mOps;
        public int mOverriddenPrimaryMode;
        public final ArrayList mSwitchOps;
        public final int mSwitchOrder;

        public AppOpEntry(AppOpsManager.OpEntry opEntry, AppEntry appEntry, int i) {
            ArrayList arrayList = new ArrayList();
            this.mOps = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.mSwitchOps = arrayList2;
            this.mOverriddenPrimaryMode = -1;
            this.mApp = appEntry;
            appEntry.mOps.put(opEntry.getOp(), opEntry);
            appEntry.mOpSwitches.put(AppOpsManager.opToSwitch(opEntry.getOp()), this);
            arrayList.add(opEntry);
            arrayList2.add(opEntry);
        }

        public static void addOp(ArrayList arrayList, AppOpsManager.OpEntry opEntry) {
            for (int i = 0; i < arrayList.size(); i++) {
                AppOpsManager.OpEntry opEntry2 = (AppOpsManager.OpEntry) arrayList.get(i);
                if (opEntry2.isRunning() != opEntry.isRunning()) {
                    if (opEntry.isRunning()) {
                        arrayList.add(i, opEntry);
                        return;
                    }
                } else if (opEntry2.getTime() < opEntry.getTime()) {
                    arrayList.add(i, opEntry);
                    return;
                }
            }
            arrayList.add(opEntry);
        }

        public final long getTime() {
            return ((AppOpsManager.OpEntry) this.mOps.get(0)).getTime();
        }

        public final String toString() {
            return this.mApp.mLabel;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.applications.appops.AppOpsState$2] */
    static {
        OpsTemplate opsTemplate = new OpsTemplate(new int[]{0, 1, 2, 10, 12, 41, 42}, new boolean[]{true, true, false, false, false, false, false});
        OpsTemplate opsTemplate2 = new OpsTemplate(new int[]{4, 5, 6, 7, 8, 9, 29, 30}, new boolean[]{true, true, true, true, true, true, false, false});
        OpsTemplate opsTemplate3 = new OpsTemplate(new int[]{14, 16, 17, 18, 19, 15, 20, 21, 22}, new boolean[]{true, true, true, true, true, true, true, true, true});
        OpsTemplate opsTemplate4 = new OpsTemplate(new int[]{3, 26, 27, 28, 31, 32, 33, 34, 35, 36, 37, 38, 39, 64, 44}, new boolean[]{false, true, true, false, false, false, false, false, false, false, false, false, false, false});
        OpsTemplate opsTemplate5 = new OpsTemplate(new int[]{11, 25, 13, 23, 24, 40, 46, 47, 49, 50}, new boolean[]{false, true, true, true, true, true, false, false, false, false});
        OpsTemplate opsTemplate6 = new OpsTemplate(new int[]{63}, new boolean[]{false});
        RUN_IN_BACKGROUND_TEMPLATE = opsTemplate6;
        new OpsTemplate[]{opsTemplate, opsTemplate2, opsTemplate3, opsTemplate4, opsTemplate5, opsTemplate6};
        Collator.getInstance();
        LABEL_COMPARATOR = new Comparator() { // from class: com.android.settings.applications.appops.AppOpsState.2
            public final Collator sCollator = Collator.getInstance();

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.sCollator.compare(((AppOpEntry) obj).mApp.mLabel, ((AppOpEntry) obj2).mApp.mLabel);
            }
        };
    }

    public AppOpsState(FragmentActivity fragmentActivity) {
        this.mContext = fragmentActivity;
        this.mAppOps = (AppOpsManager) fragmentActivity.getSystemService("appops");
        this.mPm = fragmentActivity.getPackageManager();
        fragmentActivity.getResources().getTextArray(R.array.app_ops_summaries);
        fragmentActivity.getResources().getTextArray(R.array.app_ops_labels);
    }

    public static void addOp(List list, AppEntry appEntry, AppOpsManager.OpEntry opEntry, boolean z, int i) {
        if (z) {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() > 0) {
                AppOpEntry appOpEntry = (AppOpEntry) AlertController$$ExternalSyntheticOutline0.m(1, arrayList);
                if (appOpEntry.mApp == appEntry) {
                    if ((appOpEntry.getTime() != 0) == (opEntry.getTime() != 0)) {
                        AppEntry appEntry2 = appOpEntry.mApp;
                        appEntry2.mOps.put(opEntry.getOp(), opEntry);
                        appEntry2.mOpSwitches.put(AppOpsManager.opToSwitch(opEntry.getOp()), appOpEntry);
                        AppOpEntry.addOp(appOpEntry.mOps, opEntry);
                        if (((AppOpEntry) appEntry2.mOpSwitches.get(AppOpsManager.opToSwitch(AppOpsManager.opToSwitch(opEntry.getOp())))) == null) {
                            AppOpEntry.addOp(appOpEntry.mSwitchOps, opEntry);
                            return;
                        }
                        return;
                    }
                }
            }
        }
        AppOpEntry appOpEntry2 = (AppOpEntry) appEntry.mOpSwitches.get(AppOpsManager.opToSwitch(opEntry.getOp()));
        if (appOpEntry2 == null) {
            ((ArrayList) list).add(new AppOpEntry(opEntry, appEntry, i));
            return;
        }
        AppEntry appEntry3 = appOpEntry2.mApp;
        appEntry3.mOps.put(opEntry.getOp(), opEntry);
        appEntry3.mOpSwitches.put(AppOpsManager.opToSwitch(opEntry.getOp()), appOpEntry2);
        AppOpEntry.addOp(appOpEntry2.mOps, opEntry);
        if (((AppOpEntry) appEntry3.mOpSwitches.get(AppOpsManager.opToSwitch(AppOpsManager.opToSwitch(opEntry.getOp())))) == null) {
            AppOpEntry.addOp(appOpEntry2.mSwitchOps, opEntry);
        }
    }

    public final AppEntry getAppEntry(FragmentActivity fragmentActivity, HashMap hashMap, String str, ApplicationInfo applicationInfo) {
        AppEntry appEntry = (AppEntry) hashMap.get(str);
        if (appEntry == null) {
            if (applicationInfo == null) {
                try {
                    applicationInfo = this.mPm.getApplicationInfo(str, 4194816);
                } catch (PackageManager.NameNotFoundException unused) {
                    MotionLayout$$ExternalSyntheticOutline0.m("Unable to find info for package ", str, "AppOpsState");
                    return null;
                }
            }
            appEntry = new AppEntry(this, applicationInfo);
            if (appEntry.mLabel == null || !appEntry.mMounted) {
                if (appEntry.mApkFile.exists()) {
                    appEntry.mMounted = true;
                    CharSequence loadLabel = appEntry.mInfo.loadLabel(fragmentActivity.getPackageManager());
                    appEntry.mLabel = loadLabel != null ? loadLabel.toString() : appEntry.mInfo.packageName;
                } else {
                    appEntry.mMounted = false;
                    appEntry.mLabel = appEntry.mInfo.packageName;
                }
            }
            hashMap.put(str, appEntry);
        }
        return appEntry;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OpsTemplate implements Parcelable {
        public static final Parcelable.Creator<OpsTemplate> CREATOR = new AnonymousClass1();
        public final int[] ops;
        public final boolean[] showPerms;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.applications.appops.AppOpsState$OpsTemplate$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new OpsTemplate(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new OpsTemplate[i];
            }
        }

        public OpsTemplate(int[] iArr, boolean[] zArr) {
            this.ops = iArr;
            this.showPerms = zArr;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeIntArray(this.ops);
            parcel.writeBooleanArray(this.showPerms);
        }

        public OpsTemplate(Parcel parcel) {
            this.ops = parcel.createIntArray();
            this.showPerms = parcel.createBooleanArray();
        }
    }
}
