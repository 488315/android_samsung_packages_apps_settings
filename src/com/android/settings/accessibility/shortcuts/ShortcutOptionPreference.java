package com.android.settings.accessibility.shortcuts;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.LottieColorUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ShortcutOptionPreference extends CheckBoxPreference {
    public int mIntroImageRawResId;
    public int mIntroImageResId;
    public int mSummaryTextLineHeight;

    public ShortcutOptionPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mIntroImageResId = 0;
        this.mIntroImageRawResId = 0;
        setLayoutResource(R.layout.accessibility_shortcut_option_checkable);
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mIntroImageResId == 0 && this.mIntroImageRawResId == 0) {
            preferenceViewHolder.findViewById(R.id.image).setVisibility(8);
        } else {
            preferenceViewHolder.findViewById(R.id.image).setVisibility(0);
            LottieAnimationView lottieAnimationView =
                    (LottieAnimationView) preferenceViewHolder.itemView.findViewById(R.id.image);
            int i = this.mIntroImageRawResId;
            if (i != 0) {
                lottieAnimationView.failureListener =
                        new LottieListener() { // from class:
                                               // com.android.settings.accessibility.shortcuts.ShortcutOptionPreference$$ExternalSyntheticLambda0
                            @Override // com.airbnb.lottie.LottieListener
                            public final void onResult(Object obj) {
                                StringBuilder sb =
                                        new StringBuilder("Invalid image raw resource id: ");
                                ShortcutOptionPreference shortcutOptionPreference =
                                        ShortcutOptionPreference.this;
                                sb.append(
                                        shortcutOptionPreference
                                                .getContext()
                                                .getResources()
                                                .getResourceEntryName(
                                                        shortcutOptionPreference
                                                                .mIntroImageRawResId));
                                Log.w("ShortcutOptionPreference", sb.toString(), (Throwable) obj);
                            }
                        };
                lottieAnimationView.setAnimation(i);
                lottieAnimationView.setRepeatCount(-1);
                LottieColorUtils.applyDynamicColors(
                        lottieAnimationView.getContext(), lottieAnimationView);
                lottieAnimationView.playAnimation$1();
            } else {
                lottieAnimationView.setImageResource(this.mIntroImageResId);
            }
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.summary);
        if (textView != null) {
            this.mSummaryTextLineHeight = textView.getLineHeight();
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        syncSummaryView(preferenceViewHolder.findViewById(android.R.id.summary));
    }

    public final void setIntroImageResId(int i) {
        if (i != this.mIntroImageResId) {
            this.mIntroImageResId = i;
            this.mIntroImageRawResId = 0;
            notifyChanged();
        }
    }

    public ShortcutOptionPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIntroImageResId = 0;
        this.mIntroImageRawResId = 0;
        setLayoutResource(R.layout.accessibility_shortcut_option_checkable);
    }

    public ShortcutOptionPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIntroImageResId = 0;
        this.mIntroImageRawResId = 0;
        setLayoutResource(R.layout.accessibility_shortcut_option_checkable);
    }

    public ShortcutOptionPreference(Context context) {
        super(context, null);
        this.mIntroImageResId = 0;
        this.mIntroImageRawResId = 0;
        setLayoutResource(R.layout.accessibility_shortcut_option_checkable);
    }
}
