package com.android.settingslib.location;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsInjector$MessengerHandler extends Handler {
    public Handler mHandler;
    public WeakReference mSettingRef;

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        SettingsInjector$Setting settingsInjector$Setting =
                (SettingsInjector$Setting) this.mSettingRef.get();
        if (settingsInjector$Setting == null) {
            return;
        }
        Bundle data = message.getData();
        if (Log.isLoggable("SettingsInjector", 3)) {
            Log.d(
                    "SettingsInjector",
                    settingsInjector$Setting + ": received " + message + ", bundle: " + data);
        }
        boolean z = data.getBoolean("enabled", true);
        String string = data.getString(UniversalCredentialUtil.AGENT_SUMMARY);
        boolean isEmpty = TextUtils.isEmpty(string);
        Preference preference = settingsInjector$Setting.preference;
        if (isEmpty) {
            preference.setSummary(R.string.summary_placeholder);
        } else {
            preference.setSummary(string);
        }
        preference.setEnabled(z);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(2, settingsInjector$Setting));
    }
}
