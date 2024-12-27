package com.samsung.android.settings.password;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PreviewChooseLockGenericFromBiometrics$PreviewChooseLockGenericFromBiometricsFramgment
        extends SettingsPreferenceFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        seslSetRoundedCorner(false);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
                Preference preference = preferenceScreen.getPreference(i);
                CharSequence title = preference.getTitle();
                if (title != null) {
                    SpannableString spannableString = new SpannableString(title.toString());
                    spannableString.setSpan(
                            new ForegroundColorSpan(
                                    getResources()
                                            .getColor(
                                                    R.color.sec_widget_alert_dialog_text_color,
                                                    null)),
                            0,
                            spannableString.length(),
                            0);
                    preference.setTitle(spannableString);
                }
                preference.seslSetSubheaderColor(
                        getResources().getColor(R.color.sec_widget_alert_dialog_text_color, null));
            }
        }
        if (getListView() != null) {
            getListView().seslSetFillBottomEnabled(false);
        }
    }
}
