package com.samsung.android.settings.network.apn;

import android.util.Log;

import com.android.settings.Utils;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ApnUtils {
    public static boolean isVZWConcept(int i) {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        String salesCode = Utils.getSalesCode();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider()).getClass();
        String string =
                SemCarrierFeature.getInstance()
                        .getString(
                                i,
                                "CarrierFeature_RIL_ConfigNetworkTypeCapability",
                                ApnSettings.MVNO_NONE,
                                true);
        if (!"VZW".equals(salesCode)
                && !"VPP".equals(salesCode)
                && !"CCT".equals(salesCode)
                && !"CHA".equals(salesCode)
                && !"TFV".equals(salesCode)
                && !"FKR".equals(salesCode)
                && (!"TFN".equals(salesCode) || string == null || !string.contains("VZW-TFN"))) {
            return false;
        }
        Log.d("ApnUtils", "isVZWConcept() return true");
        return true;
    }
}
