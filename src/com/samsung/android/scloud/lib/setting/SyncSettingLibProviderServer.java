package com.samsung.android.scloud.lib.setting;

import android.content.ContentProvider;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import com.samsung.android.settings.scloud.SCloudSettingProvider;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SyncSettingLibProviderServer extends ContentProvider {
    public static volatile Context applicationContext;

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        if (!SamsungCloudRPCProperty.isInitCompleted) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            SamsungCloudRPCProperty.AnonymousClass1 anonymousClass1 =
                    new PropertyChangeListener() { // from class:
                                                   // com.samsung.android.scloud.lib.setting.SamsungCloudRPCProperty.1
                        public final /* synthetic */ CountDownLatch val$initCompletedCountDownLatch;

                        public AnonymousClass1(CountDownLatch countDownLatch2) {
                            r1 = countDownLatch2;
                        }

                        @Override // java.beans.PropertyChangeListener
                        public final void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                            Log.d("SamsungCloudRPCProperty", "initCompletedListner: notified");
                            SamsungCloudRPCProperty.pcs.removePropertyChangeListener(
                                    "app_initialization_completed", this);
                            r1.countDown();
                        }
                    };
            try {
                try {
                    PropertyChangeSupport propertyChangeSupport = SamsungCloudRPCProperty.pcs;
                    propertyChangeSupport.addPropertyChangeListener(
                            "app_initialization_completed", anonymousClass1);
                    countDownLatch2.await(
                            SamsungCloudRPCProperty.TIMER_INIT_COMPLETED, TimeUnit.MILLISECONDS);
                    propertyChangeSupport.removePropertyChangeListener(
                            "app_initialization_completed", anonymousClass1);
                } catch (Exception e) {
                    Log.e("SamsungCloudRPCProperty", e.getMessage());
                    SamsungCloudRPCProperty.pcs.removePropertyChangeListener(
                            "app_initialization_completed", anonymousClass1);
                }
                SamsungCloudRPCProperty.isInitCompleted = true;
            } catch (Throwable th) {
                SamsungCloudRPCProperty.pcs.removePropertyChangeListener(
                        "app_initialization_completed", anonymousClass1);
                SamsungCloudRPCProperty.isInitCompleted = true;
                throw th;
            }
        }
        Bundle bundle2 = new Bundle();
        if ("set_network_option".equals(str)) {
            if (bundle != null) {
                int i = bundle.getInt("network_option");
                Log.i("[scsettingv2][2.0.27.0]", "notifyNetworkOption: " + i);
                Log.i("SCloudSettingProvider", "notifyNetworkOption: " + i);
            }
        } else if ("set_auto_sync".equals(str)) {
            if (bundle != null) {
                boolean z = bundle.getBoolean("auto_sync");
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "notifyAutoSync: ", "[scsettingv2][2.0.27.0]", z);
                Log.i("SCloudSettingProvider", "notifyAutoSync: " + z);
                Settings.Global.putInt(
                        ((SCloudSettingProvider) this)
                                .getContext()
                                .getApplicationContext()
                                .getContentResolver(),
                        "scloud_wifi_sync_enabled",
                        z ? 1 : 0);
            }
        } else if ("is_supported".equals(str)) {
            boolean isWifiSyncEnabled =
                    SemWifiUtils.isWifiSyncEnabled(
                            ((SCloudSettingProvider) this).getContext().getApplicationContext());
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "isSupported: ", "[scsettingv2][2.0.27.0]", isWifiSyncEnabled);
            bundle2.putBoolean("supported", isWifiSyncEnabled);
        } else if ("notify_edp_state".equals(str) && bundle != null) {
            int i2 = bundle.getInt("edp_state");
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    i2, "notifyEdpStateChanged: ", "[scsettingv2][2.0.27.0]");
            Log.d("SCloudSettingProvider", "notifyEdpStateChanged :" + i2);
            Settings.Global.putInt(
                    ((SCloudSettingProvider) this)
                            .getContext()
                            .getApplicationContext()
                            .getContentResolver(),
                    "scloud_notify_edp_state_changed",
                    i2);
        }
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Log.i("[scsettingv2][2.0.27.0]", "onCreate: ");
        synchronized (SyncSettingLibProviderServer.class) {
            try {
                if (applicationContext == null) {
                    applicationContext = getContext().getApplicationContext();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }
}
