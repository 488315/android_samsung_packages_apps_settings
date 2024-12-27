package com.samsung.android.settings.wifi.p2p;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiP2pNoItemPreference extends Preference {
    public View mBaseView;
    public int mEmptyHeight;

    public WifiP2pNoItemPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = this.mBaseView;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            this.mEmptyHeight = view.getHeight();
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                this.mEmptyHeight -= viewGroup.getChildAt(i).getHeight();
            }
        }
        preferenceViewHolder.itemView.setLayoutParams(
                new ViewGroup.LayoutParams(-1, this.mEmptyHeight - 200));
        preferenceViewHolder.mDividerAllowedBelow = false;
    }
}
