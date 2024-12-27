package com.android.settings.accessibility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PaletteListPreference extends Preference {
    public final List mGradientColors;
    public final List mGradientOffsets;

    public PaletteListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i = 2;
        int i2 = 0;
        super.onBindViewHolder(preferenceViewHolder);
        ViewGroup viewGroup =
                (ViewGroup) preferenceViewHolder.itemView.findViewById(R.id.palette_view);
        int color = getContext().getColor(R.color.palette_list_gradient_background);
        ((ArrayList) this.mGradientColors).add(0, Integer.valueOf(color));
        ((ArrayList) this.mGradientColors).add(1, Integer.valueOf(color));
        ((ArrayList) this.mGradientColors).add(2, Integer.valueOf(color));
        ((ArrayList) this.mGradientOffsets).add(0, Float.valueOf(0.0f));
        ((ArrayList) this.mGradientOffsets).add(1, Float.valueOf(0.5f));
        ((ArrayList) this.mGradientOffsets).add(2, Float.valueOf(1.0f));
        Context context = getContext();
        if (viewGroup.getChildCount() > 0) {
            viewGroup.removeAllViews();
        }
        List list =
                (List)
                        Arrays.stream(
                                        context.getResources()
                                                .getIntArray(R.array.setting_palette_colors))
                                .boxed()
                                .collect(Collectors.toList());
        List asList =
                Arrays.asList(context.getResources().getStringArray(R.array.setting_palette_data));
        float dimension =
                context.getResources().getDimension(R.dimen.accessibility_layout_margin_start_end);
        int i3 = AccessibilityUtil.$r8$clinit;
        Resources resources = context.getResources();
        ((ArrayList) this.mGradientOffsets)
                .set(
                        1,
                        Float.valueOf(
                                (Math.round(
                                                        new TextView(context)
                                                                .getPaint()
                                                                .measureText(
                                                                        (String)
                                                                                Collections.max(
                                                                                        asList,
                                                                                        Comparator
                                                                                                .comparing(
                                                                                                        new PaletteListPreference$$ExternalSyntheticLambda0()))))
                                                + dimension)
                                        / Math.round(
                                                TypedValue.applyDimension(
                                                        1,
                                                        resources.getConfiguration().screenWidthDp,
                                                        resources.getDisplayMetrics()))));
        int round =
                (Math.round(
                                        TypedValue.applyDimension(
                                                1,
                                                r2.getConfiguration().screenHeightDp,
                                                context.getResources().getDisplayMetrics()))
                                / 2)
                        / asList.size();
        Paint.FontMetrics fontMetrics = new TextView(context).getPaint().getFontMetrics();
        int[] iArr = {round, Math.round(fontMetrics.bottom - fontMetrics.top)};
        int i4 = iArr[0];
        int i5 = iArr[1];
        if (i5 > i4) {
            i4 = i5;
        }
        int i6 = 0;
        while (i6 < asList.size()) {
            TextView textView = new TextView(context);
            textView.setText((CharSequence) asList.get(i6));
            textView.setHeight(i4);
            textView.setPaddingRelative(Math.round(dimension), i2, i2, i2);
            textView.setGravity(16);
            Integer num = (Integer) list.get(i6);
            num.getClass();
            ((ArrayList) this.mGradientColors).set(i, num);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setOrientation(
                    TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1
                            ? GradientDrawable.Orientation.RIGHT_LEFT
                            : GradientDrawable.Orientation.LEFT_RIGHT);
            Object[] array = ((ArrayList) this.mGradientColors).toArray();
            int length = array.length;
            int[] iArr2 = new int[length];
            for (int i7 = i2; i7 < length; i7++) {
                Object obj = array[i7];
                obj.getClass();
                iArr2[i7] = ((Number) obj).intValue();
            }
            Object[] array2 = ((ArrayList) this.mGradientOffsets).toArray();
            int length2 = array2.length;
            float[] fArr = new float[length2];
            while (i2 < length2) {
                Object obj2 = array2[i2];
                obj2.getClass();
                fArr[i2] = ((Number) obj2).floatValue();
                i2++;
            }
            gradientDrawable.setColors(iArr2, fArr);
            textView.setBackground(gradientDrawable);
            viewGroup.addView(textView);
            i6++;
            i = 2;
            i2 = 0;
        }
        int size = asList.size();
        int dimensionPixelSize =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.accessibility_illustration_view_radius);
        GradientDrawable gradientDrawable2 =
                (GradientDrawable) viewGroup.getChildAt(0).getBackground();
        GradientDrawable gradientDrawable3 =
                (GradientDrawable) viewGroup.getChildAt(size - 1).getBackground();
        float f = dimensionPixelSize;
        gradientDrawable2.setCornerRadii(new float[] {f, f, f, f, 0.0f, 0.0f, 0.0f, 0.0f});
        gradientDrawable3.setCornerRadii(new float[] {0.0f, 0.0f, 0.0f, 0.0f, f, f, f, f});
    }

    public PaletteListPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mGradientColors = new ArrayList();
        this.mGradientOffsets = new ArrayList();
        setLayoutResource(R.layout.daltonizer_preview);
    }
}
