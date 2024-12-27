package com.android.settings.applications.specialaccess;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.datausage.DataUsageUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000:\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\b\u0007\u0018\u0000"
                + " \u00122\u00020\u0001:\u0001\u0012B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010"
                + "\t\u001a\u00020\n"
                + "2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r"
                + "\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\n"
                + "2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0013"
        },
        d2 = {
            "Lcom/android/settings/applications/specialaccess/DataSaverController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "preference",
            "Landroidx/preference/Preference;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DataSaverController extends BasePreferenceController {
    public static final int $stable = 8;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion();
    private Preference preference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
        /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object getUnrestrictedSummary(
                android.content.Context r9,
                com.android.settingslib.spaprivileged.model.app.AppListRepository r10,
                kotlin.coroutines.Continuation r11) {
            /*
                r8 = this;
                boolean r0 = r11 instanceof com.android.settings.applications.specialaccess.DataSaverController$Companion$getUnrestrictedSummary$1
                if (r0 == 0) goto L13
                r0 = r11
                com.android.settings.applications.specialaccess.DataSaverController$Companion$getUnrestrictedSummary$1 r0 = (com.android.settings.applications.specialaccess.DataSaverController$Companion$getUnrestrictedSummary$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.settings.applications.specialaccess.DataSaverController$Companion$getUnrestrictedSummary$1 r0 = new com.android.settings.applications.specialaccess.DataSaverController$Companion$getUnrestrictedSummary$1
                r0.<init>(r8, r11)
            L18:
                java.lang.Object r8 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r11 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r0.label
                r2 = 1
                if (r1 == 0) goto L45
                if (r1 != r2) goto L3d
                int r9 = r0.I$1
                int r10 = r0.I$0
                java.lang.Object r11 = r0.L$3
                java.lang.String r11 = (java.lang.String) r11
                java.lang.Object r1 = r0.L$2
                kotlin.Pair[] r1 = (kotlin.Pair[]) r1
                java.lang.Object r2 = r0.L$1
                android.content.Context r2 = (android.content.Context) r2
                java.lang.Object r0 = r0.L$0
                kotlin.Pair[] r0 = (kotlin.Pair[]) r0
                kotlin.ResultKt.throwOnFailure(r8)
                r5 = r9
                r9 = r2
                goto L79
            L3d:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L45:
                kotlin.ResultKt.throwOnFailure(r8)
                kotlin.Pair[] r1 = new kotlin.Pair[r2]
                android.content.Context r8 = r9.getApplicationContext()
                java.lang.String r3 = "getApplicationContext(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, r3)
                r0.L$0 = r1
                r0.L$1 = r9
                r0.L$2 = r1
                java.lang.String r3 = "count"
                r0.L$3 = r3
                r4 = 2132020086(0x7f140b76, float:1.9678525E38)
                r0.I$0 = r4
                r5 = 0
                r0.I$1 = r5
                r0.label = r2
                kotlinx.coroutines.scheduling.DefaultIoScheduler r2 = kotlinx.coroutines.Dispatchers.IO
                com.android.settings.applications.specialaccess.DataSaverController$Companion$getAllowCount$2 r6 = new com.android.settings.applications.specialaccess.DataSaverController$Companion$getAllowCount$2
                r7 = 0
                r6.<init>(r8, r10, r7)
                java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r2, r6, r0)
                if (r8 != r11) goto L76
                return r11
            L76:
                r0 = r1
                r11 = r3
                r10 = r4
            L79:
                kotlin.Pair r2 = new kotlin.Pair
                r2.<init>(r11, r8)
                r1[r5] = r2
                java.lang.String r8 = com.android.settingslib.spa.framework.util.MessageFormatsKt.formatString(r9, r10, r0)
                return r8
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.applications.specialaccess.DataSaverController.Companion.getUnrestrictedSummary(android.content.Context,"
                        + " com.android.settingslib.spaprivileged.model.app.AppListRepository,"
                        + " kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataSaverController(Context context, String key) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = findPreference;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mContext.getResources().getBoolean(R.bool.config_show_data_saver)
                        && !Rune.SUPPORT_SMARTMANAGER_CN
                        && (DataUsageUtils.hasMobileData(this.mContext)
                                && DataUsageUtils.hasActiveSim(this.mContext)))
                ? 0
                : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
