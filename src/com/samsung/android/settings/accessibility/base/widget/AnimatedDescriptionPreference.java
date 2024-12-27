package com.samsung.android.settings.accessibility.base.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R$styleable;

import com.airbnb.lottie.LottieAnimationView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AnimatedDescriptionPreference extends DescriptionPreference {
    public final int animationRawResId;
    public LottieAnimationView mAnimationView;

    public AnimatedDescriptionPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes =
                context.getTheme()
                        .obtainStyledAttributes(
                                attributeSet, R$styleable.DescriptionPreference, 0, 0);
        for (int i = 0; i < obtainStyledAttributes.getIndexCount(); i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 1) {
                this.animationRawResId = obtainStyledAttributes.getResourceId(index, 0);
            }
        }
        obtainStyledAttributes.recycle();
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.DescriptionPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) preferenceViewHolder.itemView.findViewById(R.id.icon);
        this.mAnimationView = lottieAnimationView;
        if (lottieAnimationView == null || this.animationRawResId == 0) {
            return;
        }
        lottieAnimationView.setVisibility(0);
        this.mAnimationView.setAnimation(this.animationRawResId);
        this.mAnimationView.playAnimation$1();
    }
}
