package com.android.settingslib.spaprivileged.template.app;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settingslib.spaprivileged.framework.compose.StringResourcesKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppStorageSizeKt {
    public static final Long calculateSizeBytes(Context context, ApplicationInfo applicationInfo) {
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService((Class<Object>) StorageStatsManager.class);
        Intrinsics.checkNotNull(systemService);
        try {
            StorageStats queryStatsForPackage =
                    ((StorageStatsManager) systemService)
                            .queryStatsForPackage(
                                    applicationInfo.storageUuid,
                                    applicationInfo.packageName,
                                    ApplicationInfosKt.getUserHandle(applicationInfo));
            Intrinsics.checkNotNullExpressionValue(
                    queryStatsForPackage, "queryStatsForPackage(...)");
            return Long.valueOf(queryStatsForPackage.codeBytes + queryStatsForPackage.dataBytes);
        } catch (Exception e) {
            Log.w("AppStorageSize", "Failed to query stats: " + e);
            return null;
        }
    }

    public static final MutableState getStorageSize(
            ApplicationInfo applicationInfo, Composer composer) {
        Intrinsics.checkNotNullParameter(applicationInfo, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(714558522);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(-477941234);
        boolean changed = composerImpl.changed(applicationInfo);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    FlowKt.flowOn(
                            new SafeFlow(
                                    new AppStorageSizeKt$getStorageSize$1$1(
                                            context, applicationInfo, null)),
                            Dispatchers.IO);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        (Flow) rememberedValue,
                        StringResourcesKt.placeholder(composerImpl),
                        composerImpl,
                        8);
        composerImpl.end(false);
        return collectAsStateWithLifecycle;
    }
}
