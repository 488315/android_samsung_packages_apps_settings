package com.android.settings.applications;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.applications.RecentAppOpsAccess;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProcStatsPackageEntry implements Parcelable {
    public static final Parcelable.Creator<ProcStatsPackageEntry> CREATOR = new AnonymousClass1();
    public long mAvgBgMem;
    public long mAvgRunMem;
    public long mBgDuration;
    public double mBgWeight;
    public final ArrayList mEntries;
    public long mMaxBgMem;
    public long mMaxRunMem;
    public final String mPackage;
    public long mRunDuration;
    public double mRunWeight;
    public String mUiLabel;
    public ApplicationInfo mUiTargetApp;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.ProcStatsPackageEntry$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new ProcStatsPackageEntry(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ProcStatsPackageEntry[i];
        }
    }

    public ProcStatsPackageEntry(String str) {
        this.mEntries = new ArrayList();
        this.mPackage = str;
    }

    public static CharSequence getFrequency(float f, FragmentActivity fragmentActivity) {
        return f > 0.95f
                ? fragmentActivity.getString(
                        R.string.always_running, Integer.valueOf((int) (f * 100.0f)))
                : f > 0.25f
                        ? fragmentActivity.getString(
                                R.string.sometimes_running, Integer.valueOf((int) (f * 100.0f)))
                        : fragmentActivity.getString(
                                R.string.rarely_running, Integer.valueOf((int) (f * 100.0f)));
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void retrieveUiData(
            FragmentActivity fragmentActivity, PackageManager packageManager) {
        this.mUiTargetApp = null;
        String str = this.mPackage;
        this.mUiLabel = str;
        try {
            if ("os".equals(str)) {
                this.mUiTargetApp =
                        packageManager.getApplicationInfo(
                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME, 4227584);
                this.mUiLabel = fragmentActivity.getString(R.string.process_stats_os_label);
            } else {
                ApplicationInfo applicationInfo =
                        packageManager.getApplicationInfo(this.mPackage, 4227584);
                this.mUiTargetApp = applicationInfo;
                this.mUiLabel = applicationInfo.loadLabel(packageManager).toString();
            }
        } catch (PackageManager.NameNotFoundException unused) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("could not find package: "), this.mPackage, "ProcStatsEntry");
        }
    }

    public final void updateMetrics() {
        this.mMaxBgMem = 0L;
        this.mAvgBgMem = 0L;
        this.mBgDuration = 0L;
        this.mBgWeight = 0.0d;
        this.mMaxRunMem = 0L;
        this.mAvgRunMem = 0L;
        this.mRunDuration = 0L;
        this.mRunWeight = 0.0d;
        int size = this.mEntries.size();
        for (int i = 0; i < size; i++) {
            ProcStatsEntry procStatsEntry = (ProcStatsEntry) this.mEntries.get(i);
            this.mBgDuration = Math.max(procStatsEntry.mBgDuration, this.mBgDuration);
            this.mAvgBgMem += procStatsEntry.mAvgBgMem;
            this.mBgWeight += procStatsEntry.mBgWeight;
            this.mRunDuration = Math.max(procStatsEntry.mRunDuration, this.mRunDuration);
            this.mAvgRunMem += procStatsEntry.mAvgRunMem;
            this.mRunWeight += procStatsEntry.mRunWeight;
            this.mMaxBgMem += procStatsEntry.mMaxBgMem;
            this.mMaxRunMem += procStatsEntry.mMaxRunMem;
        }
        long j = size;
        this.mAvgBgMem /= j;
        this.mAvgRunMem /= j;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackage);
        parcel.writeTypedList(this.mEntries);
        parcel.writeLong(this.mBgDuration);
        parcel.writeLong(this.mAvgBgMem);
        parcel.writeLong(this.mMaxBgMem);
        parcel.writeDouble(this.mBgWeight);
        parcel.writeLong(this.mRunDuration);
        parcel.writeLong(this.mAvgRunMem);
        parcel.writeLong(this.mMaxRunMem);
        parcel.writeDouble(this.mRunWeight);
    }

    public ProcStatsPackageEntry(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mEntries = arrayList;
        this.mPackage = parcel.readString();
        parcel.readTypedList(arrayList, ProcStatsEntry.CREATOR);
        this.mBgDuration = parcel.readLong();
        this.mAvgBgMem = parcel.readLong();
        this.mMaxBgMem = parcel.readLong();
        this.mBgWeight = parcel.readDouble();
        this.mRunDuration = parcel.readLong();
        this.mAvgRunMem = parcel.readLong();
        this.mMaxRunMem = parcel.readLong();
        this.mRunWeight = parcel.readDouble();
    }
}
