package com.samsung.android.knox.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ProxyProperties implements Parcelable {
    public static final Parcelable.Creator<ProxyProperties> CREATOR = new AnonymousClass1();
    public String mHostname;
    public String mPacFileUrl;
    public int mPortNumber = -1;
    public List<String> mExclusionList = new ArrayList();
    public List<AuthConfig> mAuthConfigList = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.net.ProxyProperties$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<ProxyProperties> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProxyProperties createFromParcel(Parcel parcel) {
            return new ProxyProperties(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProxyProperties[] newArray(int i) {
            return new ProxyProperties[i];
        }
    }

    public ProxyProperties() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<AuthConfig> getAuthConfigList() {
        return this.mAuthConfigList;
    }

    public List<String> getExclusionList() {
        return this.mExclusionList;
    }

    public String getHostname() {
        return this.mHostname;
    }

    public String getPacFileUrl() {
        return this.mPacFileUrl;
    }

    public int getPortNumber() {
        return this.mPortNumber;
    }

    public boolean isAuthenticationConfigured() {
        List<AuthConfig> list = this.mAuthConfigList;
        return (list == null || list.isEmpty()) ? false : true;
    }

    public boolean isValid() {
        boolean z = !TextUtils.isEmpty(this.mPacFileUrl);
        boolean z2 = !TextUtils.isEmpty(this.mHostname);
        if (z && z2) {
            return false;
        }
        if (isAuthenticationConfigured()) {
            for (AuthConfig authConfig : this.mAuthConfigList) {
                if (authConfig == null || !authConfig.isValid()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void readFromParcel(Parcel parcel) {
        this.mHostname = parcel.readString();
        this.mPortNumber = parcel.readInt();
        parcel.readStringList(this.mExclusionList);
        this.mPacFileUrl = parcel.readString();
        parcel.readList(this.mAuthConfigList, AuthConfig.class.getClassLoader());
    }

    public void setAuthConfigList(List<AuthConfig> list) {
        this.mAuthConfigList = list;
    }

    public void setExclusionList(List<String> list) {
        this.mExclusionList = list;
    }

    public void setHostname(String str) {
        this.mHostname = str;
    }

    public void setPacFileUrl(String str) {
        this.mPacFileUrl = str;
    }

    public void setPortNumber(int i) {
        this.mPortNumber = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mHostname);
        parcel.writeInt(this.mPortNumber);
        parcel.writeStringList(this.mExclusionList);
        parcel.writeString(this.mPacFileUrl);
        parcel.writeList(this.mAuthConfigList);
    }

    public ProxyProperties(Parcel parcel) {
        readFromParcel(parcel);
    }
}
