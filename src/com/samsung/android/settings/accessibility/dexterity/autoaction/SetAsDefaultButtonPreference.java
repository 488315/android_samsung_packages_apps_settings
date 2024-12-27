package com.samsung.android.settings.accessibility.dexterity.autoaction;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.accessibility.base.widget.BodyContainedButton;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SetAsDefaultButtonPreference extends Preference {
    public final Context mContext;

    public SetAsDefaultButtonPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        setLayoutResource(R.layout.preference_default_button);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        BodyContainedButton bodyContainedButton =
                (BodyContainedButton) preferenceViewHolder.itemView.findViewById(R.id.default_btn);
        if (bodyContainedButton != null) {
            bodyContainedButton.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.accessibility.dexterity.autoaction.SetAsDefaultButtonPreference$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SetAsDefaultButtonPreference setAsDefaultButtonPreference =
                                    SetAsDefaultButtonPreference.this;
                            AutoActionUtils.autoActionSetAsDefault(
                                    setAsDefaultButtonPreference.mContext);
                            view.announceForAccessibility(
                                    setAsDefaultButtonPreference.mContext.getString(
                                            R.string.settings_reset_feedback));
                            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                            if (featureFactoryImpl == null) {
                                throw new UnsupportedOperationException(
                                        "No feature factory configured");
                            }
                            featureFactoryImpl
                                    .getA11ySettingsMetricsFeatureProvider()
                                    .clicked(5008, "A11Y5023");
                        }
                    });
        }
    }
}
