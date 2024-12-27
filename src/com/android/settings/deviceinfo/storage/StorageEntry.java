package com.android.settings.deviceinfo.storage;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import android.text.TextUtils;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StorageEntry implements Comparable, Parcelable {
    public static final Parcelable.Creator<StorageEntry> CREATOR = new AnonymousClass1();
    public final VolumeRecord mMissingVolumeRecord;
    public final DiskInfo mUnsupportedDiskInfo;
    public final VolumeInfo mVolumeInfo;
    public final String mVolumeInfoDescription;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.deviceinfo.storage.StorageEntry$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new StorageEntry(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new StorageEntry[i];
        }
    }

    public StorageEntry(Context context, VolumeInfo volumeInfo) {
        this.mVolumeInfo = volumeInfo;
        this.mUnsupportedDiskInfo = null;
        this.mMissingVolumeRecord = null;
        if (isDefaultInternalStorage()) {
            this.mVolumeInfoDescription =
                    context.getResources().getString(R.string.storage_default_internal_storage);
        } else {
            this.mVolumeInfoDescription =
                    ((StorageManager) context.getSystemService(StorageManager.class))
                            .getBestVolumeDescription(volumeInfo);
        }
    }

    public static StorageEntry getDefaultInternalStorageEntry(Context context) {
        return new StorageEntry(
                context,
                ((StorageManager) context.getSystemService(StorageManager.class))
                        .findVolumeById("private"));
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        StorageEntry storageEntry = (StorageEntry) obj;
        if (isDefaultInternalStorage() && !storageEntry.isDefaultInternalStorage()) {
            return -1;
        }
        if (isDefaultInternalStorage() || !storageEntry.isDefaultInternalStorage()) {
            if (isVolumeInfo() && !storageEntry.isVolumeInfo()) {
                return -1;
            }
            if (isVolumeInfo() || !storageEntry.isVolumeInfo()) {
                if (isPrivate() && !storageEntry.isPrivate()) {
                    return -1;
                }
                if (isPrivate() || !storageEntry.isPrivate()) {
                    if (isMounted() && !storageEntry.isMounted()) {
                        return -1;
                    }
                    if (isMounted() || !storageEntry.isMounted()) {
                        if (!isVolumeRecordMissed() && storageEntry.isVolumeRecordMissed()) {
                            return -1;
                        }
                        if ((!isVolumeRecordMissed() || storageEntry.isVolumeRecordMissed())
                                && getDescription() != null) {
                            if (storageEntry.getDescription() == null) {
                                return -1;
                            }
                            return getDescription().compareTo(storageEntry.getDescription());
                        }
                    }
                }
            }
        }
        return 1;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StorageEntry)) {
            return false;
        }
        StorageEntry storageEntry = (StorageEntry) obj;
        return isVolumeInfo()
                ? this.mVolumeInfo.equals(storageEntry.mVolumeInfo)
                : isDiskInfoUnsupported()
                        ? this.mUnsupportedDiskInfo.equals(storageEntry.mUnsupportedDiskInfo)
                        : this.mMissingVolumeRecord.equals(storageEntry.mMissingVolumeRecord);
    }

    public final String getDescription() {
        return isVolumeInfo()
                ? this.mVolumeInfoDescription
                : isDiskInfoUnsupported()
                        ? this.mUnsupportedDiskInfo.getDescription()
                        : this.mMissingVolumeRecord.getNickname();
    }

    public final String getDiskId() {
        if (isVolumeInfo()) {
            return this.mVolumeInfo.getDiskId();
        }
        if (isDiskInfoUnsupported()) {
            return this.mUnsupportedDiskInfo.getId();
        }
        return null;
    }

    public final String getFsUuid() {
        if (isVolumeInfo()) {
            return this.mVolumeInfo.getFsUuid();
        }
        if (isDiskInfoUnsupported()) {
            return null;
        }
        return this.mMissingVolumeRecord.getFsUuid();
    }

    public final String getId() {
        return isVolumeInfo()
                ? this.mVolumeInfo.getId()
                : isDiskInfoUnsupported()
                        ? this.mUnsupportedDiskInfo.getId()
                        : this.mMissingVolumeRecord.getFsUuid();
    }

    public final int hashCode() {
        return isVolumeInfo()
                ? this.mVolumeInfo.hashCode()
                : isDiskInfoUnsupported()
                        ? this.mUnsupportedDiskInfo.hashCode()
                        : this.mMissingVolumeRecord.hashCode();
    }

    public final boolean isDefaultInternalStorage() {
        return isVolumeInfo()
                && this.mVolumeInfo.getType() == 1
                && TextUtils.equals(this.mVolumeInfo.getId(), "private");
    }

    public final boolean isDiskInfoUnsupported() {
        return this.mUnsupportedDiskInfo != null;
    }

    public final boolean isMounted() {
        VolumeInfo volumeInfo = this.mVolumeInfo;
        if (volumeInfo == null) {
            return false;
        }
        return volumeInfo.getState() == 2 || this.mVolumeInfo.getState() == 3;
    }

    public final boolean isPrivate() {
        VolumeInfo volumeInfo = this.mVolumeInfo;
        return volumeInfo != null && volumeInfo.getType() == 1;
    }

    public final boolean isPublic() {
        VolumeInfo volumeInfo = this.mVolumeInfo;
        return volumeInfo != null && volumeInfo.getType() == 0;
    }

    public final boolean isVolumeInfo() {
        return this.mVolumeInfo != null;
    }

    public final boolean isVolumeRecordMissed() {
        return this.mMissingVolumeRecord != null;
    }

    public final String toString() {
        return isVolumeInfo()
                ? this.mVolumeInfo.toString()
                : isDiskInfoUnsupported()
                        ? this.mUnsupportedDiskInfo.toString()
                        : this.mMissingVolumeRecord.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mVolumeInfo, 0);
        parcel.writeParcelable(this.mUnsupportedDiskInfo, 0);
        parcel.writeParcelable(this.mMissingVolumeRecord, 0);
        parcel.writeString(this.mVolumeInfoDescription);
    }

    public StorageEntry(DiskInfo diskInfo) {
        this.mVolumeInfo = null;
        this.mUnsupportedDiskInfo = diskInfo;
        this.mMissingVolumeRecord = null;
        this.mVolumeInfoDescription = null;
    }

    public StorageEntry(VolumeRecord volumeRecord) {
        this.mVolumeInfo = null;
        this.mUnsupportedDiskInfo = null;
        this.mMissingVolumeRecord = volumeRecord;
        this.mVolumeInfoDescription = null;
    }

    public StorageEntry(Parcel parcel) {
        this.mVolumeInfo = parcel.readParcelable(VolumeInfo.class.getClassLoader());
        this.mUnsupportedDiskInfo = parcel.readParcelable(DiskInfo.class.getClassLoader());
        this.mMissingVolumeRecord = parcel.readParcelable(VolumeRecord.class.getClassLoader());
        this.mVolumeInfoDescription = parcel.readString();
    }
}
