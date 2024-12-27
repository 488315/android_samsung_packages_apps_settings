package com.android.settingslib.spa.framework.common;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsEntryKt {
    public static final DynamicProvidableCompositionLocal LocalEntryDataProvider =
            CompositionLocalKt.compositionLocalOf$default(
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.framework.common.SettingsEntryKt$LocalEntryDataProvider$1

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        /* renamed from: com.android.settingslib.spa.framework.common.SettingsEntryKt$LocalEntryDataProvider$1$1, reason: invalid class name */
                        public final class AnonymousClass1 implements EntryData {}

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new AnonymousClass1();
                        }
                    });
}
