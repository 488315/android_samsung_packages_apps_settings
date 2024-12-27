package com.samsung.android.settings.bixby.control.commands;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.samsung.android.sdk.command.Command;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.samsung.android.settings.bixby.utils.BixbyUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LaunchCommand extends BaseCommand {
    @Override // com.samsung.android.settings.bixby.control.commands.BaseCommand
    public Bundle executeInternal(String str) {
        PendingIntent pendingIntent;
        BaseActionParam baseActionParam = this.mActionParam;
        Bundle call =
                call(
                        str,
                        "method_LOAD",
                        makeExtra(
                                "method_LOAD",
                                "com.android.settings.command".equalsIgnoreCase(str)));
        Bundle bundle = (Bundle) call.getParcelable("command");
        Command command = bundle == null ? null : new Command(bundle);
        if (command != null && (pendingIntent = command.mLaunchIntent) != null) {
            try {
                if (BixbyUtils.isLargeSubDisplayScreen()) {
                    Bundle bundle2 = baseActionParam.mExtra;
                    boolean z = false;
                    if (bundle2 != null) {
                        z = bundle2.getBoolean("isFolded", false);
                    }
                    if (z) {
                        Context context = baseActionParam.mContext;
                        Intent intent = new Intent();
                        intent.putExtra("showCoverToast", true);
                        intent.putExtra("ignoreKeyguardState", true);
                        ((KeyguardManager) context.getSystemService("keyguard"))
                                .semSetPendingIntentAfterUnlock(pendingIntent, intent);
                        return buildResult(call);
                    }
                }
                pendingIntent.send();
                return buildResult(call);
            } catch (Exception e) {
                Log.d("LaunchCommand", e.getMessage());
            }
        }
        return BixbyUtils.buildActionResult("fail");
    }
}
