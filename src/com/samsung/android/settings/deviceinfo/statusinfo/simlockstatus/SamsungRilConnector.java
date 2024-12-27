package com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SamsungRilConnector {
    public static SamsungRilConnector sInstance;
    public byte[] mBytesBuffer;
    public Context mContext;
    public boolean mIsConnected;
    public WeakReference mListenerRef;
    public MessageHandler mMessageHandler;
    public String mOperationName;
    public AnonymousClass1 mRilServiceConnection;
    public Messenger mServiceMessenger;
    public Messenger mServiceReplyMessenger;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Log.d("SamsungRilConnector", "message : " + message.what);
            int i = message.getData().getInt("error");
            if (i != 0) {
                Log.d(
                        "SamsungRilConnector",
                        "operation : " + message.what + " failed. error code : " + i);
            }
            byte[] byteArray = message.getData().getByteArray("response");
            SamsungRilConnector samsungRilConnector = SamsungRilConnector.this;
            samsungRilConnector.mBytesBuffer = byteArray;
            if (byteArray == null || byteArray.length == 0) {
                Log.e("SamsungRilConnector", "byte buffer is empty. failed");
            }
            final SimLockStatus simLockStatus =
                    (SimLockStatus) samsungRilConnector.mListenerRef.get();
            int i2 = message.what;
            if (i2 != 0) {
                if (i2 != 1) {
                    return;
                }
                Log.i("SamsungRilConnector", "BLOB_SET_DONE");
                if (simLockStatus != null) {
                    final boolean z = i == 0;
                    Log.i("SimLockStatus", "onSetBlobRequestFinished() => bSucceed: " + z);
                    simLockStatus
                            .getActivity()
                            .runOnUiThread(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SimLockStatus.2
                                        public final /* synthetic */ boolean val$bSucceed;

                                        public AnonymousClass2(final boolean z2) {
                                            r2 = z2;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            if (r2) {
                                                SimLockStatus.this.showDialog(4);
                                            } else {
                                                SimLockStatus.this.showDialog(3);
                                            }
                                        }
                                    });
                    return;
                }
                return;
            }
            Log.i("SamsungRilConnector", "BLOB_GET_DONE");
            byte[] bArr = samsungRilConnector.mBytesBuffer;
            if (bArr == null || bArr.length == 0) {
                Log.i("SamsungRilConnector", "Blob policy file data not received : Failed!");
            } else {
                int i3 = bArr[51] & 255;
                int i4 = bArr[i3 + 77] & 255;
                int i5 = i3 + 76 + i4;
                int i6 = (bArr[i5 + 3] & 255) | ((bArr[i5 + 2] << 8) & 65280);
                byte[] bArr2 = new byte[i6];
                for (int i7 = 0; i7 < i6; i7++) {
                    bArr2[i7] = bArr[i3 + 78 + i4 + i7 + 2];
                }
                samsungRilConnector.mOperationName =
                        new String(new String(bArr2).split("OP_NAME")[1].split("\"")[1]);
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("operation name : "),
                        samsungRilConnector.mOperationName,
                        "SamsungRilConnector");
            }
            if (simLockStatus != null) {
                Log.i("SimLockStatus", "onGetBlobRequestFinished()");
                simLockStatus.mHandler.sendEmptyMessage(1);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SamsungRilConnector$1] */
    public static SamsungRilConnector getInstance(Context context) {
        if (sInstance == null) {
            SamsungRilConnector samsungRilConnector = new SamsungRilConnector();
            samsungRilConnector.mIsConnected = false;
            samsungRilConnector.mOperationName = "-";
            samsungRilConnector.mRilServiceConnection =
                    new ServiceConnection() { // from class:
                                              // com.samsung.android.settings.deviceinfo.statusinfo.simlockstatus.SamsungRilConnector.1
                        @Override // android.content.ServiceConnection
                        public final void onServiceConnected(
                                ComponentName componentName, IBinder iBinder) {
                            Log.d("SamsungRilConnector", "onServiceConnected");
                            SamsungRilConnector.this.mServiceMessenger = new Messenger(iBinder);
                            SamsungRilConnector samsungRilConnector2 = SamsungRilConnector.this;
                            samsungRilConnector2.mIsConnected = true;
                            samsungRilConnector2.getClass();
                            Log.d("SamsungRilConnector", "getBlob()");
                            if (!samsungRilConnector2.mIsConnected) {
                                Log.w("SamsungRilConnector", "not connected to RIL yet");
                                return;
                            }
                            ByteArrayOutputStream byteArrayOutputStream =
                                    new ByteArrayOutputStream();
                            DataOutputStream dataOutputStream =
                                    new DataOutputStream(byteArrayOutputStream);
                            try {
                                dataOutputStream.writeByte(35);
                                dataOutputStream.writeByte(6);
                                dataOutputStream.writeShort(5);
                                dataOutputStream.writeByte(1);
                                samsungRilConnector2.invokeRilService(
                                        byteArrayOutputStream.toByteArray(),
                                        samsungRilConnector2.mMessageHandler.obtainMessage(0));
                                SimLockStatusUtils.closeSilently(
                                        dataOutputStream, byteArrayOutputStream);
                            } catch (IOException unused) {
                                SimLockStatusUtils.closeSilently(
                                        dataOutputStream, byteArrayOutputStream);
                            } catch (Throwable th) {
                                SimLockStatusUtils.closeSilently(
                                        dataOutputStream, byteArrayOutputStream);
                                throw th;
                            }
                        }

                        @Override // android.content.ServiceConnection
                        public final void onServiceDisconnected(ComponentName componentName) {
                            Log.d("SamsungRilConnector", "onServiceDisconnected");
                            SamsungRilConnector samsungRilConnector2 = SamsungRilConnector.this;
                            samsungRilConnector2.mServiceMessenger = null;
                            samsungRilConnector2.mIsConnected = false;
                        }
                    };
            Log.d("SamsungRilConnector", "new()");
            HandlerThread handlerThread = new HandlerThread("RilHandler");
            handlerThread.start();
            MessageHandler messageHandler =
                    samsungRilConnector.new MessageHandler(handlerThread.getLooper());
            samsungRilConnector.mMessageHandler = messageHandler;
            samsungRilConnector.mServiceReplyMessenger = new Messenger(messageHandler);
            sInstance = samsungRilConnector;
        }
        SamsungRilConnector samsungRilConnector2 = sInstance;
        samsungRilConnector2.mContext = context;
        return samsungRilConnector2;
    }

    public final void invokeRilService(byte[] bArr, Message message) {
        Log.d("SamsungRilConnector", "invoke ril service");
        message.getData().putByteArray("request", bArr);
        message.replyTo = this.mServiceReplyMessenger;
        Messenger messenger = this.mServiceMessenger;
        if (messenger == null) {
            Log.e("SamsungRilConnector", "RIL service is not connected");
            return;
        }
        try {
            messenger.send(message);
            Log.d("SamsungRilConnector", "message sent to ril service");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
