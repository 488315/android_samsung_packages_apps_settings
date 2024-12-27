package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.core.content.res.TypedArrayUtils;

import com.android.settings.R;
import com.android.settings.R$styleable;
import com.android.settingslib.widget.LayoutPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TwoStateButtonPreference extends LayoutPreference implements View.OnClickListener {
    public final Button mButtonOff;
    public final Button mButtonOn;
    public boolean mIsChecked;

    public TwoStateButtonPreference(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context,
                        R.attr.twoStateButtonPreferenceStyle,
                        android.R.attr.preferenceStyle));
        if (attributeSet == null) {
            this.mButtonOn = null;
            this.mButtonOff = null;
            return;
        }
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.TwoStateButtonPreference);
        int resourceId = obtainStyledAttributes.getResourceId(1, R.string.summary_placeholder);
        int resourceId2 = obtainStyledAttributes.getResourceId(0, R.string.summary_placeholder);
        obtainStyledAttributes.recycle();
        Button button = (Button) this.mRootView.findViewById(R.id.state_on_button);
        this.mButtonOn = button;
        button.setText(resourceId);
        button.setOnClickListener(this);
        Button button2 = (Button) this.mRootView.findViewById(R.id.state_off_button);
        this.mButtonOff = button2;
        button2.setText(resourceId2);
        button2.setOnClickListener(this);
        setChecked(this.mIsChecked);
    }

    public Button getStateOffButton() {
        return this.mButtonOff;
    }

    public Button getStateOnButton() {
        return this.mButtonOn;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        boolean z = view.getId() == R.id.state_on_button;
        setChecked(z);
        callChangeListener(Boolean.valueOf(z));
    }

    public final void setChecked(boolean z) {
        this.mIsChecked = z;
        if (z) {
            this.mButtonOn.setVisibility(8);
            this.mButtonOff.setVisibility(0);
        } else {
            this.mButtonOn.setVisibility(0);
            this.mButtonOff.setVisibility(8);
        }
    }
}
