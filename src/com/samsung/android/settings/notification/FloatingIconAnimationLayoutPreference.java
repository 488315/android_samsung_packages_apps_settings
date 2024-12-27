package com.samsung.android.settings.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FloatingIconAnimationLayoutPreference extends LayoutPreference {
    public final Context mContext;
    public FrameLayout mFloatingIconAnimationContainer;
    public LottieAnimationView mFloatingIconImage;
    public String mImageValue;

    public FloatingIconAnimationLayoutPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    @Override // com.android.settingslib.widget.LayoutPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        FrameLayout frameLayout =
                (FrameLayout)
                        preferenceViewHolder.itemView.findViewById(
                                R.id.floating_icon_animation_container);
        this.mFloatingIconAnimationContainer = frameLayout;
        frameLayout.semSetRoundedCorners(15);
        this.mFloatingIconAnimationContainer.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView)
                        preferenceViewHolder.itemView.findViewById(R.id.floating_icon_animation);
        this.mFloatingIconImage = lottieAnimationView;
        if (lottieAnimationView == null) {
            return;
        }
        String str = this.mImageValue;
        if (str != null) {
            lottieAnimationView.setAnimation(str);
        }
        this.mFloatingIconImage.playAnimation$1();
    }

    public final void setAnimationResource(String str) {
        if (str.equals(this.mImageValue)) {
            return;
        }
        this.mImageValue = str;
        notifyChanged();
    }
}
