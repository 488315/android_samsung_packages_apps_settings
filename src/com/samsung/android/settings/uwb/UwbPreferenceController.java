package com.samsung.android.settings.uwb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.SystemProperties;
import android.util.Log;
import android.uwb.UwbManager;
import android.widget.Toast;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UwbPreferenceController extends TogglePreferenceController
        implements LifecycleObserver {
    private static final String TAG = "UwbPreferenceController";
    private final int UWB_TASK_DELAY_TIME;
    private UwbManager.AdapterStateCallback mAdapterStateCallback;
    private final Context mContext;
    private Executor mExecutor;
    private boolean mInitCompleted;
    private UwbSelectablePreference mSelectablePreference;
    private UwbManager mUwbManager;
    private UwbSettingDb mUwbSettingDb;
    private UwbSettingPolicy mUwbSettingPolicy;

    public UwbPreferenceController(Context context, String str) {
        super(context, str);
        this.UWB_TASK_DELAY_TIME = 100;
        this.mExecutor = null;
        this.mUwbManager = null;
        this.mUwbSettingDb = null;
        this.mUwbSettingPolicy = null;
        this.mSelectablePreference = null;
        this.mInitCompleted = false;
        this.mContext = context;
        if (isUwbSupportedOnDevice()) {
            this.mExecutor = Executors.newSingleThreadExecutor();
            this.mUwbManager = (UwbManager) context.getSystemService(UwbManager.class);
            this.mUwbSettingDb = new UwbSettingDb(context);
            UwbSettingPolicy uwbSettingPolicy = new UwbSettingPolicy();
            uwbSettingPolicy.isRegulationMode = false;
            uwbSettingPolicy.mContext = context;
            this.mUwbSettingPolicy = uwbSettingPolicy;
            this.mSelectablePreference = new UwbSelectablePreference(context, this);
        }
    }

    private void init() {
        if (isMenuUnavailable()) {
            this.mSelectablePreference.setChecked(false);
            this.mSelectablePreference.setEnabled(false);
        } else {
            boolean uwbSettingDb = this.mUwbSettingDb.getUwbSettingDb();
            boolean isUwbEnabled = this.mUwbManager.isUwbEnabled();
            if (!isMenuUnavailable() || isUwbEnabled == uwbSettingDb) {
                this.mSelectablePreference.setChecked(uwbSettingDb);
            } else {
                this.mSelectablePreference.setChecked(isUwbEnabled);
                this.mUwbManager.setUwbEnabled(isUwbEnabled);
                this.mUwbSettingDb.setUwbSettingDb(isUwbEnabled);
            }
            SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                    Utils$$ExternalSyntheticOutline0.m(
                            "init - setByUser: ",
                            uwbSettingDb,
                            " uwbState: ",
                            isUwbEnabled,
                            " isMenuUnavailable: "),
                    isMenuUnavailable(),
                    TAG);
        }
        this.mInitCompleted = true;
    }

    public static void insertUwbSettingLogging(boolean z) {
        LoggingHelper.insertEventLogging(
                VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED, 3670, z);
    }

    private boolean isMenuUnavailable() {
        UwbSettingPolicy uwbSettingPolicy = this.mUwbSettingPolicy;
        return uwbSettingPolicy.isRegulationMode
                || uwbSettingPolicy.isRestrictionMode()
                || !isServiceReady();
    }

    private boolean isServiceReady() {
        return this.mUwbManager != null;
    }

    private boolean isSupportedModel() {
        String str = Build.MODEL;
        String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return ("XXV".equals(Utils.getSalesCode())
                        && (str.startsWith("SM-F926")
                                || str.startsWith("SM-G998")
                                || str.startsWith("SM-G996")))
                ? false
                : true;
    }

    private boolean isUwbSupportedOnDevice() {
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.uwb")) {
            return isSupportedModel();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$onStart$1(AtomicInteger atomicInteger, Object obj, int i, int i2) {
        UwbSettingPolicy uwbSettingPolicy = this.mUwbSettingPolicy;
        uwbSettingPolicy.getClass();
        if (i == 0 && i2 == 5) {
            uwbSettingPolicy.isRegulationMode = true;
        } else if (i != 0) {
            uwbSettingPolicy.isRegulationMode = false;
        }
        atomicInteger.set(i2);
        StringBuilder sb = new StringBuilder("### uwb state callback - state: ");
        sb.append(i);
        sb.append(" reason: ");
        sb.append(i2);
        sb.append(" restriction: ");
        sb.append(this.mUwbSettingPolicy.isRestrictionMode());
        sb.append(" regulation: ");
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, this.mUwbSettingPolicy.isRegulationMode, TAG);
        if (!this.mInitCompleted) {
            init();
        }
        updateView();
        synchronized (obj) {
            obj.notify();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$onStart$2(
            final Object obj, final AtomicInteger atomicInteger) throws Exception {
        synchronized (obj) {
            UwbManager uwbManager = this.mUwbManager;
            Executor executor = this.mExecutor;
            UwbManager.AdapterStateCallback adapterStateCallback =
                    new UwbManager
                            .AdapterStateCallback() { // from class:
                                                      // com.samsung.android.settings.uwb.UwbPreferenceController$$ExternalSyntheticLambda0
                        public final void onStateChanged(int i, int i2) {
                            UwbPreferenceController.this.lambda$onStart$1(
                                    atomicInteger, obj, i, i2);
                        }
                    };
            this.mAdapterStateCallback = adapterStateCallback;
            uwbManager.registerAdapterStateCallback(executor, adapterStateCallback);
            obj.wait();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setUwbEnabled, reason: merged with bridge method [inline-methods] */
    public void lambda$setChecked$0(boolean z) {
        this.mUwbManager.setUwbEnabled(z);
        this.mUwbSettingDb.setUwbSettingDb(z);
        Log.i(TAG, "+++ UWB State changed : " + this.mUwbManager.isUwbEnabled());
        insertUwbSettingLogging(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSummary() {
        if (this.mUwbSettingPolicy.isRegulationMode) {
            Log.i(TAG, "summary update: regulation mode");
            this.mSelectablePreference.mSelectablePreference.setSummary(
                    R.string.uwb_settings_samsung_ch5_ch9_no_support_summary);
        } else if (!isServiceReady() || this.mUwbSettingPolicy.isRestrictionMode()) {
            Log.i(TAG, "summary update: no service");
            this.mSelectablePreference.mSelectablePreference.setSummary(
                    R.string.uwb_settings_samsung_no_service_summary);
        } else {
            Log.i(TAG, "summary update: normal information");
            this.mSelectablePreference.mSelectablePreference.setSummary(
                    R.string.uwb_settings_samsung_summary);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(final PreferenceScreen preferenceScreen) {
        Log.i(TAG, "displayPreference " + getPreferenceKey());
        if (isUwbSupportedOnDevice()) {
            UwbSelectablePreference uwbSelectablePreference = this.mSelectablePreference;
            String preferenceKey = getPreferenceKey();
            if (uwbSelectablePreference.mLabsEnabled) {
                SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                        new SecSwitchPreferenceScreen(uwbSelectablePreference.mContext);
                secSwitchPreferenceScreen.setKey(preferenceKey);
                secSwitchPreferenceScreen.setTitle(R.string.uwb_settings_samsung_title);
                secSwitchPreferenceScreen.setOrder(750);
                secSwitchPreferenceScreen.setFragment(
                        "com.samsung.android.settings.uwb.labs.UwbLabsMainFragment");
                Preference findPreference = preferenceScreen.findPreference(preferenceKey);
                Objects.requireNonNull(findPreference);
                preferenceScreen.removePreference(findPreference);
                preferenceScreen.addPreference(secSwitchPreferenceScreen);
                secSwitchPreferenceScreen.setVisible(true);
                uwbSelectablePreference.mSelectablePreference = secSwitchPreferenceScreen;
            }
            super.displayPreference(preferenceScreen);
            final UwbSelectablePreference uwbSelectablePreference2 = this.mSelectablePreference;
            final String preferenceKey2 = getPreferenceKey();
            if (uwbSelectablePreference2.mLabsEnabled) {
                return;
            }
            RestrictedSwitchPreference restrictedSwitchPreference =
                    (RestrictedSwitchPreference) preferenceScreen.findPreference(preferenceKey2);
            uwbSelectablePreference2.mSelectablePreference = restrictedSwitchPreference;
            restrictedSwitchPreference.setOnPreferenceChangeListener(
                    new Preference.OnPreferenceChangeListener() { // from class:
                        // com.samsung.android.settings.uwb.UwbSelectablePreference$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            UwbSelectablePreference uwbSelectablePreference3 =
                                    UwbSelectablePreference.this;
                            Handler handler = uwbSelectablePreference3.mClickEventHandler;
                            UwbSelectablePreference.AnonymousClass1 anonymousClass1 =
                                    uwbSelectablePreference3.mEnableDisableEventRunnable;
                            handler.removeCallbacks(anonymousClass1);
                            uwbSelectablePreference3.mIsUwbEnabled =
                                    Boolean.parseBoolean(obj.toString());
                            int i = uwbSelectablePreference3.mClickCount;
                            PreferenceScreen preferenceScreen2 = preferenceScreen;
                            boolean z = false;
                            if (i == 5) {
                                UwbSelectablePreference.AnonymousClass1 anonymousClass12 =
                                        uwbSelectablePreference3.mClick5OverEventRunnable;
                                handler.removeCallbacks(anonymousClass12);
                                if (!uwbSelectablePreference3.mIsUwbEnabled) {
                                    Toast.makeText(
                                                    uwbSelectablePreference3.mContext,
                                                    "UWB Labs enabled!",
                                                    0)
                                            .show();
                                    SecSwitchPreferenceScreen secSwitchPreferenceScreen2 =
                                            new SecSwitchPreferenceScreen(
                                                    uwbSelectablePreference3.mContext);
                                    secSwitchPreferenceScreen2.setKey(preferenceKey2);
                                    secSwitchPreferenceScreen2.setTitle(
                                            R.string.uwb_settings_samsung_title);
                                    secSwitchPreferenceScreen2.setOrder(750);
                                    secSwitchPreferenceScreen2.setFragment(
                                            "com.samsung.android.settings.uwb.labs.UwbLabsMainFragment");
                                    preferenceScreen2.removePreference(
                                            uwbSelectablePreference3.mSelectablePreference);
                                    preferenceScreen2.addPreference(secSwitchPreferenceScreen2);
                                    secSwitchPreferenceScreen2.setVisible(true);
                                    SystemProperties.set("uwb.labs.enable", "true");
                                    uwbSelectablePreference3.mSelectablePreference =
                                            secSwitchPreferenceScreen2;
                                    z = true;
                                }
                                handler.postDelayed(anonymousClass12, 210L);
                            } else {
                                handler.postDelayed(anonymousClass1, 210L);
                                uwbSelectablePreference3.mClickCount++;
                            }
                            if (z) {
                                uwbSelectablePreference3.mLabsEnabled = true;
                                UwbPreferenceController uwbPreferenceController =
                                        uwbSelectablePreference3.mMainController;
                                uwbPreferenceController.displayPreference(preferenceScreen2);
                                uwbPreferenceController.updateView();
                            }
                            return z;
                        }
                    });
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Log.i(TAG, "getAvailabilityStatus");
        if (isUwbSupportedOnDevice()) {
            return isMenuUnavailable() ? 5 : 0;
        }
        return 3;
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
        Log.i(TAG, "isChecked");
        return this.mSelectablePreference.mSelectablePreference.isChecked();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Log.i(TAG, "onStart");
        if (isUwbSupportedOnDevice() && isServiceReady()) {
            final Object obj = new Object();
            final AtomicInteger atomicInteger = new AtomicInteger();
            ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
            FutureTask futureTask =
                    new FutureTask(
                            new Callable() { // from class:
                                             // com.samsung.android.settings.uwb.UwbPreferenceController$$ExternalSyntheticLambda2
                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    Integer lambda$onStart$2;
                                    lambda$onStart$2 =
                                            UwbPreferenceController.this.lambda$onStart$2(
                                                    obj, atomicInteger);
                                    return lambda$onStart$2;
                                }
                            });
            newSingleThreadExecutor.submit(futureTask);
            try {
                ((Integer) futureTask.get(100L, TimeUnit.MILLISECONDS)).getClass();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException unused) {
                newSingleThreadExecutor.shutdownNow();
                Log.e(TAG, "setEnabled: Timeout occurred");
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Log.i(TAG, "onStop");
        if (isUwbSupportedOnDevice() && isServiceReady()) {
            this.mUwbManager.unregisterAdapterStateCallback(this.mAdapterStateCallback);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(final boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setChecked : ", TAG, z);
        if (!isUwbSupportedOnDevice() || !isServiceReady()) {
            return false;
        }
        AbsAdapter$$ExternalSyntheticOutline0.m("+++ UWB State changed : ", TAG, z);
        this.mSelectablePreference.setEnabled(false);
        this.mSelectablePreference.setChecked(z);
        new Handler()
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.uwb.UwbPreferenceController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                UwbPreferenceController.this.lambda$setChecked$0(z);
                            }
                        },
                        100L);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        StringBuilder sb = new StringBuilder("updateState by preference restriction: ");
        sb.append(this.mUwbSettingPolicy.isRestrictionMode());
        sb.append(" regulation: ");
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, this.mUwbSettingPolicy.isRegulationMode, TAG);
        super.updateState(preference);
        if (isUwbSupportedOnDevice() && isServiceReady()) {
            updateSummary();
            if (this.mUwbSettingPolicy.isRestrictionMode()
                    || this.mUwbSettingPolicy.isRegulationMode) {
                this.mSelectablePreference.setChecked(false);
                this.mSelectablePreference.setEnabled(false);
            } else {
                if (!this.mSelectablePreference.mSelectablePreference.isEnabled()
                        && this.mUwbSettingDb.getUwbSettingDb()) {
                    this.mSelectablePreference.setChecked(true);
                }
                this.mSelectablePreference.setEnabled(true);
            }
        }
    }

    public void updateView() {
        StringBuilder sb = new StringBuilder("updateView by callback restriction: ");
        sb.append(this.mUwbSettingPolicy.isRestrictionMode());
        sb.append(" regulation: ");
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, this.mUwbSettingPolicy.isRegulationMode, TAG);
        if (isUwbSupportedOnDevice() && isServiceReady()) {
            ((Activity) this.mContext)
                    .runOnUiThread(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.uwb.UwbPreferenceController.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UwbPreferenceController.this.updateSummary();
                                    if (UwbPreferenceController.this.mUwbSettingPolicy
                                                    .isRestrictionMode()
                                            || UwbPreferenceController.this
                                                    .mUwbSettingPolicy
                                                    .isRegulationMode) {
                                        UwbPreferenceController.this.mSelectablePreference
                                                .setEnabled(false);
                                        UwbPreferenceController.this.mSelectablePreference
                                                .setChecked(false);
                                    } else {
                                        UwbPreferenceController.this.mSelectablePreference
                                                .setChecked(
                                                        UwbPreferenceController.this.mUwbManager
                                                                .isUwbEnabled());
                                        UwbPreferenceController.this.mSelectablePreference
                                                .setEnabled(true);
                                    }
                                }
                            });
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
