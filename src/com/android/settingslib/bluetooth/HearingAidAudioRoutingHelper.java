package com.android.settingslib.bluetooth;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.audiopolicy.AudioProductStrategy;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HearingAidAudioRoutingHelper {
    public final AudioManager mAudioManager;

    public HearingAidAudioRoutingHelper(Context context) {
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
    }

    public List<AudioProductStrategy> getAudioProductStrategies() {
        return AudioManager.getAudioProductStrategies();
    }

    public final AudioDeviceAttributes getMatchedHearingDeviceAttributes(
            CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice != null && cachedBluetoothDevice.isHearingAidDevice()) {
            for (AudioDeviceInfo audioDeviceInfo : this.mAudioManager.getDevices(2)) {
                if (audioDeviceInfo.getType() == 23 || audioDeviceInfo.getType() == 26) {
                    final String address = audioDeviceInfo.getAddress();
                    CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
                    Set set = cachedBluetoothDevice.mMemberDevices;
                    if (cachedBluetoothDevice.mDevice.getAddress().equals(address)
                            || ((cachedBluetoothDevice2 != null
                                            && cachedBluetoothDevice2
                                                    .mDevice
                                                    .getAddress()
                                                    .equals(address))
                                    || (!set.isEmpty()
                                            && set.stream()
                                                    .anyMatch(
                                                            new Predicate() { // from class:
                                                                              // com.android.settingslib.bluetooth.HearingAidAudioRoutingHelper$$ExternalSyntheticLambda0
                                                                @Override // java.util.function.Predicate
                                                                public final boolean test(
                                                                        Object obj) {
                                                                    return ((CachedBluetoothDevice)
                                                                                    obj)
                                                                            .mDevice
                                                                            .getAddress()
                                                                            .equals(address);
                                                                }
                                                            })))) {
                        return new AudioDeviceAttributes(audioDeviceInfo);
                    }
                }
            }
        }
        return null;
    }

    public final List getSupportedStrategies(int[] iArr) {
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(new AudioAttributes.Builder().setUsage(i).build());
        }
        List<AudioProductStrategy> audioProductStrategies = getAudioProductStrategies();
        ArrayList arrayList2 = new ArrayList();
        for (AudioProductStrategy audioProductStrategy : audioProductStrategies) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (audioProductStrategy.supportsAudioAttributes((AudioAttributes) it.next())) {
                    arrayList2.add(audioProductStrategy);
                }
            }
        }
        return (List) arrayList2.stream().distinct().collect(Collectors.toList());
    }

    public final boolean removePreferredDeviceForStrategies(List list) {
        Iterator it = list.iterator();
        boolean z = true;
        while (it.hasNext()) {
            AudioProductStrategy audioProductStrategy = (AudioProductStrategy) it.next();
            if (this.mAudioManager.getPreferredDeviceForStrategy(audioProductStrategy) != null) {
                z &= this.mAudioManager.removePreferredDeviceForStrategy(audioProductStrategy);
            }
        }
        return z;
    }

    public final boolean setPreferredDeviceRoutingStrategies(
            List list, AudioDeviceAttributes audioDeviceAttributes, int i) {
        if (i == 0) {
            return removePreferredDeviceForStrategies(list);
        }
        boolean z = true;
        if (i == 1) {
            boolean removePreferredDeviceForStrategies = removePreferredDeviceForStrategies(list);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                z &=
                        this.mAudioManager.setPreferredDeviceForStrategy(
                                (AudioProductStrategy) it.next(), audioDeviceAttributes);
            }
            return removePreferredDeviceForStrategies & z;
        }
        if (i != 2) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unexpected routingValue: "));
        }
        boolean removePreferredDeviceForStrategies2 = removePreferredDeviceForStrategies(list);
        AudioDeviceAttributes audioDeviceAttributes2 =
                HearingAidAudioRoutingConstants.DEVICE_SPEAKER_OUT;
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            z &=
                    this.mAudioManager.setPreferredDeviceForStrategy(
                            (AudioProductStrategy) it2.next(), audioDeviceAttributes2);
        }
        return removePreferredDeviceForStrategies2 & z;
    }
}
