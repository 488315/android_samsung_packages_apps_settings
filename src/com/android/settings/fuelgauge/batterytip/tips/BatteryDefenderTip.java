package com.android.settings.fuelgauge.batterytip.tips;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.widget.TipCardPreference;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryDefenderTip extends BatteryTip {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public boolean mIsPluggedIn;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batterytip.tips.BatteryDefenderTip$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new BatteryDefenderTip(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new BatteryDefenderTip[i];
        }
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final int getIconId() {
        return R.drawable.ic_battery_defender_tip_shield;
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getSummary(Context context) {
        return context.getString(R.string.battery_tip_limited_temporarily_summary);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getTitle(Context context) {
        return context.getString(R.string.battery_tip_limited_temporarily_title);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final void log(Context context, MetricsFeatureProvider metricsFeatureProvider) {
        metricsFeatureProvider.action(context, 1772, this.mState);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final void updatePreference(Preference preference) {
        super.updatePreference(preference);
        Context context = preference.getContext();
        TipCardPreference tipCardPreference =
                preference instanceof TipCardPreference ? (TipCardPreference) preference : null;
        if (tipCardPreference == null) {
            Log.e("BatteryDefenderTip", "cast Preference to TipCardPreference failed");
            return;
        }
        tipCardPreference.setSelectable(false);
        tipCardPreference.iconResId = Integer.valueOf(R.drawable.ic_battery_defender_tip_shield);
        String string = context.getString(R.string.learn_more);
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        tipCardPreference.primaryButtonText = string;
        tipCardPreference.primaryButtonAction =
                new BatteryDefenderTip$$ExternalSyntheticLambda0(context, preference);
        tipCardPreference.primaryButtonVisibility = true;
        tipCardPreference.primaryButtonContentDescription =
                context.getString(
                        R.string.battery_tip_limited_temporarily_sec_button_content_description);
        String string2 = context.getString(R.string.battery_tip_charge_to_full_button);
        Intrinsics.checkNotNullParameter(string2, "<set-?>");
        tipCardPreference.secondaryButtonText = string2;
        tipCardPreference.secondaryButtonAction =
                new BatteryDefenderTip$$ExternalSyntheticLambda0(this, context, preference);
        tipCardPreference.secondaryButtonVisibility = this.mIsPluggedIn;
        tipCardPreference.buildContent();
    }
}
