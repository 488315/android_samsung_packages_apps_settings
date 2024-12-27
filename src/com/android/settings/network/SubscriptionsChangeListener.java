package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SubscriptionsChangeListener extends ContentObserver {
    public final Uri mAirplaneModeSettingUri;
    public final AnonymousClass2 mBroadcastReceiver;
    public final SubscriptionsChangeListenerClient mClient;
    public final Context mContext;
    public boolean mRunning;
    public final SubscriptionManager mSubscriptionManager;
    public final AnonymousClass1 mSubscriptionsChangedListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SubscriptionsChangeListenerClient {
        void onAirplaneModeChanged(boolean z);

        void onSubscriptionsChanged();
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.settings.network.SubscriptionsChangeListener$1] */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.settings.network.SubscriptionsChangeListener$2] */
    public SubscriptionsChangeListener(
            Context context, SubscriptionsChangeListenerClient subscriptionsChangeListenerClient) {
        super(new Handler(Looper.getMainLooper()));
        this.mRunning = false;
        this.mContext = context;
        this.mClient = subscriptionsChangeListenerClient;
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mSubscriptionsChangedListener =
                new SubscriptionManager.OnSubscriptionsChangedListener(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.android.settings.network.SubscriptionsChangeListener.1
                    @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                    public final void onSubscriptionsChanged() {
                        SubscriptionsChangeListener.this.mClient.onSubscriptionsChanged();
                    }
                };
        this.mAirplaneModeSettingUri = Settings.Global.getUriFor("airplane_mode_on");
        this.mBroadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.network.SubscriptionsChangeListener.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (isInitialStickyBroadcast()) {
                            return;
                        }
                        SubscriptionsChangeListener.this.mClient.onSubscriptionsChanged();
                    }
                };
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        if (uri.equals(this.mAirplaneModeSettingUri)) {
            this.mClient.onAirplaneModeChanged(
                    Settings.Global.getInt(
                                    this.mContext.getContentResolver(), "airplane_mode_on", 0)
                            != 0);
        }
    }

    public final void start() {
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(
                this.mContext.getMainExecutor(), this.mSubscriptionsChangedListener);
        this.mContext
                .getContentResolver()
                .registerContentObserver(this.mAirplaneModeSettingUri, false, this);
        this.mContext.registerReceiver(
                this.mBroadcastReceiver,
                new IntentFilter("android.intent.action.RADIO_TECHNOLOGY"));
        this.mRunning = true;
    }

    public final void stop() {
        if (!this.mRunning) {
            Log.d("SubscriptionsChangeListener", "Stop has been called without associated Start.");
            return;
        }
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener(
                this.mSubscriptionsChangedListener);
        this.mContext.getContentResolver().unregisterContentObserver(this);
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        this.mRunning = false;
    }
}
