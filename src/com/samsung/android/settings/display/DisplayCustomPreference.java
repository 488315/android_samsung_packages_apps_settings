package com.samsung.android.settings.display;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DisplayCustomPreference extends Preference
        implements SeslSeekBar.OnSeekBarChangeListener, View.OnKeyListener {
    public int mMax;
    public int mProgress;
    public final int mSeekBarId;
    public boolean mTrackingTouch;
    public SeslSeekBar seekBar;

    public DisplayCustomPreference(
            Context context, AttributeSet attributeSet, int i, int i2, int i3) {
        super(context, attributeSet, i, 0);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.ProgressBar, i, 0);
        int i4 = obtainStyledAttributes.getInt(2, this.mMax);
        if (i4 != this.mMax) {
            this.mMax = i4;
            notifyChanged();
        }
        obtainStyledAttributes.recycle();
        this.mSeekBarId = i3;
        setLayoutResource(i2);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        SeslSeekBar seslSeekBar = (SeslSeekBar) preferenceViewHolder.findViewById(this.mSeekBarId);
        this.seekBar = seslSeekBar;
        if (seslSeekBar != null) {
            seslSeekBar.setOnSeekBarChangeListener(this);
            this.seekBar.setEnabled(isEnabled());
            preferenceViewHolder.itemView.setOnKeyListener(this);
            this.seekBar.setOnKeyListener(this);
        }
    }

    @Override // androidx.preference.Preference
    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        SeslSeekBar seslSeekBar;
        if (keyEvent.getAction() != 1) {
            return false;
        }
        if ((i == 21 || i == 22) && (seslSeekBar = this.seekBar) != null) {
            return seslSeekBar.onKeyDown(i, keyEvent);
        }
        return false;
    }

    public void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        int progress;
        if (!z || this.mTrackingTouch || (progress = seslSeekBar.getProgress()) == this.mProgress) {
            return;
        }
        if (callChangeListener(Integer.valueOf(progress))) {
            setProgress$2(progress, false);
        } else {
            seslSeekBar.setProgress(this.mProgress);
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
        this.mProgress = savedState.progress;
        this.mMax = savedState.max;
        notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.progress = this.mProgress;
        savedState.max = this.mMax;
        return savedState;
    }

    @Override // androidx.preference.Preference
    public final void onSetInitialValue(Object obj, boolean z) {
        setProgress(z ? getPersistedInt(this.mProgress) : ((Integer) obj).intValue());
    }

    public void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
        this.mTrackingTouch = true;
    }

    public void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        int progress;
        this.mTrackingTouch = false;
        if (seslSeekBar.getProgress() == this.mProgress
                || (progress = seslSeekBar.getProgress()) == this.mProgress) {
            return;
        }
        if (callChangeListener(Integer.valueOf(progress))) {
            setProgress$2(progress, false);
        } else {
            seslSeekBar.setProgress(this.mProgress);
        }
    }

    public void setProgress(int i) {
        setProgress$2(i, true);
    }

    public final void setProgress$2(int i, boolean z) {
        int i2 = this.mMax;
        if (i > i2) {
            i = i2;
        }
        if (i < 0) {
            i = 0;
        }
        if (i != this.mProgress) {
            this.mProgress = i;
            persistInt(i);
            if (z) {
                notifyChanged();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public int max;
        public int progress;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.display.DisplayCustomPreference$SavedState$1, reason: invalid class name */
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
            this.max = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.max);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public DisplayCustomPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 17957213, 0, 0);
    }
}
