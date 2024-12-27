package com.android.settings.security.screenlock;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.OwnerInfoPreferenceController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenLockSettings extends DashboardFragment
        implements OwnerInfoPreferenceController.OwnerInfoCallback {
    public static final int MY_USER_ID = UserHandle.myUserId();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.screen_lock_settings);
    public LockPatternUtils mLockPatternUtils;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.security.screenlock.ScreenLockSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ScreenLockSettings.buildPreferenceControllers(
                    context, null, new LockPatternUtils(context));
        }
    }

    public static List buildPreferenceControllers(
            Context context,
            DashboardFragment dashboardFragment,
            LockPatternUtils lockPatternUtils) {
        ArrayList arrayList = new ArrayList();
        int i = MY_USER_ID;
        arrayList.add(new PatternVisiblePreferenceController(i, context, lockPatternUtils));
        arrayList.add(new PinPrivacyPreferenceController(i, context, lockPatternUtils));
        arrayList.add(new PowerButtonInstantLockPreferenceController(i, context, lockPatternUtils));
        arrayList.add(new LockAfterTimeoutPreferenceController(i, context, lockPatternUtils));
        arrayList.add(
                new AutoPinConfirmPreferenceController(
                        context, i, lockPatternUtils, dashboardFragment));
        arrayList.add(new OwnerInfoPreferenceController(context, dashboardFragment));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
        this.mLockPatternUtils = lockPatternUtils;
        return buildPreferenceControllers(context, this, lockPatternUtils);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ScreenLockSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1265;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.screen_lock_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i == 111) {
            if (i2 == -1) {
                LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                int i3 = MY_USER_ID;
                lockPatternUtils.setAutoPinConfirm(true, i3);
                if (this.mLockPatternUtils.refreshStoredPinLength(i3)) {
                    return;
                }
                this.mLockPatternUtils.setAutoPinConfirm(false, i3);
                return;
            }
            return;
        }
        if (i == 112 && i2 == -1) {
            LockPatternUtils lockPatternUtils2 = this.mLockPatternUtils;
            int i4 = MY_USER_ID;
            lockPatternUtils2.setAutoPinConfirm(false, i4);
            if (this.mLockPatternUtils.refreshStoredPinLength(i4)) {
                return;
            }
            this.mLockPatternUtils.setAutoPinConfirm(true, i4);
        }
    }

    @Override // com.android.settings.security.OwnerInfoPreferenceController.OwnerInfoCallback
    public final void onOwnerInfoUpdated() {
        ((OwnerInfoPreferenceController) use(OwnerInfoPreferenceController.class)).updateSummary();
    }
}
