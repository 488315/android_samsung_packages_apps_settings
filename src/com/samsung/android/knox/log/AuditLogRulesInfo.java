package com.samsung.android.knox.log;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AuditLogRulesInfo implements Parcelable {
    public static final int AUDIT_LOG_OUTCOME_ALL = 2;
    public static final int AUDIT_LOG_OUTCOME_FAILURE = 0;
    public static final int AUDIT_LOG_OUTCOME_SUCCESS = 1;
    public static final Parcelable.Creator<AuditLogRulesInfo> CREATOR = new AnonymousClass1();
    public List<Integer> mGroupsRule;
    public int mOutcomeRule;
    public int mSeverityRule;
    public List<Integer> mUsersRule;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.log.AuditLogRulesInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<AuditLogRulesInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuditLogRulesInfo createFromParcel(Parcel parcel) {
            return new AuditLogRulesInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuditLogRulesInfo[] newArray(int i) {
            return new AuditLogRulesInfo[i];
        }
    }

    public AuditLogRulesInfo() {
        this.mSeverityRule = 5;
        this.mOutcomeRule = 2;
        this.mGroupsRule = null;
        this.mUsersRule = null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<Integer> getGroupsRule() {
        return this.mGroupsRule;
    }

    public int getOutcomeRule() {
        return this.mOutcomeRule;
    }

    public int getSeverityRule() {
        return this.mSeverityRule;
    }

    public List<Integer> getUsersRule() {
        return this.mUsersRule;
    }

    public boolean isKernelLogsEnabled() {
        return false;
    }

    public final void readFromParcel(Parcel parcel) {
        this.mSeverityRule = parcel.readInt();
        this.mOutcomeRule = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        this.mGroupsRule = arrayList;
        parcel.readList(arrayList, Integer.class.getClassLoader());
        ArrayList arrayList2 = new ArrayList();
        this.mUsersRule = arrayList2;
        parcel.readList(arrayList2, Integer.class.getClassLoader());
    }

    public void setGroupsRule(List<Integer> list) {
        this.mGroupsRule = list;
    }

    public void setOutcomeRule(int i) {
        this.mOutcomeRule = i;
    }

    public void setSeverityRule(int i) {
        this.mSeverityRule = i;
    }

    public void setUsersRule(List<Integer> list) {
        this.mUsersRule = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSeverityRule);
        parcel.writeInt(this.mOutcomeRule);
        parcel.writeList(this.mGroupsRule);
        parcel.writeList(this.mUsersRule);
    }

    public AuditLogRulesInfo(int i, int i2, List<Integer> list, boolean z, List<Integer> list2) {
        this.mSeverityRule = i;
        this.mOutcomeRule = i2;
        this.mGroupsRule = list;
        this.mUsersRule = list2;
    }

    public AuditLogRulesInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void setKernelLogsEnabled(boolean z) {}
}
