package com.samsung.android.settings.voiceinput.samsungaccount;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;

import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.msc.sa.aidl.ISACallback;
import com.msc.sa.aidl.ISAService;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;
import com.samsung.android.settings.voiceinput.samsungaccount.data.SaError;
import com.samsung.android.settings.voiceinput.samsungaccount.listener.SaTokenResultListener;
import com.samsung.android.util.SemLog;

import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SaTokenTask implements ServiceConnection {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final int BACKGROUND_NETWORK_BLOCKED = 3;
    private static final String TAG = "SaTokenTask";
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(10);
    private String mCallbackResult;
    private Context mContext;
    private CountDownTimer mCountDownTimer;
    private List<SaTokenResultListener> mListeners;
    private ISACallback.Stub mSaCallback = new AnonymousClass1();
    private ISAService mSaService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.voiceinput.samsungaccount.SaTokenTask$1, reason: invalid class name */
    public class AnonymousClass1 extends ISACallback.Stub {
        private static final int REQUEST_RETRY_MAX_COUNT = 2;
        private final long REQUEST_RETRY_DELAY = TimeUnit.MILLISECONDS.toMillis(500);
        private int mRetryCount = 0;

        public AnonymousClass1() {}

        @Override // com.msc.sa.aidl.ISACallback
        public final void onReceiveAccessToken(int i, boolean z, Bundle bundle) {
            StringBuilder m =
                    RowView$$ExternalSyntheticOutline0.m(
                            "onReceiveAccessToken, isSuccess: ", " , resultData: ", z);
            m.append(bundle != null ? bundle.getString("error_code") : "null");
            SemLog.i(SaTokenTask.TAG, m.toString());
            if (SaTokenTask.this.mCountDownTimer != null) {
                SaTokenTask.this.mCountDownTimer.cancel();
            }
            if (SaTokenTask.this.mSaService == null) {
                SemLog.i(SaTokenTask.TAG, "SA Service is null. It was already unbind");
                return;
            }
            if (bundle == null || this.mRetryCount == 2) {
                SaTokenTask.this.sendResult(-1000, bundle);
                this.mRetryCount = 0;
                return;
            }
            if (z) {
                SaTokenTask.this.sendResult(1, bundle);
                this.mRetryCount = 0;
                return;
            }
            String string = bundle.getString("error_code");
            if ("SAC_0301".equalsIgnoreCase(string) || "SAC_0401".equalsIgnoreCase(string)) {
                new Handler(Looper.getMainLooper())
                        .postDelayed(
                                new SaTokenTask$$ExternalSyntheticLambda1(1, this),
                                this.REQUEST_RETRY_DELAY);
                this.mRetryCount++;
            } else {
                SaTokenTask.this.sendResult(-1000, bundle);
                this.mRetryCount = 0;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class LazyHolder {

        @SuppressLint({"StaticFieldLeak"})
        private static final SaTokenTask INSTANCE = new SaTokenTask();
    }

    public static /* synthetic */ void $r8$lambda$r9KO_dVQnuooyqB30BYIJXWQAtw(
            SaTokenTask saTokenTask) {
        saTokenTask.getClass();
        long j = TIMEOUT;
        saTokenTask.mCountDownTimer =
                new CountDownTimer(
                        j,
                        j) { // from class:
                             // com.samsung.android.settings.voiceinput.samsungaccount.SaTokenTask.2
                    @Override // android.os.CountDownTimer
                    public final void onFinish() {
                        SemLog.i(SaTokenTask.TAG, "CountDownTimer, onFinish");
                        SaTokenTask saTokenTask2 = SaTokenTask.this;
                        int i = SaTokenTask.$r8$clinit;
                        saTokenTask2.sendResult(-1000, null);
                    }

                    @Override // android.os.CountDownTimer
                    public final void onTick(long j2) {
                        SemLog.i(SaTokenTask.TAG, "CountDownTimer, onTick");
                    }
                }.start();
    }

    public static int getErrorType(Bundle bundle) {
        if (bundle == null) {
            return -1016;
        }
        if (bundle.getInt("network_detailed_state") == 3) {
            SemLog.i(TAG, "Network blocked");
        }
        String string = bundle.getString("error_code");
        SemLog.i(TAG, "Error code, " + string);
        if (TextUtils.isEmpty(string)) {
            return -1016;
        }
        SemLog.i(TAG, "Error message, " + bundle.getString("error_message"));
        return new SaError(string).getType();
    }

    public final synchronized void execute(
            Context context, SaTokenResultListener saTokenResultListener) {
        List<SaTokenResultListener> list = this.mListeners;
        if (list != null && !list.isEmpty()) {
            SemLog.i(TAG, "Token is already requested");
            if (!this.mListeners.contains(saTokenResultListener)) {
                this.mListeners.add(saTokenResultListener);
            }
            return;
        }
        SemLog.i(TAG, "bindService");
        this.mContext = context;
        ArrayList arrayList = new ArrayList();
        this.mListeners = arrayList;
        arrayList.add(saTokenResultListener);
        Intent intent = new Intent(SaContract.IntentAction.SERVICE_BINDING);
        intent.setClassName("com.osp.app.signin", SaContract.BINDING_CLASS_NAME);
        this.mContext.bindService(intent, this, 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.msc.sa.aidl.ISAService] */
    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        ISAService.Stub.Proxy proxy;
        SemLog.i(TAG, "onServiceConnected");
        synchronized (this) {
            int i = ISAService.Stub.$r8$clinit;
            if (iBinder == null) {
                proxy = null;
            } else {
                IInterface queryLocalInterface =
                        iBinder.queryLocalInterface("com.msc.sa.aidl.ISAService");
                if (queryLocalInterface == null || !(queryLocalInterface instanceof ISAService)) {
                    ISAService.Stub.Proxy proxy2 = new ISAService.Stub.Proxy();
                    proxy2.mRemote = iBinder;
                    proxy = proxy2;
                } else {
                    proxy = (ISAService) queryLocalInterface;
                }
            }
            this.mSaService = proxy;
            if (proxy == null) {
                SemLog.i(TAG, "SAService is null");
                sendResult(-1000, null);
                return;
            }
            try {
                this.mCallbackResult =
                        proxy.registerCallback(
                                SaContract.ExtraValue.CLIENT_ID,
                                null,
                                this.mContext.getPackageName(),
                                this.mSaCallback);
            } catch (RemoteException e) {
                SemLog.e(TAG, "Failed to register callback, " + e);
            }
            if (this.mCallbackResult == null) {
                SemLog.i(TAG, "CallbackResult is null");
                sendResult(-1000, null);
            } else if (SaHelper.isSamsungAccountSigned()) {
                requestToken();
            } else {
                sendResult(-1000, null);
            }
        }
    }

    public final void requestToken() {
        SemLog.i(TAG, "requestToken");
        if (this.mSaService == null) {
            SemLog.i(TAG, "SAService is null");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray(
                SaContract.ExtraKey.ADDITIONAL, new String[] {SaContract.TokenInfo.CC});
        try {
            synchronized (this) {
                ((ISAService.Stub.Proxy) this.mSaService)
                        .requestAccessToken(18, this.mCallbackResult, bundle);
            }
            new Handler(Looper.getMainLooper())
                    .post(new SaTokenTask$$ExternalSyntheticLambda1(0, this));
        } catch (RemoteException e) {
            SemLog.e(TAG, "Failed to register callback, " + e);
            sendResult(-1000, null);
        }
    }

    public final synchronized void sendResult(int i, final Bundle bundle) {
        try {
            SemLog.i(TAG, "sendResult, " + i);
            unbind();
            final ArrayList arrayList = new ArrayList();
            List<SaTokenResultListener> list = this.mListeners;
            if (list != null) {
                arrayList.addAll(list);
            }
            if (i != 1 || bundle == null) {
                final int errorType = getErrorType(bundle);
                Schedulers.IO.scheduleDirect(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.voiceinput.samsungaccount.SaTokenTask$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                List list2 = arrayList;
                                final int i2 = errorType;
                                int i3 = SaTokenTask.$r8$clinit;
                                SemLog.d("SaTokenTask", "request_token RESULT_FAIL Failure");
                                list2.forEach(
                                        new Consumer() { // from class:
                                                         // com.samsung.android.settings.voiceinput.samsungaccount.SaTokenTask$$ExternalSyntheticLambda4
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                int i4 = i2;
                                                SaTokenResultListener saTokenResultListener =
                                                        (SaTokenResultListener) obj;
                                                int i5 = SaTokenTask.$r8$clinit;
                                                try {
                                                    saTokenResultListener.onError(i4);
                                                } catch (Exception e) {
                                                    SemLog.e(
                                                            "SaTokenTask",
                                                            "Exception happens, " + e);
                                                }
                                            }
                                        });
                            }
                        });
            } else {
                Schedulers.IO.scheduleDirect(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.voiceinput.samsungaccount.SaTokenTask$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                Bundle bundle2 = bundle;
                                List list2 = arrayList;
                                int i2 = SaTokenTask.$r8$clinit;
                                SaHelper.setSaInfo(bundle2);
                                SemLog.d("SaTokenTask", "request_token RESULT_OK Success");
                                list2.forEach(new SaTokenTask$$ExternalSyntheticLambda3());
                            }
                        });
            }
            this.mListeners = null;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void unbind() {
        String str;
        try {
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            SemLog.i(TAG, "unregisterCallback");
            ISAService iSAService = this.mSaService;
            if (iSAService != null && (str = this.mCallbackResult) != null) {
                try {
                    ((ISAService.Stub.Proxy) iSAService).unregisterCallback(str);
                } catch (RemoteException e) {
                    SemLog.e(TAG, "Failed to unregister callback, " + e);
                }
            }
            this.mSaService = null;
            this.mCallbackResult = null;
            SemLog.i(TAG, "unbindService");
            Context context = this.mContext;
            if (context != null) {
                context.unbindService(this);
            }
            this.mContext = null;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {}
}
