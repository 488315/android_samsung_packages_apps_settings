package com.android.settings.development.bluetooth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothQualityDialogPreference extends BaseBluetoothDialogPreference
        implements RadioGroup.OnCheckedChangeListener {
    public BluetoothQualityDialogPreference(Context context) {
        super(context);
        initialize$3(context);
    }

    @Override // com.android.settings.development.bluetooth.BaseBluetoothDialogPreference
    public final int getDefaultIndex() {
        return 3;
    }

    @Override // com.android.settings.development.bluetooth.BaseBluetoothDialogPreference
    public final int getRadioButtonGroupId() {
        return R.id.bluetooth_audio_quality_radio_group;
    }

    public final void initialize$3(Context context) {
        this.mRadioButtonIds.add(Integer.valueOf(R.id.bluetooth_audio_quality_default));
        this.mRadioButtonIds.add(Integer.valueOf(R.id.bluetooth_audio_quality_optimized_quality));
        this.mRadioButtonIds.add(
                Integer.valueOf(R.id.bluetooth_audio_quality_optimized_connection));
        this.mRadioButtonIds.add(Integer.valueOf(R.id.bluetooth_audio_quality_best_effort));
        for (String str :
                context.getResources()
                        .getStringArray(
                                R.array.bluetooth_a2dp_codec_ldac_playback_quality_titles)) {
            this.mRadioButtonStrings.add(str);
        }
        for (String str2 :
                context.getResources()
                        .getStringArray(
                                R.array.bluetooth_a2dp_codec_ldac_playback_quality_summaries)) {
            this.mSummaryStrings.add(str2);
        }
    }

    public BluetoothQualityDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize$3(context);
    }

    public BluetoothQualityDialogPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize$3(context);
    }

    public BluetoothQualityDialogPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initialize$3(context);
    }
}
