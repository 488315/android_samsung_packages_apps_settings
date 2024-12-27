package com.samsung.android.sec_platform_library;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;

import java.util.LinkedList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FactoryPhone {
    public final String HOST_NAME;
    public final Context mContext;
    public Messenger mServiceMessenger = null;
    public final LinkedList mPendingMessage = new LinkedList();
    public final AnonymousClass1 mSecPhoneServiceConnection =
            new ServiceConnection() { // from class:
                                      // com.samsung.android.sec_platform_library.FactoryPhone.1
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    FactoryPhone.this.getClass();
                    AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                            new StringBuilder(),
                            FactoryPhone.this.HOST_NAME,
                            " onServiceConnected()",
                            "F_PHONE");
                    FactoryPhone.this.mServiceMessenger = new Messenger(iBinder);
                    FactoryPhone.this.getClass();
                    Log.i("F_PHONE", "default registerAction()");
                    FactoryPhone.this.sendPendingMessage();
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    FactoryPhone.this.getClass();
                    AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                            new StringBuilder(),
                            FactoryPhone.this.HOST_NAME,
                            " onServiceDisconnected()",
                            "F_PHONE");
                    FactoryPhone.this.mServiceMessenger = null;
                }
            };
    public final AnonymousClass2 mDummyHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.sec_platform_library.FactoryPhone.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    FactoryPhone factoryPhone = FactoryPhone.this;
                    factoryPhone.getClass();
                    Log.i("F_PHONE", factoryPhone.HOST_NAME + " response handler does not exist");
                    if (message.what != 1000) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(factoryPhone.HOST_NAME);
                        sb.append(" dummy handler : ");
                        TooltipPopup$$ExternalSyntheticOutline0.m(sb, message.what, "F_PHONE");
                        return;
                    }
                    Log.i("F_PHONE", factoryPhone.HOST_NAME + " dummy handler : BASE_ID");
                }
            };

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.sec_platform_library.FactoryPhone$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.sec_platform_library.FactoryPhone$2] */
    public FactoryPhone(Context context) {
        this.HOST_NAME = null;
        this.mContext = null;
        this.mContext = context;
        this.HOST_NAME = "[[" + context.getPackageName() + "]]";
        connectToRilService();
    }

    public final void connectToRilService() {
        Log.i("F_PHONE", this.HOST_NAME + " bind SecPhone Service with FactoryPhone");
        UserHandle userHandle = null;
        try {
            userHandle =
                    (UserHandle)
                            Class.forName("android.os.UserHandle")
                                    .getMethod("semOf", Integer.TYPE)
                                    .invoke(null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mContext.semBindServiceAsUser(
                DisplaySettings$$ExternalSyntheticOutline0.m(
                        "com.sec.phone", "com.sec.phone.SecPhoneService"),
                this.mSecPhoneServiceConnection,
                1,
                userHandle);
    }

    public final void disconnectFromRilService() {
        AnonymousClass1 anonymousClass1;
        Log.i("F_PHONE", this.HOST_NAME + "disconnect from Ril service");
        Context context = this.mContext;
        if (context == null
                || (anonymousClass1 = this.mSecPhoneServiceConnection) == null
                || this.mServiceMessenger == null) {
            return;
        }
        context.unbindService(anonymousClass1);
        this.mServiceMessenger = null;
    }

    public final void invokeOemRilRequestRaw(byte[] bArr, Message message) {
        Log.i("F_PHONE", this.HOST_NAME + " invokeOemRilRequestRaw()");
        if (message == null) {
            message = obtainMessage(1000);
        }
        Bundle data = message.getData();
        data.putByteArray("request", bArr);
        message.setData(data);
        message.replyTo = new Messenger(message.getTarget());
        Messenger messenger = this.mServiceMessenger;
        if (messenger != null) {
            try {
                messenger.send(message);
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        Log.i(
                "F_PHONE",
                this.HOST_NAME + " mServiceMessenger is null, add message to pending queue...");
        synchronized (this) {
            Log.i("F_PHONE", this.HOST_NAME + " addMessageToPendingQueue()");
            this.mPendingMessage.offer(message);
            if (this.mServiceMessenger != null) {
                sendPendingMessage();
            }
        }
    }

    public final synchronized void sendPendingMessage() {
        Log.i("F_PHONE", this.HOST_NAME + " sendPendingMessage()");
        while (this.mPendingMessage.peek() != null) {
            try {
                this.mServiceMessenger.send((Message) this.mPendingMessage.poll());
            } catch (RemoteException unused) {
            }
        }
    }
}
