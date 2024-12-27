package com.samsung.android.settings.deviceinfo.regulatoryinfo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;

import java.io.File;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRegulatoryInfoActivityGlobal extends SettingsPreferenceFragment {
    public static String getRegulatoryFilePath(String str, boolean z) {
        String str2 = SystemProperties.get("persist.sys.omc_etcpath", ApnSettings.MVNO_NONE);
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(str2, "/");
        m.append(Utils.readCountryCode().toUpperCase());
        String sb = m.toString();
        boolean exists =
                new File(AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(sb, "/", str))
                        .exists();
        String m2 =
                (TextUtils.isEmpty(str2) || !exists)
                        ? !TextUtils.isEmpty(str2)
                                ? AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                        str2, "/", str)
                                : AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                        "/system/etc/", str)
                        : AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(sb, "/", str);
        if (z) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "isISORegulatoryFileExist: ", "SecRegulatoryInfoActivityGlobal", exists);
        } else {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "take Regulatory Info from : ", m2, "SecRegulatoryInfoActivityGlobal");
        }
        return m2;
    }

    public static boolean isRegulatoryFileExist(String str) {
        String regulatoryFilePath = getRegulatoryFilePath(str, true);
        File file = new File(regulatoryFilePath);
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "File Exist of ", regulatoryFilePath, " : ");
        m.append(file.exists());
        Log.d("SecRegulatoryInfoActivityGlobal", m.toString());
        return file.exists();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 77030;
    }

    public String getRegulatoryFileName() {
        boolean equals;
        String str;
        String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("COSTA RICA".equals(SystemProperties.get("ro.csc.country_code"))
                || "GTO".equals(Utils.getSalesCode())
                || "GLO".equals(Utils.getSalesCode())) {
            if ("1".equals(SystemProperties.get("ro.multisim.simslotcount"))) {
                equals = false;
            } else {
                boolean hasSystemFeature =
                        getContext()
                                .getPackageManager()
                                .hasSystemFeature("android.hardware.telephony.euicc");
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                        "isESimModel = ", "SecDeviceInfoUtils", hasSystemFeature);
                equals =
                        hasSystemFeature
                                ? "tsds2"
                                        .equals(
                                                SystemProperties.get(
                                                        "persist.ril.esim.slotswitch",
                                                        ApnSettings.MVNO_NONE))
                                : true;
            }
            if (equals) {
                return "regulatory_info_ds.png";
            }
        }
        if (!Rune.isJapanModel()) {
            return "regulatory_info.png";
        }
        String string =
                SemCscFeature.getInstance()
                        .getString("CscFeature_Setting_ConfigRevisionForRegulatoryInfo");
        if (!TextUtils.isEmpty(string)) {
            String str3 = SystemProperties.get("ro.revision");
            for (String str4 : string.split(";")) {
                String[] split = str4.split(",");
                if (split[0].equals(str3)) {
                    str = split[1];
                    break;
                }
            }
        }
        str = null;
        return (TextUtils.isEmpty(str) || !isRegulatoryFileExist(str))
                ? "regulatory_info.png"
                : str;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_regulatory_info, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.container);
        if (findViewById != null) {
            findViewById.semSetRoundedCorners(15);
            findViewById.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        ((ImageView) inflate.findViewById(R.id.regulatoryInfo))
                .setImageBitmap(
                        BitmapFactory.decodeFile(
                                getRegulatoryFilePath(getRegulatoryFileName(), false)));
        return inflate;
    }
}
