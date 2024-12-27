package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.google.android.material.button.MaterialButton;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryTipsCardPreference extends Preference implements View.OnClickListener {
    public int mButtonColorResourceId;
    CharSequence mDismissButtonLabel;
    public int mIconResourceId;
    CharSequence mMainButtonLabel;
    public BatteryTipsController$$ExternalSyntheticLambda0 mOnConfirmListener;
    public BatteryTipsController$$ExternalSyntheticLambda0 mOnRejectListener;

    public BatteryTipsCardPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIconResourceId = 0;
        this.mButtonColorResourceId = 0;
        setLayoutResource(R.layout.battery_tips_card);
        setViewId();
        setSelectable(false);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ((TextView) preferenceViewHolder.findViewById(R.id.title)).setText(getTitle());
        ((LinearLayout) preferenceViewHolder.findViewById(R.id.battery_tips_card))
                .setOnClickListener(this);
        MaterialButton materialButton =
                (MaterialButton) preferenceViewHolder.findViewById(R.id.main_button);
        materialButton.setOnClickListener(this);
        materialButton.setText(this.mMainButtonLabel);
        MaterialButton materialButton2 =
                (MaterialButton) preferenceViewHolder.findViewById(R.id.dismiss_button);
        materialButton2.setOnClickListener(this);
        materialButton2.setText(this.mDismissButtonLabel);
        if (this.mButtonColorResourceId != 0) {
            int color = getContext().getColor(this.mButtonColorResourceId);
            materialButton.setBackgroundColor(color);
            materialButton2.setTextColor(color);
        }
        if (this.mIconResourceId != 0) {
            ((ImageView) preferenceViewHolder.findViewById(R.id.icon))
                    .setImageResource(this.mIconResourceId);
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        BatteryTipsController$$ExternalSyntheticLambda0
                batteryTipsController$$ExternalSyntheticLambda0;
        int id = view.getId();
        if (id == R.id.main_button || id == R.id.battery_tips_card) {
            BatteryTipsController$$ExternalSyntheticLambda0
                    batteryTipsController$$ExternalSyntheticLambda02 = this.mOnConfirmListener;
            if (batteryTipsController$$ExternalSyntheticLambda02 != null) {
                batteryTipsController$$ExternalSyntheticLambda02.f$0
                        .lambda$handleBatteryTipsCardUpdated$0(
                                batteryTipsController$$ExternalSyntheticLambda02.f$1);
                return;
            }
            return;
        }
        if (id != R.id.dismiss_button
                || (batteryTipsController$$ExternalSyntheticLambda0 = this.mOnRejectListener)
                        == null) {
            return;
        }
        batteryTipsController$$ExternalSyntheticLambda0.f$0.lambda$handleBatteryTipsCardUpdated$1(
                batteryTipsController$$ExternalSyntheticLambda0.f$1);
    }
}
