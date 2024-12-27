package com.android.settings.notification;

import android.content.Context;
import android.media.AudioManager;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.settings.Utils;

import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioHelper {
    public final AudioManager mAudioManager;
    public final Context mContext;

    public AudioHelper(Context context) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    public static int getManagedProfileId(UserManager userManager) {
        return Utils.getManagedProfileId(userManager, UserHandle.myUserId());
    }
}
