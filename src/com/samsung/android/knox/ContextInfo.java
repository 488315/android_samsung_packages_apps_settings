package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ContextInfo implements Parcelable {
    public static final Parcelable.Creator<ContextInfo> CREATOR = new AnonymousClass1();
    public static final int DEVICE_CONTAINER_ID = 0;
    public static final int NON_DALESS_CALLER = -1;
    public final int mCallerUid;
    public final int mContainerId;
    public final int mDALessCallerUid;
    public final boolean mParent;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.ContextInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<ContextInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextInfo createFromParcel(Parcel parcel) {
            return new ContextInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextInfo[] newArray(int i) {
            return new ContextInfo[i];
        }
    }

    public ContextInfo() {
        int myUid = Process.myUid();
        this.mCallerUid = myUid;
        int userId = UserHandle.getUserId(myUid);
        if (SemPersonaManager.isKnoxId(userId)) {
            this.mContainerId = userId;
        } else {
            this.mContainerId = 0;
        }
        this.mParent = false;
        this.mDALessCallerUid = -1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "Caller uid: "
                + this.mCallerUid
                + " ,Container id: "
                + this.mContainerId
                + " , mParent: "
                + this.mParent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCallerUid);
        parcel.writeInt(this.mContainerId);
        parcel.writeInt(this.mParent ? 1 : 0);
        parcel.writeInt(this.mDALessCallerUid);
    }

    public ContextInfo(int i) {
        this.mCallerUid = i;
        int userId = UserHandle.getUserId(i);
        if (!SemPersonaManager.isKnoxId(userId)) {
            this.mContainerId = 0;
        } else {
            this.mContainerId = userId;
        }
        this.mParent = false;
        this.mDALessCallerUid = -1;
    }

    public ContextInfo(int i, boolean z) {
        this(i, z, -1);
    }

    public ContextInfo(int i, boolean z, int i2) {
        this.mCallerUid = i;
        int userId = UserHandle.getUserId(i);
        if (SemPersonaManager.isKnoxId(userId) && !z) {
            this.mContainerId = userId;
        } else {
            this.mContainerId = 0;
        }
        this.mParent = z;
        this.mDALessCallerUid = i2;
    }

    public ContextInfo(int i, int i2) {
        this.mCallerUid = i;
        this.mContainerId = i2;
        this.mParent = false;
        this.mDALessCallerUid = -1;
    }

    public ContextInfo(int i, int i2, boolean z) {
        this(i, i2, z, -1);
    }

    public ContextInfo(int i, int i2, boolean z, int i3) {
        this.mCallerUid = i;
        this.mContainerId = i2;
        this.mParent = z;
        this.mDALessCallerUid = i3;
    }

    public ContextInfo(Parcel parcel) {
        this.mCallerUid = parcel.readInt();
        this.mContainerId = parcel.readInt();
        this.mParent = parcel.readInt() == 1;
        this.mDALessCallerUid = parcel.readInt();
    }
}
