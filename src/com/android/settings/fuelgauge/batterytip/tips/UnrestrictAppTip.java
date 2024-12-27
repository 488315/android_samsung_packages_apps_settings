package com.android.settings.fuelgauge.batterytip.tips;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.settings.fuelgauge.batterytip.AppInfo;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UnrestrictAppTip extends BatteryTip {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public AppInfo mAppInfo;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batterytip.tips.UnrestrictAppTip$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new UnrestrictAppTip(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new UnrestrictAppTip[i];
        }
    }

    public UnrestrictAppTip(Parcel parcel) {
        super(parcel);
        this.mAppInfo = (AppInfo) parcel.readParcelable(UnrestrictAppTip.class.getClassLoader());
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final int getIconId() {
        return 0;
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getSummary(Context context) {
        return null;
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getTitle(Context context) {
        return null;
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.mAppInfo, i);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final void log(Context context, MetricsFeatureProvider metricsFeatureProvider) {}
}
