package com.android.settings.accessibility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FlashNotificationsPreviewPreference extends Preference {
    public Drawable mBackgroundDisabled;
    public Drawable mBackgroundEnabled;
    public int mTextColorDisabled;

    public FlashNotificationsPreviewPreference(Context context) {
        super(context);
        init$10();
    }

    public final void init$10() {
        setLayoutResource(R.layout.flash_notification_preview_preference);
        this.mBackgroundEnabled = getContext().getDrawable(R.drawable.settingslib_switch_bar_bg_on);
        this.mBackgroundDisabled = getContext().getDrawable(R.drawable.switch_bar_bg_disabled);
        this.mTextColorDisabled =
                Utils.getColorAttrDefaultColor(getContext(), android.R.attr.textColorPrimary);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        boolean isEnabled = isEnabled();
        View findViewById = preferenceViewHolder.findViewById(R.id.frame);
        if (findViewById != null) {
            findViewById.setBackground(
                    isEnabled ? this.mBackgroundEnabled : this.mBackgroundDisabled);
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        if (textView != null) {
            int currentTextColor = textView.getCurrentTextColor();
            textView.setAlpha(isEnabled ? 1.0f : 0.38f);
            if (!isEnabled) {
                currentTextColor = this.mTextColorDisabled;
            }
            textView.setTextColor(currentTextColor);
        }
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        notifyChanged();
    }

    public FlashNotificationsPreviewPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init$10();
    }

    public FlashNotificationsPreviewPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init$10();
    }

    public FlashNotificationsPreviewPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init$10();
    }
}
