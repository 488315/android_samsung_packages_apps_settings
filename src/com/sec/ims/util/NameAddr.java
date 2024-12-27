package com.sec.ims.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class NameAddr implements Parcelable {
    public static final Parcelable.Creator<NameAddr> CREATOR =
            new Parcelable.Creator<NameAddr>() { // from class: com.sec.ims.util.NameAddr.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NameAddr createFromParcel(Parcel parcel) {
                    return new NameAddr(0, parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NameAddr[] newArray(int i) {
                    return new NameAddr[i];
                }
            };
    private String mDisplayName;
    private ImsUri mUri;

    public /* synthetic */ NameAddr(int i, Parcel parcel) {
        this(parcel);
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
        NameAddr nameAddr = (NameAddr) obj;
        String str = this.mDisplayName;
        if (str == null) {
            if (nameAddr.mDisplayName != null) {
                return false;
            }
        } else if (!str.equals(nameAddr.mDisplayName)) {
            return false;
        }
        ImsUri imsUri = this.mUri;
        return imsUri == null ? nameAddr.mUri == null : imsUri.equals(nameAddr.mUri);
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public ImsUri getUri() {
        return this.mUri;
    }

    public int hashCode() {
        String str = this.mDisplayName;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        ImsUri imsUri = this.mUri;
        return hashCode + (imsUri != null ? imsUri.hashCode() : 0);
    }

    public void setDisplayName(String str) {
        this.mDisplayName = str;
    }

    public String toString() {
        if (TextUtils.isEmpty(this.mDisplayName)) {
            ImsUri imsUri = this.mUri;
            return imsUri == null ? ApnSettings.MVNO_NONE : imsUri.toString();
        }
        return this.mDisplayName + "<" + this.mUri + ">";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mDisplayName);
        parcel.writeParcelable(this.mUri, i);
    }

    public NameAddr(String str, ImsUri imsUri) {
        this.mDisplayName = str;
        this.mUri = imsUri;
    }

    public NameAddr(String str, String str2) {
        this(str, ImsUri.parse(str2));
    }

    public NameAddr(ImsUri imsUri) {
        this.mDisplayName = ApnSettings.MVNO_NONE;
        this.mUri = imsUri;
    }

    private NameAddr(Parcel parcel) {
        this.mDisplayName = ApnSettings.MVNO_NONE;
        this.mDisplayName = parcel.readString();
        this.mUri = (ImsUri) parcel.readParcelable(ImsUri.class.getClassLoader());
    }
}
