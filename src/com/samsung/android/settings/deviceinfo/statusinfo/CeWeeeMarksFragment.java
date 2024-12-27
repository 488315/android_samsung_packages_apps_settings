package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoFragment;
import com.samsung.android.settings.widget.SecRoundButtonView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CeWeeeMarksFragment extends SettingsPreferenceFragment {
    public FragmentActivity mContext;
    public LinearLayout mDescView;
    public SecRoundButtonView mRegulatoryButton;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 40;
    }

    public final void initDescriptionViewLayout() {
        LinearLayout linearLayout = this.mDescView;
        if (linearLayout == null || this.mRegulatoryButton == null) {
            return;
        }
        linearLayout.setVisibility(0);
        this.mRegulatoryButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.deviceinfo.statusinfo.CeWeeeMarksFragment.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(CeWeeeMarksFragment.this.mContext);
                        CeWeeeMarksFragment.this.getClass();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mSourceMetricsCategory = 40;
                        launchRequest.mDestinationName =
                                SecRegulatoryInfoFragment.class.getCanonicalName();
                        subSettingLauncher.setTitleRes(R.string.regulatory_information, null);
                        subSettingLauncher.launch();
                    }
                });
        double width =
                ((WindowManager) this.mContext.getSystemService("window"))
                        .getDefaultDisplay()
                        .getWidth();
        this.mRegulatoryButton.setMinWidth((int) (0.61d * width));
        this.mRegulatoryButton.setMaxWidth((int) (width * 0.75d));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(0, 0, 0, (int) (r0.getDefaultDisplay().getHeight() * 0.05d));
        this.mDescView.setLayoutParams(layoutParams);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initDescriptionViewLayout();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_ce_weee_marks, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.container);
        if (findViewById != null) {
            findViewById.semSetRoundedCorners(15);
            findViewById.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        if (SemCscFeature.getInstance().getBoolean("CscFeature_Setting_SupportRegulatoryInfo")) {
            this.mDescView =
                    (LinearLayout)
                            inflate.findViewById(R.id.ce_and_weee_marks_additional_description);
            this.mRegulatoryButton =
                    (SecRoundButtonView)
                            inflate.findViewById(R.id.regulatory_information_link_button);
            initDescriptionViewLayout();
        }
        return inflate;
    }
}
