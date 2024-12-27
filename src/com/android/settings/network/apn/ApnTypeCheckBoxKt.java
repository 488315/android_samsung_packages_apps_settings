package com.android.settings.network.apn;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt;
import com.android.settingslib.spa.widget.editor.SettingsDropdownCheckOption;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ApnTypeCheckBoxKt {
    public static final void ApnTypeCheckBox(
            final ApnData apnData,
            final Function1 onTypeChanged,
            final Function1 onMmsSelectedChanged,
            Composer composer,
            final int i) {
        String str;
        Intrinsics.checkNotNullParameter(apnData, "apnData");
        Intrinsics.checkNotNullParameter(onTypeChanged, "onTypeChanged");
        Intrinsics.checkNotNullParameter(onMmsSelectedChanged, "onMmsSelectedChanged");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1873447351);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(-506529792);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            String[] strArr = ApnTypes.APN_TYPES;
            List readOnlyTypes = apnData.customizedConfig.readOnlyApnTypes;
            Intrinsics.checkNotNullParameter(context, "context");
            String apnType = apnData.apnType;
            Intrinsics.checkNotNullParameter(apnType, "apnType");
            Intrinsics.checkNotNullParameter(readOnlyTypes, "readOnlyTypes");
            ListBuilder listBuilder = new ListBuilder();
            List splitToList = ApnTypes.splitToList(apnType);
            final boolean isEmpty = readOnlyTypes.isEmpty();
            Boolean valueOf = Boolean.valueOf(splitToList.contains("*"));
            StructuralEqualityPolicy structuralEqualityPolicy = StructuralEqualityPolicy.INSTANCE;
            final String str2 = "*";
            listBuilder.add(
                    new SettingsDropdownCheckOption(
                            "*",
                            true,
                            isEmpty,
                            SnapshotStateKt.mutableStateOf(valueOf, structuralEqualityPolicy),
                            new Function0() { // from class:
                                              // com.android.settings.network.apn.ApnTypes$createSettingsDropdownCheckOption$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    if (!isEmpty) {
                                        String string =
                                                context.getResources()
                                                        .getString(
                                                                R.string.error_adding_apn_type,
                                                                str2);
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        Toast.makeText(context, string, 0).show();
                                    }
                                    return Unit.INSTANCE;
                                }
                            }));
            String[] strArr2 = ApnTypes.APN_TYPES;
            for (int i2 = 0; i2 < 15; i2++) {
                final String str3 = strArr2[i2];
                final boolean z =
                        (readOnlyTypes.contains("*") || readOnlyTypes.contains(str3))
                                ? false
                                : true;
                listBuilder.add(
                        new SettingsDropdownCheckOption(
                                str3,
                                false,
                                z,
                                SnapshotStateKt.mutableStateOf(
                                        Boolean.valueOf(
                                                splitToList.contains("*")
                                                        || splitToList.contains(str3)),
                                        structuralEqualityPolicy),
                                new Function0() { // from class:
                                                  // com.android.settings.network.apn.ApnTypes$createSettingsDropdownCheckOption$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        if (!z) {
                                            String string =
                                                    context.getResources()
                                                            .getString(
                                                                    R.string.error_adding_apn_type,
                                                                    str3);
                                            Intrinsics.checkNotNullExpressionValue(
                                                    string, "getString(...)");
                                            Toast.makeText(context, string, 0).show();
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }));
            }
            rememberedValue = listBuilder.build();
            Log.d("ApnTypes", "APN Type options: " + rememberedValue);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final List list = (List) rememberedValue;
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(
                composerImpl,
                Unit.INSTANCE,
                new ApnTypeCheckBoxKt$ApnTypeCheckBox$1(list, onMmsSelectedChanged, null));
        String stringResource =
                StringResources_androidKt.stringResource(composerImpl, R.string.apn_type);
        boolean isFieldEnabled = apnData.isFieldEnabled("type");
        String stringResource2 =
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.error_apn_type_empty);
        String[] strArr3 = ApnTypes.APN_TYPES;
        Intrinsics.checkNotNullParameter(list, "<this>");
        List list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                if (((Boolean) ((SettingsDropdownCheckOption) it.next()).selected.getValue())
                        .booleanValue()) {
                    str = null;
                    break;
                }
            }
        }
        str = stringResource2;
        SettingsDropdownCheckBoxKt.SettingsDropdownCheckBox(
                stringResource,
                list,
                null,
                isFieldEnabled,
                str,
                new Function0() { // from class:
                                  // com.android.settings.network.apn.ApnTypeCheckBoxKt$ApnTypeCheckBox$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        String joinToString$default;
                        Function1 function1 = Function1.this;
                        String[] strArr4 = ApnTypes.APN_TYPES;
                        List<SettingsDropdownCheckOption> list3 = list;
                        Intrinsics.checkNotNullParameter(list3, "<this>");
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj : list3) {
                            if (((SettingsDropdownCheckOption) obj).isSelectAll) {
                                arrayList.add(obj);
                            } else {
                                arrayList2.add(obj);
                            }
                        }
                        Pair pair = new Pair(arrayList, arrayList2);
                        List list4 = (List) pair.getFirst();
                        List list5 = (List) pair.getSecond();
                        Iterator it2 = list4.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                if (((Boolean)
                                                ((SettingsDropdownCheckOption) it2.next())
                                                        .selected.getValue())
                                        .booleanValue()) {
                                    joinToString$default = "*";
                                    break;
                                }
                            } else {
                                ArrayList arrayList3 = new ArrayList();
                                for (Object obj2 : list5) {
                                    if (((Boolean)
                                                    ((SettingsDropdownCheckOption) obj2)
                                                            .selected.getValue())
                                            .booleanValue()) {
                                        arrayList3.add(obj2);
                                    }
                                }
                                joinToString$default =
                                        CollectionsKt___CollectionsKt.joinToString$default(
                                                arrayList3,
                                                ",",
                                                null,
                                                null,
                                                new Function1() { // from class:
                                                                  // com.android.settings.network.apn.ApnTypes$toApnType$3
                                                    @Override // kotlin.jvm.functions.Function1
                                                    public final Object invoke(Object obj3) {
                                                        SettingsDropdownCheckOption it3 =
                                                                (SettingsDropdownCheckOption) obj3;
                                                        Intrinsics.checkNotNullParameter(it3, "it");
                                                        return it3.text;
                                                    }
                                                },
                                                30);
                            }
                        }
                        function1.invoke(joinToString$default);
                        ApnTypeCheckBoxKt.access$ApnTypeCheckBox$updateMmsSelected(
                                list, onMmsSelectedChanged);
                        return Unit.INSTANCE;
                    }
                },
                composerImpl,
                64,
                4);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.apn.ApnTypeCheckBoxKt$ApnTypeCheckBox$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ApnTypeCheckBoxKt.ApnTypeCheckBox(
                                    ApnData.this,
                                    onTypeChanged,
                                    onMmsSelectedChanged,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void access$ApnTypeCheckBox$updateMmsSelected(
            List list, Function1 function1) {
        Object obj = null;
        boolean z = false;
        for (Object obj2 : list) {
            if (Intrinsics.areEqual(((SettingsDropdownCheckOption) obj2).text, "mms")) {
                if (z) {
                    throw new IllegalArgumentException(
                            "Collection contains more than one matching element.");
                }
                z = true;
                obj = obj2;
            }
        }
        if (!z) {
            throw new NoSuchElementException(
                    "Collection contains no element matching the predicate.");
        }
        function1.invoke(((SettingsDropdownCheckOption) obj).selected.getValue());
    }
}
