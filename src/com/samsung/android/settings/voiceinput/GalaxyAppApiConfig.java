package com.samsung.android.settings.voiceinput;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.voiceinput.samsungaccount.SaHelper;
import com.samsung.android.settings.voiceinput.samsungaccount.data.SaInfo;
import com.samsung.android.settings.voiceinput.samsungaccount.listener.SaTokenResultListener;
import com.samsung.android.util.SemLog;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class GalaxyAppApiConfig {
    private static final String TAG = "@VoiceIn: GalaxyAppApiConfig";
    private final Context mContext;

    public GalaxyAppApiConfig(Context context) {
        this.mContext = context;
    }

    public final String getCC() {
        String str = "NONE";
        SaHelper.init(this.mContext);
        SaHelper.requestToken(
                new SaTokenResultListener() { // from class:
                                              // com.samsung.android.settings.voiceinput.GalaxyAppApiConfig.1
                    @Override // com.samsung.android.settings.voiceinput.samsungaccount.listener.SaTokenResultListener
                    public final void onError(int i) {
                        SemLog.e(GalaxyAppApiConfig.TAG, "Fail to receive cc");
                        if (i == -1004) {
                            SemLog.e(GalaxyAppApiConfig.TAG, "onError : " + i);
                        }
                    }

                    @Override // com.samsung.android.settings.voiceinput.samsungaccount.listener.SaTokenResultListener
                    public final void onResult() {
                        SemLog.d(GalaxyAppApiConfig.TAG, "cc received");
                    }
                });
        try {
            if (SaHelper.getSaInfo() != null) {
                SaInfo saInfo = SaHelper.getSaInfo();
                Objects.requireNonNull(saInfo);
                SaInfo saInfo2 = saInfo;
                str = saInfo.getCC();
            }
            SemLog.d(TAG, "cc = " + str);
        } catch (Exception unused) {
            SemLog.d(TAG, "Could not getCC from SaInfo ");
        }
        return str;
    }

    public final String getCallerId() {
        return this.mContext.getPackageName();
    }

    public final String getMCC() {
        String simOperator =
                ((TelephonyManager) this.mContext.getSystemService("phone")).getSimOperator();
        return (simOperator == null || simOperator.length() <= 3)
                ? ApnSettings.MVNO_NONE
                : simOperator.substring(0, 3);
    }

    public final String getMNC() {
        String simOperator =
                ((TelephonyManager) this.mContext.getSystemService("phone")).getSimOperator();
        return (simOperator == null || simOperator.length() <= 3)
                ? ApnSettings.MVNO_NONE
                : simOperator.substring(3);
    }

    public final String getSecondaryUniqueId() {
        return Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
    }

    public final String getVersionCode(String str) {
        try {
            String valueOf =
                    String.valueOf(
                            this.mContext.getPackageManager().getPackageInfo(str, 0).versionCode);
            SemLog.d(TAG, "result versioncode = " + valueOf);
            return valueOf;
        } catch (PackageManager.NameNotFoundException e) {
            SemLog.d(TAG, "getVersionCode() Exception :: " + e.getMessage());
            return "-1";
        }
    }
}
