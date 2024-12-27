package com.samsung.android.settings.lockscreen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.widget.SecUnclickablePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecLockScreenUnclickablePreference extends SecUnclickablePreference {
    public final int mTitleColor;

    public SecLockScreenUnclickablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.samsung.android.settings.widget.SecUnclickablePreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        textView.setTextColor(this.mTitleColor);
        textView.setTypeface(null);
        textView.setText(getTitle());
        textView.invalidate();
    }

    public SecLockScreenUnclickablePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTitleColor =
                context.getColor(R.color.sec_biometrics_choose_lock_header_description_color);
        setLayoutResource(R.layout.sec_lock_screen_preference_unclickable);
    }
}
