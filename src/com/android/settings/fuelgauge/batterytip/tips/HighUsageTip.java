package com.android.settings.fuelgauge.batterytip.tips;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.settings.R;
import com.android.settings.fuelgauge.batterytip.AppInfo;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HighUsageTip extends BatteryTip {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    final List<AppInfo> mHighUsageAppList;
    public final long mLastFullChargeTimeMs;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batterytip.tips.HighUsageTip$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new HighUsageTip(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new HighUsageTip[i];
        }
    }

    public HighUsageTip(List list, long j) {
        super(2, list.isEmpty() ? 2 : 0, true);
        this.mLastFullChargeTimeMs = j;
        this.mHighUsageAppList = list;
    }

    public final List getHighUsageAppList() {
        return this.mHighUsageAppList;
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final int getIconId() {
        return R.drawable.ic_perm_device_information_theme;
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getSummary(Context context) {
        return context.getString(R.string.battery_tip_high_usage_summary);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getTitle(Context context) {
        return context.getString(R.string.battery_tip_high_usage_title);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final void log(Context context, MetricsFeatureProvider metricsFeatureProvider) {
        metricsFeatureProvider.action(context, 1348, this.mState);
        int size = this.mHighUsageAppList.size();
        for (int i = 0; i < size; i++) {
            metricsFeatureProvider.action(context, 1354, this.mHighUsageAppList.get(i).packageName);
        }
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" {");
        int size = this.mHighUsageAppList.size();
        for (int i = 0; i < size; i++) {
            sb.append(" " + this.mHighUsageAppList.get(i).toString() + " ");
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeLong(this.mLastFullChargeTimeMs);
        parcel.writeTypedList(this.mHighUsageAppList);
    }

    public HighUsageTip(Parcel parcel) {
        super(parcel);
        this.mLastFullChargeTimeMs = parcel.readLong();
        this.mHighUsageAppList = parcel.createTypedArrayList(AppInfo.CREATOR);
    }
}
