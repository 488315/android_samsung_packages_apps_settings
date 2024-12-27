package com.samsung.android.settings.languagepack.appsstub.saccount;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.languagepack.appsstub.AppStubUtils;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SaInfoUtils {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.appsstub.saccount.SaInfoUtils$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ boolean[] val$bResults;
        public final /* synthetic */ SaInfo val$saInfo;
        public final /* synthetic */ SaInfoManager val$saManager;

        public AnonymousClass1(SaInfo saInfo, SaInfoManager saInfoManager, boolean[] zArr) {
            this.val$saInfo = saInfo;
            this.val$saManager = saInfoManager;
            this.val$bResults = zArr;
        }

        public final void onFailed(String str, String str2) {
            Log.e("SaInfoUtils", "getSaInfo : onFailed : errCode = " + str);
            Log.e("SaInfoUtils", "getSaInfo : onFailed : errMsg = " + str2);
            this.val$saManager.releaseSaAuthInfo();
            boolean[] zArr = this.val$bResults;
            zArr[0] = false;
            zArr[1] = true;
        }
    }

    public static SaInfo getSaInfo(Context context) {
        AccountManager accountManager;
        SaInfo saInfo = new SaInfo();
        saInfo.appId = ApnSettings.MVNO_NONE;
        saInfo.token = ApnSettings.MVNO_NONE;
        saInfo.url = ApnSettings.MVNO_NONE;
        saInfo.countryCode = "NONE";
        if (!AppStubUtils.isUTModeTest() && !AppStubUtils.usingPreDeployServer()) {
            return saInfo;
        }
        boolean[] zArr = {false, false};
        SaInfoManager saInfoManager = new SaInfoManager(context.getApplicationContext());
        try {
            Log.i(
                    "SaInfoManager",
                    "sa ver: "
                            + saInfoManager
                                    .mContext
                                    .getPackageManager()
                                    .getPackageInfo("com.osp.app.signin", 128)
                                    .versionName);
            accountManager = AccountManager.get(saInfoManager.mContext);
        } catch (Exception unused) {
            Log.e("SaInfoUtils", "getSaInfo : there is no samsung account package!");
        }
        if (accountManager == null) {
            Log.e("SaInfoManager", "isSamsungAccountSigned : accountManager is null");
        } else if (accountManager.getAccountsByType("com.osp.app.signin").length > 0) {
            saInfoManager.mSaInfoListener = new AnonymousClass1(saInfo, saInfoManager, zArr);
            Log.i("SaInfoManager", "bindToSamsungAccountService : try to bind SA service");
            Intent intent = new Intent(SaContract.IntentAction.SERVICE_BINDING);
            intent.setClassName("com.osp.app.signin", SaContract.BINDING_CLASS_NAME);
            if (saInfoManager.mContext.bindService(intent, saInfoManager.mServiceConnection, 1)) {
                Log.i("SaInfoManager", "bindToSamsungAccountService : success to bind SA service");
            } else {
                Log.e("SaInfoManager", "bindToSamsungAccountService : cannot bind SA service");
                saInfoManager.handleFailure("cannot bind SA service");
            }
            int i = 0;
            while (true) {
                if (zArr[1]) {
                    break;
                }
                try {
                    Thread.sleep(100L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
                if (i > 70) {
                    zArr[0] = false;
                    break;
                }
            }
            if (!zArr[0]) {
                Log.e(
                        "SaInfoUtils",
                        "getSaInfo : onReceived : Time out to get samsung account information!,"
                            + " nCountLoop = "
                                + i);
                saInfo.countryCode = "FAIL";
            }
            return saInfo;
        }
        Log.e("SaInfoUtils", "getSaInfo : samsung account is not signed in!");
        return saInfo;
    }
}
