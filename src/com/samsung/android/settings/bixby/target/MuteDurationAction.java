package com.samsung.android.settings.bixby.target;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.util.SemLog;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MuteDurationAction extends Action {
    public final AudioManager mAudioManager;

    public MuteDurationAction(Context context, Bundle bundle) {
        super(context, bundle);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        String str;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "mode_ringer_time_on", 0)
                != 0) {
            try {
                str = String.valueOf(this.mAudioManager.getMuteInterval());
            } catch (Exception e) {
                SemLog.e("MuteDurationAction", "Fail to set mute duration : " + e);
                str = "fail";
            }
        } else {
            str = "temporary_mute_state_off";
        }
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        String str;
        boolean z =
                Settings.Global.getInt(this.mContext.getContentResolver(), "mode_ringer_time_on", 0)
                        != 0;
        String value = getValue();
        if (z) {
            try {
                if (TextUtils.equals(String.valueOf(this.mAudioManager.getMuteInterval()), value)) {
                    str = "already_set";
                } else {
                    this.mAudioManager.setMuteInterval(Integer.valueOf(value).intValue());
                    str = "success";
                }
            } catch (Exception e) {
                SemLog.e("MuteDurationAction", "Fail to set mute duration : " + e);
                str = "fail";
            }
        } else {
            str = "temporary_mute_state_off";
        }
        return Action.createResult(str);
    }
}
