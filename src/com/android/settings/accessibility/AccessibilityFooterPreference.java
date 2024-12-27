package com.android.settings.accessibility;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.FooterPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccessibilityFooterPreference extends FooterPreference {
    public boolean mLinkEnabled;

    public AccessibilityFooterPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.sec_preference_accessibility_footer);
        seslSetSubheaderRoundedBackground(15);
    }

    @Override // com.android.settingslib.widget.FooterPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView =
                (TextView) preferenceViewHolder.itemView.findViewById(android.R.id.title);
        if (this.mLinkEnabled) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setFocusable(false);
        } else {
            textView.setMovementMethod(null);
        }
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        preferenceViewHolder.itemView.findViewById(R.id.icon_frame).setVisibility(8);
    }

    public AccessibilityFooterPreference(Context context) {
        super(context);
        setLayoutResource(R.layout.sec_preference_accessibility_footer);
        seslSetSubheaderRoundedBackground(15);
    }
}
