package com.samsung.android.settings.biometrics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.android.settings.Utils;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class Wallet24Service {
    public ArrayList mAppList;
    public boolean mBound;
    public Messenger mCallbackMessenger;
    public Context mContext;
    public Messenger mMessenger;
    public final AnonymousClass1 mWallet24ServiceConnection;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.content.ServiceConnection, com.samsung.android.settings.biometrics.Wallet24Service$1] */
    public Wallet24Service(Context context) {
        ?? r0 = new ServiceConnection() { // from class: com.samsung.android.settings.biometrics.Wallet24Service.1
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("Biometrics_Wallet24Service", "onServiceConnected");
                Wallet24Service wallet24Service = Wallet24Service.this;
                wallet24Service.mBound = true;
                wallet24Service.mMessenger = new Messenger(iBinder);
                Wallet24Service.this.mCallbackMessenger = new Messenger(new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.settings.biometrics.Wallet24Service.1.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 1 || message.getData() == null) {
                            return;
                        }
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                        Wallet24Service.this.mAppList = message.getData().getStringArrayList("CA_packageName_list");
                        Wallet24Service.this.unbindService();
                    }
                });
                try {
                    Log.i("Biometrics_Wallet24Service", "Send a message to Wallet24.");
                    Message obtain = Message.obtain((Handler) null, 1);
                    Wallet24Service wallet24Service2 = Wallet24Service.this;
                    obtain.replyTo = wallet24Service2.mCallbackMessenger;
                    wallet24Service2.mMessenger.send(obtain);
                } catch (RemoteException unused) {
                    Log.e("Biometrics_Wallet24Service", "Failed to send a message to Wallet24.");
                    Wallet24Service.this.unbindService();
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Log.d("Biometrics_Wallet24Service", "onServiceDisconnected");
                Wallet24Service wallet24Service = Wallet24Service.this;
                wallet24Service.mBound = false;
                wallet24Service.mMessenger = null;
                wallet24Service.mCallbackMessenger = null;
                wallet24Service.mContext = null;
            }
        };
        this.mWallet24ServiceConnection = r0;
        this.mContext = context;
        if (context == null || !Utils.isPackageEnabled(context, "com.samsung.android.ssiframework")) {
            Log.d("Biometrics_Wallet24Service", "There is no Wallet24 package.");
            this.mContext = null;
        } else {
            Log.d("Biometrics_Wallet24Service", "Bind to Wallet24 service.");
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.ssiframework", "com.samsung.android.ssiframework.SsiFrameworkStatusService");
            this.mContext.bindService(intent, (ServiceConnection) r0, 1);
        }
    }

    public final void unbindService() {
        if (this.mContext == null || !this.mBound || this.mWallet24ServiceConnection == null) {
            return;
        }
        Log.d("Biometrics_Wallet24Service", "Unbind Wallet24 service.");
        this.mContext.unbindService(this.mWallet24ServiceConnection);
        this.mBound = false;
        this.mContext = null;
    }
}
