package com.samsung.android.knox.zt.config;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public enum TrustFactorType implements Parcelable {
    DEVICE_INTEGRITY,
    FACE_DETECTION,
    TOUCH_DETECTION,
    WATCH_ON,
    ON_BODY_DETECTION,
    TRUSTED_DEVICE,
    TRUSTED_SERVICE,
    PASSIVE_AUTH,
    PROCESS_ACTIVITY,
    CRITICAL_EVENT_DETECTION,
    LOCK_DETECTION,
    CERT_PROVISION,
    PHISHING_DETECTION,
    SYSTEM_SOFTWARE,
    FRAMEWORK_MONITORING,
    APP_MONITORING,
    SYSTEM_MONITORING,
    NETWORK_MONITORING,
    USER_MONITORING;

    public static final Parcelable.Creator<TrustFactorType> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.zt.config.TrustFactorType$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<TrustFactorType> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrustFactorType createFromParcel(Parcel parcel) {
            return TrustFactorType.valueOf(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrustFactorType[] newArray(int i) {
            return new TrustFactorType[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
