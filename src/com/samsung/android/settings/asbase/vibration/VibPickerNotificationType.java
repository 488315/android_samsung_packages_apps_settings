package com.samsung.android.settings.asbase.vibration;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibPickerNotificationType implements IVibPickerType {
    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getAudioUsage() {
        return 5;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final String getCallBackgroundDbName(boolean z) {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final String getDatabaseTableName() {
        return "notification";
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getDefaultSepIndex() {
        return 50034;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getRepeat() {
        return -1;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final String getSepIndexDbName(boolean z) {
        return "notification_vibration_sep_index";
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getSoundType(boolean z) {
        return z ? 256 : 2;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getSyncItemTitle() {
        return R.string.sec_vib_picker_sync_with_notification;
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final String getSyncWithHapticDbName(boolean z) {
        return "sync_vibration_with_notification";
    }

    @Override // com.samsung.android.settings.asbase.vibration.IVibPickerType
    public final int getVibrationUsage() {
        return 49;
    }
}
