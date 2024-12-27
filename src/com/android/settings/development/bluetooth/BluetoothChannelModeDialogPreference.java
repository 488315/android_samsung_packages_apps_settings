package com.android.settings.development.bluetooth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothChannelModeDialogPreference extends BaseBluetoothDialogPreference
        implements RadioGroup.OnCheckedChangeListener {
    public BluetoothChannelModeDialogPreference(Context context) {
        super(context);
        initialize$1(context);
    }

    @Override // com.android.settings.development.bluetooth.BaseBluetoothDialogPreference
    public final int getRadioButtonGroupId() {
        return R.id.bluetooth_audio_channel_mode_radio_group;
    }

    public final void initialize$1(Context context) {
        this.mRadioButtonIds.add(Integer.valueOf(R.id.bluetooth_audio_channel_mode_default));
        this.mRadioButtonIds.add(Integer.valueOf(R.id.bluetooth_audio_channel_mode_mono));
        this.mRadioButtonIds.add(Integer.valueOf(R.id.bluetooth_audio_channel_mode_stereo));
        for (String str :
                context.getResources()
                        .getStringArray(R.array.bluetooth_a2dp_codec_channel_mode_titles)) {
            this.mRadioButtonStrings.add(str);
        }
        for (String str2 :
                context.getResources()
                        .getStringArray(R.array.bluetooth_a2dp_codec_channel_mode_summaries)) {
            this.mSummaryStrings.add(str2);
        }
    }

    public BluetoothChannelModeDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize$1(context);
    }

    public BluetoothChannelModeDialogPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize$1(context);
    }

    public BluetoothChannelModeDialogPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initialize$1(context);
    }
}
