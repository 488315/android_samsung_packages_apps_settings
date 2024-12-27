package com.samsung.android.settings.wifi.mobileap.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApExpandablePreference extends Preference {
    public final Context mContext;
    public WifiApEditSettings.AnonymousClass2 mOnPreferenceItemCLickListener;

    public WifiApExpandablePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mContext = context;
        setLayoutResource(R.layout.sec_wifi_ap_expandable_preference);
        setIcon(R.drawable.ic_wifi_ap_dropdown);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        LinearLayout linearLayout =
                (LinearLayout) preferenceViewHolder.findViewById(R.id.click_container);
        StringBuilder sb = new StringBuilder(textView.getText());
        sb.append(", ");
        sb.append(this.mContext.getString(R.string.button_tts));
        linearLayout.setContentDescription(sb);
        linearLayout.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.mobileap.views.WifiApExpandablePreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WifiApEditSettings.AnonymousClass2 anonymousClass2 =
                                WifiApExpandablePreference.this.mOnPreferenceItemCLickListener;
                        if (anonymousClass2 != null) {
                            SALogging.insertSALog("TETH_011", "8014");
                            WifiApEditSettings wifiApEditSettings = WifiApEditSettings.this;
                            wifiApEditSettings.mAdvancedViewExpandablePreference.setVisible(false);
                            wifiApEditSettings.mAdvancedSection1PreferenceCategory.setVisible(true);
                            wifiApEditSettings.mAdvancedSection2PreferenceCategory.setVisible(true);
                            wifiApEditSettings.mHotspotLabPreferenceCategory.setVisible(true);
                        }
                    }
                });
    }

    public WifiApExpandablePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApExpandablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApExpandablePreference(Context context) {
        this(context, null);
    }
}
