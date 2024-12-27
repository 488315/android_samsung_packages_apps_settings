package com.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.datausage.lib.AppPreferenceRepository;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000L\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0017\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u000eH\u0016J\u0014\u0010\u001a\u001a\u00020\u00162\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r"
                + "J\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u000e\u0010\u001e\u001a\u00020\u0016H\u0082@¢\u0006\u0002\u0010\u001fR\u000e\u0010"
                + "\t\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\n"
                + "\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r"
                + "X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006 "
        },
        d2 = {
            "Lcom/android/settings/datausage/AppDataUsageListController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "repository",
            "Lcom/android/settings/datausage/lib/AppPreferenceRepository;",
            "(Landroid/content/Context;Ljava/lang/String;Lcom/android/settings/datausage/lib/AppPreferenceRepository;)V",
            "TAG",
            "preference",
            "Landroidx/preference/PreferenceGroup;",
            "uids",
            ApnSettings.MVNO_NONE,
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
            "init",
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "updateList",
            "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public class AppDataUsageListController extends BasePreferenceController {
    public static final int $stable = 8;
    private final String TAG;
    private PreferenceGroup preference;
    private final AppPreferenceRepository repository;
    private List<Integer> uids;
    private boolean visiable;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AppDataUsageListController(Context context, String preferenceKey) {
        this(context, preferenceKey, null, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateList(kotlin.coroutines.Continuation r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof com.android.settings.datausage.AppDataUsageListController$updateList$1
            if (r0 == 0) goto L13
            r0 = r10
            com.android.settings.datausage.AppDataUsageListController$updateList$1 r0 = (com.android.settings.datausage.AppDataUsageListController$updateList$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.datausage.AppDataUsageListController$updateList$1 r0 = new com.android.settings.datausage.AppDataUsageListController$updateList$1
            r0.<init>(r9, r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            r4 = 0
            java.lang.String r5 = "preference"
            r6 = 1
            if (r2 == 0) goto L38
            if (r2 != r6) goto L30
            java.lang.Object r9 = r0.L$0
            com.android.settings.datausage.AppDataUsageListController r9 = (com.android.settings.datausage.AppDataUsageListController) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L86
        L30:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L38:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.String r10 = r9.TAG
            java.util.List<java.lang.Integer> r2 = r9.uids
            int r2 = r2.size()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "uid size : "
            r7.<init>(r8)
            r7.append(r2)
            java.lang.String r2 = r7.toString()
            com.samsung.android.util.SemLog.i(r10, r2)
            java.util.List<java.lang.Integer> r10 = r9.uids
            int r10 = r10.size()
            if (r10 > r6) goto L6b
            androidx.preference.PreferenceGroup r10 = r9.preference
            if (r10 == 0) goto L67
            r0 = 0
            r10.setVisible(r0)
            r9.visiable = r0
            return r3
        L67:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
            throw r4
        L6b:
            r9.visiable = r6
            androidx.preference.PreferenceGroup r10 = r9.preference
            if (r10 == 0) goto Lb0
            r10.setVisible(r6)
            kotlinx.coroutines.scheduling.DefaultScheduler r10 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.datausage.AppDataUsageListController$updateList$appPreferences$1 r2 = new com.android.settings.datausage.AppDataUsageListController$updateList$appPreferences$1
            r2.<init>(r9, r4)
            r0.L$0 = r9
            r0.label = r6
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r10, r2, r0)
            if (r10 != r1) goto L86
            return r1
        L86:
            java.util.List r10 = (java.util.List) r10
            androidx.preference.PreferenceGroup r0 = r9.preference
            if (r0 == 0) goto Lac
            r0.removeAll()
            java.util.Iterator r10 = r10.iterator()
        L93:
            boolean r0 = r10.hasNext()
            if (r0 == 0) goto Lab
            java.lang.Object r0 = r10.next()
            androidx.preference.Preference r0 = (androidx.preference.Preference) r0
            androidx.preference.PreferenceGroup r1 = r9.preference
            if (r1 == 0) goto La7
            r1.addPreference(r0)
            goto L93
        La7:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
            throw r4
        Lab:
            return r3
        Lac:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
            throw r4
        Lb0:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.AppDataUsageListController.updateList(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = (PreferenceGroup) findPreference;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public final void init(List<Integer> uids) {
        Intrinsics.checkNotNullParameter(uids, "uids");
        this.uids = uids;
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
                new AppDataUsageListController$onViewCreated$1(viewLifecycleOwner, this, null),
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

    public /* synthetic */ AppDataUsageListController(
            Context context,
            String str,
            AppPreferenceRepository appPreferenceRepository,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(
                context,
                str,
                (i & 4) != 0 ? new AppPreferenceRepository(context) : appPreferenceRepository);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsageListController(
            Context context, String preferenceKey, AppPreferenceRepository repository) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        Intrinsics.checkNotNullParameter(repository, "repository");
        this.repository = repository;
        this.uids = EmptyList.INSTANCE;
        this.TAG = "AppDataUsageListController";
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
