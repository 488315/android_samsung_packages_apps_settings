package com.samsung.android.settings.wifi;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SeslProgressBar;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SecWifiButtonPreference extends Preference {
    public LinearLayout mButton;
    public String mButtonText;
    public View.OnClickListener mOnClickListener;
    public SeslProgressBar mProgressBar;
    public boolean mProgressVisible;
    public TextView mTitleView;

    public SecWifiButtonPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void initPreference(PreferenceViewHolder preferenceViewHolder) {
        preferenceViewHolder.itemView.setSoundEffectsEnabled(false);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        LinearLayout linearLayout =
                (LinearLayout)
                        preferenceViewHolder.itemView.findViewById(R.id.shared_password_container);
        this.mButton = linearLayout;
        linearLayout.setOnClickListener(this.mOnClickListener);
        this.mButton.setVisibility(0);
        this.mButton.setContentDescription(
                this.mButtonText + ", " + getContext().getString(R.string.button_tts));
        this.mTitleView = (TextView) preferenceViewHolder.itemView.findViewById(R.id.title);
        if (!TextUtils.isEmpty(this.mButtonText)) {
            this.mTitleView.setText(this.mButtonText);
        }
        this.mProgressBar = (SeslProgressBar) preferenceViewHolder.findViewById(R.id.progress_bar);
    }

    public final void setProgressVisibility(boolean z) {
        SeslProgressBar seslProgressBar = this.mProgressBar;
        if (seslProgressBar == null || this.mTitleView == null) {
            return;
        }
        this.mProgressVisible = z;
        if (z) {
            seslProgressBar.setVisibility(0);
            this.mTitleView.setVisibility(8);
            this.mProgressBar.setProgressInternal(0, false, true);
        } else {
            seslProgressBar.setVisibility(8);
            this.mTitleView.setVisibility(0);
        }
        boolean z2 = !this.mProgressVisible;
        LinearLayout linearLayout = this.mButton;
        if (linearLayout != null) {
            linearLayout.setAlpha(z2 ? 1.0f : 0.4f);
        }
        notifyChanged();
    }
}
