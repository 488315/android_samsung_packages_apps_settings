package com.android.settings.spa.app.storage;

import android.content.pm.ApplicationInfo;

import com.android.settings.R;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class StorageType {
    public final Function1 filter;
    public final int titleResource;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Apps extends StorageType {
        public static final Apps INSTANCE =
                new Apps(
                        R.string.apps_storage,
                        new Function1() { // from class:
                                          // com.android.settings.spa.app.storage.StorageType.Apps.1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                AppRecordWithSize it = (AppRecordWithSize) obj;
                                Intrinsics.checkNotNullParameter(it, "it");
                                ApplicationInfo applicationInfo = it.app;
                                return Boolean.valueOf(
                                        (applicationInfo.flags & 33554432) == 0
                                                && applicationInfo.category != 0);
                            }
                        });
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Games extends StorageType {
        public static final Games INSTANCE =
                new Games(
                        R.string.game_storage_settings,
                        new Function1() { // from class:
                                          // com.android.settings.spa.app.storage.StorageType.Games.1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                AppRecordWithSize it = (AppRecordWithSize) obj;
                                Intrinsics.checkNotNullParameter(it, "it");
                                ApplicationInfo applicationInfo = it.app;
                                return Boolean.valueOf(
                                        (applicationInfo.flags & 33554432) != 0
                                                || applicationInfo.category == 0);
                            }
                        });
    }

    public StorageType(int i, Function1 function1) {
        this.titleResource = i;
        this.filter = function1;
    }
}
