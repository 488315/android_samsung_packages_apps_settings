package com.android.settings.wifi.dpp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManager;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiDppChooseSavedWifiNetworkFragment extends WifiDppQrCodeBaseFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1595;
    }

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment
    public final boolean isFooterAvailable() {
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        FragmentManager childFragmentManager = getChildFragmentManager();
        WifiNetworkListFragment wifiNetworkListFragment = new WifiNetworkListFragment();
        Bundle arguments = getArguments();
        if (arguments != null) {
            wifiNetworkListFragment.setArguments(arguments);
        }
        childFragmentManager.getClass();
        BackStackRecord backStackRecord = new BackStackRecord(childFragmentManager);
        backStackRecord.replace(
                R.id.wifi_network_list_container,
                wifiNetworkListFragment,
                "wifi_network_list_fragment");
        backStackRecord.commitInternal(false);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(
                R.layout.wifi_dpp_choose_saved_wifi_network_fragment, viewGroup, false);
    }

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment,
              // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setHeaderTitle(R.string.wifi_dpp_choose_network, new Object[0]);
        this.mSummary.setText(R.string.wifi_dpp_choose_network_to_connect_device);
        this.mLeftButton.setText(getContext(), R.string.cancel);
        this.mLeftButton.onClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.wifi.dpp.WifiDppChooseSavedWifiNetworkFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        WifiDppChooseSavedWifiNetworkFragment
                                wifiDppChooseSavedWifiNetworkFragment =
                                        WifiDppChooseSavedWifiNetworkFragment.this;
                        Intent intent =
                                wifiDppChooseSavedWifiNetworkFragment.getActivity().getIntent();
                        String action = intent != null ? intent.getAction() : null;
                        if ("android.settings.WIFI_DPP_CONFIGURATOR_QR_CODE_SCANNER".equals(action)
                                || "android.settings.WIFI_DPP_CONFIGURATOR_QR_CODE_GENERATOR"
                                        .equals(action)) {
                            wifiDppChooseSavedWifiNetworkFragment
                                    .getFragmentManager()
                                    .popBackStack();
                        } else {
                            wifiDppChooseSavedWifiNetworkFragment.getActivity().finish();
                        }
                    }
                };
        this.mRightButton.setVisibility(8);
    }
}
