package com.android.settingslib.mobile;

import com.android.settings.R;
import com.android.settingslib.SignalIcon$MobileIconGroup;

import com.sec.ims.settings.ImsProfile;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TelephonyIcons {
    public static final SignalIcon$MobileIconGroup CARRIER_MERGED_WIFI;
    public static final SignalIcon$MobileIconGroup CARRIER_NETWORK_CHANGE;
    public static final SignalIcon$MobileIconGroup E;
    public static final SignalIcon$MobileIconGroup FOUR_G;
    public static final SignalIcon$MobileIconGroup FOUR_G_LTE;
    public static final SignalIcon$MobileIconGroup FOUR_G_LTE_PLUS;
    public static final SignalIcon$MobileIconGroup FOUR_G_PLUS;
    public static final SignalIcon$MobileIconGroup G;
    public static final SignalIcon$MobileIconGroup H;
    public static final SignalIcon$MobileIconGroup H_PLUS;
    public static final SignalIcon$MobileIconGroup LTE;
    public static final SignalIcon$MobileIconGroup LTE_CA_5G_E;
    public static final SignalIcon$MobileIconGroup LTE_PLUS;
    public static final SignalIcon$MobileIconGroup NR_5G;
    public static final SignalIcon$MobileIconGroup NR_5G_PLUS;
    public static final SignalIcon$MobileIconGroup ONE_X;
    public static final SignalIcon$MobileIconGroup THREE_G;
    public static final SignalIcon$MobileIconGroup UNKNOWN;
    public static final SignalIcon$MobileIconGroup WFC;

    static {
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup =
                new SignalIcon$MobileIconGroup(
                        "CARRIER_NETWORK_CHANGE", R.string.carrier_network_change_mode);
        CARRIER_NETWORK_CHANGE = signalIcon$MobileIconGroup;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 =
                new SignalIcon$MobileIconGroup("3G", R.string.data_connection_3g);
        THREE_G = signalIcon$MobileIconGroup2;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 =
                new SignalIcon$MobileIconGroup("WFC", 0);
        WFC = signalIcon$MobileIconGroup3;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 =
                new SignalIcon$MobileIconGroup("Unknown", 0);
        UNKNOWN = signalIcon$MobileIconGroup4;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 =
                new SignalIcon$MobileIconGroup(
                        ImsProfile.TIMER_NAME_E, R.string.data_connection_edge);
        E = signalIcon$MobileIconGroup5;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 =
                new SignalIcon$MobileIconGroup("1X", R.string.data_connection_cdma);
        ONE_X = signalIcon$MobileIconGroup6;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 =
                new SignalIcon$MobileIconGroup(
                        ImsProfile.TIMER_NAME_G, R.string.data_connection_gprs);
        G = signalIcon$MobileIconGroup7;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup8 =
                new SignalIcon$MobileIconGroup(
                        ImsProfile.TIMER_NAME_H, R.string.data_connection_3_5g);
        H = signalIcon$MobileIconGroup8;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup9 =
                new SignalIcon$MobileIconGroup("H+", R.string.data_connection_3_5g_plus);
        H_PLUS = signalIcon$MobileIconGroup9;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup10 =
                new SignalIcon$MobileIconGroup("4G", R.string.data_connection_4g);
        FOUR_G = signalIcon$MobileIconGroup10;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup11 =
                new SignalIcon$MobileIconGroup("4G+", R.string.data_connection_4g_plus);
        FOUR_G_PLUS = signalIcon$MobileIconGroup11;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup12 =
                new SignalIcon$MobileIconGroup("LTE", R.string.data_connection_lte);
        LTE = signalIcon$MobileIconGroup12;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup13 =
                new SignalIcon$MobileIconGroup("LTE+", R.string.data_connection_lte_plus);
        LTE_PLUS = signalIcon$MobileIconGroup13;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup14 =
                new SignalIcon$MobileIconGroup("4G LTE", R.string.data_connection_4g_lte);
        FOUR_G_LTE = signalIcon$MobileIconGroup14;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup15 =
                new SignalIcon$MobileIconGroup("4G LTE+", R.string.data_connection_4g_lte_plus);
        FOUR_G_LTE_PLUS = signalIcon$MobileIconGroup15;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup16 =
                new SignalIcon$MobileIconGroup("5Ge", R.string.data_connection_5ge_html);
        LTE_CA_5G_E = signalIcon$MobileIconGroup16;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup17 =
                new SignalIcon$MobileIconGroup("5G", R.string.data_connection_5g);
        NR_5G = signalIcon$MobileIconGroup17;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup18 =
                new SignalIcon$MobileIconGroup("5G_PLUS", R.string.data_connection_5g_plus);
        NR_5G_PLUS = signalIcon$MobileIconGroup18;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup19 =
                new SignalIcon$MobileIconGroup(
                        "DataDisabled", R.string.cell_data_off_content_description);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup20 =
                new SignalIcon$MobileIconGroup(
                        "NotDefaultData", R.string.not_default_data_content_description);
        CARRIER_MERGED_WIFI =
                new SignalIcon$MobileIconGroup("CWF", R.string.data_connection_carrier_wifi);
        HashMap hashMap = new HashMap();
        hashMap.put("carrier_network_change", signalIcon$MobileIconGroup);
        hashMap.put("3g", signalIcon$MobileIconGroup2);
        hashMap.put("wfc", signalIcon$MobileIconGroup3);
        hashMap.put("unknown", signalIcon$MobileIconGroup4);
        hashMap.put("e", signalIcon$MobileIconGroup5);
        hashMap.put("1x", signalIcon$MobileIconGroup6);
        hashMap.put("g", signalIcon$MobileIconGroup7);
        hashMap.put("h", signalIcon$MobileIconGroup8);
        hashMap.put("h+", signalIcon$MobileIconGroup9);
        hashMap.put("4g", signalIcon$MobileIconGroup10);
        hashMap.put("4g+", signalIcon$MobileIconGroup11);
        hashMap.put("4glte", signalIcon$MobileIconGroup14);
        hashMap.put("4glte+", signalIcon$MobileIconGroup15);
        hashMap.put("5ge", signalIcon$MobileIconGroup16);
        hashMap.put("lte", signalIcon$MobileIconGroup12);
        hashMap.put("lte+", signalIcon$MobileIconGroup13);
        hashMap.put("5g", signalIcon$MobileIconGroup17);
        hashMap.put("5g_plus", signalIcon$MobileIconGroup18);
        hashMap.put("datadisable", signalIcon$MobileIconGroup19);
        hashMap.put("notdefaultdata", signalIcon$MobileIconGroup20);
    }
}
