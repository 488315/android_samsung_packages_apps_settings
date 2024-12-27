package com.samsung.android.knox.net.vpn;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KnoxVpnContext implements Parcelable {
    public static final Parcelable.Creator<KnoxVpnContext> CREATOR = new AnonymousClass1();
    public int adminId;
    public int personaId;
    public String vendorName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.net.vpn.KnoxVpnContext$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<KnoxVpnContext> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxVpnContext createFromParcel(Parcel parcel) {
            return new KnoxVpnContext(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxVpnContext[] newArray(int i) {
            return new KnoxVpnContext[i];
        }
    }

    public KnoxVpnContext(int i, int i2, String str) {
        this.adminId = i;
        this.personaId = i2;
        this.vendorName = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getAdminId() {
        return this.adminId;
    }

    public int getPersonaId() {
        return this.personaId;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void readFromParcel(Parcel parcel) {
        this.adminId = parcel.readInt();
        this.personaId = parcel.readInt();
        this.vendorName = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.adminId);
        parcel.writeInt(this.personaId);
        parcel.writeString(this.vendorName);
    }

    public KnoxVpnContext(Parcel parcel) {
        readFromParcel(parcel);
    }
}
