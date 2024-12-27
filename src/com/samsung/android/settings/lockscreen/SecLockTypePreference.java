package com.samsung.android.settings.lockscreen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecLockTypePreference extends RestrictedPreference {
    public int mIconVisibility;

    public SecLockTypePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIconVisibility = 8;
        setLayoutResource(R.layout.sec_choose_lock_preference_layout);
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.icon_status_frame);
        if (findViewById != null) {
            findViewById.setVisibility(this.mIconVisibility);
        }
    }

    public SecLockTypePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecLockTypePreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
    }

    public SecLockTypePreference(Context context) {
        this(context, null);
    }
}
