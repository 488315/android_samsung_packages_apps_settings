package com.samsung.android.settings.theftprotection;

import androidx.preference.CheckBoxPreference;

import com.samsung.android.settings.theftprotection.location.LocationData;
import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.logging.Log;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda8 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) obj;
        int i = MandatoryBiometricLocationDeleteFragment.$r8$clinit;
        if (checkBoxPreference.mChecked) {
            LocationStorage locationStorage = LocationStorage.InstanceHolder.INSTANCE;
            LocationData loadLocationData =
                    locationStorage.loadLocationData(checkBoxPreference.getKey());
            synchronized (locationStorage) {
                if (loadLocationData == null) {
                    return;
                }
                Log.d("LocationStorage", "removeLocationData : " + loadLocationData.mKey);
                ((ArrayList) locationStorage.mLocationList).remove(loadLocationData);
                locationStorage.mHandler.sendMessage(locationStorage.mHandler.obtainMessage(1));
            }
        }
    }
}
