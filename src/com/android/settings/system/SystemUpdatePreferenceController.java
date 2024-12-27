package com.android.settings.system;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.UserManager;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.network.telephony.CarrierConfigManagerExtKt;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
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
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\b\u0017\u0018\u0000"
                + " \u001d2\u00020\u0001:\u0001\u001dB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000f\u001a\u00020\u0005H\u0082@¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0005H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010"
                + "\t\u001a\u00020\n"
                + "H\u0016J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\n"
                + "X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\r"
                + "\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\u001e"
        },
        d2 = {
            "Lcom/android/settings/system/SystemUpdatePreferenceController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "clientInitiatedActionRepository",
            "Lcom/android/settings/system/ClientInitiatedActionRepository;",
            "preference",
            "Landroidx/preference/Preference;",
            "systemUpdateRepository",
            "Lcom/android/settings/system/SystemUpdateRepository;",
            "userManager",
            "Landroid/os/UserManager;",
            "calculateSummary",
            "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getReleaseVersionSummary",
            "handlePreferenceTreeClick",
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
public class SystemUpdatePreferenceController extends BasePreferenceController {
    public static final int $stable = 8;
    private static final String TAG = "SysUpdatePrefContr";
    private final ClientInitiatedActionRepository clientInitiatedActionRepository;
    private Preference preference;
    private final SystemUpdateRepository systemUpdateRepository;
    private final UserManager userManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SystemUpdatePreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        this.userManager = ContextsKt.getUserManager(context);
        this.systemUpdateRepository = new SystemUpdateRepository(context);
        this.clientInitiatedActionRepository = new ClientInitiatedActionRepository(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object calculateSummary(kotlin.coroutines.Continuation r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.android.settings.system.SystemUpdatePreferenceController$calculateSummary$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.settings.system.SystemUpdatePreferenceController$calculateSummary$1 r0 = (com.android.settings.system.SystemUpdatePreferenceController$calculateSummary$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.system.SystemUpdatePreferenceController$calculateSummary$1 r0 = new com.android.settings.system.SystemUpdatePreferenceController$calculateSummary$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r6 = r0.L$0
            com.android.settings.system.SystemUpdatePreferenceController r6 = (com.android.settings.system.SystemUpdatePreferenceController) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L50
        L2b:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            android.content.Context r7 = r6.mContext
            java.lang.String r2 = "mContext"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r2)
            r0.L$0 = r6
            r0.label = r3
            kotlinx.coroutines.scheduling.DefaultScheduler r2 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.system.SystemUpdateManagerExtKt$getSystemUpdateInfo$2 r4 = new com.android.settings.system.SystemUpdateManagerExtKt$getSystemUpdateInfo$2
            r5 = 0
            r4.<init>(r7, r5)
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r2, r4, r0)
            if (r7 != r1) goto L50
            return r1
        L50:
            android.os.Bundle r7 = (android.os.Bundle) r7
            if (r7 != 0) goto L59
            java.lang.String r6 = r6.getReleaseVersionSummary()
            return r6
        L59:
            java.lang.String r0 = "status"
            int r0 = r7.getInt(r0)
            if (r0 != 0) goto L68
            java.lang.String r1 = "SysUpdatePrefContr"
            java.lang.String r2 = "Update statue unknown"
            android.util.Log.d(r1, r2)
        L68:
            java.lang.String r1 = "getString(...)"
            if (r0 == 0) goto L88
            if (r0 == r3) goto L88
            r7 = 2
            if (r0 == r7) goto L7b
            r7 = 3
            if (r0 == r7) goto L7b
            r7 = 4
            if (r0 == r7) goto L7b
            r7 = 5
            if (r0 == r7) goto L7b
            goto La8
        L7b:
            android.content.Context r6 = r6.mContext
            r7 = 2132017948(0x7f14031c, float:1.9674189E38)
            java.lang.String r6 = r6.getString(r7)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r1)
            return r6
        L88:
            java.lang.String r0 = "title"
            java.lang.String r7 = r7.getString(r0)
            if (r7 == 0) goto La8
            int r0 = r7.length()
            if (r0 != 0) goto L97
            goto La8
        L97:
            android.content.Context r6 = r6.mContext
            r0 = 2132017949(0x7f14031d, float:1.967419E38)
            java.lang.Object[] r7 = new java.lang.Object[]{r7}
            java.lang.String r6 = r6.getString(r0, r7)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r1)
            return r6
        La8:
            java.lang.String r6 = r6.getReleaseVersionSummary()
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.system.SystemUpdatePreferenceController.calculateSummary(kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final String getReleaseVersionSummary() {
        String string =
                this.mContext.getString(
                        R.string.android_version_summary, Build.VERSION.RELEASE_OR_PREVIEW_DISPLAY);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        ActivityInfo activityInfo;
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = findPreference;
        if (isAvailable()) {
            SystemUpdateRepository systemUpdateRepository = this.systemUpdateRepository;
            systemUpdateRepository.getClass();
            ResolveInfo resolveActivity =
                    systemUpdateRepository.packageManager.resolveActivity(
                            new Intent("android.settings.SYSTEM_UPDATE_SETTINGS"), 1048576);
            Intent className =
                    (resolveActivity == null
                                    || (activityInfo = resolveActivity.activityInfo) == null)
                            ? null
                            : new Intent()
                                    .setClassName(activityInfo.packageName, activityInfo.name);
            if (className != null) {
                Preference preference = this.preference;
                if (preference != null) {
                    preference.setIntent(className);
                    return;
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("preference");
                    throw null;
                }
            }
            Preference preference2 = this.preference;
            if (preference2 != null) {
                screen.removePreference(preference2);
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("preference");
                throw null;
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mContext.getResources().getBoolean(R.bool.config_show_system_update_settings)
                        && this.userManager.isAdminUser())
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        String string;
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!Intrinsics.areEqual(getPreferenceKey(), preference.getKey())) {
            return false;
        }
        ClientInitiatedActionRepository clientInitiatedActionRepository =
                this.clientInitiatedActionRepository;
        PersistableBundle safeGetConfig =
                CarrierConfigManagerExtKt.safeGetConfig(
                        clientInitiatedActionRepository.configManager,
                        CollectionsKt__CollectionsKt.listOf(
                                (Object[])
                                        new String[] {
                                            "ci_action_on_sys_update_bool",
                                            "ci_action_on_sys_update_intent_string",
                                            "ci_action_on_sys_update_extra_string",
                                            "ci_action_on_sys_update_extra_val_string"
                                        }),
                        SubscriptionManager.getDefaultSubscriptionId());
        if (!safeGetConfig.getBoolean("ci_action_on_sys_update_bool")
                || (string = safeGetConfig.getString("ci_action_on_sys_update_intent_string"))
                        == null
                || string.length() == 0) {
            return false;
        }
        String string2 = safeGetConfig.getString("ci_action_on_sys_update_extra_string");
        String string3 = safeGetConfig.getString("ci_action_on_sys_update_extra_val_string");
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "onSystemUpdate: broadcasting intent ",
                        string,
                        " with extra ",
                        string2,
                        ", ");
        m.append(string3);
        Log.d("ClientInitiatedAction", m.toString());
        Intent intent = new Intent(string);
        if (string2 != null && string2.length() != 0) {
            intent.putExtra(string2, string3);
        }
        intent.addFlags(16777216);
        clientInitiatedActionRepository.context.getApplicationContext().sendBroadcast(intent);
        return false;
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
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                null,
                null,
                new SystemUpdatePreferenceController$onViewCreated$1(
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
