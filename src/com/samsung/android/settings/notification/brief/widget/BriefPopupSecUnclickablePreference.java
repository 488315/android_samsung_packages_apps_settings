package com.samsung.android.settings.notification.brief.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopupSecUnclickablePreference extends SecPreference {
    public BriefPopupSecUnclickablePreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setSelectable(false);
        if (attributeSet == null) {
            setLayoutResource(R.layout.sec_widget_preference_brief_popup_unclickable);
            seslSetSubheaderRoundedBackground(15);
            return;
        }
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.SecUnclickable);
        obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 =
                context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, i2);
        setLayoutResource(
                obtainStyledAttributes2.getResourceId(
                        3, R.layout.sec_widget_preference_brief_popup_unclickable));
        obtainStyledAttributes2.recycle();
        TypedArray obtainStyledAttributes3 =
                context.obtainStyledAttributes(attributeSet, R$styleable.SecCategory);
        seslSetSubheaderRoundedBackground(obtainStyledAttributes3.getInt(0, 15));
        obtainStyledAttributes3.recycle();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.brief_title);
        textView.setText(getTitle());
        textView.setVisibility(0);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
    }

    public BriefPopupSecUnclickablePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BriefPopupSecUnclickablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BriefPopupSecUnclickablePreference(Context context) {
        this(context, null);
    }
}
