package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.R;
import com.android.settings.Utils;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CustomSeekbarPreference extends RestrictedPreference
        implements SeslSeekBar.OnSeekBarChangeListener, View.OnKeyListener {
    public int mMax;
    public int mMin;
    public int mProgress;
    public SeslSeekBar mSeekBar;
    public CharSequence mSeekBarContentDescription;
    public boolean mTrackingTouch;

    public CustomSeekbarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 17957213, 0);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.ProgressBar, 17957213, 0);
        int i = obtainStyledAttributes.getInt(2, this.mMax);
        if (i != this.mMax) {
            this.mMax = i;
            notifyChanged();
        }
        obtainStyledAttributes.recycle();
        setLayoutResource(com.android.settings.R.layout.sec_preference_custom_seekbar);
    }

    @Override // androidx.preference.Preference
    public final CharSequence getSummary() {
        return null;
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
        SeslSeekBar seslSeekBar =
                (SeslSeekBar)
                        preferenceViewHolder.findViewById(com.android.settings.R.id.custom_seekbar);
        this.mSeekBar = seslSeekBar;
        seslSeekBar.setOnSeekBarChangeListener(this);
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
        this.mSeekBar.setAccessibilityDelegate(
                new View
                        .AccessibilityDelegate() { // from class:
                                                   // com.samsung.android.settings.inputmethod.CustomSeekbarPreference.1
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        AccessibilityNodeInfo.RangeInfo rangeInfo =
                                accessibilityNodeInfo.getRangeInfo();
                        if (rangeInfo != null) {
                            CustomSeekbarPreference.this.getClass();
                            accessibilityNodeInfo.setRangeInfo(
                                    AccessibilityNodeInfo.RangeInfo.obtain(
                                            0,
                                            rangeInfo.getMin(),
                                            rangeInfo.getMax(),
                                            rangeInfo.getCurrent()));
                        }
                    }
                });
    }

    @Override // androidx.preference.Preference
    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    @Override // androidx.preference.Preference
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        accessibilityNodeInfoCompat.mInfo.removeAction(
                (AccessibilityNodeInfo.AccessibilityAction)
                        new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                                        64, (CharSequence) null)
                                .mAction);
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        SeslSeekBar seslSeekBar;
        if (keyEvent.getAction() == 0
                && (seslSeekBar =
                                (SeslSeekBar)
                                        view.findViewById(com.android.settings.R.id.custom_seekbar))
                        != null) {
            return seslSeekBar.onKeyDown(i, keyEvent);
        }
        return false;
    }

    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        int progress;
        if (!z || this.mTrackingTouch || (progress = seslSeekBar.getProgress()) == this.mProgress) {
            return;
        }
        if (callChangeListener(Integer.valueOf(progress))) {
            setProgress$1(progress, false);
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
        setProgress$1(z ? getPersistedInt(this.mProgress) : ((Integer) obj).intValue(), true);
    }

    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
        this.mTrackingTouch = true;
    }

    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        int progress;
        this.mTrackingTouch = false;
        if (seslSeekBar.getProgress() == this.mProgress
                || (progress = seslSeekBar.getProgress()) == this.mProgress) {
            return;
        }
        if (callChangeListener(Integer.valueOf(progress))) {
            setProgress$1(progress, false);
        } else {
            seslSeekBar.setProgress(this.mProgress);
        }
    }

    public final void setProgress$1(int i, boolean z) {
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

    public final void setSeekBarContentDescription(CharSequence charSequence) {
        if (Utils.isTalkBackEnabled(getContext())) {
            return;
        }
        this.mSeekBarContentDescription = charSequence;
        SeslSeekBar seslSeekBar = this.mSeekBar;
        if (seslSeekBar != null) {
            seslSeekBar.setContentDescription(charSequence);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public int max;
        public int min;
        public int progress;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.inputmethod.CustomSeekbarPreference$SavedState$1, reason: invalid class name */
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
}
