package com.samsung.android.settings.deviceinfo.softwareinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.configuration.DATA;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecuritySoftwareVersionPreferenceController extends BasePreferenceController {
    private static final String LOG_TAG = "SecuritySoftwareVersionPreferenceController";
    private static final String ASKS_VERSION =
            "ASKS v"
                    + SystemProperties.get(
                            "security.ASKS.version", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)
                    + " Release ";
    private static final String ADP_VERSION =
            "ADP v"
                    + SystemProperties.get("security.ADP.version", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)
                    + " Release ";

    public SecuritySoftwareVersionPreferenceController(Context context, String str) {
        super(context, str);
    }

    private String getADPVersion() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                new StringBuilder(),
                ADP_VERSION,
                SystemProperties.get("security.ADP.policy_version", "000000"));
    }

    private String getASKSVersion() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                new StringBuilder(),
                ASKS_VERSION,
                SystemProperties.get("security.ASKS.policy_version", "000000"));
    }

    private String getESECOSValue() {
        String str;
        String[] split = SystemProperties.get("ro.security.ese.cosname").split("_");
        if (split[0].length() == 0) {
            return split[0];
        }
        if (split.length > 1) {
            str = split[split.length - 1];
        } else {
            String[] split2 = SystemProperties.get("ro.security.esebap").split("_");
            str = split2.length > 2 ? split2[split2.length - 1] : "00000000";
        }
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("eSE COS ", str);
        String str2 = SystemProperties.get("security.esesqn");
        if (!ApnSettings.MVNO_NONE.equals(str2)) {
            m = AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(m, ".", str2);
        }
        DialogFragment$$ExternalSyntheticOutline0.m("eSECOSValue : ", m, LOG_TAG);
        return m;
    }

    private String getFIPSVersion() {
        String str = "v" + SystemProperties.get("ro.security.fips_bssl.ver");
        String str2 = "v" + SystemProperties.get("ro.security.fips_skc.ver");
        String str3 = "v" + SystemProperties.get("ro.security.fips_fmp.ver");
        String str4 = "v" + SystemProperties.get("ro.security.fips_scrypto.ver");
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "FIPS BoringSSL ", str, "\nFIPS SKC ", str2, "\nFIPS SCrypto ");
        m.append(str4);
        StringBuilder m2 =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                        m.toString());
        m2.append(str3.equals("v") ? ApnSettings.MVNO_NONE : "\nFIPS FMP ".concat(str3));
        return m2.toString();
    }

    private String getSPLVersion() {
        String str;
        String str2;
        try {
            str =
                    new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH)
                            .format(
                                    new SimpleDateFormat("yyyy-MM-dd")
                                            .parse(Build.VERSION.SECURITY_PATCH));
        } catch (ParseException e) {
            e.printStackTrace();
            str = ApnSettings.MVNO_NONE;
        }
        Log.d(LOG_TAG, "getSPLVersion() new_date : " + str);
        if (ApnSettings.MVNO_NONE.equals(str) || (str2 = Build.VERSION.SECURITY_INDEX) == null) {
            return ApnSettings.MVNO_NONE;
        }
        return "SMR " + str + " Release " + str2;
    }

    private String getSafeInstallVersion() {
        return "SafeInstall Release "
                + SystemProperties.get("security.ASKS.delta_policy_version", "00000000");
    }

    private String getSecurityVersion() {
        String sb;
        String str;
        String str2;
        String str3;
        if ("Enabled".equals(SystemProperties.get("ro.security.mdf.ux"))) {
            sb = ApnSettings.MVNO_NONE + showSecuritySWVersion();
        } else {
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            ApnSettings.MVNO_NONE + getASKSVersion(), "\n");
            m.append(getADPVersion());
            sb = m.toString();
        }
        if ("Enabled".equals(SystemProperties.get("ro.security.fips.ux"))) {
            StringBuilder m2 =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(sb);
            if (sb.equals(ApnSettings.MVNO_NONE)) {
                str3 = getFIPSVersion();
            } else {
                str3 = "\n" + getFIPSVersion();
            }
            m2.append(str3);
            sb = m2.toString();
        }
        StringBuilder m3 =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(sb);
        if (sb.equals(ApnSettings.MVNO_NONE)) {
            str = getSPLVersion();
        } else {
            str = "\n" + getSPLVersion();
        }
        m3.append(str);
        String sb2 = m3.toString();
        String eSECOSValue = getESECOSValue();
        if (!ApnSettings.MVNO_NONE.equals(eSECOSValue)) {
            StringBuilder m4 =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(sb2);
            if (!sb2.equals(ApnSettings.MVNO_NONE)) {
                eSECOSValue =
                        AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "\n", eSECOSValue);
            }
            m4.append(eSECOSValue);
            sb2 = m4.toString();
        }
        if (!"true".equals(SystemProperties.get("security.ASKS.safeinstall.enable"))) {
            return sb2;
        }
        StringBuilder m5 =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(sb2);
        if (sb2.equals(ApnSettings.MVNO_NONE)) {
            str2 = getSafeInstallVersion();
        } else {
            str2 = "\n" + getSafeInstallVersion();
        }
        m5.append(str2);
        return m5.toString();
    }

    private String showSecuritySWVersion() {
        String str;
        String str2;
        String str3 = "v" + SystemProperties.get("ro.security.mdf.ver");
        String str4 = " Release " + SystemProperties.get("ro.security.mdf.release");
        String str5 = SystemProperties.get("security.mdf");
        boolean equals = KnoxVpnPolicyConstants.VPN_CERT_TYPE_DISABLED.equals(str5);
        String str6 = ApnSettings.MVNO_NONE;
        String str7 =
                "MDF "
                        + str3
                        + str4
                        + (equals
                                ? AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                        " : ", str5)
                                : ApnSettings.MVNO_NONE);
        if (ApnSettings.MVNO_NONE.equals(SystemProperties.get("ro.security.bt.ver"))) {
            str = ApnSettings.MVNO_NONE;
        } else {
            str =
                    ComposerKt$$ExternalSyntheticOutline0.m(
                            "\nBT ",
                            "v" + SystemProperties.get("ro.security.bt.ver"),
                            " Release " + SystemProperties.get("ro.security.bt.release"));
        }
        if (ApnSettings.MVNO_NONE.equals(SystemProperties.get("ro.security.wlan.ver"))) {
            str2 = ApnSettings.MVNO_NONE;
        } else {
            str2 =
                    ComposerKt$$ExternalSyntheticOutline0.m(
                            "\nWLAN ",
                            "v" + SystemProperties.get("ro.security.wlan.ver"),
                            " Release " + SystemProperties.get("ro.security.wlan.release"));
        }
        if (!ApnSettings.MVNO_NONE.equals(SystemProperties.get("ro.security.vpnpp.ver"))) {
            str6 =
                    ComposerKt$$ExternalSyntheticOutline0.m(
                            "\nVPN Client ",
                            "v" + SystemProperties.get("ro.security.vpnpp.ver"),
                            " Release " + SystemProperties.get("ro.security.vpnpp.release"));
        }
        return str7 + str + str2 + str6 + ("\n" + getASKSVersion()) + ("\n" + getADPVersion());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return getSecurityVersion();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
