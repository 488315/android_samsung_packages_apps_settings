package com.samsung.android.settings.biometrics.face;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FaceReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("FcstFaceReceiver", "onReceive : " + action.toString());
        if ("com.samsung.android.bio.face.intent.action.FACE_RESET".equals(action)) {
            int currentUser = ActivityManager.getCurrentUser();
            if (SecurityUtils.isEnrolledFace(context, currentUser)) {
                Log.w("FcstFaceReceiver", "Face data exist! Can't remove Face lock!");
            } else {
                FaceSettingsHelper.removeFaceLock(
                        currentUser, context, new LockPatternUtils(context));
            }
        }
    }
}
