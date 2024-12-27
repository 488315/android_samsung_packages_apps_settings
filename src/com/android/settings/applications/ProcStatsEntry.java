package com.android.settings.applications;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LongSparseArray;

import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.app.procstats.ServiceState;
import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.RecentAppOpsAccess;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProcStatsEntry implements Parcelable {
    public static final Parcelable.Creator<ProcStatsEntry> CREATOR = new AnonymousClass1(0);
    public final long mAvgBgMem;
    public final long mAvgRunMem;
    public String mBestTargetPackage;
    public final long mBgDuration;
    public final double mBgWeight;
    public CharSequence mLabel;
    public final long mMaxBgMem;
    public final long mMaxRunMem;
    public final String mName;
    public final String mPackage;
    public final ArrayList mPackages;
    public final long mRunDuration;
    public final double mRunWeight;
    public final ArrayMap mServices;
    public final int mUid;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.ProcStatsEntry$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass1(int i) {
            this.$r8$classId = i;
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            switch (this.$r8$classId) {
                case 0:
                    return new ProcStatsEntry(parcel);
                default:
                    return new Service(parcel);
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            switch (this.$r8$classId) {
                case 0:
                    return new ProcStatsEntry[i];
                default:
                    return new Service[i];
            }
        }
    }

    public ProcStatsEntry(
            ProcessState processState,
            String str,
            ProcessStats.ProcessDataCollection processDataCollection,
            ProcessStats.ProcessDataCollection processDataCollection2) {
        ArrayList arrayList = new ArrayList();
        this.mPackages = arrayList;
        this.mServices = new ArrayMap(1);
        processState.computeProcessData(processDataCollection, 0L);
        processState.computeProcessData(processDataCollection2, 0L);
        this.mPackage = processState.getPackage();
        this.mUid = processState.getUid();
        this.mName = processState.getName();
        arrayList.add(str);
        long j = processDataCollection.totalTime;
        this.mBgDuration = j;
        long j2 = processDataCollection.avgPss;
        this.mAvgBgMem = j2;
        this.mMaxBgMem = processDataCollection.maxPss;
        this.mBgWeight = j2 * j;
        long j3 = processDataCollection2.totalTime;
        this.mRunDuration = j3;
        long j4 = processDataCollection2.avgPss;
        this.mAvgRunMem = j4;
        this.mMaxRunMem = processDataCollection2.maxPss;
        this.mRunWeight = j4 * j3;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void evaluateTargetPackage(
            PackageManager packageManager,
            ProcessStats processStats,
            ProcessStats.ProcessDataCollection processDataCollection,
            ProcessStats.ProcessDataCollection processDataCollection2) {
        ArrayList arrayList;
        ProcStatsData.AnonymousClass1 anonymousClass1 = ProcStatsData.sEntryCompare;
        this.mBestTargetPackage = null;
        int i = 0;
        boolean z = true;
        if (this.mPackages.size() == 1) {
            this.mBestTargetPackage = (String) this.mPackages.get(0);
            return;
        }
        for (int i2 = 0; i2 < this.mPackages.size(); i2++) {
            if (RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME.equals(this.mPackages.get(i2))) {
                this.mBestTargetPackage = (String) this.mPackages.get(i2);
                return;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < this.mPackages.size(); i3++) {
            LongSparseArray longSparseArray =
                    (LongSparseArray)
                            processStats.mPackages.get((String) this.mPackages.get(i3), this.mUid);
            for (int i4 = 0; i4 < longSparseArray.size(); i4++) {
                ProcessStats.PackageState packageState =
                        (ProcessStats.PackageState) longSparseArray.valueAt(i4);
                if (packageState == null) {
                    StringBuilder sb = new StringBuilder("No package state found for ");
                    sb.append((String) this.mPackages.get(i3));
                    sb.append("/");
                    sb.append(this.mUid);
                    sb.append(" in process ");
                    MainClear$$ExternalSyntheticOutline0.m$1(sb, this.mName, "ProcStatsEntry");
                } else {
                    ProcessState processState =
                            (ProcessState) packageState.mProcesses.get(this.mName);
                    if (processState == null) {
                        Log.w(
                                "ProcStatsEntry",
                                "No process "
                                        + this.mName
                                        + " found in package state "
                                        + ((String) this.mPackages.get(i3))
                                        + "/"
                                        + this.mUid);
                    } else {
                        arrayList2.add(
                                new ProcStatsEntry(
                                        processState,
                                        packageState.mPackageName,
                                        processDataCollection,
                                        processDataCollection2));
                    }
                }
            }
        }
        if (arrayList2.size() <= 1) {
            if (arrayList2.size() == 1) {
                this.mBestTargetPackage = ((ProcStatsEntry) arrayList2.get(0)).mPackage;
                return;
            }
            return;
        }
        Collections.sort(arrayList2, anonymousClass1);
        if (((ProcStatsEntry) arrayList2.get(0)).mRunWeight
                > ((ProcStatsEntry) arrayList2.get(1)).mRunWeight * 3.0d) {
            this.mBestTargetPackage = ((ProcStatsEntry) arrayList2.get(0)).mPackage;
            return;
        }
        double d = ((ProcStatsEntry) arrayList2.get(0)).mRunWeight;
        long j = -1;
        int i5 = 0;
        boolean z2 = false;
        while (i5 < arrayList2.size()) {
            ProcStatsEntry procStatsEntry = (ProcStatsEntry) arrayList2.get(i5);
            if (procStatsEntry.mRunWeight >= d / 2.0d) {
                try {
                    try {
                        ApplicationInfo applicationInfo =
                                packageManager.getApplicationInfo(procStatsEntry.mPackage, i);
                        if (applicationInfo.icon != 0) {
                            if ((applicationInfo.flags & 8) != 0) {
                                long j2 = procStatsEntry.mRunDuration;
                                if (!z2 || j2 > j) {
                                    z2 = z;
                                    j = j2;
                                }
                            } else if (!z2) {
                                int size = this.mServices.size();
                                int i6 = i;
                                while (true) {
                                    if (i6 >= size) {
                                        arrayList = null;
                                        break;
                                    }
                                    arrayList = (ArrayList) this.mServices.valueAt(i6);
                                    if (((Service) arrayList.get(i))
                                            .mPackage.equals(procStatsEntry.mPackage)) {
                                        break;
                                    } else {
                                        i6++;
                                    }
                                }
                                long j3 = 0;
                                if (arrayList != null) {
                                    int size2 = arrayList.size();
                                    int i7 = i;
                                    while (true) {
                                        if (i7 >= size2) {
                                            break;
                                        }
                                        int i8 = i7;
                                        long j4 = ((Service) arrayList.get(i7)).mDuration;
                                        if (j4 > 0) {
                                            j3 = j4;
                                            break;
                                        }
                                        i7 = i8 + 1;
                                    }
                                }
                                if (j3 > j) {
                                    this.mBestTargetPackage = procStatsEntry.mPackage;
                                    j = j3;
                                }
                            }
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                } catch (PackageManager.NameNotFoundException unused2) {
                }
                i5++;
                i = 0;
                z = true;
            }
            i5++;
            i = 0;
            z = true;
        }
        if (TextUtils.isEmpty(this.mBestTargetPackage)) {
            this.mBestTargetPackage = ((ProcStatsEntry) arrayList2.get(0)).mPackage;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackage);
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mName);
        parcel.writeStringList(this.mPackages);
        parcel.writeLong(this.mBgDuration);
        parcel.writeLong(this.mAvgBgMem);
        parcel.writeLong(this.mMaxBgMem);
        parcel.writeDouble(this.mBgWeight);
        parcel.writeLong(this.mRunDuration);
        parcel.writeLong(this.mAvgRunMem);
        parcel.writeLong(this.mMaxRunMem);
        parcel.writeDouble(this.mRunWeight);
        parcel.writeString(this.mBestTargetPackage);
        int size = this.mServices.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeString((String) this.mServices.keyAt(i2));
            parcel.writeTypedList((List) this.mServices.valueAt(i2));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Service implements Parcelable {
        public static final Parcelable.Creator<Service> CREATOR = new AnonymousClass1(1);
        public final long mDuration;
        public final String mName;
        public final String mPackage;
        public final String mProcess;

        public Service(ServiceState serviceState) {
            this.mPackage = serviceState.getPackage();
            this.mName = serviceState.getName();
            this.mProcess = serviceState.getProcessName();
            this.mDuration =
                    serviceState.dumpTime((PrintWriter) null, (String) null, 0, -1, 0L, 0L);
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mPackage);
            parcel.writeString(this.mName);
            parcel.writeString(this.mProcess);
            parcel.writeLong(this.mDuration);
        }

        public Service(Parcel parcel) {
            this.mPackage = parcel.readString();
            this.mName = parcel.readString();
            this.mProcess = parcel.readString();
            this.mDuration = parcel.readLong();
        }
    }

    public ProcStatsEntry(String str, String str2, long j, long j2, long j3) {
        this.mPackages = new ArrayList();
        this.mServices = new ArrayMap(1);
        this.mPackage = str;
        this.mUid = 0;
        this.mName = str2;
        this.mRunDuration = j;
        this.mBgDuration = j;
        this.mMaxRunMem = j2;
        this.mAvgRunMem = j2;
        this.mMaxBgMem = j2;
        this.mAvgBgMem = j2;
        double d = j3 * j2;
        this.mRunWeight = d;
        this.mBgWeight = d;
    }

    public ProcStatsEntry(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mPackages = arrayList;
        ArrayMap arrayMap = new ArrayMap(1);
        this.mServices = arrayMap;
        this.mPackage = parcel.readString();
        this.mUid = parcel.readInt();
        this.mName = parcel.readString();
        parcel.readStringList(arrayList);
        this.mBgDuration = parcel.readLong();
        this.mAvgBgMem = parcel.readLong();
        this.mMaxBgMem = parcel.readLong();
        this.mBgWeight = parcel.readDouble();
        this.mRunDuration = parcel.readLong();
        this.mAvgRunMem = parcel.readLong();
        this.mMaxRunMem = parcel.readLong();
        this.mRunWeight = parcel.readDouble();
        this.mBestTargetPackage = parcel.readString();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            arrayMap.ensureCapacity(readInt);
            for (int i = 0; i < readInt; i++) {
                String readString = parcel.readString();
                ArrayList arrayList2 = new ArrayList();
                parcel.readTypedList(arrayList2, Service.CREATOR);
                this.mServices.append(readString, arrayList2);
            }
        }
    }
}
