package com.android.settings.network.telephony;

import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;

import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CarrierConfigManagerExtKt {
    public static final PersistableBundle safeGetConfig(
            CarrierConfigManager carrierConfigManager, List keys, int i) {
        Intrinsics.checkNotNullParameter(carrierConfigManager, "<this>");
        Intrinsics.checkNotNullParameter(keys, "keys");
        try {
            String[] strArr = (String[]) keys.toArray(new String[0]);
            PersistableBundle configForSubId =
                    carrierConfigManager.getConfigForSubId(
                            i, (String[]) Arrays.copyOf(strArr, strArr.length));
            Intrinsics.checkNotNull(configForSubId);
            return configForSubId;
        } catch (IllegalStateException unused) {
            return new PersistableBundle(0);
        } catch (RuntimeException unused2) {
            return new PersistableBundle(0);
        }
    }
}
