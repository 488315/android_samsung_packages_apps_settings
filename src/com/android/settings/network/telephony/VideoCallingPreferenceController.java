package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsException;
import android.telephony.ims.ImsMmTelManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.network.CarrierConfigCache;
import com.android.settings.network.MobileDataEnabledListener;
import com.android.settings.network.ims.VolteQueryImsState;
import com.android.settings.network.ims.VtQueryImsState;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VideoCallingPreferenceController extends TelephonyTogglePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                MobileDataEnabledListener.Client,
                Enhanced4gBasePreferenceController.On4gLteUpdateListener {
    private static final String TAG = "VideoCallingPreference";
    Integer mCallState;
    private CallingPreferenceCategoryController mCallingPreferenceCategoryController;
    private MobileDataEnabledListener mDataContentObserver;
    private Preference mPreference;
    private PhoneTelephonyCallback mTelephonyCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PhoneTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.CallStateListener {
        public TelephonyManager mTelephonyManager;

        public PhoneTelephonyCallback() {}

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            VideoCallingPreferenceController.this.mCallState = Integer.valueOf(i);
            VideoCallingPreferenceController videoCallingPreferenceController =
                    VideoCallingPreferenceController.this;
            videoCallingPreferenceController.updateState(
                    videoCallingPreferenceController.mPreference);
        }
    }

    public VideoCallingPreferenceController(Context context, String str) {
        super(context, str);
        this.mDataContentObserver = new MobileDataEnabledListener(context, this);
        this.mTelephonyCallback = new PhoneTelephonyCallback();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        return (SubscriptionManager.isValidSubscriptionId(i) && isVideoCallEnabled(i)) ? 0 : 2;
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

    public VideoCallingPreferenceController init(
            int i, CallingPreferenceCategoryController callingPreferenceCategoryController) {
        this.mSubId = i;
        this.mCallingPreferenceCategoryController = callingPreferenceCategoryController;
        return this;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        VtQueryImsState queryImsState = queryImsState(this.mSubId);
        int i = queryImsState.mSubId;
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            return queryImsState.isEnabledByUser(i);
        }
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isImsSupported() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony.ims");
    }

    public boolean isVideoCallEnabled(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return false;
        }
        CarrierConfigCache.getInstance(this.mContext).getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        if (configForSubId == null) {
            return false;
        }
        if ((!configForSubId.getBoolean("ignore_data_enabled_changed_for_video_calls")
                        && !((TelephonyManager)
                                        this.mContext.getSystemService(TelephonyManager.class))
                                .createForSubscriptionId(i)
                                .isDataEnabled())
                || !isImsSupported()) {
            return false;
        }
        VtQueryImsState queryImsState = queryImsState(i);
        int i2 = queryImsState.mSubId;
        if (!queryImsState.isProvisionedOnDevice(i2)) {
            return false;
        }
        try {
            if (queryImsState.isEnabledByPlatform(i2)) {
                return queryImsState.isServiceStateReady(i2);
            }
            return false;
        } catch (ImsException | IllegalArgumentException | InterruptedException e) {
            Log.w("VtQueryImsState", "fail to get Vt ready. subId=" + i2, e);
            return false;
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.telephony.Enhanced4gBasePreferenceController.On4gLteUpdateListener
    public void on4gLteUpdated() {
        updateState(this.mPreference);
    }

    @Override // com.android.settings.network.MobileDataEnabledListener.Client
    public void onMobileDataEnabledChange() {
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        PhoneTelephonyCallback phoneTelephonyCallback = this.mTelephonyCallback;
        Context context = this.mContext;
        int i = this.mSubId;
        phoneTelephonyCallback.getClass();
        phoneTelephonyCallback.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            phoneTelephonyCallback.mTelephonyManager =
                    phoneTelephonyCallback.mTelephonyManager.createForSubscriptionId(i);
        }
        try {
            VideoCallingPreferenceController.this.mCallState =
                    Integer.valueOf(phoneTelephonyCallback.mTelephonyManager.getCallState(i));
        } catch (UnsupportedOperationException unused) {
            VideoCallingPreferenceController.this.mCallState = 0;
        }
        phoneTelephonyCallback.mTelephonyManager.registerTelephonyCallback(
                context.getMainExecutor(), phoneTelephonyCallback);
        this.mDataContentObserver.start(this.mSubId);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        PhoneTelephonyCallback phoneTelephonyCallback = this.mTelephonyCallback;
        VideoCallingPreferenceController.this.mCallState = null;
        phoneTelephonyCallback.mTelephonyManager.unregisterTelephonyCallback(
                phoneTelephonyCallback);
        this.mDataContentObserver.stop();
    }

    public VtQueryImsState queryImsState(int i) {
        Context context = this.mContext;
        VtQueryImsState vtQueryImsState = new VtQueryImsState(2, 0, 1);
        vtQueryImsState.mContext = context;
        vtQueryImsState.mSubId = i;
        return vtQueryImsState;
    }

    public VolteQueryImsState queryVoLteState(int i) {
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
        try {
            createForSubscriptionId.setVtSettingEnabled(z);
            return true;
        } catch (IllegalArgumentException e) {
            StringBuilder m =
                    RowView$$ExternalSyntheticOutline0.m("Unable to set VT status ", ". subId=", z);
            m.append(this.mSubId);
            Log.w(TAG, m.toString(), e);
            return false;
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        boolean z;
        super.updateState(preference);
        if (this.mCallState == null || preference == null) {
            Log.d(TAG, "Skip update under mCallState=" + this.mCallState);
            return;
        }
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        boolean isVideoCallEnabled = isVideoCallEnabled(this.mSubId);
        twoStatePreference.setVisible(isVideoCallEnabled);
        this.mCallingPreferenceCategoryController.updateChildVisible(
                getPreferenceKey(), isVideoCallEnabled);
        if (isVideoCallEnabled) {
            boolean z2 = false;
            if (queryVoLteState(this.mSubId).isEnabledByUser()) {
                VtQueryImsState queryImsState = queryImsState(this.mSubId);
                int i = queryImsState.mSubId;
                if (SubscriptionManager.isValidSubscriptionId(i)
                        && (!queryImsState.isTtyEnabled(queryImsState.mContext)
                                || queryImsState.isTtyOnVolteEnabled(i))) {
                    z = true;
                    preference.setEnabled(!z && this.mCallState.intValue() == 0);
                    if (z && getThreadEnabled()) {
                        z2 = true;
                    }
                    twoStatePreference.setChecked(z2);
                }
            }
            z = false;
            preference.setEnabled(!z && this.mCallState.intValue() == 0);
            if (z) {
                z2 = true;
            }
            twoStatePreference.setChecked(z2);
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
