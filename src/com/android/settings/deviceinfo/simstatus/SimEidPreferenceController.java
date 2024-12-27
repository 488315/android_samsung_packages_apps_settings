package com.android.settings.deviceinfo.simstatus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionInfo;

import androidx.lifecycle.LifecycleCoroutineScopeImpl;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.CustomDialogPreferenceCompat;
import com.android.settingslib.Utils;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000d\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010!\n"
                + "\u0002\b\u0002\b\u0007\u0018\u0000"
                + " %2\u00020\u0001:\u0001%B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0005H\u0002J\u0010\u0010\u0019\u001a\u00020\u00172\u0006\u0010\f\u001a\u00020\u001aH\u0016J\u001a\u0010\u001b\u001a\u00020\u00112\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\n"
                + "\u001a\u0004\u0018\u00010\u000bJ\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u000e\u0010\u001f\u001a\u00020\u0011H\u0082@¢\u0006\u0002\u0010"
                + " J\u000e\u0010!\u001a\u00020\u0011H\u0082@¢\u0006\u0002\u0010"
                + " J\u0016\u0010\"\u001a\u00020\u00112\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00050$H\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\n"
                + "\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\f\u001a\u00020\r"
                + "X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006&"
        },
        d2 = {
            "Lcom/android/settings/deviceinfo/simstatus/SimEidPreferenceController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "coroutineScope",
            "Lkotlinx/coroutines/CoroutineScope;",
            "eid",
            "eidStatus",
            "Lcom/android/settings/deviceinfo/simstatus/EidStatus;",
            "preference",
            "Lcom/android/settingslib/CustomDialogPreferenceCompat;",
            "slotSimStatus",
            "Lcom/android/settings/deviceinfo/simstatus/SlotSimStatus;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getIsAvailableAndUpdateEid",
            ApnSettings.MVNO_NONE,
            "getTitle",
            "handlePreferenceTreeClick",
            "Landroidx/preference/Preference;",
            "init",
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "update",
            "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "updateDialog",
            "updateNonIndexableKeys",
            GoodSettingsContract.EXTRA_NAME.PREFERENCE_KEYS,
            ApnSettings.MVNO_NONE,
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class SimEidPreferenceController extends BasePreferenceController {
    public static final int $stable = 8;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion();
    private static final int QR_CODE_SIZE = 600;
    private static final String TAG = "SimEidPreferenceController";
    private CoroutineScope coroutineScope;
    private String eid;
    private EidStatus eidStatus;
    private CustomDialogPreferenceCompat preference;
    private SlotSimStatus slotSimStatus;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimEidPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getIsAvailableAndUpdateEid() {
        String str;
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        if (!ContextsKt.getUserManager(mContext).isAdminUser() || Utils.isWifiOnly(this.mContext)) {
            return false;
        }
        EidStatus eidStatus = this.eidStatus;
        if (eidStatus != null) {
            eidStatus.mBlocker.awaitAdvance(0);
            str = (String) eidStatus.mEid.get();
        } else {
            str = null;
        }
        if (str == null) {
            str = ApnSettings.MVNO_NONE;
        }
        this.eid = str;
        return str.length() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getTitle() {
        SlotSimStatus slotSimStatus = this.slotSimStatus;
        int size = slotSimStatus != null ? slotSimStatus.size() : 0;
        if (size <= 1) {
            String string = this.mContext.getString(R.string.status_eid);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            return string;
        }
        for (int i = 0; i < size; i++) {
            SlotSimStatus slotSimStatus2 = this.slotSimStatus;
            SubscriptionInfo subscriptionInfo =
                    slotSimStatus2 != null ? slotSimStatus2.getSubscriptionInfo(i) : null;
            if (subscriptionInfo != null && subscriptionInfo.isEmbedded()) {
                String string2 =
                        this.mContext.getString(R.string.eid_multi_sim, Integer.valueOf(i + 1));
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                return string2;
            }
        }
        String string3 = this.mContext.getString(R.string.status_eid);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        return string3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object update(kotlin.coroutines.Continuation r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$update$1
            if (r0 == 0) goto L13
            r0 = r10
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$update$1 r0 = (com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$update$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$update$1 r0 = new com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$update$1
            r0.<init>(r9, r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            r4 = 0
            r5 = 3
            r6 = 2
            r7 = 1
            java.lang.String r8 = "preference"
            if (r2 == 0) goto L4a
            if (r2 == r7) goto L42
            if (r2 == r6) goto L3a
            if (r2 != r5) goto L32
            kotlin.ResultKt.throwOnFailure(r10)
            goto La1
        L32:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L3a:
            java.lang.Object r9 = r0.L$0
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController r9 = (com.android.settings.deviceinfo.simstatus.SimEidPreferenceController) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L80
        L42:
            java.lang.Object r9 = r0.L$0
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController r9 = (com.android.settings.deviceinfo.simstatus.SimEidPreferenceController) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L5f
        L4a:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlinx.coroutines.scheduling.DefaultScheduler r10 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$update$isAvailable$1 r2 = new com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$update$isAvailable$1
            r2.<init>(r9, r4)
            r0.L$0 = r9
            r0.label = r7
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r10, r2, r0)
            if (r10 != r1) goto L5f
            return r1
        L5f:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            com.android.settingslib.CustomDialogPreferenceCompat r2 = r9.preference
            if (r2 == 0) goto Lb1
            r2.setVisible(r10)
            if (r10 == 0) goto Lb0
            kotlinx.coroutines.scheduling.DefaultScheduler r10 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$update$title$1 r2 = new com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$update$title$1
            r2.<init>(r9, r4)
            r0.L$0 = r9
            r0.label = r6
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r10, r2, r0)
            if (r10 != r1) goto L80
            return r1
        L80:
            java.lang.String r10 = (java.lang.String) r10
            com.android.settingslib.CustomDialogPreferenceCompat r2 = r9.preference
            if (r2 == 0) goto Lac
            r2.setTitle(r10)
            com.android.settingslib.CustomDialogPreferenceCompat r2 = r9.preference
            if (r2 == 0) goto La8
            r2.mDialogTitle = r10
            java.lang.String r10 = r9.eid
            if (r10 == 0) goto La2
            r2.setSummary(r10)
            r0.L$0 = r4
            r0.label = r5
            java.lang.Object r9 = r9.updateDialog(r0)
            if (r9 != r1) goto La1
            return r1
        La1:
            return r3
        La2:
            java.lang.String r9 = "eid"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r9)
            throw r4
        La8:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r8)
            throw r4
        Lac:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r8)
            throw r4
        Lb0:
            return r3
        Lb1:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r8)
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.deviceinfo.simstatus.SimEidPreferenceController.update(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateDialog(kotlin.coroutines.Continuation r11) {
        /*
            r10 = this;
            boolean r0 = r11 instanceof com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$updateDialog$1
            if (r0 == 0) goto L13
            r0 = r11
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$updateDialog$1 r0 = (com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$updateDialog$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$updateDialog$1 r0 = new com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$updateDialog$1
            r0.<init>(r10, r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            r4 = 1
            if (r2 == 0) goto L35
            if (r2 != r4) goto L2d
            java.lang.Object r10 = r0.L$0
            android.widget.ImageView r10 = (android.widget.ImageView) r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L98
        L2d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L35:
            kotlin.ResultKt.throwOnFailure(r11)
            com.android.settingslib.CustomDialogPreferenceCompat r11 = r10.preference
            r2 = 0
            if (r11 == 0) goto La6
            android.app.Dialog r11 = r11.getDialog()
            if (r11 != 0) goto L44
            return r3
        L44:
            android.view.Window r5 = r11.getWindow()
            if (r5 == 0) goto L4f
            r6 = 8192(0x2000, float:1.14794E-41)
            r5.setFlags(r6, r6)
        L4f:
            r5 = 0
            r11.setCanceledOnTouchOutside(r5)
            r5 = 2131363288(0x7f0a05d8, float:1.834638E38)
            android.view.View r5 = r11.requireViewById(r5)
            java.lang.String r6 = "requireViewById(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            android.widget.TextView r5 = (android.widget.TextView) r5
            java.lang.String r7 = r10.eid
            java.lang.String r8 = "eid"
            if (r7 == 0) goto La2
            java.lang.CharSequence r7 = com.android.settings.deviceinfo.PhoneNumberUtil.expandByTts(r7)
            r5.setText(r7)
            r5 = 2131363287(0x7f0a05d7, float:1.8346379E38)
            android.view.View r11 = r11.requireViewById(r5)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, r6)
            android.widget.ImageView r11 = (android.widget.ImageView) r11
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$Companion r5 = com.android.settings.deviceinfo.simstatus.SimEidPreferenceController.INSTANCE
            java.lang.String r10 = r10.eid
            if (r10 == 0) goto L9e
            r0.L$0 = r11
            r0.label = r4
            r5.getClass()
            kotlinx.coroutines.scheduling.DefaultScheduler r4 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$Companion$getEidQrCode$2 r5 = new com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$Companion$getEidQrCode$2
            r5.<init>(r10, r2)
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r10 != r1) goto L95
            return r1
        L95:
            r9 = r11
            r11 = r10
            r10 = r9
        L98:
            android.graphics.Bitmap r11 = (android.graphics.Bitmap) r11
            r10.setImageBitmap(r11)
            return r3
        L9e:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r8)
            throw r2
        La2:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r8)
            throw r2
        La6:
            java.lang.String r10 = "preference"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r10)
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.deviceinfo.simstatus.SimEidPreferenceController.updateDialog(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = (CustomDialogPreferenceCompat) findPreference;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SubscriptionUtil.isSimHardwareVisible(this.mContext) ? 0 : 3;
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
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!Intrinsics.areEqual(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        CustomDialogPreferenceCompat customDialogPreferenceCompat = this.preference;
        if (customDialogPreferenceCompat != null) {
            customDialogPreferenceCompat.mOnShowListener =
                    new DialogInterface.OnShowListener() { // from class:
                        // com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$handlePreferenceTreeClick$1

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                d1 = {
                                    "\u0000\n\n"
                                        + "\u0000\n"
                                        + "\u0002\u0010\u0002\n"
                                        + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
                                },
                                d2 = {
                                    "<anonymous>",
                                    ApnSettings.MVNO_NONE,
                                    "Lkotlinx/coroutines/CoroutineScope;"
                                },
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.deviceinfo.simstatus.SimEidPreferenceController$handlePreferenceTreeClick$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            int label;
                            final /* synthetic */ SimEidPreferenceController this$0;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(
                                    SimEidPreferenceController simEidPreferenceController,
                                    Continuation continuation) {
                                super(2, continuation);
                                this.this$0 = simEidPreferenceController;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(
                                    Object obj, Continuation continuation) {
                                return new AnonymousClass1(this.this$0, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1)
                                                create((CoroutineScope) obj, (Continuation) obj2))
                                        .invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                Object updateDialog;
                                CoroutineSingletons coroutineSingletons =
                                        CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    SimEidPreferenceController simEidPreferenceController =
                                            this.this$0;
                                    this.label = 1;
                                    updateDialog = simEidPreferenceController.updateDialog(this);
                                    if (updateDialog == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException(
                                                "call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            CoroutineScope coroutineScope;
                            coroutineScope = SimEidPreferenceController.this.coroutineScope;
                            if (coroutineScope != null) {
                                BuildersKt.launch$default(
                                        coroutineScope,
                                        null,
                                        null,
                                        new AnonymousClass1(SimEidPreferenceController.this, null),
                                        3);
                            }
                        }
                    };
            return true;
        }
        Intrinsics.throwUninitializedPropertyAccessException("preference");
        throw null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public final void init(SlotSimStatus slotSimStatus, EidStatus eidStatus) {
        this.slotSimStatus = slotSimStatus;
        this.eidStatus = eidStatus;
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
        LifecycleCoroutineScopeImpl lifecycleScope =
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner);
        this.coroutineScope = lifecycleScope;
        BuildersKt.launch$default(
                lifecycleScope,
                null,
                null,
                new SimEidPreferenceController$onViewCreated$1(viewLifecycleOwner, this, null),
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

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        if (isAvailable() && getIsAvailableAndUpdateEid()) {
            return;
        }
        keys.add(getPreferenceKey());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
