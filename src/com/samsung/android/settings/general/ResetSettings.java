package com.samsung.android.settings.general;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.system.ResetDashboardFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ResetSettings extends ResetSettingsPreferenceFragment {
    public View mContentView;
    public TextView mResetListView;

    public final void establishResetState() {
        this.mResetListView = (TextView) this.mContentView.findViewById(R.id.reset_list);
        boolean isRTL = Utils.isRTL(getActivity());
        StringBuilder sb = new StringBuilder("• ");
        sb.append(
                BidiFormatter.getInstance()
                        .unicodeWrap(
                                getResources().getString(R.string.reset_security_settings),
                                isRTL ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR));
        sb.append("\n• ");
        sb.append(
                BidiFormatter.getInstance()
                        .unicodeWrap(
                                getResources().getString(R.string.reset_language_settings),
                                isRTL ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR));
        sb.append("\n• ");
        sb.append(
                BidiFormatter.getInstance()
                        .unicodeWrap(
                                getResources().getString(R.string.account_dashboard_title),
                                isRTL ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR));
        sb.append("\n• ");
        sb.append(
                BidiFormatter.getInstance()
                        .unicodeWrap(
                                getResources().getString(R.string.reset_personal_data),
                                isRTL ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR));
        sb.append("\n• ");
        sb.append(
                BidiFormatter.getInstance()
                        .unicodeWrap(
                                getResources().getString(R.string.reset_download_apps),
                                isRTL ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR));
        this.mResetListView.setText(sb.toString());
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return ResetDashboardFragment.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4660;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final String getResetButtonTitle() {
        return getString(R.string.reset_network_button_text);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_general";
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 58 || i == 55) {
            if (i == 55) {
                if (i2 == -1) {
                    showFinalConfirmation$2();
                    return;
                } else {
                    establishResetState();
                    return;
                }
            }
            if (i != 58) {
                return;
            }
            if (i2 == -1) {
                showFinalConfirmation$2();
            } else {
                establishResetState();
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContentView = layoutInflater.inflate(R.layout.sec_reset_settings, viewGroup, false);
        establishResetState();
        return this.mContentView;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final void onResetButtonClick() {
        Resources resources = getActivity().getResources();
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(getActivity(), this);
        builder.mRequestCode = 55;
        builder.mTitle = resources.getText(R.string.sec_settings_reset_title);
        if (builder.show()) {
            return;
        }
        showFinalConfirmation$2();
    }

    public final void showFinalConfirmation$2() {
        Bundle bundle = new Bundle();
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = ResetSettingsConfirm.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.sec_settings_reset_title, null);
        launchRequest.mSourceMetricsCategory = 4661;
        subSettingLauncher.launch();
    }
}
