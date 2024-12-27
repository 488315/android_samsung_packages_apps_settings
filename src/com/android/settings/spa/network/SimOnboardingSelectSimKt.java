package com.android.settings.spa.network;

import androidx.compose.material.icons.outlined.SignalCellularAltKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.settings.R;
import com.android.settings.network.SimOnboardingService;
import com.android.settingslib.spa.widget.scaffold.BottomAppBarButton;
import com.android.settingslib.spa.widget.scaffold.SuwScaffoldKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SimOnboardingSelectSimKt {
    /* JADX WARN: Type inference failed for: r1v2, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.android.settings.spa.network.SimOnboardingSelectSimKt$SimOnboardingSelectSimImpl$1, kotlin.jvm.internal.Lambda] */
    public static final void SimOnboardingSelectSimImpl(final Function0 nextAction, final Function0 cancelAction, final SimOnboardingService onboardingService, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(nextAction, "nextAction");
        Intrinsics.checkNotNullParameter(cancelAction, "cancelAction");
        Intrinsics.checkNotNullParameter(onboardingService, "onboardingService");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1994503976);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = RememberSaveableKt.rememberSaveable(new Object[0], null, new Function0() { // from class: com.android.settings.spa.network.SimOnboardingSelectSimKt$SimOnboardingSelectSimImpl$actionButtonController$1
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return SnapshotStateKt.mutableStateOf(Boolean.FALSE, StructuralEqualityPolicy.INSTANCE);
            }
        }, composerImpl, 3080, 6);
        SuwScaffoldKt.SuwScaffold(SignalCellularAltKt.getSignalCellularAlt(), StringResources_androidKt.stringResource(composerImpl, R.string.sim_onboarding_select_sim_title), new BottomAppBarButton(StringResources_androidKt.stringResource(composerImpl, R.string.sim_onboarding_next), ((Boolean) ((MutableState) ref$ObjectRef.element).getValue()).booleanValue(), nextAction), new BottomAppBarButton(StringResources_androidKt.stringResource(composerImpl, R.string.cancel), true, cancelAction), ComposableLambdaKt.rememberComposableLambda(537798124, new Function2() { // from class: com.android.settings.spa.network.SimOnboardingSelectSimKt$SimOnboardingSelectSimImpl$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                SimOnboardingSelectSimKt.access$SelectSimBody(SimOnboardingService.this, ref$ObjectRef.element, composer2, 8);
                return Unit.INSTANCE;
            }
        }, composerImpl), composerImpl, 24576, 0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settings.spa.network.SimOnboardingSelectSimKt$SimOnboardingSelectSimImpl$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SimOnboardingSelectSimKt.SimOnboardingSelectSimImpl(Function0.this, cancelAction, onboardingService, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r15v0 ??, still in use, count: 1, list:
          (r15v0 ?? I:java.lang.Object) from 0x0092: INVOKE (r9v1 ?? I:androidx.compose.runtime.ComposerImpl), (r15v0 ?? I:java.lang.Object) VIRTUAL call: androidx.compose.runtime.ComposerImpl.updateRememberedValue(java.lang.Object):void A[MD:(java.lang.Object):void (m)] (LINE:147)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    public static final void access$SelectSimBody(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r15v0 ??, still in use, count: 1, list:
          (r15v0 ?? I:java.lang.Object) from 0x0092: INVOKE (r9v1 ?? I:androidx.compose.runtime.ComposerImpl), (r15v0 ?? I:java.lang.Object) VIRTUAL call: androidx.compose.runtime.ComposerImpl.updateRememberedValue(java.lang.Object):void A[MD:(java.lang.Object):void (m)] (LINE:147)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r16v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */
}
