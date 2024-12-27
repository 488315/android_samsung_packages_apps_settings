package com.samsung.android.settings.theftprotection.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.location.SemGeopointGeofence;
import com.samsung.android.location.SemLocationManager;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;
import com.samsung.android.settings.theftprotection.logging.Log;
import com.samsung.android.settings.theftprotection.receiver.ProtectionReceiver;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LocationManager$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Context f$0;

    public /* synthetic */ LocationManager$$ExternalSyntheticLambda0(Context context, int i) {
        this.$r8$classId = i;
        this.f$0 = context;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Context context = this.f$0;
        LocationData locationData = (LocationData) obj;
        switch (i) {
            case 0:
                locationData.mEnterLocation = 0;
                LocationStorage.InstanceHolder.INSTANCE.updateLocationData(locationData);
                String str = locationData.mKey;
                if (!TextUtils.isEmpty(str)) {
                    Log.d(
                            "LocationManager",
                            "unregisterGeofence() removeGeofence: key="
                                    + str
                                    + ", result="
                                    + ((SemLocationManager)
                                                    context.getSystemService("sec_location"))
                                            .removeGeofence(str));
                    break;
                } else {
                    Log.w("LocationManager", "unregisterGeofence() key is empty");
                    break;
                }
            default:
                if (!TextUtils.isEmpty(locationData.mKey)) {
                    SemLocationManager semLocationManager =
                            (SemLocationManager) context.getSystemService("sec_location");
                    TheftProtectionUtils.updateInSignificantPlace(context, false);
                    String str2 = locationData.mKey;
                    if (TextUtils.isEmpty(str2)) {
                        Log.w("LocationManager", "unregisterGeofence() key is empty");
                    } else {
                        Log.d(
                                "LocationManager",
                                "unregisterGeofence() removeGeofence: key="
                                        + str2
                                        + ", result="
                                        + ((SemLocationManager)
                                                        context.getSystemService("sec_location"))
                                                .removeGeofence(str2));
                    }
                    SemGeopointGeofence semGeopointGeofence =
                            new SemGeopointGeofence(
                                    locationData.mLatitude,
                                    locationData.mLongitude,
                                    locationData.mRadius,
                                    locationData.mKey);
                    if (!TextUtils.isEmpty(locationData.mBSSID)) {
                        semGeopointGeofence.setWifiBssids(new String[] {locationData.mBSSID});
                    }
                    Intent intent =
                            new Intent(
                                    "com.samsung.android.settings.action.GEOFENCE_STATE_CHANGED");
                    intent.putExtra(
                            KnoxContainerManager.CONTAINER_CREATION_REQUEST_ID, locationData.mKey);
                    intent.setClass(context, ProtectionReceiver.class);
                    Log.d(
                            "LocationManager",
                            "registerGeofence() addGeofence: key="
                                    + locationData.mKey
                                    + ", result="
                                    + semLocationManager.addGeofence(
                                            semGeopointGeofence,
                                            PendingIntent.getBroadcast(
                                                    context,
                                                    locationData.mType,
                                                    intent,
                                                    167772160)));
                    break;
                } else {
                    Log.w("LocationManager", "registerGeofence() key is empty");
                    break;
                }
        }
    }
}
