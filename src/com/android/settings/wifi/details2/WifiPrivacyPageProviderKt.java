package com.android.settings.wifi.details2;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.Resources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.preference.ListPreferenceModel;
import com.android.settingslib.spa.widget.preference.ListPreferenceOption;
import com.android.settingslib.spa.widget.preference.RadioPreferencesKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceKt;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;
import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;
import com.android.settingslib.spa.widget.ui.CategoryKt;
import com.android.wifitrackerlib.WifiEntry;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class WifiPrivacyPageProviderKt {
    public static final void DeviceNameSwitchPreference(
            final WifiConfiguration wifiConfiguration, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(wifiConfiguration, "wifiConfiguration");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1866102070);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
        float f = SettingsDimension.itemDividerHeight;
        SpacerKt.Spacer(composerImpl, SizeKt.m100width3ABfNKs(companion, f));
        CategoryKt.CategoryTitle(
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.wifi_privacy_device_name_settings),
                composerImpl,
                0);
        SpacerKt.Spacer(composerImpl, SizeKt.m100width3ABfNKs(companion, f));
        composerImpl.startReplaceGroup(-44801623);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    SnapshotStateKt.mutableStateOf(
                            Boolean.valueOf(wifiConfiguration.isSendDhcpHostnameEnabled()),
                            StructuralEqualityPolicy.INSTANCE);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final MutableState mutableState = (MutableState) rememberedValue;
        composerImpl.end(false);
        final Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Object systemService = context.getSystemService((Class<Object>) WifiManager.class);
        Intrinsics.checkNotNull(systemService);
        final WifiManager wifiManager = (WifiManager) systemService;
        SwitchPreferenceKt.SwitchPreference(
                new SwitchPreferenceModel(
                        context,
                        mutableState,
                        wifiConfiguration,
                        wifiManager) { // from class:
                                       // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$DeviceNameSwitchPreference$1
                    public final Function0 checked;
                    public final Function1 onCheckedChange;
                    public final Function0 summary;
                    public final String title;

                    {
                        String string =
                                context.getResources()
                                        .getString(
                                                R.string
                                                        .wifi_privacy_send_device_name_toggle_title);
                        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                        this.title = string;
                        this.summary =
                                new Function0() { // from class:
                                                  // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$DeviceNameSwitchPreference$1$summary$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        return context.getResources()
                                                .getString(
                                                        R.string
                                                                .wifi_privacy_send_device_name_toggle_summary);
                                    }
                                };
                        this.checked =
                                new Function0() { // from class:
                                                  // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$DeviceNameSwitchPreference$1$checked$1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        Boolean bool = (Boolean) MutableState.this.getValue();
                                        bool.booleanValue();
                                        return bool;
                                    }
                                };
                        this.onCheckedChange =
                                new Function1() { // from class:
                                                  // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$DeviceNameSwitchPreference$1$onCheckedChange$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        Boolean bool = (Boolean) obj;
                                        wifiConfiguration.setSendDhcpHostnameEnabled(
                                                bool.booleanValue());
                                        wifiManager.save(wifiConfiguration, null);
                                        mutableState.setValue(bool);
                                        return Unit.INSTANCE;
                                    }
                                };
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
                    public final Function0 getSummary() {
                        return this.summary;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                },
                composerImpl,
                0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$DeviceNameSwitchPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            WifiPrivacyPageProviderKt.DeviceNameSwitchPreference(
                                    wifiConfiguration,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$WifiPrivacyPage$1, kotlin.jvm.internal.Lambda] */
    public static final void WifiPrivacyPage(
            final WifiEntry wifiEntry, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(wifiEntry, "wifiEntry");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1649036356);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final boolean canSetPrivacy = wifiEntry.canSetPrivacy();
        RegularScaffoldKt.RegularScaffold(
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.wifi_privacy_settings),
                null,
                ComposableLambdaKt.rememberComposableLambda(
                        -689345727,
                        new Function2() { // from class:
                                          // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$WifiPrivacyPage$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ComposerImpl composerImpl2;
                                Composer composer2 = (Composer) obj;
                                if ((((Number) obj2).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                final WifiEntry wifiEntry2 = WifiEntry.this;
                                final boolean z = canSetPrivacy;
                                Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
                                ColumnMeasurePolicy columnMeasurePolicy =
                                        ColumnKt.columnMeasurePolicy(
                                                Arrangement.Top,
                                                Alignment.Companion.Start,
                                                composer2,
                                                0);
                                int currentCompositeKeyHash =
                                        ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap currentCompositionLocalScope =
                                        composerImpl4.currentCompositionLocalScope();
                                Modifier materializeModifier =
                                        ComposedModifierKt.materializeModifier(
                                                composer2, companion);
                                ComposeUiNode.Companion.getClass();
                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                if (!(composerImpl4.applier instanceof Applier)) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl4.startReusableNode();
                                if (composerImpl4.inserting) {
                                    composerImpl4.createNode(function0);
                                } else {
                                    composerImpl4.useNode();
                                }
                                Updater.m221setimpl(
                                        composer2,
                                        columnMeasurePolicy,
                                        ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m221setimpl(
                                        composer2,
                                        currentCompositionLocalScope,
                                        ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl4.inserting
                                        || !Intrinsics.areEqual(
                                                composerImpl4.rememberedValue(),
                                                Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(
                                            currentCompositeKeyHash,
                                            composerImpl4,
                                            currentCompositeKeyHash,
                                            function2);
                                }
                                Updater.m221setimpl(
                                        composer2,
                                        materializeModifier,
                                        ComposeUiNode.Companion.SetModifier);
                                final String stringResource =
                                        StringResources_androidKt.stringResource(
                                                composer2, R.string.wifi_privacy_mac_settings);
                                String[] stringArray =
                                        Resources_androidKt.resources(composer2)
                                                .getStringArray(R.array.wifi_privacy_entries);
                                String[] stringArray2 =
                                        Resources_androidKt.resources(composer2)
                                                .getStringArray(R.array.wifi_privacy_values);
                                final MutableIntState mutableIntState =
                                        (MutableIntState)
                                                RememberSaveableKt.rememberSaveable(
                                                        new Object[0],
                                                        null,
                                                        new Function0() { // from class:
                                                                          // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$WifiPrivacyPage$1$1$textsSelectedId$1
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            /* renamed from: invoke */
                                                            public final Object mo1068invoke() {
                                                                return SnapshotIntStateKt
                                                                        .mutableIntStateOf(
                                                                                WifiEntry.this
                                                                                        .getPrivacy());
                                                            }
                                                        },
                                                        composer2,
                                                        8,
                                                        6);
                                composerImpl4.startReplaceGroup(209258352);
                                Object rememberedValue = composerImpl4.rememberedValue();
                                Composer$Companion$Empty$1 composer$Companion$Empty$1 =
                                        Composer.Companion.Empty;
                                Object obj3 = rememberedValue;
                                if (rememberedValue == composer$Companion$Empty$1) {
                                    ArrayList arrayList = new ArrayList(stringArray.length);
                                    int length = stringArray.length;
                                    int i2 = 0;
                                    int i3 = 0;
                                    while (i2 < length) {
                                        arrayList.add(
                                                new ListPreferenceOption(
                                                        Integer.parseInt(stringArray2[i3]),
                                                        stringArray[i2]));
                                        i2++;
                                        i3++;
                                    }
                                    composerImpl4.updateRememberedValue(arrayList);
                                    obj3 = arrayList;
                                }
                                final List list = (List) obj3;
                                composerImpl4.end(false);
                                composerImpl4.startReplaceGroup(209258580);
                                Object rememberedValue2 = composerImpl4.rememberedValue();
                                if (rememberedValue2 == composer$Companion$Empty$1) {
                                    composerImpl2 = composerImpl4;
                                    rememberedValue2 =
                                            new ListPreferenceModel(
                                                    stringResource,
                                                    list,
                                                    mutableIntState,
                                                    wifiEntry2,
                                                    z) { // from class:
                                                         // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$WifiPrivacyPage$1$1$1$1
                                                public final Function0 enabled;
                                                public final Function1 onIdSelected;
                                                public final List options;
                                                public final MutableIntState selectedId;
                                                public final String title;

                                                {
                                                    this.title = stringResource;
                                                    this.options = list;
                                                    this.selectedId = mutableIntState;
                                                    this.onIdSelected =
                                                            new Function1() { // from class:
                                                                              // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$WifiPrivacyPage$1$1$1$1$onIdSelected$1
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                public final Object invoke(
                                                                        Object obj4) {
                                                                    int intValue =
                                                                            ((Number) obj4)
                                                                                    .intValue();
                                                                    ((SnapshotMutableIntStateImpl)
                                                                                    MutableIntState
                                                                                            .this)
                                                                            .setIntValue(intValue);
                                                                    WifiEntry wifiEntry3 =
                                                                            wifiEntry2;
                                                                    Intrinsics
                                                                            .checkNotNullParameter(
                                                                                    wifiEntry3,
                                                                                    "wifiEntry");
                                                                    if (wifiEntry3.getPrivacy()
                                                                            != intValue) {
                                                                        wifiEntry3.setPrivacy(
                                                                                intValue);
                                                                        if (wifiEntry3
                                                                                        .getConnectedState()
                                                                                == 2) {
                                                                            wifiEntry3.disconnect(
                                                                                    null);
                                                                            wifiEntry3.connect(
                                                                                    null);
                                                                        }
                                                                    }
                                                                    return Unit.INSTANCE;
                                                                }
                                                            };
                                                    this.enabled =
                                                            new Function0() { // from class:
                                                                              // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$WifiPrivacyPage$1$1$1$1$enabled$1
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(0);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function0
                                                                /* renamed from: invoke */
                                                                public final Object mo1068invoke() {
                                                                    return Boolean.valueOf(z);
                                                                }
                                                            };
                                                }

                                                @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                                                public final Function0 getEnabled() {
                                                    return this.enabled;
                                                }

                                                @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                                                public final Function1 getOnIdSelected() {
                                                    return this.onIdSelected;
                                                }

                                                @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                                                public final List getOptions() {
                                                    return this.options;
                                                }

                                                @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                                                public final MutableIntState getSelectedId() {
                                                    return this.selectedId;
                                                }

                                                @Override // com.android.settingslib.spa.widget.preference.ListPreferenceModel
                                                public final String getTitle() {
                                                    return this.title;
                                                }
                                            };
                                    composerImpl2.updateRememberedValue(rememberedValue2);
                                } else {
                                    composerImpl2 = composerImpl4;
                                }
                                composerImpl2.end(false);
                                RadioPreferencesKt.RadioPreferences(
                                        (WifiPrivacyPageProviderKt$WifiPrivacyPage$1$1$1$1)
                                                rememberedValue2,
                                        composer2,
                                        8);
                                WifiConfiguration wifiConfiguration =
                                        wifiEntry2.getWifiConfiguration();
                                composerImpl2.startReplaceGroup(-1635101479);
                                if (wifiConfiguration != null) {
                                    WifiPrivacyPageProviderKt.DeviceNameSwitchPreference(
                                            wifiConfiguration, composer2, 8);
                                }
                                composerImpl2.end(false);
                                composerImpl2.end(true);
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                384,
                2);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.wifi.details2.WifiPrivacyPageProviderKt$WifiPrivacyPage$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            WifiPrivacyPageProviderKt.WifiPrivacyPage(
                                    WifiEntry.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
