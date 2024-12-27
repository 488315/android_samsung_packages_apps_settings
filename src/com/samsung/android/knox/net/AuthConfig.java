package com.samsung.android.knox.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AuthConfig implements Parcelable {
    public static String ANY_HOST = "*";
    public static int ANY_PORT = -1;
    public static final Parcelable.Creator<AuthConfig> CREATOR = new AnonymousClass1();
    public String mHost;
    public String mPassword;
    public int mPort;
    public String mUsername;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.net.AuthConfig$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<AuthConfig> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthConfig createFromParcel(Parcel parcel) {
            return new AuthConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthConfig[] newArray(int i) {
            return new AuthConfig[i];
        }
    }

    public AuthConfig(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }
        this.mUsername = str;
        this.mPassword = str2;
        this.mHost = ANY_HOST;
        this.mPort = ANY_PORT;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AuthConfig authConfig = (AuthConfig) obj;
        String str = this.mHost;
        if (str == null) {
            if (authConfig.mHost != null) {
                return false;
            }
        } else if (!str.equals(authConfig.mHost)) {
            return false;
        }
        String str2 = this.mPassword;
        if (str2 == null) {
            if (authConfig.mPassword != null) {
                return false;
            }
        } else if (!str2.equals(authConfig.mPassword)) {
            return false;
        }
        if (this.mPort != authConfig.mPort) {
            return false;
        }
        String str3 = this.mUsername;
        if (str3 == null) {
            if (authConfig.mUsername != null) {
                return false;
            }
        } else if (!str3.equals(authConfig.mUsername)) {
            return false;
        }
        return true;
    }

    public String getHost() {
        return this.mHost;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public int getPort() {
        return this.mPort;
    }

    public String getUsername() {
        return this.mUsername;
    }

    public int hashCode() {
        String str = this.mHost;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        String str2 = this.mPassword;
        int hashCode2 =
                (((hashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + this.mPort) * 31;
        String str3 = this.mUsername;
        return hashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public boolean isValid() {
        if (TextUtils.isEmpty(this.mHost)
                || TextUtils.isEmpty(this.mUsername)
                || TextUtils.isEmpty(this.mPassword)) {
            return false;
        }
        return (!ANY_HOST.equals(this.mHost) || ANY_PORT == this.mPort)
                && (ANY_HOST.equals(this.mHost) || ANY_PORT != this.mPort);
    }

    public void readFromParcel(Parcel parcel) {
        this.mUsername = parcel.readString();
        this.mPassword = parcel.readString();
        this.mHost = parcel.readString();
        this.mPort = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUsername);
        parcel.writeString(this.mPassword);
        parcel.writeString(this.mHost);
        parcel.writeInt(this.mPort);
    }

    public AuthConfig(String str, int i, String str2, String str3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            this.mUsername = str2;
            this.mPassword = str3;
            this.mHost = str;
            this.mPort = i;
            return;
        }
        throw new IllegalArgumentException("Host, username and password cannot be null");
    }

    public AuthConfig(Parcel parcel) {
        readFromParcel(parcel);
    }
}
