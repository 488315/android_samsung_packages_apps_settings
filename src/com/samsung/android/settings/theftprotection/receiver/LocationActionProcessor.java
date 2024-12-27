package com.samsung.android.settings.theftprotection.receiver;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;
import com.samsung.android.settings.theftprotection.location.LocationData;
import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.logging.Log;
import com.sec.ims.gls.GlsIntent;

import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LocationActionProcessor implements ProcessAction {
    @Override // com.samsung.android.settings.theftprotection.receiver.ProcessAction
    public final void handle(Context context, Intent intent) {
        Log.d(
                "LocationActionProcessor",
                "ACTION_GEOFENCE_STATE_CHANGED: "
                        + TheftProtectionUtils.isMandatoryBiometricEnabled(context));
        final int intExtra = intent.getIntExtra("transition", -1);
        final String stringExtra =
                intent.getStringExtra(KnoxContainerManager.CONTAINER_CREATION_REQUEST_ID);
        Bundle extras = intent.getExtras();
        final boolean z = false;
        if (extras != null) {
            Location location =
                    (Location)
                            extras.getParcelable(GlsIntent.Extras.EXTRA_LOCATION, Location.class);
            r3 = location != null ? location.getAccuracy() : 0.0f;
            if (location != null && location.isMock()) {
                z = true;
            }
        }
        Log.d(
                "LocationActionProcessor",
                "GEOFENCE_STATE_CHANGED: direction=" + intExtra + ", key=" + stringExtra);
        Log.d("LocationActionProcessor", "GEOFENCE_STATE_CHANGED: info : " + r3 + ", " + z);
        if (intExtra >= 0) {
            LocationStorage locationStorage = LocationStorage.InstanceHolder.INSTANCE;
            locationStorage.loadLocationData().stream()
                    .filter(
                            new Predicate() { // from class:
                                              // com.samsung.android.settings.theftprotection.receiver.LocationActionProcessor$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return TextUtils.equals(((LocationData) obj).mKey, stringExtra);
                                }
                            })
                    .findFirst()
                    .ifPresent(
                            new Consumer() { // from class:
                                             // com.samsung.android.settings.theftprotection.receiver.LocationActionProcessor$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    boolean z2 = z;
                                    int i = intExtra;
                                    LocationData locationData = (LocationData) obj;
                                    if (z2 || 1 != i) {
                                        locationData.mEnterLocation = 0;
                                    } else {
                                        locationData.mEnterLocation = 1;
                                    }
                                    LocationStorage.InstanceHolder.INSTANCE.updateLocationData(
                                            locationData);
                                }
                            });
            boolean anyMatch =
                    locationStorage.loadLocationData().stream()
                            .anyMatch(new LocationActionProcessor$$ExternalSyntheticLambda2());
            TheftProtectionUtils.updateInSignificantPlace(context, anyMatch);
            Log.d("LocationActionProcessor", "GEOFENCE_STATE_CHANGED: inTrustedPlace: " + anyMatch);
        }
    }
}
