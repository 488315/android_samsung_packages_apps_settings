package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecMousePointerSizePreference extends CustomSeekbarPreference {
    public int mLastProgress;
    public SeslSeekBar mSeekBar;
    public boolean mTouchInProgress;

    public SecMousePointerSizePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static void setMousePointerSize(Context context, int i) {
        if (i < 1 || i > 9) {
            throw new IllegalArgumentException("out of range");
        }
        Settings.System.putInt(context.getContentResolver(), "mouse_pointer_size_step", i);
        LoggingHelper.insertEventLogging(770105, 77018, i);
    }

    @Override // com.samsung.android.settings.inputmethod.CustomSeekbarPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) preferenceViewHolder.findViewById(R.id.custom_seekbar);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.left_text);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.right_text);
        textView.setText(R.string.sec_small);
        textView2.setText(R.string.sec_large);
        if (seslSeekBar == this.mSeekBar) {
            this.mSeekBar.setProgress(
                    Settings.System.getInt(
                                    getContext().getContentResolver(), "mouse_pointer_size_step", 1)
                            - 1);
        } else {
            this.mSeekBar = seslSeekBar;
            Settings.System.getInt(getContext().getContentResolver(), "mouse_pointer_size_step", 1);
            this.mSeekBar.setOnSeekBarChangeListener(this);
            this.mSeekBar.setMode(8);
        }
    }

    @Override // com.samsung.android.settings.inputmethod.CustomSeekbarPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        if (!this.mTouchInProgress && z) {
            setMousePointerSize(getContext(), i + 1);
        }
        if (i != this.mLastProgress) {
            seslSeekBar.performHapticFeedback(4);
            this.mLastProgress = i;
        }
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
        setMousePointerSize(getContext(), this.mSeekBar.getProgress() + 1);
    }
}
