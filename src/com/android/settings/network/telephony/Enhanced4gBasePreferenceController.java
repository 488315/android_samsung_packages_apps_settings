package com.android.settings.network.telephony;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsMmTelManager;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.internal.telephony.util.ArrayUtils;
import com.android.settings.R;
import com.android.settings.network.ims.VolteQueryImsState;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class Enhanced4gBasePreferenceController extends TelephonyTogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    protected static final int MODE_4G_CALLING = 2;
    protected static final int MODE_ADVANCED_CALL = 1;
    protected static final int MODE_NONE = -1;
    protected static final int MODE_VOLTE = 0;
    private static final String TAG = "Enhanced4g";
    private int m4gCurrentMode;
    private final List<On4gLteUpdateListener> m4gLteListeners;
    private Integer mCallState;
    private boolean mHas5gCapability;
    boolean mIsNrEnabledFromCarrierConfig;
    Preference mPreference;
    private boolean mShow5gLimitedDialog;
    private PhoneCallStateTelephonyCallback mTelephonyCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface On4gLteUpdateListener {
        void on4gLteUpdated();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PhoneCallStateTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.CallStateListener {
        public TelephonyManager mTelephonyManager;

        public PhoneCallStateTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            Enhanced4gBasePreferenceController.this.mCallState = Integer.valueOf(i);
            Enhanced4gBasePreferenceController enhanced4gBasePreferenceController =
                    Enhanced4gBasePreferenceController.this;
            enhanced4gBasePreferenceController.updateState(
                    enhanced4gBasePreferenceController.mPreference);
        }
    }

    public Enhanced4gBasePreferenceController(Context context, String str) {
        super(context, str);
        this.m4gCurrentMode = -1;
        this.m4gLteListeners = new ArrayList();
    }

    private boolean isDialogNeeded() {
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("Has5gCapability:"), this.mHas5gCapability, TAG);
        return this.mShow5gLimitedDialog
                && this.mHas5gCapability
                && this.mIsNrEnabledFromCarrierConfig;
    }

    private boolean isModeMatched() {
        return this.m4gCurrentMode == getMode();
    }

    private boolean isUserControlAllowed(PersistableBundle persistableBundle) {
        return isCallStateIdle()
                && persistableBundle != null
                && persistableBundle.getBoolean("editable_enhanced_4g_lte_bool");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setAdvancedCallingSettingEnabled(ImsMmTelManager imsMmTelManager, boolean z) {
        try {
            imsMmTelManager.setAdvancedCallingSettingEnabled(z);
            Iterator<On4gLteUpdateListener> it = this.m4gLteListeners.iterator();
            while (it.hasNext()) {
                it.next().on4gLteUpdated();
            }
            return true;
        } catch (IllegalArgumentException e) {
            StringBuilder m =
                    RowView$$ExternalSyntheticOutline0.m("fail to set VoLTE=", ". subId=", z);
            m.append(this.mSubId);
            Log.w(TAG, m.toString(), e);
            return false;
        }
    }

    private void show5gLimitedDialog(final ImsMmTelManager imsMmTelManager) {
        Log.d(TAG, "show5gLimitedDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(R.string.volte_5G_limited_title)
                .setMessage(R.string.volte_5G_limited_text)
                .setNeutralButton(
                        this.mContext.getResources().getString(R.string.cancel),
                        (DialogInterface.OnClickListener) null)
                .setPositiveButton(
                        this.mContext.getResources().getString(R.string.condition_turn_off),
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.network.telephony.Enhanced4gBasePreferenceController.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(
                                        Enhanced4gBasePreferenceController.TAG,
                                        "onClick,isChecked:false");
                                Enhanced4gBasePreferenceController.this
                                        .setAdvancedCallingSettingEnabled(imsMmTelManager, false);
                                Enhanced4gBasePreferenceController
                                        enhanced4gBasePreferenceController =
                                                Enhanced4gBasePreferenceController.this;
                                enhanced4gBasePreferenceController.updateState(
                                        enhanced4gBasePreferenceController.mPreference);
                            }
                        })
                .create()
                .show();
    }

    public Enhanced4gBasePreferenceController addListener(
            On4gLteUpdateListener on4gLteUpdateListener) {
        this.m4gLteListeners.add(on4gLteUpdateListener);
        return this;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x005c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005d  */
    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAvailabilityStatus(int r9) {
        /*
            r8 = this;
            r8.init(r9)
            boolean r0 = r8.isModeMatched()
            r1 = 2
            if (r0 != 0) goto Lb
            return r1
        Lb:
            com.android.settings.network.ims.VolteQueryImsState r0 = r8.queryImsState(r9)
            int r2 = r0.mSubId
            android.telephony.ims.ProvisioningManager r2 = android.telephony.ims.ProvisioningManager.createForSubscriptionId(r2)
            r3 = 68
            int r2 = r2.getProvisioningIntValue(r3)
            r3 = 1
            r4 = 0
            if (r2 != r3) goto L20
            return r4
        L20:
            android.os.PersistableBundle r9 = r8.getCarrierConfigForSubId(r9)
            if (r9 == 0) goto L6b
            java.lang.String r2 = "hide_enhanced_4g_lte_bool"
            boolean r2 = r9.getBoolean(r2)
            if (r2 == 0) goto L2f
            goto L6b
        L2f:
            int r2 = r0.mSubId
            boolean r5 = android.telephony.SubscriptionManager.isValidSubscriptionId(r2)
            if (r5 != 0) goto L39
        L37:
            r2 = r4
            goto L5a
        L39:
            boolean r5 = r0.isVoLteProvisioned()
            if (r5 != 0) goto L40
            goto L37
        L40:
            boolean r2 = r0.isServiceStateReady(r2)     // Catch: java.lang.Throwable -> L45
            goto L5a
        L45:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "fail to get VoLte service status. subId="
            r6.<init>(r7)
            r6.append(r2)
            java.lang.String r2 = r6.toString()
            java.lang.String r6 = "VolteQueryImsState"
            android.util.Log.w(r6, r2, r5)
            goto L37
        L5a:
            if (r2 != 0) goto L5d
            return r1
        L5d:
            boolean r8 = r8.isUserControlAllowed(r9)
            if (r8 == 0) goto L6a
            boolean r8 = r0.isAllowUserControl()
            if (r8 == 0) goto L6a
            r3 = r4
        L6a:
            return r3
        L6b:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.Enhanced4gBasePreferenceController.getAvailabilityStatus(int):int");
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public int getMode() {
        return -1;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public Enhanced4gBasePreferenceController init(int i) {
        if (this.mTelephonyCallback == null) {
            this.mTelephonyCallback = new PhoneCallStateTelephonyCallback();
        }
        this.mSubId = i;
        PersistableBundle carrierConfigForSubId = getCarrierConfigForSubId(i);
        if (carrierConfigForSubId == null) {
            return this;
        }
        boolean z = carrierConfigForSubId.getBoolean("show_4g_for_lte_data_icon_bool");
        int i2 = carrierConfigForSubId.getInt("enhanced_4g_lte_title_variant_int");
        this.m4gCurrentMode = i2;
        if (i2 != 1) {
            this.m4gCurrentMode = z ? 2 : 0;
        }
        this.mShow5gLimitedDialog =
                carrierConfigForSubId.getBoolean("volte_5g_limited_alert_dialog_bool");
        this.mIsNrEnabledFromCarrierConfig =
                !ArrayUtils.isEmpty(
                        carrierConfigForSubId.getIntArray("carrier_nr_availabilities_int_array"));
        return this;
    }

    public boolean isCallStateIdle() {
        Integer num = this.mCallState;
        return num != null && num.intValue() == 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return queryImsState(this.mSubId).isEnabledByUser();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback = this.mTelephonyCallback;
        if (phoneCallStateTelephonyCallback == null) {
            return;
        }
        Context context = this.mContext;
        int i = this.mSubId;
        phoneCallStateTelephonyCallback.getClass();
        phoneCallStateTelephonyCallback.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            phoneCallStateTelephonyCallback.mTelephonyManager =
                    phoneCallStateTelephonyCallback.mTelephonyManager.createForSubscriptionId(i);
        }
        try {
            Enhanced4gBasePreferenceController.this.mCallState =
                    Integer.valueOf(
                            phoneCallStateTelephonyCallback.mTelephonyManager.getCallState(i));
        } catch (UnsupportedOperationException unused) {
            Enhanced4gBasePreferenceController.this.mCallState = 0;
        }
        phoneCallStateTelephonyCallback.mTelephonyManager.registerTelephonyCallback(
                Enhanced4gBasePreferenceController.this.mContext.getMainExecutor(),
                Enhanced4gBasePreferenceController.this.mTelephonyCallback);
        long supportedRadioAccessFamily =
                phoneCallStateTelephonyCallback.mTelephonyManager.getSupportedRadioAccessFamily();
        Enhanced4gBasePreferenceController.this.mHas5gCapability =
                (supportedRadioAccessFamily & 524288) > 0;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback = this.mTelephonyCallback;
        if (phoneCallStateTelephonyCallback == null) {
            return;
        }
        Enhanced4gBasePreferenceController.this.mCallState = null;
        TelephonyManager telephonyManager = phoneCallStateTelephonyCallback.mTelephonyManager;
        if (telephonyManager != null) {
            telephonyManager.unregisterTelephonyCallback(phoneCallStateTelephonyCallback);
        }
    }

    public VolteQueryImsState queryImsState(int i) {
        return new VolteQueryImsState(this.mContext, i);
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        ImsMmTelManager createForSubscriptionId;
        if (!SubscriptionManager.isValidSubscriptionId(this.mSubId)
                || (createForSubscriptionId = ImsMmTelManager.createForSubscriptionId(this.mSubId))
                        == null) {
            return false;
        }
        if (!isDialogNeeded() || z) {
            return setAdvancedCallingSettingEnabled(createForSubscriptionId, z);
        }
        show5gLimitedDialog(createForSubscriptionId);
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        VolteQueryImsState queryImsState = queryImsState(this.mSubId);
        boolean z = false;
        twoStatePreference.setEnabled(
                isUserControlAllowed(getCarrierConfigForSubId(this.mSubId))
                        && queryImsState.isAllowUserControl());
        if (queryImsState.isEnabledByUser() && queryImsState.isAllowUserControl()) {
            z = true;
        }
        twoStatePreference.setChecked(z);
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
