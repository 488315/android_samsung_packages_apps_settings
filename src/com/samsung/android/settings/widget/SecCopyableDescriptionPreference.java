package com.samsung.android.settings.widget;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecCopyableDescriptionPreference extends Preference {
    public SecCopyableDescriptionPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        preferenceViewHolder.mDividerAllowedAbove = true;
        preferenceViewHolder.mDividerAllowedBelow = true;
        view.setLongClickable(true);
        view.setOnLongClickListener(
                new View
                        .OnLongClickListener() { // from class:
                                                 // com.samsung.android.settings.widget.SecCopyableDescriptionPreference.1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view2) {
                        ((ClipboardManager)
                                        SecCopyableDescriptionPreference.this
                                                .getContext()
                                                .getSystemService("clipboard"))
                                .setText(SecCopyableDescriptionPreference.this.getSummary());
                        return true;
                    }
                });
    }

    public SecCopyableDescriptionPreference(Context context) {
        this(context, null);
    }
}
