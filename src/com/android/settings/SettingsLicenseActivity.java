package com.android.settings;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.android.settingslib.license.LicenseHtmlLoaderCompat;

import java.io.File;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SettingsLicenseActivity extends FragmentActivity
        implements LoaderManager.LoaderCallbacks {
    public Uri getUriFromGeneratedHtmlFile(File file) {
        return FileProvider.getUriForFile(this, file, "com.android.settings.files");
    }

    public boolean isFileValid(File file) {
        return file.exists() && file.length() != 0;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        File file = new File("/system/etc/NOTICE.html.gz");
        if (isFileValid(file)) {
            showHtmlFromUri(Uri.fromFile(file));
        } else {
            LoaderManager.getInstance(this).initLoader(0, Bundle.EMPTY, this);
        }
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader onCreateLoader(int i, Bundle bundle) {
        return new LicenseHtmlLoaderCompat(this);
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader loader, Object obj) {
        File file = (File) obj;
        if (file != null) {
            showHtmlFromUri(getUriFromGeneratedHtmlFile(file));
            return;
        }
        Log.e("SettingsLicenseActivity", "Failed to generate.");
        Toast.makeText(this, R.string.settings_license_activity_unavailable, 1).show();
        finish();
    }

    public final void showHtmlFromUri(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        intent.putExtra(
                "android.intent.extra.TITLE", getString(R.string.settings_license_activity_title));
        if ("content".equals(uri.getScheme())) {
            intent.addFlags(129);
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage("com.android.htmlviewer");
        try {
            startActivity(intent);
            finish();
        } catch (ActivityNotFoundException e) {
            Log.e("SettingsLicenseActivity", "Failed to find viewer", e);
            Toast.makeText(this, R.string.settings_license_activity_unavailable, 1).show();
            finish();
        }
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {}
}
