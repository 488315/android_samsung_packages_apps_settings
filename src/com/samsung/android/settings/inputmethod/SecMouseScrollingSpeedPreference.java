package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecMouseScrollingSpeedPreference extends CustomSeekbarPreference {
    public SeslSeekBar mSeekBar;
    public boolean mTouchInProgress;

    public SecMouseScrollingSpeedPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.samsung.android.settings.inputmethod.CustomSeekbarPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) preferenceViewHolder.findViewById(R.id.custom_seekbar);
        if (seslSeekBar == this.mSeekBar) {
            this.mSeekBar.setProgress(
                    Settings.System.getInt(
                                    getContext().getContentResolver(), "mouse_scrolling_speed", 1)
                            - 1);
            return;
        }
        this.mSeekBar = seslSeekBar;
        Settings.System.getInt(getContext().getContentResolver(), "mouse_scrolling_speed", 1);
        this.mSeekBar.setOnSeekBarChangeListener(this);
        this.mSeekBar.setSeamless(true);
        this.mSeekBar.setMode(5);
    }

    @Override // com.samsung.android.settings.inputmethod.CustomSeekbarPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        if (this.mTouchInProgress || !z) {
            return;
        }
        Context context = getContext();
        int i2 = i + 1;
        if (i2 < 1 || i2 > 100) {
            throw new IllegalArgumentException("scrolling speed out of range");
        }
        Settings.System.putInt(context.getContentResolver(), "mouse_scrolling_speed", i2);
        Log.d("SecMouseScrollingSpeedPreference", "Scrolling speed change not touched");
    }

    @Override // com.samsung.android.settings.inputmethod.CustomSeekbarPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
        this.mTouchInProgress = true;
    }

    @Override // com.samsung.android.settings.inputmethod.CustomSeekbarPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        this.mTouchInProgress = false;
        Context context = getContext();
        int progress = this.mSeekBar.getProgress() + 1;
        if (progress < 1 || progress > 100) {
            throw new IllegalArgumentException("scrolling speed out of range");
        }
        Settings.System.putInt(context.getContentResolver(), "mouse_scrolling_speed", progress);
    }
}
