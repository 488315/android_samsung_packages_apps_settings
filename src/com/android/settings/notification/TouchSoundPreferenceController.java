package com.android.settings.notification;

import android.content.Context;
import android.media.AudioManager;
import android.os.AsyncTask;

import com.android.settings.R;

import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TouchSoundPreferenceController extends SettingPrefController {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.TouchSoundPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends SettingPref {
        @Override // com.android.settings.notification.SettingPref
        public final boolean setSetting(final Context context, final int i) {
            AsyncTask.execute(
                    new Runnable() { // from class:
                                     // com.android.settings.notification.TouchSoundPreferenceController.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AudioManager audioManager =
                                    (AudioManager)
                                            context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                            if (i != 0) {
                                audioManager.loadSoundEffects();
                            } else {
                                audioManager.unloadSoundEffects();
                            }
                        }
                    });
            return super.setSetting(context, i);
        }
    }

    @Override // com.android.settings.notification.SettingPrefController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_touch_sounds);
    }
}
