package com.android.settingslib.deviceinfo;

import android.bluetooth.BluetoothAdapter;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractBluetoothAddressPreferenceController
        extends AbstractConnectivityPreferenceController {
    public static final String[] CONNECTIVITY_INTENTS = {
        "android.bluetooth.adapter.action.STATE_CHANGED"
    };
    static final String KEY_BT_ADDRESS = "bt_address";
    public Preference mBtAddress;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mBtAddress = preferenceScreen.findPreference(KEY_BT_ADDRESS);
        updateConnectivity();
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final String[] getConnectivityIntents() {
        return CONNECTIVITY_INTENTS;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_BT_ADDRESS;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return BluetoothAdapter.getDefaultAdapter() != null;
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final void updateConnectivity() {
        final BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || this.mBtAddress == null) {
            return;
        }
        ListenableFuture submit =
                ((MoreExecutors.ListeningDecorator) ThreadUtils.getBackgroundExecutor())
                        .submit(
                                new Callable() { // from class:
                                                 // com.android.settingslib.deviceinfo.AbstractBluetoothAddressPreferenceController$$ExternalSyntheticLambda0
                                    @Override // java.util.concurrent.Callable
                                    public final Object call() {
                                        BluetoothAdapter bluetoothAdapter = defaultAdapter;
                                        if (bluetoothAdapter.isEnabled()) {
                                            return bluetoothAdapter.getAddress();
                                        }
                                        return null;
                                    }
                                });
        FutureCallback futureCallback =
                new FutureCallback() { // from class:
                                       // com.android.settingslib.deviceinfo.AbstractBluetoothAddressPreferenceController.1
                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onSuccess(Object obj) {
                        String str = (String) obj;
                        boolean isEmpty = TextUtils.isEmpty(str);
                        AbstractBluetoothAddressPreferenceController
                                abstractBluetoothAddressPreferenceController =
                                        AbstractBluetoothAddressPreferenceController.this;
                        if (isEmpty) {
                            abstractBluetoothAddressPreferenceController.mBtAddress.setSummary(
                                    R.string.status_unavailable);
                        } else {
                            abstractBluetoothAddressPreferenceController.mBtAddress.setSummary(
                                    str.toUpperCase());
                        }
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onFailure(Throwable th) {}
                };
        submit.addListener(
                new Futures.CallbackListener(submit, futureCallback),
                this.mContext.getMainExecutor());
    }
}
