package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EnterpriseResponseData<T> implements Parcelable {
    public static final int APINOTSUPPORTED = 1;
    public static final Parcelable.Creator<EnterpriseResponseData> CREATOR = new AnonymousClass1();
    public static final int ERROR = -1;
    public static final int EXCEPTIONFAILURE = 3;
    public static final int FAILURE = 1;
    public static final int INSTALL_FAILURE = 6;
    public static final int INVALID_ADMIN = 8;
    public static final int INVALID_CONTAINER_ID = 11;
    public static final int INVALID_PARAMETER = 9;
    public static final int INVALID_VENDOR = 7;
    public static final int INVALID_VPN_STATE = 12;
    public static final int NOERROR = 0;
    public static final int NULLPACKAGE = 4;
    public static final int NULLPROFILE = 2;
    public static final int SERVICE_NOT_STARTED = 10;
    public static final int SUCCESS = 0;
    public static final int SYSTEM_UID_FAILURE = 5;
    public T mData;
    public int mStatus = 0;
    public int mFailureState = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.container.EnterpriseResponseData$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<EnterpriseResponseData> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseResponseData createFromParcel(Parcel parcel) {
            return new EnterpriseResponseData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseResponseData[] newArray(int i) {
            return new EnterpriseResponseData[i];
        }
    }

    public EnterpriseResponseData() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public T getData() {
        return this.mData;
    }

    public int getFailureState() {
        return this.mFailureState;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void readFromParcel(Parcel parcel) {
        this.mData = (T) parcel.readValue(null);
        this.mStatus = parcel.readInt();
        this.mFailureState = parcel.readInt();
    }

    public void setData(T t) {
        this.mData = t;
    }

    public void setStatus(int i, int i2) {
        this.mStatus = i;
        this.mFailureState = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.mData);
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mFailureState);
    }

    public EnterpriseResponseData(Parcel parcel) {
        readFromParcel(parcel);
    }
}
