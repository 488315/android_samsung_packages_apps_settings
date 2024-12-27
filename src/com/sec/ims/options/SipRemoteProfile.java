package com.sec.ims.options;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SipRemoteProfile implements Parcelable {
    public static final Parcelable.Creator<SipRemoteProfile> CREATOR =
            new Parcelable.Creator<
                    SipRemoteProfile>() { // from class: com.sec.ims.options.SipRemoteProfile.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SipRemoteProfile createFromParcel(Parcel parcel) {
                    return new SipRemoteProfile(0, parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SipRemoteProfile[] newArray(int i) {
                    return new SipRemoteProfile[i];
                }
            };
    public int mAvailability;
    public transient Bundle mCapabilities;
    private String mProfileName;

    public /* synthetic */ SipRemoteProfile(int i) {
        this();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getAvailability() {
        return this.mAvailability;
    }

    public Bundle getCapabilities() {
        return this.mCapabilities;
    }

    public String getProfileName() {
        return this.mProfileName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mProfileName);
        parcel.writeBundle(this.mCapabilities);
        parcel.writeInt(this.mAvailability);
    }

    public /* synthetic */ SipRemoteProfile(int i, Parcel parcel) {
        this(parcel);
    }

    public SipRemoteProfile(SipRemoteProfile sipRemoteProfile) {
        this.mProfileName = sipRemoteProfile.getProfileName();
        this.mCapabilities = sipRemoteProfile.getCapabilities();
        this.mAvailability = sipRemoteProfile.getAvailability();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Builder {
        private SipRemoteProfile mProfile;

        public Builder(SipRemoteProfile sipRemoteProfile) {
            this.mProfile = new SipRemoteProfile(0);
            sipRemoteProfile.getClass();
            try {
                this.mProfile = (SipRemoteProfile) sipRemoteProfile.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("should not occur", e);
            }
        }

        public SipRemoteProfile build() {
            return this.mProfile;
        }

        public Builder setProfileName(String str) {
            this.mProfile.mProfileName = str;
            return this;
        }

        public Builder(String str) throws ParseException {
            SipRemoteProfile sipRemoteProfile = new SipRemoteProfile(0);
            this.mProfile = sipRemoteProfile;
            if (str != null) {
                sipRemoteProfile.mProfileName = str;
                return;
            }
            throw new NullPointerException("uriString cannot be null");
        }
    }

    private SipRemoteProfile() {}

    private SipRemoteProfile(Parcel parcel) {
        this.mProfileName = parcel.readString();
        this.mCapabilities = parcel.readBundle();
        this.mAvailability = parcel.readInt();
    }
}
