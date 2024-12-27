package com.sec.android.app.swlpcontract;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.samsung.android.gtscell.data.FieldName;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SWLPContract {
    public static final Uri URI_AA_GET;
    public static final Uri URI_DIAGNONSENSITIVE_GET;
    public static final Uri URI_DIAG_GET;
    public static final Uri URI_DIAG_VERSION;
    public static final Uri URI_EULA_GET =
            Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/eulaprovider/get");
    public static final Uri URI_EULA_VERSION;
    public static final Uri URI_PP_VERSION;

    static {
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/eulaprovider/agree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/eulaprovider/disagree");
        URI_EULA_VERSION =
                Uri.parse(
                        "content://com.sec.android.app.setupwizardlegalprovider/eulaprovider/version");
        URI_DIAG_GET =
                Uri.parse(
                        "content://com.sec.android.app.setupwizardlegalprovider/diagprovider/get");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/diagprovider/agree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/diagprovider/disagree");
        URI_DIAG_VERSION =
                Uri.parse(
                        "content://com.sec.android.app.setupwizardlegalprovider/diagprovider/version");
        URI_AA_GET =
                Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/aaprovider/get");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/aaprovider/agree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/aaprovider/disagree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/aaprovider/version");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/ppprovider/get");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/ppprovider/agree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/ppprovider/disagree");
        URI_PP_VERSION =
                Uri.parse(
                        "content://com.sec.android.app.setupwizardlegalprovider/ppprovider/version");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/gdgpprovider/get");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/gdgpprovider/agree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/gdgpprovider/disagree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/gdgpprovider/version");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/uspdprovider/get");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/uspdprovider/agree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/uspdprovider/disagree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/uspdprovider/version");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/cbtprovider/get");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/cbtprovider/agree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/cbtprovider/disagree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/cbtprovider/version");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/cpp2provider/get");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/cpp2provider/agree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/cpp2provider/disagree");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/cpp2provider/version");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/diagcbt2provider/get");
        Uri.parse("content://com.sec.android.app.setupwizardlegalprovider/diagcbt2provider/agree");
        Uri.parse(
                "content://com.sec.android.app.setupwizardlegalprovider/diagcbt2provider/disagree");
        Uri.parse(
                "content://com.sec.android.app.setupwizardlegalprovider/diagcbt2provider/version");
        URI_DIAGNONSENSITIVE_GET =
                Uri.parse(
                        "content://com.sec.android.app.setupwizardlegalprovider/diagnonsensitive2provider/get");
        Uri.parse(
                "content://com.sec.android.app.setupwizardlegalprovider/diagnonsensitive2provider/agree");
        Uri.parse(
                "content://com.sec.android.app.setupwizardlegalprovider/diagnonsensitive2provider/disagree");
        Uri.parse(
                "content://com.sec.android.app.setupwizardlegalprovider/diagnonsensitive2provider/version");
        Uri.parse(
                "content://com.sec.android.app.setupwizardlegalprovider/diagsensitiveprovider/get");
        Uri.parse(
                "content://com.sec.android.app.setupwizardlegalprovider/diagsensitiveprovider/agree");
        Uri.parse(
                "content://com.sec.android.app.setupwizardlegalprovider/diagsensitiveprovider/disagree");
        Uri.parse(
                "content://com.sec.android.app.setupwizardlegalprovider/diagsensitiveprovider/version");
    }

    public static int getProcessByUri(Uri uri) {
        int i = -1;
        if (uri.getAuthority().contains("com.sec.android.app.setupwizardlegalprovider")) {
            int i2 =
                    uri.getPath().contains("eulaprovider")
                            ? 10
                            : uri.getPath().contains("diagprovider")
                                    ? 40
                                    : uri.getPath().contains("ppprovider")
                                            ? 20
                                            : uri.getPath().contains("aaprovider")
                                                    ? 30
                                                    : uri.getPath().contains("gdgpprovider")
                                                            ? 50
                                                            : uri.getPath().contains("uspdprovider")
                                                                    ? 60
                                                                    : uri.getPath()
                                                                                    .contains(
                                                                                            "cbtprovider")
                                                                            ? 70
                                                                            : uri.getPath()
                                                                                            .contains(
                                                                                                    "cpp2provider")
                                                                                    ? 80
                                                                                    : uri.getPath()
                                                                                                    .contains(
                                                                                                            "diagcbt2provider")
                                                                                            ? 90
                                                                                            : uri.getPath()
                                                                                                            .contains(
                                                                                                                    "diagnonsensitive2provider")
                                                                                                    ? 100
                                                                                                    : uri.getPath()
                                                                                                                    .contains(
                                                                                                                            "diagsensitiveprovider")
                                                                                                            ? 110
                                                                                                            : -1;
            if (i2 <= 0) {
                i = i2;
            } else if (uri.getLastPathSegment().equals("get")) {
                i = i2 + 1;
            } else if (uri.getLastPathSegment().equals("agree")) {
                i = i2 + 2;
            } else if (uri.getLastPathSegment().equals("disagree")) {
                i = i2 + 3;
            } else if (uri.getLastPathSegment().equals(FieldName.VERSION)) {
                i = i2 + 4;
            }
        }
        Log.d("SWLProvider::SWLPContract", "Uri:" + uri.toString() + " > process: " + i);
        return i;
    }

    public static String getStringByUri(Context context, Uri uri, boolean z) {
        if (getProcessByUri(uri) % 10 != 1) {
            Log.e(
                    "SWLProvider::SWLPContract",
                    "this is not a granted get action("
                            + uri.toString()
                            + ") from "
                            + context.getPackageName());
            return null;
        }
        Cursor query =
                context.getContentResolver()
                        .query(
                                uri,
                                null,
                                context.getPackageName(),
                                z ? new String[] {"requestLastest"} : null,
                                null);
        if (query == null || !query.moveToFirst()) {
            Log.i("SWLProvider::SWLPContract", "no saved, no raw-data");
            return null;
        }
        String string = query.getString(0);
        query.close();
        Log.i("SWLProvider::SWLPContract", "getString success");
        return string;
    }

    public static String getVersionByUri(Context context, Uri uri, boolean z) {
        if (getProcessByUri(uri) % 10 == 4) {
            Cursor query =
                    context.getContentResolver()
                            .query(
                                    uri,
                                    null,
                                    context.getPackageName(),
                                    z ? new String[] {"requestLastest"} : null,
                                    null);
            if (query != null && query.moveToFirst()) {
                Log.i("SWLProvider::SWLPContract", "read Version done");
                String string = query.getString(0);
                query.close();
                return string;
            }
            Log.e("SWLProvider::SWLPContract", "no version is found");
        }
        Log.e(
                "SWLProvider::SWLPContract",
                "this is not a granted ver action("
                        + uri.toString()
                        + ") from "
                        + context.getPackageName());
        return null;
    }
}
