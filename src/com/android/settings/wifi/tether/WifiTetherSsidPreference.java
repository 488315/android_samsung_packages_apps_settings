package com.android.settings.wifi.tether;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.preference.PreferenceViewHolder;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.widget.ValidatedEditTextPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiTetherSsidPreference extends ValidatedEditTextPreference {
    public View.OnClickListener mClickListener;
    public Drawable mShareIconDrawable;
    public boolean mVisible;

    public WifiTetherSsidPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initialize$2$1();
    }

    public final void initialize$2$1() {
        Drawable drawable;
        setLayoutResource(R.layout.preference_two_target);
        setWidgetLayoutResource(R.layout.wifi_button_preference_widget);
        try {
            drawable = getContext().getDrawable(R.drawable.ic_qrcode_24dp);
        } catch (Resources.NotFoundException unused) {
            Log.e("WifiTetherSsidPreference", "Resource does not exist: 2131232115");
            drawable = null;
        }
        this.mShareIconDrawable = drawable;
    }

    @VisibleForTesting
    public boolean isQrCodeButtonAvailable() {
        return this.mVisible && this.mClickListener != null;
    }

    @Override // com.android.settings.widget.ValidatedEditTextPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageButton imageButton = (ImageButton) preferenceViewHolder.findViewById(R.id.button_icon);
        View findViewById = preferenceViewHolder.findViewById(R.id.two_target_divider);
        if (!this.mVisible) {
            imageButton.setVisibility(8);
            findViewById.setVisibility(8);
            return;
        }
        imageButton.setOnClickListener(this.mClickListener);
        imageButton.setVisibility(0);
        imageButton.setContentDescription(getContext().getString(R.string.wifi_dpp_share_hotspot));
        imageButton.setImageDrawable(this.mShareIconDrawable);
        findViewById.setVisibility(0);
    }

    public WifiTetherSsidPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize$2$1();
    }

    public WifiTetherSsidPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize$2$1();
    }

    public WifiTetherSsidPreference(Context context) {
        super(context);
        initialize$2$1();
    }
}
