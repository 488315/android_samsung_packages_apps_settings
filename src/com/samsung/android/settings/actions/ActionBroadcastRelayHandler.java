package com.samsung.android.settings.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionBroadcastRelayHandler {
    public static ActionBroadcastRelayHandler sInstance;
    public final ArrayMap mRelays = new ArrayMap();
    public AnonymousClass2 mHandler = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.actions.ActionBroadcastRelayHandler$2, reason: invalid class name */
    public final class AnonymousClass2 extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 0) {
                return;
            }
            Object obj = message.obj;
            if (obj instanceof Runnable) {
                ((Runnable) obj).run();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BroadcastRelay extends BroadcastReceiver {
        public static final String TAG = BroadcastRelay.class.getSimpleName();
        public final Uri mUri;

        public BroadcastRelay(Uri uri) {
            this.mUri = uri;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            SemLog.d(TAG, "on receive called : " + intent.getAction());
            context.getContentResolver().notifyChange(this.mUri, null);
        }
    }
}
