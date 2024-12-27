package com.android.settings.notification;

import android.content.Intent;
import android.content.IntentFilter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DockAudioMediaPreferenceController extends SettingPrefController {
    /* renamed from: -$$Nest$misLeDesk, reason: not valid java name */
    public static boolean m978$$Nest$misLeDesk(
            DockAudioMediaPreferenceController dockAudioMediaPreferenceController) {
        dockAudioMediaPreferenceController.getClass();
        Intent registerReceiver =
                dockAudioMediaPreferenceController.mContext.registerReceiver(
                        null, new IntentFilter("android.intent.action.DOCK_EVENT"));
        return registerReceiver != null
                && registerReceiver.getIntExtra("android.intent.extra.DOCK_STATE", -1) == 3;
    }
}
