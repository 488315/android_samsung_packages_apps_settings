package com.samsung.android.settings.asbase.vibration;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibPickerRingtoneType implements IVibPickerType {
    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getAudioUsage() {
        return 6;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final String getCallBackgroundDbName(boolean z) {
        return z ? "use_video_sound_for_ringtone_sim2" : "use_video_sound_for_ringtone";
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final String getDatabaseTableName() {
        return "registerinfo";
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getDefaultSepIndex() {
        return 50035;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getRepeat() {
        return 0;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final String getSepIndexDbName(boolean z) {
        return z ? "ringtone_vibration_sep_index_2" : "ringtone_vibration_sep_index";
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getSoundType(boolean z) {
        return z ? 128 : 1;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getSyncItemTitle() {
        return R.string.sec_vib_picker_sync_with_ringtone;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final String getSyncWithHapticDbName(boolean z) {
        return z ? "sync_vibration_with_ringtone_2" : "sync_vibration_with_ringtone";
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getVibrationUsage() {
        return 33;
    }
}
