package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.provider.Telephony;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.network.CarrierConfigCache;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ApnPreferenceController extends TelephonyBasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    CarrierConfigCache mCarrierConfigCache;
    private DpcApnEnforcedObserver mDpcApnEnforcedObserver;
    private Preference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DpcApnEnforcedObserver extends ContentObserver {
        public DpcApnEnforcedObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            ApnPreferenceController apnPreferenceController = ApnPreferenceController.this;
            apnPreferenceController.updateState(apnPreferenceController.mPreference);
        }
    }

    public ApnPreferenceController(Context context, String str) {
        super(context, str);
        this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
        this.mDpcApnEnforcedObserver =
                new DpcApnEnforcedObserver(new Handler(Looper.getMainLooper()));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        this.mCarrierConfigCache.getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        return (configForSubId == null
                        || configForSubId.getBoolean("hide_carrier_network_settings_bool")
                        || !((MobileNetworkUtils.isCdmaOptions(this.mContext, i)
                                        && configForSubId != null
                                        && configForSubId.getBoolean("show_apn_setting_cdma_bool"))
                                || (MobileNetworkUtils.isGsmOptions(this.mContext, i)
                                        && configForSubId != null
                                        && configForSubId.getBoolean("apn_expand_bool"))))
                ? 2
                : 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        Intent intent = new Intent("android.settings.APN_SETTINGS");
        intent.setPackage(this.mContext.getPackageName());
        intent.putExtra(":settings:show_fragment_as_subsetting", true);
        intent.putExtra("sub_id", this.mSubId);
        this.mContext.startActivity(intent);
        return true;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(int i) {
        this.mSubId = i;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        DpcApnEnforcedObserver dpcApnEnforcedObserver = this.mDpcApnEnforcedObserver;
        Context context = this.mContext;
        dpcApnEnforcedObserver.getClass();
        context.getContentResolver()
                .registerContentObserver(
                        Telephony.Carriers.ENFORCE_MANAGED_URI, false, dpcApnEnforcedObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        DpcApnEnforcedObserver dpcApnEnforcedObserver = this.mDpcApnEnforcedObserver;
        Context context = this.mContext;
        dpcApnEnforcedObserver.getClass();
        context.getContentResolver().unregisterContentObserver(dpcApnEnforcedObserver);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setPreference(Preference preference) {
        this.mPreference = preference;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x003f, code lost:

       if (r0 != null) goto L13;
    */
    @Override // com.android.settingslib.core.AbstractPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateState(androidx.preference.Preference r9) {
        /*
            r8 = this;
            super.updateState(r9)
            androidx.preference.Preference r9 = r8.mPreference
            if (r9 != 0) goto L8
            return
        L8:
            com.android.settingslib.RestrictedPreference r9 = (com.android.settingslib.RestrictedPreference) r9
            android.content.Context r0 = r8.mContext
            android.graphics.drawable.Drawable r1 = com.android.settings.network.telephony.MobileNetworkUtils.EMPTY_DRAWABLE
            android.content.ContentResolver r2 = r0.getContentResolver()
            android.net.Uri r3 = android.provider.Telephony.Carriers.ENFORCE_MANAGED_URI
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r0 = r2.query(r3, r4, r5, r6, r7)
            r1 = 0
            if (r0 == 0) goto L3f
            int r2 = r0.getCount()     // Catch: java.lang.Throwable -> L35
            r3 = 1
            if (r2 == r3) goto L27
            goto L3f
        L27:
            r0.moveToFirst()     // Catch: java.lang.Throwable -> L35
            int r2 = r0.getInt(r1)     // Catch: java.lang.Throwable -> L35
            if (r2 <= 0) goto L31
            r1 = r3
        L31:
            r0.close()
            goto L42
        L35:
            r8 = move-exception
            r0.close()     // Catch: java.lang.Throwable -> L3a
            goto L3e
        L3a:
            r9 = move-exception
            r8.addSuppressed(r9)
        L3e:
            throw r8
        L3f:
            if (r0 == 0) goto L42
            goto L31
        L42:
            if (r1 == 0) goto L4b
            android.content.Context r8 = r8.mContext
            com.android.settingslib.RestrictedLockUtils$EnforcedAdmin r8 = com.android.settingslib.RestrictedLockUtilsInternal.getDeviceOwner(r8)
            goto L4c
        L4b:
            r8 = 0
        L4c:
            r9.setDisabledByAdmin(r8)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.ApnPreferenceController.updateState(androidx.preference.Preference):void");
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
