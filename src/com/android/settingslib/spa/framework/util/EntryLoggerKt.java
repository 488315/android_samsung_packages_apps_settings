package com.android.settingslib.spa.framework.util;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.OpaqueKey;
import androidx.core.os.BundleKt;

import com.android.settings.spa.SettingsSpaEnvironment;
import com.android.settingslib.spa.framework.common.EntryData;
import com.android.settingslib.spa.framework.common.LogCategory;
import com.android.settingslib.spa.framework.common.LogEvent;
import com.android.settingslib.spa.framework.common.SettingsEntryKt;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;

import com.samsung.android.settings.accessibility.vision.routine.ReduceBrightnessRoutineActionHandler;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class EntryLoggerKt {
    public static final Function2 logEntryEvent(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1698801664);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal =
                SettingsEntryKt.LocalEntryDataProvider;
        final String entryId =
                ((EntryData) composerImpl.consume(dynamicProvidableCompositionLocal)).getEntryId();
        if (entryId == null) {
            EntryLoggerKt$logEntryEvent$entryId$1 entryLoggerKt$logEntryEvent$entryId$1 =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.framework.util.EntryLoggerKt$logEntryEvent$entryId$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Intrinsics.checkNotNullParameter(
                                    (LogEvent) obj, "<anonymous parameter 0>");
                            Intrinsics.checkNotNullParameter(
                                    (Bundle) obj2, "<anonymous parameter 1>");
                            return Unit.INSTANCE;
                        }
                    };
            composerImpl.end(false);
            return entryLoggerKt$logEntryEvent$entryId$1;
        }
        final Bundle arguments =
                ((EntryData) composerImpl.consume(dynamicProvidableCompositionLocal))
                        .getArguments();
        Function2 function2 =
                new Function2() { // from class:
                                  // com.android.settingslib.spa.framework.util.EntryLoggerKt$logEntryEvent$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        LogEvent event = (LogEvent) obj;
                        Bundle extraData = (Bundle) obj2;
                        Intrinsics.checkNotNullParameter(event, "event");
                        Intrinsics.checkNotNullParameter(extraData, "extraData");
                        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
                        if (spaEnvironment == null) {
                            throw new UnsupportedOperationException("Spa environment is not set");
                        }
                        SettingsSpaEnvironment settingsSpaEnvironment =
                                (SettingsSpaEnvironment) spaEnvironment;
                        String str = entryId;
                        LogCategory logCategory = LogCategory.VIEW;
                        Bundle bundle = arguments;
                        if (bundle != null) {
                            extraData.putAll(bundle);
                        }
                        settingsSpaEnvironment.logger.event(str, event, logCategory, extraData);
                        return Unit.INSTANCE;
                    }
                };
        composerImpl.end(false);
        return function2;
    }

    public static final Function0 wrapOnClickWithLog(final Function0 function0, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1713499887);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (function0 == null) {
            composerImpl.end(false);
            return null;
        }
        final Function2 logEntryEvent = logEntryEvent(composerImpl);
        composerImpl.startReplaceGroup(728876877);
        boolean changed = composerImpl.changed(logEntryEvent) | composerImpl.changed(function0);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.framework.util.EntryLoggerKt$wrapOnClickWithLog$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Function2.this.invoke(LogEvent.ENTRY_CLICK, new Bundle(0));
                            function0.mo1068invoke();
                            return Unit.INSTANCE;
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue);
        }
        Function0 function02 = (Function0) rememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        return function02;
    }

    public static final Function1 wrapOnSwitchWithLog(
            final Function1 function1, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(162536807);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (function1 == null) {
            composerImpl.end(false);
            return null;
        }
        final Function2 logEntryEvent = logEntryEvent(composerImpl);
        composerImpl.startReplaceGroup(1005531761);
        boolean changed =
                ((((i & 14) ^ 6) > 4 && composerImpl.changed(function1)) || (i & 6) == 4)
                        | composerImpl.changed(logEntryEvent);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new Function1() { // from class:
                                      // com.android.settingslib.spa.framework.util.EntryLoggerKt$wrapOnSwitchWithLog$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            Boolean bool = (Boolean) obj;
                            bool.booleanValue();
                            logEntryEvent.invoke(
                                    LogEvent.ENTRY_SWITCH,
                                    BundleKt.bundleOf(
                                            new Pair(
                                                    ReduceBrightnessRoutineActionHandler.KEY_SWITCH,
                                                    bool)));
                            function1.invoke(bool);
                            return Unit.INSTANCE;
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue);
        }
        Function1 function12 = (Function1) rememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        return function12;
    }
}
