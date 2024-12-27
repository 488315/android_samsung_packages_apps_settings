package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultSubscriptionReceiver extends BroadcastReceiver {
    public final Context mContext;
    public final DefaultSubscriptionListener mListener;

    public DefaultSubscriptionReceiver(
            Context context, DefaultSubscriptionListener defaultSubscriptionListener) {
        this.mContext = context;
        this.mListener = defaultSubscriptionListener;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
            this.mListener.onDefaultDataChanged(SubscriptionManager.getDefaultDataSubscriptionId());
            return;
        }
        if ("android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED".equals(action)) {
            this.mListener.onDefaultSubInfoChanged(SubscriptionManager.getDefaultSubscriptionId());
        } else if ("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED"
                .equals(action)) {
            this.mListener.onDefaultVoiceChanged(
                    SubscriptionManager.getDefaultVoiceSubscriptionId());
        } else if ("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED".equals(action)) {
            this.mListener.onDefaultSmsChanged(SubscriptionManager.getDefaultSmsSubscriptionId());
        }
    }

    public final void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED");
        this.mContext.registerReceiver(this, intentFilter);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DefaultSubscriptionListener {
        void onDefaultDataChanged(int i);

        default void onDefaultSmsChanged(int i) {}

        default void onDefaultSubInfoChanged(int i) {}

        default void onDefaultVoiceChanged(int i) {}
    }
}
