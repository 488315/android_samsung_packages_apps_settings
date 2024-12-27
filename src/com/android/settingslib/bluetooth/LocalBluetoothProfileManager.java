package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelUuid;
import android.util.Log;
import android.util.SparseIntArray;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.settings.R;
import com.android.settingslib.bluetooth.PbapServerProfile.PbapServiceListener;

import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settingslib.bluetooth.GattProfile;
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;
import com.samsung.android.settingslib.bluetooth.SppProfile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocalBluetoothProfileManager {
    public static final boolean DEBUG = BluetoothUtils.DEBUG;
    public A2dpProfile mA2dpProfile;
    public final Context mContext;
    public CsipSetCoordinatorProfile mCsipSetCoordinatorProfile;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final BluetoothEventManager mEventManager;
    public GattProfile mGattProfile;
    public HapClientProfile mHapClientProfile;
    public HeadsetProfile mHeadsetProfile;
    public HearingAidProfile mHearingAidProfile;
    public HidProfile mHidProfile;
    public LocalBluetoothLeBroadcast mLeAudioBroadcast;
    public LocalBluetoothLeBroadcastAssistant mLeAudioBroadcastAssistant;
    public LeAudioProfile mLeAudioProfile;
    public final LocalBluetoothAdapter mLocalAdapter;
    public MapClientProfile mMapClientProfile;
    public MapProfile mMapProfile;
    public OppProfile mOppProfile;
    public final PanProfile mPanProfile;
    public PbapServerProfile mPbapProfile;
    public final Map mProfileNameMap;
    public SapProfile mSapProfile;
    public final Collection mServiceListeners;
    public SppProfile mSppProfile;
    public VolumeControlProfile mVolumeControlProfile;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PanStateChangedHandler extends StateChangedHandler {
        @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.StateChangedHandler, com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(
                Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            ((PanProfile) this.mProfile)
                    .setLocalRole(
                            bluetoothDevice,
                            intent.getIntExtra("android.bluetooth.pan.extra.LOCAL_ROLE", 0));
            super.onReceive(context, intent, bluetoothDevice);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ServiceListener {
        void onServiceConnected();

        void onServiceDisconnected();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class StateChangedHandler implements BluetoothEventManager.Handler {
        public final LocalBluetoothProfile mProfile;

        public StateChangedHandler(LocalBluetoothProfile localBluetoothProfile) {
            this.mProfile = localBluetoothProfile;
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int i;
            boolean onProfileConnectionStateChangedIfProcessed;
            boolean z;
            boolean z2;
            int i2;
            String string;
            int deviceSide;
            int deviceMode;
            if (this.mProfile == null) {
                Log.e("LocalBluetoothProfileManager", "StateChangedHandler :: mProfile is null");
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
            CachedBluetoothDevice findDevice =
                    LocalBluetoothProfileManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                boolean z3 = LocalBluetoothProfileManager.DEBUG;
                if (z3) {
                    Log.w(
                            "LocalBluetoothProfileManager",
                            "StateChangedHandler found new device: " + bluetoothDevice);
                }
                if (bluetoothDevice.getBondState() == 10
                        && KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(
                                context.getPackageName())
                        && intExtra == 0) {
                    Log.w(
                            "LocalBluetoothProfileManager",
                            "StateChangedHandler: not create cached for devices that have already"
                                + " been unbonded");
                    return;
                }
                CachedBluetoothDevice addDevice =
                        LocalBluetoothProfileManager.this.mDeviceManager.addDevice(bluetoothDevice);
                if (addDevice == null) {
                    if (z3) {
                        Log.w(
                                "LocalBluetoothProfileManager",
                                "StateChangedHandler :: Can't add CachedDevice");
                        return;
                    }
                    return;
                }
                findDevice = addDevice;
            }
            boolean booleanExtra =
                    intent.getBooleanExtra("android.bluetooth.profile.extra.isNormallyType", false);
            Log.d(
                    "LocalBluetoothProfileManager",
                    "Profiles StateChangedHandler device : "
                            + findDevice.getNameForLog()
                            + ", mProfile : "
                            + this.mProfile
                            + ", new state : "
                            + intExtra
                            + ", old state : "
                            + intExtra2
                            + ", normally type : "
                            + booleanExtra);
            int intExtra3 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            int intExtra4 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
            if (intExtra3 == 0 && intExtra4 == 1) {
                Log.i(
                        "LocalBluetoothProfileManager",
                        "Failed to connect " + this.mProfile + " device");
            }
            if (LocalBluetoothProfileManager.this.mHearingAidProfile != null
                    && (this.mProfile instanceof HearingAidProfile)
                    && intExtra3 == 2) {
                if (findDevice.getHiSyncId() == 0) {
                    i = intExtra2;
                    long hiSyncId =
                            LocalBluetoothProfileManager.this.mHearingAidProfile.getHiSyncId(
                                    findDevice.mDevice);
                    if (hiSyncId != 0) {
                        BluetoothDevice bluetoothDevice2 = findDevice.mDevice;
                        BluetoothHearingAid bluetoothHearingAid =
                                LocalBluetoothProfileManager.this.mHearingAidProfile.mService;
                        if (bluetoothHearingAid == null) {
                            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            deviceSide = -1;
                        } else {
                            deviceSide = bluetoothHearingAid.getDeviceSide(bluetoothDevice2);
                        }
                        int i3 =
                                HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(
                                        deviceSide, -1);
                        BluetoothHearingAid bluetoothHearingAid2 =
                                LocalBluetoothProfileManager.this.mHearingAidProfile.mService;
                        if (bluetoothHearingAid2 == null) {
                            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            deviceMode = -1;
                        } else {
                            deviceMode = bluetoothHearingAid2.getDeviceMode(bluetoothDevice2);
                        }
                        findDevice.setHearingAidInfo(
                                new HearingAidInfo(
                                        i3,
                                        HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING
                                                .get(deviceMode, -1),
                                        hiSyncId));
                    }
                } else {
                    i = intExtra2;
                }
                HearingAidStatsLogUtils.logHearingAidInfo(findDevice);
            } else {
                i = intExtra2;
            }
            LocalBluetoothProfileManager localBluetoothProfileManager =
                    LocalBluetoothProfileManager.this;
            boolean z4 =
                    localBluetoothProfileManager.mHapClientProfile != null
                            && (this.mProfile instanceof HapClientProfile);
            boolean z5 =
                    localBluetoothProfileManager.mLeAudioProfile != null
                            && (this.mProfile instanceof LeAudioProfile);
            if ((z4 || z5)
                    && intExtra3 == 2
                    && findDevice.isConnectedHapClientDevice()
                    && findDevice.isConnectedLeAudioDevice()) {
                BluetoothDevice bluetoothDevice3 = findDevice.mDevice;
                BluetoothLeAudio bluetoothLeAudio =
                        LocalBluetoothProfileManager.this.mLeAudioProfile.mService;
                int audioLocation =
                        (bluetoothLeAudio == null || bluetoothDevice3 == null)
                                ? 0
                                : bluetoothLeAudio.getAudioLocation(bluetoothDevice3);
                SparseIntArray sparseIntArray =
                        HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING;
                boolean z6 = (88413265 & audioLocation) != 0;
                boolean z7 = (audioLocation & 176826530) != 0;
                int i4 = (z6 && z7) ? 2 : z6 ? 0 : z7 ? 1 : -1;
                BluetoothHapClient bluetoothHapClient =
                        LocalBluetoothProfileManager.this.mHapClientProfile.mService;
                findDevice.setHearingAidInfo(
                        new HearingAidInfo(
                                i4,
                                HearingAidInfo.HAP_DEVICE_TYPE_TO_INTERNAL_MODE_MAPPING.get(
                                        bluetoothHapClient == null
                                                ? -1
                                                : bluetoothHapClient.getHearingAidType(
                                                        bluetoothDevice3),
                                        -1),
                                0L));
                HearingAidStatsLogUtils.logHearingAidInfo(findDevice);
            }
            CsipSetCoordinatorProfile csipSetCoordinatorProfile =
                    LocalBluetoothProfileManager.this.mCsipSetCoordinatorProfile;
            if (csipSetCoordinatorProfile != null
                    && (this.mProfile instanceof CsipSetCoordinatorProfile)
                    && intExtra3 == 2
                    && findDevice.mGroupId == -1) {
                BluetoothDevice bluetoothDevice4 = findDevice.mDevice;
                BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator =
                        csipSetCoordinatorProfile.mService;
                Map groupUuidMapByDevice =
                        (bluetoothCsipSetCoordinator == null || bluetoothDevice4 == null)
                                ? null
                                : bluetoothCsipSetCoordinator.getGroupUuidMapByDevice(
                                        bluetoothDevice4);
                if (groupUuidMapByDevice != null) {
                    Iterator it = groupUuidMapByDevice.entrySet().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Map.Entry entry = (Map.Entry) it.next();
                        if (((ParcelUuid) entry.getValue()).equals(BluetoothUuid.CAP)) {
                            findDevice.setGroupId(((Integer) entry.getKey()).intValue());
                            break;
                        }
                    }
                }
            }
            findDevice.onProfileStateChanged(this.mProfile, intExtra3);
            if (findDevice.getHiSyncId() == 0 && findDevice.mGroupId == -1) {
                z2 = true;
            } else {
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                        LocalBluetoothProfileManager.this.mDeviceManager;
                int profileId = this.mProfile.getProfileId();
                synchronized (cachedBluetoothDeviceManager) {
                    if ((profileId == 28 || profileId == 21 || profileId == 25) && intExtra3 == 2) {
                        cachedBluetoothDeviceManager.mHearingAidDeviceManager.syncDeviceIfNeeded(
                                findDevice);
                    }
                }
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager2 =
                        LocalBluetoothProfileManager.this.mDeviceManager;
                int profileId2 = this.mProfile.getProfileId();
                synchronized (cachedBluetoothDeviceManager2) {
                    if (profileId2 == 21) {
                        onProfileConnectionStateChangedIfProcessed =
                                cachedBluetoothDeviceManager2.mHearingAidDeviceManager
                                        .onProfileConnectionStateChangedIfProcessed(
                                                intExtra3, findDevice);
                    } else if (profileId2 == 1
                            || profileId2 == 2
                            || profileId2 == 22
                            || profileId2 == 25) {
                        onProfileConnectionStateChangedIfProcessed =
                                cachedBluetoothDeviceManager2.mCsipDeviceManager
                                        .onProfileConnectionStateChangedIfProcessed(
                                                intExtra3, findDevice);
                    } else {
                        z = true;
                        onProfileConnectionStateChangedIfProcessed = false;
                    }
                    z = true;
                }
                z2 = onProfileConnectionStateChangedIfProcessed ^ z;
            }
            if (z2) {
                findDevice.refresh();
                BluetoothEventManager bluetoothEventManager =
                        LocalBluetoothProfileManager.this.mEventManager;
                int profileId3 = this.mProfile.getProfileId();
                Iterator it2 = ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).iterator();
                while (it2.hasNext()) {
                    ((BluetoothCallback) it2.next())
                            .onProfileConnectionStateChanged(findDevice, intExtra3, profileId3);
                }
                if (profileId3 == 29 && intExtra3 == 0) {
                    boolean z8 = BluetoothUtils.DEBUG;
                    BluetoothAdapter.getDefaultAdapter();
                }
            }
            BluetoothEventManager bluetoothEventManager2 =
                    LocalBluetoothProfileManager.this.mEventManager;
            LocalBluetoothProfile localBluetoothProfile = this.mProfile;
            synchronized (bluetoothEventManager2.mSemCallbacks) {
                try {
                    Iterator it3 = ((ArrayList) bluetoothEventManager2.mSemCallbacks).iterator();
                    while (it3.hasNext()) {
                        int i5 = i;
                        ((SemBluetoothCallback) it3.next())
                                .onProfileStateChanged(
                                        findDevice, localBluetoothProfile, intExtra, i5);
                        i = i5;
                    }
                    i2 = i;
                } catch (Throwable th) {
                    throw th;
                }
            }
            LocalBluetoothManager localBluetoothManager =
                    LocalBluetoothManager.getInstance(context, null);
            if (localBluetoothManager == null) {
                Log.e(
                        "LocalBluetoothProfileManager",
                        "StateChangedHandler :: localBtManager is null");
                return;
            }
            if (intExtra == 0 && i2 == 1) {
                Log.d(
                        "LocalBluetoothProfileManager",
                        "Failed to connect " + this.mProfile + " device");
                if (findDevice.isBusy() || findDevice.isConnected()) {
                    return;
                }
                if (this.mProfile.toString().equals("PAN")) {
                    Log.d(
                            "LocalBluetoothProfileManager",
                            "PAN connection was rejected by NAP or Connection Timeout...");
                    int intExtra5 = intent.getIntExtra("android.bluetooth.pan.extra.LOCAL_ROLE", 0);
                    if (LocalBluetoothManager.mSystemUiInstance) {
                        if (localBluetoothManager.semIsForegroundActivity()
                                || localBluetoothManager.isTetheredSettings()) {
                            return;
                        }
                        if (intExtra5 == 1) {
                            string =
                                    context.getString(
                                            R.string.bluetooth_connecting_error_message,
                                            findDevice.getName());
                        } else {
                            String name =
                                    findDevice.mBondState == 10
                                            ? findDevice.mDeviceName
                                            : findDevice.getName();
                            if (BluetoothUtils.isRTL(context)) {
                                name =
                                        ComposerKt$$ExternalSyntheticOutline0.m(
                                                "\u200e", name, "\u200e");
                            }
                            string =
                                    context.getString(
                                            R.string.bluetooth_pan_connecting_error_summury, name);
                        }
                        BluetoothUtils.showToast(context, string);
                        return;
                    }
                    if (localBluetoothManager.semIsForegroundActivity()
                            || localBluetoothManager.isTetheredSettings()) {
                        if (intExtra5 == 1) {
                            findDevice.mErrorMsg =
                                    context.getString(
                                            R.string.bluetooth_pan_nap_connecting_error_summury);
                            return;
                        }
                        String name2 =
                                findDevice.mBondState == 10
                                        ? findDevice.mDeviceName
                                        : findDevice.getName();
                        if (BluetoothUtils.isRTL(context)) {
                            name2 =
                                    ComposerKt$$ExternalSyntheticOutline0.m(
                                            "\u200e", name2, "\u200e");
                        }
                        String string2 =
                                context.getString(
                                        R.string.bluetooth_pan_connecting_error_summury, name2);
                        if (localBluetoothManager.semIsForegroundActivity()) {
                            findDevice.mErrorMsg = string2;
                            return;
                        } else {
                            if (localBluetoothManager.isTetheredSettings()) {
                                BluetoothUtils.showToast(context, string2);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                if (!this.mProfile.toString().equals(PeripheralConstants.ConnectionProfile.HID)) {
                    if (localBluetoothManager.semIsForegroundActivity()
                            || !LocalBluetoothManager.mSystemUiInstance) {
                        return;
                    }
                    if (findDevice.mGroupId != -1) {
                        CachedBluetoothDevice cachedBluetoothDevice = findDevice.mLeadDevice;
                        if (cachedBluetoothDevice == null) {
                            cachedBluetoothDevice = findDevice;
                        }
                        if (BluetoothUtils.getDeviceForGroupConnectionState(cachedBluetoothDevice)
                                        .getMaxConnectionState()
                                > 0) {
                            return;
                        }
                    }
                    BluetoothUtils.showToast(
                            context,
                            context.getString(
                                    R.string.bluetooth_connecting_error_message,
                                    ComposerKt$$ExternalSyntheticOutline0.m(
                                            "\u200e", findDevice.getName(), "\u200e")));
                    return;
                }
                if (booleanExtra) {
                    if (!LocalBluetoothManager.mSystemUiInstance) {
                        if (localBluetoothManager.semIsForegroundActivity()) {
                            findDevice.mErrorMsg =
                                    context.getString(
                                            R.string
                                                    .bluetooth_hid_normally_connecting_error_summury);
                            return;
                        }
                        return;
                    } else {
                        if (localBluetoothManager.semIsForegroundActivity()) {
                            return;
                        }
                        String name3 = findDevice.getName();
                        if (BluetoothUtils.isRTL(context)) {
                            name3 =
                                    ComposerKt$$ExternalSyntheticOutline0.m(
                                            "\u200e", name3, "\u200e");
                        }
                        BluetoothUtils.showToast(
                                context,
                                context.getString(
                                        R.string.bluetooth_connecting_error_message, name3));
                        return;
                    }
                }
                Log.d(
                        "LocalBluetoothProfileManager",
                        "Failed to connect " + this.mProfile + " device");
                String name4 = findDevice.getName();
                if (BluetoothUtils.isRTL(context)) {
                    name4 = ComposerKt$$ExternalSyntheticOutline0.m("\u200e", name4, "\u200e");
                }
                String string3 =
                        context.getString(R.string.bluetooth_hid_connecting_error_summury, name4);
                if (LocalBluetoothManager.mSystemUiInstance) {
                    if (localBluetoothManager.semIsForegroundActivity()) {
                        return;
                    }
                    BluetoothUtils.showToast(context, string3);
                } else if (localBluetoothManager.semIsForegroundActivity()) {
                    findDevice.mErrorMsg = string3;
                }
            }
        }
    }

    public LocalBluetoothProfileManager(
            Context context,
            LocalBluetoothAdapter localBluetoothAdapter,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            BluetoothEventManager bluetoothEventManager) {
        HashMap hashMap = new HashMap();
        this.mProfileNameMap = hashMap;
        this.mServiceListeners = new CopyOnWriteArrayList();
        this.mContext = context;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mEventManager = bluetoothEventManager;
        localBluetoothAdapter.mProfileManager = this;
        bluetoothEventManager.mProfileManager = this;
        this.mLocalAdapter = localBluetoothAdapter;
        if (this.mPanProfile == null) {
            PanProfile panProfile = new PanProfile(context, cachedBluetoothDeviceManager, this);
            this.mPanProfile = panProfile;
            bluetoothEventManager.addProfileHandler(
                    "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED",
                    new PanStateChangedHandler(panProfile));
            hashMap.put("PAN", panProfile);
        } else {
            Log.w("LocalBluetoothProfileManager", "Warning: PAN profile was previously added.");
        }
        if (this.mSapProfile == null) {
            SapProfile sapProfile = new SapProfile(context, cachedBluetoothDeviceManager, this);
            this.mSapProfile = sapProfile;
            addProfile(
                    sapProfile,
                    "SAP",
                    "android.bluetooth.sap.profile.action.CONNECTION_STATE_CHANGED");
        } else {
            Log.w("LocalBluetoothProfileManager", "Warning: SAP profile was previously added.");
        }
        if (DEBUG) {
            Log.d(
                    "LocalBluetoothProfileManager",
                    "LocalBluetoothProfileManager construction complete");
        }
    }

    public final void addProfile(
            LocalBluetoothProfile localBluetoothProfile, String str, String str2) {
        this.mEventManager.addProfileHandler(str2, new StateChangedHandler(localBluetoothProfile));
        ((HashMap) this.mProfileNameMap).put(str, localBluetoothProfile);
    }

    public final void addServiceListener(ServiceListener serviceListener) {
        ((CopyOnWriteArrayList) this.mServiceListeners).add(serviceListener);
    }

    public final void callServiceConnectedListeners() {
        Iterator it = new ArrayList(this.mServiceListeners).iterator();
        while (it.hasNext()) {
            ((ServiceListener) it.next()).onServiceConnected();
        }
    }

    public final void callServiceDisconnectedListeners() {
        Iterator it = new ArrayList(this.mServiceListeners).iterator();
        while (it.hasNext()) {
            ((ServiceListener) it.next()).onServiceDisconnected();
        }
    }

    public HidDeviceProfile getHidDeviceProfile() {
        return null;
    }

    public final LocalBluetoothProfile getProfileByName(String str) {
        return (LocalBluetoothProfile) ((HashMap) this.mProfileNameMap).get(str);
    }

    public final void removeServiceListener(ServiceListener serviceListener) {
        ((CopyOnWriteArrayList) this.mServiceListeners).remove(serviceListener);
    }

    public final synchronized void updateLocalProfiles() {
        boolean z;
        Log.d("LocalBluetoothProfileManager", "updateLocalProfiles :: ");
        List supportedProfiles = BluetoothAdapter.getDefaultAdapter().getSupportedProfiles();
        if (CollectionUtils.isEmpty(supportedProfiles)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "supportedList is null");
            }
            return;
        }
        boolean z2 = true;
        if (this.mA2dpProfile == null && supportedProfiles.contains(2)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local A2DP profile");
            }
            A2dpProfile a2dpProfile = new A2dpProfile(this.mContext, this.mDeviceManager, this);
            this.mA2dpProfile = a2dpProfile;
            addProfile(
                    a2dpProfile,
                    "A2DP",
                    "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        } else {
            z = false;
        }
        if (this.mHeadsetProfile == null && supportedProfiles.contains(1)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local HEADSET profile");
            }
            HeadsetProfile headsetProfile =
                    new HeadsetProfile(this.mContext, this.mDeviceManager, this);
            this.mHeadsetProfile = headsetProfile;
            addProfile(
                    headsetProfile,
                    "HEADSET",
                    "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mMapClientProfile == null && supportedProfiles.contains(18)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local MAP CLIENT profile");
            }
            MapClientProfile mapClientProfile =
                    new MapClientProfile(this.mContext, this.mDeviceManager, this);
            this.mMapClientProfile = mapClientProfile;
            addProfile(
                    mapClientProfile,
                    "MAP Client",
                    "android.bluetooth.mapmce.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mMapProfile == null && supportedProfiles.contains(9)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local MAP profile");
            }
            MapProfile mapProfile = new MapProfile(this.mContext, this.mDeviceManager, this);
            this.mMapProfile = mapProfile;
            addProfile(
                    mapProfile,
                    "MAP",
                    "android.bluetooth.map.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mOppProfile == null && supportedProfiles.contains(20)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local OPP profile");
            }
            OppProfile oppProfile = new OppProfile();
            this.mOppProfile = oppProfile;
            ((HashMap) this.mProfileNameMap).put("OPP", oppProfile);
        }
        if (this.mHearingAidProfile == null && supportedProfiles.contains(21)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local Hearing Aid profile");
            }
            HearingAidProfile hearingAidProfile =
                    new HearingAidProfile(this.mContext, this.mDeviceManager, this);
            this.mHearingAidProfile = hearingAidProfile;
            addProfile(
                    hearingAidProfile,
                    "HearingAid",
                    "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mHapClientProfile == null && supportedProfiles.contains(28)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local HAP_CLIENT profile");
            }
            HapClientProfile hapClientProfile =
                    new HapClientProfile(this.mContext, this.mDeviceManager, this);
            this.mHapClientProfile = hapClientProfile;
            addProfile(
                    hapClientProfile,
                    "HapClient",
                    "android.bluetooth.action.HAP_CONNECTION_STATE_CHANGED");
        }
        if (this.mHidProfile == null && supportedProfiles.contains(4)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local HID_HOST profile");
            }
            HidProfile hidProfile = new HidProfile(this.mContext, this.mDeviceManager, this);
            this.mHidProfile = hidProfile;
            addProfile(
                    hidProfile,
                    PeripheralConstants.ConnectionProfile.HID,
                    "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mPbapProfile == null && supportedProfiles.contains(6)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local PBAP profile");
            }
            Context context = this.mContext;
            PbapServerProfile pbapServerProfile = new PbapServerProfile();
            BluetoothAdapter.getDefaultAdapter()
                    .getProfileProxy(context, pbapServerProfile.new PbapServiceListener(), 6);
            this.mPbapProfile = pbapServerProfile;
            addProfile(
                    pbapServerProfile,
                    PbapServerProfile.NAME,
                    "android.bluetooth.pbap.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mSapProfile == null && supportedProfiles.contains(10)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local SAP profile");
            }
            SapProfile sapProfile = new SapProfile(this.mContext, this.mDeviceManager, this);
            this.mSapProfile = sapProfile;
            addProfile(
                    sapProfile,
                    "SAP",
                    "android.bluetooth.sap.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mSppProfile == null) {
            Log.d("LocalBluetoothProfileManager", "Adding local Spp profile");
            SppProfile sppProfile =
                    new SppProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager);
            this.mSppProfile = sppProfile;
            addProfile(
                    sppProfile,
                    PeripheralConstants.ConnectionProfile.SPP,
                    "com.samsung.bluetooth.action.GEAR_CONNECTION_STATE_CHANGED");
            z = true;
        } else {
            Log.w(
                    "LocalBluetoothProfileManager",
                    "updateLocalProfiles :: Spp profile was created already ");
        }
        if (this.mGattProfile == null) {
            Log.d("LocalBluetoothProfileManager", "Adding local Gatt profile");
            GattProfile gattProfile =
                    new GattProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager);
            this.mGattProfile = gattProfile;
            addProfile(
                    gattProfile,
                    "GATT",
                    "com.samsung.bluetooth.action.GATT_CONNECTION_STATE_CHANGED");
        } else {
            z2 = z;
        }
        if (this.mVolumeControlProfile == null && supportedProfiles.contains(23)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local Volume Control profile");
            }
            VolumeControlProfile volumeControlProfile =
                    new VolumeControlProfile(this.mContext, this.mDeviceManager, this);
            this.mVolumeControlProfile = volumeControlProfile;
            addProfile(
                    volumeControlProfile,
                    "VCP",
                    "android.bluetooth.volume-control.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mLeAudioProfile == null && supportedProfiles.contains(22)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local LE_AUDIO profile");
            }
            LeAudioProfile leAudioProfile =
                    new LeAudioProfile(this.mContext, this.mDeviceManager, this);
            this.mLeAudioProfile = leAudioProfile;
            addProfile(
                    leAudioProfile,
                    "LE_AUDIO",
                    "android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
        }
        if (this.mLeAudioBroadcast == null && supportedProfiles.contains(26)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local LE_AUDIO_BROADCAST profile");
            }
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                    new LocalBluetoothLeBroadcast(this.mContext, this.mDeviceManager, this);
            this.mLeAudioBroadcast = localBluetoothLeBroadcast;
            ((HashMap) this.mProfileNameMap).put("LE_AUDIO_BROADCAST", localBluetoothLeBroadcast);
        }
        if (this.mLeAudioBroadcastAssistant == null && supportedProfiles.contains(29)) {
            if (DEBUG) {
                Log.d(
                        "LocalBluetoothProfileManager",
                        "Adding local LE_AUDIO_BROADCAST_ASSISTANT profile");
            }
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                    new LocalBluetoothLeBroadcastAssistant(
                            this.mContext, this.mDeviceManager, this);
            this.mLeAudioBroadcastAssistant = localBluetoothLeBroadcastAssistant;
            addProfile(
                    localBluetoothLeBroadcastAssistant,
                    "LE_AUDIO_BROADCAST",
                    "android.bluetooth.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mCsipSetCoordinatorProfile == null && supportedProfiles.contains(25)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local CSIP set coordinator profile");
            }
            CsipSetCoordinatorProfile csipSetCoordinatorProfile =
                    new CsipSetCoordinatorProfile(this.mContext, this.mDeviceManager, this);
            this.mCsipSetCoordinatorProfile = csipSetCoordinatorProfile;
            addProfile(
                    csipSetCoordinatorProfile,
                    "CSIP Set Coordinator",
                    "android.bluetooth.action.CSIS_CONNECTION_STATE_CHANGED");
        }
        if (z2) {
            this.mEventManager.registerProfileIntentReceiver();
        }
    }

    public final synchronized void updateProfiles(
            ParcelUuid[] parcelUuidArr,
            ParcelUuid[] parcelUuidArr2,
            Collection collection,
            Collection collection2,
            CachedBluetoothDevice cachedBluetoothDevice) {
        HidProfile hidProfile;
        SppProfile sppProfile;
        HearingAidProfile hearingAidProfile;
        PanProfile panProfile;
        OppProfile oppProfile;
        A2dpProfile a2dpProfile;
        LeAudioProfile leAudioProfile;
        try {
            collection2.clear();
            collection2.addAll(collection);
            boolean z = DEBUG;
            if (z) {
                Log.d("LocalBluetoothProfileManager", "Current Profiles" + collection.toString());
            }
            collection.clear();
            if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.LE_AUDIO)
                    && (leAudioProfile = this.mLeAudioProfile) != null) {
                collection.add(leAudioProfile);
                collection2.remove(this.mLeAudioProfile);
            }
            if (this.mHeadsetProfile != null
                    && ((ArrayUtils.contains(parcelUuidArr2, BluetoothUuid.HSP_AG)
                                    && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HSP))
                            || (ArrayUtils.contains(parcelUuidArr2, BluetoothUuid.HFP_AG)
                                    && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HFP)))) {
                collection.add(this.mHeadsetProfile);
                collection2.remove(this.mHeadsetProfile);
            }
            if (BluetoothUuid.containsAnyUuid(parcelUuidArr, A2dpProfile.SINK_UUIDS)
                    && (a2dpProfile = this.mA2dpProfile) != null) {
                collection.add(a2dpProfile);
                collection2.remove(this.mA2dpProfile);
            }
            BluetoothUuid.containsAnyUuid(parcelUuidArr, A2dpSinkProfile.SRC_UUIDS);
            if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.OBEX_OBJECT_PUSH)
                    && (oppProfile = this.mOppProfile) != null) {
                collection.add(oppProfile);
                collection2.remove(this.mOppProfile);
            }
            if ((ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HID)
                            || ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HOGP))
                    && (hidProfile = this.mHidProfile) != null) {
                collection.add(hidProfile);
                collection2.remove(this.mHidProfile);
            }
            if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.NAP)
                    && (panProfile = this.mPanProfile) != null) {
                collection.add(panProfile);
                collection2.remove(this.mPanProfile);
            }
            if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HEARING_AID)
                    && (hearingAidProfile = this.mHearingAidProfile) != null) {
                collection.add(hearingAidProfile);
                collection2.remove(this.mHearingAidProfile);
            }
            if (this.mHapClientProfile != null
                    && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HAS)) {
                collection.add(this.mHapClientProfile);
                collection2.remove(this.mHapClientProfile);
            }
            if (this.mVolumeControlProfile != null
                    && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.VOLUME_CONTROL)) {
                collection.add(this.mVolumeControlProfile);
                collection2.remove(this.mVolumeControlProfile);
            }
            if (this.mCsipSetCoordinatorProfile != null
                    && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.COORDINATED_SET)) {
                collection.add(this.mCsipSetCoordinatorProfile);
                collection2.remove(this.mCsipSetCoordinatorProfile);
            }
            if (this.mLeAudioBroadcastAssistant != null
                    && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.BASS)) {
                collection.add(this.mLeAudioBroadcastAssistant);
                collection2.remove(this.mLeAudioBroadcastAssistant);
            }
            if (cachedBluetoothDevice != null
                    && cachedBluetoothDevice.getDeviceType() != 0
                    && (sppProfile = this.mSppProfile) != null) {
                collection.add(sppProfile);
                collection2.remove(this.mSppProfile);
            }
            if (collection2.contains(this.mSapProfile) && this.mSapProfile != null) {
                Log.d("LocalBluetoothProfileManager", "Adding back SAP profile");
                collection.add(this.mSapProfile);
                collection2.remove(this.mSapProfile);
            }
            if (collection2.contains(this.mMapProfile) && this.mMapProfile != null) {
                Log.d("LocalBluetoothProfileManager", "Adding back MAP profile");
                collection.add(this.mMapProfile);
                collection2.remove(this.mMapProfile);
            }
            if (collection2.contains(this.mPbapProfile) && this.mPbapProfile != null) {
                Log.d("LocalBluetoothProfileManager", "Adding back PBAP profile");
                collection.add(this.mPbapProfile);
                collection2.remove(this.mPbapProfile);
            }
            if (z) {
                Log.d("LocalBluetoothProfileManager", "New Profiles" + collection.toString());
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
