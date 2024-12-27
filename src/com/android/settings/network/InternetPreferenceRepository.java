package com.android.settings.network;

import android.content.Context;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.android.settings.network.telephony.DataSubscriptionRepository;
import com.android.settings.wifi.WifiSummaryRepository;
import com.android.settings.wifi.repository.WifiRepository;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanDelegate;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanKt;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanKt$settingsGlobalBooleanFlow$$inlined$map$1;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalChangeFlowKt;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InternetPreferenceRepository {
    public final Flow airplaneModeOnFlow;
    public final ConnectivityRepository connectivityRepository;
    public final Context context;
    public final DataSubscriptionRepository dataSubscriptionRepository;
    public final WifiRepository wifiRepository;
    public final WifiSummaryRepository wifiSummaryRepository;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DisplayInfo {
        public final int iconResId;
        public final String summary;

        public DisplayInfo(String summary, int i) {
            Intrinsics.checkNotNullParameter(summary, "summary");
            this.summary = summary;
            this.iconResId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisplayInfo)) {
                return false;
            }
            DisplayInfo displayInfo = (DisplayInfo) obj;
            return Intrinsics.areEqual(this.summary, displayInfo.summary)
                    && this.iconResId == displayInfo.iconResId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.iconResId) + (this.summary.hashCode() * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DisplayInfo(summary=");
            sb.append(this.summary);
            sb.append(", iconResId=");
            return Anchor$$ExternalSyntheticOutline0.m(sb, this.iconResId, ")");
        }
    }

    public InternetPreferenceRepository(Context context) {
        ConnectivityRepository connectivityRepository = new ConnectivityRepository(context);
        WifiSummaryRepository wifiSummaryRepository = new WifiSummaryRepository(context);
        DataSubscriptionRepository dataSubscriptionRepository =
                new DataSubscriptionRepository(context);
        WifiRepository wifiRepository = new WifiRepository(context);
        KProperty[] kPropertyArr = SettingsGlobalBooleanKt.$$delegatedProperties;
        Intrinsics.checkNotNullParameter(context, "<this>");
        Flow distinctUntilChanged =
                FlowKt.distinctUntilChanged(
                        new SettingsGlobalBooleanKt$settingsGlobalBooleanFlow$$inlined$map$1(
                                SettingsGlobalChangeFlowKt.settingsGlobalChangeFlow(
                                        context, "airplane_mode_on", true),
                                new SettingsGlobalBooleanDelegate(
                                        context, "airplane_mode_on", false)));
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.connectivityRepository = connectivityRepository;
        this.wifiSummaryRepository = wifiSummaryRepository;
        this.dataSubscriptionRepository = dataSubscriptionRepository;
        this.wifiRepository = wifiRepository;
        this.airplaneModeOnFlow = distinctUntilChanged;
    }
}
