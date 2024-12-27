package com.android.settings.fuelgauge.batterytip.tips;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.settings.R;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LowBatteryTip extends BatteryTip {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public boolean mPowerSaveModeOn;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batterytip.tips.LowBatteryTip$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            LowBatteryTip lowBatteryTip = new LowBatteryTip(parcel);
            lowBatteryTip.mPowerSaveModeOn = parcel.readBoolean();
            return lowBatteryTip;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new LowBatteryTip[i];
        }
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final int getIconId() {
        this.mState = R.drawable.ic_battery_saver_accent_24dp;
        return R.drawable.ic_battery_saver_accent_24dp;
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getSummary(Context context) {
        return context.getString(R.string.battery_tip_low_battery_summary);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getTitle(Context context) {
        return context.getString(R.string.battery_tip_low_battery_title);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final void log(Context context, MetricsFeatureProvider metricsFeatureProvider) {
        metricsFeatureProvider.action(context, 1352, this.mState);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mPowerSaveModeOn);
    }
}
