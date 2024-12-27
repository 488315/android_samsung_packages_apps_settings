package com.samsung.android.settings.eternal.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.eternal.scpm.ScpmUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EternalReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(UserHandle.semGetMyUserId());
        sb.append("] onReceive() action : ");
        sb.append(intent != null ? intent.getAction() : " null");
        EternalFileLog.d("Eternal/EternalReceiver", sb.toString());
        if (intent == null) {
            return;
        }
        ScpmUtils.getInstance(context).registerScpm();
        intent.setComponent(new ComponentName(context, EternalService.class.getCanonicalName()));
        context.startService(intent);
    }
}
