package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Context;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeBlockedEffectsSettings extends ZenModeSettingsBase {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.zen_mode_block_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeBlockedEffectsSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            int i =
                    ((NotificationManager) context.getSystemService(NotificationManager.class))
                            .getNotificationPolicy()
                            .suppressedVisualEffects;
            boolean z = (i & 4) != 0;
            String valueOf = String.valueOf(36315);
            String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            String str2 = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            boolean z2 = (i & 8) != 0;
            String valueOf2 = String.valueOf(36316);
            String str3 = z2 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = str3;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            boolean z3 = (i & 64) != 0;
            String valueOf3 = String.valueOf(36317);
            String str4 = z3 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = str4;
            statusData3.mStatusKey = valueOf3;
            arrayList.add(statusData3);
            boolean z4 = (i & 256) != 0;
            String valueOf4 = String.valueOf(36318);
            String str5 = z4 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData4 = new StatusData();
            statusData4.mStatusValue = str5;
            statusData4.mStatusKey = valueOf4;
            arrayList.add(statusData4);
            boolean z5 = (i & 16) != 0;
            String valueOf5 = String.valueOf(36319);
            String str6 = z5 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData5 = new StatusData();
            statusData5.mStatusValue = str6;
            statusData5.mStatusKey = valueOf5;
            arrayList.add(statusData5);
            boolean z6 = (i & 32) != 0;
            String valueOf6 = String.valueOf(36320);
            if (z6) {
                str = "1";
            }
            StatusData statusData6 = new StatusData();
            statusData6.mStatusValue = str;
            statusData6.mStatusKey = valueOf6;
            arrayList.add(statusData6);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeBlockedEffectsSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ZenModeBlockedEffectsSettings.buildPreferenceControllers$1(context, null);
        }
    }

    public static List buildPreferenceControllers$1(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new ZenModeVisEffectPreferenceController(
                        context, lifecycle, "zen_effect_intent", 4, 1332, null));
        arrayList.add(
                new ZenModeVisEffectPreferenceController(
                        context, lifecycle, "zen_effect_light", 8, 1333, null));
        arrayList.add(
                new ZenModeVisEffectPreferenceController(
                        context, lifecycle, "zen_effect_peek", 16, 1334, new int[] {256}));
        arrayList.add(
                new ZenModeVisEffectPreferenceController(
                        context, lifecycle, "zen_effect_status", 32, 1335, new int[] {256}));
        arrayList.add(
                new ZenModeVisEffectPreferenceController(
                        context, lifecycle, "zen_effect_badge", 64, 1336, null));
        arrayList.add(
                new ZenModeVisEffectPreferenceController(
                        context, lifecycle, "zen_effect_list", 256, 1338, null));
        arrayList.add(
                new ZenFooterPreferenceController(context, "zen_mode_block_footer", lifecycle));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers$1(context, getSettingsLifecycle());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 36039;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_block_settings;
    }
}
