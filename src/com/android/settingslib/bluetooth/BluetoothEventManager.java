package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.SecBluetoothDevicePreference;
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothEventManager {
    public final IntentFilter mAdapterIntentFilter;
    public final AnonymousClass1 mBroadcastReceiver;
    public final Collection mCallbacks = new CopyOnWriteArrayList();
    public final Context mContext;
    public final DelayedSyncHandler mDelayedSyncHandler;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final Map mHandlerMap;
    public final AnonymousClass1 mIconBroadcastReceiver;
    public final LocalBluetoothAdapter mLocalAdapter;
    public final AnonymousClass1 mPackageBroadcastReceiver;
    public final AnonymousClass1 mProfileBroadcastReceiver;
    public final IntentFilter mProfileIntentFilter;
    public LocalBluetoothProfileManager mProfileManager;
    public final android.os.Handler mReceiverHandler;
    public final ArrayList mReceivers;
    public final Collection mSemCallbacks;
    public final UserHandle mUserHandle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DelayedSyncHandler extends android.os.Handler {
        public DelayedSyncHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ArrayList arrayList;
            if (message.what != 1) {
                return;
            }
            LocalBluetoothAdapter localBluetoothAdapter = BluetoothEventManager.this.mLocalAdapter;
            if (localBluetoothAdapter != null && localBluetoothAdapter.mAdapter.isDiscovering()) {
                BluetoothEventManager.this.mLocalAdapter.cancelDiscovery();
            }
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            bluetoothEventManager.getClass();
            boolean z = BluetoothUtils.DEBUG;
            if (z) {
                Log.d("BluetoothEventManager", "readSyncedDevices()");
            }
            List restoredDevices =
                    BluetoothUtils.getRestoredDevices(
                            bluetoothEventManager.mContext,
                            bluetoothEventManager.mProfileManager,
                            true);
            if (restoredDevices == null) {
                if (z) {
                    Log.d(
                            "BluetoothEventManager",
                            "readSyncedDevices():: There are no synced devices");
                    return;
                }
                return;
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                    bluetoothEventManager.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                try {
                    synchronized (cachedBluetoothDeviceManager) {
                        arrayList = new ArrayList();
                        for (int size = cachedBluetoothDeviceManager.mCachedDevices.size() - 1;
                                size >= 0;
                                size--) {
                            CachedBluetoothDevice cachedBluetoothDevice =
                                    cachedBluetoothDeviceManager.mCachedDevices.get(size);
                            if (cachedBluetoothDevice.mIsSynced) {
                                arrayList.add(cachedBluetoothDevice);
                                cachedBluetoothDeviceManager.removeDevice(cachedBluetoothDevice);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Iterator it = ((ArrayList) restoredDevices).iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice2.mBondState != 12) {
                    int indexOf = arrayList.indexOf(cachedBluetoothDevice2);
                    if (indexOf > -1) {
                        Log.d(
                                "CachedBluetoothDeviceManager",
                                "addSyncedDevices :: newDevice is added already - "
                                        + cachedBluetoothDevice2.getName());
                        CachedBluetoothDevice cachedBluetoothDevice3 =
                                (CachedBluetoothDevice) arrayList.get(indexOf);
                        cachedBluetoothDevice2.mErrorMsg = cachedBluetoothDevice3.mErrorMsg;
                        cachedBluetoothDevice2.mBondState = cachedBluetoothDevice3.mBondState;
                        cachedBluetoothDevice2.mIsBondingByCached =
                                cachedBluetoothDevice3.mIsBondingByCached;
                    }
                    if (cachedBluetoothDeviceManager.mCachedDevices.contains(
                            cachedBluetoothDevice2)) {
                        cachedBluetoothDeviceManager.removeDevice(cachedBluetoothDevice2);
                        cachedBluetoothDeviceManager.mBtManager.mEventManager.dispatchDeviceRemoved(
                                cachedBluetoothDevice2);
                    }
                    boolean addDevice =
                            cachedBluetoothDeviceManager.addDevice(cachedBluetoothDevice2);
                    cachedBluetoothDevice2.mSequence =
                            cachedBluetoothDeviceManager.mCachedDevices.indexOf(
                                    cachedBluetoothDevice2);
                    if (!addDevice) {
                        cachedBluetoothDeviceManager.mBtManager.mEventManager.dispatchDeviceAdded(
                                cachedBluetoothDevice2);
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DeviceNameChangedHandler implements Handler {
        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(
                Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            Log.d(
                    "BluetoothEventManager",
                    "DeviceNameChangedHandler :: com.android.settings.DEVICE_NAME_CHANGED");
            BluetoothUtils.updateDeviceName(context);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DeviceSyncHandler implements Handler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BluetoothEventManager this$0;

        public /* synthetic */ DeviceSyncHandler(
                BluetoothEventManager bluetoothEventManager, int i) {
            this.$r8$classId = i;
            this.this$0 = bluetoothEventManager;
        }

        private final void
                onReceive$com$android$settingslib$bluetooth$BluetoothEventManager$BondStateChangedHandler(
                        Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int i;
            String string;
            LocalBluetoothManager localBluetoothManager;
            LocalBluetoothManager localBluetoothManager2;
            String string2;
            LocalBluetoothProfileManager localBluetoothProfileManager;
            String[] strArr;
            int i2;
            if (bluetoothDevice == null) {
                Log.e("BluetoothEventManager", "ACTION_BOND_STATE_CHANGED with no EXTRA_DEVICE");
                return;
            }
            int intExtra =
                    intent.getIntExtra(
                            "android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
            int intExtra2 =
                    intent.getIntExtra(
                            "android.bluetooth.device.extra.PREVIOUS_BOND_STATE",
                            Integer.MIN_VALUE);
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.this$0.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                if (cachedBluetoothDeviceManager.isOngoingPairByCsip(bluetoothDevice)) {
                    if (intExtra != 11) {
                        cachedBluetoothDeviceManager.mGroupIdOfLateBonding = -1;
                        cachedBluetoothDeviceManager.setOngoingSetMemberPair();
                        if (intExtra != 10
                                && cachedBluetoothDeviceManager.findDevice(bluetoothDevice)
                                        == null) {
                            CachedBluetoothDevice cachedBluetoothDevice =
                                    new CachedBluetoothDevice(
                                            cachedBluetoothDeviceManager.mContext,
                                            cachedBluetoothDeviceManager.mBtManager.mProfileManager,
                                            bluetoothDevice);
                            cachedBluetoothDeviceManager.addDevice(cachedBluetoothDevice);
                            if (cachedBluetoothDevice.mBondState == 10) {
                                cachedBluetoothDevice.startPairing();
                            } else {
                                cachedBluetoothDevice.checkLEConnectionGuide(true, false);
                                cachedBluetoothDevice.mConnectAttempted =
                                        SystemClock.elapsedRealtime();
                                cachedBluetoothDevice.connectDevice();
                            }
                        }
                    }
                    Log.d("BluetoothEventManager", "Should not update UI for the set member");
                }
            }
            int intExtra3 =
                    intent.getIntExtra("android.bluetooth.device.extra.REASON", Integer.MIN_VALUE);
            CachedBluetoothDevice findDevice =
                    this.this$0.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                boolean z = BluetoothUtils.DEBUG;
                if (z) {
                    Log.w(
                            "BluetoothEventManager",
                            "Got bonding state changed for "
                                    + bluetoothDevice
                                    + ", but we have no record of that device.");
                }
                this.this$0.readRestoredDevices();
                if (!this.this$0.readPairedDevices() && z) {
                    Log.e(
                            "BluetoothEventManager",
                            "Got bonding state changed for "
                                    + bluetoothDevice
                                    + ", but we have no record of that device.");
                }
                findDevice = this.this$0.mDeviceManager.findDevice(bluetoothDevice);
            }
            if (intExtra == 12) {
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager2 =
                        this.this$0.mDeviceManager;
                synchronized (cachedBluetoothDeviceManager2) {
                    final String identityAddress = bluetoothDevice.getIdentityAddress();
                    if (identityAddress != null
                            && !identityAddress.equals(bluetoothDevice.getAddress())) {
                        cachedBluetoothDeviceManager2.mCachedDevices.removeIf(
                                new Predicate() { // from class:
                                                  // com.android.settingslib.bluetooth.CachedBluetoothDeviceManager$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        CachedBluetoothDevice cachedBluetoothDevice2 =
                                                (CachedBluetoothDevice) obj;
                                        boolean equals =
                                                cachedBluetoothDevice2
                                                        .mDevice
                                                        .getAddress()
                                                        .equals(identityAddress);
                                        if (equals) {
                                            Log.d(
                                                    "CachedBluetoothDeviceManager",
                                                    "Remove instance for identity address "
                                                            + cachedBluetoothDevice2);
                                        }
                                        return equals;
                                    }
                                });
                    }
                }
            }
            if (findDevice == null) {
                if (BluetoothUtils.DEBUG) {
                    Log.e(
                            "BluetoothEventManager",
                            "Got bonding state changed for "
                                    + bluetoothDevice
                                    + ", but device not added in cache.");
                }
                i = 12;
            } else {
                Log.d(
                        "BluetoothEventManager",
                        "CachedBluetoothDevice was created from paired devices. It will be"
                            + " refreshed.");
                Log.d(
                        "CachedBluetoothDevice",
                        "onBondingStateChanged :: Device ["
                                + findDevice.getNameForLog()
                                + "], bond state change to "
                                + findDevice.mBondState
                                + " -> "
                                + intExtra);
                int i3 = findDevice.mBondState;
                findDevice.mBondState = intExtra;
                BluetoothRetryDetector.FailCase failCase =
                        BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH;
                BluetoothRetryDetector.FailCase failCase2 =
                        BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING;
                if (intExtra == 10) {
                    findDevice.clearProfileConnectionState();
                    if (i3 == 11) {
                        findDevice.mBondingDetector.mCount++;
                        if (findDevice.mIsRestored
                                && findDevice.mIsBondingByCached
                                && (localBluetoothManager =
                                                LocalBluetoothManager.getInstance(
                                                        findDevice.mContext, null))
                                        != null) {
                            String str = findDevice.mAddress;
                            BluetoothRetryDetector bluetoothRetryDetector =
                                    localBluetoothManager.mRestoredRetryDetector;
                            if (bluetoothRetryDetector.mIsForRestored) {
                                bluetoothRetryDetector.mRestoredDeviceList.put(
                                        str,
                                        Integer.valueOf(
                                                (bluetoothRetryDetector.mRestoredDeviceList
                                                                        .containsKey(str)
                                                                ? ((Integer)
                                                                                bluetoothRetryDetector
                                                                                        .mRestoredDeviceList
                                                                                        .get(str))
                                                                        .intValue()
                                                                : 0)
                                                        + 1));
                            }
                            Intent intent2 =
                                    new Intent(
                                            "com.samsung.settings.bluetooth.restoredialog.LAUNCH");
                            intent2.setFlags(335544320);
                            intent2.putExtra("cachedAddress", findDevice.mAddress);
                            findDevice.mContext.startActivityAsUser(intent2, UserHandle.CURRENT);
                        }
                    } else if (i3 == 12) {
                        findDevice.mBondingDetector.reset();
                        if (findDevice.isRing()) {
                            findDevice.mBondingDetector.setFailCase(failCase2);
                        } else if (BluetoothUtils.isGalaxyWatchDevice(
                                findDevice.mDeviceName,
                                findDevice.mBtClass,
                                findDevice.getManufacturerRawData(),
                                findDevice.mDevice.getUuids())) {
                            findDevice.mBondingDetector.setFailCase(failCase);
                        } else {
                            findDevice.mBondingDetector.setFailCase(
                                    BluetoothRetryDetector.FailCase.PAIRING_FAILURE);
                        }
                    }
                    findDevice.mDevice.setPhonebookAccessPermission(0);
                    findDevice.mDevice.setMessageAccessPermission(0);
                    findDevice.mDevice.setSimAccessPermission(0);
                    findDevice.mBondTimestamp = null;
                    findDevice.mIsBondingByCached = false;
                } else if (intExtra == 12) {
                    findDevice.mBondTimestamp = new Timestamp(System.currentTimeMillis());
                    findDevice.mIsBondingByCached = false;
                    if (findDevice.mIsSynced) {
                        findDevice.mIsSynced = false;
                    }
                    if (findDevice.mIsRestored) {
                        findDevice.mIsRestored = false;
                        BluetoothClass bluetoothClass = findDevice.mDevice.getBluetoothClass();
                        BluetoothClass bluetoothClass2 = findDevice.mBtClass;
                        if (bluetoothClass2 != null
                                && bluetoothClass2.getMajorDeviceClass() != 7936
                                && (bluetoothClass == null
                                        || findDevice.mBtClass != bluetoothClass)) {
                            Log.d(
                                    "CachedBluetoothDevice",
                                    "onBondingStateChanged :: COD - " + findDevice.mBtClass);
                            findDevice.mDevice.setBluetoothClass(findDevice.mRestoredDevice.mCod);
                        }
                        if (findDevice.getManufacturerRawData() != null
                                && !Arrays.equals(
                                        findDevice.mDevice.semGetManufacturerData(),
                                        findDevice.getManufacturerRawData())) {
                            findDevice.mDevice.semSetManufacturerData(
                                    findDevice.getManufacturerRawData());
                        }
                        if (!findDevice.mName.equals(findDevice.mDevice.getAlias())) {
                            findDevice.mDevice.setAlias(findDevice.mName);
                        }
                    }
                    findDevice.resetRetryDetector();
                    if (findDevice.isRing()) {
                        findDevice.mBondingDetector.setFailCase(failCase2);
                    } else if (BluetoothUtils.isGalaxyWatchDevice(
                            findDevice.mDeviceName,
                            findDevice.mBtClass,
                            findDevice.getManufacturerRawData(),
                            findDevice.mDevice.getUuids())) {
                        findDevice.mBondingDetector.setFailCase(failCase);
                    } else {
                        findDevice.mBondingDetector.setFailCase(
                                BluetoothRetryDetector.FailCase.CONNECTION_FAILURE);
                    }
                }
                Iterator it = ((CopyOnWriteArrayList) findDevice.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((CachedBluetoothDevice.Callback) it.next()).onDeviceAttributesChanged();
                }
                Iterator it2 = ((ArrayList) findDevice.mSemCallbacks).iterator();
                while (it2.hasNext()) {
                    SecBluetoothDevicePreference secBluetoothDevicePreference =
                            (SecBluetoothDevicePreference)
                                    ((CachedBluetoothDevice.SemCallback) it2.next());
                    Log.i(
                            "SecBluetoothDevicePreference",
                            "semOnDeviceBondStateChanged: Device = "
                                    + secBluetoothDevicePreference.mCachedDevice.getNameForLog()
                                    + ", bondState = "
                                    + intExtra);
                    if (intExtra == 11) {
                        secBluetoothDevicePreference.mCachedDevice.mErrorMsg = null;
                    }
                    if (intExtra == 10 && intExtra3 != Integer.MIN_VALUE) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                intExtra3,
                                "semOnDeviceBondStateChanged: reason = ",
                                "SecBluetoothDevicePreference");
                        CachedBluetoothDevice cachedBluetoothDevice2 =
                                secBluetoothDevicePreference.mCachedDevice;
                        if (cachedBluetoothDevice2.mIsRestored) {
                            cachedBluetoothDevice2.mErrorMsg =
                                    secBluetoothDevicePreference
                                            .getContext()
                                            .getString(
                                                    R.string
                                                            .bluetooth_settings_guide_pairing_fail_restored);
                            secBluetoothDevicePreference
                                    .mListController
                                    .getSelectedAdapter(secBluetoothDevicePreference)
                                    .setNeedUpdatePreference();
                        } else {
                            switch (intExtra3) {
                                case 1:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                    string =
                                            secBluetoothDevicePreference
                                                    .getContext()
                                                    .getString(
                                                            R.string
                                                                    .bluetooth_settings_guide_pairing_fail_auth);
                                    break;
                                case 2:
                                    string =
                                            secBluetoothDevicePreference
                                                    .getContext()
                                                    .getString(
                                                            R.string
                                                                    .bluetooth_settings_guide_pairing_fail_reject);
                                    break;
                                case 3:
                                default:
                                    string = ApnSettings.MVNO_NONE;
                                    break;
                                case 4:
                                    if (!BluetoothUtils.isBlackListDevice(
                                            cachedBluetoothDevice2.mDevice)) {
                                        string =
                                                secBluetoothDevicePreference
                                                        .getContext()
                                                        .getString(
                                                                R.string
                                                                        .bluetooth_settings_guide_pairing_fail_remote_down);
                                        break;
                                    } else {
                                        string =
                                                secBluetoothDevicePreference
                                                        .getContext()
                                                        .getString(
                                                                R.string
                                                                        .bluetooth_settings_guide_pairing_fail_carkit);
                                        break;
                                    }
                            }
                            if (!string.isEmpty()) {
                                secBluetoothDevicePreference.mCachedDevice.mErrorMsg = string;
                            }
                            secBluetoothDevicePreference
                                    .mListController
                                    .getSelectedAdapter(secBluetoothDevicePreference)
                                    .setNeedUpdatePreference();
                        }
                    }
                }
                findDevice.refresh();
                if (intExtra == 12 && findDevice.mDevice.isBondingInitiatedLocally()) {
                    if (LocalBluetoothManager.getInstance(findDevice.mContext, null) != null
                            && LocalBluetoothManager.mSystemUiInstance) {
                        findDevice.mConnectAttempted = SystemClock.elapsedRealtime();
                        findDevice.connectDevice();
                    }
                    ((HashSet) HearingAidStatsLogUtils.sJustBondedDeviceAddressSet)
                            .add(findDevice.mDevice.getAddress());
                }
                if (findDevice.mVisible) {
                    findDevice.refresh();
                    Iterator it3 = ((CopyOnWriteArrayList) this.this$0.mCallbacks).iterator();
                    while (it3.hasNext()) {
                        ((BluetoothCallback) it3.next())
                                .onDeviceBondStateChanged(findDevice, intExtra);
                    }
                } else if (intExtra == 10) {
                    this.this$0.mDeviceManager.removeDevice(findDevice);
                }
                i = 12;
            }
            if (intExtra == i) {
                if (LocalBluetoothManager.getInstance(context, null) != null
                        && !LocalBluetoothManager.mSystemUiInstance) {
                    BluetoothEventManager bluetoothEventManager = this.this$0;
                    bluetoothEventManager.getClass();
                    StringBuilder sb = new StringBuilder();
                    String replace =
                            bluetoothDevice.getAddress().replace(":", ApnSettings.MVNO_NONE);
                    String string3 =
                            bluetoothEventManager
                                    .mContext
                                    .getSharedPreferences("bluetooth_blocking_device", 0)
                                    .getString("blocking_device_list", ApnSettings.MVNO_NONE);
                    if (!string3.equals(ApnSettings.MVNO_NONE)) {
                        String[] split = string3.split(";");
                        if (split != null) {
                            int length = split.length;
                            int i4 = 0;
                            while (i4 < length) {
                                String[] split2 = split[i4].split(",");
                                if (split2.length == 5) {
                                    String str2 = split2[0];
                                    String str3 = split2[1];
                                    try {
                                        int parseInt = Integer.parseInt(split2[2]);
                                        strArr = split;
                                        try {
                                            int parseInt2 = Integer.parseInt(split2[3]);
                                            int parseInt3 = Integer.parseInt(split2[4]);
                                            i2 = length;
                                            if (parseInt3 <= 2 && !replace.equals(str2)) {
                                                sb.append(
                                                        str2 + "," + str3 + "," + parseInt + ","
                                                                + parseInt2 + "," + parseInt3
                                                                + ";");
                                            }
                                        } catch (NumberFormatException unused) {
                                        }
                                    } catch (NumberFormatException unused2) {
                                    }
                                    i4++;
                                    split = strArr;
                                    length = i2;
                                }
                                strArr = split;
                                i2 = length;
                                i4++;
                                split = strArr;
                                length = i2;
                            }
                        }
                        String sb2 = sb.toString();
                        SharedPreferences.Editor edit =
                                bluetoothEventManager
                                        .mContext
                                        .getSharedPreferences("bluetooth_blocking_device", 0)
                                        .edit();
                        edit.putString("blocking_device_list", sb2);
                        edit.commit();
                    }
                }
                if (findDevice == null
                        || (localBluetoothProfileManager = this.this$0.mProfileManager) == null
                        || !findDevice.hasProfile(
                                localBluetoothProfileManager.mCsipSetCoordinatorProfile)
                        || findDevice.mGroupId != -1) {
                    return;
                }
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager3 =
                        this.this$0.mDeviceManager;
                synchronized (cachedBluetoothDeviceManager3) {
                    cachedBluetoothDeviceManager3.mCsipDeviceManager.initCsipDeviceIfNeeded(
                            findDevice);
                }
                return;
            }
            if (intExtra != 10
                    || (localBluetoothManager2 = LocalBluetoothManager.getInstance(context, null))
                            == null) {
                return;
            }
            if (findDevice != null) {
                if (findDevice.mGroupId != -1 || findDevice.getHiSyncId() != 0) {
                    if (LocalBluetoothManager.mSystemUiInstance) {
                        CachedBluetoothDeviceManager cachedBluetoothDeviceManager4 =
                                this.this$0.mDeviceManager;
                        synchronized (cachedBluetoothDeviceManager4) {
                            try {
                                int i5 = findDevice.mGroupId;
                                findDevice.setGroupId(-1);
                                CachedBluetoothDevice findMainDevice =
                                        cachedBluetoothDeviceManager4.mCsipDeviceManager
                                                .findMainDevice(findDevice);
                                HashSet hashSet = new HashSet(findDevice.mMemberDevices);
                                if (!hashSet.isEmpty()) {
                                    Iterator it4 = hashSet.iterator();
                                    while (it4.hasNext()) {
                                        CachedBluetoothDevice cachedBluetoothDevice3 =
                                                (CachedBluetoothDevice) it4.next();
                                        cachedBluetoothDevice3.unpair();
                                        cachedBluetoothDevice3.setGroupId(-1);
                                        ((HashSet) findDevice.mMemberDevices)
                                                .remove(cachedBluetoothDevice3);
                                        cachedBluetoothDevice3.mLeadDevice = null;
                                    }
                                } else if (findMainDevice != null) {
                                    findMainDevice.unpair();
                                }
                                BluetoothDevice bluetoothDevice2 =
                                        cachedBluetoothDeviceManager4.mOngoingSetMemberPair;
                                if (bluetoothDevice2 != null
                                        && cachedBluetoothDeviceManager4.mOngoingSetMemberGroupId
                                                == i5) {
                                    if (bluetoothDevice2.getBondState() == 11) {
                                        BluetoothDump.BtLog(
                                                "CachedBluetoothDeviceManager -- onDeviceUnpaired:"
                                                    + " cancelBondProcess()");
                                        cachedBluetoothDeviceManager4.mOngoingSetMemberPair
                                                .cancelBondProcess();
                                    } else if (cachedBluetoothDeviceManager4.mOngoingSetMemberPair
                                                    .getBondState()
                                            == 12) {
                                        BluetoothDump.BtLog(
                                                "CachedBluetoothDeviceManager -- onDeviceUnpaired:"
                                                    + " removeBond()");
                                        cachedBluetoothDeviceManager4.mOngoingSetMemberPair
                                                .removeBond();
                                    }
                                }
                                CachedBluetoothDevice findMainDevice2 =
                                        cachedBluetoothDeviceManager4.mHearingAidDeviceManager
                                                .findMainDevice(findDevice);
                                CachedBluetoothDevice cachedBluetoothDevice4 =
                                        findDevice.mSubDevice;
                                if (cachedBluetoothDevice4 != null) {
                                    if (cachedBluetoothDevice4.mIsRestored) {
                                        cachedBluetoothDeviceManager4.removeRestoredDevice(
                                                cachedBluetoothDevice4);
                                    } else {
                                        cachedBluetoothDevice4.unpairLegacy();
                                    }
                                    findDevice.mSubDevice = null;
                                } else if (findMainDevice2 != null) {
                                    if (findMainDevice2.mIsRestored) {
                                        cachedBluetoothDeviceManager4.removeRestoredDevice(
                                                findMainDevice2);
                                    } else {
                                        findMainDevice2.unpairLegacy();
                                    }
                                    findMainDevice2.mSubDevice = null;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                    if (findDevice.mGroupId != -1) {
                        findDevice.setGroupId(-1);
                    } else if (findDevice.getHiSyncId() != 0) {
                        findDevice.mSubDevice = null;
                    }
                }
                if (intExtra2 == 12) {
                    this.this$0.mDeviceManager.removeDevice(findDevice);
                }
            }
            if (localBluetoothManager2.semIsForegroundActivity()
                    || LocalBluetoothManager.mSystemUiInstance
                    || this.this$0.isBlockingDevice(bluetoothDevice)) {
                return;
            }
            if (context == null) {
                Log.d("BluetoothEventManager", "showUnbondMessage: context is null");
                return;
            }
            if (findDevice != null && findDevice.mIsRestored && intExtra3 != 9) {
                BluetoothUtils.showToast(
                        context,
                        BluetoothUtils.isTablet()
                                ? context.getString(R.string.bluetooth_pairing_fail_restored_tablet)
                                : context.getString(R.string.bluetooth_pairing_fail_restored));
                return;
            }
            String alias = bluetoothDevice.getAlias();
            if (alias == null) {
                alias = bluetoothDevice.getAddress();
            }
            switch (intExtra3) {
                case 1:
                case 5:
                case 6:
                case 7:
                case 8:
                    string2 = context.getString(R.string.bluetooth_pairing_error_message, alias);
                    break;
                case 2:
                    string2 =
                            context.getString(
                                    R.string.bluetooth_pairing_rejected_error_message, alias);
                    break;
                case 3:
                default:
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(
                            intExtra3,
                            "showUnbondMessage: Not displaying any message for reason: ",
                            "BluetoothEventManager");
                    return;
                case 4:
                    if (!BluetoothUtils.isBlackListDevice(bluetoothDevice)) {
                        string2 =
                                context.getString(
                                        R.string.bluetooth_pairing_device_down_error_message,
                                        alias);
                        break;
                    } else {
                        String m =
                                ComposerKt$$ExternalSyntheticOutline0.m("\u200e", alias, "\u200e");
                        string2 =
                                context.getString(
                                        R.string
                                                .bluetooth_pairing_device_down_black_list_error_message,
                                        m,
                                        m);
                        break;
                    }
            }
            BluetoothUtils.showToast(context, string2);
        }

        /* JADX WARN: Removed duplicated region for block: B:314:0x0525  */
        /* JADX WARN: Removed duplicated region for block: B:323:0x053e  */
        /* JADX WARN: Removed duplicated region for block: B:326:0x0545  */
        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(
                android.content.Context r12,
                android.content.Intent r13,
                android.bluetooth.BluetoothDevice r14) {
            /*
                Method dump skipped, instructions count: 1952
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settingslib.bluetooth.BluetoothEventManager.DeviceSyncHandler.onReceive(android.content.Context,"
                        + " android.content.Intent, android.bluetooth.BluetoothDevice):void");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Handler {
        void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScanningStateChangedHandler implements Handler {
        public final boolean mStarted;

        public ScanningStateChangedHandler(boolean z) {
            this.mStarted = z;
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(
                Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            Iterator it = ((CopyOnWriteArrayList) BluetoothEventManager.this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onScanningStateChanged(this.mStarted);
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                    BluetoothEventManager.this.mDeviceManager;
            boolean z = this.mStarted;
            synchronized (cachedBluetoothDeviceManager) {
                if (z) {
                    for (int size = cachedBluetoothDeviceManager.mCachedDevices.size() - 1;
                            size >= 0;
                            size--) {
                        CachedBluetoothDevice cachedBluetoothDevice =
                                cachedBluetoothDeviceManager.mCachedDevices.get(size);
                        cachedBluetoothDevice.setJustDiscovered(false);
                        HashSet hashSet = (HashSet) cachedBluetoothDevice.mMemberDevices;
                        if (!hashSet.isEmpty()) {
                            Iterator it2 = hashSet.iterator();
                            while (it2.hasNext()) {
                                ((CachedBluetoothDevice) it2.next()).setJustDiscovered(false);
                            }
                            return;
                        } else {
                            CachedBluetoothDevice cachedBluetoothDevice2 =
                                    cachedBluetoothDevice.mSubDevice;
                            if (cachedBluetoothDevice2 != null) {
                                cachedBluetoothDevice2.setJustDiscovered(false);
                            }
                        }
                    }
                }
            }
        }
    }

    static {
        Log.isLoggable("BluetoothEventManager", 3);
    }

    /* JADX WARN: Type inference failed for: r11v1, types: [com.android.settingslib.bluetooth.BluetoothEventManager$1] */
    /* JADX WARN: Type inference failed for: r11v2, types: [com.android.settingslib.bluetooth.BluetoothEventManager$1] */
    public BluetoothEventManager(
            LocalBluetoothAdapter localBluetoothAdapter,
            LocalBluetoothManager localBluetoothManager,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            final Context context) {
        final int i = 1;
        this.mBroadcastReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.settingslib.bluetooth.BluetoothEventManager.1
                    public final /* synthetic */ BluetoothEventManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(final Context context2, Intent intent) {
                        switch (i) {
                            case 0:
                                String action = intent.getAction();
                                if (action != null) {
                                    Log.i(
                                            "BluetoothEventManager",
                                            "onReceive : action ".concat(action));
                                    if (!action.equals(
                                            "com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL")) {
                                        if (action.equals(
                                                "com.samsung.bluetooth.device.action.RESOURCE_UPDATE")) {
                                            BluetoothDevice bluetoothDevice =
                                                    (BluetoothDevice)
                                                            intent.getParcelableExtra(
                                                                    "android.bluetooth.device.extra.DEVICE");
                                            final ArrayList parcelableArrayListExtra =
                                                    intent.getParcelableArrayListExtra(
                                                            "com.samsung.bluetooth.device.extra.RESOURCE_URI");
                                            if (parcelableArrayListExtra != null) {
                                                Log.d(
                                                        "BluetoothEventManager",
                                                        "BluetoothDevice.SEM_ACTION_RESOURCE_UPDATE:"
                                                            + " "
                                                                + parcelableArrayListExtra
                                                                        .toString());
                                            }
                                            if (bluetoothDevice != null) {
                                                CachedBluetoothDevice findDevice =
                                                        this.this$0.mDeviceManager.findDevice(
                                                                bluetoothDevice);
                                                if (findDevice != null) {
                                                    final String resourcePath =
                                                            findDevice.getResourcePath();
                                                    if (resourcePath != null) {
                                                        final Collection collection =
                                                                this.this$0.mSemCallbacks;
                                                        String str = ScspUtils.FILE_PATH_ROOT;
                                                        ExecutorService newSingleThreadExecutor =
                                                                Executors.newSingleThreadExecutor();
                                                        final android.os.Handler handler =
                                                                new android.os.Handler(
                                                                        Looper.getMainLooper());
                                                        newSingleThreadExecutor.execute(
                                                                new Runnable() { // from class:
                                                                    // com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda3
                                                                    @Override // java.lang.Runnable
                                                                    public final void run() {
                                                                        ArrayList arrayList =
                                                                                parcelableArrayListExtra;
                                                                        Context context3 = context2;
                                                                        String str2 = resourcePath;
                                                                        Collection collection2 =
                                                                                collection;
                                                                        Handler handler2 = handler;
                                                                        if (arrayList == null
                                                                                || arrayList.size()
                                                                                        <= 0) {
                                                                            Log.d(
                                                                                    "ScspUtils",
                                                                                    "saveResource:"
                                                                                        + " uri is"
                                                                                        + " null");
                                                                            return;
                                                                        }
                                                                        Iterator it =
                                                                                arrayList
                                                                                        .iterator();
                                                                        boolean z = false;
                                                                        while (it.hasNext()) {
                                                                            Uri uri =
                                                                                    (Uri) it.next();
                                                                            Log.d(
                                                                                    "ScspUtils",
                                                                                    "saveResource:"
                                                                                        + " uri = "
                                                                                            + uri
                                                                                                    .toString());
                                                                            z =
                                                                                    ScspUtils
                                                                                            .saveFileFromUri(
                                                                                                    context3,
                                                                                                    uri,
                                                                                                    str2);
                                                                            AirplaneModeEnabler$$ExternalSyntheticOutline0
                                                                                    .m(
                                                                                            "saveResource:"
                                                                                                + " success"
                                                                                                + " = ",
                                                                                            "ScspUtils",
                                                                                            z);
                                                                        }
                                                                        if (!z
                                                                                || collection2
                                                                                        == null) {
                                                                            return;
                                                                        }
                                                                        handler2.post(
                                                                                new ScspUtils$$ExternalSyntheticLambda1(
                                                                                        1,
                                                                                        collection2));
                                                                    }
                                                                });
                                                        break;
                                                    } else {
                                                        Log.i(
                                                                "BluetoothEventManager",
                                                                "onReceive : path is null");
                                                        break;
                                                    }
                                                } else {
                                                    Log.i(
                                                            "BluetoothEventManager",
                                                            "onReceive : cachedDevice is null");
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        final ArrayList parcelableArrayListExtra2 =
                                                intent.getParcelableArrayListExtra(
                                                        "com.samsung.bluetooth.adapter.extra.RESOURCE_ALL_URI");
                                        if (parcelableArrayListExtra2 != null) {
                                            Log.d(
                                                    "BluetoothEventManager",
                                                    "BluetoothAdapter.SEM_EXTRA_RESOURCE_ALL_URI: "
                                                            + parcelableArrayListExtra2.toString());
                                            final Collection collection2 =
                                                    this.this$0.mSemCallbacks;
                                            String str2 = ScspUtils.FILE_PATH_ROOT;
                                            ExecutorService newSingleThreadExecutor2 =
                                                    Executors.newSingleThreadExecutor();
                                            final android.os.Handler handler2 =
                                                    new android.os.Handler(Looper.getMainLooper());
                                            newSingleThreadExecutor2.execute(
                                                    new Runnable() { // from class:
                                                        // com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda2
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            ArrayList arrayList =
                                                                    parcelableArrayListExtra2;
                                                            Context context3 = context2;
                                                            Collection collection3 = collection2;
                                                            Handler handler3 = handler2;
                                                            if (arrayList == null
                                                                    || arrayList.size() <= 0) {
                                                                Log.d(
                                                                        "ScspUtils",
                                                                        "saveAllResources: uri is"
                                                                            + " null");
                                                                return;
                                                            }
                                                            Iterator it = arrayList.iterator();
                                                            boolean z = false;
                                                            while (it.hasNext()) {
                                                                Uri uri = (Uri) it.next();
                                                                Log.d(
                                                                        "ScspUtils",
                                                                        "saveAllResources: uri = "
                                                                                + uri.toString());
                                                                z =
                                                                        ScspUtils
                                                                                .makeAllResourceData(
                                                                                        context3,
                                                                                        uri);
                                                            }
                                                            if (!z || collection3 == null) {
                                                                return;
                                                            }
                                                            handler3.post(
                                                                    new ScspUtils$$ExternalSyntheticLambda1(
                                                                            2, collection3));
                                                        }
                                                    });
                                            break;
                                        }
                                    }
                                } else {
                                    Log.w("BluetoothEventManager", "onReceive : action is null");
                                    break;
                                }
                                break;
                            default:
                                String action2 = intent.getAction();
                                Log.v("BluetoothEventManager", "onReceive :: " + action2);
                                BluetoothEventManager bluetoothEventManager = this.this$0;
                                if (bluetoothEventManager.mLocalAdapter != null
                                        && bluetoothEventManager.mDeviceManager != null
                                        && bluetoothEventManager.mProfileManager != null) {
                                    BluetoothDevice bluetoothDevice2 =
                                            (BluetoothDevice)
                                                    intent.getParcelableExtra(
                                                            "android.bluetooth.device.extra.DEVICE");
                                    Handler handler3 =
                                            (Handler)
                                                    ((HashMap) this.this$0.mHandlerMap)
                                                            .get(action2);
                                    if (handler3 != null) {
                                        handler3.onReceive(context2, intent, bluetoothDevice2);
                                        break;
                                    }
                                } else {
                                    Log.e(
                                            "BluetoothEventManager",
                                            "onReceive :: ignore this broadcast, because"
                                                + " BluetoothSettings instance are not created"
                                                + " yet");
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mProfileBroadcastReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.settingslib.bluetooth.BluetoothEventManager.1
                    public final /* synthetic */ BluetoothEventManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(final Context context2, Intent intent) {
                        switch (i) {
                            case 0:
                                String action = intent.getAction();
                                if (action != null) {
                                    Log.i(
                                            "BluetoothEventManager",
                                            "onReceive : action ".concat(action));
                                    if (!action.equals(
                                            "com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL")) {
                                        if (action.equals(
                                                "com.samsung.bluetooth.device.action.RESOURCE_UPDATE")) {
                                            BluetoothDevice bluetoothDevice =
                                                    (BluetoothDevice)
                                                            intent.getParcelableExtra(
                                                                    "android.bluetooth.device.extra.DEVICE");
                                            final ArrayList parcelableArrayListExtra =
                                                    intent.getParcelableArrayListExtra(
                                                            "com.samsung.bluetooth.device.extra.RESOURCE_URI");
                                            if (parcelableArrayListExtra != null) {
                                                Log.d(
                                                        "BluetoothEventManager",
                                                        "BluetoothDevice.SEM_ACTION_RESOURCE_UPDATE:"
                                                            + " "
                                                                + parcelableArrayListExtra
                                                                        .toString());
                                            }
                                            if (bluetoothDevice != null) {
                                                CachedBluetoothDevice findDevice =
                                                        this.this$0.mDeviceManager.findDevice(
                                                                bluetoothDevice);
                                                if (findDevice != null) {
                                                    final String resourcePath =
                                                            findDevice.getResourcePath();
                                                    if (resourcePath != null) {
                                                        final Collection collection =
                                                                this.this$0.mSemCallbacks;
                                                        String str = ScspUtils.FILE_PATH_ROOT;
                                                        ExecutorService newSingleThreadExecutor =
                                                                Executors.newSingleThreadExecutor();
                                                        final android.os.Handler handler =
                                                                new android.os.Handler(
                                                                        Looper.getMainLooper());
                                                        newSingleThreadExecutor.execute(
                                                                new Runnable() { // from class:
                                                                    // com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda3
                                                                    @Override // java.lang.Runnable
                                                                    public final void run() {
                                                                        ArrayList arrayList =
                                                                                parcelableArrayListExtra;
                                                                        Context context3 = context2;
                                                                        String str2 = resourcePath;
                                                                        Collection collection2 =
                                                                                collection;
                                                                        Handler handler2 = handler;
                                                                        if (arrayList == null
                                                                                || arrayList.size()
                                                                                        <= 0) {
                                                                            Log.d(
                                                                                    "ScspUtils",
                                                                                    "saveResource:"
                                                                                        + " uri is"
                                                                                        + " null");
                                                                            return;
                                                                        }
                                                                        Iterator it =
                                                                                arrayList
                                                                                        .iterator();
                                                                        boolean z = false;
                                                                        while (it.hasNext()) {
                                                                            Uri uri =
                                                                                    (Uri) it.next();
                                                                            Log.d(
                                                                                    "ScspUtils",
                                                                                    "saveResource:"
                                                                                        + " uri = "
                                                                                            + uri
                                                                                                    .toString());
                                                                            z =
                                                                                    ScspUtils
                                                                                            .saveFileFromUri(
                                                                                                    context3,
                                                                                                    uri,
                                                                                                    str2);
                                                                            AirplaneModeEnabler$$ExternalSyntheticOutline0
                                                                                    .m(
                                                                                            "saveResource:"
                                                                                                + " success"
                                                                                                + " = ",
                                                                                            "ScspUtils",
                                                                                            z);
                                                                        }
                                                                        if (!z
                                                                                || collection2
                                                                                        == null) {
                                                                            return;
                                                                        }
                                                                        handler2.post(
                                                                                new ScspUtils$$ExternalSyntheticLambda1(
                                                                                        1,
                                                                                        collection2));
                                                                    }
                                                                });
                                                        break;
                                                    } else {
                                                        Log.i(
                                                                "BluetoothEventManager",
                                                                "onReceive : path is null");
                                                        break;
                                                    }
                                                } else {
                                                    Log.i(
                                                            "BluetoothEventManager",
                                                            "onReceive : cachedDevice is null");
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        final ArrayList parcelableArrayListExtra2 =
                                                intent.getParcelableArrayListExtra(
                                                        "com.samsung.bluetooth.adapter.extra.RESOURCE_ALL_URI");
                                        if (parcelableArrayListExtra2 != null) {
                                            Log.d(
                                                    "BluetoothEventManager",
                                                    "BluetoothAdapter.SEM_EXTRA_RESOURCE_ALL_URI: "
                                                            + parcelableArrayListExtra2.toString());
                                            final Collection collection2 =
                                                    this.this$0.mSemCallbacks;
                                            String str2 = ScspUtils.FILE_PATH_ROOT;
                                            ExecutorService newSingleThreadExecutor2 =
                                                    Executors.newSingleThreadExecutor();
                                            final android.os.Handler handler2 =
                                                    new android.os.Handler(Looper.getMainLooper());
                                            newSingleThreadExecutor2.execute(
                                                    new Runnable() { // from class:
                                                        // com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda2
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            ArrayList arrayList =
                                                                    parcelableArrayListExtra2;
                                                            Context context3 = context2;
                                                            Collection collection3 = collection2;
                                                            Handler handler3 = handler2;
                                                            if (arrayList == null
                                                                    || arrayList.size() <= 0) {
                                                                Log.d(
                                                                        "ScspUtils",
                                                                        "saveAllResources: uri is"
                                                                            + " null");
                                                                return;
                                                            }
                                                            Iterator it = arrayList.iterator();
                                                            boolean z = false;
                                                            while (it.hasNext()) {
                                                                Uri uri = (Uri) it.next();
                                                                Log.d(
                                                                        "ScspUtils",
                                                                        "saveAllResources: uri = "
                                                                                + uri.toString());
                                                                z =
                                                                        ScspUtils
                                                                                .makeAllResourceData(
                                                                                        context3,
                                                                                        uri);
                                                            }
                                                            if (!z || collection3 == null) {
                                                                return;
                                                            }
                                                            handler3.post(
                                                                    new ScspUtils$$ExternalSyntheticLambda1(
                                                                            2, collection3));
                                                        }
                                                    });
                                            break;
                                        }
                                    }
                                } else {
                                    Log.w("BluetoothEventManager", "onReceive : action is null");
                                    break;
                                }
                                break;
                            default:
                                String action2 = intent.getAction();
                                Log.v("BluetoothEventManager", "onReceive :: " + action2);
                                BluetoothEventManager bluetoothEventManager = this.this$0;
                                if (bluetoothEventManager.mLocalAdapter != null
                                        && bluetoothEventManager.mDeviceManager != null
                                        && bluetoothEventManager.mProfileManager != null) {
                                    BluetoothDevice bluetoothDevice2 =
                                            (BluetoothDevice)
                                                    intent.getParcelableExtra(
                                                            "android.bluetooth.device.extra.DEVICE");
                                    Handler handler3 =
                                            (Handler)
                                                    ((HashMap) this.this$0.mHandlerMap)
                                                            .get(action2);
                                    if (handler3 != null) {
                                        handler3.onReceive(context2, intent, bluetoothDevice2);
                                        break;
                                    }
                                } else {
                                    Log.e(
                                            "BluetoothEventManager",
                                            "onReceive :: ignore this broadcast, because"
                                                + " BluetoothSettings instance are not created"
                                                + " yet");
                                    break;
                                }
                                break;
                        }
                    }
                };
        final ArrayList arrayList = new ArrayList();
        this.mSemCallbacks = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mReceivers = arrayList2;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class:
                    // com.android.settingslib.bluetooth.BluetoothEventManager.1
                    public final /* synthetic */ BluetoothEventManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(final Context context2, Intent intent) {
                        switch (i) {
                            case 0:
                                String action = intent.getAction();
                                if (action != null) {
                                    Log.i(
                                            "BluetoothEventManager",
                                            "onReceive : action ".concat(action));
                                    if (!action.equals(
                                            "com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL")) {
                                        if (action.equals(
                                                "com.samsung.bluetooth.device.action.RESOURCE_UPDATE")) {
                                            BluetoothDevice bluetoothDevice =
                                                    (BluetoothDevice)
                                                            intent.getParcelableExtra(
                                                                    "android.bluetooth.device.extra.DEVICE");
                                            final ArrayList parcelableArrayListExtra =
                                                    intent.getParcelableArrayListExtra(
                                                            "com.samsung.bluetooth.device.extra.RESOURCE_URI");
                                            if (parcelableArrayListExtra != null) {
                                                Log.d(
                                                        "BluetoothEventManager",
                                                        "BluetoothDevice.SEM_ACTION_RESOURCE_UPDATE:"
                                                            + " "
                                                                + parcelableArrayListExtra
                                                                        .toString());
                                            }
                                            if (bluetoothDevice != null) {
                                                CachedBluetoothDevice findDevice =
                                                        this.this$0.mDeviceManager.findDevice(
                                                                bluetoothDevice);
                                                if (findDevice != null) {
                                                    final String resourcePath =
                                                            findDevice.getResourcePath();
                                                    if (resourcePath != null) {
                                                        final Collection collection =
                                                                this.this$0.mSemCallbacks;
                                                        String str = ScspUtils.FILE_PATH_ROOT;
                                                        ExecutorService newSingleThreadExecutor =
                                                                Executors.newSingleThreadExecutor();
                                                        final android.os.Handler handler =
                                                                new android.os.Handler(
                                                                        Looper.getMainLooper());
                                                        newSingleThreadExecutor.execute(
                                                                new Runnable() { // from class:
                                                                    // com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda3
                                                                    @Override // java.lang.Runnable
                                                                    public final void run() {
                                                                        ArrayList arrayList3 =
                                                                                parcelableArrayListExtra;
                                                                        Context context3 = context2;
                                                                        String str2 = resourcePath;
                                                                        Collection collection2 =
                                                                                collection;
                                                                        Handler handler2 = handler;
                                                                        if (arrayList3 == null
                                                                                || arrayList3.size()
                                                                                        <= 0) {
                                                                            Log.d(
                                                                                    "ScspUtils",
                                                                                    "saveResource:"
                                                                                        + " uri is"
                                                                                        + " null");
                                                                            return;
                                                                        }
                                                                        Iterator it =
                                                                                arrayList3
                                                                                        .iterator();
                                                                        boolean z = false;
                                                                        while (it.hasNext()) {
                                                                            Uri uri =
                                                                                    (Uri) it.next();
                                                                            Log.d(
                                                                                    "ScspUtils",
                                                                                    "saveResource:"
                                                                                        + " uri = "
                                                                                            + uri
                                                                                                    .toString());
                                                                            z =
                                                                                    ScspUtils
                                                                                            .saveFileFromUri(
                                                                                                    context3,
                                                                                                    uri,
                                                                                                    str2);
                                                                            AirplaneModeEnabler$$ExternalSyntheticOutline0
                                                                                    .m(
                                                                                            "saveResource:"
                                                                                                + " success"
                                                                                                + " = ",
                                                                                            "ScspUtils",
                                                                                            z);
                                                                        }
                                                                        if (!z
                                                                                || collection2
                                                                                        == null) {
                                                                            return;
                                                                        }
                                                                        handler2.post(
                                                                                new ScspUtils$$ExternalSyntheticLambda1(
                                                                                        1,
                                                                                        collection2));
                                                                    }
                                                                });
                                                        break;
                                                    } else {
                                                        Log.i(
                                                                "BluetoothEventManager",
                                                                "onReceive : path is null");
                                                        break;
                                                    }
                                                } else {
                                                    Log.i(
                                                            "BluetoothEventManager",
                                                            "onReceive : cachedDevice is null");
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        final ArrayList parcelableArrayListExtra2 =
                                                intent.getParcelableArrayListExtra(
                                                        "com.samsung.bluetooth.adapter.extra.RESOURCE_ALL_URI");
                                        if (parcelableArrayListExtra2 != null) {
                                            Log.d(
                                                    "BluetoothEventManager",
                                                    "BluetoothAdapter.SEM_EXTRA_RESOURCE_ALL_URI: "
                                                            + parcelableArrayListExtra2.toString());
                                            final Collection collection2 =
                                                    this.this$0.mSemCallbacks;
                                            String str2 = ScspUtils.FILE_PATH_ROOT;
                                            ExecutorService newSingleThreadExecutor2 =
                                                    Executors.newSingleThreadExecutor();
                                            final android.os.Handler handler2 =
                                                    new android.os.Handler(Looper.getMainLooper());
                                            newSingleThreadExecutor2.execute(
                                                    new Runnable() { // from class:
                                                        // com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda2
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            ArrayList arrayList3 =
                                                                    parcelableArrayListExtra2;
                                                            Context context3 = context2;
                                                            Collection collection3 = collection2;
                                                            Handler handler3 = handler2;
                                                            if (arrayList3 == null
                                                                    || arrayList3.size() <= 0) {
                                                                Log.d(
                                                                        "ScspUtils",
                                                                        "saveAllResources: uri is"
                                                                            + " null");
                                                                return;
                                                            }
                                                            Iterator it = arrayList3.iterator();
                                                            boolean z = false;
                                                            while (it.hasNext()) {
                                                                Uri uri = (Uri) it.next();
                                                                Log.d(
                                                                        "ScspUtils",
                                                                        "saveAllResources: uri = "
                                                                                + uri.toString());
                                                                z =
                                                                        ScspUtils
                                                                                .makeAllResourceData(
                                                                                        context3,
                                                                                        uri);
                                                            }
                                                            if (!z || collection3 == null) {
                                                                return;
                                                            }
                                                            handler3.post(
                                                                    new ScspUtils$$ExternalSyntheticLambda1(
                                                                            2, collection3));
                                                        }
                                                    });
                                            break;
                                        }
                                    }
                                } else {
                                    Log.w("BluetoothEventManager", "onReceive : action is null");
                                    break;
                                }
                                break;
                            default:
                                String action2 = intent.getAction();
                                Log.v("BluetoothEventManager", "onReceive :: " + action2);
                                BluetoothEventManager bluetoothEventManager = this.this$0;
                                if (bluetoothEventManager.mLocalAdapter != null
                                        && bluetoothEventManager.mDeviceManager != null
                                        && bluetoothEventManager.mProfileManager != null) {
                                    BluetoothDevice bluetoothDevice2 =
                                            (BluetoothDevice)
                                                    intent.getParcelableExtra(
                                                            "android.bluetooth.device.extra.DEVICE");
                                    Handler handler3 =
                                            (Handler)
                                                    ((HashMap) this.this$0.mHandlerMap)
                                                            .get(action2);
                                    if (handler3 != null) {
                                        handler3.onReceive(context2, intent, bluetoothDevice2);
                                        break;
                                    }
                                } else {
                                    Log.e(
                                            "BluetoothEventManager",
                                            "onReceive :: ignore this broadcast, because"
                                                + " BluetoothSettings instance are not created"
                                                + " yet");
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 0;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver(this) { // from class:
                    // com.android.settingslib.bluetooth.BluetoothEventManager.1
                    public final /* synthetic */ BluetoothEventManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(final Context context2, Intent intent) {
                        switch (i2) {
                            case 0:
                                String action = intent.getAction();
                                if (action != null) {
                                    Log.i(
                                            "BluetoothEventManager",
                                            "onReceive : action ".concat(action));
                                    if (!action.equals(
                                            "com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL")) {
                                        if (action.equals(
                                                "com.samsung.bluetooth.device.action.RESOURCE_UPDATE")) {
                                            BluetoothDevice bluetoothDevice =
                                                    (BluetoothDevice)
                                                            intent.getParcelableExtra(
                                                                    "android.bluetooth.device.extra.DEVICE");
                                            final ArrayList parcelableArrayListExtra =
                                                    intent.getParcelableArrayListExtra(
                                                            "com.samsung.bluetooth.device.extra.RESOURCE_URI");
                                            if (parcelableArrayListExtra != null) {
                                                Log.d(
                                                        "BluetoothEventManager",
                                                        "BluetoothDevice.SEM_ACTION_RESOURCE_UPDATE:"
                                                            + " "
                                                                + parcelableArrayListExtra
                                                                        .toString());
                                            }
                                            if (bluetoothDevice != null) {
                                                CachedBluetoothDevice findDevice =
                                                        this.this$0.mDeviceManager.findDevice(
                                                                bluetoothDevice);
                                                if (findDevice != null) {
                                                    final String resourcePath =
                                                            findDevice.getResourcePath();
                                                    if (resourcePath != null) {
                                                        final Collection collection =
                                                                this.this$0.mSemCallbacks;
                                                        String str = ScspUtils.FILE_PATH_ROOT;
                                                        ExecutorService newSingleThreadExecutor =
                                                                Executors.newSingleThreadExecutor();
                                                        final android.os.Handler handler =
                                                                new android.os.Handler(
                                                                        Looper.getMainLooper());
                                                        newSingleThreadExecutor.execute(
                                                                new Runnable() { // from class:
                                                                    // com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda3
                                                                    @Override // java.lang.Runnable
                                                                    public final void run() {
                                                                        ArrayList arrayList3 =
                                                                                parcelableArrayListExtra;
                                                                        Context context3 = context2;
                                                                        String str2 = resourcePath;
                                                                        Collection collection2 =
                                                                                collection;
                                                                        Handler handler2 = handler;
                                                                        if (arrayList3 == null
                                                                                || arrayList3.size()
                                                                                        <= 0) {
                                                                            Log.d(
                                                                                    "ScspUtils",
                                                                                    "saveResource:"
                                                                                        + " uri is"
                                                                                        + " null");
                                                                            return;
                                                                        }
                                                                        Iterator it =
                                                                                arrayList3
                                                                                        .iterator();
                                                                        boolean z = false;
                                                                        while (it.hasNext()) {
                                                                            Uri uri =
                                                                                    (Uri) it.next();
                                                                            Log.d(
                                                                                    "ScspUtils",
                                                                                    "saveResource:"
                                                                                        + " uri = "
                                                                                            + uri
                                                                                                    .toString());
                                                                            z =
                                                                                    ScspUtils
                                                                                            .saveFileFromUri(
                                                                                                    context3,
                                                                                                    uri,
                                                                                                    str2);
                                                                            AirplaneModeEnabler$$ExternalSyntheticOutline0
                                                                                    .m(
                                                                                            "saveResource:"
                                                                                                + " success"
                                                                                                + " = ",
                                                                                            "ScspUtils",
                                                                                            z);
                                                                        }
                                                                        if (!z
                                                                                || collection2
                                                                                        == null) {
                                                                            return;
                                                                        }
                                                                        handler2.post(
                                                                                new ScspUtils$$ExternalSyntheticLambda1(
                                                                                        1,
                                                                                        collection2));
                                                                    }
                                                                });
                                                        break;
                                                    } else {
                                                        Log.i(
                                                                "BluetoothEventManager",
                                                                "onReceive : path is null");
                                                        break;
                                                    }
                                                } else {
                                                    Log.i(
                                                            "BluetoothEventManager",
                                                            "onReceive : cachedDevice is null");
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        final ArrayList parcelableArrayListExtra2 =
                                                intent.getParcelableArrayListExtra(
                                                        "com.samsung.bluetooth.adapter.extra.RESOURCE_ALL_URI");
                                        if (parcelableArrayListExtra2 != null) {
                                            Log.d(
                                                    "BluetoothEventManager",
                                                    "BluetoothAdapter.SEM_EXTRA_RESOURCE_ALL_URI: "
                                                            + parcelableArrayListExtra2.toString());
                                            final Collection collection2 =
                                                    this.this$0.mSemCallbacks;
                                            String str2 = ScspUtils.FILE_PATH_ROOT;
                                            ExecutorService newSingleThreadExecutor2 =
                                                    Executors.newSingleThreadExecutor();
                                            final android.os.Handler handler2 =
                                                    new android.os.Handler(Looper.getMainLooper());
                                            newSingleThreadExecutor2.execute(
                                                    new Runnable() { // from class:
                                                        // com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda2
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            ArrayList arrayList3 =
                                                                    parcelableArrayListExtra2;
                                                            Context context3 = context2;
                                                            Collection collection3 = collection2;
                                                            Handler handler3 = handler2;
                                                            if (arrayList3 == null
                                                                    || arrayList3.size() <= 0) {
                                                                Log.d(
                                                                        "ScspUtils",
                                                                        "saveAllResources: uri is"
                                                                            + " null");
                                                                return;
                                                            }
                                                            Iterator it = arrayList3.iterator();
                                                            boolean z = false;
                                                            while (it.hasNext()) {
                                                                Uri uri = (Uri) it.next();
                                                                Log.d(
                                                                        "ScspUtils",
                                                                        "saveAllResources: uri = "
                                                                                + uri.toString());
                                                                z =
                                                                        ScspUtils
                                                                                .makeAllResourceData(
                                                                                        context3,
                                                                                        uri);
                                                            }
                                                            if (!z || collection3 == null) {
                                                                return;
                                                            }
                                                            handler3.post(
                                                                    new ScspUtils$$ExternalSyntheticLambda1(
                                                                            2, collection3));
                                                        }
                                                    });
                                            break;
                                        }
                                    }
                                } else {
                                    Log.w("BluetoothEventManager", "onReceive : action is null");
                                    break;
                                }
                                break;
                            default:
                                String action2 = intent.getAction();
                                Log.v("BluetoothEventManager", "onReceive :: " + action2);
                                BluetoothEventManager bluetoothEventManager = this.this$0;
                                if (bluetoothEventManager.mLocalAdapter != null
                                        && bluetoothEventManager.mDeviceManager != null
                                        && bluetoothEventManager.mProfileManager != null) {
                                    BluetoothDevice bluetoothDevice2 =
                                            (BluetoothDevice)
                                                    intent.getParcelableExtra(
                                                            "android.bluetooth.device.extra.DEVICE");
                                    Handler handler3 =
                                            (Handler)
                                                    ((HashMap) this.this$0.mHandlerMap)
                                                            .get(action2);
                                    if (handler3 != null) {
                                        handler3.onReceive(context2, intent, bluetoothDevice2);
                                        break;
                                    }
                                } else {
                                    Log.e(
                                            "BluetoothEventManager",
                                            "onReceive :: ignore this broadcast, because"
                                                + " BluetoothSettings instance are not created"
                                                + " yet");
                                    break;
                                }
                                break;
                        }
                    }
                };
        Log.d("BluetoothEventManager", "BluetoothEventManager Constructor :: ");
        IntentFilter intentFilter = new IntentFilter();
        IntentFilter intentFilter2 = new IntentFilter();
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mAdapterIntentFilter = new IntentFilter();
        this.mProfileIntentFilter = new IntentFilter();
        HashMap hashMap = new HashMap();
        this.mHandlerMap = hashMap;
        this.mContext = context;
        this.mUserHandle = null;
        this.mReceiverHandler = null;
        arrayList2.clear();
        addHandler(
                "android.bluetooth.adapter.action.STATE_CHANGED", new DeviceSyncHandler(this, i));
        addHandler(
                "android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED",
                new DeviceSyncHandler(this, 13));
        addHandler(
                "android.bluetooth.adapter.action.DISCOVERY_STARTED",
                new ScanningStateChangedHandler(true));
        addHandler(
                "android.bluetooth.adapter.action.DISCOVERY_FINISHED",
                new ScanningStateChangedHandler(false));
        addHandler("android.bluetooth.device.action.FOUND", new DeviceSyncHandler(this, 3));
        int i3 = 17;
        addHandler("android.bluetooth.device.action.NAME_CHANGED", new DeviceSyncHandler(this, i3));
        addHandler(
                "android.bluetooth.device.action.ALIAS_CHANGED", new DeviceSyncHandler(this, i3));
        addHandler(
                "android.bluetooth.device.action.BOND_STATE_CHANGED",
                new DeviceSyncHandler(this, 2));
        addHandler(
                "android.bluetooth.device.action.PAIRING_CANCEL", new DeviceSyncHandler(this, 18));
        addHandler(
                "android.bluetooth.device.action.CLASS_CHANGED", new DeviceSyncHandler(this, 12));
        addHandler("android.bluetooth.device.action.UUID", new DeviceSyncHandler(this, 19));
        addHandler(
                "android.bluetooth.device.action.BATTERY_LEVEL_CHANGED",
                new DeviceSyncHandler(this, 11));
        addHandler(
                "com.samsung.bluetooth.device.action.MANUFACTURER_CHANGED",
                new DeviceSyncHandler(this, 16));
        addHandler(
                "com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED",
                new DeviceSyncHandler(this, 15));
        int i4 = 6;
        addHandler(
                "android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED",
                new DeviceSyncHandler(this, i4));
        addHandler(
                "android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED",
                new DeviceSyncHandler(this, i4));
        addHandler(
                "android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED",
                new DeviceSyncHandler(this, i4));
        addHandler(
                "android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED",
                new DeviceSyncHandler(this, i4));
        int i5 = 8;
        addHandler(
                "android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED",
                new DeviceSyncHandler(this, i5));
        addHandler("android.intent.action.PHONE_STATE", new DeviceSyncHandler(this, i5));
        int i6 = 5;
        addHandler(
                "android.bluetooth.device.action.ACL_CONNECTED", new DeviceSyncHandler(this, i6));
        addHandler(
                "android.bluetooth.device.action.ACL_DISCONNECTED",
                new DeviceSyncHandler(this, i6));
        addHandler(
                "android.bluetooth.action.AUTO_ON_STATE_CHANGED", new DeviceSyncHandler(this, 10));
        addHandler("com.android.settings.DEVICE_NAME_CHANGED", new DeviceNameChangedHandler());
        addHandler(
                "com.samsung.android.intent.action.RESPONSE_RESTORE_BLUETOOTH",
                new DeviceSyncHandler(this, 14));
        addHandler(
                "com.samsung.android.intent.action.NOTIFY_SC_SYNC_BLUETOOTH",
                new DeviceSyncHandler(this, i2));
        this.mDelayedSyncHandler = new DelayedSyncHandler(Looper.getMainLooper());
        addHandler(
                "com.samsung.bluetooth.device.action.AUDIO_TYPE_CHANGED",
                new DeviceSyncHandler(this, 9));
        addHandler(
                "com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET",
                new DeviceSyncHandler(this, 4));
        hashMap.put("android.intent.action.PACKAGE_ADDED", new DeviceSyncHandler(this, 7));
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        synchronized (arrayList2) {
            try {
                if (arrayList2.contains(broadcastReceiver)) {
                    context.unregisterReceiver(broadcastReceiver);
                    arrayList2.remove(broadcastReceiver);
                    Log.e(
                            "BluetoothEventManager",
                            "registerPackageIntentReceiver: mPackageBroadcastReceiver was"
                                + " registered already. Receiver will refresh.");
                }
                registerIntentReceiver(broadcastReceiver, intentFilter);
                arrayList2.add(broadcastReceiver);
            } finally {
            }
        }
        intentFilter2.addAction("com.samsung.bluetooth.device.action.RESOURCE_UPDATE");
        intentFilter2.addAction("com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL");
        synchronized (arrayList2) {
            try {
                if (arrayList2.contains(broadcastReceiver2)) {
                    context.unregisterReceiver(broadcastReceiver2);
                    arrayList2.remove(broadcastReceiver2);
                    Log.e(
                            "BluetoothEventManager",
                            "registerIconIntentReceiver: mIconBroadcastReceiver was registered"
                                + " already. Receiver will refresh.");
                }
                registerIntentReceiver(broadcastReceiver2, intentFilter2);
                arrayList2.add(broadcastReceiver2);
            } finally {
            }
        }
        String str = ScspUtils.FILE_PATH_ROOT;
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
        newSingleThreadExecutor.execute(
                new Runnable() { // from class:
                                 // com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Context context2 = context;
                        Collection collection = arrayList;
                        Handler handler2 = handler;
                        File file = new File(ScspUtils.getFileRootPath(context2));
                        boolean z = false;
                        if (file.exists() && file.isDirectory()) {
                            File[] listFiles = file.listFiles();
                            if (listFiles == null) {
                                Log.d("ScspUtils", "removeOldDir: listFiles is null");
                            } else {
                                String num =
                                        Integer.toString(
                                                SystemProperties.getInt(
                                                        "ro.build.version.oneui", 0));
                                for (File file2 : listFiles) {
                                    if (file2.isDirectory() && !num.equals(file2.getName())) {
                                        ScspUtils.deleteDirectory(file2);
                                    }
                                }
                            }
                        }
                        BluetoothAdapter adapter =
                                ((BluetoothManager)
                                                context2.getSystemService(BluetoothManager.class))
                                        .getAdapter();
                        if (adapter == null) {
                            return;
                        }
                        String semGetEtag = adapter.semGetEtag(context2.getPackageName(), null);
                        if (TextUtils.isEmpty(semGetEtag)) {
                            Log.d("ScspUtils", "init: etag is empty");
                            return;
                        }
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "init: etag = ", semGetEtag, "ScspUtils");
                        SharedPreferences sharedPreferences =
                                context2.getSharedPreferences("bluetooth_scsp_manager", 0);
                        String string = sharedPreferences.getString("etag", ApnSettings.MVNO_NONE);
                        Log.d("ScspUtils", "init: sharedEtag = " + string);
                        if (!semGetEtag.equals(string)) {
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("etag", semGetEtag);
                            edit.apply();
                        } else {
                            if (new File(ScspUtils.getFileRootPath(context2)).exists()) {
                                Log.d("ScspUtils", "init: etag is not updated");
                                return;
                            }
                            Log.d("ScspUtils", "init: dir is not exist. need update");
                        }
                        List<Uri> semGetAllIconResourceUri =
                                adapter.semGetAllIconResourceUri(context2.getPackageName());
                        if (semGetAllIconResourceUri == null
                                || semGetAllIconResourceUri.size() == 0) {
                            Log.d("ScspUtils", "init: uriList is empty");
                            return;
                        }
                        for (Uri uri : semGetAllIconResourceUri) {
                            Log.d("ScspUtils", "saveAllResources: uri = " + uri.toString());
                            z = ScspUtils.makeAllResourceData(context2, uri);
                        }
                        if (!z || collection == null) {
                            return;
                        }
                        handler2.post(new ScspUtils$$ExternalSyntheticLambda1(0, collection));
                    }
                });
        registerAdapterIntentReceiver();
    }

    public void addHandler(String str, Handler handler) {
        ((HashMap) this.mHandlerMap).put(str, handler);
        this.mAdapterIntentFilter.addAction(str);
    }

    public void addProfileHandler(String str, Handler handler) {
        ((HashMap) this.mHandlerMap).put(str, handler);
        this.mProfileIntentFilter.addAction(str);
    }

    public void dispatchActiveDeviceChanged(
            final CachedBluetoothDevice cachedBluetoothDevice, int i) {
        Iterator it = ((ArrayList) this.mDeviceManager.getCachedDevicesCopy()).iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
            CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice2.mSubDevice;
            if (cachedBluetoothDevice != null
                    && ((cachedBluetoothDevice3 != null
                                    && cachedBluetoothDevice3.equals(cachedBluetoothDevice))
                            || cachedBluetoothDevice2.mMemberDevices.stream()
                                    .anyMatch(
                                            new Predicate() { // from class:
                                                              // com.android.settingslib.bluetooth.BluetoothEventManager$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    return ((CachedBluetoothDevice) obj)
                                                            .equals(CachedBluetoothDevice.this);
                                                }
                                            }))) {
                Log.d(
                        "BluetoothEventManager",
                        "The active device is the sub/member device "
                                + cachedBluetoothDevice.mDevice.getAnonymizedAddress()
                                + ". change targetDevice as main device "
                                + cachedBluetoothDevice2.mDevice.getAnonymizedAddress());
                cachedBluetoothDevice = cachedBluetoothDevice2;
            }
            boolean equals = cachedBluetoothDevice2.equals(cachedBluetoothDevice);
            Log.d(
                    "CachedBluetoothDevice",
                    "onActiveDeviceChanged: profile "
                            + BluetoothProfile.getProfileName(i)
                            + ", device "
                            + cachedBluetoothDevice2.mDevice.getAnonymizedAddress()
                            + ", isActive "
                            + equals);
            if (i == 1) {
                r5 = cachedBluetoothDevice2.mIsActiveDeviceHeadset != equals;
                cachedBluetoothDevice2.mIsActiveDeviceHeadset = equals;
            } else if (i == 2) {
                r5 = cachedBluetoothDevice2.mIsActiveDeviceA2dp != equals;
                cachedBluetoothDevice2.mIsActiveDeviceA2dp = equals;
            } else if (i == 21) {
                r5 = cachedBluetoothDevice2.mIsActiveDeviceHearingAid != equals;
                cachedBluetoothDevice2.mIsActiveDeviceHearingAid = equals;
            } else if (i != 22) {
                Log.w(
                        "CachedBluetoothDevice",
                        "onActiveDeviceChanged: unknown profile " + i + " isActive " + equals);
            } else {
                r5 = cachedBluetoothDevice2.mIsActiveDeviceLeAudio != equals;
                cachedBluetoothDevice2.mIsActiveDeviceLeAudio = equals;
            }
            if (r5) {
                cachedBluetoothDevice2.dispatchAttributesChanged(true);
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                if (cachedBluetoothDevice2.isHearingAidDevice()) {
                    cachedBluetoothDeviceManager.mHearingAidDeviceManager.onActiveDeviceChanged(
                            cachedBluetoothDevice2);
                }
            }
        }
        Iterator it2 = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it2.hasNext()) {
            ((BluetoothCallback) it2.next()).onActiveDeviceChanged(cachedBluetoothDevice, i);
        }
    }

    public final void dispatchDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice.mIsSynced) {
            synchronized (this.mSemCallbacks) {
                try {
                    Iterator it = ((ArrayList) this.mSemCallbacks).iterator();
                    while (it.hasNext()) {
                        ((SemBluetoothCallback) it.next()).onSyncDeviceAdded(cachedBluetoothDevice);
                    }
                } finally {
                }
            }
        }
        Iterator it2 = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it2.hasNext()) {
            ((BluetoothCallback) it2.next()).onDeviceAdded(cachedBluetoothDevice);
        }
    }

    public final void dispatchDeviceRemoved(CachedBluetoothDevice cachedBluetoothDevice) {
        if (BluetoothUtils.DEBUG) {
            Log.d(
                    "BluetoothEventManager",
                    "dispatchDeviceRemoved :: cachedDevice - " + cachedBluetoothDevice.getName());
        }
        Iterator it = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((BluetoothCallback) it.next()).onDeviceDeleted(cachedBluetoothDevice);
        }
    }

    public final boolean isBlockingDevice(BluetoothDevice bluetoothDevice) {
        String replace = bluetoothDevice.getAddress().replace(":", ApnSettings.MVNO_NONE);
        String string =
                this.mContext
                        .getSharedPreferences("bluetooth_blocking_device", 0)
                        .getString("blocking_device_list", ApnSettings.MVNO_NONE);
        if (string.equals(ApnSettings.MVNO_NONE)) {
            return false;
        }
        for (String str : string.split(";")) {
            String[] split = str.split(",");
            if (split.length == 5 && split[0].equals(replace)) {
                try {
                    if (Integer.parseInt(split[4]) == 2) {
                        Log.i("BluetoothEventManager", "It's blocked device for pairing");
                        return true;
                    }
                } catch (NumberFormatException unused) {
                    continue;
                }
            }
        }
        return false;
    }

    public final boolean readPairedDevices() {
        Set<BluetoothDevice> bondedDevices = this.mLocalAdapter.mAdapter.getBondedDevices();
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
        Collection cachedDevicesCopy = cachedBluetoothDeviceManager.getCachedDevicesCopy();
        boolean z = false;
        if (bondedDevices == null || bondedDevices.size() == 0) {
            Iterator it = ((ArrayList) cachedDevicesCopy).iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice.mBondState == 12) {
                    cachedBluetoothDevice.mBondState = 10;
                }
            }
            return false;
        }
        Iterator it2 = ((ArrayList) cachedDevicesCopy).iterator();
        while (it2.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it2.next();
            if (bondedDevices.contains(cachedBluetoothDevice2.mDevice)) {
                cachedBluetoothDevice2.mBondState = cachedBluetoothDevice2.mDevice.getBondState();
            }
        }
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            CachedBluetoothDevice findDevice =
                    cachedBluetoothDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                cachedBluetoothDeviceManager.addDevice(bluetoothDevice);
                if (cachedBluetoothDeviceManager.findDevice(bluetoothDevice) != null) {
                    z = true;
                }
            } else if (findDevice.getName().equals(findDevice.mDevice.getAddress())) {
                findDevice.fetchName$1();
            }
        }
        return z;
    }

    public final void readRestoredDevices() {
        ArrayList arrayList;
        List restoredDevices =
                BluetoothUtils.getRestoredDevices(this.mContext, this.mProfileManager, false);
        if (restoredDevices != null) {
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                try {
                    synchronized (cachedBluetoothDeviceManager) {
                        arrayList = new ArrayList();
                        for (int size = cachedBluetoothDeviceManager.mCachedDevices.size() - 1;
                                size >= 0;
                                size--) {
                            CachedBluetoothDevice cachedBluetoothDevice =
                                    cachedBluetoothDeviceManager.mCachedDevices.get(size);
                            if (cachedBluetoothDevice.mIsRestored) {
                                arrayList.add(cachedBluetoothDevice);
                                cachedBluetoothDeviceManager.removeDevice(cachedBluetoothDevice);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Iterator it = ((ArrayList) restoredDevices).iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice2.mBondState != 12) {
                    int indexOf = arrayList.indexOf(cachedBluetoothDevice2);
                    if (indexOf > -1) {
                        Log.d(
                                "CachedBluetoothDeviceManager",
                                "addRestoredDevices :: newDevice is added already");
                        CachedBluetoothDevice cachedBluetoothDevice3 =
                                (CachedBluetoothDevice) arrayList.get(indexOf);
                        cachedBluetoothDevice2.mErrorMsg = cachedBluetoothDevice3.mErrorMsg;
                        cachedBluetoothDevice2.mBondState = cachedBluetoothDevice3.mBondState;
                        cachedBluetoothDevice2.mIsBondingByCached =
                                cachedBluetoothDevice3.mIsBondingByCached;
                    }
                    BluetoothRetryDetector bluetoothRetryDetector =
                            cachedBluetoothDeviceManager.mBtManager.mRestoredRetryDetector;
                    if (bluetoothRetryDetector != null) {
                        String address = cachedBluetoothDevice2.mDevice.getAddress();
                        int intValue =
                                (bluetoothRetryDetector.mIsForRestored
                                                && bluetoothRetryDetector.mRestoredDeviceList
                                                        .containsKey(address))
                                        ? ((Integer)
                                                        bluetoothRetryDetector.mRestoredDeviceList
                                                                .get(address))
                                                .intValue()
                                        : 0;
                        BluetoothRetryDetector bluetoothRetryDetector2 =
                                cachedBluetoothDevice2.mBondingDetector;
                        if (bluetoothRetryDetector2 != null) {
                            bluetoothRetryDetector2.mCount = intValue;
                        }
                    }
                    cachedBluetoothDeviceManager.addDevice(cachedBluetoothDevice2);
                    cachedBluetoothDevice2.mSequence =
                            cachedBluetoothDeviceManager.mCachedDevices.indexOf(
                                    cachedBluetoothDevice2);
                }
            }
        }
    }

    public void registerAdapterIntentReceiver() {
        synchronized (this.mReceivers) {
            try {
                if (this.mReceivers.contains(this.mBroadcastReceiver)) {
                    this.mContext.unregisterReceiver(this.mBroadcastReceiver);
                    this.mReceivers.remove(this.mBroadcastReceiver);
                    Log.e(
                            "BluetoothEventManager",
                            "registerAdapterIntentReceiver :: mBroadcastReceiver was registered"
                                + " already. Receiver will refresh.");
                }
                registerIntentReceiver(this.mBroadcastReceiver, this.mAdapterIntentFilter);
                this.mReceivers.add(this.mBroadcastReceiver);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerCallback(BluetoothCallback bluetoothCallback) {
        ((CopyOnWriteArrayList) this.mCallbacks).add(bluetoothCallback);
    }

    public final void registerIntentReceiver(
            BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        UserHandle userHandle = this.mUserHandle;
        if (userHandle == null) {
            this.mContext.registerReceiver(
                    broadcastReceiver, intentFilter, null, this.mReceiverHandler, 2);
        } else {
            this.mContext.registerReceiverAsUser(
                    broadcastReceiver, userHandle, intentFilter, null, this.mReceiverHandler, 2);
        }
    }

    public void registerProfileIntentReceiver() {
        synchronized (this.mReceivers) {
            try {
                if (this.mReceivers.contains(this.mProfileBroadcastReceiver)) {
                    this.mContext.unregisterReceiver(this.mProfileBroadcastReceiver);
                    this.mReceivers.remove(this.mProfileBroadcastReceiver);
                    Log.e(
                            "BluetoothEventManager",
                            "registerProfileIntentReceiver :: mProfileConnectionReceiver was"
                                + " registered already. Receiver will refresh.");
                }
                registerIntentReceiver(this.mProfileBroadcastReceiver, this.mProfileIntentFilter);
                this.mReceivers.add(this.mProfileBroadcastReceiver);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerSemCallback(SemBluetoothCallback semBluetoothCallback) {
        synchronized (this.mSemCallbacks) {
            ((ArrayList) this.mSemCallbacks).add(semBluetoothCallback);
        }
    }

    public final void unregisterCallback(BluetoothCallback bluetoothCallback) {
        ((CopyOnWriteArrayList) this.mCallbacks).remove(bluetoothCallback);
    }

    public final void unregisterSemCallback(SemBluetoothCallback semBluetoothCallback) {
        synchronized (this.mSemCallbacks) {
            ((ArrayList) this.mSemCallbacks).remove(semBluetoothCallback);
        }
    }
}
