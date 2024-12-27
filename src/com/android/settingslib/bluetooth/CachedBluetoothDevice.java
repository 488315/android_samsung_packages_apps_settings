package com.android.settingslib.bluetooth;

import android.app.StatusBarManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothHidHost;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.util.ArrayUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.AdaptiveOutlineDrawable;

import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.SecBluetoothDevicePreference;
import com.samsung.android.settingslib.bluetooth.BluetoothRestoredDevice;
import com.samsung.android.settingslib.bluetooth.GattProfile;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.SppProfile;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;
import com.samsung.android.settingslib.bluetooth.smep.SppByteUtil;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CachedBluetoothDevice implements Comparable {
    public final String mAddress;
    public short mAppearance;
    public String mBluetoothCastMsg;
    public int mBondState;
    public long mBondTimeStamp;
    public Timestamp mBondTimestamp;
    public BluetoothRetryDetector mBondingDetector;
    public BluetoothClass mBtClass;
    public final Map mCallbackExecutorMap;
    public final Collection mCallbacks;
    public long mConnectAttempted;
    public final Context mContext;
    public BluetoothDevice mDevice;
    public String mDeviceName;
    LruCache<String, BitmapDrawable> mDrawableCache;
    public String mErrorMsg;
    public int mGroupId;
    public HearingAidInfo mHearingAidInfo;
    public boolean mIsActiveDeviceA2dp;
    public boolean mIsActiveDeviceHeadset;
    public boolean mIsActiveDeviceHearingAid;
    public boolean mIsActiveDeviceLeAudio;
    public boolean mIsAddrSwitched;
    public boolean mIsBondingByCached;
    public boolean mIsHearingAidDeviceByUUID;
    public boolean mIsRestored;
    public boolean mIsSynced;
    public boolean mIsTablet;
    public boolean mJustDiscovered;
    public CachedBluetoothDevice mLeadDevice;
    public final BluetoothAdapter mLocalAdapter;
    public boolean mLocalNapRoleConnected;
    public ManufacturerData mManufacturerData;
    public final Set mMemberDevices;
    public String mModelName;
    public String mName;
    public String mPrefixName;
    public HashMap mProfileConnectionState;
    public final Object mProfileLock;
    public final LocalBluetoothProfileManager mProfileManager;
    public final LinkedHashSet mProfiles;
    public final LinkedHashSet mRemovedProfiles;
    public final BluetoothRestoredDevice mRestoredDevice;
    public short mRssi;
    public int mRssiGroup;
    public final Collection mSemCallbacks;
    public int mSequence;
    public CachedBluetoothDevice mSubDevice;
    public int mType;
    public boolean mUnpairing;
    public boolean mVisible;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.bluetooth.CachedBluetoothDevice$1, reason: invalid class name */
    public final class AnonymousClass1 extends LruCache {
        @Override // android.util.LruCache
        public final int sizeOf(Object obj, Object obj2) {
            return ((BitmapDrawable) obj2).getBitmap().getByteCount() / 1024;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.bluetooth.CachedBluetoothDevice$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$bluetooth$SmepTag;

        static {
            int[] iArr = new int[SmepTag.values().length];
            $SwitchMap$com$samsung$android$bluetooth$SmepTag = iArr;
            try {
                iArr[SmepTag.FEATURE_AURACAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void onDeviceAttributesChanged();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SemCallback {}

    public CachedBluetoothDevice(
            Context context,
            LocalBluetoothProfileManager localBluetoothProfileManager,
            BluetoothDevice bluetoothDevice) {
        this.mModelName = null;
        this.mType = 0;
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mCallbackExecutorMap = new ConcurrentHashMap();
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
        this.mContext = context;
        this.mLocalAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mProfileConnectionState = new HashMap();
        this.mProfileManager = localBluetoothProfileManager;
        this.mDevice = bluetoothDevice;
        this.mAddress = bluetoothDevice.getAddress();
        fillData();
        this.mGroupId = -1;
        initDrawableCache();
        this.mUnpairing = false;
    }

    public final void addMemberDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        Log.d(
                "CachedBluetoothDevice",
                this
                        + " addMemberDevice = "
                        + cachedBluetoothDevice.mDevice.getAddressForLogging());
        BluetoothDump.BtLog(
                "CachedBtDev -- addMemberDevice: main = "
                        + this.mDevice.getAddressForLogging()
                        + ", member = "
                        + cachedBluetoothDevice.mDevice.getAddressForLogging());
        if (this.mMemberDevices.contains(cachedBluetoothDevice)) {
            BluetoothDump.BtLog("CachedBtDev -- addMemberDevice: contains already");
            return;
        }
        this.mMemberDevices.add(cachedBluetoothDevice);
        this.mLeadDevice = null;
        cachedBluetoothDevice.mLeadDevice = this;
    }

    public final boolean checkHearingAidByUuid() {
        ParcelUuid[] uuids = this.mDevice.getUuids();
        ParcelUuid parcelUuid = BluetoothUuid.HEARING_AID;
        return ArrayUtils.contains(uuids, parcelUuid)
                || ArrayUtils.contains(this.mDevice.getLeService16BitsUuidData(), parcelUuid)
                || ArrayUtils.contains(
                        this.mDevice.getLeComplete128BitsUuidData(),
                        ParcelUuid.fromString("7d74f4bd-c74a-4431-862c-cce884371592"));
    }

    public final void checkLEConnectionGuide(boolean z, boolean z2) {
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, null);
        if (localBluetoothManager == null) {
            return;
        }
        if (!localBluetoothManager.semIsForegroundActivity() && !z2) {
            Log.d("CachedBluetoothDevice", "notForeground - skip checking LE");
            return;
        }
        if (this.mType != 2) {
            return;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager == null
                || !hasProfile(localBluetoothProfileManager.mLeAudioProfile)) {
            if (this.mIsRestored) {
                ParcelUuid[] parcelUuidArr = this.mRestoredDevice.mUuids;
                if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HEARING_AID)
                        || ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HOGP)) {
                    return;
                }
            }
            if (z) {
                ParcelUuid[] uuids = this.mDevice.getUuids();
                if (ArrayUtils.contains(uuids, BluetoothUuid.HEARING_AID)) {
                    return;
                }
                if (ArrayUtils.contains(uuids, BluetoothUuid.HOGP)) {
                    this.mBondingDetector.setFailCase(
                            BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_HOGP);
                    return;
                }
            } else {
                ParcelUuid[] leService16BitsUuidData = this.mDevice.getLeService16BitsUuidData();
                if (ArrayUtils.contains(leService16BitsUuidData, BluetoothUuid.HEARING_AID)
                        || ArrayUtils.contains(leService16BitsUuidData, BluetoothUuid.LE_AUDIO)
                        || ArrayUtils.contains(
                                this.mDevice.getLeComplete16BitsUuidData(), BluetoothUuid.HOGP)
                        || ArrayUtils.contains(
                                this.mDevice.getLeComplete128BitsUuidData(),
                                ParcelUuid.fromString("7d74f4bd-c74a-4431-862c-cce884371592"))) {
                    return;
                }
            }
            this.mBondingDetector.setFailCase(
                    BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_LE);
            Context context = this.mContext;
            BluetoothUtils.showToast(
                    context, context.getString(R.string.bluetooth_le_connection_guide));
        }
    }

    public final void clearProfileConnectionState() {
        Log.d("CachedBluetoothDevice", " Clearing all connection state for dev:" + getNameForLog());
        Iterator it = getProfiles().iterator();
        while (it.hasNext()) {
            this.mProfileConnectionState.put((LocalBluetoothProfile) it.next(), 0);
        }
        this.mBluetoothCastMsg = null;
    }

    public final void connect$1() {
        if (shouldLaunchGM(this.mContext.getPackageName(), false)) {
            return;
        }
        if (this.mBondState == 10) {
            startPairing();
            return;
        }
        checkLEConnectionGuide(true, false);
        this.mConnectAttempted = SystemClock.elapsedRealtime();
        connectDevice();
    }

    public final void connectDevice() {
        NetworkCapabilities networkCapabilities;
        synchronized (this.mProfileLock) {
            try {
                if (getProfiles().isEmpty()) {
                    Log.d(
                            "CachedBluetoothDevice",
                            "No profiles. Maybe we will connect later for device " + this.mDevice);
                    return;
                }
                LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
                if (localBluetoothProfileManager != null
                        && hasProfile(localBluetoothProfileManager.mLeAudioProfile)) {
                    this.mConnectAttempted = 0L;
                }
                LocalBluetoothProfileManager localBluetoothProfileManager2 = this.mProfileManager;
                if (localBluetoothProfileManager2 != null
                        && hasProfile(localBluetoothProfileManager2.mPanProfile)
                        && isPanProfileOnlySupported()) {
                    this.mErrorMsg = null;
                    ConnectivityManager connectivityManager =
                            (ConnectivityManager) this.mContext.getSystemService("connectivity");
                    if (connectivityManager != null
                            && (networkCapabilities =
                                            connectivityManager.getNetworkCapabilities(
                                                    connectivityManager.getActiveNetwork()))
                                    != null
                            && networkCapabilities.hasTransport(1)) {
                        this.mErrorMsg = BluetoothUtils.getTetheringErrorWithWifi(this.mContext);
                        refresh();
                        return;
                    }
                    if (this.mProfileManager.mPanProfile.isNapEnabled()) {
                        Context context = this.mContext;
                        BluetoothDevice bluetoothDevice = this.mDevice;
                        Intent intent =
                                new Intent(
                                        "com.samsung.android.settings.bluetooth.LAUNCH_TETHERING_OFF_ACTIVITY");
                        intent.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
                        intent.setFlags(268435456);
                        context.startActivity(intent);
                        return;
                    }
                }
                Log.d("CachedBluetoothDevice", "connect " + this);
                this.mDevice.connect();
                if (this.mGroupId != -1) {
                    Iterator it = ((HashSet) this.mMemberDevices).iterator();
                    while (it.hasNext()) {
                        CachedBluetoothDevice cachedBluetoothDevice =
                                (CachedBluetoothDevice) it.next();
                        if (cachedBluetoothDevice.mBondState == 12) {
                            Log.d(
                                    "CachedBluetoothDevice",
                                    "connect the member("
                                            + cachedBluetoothDevice.mDevice.getAddress()
                                            + ")");
                            cachedBluetoothDevice.mDevice.connect();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String describeDetail() {
        StringBuilder sb = new StringBuilder();
        String identityAddress = getIdentityAddress();
        if (TextUtils.isEmpty(identityAddress) || !identityAddress.equals(this.mAddress)) {
            sb.append(
                    "["
                            + this.mDevice.getAddressForLogging()
                            + " => "
                            + getIdentityAddressForLog()
                            + "]");
        } else {
            sb.append("[" + this.mDevice.getAddressForLogging() + "]");
        }
        sb.append(", [" + this.mBondState + "]");
        sb.append(", [" + this.mIsRestored + "]");
        if (this.mBtClass != null) {
            sb.append(", [" + this.mBtClass + "]");
        } else {
            sb.append(", [null]");
        }
        sb.append(", [" + ((int) this.mAppearance) + "]");
        sb.append(", [" + this.mType + "]");
        if (getManufacturerRawData() != null) {
            sb.append(", [" + this.mManufacturerData.mManufacturerType + "]");
            sb.append(", [");
            for (byte b : getManufacturerRawData()) {
                sb.append(String.format("%02X ", Byte.valueOf(b)));
            }
            sb.append("]");
        }
        if (this.mGroupId != -1) {
            sb.append(", [" + this.mGroupId + "]");
        }
        return sb.toString();
    }

    public final void disconnect() {
        boolean z;
        boolean z2;
        PbapServerProfile pbapServerProfile;
        BluetoothDevice bluetoothDevice = this.mDevice;
        if (bluetoothDevice.semGetAutoSwitchMode() != -1) {
            Intent intent =
                    new Intent(
                            "com.samsung.android.mcfds.autoswitch.BUDS_DISCONNECTED_BY_SETTINGS");
            intent.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
            this.mContext.sendBroadcast(intent, "android.permission.BLUETOOTH_PRIVILEGED");
        }
        synchronized (this.mProfileLock) {
            try {
                Iterator it = this.mProfiles.iterator();
                z = false;
                z2 = false;
                while (it.hasNext()) {
                    LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                    if (isConnectedProfile(localBluetoothProfile)) {
                        if (localBluetoothProfile instanceof SppProfile) {
                            z = true;
                        } else if (localBluetoothProfile instanceof GattProfile) {
                            z2 = true;
                        }
                    }
                }
            } finally {
            }
        }
        if (z || z2) {
            Log.d(
                    "CachedBluetoothDevice",
                    "disconnect :: Send Intent to disconnect. It won't launch GM");
            Intent intent2 =
                    new Intent("com.samsung.android.watchmanager.ACTION_HM_REQUEST_DISCONNECT");
            String packageName = this.mContext.getPackageName();
            if (packageName != null) {
                intent2.putExtra("request_app_package_name", packageName);
            }
            intent2.putExtra("device_address", this.mAddress);
            intent2.addFlags(268435456);
            intent2.addFlags(32);
            intent2.addFlags(16777216);
            this.mContext.sendBroadcast(
                    intent2,
                    "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER");
        }
        String name = getName();
        if (BluetoothUtils.isRTL(this.mContext)) {
            name = ComposerKt$$ExternalSyntheticOutline0.m("\u200e", name, "\u200e");
        }
        BluetoothUtils.showToast(
                this.mContext,
                this.mContext.getString(R.string.bluetooth_disconnect_message, name));
        synchronized (this.mProfileLock) {
            try {
                if (this.mGroupId != -1) {
                    for (CachedBluetoothDevice cachedBluetoothDevice : this.mMemberDevices) {
                        Log.d(
                                "CachedBluetoothDevice",
                                "Disconnect the member:" + cachedBluetoothDevice);
                        cachedBluetoothDevice.disconnect();
                    }
                }
                Log.d("CachedBluetoothDevice", "Disconnect " + this);
                this.mDevice.disconnect();
            } finally {
            }
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager == null
                || (pbapServerProfile = localBluetoothProfileManager.mPbapProfile) == null
                || !isConnectedProfile(pbapServerProfile)) {
            return;
        }
        pbapServerProfile.setEnabled(this.mDevice, false);
    }

    public final void dispatchAttributesChanged() {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onDeviceAttributesChanged();
        }
        this.mCallbackExecutorMap.forEach(new CachedBluetoothDevice$$ExternalSyntheticLambda2());
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CachedBluetoothDevice)) {
            return false;
        }
        return this.mDevice.equals(((CachedBluetoothDevice) obj).mDevice);
    }

    public final void fetchActiveDevices() {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null) {
            A2dpProfile a2dpProfile = localBluetoothProfileManager.mA2dpProfile;
            if (a2dpProfile != null) {
                this.mIsActiveDeviceA2dp = this.mDevice.equals(a2dpProfile.getActiveDevice());
            }
            HeadsetProfile headsetProfile = this.mProfileManager.mHeadsetProfile;
            if (headsetProfile != null) {
                this.mIsActiveDeviceHeadset = this.mDevice.equals(headsetProfile.getActiveDevice());
            }
            HearingAidProfile hearingAidProfile = this.mProfileManager.mHearingAidProfile;
            if (hearingAidProfile != null) {
                this.mIsActiveDeviceHearingAid =
                        hearingAidProfile.getActiveDevices().contains(this.mDevice);
            }
            LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
            if (leAudioProfile != null) {
                this.mIsActiveDeviceLeAudio =
                        leAudioProfile.getActiveDevices().contains(this.mDevice);
            }
        }
    }

    public final void fetchManufacturerData(byte[] bArr) {
        setManufacturerData(bArr);
        if (BluetoothUtils.DEBUG) {
            Log.d(
                    "CachedBluetoothDevice",
                    "fetchManufacturerData : " + Arrays.toString(getManufacturerRawData()));
        }
    }

    public final void fetchName$1() {
        String name = this.mDevice.getName();
        String alias = this.mDevice.getAlias();
        if (TextUtils.isEmpty(name)) {
            this.mDeviceName = this.mDevice.getAddress();
            if (BluetoothUtils.DEBUG) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Device has no Device name (yet), use address: "),
                        this.mDeviceName,
                        "CachedBluetoothDevice");
            }
        } else {
            this.mDeviceName = name;
        }
        if (!TextUtils.isEmpty(alias)) {
            this.mName = alias;
            return;
        }
        this.mName = this.mDevice.getAddress();
        if (BluetoothUtils.DEBUG) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Device has no name (yet), use address: "),
                    this.mName,
                    "CachedBluetoothDevice");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void fillData() {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.CachedBluetoothDevice.fillData():void");
    }

    public final void fillRestoredData() {
        if (TextUtils.isEmpty(this.mRestoredDevice.mName)) {
            fetchName$1();
        } else {
            String str = this.mRestoredDevice.mName;
            this.mName = str;
            this.mDeviceName = str;
        }
        Log.d(
                "CachedBluetoothDevice",
                "fillRestoredData() :: Device - "
                        + getNameForLog()
                        + ", Class - "
                        + this.mRestoredDevice.mCod);
        setBtClass(new BluetoothClass(this.mRestoredDevice.mCod));
        BluetoothClass bluetoothClass = this.mBtClass;
        if (bluetoothClass != null && !bluetoothClass.equals(this.mDevice.getBluetoothClass())) {
            this.mDevice.setBluetoothClass(this.mRestoredDevice.mCod);
        }
        BluetoothRestoredDevice bluetoothRestoredDevice = this.mRestoredDevice;
        this.mAppearance = (short) bluetoothRestoredDevice.mAppearance;
        setManufacturerData(bluetoothRestoredDevice.mManufacturerData);
        BluetoothRestoredDevice bluetoothRestoredDevice2 = this.mRestoredDevice;
        this.mBondTimeStamp = bluetoothRestoredDevice2.mTimeStamp;
        this.mType = bluetoothRestoredDevice2.mLinkType;
        if (bluetoothRestoredDevice2.mManufacturerData != null
                && !Arrays.equals(
                        this.mDevice.semGetManufacturerData(),
                        this.mRestoredDevice.mManufacturerData)) {
            this.mDevice.semSetManufacturerData(this.mRestoredDevice.mManufacturerData);
        }
        this.mIsRestored = true;
        this.mIsBondingByCached = false;
        if (this.mRestoredDevice.mBondState == 4) {
            this.mIsAddrSwitched = true;
        }
        this.mBondState = 10;
        updateProfiles(null);
        if (isRing()) {
            this.mBondingDetector =
                    new BluetoothRetryDetector(
                            BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING);
        } else if (BluetoothUtils.isGalaxyWatchDevice(
                this.mDeviceName,
                this.mBtClass,
                getManufacturerRawData(),
                this.mRestoredDevice.mUuids)) {
            this.mBondingDetector =
                    new BluetoothRetryDetector(
                            BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH);
        } else {
            this.mBondingDetector =
                    new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE);
        }
    }

    public final int getAppearanceDrawable(int i) {
        if (i == 64) {
            return getName().startsWith("GALAXY Gear (")
                    ? R.drawable.list_ic_wearable
                    : R.drawable.list_ic_mobile;
        }
        if (i == 128) {
            return R.drawable.list_ic_laptop;
        }
        if (i == 512) {
            return R.drawable.list_ic_dongle;
        }
        if (i == 192 || i == 193) {
            String upperCase = this.mDeviceName.toUpperCase();
            return (upperCase.startsWith("GEAR FIT") || upperCase.startsWith("GALAXY FIT"))
                    ? R.drawable.list_ic_band
                    : R.drawable.list_ic_wearable;
        }
        switch (i) {
            case 960:
            case 961:
                return R.drawable.list_ic_keyboard;
            case 962:
                return R.drawable.list_ic_mouse;
            case 963:
            case 964:
                return R.drawable.list_ic_game_device;
            default:
                return 0;
        }
    }

    public final int getBtClassDrawable() {
        int deviceIcon;
        String str = this.mDeviceName;
        String upperCase = str != null ? str.toUpperCase() : null;
        StringBuilder sb = new StringBuilder("getBtClassDrawable :: ");
        sb.append(getNameForLog());
        sb.append(", BluetoothClass = ");
        sb.append(this.mBtClass);
        sb.append(", Appearance = ");
        Preference$$ExternalSyntheticOutline0.m(sb, this.mAppearance, "CachedBluetoothDevice");
        ManufacturerData manufacturerData = this.mManufacturerData;
        if (manufacturerData != null && (deviceIcon = manufacturerData.getDeviceIcon()) != 0) {
            return deviceIcon;
        }
        BluetoothClass bluetoothClass = this.mBtClass;
        if (bluetoothClass != null) {
            int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
            if (majorDeviceClass == 256) {
                return this.mBtClass.getDeviceClass() == 284
                        ? R.drawable.list_ic_tablet
                        : R.drawable.list_ic_laptop;
            }
            if (majorDeviceClass == 512) {
                return this.mIsTablet ? R.drawable.list_ic_tablet : R.drawable.list_ic_mobile;
            }
            if (majorDeviceClass == 1024) {
                if (upperCase != null) {
                    int i =
                            upperCase.startsWith("SAMSUNG LEVEL")
                                    ? upperCase.contains("BOX")
                                            ? R.drawable.list_ic_dlna_audio
                                            : R.drawable.list_ic_headset
                                    : (upperCase.startsWith("GEAR CIRCLE") && isGearIconX())
                                            ? R.drawable.list_ic_gear_circle
                                            : 0;
                    if (i != 0) {
                        return i;
                    }
                }
                if (isGearIconX()) {
                    return R.drawable.list_ic_true_wireless_earbuds;
                }
                if (this.mBtClass.getDeviceClass() == 1084) {
                    return R.drawable.list_ic_tv;
                }
                if (this.mBtClass.getDeviceClass() == 1076) {
                    return R.drawable.list_ic_camcoder;
                }
                LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
                if (localBluetoothProfileManager != null
                        && hasProfile(localBluetoothProfileManager.mA2dpProfile)
                        && hasProfile(this.mProfileManager.mHeadsetProfile)) {
                    return R.drawable.list_ic_sound_accessory_default;
                }
            } else {
                if (majorDeviceClass == 1280) {
                    return HidProfile.getHidClassDrawable(this.mBtClass);
                }
                if (majorDeviceClass == 1536) {
                    return (this.mBtClass.getDeviceClass() == 1664
                                    || this.mBtClass.getDeviceClass() == 1600)
                            ? R.drawable.list_ic_printer
                            : R.drawable.list_ic_camera;
                }
                if (majorDeviceClass == 1792 && this.mBtClass.getDeviceClass() == 1796) {
                    return upperCase != null
                            ? (upperCase.startsWith("GEAR FIT")
                                            || upperCase.startsWith("GALAXY FIT"))
                                    ? R.drawable.list_ic_band
                                    : R.drawable.list_ic_wearable
                            : R.drawable.list_ic_wearable;
                }
            }
            int appearanceDrawable = getAppearanceDrawable(this.mAppearance);
            if (appearanceDrawable != 0) {
                return appearanceDrawable;
            }
            if (this.mBtClass.doesClassMatch(1)) {
                return R.drawable.list_ic_sound_accessory_default;
            }
            if (this.mBtClass.doesClassMatch(0)) {
                return R.drawable.list_ic_mono_headset;
            }
        } else {
            short s = this.mAppearance;
            if (s != 0) {
                int appearanceDrawable2 = getAppearanceDrawable(s);
                if (appearanceDrawable2 != 0) {
                    return appearanceDrawable2;
                }
            } else {
                Log.w("CachedBluetoothDevice", "mBtClass is null");
            }
        }
        if (isHearingAidDevice()) {
            return R.drawable.sec_bluetooth_2d_hearing_aids;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager2 = this.mProfileManager;
        if (localBluetoothProfileManager2 != null
                && hasProfile(localBluetoothProfileManager2.mA2dpProfile)
                && hasProfile(this.mProfileManager.mHeadsetProfile)) {
            setBtClass(new BluetoothClass(1056));
            return R.drawable.list_ic_sound_accessory_default;
        }
        List<LocalBluetoothProfile> profiles = getProfiles();
        for (LocalBluetoothProfile localBluetoothProfile : profiles) {
            for (int i2 = 0; i2 < profiles.size(); i2++) {
                if (profiles.get(i2) instanceof A2dpProfile) {
                    setBtClass(new BluetoothClass(1048));
                    return R.drawable.list_ic_sound_accessory_default;
                }
            }
            int drawableResource = localBluetoothProfile.getDrawableResource(this.mBtClass);
            if (drawableResource != 0) {
                return drawableResource;
            }
        }
        return R.drawable.list_ic_general_device;
    }

    public final List getConnectableProfiles() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mProfileLock) {
            try {
                Iterator it = this.mProfiles.iterator();
                while (it.hasNext()) {
                    LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                    if (localBluetoothProfile.accessProfileEnabled()) {
                        arrayList.add(localBluetoothProfile);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final Optional getConnectedHearingAidSide(final int i) {
        return Stream.concat(
                        Stream.of((Object[]) new CachedBluetoothDevice[] {this, this.mSubDevice}),
                        this.mMemberDevices.stream())
                .filter(new CachedBluetoothDevice$$ExternalSyntheticLambda4(2))
                .filter(
                        new Predicate() { // from class:
                                          // com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda13
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                CachedBluetoothDevice cachedBluetoothDevice =
                                        (CachedBluetoothDevice) obj;
                                return cachedBluetoothDevice.getDeviceSide() == i
                                        || cachedBluetoothDevice.getDeviceSide() == 2;
                            }
                        })
                .filter(new CachedBluetoothDevice$$ExternalSyntheticLambda4(3))
                .findAny();
    }

    public final String getConnectionSummary() {
        String str = this.mBluetoothCastMsg;
        return str != null ? str : getConnectionSummary(false);
    }

    public final int getDeviceSide() {
        HearingAidInfo hearingAidInfo = this.mHearingAidInfo;
        if (hearingAidInfo != null) {
            return hearingAidInfo.mSide;
        }
        return -1;
    }

    public final int getDeviceType() {
        ManufacturerData manufacturerData;
        int i;
        String str;
        if (BluetoothUtils.isEnabledUltraPowerSaving(this.mContext)) {
            Log.d("CachedBluetoothDevice", "getDeviceType: EmergencyMode enabled");
            return 0;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager == null) {
            Log.d("CachedBluetoothDevice", "getDeviceType: LocalBluetoothProfileManager is null");
            return 0;
        }
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                localBluetoothProfileManager.mDeviceManager;
        if (cachedBluetoothDeviceManager == null) {
            Log.d("CachedBluetoothDevice", "getDeviceType: CachedBluetoothDeviceManager is null");
            return 0;
        }
        if (getBtClassDrawable() == R.drawable.list_ic_wearable
                && (str = this.mDeviceName) != null
                && str.startsWith("GALAXY Gear (")) {
            if (cachedBluetoothDeviceManager.isValidStub()) {
                return 1;
            }
        } else {
            if (getManufacturerRawData() == null
                    || !((i = (manufacturerData = this.mManufacturerData).mManufacturerType) == 1
                            || i == 2
                            || i == 3)) {
                return 0;
            }
            byte[] bArr = manufacturerData.mData.mDeviceId;
            byte b = bArr[0];
            if (b == 0) {
                int i2 = bArr[1] & 255;
                if (i2 >= 1 && i2 < 144 && cachedBluetoothDeviceManager.isValidStub()) {
                    return 2;
                }
            } else if ((b == 1 || b == 2 || b == 3) && cachedBluetoothDeviceManager.isValidStub()) {
                return 2;
            }
            ManufacturerData.Data data = this.mManufacturerData.mData;
            byte b2 = data.mDeviceStatus;
            byte b3 = data.mDeviceSubType;
            if ((b2 & 128) == 128 && b3 >= 16 && b3 <= 31) {
                Log.i("CachedBluetoothDevice", "Found a SEC Wearable device with new type");
                if (cachedBluetoothDeviceManager.isValidStub()) {
                    Log.w("CachedBluetoothDevice", "jump to WM");
                    return 2;
                }
            }
        }
        return 0;
    }

    public final Pair getDrawableWithDescription() {
        String stringMetaData = BluetoothUtils.getStringMetaData(this.mDevice, 5);
        Uri parse = stringMetaData == null ? null : Uri.parse(stringMetaData);
        Pair btClassDrawableWithDescription =
                BluetoothUtils.getBtClassDrawableWithDescription(this.mContext, this);
        if (BluetoothUtils.isAdvancedDetailsHeader(this.mDevice) && parse != null) {
            BitmapDrawable bitmapDrawable = this.mDrawableCache.get(parse.toString());
            if (bitmapDrawable != null) {
                return new Pair(
                        new AdaptiveOutlineDrawable(
                                this.mContext.getResources(), bitmapDrawable.getBitmap()),
                        (String) btClassDrawableWithDescription.second);
            }
            refresh();
        }
        return BluetoothUtils.getBtRainbowDrawableWithDescription(this.mContext, this);
    }

    public final long getHiSyncId() {
        HearingAidInfo hearingAidInfo = this.mHearingAidInfo;
        if (hearingAidInfo != null) {
            return hearingAidInfo.mHiSyncId;
        }
        return 0L;
    }

    public final Drawable getIconDrawable() {
        Drawable icon;
        String resourcePath = getResourcePath();
        if (TextUtils.isEmpty(resourcePath)) {
            Log.d("CachedBluetoothDevice", "getLocalIconResource: path is null");
            icon = null;
        } else if (isSupportAuraCast()) {
            Context context = this.mContext;
            String str = ScspUtils.FILE_PATH_ROOT;
            Log.d("ScspUtils", "getAuraCastIcon: path = " + resourcePath);
            icon =
                    ScspUtils.getIcon(
                            context,
                            resourcePath
                                    + ScspUtils.FILE_NAME_AURA_CAST
                                    + ScspUtils.FILE_EXTENSION_SVG);
        } else {
            Context context2 = this.mContext;
            String str2 = ScspUtils.FILE_PATH_ROOT;
            Log.d("ScspUtils", "getListIcon: path = " + resourcePath);
            icon =
                    ScspUtils.getIcon(
                            context2,
                            resourcePath + ScspUtils.FILE_NAME + ScspUtils.FILE_EXTENSION_SVG);
        }
        if (icon == null) {
            Resources resources = this.mContext.getResources();
            Drawable drawable = resources.getDrawable(getBtClassDrawable());
            if (isSupportAuraCast()) {
                ManufacturerData manufacturerData = this.mManufacturerData;
                int deviceIcon = manufacturerData != null ? manufacturerData.getDeviceIcon() : 0;
                if (deviceIcon != 0 && deviceIcon == R.drawable.list_ic_earbuds_stem) {
                    return resources.getDrawable(R.drawable.list_ic_earbuds_stem_auracast);
                }
                icon =
                        BluetoothUtils.getOverlayIconTintableDrawable(
                                this.mContext,
                                drawable,
                                R.drawable.auracast_ic_overlay,
                                R.drawable.auracast_ic_tintable);
            } else {
                icon = drawable;
            }
        }
        return icon;
    }

    public final String getIdentityAddress() {
        String identityAddress = this.mDevice.getIdentityAddress();
        return TextUtils.isEmpty(identityAddress) ? this.mDevice.getAddress() : identityAddress;
    }

    public final String getIdentityAddressForLog() {
        String identityAddress = this.mDevice.getIdentityAddress();
        if (BluetoothUtils.DEBUG) {
            return identityAddress;
        }
        if (identityAddress == null) {
            return "null";
        }
        String replaceAll = identityAddress.replaceAll(":", ApnSettings.MVNO_NONE);
        return replaceAll.substring(0, 6) + "_" + replaceAll.substring(11);
    }

    public final byte[] getManufacturerRawData() {
        ManufacturerData manufacturerData = this.mManufacturerData;
        if (manufacturerData == null) {
            return null;
        }
        return manufacturerData.mManufacturerRawData;
    }

    public final int getMaxConnectionState() {
        int i;
        int profileConnectionState;
        synchronized (this.mProfileLock) {
            try {
                i = 0;
                for (LocalBluetoothProfile localBluetoothProfile : getProfiles()) {
                    if (localBluetoothProfile != null
                            && !(localBluetoothProfile instanceof CsipSetCoordinatorProfile)
                            && (profileConnectionState =
                                            getProfileConnectionState(localBluetoothProfile))
                                    > i) {
                        i = profileConnectionState;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getMessagePermissionChoice() {
        int messageAccessPermission = this.mDevice.getMessageAccessPermission();
        if (messageAccessPermission == 1) {
            return 1;
        }
        return messageAccessPermission == 2 ? 2 : 0;
    }

    public final String getName() {
        if (!TextUtils.isEmpty(this.mName) && !this.mName.equals(this.mDeviceName)) {
            return this.mName;
        }
        String str = this.mDeviceName;
        return str != null ? str : this.mAddress;
    }

    public final String getNameForLog() {
        StringBuilder sb = new StringBuilder();
        String str = this.mName;
        if (str == null || str.equals(this.mDeviceName)) {
            String str2 = this.mDeviceName;
            if (str2 != null && !str2.equals(this.mDevice.getAddress())) {
                sb.append("(N) ");
            }
        } else {
            sb.append("(A) ");
        }
        String name = getName();
        if (!name.equals(this.mDevice.getAddress()) || BluetoothUtils.DEBUG) {
            sb.append(name);
            return sb.toString();
        }
        return name.substring(0, 14) + ":XX";
    }

    public final String getNameForSAlogging() {
        String name = getName();
        if (!name.equals(this.mDevice.getAddress()) || BluetoothUtils.DEBUG) {
            return name;
        }
        return name.substring(0, 14) + ":XX";
    }

    public final int getPhonebookPermissionChoice() {
        int phonebookAccessPermission = this.mDevice.getPhonebookAccessPermission();
        if (phonebookAccessPermission == 1) {
            return 1;
        }
        return phonebookAccessPermission == 2 ? 2 : 0;
    }

    public final int getProfileConnectionState(LocalBluetoothProfile localBluetoothProfile) {
        if (localBluetoothProfile == null) {
            Log.e("CachedBluetoothDevice", "getProfileConnectionState :: profile is null");
            return 0;
        }
        if (this.mProfileConnectionState == null) {
            this.mProfileConnectionState = new HashMap();
        }
        synchronized (this.mProfileLock) {
            try {
                if (!this.mProfiles.contains(localBluetoothProfile)) {
                    Log.e(
                            "CachedBluetoothDevice",
                            "getProfileConnectionState :: not support profile = "
                                    + localBluetoothProfile);
                    return 0;
                }
                if (this.mProfileConnectionState.get(localBluetoothProfile) != null) {
                    return ((Integer) this.mProfileConnectionState.get(localBluetoothProfile))
                            .intValue();
                }
                int connectionStatus = localBluetoothProfile.getConnectionStatus(this.mDevice);
                Log.d(
                        "CachedBluetoothDevice",
                        "getProfileConnectionState :: "
                                + localBluetoothProfile
                                + ", state : "
                                + connectionStatus);
                this.mProfileConnectionState.put(
                        localBluetoothProfile, Integer.valueOf(connectionStatus));
                return connectionStatus;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getProfiles() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mProfileLock) {
            arrayList.addAll(this.mProfiles);
        }
        return Collections.unmodifiableList(arrayList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00ed, code lost:

       r14 = r14[1].trim();
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00f3, code lost:

       r8.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f7, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f8, code lost:

       android.util.Log.w("ScspUtils", "getMappingKey: Exception.", r0);
    */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0192 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getResourcePath() {
        /*
            Method dump skipped, instructions count: 533
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.CachedBluetoothDevice.getResourcePath():java.lang.String");
    }

    public final int getSimPermissionChoice() {
        int simAccessPermission = this.mDevice.getSimAccessPermission();
        if (simAccessPermission == 1) {
            return 1;
        }
        return simAccessPermission == 2 ? 2 : 0;
    }

    public final boolean hasProfile(LocalBluetoothProfile localBluetoothProfile) {
        if (localBluetoothProfile == null) {
            Log.e("CachedBluetoothDevice", "hasProfile :: target profile is null, return false.");
            return false;
        }
        List profiles = getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            LocalBluetoothProfile localBluetoothProfile2 = (LocalBluetoothProfile) profiles.get(i);
            if (localBluetoothProfile2 != null
                    && localBluetoothProfile2.equals(localBluetoothProfile)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.mDevice.getAddress().hashCode();
    }

    public final void initDrawableCache() {
        this.mDrawableCache =
                new AnonymousClass1(((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8);
    }

    public boolean isActiveDevice(int i) {
        if (i == 1) {
            return this.mIsActiveDeviceHeadset;
        }
        if (i == 2) {
            return this.mIsActiveDeviceA2dp;
        }
        if (i == 21) {
            return this.mIsActiveDeviceHearingAid;
        }
        if (i == 22) {
            return this.mIsActiveDeviceLeAudio;
        }
        RecordingInputConnection$$ExternalSyntheticOutline0.m(
                i, "getActiveDevice: unknown profile ", "CachedBluetoothDevice");
        return false;
    }

    public final boolean isBuds3Device() {
        int i;
        byte[] bArr = this.mManufacturerData.mData.mDeviceId;
        return bArr != null
                && bArr.length > 1
                && bArr[0] == 1
                && (i = bArr[1] & 255) >= 77
                && i <= 98;
    }

    public final boolean isBusy() {
        int profileConnectionState;
        for (LocalBluetoothProfile localBluetoothProfile : getProfiles()) {
            if (localBluetoothProfile != null
                    && ((profileConnectionState = getProfileConnectionState(localBluetoothProfile))
                                    == 1
                            || profileConnectionState == 3)) {
                return true;
            }
        }
        return this.mBondState == 11;
    }

    public final boolean isBusyForList() {
        boolean z = false;
        boolean z2 = false;
        for (LocalBluetoothProfile localBluetoothProfile : getProfiles()) {
            if (localBluetoothProfile != null) {
                int profileConnectionState = getProfileConnectionState(localBluetoothProfile);
                if (profileConnectionState == 1 || profileConnectionState == 3) {
                    z = true;
                }
                if (profileConnectionState == 2) {
                    z2 = true;
                }
            }
        }
        return (z && !z2) || this.mBondState == 11;
    }

    public final boolean isConnected() {
        synchronized (this.mProfileLock) {
            try {
                Iterator it = this.mProfiles.iterator();
                while (it.hasNext()) {
                    if (getProfileConnectionState((LocalBluetoothProfile) it.next()) == 2) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isConnectedAshaHearingAidDevice() {
        HearingAidProfile hearingAidProfile;
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        return (localBluetoothProfileManager == null
                        || (hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile)
                                == null
                        || hearingAidProfile.getConnectionStatus(this.mDevice) != 2)
                ? false
                : true;
    }

    public final boolean isConnectedForGroup() {
        if (isConnected()) {
            return true;
        }
        return isConnectedMembers();
    }

    public final boolean isConnectedHapClientDevice() {
        HapClientProfile hapClientProfile = this.mProfileManager.mHapClientProfile;
        return hapClientProfile != null && hapClientProfile.getConnectionStatus(this.mDevice) == 2;
    }

    public final boolean isConnectedLeAudioDevice() {
        LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
        return leAudioProfile != null && leAudioProfile.getConnectionStatus(this.mDevice) == 2;
    }

    public final boolean isConnectedMembers() {
        Iterator it = this.mMemberDevices.iterator();
        while (it.hasNext()) {
            if (((CachedBluetoothDevice) it.next()).isConnected()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isConnectedProfile(LocalBluetoothProfile localBluetoothProfile) {
        return getProfileConnectionState(localBluetoothProfile) == 2;
    }

    public final boolean isGearIconX() {
        BluetoothClass bluetoothClass;
        byte[] manufacturerRawData = getManufacturerRawData();
        if (manufacturerRawData == null
                || (bluetoothClass = this.mBtClass) == null
                || manufacturerRawData.length < 9) {
            return false;
        }
        byte[] bArr = this.mManufacturerData.mData.mDeviceId;
        byte b = bArr[0];
        return (b == 0 || b == 1) && bArr[1] == 1 && bluetoothClass.getDeviceClass() == 1028;
    }

    public final boolean isHearableUsingWearableManager() {
        BluetoothClass bluetoothClass;
        byte b;
        byte[] manufacturerRawData = getManufacturerRawData();
        if (manufacturerRawData == null
                || (bluetoothClass = this.mBtClass) == null
                || manufacturerRawData.length < 9) {
            return false;
        }
        byte[] bArr = this.mManufacturerData.mData.mDeviceId;
        int i = bArr[1] & 255;
        return bluetoothClass.getDeviceClass() == 1028
                && (((b = bArr[0]) == 0 && i >= 1 && i < 144) || b == 1 || b == 2 || b == 3);
    }

    public final boolean isHearingAidDevice() {
        return this.mHearingAidInfo != null;
    }

    public final boolean isPanProfileOnlySupported() {
        boolean z;
        synchronized (this.mProfileLock) {
            try {
                z = true;
                if (this.mProfiles.size() != 1
                        || !this.mProfiles.stream()
                                .anyMatch(
                                        new Predicate() { // from class:
                                                          // com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                LocalBluetoothProfile localBluetoothProfile =
                                                        (LocalBluetoothProfile) obj;
                                                CachedBluetoothDevice.this.getClass();
                                                if (!(localBluetoothProfile
                                                        instanceof PanProfile)) {
                                                    return false;
                                                }
                                                ((PanProfile) localBluetoothProfile).getClass();
                                                return true;
                                            }
                                        })) {
                    z = false;
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean isRing() {
        byte b;
        ManufacturerData manufacturerData = this.mManufacturerData;
        return manufacturerData != null
                && manufacturerData.mManufacturerType == 1
                && (b = manufacturerData.mData.mDeviceCategory) >= 0
                && b == 1;
    }

    public final boolean isSupportAuraCast() {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager == null
                || !hasProfile(localBluetoothProfileManager.mLeAudioBroadcastAssistant)) {
            return false;
        }
        if (!isHearableUsingWearableManager()) {
            return true;
        }
        BluetoothDevice bluetoothDevice = this.mDevice;
        SmepTag smepTag = SmepTag.SUPPORTED_FEATURES;
        byte[] m = BluetoothUtils$$ExternalSyntheticOutline0.m(smepTag, bluetoothDevice);
        if (m == null || m.length < 5) {
            Log.e("CachedBluetoothDevice", "isSupportAssistant: DataPacket is too short.");
            return false;
        }
        if ((((m[0] & 255) | ((m[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL)
                != smepTag.getTag()) {
            return false;
        }
        int i = 2;
        while (i < m.length) {
            int i2 = ((m[i] & 255) | ((m[i + 1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL;
            int i3 = m[i + 2] & 255;
            byte[] bArr = new byte[i3];
            System.arraycopy(m, i + 3, bArr, 0, i3);
            i += i3 + 3;
            if (AnonymousClass2.$SwitchMap$com$samsung$android$bluetooth$SmepTag[
                            SmepTag.getSmepKey(i2).ordinal()]
                    != 1) {
                Log.e(
                        "CachedBluetoothDevice",
                        "isSupportAssistant: NOT SUPPORTED FEATURE : "
                                .concat(String.format("%x", Integer.valueOf(i2))));
            } else {
                if (i3 < 1) {
                    Log.d("CachedBluetoothDevice", "isSupportAssistant: wrong data");
                    return false;
                }
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("isSupportAssistant: data = "),
                        bArr[0],
                        "CachedBluetoothDevice");
                if (bArr[0] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void onProfileStateChanged(LocalBluetoothProfile localBluetoothProfile, int i) {
        BluetoothDevice bluetoothDevice;
        if (this.mDevice != null) {
            Log.d(
                    "CachedBluetoothDevice",
                    "onProfileStateChanged: profile "
                            + localBluetoothProfile
                            + ", device "
                            + this.mDevice.getAnonymizedAddress()
                            + ", newProfileState "
                            + i);
        } else {
            Log.d(
                    "CachedBluetoothDevice",
                    "onProfileStateChanged: profile "
                            + localBluetoothProfile
                            + ", device is null, newProfileState "
                            + i);
        }
        if (this.mLocalAdapter.getState() == 13) {
            Log.d(
                    "CachedBluetoothDevice",
                    " BT Turninig Off...Profile conn state change ignored...");
            return;
        }
        if (this.mDevice != null && (i == 2 || i == 0)) {
            AuditLog.log(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    "CachedBluetoothDevice",
                    String.format(
                            i == 2
                                    ? "Bluetooth profile %s on bluetooth device %s has connected."
                                    : "Bluetooth profile %s on bluetooth device %s has"
                                          + " disconnected.",
                            localBluetoothProfile,
                            this.mDevice.getAddress()),
                    ApnSettings.MVNO_NONE);
        }
        this.mProfileConnectionState.put(localBluetoothProfile, Integer.valueOf(i));
        synchronized (this.mProfileLock) {
            try {
                if (i == 2) {
                    if (!this.mProfiles.contains(localBluetoothProfile)) {
                        this.mRemovedProfiles.remove(localBluetoothProfile);
                        this.mProfiles.add(localBluetoothProfile);
                        if ((localBluetoothProfile instanceof PanProfile)
                                && ((PanProfile) localBluetoothProfile)
                                        .isLocalRoleNap(this.mDevice)) {
                            this.mLocalNapRoleConnected = true;
                        }
                    }
                    if (localBluetoothProfile instanceof HidProfile) {
                        updatePreferredTransport();
                    }
                    CachedBluetoothDevice cachedBluetoothDevice = this.mLeadDevice;
                    if (cachedBluetoothDevice != null
                            && !(localBluetoothProfile instanceof CsipSetCoordinatorProfile)
                            && !(localBluetoothProfile instanceof VolumeControlProfile)) {
                        cachedBluetoothDevice.resetRetryDetector();
                    }
                } else if (this.mLocalNapRoleConnected
                        && (localBluetoothProfile instanceof PanProfile)
                        && (bluetoothDevice = this.mDevice) != null) {
                    ParcelUuid[] uuids = bluetoothDevice.getUuids();
                    if (!ArrayUtils.contains(uuids, BluetoothUuid.NAP)
                            && ArrayUtils.contains(uuids, BluetoothUuid.PANU)
                            && i == 0) {
                        Log.d(
                                "CachedBluetoothDevice",
                                "Removing PanProfile from device after NAP disconnect");
                        this.mProfiles.remove(localBluetoothProfile);
                        this.mRemovedProfiles.add(localBluetoothProfile);
                        this.mLocalNapRoleConnected = false;
                    }
                }
                if (localBluetoothProfile instanceof LeAudioProfile) {
                    updatePreferredTransport();
                }
                HearingAidStatsLogUtils.updateHistoryIfNeeded(
                        this.mContext, this, localBluetoothProfile, i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void refresh() {
        ThreadUtils.postOnBackgroundThread(
                new CachedBluetoothDevice$$ExternalSyntheticLambda1(0, this));
    }

    public final void refreshName() {
        fetchName$1();
        if (BluetoothUtils.DEBUG) {
            Log.d("CachedBluetoothDevice", "Device name: " + getName());
        }
        dispatchAttributesChanged(true);
    }

    public final void registerCallback(Callback callback) {
        if (this.mCallbacks.contains(callback)) {
            this.mCallbacks.remove(callback);
        }
        this.mCallbacks.add(callback);
    }

    public final void resetRetryDetector() {
        BluetoothRetryDetector bluetoothRetryDetector = this.mBondingDetector;
        if (bluetoothRetryDetector != null) {
            bluetoothRetryDetector.reset();
        }
    }

    public final void setActive() {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager == null) {
            return;
        }
        A2dpProfile a2dpProfile = localBluetoothProfileManager.mA2dpProfile;
        boolean z = false;
        if (a2dpProfile != null && isConnectedProfile(a2dpProfile)) {
            BluetoothDevice bluetoothDevice = this.mDevice;
            BluetoothAdapter bluetoothAdapter = a2dpProfile.mBluetoothAdapter;
            if (bluetoothAdapter == null
                    ? false
                    : bluetoothDevice == null
                            ? bluetoothAdapter.removeActiveDevice(0)
                            : bluetoothAdapter.setActiveDevice(bluetoothDevice, 0)) {
                Log.i(
                        "CachedBluetoothDevice",
                        "OnPreferenceClickListener: A2DP active device=" + this);
            }
        }
        HeadsetProfile headsetProfile = this.mProfileManager.mHeadsetProfile;
        if (headsetProfile != null && isConnectedProfile(headsetProfile)) {
            BluetoothDevice bluetoothDevice2 = this.mDevice;
            BluetoothAdapter bluetoothAdapter2 = headsetProfile.mBluetoothAdapter;
            if (bluetoothAdapter2 == null
                    ? false
                    : bluetoothDevice2 == null
                            ? bluetoothAdapter2.removeActiveDevice(1)
                            : bluetoothAdapter2.setActiveDevice(bluetoothDevice2, 1)) {
                Log.i(
                        "CachedBluetoothDevice",
                        "OnPreferenceClickListener: Headset active device=" + this);
            }
        }
        HearingAidProfile hearingAidProfile = this.mProfileManager.mHearingAidProfile;
        if (hearingAidProfile != null
                && isConnectedProfile(hearingAidProfile)
                && hearingAidProfile.setActiveDevice(this.mDevice)) {
            Log.i(
                    "CachedBluetoothDevice",
                    "OnPreferenceClickListener: Hearing Aid active device=" + this);
        }
        LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
        if (leAudioProfile == null || !isConnectedProfile(leAudioProfile)) {
            return;
        }
        BluetoothDevice bluetoothDevice3 = this.mDevice;
        BluetoothAdapter bluetoothAdapter3 = leAudioProfile.mBluetoothAdapter;
        if (bluetoothAdapter3 != null) {
            z =
                    bluetoothDevice3 == null
                            ? bluetoothAdapter3.removeActiveDevice(2)
                            : bluetoothAdapter3.setActiveDevice(bluetoothDevice3, 2);
        }
        if (z) {
            Log.i(
                    "CachedBluetoothDevice",
                    "OnPreferenceClickListener: LeAudio active device=" + this);
        }
    }

    public final void setBtClass(BluetoothClass bluetoothClass) {
        if (this.mBtClass != bluetoothClass) {
            boolean z = BluetoothUtils.DEBUG;
            if (z) {
                Log.d("CachedBluetoothDevice", "setBtClass :: " + bluetoothClass);
            }
            if (this.mBtClass == null || bluetoothClass.getMajorDeviceClass() != 7936) {
                this.mBtClass = bluetoothClass;
                dispatchAttributesChanged(false);
            } else if (z) {
                Log.d(
                        "CachedBluetoothDevice",
                        "setBtClass :: btClass is " + bluetoothClass + ", not set uncategorized");
            }
        }
    }

    public final void setGroupId(int i) {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null
                && hasProfile(localBluetoothProfileManager.mLeAudioProfile)) {
            BluetoothDump.BtLog(
                    "CachedBtDev -- setGroupId: "
                            + this.mDevice.getAddressForLogging()
                            + ", groupId = "
                            + i);
        }
        this.mGroupId = i;
    }

    public final void setHearingAidInfo(HearingAidInfo hearingAidInfo) {
        this.mHearingAidInfo = hearingAidInfo;
        dispatchAttributesChanged();
    }

    public final void setJustDiscovered(boolean z) {
        if (this.mJustDiscovered != z) {
            this.mJustDiscovered = z;
            dispatchAttributesChanged(false);
        }
    }

    public final void setManufacturerData(byte[] bArr) {
        if (BluetoothUtils.DEBUG) {
            Log.d("CachedBluetoothDevice", "setManufacturerData to " + Arrays.toString(bArr));
        }
        if (bArr == null) {
            Log.i("CachedBluetoothDevice", "MF is NULL");
            return;
        }
        if (this.mManufacturerData == null) {
            ManufacturerData manufacturerData = new ManufacturerData(bArr);
            this.mManufacturerData = manufacturerData;
            manufacturerData.mData.getClass();
            this.mPrefixName = ApnSettings.MVNO_NONE;
            dispatchAttributesChanged(false);
            return;
        }
        if (Arrays.equals(getManufacturerRawData(), bArr)) {
            return;
        }
        this.mManufacturerData.updateDeviceInfo(bArr);
        this.mManufacturerData.mData.getClass();
        this.mPrefixName = ApnSettings.MVNO_NONE;
        dispatchAttributesChanged(false);
    }

    public final void setName(String str) {
        byte b;
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, getName())) {
            return;
        }
        byte[] m =
                BluetoothUtils$$ExternalSyntheticOutline0.m(
                        SmepTag.FEATURE_CHANGE_DEVICE_NAME, this.mDevice);
        if (m == null || m.length == 0) {
            Log.d("CachedBluetoothDevice", "FEATURE_CHANGE_DEVICE_NAME = null");
        } else {
            Log.d("CachedBluetoothDevice", "FEATURE_CHANGE_DEVICE_NAME = " + Arrays.toString(m));
        }
        if (m == null || m.length <= 3 || (b = m[3]) <= 0 || b == -1) {
            this.mDevice.setAlias(str);
        } else {
            this.mDevice.semSetMetadata(
                    SppByteUtil.makeTlv(
                            SmepTag.CMD_PERSONALIZED_NAME_VALUE.getTag(),
                            str.getBytes(StandardCharsets.UTF_8)));
        }
        dispatchAttributesChanged(true);
        Iterator it = this.mMemberDevices.iterator();
        while (it.hasNext()) {
            ((CachedBluetoothDevice) it.next()).setName(str);
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mSubDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.setName(str);
        }
    }

    public final void setRssi(short s) {
        int i = this.mRssiGroup;
        if (i != (s >= -56 ? 3 : s >= -68 ? 2 : 1)) {
            if (s >= -56) {
                if (i != 3) {
                    this.mRssiGroup = 3;
                    dispatchAttributesChanged(true);
                }
            } else if (s >= -68) {
                if (i != 2) {
                    this.mRssiGroup = 2;
                    dispatchAttributesChanged(true);
                }
            } else if (s < -68 && i != 1) {
                this.mRssiGroup = 1;
                dispatchAttributesChanged(true);
            }
            this.mRssi = s;
        }
    }

    public final boolean shouldLaunchGM(String str, boolean z) {
        Intent intent;
        String address = this.mDevice.getAddress();
        int deviceType = getDeviceType();
        boolean z2 = true;
        if (deviceType == 1) {
            intent = new Intent("com.samsung.android.sconnect.action.CONNECT_WEARABLE");
            intent.putExtra("WM_MANAGER", "watchmanager");
            intent.setPackage("com.samsung.android.app.watchmanagerstub");
            Log.d(
                    "CachedBluetoothDevice",
                    "shouldLaunchGM :: Send Bradcast to WatchManagerStub, type : "
                            .concat(
                                    deviceType != 0
                                            ? deviceType != 1
                                                    ? deviceType != 2 ? "UNKNOWN" : "GEAR"
                                                    : "GEAR1"
                                            : "GENERIC"));
        } else {
            if (deviceType != 2) {
                return false;
            }
            intent = new Intent("com.samsung.android.action.BLUETOOTH_DEVICE");
            intent.putExtra("DATA", getManufacturerRawData());
            intent.setPackage("com.samsung.android.app.watchmanagerstub");
            Log.d(
                    "CachedBluetoothDevice",
                    "shouldLaunchGM :: Send Bradcast to WatchManagerStub, type : "
                            .concat(
                                    deviceType != 0
                                            ? deviceType != 1
                                                    ? deviceType != 2 ? "UNKNOWN" : "GEAR"
                                                    : "GEAR1"
                                            : "GENERIC"));
            z2 = true ^ isHearableUsingWearableManager();
        }
        if (str != null) {
            intent.putExtra("request_app_package_name", str);
        }
        intent.putExtra("MAC", address);
        String str2 = this.mDeviceName;
        intent.putExtra(
                PeripheralConstants.Internal.BtPairingExtraDataType.DEVICE_NAME,
                ((str2 == null || str2.equals(this.mAddress))
                                && !TextUtils.isEmpty(this.mModelName))
                        ? this.mModelName
                        : this.mDeviceName);
        intent.putExtra("IS_START_ACTIVITY", z);
        intent.addFlags(268435456);
        intent.addFlags(32);
        intent.addFlags(16777216);
        this.mContext.sendBroadcast(intent, "com.samsung.bluetooth.permission.BLUETOOTH_DEVICE");
        StatusBarManager statusBarManager =
                (StatusBarManager) this.mContext.getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
        return z2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0018, code lost:

       if (r3.mType == 2) goto L11;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startPairing() {
        /*
            r3 = this;
            android.bluetooth.BluetoothAdapter r0 = r3.mLocalAdapter
            boolean r0 = r0.isDiscovering()
            if (r0 == 0) goto Ld
            android.bluetooth.BluetoothAdapter r0 = r3.mLocalAdapter
            r0.cancelDiscovery()
        Ld:
            r0 = 0
            r3.checkLEConnectionGuide(r0, r0)
            boolean r1 = r3.mIsRestored
            if (r1 == 0) goto L1b
            int r1 = r3.mType
            r2 = 2
            if (r1 != r2) goto L1b
            goto L1c
        L1b:
            r2 = r0
        L1c:
            android.bluetooth.BluetoothDevice r1 = r3.mDevice
            boolean r1 = r1.createBond(r2)
            if (r1 != 0) goto L25
            return r0
        L25:
            r0 = 1
            r3.mIsBondingByCached = r0
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.CachedBluetoothDevice.startPairing():boolean");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CachedBluetoothDevice{anonymizedAddress=");
        sb.append(this.mDevice.getAnonymizedAddress());
        sb.append(", name=");
        sb.append(getName());
        sb.append(", groupId=");
        sb.append(this.mGroupId);
        sb.append(", member=");
        sb.append(this.mMemberDevices);
        if (isHearingAidDevice()) {
            sb.append(", hearingAidInfo=");
            sb.append(this.mHearingAidInfo);
            sb.append(", subDevice=");
            sb.append(this.mSubDevice);
        }
        sb.append("}");
        return sb.toString();
    }

    public final void unpair() {
        if (this.mIsRestored) {
            LocalBluetoothManager localBluetoothManager =
                    LocalBluetoothManager.getInstance(this.mContext, null);
            if (localBluetoothManager != null) {
                localBluetoothManager.mCachedDeviceManager.removeRestoredDevice(this);
            }
        } else {
            unpairLegacy();
        }
        BluetoothRetryDetector bluetoothRetryDetector = this.mBondingDetector;
        if (bluetoothRetryDetector != null) {
            bluetoothRetryDetector.reset();
            if (isRing()) {
                this.mBondingDetector.setFailCase(
                        BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING);
            } else if (BluetoothUtils.isGalaxyWatchDevice(
                    this.mDeviceName,
                    this.mBtClass,
                    getManufacturerRawData(),
                    this.mDevice.getUuids())) {
                this.mBondingDetector.setFailCase(
                        BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH);
            } else {
                this.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.PAIRING_FAILURE);
            }
        }
    }

    public final void unpairLegacy() {
        int i = this.mBondState;
        if (i != 10) {
            if (i == 11) {
                this.mDevice.cancelBondProcess();
            }
            BluetoothDevice bluetoothDevice = this.mDevice;
            if (bluetoothDevice != null) {
                this.mUnpairing = true;
                BluetoothDump.BtLog(
                        "CachedBluetoothDevice -- unpairLegacy: decribe = " + describeDetail());
                if (this.mGroupId != -1) {
                    StringBuilder sb = new StringBuilder();
                    for (CachedBluetoothDevice cachedBluetoothDevice : this.mMemberDevices) {
                        if (sb.length() != 0) {
                            sb.append(" : ");
                        }
                        sb.append(
                                cachedBluetoothDevice.getName()
                                        + "("
                                        + cachedBluetoothDevice.mDevice.getAddress()
                                        + ")");
                    }
                    BluetoothDump.BtLog(
                            "CachedBluetoothDevice -- unpairLegacy: member = " + sb.toString());
                }
                if (bluetoothDevice.removeBond()) {
                    this.mDrawableCache.evictAll();
                    boolean z = BluetoothUtils.DEBUG;
                    if (z) {
                        StringBuilder sb2 =
                                new StringBuilder("Command sent successfully:REMOVE_BOND ");
                        StringBuilder sb3 = new StringBuilder();
                        if (z) {
                            sb3.append("Address:");
                            sb3.append(this.mDevice);
                        }
                        sb2.append(sb3.toString());
                        Log.d("CachedBluetoothDevice", sb2.toString());
                    }
                }
            }
        }
    }

    public final void unregisterCallback(Callback callback) {
        if (this.mCallbacks.contains(callback)) {
            this.mCallbacks.remove(callback);
        }
        this.mCallbacks.remove(callback);
        this.mCallbackExecutorMap.remove(callback);
    }

    public final void updatePreferredTransport() {
        if (this.mProfiles.stream()
                        .noneMatch(new CachedBluetoothDevice$$ExternalSyntheticLambda4(5))
                || this.mProfiles.stream()
                        .noneMatch(new CachedBluetoothDevice$$ExternalSyntheticLambda4(1))) {
            return;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        HidProfile hidProfile = localBluetoothProfileManager.mHidProfile;
        BluetoothDevice bluetoothDevice = this.mDevice;
        int i = localBluetoothProfileManager.mLeAudioProfile.isEnabled(bluetoothDevice) ? 2 : 1;
        BluetoothHidHost bluetoothHidHost = hidProfile.mService;
        if (bluetoothHidHost != null) {
            bluetoothHidHost.setPreferredTransport(bluetoothDevice, i);
        }
        Log.w("CachedBluetoothDevice", "Fail to set preferred transport");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0062, code lost:

       android.util.Log.d("CachedBluetoothDevice", "PAN connection exists. Restore PAN profile.");
       r8.mRemovedProfiles.remove(r2);
       r1 = r8.mProfileLock;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0070, code lost:

       monitor-enter(r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0071, code lost:

       r8.mProfiles.add(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0076, code lost:

       monitor-exit(r1);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean updateProfiles(android.os.ParcelUuid[] r9) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.CachedBluetoothDevice.updateProfiles(android.os.ParcelUuid[]):boolean");
    }

    @Override // java.lang.Comparable
    public final int compareTo(CachedBluetoothDevice cachedBluetoothDevice) {
        int maxConnectionState = cachedBluetoothDevice.getMaxConnectionState();
        int maxConnectionState2 = getMaxConnectionState();
        int i =
                ((!this.mIsRestored || this.mIsSynced) ? 0 : 1)
                        - ((!cachedBluetoothDevice.mIsRestored || cachedBluetoothDevice.mIsSynced)
                                ? 0
                                : 1);
        if (i != 0) {
            return i;
        }
        int i2 = (this.mIsSynced ? 1 : 0) - (cachedBluetoothDevice.mIsSynced ? 1 : 0);
        if (i2 != 0) {
            return i2;
        }
        int i3 = (maxConnectionState == 2 ? 1 : 0) - (maxConnectionState2 == 2 ? 1 : 0);
        if (i3 != 0) {
            return i3;
        }
        int i4 = cachedBluetoothDevice.mBondState == 12 ? 1 : 0;
        int i5 = this.mBondState;
        int i6 = i4 - (i5 == 12 ? 1 : 0);
        if (i6 != 0) {
            return i6;
        }
        if (i5 == 12) {
            int i7 =
                    ((maxConnectionState == 1 || maxConnectionState == 3) ? 1 : 0)
                            - ((maxConnectionState2 == 1 || maxConnectionState2 == 3) ? 1 : 0);
            if (i7 != 0) {
                return i7;
            }
            int i8 =
                    (cachedBluetoothDevice.mIsHearingAidDeviceByUUID ? 1 : 0)
                            - (this.mIsHearingAidDeviceByUUID ? 1 : 0);
            if (i8 != 0) {
                return i8;
            }
            long connectionTimeStamp =
                    cachedBluetoothDevice.mDevice.getConnectionTimeStamp()
                            - this.mDevice.getConnectionTimeStamp();
            if (connectionTimeStamp > 0) {
                return 1;
            }
            if (connectionTimeStamp < 0) {
                return -1;
            }
        } else {
            int i9 =
                    (cachedBluetoothDevice.mIsHearingAidDeviceByUUID ? 1 : 0)
                            - (this.mIsHearingAidDeviceByUUID ? 1 : 0);
            if (i9 != 0) {
                return i9;
            }
            int i10 = cachedBluetoothDevice.mRssiGroup - this.mRssiGroup;
            if (i10 != 0) {
                return i10;
            }
            long j = cachedBluetoothDevice.mBondTimeStamp - this.mBondTimeStamp;
            if (j > 0) {
                return 1;
            }
            if (j < 0) {
                return -1;
            }
            int i11 = this.mSequence - cachedBluetoothDevice.mSequence;
            if (i11 != 0) {
                return i11;
            }
        }
        int i12 = (cachedBluetoothDevice.mJustDiscovered ? 1 : 0) - (this.mJustDiscovered ? 1 : 0);
        if (i12 != 0) {
            return i12;
        }
        int i13 = cachedBluetoothDevice.mRssi - this.mRssi;
        return i13 != 0 ? i13 : getName().compareTo(cachedBluetoothDevice.getName());
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x038a A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getConnectionSummary(boolean r20) {
        /*
            Method dump skipped, instructions count: 910
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.CachedBluetoothDevice.getConnectionSummary(boolean):java.lang.String");
    }

    public final void dispatchAttributesChanged(boolean z) {
        Iterator it = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onDeviceAttributesChanged();
        }
        synchronized (this.mSemCallbacks) {
            try {
                Iterator it2 = ((ArrayList) this.mSemCallbacks).iterator();
                while (it2.hasNext()) {
                    ((SecBluetoothDevicePreference) ((SemCallback) it2.next()))
                            .semOnDeviceAttributesChanged();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setLocalBluetoothManager(LocalBluetoothManager localBluetoothManager) {}

    /* JADX WARN: Removed duplicated region for block: B:19:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public CachedBluetoothDevice(
            android.content.Context r5,
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r6,
            android.bluetooth.BluetoothDevice r7,
            android.content.Intent r8) {
        /*
            Method dump skipped, instructions count: 297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.CachedBluetoothDevice.<init>(android.content.Context,"
                    + " com.android.settingslib.bluetooth.LocalBluetoothProfileManager,"
                    + " android.bluetooth.BluetoothDevice, android.content.Intent):void");
    }

    public CachedBluetoothDevice(
            Context context,
            LocalBluetoothProfileManager localBluetoothProfileManager,
            BluetoothRestoredDevice bluetoothRestoredDevice) {
        this.mModelName = null;
        this.mType = 0;
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mCallbackExecutorMap = new ConcurrentHashMap();
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
        this.mContext = context;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mLocalAdapter = defaultAdapter;
        this.mProfileManager = localBluetoothProfileManager;
        this.mRestoredDevice = bluetoothRestoredDevice;
        String str = bluetoothRestoredDevice.mAddress;
        this.mAddress = str;
        if (defaultAdapter != null) {
            this.mDevice = defaultAdapter.getRemoteDevice(str);
        }
        this.mProfileConnectionState = new HashMap();
        fillRestoredData();
        this.mGroupId = -1;
        initDrawableCache();
    }

    public CachedBluetoothDevice(
            Context context,
            LocalBluetoothProfileManager localBluetoothProfileManager,
            BluetoothRestoredDevice bluetoothRestoredDevice,
            boolean z) {
        this.mModelName = null;
        this.mType = 0;
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mCallbackExecutorMap = new ConcurrentHashMap();
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
        this.mContext = context;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mLocalAdapter = defaultAdapter;
        this.mProfileManager = localBluetoothProfileManager;
        this.mRestoredDevice = bluetoothRestoredDevice;
        String str = bluetoothRestoredDevice.mAddress;
        this.mAddress = str;
        if (defaultAdapter != null) {
            this.mDevice = defaultAdapter.getRemoteDevice(str);
        }
        this.mProfileConnectionState = new HashMap();
        if (z) {
            String str2 = bluetoothRestoredDevice.mName;
            if (str2 == null) {
                fetchName$1();
            } else {
                this.mName = str2;
                this.mDeviceName = str2;
            }
            Log.d(
                    "CachedBluetoothDevice",
                    "fillSyncedData() :: Device - "
                            + getNameForLog()
                            + ", Class - "
                            + bluetoothRestoredDevice.mCod);
            setBtClass(new BluetoothClass(bluetoothRestoredDevice.mCod));
            BluetoothClass bluetoothClass = this.mBtClass;
            if (bluetoothClass != null
                    && !bluetoothClass.equals(this.mDevice.getBluetoothClass())) {
                this.mDevice.setBluetoothClass(bluetoothRestoredDevice.mCod);
            }
            this.mAppearance = (short) bluetoothRestoredDevice.mAppearance;
            setManufacturerData(bluetoothRestoredDevice.mManufacturerData);
            this.mBondTimeStamp = bluetoothRestoredDevice.mTimeStamp;
            this.mType = bluetoothRestoredDevice.mLinkType;
            if (bluetoothRestoredDevice.mManufacturerData != null
                    && !Arrays.equals(
                            this.mDevice.semGetManufacturerData(),
                            bluetoothRestoredDevice.mManufacturerData)) {
                this.mDevice.semSetManufacturerData(bluetoothRestoredDevice.mManufacturerData);
            }
            if (ArrayUtils.contains(
                            bluetoothRestoredDevice.mUuids,
                            ParcelUuid.fromString("f8620674-a1ed-41ab-a8b9-de9ad655729d"))
                    && this.mDevice.semGetAutoSwitchMode() == -1) {
                if (Settings.System.semGetIntForUser(
                                context.getContentResolver(),
                                "mcf_permission_denied",
                                0,
                                UserHandle.semGetMyUserId())
                        != 1) {
                    this.mDevice.semSetAutoSwitchMode(1);
                    Log.i(
                            "CachedBluetoothDevice",
                            "fillSyncedData :: call semSetAutoSwitchMode to enabled");
                } else {
                    this.mDevice.semSetAutoSwitchMode(0);
                    Log.i("CachedBluetoothDevice", "fillSyncedData :: mcf permission denied");
                    BluetoothDump.BtLog(
                            "CachedBluetoothDevice -- fillSyncedData :: mcf permission denied");
                }
            }
            this.mIsSynced = true;
            this.mIsRestored = true;
            this.mBondState = 10;
            this.mIsBondingByCached = false;
            updateProfiles(null);
            if (isRing()) {
                this.mBondingDetector =
                        new BluetoothRetryDetector(
                                BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING);
            } else if (BluetoothUtils.isGalaxyWatchDevice(
                    this.mDeviceName,
                    this.mBtClass,
                    getManufacturerRawData(),
                    bluetoothRestoredDevice.mUuids)) {
                this.mBondingDetector =
                        new BluetoothRetryDetector(
                                BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH);
            } else {
                this.mBondingDetector =
                        new BluetoothRetryDetector(
                                BluetoothRetryDetector.FailCase.CONNECTION_FAILURE);
            }
            dispatchAttributesChanged(true);
        } else {
            fillRestoredData();
        }
        this.mGroupId = -1;
        initDrawableCache();
    }
}
