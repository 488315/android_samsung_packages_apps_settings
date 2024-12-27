package com.samsung.android.settings.eternal.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import com.samsung.android.lib.episode.EpisodeUtils;
import com.samsung.android.settings.eternal.log.EternalFileLog;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EternalRemoteService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Messenger mMessenger = new Messenger(new EternalHandler(this));

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EternalHandler extends Handler {
        public final WeakReference mService;

        public EternalHandler(EternalRemoteService eternalRemoteService) {
            this.mService = new WeakReference(eternalRemoteService);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            EternalRemoteService eternalRemoteService = (EternalRemoteService) this.mService.get();
            if (eternalRemoteService != null) {
                int i = EternalRemoteService.$r8$clinit;
                if (message == null) {
                    EternalFileLog.e(
                            "Eternal/EternalRemoteService", "handleMessage() - msg is null");
                    return;
                }
                EpisodeUtils.isSettingAppSupportBnR();
                EternalFileLog.d(
                        "Eternal/EternalRemoteService", "handleMessage() - " + message.what);
                if (message.what != 12288) {
                    EternalFileLog.e(
                            "Eternal/EternalRemoteService",
                            "handleMessage() - Unsupported cmd : " + message.what);
                    return;
                }
                Bundle data = message.getData();
                if (data == null) {
                    EternalFileLog.e(
                            "Eternal/EternalRemoteService", "handleMessage() - bundle is null");
                    return;
                }
                String string = data.getString("SAVE_PATH");
                String string2 = data.getString("SESSION_KEY");
                int i2 = data.getInt("SECURITY_LEVEL", 0);
                Intent intent =
                        new Intent("com.samsung.android.intent.action.REQUEST_RESTORE_SETTINGS");
                intent.putExtra("SAVE_PATH", string);
                intent.putExtra("SESSION_KEY", string2);
                intent.putExtra("FAST_TRACK", true);
                intent.putExtra("SECURITY_LEVEL", i2);
                intent.putExtra("FROM_REMOTE_SERVICE", true);
                intent.putStringArrayListExtra(
                        "RESTORE_RESTRICTED_LIST",
                        data.getStringArrayList("RESTORE_RESTRICTED_LIST"));
                intent.setComponent(
                        new ComponentName(
                                eternalRemoteService.getApplicationContext(),
                                (Class<?>) EternalService.class));
                eternalRemoteService.getApplicationContext().startService(intent);
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }
}
