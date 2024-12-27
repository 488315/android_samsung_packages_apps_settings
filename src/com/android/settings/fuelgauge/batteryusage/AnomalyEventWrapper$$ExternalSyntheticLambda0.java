package com.android.settings.fuelgauge.batteryusage;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AnomalyEventWrapper$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AnomalyEventWrapper$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((WarningItemInfo) obj).getAnomalyHintPrefKey();
            case 1:
                return ((WarningBannerInfo) obj).getMainButtonSourceHighlightKey();
            case 2:
                return ((WarningBannerInfo) obj).getMainButtonConfigSettingsName();
            case 3:
                return Integer.valueOf(
                        ((WarningBannerInfo) obj).getMainButtonConfigSettingsValue());
            case 4:
                return ((WarningItemInfo) obj).getWarningInfoString();
            case 5:
                return ((WarningBannerInfo) obj).getTitleString();
            case 6:
                return ((WarningItemInfo) obj).getTitleString();
            case 7:
                return ((WarningBannerInfo) obj).getCancelButtonString();
            case 8:
                return ((WarningItemInfo) obj).getCancelButtonString();
            case 9:
                return ((WarningBannerInfo) obj).getMainButtonString();
            case 10:
                return ((WarningItemInfo) obj).getMainButtonString();
            case 11:
                return ((WarningBannerInfo) obj).getMainButtonDestination();
            default:
                return Integer.valueOf(
                        ((WarningBannerInfo) obj).getMainButtonSourceMetricsCategory());
        }
    }
}
