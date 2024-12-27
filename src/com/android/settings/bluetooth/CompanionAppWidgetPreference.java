package com.android.settings.bluetooth;

import android.widget.ImageButton;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CompanionAppWidgetPreference extends Preference {
    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageButton imageButton =
                (ImageButton) preferenceViewHolder.findViewById(R.id.remove_button);
        imageButton.setPadding(0, 0, 0, 0);
        imageButton.setColorFilter(getContext().getColor(android.R.color.darker_gray));
        imageButton.setImageDrawable(null);
        imageButton.setOnClickListener(null);
    }
}
