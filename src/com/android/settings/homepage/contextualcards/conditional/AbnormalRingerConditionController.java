package com.android.settings.homepage.contextualcards.conditional;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbnormalRingerConditionController implements ConditionalCardController {
    public static final IntentFilter FILTER =
            new IntentFilter("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
    public final Context mAppContext;
    public final AudioManager mAudioManager;
    public final ConditionManager mConditionManager;
    public final RingerModeChangeReceiver mReceiver = new RingerModeChangeReceiver();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RingerModeChangeReceiver extends BroadcastReceiver {
        public RingerModeChangeReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(intent.getAction())) {
                AbnormalRingerConditionController.this.mConditionManager.onConditionChanged();
            }
        }
    }

    public AbnormalRingerConditionController(Context context, ConditionManager conditionManager) {
        this.mAppContext = context;
        this.mConditionManager = conditionManager;
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onActionClick() {
        this.mAudioManager.setRingerModeInternal(2);
        this.mAudioManager.setStreamVolume(2, 1, 0);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void onPrimaryClick(Context context) {
        context.startActivity(
                new Intent("android.settings.SOUND_SETTINGS").setPackage(context.getPackageName()));
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void startMonitoringStateChange() {
        this.mAppContext.registerReceiver(this.mReceiver, FILTER);
    }

    @Override // com.android.settings.homepage.contextualcards.conditional.ConditionalCardController
    public final void stopMonitoringStateChange() {
        this.mAppContext.unregisterReceiver(this.mReceiver);
    }
}
