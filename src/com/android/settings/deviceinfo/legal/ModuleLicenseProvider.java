package com.android.settings.deviceinfo.legal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.core.util.Preconditions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.GZIPInputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ModuleLicenseProvider extends ContentProvider {
    public static final /* synthetic */ int $r8$clinit = 0;

    public static void checkUri(Context context, Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (!"content".equals(uri.getScheme())
                || !"com.android.settings.module_licenses".equals(uri.getAuthority())
                || pathSegments == null
                || pathSegments.size() != 2
                || !"NOTICE.html".equals(pathSegments.get(1))) {
            throw new IllegalArgumentException(uri + "is not a valid URI");
        }
        try {
            context.getPackageManager().getModuleInfo(pathSegments.get(0), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(uri + "is not a valid URI", e);
        }
    }

    public static File getCachedHtmlFile(Context context, String str) {
        return new File(context.getCacheDir() + "/" + str, "NOTICE.html");
    }

    public static boolean isCachedHtmlFileOutdated(Context context, String str)
            throws PackageManager.NameNotFoundException {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("ModuleLicenseProvider", 0);
        File cachedHtmlFile = getCachedHtmlFile(context, str);
        return (sharedPreferences.contains(str)
                        && sharedPreferences.getLong(str, 0L)
                                == context.getPackageManager()
                                        .getPackageInfo(str, 1073741824)
                                        .getLongVersionCode()
                        && cachedHtmlFile.exists()
                        && cachedHtmlFile.length() != 0)
                ? false
                : true;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException();
    }

    public Context getModuleContext() {
        return getContext();
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        checkUri(getModuleContext(), uri);
        return "text/html";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException();
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final ParcelFileDescriptor openFile(Uri uri, String str) {
        Context moduleContext = getModuleContext();
        checkUri(moduleContext, uri);
        Preconditions.checkArgument("Read is the only supported mode", "r".equals(str));
        try {
            String str2 = uri.getPathSegments().get(0);
            File cachedHtmlFile = getCachedHtmlFile(moduleContext, str2);
            if (isCachedHtmlFileOutdated(moduleContext, str2)) {
                PackageManager packageManager = moduleContext.getPackageManager();
                GZIPInputStream gZIPInputStream =
                        new GZIPInputStream(
                                packageManager
                                        .getResourcesForApplication(
                                                packageManager.getPackageInfo(str2, 1073741824)
                                                        .applicationInfo)
                                        .getAssets()
                                        .open("NOTICE.html.gz"));
                try {
                    File file = new File(moduleContext.getCacheDir(), str2);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    Files.copy(
                            gZIPInputStream,
                            cachedHtmlFile.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);
                    gZIPInputStream.close();
                    moduleContext
                            .getSharedPreferences("ModuleLicenseProvider", 0)
                            .edit()
                            .putLong(
                                    str2,
                                    moduleContext
                                            .getPackageManager()
                                            .getPackageInfo(str2, 1073741824)
                                            .getLongVersionCode())
                            .commit();
                } finally {
                }
            }
            return ParcelFileDescriptor.open(cachedHtmlFile, 268435456);
        } catch (PackageManager.NameNotFoundException e) {
            Log.wtf("ModuleLicenseProvider", "checkUri should have already caught this error", e);
            return null;
        } catch (IOException e2) {
            Log.e("ModuleLicenseProvider", "Could not open file descriptor", e2);
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new UnsupportedOperationException();
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException();
    }
}
