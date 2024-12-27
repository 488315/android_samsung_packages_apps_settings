package com.google.android.material.theme;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.app.AppCompatViewInflater;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MaterialComponentsViewInflater extends AppCompatViewInflater {
    @Override // androidx.appcompat.app.AppCompatViewInflater
    public final AppCompatAutoCompleteTextView createAutoCompleteTextView(
            Context context, AttributeSet attributeSet) {
        return new MaterialAutoCompleteTextView(context, attributeSet);
    }

    @Override // androidx.appcompat.app.AppCompatViewInflater
    public final AppCompatButton createButton(Context context, AttributeSet attributeSet) {
        return new MaterialButton(context, attributeSet);
    }

    @Override // androidx.appcompat.app.AppCompatViewInflater
    public final AppCompatCheckBox createCheckBox(Context context, AttributeSet attributeSet) {
        return new MaterialCheckBox(context, attributeSet);
    }

    @Override // androidx.appcompat.app.AppCompatViewInflater
    public final AppCompatRadioButton createRadioButton(
            Context context, AttributeSet attributeSet) {
        MaterialRadioButton materialRadioButton =
                new MaterialRadioButton(
                        MaterialThemeOverlay.wrap(
                                context, attributeSet, R.attr.radioButtonStyle, 2132084896),
                        attributeSet,
                        R.attr.radioButtonStyle);
        Context context2 = materialRadioButton.getContext();
        TypedArray obtainStyledAttributes =
                ThemeEnforcement.obtainStyledAttributes(
                        context2,
                        attributeSet,
                        R$styleable.MaterialRadioButton,
                        R.attr.radioButtonStyle,
                        2132084896,
                        new int[0]);
        if (obtainStyledAttributes.hasValue(0)) {
            materialRadioButton.setButtonTintList(
                    MaterialResources.getColorStateList(context2, obtainStyledAttributes, 0));
        }
        materialRadioButton.useMaterialThemeColors = obtainStyledAttributes.getBoolean(1, false);
        obtainStyledAttributes.recycle();
        return materialRadioButton;
    }

    @Override // androidx.appcompat.app.AppCompatViewInflater
    public final AppCompatTextView createTextView(Context context, AttributeSet attributeSet) {
        MaterialTextView materialTextView =
                new MaterialTextView(
                        MaterialThemeOverlay.wrap(
                                context, attributeSet, android.R.attr.textViewStyle, 0),
                        attributeSet,
                        android.R.attr.textViewStyle);
        Context context2 = materialTextView.getContext();
        if (MaterialAttributes.resolveBoolean(
                context2, R.attr.textAppearanceLineHeightEnabled, true)) {
            Resources.Theme theme = context2.getTheme();
            int[] iArr = R$styleable.MaterialTextView;
            TypedArray obtainStyledAttributes =
                    theme.obtainStyledAttributes(
                            attributeSet, iArr, android.R.attr.textViewStyle, 0);
            int readFirstAvailableDimension =
                    MaterialTextView.readFirstAvailableDimension(
                            context2, obtainStyledAttributes, 1, 2);
            obtainStyledAttributes.recycle();
            if (readFirstAvailableDimension == -1) {
                TypedArray obtainStyledAttributes2 =
                        theme.obtainStyledAttributes(
                                attributeSet, iArr, android.R.attr.textViewStyle, 0);
                int resourceId = obtainStyledAttributes2.getResourceId(0, -1);
                obtainStyledAttributes2.recycle();
                if (resourceId != -1) {
                    TypedArray obtainStyledAttributes3 =
                            theme.obtainStyledAttributes(
                                    resourceId, R$styleable.MaterialTextAppearance);
                    int readFirstAvailableDimension2 =
                            MaterialTextView.readFirstAvailableDimension(
                                    materialTextView.getContext(), obtainStyledAttributes3, 1, 2);
                    obtainStyledAttributes3.recycle();
                    if (readFirstAvailableDimension2 >= 0) {
                        TextViewCompat.setLineHeight(
                                readFirstAvailableDimension2, materialTextView);
                    }
                }
            }
        }
        return materialTextView;
    }
}
