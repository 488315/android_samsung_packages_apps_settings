package com.samsung.android.settings.bixby.target;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.bixby.utils.BixbyUtils;
import com.samsung.android.util.SemLog;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SoundAction extends Action {
    public AudioManager mAudioManager;

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        int currentRingerMode = getCurrentRingerMode();
        String str =
                currentRingerMode != 0
                        ? currentRingerMode != 1
                                ? currentRingerMode != 2 ? null : "ringer_mode_sound"
                                : "ringer_mode_vibrate"
                        : "ringer_mode_silent";
        SemLog.d("SoundAction", "sound mode : " + str);
        Bundle bundle = new Bundle();
        bundle.putString("result", str);
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        Intent intent = new Intent("com.android.settings.SOUND_SETTINGS");
        intent.addFlags(268468224);
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        Utils.setTaskIdToIntent(intent, getTaskId());
        launchSettings(
                intent,
                Utils.isDesktopModeEnabled(this.mContext)
                        ? BixbyUtils.getDeXDisplay(this.mContext)
                        : null);
        return Action.createResult("success");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        int i;
        int currentRingerMode;
        i = 2;
        currentRingerMode = getCurrentRingerMode();
        String value = getValue();
        value.getClass();
        switch (value) {
            case "ringer_mode_silent":
                i = 0;
                break;
            case "ringer_mode_vibrate":
                i = 1;
                break;
            case "ringer_mode_sound":
                break;
            default:
                i = currentRingerMode;
                break;
        }
        if (i == 1 && !VibUtils.hasVibrator(this.mContext)) {
            return Action.createResult("not_supported_device");
        }
        Context context = this.mContext;
        ((AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO))
                .setRingerModeInternal(i);
        if (i != 0) {
            Settings.Global.putInt(context.getContentResolver(), "mode_ringer_time_on", 0);
        }
        return Action.createResult("success");
    }

    public final int getCurrentRingerMode() {
        if (this.mAudioManager == null) {
            this.mAudioManager =
                    (AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        }
        try {
            return this.mAudioManager.semGetRingerModeInternal();
        } catch (RuntimeException e) {
            SemLog.w("SoundAction", "fail to get real ringer mode : " + e.getMessage());
            return 2;
        }
    }
}
