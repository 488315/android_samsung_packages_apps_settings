package com.samsung.android.settings.connection.tether;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.net.TetheringManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecEthernetTetherPreferenceController extends TogglePreferenceController
        implements DataSaverBackend.Listener,
                SecTetherControllerInterface,
                LifecycleObserver,
                OnCreate,
                OnStart,
                OnStop,
                OnDestroy {
    private static final int ETHERNET_TETHER_PROVISION_REQUEST = 1001;
    private static final String TAG = "SecEthernetTetherPreferenceController";
    private static final String TETHER_CHOICE = "TETHER_TYPE";
    private final HashSet<String> mAvailableInterfaces;
    private ConnectivityManager mCm;
    private DataSaverBackend mDataSaverBackend;
    private boolean mDataSaverEnabled;
    private EthernetManager mEm;
    private EthernetListener mEthernetListener;
    private BroadcastReceiver mEthernetTetherReceiver;
    private SecTetherSettings mFragment;
    private Handler mHandler;
    private SecSwitchPreference mPreference;
    private ConnectivityManager.OnStartTetheringCallback mStartTetheringCallback;
    private TetheringManager mTm;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EthernetListener implements EthernetManager.InterfaceStateListener {
        public EthernetListener() {}

        public final void onInterfaceStateChanged(
                String str, int i, int i2, IpConfiguration ipConfiguration) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "EthernetListener : state :", SecEthernetTetherPreferenceController.TAG);
            if (i == 2) {
                SecEthernetTetherPreferenceController.this.mAvailableInterfaces.add(str);
            } else {
                SecEthernetTetherPreferenceController.this.mAvailableInterfaces.remove(str);
            }
            SecEthernetTetherPreferenceController.this.updateController();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OnStartTetheringCallback
            extends ConnectivityManager.OnStartTetheringCallback {
        public final SecEthernetTetherPreferenceController EthernetController;

        public OnStartTetheringCallback(
                SecEthernetTetherPreferenceController secEthernetTetherPreferenceController) {
            this.EthernetController = secEthernetTetherPreferenceController;
        }

        public final void onTetheringFailed() {
            Log.d(SecEthernetTetherPreferenceController.TAG, "onTetheringFailed()");
            Log.d(SecEthernetTetherPreferenceController.TAG, "onTethering Update()");
            this.EthernetController.updateController();
        }

        public final void onTetheringStarted() {
            Log.d(SecEthernetTetherPreferenceController.TAG, "onTetheringStarted()");
            Log.d(SecEthernetTetherPreferenceController.TAG, "onTethering Update()");
            this.EthernetController.updateController();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class mEthernetTetherReceiver extends BroadcastReceiver {
        public mEthernetTetherReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                    "onReceive() :",
                    intent.getAction(),
                    SecEthernetTetherPreferenceController.TAG,
                    "android.intent.action.AIRPLANE_MODE")) {
                SecEthernetTetherPreferenceController.this.updateController();
            }
        }
    }

    public SecEthernetTetherPreferenceController(Context context, String str) {
        super(context, str);
        this.mAvailableInterfaces = new HashSet<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateController$1(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (!booleanValue) {
            setEthernetTethering(booleanValue);
        } else {
            if (startEthernetTetherProvisioningIfNecessary(this.mFragment, 5)) {
                return true;
            }
            startTethering();
        }
        return true;
    }

    private void setEthernetTethering(boolean z) {
        if (z) {
            Log.d(TAG, "mEthernetTether onPreferenceTreeClick startTethering ");
            this.mCm.startTethering(5, true, this.mStartTetheringCallback, this.mHandler);
        } else {
            Log.d(TAG, "mEthernetTether onPreferenceTreeClick stopTethering");
            this.mCm.stopTethering(5);
        }
    }

    private boolean startEthernetTetherProvisioningIfNecessary(
            SecTetherSettings secTetherSettings, int i) {
        if (!SecTetherUtils.isProvisioningNeeded(secTetherSettings.getActivity())) {
            Log.i(
                    TAG,
                    "startEthernetTetherProvisioningIfNecessary SecTetherUtils.isProvisioningNeeded"
                        + " : FALSE");
            return false;
        }
        String str = TAG;
        Log.i(
                str,
                "startEthernetTetherProvisioningIfNecessary SecTetherUtils.isProvisioningNeeded :"
                    + " TRUE");
        String[] stringArray = this.mContext.getResources().getStringArray(17236257);
        if (stringArray == null || stringArray.length == 0) {
            Log.i(str, " provisioning app is not set in CSCfeature");
            return false;
        }
        String[] stringArray2 = this.mContext.getResources().getStringArray(17236257);
        if (stringArray2 == null) {
            Log.i(str, "startEthernetTetherProvisioningIfNecessary provisionApp == null return");
            return false;
        }
        if (stringArray2.length != 2) {
            Log.i(
                    str,
                    "startEthernetTetherProvisioningIfNecessary provisionApp.length != 2 return");
            return false;
        }
        Log.i(str, "ProvisionApp : " + stringArray2[0] + " , " + stringArray2[1]);
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(stringArray2[0], stringArray2[1]);
        intent.putExtra(TETHER_CHOICE, 5);
        Log.i(str, "Intent putExtra key : TETHER_TYPE , value : 5");
        try {
            secTetherSettings.startActivityForResult(intent, 1001);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return this.mPreference.isChecked();
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

    public void onActivityResult(int i, int i2) {
        if (i2 != -1) {
            SecSwitchPreference secSwitchPreference = this.mPreference;
            if (secSwitchPreference != null) {
                secSwitchPreference.setChecked(false);
                return;
            }
            return;
        }
        SecSwitchPreference secSwitchPreference2 = this.mPreference;
        if (secSwitchPreference2 == null || !secSwitchPreference2.isChecked()) {
            return;
        }
        startTethering();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        DataSaverBackend dataSaverBackend = new DataSaverBackend(this.mContext);
        this.mDataSaverBackend = dataSaverBackend;
        this.mDataSaverEnabled = dataSaverBackend.mPolicyManager.getRestrictBackground();
        this.mCm = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        this.mTm = (TetheringManager) this.mContext.getSystemService("tethering");
        this.mEm = (EthernetManager) this.mContext.getSystemService("ethernet");
        this.mDataSaverBackend.addListener(this);
        this.mHandler = new Handler();
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public void onDataSaverChanged(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("onDataSaverChanged : ", TAG, z);
        this.mDataSaverEnabled = z;
        SecSwitchPreference secSwitchPreference = this.mPreference;
        if (secSwitchPreference != null) {
            secSwitchPreference.setEnabled(!z);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mDataSaverBackend.remListener(this);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        Log.d(TAG, "onStart");
        this.mStartTetheringCallback = new OnStartTetheringCallback(this);
        this.mEthernetTetherReceiver = new mEthernetTetherReceiver();
        Intent registerReceiver =
                this.mContext.registerReceiver(
                        this.mEthernetTetherReceiver,
                        new IntentFilter("android.intent.action.AIRPLANE_MODE"),
                        2);
        if (registerReceiver != null) {
            this.mEthernetTetherReceiver.onReceive(this.mContext, registerReceiver);
        }
        EthernetListener ethernetListener = new EthernetListener();
        this.mEthernetListener = ethernetListener;
        EthernetManager ethernetManager = this.mEm;
        if (ethernetManager != null) {
            ethernetManager.addInterfaceStateListener(
                    new Executor() { // from class:
                                     // com.samsung.android.settings.connection.tether.SecEthernetTetherPreferenceController$$ExternalSyntheticLambda1
                        @Override // java.util.concurrent.Executor
                        public final void execute(Runnable runnable) {
                            SecEthernetTetherPreferenceController.this.lambda$onStart$0(runnable);
                        }
                    },
                    ethernetListener);
        }
        updateController();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        Log.d(TAG, "onStop");
        BroadcastReceiver broadcastReceiver = this.mEthernetTetherReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
        EthernetManager ethernetManager = this.mEm;
        if (ethernetManager != null) {
            ethernetManager.removeInterfaceStateListener(this.mEthernetListener);
        }
        this.mEthernetTetherReceiver = null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        return false;
    }

    public void setHost(SecTetherSettings secTetherSettings) {
        this.mFragment = secTetherSettings;
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public /* bridge */ /* synthetic */ void startProvisioningIfNecessary(
            SecTetherSettings secTetherSettings, int i) {
        super.startProvisioningIfNecessary(secTetherSettings, i);
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public void startTethering() {
        setEthernetTethering(true);
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public void updateController() {
        updateController(this.mTm.getTetheredIfaces());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private void updateController(String[] strArr) {
        if (ConnectionsUtils.isSatelliteNetworksOn(this.mContext)) {
            Log.d(TAG, "Tethering is disabled by UsingNonTerrestrialNetwork");
            this.mPreference.setEnabled(false);
            this.mPreference.setChecked(false);
            return;
        }
        boolean z = false;
        for (String str : strArr) {
            if (this.mAvailableInterfaces.contains(str)
                    || this.mEm.getInterfaceList().contains(str)) {
                z = true;
            }
        }
        if (z) {
            this.mPreference.setEnabled(!this.mDataSaverEnabled);
            this.mPreference.setChecked(true);
            this.mPreference.setSummary(R.string.interact_across_profiles_summary_allowed);
        } else if (this.mAvailableInterfaces.size() <= 0
                && this.mEm.getInterfaceList().size() <= 0) {
            this.mPreference.setEnabled(false);
            this.mPreference.setChecked(false);
            this.mPreference.setSummary(R.string.sec_ethernet_tethering_subtext);
        } else {
            this.mPreference.setEnabled(!this.mDataSaverEnabled);
            this.mPreference.setChecked(false);
            this.mPreference.setSummary(R.string.sec_ethernet_tethering_subtext);
        }
        String str2 = TAG;
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m(
                        "mEthernetTether updateEthernetState isTethered:  ",
                        " mAvailableInterfaces.size(): ",
                        z);
        m.append(this.mAvailableInterfaces.size() > 0);
        m.append(" mEm.getInterfaceList().size(): ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                m, this.mEm.getInterfaceList().size() > 0, str2);
        this.mPreference.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.samsung.android.settings.connection.tether.SecEthernetTetherPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        boolean lambda$updateController$1;
                        lambda$updateController$1 =
                                SecEthernetTetherPreferenceController.this
                                        .lambda$updateController$1(preference, obj);
                        return lambda$updateController$1;
                    }
                });
    }

    public void onActivityCreated(Bundle bundle) {}

    public void onSaveInstanceState(Bundle bundle) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public void onAllowlistStatusChanged(int i, boolean z) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public void onDenylistStatusChanged(int i, boolean z) {}
}
