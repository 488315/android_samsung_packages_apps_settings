package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.os.Bundle;
import android.view.View;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TapDurationTutorialFragment extends TouchSettingsTutorialFragment {
    public final TapDurationTutorialFragment$$ExternalSyntheticLambda0 mButtonClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationTutorialFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TapDurationTutorialFragment tapDurationTutorialFragment =
                            TapDurationTutorialFragment.this;
                    tapDurationTutorialFragment.getClass();
                    tapDurationTutorialFragment.launchFragment$1(
                            R.string.tap_duration_title, TapDurationPickerFragment.class.getName());
                }
            };

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsTutorialFragment, com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5005;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        setDescription(R.string.tap_duration_tutorial_description);
        this.mSetButton.setOnClickListener(this.mButtonClickListener);
        this.mSetButton.setText(R.string.tap_duration_tutorial_button);
        this.mImageView.setImageDrawable(
                this.mContext.getDrawable(R.drawable.tap_duration_tutorial));
    }
}
