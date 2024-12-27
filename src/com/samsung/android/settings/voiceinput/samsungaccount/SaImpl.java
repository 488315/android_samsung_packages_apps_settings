package com.samsung.android.settings.voiceinput.samsungaccount;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.voiceinput.PackageUtils;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;
import com.samsung.android.settings.voiceinput.samsungaccount.listener.SaTokenResultListener;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SaImpl implements SaHelperInterface {
    private static final String TAG = "SaImpl";
    private Context mContext;

    public SaImpl(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override // com.samsung.android.settings.voiceinput.samsungaccount.SaHelperInterface
    public final boolean isSamsungAccountSigned() {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        Bundle bundle2;
        SemLog.d(TAG, "isSamsungAccountSigned");
        Context context = this.mContext;
        SemLog.i("@VoiceIn: SamsungAccountUtils", "isSamsungAccountSigned");
        if (PackageUtils.isNetworkConnected(context, 1)
                || PackageUtils.isNetworkConnected(context, 0)) {
            String str = null;
            try {
                applicationInfo =
                        context.getPackageManager().getApplicationInfo("com.osp.app.signin", 128);
            } catch (PackageManager.NameNotFoundException unused) {
                SemLog.d(
                        "@VoiceIn: SamsungAccountUtils",
                        "Signature of App not registered with SA server");
                applicationInfo = null;
            }
            if (((applicationInfo == null || (bundle2 = applicationInfo.metaData) == null)
                            ? 0.0f
                            : bundle2.getFloat("AccountManagerProvider", 0.0f))
                    > 0.0f) {
                SemLog.i("@VoiceIn: SamsungAccountUtils", "getSamsungAccountId");
                try {
                    bundle =
                            context.getContentResolver()
                                    .call(
                                            Uri.parse(
                                                    "content://com.samsung.android.samsungaccount.accountmanagerprovider"),
                                            "getSamsungAccountId",
                                            SaContract.ExtraValue.CLIENT_ID,
                                            (Bundle) null);
                } catch (Exception e) {
                    SemLog.i("@VoiceIn: SamsungAccountUtils", "getSamsungAccountId " + e);
                    bundle = null;
                }
                if (bundle != null) {
                    int i = bundle.getInt("result_code", 1);
                    str = bundle.getString("result_message", ApnSettings.MVNO_NONE);
                    if (i == 0) {
                        SemLog.i(
                                "@VoiceIn: SamsungAccountUtils",
                                "AccountManagerProvider successful");
                    } else {
                        SemLog.i(
                                "@VoiceIn: SamsungAccountUtils",
                                "AccountManagerProvider could not check:" + i);
                    }
                }
                if (!TextUtils.isEmpty(str)) {
                    SemLog.i("@VoiceIn: SamsungAccountUtils", "SamsungAccount signed in");
                    return true;
                }
                SemLog.i("@VoiceIn: SamsungAccountUtils", "SamsungAccount not signed in");
            } else {
                SemLog.i(
                        "@VoiceIn: SamsungAccountUtils",
                        "AccountManagerProvider version not supported");
            }
        } else {
            SemLog.i("@VoiceIn: SamsungAccountUtils", "isSamsungAccountSigned: No network");
        }
        return false;
    }

    @Override // com.samsung.android.settings.voiceinput.samsungaccount.SaHelperInterface
    public final void requestToken(SaTokenResultListener saTokenResultListener) {
        SaTokenTask saTokenTask;
        SemLog.d(TAG, "Request token");
        int i = SaTokenTask.$r8$clinit;
        saTokenTask = SaTokenTask.LazyHolder.INSTANCE;
        saTokenTask.execute(this.mContext.getApplicationContext(), saTokenResultListener);
    }
}
