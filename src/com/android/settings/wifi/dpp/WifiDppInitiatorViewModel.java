package com.android.settings.wifi.dpp;

import android.app.Application;
import android.net.wifi.EasyConnectStatusCallback;
import android.util.SparseArray;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiDppInitiatorViewModel extends AndroidViewModel {
    public int[] mBandArray;
    public MutableLiveData mEnrolleeSuccessNetworkId;
    public boolean mIsWifiDppHandshaking;
    public MutableLiveData mStatusCode;
    public SparseArray mTriedChannels;
    public String mTriedSsid;

    public WifiDppInitiatorViewModel(Application application) {
        super(application);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EasyConnectDelegateCallback extends EasyConnectStatusCallback {
        public EasyConnectDelegateCallback() {}

        public final void onConfiguratorSuccess(int i) {
            WifiDppInitiatorViewModel wifiDppInitiatorViewModel = WifiDppInitiatorViewModel.this;
            wifiDppInitiatorViewModel.mIsWifiDppHandshaking = false;
            wifiDppInitiatorViewModel.mStatusCode.setValue(1);
        }

        public final void onEnrolleeSuccess(int i) {
            WifiDppInitiatorViewModel wifiDppInitiatorViewModel = WifiDppInitiatorViewModel.this;
            wifiDppInitiatorViewModel.mIsWifiDppHandshaking = false;
            wifiDppInitiatorViewModel.mEnrolleeSuccessNetworkId.setValue(Integer.valueOf(i));
        }

        public final void onFailure(int i, String str, SparseArray sparseArray, int[] iArr) {
            WifiDppInitiatorViewModel wifiDppInitiatorViewModel = WifiDppInitiatorViewModel.this;
            wifiDppInitiatorViewModel.mIsWifiDppHandshaking = false;
            wifiDppInitiatorViewModel.mTriedSsid = str;
            wifiDppInitiatorViewModel.mTriedChannels = sparseArray;
            wifiDppInitiatorViewModel.mBandArray = iArr;
            wifiDppInitiatorViewModel.mStatusCode.setValue(Integer.valueOf(i));
        }

        public final void onProgress(int i) {}
    }
}
