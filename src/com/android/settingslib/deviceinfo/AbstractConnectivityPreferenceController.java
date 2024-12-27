package com.android.settingslib.deviceinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import com.android.internal.util.ArrayUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractConnectivityPreferenceController extends AbstractPreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    public final AnonymousClass1 mConnectivityReceiver;
    public ConnectivityEventHandler mHandler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectivityEventHandler extends Handler {
        public WeakReference mPreferenceController;

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            AbstractConnectivityPreferenceController abstractConnectivityPreferenceController =
                    (AbstractConnectivityPreferenceController) this.mPreferenceController.get();
            if (abstractConnectivityPreferenceController == null) {
                return;
            }
            if (message.what == 600) {
                abstractConnectivityPreferenceController.updateConnectivity();
            } else {
                throw new IllegalStateException("Unknown message " + message.what);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController$1] */
    public AbstractConnectivityPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        this.mConnectivityReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (ArrayUtils.contains(
                                AbstractConnectivityPreferenceController.this
                                        .getConnectivityIntents(),
                                intent.getAction())) {
                            AbstractConnectivityPreferenceController
                                    abstractConnectivityPreferenceController =
                                            AbstractConnectivityPreferenceController.this;
                            if (abstractConnectivityPreferenceController.mHandler == null) {
                                ConnectivityEventHandler connectivityEventHandler =
                                        new ConnectivityEventHandler();
                                connectivityEventHandler.mPreferenceController =
                                        new WeakReference(abstractConnectivityPreferenceController);
                                abstractConnectivityPreferenceController.mHandler =
                                        connectivityEventHandler;
                            }
                            abstractConnectivityPreferenceController.mHandler.sendEmptyMessage(600);
                        }
                    }
                };
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public abstract String[] getConnectivityIntents();

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        IntentFilter intentFilter = new IntentFilter();
        for (String str : getConnectivityIntents()) {
            intentFilter.addAction(str);
        }
        this.mContext.registerReceiver(
                this.mConnectivityReceiver,
                intentFilter,
                "android.permission.CHANGE_NETWORK_STATE",
                null,
                2);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mContext.unregisterReceiver(this.mConnectivityReceiver);
    }

    public abstract void updateConnectivity();
}
