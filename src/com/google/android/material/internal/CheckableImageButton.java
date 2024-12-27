package com.google.android.material.internal;

import android.R;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Checkable;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class CheckableImageButton extends AppCompatImageButton implements Checkable {
    public static final int[] DRAWABLE_STATE_CHECKED = {R.attr.state_checked};
    public boolean checkable;
    public boolean checked;
    public boolean pressable;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public boolean checked;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.android.material.internal.CheckableImageButton$SavedState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.ClassLoaderCreator {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.checked = parcel.readInt() == 1;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.checked ? 1 : 0);
        }
    }

    public CheckableImageButton(Context context) {
        this(context, null);
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.checked;
    }

    @Override // android.widget.ImageView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        return this.checked
                ? ImageButton.mergeDrawableStates(
                        super.onCreateDrawableState(i + 1), DRAWABLE_STATE_CHECKED)
                : super.onCreateDrawableState(i);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        setChecked(savedState.checked);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.checked;
        return savedState;
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        if (!this.checkable || this.checked == z) {
            return;
        }
        this.checked = z;
        refreshDrawableState();
        sendAccessibilityEvent(2048);
    }

    @Override // android.view.View
    public final void setPressed(boolean z) {
        if (this.pressable) {
            super.setPressed(z);
        }
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        setChecked(!this.checked);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.settings.R.attr.imageButtonStyle);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.checkable = true;
        this.pressable = true;
        ViewCompat.setAccessibilityDelegate(
                this,
                new AccessibilityDelegateCompat() { // from class:
                                                    // com.google.android.material.internal.CheckableImageButton.1
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final void onInitializeAccessibilityEvent(
                            View view, AccessibilityEvent accessibilityEvent) {
                        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
                        accessibilityEvent.setChecked(CheckableImageButton.this.checked);
                    }

                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                view, accessibilityNodeInfoCompat.mInfo);
                        CheckableImageButton checkableImageButton = CheckableImageButton.this;
                        accessibilityNodeInfoCompat.setCheckable(checkableImageButton.checkable);
                        accessibilityNodeInfoCompat.setChecked(checkableImageButton.checked);
                    }
                });
    }
}
