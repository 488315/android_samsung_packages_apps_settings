package com.android.settings.nfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BaseNfcEnabler {
    public final Context mContext;
    public final IntentFilter mIntentFilter;
    public final NfcAdapter mNfcAdapter;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.nfc.BaseNfcEnabler.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.nfc.action.ADAPTER_STATE_CHANGED".equals(intent.getAction())) {
                        BaseNfcEnabler.this.handleNfcStateChanged(
                                intent.getIntExtra("android.nfc.extra.ADAPTER_STATE", 1));
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.nfc.BaseNfcEnabler$1] */
    public BaseNfcEnabler(Context context) {
        this.mContext = context;
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(context);
        this.mNfcAdapter = defaultAdapter;
        if (defaultAdapter != null) {
            this.mIntentFilter = new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED");
        } else {
            this.mIntentFilter = null;
        }
    }

    public abstract void handleNfcStateChanged(int i);
}
