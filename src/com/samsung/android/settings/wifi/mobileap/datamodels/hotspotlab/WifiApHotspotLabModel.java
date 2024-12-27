package com.samsung.android.settings.wifi.mobileap.datamodels.hotspotlab;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApHotspotLabModel {
    public long mEventDateTimeInMillis;
    public String mEventLog;
    public String[] mEventLogArray;

    public final SpannableString getSpannableSummary() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String[] strArr = this.mEventLogArray;
        if (strArr != null) {
            for (int i = 0; i < strArr.length; i++) {
                if (!TextUtils.isEmpty(strArr[i].trim())) {
                    SpannableString spannableString = new SpannableString(strArr[i].trim());
                    spannableString.setSpan(
                            new StyleSpan(1), 0, spannableString.toString().indexOf("=") + 1, 33);
                    spannableStringBuilder.append((CharSequence) spannableString);
                    if (i < strArr.length - 1) {
                        spannableStringBuilder.append((CharSequence) "\n");
                    }
                }
            }
        } else {
            Log.i("WifiApHotspotLabModel", "mEventLogArray is null");
        }
        return SpannableString.valueOf(spannableStringBuilder);
    }
}
