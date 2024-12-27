package com.android.settings;

import android.content.Context;
import android.content.Intent;
import android.sysprop.SetupWizardProperties;

import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.util.ThemeHelper;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class SetupWizardUtils {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getTheme(Context context, Intent intent) {
        char c;
        String stringExtra = intent.getStringExtra("theme");
        if (stringExtra == null) {
            stringExtra = (String) SetupWizardProperties.theme().orElse(ApnSettings.MVNO_NONE);
        }
        if (stringExtra == null) {
            return R.style.GlifTheme;
        }
        if (!intent.getBooleanExtra("isSetupFlow", false)) {
            switch (stringExtra) {
                case "glif_v2_light":
                case "glif_v2":
                    return R.style.GlifV2Theme;
                case "glif_v3_light":
                case "glif_v3":
                    return R.style.GlifV3Theme;
                case "glif_v4_light":
                case "glif_v4":
                    return R.style.GlifV4Theme;
                default:
                    return R.style.GlifTheme;
            }
        }
        Logger logger = ThemeHelper.LOG;
        if (!PartnerConfigHelper.isSetupWizardDayNightEnabled(context)) {
            switch (stringExtra) {
                case "glif_v2_light":
                    return 2132083321;
                case "glif_v3_light":
                    return 2132083331;
                case "glif_v4_light":
                    return 2132083338;
                case "glif_v2":
                    return R.style.GlifV2Theme;
                case "glif_v3":
                    return R.style.GlifV3Theme;
                case "glif_v4":
                    return R.style.GlifV4Theme;
                case "glif_light":
                    return 2132083317;
                default:
                    return R.style.GlifTheme;
            }
        }
        switch (stringExtra.hashCode()) {
            case -2128555920:
                if (stringExtra.equals("glif_v2_light")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1241052239:
                if (stringExtra.equals("glif_v3_light")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -353548558:
                if (stringExtra.equals("glif_v4_light")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3175618:
                if (stringExtra.equals("glif")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 115650329:
                if (stringExtra.equals("glif_v2")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 115650330:
                if (stringExtra.equals("glif_v3")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 115650331:
                if (stringExtra.equals("glif_v4")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 767685465:
                if (stringExtra.equals("glif_light")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 4:
                return 2132083319;
            case 1:
            case 5:
                return 2132083327;
            case 2:
            case 6:
                return 2132083337;
            case 3:
            case 7:
                return 2132083316;
            default:
                return R.style.GlifTheme;
        }
    }
}
