package com.sec.ims;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ImsRegistrationError implements Parcelable {
    public static final Parcelable.Creator<ImsRegistrationError> CREATOR =
            new Parcelable.Creator<
                    ImsRegistrationError>() { // from class: com.sec.ims.ImsRegistrationError.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ImsRegistrationError createFromParcel(Parcel parcel) {
                    return new ImsRegistrationError(0, parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ImsRegistrationError[] newArray(int i) {
                    return new ImsRegistrationError[i];
                }
            };
    int mDeregistrationReason;
    int mDetailedDeregiReason;
    int mSipErrorCode;
    String mSipErrorReason;

    public /* synthetic */ ImsRegistrationError(int i, Parcel parcel) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getDeregistrationReason() {
        return this.mDeregistrationReason;
    }

    public int getDetailedDeregiReason() {
        return this.mDetailedDeregiReason;
    }

    public int getSipErrorCode() {
        return this.mSipErrorCode;
    }

    public String getSipErrorReason() {
        return this.mSipErrorReason;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSipErrorCode);
        parcel.writeString(this.mSipErrorReason);
        parcel.writeInt(this.mDetailedDeregiReason);
        parcel.writeInt(this.mDeregistrationReason);
    }

    public ImsRegistrationError() {
        this.mSipErrorCode = 0;
        this.mSipErrorReason = ApnSettings.MVNO_NONE;
        this.mDetailedDeregiReason = 0;
        this.mDeregistrationReason = 0;
    }

    public ImsRegistrationError(int i) {
        this.mSipErrorCode = 0;
    }

    public ImsRegistrationError(int i, String str, int i2, int i3) {
        this.mSipErrorCode = i;
        this.mSipErrorReason = str;
        this.mDetailedDeregiReason = i2;
        this.mDeregistrationReason = i3;
    }

    private ImsRegistrationError(Parcel parcel) {
        this.mSipErrorCode = parcel.readInt();
        this.mSipErrorReason = parcel.readString();
        this.mDetailedDeregiReason = parcel.readInt();
        this.mDeregistrationReason = parcel.readInt();
    }
}
