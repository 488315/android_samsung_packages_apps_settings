package com.samsung.android.knox.net.firewall;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FirewallResponse implements Parcelable {
    public static final Parcelable.Creator<FirewallResponse> CREATOR = new AnonymousClass1();
    public ErrorCode mErrorCode;
    public String mMessage;
    public Result mResult;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.net.firewall.FirewallResponse$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<FirewallResponse> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FirewallResponse createFromParcel(Parcel parcel) {
            return new FirewallResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FirewallResponse[] newArray(int i) {
            return new FirewallResponse[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum ErrorCode {
        NO_ERROR,
        DATABASE_ERROR,
        INVALID_PARAMETER_ERROR,
        OPERATION_NOT_PERMITTED_ERROR,
        NOT_AUTHORIZED_ERROR,
        IPV6_NOT_SUPPORTED_ERROR,
        UNEXPECTED_ERROR,
        INPUT_CHAIN_NOT_SUPPORTED_ERROR
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum Result {
        SUCCESS,
        NO_CHANGES,
        FAILED
    }

    public FirewallResponse(Result result, ErrorCode errorCode, String str) {
        this.mResult = result;
        this.mMessage = str;
        this.mErrorCode = errorCode;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ErrorCode getErrorCode() {
        return this.mErrorCode;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public Result getResult() {
        return this.mResult;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.mErrorCode = errorCode;
    }

    public void setMessage(String str) {
        this.mMessage = str;
    }

    public void setResult(Result result) {
        this.mResult = result;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(this.mResult);
        parcel.writeString(this.mMessage);
        parcel.writeSerializable(this.mErrorCode);
    }

    public FirewallResponse(Parcel parcel) {
        this.mResult = (Result) parcel.readSerializable();
        this.mMessage = parcel.readString();
        this.mErrorCode = (ErrorCode) parcel.readSerializable();
    }
}
