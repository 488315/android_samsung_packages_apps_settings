package com.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import kotlin.Metadata;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000V\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u001c\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0017\u0018\u0000"
                + " $2\u00020\u0001:\u0001$B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0011H\u0016J\u0010\u0010\u001d\u001a\u00020\u00132\u0006\u0010\f\u001a\u00020\r"
                + "H\u0016J\u001c\u0010\u001e\u001a\u00020\u00192\f\u0010\n"
                + "\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010"
                + " \u001a\u00020!H\u0016J\u000e\u0010\"\u001a\u00020\u0019H\u0082@¢\u0006\u0002\u0010#R\u0016\u0010\u0007\u001a\n"
                + " \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0014\u0010\n"
                + "\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\f\u001a\u00020\r"
                + "X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006%"
        },
        d2 = {
            "Lcom/android/settings/datausage/AppDataUsageAppSettingsController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "packageManager",
            "Landroid/content/pm/PackageManager;",
            "kotlin.jvm.PlatformType",
            "packageNames",
            ApnSettings.MVNO_NONE,
            "preference",
            "Landroidx/preference/Preference;",
            "resolvedIntent",
            "Landroid/content/Intent;",
            "userId",
            ApnSettings.MVNO_NONE,
            "visiable",
            ApnSettings.MVNO_NONE,
            "getVisiable",
            "()Z",
            "setVisiable",
            "(Z)V",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            "handlePreferenceTreeClick",
            "init",
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "update",
            "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public class AppDataUsageAppSettingsController extends BasePreferenceController {
    public static final int $stable = 8;
    private static final Companion Companion = new Companion();
    private static final Intent SettingsIntent;
    private static final String TAG = "AppDataUsageAppSettingsController";
    private final PackageManager packageManager;
    private Iterable<String> packageNames;
    private Preference preference;
    private Intent resolvedIntent;
    private int userId;
    private boolean visiable;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    static {
        Intent intent = new Intent("android.intent.action.MANAGE_NETWORK_USAGE");
        intent.addCategory("android.intent.category.DEFAULT");
        SettingsIntent = intent;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsageAppSettingsController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        this.packageNames = EmptyList.INSTANCE;
        this.userId = -1;
        this.packageManager = this.mContext.getPackageManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object update(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.settings.datausage.AppDataUsageAppSettingsController$update$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.settings.datausage.AppDataUsageAppSettingsController$update$1 r0 = (com.android.settings.datausage.AppDataUsageAppSettingsController$update$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.datausage.AppDataUsageAppSettingsController$update$1 r0 = new com.android.settings.datausage.AppDataUsageAppSettingsController$update$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L38
            if (r2 != r4) goto L30
            java.lang.Object r5 = r0.L$1
            com.android.settings.datausage.AppDataUsageAppSettingsController r5 = (com.android.settings.datausage.AppDataUsageAppSettingsController) r5
            java.lang.Object r0 = r0.L$0
            com.android.settings.datausage.AppDataUsageAppSettingsController r0 = (com.android.settings.datausage.AppDataUsageAppSettingsController) r0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L50
        L30:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L38:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.scheduling.DefaultScheduler r6 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.datausage.AppDataUsageAppSettingsController$update$2 r2 = new com.android.settings.datausage.AppDataUsageAppSettingsController$update$2
            r2.<init>(r5, r3)
            r0.L$0 = r5
            r0.L$1 = r5
            r0.label = r4
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r6, r2, r0)
            if (r6 != r1) goto L4f
            return r1
        L4f:
            r0 = r5
        L50:
            android.content.Intent r6 = (android.content.Intent) r6
            r5.resolvedIntent = r6
            androidx.preference.Preference r5 = r0.preference
            if (r5 == 0) goto L84
            android.content.Intent r6 = r0.resolvedIntent
            r1 = 0
            if (r6 == 0) goto L5f
            r6 = r4
            goto L60
        L5f:
            r6 = r1
        L60:
            r5.setVisible(r6)
            android.content.Intent r5 = r0.resolvedIntent
            if (r5 == 0) goto L68
            goto L69
        L68:
            r4 = r1
        L69:
            r0.visiable = r4
            java.lang.String r5 = com.android.settings.datausage.AppDataUsageAppSettingsController.TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r1 = "visiable : "
            r6.<init>(r1)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            com.samsung.android.util.SemLog.i(r5, r6)
            r0.isAvailable()
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L84:
            java.lang.String r5 = "preference"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.AppDataUsageAppSettingsController.update(kotlin.coroutines.Continuation):java.lang.Object");
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
        if (this.resolvedIntent != null) {
            return 0;
        }
        SemLog.i(TAG, "resolvedIntent is null");
        return 2;
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

    public final boolean getVisiable() {
        return this.visiable;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!Intrinsics.areEqual(preference.getKey(), this.mPreferenceKey)) {
            return false;
        }
        Intent intent = this.resolvedIntent;
        if (intent == null) {
            return true;
        }
        this.mContext.startActivityAsUser(intent, UserHandle.of(this.userId));
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public final void init(Iterable<String> packageNames, int userId) {
        Intrinsics.checkNotNullParameter(packageNames, "packageNames");
        this.packageNames = packageNames;
        this.userId = userId;
        SemLog.i(TAG, "package Name : " + packageNames);
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
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                null,
                null,
                new AppDataUsageAppSettingsController$onViewCreated$1(
                        viewLifecycleOwner, this, null),
                3);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public final void setVisiable(boolean z) {
        this.visiable = z;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
