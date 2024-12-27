package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.os.Bundle;
import android.view.View;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchHoldDelayTutorialFragment extends TouchSettingsTutorialFragment {
    public final TouchHoldDelayTutorialFragment$$ExternalSyntheticLambda0 mButtonClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayTutorialFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TouchHoldDelayTutorialFragment touchHoldDelayTutorialFragment =
                            TouchHoldDelayTutorialFragment.this;
                    touchHoldDelayTutorialFragment.getClass();
                    touchHoldDelayTutorialFragment.launchFragment$1(
                            R.string.touch_and_hold_delay_header,
                            TouchHoldDelayPickerFragment.class.getName());
                }
            };

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsTutorialFragment, com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5005;
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsTutorialFragment
    public final String getSharedPreferenceKey() {
        return "touch_hold_delay_tutorial";
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        setDescription(R.string.touch_and_hold_delay_tutorial_description);
        this.mSetButton.setOnClickListener(this.mButtonClickListener);
        this.mSetButton.setText(R.string.touch_and_hold_delay_tutorial_button);
        this.mImageView.setImageDrawable(
                this.mContext.getDrawable(R.drawable.tap_and_hold_delay_tutorial));
        this.mImageView.setContentDescription(
                getString(R.string.touch_and_hold_tutorial_content_description));
    }
}
