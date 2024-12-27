package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TwoTargetPreference extends Preference {
    public int mIconSize;
    public int mMediumIconSize;
    public int mSmallIconSize;

    public TwoTargetPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init$4(context);
    }

    public int getSecondTargetResId() {
        return 0;
    }

    public final void init$4(Context context) {
        setLayoutResource(R.layout.preference_two_target);
        this.mSmallIconSize =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.two_target_pref_small_icon_size);
        this.mMediumIconSize =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.two_target_pref_medium_icon_size);
        int secondTargetResId = getSecondTargetResId();
        if (secondTargetResId != 0) {
            setWidgetLayoutResource(secondTargetResId);
        }
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView =
                (ImageView) preferenceViewHolder.itemView.findViewById(android.R.id.icon);
        int i = this.mIconSize;
        if (i == 1) {
            int i2 = this.mMediumIconSize;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(i2, i2));
        } else if (i == 2) {
            int i3 = this.mSmallIconSize;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(i3, i3));
        }
        View findViewById = preferenceViewHolder.findViewById(R.id.two_target_divider);
        View findViewById2 = preferenceViewHolder.findViewById(android.R.id.widget_frame);
        boolean shouldHideSecondTarget = shouldHideSecondTarget();
        if (findViewById != null) {
            findViewById.setVisibility(shouldHideSecondTarget ? 8 : 0);
        }
        if (findViewById2 != null) {
            findViewById2.setVisibility(shouldHideSecondTarget ? 8 : 0);
        }
    }

    public boolean shouldHideSecondTarget() {
        return getSecondTargetResId() == 0;
    }

    public TwoTargetPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init$4(context);
    }

    public TwoTargetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init$4(context);
    }

    public TwoTargetPreference(Context context) {
        super(context);
        init$4(context);
    }
}
