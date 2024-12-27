package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.SystemProperties;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.bluetooth.MapProfile;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothDetailsProfilesController$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BluetoothDetailsProfilesController f$0;

    public /* synthetic */ BluetoothDetailsProfilesController$$ExternalSyntheticLambda4(
            BluetoothDetailsProfilesController bluetoothDetailsProfilesController, int i) {
        this.$r8$classId = i;
        this.f$0 = bluetoothDetailsProfilesController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final BluetoothDetailsProfilesController bluetoothDetailsProfilesController = this.f$0;
        switch (i) {
            case 0:
                bluetoothDetailsProfilesController.getClass();
                ArrayList arrayList = new ArrayList();
                ((HashMap) bluetoothDetailsProfilesController.mProfileDeviceMap).clear();
                List list = bluetoothDetailsProfilesController.mAllOfCachedDevices;
                CachedBluetoothDevice cachedBluetoothDevice =
                        bluetoothDetailsProfilesController.mCachedDevice;
                if (list != null && !((ArrayList) list).isEmpty()) {
                    Iterator it =
                            ((ArrayList) bluetoothDetailsProfilesController.mAllOfCachedDevices)
                                    .iterator();
                    while (it.hasNext()) {
                        CachedBluetoothDevice cachedBluetoothDevice2 =
                                (CachedBluetoothDevice) it.next();
                        Iterator it2 =
                                ((ArrayList) cachedBluetoothDevice2.getConnectableProfiles())
                                        .iterator();
                        while (it2.hasNext()) {
                            LocalBluetoothProfile localBluetoothProfile =
                                    (LocalBluetoothProfile) it2.next();
                            if (((HashMap) bluetoothDetailsProfilesController.mProfileDeviceMap)
                                    .containsKey(localBluetoothProfile.toString())) {
                                ((List)
                                                ((HashMap)
                                                                bluetoothDetailsProfilesController
                                                                        .mProfileDeviceMap)
                                                        .get(localBluetoothProfile.toString()))
                                        .add(cachedBluetoothDevice2);
                            } else {
                                ArrayList arrayList2 = new ArrayList();
                                arrayList2.add(cachedBluetoothDevice2);
                                ((HashMap) bluetoothDetailsProfilesController.mProfileDeviceMap)
                                        .put(localBluetoothProfile.toString(), arrayList2);
                                arrayList.add(localBluetoothProfile);
                            }
                        }
                    }
                    BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                    int phonebookAccessPermission = bluetoothDevice.getPhonebookAccessPermission();
                    LocalBluetoothManager localBluetoothManager =
                            bluetoothDetailsProfilesController.mManager;
                    if (phonebookAccessPermission != 0) {
                        arrayList.add(localBluetoothManager.mProfileManager.mPbapProfile);
                    }
                    MapProfile mapProfile = localBluetoothManager.mProfileManager.mMapProfile;
                    if (bluetoothDevice.getMessageAccessPermission() != 0) {
                        arrayList.add(mapProfile);
                    }
                    LocalBluetoothProfileManager localBluetoothProfileManager =
                            localBluetoothManager.mProfileManager;
                    boolean contains =
                            arrayList.contains(localBluetoothProfileManager.mLeAudioProfile);
                    boolean z =
                            arrayList.contains(localBluetoothProfileManager.mA2dpProfile)
                                    || arrayList.contains(
                                            localBluetoothProfileManager.mHeadsetProfile);
                    if (contains && z) {
                        arrayList.remove(localBluetoothProfileManager.mA2dpProfile);
                        arrayList.remove(localBluetoothProfileManager.mHeadsetProfile);
                    }
                    Log.d(
                            "BtDetailsProfilesCtrl",
                            "getProfiles:Map:"
                                    + bluetoothDetailsProfilesController.mProfileDeviceMap);
                }
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    LocalBluetoothProfile localBluetoothProfile2 =
                            (LocalBluetoothProfile) it3.next();
                    if (localBluetoothProfile2 != null && localBluetoothProfile2.isProfileReady()) {
                        TwoStatePreference twoStatePreference =
                                (TwoStatePreference)
                                        bluetoothDetailsProfilesController.mProfilesContainer
                                                .findPreference(localBluetoothProfile2.toString());
                        if (twoStatePreference == null) {
                            SwitchPreferenceCompat switchPreferenceCompat =
                                    new SwitchPreferenceCompat(
                                            bluetoothDetailsProfilesController.mProfilesContainer
                                                    .getContext());
                            switchPreferenceCompat.setKey(localBluetoothProfile2.toString());
                            switchPreferenceCompat.setTitle(
                                    localBluetoothProfile2.getNameResource(
                                            cachedBluetoothDevice.mDevice));
                            switchPreferenceCompat.setOnPreferenceClickListener(
                                    bluetoothDetailsProfilesController);
                            switchPreferenceCompat.setOrder(localBluetoothProfile2.getOrdinal());
                            boolean z2 =
                                    SystemProperties.getBoolean(
                                            "ro.bluetooth.leaudio.le_audio_connection_by_default",
                                            true);
                            if ((localBluetoothProfile2 instanceof LeAudioProfile)
                                    && (!z2
                                            || !bluetoothDetailsProfilesController
                                                    .isModelNameInAllowList(
                                                            BluetoothUtils.getStringMetaData(
                                                                    cachedBluetoothDevice.mDevice,
                                                                    1)))) {
                                switchPreferenceCompat.setSummary(
                                        R.string.device_details_leaudio_toggle_summary);
                            }
                            bluetoothDetailsProfilesController.mProfilesContainer.addPreference(
                                    switchPreferenceCompat);
                            if (localBluetoothProfile2 instanceof A2dpProfile) {
                                BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
                                final A2dpProfile a2dpProfile =
                                        (A2dpProfile) localBluetoothProfile2;
                                if (a2dpProfile.mIsProfileReady
                                        && a2dpProfile.supportsHighQualityAudio(bluetoothDevice2)) {
                                    SwitchPreferenceCompat switchPreferenceCompat2 =
                                            new SwitchPreferenceCompat(
                                                    bluetoothDetailsProfilesController
                                                            .mProfilesContainer.getContext());
                                    switchPreferenceCompat2.setKey("A2dpProfileHighQualityAudio");
                                    switchPreferenceCompat2.setVisible(false);
                                    switchPreferenceCompat2.setOnPreferenceClickListener(
                                            new Preference
                                                    .OnPreferenceClickListener() { // from class:
                                                                                   // com.android.settings.bluetooth.BluetoothDetailsProfilesController$$ExternalSyntheticLambda2
                                                @Override // androidx.preference.Preference.OnPreferenceClickListener
                                                public final boolean onPreferenceClick(
                                                        Preference preference) {
                                                    BluetoothDetailsProfilesController
                                                            bluetoothDetailsProfilesController2 =
                                                                    BluetoothDetailsProfilesController
                                                                            .this;
                                                    bluetoothDetailsProfilesController2.getClass();
                                                    boolean isChecked =
                                                            ((TwoStatePreference) preference)
                                                                    .isChecked();
                                                    BluetoothDevice bluetoothDevice3 =
                                                            bluetoothDetailsProfilesController2
                                                                    .mCachedDevice
                                                                    .mDevice;
                                                    A2dpProfile a2dpProfile2 = a2dpProfile;
                                                    if (bluetoothDevice3 == null) {
                                                        bluetoothDevice3 =
                                                                a2dpProfile2.getActiveDevice();
                                                    }
                                                    if (bluetoothDevice3 == null) {
                                                        a2dpProfile2.getClass();
                                                        return true;
                                                    }
                                                    a2dpProfile2.mService.setOptionalCodecsEnabled(
                                                            bluetoothDevice3, isChecked ? 1 : 0);
                                                    if (a2dpProfile2.getConnectionStatus(
                                                                    bluetoothDevice3)
                                                            != 2) {
                                                        return true;
                                                    }
                                                    if (isChecked) {
                                                        a2dpProfile2.mService.enableOptionalCodecs(
                                                                bluetoothDevice3);
                                                        return true;
                                                    }
                                                    a2dpProfile2.mService.disableOptionalCodecs(
                                                            bluetoothDevice3);
                                                    return true;
                                                }
                                            });
                                    bluetoothDetailsProfilesController.mProfilesContainer
                                            .addPreference(switchPreferenceCompat2);
                                }
                            }
                            twoStatePreference = switchPreferenceCompat;
                        }
                        bluetoothDetailsProfilesController.refreshProfilePreference(
                                twoStatePreference, localBluetoothProfile2);
                    }
                }
                cachedBluetoothDevice.getClass();
                ArrayList arrayList3 = new ArrayList();
                synchronized (cachedBluetoothDevice.mProfileLock) {
                    arrayList3.addAll(cachedBluetoothDevice.mRemovedProfiles);
                }
                Iterator it4 = arrayList3.iterator();
                while (it4.hasNext()) {
                    TwoStatePreference twoStatePreference2 =
                            (TwoStatePreference)
                                    bluetoothDetailsProfilesController.mProfilesContainer
                                            .findPreference(
                                                    ((LocalBluetoothProfile) it4.next())
                                                            .toString());
                    if (twoStatePreference2 != null) {
                        bluetoothDetailsProfilesController.mProfilesContainer.removePreference(
                                twoStatePreference2);
                    }
                }
                if (bluetoothDetailsProfilesController.mProfilesContainer.findPreference(
                                "bottom_preference")
                        == null) {
                    Preference preference =
                            new Preference(
                                    ((BluetoothDetailsController)
                                                    bluetoothDetailsProfilesController)
                                            .mContext);
                    preference.setLayoutResource(R.layout.preference_bluetooth_profile_category);
                    preference.setEnabled(false);
                    preference.setKey("bottom_preference");
                    preference.setOrder(99);
                    preference.setSelectable(false);
                    bluetoothDetailsProfilesController.mProfilesContainer.addPreference(preference);
                }
                Set set = (Set) bluetoothDetailsProfilesController.mInvisiblePreferenceKey.get();
                if (set != null) {
                    for (int i2 = 0;
                            i2
                                    < bluetoothDetailsProfilesController.mProfilesContainer
                                            .getPreferenceCount();
                            i2++) {
                        Preference preference2 =
                                bluetoothDetailsProfilesController.mProfilesContainer.getPreference(
                                        i2);
                        preference2.setVisible(
                                preference2.isVisible() && !set.contains(preference2.getKey()));
                    }
                    return;
                }
                return;
            default:
                AtomicReference atomicReference =
                        bluetoothDetailsProfilesController.mInvisiblePreferenceKey;
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                BluetoothFeatureProviderImpl bluetoothFeatureProvider =
                        featureFactoryImpl.getBluetoothFeatureProvider();
                BluetoothDevice bluetoothDevice3 =
                        bluetoothDetailsProfilesController.mCachedDevice.mDevice;
                bluetoothFeatureProvider.getClass();
                atomicReference.set(ImmutableSet.of());
                ThreadUtils.postOnMainThread(
                        new BluetoothDetailsProfilesController$$ExternalSyntheticLambda4(
                                bluetoothDetailsProfilesController, 0));
                return;
        }
    }
}
