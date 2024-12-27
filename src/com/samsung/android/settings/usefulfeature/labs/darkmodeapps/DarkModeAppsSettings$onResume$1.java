package com.samsung.android.settings.usefulfeature.labs.darkmodeapps;

import android.app.UiModeManager;
import android.os.UserHandle;
import android.view.ViewGroup;

import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.widget.SeslAppPickerView;

import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
final class DarkModeAppsSettings$onResume$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DarkModeAppsSettings this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DarkModeAppsSettings$onResume$1(
            DarkModeAppsSettings darkModeAppsSettings, Continuation continuation) {
        super(2, continuation);
        this.this$0 = darkModeAppsSettings;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DarkModeAppsSettings$onResume$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        DarkModeAppsSettings$onResume$1 darkModeAppsSettings$onResume$1 =
                (DarkModeAppsSettings$onResume$1) create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        darkModeAppsSettings$onResume$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ViewGroup viewGroup = this.this$0.mProgressBar;
        if (viewGroup != null) {
            viewGroup.setVisibility(0);
        }
        SeslAppPickerView seslAppPickerView = this.this$0.mAppPickerView;
        if (seslAppPickerView != null) {
            seslAppPickerView.setVisibility(8);
        }
        ArrayList arrayList = new ArrayList();
        UiModeManager uiModeManager = this.this$0.mUiModeManager;
        List<String> nightPriorityAllowedPackagesFromScpm =
                uiModeManager != null
                        ? uiModeManager.getNightPriorityAllowedPackagesFromScpm()
                        : null;
        Intrinsics.checkNotNull(nightPriorityAllowedPackagesFromScpm);
        for (String str : nightPriorityAllowedPackagesFromScpm) {
            if (Utils.hasPackage(this.this$0.getContext(), str)) {
                UiModeManager uiModeManager2 = this.this$0.mUiModeManager;
                Intrinsics.checkNotNull(uiModeManager2);
                boolean z = uiModeManager2.getPackageNightMode(str) > 0;
                AppInfo.Companion companion = AppInfo.Companion;
                Intrinsics.checkNotNull(str);
                arrayList.add(
                        new AppData.ListSwitchAppDataBuilder(
                                        AppInfo.Companion.obtain(
                                                UserHandle.myUserId(), str, ApnSettings.MVNO_NONE))
                                .setSelected(z)
                                .build());
            }
        }
        SeslAppPickerView seslAppPickerView2 = this.this$0.mAppPickerView;
        if (seslAppPickerView2 != null) {
            seslAppPickerView2.submitList(arrayList);
        }
        ViewGroup viewGroup2 = this.this$0.mProgressBar;
        if (viewGroup2 != null) {
            viewGroup2.setVisibility(8);
        }
        SeslAppPickerView seslAppPickerView3 = this.this$0.mAppPickerView;
        if (seslAppPickerView3 != null) {
            seslAppPickerView3.setVisibility(0);
        }
        return Unit.INSTANCE;
    }
}
