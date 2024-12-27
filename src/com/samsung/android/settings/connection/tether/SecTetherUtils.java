package com.samsung.android.settings.connection.tether;

import android.app.Application;
import android.content.Context;
import android.net.TetheringManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.util.Log;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.Utils;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifi.SemWifiApCust;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecTetherUtils {
    public static boolean hasUserRestriction(Context context) {
        boolean hasUserRestriction =
                ((UserManager) context.getSystemService("user"))
                        .hasUserRestriction("no_config_tethering");
        SemLog.d("SecTetherUtils", "hasUserRestriction : " + hasUserRestriction);
        return hasUserRestriction;
    }

    public static boolean isAvailableTetherMenu(Context context) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        SecTetheringManagerModel secTetheringManagerModel =
                new SecTetheringManagerModel((Application) context.getApplicationContext());
        boolean z = !userManager.isAdminUser();
        if (!z) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if (!Utils.isTablet() || !"AIO".equals(Utils.getSalesCode())) {
                if (!hasUserRestriction(context)
                        && !secTetheringManagerModel.mTetheringManager.isTetheringSupported()) {
                    SemLog.d(
                            "SecTetherUtils",
                            "isTetheringSupported : "
                                    + secTetheringManagerModel.mTetheringManager
                                            .isTetheringSupported());
                    return false;
                }
                if (SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Common_EnableSprintExtension", false)) {
                    TetheringManager tetheringManager = secTetheringManagerModel.mTetheringManager;
                    int i = ConnectionsUtils.$r8$clinit;
                    String[] tetherableUsbRegexs = tetheringManager.getTetherableUsbRegexs();
                    String[] tetherableWifiRegexs = tetheringManager.getTetherableWifiRegexs();
                    String[] tetherableBluetoothRegexs =
                            tetheringManager.getTetherableBluetoothRegexs();
                    boolean z2 = tetherableUsbRegexs.length != 0;
                    boolean z3 = tetherableWifiRegexs.length != 0;
                    boolean z4 = tetherableBluetoothRegexs.length != 0;
                    int i2 = SystemProperties.getInt("persist.sys.tether_data", -1);
                    if (i2 < 3) {
                        z2 = false;
                    }
                    if (i2 < 2) {
                        z4 = false;
                    }
                    int i3 = SystemProperties.getInt("persist.sys.tether_data_usb", -1);
                    int i4 = SystemProperties.getInt("persist.sys.tether_data_bt", -1);
                    int i5 = SystemProperties.getInt("persist.sys.tether_data_wifi", -1);
                    if (i3 != -1 || i4 != -1 || i5 != -1) {
                        z2 = i3 > 0;
                        z4 = i4 > 0;
                        z3 = i5 > 0;
                    }
                    StringBuilder m =
                            RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                                    "tetheredData : ",
                                    "tetheredDataUsb :",
                                    i2,
                                    i3,
                                    "tetheredDataBluetooth :");
                    m.append(i4);
                    m.append(" tetheredDataHotspot : ");
                    m.append(i5);
                    Log.i("ConnectionsUtils", m.toString());
                    if (i2 == -1 && i3 == -1 && i4 == -1 && i5 == -1) {
                        z4 = false;
                        z2 = false;
                        z3 = false;
                    }
                    if (!z3 && !z2 && !z4) {
                        SemLog.d("SecTetherUtils", "SPR Concept");
                        return false;
                    }
                }
                return true;
            }
        }
        StringBuilder m2 =
                RowView$$ExternalSyntheticOutline0.m(
                        "mIsSecondaryUser: ", " nonSupportTethering: ", z);
        String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        m2.append(Utils.isTablet() && "AIO".equals(Utils.getSalesCode()));
        SemLog.d("SecTetherUtils", m2.toString());
        return false;
    }

    public static boolean isProvisioningNeeded(Context context) {
        if (context == null || !SemWifiApCust.isProvisioningNeeded()) {
            return false;
        }
        String[] stringArray = context.getResources().getStringArray(17236257);
        return (SystemProperties.getBoolean("net.tethering.noprovisioning", false)
                        || stringArray == null
                        || stringArray.length != 2)
                ? false
                : true;
    }
}
