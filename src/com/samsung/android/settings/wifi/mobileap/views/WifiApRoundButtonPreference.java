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
public class WifiApRoundButtonPreference extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final AnonymousClass1 mOnButtonClickListener;
    public Preference.OnPreferenceClickListener mOnPreferenceClickListener;
    public String mText;
    public final WifiApRoundButtonPreference mThisPreference;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.settings.wifi.mobileap.views.WifiApRoundButtonPreference$1] */
    public WifiApRoundButtonPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOnButtonClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.mobileap.views.WifiApRoundButtonPreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i3 = WifiApRoundButtonPreference.$r8$clinit;
                        Log.i("WifiApRoundButtonPreference", "Button Clicked");
                        WifiApRoundButtonPreference wifiApRoundButtonPreference =
                                WifiApRoundButtonPreference.this;
                        wifiApRoundButtonPreference.mOnPreferenceClickListener.onPreferenceClick(
                                wifiApRoundButtonPreference.mThisPreference);
                    }
                };
        this.mContext = context;
        this.mThisPreference = this;
        setLayoutResource(R.layout.sec_wifi_ap_round_button_layout);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Button button = (Button) preferenceViewHolder.findViewById(R.id.button);
        button.setText(this.mText);
        button.setOnClickListener(this.mOnButtonClickListener);
    }

    @Override // androidx.preference.Preference
    public final void setOnPreferenceClickListener(
            Preference.OnPreferenceClickListener onPreferenceClickListener) {
        super.setOnPreferenceClickListener(onPreferenceClickListener);
        this.mOnPreferenceClickListener = onPreferenceClickListener;
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

    public WifiApRoundButtonPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApRoundButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApRoundButtonPreference(Context context) {
        this(context, null);
    }
}
