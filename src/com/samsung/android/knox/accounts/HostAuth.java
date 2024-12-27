package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HostAuth implements Parcelable {
    public static final String ACCOUNT_KEY = "accountKey";
    public static final String ADDRESS = "address";
    public static final Parcelable.Creator<HostAuth> CREATOR = new AnonymousClass1();
    public static final String DOMAIN = "domain";
    public static final String FLAGS = "flags";
    public static final int FLAGS_ACCEPT_ALL_CERT = 8;
    public static final int FLAGS_USE_SSL = 1;
    public static final int FLAGS_USE_TLS = 2;
    public static final String ID = "_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String PORT = "port";
    public static final String PROTOCOL = "protocol";
    public long accountKey;
    public String address;
    public String domain;
    public int flags;
    public int id;
    public String login;
    public String password;
    public int port;
    public String protocol;
    public boolean useSSL = false;
    public boolean useTLS = false;
    public boolean acceptAllCertificates = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.accounts.HostAuth$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<HostAuth> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HostAuth createFromParcel(Parcel parcel) {
            return new HostAuth(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HostAuth[] newArray(int i) {
            return new HostAuth[i];
        }
    }

    public HostAuth() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.id = parcel.readInt();
        this.protocol = parcel.readString();
        this.address = parcel.readString();
        this.port = parcel.readInt();
        this.flags = parcel.readInt();
        this.useSSL = parcel.readInt() != 0;
        this.useTLS = parcel.readInt() != 0;
        this.acceptAllCertificates = parcel.readInt() != 0;
        this.login = parcel.readString();
        this.password = parcel.readString();
        this.domain = parcel.readString();
        this.accountKey = parcel.readLong();
    }

    public String toString() {
        return "_id="
                + this.id
                + " protocol="
                + this.protocol
                + " address="
                + this.address
                + " port="
                + this.port
                + " usessl = "
                + this.useSSL
                + " usetls = "
                + this.useTLS
                + " acceptallcertificate = "
                + this.acceptAllCertificates
                + " login="
                + this.login
                + " password= **** domain="
                + this.domain
                + " accountKey="
                + this.accountKey;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.protocol);
        parcel.writeString(this.address);
        parcel.writeInt(this.port);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.useSSL ? 1 : 0);
        parcel.writeInt(this.useTLS ? 1 : 0);
        parcel.writeInt(this.acceptAllCertificates ? 1 : 0);
        parcel.writeString(this.login);
        parcel.writeString(this.password);
        parcel.writeString(this.domain);
        parcel.writeLong(this.accountKey);
    }

    public HostAuth(Parcel parcel) {
        readFromParcel(parcel);
    }
}
