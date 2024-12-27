package com.samsung.android.settings.deviceinfo.aboutphone;

import android.content.Context;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.deviceinfo.SecAboutDeviceItems;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ModelNameGetter {
    public final Context mContext;

    public ModelNameGetter(Context context) {
        this.mContext = context;
    }

    public final String getModelName() {
        if (SystemProperties.getInt("persist.sys.iss.flag_altermodel", 0) == 1) {
            return SystemProperties.get("persist.sys.iss.altermodel");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(SystemProperties.get("ro.product.model"));
        int i = SecAboutDeviceItems.ABOUTITEM_STATUS_MODELNUMBER_DS_SUFFIX;
        String str =
                (i != 0
                                && (i == 1
                                        || 2
                                                == ((TelephonyManager)
                                                                this.mContext.getSystemService(
                                                                        "phone"))
                                                        .getPhoneCount()))
                        ? "/DS"
                        : ApnSettings.MVNO_NONE;
        "NONE".split(";");
        Log.i("ModelNameGetter", "modelNameType: NONE");
        if (SecAboutDeviceItems.ABOUTITEM_STATUS_MODELNUMBER_EXTRA_SUFFIX == 2) {
            if (TextUtils.isEmpty(str)) {
                str = str.concat("/");
            }
            StringBuilder m =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
            m.append(SecAboutDeviceItems.getItemValue("modelnumberEXTRAsuffix"));
            str = m.toString();
        }
        Log.i("ModelNameGetter", "Model Number Suffix: " + str);
        sb.append(str);
        return sb.toString();
    }
}
