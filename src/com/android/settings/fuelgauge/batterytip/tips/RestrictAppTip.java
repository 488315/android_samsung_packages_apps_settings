package com.android.settings.fuelgauge.batterytip.tips;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.fuelgauge.batterytip.AppInfo;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RestrictAppTip extends BatteryTip {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public List mRestrictAppList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batterytip.tips.RestrictAppTip$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new RestrictAppTip(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new RestrictAppTip[i];
        }
    }

    public RestrictAppTip(Parcel parcel) {
        super(parcel);
        this.mRestrictAppList = parcel.createTypedArrayList(AppInfo.CREATOR);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final int getIconId() {
        return this.mState == 1
                ? R.drawable.ic_perm_device_information_theme
                : R.drawable.ic_battery_alert_theme;
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getSummary(Context context) {
        int size = this.mRestrictAppList.size();
        CharSequence applicationLabel =
                size > 0
                        ? Utils.getApplicationLabel(
                                context, ((AppInfo) this.mRestrictAppList.get(0)).packageName)
                        : ApnSettings.MVNO_NONE;
        int i =
                this.mState == 1
                        ? R.string.battery_tip_restrict_handled_summary
                        : R.string.battery_tip_restrict_summary;
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("count", Integer.valueOf(size));
        arrayMap.put("label", applicationLabel);
        return StringUtil.getIcuPluralsString(context, arrayMap, i);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getTitle(Context context) {
        int size = this.mRestrictAppList.size();
        CharSequence applicationLabel =
                size > 0
                        ? Utils.getApplicationLabel(
                                context, ((AppInfo) this.mRestrictAppList.get(0)).packageName)
                        : ApnSettings.MVNO_NONE;
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("count", Integer.valueOf(size));
        arrayMap.put("label", applicationLabel);
        return this.mState == 1
                ? StringUtil.getIcuPluralsString(
                        context, arrayMap, R.string.battery_tip_restrict_handled_title)
                : StringUtil.getIcuPluralsString(
                        context, arrayMap, R.string.battery_tip_restrict_title);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final void log(Context context, MetricsFeatureProvider metricsFeatureProvider) {
        metricsFeatureProvider.action(context, 1347, this.mState);
        if (this.mState == 0) {
            int size = this.mRestrictAppList.size();
            for (int i = 0; i < size; i++) {
                AppInfo appInfo = (AppInfo) this.mRestrictAppList.get(i);
                Iterator it = appInfo.anomalyTypes.iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    metricsFeatureProvider.action(0, 1353, 0, num.intValue(), appInfo.packageName);
                }
            }
        }
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" {");
        int size = this.mRestrictAppList.size();
        for (int i = 0; i < size; i++) {
            sb.append(" " + ((AppInfo) this.mRestrictAppList.get(i)).toString() + " ");
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final void validateCheck(Context context) {
        List list = this.mRestrictAppList;
        if (AppLabelPredicate.sInstance == null) {
            Context applicationContext = context.getApplicationContext();
            AppLabelPredicate appLabelPredicate = new AppLabelPredicate();
            appLabelPredicate.mContext = applicationContext;
            AppLabelPredicate.sInstance = appLabelPredicate;
        }
        list.removeIf(AppLabelPredicate.sInstance);
        if (this.mRestrictAppList.isEmpty()) {
            this.mState = 2;
        }
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mRestrictAppList);
    }
}
