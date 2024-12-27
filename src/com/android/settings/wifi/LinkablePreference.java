package com.android.settings.wifi;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LinkablePreference extends Preference {
    public LinkablePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setIcon(R.drawable.ic_info_outline_24dp);
        setSelectable(false);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        if (textView == null) {
            return;
        }
        textView.setSingleLine(false);
    }

    public LinkablePreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.footerPreferenceStyle, android.R.attr.preferenceStyle));
    }
}
