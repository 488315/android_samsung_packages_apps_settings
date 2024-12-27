package com.samsung.android.settings.wifi.mobileap.datamodels;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.settings.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApDataUsageConfig implements Parcelable {
    public static final Parcelable.Creator<WifiApDataUsageConfig> CREATOR = new AnonymousClass1();
    public long mEndDateAndTime;
    public long mStartDateAndTime;
    public long mUsageValueInBytes;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            WifiApDataUsageConfig wifiApDataUsageConfig = new WifiApDataUsageConfig();
            wifiApDataUsageConfig.mStartDateAndTime = parcel.readLong();
            wifiApDataUsageConfig.mEndDateAndTime = parcel.readLong();
            wifiApDataUsageConfig.mUsageValueInBytes = parcel.readLong();
            return wifiApDataUsageConfig;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new WifiApDataUsageConfig[i];
        }
    }

    public WifiApDataUsageConfig(long j, long j2, long j3) {
        this.mStartDateAndTime = j;
        this.mEndDateAndTime = j2;
        this.mUsageValueInBytes = j3;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String[] getUsageValueInLocale(Context context) {
        double d = this.mUsageValueInBytes;
        String[] strArr = new String[2];
        double d2 = (d / 1000.0d) / 1000.0d;
        double d3 = d2 / 1000.0d;
        if (d < 1.0E9d) {
            strArr[0] = String.format("%.2f", Double.valueOf(d2));
            strArr[1] = context.getString(R.string.wifi_ap_mb);
        } else {
            strArr[0] = String.format("%.2f", Double.valueOf(d3));
            strArr[1] = context.getString(R.string.wifi_ap_gb);
        }
        return strArr;
    }

    public final String getUsageValueInLocaleString(Context context) {
        String[] usageValueInLocale = getUsageValueInLocale(context);
        Log.i(
                "WifiApDataUsageConfig",
                "convertDataSizeToLocaleString() : " + Arrays.toString(usageValueInLocale));
        return context.getString(R.string.wifi_ap_gb).equals(usageValueInLocale[1])
                ? String.format(
                        context.getString(R.string.wifi_ap_value_in_gb), usageValueInLocale[0])
                : String.format(
                        context.getString(R.string.wifi_ap_value_in_mb), usageValueInLocale[0]);
    }

    public final double getUsageValueInMB() {
        return new BigDecimal(this.mUsageValueInBytes / 1000000.0d)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public final String getUsageValueUnit(Context context) {
        return getUsageValueInLocale(context)[1].equals(context.getString(R.string.wifi_ap_mb))
                ? "MB"
                : "GB";
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mStartDateAndTime);
        parcel.writeLong(this.mEndDateAndTime);
        parcel.writeLong(this.mUsageValueInBytes);
    }

    public WifiApDataUsageConfig(long j) {
        this.mStartDateAndTime = System.currentTimeMillis();
        this.mEndDateAndTime = System.currentTimeMillis();
        this.mUsageValueInBytes = j;
    }
}
