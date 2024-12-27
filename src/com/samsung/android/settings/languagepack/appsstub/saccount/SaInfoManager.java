package com.samsung.android.settings.languagepack.appsstub.saccount;

import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Base64;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.msc.sa.aidl.ISACallback;
import com.msc.sa.aidl.ISAService;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;
import com.sec.ims.configuration.DATA;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SaInfoManager {
    public boolean mAlreadyExpiredToken;
    public boolean mBound;
    public final Context mContext;
    public ISAService mISaService;
    public final String mPackageName;
    public String mRegistrationCode;
    public boolean mRequested;
    public final String mSAClientId;
    public final String mSAKey;
    public final AnonymousClass1 mSaCallback;
    public SaInfoUtils.AnonymousClass1 mSaInfoListener;
    public final AnonymousClass2 mServiceConnection;

    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.settings.languagepack.appsstub.saccount.SaInfoManager$2] */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.samsung.android.settings.languagepack.appsstub.saccount.SaInfoManager$1] */
    public SaInfoManager(Context context) {
        byte[] decode = Base64.decode("aWt5amV3a2RxcA==", 2);
        Charset charset = StandardCharsets.UTF_8;
        String str = new String(decode, charset);
        boolean isEmpty = TextUtils.isEmpty(str);
        String str2 = ApnSettings.MVNO_NONE;
        this.mSAClientId = isEmpty ? ApnSettings.MVNO_NONE : str;
        String str3 =
                new String(
                        Base64.decode("MjZGMzRBMkUwREYxRDhEREU4MzAyRjI4NkZEMTFDOTM=", 2), charset);
        this.mSAKey = TextUtils.isEmpty(str3) ? str2 : str3;
        this.mSaInfoListener = null;
        this.mServiceConnection =
                new ServiceConnection() { // from class:
                                          // com.samsung.android.settings.languagepack.appsstub.saccount.SaInfoManager.2
                    @Override // android.content.ServiceConnection
                    public final void onServiceConnected(
                            ComponentName componentName, IBinder iBinder) {
                        Log.i("SaInfoManager", "onServiceConnected : start");
                        SaInfoManager saInfoManager = SaInfoManager.this;
                        ISAService iSAService = null;
                        saInfoManager.mISaService = null;
                        saInfoManager.mRegistrationCode = null;
                        saInfoManager.mBound = true;
                        int i = ISAService.Stub.$r8$clinit;
                        if (iBinder != null) {
                            IInterface queryLocalInterface =
                                    iBinder.queryLocalInterface("com.msc.sa.aidl.ISAService");
                            if (queryLocalInterface == null
                                    || !(queryLocalInterface instanceof ISAService)) {
                                ISAService.Stub.Proxy proxy = new ISAService.Stub.Proxy();
                                proxy.mRemote = iBinder;
                                iSAService = proxy;
                            } else {
                                iSAService = (ISAService) queryLocalInterface;
                            }
                        }
                        saInfoManager.mISaService = iSAService;
                        try {
                            if (SaInfoManager.this.mISaService == null) {
                                Log.e("SaInfoManager", "onServiceConnected : mISaService is null");
                                SaInfoManager.this.handleFailure("mISaService is null");
                                return;
                            }
                            Log.i(
                                    "SaInfoManager",
                                    "onServiceConnected : pn = " + SaInfoManager.this.mPackageName);
                            Log.d(
                                    "SaInfoManager",
                                    "onServiceConnected : ai = " + SaInfoManager.this.mSAClientId);
                            SaInfoManager saInfoManager2 = SaInfoManager.this;
                            saInfoManager2.mRegistrationCode =
                                    ((ISAService.Stub.Proxy) saInfoManager2.mISaService)
                                            .registerCallback(
                                                    saInfoManager2.mSAClientId,
                                                    saInfoManager2.mSAKey,
                                                    saInfoManager2.mPackageName,
                                                    saInfoManager2.mSaCallback);
                            if (SaInfoManager.this.mRegistrationCode == null) {
                                Log.e(
                                        "SaInfoManager",
                                        "onServiceConnected : mRegistrationCode is null");
                                SaInfoManager.this.handleFailure("mRegistrationCode is null");
                                return;
                            }
                            Log.d(
                                    "SaInfoManager",
                                    "onServiceConnected : rc = "
                                            + SaInfoManager.this.mRegistrationCode);
                            AccountManager accountManager =
                                    AccountManager.get(SaInfoManager.this.mContext);
                            if (accountManager == null) {
                                Log.e(
                                        "SaInfoManager",
                                        "isSamsungAccountSigned : accountManager is null");
                            } else if (accountManager.getAccountsByType("com.osp.app.signin").length
                                    > 0) {
                                SaInfoManager saInfoManager3 = SaInfoManager.this;
                                saInfoManager3.mAlreadyExpiredToken = false;
                                saInfoManager3.requestForUserToken(ApnSettings.MVNO_NONE);
                                return;
                            }
                            Log.e(
                                    "SaInfoManager",
                                    "onServiceConnected : samsung account is not signed in!");
                            SaInfoManager.this.handleFailure("samsung account is not signed in!");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceDisconnected(ComponentName componentName) {
                        Log.i("SaInfoManager", "onServiceDisconnected : start");
                        SaInfoManager saInfoManager = SaInfoManager.this;
                        saInfoManager.mBound = true;
                        saInfoManager.mISaService = null;
                    }
                };
        this.mContext = context;
        this.mPackageName = context.getPackageName();
        this.mBound = false;
        this.mAlreadyExpiredToken = false;
        this.mRequested = false;
        this.mSaCallback =
                new ISACallback.Stub() { // from class:
                    // com.samsung.android.settings.languagepack.appsstub.saccount.SaInfoManager.1
                    @Override // com.msc.sa.aidl.ISACallback
                    public final void onReceiveAccessToken(int i, boolean z, Bundle bundle) {
                        Log.i(
                                "SaInfoManager",
                                "onReceiveAT : requestId = " + i + ", isSuccess = " + z);
                        SaInfoManager saInfoManager = SaInfoManager.this;
                        saInfoManager.mRequested = false;
                        if (i != 1000) {
                            Log.e("SaInfoManager", "onReceiveAT : not supported requestId!");
                            SaInfoManager.this.handleFailure("not supported requestId!");
                            return;
                        }
                        if (!z) {
                            if (bundle == null) {
                                Log.e("SaInfoManager", "onReceiveAT : resultData is null!");
                                saInfoManager.handleFailure("resultData is null!");
                                return;
                            }
                            String string = bundle.getString("error_code");
                            String string2 = bundle.getString("error_message");
                            StringBuilder m =
                                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                            "onReceiveAT : err = ",
                                            string,
                                            ", msg = ",
                                            string2,
                                            ", alreadyExp = ");
                            m.append(saInfoManager.mAlreadyExpiredToken);
                            Log.e("SaInfoManager", m.toString());
                            if (!saInfoManager.mAlreadyExpiredToken && "SAC_0402".equals(string)) {
                                String string3 = bundle.getString("access_token");
                                Log.d("SaInfoManager", "onReceiveAT : eat = " + string3);
                                saInfoManager.mAlreadyExpiredToken = true;
                                saInfoManager.requestForUserToken(string3);
                                return;
                            }
                            Log.e("SaInfoManager", "handleFailure : " + string + ", " + string2);
                            SaInfoUtils.AnonymousClass1 anonymousClass1 =
                                    saInfoManager.mSaInfoListener;
                            if (anonymousClass1 != null) {
                                anonymousClass1.onFailed(string, string2);
                                return;
                            }
                            return;
                        }
                        if (bundle == null) {
                            Log.e("SaInfoManager", "onReceiveAT : resultData is null!");
                            saInfoManager.handleFailure("resultData is null!");
                            return;
                        }
                        String string4 = bundle.getString("access_token");
                        String string5 = bundle.getString("api_server_url");
                        String string6 = bundle.getString("auth_server_url");
                        String string7 = bundle.getString(SaContract.TokenInfo.CC);
                        Log.d("SaInfoManager", "onReceiveAT : at = " + string4);
                        Log.d("SaInfoManager", "onReceiveAT : apsu = " + string5);
                        Log.d("SaInfoManager", "onReceiveAT : ausu = " + string6);
                        Log.d("SaInfoManager", "onReceiveAT : cc = " + string7);
                        SaInfoUtils.AnonymousClass1 anonymousClass12 =
                                saInfoManager.mSaInfoListener;
                        if (anonymousClass12 == null) {
                            Log.e(
                                    "SaInfoManager",
                                    "onReceiveAT : mSaInfoListener is null. Cannot return"
                                        + " information!");
                            return;
                        }
                        StringBuilder sb = new StringBuilder("getSaInfo : onReceived : aid = ");
                        String str4 = saInfoManager.mSAClientId;
                        sb.append(str4);
                        Log.d("SaInfoUtils", sb.toString());
                        Log.d("SaInfoUtils", "getSaInfo : onReceived : tkn = " + string4);
                        Log.d("SaInfoUtils", "getSaInfo : onReceived : url = " + string6);
                        Log.i("SaInfoUtils", "getSaInfo : onReceived : cc = " + string7);
                        SaInfo saInfo = anonymousClass12.val$saInfo;
                        saInfo.appId = str4;
                        saInfo.token = string4;
                        saInfo.url = string6;
                        saInfo.countryCode = string7;
                        anonymousClass12.val$saManager.releaseSaAuthInfo();
                        boolean[] zArr = anonymousClass12.val$bResults;
                        zArr[0] = true;
                        zArr[1] = true;
                    }
                };
    }

    public final void handleFailure(String str) {
        Log.e("SaInfoManager", "handleFailure : ".concat(str));
        SaInfoUtils.AnonymousClass1 anonymousClass1 = this.mSaInfoListener;
        if (anonymousClass1 != null) {
            anonymousClass1.onFailed(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, str);
        }
    }

    public final void releaseSaAuthInfo() {
        Log.i("SaInfoManager", "unbindSamsungAccountService : unbind SA service");
        String str = this.mRegistrationCode;
        if (str != null) {
            try {
                ISAService iSAService = this.mISaService;
                if (iSAService != null) {
                    ((ISAService.Stub.Proxy) iSAService).unregisterCallback(str);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.mRegistrationCode = null;
        }
        this.mISaService = null;
        try {
            this.mContext.unbindService(this.mServiceConnection);
            this.mBound = false;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Log.i("SaInfoManager", "unbindSamsungAccountService : finish to unbind SA service");
    }

    public final void requestForUserToken(String str) {
        Log.i("SaInfoManager", "requestForUserToken : start");
        if (this.mRequested) {
            android.util.Log.w(
                    "[LanguagePack]".concat("SaInfoManager"),
                    "requestForUserToken : requesting...");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray(
                SaContract.ExtraKey.ADDITIONAL,
                new String[] {
                    "access_token", "api_server_url", "auth_server_url", SaContract.TokenInfo.CC
                });
        bundle.putString("scope", "galaxystore.openapi");
        if (!TextUtils.isEmpty(str)) {
            Log.i("SaInfoManager", "requestForUserToken : access token expired - " + str);
            bundle.putString("expired_access_token", str);
        }
        if (this.mBound) {
            this.mRequested = true;
            ((ISAService.Stub.Proxy) this.mISaService)
                    .requestAccessToken(1000, this.mRegistrationCode, bundle);
        } else {
            Log.e(
                    "SaInfoManager",
                    "requestForUserToken : cannot requestAccessToken, SA service is not"
                        + " connected!");
            handleFailure("cannot requestAccessToken, SA service is not connected!");
        }
        Log.i("SaInfoManager", "requestForUserToken : finish");
    }
}
