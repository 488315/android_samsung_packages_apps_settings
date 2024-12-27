package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LDAPAccount implements Parcelable {
    public static final Parcelable.Creator<LDAPAccount> CREATOR = new AnonymousClass1();
    public String baseDN;
    public String host;
    public long id;
    public boolean isAnonymous;
    public boolean isSSL;
    public String password;
    public int port;
    public int trustAll;
    public String userName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.accounts.LDAPAccount$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<LDAPAccount> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LDAPAccount createFromParcel(Parcel parcel) {
            return new LDAPAccount(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LDAPAccount[] newArray(int i) {
            return new LDAPAccount[i];
        }
    }

    public LDAPAccount(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.id = parcel.readLong();
        this.userName = parcel.readString();
        this.password = parcel.readString();
        this.port = parcel.readInt();
        this.host = parcel.readString();
        this.isSSL = parcel.readInt() == 0;
        this.isAnonymous = parcel.readInt() == 0;
        this.baseDN = parcel.readString();
        this.trustAll = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.userName);
        parcel.writeString(this.password);
        parcel.writeInt(this.port);
        parcel.writeString(this.host);
        parcel.writeInt(!this.isSSL ? 1 : 0);
        parcel.writeInt(!this.isAnonymous ? 1 : 0);
        parcel.writeString(this.baseDN);
        parcel.writeInt(this.trustAll);
    }

    public LDAPAccount() {}
}
