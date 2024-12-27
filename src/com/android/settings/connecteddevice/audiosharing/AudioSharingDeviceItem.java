package com.android.settings.connecteddevice.audiosharing;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioSharingDeviceItem implements Parcelable {
    public static final Parcelable.Creator<AudioSharingDeviceItem> CREATOR = new AnonymousClass1();
    public final int mGroupId;
    public final boolean mIsActive;
    public final String mName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.connecteddevice.audiosharing.AudioSharingDeviceItem$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new AudioSharingDeviceItem(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new AudioSharingDeviceItem[i];
        }
    }

    public AudioSharingDeviceItem(String str, int i, boolean z) {
        this.mName = str;
        this.mGroupId = i;
        this.mIsActive = z;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mGroupId);
        parcel.writeBoolean(this.mIsActive);
    }

    public AudioSharingDeviceItem(Parcel parcel) {
        this.mName = parcel.readString();
        this.mGroupId = parcel.readInt();
        this.mIsActive = parcel.readBoolean();
    }
}
