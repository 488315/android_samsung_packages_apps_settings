package com.samsung.android.settings.wifi.mobileap.datamodels;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApBandConfig {
    public final int[] mBandArray;
    public final Context mContext;
    public final String mDisplaySummaryText;
    public final String mDisplayText;
    public final int mDisplayValueIndex;
    public final int mSaLoggingEventId;

    public WifiApBandConfig(Context context, int[] iArr, int i) {
        String str;
        int i2;
        int i3;
        int i4 = 0;
        this.mSaLoggingEventId = 0;
        this.mContext = context;
        int[] bandArrayAfterBitWiseOperation =
                WifiApSoftApUtils.getBandArrayAfterBitWiseOperation(iArr);
        this.mBandArray = bandArrayAfterBitWiseOperation;
        Resources resources = context.getResources();
        boolean isThisBand2Ghz = isThisBand2Ghz();
        String str2 = ApnSettings.MVNO_NONE;
        if (isThisBand2Ghz) {
            str = resources.getString(R.string.wifi_band_24ghz);
        } else if (isThisDualBand2GhzAnd5Ghz()) {
            str = resources.getString(R.string.wifi_ap_band_2ghz_and_5ghz);
        } else if (isThisBand5Ghz()) {
            str = resources.getString(R.string.wifi_ap_band_5ghz);
        } else if (isThisBand6Ghz()) {
            str = resources.getString(R.string.sec_wifi_ap_sixghz_prefered);
        } else {
            Log.e("WifiApBandConfig", "Error getting band text, Invalid Band.");
            str = ApnSettings.MVNO_NONE;
        }
        this.mDisplayText = str;
        context.getResources();
        if (isThisDualBand2GhzAnd5Ghz()) {
            i2 = 2;
        } else if (isThisBand5Ghz()) {
            i2 = 1;
        } else if (isThisBand6Ghz()) {
            i2 = 3;
        } else {
            Log.e("WifiApBandConfig", "Error getting band event value, Invalid Band.");
            i2 = 0;
        }
        this.mSaLoggingEventId = i2;
        if (WifiApFrameworkUtils.isBandSeekBarUxSupported(context)) {
            if (isThisBand2Ghz()) {
                str2 = context.getResources().getString(R.string.wifi_ap_2ghz_tip_summary_text);
            } else if (isThisDualBand2GhzAnd5Ghz()) {
                str2 =
                        context.getResources()
                                .getString(R.string.wifi_ap_2ghz_5ghz_tip_summary_text);
            } else if (isThisBand5Ghz()) {
                str2 = context.getResources().getString(R.string.wifi_ap_5ghz_tip_summary_text);
            } else if (isThisBand6Ghz()) {
                str2 =
                        "US".equalsIgnoreCase(Utils.readCountryCode())
                                ? context.getResources()
                                        .getString(R.string.wifi_ap_6ghz_tip_vlp_summary_text)
                                : context.getResources()
                                        .getString(R.string.wifi_ap_6ghz_tip_summary_text);
            } else {
                Log.e("WifiApBandConfig", "Error getting band tip summary text, Invalid Band.");
            }
        } else if (isThisBand2Ghz()) {
            str2 = context.getResources().getString(R.string.wifi_ap_2GHz_error_text);
        } else if (isThisDualBand2GhzAnd5Ghz()) {
            str2 = context.getResources().getString(R.string.wifi_ap_band_2ghz_and_5ghz_warning);
        } else if (isThisBand5Ghz()) {
            str2 = context.getResources().getString(R.string.wifi_ap_5GHz_error_text);
        } else if (isThisBand6Ghz()) {
            str2 =
                    "US".equalsIgnoreCase(Utils.readCountryCode())
                            ? context.getResources()
                                    .getString(R.string.wifi_ap_6ghz_tip_vlp_summary_text)
                            : String.format(
                                    context.getResources()
                                            .getString(R.string.wifi_ap_6GHz_error_text),
                                    6);
        } else {
            Log.e("WifiApBandConfig", "Error getting band tip summary text, Invalid Band.");
        }
        this.mDisplaySummaryText = str2;
        if (i != -1) {
            this.mDisplayValueIndex = i;
            return;
        }
        Iterator it = ((ArrayList) WifiApSoftApUtils.getSupportedBandList(context)).iterator();
        while (true) {
            if (!it.hasNext()) {
                i3 = -1;
                break;
            }
            WifiApBandConfig wifiApBandConfig = (WifiApBandConfig) it.next();
            if (Arrays.equals(bandArrayAfterBitWiseOperation, wifiApBandConfig.mBandArray)) {
                i3 = wifiApBandConfig.mDisplayValueIndex;
                break;
            }
        }
        if (i3 == -1) {
            Log.e("WifiApBandConfig", "Error getting band index (Resetting to 0), Invalid Band.");
        } else {
            i4 = i3;
        }
        this.mDisplayValueIndex = i4;
    }

    public final boolean isThisBand2Ghz() {
        int[] iArr = this.mBandArray;
        return iArr.length == 1 && (iArr[0] & 1) != 0;
    }

    public final boolean isThisBand5Ghz() {
        int[] iArr = this.mBandArray;
        return iArr.length == 1 && (iArr[0] & 2) != 0;
    }

    public final boolean isThisBand6Ghz() {
        int[] iArr = this.mBandArray;
        return iArr.length == 1 && (iArr[0] & 4) != 0;
    }

    public final boolean isThisDualBand2GhzAnd5Ghz() {
        return WifiApFeatureUtils.is5GhzBandSupported(this.mContext) && this.mBandArray.length > 1;
    }
}
