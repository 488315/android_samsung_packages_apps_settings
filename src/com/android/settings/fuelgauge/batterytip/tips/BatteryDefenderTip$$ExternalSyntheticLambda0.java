package com.android.settings.fuelgauge.batterytip.tips;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.HelpUtils;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryDefenderTip$$ExternalSyntheticLambda0
        implements Function0 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Preference f$1;

    public /* synthetic */ BatteryDefenderTip$$ExternalSyntheticLambda0(
            Context context, Preference preference) {
        this.f$0 = context;
        this.f$1 = preference;
    }

    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final Object mo1068invoke() {
        switch (this.$r8$classId) {
            case 0:
                Context context = (Context) this.f$0;
                ((Activity) this.f$1.getContext())
                        .startActivityForResult(
                                HelpUtils.getHelpIntent(
                                        context,
                                        context.getString(R.string.help_url_battery_defender),
                                        ApnSettings.MVNO_NONE),
                                0,
                                null);
                return Unit.INSTANCE;
            default:
                ((BatteryDefenderTip) this.f$0).getClass();
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl.getPowerUsageFeatureProvider().getClass();
                Log.i("BatteryDefenderTip", "send resume charging broadcast intent=null");
                this.f$1.setVisible(false);
                return Unit.INSTANCE;
        }
    }

    public /* synthetic */ BatteryDefenderTip$$ExternalSyntheticLambda0(
            BatteryDefenderTip batteryDefenderTip, Context context, Preference preference) {
        this.f$0 = batteryDefenderTip;
        this.f$1 = preference;
    }
}
