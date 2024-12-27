package com.android.settings.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.Utils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocationEnabler implements LifecycleObserver, OnStart, OnStop {
    static final IntentFilter INTENT_FILTER_LOCATION_MODE_CHANGED =
            new IntentFilter("android.location.MODE_CHANGED");
    public final Context mContext;
    public final LocationModeChangeListener mListener;
    BroadcastReceiver mReceiver;
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface LocationModeChangeListener {
        void onLocationModeChanged(int i, boolean z);
    }

    public LocationEnabler(
            Context context,
            LocationModeChangeListener locationModeChangeListener,
            Lifecycle lifecycle) {
        this.mContext = context;
        this.mListener = locationModeChangeListener;
        this.mUserManager = (UserManager) context.getSystemService("user");
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public final RestrictedLockUtils.EnforcedAdmin getShareLocationEnforcedAdmin(int i) {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, i, "no_share_location");
        return checkIfRestrictionEnforced == null
                ? RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, i, "no_config_location")
                : checkIfRestrictionEnforced;
    }

    public final boolean isEnabled(int i) {
        return (i == 0 || this.mUserManager.hasUserRestriction("no_share_location")) ? false : true;
    }

    public final boolean isRestrictedToChangeByMdm() {
        boolean z =
                Utils.getEnterprisePolicyEnabled(
                                this.mContext,
                                "content://com.sec.knox.provider/RestrictionPolicy",
                                "isSettingsChangesAllowed",
                                new String[] {"false"})
                        != 0;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "isSettingsChangesAllowed = ", "LocationEnabler", z);
        if (z) {
            boolean z2 =
                    Utils.getEnterprisePolicyEnabled(
                                    this.mContext,
                                    "content://com.sec.knox.provider/LocationPolicy",
                                    "isGPSStateChangeAllowed",
                                    new String[] {null})
                            != 0;
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    ", isLocationGPSStateChangeAllowed = ", "LocationEnabler", z2);
            if (z2) {
                return false;
            }
        }
        Log.i("LocationEnabler", "Location State Change restricted by MDM");
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        if (this.mReceiver == null) {
            this.mReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.settings.location.LocationEnabler.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (Log.isLoggable("LocationEnabler", 3)) {
                                Log.d(
                                        "LocationEnabler",
                                        "Received location mode change intent: " + intent);
                            }
                            LocationEnabler.this.refreshLocationMode();
                        }
                    };
        }
        this.mContext.registerReceiver(this.mReceiver, INTENT_FILTER_LOCATION_MODE_CHANGED);
        refreshLocationMode();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    public final void refreshLocationMode() {
        int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "location_mode", 0);
        if (Log.isLoggable("LocationEnabler", 4)) {
            Log.i("LocationEnabler", "Location mode has been changed");
        }
        LocationModeChangeListener locationModeChangeListener = this.mListener;
        if (locationModeChangeListener != null) {
            locationModeChangeListener.onLocationModeChanged(
                    i,
                    this.mUserManager.hasUserRestriction("no_share_location")
                            || isRestrictedToChangeByMdm());
        }
    }

    public final void setLocationEnabled(boolean z) {
        int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "location_mode", 0);
        if (!this.mUserManager.hasUserRestriction("no_share_location")
                && !isRestrictedToChangeByMdm()) {
            Context context = this.mContext;
            int myUserId = UserHandle.myUserId();
            Settings.Secure.putIntForUser(
                    context.getContentResolver(), "location_changer", 1, myUserId);
            ((LocationManager) context.getSystemService(LocationManager.class))
                    .setLocationEnabledForUser(z, UserHandle.of(myUserId));
            refreshLocationMode();
            return;
        }
        if (Log.isLoggable("LocationEnabler", 4)) {
            Log.i("LocationEnabler", "Restricted user, not setting location mode");
        }
        LocationModeChangeListener locationModeChangeListener = this.mListener;
        if (locationModeChangeListener != null) {
            locationModeChangeListener.onLocationModeChanged(i, true);
        }
    }

    public LocationEnabler(Context context) {
        this.mContext = context;
        this.mListener = null;
        this.mUserManager = (UserManager) context.getSystemService("user");
    }
}
