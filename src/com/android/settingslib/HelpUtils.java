package com.android.settingslib;

import android.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.samsung.android.gtscell.data.FieldName;

import java.net.URISyntaxException;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class HelpUtils {
    static final int MENU_HELP = 101;
    public static String sCachedVersionCode;

    public static void addIntentParameters(Context context, Intent intent, String str) {
        if (!intent.hasExtra("EXTRA_CONTEXT")) {
            intent.putExtra("EXTRA_CONTEXT", str);
        }
        Resources resources = context.getResources();
        if (resources.getBoolean(R.bool.config_sendPackageName)) {
            String[] strArr = {resources.getString(R.string.config_helpPackageNameKey)};
            String[] strArr2 = {resources.getString(R.string.config_helpPackageNameValue)};
            String string = resources.getString(R.string.config_helpIntentExtraKey);
            String string2 = resources.getString(R.string.config_helpIntentNameKey);
            String string3 = resources.getString(R.string.config_feedbackIntentExtraKey);
            String string4 = resources.getString(R.string.config_feedbackIntentNameKey);
            intent.putExtra(string, strArr);
            intent.putExtra(string2, strArr2);
            intent.putExtra(string3, strArr);
            intent.putExtra(string4, strArr2);
        }
        intent.putExtra("EXTRA_THEME", 3);
    }

    public static Intent getHelpIntent(Context context, String str, String str2) {
        if (Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) == 0) {
            return null;
        }
        try {
            Intent parseUri = Intent.parseUri(str, 3);
            addIntentParameters(context, parseUri, str2);
            if (parseUri.resolveActivity(context.getPackageManager()) != null) {
                return parseUri;
            }
            if (parseUri.hasExtra("EXTRA_BACKUP_URI")) {
                return getHelpIntent(context, parseUri.getStringExtra("EXTRA_BACKUP_URI"), str2);
            }
            return null;
        } catch (URISyntaxException unused) {
            Uri.Builder buildUpon = Uri.parse(str).buildUpon();
            buildUpon.appendQueryParameter("hl", Locale.getDefault().toString());
            String str3 = sCachedVersionCode;
            if (str3 == null) {
                try {
                    String l =
                            Long.toString(
                                    context.getPackageManager()
                                            .getPackageInfo(context.getPackageName(), 0)
                                            .getLongVersionCode());
                    sCachedVersionCode = l;
                    buildUpon.appendQueryParameter(FieldName.VERSION, l);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.wtf("HelpUtils", "Invalid package name for context", e);
                }
            } else {
                buildUpon.appendQueryParameter(FieldName.VERSION, str3);
            }
            Intent intent = new Intent("android.intent.action.VIEW", buildUpon.build());
            intent.setFlags(276824064);
            return intent;
        }
    }

    public static boolean prepareHelpMenuItem(
            final Activity activity, MenuItem menuItem, String str, String str2) {
        if (Settings.Global.getInt(activity.getContentResolver(), "device_provisioned", 0) == 0) {
            return false;
        }
        if (TextUtils.isEmpty(str)) {
            menuItem.setVisible(false);
            return false;
        }
        final Intent helpIntent = getHelpIntent(activity, str, str2);
        if (helpIntent == null) {
            menuItem.setVisible(false);
            return false;
        }
        menuItem.setOnMenuItemClickListener(
                new MenuItem
                        .OnMenuItemClickListener() { // from class:
                                                     // com.android.settingslib.HelpUtils.1
                    @Override // android.view.MenuItem.OnMenuItemClickListener
                    public final boolean onMenuItemClick(MenuItem menuItem2) {
                        try {
                            activity.startActivityForResult(helpIntent, 0);
                            return true;
                        } catch (ActivityNotFoundException unused) {
                            Log.e("HelpUtils", "No activity found for intent: " + helpIntent);
                            return true;
                        }
                    }
                });
        menuItem.setShowAsAction(2);
        menuItem.setVisible(true);
        return true;
    }
}
