package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.R;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settings.R$styleable;
import com.android.settings.Utils;
import com.android.settings.fuelgauge.batterysaver.BatterySaverScheduleSeekBarController;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SeekBarPreference extends RestrictedPreference
        implements SeekBar.OnSeekBarChangeListener, View.OnKeyListener, View.OnHoverListener {
    public boolean mContinuousUpdates;
    public final int mDefaultProgress;
    public int mHapticFeedbackMode;
    public final boolean mIsTalkBackEnabled;
    public final InteractionJankMonitor mJankMonitor;
    public int mMax;
    public int mMin;
    public SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;
    public CharSequence mOverrideSeekBarStateDescription;
    public int mProgress;
    public final int mProgressBarMode;
    public SeekBar mSeekBar;
    public CharSequence mSeekBarContentDescription;
    public CharSequence mSeekBarStateDescription;
    public boolean mTrackingTouch;

    public SeekBarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        this.mHapticFeedbackMode = 0;
        this.mDefaultProgress = -1;
        this.mIsTalkBackEnabled = false;
        this.mProgressBarMode = -1;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.ProgressBar, i, i2);
        setMax(obtainStyledAttributes.getInt(2, this.mMax));
        setMin(obtainStyledAttributes.getInt(26, this.mMin));
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 =
                context.obtainStyledAttributes(attributeSet, R.styleable.SeekBarPreference, i, i2);
        int resourceId =
                obtainStyledAttributes2.getResourceId(
                        0, android.R.layout.screen_simple_overlay_action_mode);
        obtainStyledAttributes2.recycle();
        setSelectable(false);
        TypedArray obtainStyledAttributes3 =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.SecSeekBarPreference, i, i2);
        this.mProgressBarMode = obtainStyledAttributes3.getInt(0, -1);
        obtainStyledAttributes3.recycle();
        setLayoutResource(resourceId);
        this.mIsTalkBackEnabled = Utils.isTalkBackEnabled(context);
    }

    @Override // androidx.preference.Preference
    public final boolean isSelectable() {
        if (this.mHelper.mDisabledByAdmin) {
            return true;
        }
        return super.isSelectable();
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setOnKeyListener(this);
        preferenceViewHolder.itemView.setOnHoverListener(this);
        SeekBar seekBar = (SeekBar) preferenceViewHolder.findViewById(android.R.id.textClassifier);
        this.mSeekBar = seekBar;
        int i = this.mProgressBarMode;
        if (i >= 0) {
            seekBar.semSetMode(i);
        }
        this.mSeekBar.setOnSeekBarChangeListener(this);
        this.mSeekBar.setMax(this.mMax);
        this.mSeekBar.setMin(this.mMin);
        this.mSeekBar.setProgress(this.mProgress);
        this.mSeekBar.setEnabled(isEnabled());
        CharSequence title = getTitle();
        if (!TextUtils.isEmpty(this.mSeekBarContentDescription)) {
            this.mSeekBar.setContentDescription(this.mSeekBarContentDescription);
        } else if (!TextUtils.isEmpty(title)) {
            this.mSeekBar.setContentDescription(title);
        }
        if (!TextUtils.isEmpty(this.mSeekBarStateDescription)) {
            this.mSeekBar.setStateDescription(this.mSeekBarStateDescription);
        }
        SeekBar seekBar2 = this.mSeekBar;
        if (seekBar2 instanceof DefaultIndicatorSeekBar) {
            ((DefaultIndicatorSeekBar) seekBar2).setDefaultProgress(this.mDefaultProgress);
        }
        this.mSeekBar.setAccessibilityDelegate(
                new View
                        .AccessibilityDelegate() { // from class:
                                                   // com.android.settings.widget.SeekBarPreference.1
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        AccessibilityNodeInfo.RangeInfo rangeInfo =
                                accessibilityNodeInfo.getRangeInfo();
                        if (rangeInfo != null) {
                            SeekBarPreference.this.getClass();
                            accessibilityNodeInfo.setRangeInfo(
                                    AccessibilityNodeInfo.RangeInfo.obtain(
                                            0,
                                            rangeInfo.getMin(),
                                            rangeInfo.getMax(),
                                            rangeInfo.getCurrent()));
                        }
                        CharSequence charSequence =
                                SeekBarPreference.this.mOverrideSeekBarStateDescription;
                        if (charSequence != null) {
                            accessibilityNodeInfo.setStateDescription(charSequence);
                        }
                    }
                });
    }

    @Override // androidx.preference.Preference
    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 9) {
            view.setHovered(true);
        } else if (action == 10) {
            view.setHovered(false);
        }
        return false;
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        SeekBar seekBar;
        if (keyEvent.getAction() == 0
                && (seekBar = (SeekBar) view.findViewById(android.R.id.textClassifier)) != null) {
            return seekBar.onKeyDown(i, keyEvent);
        }
        return false;
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z && (this.mContinuousUpdates || !this.mTrackingTouch)) {
            syncProgress(seekBar);
        }
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(seekBar, i, z);
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
        this.mMin = savedState.min;
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
        savedState.min = this.mMin;
        return savedState;
    }

    @Override // androidx.preference.Preference
    public final void onSetInitialValue(Object obj, boolean z) {
        setProgress(z ? getPersistedInt(this.mProgress) : ((Integer) obj).intValue());
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mTrackingTouch = true;
        this.mJankMonitor.begin(
                InteractionJankMonitor.Configuration.Builder.withView(53, seekBar)
                        .setTag(getKey()));
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch(seekBar);
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        this.mTrackingTouch = false;
        if (seekBar.getProgress() != this.mProgress) {
            syncProgress(seekBar);
        }
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStopTrackingTouch(seekBar);
        }
        this.mJankMonitor.end(53);
    }

    public final void setMax(int i) {
        if (i != this.mMax) {
            this.mMax = i;
            notifyChanged();
        }
    }

    public final void setMin(int i) {
        if (i != this.mMin) {
            this.mMin = i;
            notifyChanged();
        }
    }

    public void setOnSeekBarChangeListener(
            BatterySaverScheduleSeekBarController batterySaverScheduleSeekBarController) {
        this.mOnSeekBarChangeListener = batterySaverScheduleSeekBarController;
    }

    public void setProgress(int i) {
        setProgress(i, true);
    }

    public final void setSeekBarContentDescription(CharSequence charSequence) {
        if (this.mIsTalkBackEnabled) {
            return;
        }
        this.mSeekBarContentDescription = charSequence;
        SeekBar seekBar = this.mSeekBar;
        if (seekBar != null) {
            seekBar.setContentDescription(charSequence);
        }
    }

    public final void syncProgress(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        if (progress != this.mProgress) {
            if (!callChangeListener(Integer.valueOf(progress))) {
                seekBar.setProgress(this.mProgress);
                return;
            }
            setProgress(progress, false);
            int i = this.mHapticFeedbackMode;
            if (i == 1) {
                seekBar.performHapticFeedback(4);
            } else {
                if (i != 2) {
                    return;
                }
                if (progress == this.mMax || progress == this.mMin) {
                    seekBar.performHapticFeedback(4);
                }
            }
        }
    }

    public final void setProgress(int i, boolean z) {
        int i2 = this.mMax;
        if (i > i2) {
            i = i2;
        }
        int i3 = this.mMin;
        if (i < i3) {
            i = i3;
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
        public int min;
        public int progress;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.widget.SeekBarPreference$SavedState$1, reason: invalid class name */
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
            this.min = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.max);
            parcel.writeInt(this.min);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, com.android.settings.R.attr.seekBarPreferenceStyle, 17957213));
    }

    public SeekBarPreference(Context context) {
        this(context, null);
    }
}
