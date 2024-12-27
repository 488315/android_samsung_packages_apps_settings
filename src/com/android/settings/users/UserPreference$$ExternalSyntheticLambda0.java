package com.android.settings.users;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserPreference$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        UserPreference userPreference = (UserPreference) obj;
        UserPreference userPreference2 = (UserPreference) obj2;
        UserPreference$$ExternalSyntheticLambda0 userPreference$$ExternalSyntheticLambda0 =
                UserPreference.SERIAL_NUMBER_COMPARATOR;
        if (userPreference == null) {
            return -1;
        }
        if (userPreference2 != null) {
            int serialNumber = userPreference.getSerialNumber();
            int serialNumber2 = userPreference2.getSerialNumber();
            if (serialNumber < serialNumber2) {
                return -1;
            }
            if (serialNumber <= serialNumber2) {
                return 0;
            }
        }
        return 1;
    }
}
