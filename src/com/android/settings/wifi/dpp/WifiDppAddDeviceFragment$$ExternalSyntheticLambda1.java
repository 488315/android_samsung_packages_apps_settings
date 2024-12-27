package com.android.settings.wifi.dpp;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiDppAddDeviceFragment$$ExternalSyntheticLambda1
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiDppAddDeviceFragment f$0;

    public /* synthetic */ WifiDppAddDeviceFragment$$ExternalSyntheticLambda1(
            WifiDppAddDeviceFragment wifiDppAddDeviceFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiDppAddDeviceFragment;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        WifiDppAddDeviceFragment wifiDppAddDeviceFragment = this.f$0;
        switch (i) {
            case 0:
                ((WifiDppConfiguratorActivity)
                                wifiDppAddDeviceFragment.mClickChooseDifferentNetworkListener)
                        .showChooseSavedWifiNetworkFragment(true);
                break;
            case 1:
                wifiDppAddDeviceFragment.getActivity().finish();
                break;
            case 2:
                wifiDppAddDeviceFragment.setProgressBarShown(true);
                wifiDppAddDeviceFragment.mRightButton.setVisibility(4);
                wifiDppAddDeviceFragment.startWifiDppConfiguratorInitiator();
                wifiDppAddDeviceFragment.updateSummary$2$1();
                break;
            case 3:
                wifiDppAddDeviceFragment.getFragmentManager().popBackStack();
                break;
            default:
                FragmentActivity activity = wifiDppAddDeviceFragment.getActivity();
                activity.setResult(-1);
                activity.finish();
                break;
        }
    }
}
