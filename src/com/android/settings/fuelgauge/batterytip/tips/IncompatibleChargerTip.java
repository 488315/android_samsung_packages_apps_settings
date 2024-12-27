package com.android.settings.fuelgauge.batterytip.tips;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.widget.TipCardPreference;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class IncompatibleChargerTip extends BatteryTip {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batterytip.tips.IncompatibleChargerTip$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new IncompatibleChargerTip(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new IncompatibleChargerTip[i];
        }
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final int getIconId() {
        return R.drawable.ic_battery_incompatible_charger;
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getSummary(Context context) {
        return context.getString(R.string.battery_tip_incompatible_charging_message);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final CharSequence getTitle(Context context) {
        return context.getString(R.string.battery_tip_incompatible_charging_title);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final void log(Context context, MetricsFeatureProvider metricsFeatureProvider) {
        metricsFeatureProvider.action(context, 1823, this.mState);
    }

    @Override // com.android.settings.fuelgauge.batterytip.tips.BatteryTip
    public final void updatePreference(Preference preference) {
        super.updatePreference(preference);
        final Context context = preference.getContext();
        TipCardPreference tipCardPreference =
                preference instanceof TipCardPreference ? (TipCardPreference) preference : null;
        if (tipCardPreference == null) {
            Log.e("IncompatibleChargerTip", "cast Preference to CardPreference failed");
            return;
        }
        tipCardPreference.setSelectable(false);
        tipCardPreference.onDismiss = null;
        tipCardPreference.iconResId = Integer.valueOf(R.drawable.ic_battery_incompatible_charger);
        String string = context.getString(R.string.learn_more);
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        tipCardPreference.primaryButtonText = string;
        tipCardPreference.primaryButtonAction =
                new Function0() { // from class:
                                  // com.android.settings.fuelgauge.batterytip.tips.IncompatibleChargerTip$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        Context context2 = context;
                        ((Activity) context2)
                                .startActivityForResult(
                                        HelpUtils.getHelpIntent(
                                                context2,
                                                context2.getString(
                                                        R.string.help_url_incompatible_charging),
                                                ApnSettings.MVNO_NONE),
                                        0,
                                        null);
                        return Unit.INSTANCE;
                    }
                };
        tipCardPreference.primaryButtonVisibility = true;
        tipCardPreference.primaryButtonContentDescription =
                context.getString(R.string.battery_tip_incompatible_charging_content_description);
        tipCardPreference.buildContent();
    }
}
