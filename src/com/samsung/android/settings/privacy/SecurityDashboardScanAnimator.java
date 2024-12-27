package com.samsung.android.settings.privacy;

import android.os.Handler;

import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.widget.SecurityDashboardStatusPreference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecurityDashboardScanAnimator {
    public Handler mHandler;
    public boolean mPerformScanVi;
    public Map mScanViMenuControllers;
    public SecurityDashboardStatusPreference mStatusPreference;

    public static AbstractPreferenceController findController(final String str, List list) {
        return (AbstractPreferenceController)
                list.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.privacy.SecurityDashboardScanAnimator$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((AbstractPreferenceController) obj)
                                                .getPreferenceKey()
                                                .equals(str);
                                    }
                                })
                        .findFirst()
                        .orElse(null);
    }

    public final void refreshMenuItemsStatusWithVi(final int i, final List list) {
        ArrayList arrayList = (ArrayList) list;
        if (((SecurityDashboardPreferenceController)
                        ((LinkedHashMap) this.mScanViMenuControllers).get(arrayList.get(i)))
                .isShown()) {
            ((SecurityDashboardPreferenceController)
                            ((LinkedHashMap) this.mScanViMenuControllers).get(arrayList.get(i)))
                    .updateStateAfterVi();
        }
        int size = arrayList.size() - 1;
        SecurityDashboardStatusPreference securityDashboardStatusPreference =
                this.mStatusPreference;
        if (i >= size) {
            securityDashboardStatusPreference.setScanning(false, true);
            return;
        }
        int i2 = i + 1;
        if (!((SecurityDashboardPreferenceController)
                        ((LinkedHashMap) this.mScanViMenuControllers).get(arrayList.get(i2)))
                .isShown()) {
            refreshMenuItemsStatusWithVi(i2, list);
            return;
        }
        SecurityDashboardPreferenceController securityDashboardPreferenceController =
                (SecurityDashboardPreferenceController)
                        ((LinkedHashMap) this.mScanViMenuControllers)
                                .get((String) arrayList.get(i2));
        securityDashboardPreferenceController.setStatus(SecurityDashboardConstants$Status.SCANNING);
        securityDashboardStatusPreference.updateScanCategoryIcon(
                securityDashboardPreferenceController.getScanCategoryIcon());
        this.mHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.privacy.SecurityDashboardScanAnimator$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecurityDashboardScanAnimator.this.refreshMenuItemsStatusWithVi(
                                i + 1, list);
                    }
                },
                500L);
    }
}
