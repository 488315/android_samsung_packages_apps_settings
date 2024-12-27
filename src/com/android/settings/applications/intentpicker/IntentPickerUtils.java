package com.android.settings.applications.intentpicker;

import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.os.Build;
import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class IntentPickerUtils {
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;

    public static DomainVerificationUserState getDomainVerificationUserState(
            DomainVerificationManager domainVerificationManager, String str) {
        try {
            return domainVerificationManager.getDomainVerificationUserState(str);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("IntentPickerUtils", e.getMessage());
            return null;
        }
    }

    public static List getLinksList(
            DomainVerificationManager domainVerificationManager, String str, final int i) {
        DomainVerificationUserState domainVerificationUserState =
                getDomainVerificationUserState(domainVerificationManager, str);
        if (domainVerificationUserState == null) {
            return null;
        }
        return (List)
                domainVerificationUserState.getHostToStateMap().entrySet().stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.settings.applications.intentpicker.IntentPickerUtils$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((Integer) ((Map.Entry) obj).getValue()).intValue()
                                                == i;
                                    }
                                })
                        .map(new IntentPickerUtils$$ExternalSyntheticLambda1(0))
                        .collect(Collectors.toList());
    }

    public static void logd(String str) {
        if (DEBUG) {
            Log.d("IntentPickerUtils", str);
        }
    }
}
