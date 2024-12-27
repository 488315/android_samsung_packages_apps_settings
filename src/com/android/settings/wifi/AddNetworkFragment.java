package com.android.settings.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.wifi.dpp.WifiDppUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AddNetworkFragment extends InstrumentedFragment
        implements WifiConfigUiBase2, View.OnClickListener {
    static final int CANCEL_BUTTON_ID = 16908314;
    static final int SUBMIT_BUTTON_ID = 16908313;
    public Button mCancelBtn;
    public Button mSubmitBtn;
    public WifiConfigController2 mUIController;

    public static boolean isAddWifiConfigAllowed(Context context) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager == null || !userManager.hasUserRestriction("no_add_wifi_config")) {
            return true;
        }
        Log.e("AddNetworkFragment", "The user is not allowed to add Wi-Fi configuration.");
        return false;
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void dispatchSubmit() {
        handleSubmitAction();
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final Button getForgetButton() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1556;
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final Button getSubmitButton() {
        return this.mSubmitBtn;
    }

    public void handleCancelAction() {
        FragmentActivity activity = getActivity();
        activity.setResult(0);
        activity.finish();
    }

    public void handleSubmitAction() {
        successfullyFinish(this.mUIController.getConfig());
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 0 && i2 == -1) {
            successfullyFinish(
                    (WifiConfiguration) intent.getParcelableExtra("key_wifi_configuration"));
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == 16908313) {
            handleSubmitAction();
            return;
        }
        if (view.getId() == 16908314) {
            handleCancelAction();
        } else if (view.getId() == R.id.ssid_scanner_button) {
            startActivityForResult(
                    WifiDppUtils.getEnrolleeQrCodeScannerIntent(
                            view.getContext(),
                            ((TextView) getView().findViewById(R.id.ssid)).getText().toString()),
                    0);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isAddWifiConfigAllowed(getContext())) {
            return;
        }
        getActivity().finish();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.wifi_add_network_view, viewGroup, false);
        Button button = (Button) inflate.findViewById(android.R.id.button3);
        if (button != null) {
            button.setVisibility(8);
        }
        this.mSubmitBtn = (Button) inflate.findViewById(16908313);
        this.mCancelBtn = (Button) inflate.findViewById(16908314);
        ImageButton imageButton = (ImageButton) inflate.findViewById(R.id.ssid_scanner_button);
        this.mSubmitBtn.setOnClickListener(this);
        this.mCancelBtn.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        this.mUIController = new WifiConfigController2(this, inflate, null, 1, false);
        getActivity().getWindow().setSoftInputMode(16);
        return inflate;
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mUIController.showSecurityFields(false, true);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        this.mUIController.updatePassword();
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setCancelButton(CharSequence charSequence) {
        this.mCancelBtn.setText(charSequence);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setSubmitButton(CharSequence charSequence) {
        this.mSubmitBtn.setText(charSequence);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setTitle(int i) {
        getActivity().setTitle(i);
    }

    public final void successfullyFinish(WifiConfiguration wifiConfiguration) {
        final FragmentActivity activity = getActivity();
        if (activity.getIntent().getBooleanExtra(":settings:save_when_submit", false)
                && wifiConfiguration != null) {
            ((WifiManager) activity.getSystemService(WifiManager.class))
                    .save(
                            wifiConfiguration,
                            new WifiManager
                                    .ActionListener() { // from class:
                                                        // com.android.settings.wifi.AddNetworkFragment.1
                                public final void onFailure(int i) {
                                    Activity activity2 = activity;
                                    if (activity2 == null || activity2.isFinishing()) {
                                        return;
                                    }
                                    Toast.makeText(activity, R.string.wifi_failed_save_message, 0)
                                            .show();
                                    activity.finish();
                                }

                                public final void onSuccess() {
                                    Activity activity2 = activity;
                                    if (activity2 == null || activity2.isFinishing()) {
                                        return;
                                    }
                                    activity.setResult(-1);
                                    activity.finish();
                                }
                            });
        } else {
            Intent intent = new Intent();
            intent.putExtra("wifi_config_key", wifiConfiguration);
            activity.setResult(-1, intent);
            activity.finish();
        }
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setTitle(CharSequence charSequence) {
        getActivity().setTitle(charSequence);
    }

    @Override // com.android.settings.wifi.WifiConfigUiBase2
    public final void setForgetButton(CharSequence charSequence) {}
}
