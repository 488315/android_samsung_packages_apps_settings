package com.samsung.android.settings.wifi.mobileap.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

import com.samsung.android.wifi.SemWifiApClientDetails;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApClientConfig extends SemWifiApClientDetails implements Parcelable {
    public static final Parcelable.Creator<WifiApClientConfig> CREATOR = new AnonymousClass1();
    public long mDataLimitForEachDayInBytes;
    public long mDataUsageForTodayInBytes;
    public String mDeviceName;
    public int mDeviceType;
    public String mIp;
    public boolean mIsDataPausedByDataLimitCondition;
    public boolean mIsDataPausedByTimeLimitCondition;
    public boolean mIsDataPausedByUserManually;
    public boolean mIsDeviceCurrentlyConnected;
    public String mMac;
    public int mProgressbarColorId;
    public int mProgressbarOrderFromLeft;
    public SemWifiApClientDetails mSemWifiApClientDetails;
    public long mTimeLimitForEachDayInMillis;
    public long mTimeUsageForTodayInMillis;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.datamodels.WifiApClientConfig$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            WifiApClientConfig wifiApClientConfig =
                    new WifiApClientConfig(
                            parcel.readParcelable(SemWifiApClientDetails.class.getClassLoader()));
            wifiApClientConfig.mDeviceName = parcel.readString();
            wifiApClientConfig.mIp = parcel.readString();
            wifiApClientConfig.mMac = parcel.readString();
            wifiApClientConfig.mDataUsageForTodayInBytes = parcel.readLong();
            wifiApClientConfig.mDataLimitForEachDayInBytes = parcel.readLong();
            wifiApClientConfig.mIsDataPausedByDataLimitCondition = parcel.readBoolean();
            wifiApClientConfig.mTimeUsageForTodayInMillis = parcel.readLong();
            wifiApClientConfig.mTimeLimitForEachDayInMillis = parcel.readLong();
            wifiApClientConfig.mIsDataPausedByTimeLimitCondition = parcel.readBoolean();
            wifiApClientConfig.mIsDataPausedByUserManually = parcel.readBoolean();
            wifiApClientConfig.mIsDeviceCurrentlyConnected = parcel.readBoolean();
            wifiApClientConfig.mDeviceType = parcel.readInt();
            wifiApClientConfig.mProgressbarColorId = parcel.readInt();
            wifiApClientConfig.mProgressbarOrderFromLeft = parcel.readInt();
            return wifiApClientConfig;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new WifiApClientConfig[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || WifiApClientConfig.class != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mMac, ((WifiApClientConfig) obj).mMac);
    }

    public final int getDeviceType() {
        return this.mDeviceType;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("WifiApClientConfig{deviceName=");
        sb.append(this.mDeviceName);
        sb.append(", ip='");
        sb.append(this.mIp);
        sb.append(", mac=");
        sb.append(this.mMac);
        sb.append(", dataUsageForTodayInBytes='");
        sb.append(this.mDataUsageForTodayInBytes);
        sb.append(", dataLimitForEachDayInBytes=");
        sb.append(this.mDataLimitForEachDayInBytes);
        sb.append(", isDataPausedByDataLimitCondition='");
        sb.append(this.mIsDataPausedByDataLimitCondition);
        sb.append(", timeUsageForTodayInMillis='");
        sb.append(this.mTimeUsageForTodayInMillis);
        sb.append(", timeLimitForEachDayInMillis=");
        sb.append(this.mTimeLimitForEachDayInMillis);
        sb.append(", isDataPausedByTimeLimitCondition='");
        sb.append(this.mIsDataPausedByTimeLimitCondition);
        sb.append(", isDataPausedByUserManually='");
        sb.append(this.mIsDataPausedByUserManually);
        sb.append(", isDeviceCurrentlyConnected='");
        sb.append(this.mIsDeviceCurrentlyConnected);
        sb.append(", deviceType='");
        sb.append(this.mDeviceType);
        sb.append(", progressbarColorId='");
        sb.append(this.mProgressbarColorId);
        sb.append(", progressbarOrderFromLeft='");
        return BackEventCompat$$ExternalSyntheticOutline0.m(
                sb, this.mProgressbarOrderFromLeft, '}');
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mSemWifiApClientDetails, 0);
        parcel.writeString(this.mDeviceName);
        parcel.writeString(this.mIp);
        parcel.writeString(this.mMac);
        parcel.writeLong(this.mDataUsageForTodayInBytes);
        parcel.writeLong(this.mDataLimitForEachDayInBytes);
        parcel.writeBoolean(this.mIsDataPausedByDataLimitCondition);
        parcel.writeLong(this.mTimeUsageForTodayInMillis);
        parcel.writeLong(this.mTimeLimitForEachDayInMillis);
        parcel.writeBoolean(this.mIsDataPausedByTimeLimitCondition);
        parcel.writeBoolean(this.mIsDataPausedByUserManually);
        parcel.writeBoolean(this.mIsDeviceCurrentlyConnected);
        parcel.writeInt(this.mDeviceType);
        parcel.writeInt(this.mProgressbarColorId);
        parcel.writeInt(this.mProgressbarOrderFromLeft);
    }
}
