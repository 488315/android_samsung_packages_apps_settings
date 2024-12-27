package com.android.settings.location;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.provider.Settings;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SeslSwitchBar;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationSwitchBarController extends TogglePreferenceController
        implements CompoundButton.OnCheckedChangeListener,
                LocationEnabler.LocationModeChangeListener,
                LifecycleObserver,
                OnStart,
                OnStop {
    private static final String KEY_LOCATION_SETTINGS = "location_settings";
    private final LocationEnabler mLocationEnabler;
    private final SettingsMainSwitchBar mSwitchBar;
    private boolean mValidListener;

    public LocationSwitchBarController(Context context) {
        super(context, KEY_LOCATION_SETTINGS);
        this.mSwitchBar = null;
        this.mLocationEnabler = new LocationEnabler(context);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int myUserId = UserHandle.myUserId();
        return ((RestrictedLockUtilsInternal.hasBaseUserRestriction(
                                        this.mLocationEnabler.mContext,
                                        myUserId,
                                        "no_share_location")
                                || this.mLocationEnabler.getShareLocationEnforcedAdmin(myUserId)
                                        == null)
                        && !this.mLocationEnabler.mUserManager.hasUserRestriction(
                                "no_share_location"))
                ? 0
                : 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$LocationSettingsActivity");
        intent.addFlags(268468224);
        return intent;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mLocationEnabler.isEnabled(
                Settings.Secure.getInt(this.mContext.getContentResolver(), "location_mode", 0));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        onLocationOptionChange(compoundButton, z);
        this.mLocationEnabler.setLocationEnabled(z);
    }

    @Override // com.android.settings.location.LocationEnabler.LocationModeChangeListener
    public void onLocationModeChanged(int i, boolean z) {
        boolean isEnabled = this.mLocationEnabler.isEnabled(i);
        int myUserId = UserHandle.myUserId();
        RestrictedLockUtils.EnforcedAdmin shareLocationEnforcedAdmin =
                this.mLocationEnabler.getShareLocationEnforcedAdmin(myUserId);
        boolean hasBaseUserRestriction =
                RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        this.mLocationEnabler.mContext, myUserId, "no_share_location");
        boolean z2 = !ConnectionsUtils.isSatelliteNetworksOn(this.mContext);
        if (!hasBaseUserRestriction && shareLocationEnforcedAdmin != null) {
            this.mSwitchBar.setDisabledByAdmin(shareLocationEnforcedAdmin);
        } else if (z) {
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin =
                    new RestrictedLockUtils.EnforcedAdmin();
            enforcedAdmin.enforcedRestriction = "no_share_location";
            this.mSwitchBar.setDisabledByAdmin(enforcedAdmin);
        } else {
            this.mSwitchBar.setEnabled(z2);
        }
        if (isEnabled != ((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked()) {
            if (this.mValidListener) {
                this.mSwitchBar.removeOnSwitchChangeListener(this);
            }
            this.mSwitchBar.setChecked(isEnabled);
            if (this.mValidListener) {
                this.mSwitchBar.addOnSwitchChangeListener(this);
            }
        }
    }

    public boolean onLocationOptionChange(Object obj, boolean z) {
        boolean isLocationProviderEnabled =
                Settings.Secure.isLocationProviderEnabled(
                        this.mContext.getContentResolver(), "gps");
        final int i = 0;
        final int i2 = 1;
        boolean z2 =
                Utils.getEnterprisePolicyEnabled(
                                this.mContext,
                                "content://com.sec.knox.provider/LocationPolicy",
                                "isGPSStateChangeAllowed",
                                null)
                        != 0;
        if (isLocationProviderEnabled && !z2) {
            return true;
        }
        if (this.mSwitchBar != null) {
            LoggingHelper.insertEventLogging(
                    VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED,
                    4601,
                    z);
        }
        if (z) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if ("DCM".equals(Utils.getSalesCode())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                builder.setTitle(this.mContext.getResources().getText(R.string.gpsAlertTitle));
                builder.setMessage(this.mContext.getResources().getText(R.string.gpsAlertDetail));
                builder.setCancelable(false);
                builder.setPositiveButton(
                        this.mContext
                                .getResources()
                                .getText(R.string.assistant_security_warning_agree),
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.location.LocationSwitchBarController.1
                            public final /* synthetic */ LocationSwitchBarController this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i3) {
                                switch (i) {
                                    case 0:
                                        this.this$0.mLocationEnabler.setLocationEnabled(true);
                                        dialogInterface.cancel();
                                        break;
                                    default:
                                        this.this$0.mLocationEnabler.setLocationEnabled(false);
                                        dialogInterface.cancel();
                                        break;
                                }
                            }
                        });
                builder.setNegativeButton(
                        this.mContext
                                .getResources()
                                .getText(R.string.assistant_security_warning_disagree),
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.location.LocationSwitchBarController.1
                            public final /* synthetic */ LocationSwitchBarController this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i3) {
                                switch (i2) {
                                    case 0:
                                        this.this$0.mLocationEnabler.setLocationEnabled(true);
                                        dialogInterface.cancel();
                                        break;
                                    default:
                                        this.this$0.mLocationEnabler.setLocationEnabled(false);
                                        dialogInterface.cancel();
                                        break;
                                }
                            }
                        });
                builder.create().show();
            } else {
                this.mLocationEnabler.setLocationEnabled(true);
            }
        } else {
            this.mLocationEnabler.setLocationEnabled(false);
        }
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (this.mValidListener) {
            return;
        }
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mValidListener = true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        if (this.mValidListener) {
            this.mSwitchBar.removeOnSwitchChangeListener(this);
            this.mValidListener = false;
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        onLocationOptionChange(this.mSwitchBar, z);
        this.mLocationEnabler.setLocationEnabled(z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public LocationSwitchBarController(
            Context context, SettingsMainSwitchBar settingsMainSwitchBar, Lifecycle lifecycle) {
        super(context, KEY_LOCATION_SETTINGS);
        this.mSwitchBar = settingsMainSwitchBar;
        this.mLocationEnabler = new LocationEnabler(context, this, lifecycle);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }
}
