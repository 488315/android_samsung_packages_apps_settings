package com.samsung.android.settings.voiceinput.samsungaccount;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;
import com.samsung.android.settings.voiceinput.samsungaccount.data.SaInfo;
import com.samsung.android.settings.voiceinput.samsungaccount.listener.SaTokenResultListener;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SaHelper {
    private static final String TAG = "SaHelper";
    private static SaHelperInterface mSaHelperImpl;
    private static SaInfo mSaInfo;

    public static synchronized SaInfo getSaInfo() {
        synchronized (SaHelper.class) {
            Log.d(TAG, "getSaInfo");
            if (!isSamsungAccountSigned()) {
                return null;
            }
            SaInfo saInfo = mSaInfo;
            if (saInfo != null) {
                return saInfo;
            }
            SaHelper$$ExternalSyntheticLambda1 saHelper$$ExternalSyntheticLambda1 =
                    new SaHelper$$ExternalSyntheticLambda1();
            SaInfo saInfo2 = new SaInfo();
            saHelper$$ExternalSyntheticLambda1.accept(saInfo2);
            mSaInfo = saInfo2;
            return saInfo2;
        }
    }

    public static synchronized void init(Context context) {
        synchronized (SaHelper.class) {
            Log.d(TAG, "init");
            mSaHelperImpl = new SaImpl(context);
        }
    }

    public static boolean isSamsungAccountSigned() {
        Log.d(TAG, "isSamsungAccountSigned");
        return mSaHelperImpl.isSamsungAccountSigned();
    }

    public static void requestToken(SaTokenResultListener saTokenResultListener) {
        Log.d(TAG, "requestToken");
        mSaHelperImpl.requestToken(saTokenResultListener);
    }

    public static void setSaInfo(Bundle bundle) {
        Log.d(TAG, "setSaInfo");
        if (bundle == null) {
            Log.i(TAG, "Bundle is null");
            return;
        }
        final String string = bundle.getString(SaContract.TokenInfo.CC, null);
        Consumer consumer =
                new Consumer() { // from class:
                                 // com.samsung.android.settings.voiceinput.samsungaccount.SaHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((SaInfo) obj).setCC(string);
                    }
                };
        SaInfo saInfo = new SaInfo();
        consumer.accept(saInfo);
        synchronized (SaHelper.class) {
            mSaInfo = saInfo;
        }
    }
}
