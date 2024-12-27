package com.android.settings.uwb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.uwb.UwbManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UwbPreferenceController extends TogglePreferenceController
        implements LifecycleObserver {
    private final UwbManager.AdapterStateCallback mAdapterStateCallback;
    private final BroadcastReceiver mAirplaneModeChangedReceiver;
    private boolean mAirplaneModeOn;
    private final Executor mExecutor;
    private final Handler mHandler;
    private Preference mPreference;
    private int mState;
    private int mStateReason;
    private final UwbManager mUwbManager;
    private final UwbUtils mUwbUtils;

    @VisibleForTesting
    public UwbPreferenceController(Context context, String str, UwbUtils uwbUtils) {
        super(context, str);
        Handler handler = new Handler(context.getMainLooper());
        this.mHandler = handler;
        this.mExecutor = new HandlerExecutor(handler);
        this.mUwbUtils = uwbUtils;
        if (!isUwbSupportedOnDevice()) {
            this.mUwbManager = null;
            this.mAirplaneModeChangedReceiver = null;
            this.mAdapterStateCallback = null;
        } else {
            UwbManager uwbManager = (UwbManager) context.getSystemService(UwbManager.class);
            this.mUwbManager = uwbManager;
            this.mAirplaneModeChangedReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.settings.uwb.UwbPreferenceController.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context2, Intent intent) {
                            UwbPreferenceController uwbPreferenceController =
                                    UwbPreferenceController.this;
                            UwbUtils uwbUtils2 = uwbPreferenceController.mUwbUtils;
                            Context context3 =
                                    ((AbstractPreferenceController) UwbPreferenceController.this)
                                            .mContext;
                            uwbUtils2.getClass();
                            uwbPreferenceController.mAirplaneModeOn =
                                    Settings.Global.getInt(
                                                    context3.getContentResolver(),
                                                    "airplane_mode_on",
                                                    0)
                                            == 1;
                            UwbPreferenceController uwbPreferenceController2 =
                                    UwbPreferenceController.this;
                            uwbPreferenceController2.updateState(
                                    uwbPreferenceController2.mPreference);
                        }
                    };
            this.mAdapterStateCallback =
                    new UwbManager
                            .AdapterStateCallback() { // from class:
                                                      // com.android.settings.uwb.UwbPreferenceController$$ExternalSyntheticLambda0
                        public final void onStateChanged(int i, int i2) {
                            UwbPreferenceController.this.lambda$new$0(i, i2);
                        }
                    };
            this.mState = uwbManager.getAdapterState();
        }
    }

    private boolean isUwbDisabledDueToRegulatory() {
        return this.mState == 0 && this.mStateReason == 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, int i2) {
        this.mState = i;
        this.mStateReason = i2;
        updateState(this.mPreference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!isUwbSupportedOnDevice()) {
            return 3;
        }
        if (this.mAirplaneModeOn) {
            return 5;
        }
        return isUwbDisabledDueToRegulatory() ? 2 : 0;
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
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_connected_devices;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mAirplaneModeOn
                ? this.mContext
                        .getResources()
                        .getString(R.string.uwb_settings_summary_airplane_mode)
                : isUwbDisabledDueToRegulatory()
                        ? this.mContext
                                .getResources()
                                .getString(R.string.uwb_settings_summary_no_uwb_regulatory)
                        : this.mContext.getResources().getString(R.string.uwb_settings_summary);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean hasAsyncUpdate() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        if (!isUwbSupportedOnDevice()) {
            return false;
        }
        int i = this.mState;
        return i == 2 || i == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isUwbSupportedOnDevice() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.uwb");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (isUwbSupportedOnDevice()) {
            this.mState = this.mUwbManager.getAdapterState();
            this.mStateReason = 4;
            UwbUtils uwbUtils = this.mUwbUtils;
            Context context = this.mContext;
            uwbUtils.getClass();
            this.mAirplaneModeOn =
                    Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0)
                            == 1;
            this.mUwbManager.registerAdapterStateCallback(
                    this.mExecutor, this.mAdapterStateCallback);
            this.mContext.registerReceiver(
                    this.mAirplaneModeChangedReceiver,
                    new IntentFilter("android.intent.action.AIRPLANE_MODE"),
                    null,
                    this.mHandler);
            refreshSummary(this.mPreference);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        if (isUwbSupportedOnDevice()) {
            this.mUwbManager.unregisterAdapterStateCallback(this.mAdapterStateCallback);
            this.mContext.unregisterReceiver(this.mAirplaneModeChangedReceiver);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (!isUwbSupportedOnDevice()) {
            return true;
        }
        if (this.mAirplaneModeOn) {
            this.mUwbManager.setUwbEnabled(false);
            return true;
        }
        this.mUwbManager.setUwbEnabled(z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(!this.mAirplaneModeOn);
        refreshSummary(this.mPreference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public UwbPreferenceController(Context context, String str) {
        this(context, str, new UwbUtils());
    }
}
