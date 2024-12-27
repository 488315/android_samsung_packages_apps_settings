package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.network.MobileDataContentObserver;
import com.android.settings.network.SubscriptionsChangeListener;
import com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Unit;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutoDataSwitchPreferenceController extends TelephonyTogglePreferenceController
        implements LifecycleObserver,
                SubscriptionsChangeListener.SubscriptionsChangeListenerClient {
    private SubscriptionsChangeListener mChangeListener;
    private CrossSimCallingViewModel mCrossSimCallingViewModel;
    private TelephonyManager mManager;
    private MobileDataContentObserver mMobileDataContentObserver;
    private TwoStatePreference mPreference;
    private PreferenceScreen mScreen;

    public AutoDataSwitchPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (TwoStatePreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mScreen = preferenceScreen;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        return 2;
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

    @VisibleForTesting
    public boolean hasMobileData() {
        return DataUsageUtils.hasMobileData(this.mContext);
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(int i, CrossSimCallingViewModel crossSimCallingViewModel) {
        this.mSubId = i;
        this.mManager =
                ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(i);
        this.mCrossSimCallingViewModel = crossSimCallingViewModel;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        TelephonyManager telephonyManager = this.mManager;
        return telephonyManager != null && telephonyManager.isMobileDataPolicyEnabled(3);
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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        SubscriptionsChangeListener subscriptionsChangeListener = this.mChangeListener;
        if (subscriptionsChangeListener != null) {
            subscriptionsChangeListener.stop();
        }
        MobileDataContentObserver mobileDataContentObserver = this.mMobileDataContentObserver;
        if (mobileDataContentObserver != null) {
            Context context = this.mContext;
            mobileDataContentObserver.getClass();
            context.getContentResolver().unregisterContentObserver(mobileDataContentObserver);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (this.mChangeListener == null) {
            this.mChangeListener = new SubscriptionsChangeListener(this.mContext, this);
        }
        this.mChangeListener.start();
        if (this.mMobileDataContentObserver == null) {
            MobileDataContentObserver mobileDataContentObserver =
                    new MobileDataContentObserver(new Handler(Looper.getMainLooper()));
            this.mMobileDataContentObserver = mobileDataContentObserver;
            mobileDataContentObserver.mListener =
                    new AutoDataSwitchPreferenceController$$ExternalSyntheticLambda0(this);
        }
        MobileDataContentObserver mobileDataContentObserver2 = this.mMobileDataContentObserver;
        Context context = this.mContext;
        int i = this.mSubId;
        mobileDataContentObserver2.getClass();
        context.getContentResolver()
                .registerContentObserver(
                        MobileDataContentObserver.getObservableUri(context, i),
                        false,
                        mobileDataContentObserver2);
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public void onSubscriptionsChanged() {
        updateState(this.mPreference);
    }

    @VisibleForTesting
    /* renamed from: refreshPreference, reason: merged with bridge method [inline-methods] */
    public void lambda$onResume$0() {
        PreferenceScreen preferenceScreen = this.mScreen;
        if (preferenceScreen != null) {
            super.displayPreference(preferenceScreen);
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        TelephonyManager telephonyManager = this.mManager;
        if (telephonyManager != null) {
            telephonyManager.setMobileDataPolicyEnabled(3, z);
        }
        CrossSimCallingViewModel crossSimCallingViewModel = this.mCrossSimCallingViewModel;
        if (crossSimCallingViewModel == null) {
            return true;
        }
        crossSimCallingViewModel.updateChannel.mo1469trySendJP2dKIU(Unit.INSTANCE);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        preference.setVisible(isAvailable());
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settings.network.SubscriptionsChangeListener.SubscriptionsChangeListenerClient
    public void onAirplaneModeChanged(boolean z) {}
}
