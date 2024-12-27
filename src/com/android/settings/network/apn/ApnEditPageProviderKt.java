package com.android.settings.network.apn;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.AndroidMenu_androidKt;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.Resources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.spa.framework.compose.NavControllerWrapper;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.editor.SettingsDropdownBoxKt;
import com.android.settingslib.spa.widget.editor.SettingsOutlinedTextFieldKt;
import com.android.settingslib.spa.widget.editor.SettingsTextFieldPasswordKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;
import com.android.settingslib.spa.widget.scaffold.MoreOptionsKt;
import com.android.settingslib.spa.widget.scaffold.MoreOptionsScope;
import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;
import com.android.settingslib.utils.ThreadUtils;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ApnEditPageProviderKt {
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2, kotlin.jvm.internal.Lambda] */
    public static final void ApnPage(final ApnData apnDataInit, final MutableState apnDataCur, final Uri uriInit, Composer composer, final int i) {
        String stringResource;
        Intrinsics.checkNotNullParameter(apnDataInit, "apnDataInit");
        Intrinsics.checkNotNullParameter(apnDataCur, "apnDataCur");
        Intrinsics.checkNotNullParameter(uriInit, "uriInit");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1864313111);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        final List list = ArraysKt___ArraysKt.toList(Resources_androidKt.resources(composerImpl).getStringArray(R.array.apn_auth_entries));
        final List list2 = ArraysKt___ArraysKt.toList(Resources_androidKt.resources(composerImpl).getStringArray(R.array.apn_protocol_entries));
        composerImpl.startReplaceGroup(1762095127);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = SnapshotStateKt.mutableStateOf(Boolean.FALSE, StructuralEqualityPolicy.INSTANCE);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final MutableState mutableState = (MutableState) rememberedValue;
        composerImpl.end(false);
        final NavControllerWrapper navControllerWrapper = (NavControllerWrapper) composerImpl.consume(NavControllerWrapperKt.LocalNavController);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        if (apnDataInit.newApn) {
            composerImpl.startReplaceGroup(1762095297);
            stringResource = StringResources_androidKt.stringResource(composerImpl, R.string.apn_add);
        } else {
            composerImpl.startReplaceGroup(1762095340);
            stringResource = StringResources_androidKt.stringResource(composerImpl, R.string.apn_edit);
        }
        composerImpl.end(false);
        RegularScaffoldKt.RegularScaffold(stringResource, ComposableLambdaKt.rememberComposableLambda(-1658659925, new Function3() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            /* JADX WARN: Type inference failed for: r1v25, types: [com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$1$2, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                RowScope RegularScaffold = (RowScope) obj;
                Composer composer2 = (Composer) obj2;
                int intValue = ((Number) obj3).intValue();
                Intrinsics.checkNotNullParameter(RegularScaffold, "$this$RegularScaffold");
                if ((intValue & 81) == 16) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                composerImpl3.startReplaceGroup(-1935469739);
                if (!((ApnData) apnDataCur.getValue()).customizedConfig.readOnlyApn) {
                    final Ref$ObjectRef<String> ref$ObjectRef2 = ref$ObjectRef;
                    final ApnData apnData = apnDataInit;
                    final Context context2 = context;
                    final Uri uri = uriInit;
                    final NavControllerWrapper navControllerWrapper2 = navControllerWrapper;
                    final MutableState mutableState2 = apnDataCur;
                    ButtonKt.Button(new Function0() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Ref$ObjectRef<String> ref$ObjectRef3 = ref$ObjectRef2;
                            ApnData apnDataInit2 = apnData;
                            ApnData newApnData = (ApnData) mutableState2.getValue();
                            final Context context3 = context2;
                            final Uri uriInit2 = uri;
                            Intrinsics.checkNotNullParameter(apnDataInit2, "apnDataInit");
                            Intrinsics.checkNotNullParameter(newApnData, "newApnData");
                            Intrinsics.checkNotNullParameter(context3, "context");
                            Intrinsics.checkNotNullParameter(uriInit2, "uriInit");
                            String validateApnData = ApnStatusKt.validateApnData(newApnData, context3);
                            T t = validateApnData;
                            if (validateApnData == null) {
                                final boolean z = newApnData.newApn;
                                if (z || !newApnData.equals(apnDataInit2)) {
                                    Log.d("ApnStatus", "[validateAndSaveApnData] newApnData.networkType: " + newApnData.networkType);
                                    final ContentValues contentValues = new ContentValues();
                                    if (z) {
                                        String[] strArr = ApnRepositoryKt.Projection;
                                        Object systemService = context3.getSystemService((Class<Object>) SubscriptionManager.class);
                                        Intrinsics.checkNotNull(systemService);
                                        int i2 = newApnData.subId;
                                        SubscriptionInfo activeSubscriptionInfo = ((SubscriptionManager) systemService).getActiveSubscriptionInfo(i2);
                                        int carrierId = activeSubscriptionInfo.getCarrierId();
                                        Map mapOf = carrierId != -1 ? MapsKt__MapsKt.mapOf(new Pair("carrier_id", Integer.valueOf(carrierId))) : MapsKt__MapsKt.mapOf(new Pair("numeric", AbstractResolvableFuture$$ExternalSyntheticOutline0.m(activeSubscriptionInfo.getMccString(), activeSubscriptionInfo.getMncString())));
                                        Log.d("ApnRepository", "[" + i2 + "] New APN item with id: " + mapOf);
                                        final int i3 = 0;
                                        mapOf.forEach(new BiConsumer() { // from class: com.android.settings.network.apn.ApnData$getContentValues$1$1
                                            @Override // java.util.function.BiConsumer
                                            public final void accept(Object obj4, Object obj5) {
                                                switch (i3) {
                                                    case 0:
                                                        contentValues.putObject((String) obj4, obj5);
                                                        break;
                                                    default:
                                                        contentValues.putObject((String) obj4, obj5);
                                                        break;
                                                }
                                            }
                                        });
                                    }
                                    final int i4 = 1;
                                    newApnData.getContentValueMap(context3).forEach(new BiConsumer() { // from class: com.android.settings.network.apn.ApnData$getContentValues$1$1
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj4, Object obj5) {
                                            switch (i4) {
                                                case 0:
                                                    contentValues.putObject((String) obj4, obj5);
                                                    break;
                                                default:
                                                    contentValues.putObject((String) obj4, obj5);
                                                    break;
                                            }
                                        }
                                    });
                                    ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settings.network.apn.ApnRepositoryKt$updateApnDataToDatabase$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            if (!z) {
                                                Log.d("ApnRepository", "Updating an existing APN to the database " + uriInit2 + " " + contentValues);
                                                context3.getContentResolver().update(uriInit2, contentValues, null, null);
                                                return;
                                            }
                                            Log.d("ApnRepository", "Adding an new APN to the database " + uriInit2 + " " + contentValues);
                                            if (context3.getContentResolver().insert(uriInit2, contentValues) == null) {
                                                Log.e("ApnRepository", "Can't add a new apn to database " + uriInit2);
                                            }
                                        }
                                    });
                                }
                                t = 0;
                            }
                            ref$ObjectRef3.element = t;
                            if (ref$ObjectRef2.element == null) {
                                navControllerWrapper2.navigateBack();
                            } else if (!((ApnData) mutableState2.getValue()).validEnabled) {
                                MutableState mutableState3 = mutableState2;
                                mutableState3.setValue(ApnData.copy$default((ApnData) mutableState3.getValue(), null, null, null, null, null, null, null, null, null, null, 0, null, 0, 0, false, 0L, false, false, 0, true, null, 12582911));
                            }
                            return Unit.INSTANCE;
                        }
                    }, null, false, null, null, null, null, null, null, ComposableSingletons$ApnEditPageProviderKt.f25lambda1, composerImpl3, 805306368, FileType.EML);
                }
                composerImpl3.end(false);
                if (!((ApnData) apnDataCur.getValue()).newApn && !((ApnData) apnDataCur.getValue()).customizedConfig.readOnlyApn && ((ApnData) apnDataCur.getValue()).customizedConfig.isAddApnAllowed) {
                    final Uri uri2 = uriInit;
                    final Context context3 = context;
                    final NavControllerWrapper navControllerWrapper3 = navControllerWrapper;
                    MoreOptionsKt.MoreOptionsAction(ComposableLambdaKt.rememberComposableLambda(841110654, new Function3() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$1.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                            MoreOptionsScope MoreOptionsAction = (MoreOptionsScope) obj4;
                            Composer composer3 = (Composer) obj5;
                            int intValue2 = ((Number) obj6).intValue();
                            Intrinsics.checkNotNullParameter(MoreOptionsAction, "$this$MoreOptionsAction");
                            if ((intValue2 & 81) == 16) {
                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                if (composerImpl4.getSkipping()) {
                                    composerImpl4.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey3 = ComposerKt.invocation;
                            ComposableLambdaImpl composableLambdaImpl = ComposableSingletons$ApnEditPageProviderKt.f26lambda2;
                            final Uri uri3 = uri2;
                            final Context context4 = context3;
                            final NavControllerWrapper navControllerWrapper4 = navControllerWrapper3;
                            AndroidMenu_androidKt.DropdownMenuItem(composableLambdaImpl, new Function0() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt.ApnPage.1.2.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    Uri uri4 = uri3;
                                    Context context5 = context4;
                                    Intrinsics.checkNotNullParameter(uri4, "uri");
                                    Intrinsics.checkNotNullParameter(context5, "context");
                                    context5.getContentResolver().delete(uri4, null, null);
                                    navControllerWrapper4.navigateBack();
                                    return Unit.INSTANCE;
                                }
                            }, null, null, null, false, null, null, null, composer3, 6, FileType.VTS);
                            return Unit.INSTANCE;
                        }
                    }, composerImpl3), composerImpl3, 6);
                }
                return Unit.INSTANCE;
            }
        }, composerImpl), ComposableLambdaKt.rememberComposableLambda(2116292612, new Function2() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Type inference failed for: r9v13, types: [T, java.lang.String] */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                MutableState mutableState2;
                Composer composer2;
                Context context2;
                List<String> list3;
                List<String> list4;
                MutableState mutableState3;
                ComposerImpl composerImpl2;
                final MutableState mutableState4;
                ComposerImpl composerImpl3;
                Composer composer3 = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                    if (composerImpl4.getSkipping()) {
                        composerImpl4.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                Ref$ObjectRef<String> ref$ObjectRef2 = Ref$ObjectRef.this;
                Context context3 = context;
                MutableState mutableState5 = apnDataCur;
                List<String> list5 = list;
                List<String> list6 = list2;
                MutableState mutableState6 = mutableState;
                Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
                boolean z = false;
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, Alignment.Companion.Start, composer3, 0);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer3, companion);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (!(composerImpl5.applier instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl5.startReusableNode();
                if (composerImpl5.inserting) {
                    composerImpl5.createNode(function0);
                } else {
                    composerImpl5.useNode();
                }
                Updater.m221setimpl(composer3, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m221setimpl(composer3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function2);
                }
                Updater.m221setimpl(composer3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                composerImpl5.startReplaceGroup(-926969794);
                if (((ApnData) mutableState5.getValue()).validEnabled) {
                    ?? validateApnData = ApnStatusKt.validateApnData((ApnData) mutableState5.getValue(), context3);
                    ref$ObjectRef2.element = validateApnData;
                    if (validateApnData == 0) {
                        mutableState2 = mutableState6;
                        composer2 = composer3;
                        context2 = context3;
                        composerImpl3 = composerImpl5;
                        list3 = list5;
                        list4 = list6;
                        mutableState3 = mutableState5;
                    } else {
                        composerImpl3 = composerImpl5;
                        list3 = list5;
                        list4 = list6;
                        mutableState3 = mutableState5;
                        mutableState2 = mutableState6;
                        composer2 = composer3;
                        context2 = context3;
                        TextKt.m210Text4IGK_g(validateApnData, PaddingKt.padding(SizeKt.FillWholeMaxWidth, SettingsDimension.menuFieldPadding), ((ColorScheme) ((ComposerImpl) composer3).consume(ColorSchemeKt.LocalColorScheme)).primary, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer2, 0, 0, 131064);
                    }
                    composerImpl2 = composerImpl3;
                    z = false;
                } else {
                    mutableState2 = mutableState6;
                    composer2 = composer3;
                    context2 = context3;
                    list3 = list5;
                    list4 = list6;
                    mutableState3 = mutableState5;
                    composerImpl2 = composerImpl5;
                }
                composerImpl2.end(z);
                String str = ((ApnData) mutableState3.getValue()).name;
                final Composer composer4 = composer2;
                String stringResource2 = StringResources_androidKt.stringResource(composer4, R.string.apn_name);
                boolean isFieldEnabled = ((ApnData) mutableState3.getValue()).isFieldEnabled("name");
                boolean z2 = ((ApnData) mutableState3.getValue()).validEnabled;
                String name = ((ApnData) mutableState3.getValue()).name;
                Intrinsics.checkNotNullParameter(name, "name");
                Context context4 = context2;
                Intrinsics.checkNotNullParameter(context4, "context");
                String string = (z2 && name.equals(com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE)) ? context4.getResources().getString(R.string.error_name_empty) : null;
                composerImpl2.startReplaceGroup(-926969022);
                final MutableState mutableState7 = mutableState3;
                boolean changed = composerImpl2.changed(mutableState7);
                Object rememberedValue2 = composerImpl2.rememberedValue();
                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (changed || rememberedValue2 == composer$Companion$Empty$1) {
                    rememberedValue2 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$2$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            String it = (String) obj3;
                            Intrinsics.checkNotNullParameter(it, "it");
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), it, null, null, null, null, null, null, null, null, null, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16777213));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue2);
                }
                composerImpl2.end(z);
                SettingsOutlinedTextFieldKt.SettingsOutlinedTextField(str, stringResource2, string, false, isFieldEnabled, null, null, null, (Function1) rememberedValue2, composer4, 0, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
                String str2 = ((ApnData) mutableState7.getValue()).apn;
                String stringResource3 = StringResources_androidKt.stringResource(composer4, R.string.apn_apn);
                boolean isFieldEnabled2 = ((ApnData) mutableState7.getValue()).isFieldEnabled("apn");
                boolean z3 = ((ApnData) mutableState7.getValue()).validEnabled;
                String apn = ((ApnData) mutableState7.getValue()).apn;
                Intrinsics.checkNotNullParameter(apn, "apn");
                String string2 = (z3 && apn.equals(com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE)) ? context4.getResources().getString(R.string.error_apn_empty) : null;
                composerImpl2.startReplaceGroup(-926968675);
                boolean changed2 = composerImpl2.changed(mutableState7);
                Object rememberedValue3 = composerImpl2.rememberedValue();
                if (changed2 || rememberedValue3 == composer$Companion$Empty$1) {
                    rememberedValue3 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$3$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            String it = (String) obj3;
                            Intrinsics.checkNotNullParameter(it, "it");
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, it, null, null, null, null, null, null, null, null, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16777211));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue3);
                }
                composerImpl2.end(false);
                SettingsOutlinedTextFieldKt.SettingsOutlinedTextField(str2, stringResource3, string2, false, isFieldEnabled2, null, null, null, (Function1) rememberedValue3, composer4, 0, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
                String str3 = ((ApnData) mutableState7.getValue()).proxy;
                String stringResource4 = StringResources_androidKt.stringResource(composer4, R.string.apn_http_proxy);
                boolean isFieldEnabled3 = ((ApnData) mutableState7.getValue()).isFieldEnabled("proxy");
                composerImpl2.startReplaceGroup(-926968405);
                boolean changed3 = composerImpl2.changed(mutableState7);
                Object rememberedValue4 = composerImpl2.rememberedValue();
                if (changed3 || rememberedValue4 == composer$Companion$Empty$1) {
                    rememberedValue4 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$4$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            String it = (String) obj3;
                            Intrinsics.checkNotNullParameter(it, "it");
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, it, null, null, null, null, null, null, null, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16777207));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue4);
                }
                composerImpl2.end(false);
                SettingsOutlinedTextFieldKt.SettingsOutlinedTextField(str3, stringResource4, null, false, isFieldEnabled3, null, null, null, (Function1) rememberedValue4, composer4, 0, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
                String str4 = ((ApnData) mutableState7.getValue()).port;
                String stringResource5 = StringResources_androidKt.stringResource(composer4, R.string.apn_http_port);
                boolean isFieldEnabled4 = ((ApnData) mutableState7.getValue()).isFieldEnabled(HostAuth.PORT);
                composerImpl2.startReplaceGroup(-926968136);
                boolean changed4 = composerImpl2.changed(mutableState7);
                Object rememberedValue5 = composerImpl2.rememberedValue();
                if (changed4 || rememberedValue5 == composer$Companion$Empty$1) {
                    rememberedValue5 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$5$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            String it = (String) obj3;
                            Intrinsics.checkNotNullParameter(it, "it");
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, it, null, null, null, null, null, null, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16777199));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue5);
                }
                composerImpl2.end(false);
                SettingsOutlinedTextFieldKt.SettingsOutlinedTextField(str4, stringResource5, null, false, isFieldEnabled4, null, null, null, (Function1) rememberedValue5, composer4, 0, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
                String str5 = ((ApnData) mutableState7.getValue()).userName;
                String stringResource6 = StringResources_androidKt.stringResource(composer4, R.string.apn_user);
                boolean isFieldEnabled5 = ((ApnData) mutableState7.getValue()).isFieldEnabled("user");
                composerImpl2.startReplaceGroup(-926967869);
                boolean changed5 = composerImpl2.changed(mutableState7);
                Object rememberedValue6 = composerImpl2.rememberedValue();
                if (changed5 || rememberedValue6 == composer$Companion$Empty$1) {
                    rememberedValue6 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$6$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            String it = (String) obj3;
                            Intrinsics.checkNotNullParameter(it, "it");
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, it, null, null, null, null, null, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16777183));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue6);
                }
                composerImpl2.end(false);
                SettingsOutlinedTextFieldKt.SettingsOutlinedTextField(str5, stringResource6, null, false, isFieldEnabled5, null, null, null, (Function1) rememberedValue6, composer4, 0, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
                String str6 = ((ApnData) mutableState7.getValue()).passWord;
                String stringResource7 = StringResources_androidKt.stringResource(composer4, R.string.apn_password);
                boolean isFieldEnabled6 = ((ApnData) mutableState7.getValue()).isFieldEnabled(HostAuth.PASSWORD);
                composerImpl2.startReplaceGroup(-926967590);
                boolean changed6 = composerImpl2.changed(mutableState7);
                Object rememberedValue7 = composerImpl2.rememberedValue();
                if (changed6 || rememberedValue7 == composer$Companion$Empty$1) {
                    rememberedValue7 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$7$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            String it = (String) obj3;
                            Intrinsics.checkNotNullParameter(it, "it");
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, it, null, null, null, null, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16777151));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue7);
                }
                composerImpl2.end(false);
                SettingsTextFieldPasswordKt.SettingsTextFieldPassword(str6, stringResource7, isFieldEnabled6, (Function1) rememberedValue7, composer4, 0, 0);
                String str7 = ((ApnData) mutableState7.getValue()).server;
                String stringResource8 = StringResources_androidKt.stringResource(composer4, R.string.apn_server);
                boolean isFieldEnabled7 = ((ApnData) mutableState7.getValue()).isFieldEnabled("server");
                composerImpl2.startReplaceGroup(-926967317);
                boolean changed7 = composerImpl2.changed(mutableState7);
                Object rememberedValue8 = composerImpl2.rememberedValue();
                if (changed7 || rememberedValue8 == composer$Companion$Empty$1) {
                    rememberedValue8 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$8$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            String it = (String) obj3;
                            Intrinsics.checkNotNullParameter(it, "it");
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, it, null, null, null, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16777087));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue8);
                }
                composerImpl2.end(false);
                SettingsOutlinedTextFieldKt.SettingsOutlinedTextField(str7, stringResource8, null, false, isFieldEnabled7, null, null, null, (Function1) rememberedValue8, composer4, 0, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
                ApnData apnData = (ApnData) mutableState7.getValue();
                composerImpl2.startReplaceGroup(-926967181);
                boolean changed8 = composerImpl2.changed(mutableState7);
                Object rememberedValue9 = composerImpl2.rememberedValue();
                if (changed8 || rememberedValue9 == composer$Companion$Empty$1) {
                    rememberedValue9 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$9$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            String it = (String) obj3;
                            Intrinsics.checkNotNullParameter(it, "it");
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, null, null, null, null, 0, it, 0, 0, false, 0L, false, false, 0, false, null, 16773119));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue9);
                }
                Function1 function1 = (Function1) rememberedValue9;
                composerImpl2.end(false);
                composerImpl2.startReplaceGroup(-926967100);
                Object rememberedValue10 = composerImpl2.rememberedValue();
                if (rememberedValue10 == composer$Companion$Empty$1) {
                    mutableState4 = mutableState2;
                    rememberedValue10 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$10$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            Boolean bool = (Boolean) obj3;
                            bool.booleanValue();
                            MutableState.this.setValue(bool);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue10);
                } else {
                    mutableState4 = mutableState2;
                }
                composerImpl2.end(false);
                ApnTypeCheckBoxKt.ApnTypeCheckBox(apnData, function1, (Function1) rememberedValue10, composer4, 392);
                composerImpl2.startReplaceGroup(-926967045);
                if (((Boolean) mutableState4.getValue()).booleanValue()) {
                    String str8 = ((ApnData) mutableState7.getValue()).mmsc;
                    String stringResource9 = StringResources_androidKt.stringResource(composer4, R.string.apn_mmsc);
                    String validateMMSC = ApnStatusKt.validateMMSC(context4, ((ApnData) mutableState7.getValue()).mmsc, ((ApnData) mutableState7.getValue()).validEnabled);
                    boolean isFieldEnabled8 = ((ApnData) mutableState7.getValue()).isFieldEnabled("mmsc");
                    composerImpl2.startReplaceGroup(-926966680);
                    boolean changed9 = composerImpl2.changed(mutableState7);
                    Object rememberedValue11 = composerImpl2.rememberedValue();
                    if (changed9 || rememberedValue11 == composer$Companion$Empty$1) {
                        rememberedValue11 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$11$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                String it = (String) obj3;
                                Intrinsics.checkNotNullParameter(it, "it");
                                MutableState mutableState8 = MutableState.this;
                                mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, null, it, null, null, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16776959));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(rememberedValue11);
                    }
                    composerImpl2.end(false);
                    SettingsOutlinedTextFieldKt.SettingsOutlinedTextField(str8, stringResource9, validateMMSC, false, isFieldEnabled8, null, null, null, (Function1) rememberedValue11, composer4, 0, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
                    String str9 = ((ApnData) mutableState7.getValue()).mmsProxy;
                    String stringResource10 = StringResources_androidKt.stringResource(composer4, R.string.apn_mms_proxy);
                    boolean isFieldEnabled9 = ((ApnData) mutableState7.getValue()).isFieldEnabled("mmsproxy");
                    composerImpl2.startReplaceGroup(-926966384);
                    boolean changed10 = composerImpl2.changed(mutableState7);
                    Object rememberedValue12 = composerImpl2.rememberedValue();
                    if (changed10 || rememberedValue12 == composer$Companion$Empty$1) {
                        rememberedValue12 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$12$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                String it = (String) obj3;
                                Intrinsics.checkNotNullParameter(it, "it");
                                MutableState mutableState8 = MutableState.this;
                                mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, null, null, it, null, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16776703));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(rememberedValue12);
                    }
                    composerImpl2.end(false);
                    SettingsOutlinedTextFieldKt.SettingsOutlinedTextField(str9, stringResource10, null, false, isFieldEnabled9, null, null, null, (Function1) rememberedValue12, composer4, 0, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
                    String str10 = ((ApnData) mutableState7.getValue()).mmsPort;
                    String stringResource11 = StringResources_androidKt.stringResource(composer4, R.string.apn_mms_port);
                    boolean isFieldEnabled10 = ((ApnData) mutableState7.getValue()).isFieldEnabled("mmsport");
                    composerImpl2.startReplaceGroup(-926966087);
                    boolean changed11 = composerImpl2.changed(mutableState7);
                    Object rememberedValue13 = composerImpl2.rememberedValue();
                    if (changed11 || rememberedValue13 == composer$Companion$Empty$1) {
                        rememberedValue13 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$13$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                String it = (String) obj3;
                                Intrinsics.checkNotNullParameter(it, "it");
                                MutableState mutableState8 = MutableState.this;
                                mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, null, null, null, it, 0, null, 0, 0, false, 0L, false, false, 0, false, null, 16776191));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(rememberedValue13);
                    }
                    composerImpl2.end(false);
                    SettingsOutlinedTextFieldKt.SettingsOutlinedTextField(str10, stringResource11, null, false, isFieldEnabled10, null, null, null, (Function1) rememberedValue13, composer4, 0, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
                }
                composerImpl2.end(false);
                String stringResource12 = StringResources_androidKt.stringResource(composer4, R.string.apn_auth_type);
                int i2 = ((ApnData) mutableState7.getValue()).authType;
                boolean isFieldEnabled11 = ((ApnData) mutableState7.getValue()).isFieldEnabled("authtype");
                composerImpl2.startReplaceGroup(-926965742);
                boolean changed12 = composerImpl2.changed(mutableState7);
                Object rememberedValue14 = composerImpl2.rememberedValue();
                if (changed12 || rememberedValue14 == composer$Companion$Empty$1) {
                    rememberedValue14 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$14$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            int intValue = ((Number) obj3).intValue();
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, null, null, null, null, intValue, null, 0, 0, false, 0L, false, false, 0, false, null, 16775167));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue14);
                }
                composerImpl2.end(false);
                SettingsDropdownBoxKt.SettingsDropdownBox(stringResource12, list3, i2, isFieldEnabled11, (Function1) rememberedValue14, composer4, 64, 0);
                String stringResource13 = StringResources_androidKt.stringResource(composer4, R.string.apn_protocol);
                int i3 = ((ApnData) mutableState7.getValue()).apnProtocol;
                boolean isFieldEnabled12 = ((ApnData) mutableState7.getValue()).isFieldEnabled("protocol");
                composerImpl2.startReplaceGroup(-926965406);
                boolean changed13 = composerImpl2.changed(mutableState7);
                Object rememberedValue15 = composerImpl2.rememberedValue();
                if (changed13 || rememberedValue15 == composer$Companion$Empty$1) {
                    rememberedValue15 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$15$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            int intValue = ((Number) obj3).intValue();
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, null, null, null, null, 0, null, intValue, 0, false, 0L, false, false, 0, false, null, 16769023));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue15);
                }
                composerImpl2.end(false);
                SettingsDropdownBoxKt.SettingsDropdownBox(stringResource13, list4, i3, isFieldEnabled12, (Function1) rememberedValue15, composer4, 64, 0);
                String stringResource14 = StringResources_androidKt.stringResource(composer4, R.string.apn_roaming_protocol);
                int i4 = ((ApnData) mutableState7.getValue()).apnRoaming;
                boolean isFieldEnabled13 = ((ApnData) mutableState7.getValue()).isFieldEnabled("roaming_protocol");
                composerImpl2.startReplaceGroup(-926965052);
                boolean changed14 = composerImpl2.changed(mutableState7);
                Object rememberedValue16 = composerImpl2.rememberedValue();
                if (changed14 || rememberedValue16 == composer$Companion$Empty$1) {
                    rememberedValue16 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$16$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            int intValue = ((Number) obj3).intValue();
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, null, null, null, null, 0, null, 0, intValue, false, 0L, false, false, 0, false, null, 16760831));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue16);
                }
                composerImpl2.end(false);
                SettingsDropdownBoxKt.SettingsDropdownBox(stringResource14, list4, i4, isFieldEnabled13, (Function1) rememberedValue16, composer4, 64, 0);
                ApnData apnData2 = (ApnData) mutableState7.getValue();
                composerImpl2.startReplaceGroup(-926964964);
                boolean changed15 = composerImpl2.changed(mutableState7);
                Object rememberedValue17 = composerImpl2.rememberedValue();
                if (changed15 || rememberedValue17 == composer$Companion$Empty$1) {
                    rememberedValue17 = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$17$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            long longValue = ((Number) obj3).longValue();
                            MutableState mutableState8 = MutableState.this;
                            mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, null, null, null, null, 0, null, 0, 0, false, longValue, false, false, 0, false, null, 16711679));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue17);
                }
                composerImpl2.end(false);
                ApnNetworkTypeCheckBoxKt.ApnNetworkTypeCheckBox(apnData2, (Function1) rememberedValue17, composer4, 8);
                SwitchPreferenceKt.SwitchPreference(new SwitchPreferenceModel(composer4, mutableState7) { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$18
                    public final Function0 changeable;
                    public final Function0 checked;
                    public final Function1 onCheckedChange;
                    public final String title;

                    {
                        this.title = StringResources_androidKt.stringResource(composer4, R.string.carrier_enabled);
                        this.changeable = new Function0() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$18$changeable$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return Boolean.valueOf(((ApnData) MutableState.this.getValue()).apnEnableEnabled && ((ApnData) MutableState.this.getValue()).isFieldEnabled("carrier_enabled"));
                            }
                        };
                        this.checked = new Function0() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$18$checked$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return Boolean.valueOf(((ApnData) MutableState.this.getValue()).apnEnable);
                            }
                        };
                        this.onCheckedChange = new Function1() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$2$1$18$onCheckedChange$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                boolean booleanValue = ((Boolean) obj3).booleanValue();
                                MutableState mutableState8 = MutableState.this;
                                mutableState8.setValue(ApnData.copy$default((ApnData) mutableState8.getValue(), null, null, null, null, null, null, null, null, null, null, 0, null, 0, 0, booleanValue, 0L, false, false, 0, false, null, 16744447));
                                return Unit.INSTANCE;
                            }
                        };
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final Function0 getChangeable() {
                        return this.changeable;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final Function0 getChecked() {
                        return this.checked;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final Function1 getOnCheckedChange() {
                        return this.onCheckedChange;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                }, composer4, 0);
                composerImpl2.end(true);
                return Unit.INSTANCE;
            }
        }, composerImpl), composerImpl, FileType.CRT, 0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settings.network.apn.ApnEditPageProviderKt$ApnPage$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ApnEditPageProviderKt.ApnPage(ApnData.this, apnDataCur, uriInit, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
