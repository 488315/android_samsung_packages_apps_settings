package com.android.settings.network.apn;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spaprivileged.database.ContentChangeFlowKt;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PreferredApnRepository {
    public static final Uri RestorePreferredApnUri;
    public final ContentResolver contentResolver;
    public final Context context;
    public final Uri preferredApnUri;
    public final int subId;

    static {
        Uri parse = Uri.parse("content://telephony/carriers/restore");
        Intrinsics.checkNotNullExpressionValue(parse, "parse(...)");
        RestorePreferredApnUri = parse;
    }

    public PreferredApnRepository(FragmentActivity fragmentActivity, int i) {
        this.context = fragmentActivity;
        this.subId = i;
        this.contentResolver = fragmentActivity.getContentResolver();
        this.preferredApnUri =
                Uri.withAppendedPath(Telephony.Carriers.PREFERRED_APN_URI, String.valueOf(i));
    }

    public final void collectPreferredApn(LifecycleOwner lifecycleOwner, Function1 function1) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Context context = this.context;
        Uri preferredApnUri = this.preferredApnUri;
        Intrinsics.checkNotNullExpressionValue(preferredApnUri, "preferredApnUri");
        final Flow contentChangeFlow =
                ContentChangeFlowKt.contentChangeFlow(context, preferredApnUri, true);
        FlowsKt.collectLatestWithLifecycle(
                FlowKt.flowOn(
                        FlowKt.buffer$default(
                                new Flow() { // from class:
                                    // com.android.settings.network.apn.PreferredApnRepository$preferredApnFlow$$inlined$map$1

                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                    /* renamed from: com.android.settings.network.apn.PreferredApnRepository$preferredApnFlow$$inlined$map$1$2, reason: invalid class name */
                                    public final class AnonymousClass2 implements FlowCollector {
                                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                        public final /* synthetic */ PreferredApnRepository this$0;

                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                        @Metadata(
                                                k = 3,
                                                mv = {1, 9, 0},
                                                xi = 48)
                                        /* renamed from: com.android.settings.network.apn.PreferredApnRepository$preferredApnFlow$$inlined$map$1$2$1, reason: invalid class name */
                                        public final class AnonymousClass1
                                                extends ContinuationImpl {
                                            Object L$0;
                                            int label;
                                            /* synthetic */ Object result;

                                            public AnonymousClass1(Continuation continuation) {
                                                super(continuation);
                                            }

                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                            public final Object invokeSuspend(Object obj) {
                                                this.result = obj;
                                                this.label |= Integer.MIN_VALUE;
                                                return AnonymousClass2.this.emit(null, this);
                                            }
                                        }

                                        public AnonymousClass2(
                                                FlowCollector flowCollector,
                                                PreferredApnRepository preferredApnRepository) {
                                            this.$this_unsafeFlow = flowCollector;
                                            this.this$0 = preferredApnRepository;
                                        }

                                        /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
                                        /* JADX WARN: Removed duplicated region for block: B:21:0x0087 A[RETURN] */
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                                        @Override // kotlinx.coroutines.flow.FlowCollector
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                                        */
                                        public final java.lang.Object emit(
                                                java.lang.Object r12,
                                                kotlin.coroutines.Continuation r13) {
                                            /*
                                                r11 = this;
                                                java.lang.String r0 = "["
                                                boolean r1 = r13 instanceof com.android.settings.network.apn.PreferredApnRepository$preferredApnFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                if (r1 == 0) goto L15
                                                r1 = r13
                                                com.android.settings.network.apn.PreferredApnRepository$preferredApnFlow$$inlined$map$1$2$1 r1 = (com.android.settings.network.apn.PreferredApnRepository$preferredApnFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r1
                                                int r2 = r1.label
                                                r3 = -2147483648(0xffffffff80000000, float:-0.0)
                                                r4 = r2 & r3
                                                if (r4 == 0) goto L15
                                                int r2 = r2 - r3
                                                r1.label = r2
                                                goto L1a
                                            L15:
                                                com.android.settings.network.apn.PreferredApnRepository$preferredApnFlow$$inlined$map$1$2$1 r1 = new com.android.settings.network.apn.PreferredApnRepository$preferredApnFlow$$inlined$map$1$2$1
                                                r1.<init>(r13)
                                            L1a:
                                                java.lang.Object r13 = r1.result
                                                kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                int r3 = r1.label
                                                r4 = 1
                                                if (r3 == 0) goto L31
                                                if (r3 != r4) goto L29
                                                kotlin.ResultKt.throwOnFailure(r13)
                                                goto L88
                                            L29:
                                                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                                                java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                                                r11.<init>(r12)
                                                throw r11
                                            L31:
                                                kotlin.ResultKt.throwOnFailure(r13)
                                                kotlin.Unit r12 = (kotlin.Unit) r12
                                                com.android.settings.network.apn.PreferredApnRepository r12 = r11.this$0
                                                android.content.ContentResolver r5 = r12.contentResolver
                                                android.net.Uri r6 = r12.preferredApnUri
                                                java.lang.String r13 = "_id"
                                                java.lang.String[] r7 = new java.lang.String[]{r13}
                                                r9 = 0
                                                java.lang.String r10 = "name ASC"
                                                r8 = 0
                                                android.database.Cursor r3 = r5.query(r6, r7, r8, r9, r10)
                                                r5 = 0
                                                if (r3 == 0) goto L5e
                                                boolean r6 = r3.moveToNext()     // Catch: java.lang.Throwable -> L5c
                                                if (r6 != r4) goto L5e
                                                int r13 = r3.getColumnIndex(r13)     // Catch: java.lang.Throwable -> L5c
                                                java.lang.String r13 = r3.getString(r13)     // Catch: java.lang.Throwable -> L5c
                                                goto L5f
                                            L5c:
                                                r11 = move-exception
                                                goto L8b
                                            L5e:
                                                r13 = r5
                                            L5f:
                                                java.lang.String r6 = "PreferredApnRepository"
                                                int r12 = r12.subId     // Catch: java.lang.Throwable -> L5c
                                                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5c
                                                r7.<init>(r0)     // Catch: java.lang.Throwable -> L5c
                                                r7.append(r12)     // Catch: java.lang.Throwable -> L5c
                                                java.lang.String r12 = "] preferred APN: "
                                                r7.append(r12)     // Catch: java.lang.Throwable -> L5c
                                                r7.append(r13)     // Catch: java.lang.Throwable -> L5c
                                                java.lang.String r12 = r7.toString()     // Catch: java.lang.Throwable -> L5c
                                                android.util.Log.d(r6, r12)     // Catch: java.lang.Throwable -> L5c
                                                kotlin.io.CloseableKt.closeFinally(r3, r5)
                                                r1.label = r4
                                                kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                                                java.lang.Object r11 = r11.emit(r13, r1)
                                                if (r11 != r2) goto L88
                                                return r2
                                            L88:
                                                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                                                return r11
                                            L8b:
                                                throw r11     // Catch: java.lang.Throwable -> L8c
                                            L8c:
                                                r12 = move-exception
                                                kotlin.io.CloseableKt.closeFinally(r3, r11)
                                                throw r12
                                            */
                                            throw new UnsupportedOperationException(
                                                    "Method not decompiled:"
                                                        + " com.android.settings.network.apn.PreferredApnRepository$preferredApnFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                                        }
                                    }

                                    @Override // kotlinx.coroutines.flow.Flow
                                    public final Object collect(
                                            FlowCollector flowCollector,
                                            Continuation continuation) {
                                        Object collect =
                                                Flow.this.collect(
                                                        new AnonymousClass2(flowCollector, this),
                                                        continuation);
                                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                                ? collect
                                                : Unit.INSTANCE;
                                    }
                                },
                                -1),
                        Dispatchers.Default),
                lifecycleOwner,
                Lifecycle.State.STARTED,
                new PreferredApnRepository$collectPreferredApn$1(
                        2,
                        function1,
                        Intrinsics.Kotlin.class,
                        "suspendConversion0",
                        "collectPreferredApn$suspendConversion0(Lkotlin/jvm/functions/Function1;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
                        0));
    }

    public final void setPreferredApn(String apnId) {
        Intrinsics.checkNotNullParameter(apnId, "apnId");
        ContentValues contentValues = new ContentValues();
        contentValues.put("apn_id", apnId);
        Log.d(
                "PreferredApnRepository",
                "subId : " + this.subId + ", preferredApnUri : " + this.preferredApnUri);
        this.contentResolver.update(this.preferredApnUri, contentValues, null, null);
    }
}
