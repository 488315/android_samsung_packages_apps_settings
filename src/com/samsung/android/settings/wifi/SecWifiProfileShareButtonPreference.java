package com.samsung.android.settings.wifi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecWifiProfileShareButtonPreference extends SecWifiButtonPreference {
    public boolean mAnimated;
    public LinearLayout mAnimationContainer;

    public SecWifiProfileShareButtonPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        initPreference(preferenceViewHolder);
        if (this.mAnimationContainer == null) {
            this.mAnimationContainer =
                    (LinearLayout)
                            preferenceViewHolder.findViewById(
                                    R.id.profile_share_animation_container);
        }
        if (this.mAnimated || this.mAnimationContainer == null) {
            return;
        }
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(250L);
        animationSet.addAnimation(alphaAnimation);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.9f, 1.0f);
        scaleAnimation.setDuration(250L);
        animationSet.addAnimation(scaleAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, -12.0f, 0.0f);
        translateAnimation.setDuration(250L);
        animationSet.addAnimation(translateAnimation);
        this.mAnimationContainer.startAnimation(animationSet);
        this.mAnimated = true;
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        LinearLayout linearLayout = this.mButton;
        if (linearLayout != null) {
            linearLayout.setAlpha(z ? 1.0f : 0.4f);
        }
        this.mProgressVisible = !z;
        notifyChanged();
    }

    public SecWifiProfileShareButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecWifiProfileShareButtonPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.obtainStyledAttributes(
                        attributeSet,
                        R$styleable.SecButtonPreferenceStyle,
                        i,
                        R.style.RoundButtonStyle)
                .recycle();
        setLayoutResource(R.layout.sec_wifi_profile_share_button_preference);
        setSelectable(false);
    }

    public SecWifiProfileShareButtonPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }
}
