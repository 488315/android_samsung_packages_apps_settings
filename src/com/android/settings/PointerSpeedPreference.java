package com.android.settings;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.input.InputManager;
import android.hardware.input.InputSettings;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import androidx.preference.Preference;

import com.android.internal.jank.InteractionJankMonitor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class PointerSpeedPreference extends SeekBarDialogPreference
        implements SeekBar.OnSeekBarChangeListener {
    public final InputManager mIm;
    public final InteractionJankMonitor mJankMonitor;
    public int mLastProgress;
    public int mOldSpeed;
    public boolean mRestoredOldState;
    public SeekBar mSeekBar;
    public final AnonymousClass1 mSpeedObserver;
    public boolean mTouchInProgress;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.settings.PointerSpeedPreference$1] */
    public PointerSpeedPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        this.mLastProgress = -1;
        this.mSpeedObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.android.settings.PointerSpeedPreference.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        PointerSpeedPreference pointerSpeedPreference = PointerSpeedPreference.this;
                        pointerSpeedPreference.mSeekBar.setProgress(
                                InputSettings.getPointerSpeed(pointerSpeedPreference.getContext())
                                        + 7);
                    }
                };
        this.mIm = (InputManager) getContext().getSystemService("input");
    }

    @Override // com.android.settings.SeekBarDialogPreference,
              // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onBindDialogView(View view) {
        super.onBindDialogView(view);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        this.mSeekBar = seekBar;
        seekBar.setMax(14);
        int pointerSpeed = InputSettings.getPointerSpeed(getContext());
        this.mOldSpeed = pointerSpeed;
        this.mSeekBar.setProgress(pointerSpeed + 7);
        this.mSeekBar.setOnSeekBarChangeListener(this);
        this.mSeekBar.setContentDescription(getTitle());
    }

    @Override // androidx.preference.DialogPreference, androidx.preference.Preference
    public final void onClick() {
        super.onClick();
        getContext()
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("pointer_speed"), true, this.mSpeedObserver);
        this.mRestoredOldState = false;
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public final void onDialogClosed(boolean z) {
        ContentResolver contentResolver = getContext().getContentResolver();
        if (z) {
            InputSettings.setPointerSpeed(getContext(), this.mSeekBar.getProgress() - 7);
        } else if (!this.mRestoredOldState) {
            this.mIm.tryPointerSpeed(this.mOldSpeed);
            this.mRestoredOldState = true;
        }
        contentResolver.unregisterContentObserver(this.mSpeedObserver);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (!this.mTouchInProgress) {
            this.mIm.tryPointerSpeed(i - 7);
        }
        if (i != this.mLastProgress) {
            seekBar.performHapticFeedback(4);
            this.mLastProgress = i;
        }
    }

    @Override // androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mOldSpeed = savedState.oldSpeed;
        this.mIm.tryPointerSpeed(savedState.progress - 7);
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (getDialog() == null || !getDialog().isShowing()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.progress = this.mSeekBar.getProgress();
        int i = this.mOldSpeed;
        savedState.oldSpeed = i;
        if (!this.mRestoredOldState) {
            this.mIm.tryPointerSpeed(i);
            this.mRestoredOldState = true;
        }
        return savedState;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
        this.mTouchInProgress = true;
        this.mJankMonitor.begin(
                InteractionJankMonitor.Configuration.Builder.withView(53, seekBar)
                        .setTag(getKey()));
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
        this.mTouchInProgress = false;
        this.mIm.tryPointerSpeed(seekBar.getProgress() - 7);
        this.mJankMonitor.end(53);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public int oldSpeed;
        public int progress;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.PointerSpeedPreference$SavedState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.oldSpeed = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.oldSpeed);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }
}
