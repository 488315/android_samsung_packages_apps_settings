package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.hardware.input.InputManager;
import android.hardware.input.InputSettings;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecPointerSpeedPreference extends CustomSeekbarPreference {
    public final InputManager mIm;
    public SeslSeekBar mSeekBar;
    public boolean mTouchInProgress;

    public SecPointerSpeedPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIm = (InputManager) getContext().getSystemService("input");
    }

    @Override // com.samsung.android.settings.inputmethod.CustomSeekbarPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) preferenceViewHolder.findViewById(R.id.custom_seekbar);
        if (seslSeekBar == this.mSeekBar) {
            this.mSeekBar.setProgress(InputSettings.getPointerSpeed(getContext()) + 7);
            return;
        }
        this.mSeekBar = seslSeekBar;
        InputSettings.getPointerSpeed(getContext());
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
        int i2 = i - 7;
        this.mIm.tryPointerSpeed(i2);
        InputSettings.setPointerSpeed(getContext(), i2);
        Log.d("SecPointerSpeedPreference", "PointerSpeed change not touched");
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
        this.mIm.tryPointerSpeed(seslSeekBar.getProgress() - 7);
        InputSettings.setPointerSpeed(getContext(), this.mSeekBar.getProgress() - 7);
    }
}
