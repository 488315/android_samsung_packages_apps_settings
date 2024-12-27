package com.android.settings.network.apn;

import android.telephony.TelephonyManager;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.editor.SettingsDropdownCheckBoxKt;
import com.android.settingslib.spa.widget.editor.SettingsDropdownCheckOption;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ApnNetworkTypeCheckBoxKt {
    public static final void ApnNetworkTypeCheckBox(
            final ApnData apnData,
            final Function1 onNetworkTypeChanged,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(apnData, "apnData");
        Intrinsics.checkNotNullParameter(onNetworkTypeChanged, "onNetworkTypeChanged");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1943134050);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(-1097257891);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            List list = ApnNetworkTypes.Types;
            ArrayList arrayList =
                    new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                int intValue = ((Number) it.next()).intValue();
                boolean z =
                        (TelephonyManager.getBitMaskForNetworkType(intValue) & apnData.networkType)
                                != 0;
                String networkTypeName = TelephonyManager.getNetworkTypeName(intValue);
                Intrinsics.checkNotNullExpressionValue(networkTypeName, "getNetworkTypeName(...)");
                arrayList.add(
                        new SettingsDropdownCheckOption(
                                networkTypeName,
                                SnapshotStateKt.mutableStateOf(
                                        Boolean.valueOf(z), StructuralEqualityPolicy.INSTANCE)));
            }
            composerImpl.updateRememberedValue(arrayList);
            rememberedValue = arrayList;
        }
        final List list2 = (List) rememberedValue;
        composerImpl.end(false);
        SettingsDropdownCheckBoxKt.SettingsDropdownCheckBox(
                StringResources_androidKt.stringResource(composerImpl, R.string.network_type),
                list2,
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.network_type_unspecified),
                apnData.isFieldEnabled("bearer", "bearer_bitmask", "network_type_bitmask"),
                null,
                new Function0() { // from class:
                                  // com.android.settings.network.apn.ApnNetworkTypeCheckBoxKt$ApnNetworkTypeCheckBox$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        Function1 function1 = onNetworkTypeChanged;
                        List list3 = ApnNetworkTypes.Types;
                        List<SettingsDropdownCheckOption> options = list2;
                        Intrinsics.checkNotNullParameter(options, "options");
                        long j = 0;
                        int i2 = 0;
                        for (Object obj : options) {
                            int i3 = i2 + 1;
                            if (i2 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            if (((Boolean) ((SettingsDropdownCheckOption) obj).selected.getValue())
                                    .booleanValue()) {
                                j |=
                                        TelephonyManager.getBitMaskForNetworkType(
                                                ((Number) ApnNetworkTypes.Types.get(i2))
                                                        .intValue());
                            }
                            i2 = i3;
                        }
                        function1.invoke(Long.valueOf(j));
                        return Unit.INSTANCE;
                    }
                },
                composerImpl,
                64,
                16);
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.network.apn.ApnNetworkTypeCheckBoxKt$ApnNetworkTypeCheckBox$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ApnNetworkTypeCheckBoxKt.ApnNetworkTypeCheckBox(
                                    ApnData.this,
                                    onNetworkTypeChanged,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
