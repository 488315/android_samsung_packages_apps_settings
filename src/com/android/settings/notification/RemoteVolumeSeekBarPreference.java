package com.android.settings.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RemoteVolumeSeekBarPreference extends VolumeSeekBarPreference {
    public RemoteVolumeSeekBarPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreference
    public final void init() {
        if (((VolumeSeekBarPreference) this).mSeekBar == null) {
            return;
        }
        this.mContinuousUpdates = true;
        updateIconView();
        updateSuppressionText();
        notifyHierarchyChanged();
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        super.onProgressChanged(seekBar, i, z);
        if (z) {
            notifyChanged();
        }
    }

    public RemoteVolumeSeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public RemoteVolumeSeekBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreference
    public final void setStream(int i) {}
}
