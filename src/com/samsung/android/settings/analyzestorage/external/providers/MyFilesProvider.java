package com.samsung.android.settings.analyzestorage.external.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;

import com.samsung.android.settings.analyzestorage.data.database.repository.RepositoryFactory;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.external.database.repository.RepositoryFactoryImpl;
import com.samsung.android.settings.analyzestorage.external.injection.fileinfo.AnalyzeStorageFileInfoGenerator;
import com.samsung.android.settings.analyzestorage.external.injection.fileinfo.DefaultFileInfoGenerator;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/external/providers/MyFilesProvider;",
            "Landroid/content/ContentProvider;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class MyFilesProvider extends ContentProvider {
    public static final List FILTER_PACKAGE;
    public static final UriMatcher URI_MATCHER;

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        URI_MATCHER = uriMatcher;
        FILTER_PACKAGE =
                CollectionsKt__CollectionsKt.listOf(
                        (Object[])
                                new String[] {
                                    "com.sec.android.app.launcher", "com.samsung.android.settings"
                                });
        uriMatcher.addURI("com.samsung.android.settings.managestorage", "detailed_size", 100);
        uriMatcher.addURI("com.samsung.android.settings.managestorage", "top_package", 110);
        uriMatcher.addURI("com.samsung.android.settings.managestorage", "focused_view", 120);
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String method, String str, Bundle bundle) {
        Intrinsics.checkNotNullParameter(method, "method");
        return null;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        Log.d("SettingsMyFilesProvider", "onCreate()");
        final int i = 0;
        FileInfoFactory.sDefaultGeneratorSupplier =
                new Supplier() { // from class:
                                 // com.samsung.android.settings.analyzestorage.external.utils.SupplierUtils$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        switch (i) {
                            case 0:
                                return new DefaultFileInfoGenerator();
                            case 1:
                                return new AnalyzeStorageFileInfoGenerator();
                            default:
                                return new RepositoryFactoryImpl();
                        }
                    }
                };
        final int i2 = 1;
        ((ArrayList) FileInfoFactory.sGeneratorSupplierList)
                .add(
                        new Supplier() { // from class:
                                         // com.samsung.android.settings.analyzestorage.external.utils.SupplierUtils$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i2) {
                                    case 0:
                                        return new DefaultFileInfoGenerator();
                                    case 1:
                                        return new AnalyzeStorageFileInfoGenerator();
                                    default:
                                        return new RepositoryFactoryImpl();
                                }
                            }
                        });
        final int i3 = 2;
        RepositoryFactory.sGeneratorSupplier =
                new Supplier() { // from class:
                                 // com.samsung.android.settings.analyzestorage.external.utils.SupplierUtils$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        switch (i3) {
                            case 0:
                                return new DefaultFileInfoGenerator();
                            case 1:
                                return new AnalyzeStorageFileInfoGenerator();
                            default:
                                return new RepositoryFactoryImpl();
                        }
                    }
                };
        Context context = getContext();
        RepositoryFactory.getGenerator().getClass();
        RepositoryFactoryImpl.sContext = context;
        UserInfoManager.sUserInfoChecker = new UserInfoCheckerImpl();
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0296  */
    /* JADX WARN: Type inference failed for: r7v38, types: [java.util.List] */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.database.Cursor query(
            android.net.Uri r8,
            java.lang.String[] r9,
            java.lang.String r10,
            java.lang.String[] r11,
            java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 743
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.external.providers.MyFilesProvider.query(android.net.Uri,"
                    + " java.lang.String[], java.lang.String, java.lang.String[],"
                    + " java.lang.String):android.database.Cursor");
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }
}
