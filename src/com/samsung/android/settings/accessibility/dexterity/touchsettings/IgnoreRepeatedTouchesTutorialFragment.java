package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.os.Bundle;
import android.view.View;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class IgnoreRepeatedTouchesTutorialFragment extends TouchSettingsTutorialFragment {
    public final IgnoreRepeatedTouchesTutorialFragment$$ExternalSyntheticLambda0
            mButtonClickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesTutorialFragment$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            IgnoreRepeatedTouchesTutorialFragment
                                    ignoreRepeatedTouchesTutorialFragment =
                                            IgnoreRepeatedTouchesTutorialFragment.this;
                            ignoreRepeatedTouchesTutorialFragment.getClass();
                            ignoreRepeatedTouchesTutorialFragment.launchFragment$1(
                                    R.string.accessibility_ignore_repeat,
                                    IgnoreRepeatedTouchesPickerFragment.class.getName());
                        }
                    };

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsTutorialFragment, com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5005;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        setDescription(R.string.accessibility_ignore_repeat_description);
        this.mSetButton.setOnClickListener(this.mButtonClickListener);
        this.mSetButton.setText(R.string.ignore_repeated_touches_tutorial_button);
        this.mImageView.setImageDrawable(
                this.mContext.getDrawable(R.drawable.ignore_repeated_touches_tutorial));
    }
}
