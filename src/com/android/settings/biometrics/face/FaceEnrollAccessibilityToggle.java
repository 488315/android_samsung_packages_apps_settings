package com.android.settings.biometrics.face;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.R$styleable;

import com.google.android.material.materialswitch.MaterialSwitch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollAccessibilityToggle extends LinearLayout {
    public final MaterialSwitch mSwitch;

    public FaceEnrollAccessibilityToggle(Context context) {
        this(context, null);
    }

    public CompoundButton getSwitch() {
        return this.mSwitch;
    }

    public void setChecked(boolean z) {
        this.mSwitch.setChecked(z);
        this.mSwitch.jumpDrawablesToCurrentState();
    }

    public void setListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mSwitch.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public FaceEnrollAccessibilityToggle(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FaceEnrollAccessibilityToggle(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context)
                .inflate(R.layout.face_enroll_accessibility_toggle, (ViewGroup) this, true);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.FaceEnrollAccessibilityToggle);
        try {
            ((TextView) findViewById(R.id.title)).setText(obtainStyledAttributes.getText(0));
            obtainStyledAttributes.recycle();
            MaterialSwitch materialSwitch = (MaterialSwitch) findViewById(R.id.toggle);
            this.mSwitch = materialSwitch;
            materialSwitch.setChecked(false);
            materialSwitch.setClickable(false);
            materialSwitch.setFocusable(false);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
