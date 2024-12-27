package com.android.settings.accounts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ManagedProfileQuietModeEnabler implements DefaultLifecycleObserver {
    public final Context mContext;
    public final QuietModeChangeListener mListener;
    public final UserHandle mManagedProfile;
    final BroadcastReceiver mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.accounts.ManagedProfileQuietModeEnabler.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent == null) {
                        return;
                    }
                    String action = intent.getAction();
                    Log.v("QuietModeEnabler", "Received broadcast: " + action);
                    if (!"android.intent.action.MANAGED_PROFILE_AVAILABLE".equals(action)
                            && !"android.intent.action.MANAGED_PROFILE_UNAVAILABLE"
                                    .equals(action)) {
                        Log.w(
                                "QuietModeEnabler",
                                "Cannot handle received broadcast: " + intent.getAction());
                        return;
                    }
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    if (intExtra
                            != ManagedProfileQuietModeEnabler.this.mManagedProfile
                                    .getIdentifier()) {
                        StringBuilder m =
                                ListPopupWindow$$ExternalSyntheticOutline0.m(
                                        intExtra,
                                        "Managed profile broadcast ID: ",
                                        " does not match managed user: ");
                        m.append(ManagedProfileQuietModeEnabler.this.mManagedProfile);
                        Log.w("QuietModeEnabler", m.toString());
                    } else {
                        QuietModeChangeListener quietModeChangeListener =
                                ManagedProfileQuietModeEnabler.this.mListener;
                        if (quietModeChangeListener != null) {
                            quietModeChangeListener.onQuietModeChanged();
                        }
                    }
                }
            };
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface QuietModeChangeListener {
        void onQuietModeChanged();
    }

    public ManagedProfileQuietModeEnabler(
            Context context, QuietModeChangeListener quietModeChangeListener) {
        this.mContext = context;
        this.mListener = quietModeChangeListener;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUserManager = userManager;
        this.mManagedProfile = Utils.getManagedProfile(userManager);
    }

    public final boolean isQuietModeEnabled() {
        UserHandle userHandle = this.mManagedProfile;
        return userHandle != null && this.mUserManager.isQuietModeEnabled(userHandle);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onStart(LifecycleOwner lifecycleOwner) {
        this.mContext.registerReceiver(
                this.mReceiver,
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "android.intent.action.MANAGED_PROFILE_AVAILABLE",
                        "android.intent.action.MANAGED_PROFILE_UNAVAILABLE"),
                4);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onStop(LifecycleOwner lifecycleOwner) {
        this.mContext.unregisterReceiver(this.mReceiver);
    }
}
