package com.samsung.android.knox.net.billing;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EnterpriseApn implements Parcelable {
    public static Parcelable.Creator<EnterpriseApn> CREATOR = new AnonymousClass1();
    public final String apn;
    public final String mcc;
    public final String mnc;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.net.billing.EnterpriseApn$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<EnterpriseApn> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseApn createFromParcel(Parcel parcel) {
            return new EnterpriseApn(parcel.readString(), parcel.readString(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseApn[] newArray(int i) {
            return new EnterpriseApn[i];
        }
    }

    public EnterpriseApn(String str, String str2, String str3) {
        if (str == null
                || str.length() == 0
                || str2 == null
                || str2.length() == 0
                || str3 == null
                || str3.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.apn = str;
        this.mcc = str2;
        this.mnc = str3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            return ((EnterpriseApn) obj).toString().equalsIgnoreCase(toString());
        }
        return false;
    }

    public int hashCode() {
        String str = this.apn;
        if (str == null || this.mcc == null || this.mnc == null) {
            return 0;
        }
        int i = 0;
        for (char c : str.toCharArray()) {
            i += c;
        }
        for (char c2 : this.mcc.toCharArray()) {
            i += c2;
        }
        for (char c3 : this.mnc.toCharArray()) {
            i += c3;
        }
        return i;
    }

    public String toString() {
        return this.apn + ":" + this.mcc + ":" + this.mnc;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.apn);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
    }
}
