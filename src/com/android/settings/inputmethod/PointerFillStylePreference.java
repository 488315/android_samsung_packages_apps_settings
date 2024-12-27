package com.android.settings.inputmethod;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.PointerIcon;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PointerFillStylePreference extends Preference {
    public LinearLayout mButtonHolder;

    public PointerFillStylePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.pointer_icon_fill_style_layout);
    }

    public final StateListDrawable getBackgroundSelector(int i) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Resources resources = getContext().getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.pointer_fill_style_circle_diameter);
        stateListDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i);
        int defaultColor = Utils.getColorAttr(getContext(), android.R.^attr-private.materialColorPrimaryContainer).getDefaultColor();
        gradientDrawable.setStroke(resources.getDimensionPixelSize(R.dimen.pointer_fill_style_shape_hovered_stroke), defaultColor);
        gradientDrawable.setSize(dimensionPixelSize, dimensionPixelSize);
        gradientDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        float f = dimensionPixelSize / 2.0f;
        gradientDrawable.setCornerRadius(f);
        stateListDrawable.addState(new int[]{android.R.attr.state_hovered}, gradientDrawable);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(i);
        gradientDrawable2.setStroke(resources.getDimensionPixelSize(R.dimen.pointer_fill_style_shape_default_stroke), defaultColor);
        gradientDrawable2.setSize(dimensionPixelSize, dimensionPixelSize);
        gradientDrawable2.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        gradientDrawable2.setCornerRadius(f);
        stateListDrawable.addState(StateSet.WILD_CARD, gradientDrawable2);
        return stateListDrawable;
    }

    public final void initStyleButton(PreferenceViewHolder preferenceViewHolder, final int i, final int i2, int i3) {
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(i);
        if (imageView == null) {
            return;
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(PointerIcon.vectorFillStyleToResource(i2), new int[]{android.R.^attr-private.textAppearanceEasyCorrectSuggestion});
        try {
            imageView.setBackground(getBackgroundSelector(obtainStyledAttributes.getColor(0, EmergencyPhoneWidget.BG_COLOR)));
            obtainStyledAttributes.close();
            Resources resources = getContext().getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.pointer_fill_style_circle_diameter);
            Drawable drawable = getContext().getDrawable(R.drawable.ic_check_24dp);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.pointer_fill_style_circle_padding) / 2;
            int i4 = dimensionPixelSize - dimensionPixelSize2;
            drawable.setBounds(dimensionPixelSize2, dimensionPixelSize2, i4, i4);
            drawable.setColorFilter(new BlendModeColorFilter(-1, BlendMode.SRC_IN));
            drawable.setAlpha(i2 == i3 ? 255 : 0);
            imageView.setForeground(drawable);
            imageView.setForegroundGravity(17);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.inputmethod.PointerFillStylePreference$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context context;
                    PointerFillStylePreference pointerFillStylePreference = PointerFillStylePreference.this;
                    int i5 = i2;
                    int i6 = i;
                    context = ((AbstractPreferenceController) PointerFillStylePreferenceController.this).mContext;
                    Settings.System.putIntForUser(context.getContentResolver(), "pointer_fill_style", i5, -2);
                    if (pointerFillStylePreference.mButtonHolder == null) {
                        return;
                    }
                    for (int i7 = 0; i7 < pointerFillStylePreference.mButtonHolder.getChildCount(); i7++) {
                        View childAt = pointerFillStylePreference.mButtonHolder.getChildAt(i7);
                        if (childAt != null) {
                            childAt.getForeground().setAlpha(childAt.getId() == i6 ? 255 : 0);
                        }
                    }
                }
            });
            imageView.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
        } catch (Throwable th) {
            if (obtainStyledAttributes != null) {
                try {
                    obtainStyledAttributes.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        Context context;
        super.onBindViewHolder(preferenceViewHolder);
        LinearLayout linearLayout = (LinearLayout) preferenceViewHolder.findViewById(R.id.button_holder);
        this.mButtonHolder = linearLayout;
        if (linearLayout != null) {
            linearLayout.setOnHoverListener(new PointerFillStylePreference$$ExternalSyntheticLambda0());
        }
        context = ((AbstractPreferenceController) PointerFillStylePreferenceController.this).mContext;
        int intForUser = Settings.System.getIntForUser(context.getContentResolver(), "pointer_fill_style", 0, -2);
        initStyleButton(preferenceViewHolder, R.id.button_black, 0, intForUser);
        initStyleButton(preferenceViewHolder, R.id.button_green, 1, intForUser);
        initStyleButton(preferenceViewHolder, R.id.button_yellow, 2, intForUser);
        initStyleButton(preferenceViewHolder, R.id.button_pink, 3, intForUser);
        initStyleButton(preferenceViewHolder, R.id.button_blue, 4, intForUser);
    }
}
