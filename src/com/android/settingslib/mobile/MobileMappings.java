package com.android.settingslib.mobile;

import android.content.Context;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MobileMappings {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Config {
        public boolean hideLtePlus;
        public boolean hspaDataDistinguishable;
        public boolean show4gFor3g;
        public boolean show4gForLte;
        public boolean show4glteForLte;
        public boolean showAtLeast3G;

        public static Config readConfig(Context context) {
            Config config = new Config();
            config.showAtLeast3G = false;
            config.show4gFor3g = false;
            config.show4gForLte = false;
            config.show4glteForLte = false;
            config.hideLtePlus = false;
            Resources resources = context.getResources();
            config.showAtLeast3G = resources.getBoolean(R.bool.config_showMin3G);
            resources.getBoolean(android.R.bool.config_annoy_dianne);
            config.hspaDataDistinguishable =
                    resources.getBoolean(R.bool.config_hspa_data_distinguishable);
            CarrierConfigManager carrierConfigManager =
                    (CarrierConfigManager) context.getSystemService("carrier_config");
            SubscriptionManager.from(context);
            PersistableBundle configForSubId =
                    carrierConfigManager == null
                            ? null
                            : carrierConfigManager.getConfigForSubId(
                                    SubscriptionManager.getDefaultDataSubscriptionId());
            if (configForSubId != null) {
                configForSubId.getBoolean("always_show_data_rat_icon_bool");
                config.show4gForLte = configForSubId.getBoolean("show_4g_for_lte_data_icon_bool");
                config.show4glteForLte =
                        configForSubId.getBoolean("show_4glte_for_lte_data_icon_bool");
                config.show4gFor3g = configForSubId.getBoolean("show_4g_for_3g_data_icon_bool");
                config.hideLtePlus = configForSubId.getBoolean("hide_lte_plus_data_icon_bool");
            }
            return config;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Map mapIconSets(
            com.android.settingslib.mobile.MobileMappings.Config r9) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.mobile.MobileMappings.mapIconSets(com.android.settingslib.mobile.MobileMappings$Config):java.util.Map");
    }

    public static String toDisplayIconKey(int i) {
        if (i == 1) {
            return Integer.toString(13) + "_CA";
        }
        if (i == 2) {
            return Integer.toString(13) + "_CA_Plus";
        }
        if (i == 3) {
            return Integer.toString(20);
        }
        if (i == 5) {
            return Integer.toString(20) + "_Plus";
        }
        if (i != 999) {
            return "unsupported";
        }
        return Integer.toString(20) + "_Available";
    }
}
