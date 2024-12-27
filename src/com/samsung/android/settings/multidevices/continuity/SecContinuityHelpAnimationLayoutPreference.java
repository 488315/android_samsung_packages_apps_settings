package com.samsung.android.settings.multidevices.continuity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.widget.PlayPauseLottieAnimationView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecContinuityHelpAnimationLayoutPreference extends LayoutPreference {
    public final Context mContext;
    public String mDescAppString1;
    public String mDescAppString2;
    public TextView mDescAppText1;
    public TextView mDescAppText2;
    public String mDescString1;
    public String mDescString2;
    public String mDescString3;
    public TextView mDescText1;
    public TextView mDescText2;
    public TextView mDescText3;
    public FrameLayout mHelpAnimationContainer;
    public PlayPauseLottieAnimationView mHelpImage;
    public String mImageValue;

    public SecContinuityHelpAnimationLayoutPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
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
        String str = this.mImageValue;
        if (str != null) {
            playPauseLottieAnimationView.setAnimation(str);
            this.mHelpImage.playAnimation$1();
        }
        this.mDescText1 =
                (TextView) preferenceViewHolder.itemView.findViewById(R.id.help_description_text1);
        this.mDescText2 =
                (TextView) preferenceViewHolder.itemView.findViewById(R.id.help_description_text2);
        this.mDescText3 =
                (TextView) preferenceViewHolder.itemView.findViewById(R.id.help_description_text3);
        this.mDescAppText1 =
                (TextView)
                        preferenceViewHolder.itemView.findViewById(R.id.help_description_app_text1);
        this.mDescAppText2 =
                (TextView)
                        preferenceViewHolder.itemView.findViewById(R.id.help_description_app_text2);
        String str2 = this.mDescString1;
        if (str2 != null) {
            this.mDescText1.setText(str2);
            this.mDescText1.setContentDescription(this.mDescString1);
        }
        String str3 = this.mDescString2;
        if (str3 != null) {
            this.mDescText2.setText(str3);
            this.mDescText2.setContentDescription(this.mDescString2);
        }
        String str4 = this.mDescString3;
        if (str4 != null) {
            this.mDescText3.setText(str4);
            this.mDescText3.setContentDescription(this.mDescString3);
        }
        String str5 = this.mDescAppString1;
        if (str5 != null) {
            this.mDescAppText1.setText(str5);
            this.mDescAppText1.setContentDescription(this.mDescAppString1);
        }
        String str6 = this.mDescAppString2;
        if (str6 != null) {
            this.mDescAppText2.setText(str6);
            this.mDescAppText2.setContentDescription(this.mDescAppString2);
        }
    }
}
