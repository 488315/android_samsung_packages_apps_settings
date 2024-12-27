package com.android.settings.network;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.satellite.SatelliteManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.fragment.app.Fragment;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.picker.features.search.InitialSearchUtils$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.AirplaneModeEnabler;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.google.common.util.concurrent.Futures;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen;
import com.sec.ims.IMSParameter;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AirplaneModePreferenceController extends TogglePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnResume,
                OnStop,
                OnDestroy,
                AirplaneModeEnabler.OnAirplaneModeChangedListener,
                OnPause {
    private static final String EMERGENCY_MESSAGE_IN_WORKING_STATE =
            "emergency_message_working_state";
    public static final int REQUEST_CODE_EXIT_ECM = 1;
    private static final String TAG = "AirplaneModePreferenceController";
    private AirplaneModeEnabler mAirplaneModeEnabler;
    private SecRestrictedSwitchPreferenceScreen mAirplaneModePreference;
    private Fragment mFragment;
    AtomicBoolean mIsSatelliteOn;
    private PhoneStateListener[] mPhoneStateListener;
    private int[] mRadioState;
    private SatelliteRepository mSatelliteRepository;
    private boolean mSupportCellularVoice;
    private static final String PREFERENCE_KEY = "toggle_airplane";
    public static final Uri SLICE_URI =
            InitialSearchUtils$$ExternalSyntheticOutline0.m(
                    "content", "android.settings.slices", IMSParameter.CALL.ACTION, PREFERENCE_KEY);

    public AirplaneModePreferenceController(Context context, String str) {
        super(context, str);
        this.mSupportCellularVoice = true;
        this.mIsSatelliteOn = new AtomicBoolean(false);
        if (isAvailable(this.mContext)) {
            this.mAirplaneModeEnabler = new AirplaneModeEnabler(this.mContext, this);
            this.mSatelliteRepository = new SatelliteRepository(context.getApplicationContext());
        }
    }

    private int getPhoneId(int i) {
        int phoneId = SubscriptionManager.getPhoneId(i);
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            return phoneId;
        }
        return 0;
    }

    private PhoneStateListener getPhoneStateListener(final int i) {
        return new PhoneStateListener(
                Looper
                        .getMainLooper()) { // from class:
                                            // com.android.settings.network.AirplaneModePreferenceController.1
            public final void onRadioPowerStateChanged(int i2) {
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                ((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                        .getEnabledSimCnt();
                Log.d(
                        AirplaneModePreferenceController.TAG,
                        "onRadioPowerStateChanged (" + i + ") : " + i2);
                if (AirplaneModePreferenceController.this.mRadioState == null) {
                    return;
                }
                AirplaneModePreferenceController.this.mRadioState[i] = i2;
                if (AirplaneModePreferenceController.this.mPhoneStateListener[0] == null
                        || AirplaneModePreferenceController.this.mPhoneStateListener[1] == null) {
                    setPreferenceState();
                } else if (AirplaneModePreferenceController.this.mRadioState[0]
                        == AirplaneModePreferenceController.this.mRadioState[1]) {
                    setPreferenceState();
                }
            }

            public final void setPreferenceState() {
                if (AirplaneModePreferenceController.this.mAirplaneModePreference != null) {
                    boolean z =
                            !AirplaneModePreferenceController.this
                                    .mAirplaneModePreference
                                    .mHelper
                                    .mDisabledByAdmin;
                    AirplaneModePreferenceController.this.mAirplaneModePreference.setEnabled(z);
                    AbsAdapter$$ExternalSyntheticOutline0.m(
                            "PhoneStateListener setEnabled : ",
                            AirplaneModePreferenceController.TAG,
                            z);
                }
            }
        };
    }

    public static boolean isAvailable(Context context) {
        return context.getResources().getBoolean(R.bool.config_show_toggle_airplane)
                && !context.getPackageManager().hasSystemFeature("android.software.leanback");
    }

    private void makeAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        String format =
                String.format(
                        this.mContext.getString(R.string.satellite_warning_dialog_content),
                        this.mContext.getString(R.string.airplane_mode));
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = format;
        alertParams.mTitle =
                String.format(
                        this.mContext.getString(R.string.satellite_warning_dialog_title),
                        this.mContext.getString(R.string.airplane_mode));
        builder.setPositiveButton(R.string.okay, (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    private void registerPhoneStateListener() {
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SecSimFeatureProviderImpl secSimFeatureProviderImpl =
                (SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider();
        int i = secSimFeatureProviderImpl.isMultiSimModel() ? 2 : 1;
        int enabledSimCnt = secSimFeatureProviderImpl.getEnabledSimCnt();
        if (enabledSimCnt == 0) {
            this.mRadioState = null;
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                enabledSimCnt, "registerPhoneStateListener: ActiveSimCnt ", TAG);
        this.mPhoneStateListener = new PhoneStateListener[2];
        this.mRadioState = new int[2];
        for (int i2 = 0; i2 < i; i2++) {
            SubscriptionManager.from(this.mContext);
            int[] subId = SubscriptionManager.getSubId(i2);
            if (subId != null) {
                int i3 = subId[0];
                int phoneId = getPhoneId(i3);
                if (i3 < 0 || i2 != phoneId) {
                    this.mPhoneStateListener[i2] = null;
                } else {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            "registerPhoneStateListener subId:", " phoneId :", i3, phoneId, TAG);
                    this.mPhoneStateListener[phoneId] = getPhoneStateListener(phoneId);
                    telephonyManager.listen(this.mPhoneStateListener[phoneId], 8388608);
                }
                this.mRadioState[i2] = -1;
            }
        }
    }

    private void unregisterPhoneStateListener() {
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        if (((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                                .getEnabledSimCnt()
                        == 0
                || this.mPhoneStateListener == null) {
            return;
        }
        for (int i = 0; i < 2; i++) {
            if (this.mPhoneStateListener[i] != null) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i, "unregisterPhoneStateListener: ", TAG);
                telephonyManager.listen(this.mPhoneStateListener[i], 0);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mAirplaneModePreference =
                (SecRestrictedSwitchPreferenceScreen)
                        preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Utils.isOnCall(this.mContext)) {
            return 5;
        }
        return isAvailable(this.mContext) ? 0 : 3;
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
        Intent intent = new Intent();
        intent.addFlags(268468224);
        intent.setAction("com.samsung.settings.AIRPLANE_MODE");
        return intent;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_network;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public Uri getSliceUri() {
        return SLICE_URI;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!"airplane_mode".equals(preference.getKey()) || !isAvailable()) {
            return false;
        }
        if (this.mAirplaneModeEnabler.isInEcmMode()) {
            Fragment fragment = this.mFragment;
            if (fragment != null) {
                fragment.startActivityForResult(
                        new Intent(
                                        "android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS",
                                        (Uri) null)
                                .setPackage("com.android.phone"),
                        1);
            }
            return true;
        }
        if (!this.mIsSatelliteOn.get()) {
            return false;
        }
        this.mContext.startActivity(
                new Intent(this.mContext, (Class<?>) SatelliteWarningDialogActivity.class)
                        .putExtra("extra_type_of_satellite_warning_dialog", 2));
        return true;
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
        return isAvailable() && WirelessUtils.isAirplaneModeOn(this.mAirplaneModeEnabler.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1 && isAvailable()) {
            boolean z = i2 == -1;
            AirplaneModeEnabler airplaneModeEnabler = this.mAirplaneModeEnabler;
            boolean z2 = this.mAirplaneModePreference.mChecked;
            airplaneModeEnabler.getClass();
            Log.d("AirplaneModeEnabler", "Exist ECM=" + z + ", with airplane mode=" + z2);
            if (z) {
                airplaneModeEnabler.setAirplaneModeOn(z2);
                return;
            }
            AirplaneModeEnabler.OnAirplaneModeChangedListener onAirplaneModeChangedListener =
                    airplaneModeEnabler.mOnAirplaneModeChangedListener;
            if (onAirplaneModeChangedListener != null) {
                onAirplaneModeChangedListener.onAirplaneModeChanged(
                        WirelessUtils.isAirplaneModeOn(airplaneModeEnabler.mContext));
            }
        }
    }

    @Override // com.android.settings.AirplaneModeEnabler.OnAirplaneModeChangedListener
    public void onAirplaneModeChanged(boolean z) {
        SecRestrictedSwitchPreferenceScreen secRestrictedSwitchPreferenceScreen =
                this.mAirplaneModePreference;
        if (secRestrictedSwitchPreferenceScreen != null) {
            secRestrictedSwitchPreferenceScreen.setChecked(z);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        if (isAvailable()) {
            this.mAirplaneModeEnabler.close();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        Log.d(TAG, "onPause");
        unregisterPhoneStateListener();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        SecRestrictedSwitchPreferenceScreen secRestrictedSwitchPreferenceScreen;
        Future future;
        try {
            AtomicBoolean atomicBoolean = this.mIsSatelliteOn;
            SatelliteRepository satelliteRepository = this.mSatelliteRepository;
            ExecutorService executor = Executors.newSingleThreadExecutor();
            satelliteRepository.getClass();
            Intrinsics.checkNotNullParameter(executor, "executor");
            SatelliteManager satelliteManager =
                    (SatelliteManager)
                            satelliteRepository.context.getSystemService(SatelliteManager.class);
            if (satelliteManager == null) {
                Log.w("SatelliteRepository", "SatelliteManager is null");
                future = Futures.immediateFuture(Boolean.FALSE);
            } else {
                future =
                        CallbackToFutureAdapter.getFuture(
                                new SatelliteRepository$requestIsEnabled$1(
                                        satelliteManager, executor));
            }
            atomicBoolean.set(((Boolean) future.get(2000L, TimeUnit.MILLISECONDS)).booleanValue());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "Error to get satellite status : ", e, TAG);
        }
        if (this.mAirplaneModePreference != null) {
            AtomicBoolean atomicBoolean2 = this.mIsSatelliteOn;
            if ((atomicBoolean2 != null && atomicBoolean2.get())
                    || ConnectionsUtils.isSatelliteNetworksOn(this.mContext)) {
                this.mAirplaneModePreference.setEnabled(false);
                return;
            }
            this.mAirplaneModePreference.setEnabled(true);
        }
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy1",
                        "isAirplaneModeAllowed",
                        new String[] {"false"});
        if (enterprisePolicyEnabled != -1
                && enterprisePolicyEnabled != 1
                && (secRestrictedSwitchPreferenceScreen = this.mAirplaneModePreference) != null) {
            secRestrictedSwitchPreferenceScreen.setEnabled(false);
            this.mAirplaneModePreference.setChecked(false);
        } else {
            SecRestrictedSwitchPreferenceScreen secRestrictedSwitchPreferenceScreen2 =
                    this.mAirplaneModePreference;
            if (secRestrictedSwitchPreferenceScreen2 != null) {
                secRestrictedSwitchPreferenceScreen2.mHelper.checkRestrictionAndSetDisabled(
                        UserHandle.myUserId(), "no_airplane_mode");
            }
            registerPhoneStateListener();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (isAvailable()) {
            this.mAirplaneModeEnabler.start();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        if (isAvailable()) {
            this.mAirplaneModeEnabler.stop();
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setAirplaneModeEnabler(AirplaneModeEnabler airplaneModeEnabler) {
        this.mAirplaneModeEnabler = airplaneModeEnabler;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (getThreadEnabled() == z || this.mIsSatelliteOn.get()) {
            return false;
        }
        if (z) {
            int i =
                    (WirelessUtils.isAirplaneModeOn(this.mContext)
                                    || !Utils.isOnCall(this.mContext))
                            ? -1
                            : R.string.airplane_mode_toast_impossible_during_call;
            if (Settings.System.getInt(
                            this.mContext.getContentResolver(),
                            EMERGENCY_MESSAGE_IN_WORKING_STATE,
                            0)
                    == 1) {
                i = R.string.airplane_mode_toast_impossible_while_emergency_sharing;
            }
            if (i != -1) {
                Toast.makeText(this.mContext, i, 1).show();
                return false;
            }
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        int enabledSimCnt =
                ((SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider())
                        .getEnabledSimCnt();
        SecRestrictedSwitchPreferenceScreen secRestrictedSwitchPreferenceScreen =
                this.mAirplaneModePreference;
        if (secRestrictedSwitchPreferenceScreen != null && enabledSimCnt > 0) {
            secRestrictedSwitchPreferenceScreen.setEnabled(false);
        }
        if (isAvailable()) {
            this.mAirplaneModeEnabler.setAirplaneMode(z);
        }
        return true;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mAirplaneModePreference != null) {
            AtomicBoolean atomicBoolean = this.mIsSatelliteOn;
            if ((atomicBoolean == null || !atomicBoolean.get())
                    && !ConnectionsUtils.isSatelliteNetworksOn(this.mContext)) {
                return;
            }
            this.mAirplaneModePreference.setEnabled(false);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
