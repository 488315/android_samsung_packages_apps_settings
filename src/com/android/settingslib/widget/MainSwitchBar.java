package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.preference.R$styleable;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MainSwitchBar extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final View mFrameView;
    public final CompoundButton mSwitch;
    public final List mSwitchChangeListeners;
    public final TextView mTextView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public boolean mChecked;
        public boolean mVisible;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settingslib.widget.MainSwitchBar$SavedState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                SavedState savedState = new SavedState(parcel);
                savedState.mChecked = ((Boolean) parcel.readValue(null)).booleanValue();
                savedState.mVisible = ((Boolean) parcel.readValue(null)).booleanValue();
                return savedState;
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MainSwitchBar.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" checked=");
            sb.append(this.mChecked);
            sb.append(" visible=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.mVisible, "}");
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState,
                  // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.mChecked));
            parcel.writeValue(Boolean.valueOf(this.mVisible));
        }
    }

    public MainSwitchBar(Context context) {
        this(context, null);
    }

    private void setBackground(boolean z) {
        this.mFrameView.setActivated(z);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        setBackground(z);
        Iterator it = ((ArrayList) this.mSwitchChangeListeners).iterator();
        while (it.hasNext()) {
            ((CompoundButton.OnCheckedChangeListener) it.next()).onCheckedChanged(this.mSwitch, z);
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSwitch.setChecked(savedState.mChecked);
        setChecked(savedState.mChecked);
        setBackground(savedState.mChecked);
        setVisibility(savedState.mVisible ? 0 : 8);
        this.mSwitch.setOnCheckedChangeListener(savedState.mVisible ? this : null);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mChecked = this.mSwitch.isChecked();
        savedState.mVisible = getVisibility() == 0;
        return savedState;
    }

    @Override // android.view.View
    public final boolean performClick() {
        this.mSwitch.performClick();
        return super.performClick();
    }

    public void setChecked(boolean z) {
        CompoundButton compoundButton = this.mSwitch;
        if (compoundButton != null) {
            compoundButton.setChecked(z);
        }
        setBackground(z);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mTextView.setEnabled(z);
        this.mSwitch.setEnabled(z);
        this.mFrameView.setEnabled(z);
        this.mFrameView.setActivated(this.mSwitch.isChecked());
    }

    public void setTitle(CharSequence charSequence) {
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ArrayList arrayList = new ArrayList();
        this.mSwitchChangeListeners = arrayList;
        LayoutInflater.from(context).inflate(R.layout.settingslib_main_switch_bar, this);
        setFocusable(true);
        setClickable(true);
        this.mFrameView = findViewById(R.id.frame);
        this.mTextView = (TextView) findViewById(R.id.switch_text);
        CompoundButton compoundButton = (CompoundButton) findViewById(android.R.id.switch_widget);
        this.mSwitch = compoundButton;
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener =
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.android.settingslib.widget.MainSwitchBar$$ExternalSyntheticLambda0
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton2, boolean z) {
                        MainSwitchBar mainSwitchBar = MainSwitchBar.this;
                        int i3 = MainSwitchBar.$r8$clinit;
                        mainSwitchBar.setChecked(z);
                    }
                };
        if (!arrayList.contains(onCheckedChangeListener)) {
            arrayList.add(onCheckedChangeListener);
        }
        if (compoundButton.getVisibility() == 0) {
            compoundButton.setOnCheckedChangeListener(this);
        }
        setChecked(compoundButton.isChecked());
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(attributeSet, R$styleable.Preference, 0, 0);
            setTitle(obtainStyledAttributes.getText(4));
            obtainStyledAttributes.recycle();
        }
        setBackground(compoundButton.isChecked());
    }

    public void setIconSpaceReserved(boolean z) {}
}
