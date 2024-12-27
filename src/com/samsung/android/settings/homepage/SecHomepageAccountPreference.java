package com.samsung.android.settings.homepage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.widget.HomepagePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecHomepageAccountPreference extends HomepagePreference {
    public int mType;

    public SecHomepageAccountPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mType = 1;
    }

    @Override // com.android.settings.widget.HomepagePreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mType == 3) {
            preferenceViewHolder.itemView.setFocusable(false);
            View findViewById = preferenceViewHolder.findViewById(R.id.text_frame);
            findViewById.setFocusable(true);
            findViewById.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.homepage.SecHomepageAccountPreference$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SecHomepageAccountPreference.this.performClick();
                        }
                    });
        }
    }

    public SecHomepageAccountPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mType = 1;
    }

    public SecHomepageAccountPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mType = 1;
    }

    public SecHomepageAccountPreference(Context context) {
        super(context);
        this.mType = 1;
    }
}
