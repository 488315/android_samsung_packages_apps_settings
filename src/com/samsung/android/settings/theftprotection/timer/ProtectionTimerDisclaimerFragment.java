package com.samsung.android.settings.theftprotection.timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.theftprotection.TheftProtectionConstants;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ProtectionTimerDisclaimerFragment extends SettingsPreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Bundle mBundle;
    public String mLoggingEventValue;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 54104;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ((SettingsActivity) getActivity()).hideAppBar();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(
                        R.layout.protection_timer_disclaimer_layout, viewGroup, false);
        Bundle bundleExtra = getIntent().getBundleExtra("pp_security_delay_bundle");
        this.mBundle = bundleExtra;
        if (bundleExtra != null) {
            String string = bundleExtra.getString("pp_package_name");
            int i = this.mBundle.getInt("pp_security_delay_title_res");
            if (i >= 0) {
                ((TextView) inflate.findViewById(R.id.disclaimer_title))
                        .setText(
                                TheftProtectionUtils.getStringFromPackage(getContext(), i, string));
            }
            this.mLoggingEventValue =
                    this.mBundle.getString("pp_caller_name", ApnSettings.MVNO_NONE);
        }
        ProtectionTimerTimeView protectionTimerTimeView =
                (ProtectionTimerTimeView) inflate.findViewById(R.id.timer_time_view);
        TheftProtectionUtils.isSecurityDelayTest();
        protectionTimerTimeView.setTimeTextView(
                TheftProtectionConstants.SECURITY_DELAY_DURATION_MILLIS);
        Utils.setMaxFontScale$1(
                getContext(), (TextView) inflate.findViewById(R.id.timer_delay_description));
        TheftProtectionUtils.setLinkableText((TextView) inflate.findViewById(R.id.description));
        return inflate;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Button button = (Button) view.findViewById(R.id.button);
        button.setText(R.string.protection_timer_disclaimer_start_timer);
        button.setOnClickListener(
                new View.OnClickListener() { // from class:
                    // com.samsung.android.settings.theftprotection.timer.ProtectionTimerDisclaimerFragment$$ExternalSyntheticLambda0
                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    /* JADX WARN: Code restructure failed: missing block: B:26:0x00e9, code lost:

                       if (r7.equals("ChangeLockscreen") == false) goto L7;
                    */
                    @Override // android.view.View.OnClickListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onClick(android.view.View r8) {
                        /*
                            Method dump skipped, instructions count: 364
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.theftprotection.timer.ProtectionTimerDisclaimerFragment$$ExternalSyntheticLambda0.onClick(android.view.View):void");
                    }
                });
    }
}
