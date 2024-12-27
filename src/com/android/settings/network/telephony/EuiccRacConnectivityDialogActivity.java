package com.android.settings.network.telephony;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EuiccRacConnectivityDialogActivity extends FragmentActivity
        implements WarningDialogFragment.OnConfirmListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public Intent mResetMobileNetworkIntent;
    public int mSubId;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.mSubId = intent.getIntExtra("sub_id", -1);
        this.mResetMobileNetworkIntent =
                (Intent) intent.getParcelableExtra("reset_mobile_netword_id", Intent.class);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        if (bundle == null) {
            int i = this.mResetMobileNetworkIntent != null ? 1916 : 1915;
            String string = getString(R.string.wifi_warning_dialog_title);
            String string2 = getString(R.string.wifi_warning_dialog_text);
            String string3 = getString(R.string.wifi_warning_continue_button);
            String string4 = getString(R.string.wifi_warning_return_button);
            WarningDialogFragment warningDialogFragment = new WarningDialogFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString(UniversalCredentialUtil.AGENT_TITLE, string);
            bundle2.putCharSequence("msg", string2);
            bundle2.putString("pos_button_string", string3);
            bundle2.putString("neg_button_string", string4);
            BaseDialogFragment.setListener(
                    this, WarningDialogFragment.OnConfirmListener.class, i, bundle2);
            warningDialogFragment.setArguments(bundle2);
            warningDialogFragment.show(getSupportFragmentManager(), "WarningDialogFragment");
        }
    }
}
