package com.google.android.setupdesign.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;

import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.BuildCompatUtils;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ThemeHelper {
    public static final Logger LOG = new Logger("ThemeHelper");

    public static String colorIntToHex(Context context, int i) {
        return String.format(
                "#%06X", Integer.valueOf(context.getResources().getColor(i) & 16777215));
    }

    public static boolean shouldApplyDynamicColor(Context context) {
        if (PartnerConfigHelper.applyDynamicColorBundle == null) {
            try {
                PartnerConfigHelper.applyDynamicColorBundle =
                        context.getContentResolver()
                                .call(
                                        PartnerConfigHelper.getContentUri(context),
                                        PartnerConfigHelper.IS_DYNAMIC_COLOR_ENABLED_METHOD,
                                        (String) null,
                                        (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(
                        "PartnerConfigHelper",
                        "SetupWizard dynamic color supporting status unknown; return as false.");
                PartnerConfigHelper.applyDynamicColorBundle = null;
                return false;
            }
        }
        Bundle bundle = PartnerConfigHelper.applyDynamicColorBundle;
        return bundle != null
                && bundle.getBoolean(PartnerConfigHelper.IS_DYNAMIC_COLOR_ENABLED_METHOD, false);
    }

    public static void trySetDynamicColor(Context context) {
        boolean shouldApplyDynamicColor = shouldApplyDynamicColor(context);
        Logger logger = LOG;
        if (!shouldApplyDynamicColor) {
            logger.w(
                    "SetupWizard does not support the dynamic color or supporting status unknown.");
            return;
        }
        try {
            Logger logger2 = PartnerCustomizationLayout.LOG;
            Activity lookupActivityFromContext =
                    PartnerConfigHelper.lookupActivityFromContext(context);
            int i = 0;
            try {
                boolean isAnySetupWizard =
                        WizardManagerHelper.isAnySetupWizard(
                                PartnerConfigHelper.lookupActivityFromContext(context).getIntent());
                boolean isSetupWizardDayNightEnabled =
                        PartnerConfigHelper.isSetupWizardDayNightEnabled(context);
                if (PartnerConfigHelper.applyFullDynamicColorBundle == null) {
                    try {
                        PartnerConfigHelper.applyFullDynamicColorBundle =
                                context.getContentResolver()
                                        .call(
                                                PartnerConfigHelper.getContentUri(context),
                                                PartnerConfigHelper
                                                        .IS_FULL_DYNAMIC_COLOR_ENABLED_METHOD,
                                                (String) null,
                                                (Bundle) null);
                    } catch (IllegalArgumentException | SecurityException unused) {
                        Log.w(
                                "PartnerConfigHelper",
                                "SetupWizard full dynamic color supporting status unknown; return"
                                    + " as false.");
                        PartnerConfigHelper.applyFullDynamicColorBundle = null;
                    }
                }
                Bundle bundle = PartnerConfigHelper.applyFullDynamicColorBundle;
                if (bundle != null
                        && bundle.getBoolean(
                                PartnerConfigHelper.IS_FULL_DYNAMIC_COLOR_ENABLED_METHOD, false)) {
                    i = 1;
                }
                if (!isAnySetupWizard || (BuildCompatUtils.isAtLeastU() && i != 0)) {
                    i = isSetupWizardDayNightEnabled ? 2132083795 : 2132083796;
                    logger.atInfo(
                            "Return "
                                    .concat(
                                            isSetupWizardDayNightEnabled
                                                    ? "SudFullDynamicColorTheme_DayNight"
                                                    : "SudFullDynamicColorTheme_Light"));
                } else {
                    i = isSetupWizardDayNightEnabled ? 2132083781 : 2132083782;
                }
                String str =
                        "Gets the dynamic accentColor: [Light] "
                                + colorIntToHex(
                                        context, R.color.sud_dynamic_color_accent_glif_v3_light)
                                + ", "
                                + colorIntToHex(context, android.R.color.system_accent1_600)
                                + ", [Dark] "
                                + colorIntToHex(
                                        context, R.color.sud_dynamic_color_accent_glif_v3_dark)
                                + ", "
                                + colorIntToHex(context, android.R.color.system_accent1_100);
                logger.getClass();
                if (Log.isLoggable("SetupLibrary", 3)) {
                    Log.d("SetupLibrary", logger.prefix.concat(str));
                }
            } catch (IllegalArgumentException e) {
                String message = e.getMessage();
                Objects.requireNonNull(message);
                logger.e(message);
            }
            if (i != 0) {
                lookupActivityFromContext.setTheme(i);
            } else {
                logger.w("Error occurred on getting dynamic color theme.");
            }
        } catch (IllegalArgumentException e2) {
            String message2 = e2.getMessage();
            Objects.requireNonNull(message2);
            logger.e(message2);
        }
    }
}
