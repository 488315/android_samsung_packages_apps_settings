package com.android.settingslib.spa.framework.common;

import android.content.Context;

import com.android.settings.spa.SettingsSpaEnvironment;
import com.android.settingslib.spa.slice.SettingsSliceDataRepository;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import java.util.LinkedHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SpaEnvironment {
    public final Context appContext;
    public final Lazy entryRepository;

    public SpaEnvironment(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.entryRepository =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.framework.common.SpaEnvironment$entryRepository$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return new SettingsEntryRepository(
                                        (SettingsPageProviderRepository)
                                                ((SettingsSpaEnvironment) SpaEnvironment.this)
                                                        .pageProviderRepository.getValue());
                            }
                        });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settingslib.spa.framework.common.SpaEnvironment$sliceDataRepository$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        SettingsEntryRepository entryRepository =
                                (SettingsEntryRepository)
                                        SpaEnvironment.this.entryRepository.getValue();
                        Intrinsics.checkNotNullParameter(entryRepository, "entryRepository");
                        SettingsSliceDataRepository settingsSliceDataRepository =
                                new SettingsSliceDataRepository();
                        new LinkedHashMap();
                        return settingsSliceDataRepository;
                    }
                });
        Context applicationContext = context.getApplicationContext();
        this.appContext = applicationContext != null ? applicationContext : context;
    }
}
