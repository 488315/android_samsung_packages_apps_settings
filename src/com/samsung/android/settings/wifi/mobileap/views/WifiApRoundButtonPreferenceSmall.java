package com.samsung.android.settings.wifi.mobileap.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApRoundButtonPreferenceSmall extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final AnonymousClass1 mOnButtonClickListener;
    public String mText;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.views.WifiApRoundButtonPreferenceSmall$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnClickListener {
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            int i = WifiApRoundButtonPreferenceSmall.$r8$clinit;
            Log.i("WifiApRoundButtonPreferenceSmall", "Button Clicked");
        }
    }

    public WifiApRoundButtonPreferenceSmall(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        this.mOnButtonClickListener = new AnonymousClass1();
        this.mContext = context;
        setLayoutResource(R.layout.sec_wifi_ap_round_button_layout_small);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Button button = (Button) preferenceViewHolder.findViewById(R.id.button);
        button.setText(this.mText);
        button.setOnClickListener(this.mOnButtonClickListener);
    }

    @Override // androidx.preference.Preference
    public final void setTitle(int i) {
        this.mText = this.mContext.getString(i);
        notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final void setTitle(CharSequence charSequence) {
        this.mText = (String) charSequence;
        notifyChanged();
    }
}
