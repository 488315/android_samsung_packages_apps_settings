package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecHelpAnimationLayoutPreference extends LayoutPreference {
    public final Context mContext;
    public String mDescString;
    public TextView mDescText;
    public FrameLayout mHelpAnimationContainer;
    public PlayPauseLottieAnimationView mHelpImage;
    public String mImageValue;
    public final boolean mTextVisible;

    public SecHelpAnimationLayoutPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTextVisible = true;
        this.mContext = context;
    }

    @Override // com.android.settingslib.widget.LayoutPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        FrameLayout frameLayout =
                (FrameLayout)
                        preferenceViewHolder.itemView.findViewById(R.id.help_animation_container);
        this.mHelpAnimationContainer = frameLayout;
        frameLayout.semSetRoundedCorners(15);
        this.mHelpAnimationContainer.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        PlayPauseLottieAnimationView playPauseLottieAnimationView =
                (PlayPauseLottieAnimationView)
                        preferenceViewHolder.itemView.findViewById(R.id.help_animation);
        this.mHelpImage = playPauseLottieAnimationView;
        if (this.mImageValue != null) {
            playPauseLottieAnimationView.setVisibility(0);
            this.mHelpImage.setAnimation(this.mImageValue);
            this.mHelpImage.playAnimation$1();
        } else {
            playPauseLottieAnimationView.setVisibility(8);
        }
        TextView textView =
                (TextView) preferenceViewHolder.itemView.findViewById(R.id.help_description_text);
        this.mDescText = textView;
        textView.setVisibility(this.mTextVisible ? 0 : 8);
        String str = this.mDescString;
        if (str != null) {
            this.mDescText.setText(str);
            this.mDescText.setContentDescription(this.mDescString);
        }
    }

    public final void setAnimationResource(String str) {
        if (str.equals(this.mImageValue)) {
            return;
        }
        this.mImageValue = str;
        notifyChanged();
    }

    public final void setDescText(String str) {
        if (str != null) {
            this.mDescString = str;
            notifyChanged();
        }
    }
}
