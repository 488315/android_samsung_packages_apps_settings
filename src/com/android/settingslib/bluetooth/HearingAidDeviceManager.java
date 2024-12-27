package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioDeviceAttributes;
import android.provider.Settings;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.util.SparseIntArray;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HearingAidDeviceManager {
    public static final boolean DEBUG = BluetoothUtils.DEBUG;
    public final LocalBluetoothManager mBtManager;
    public final List mCachedDevices;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final List mFilteredCachedDevices;
    public final HearingAidAudioRoutingHelper mRoutingHelper;

    public HearingAidDeviceManager(
            Context context, LocalBluetoothManager localBluetoothManager, List list, List list2) {
        this.mFilteredCachedDevices = list2;
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mBtManager = localBluetoothManager;
        this.mCachedDevices = list;
        this.mRoutingHelper = new HearingAidAudioRoutingHelper(context);
    }

    public static boolean isValidHiSyncId(long j) {
        return j != 0;
    }

    public final CachedBluetoothDevice findMainDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice2;
        List<CachedBluetoothDevice> list = this.mCachedDevices;
        if (list == null) {
            return null;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice3 : list) {
            if (cachedBluetoothDevice3.mGroupId != -1) {
                Iterator it = ((HashSet) cachedBluetoothDevice3.mMemberDevices).iterator();
                while (it.hasNext()) {
                    CachedBluetoothDevice cachedBluetoothDevice4 =
                            (CachedBluetoothDevice) it.next();
                    if (cachedBluetoothDevice4 != null
                            && cachedBluetoothDevice4.equals(cachedBluetoothDevice)) {
                        return cachedBluetoothDevice3;
                    }
                }
            }
            if (isValidHiSyncId(cachedBluetoothDevice3.getHiSyncId())
                    && (cachedBluetoothDevice2 = cachedBluetoothDevice3.mSubDevice) != null
                    && cachedBluetoothDevice2.equals(cachedBluetoothDevice)) {
                return cachedBluetoothDevice3;
            }
        }
        return null;
    }

    public final HearingAidInfo generateHearingAidInfo(
            CachedBluetoothDevice cachedBluetoothDevice) {
        int deviceSide;
        int deviceMode;
        int i = 0;
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mBtManager.mProfileManager;
        HearingAidProfile hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile;
        boolean z = DEBUG;
        if (hearingAidProfile == null) {
            Log.w("HearingAidDeviceManager", "HearingAidProfile is not supported on this device");
        } else {
            long hiSyncId = hearingAidProfile.getHiSyncId(cachedBluetoothDevice.mDevice);
            if (isValidHiSyncId(hiSyncId)) {
                BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                BluetoothHearingAid bluetoothHearingAid = hearingAidProfile.mService;
                if (bluetoothHearingAid == null) {
                    Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                    deviceSide = -1;
                } else {
                    deviceSide = bluetoothHearingAid.getDeviceSide(bluetoothDevice);
                }
                int i2 =
                        HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(
                                deviceSide, -1);
                BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
                BluetoothHearingAid bluetoothHearingAid2 = hearingAidProfile.mService;
                if (bluetoothHearingAid2 == null) {
                    Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                    deviceMode = -1;
                } else {
                    deviceMode = bluetoothHearingAid2.getDeviceMode(bluetoothDevice2);
                }
                HearingAidInfo hearingAidInfo =
                        new HearingAidInfo(
                                i2,
                                HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(
                                        deviceMode, -1),
                                hiSyncId);
                if (z) {
                    Log.d(
                            "HearingAidDeviceManager",
                            "generateHearingAidInfo, "
                                    + cachedBluetoothDevice
                                    + ", info="
                                    + hearingAidInfo);
                }
                return hearingAidInfo;
            }
        }
        HapClientProfile hapClientProfile = localBluetoothProfileManager.mHapClientProfile;
        LeAudioProfile leAudioProfile = localBluetoothProfileManager.mLeAudioProfile;
        if (hapClientProfile == null || leAudioProfile == null) {
            Log.w(
                    "HearingAidDeviceManager",
                    "HapClientProfile or LeAudioProfile is not supported on this device");
            return null;
        }
        if (!cachedBluetoothDevice.getProfiles().stream()
                .anyMatch(new HearingAidDeviceManager$$ExternalSyntheticLambda0(i))) {
            return null;
        }
        BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
        BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
        int audioLocation =
                (bluetoothLeAudio == null || bluetoothDevice3 == null)
                        ? 0
                        : bluetoothLeAudio.getAudioLocation(bluetoothDevice3);
        BluetoothDevice bluetoothDevice4 = cachedBluetoothDevice.mDevice;
        BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
        int hearingAidType =
                bluetoothHapClient == null
                        ? -1
                        : bluetoothHapClient.getHearingAidType(bluetoothDevice4);
        if (audioLocation == 0 || hearingAidType == -1) {
            return null;
        }
        SparseIntArray sparseIntArray = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING;
        boolean z2 = (88413265 & audioLocation) != 0;
        boolean z3 = (audioLocation & 176826530) != 0;
        if (z2 && z3) {
            i = 2;
        } else if (!z2) {
            i = z3 ? 1 : -1;
        }
        HearingAidInfo hearingAidInfo2 =
                new HearingAidInfo(
                        i,
                        HearingAidInfo.HAP_DEVICE_TYPE_TO_INTERNAL_MODE_MAPPING.get(
                                hearingAidType, -1),
                        0L);
        if (z) {
            Log.d(
                    "HearingAidDeviceManager",
                    "generateHearingAidInfo, "
                            + cachedBluetoothDevice
                            + ", info="
                            + hearingAidInfo2);
        }
        return hearingAidInfo2;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initHearingAidDeviceIfNeeded(
            com.android.settingslib.bluetooth.CachedBluetoothDevice r9) {
        /*
            r8 = this;
            android.bluetooth.BluetoothDevice r0 = r9.mDevice
            com.android.settingslib.bluetooth.LocalBluetoothManager r8 = r8.mBtManager
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r1 = r8.mProfileManager
            r2 = 0
            if (r1 != 0) goto Lb
            goto L14
        Lb:
            com.android.settingslib.bluetooth.HearingAidProfile r1 = r1.mHearingAidProfile
            if (r1 != 0) goto L10
            goto L14
        L10:
            long r2 = r1.getHiSyncId(r0)
        L14:
            boolean r0 = isValidHiSyncId(r2)
            if (r0 == 0) goto L6f
            android.bluetooth.BluetoothDevice r0 = r9.mDevice
            java.lang.String r1 = "Proxy not attached to HearingAidService"
            java.lang.String r4 = "HearingAidProfile"
            r5 = -1
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r8 = r8.mProfileManager
            java.lang.String r6 = "HearingAidDeviceManager"
            if (r8 != 0) goto L29
        L27:
            r0 = r5
            goto L3f
        L29:
            com.android.settingslib.bluetooth.HearingAidProfile r7 = r8.mHearingAidProfile
            if (r7 != 0) goto L33
            java.lang.String r0 = "HearingAidProfile is not supported and not ready to fetch device side"
            android.util.Log.w(r6, r0)
            goto L27
        L33:
            android.bluetooth.BluetoothHearingAid r7 = r7.mService
            if (r7 != 0) goto L3b
            android.util.Log.w(r4, r1)
            goto L27
        L3b:
            int r0 = r7.getDeviceSide(r0)
        L3f:
            android.util.SparseIntArray r7 = com.android.settingslib.bluetooth.HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING
            int r0 = r7.get(r0, r5)
            android.bluetooth.BluetoothDevice r7 = r9.mDevice
            if (r8 != 0) goto L4b
        L49:
            r8 = r5
            goto L61
        L4b:
            com.android.settingslib.bluetooth.HearingAidProfile r8 = r8.mHearingAidProfile
            if (r8 != 0) goto L55
            java.lang.String r8 = "HearingAidProfile is not supported and not ready to fetch device mode"
            android.util.Log.w(r6, r8)
            goto L49
        L55:
            android.bluetooth.BluetoothHearingAid r8 = r8.mService
            if (r8 != 0) goto L5d
            android.util.Log.w(r4, r1)
            goto L49
        L5d:
            int r8 = r8.getDeviceMode(r7)
        L61:
            android.util.SparseIntArray r1 = com.android.settingslib.bluetooth.HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING
            int r8 = r1.get(r8, r5)
            com.android.settingslib.bluetooth.HearingAidInfo r1 = new com.android.settingslib.bluetooth.HearingAidInfo
            r1.<init>(r0, r8, r2)
            r9.setHearingAidInfo(r1)
        L6f:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.HearingAidDeviceManager.initHearingAidDeviceIfNeeded(com.android.settingslib.bluetooth.CachedBluetoothDevice):void");
    }

    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice) {
        if (FeatureFlagUtils.isEnabled(this.mContext, "settings_audio_routing")) {
            if (!cachedBluetoothDevice.isActiveDevice(21)
                    && !cachedBluetoothDevice.isActiveDevice(22)) {
                setPreferredDeviceRoutingStrategies(
                        HearingAidAudioRoutingConstants.CALL_ROUTING_ATTRIBUTES, null, 0);
                setPreferredDeviceRoutingStrategies(
                        HearingAidAudioRoutingConstants.MEDIA_ROUTING_ATTRIBUTES, null, 0);
                setPreferredDeviceRoutingStrategies(
                        HearingAidAudioRoutingConstants.RINGTONE_ROUTING_ATTRIBUTES, null, 0);
                setPreferredDeviceRoutingStrategies(
                        HearingAidAudioRoutingConstants.NOTIFICATION_ROUTING_ATTRIBUTES, null, 0);
                return;
            }
            AudioDeviceAttributes matchedHearingDeviceAttributes =
                    this.mRoutingHelper.getMatchedHearingDeviceAttributes(cachedBluetoothDevice);
            if (matchedHearingDeviceAttributes == null) {
                Log.w(
                        "HearingAidDeviceManager",
                        "Can not find expected AudioDeviceAttributes for hearing device: "
                                + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
                return;
            }
            int i = Settings.Secure.getInt(this.mContentResolver, "hearing_aid_call_routing", 0);
            int i2 = Settings.Secure.getInt(this.mContentResolver, "hearing_aid_media_routing", 0);
            int i3 =
                    Settings.Secure.getInt(
                            this.mContentResolver, "hearing_aid_ringtone_routing", 0);
            int i4 =
                    Settings.Secure.getInt(
                            this.mContentResolver, "hearing_aid_notification_routing", 0);
            setPreferredDeviceRoutingStrategies(
                    HearingAidAudioRoutingConstants.CALL_ROUTING_ATTRIBUTES,
                    matchedHearingDeviceAttributes,
                    i);
            setPreferredDeviceRoutingStrategies(
                    HearingAidAudioRoutingConstants.MEDIA_ROUTING_ATTRIBUTES,
                    matchedHearingDeviceAttributes,
                    i2);
            setPreferredDeviceRoutingStrategies(
                    HearingAidAudioRoutingConstants.RINGTONE_ROUTING_ATTRIBUTES,
                    matchedHearingDeviceAttributes,
                    i3);
            setPreferredDeviceRoutingStrategies(
                    HearingAidAudioRoutingConstants.NOTIFICATION_ROUTING_ATTRIBUTES,
                    matchedHearingDeviceAttributes,
                    i4);
        }
    }

    @VisibleForTesting
    public void onHiSyncIdChanged(long j) {
        CachedBluetoothDevice cachedBluetoothDevice;
        int size = this.mCachedDevices.size() - 1;
        int i = -1;
        while (size >= 0) {
            CachedBluetoothDevice cachedBluetoothDevice2 =
                    (CachedBluetoothDevice) this.mCachedDevices.get(size);
            if (cachedBluetoothDevice2.getHiSyncId() == j) {
                if (cachedBluetoothDevice2.getProfiles().stream()
                        .anyMatch(new HearingAidDeviceManager$$ExternalSyntheticLambda0(1))) {
                    Log.w(
                            "HearingAidDeviceManager",
                            "Skip ASHA grouping since this device supports CSIP");
                } else {
                    if (i != -1) {
                        if (cachedBluetoothDevice2.isConnected()) {
                            cachedBluetoothDevice =
                                    (CachedBluetoothDevice) this.mCachedDevices.get(i);
                            size = i;
                        } else {
                            cachedBluetoothDevice2 =
                                    (CachedBluetoothDevice) this.mCachedDevices.get(i);
                            cachedBluetoothDevice = cachedBluetoothDevice2;
                        }
                        cachedBluetoothDevice2.mSubDevice = cachedBluetoothDevice;
                        List list = this.mFilteredCachedDevices;
                        if (list != null) {
                            list.remove(this.mCachedDevices.get(size));
                        }
                        this.mCachedDevices.remove(size);
                        String str =
                                "onHiSyncIdChanged: removed from UI device ="
                                        + cachedBluetoothDevice
                                        + ", with hiSyncId="
                                        + j;
                        if (DEBUG) {
                            Log.d("HearingAidDeviceManager", str);
                        }
                        this.mBtManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice);
                        return;
                    }
                    i = size;
                }
            }
            size--;
        }
    }

    public final boolean onProfileConnectionStateChangedIfProcessed(
            int i, CachedBluetoothDevice cachedBluetoothDevice) {
        if (i != 0) {
            if (i != 2) {
                return false;
            }
            onHiSyncIdChanged(cachedBluetoothDevice.getHiSyncId());
            CachedBluetoothDevice findMainDevice = findMainDevice(cachedBluetoothDevice);
            if (findMainDevice == null) {
                return false;
            }
            if (!findMainDevice.isConnected()) {
                switchDeviceContent(findMainDevice, cachedBluetoothDevice);
            }
            return true;
        }
        if (cachedBluetoothDevice.mUnpairing || findMainDevice(cachedBluetoothDevice) != null) {
            return true;
        }
        CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
        CachedBluetoothDevice cachedBluetoothDevice3 =
                (cachedBluetoothDevice2 == null || !cachedBluetoothDevice2.isConnected())
                        ? (CachedBluetoothDevice)
                                cachedBluetoothDevice.mMemberDevices.stream()
                                        .filter(
                                                new HearingAidDeviceManager$$ExternalSyntheticLambda0(
                                                        3))
                                        .findAny()
                                        .orElse(null)
                        : cachedBluetoothDevice.mSubDevice;
        if (cachedBluetoothDevice3 == null) {
            return false;
        }
        switchDeviceContent(cachedBluetoothDevice, cachedBluetoothDevice3);
        return true;
    }

    public final void setPreferredDeviceRoutingStrategies(
            int[] iArr, AudioDeviceAttributes audioDeviceAttributes, int i) {
        HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper = this.mRoutingHelper;
        List supportedStrategies = hearingAidAudioRoutingHelper.getSupportedStrategies(iArr);
        if (hearingAidAudioRoutingHelper.setPreferredDeviceRoutingStrategies(
                supportedStrategies, audioDeviceAttributes, i)) {
            return;
        }
        Log.w(
                "HearingAidDeviceManager",
                "routingStrategies: "
                        + supportedStrategies.toString()
                        + "routingValue: "
                        + i
                        + " fail to configure AudioProductStrategy");
    }

    public final boolean setSubDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice2;
        long hiSyncId = cachedBluetoothDevice.getHiSyncId();
        if (isValidHiSyncId(hiSyncId)) {
            if (cachedBluetoothDevice.getProfiles().stream()
                    .anyMatch(new HearingAidDeviceManager$$ExternalSyntheticLambda0(2))) {
                Log.w(
                        "HearingAidDeviceManager",
                        "Skip ASHA grouping since this device supports CSIP");
                return false;
            }
            int size = this.mCachedDevices.size() - 1;
            while (true) {
                if (size < 0) {
                    cachedBluetoothDevice2 = null;
                    break;
                }
                cachedBluetoothDevice2 = (CachedBluetoothDevice) this.mCachedDevices.get(size);
                if (cachedBluetoothDevice2.getHiSyncId() == hiSyncId) {
                    break;
                }
                size--;
            }
            if (cachedBluetoothDevice2 != null) {
                cachedBluetoothDevice2.mSubDevice = cachedBluetoothDevice;
                cachedBluetoothDevice.setName(cachedBluetoothDevice2.getName());
                return true;
            }
        }
        return false;
    }

    public final void switchDeviceContent(
            CachedBluetoothDevice cachedBluetoothDevice,
            CachedBluetoothDevice cachedBluetoothDevice2) {
        CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice.mSubDevice;
        if (cachedBluetoothDevice3 == null
                || !cachedBluetoothDevice3.equals(cachedBluetoothDevice2)) {
            return;
        }
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
        localBluetoothManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice);
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        short s = cachedBluetoothDevice.mRssi;
        boolean z = cachedBluetoothDevice.mJustDiscovered;
        HearingAidInfo hearingAidInfo = cachedBluetoothDevice.mHearingAidInfo;
        HashMap hashMap = cachedBluetoothDevice.mProfileConnectionState;
        String str = cachedBluetoothDevice.mName;
        CachedBluetoothDevice cachedBluetoothDevice4 = cachedBluetoothDevice.mSubDevice;
        cachedBluetoothDevice.mDevice = cachedBluetoothDevice4.mDevice;
        cachedBluetoothDevice.mRssi = cachedBluetoothDevice4.mRssi;
        cachedBluetoothDevice.mJustDiscovered = cachedBluetoothDevice4.mJustDiscovered;
        cachedBluetoothDevice.mProfileConnectionState =
                cachedBluetoothDevice4.mProfileConnectionState;
        cachedBluetoothDevice.mName = cachedBluetoothDevice4.mName;
        cachedBluetoothDevice.mHearingAidInfo = cachedBluetoothDevice4.mHearingAidInfo;
        cachedBluetoothDevice4.mDevice = bluetoothDevice;
        cachedBluetoothDevice4.mRssi = s;
        cachedBluetoothDevice4.mJustDiscovered = z;
        cachedBluetoothDevice4.mProfileConnectionState = hashMap;
        cachedBluetoothDevice4.mName = str;
        cachedBluetoothDevice4.mHearingAidInfo = hearingAidInfo;
        cachedBluetoothDevice.fetchActiveDevices();
        localBluetoothManager.mEventManager.dispatchDeviceAdded(cachedBluetoothDevice);
    }

    public final void syncDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice findMainDevice;
        HapClientProfile hapClientProfile = this.mBtManager.mProfileManager.mHapClientProfile;
        if (hapClientProfile != null) {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
            if ((bluetoothHapClient == null
                            ? false
                            : bluetoothHapClient.supportsSynchronizedPresets(bluetoothDevice))
                    || (findMainDevice = findMainDevice(cachedBluetoothDevice)) == null) {
                return;
            }
            int activePresetIndex = hapClientProfile.getActivePresetIndex(findMainDevice.mDevice);
            int activePresetIndex2 =
                    hapClientProfile.getActivePresetIndex(cachedBluetoothDevice.mDevice);
            if (activePresetIndex == 0 || activePresetIndex == activePresetIndex2) {
                return;
            }
            if (DEBUG) {
                StringBuilder m =
                        RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                                "syncing preset from ",
                                "->",
                                activePresetIndex2,
                                activePresetIndex,
                                ", device=");
                m.append(cachedBluetoothDevice);
                Log.d("HearingAidDeviceManager", m.toString());
            }
            hapClientProfile.selectPreset(cachedBluetoothDevice.mDevice, activePresetIndex);
        }
    }

    public final void updateHearingAidsDevices() {
        HearingAidInfo generateHearingAidInfo;
        HashSet hashSet = new HashSet();
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            if (!isValidHiSyncId(cachedBluetoothDevice.getHiSyncId())
                    && (generateHearingAidInfo = generateHearingAidInfo(cachedBluetoothDevice))
                            != null) {
                cachedBluetoothDevice.setHearingAidInfo(generateHearingAidInfo);
                long j = generateHearingAidInfo.mHiSyncId;
                if (isValidHiSyncId(j)) {
                    hashSet.add(Long.valueOf(j));
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            onHiSyncIdChanged(((Long) it.next()).longValue());
        }
    }

    @VisibleForTesting
    public HearingAidDeviceManager(
            Context context,
            LocalBluetoothManager localBluetoothManager,
            List<CachedBluetoothDevice> list,
            HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper) {
        this.mContentResolver = context.getContentResolver();
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
        this.mCachedDevices = list;
        this.mRoutingHelper = hearingAidAudioRoutingHelper;
        this.mFilteredCachedDevices =
                localBluetoothManager.mCachedDeviceManager.mFilteredCachedDevices;
    }
}
