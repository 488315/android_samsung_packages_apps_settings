package com.android.settings.applications;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;

import com.android.internal.app.ProcessMap;
import com.android.internal.app.procstats.DumpUtils;
import com.android.internal.app.procstats.IProcessStats;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.app.procstats.ServiceState;
import com.android.internal.util.MemInfoReader;
import com.android.settings.R;

import com.samsung.android.settings.applications.RamUiUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProcStatsData {
    public static final AnonymousClass1 sEntryCompare = new AnonymousClass1();
    public static ProcessStats sStatsXfer;
    public final Context mContext;
    public long mDuration;
    public MemInfo mMemInfo;
    public final PackageManager mPm;
    public ProcessStats mStats;
    public long memTotalTime;
    public ArrayList pkgEntries;
    public final IProcessStats mProcessStats =
            IProcessStats.Stub.asInterface(ServiceManager.getService("procstats"));
    public final int[] mMemStates = ProcessStats.ALL_MEM_ADJ;
    public final int[] mStates = ProcessStats.BACKGROUND_PROC_STATES;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.ProcStatsData$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            ProcStatsEntry procStatsEntry = (ProcStatsEntry) obj;
            ProcStatsEntry procStatsEntry2 = (ProcStatsEntry) obj2;
            double d = procStatsEntry.mRunWeight;
            double d2 = procStatsEntry2.mRunWeight;
            if (d < d2) {
                return 1;
            }
            if (d <= d2) {
                long j = procStatsEntry.mRunDuration;
                long j2 = procStatsEntry2.mRunDuration;
                if (j < j2) {
                    return 1;
                }
                if (j <= j2) {
                    return 0;
                }
            }
            return -1;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MemInfo {
        public long baseCacheRam;
        public double freeWeight;
        public long hwRam;
        public double[] mMemStateWeights;
        public long memTotalTime;
        public double realFreeRam;
        public long realReservedRam;
        public double realTotalRam;
        public double realUsedRam;
        public double totalScale;
        public double usedWeight;
        public double weightToRam;
    }

    public ProcStatsData(Context context, boolean z) {
        this.mContext = context;
        this.mPm = context.getPackageManager();
        if (z) {
            this.mStats = sStatsXfer;
        }
    }

    public final void refreshStats(boolean z) {
        String str;
        double d;
        double d2;
        ProcessMap processMap;
        LongSparseArray longSparseArray;
        ProcessStats.PackageState packageState;
        String str2;
        if (this.mStats == null || z) {
            try {
                ParcelFileDescriptor statsOverTime =
                        this.mProcessStats.getStatsOverTime(this.mDuration);
                this.mStats = new ProcessStats(false);
                ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream =
                        new ParcelFileDescriptor.AutoCloseInputStream(statsOverTime);
                this.mStats.read(autoCloseInputStream);
                try {
                    autoCloseInputStream.close();
                } catch (IOException unused) {
                }
                if (this.mStats.mReadError != null) {
                    Log.w(
                            "ProcStatsManager",
                            "Failure reading process stats: " + this.mStats.mReadError);
                }
            } catch (RemoteException e) {
                Log.e("ProcStatsManager", "RemoteException:", e);
            } catch (NullPointerException e2) {
                Log.e("ProcStatsManager", "NullPointerException:", e2);
            }
        }
        this.pkgEntries = new ArrayList();
        long uptimeMillis = SystemClock.uptimeMillis();
        ProcessStats processStats = this.mStats;
        this.memTotalTime =
                DumpUtils.dumpSingleTime(
                        (PrintWriter) null,
                        (String) null,
                        processStats.mMemFactorDurations,
                        processStats.mMemFactor,
                        processStats.mStartTime,
                        uptimeMillis);
        int[] iArr = ProcessStats.ALL_SCREEN_ADJ;
        int[] iArr2 = this.mMemStates;
        ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection =
                new ProcessStats.TotalMemoryUseCollection(iArr, iArr2);
        this.mStats.computeTotalMemoryUse(totalMemoryUseCollection, uptimeMillis);
        Context context = this.mContext;
        long j = this.memTotalTime;
        MemInfo memInfo = new MemInfo();
        memInfo.mMemStateWeights = new double[16];
        memInfo.memTotalTime = j;
        new MemInfoReader().readMemInfo();
        memInfo.realTotalRam = r10.getTotalSize();
        memInfo.freeWeight =
                totalMemoryUseCollection.sysMemFreeWeight
                        + totalMemoryUseCollection.sysMemCachedWeight;
        memInfo.usedWeight =
                totalMemoryUseCollection.sysMemKernelWeight
                        + totalMemoryUseCollection.sysMemNativeWeight;
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }
        long j2 = memoryInfo.totalMem / 1048576;
        memInfo.hwRam =
                j2 > 12288
                        ? GoodSettingsContract.PreferenceFlag.FLAG_NEED_PREF_IS_VISIBLE
                        : j2 > 8192
                                ? 12884901888L
                                : j2 > 6144
                                        ? GoodSettingsContract.PreferenceFlag
                                                .FLAG_NEED_PREF_IS_AVAILABLE
                                        : j2 > 4096
                                                ? 6442450944L
                                                : j2 > 3072
                                                        ? GoodSettingsContract.PreferenceFlag
                                                                .FLAG_NEED_TYPE
                                                        : j2 > 2048
                                                                ? 3221225472L
                                                                : j2 > 1536
                                                                        ? 2147483648L
                                                                        : j2 > 1024
                                                                                ? 1610612736L
                                                                                : j2 > 768
                                                                                        ? 1073741824L
                                                                                        : j2 > 512
                                                                                                ? 751619276L
                                                                                                : 536870912L;
        if (!totalMemoryUseCollection.hasSwappedOutPss) {
            memInfo.usedWeight += totalMemoryUseCollection.sysMemZRamWeight;
        }
        for (int i = 0; i < 16; i++) {
            double[] dArr = memInfo.mMemStateWeights;
            if (i == 9) {
                dArr[i] = 0.0d;
            } else {
                double[] dArr2 = totalMemoryUseCollection.processStateWeight;
                dArr[i] = dArr2[i];
                if (i >= 12) {
                    memInfo.freeWeight += dArr2[i];
                } else {
                    memInfo.usedWeight += dArr2[i];
                }
            }
        }
        double d3 = j;
        double d4 = (memInfo.usedWeight * 1024.0d) / d3;
        double d5 = (memInfo.freeWeight * 1024.0d) / d3;
        double d6 = memInfo.realTotalRam;
        double d7 = d6 / (d4 + d5);
        memInfo.totalScale = d7;
        memInfo.weightToRam = (d7 / d3) * 1024.0d;
        double d8 = d7 * d5;
        memInfo.realFreeRam = d8;
        long j3 = (long) (memInfo.hwRam - d6);
        memInfo.realReservedRam = j3;
        ActivityManager.MemoryInfo memoryInfo2 = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo2);
        long j4 = memoryInfo2.hiddenAppThreshold;
        double d9 = j4;
        if (d9 >= d8) {
            str = "ProcStatsManager";
            memInfo.realUsedRam = d5;
            memInfo.realFreeRam = 0.0d;
            memInfo.baseCacheRam = (long) 0.0d;
        } else {
            str = "ProcStatsManager";
            double d10 = d8 - d9;
            memInfo.realFreeRam = d10;
            memInfo.baseCacheRam = j4;
            double decimalValueFormatted =
                    (RamUiUtil.getDecimalValueFormatted(memInfo.hwRam)
                                    - RamUiUtil.getDecimalValueFormatted(j3))
                            - RamUiUtil.getDecimalValueFormatted((long) d10);
            if (decimalValueFormatted < 0.0d) {
                decimalValueFormatted = 0.0d;
            }
            if (decimalValueFormatted >= 1.0E9d) {
                d = decimalValueFormatted / 1.0E9d;
                d2 = 1.073741824E9d;
            } else if (decimalValueFormatted >= 1000000.0d) {
                d = decimalValueFormatted / 1000000.0d;
                d2 = 1048576.0d;
            } else {
                if (decimalValueFormatted >= 1000.0d) {
                    decimalValueFormatted = (decimalValueFormatted / 1000.0d) * 1024.0d;
                }
                memInfo.realUsedRam = (long) decimalValueFormatted;
            }
            decimalValueFormatted = d * d2;
            memInfo.realUsedRam = (long) decimalValueFormatted;
        }
        this.mMemInfo = memInfo;
        int[] iArr3 = ProcessStats.ALL_SCREEN_ADJ;
        ProcessStats.ProcessDataCollection processDataCollection =
                new ProcessStats.ProcessDataCollection(iArr3, iArr2, this.mStates);
        ProcessStats.ProcessDataCollection processDataCollection2 =
                new ProcessStats.ProcessDataCollection(
                        iArr3, iArr2, ProcessStats.NON_CACHED_PROC_STATES);
        ArrayList arrayList = new ArrayList();
        ProcessMap processMap2 = new ProcessMap();
        int size = this.mStats.mPackages.getMap().size();
        for (int i2 = 0; i2 < size; i2++) {
            SparseArray sparseArray = (SparseArray) this.mStats.mPackages.getMap().valueAt(i2);
            for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                LongSparseArray longSparseArray2 = (LongSparseArray) sparseArray.valueAt(i3);
                int i4 = 0;
                while (i4 < longSparseArray2.size()) {
                    ProcessStats.PackageState packageState2 =
                            (ProcessStats.PackageState) longSparseArray2.valueAt(i4);
                    int i5 = size;
                    int i6 = 0;
                    while (i6 < packageState2.mProcesses.size()) {
                        ProcessState processState =
                                (ProcessState) packageState2.mProcesses.valueAt(i6);
                        SparseArray sparseArray2 = sparseArray;
                        LongSparseArray longSparseArray3 = longSparseArray2;
                        ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection2 =
                                totalMemoryUseCollection;
                        ProcessState processState2 =
                                (ProcessState)
                                        this.mStats.mProcesses.get(
                                                processState.getName(), processState.getUid());
                        if (processState2 == null) {
                            Log.w(
                                    str,
                                    "No process found for pkg "
                                            + packageState2.mPackageName
                                            + "/"
                                            + packageState2.mUid
                                            + " proc name "
                                            + processState.getName());
                            str2 = str;
                        } else {
                            ProcStatsEntry procStatsEntry =
                                    (ProcStatsEntry)
                                            processMap2.get(
                                                    processState2.getName(),
                                                    processState2.getUid());
                            if (procStatsEntry == null) {
                                ProcStatsEntry procStatsEntry2 =
                                        new ProcStatsEntry(
                                                processState2,
                                                packageState2.mPackageName,
                                                processDataCollection,
                                                processDataCollection2);
                                str2 = str;
                                if (procStatsEntry2.mRunWeight > 0.0d) {
                                    processMap2.put(
                                            processState2.getName(),
                                            processState2.getUid(),
                                            procStatsEntry2);
                                    arrayList.add(procStatsEntry2);
                                }
                            } else {
                                str2 = str;
                                procStatsEntry.mPackages.add(packageState2.mPackageName);
                            }
                        }
                        i6++;
                        str = str2;
                        sparseArray = sparseArray2;
                        longSparseArray2 = longSparseArray3;
                        totalMemoryUseCollection = totalMemoryUseCollection2;
                    }
                    i4++;
                    size = i5;
                    longSparseArray2 = longSparseArray2;
                }
            }
        }
        ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection3 = totalMemoryUseCollection;
        String str3 = str;
        int size2 = this.mStats.mPackages.getMap().size();
        for (int i7 = 0; i7 < size2; i7++) {
            SparseArray sparseArray3 = (SparseArray) this.mStats.mPackages.getMap().valueAt(i7);
            for (int i8 = 0; i8 < sparseArray3.size(); i8++) {
                LongSparseArray longSparseArray4 = (LongSparseArray) sparseArray3.valueAt(i8);
                for (int i9 = 0; i9 < longSparseArray4.size(); i9++) {
                    ProcessStats.PackageState packageState3 =
                            (ProcessStats.PackageState) longSparseArray4.valueAt(i9);
                    int size3 = packageState3.mServices.size();
                    int i10 = 0;
                    while (i10 < size3) {
                        int i11 = size2;
                        ServiceState serviceState =
                                (ServiceState) packageState3.mServices.valueAt(i10);
                        if (serviceState.getProcessName() != null) {
                            longSparseArray = longSparseArray4;
                            packageState = packageState3;
                            ProcStatsEntry procStatsEntry3 =
                                    (ProcStatsEntry)
                                            processMap2.get(
                                                    serviceState.getProcessName(),
                                                    sparseArray3.keyAt(i8));
                            if (procStatsEntry3 != null) {
                                processMap = processMap2;
                                ArrayList arrayList2 =
                                        (ArrayList)
                                                procStatsEntry3.mServices.get(
                                                        serviceState.getPackage());
                                if (arrayList2 == null) {
                                    arrayList2 = new ArrayList();
                                    procStatsEntry3.mServices.put(
                                            serviceState.getPackage(), arrayList2);
                                }
                                arrayList2.add(new ProcStatsEntry.Service(serviceState));
                            } else {
                                processMap = processMap2;
                                Log.w(
                                        str3,
                                        "No process "
                                                + serviceState.getProcessName()
                                                + "/"
                                                + sparseArray3.keyAt(i8)
                                                + " for service "
                                                + serviceState.getName());
                            }
                        } else {
                            processMap = processMap2;
                            longSparseArray = longSparseArray4;
                            packageState = packageState3;
                        }
                        i10++;
                        size2 = i11;
                        longSparseArray4 = longSparseArray;
                        packageState3 = packageState;
                        processMap2 = processMap;
                    }
                }
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        for (int size4 = arrayList.size() - 1; size4 >= 0; size4--) {
            ProcStatsEntry procStatsEntry4 = (ProcStatsEntry) arrayList.get(size4);
            procStatsEntry4.evaluateTargetPackage(
                    this.mPm, this.mStats, processDataCollection, processDataCollection2);
            ProcStatsPackageEntry procStatsPackageEntry =
                    (ProcStatsPackageEntry) arrayMap.get(procStatsEntry4.mBestTargetPackage);
            if (procStatsPackageEntry == null) {
                procStatsPackageEntry =
                        new ProcStatsPackageEntry(procStatsEntry4.mBestTargetPackage);
                arrayMap.put(procStatsEntry4.mBestTargetPackage, procStatsPackageEntry);
                this.pkgEntries.add(procStatsPackageEntry);
            }
            procStatsPackageEntry.mEntries.add(procStatsEntry4);
        }
        double d11 = totalMemoryUseCollection3.sysMemZRamWeight;
        if (d11 > 0.0d && !totalMemoryUseCollection3.hasSwappedOutPss) {
            long j5 = (long) (d11 / this.memTotalTime);
            long j6 = 0;
            for (int size5 = this.pkgEntries.size() - 1; size5 >= 0; size5--) {
                ProcStatsPackageEntry procStatsPackageEntry2 =
                        (ProcStatsPackageEntry) this.pkgEntries.get(size5);
                for (int size6 = procStatsPackageEntry2.mEntries.size() - 1; size6 >= 0; size6--) {
                    j6 +=
                            ((ProcStatsEntry) procStatsPackageEntry2.mEntries.get(size6))
                                    .mRunDuration;
                }
            }
            int size7 = this.pkgEntries.size() - 1;
            for (long j7 = 0; size7 >= 0 && j6 > j7; j7 = 0) {
                ProcStatsPackageEntry procStatsPackageEntry3 =
                        (ProcStatsPackageEntry) this.pkgEntries.get(size7);
                long j8 = j7;
                long j9 = j8;
                for (int size8 = procStatsPackageEntry3.mEntries.size() - 1; size8 >= 0; size8--) {
                    long j10 =
                            ((ProcStatsEntry) procStatsPackageEntry3.mEntries.get(size8))
                                    .mRunDuration;
                    j8 += j10;
                    if (j10 > j9) {
                        j9 = j10;
                    }
                }
                long j11 = (j5 * j8) / j6;
                if (j11 > 0) {
                    j5 -= j11;
                    j6 -= j8;
                    ProcStatsEntry procStatsEntry5 =
                            new ProcStatsEntry(
                                    procStatsPackageEntry3.mPackage,
                                    this.mContext.getString(R.string.process_stats_os_zram),
                                    j9,
                                    j11,
                                    this.memTotalTime);
                    procStatsEntry5.evaluateTargetPackage(this.mPm, this.mStats, null, null);
                    procStatsPackageEntry3.mEntries.add(procStatsEntry5);
                }
                size7--;
            }
        }
        long j12 = this.mMemInfo.baseCacheRam;
        ProcStatsPackageEntry procStatsPackageEntry4 = new ProcStatsPackageEntry("os");
        if (totalMemoryUseCollection3.sysMemNativeWeight > 0.0d) {
            String string = this.mContext.getString(R.string.process_stats_os_native);
            long j13 = this.memTotalTime;
            ProcStatsEntry procStatsEntry6 =
                    new ProcStatsEntry(
                            "os",
                            string,
                            j13,
                            (long) (totalMemoryUseCollection3.sysMemNativeWeight / j13),
                            j13);
            procStatsEntry6.evaluateTargetPackage(
                    this.mPm, this.mStats, processDataCollection, processDataCollection2);
            procStatsPackageEntry4.mEntries.add(procStatsEntry6);
        }
        if (totalMemoryUseCollection3.sysMemKernelWeight > 0.0d) {
            String string2 = this.mContext.getString(R.string.process_stats_os_kernel);
            long j14 = this.memTotalTime;
            ProcStatsEntry procStatsEntry7 =
                    new ProcStatsEntry(
                            "os",
                            string2,
                            j14,
                            (long) (totalMemoryUseCollection3.sysMemKernelWeight / j14),
                            j14);
            procStatsEntry7.evaluateTargetPackage(
                    this.mPm, this.mStats, processDataCollection, processDataCollection2);
            procStatsPackageEntry4.mEntries.add(procStatsEntry7);
        }
        if (j12 > 0) {
            String string3 = this.mContext.getString(R.string.process_stats_os_cache);
            long j15 = this.memTotalTime;
            ProcStatsEntry procStatsEntry8 =
                    new ProcStatsEntry("os", string3, j15, j12 / 1024, j15);
            procStatsEntry8.evaluateTargetPackage(
                    this.mPm, this.mStats, processDataCollection, processDataCollection2);
            procStatsPackageEntry4.mEntries.add(procStatsEntry8);
        }
        this.pkgEntries.add(procStatsPackageEntry4);
    }
}
