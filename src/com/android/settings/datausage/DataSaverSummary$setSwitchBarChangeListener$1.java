package com.android.settings.datausage;

import androidx.preference.Preference;

import com.samsung.android.settings.logging.LoggingHelper;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataSaverSummary$setSwitchBarChangeListener$1
        implements Preference.OnPreferenceChangeListener {
    public final /* synthetic */ DataSaverSummary this$0;

    public DataSaverSummary$setSwitchBarChangeListener$1(DataSaverSummary dataSaverSummary) {
        this.this$0 = dataSaverSummary;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object newValue) {
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        boolean booleanValue = ((Boolean) newValue).booleanValue();
        long j = !booleanValue ? 1L : 0L;
        DataSaverSummary dataSaverSummary = this.this$0;
        synchronized (dataSaverSummary) {
            try {
                if (!dataSaverSummary.switching) {
                    dataSaverSummary.switching = true;
                    DataSaverBackend dataSaverBackend = dataSaverSummary.dataSaverBackend;
                    if (dataSaverBackend == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("dataSaverBackend");
                        throw null;
                    }
                    dataSaverBackend.mPolicyManager.setRestrictBackground(booleanValue);
                    dataSaverBackend.mMetricsFeatureProvider.action(
                            dataSaverBackend.mContext, 394, booleanValue ? 1 : 0);
                    LoggingHelper.insertEventLogging(7100, 7101, j);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }
}
