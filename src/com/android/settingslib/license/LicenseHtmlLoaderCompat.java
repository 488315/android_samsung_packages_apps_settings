package com.android.settingslib.license;

import android.content.Context;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.utils.AsyncLoaderCompat;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LicenseHtmlLoaderCompat extends AsyncLoaderCompat {
    public static final String[] DEFAULT_LICENSE_XML_PATHS = {
        "/system/etc/NOTICE.xml.gz",
        "/vendor/etc/NOTICE.xml.gz",
        "/odm/etc/NOTICE.xml.gz",
        "/oem/etc/NOTICE.xml.gz",
        "/product/etc/NOTICE.xml.gz",
        "/system_ext/etc/NOTICE.xml.gz",
        "/vendor_dlkm/etc/NOTICE.xml.gz",
        "/odm_dlkm/etc/NOTICE.xml.gz"
    };
    public final Context mContext;

    public LicenseHtmlLoaderCompat(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        PrintWriter printWriter;
        InputStreamReader inputStreamReader;
        ArrayList arrayList = new ArrayList();
        String[] strArr = DEFAULT_LICENSE_XML_PATHS;
        for (int i = 0; i < 8; i++) {
            File file = new File(strArr[i]);
            if (file.exists() && file.length() != 0) {
                arrayList.add(file);
            }
        }
        if (arrayList.isEmpty()) {
            Log.e("LicenseHtmlLoaderCompat", "No notice file exists.");
            return null;
        }
        File file2 = new File(this.mContext.getCacheDir(), "NOTICE.html");
        if (file2.exists() && file2.length() != 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (file2.lastModified() < ((File) it.next()).lastModified()) {}
            }
            return file2;
        }
        String string = this.mContext.getString(R.string.notice_header);
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            File file3 = (File) it2.next();
            if (file3 != null && file3.exists() && file3.length() != 0) {
                try {
                    InputStreamReader inputStreamReader2 =
                            file3.getName().endsWith(".gz")
                                    ? new InputStreamReader(
                                            new GZIPInputStream(new FileInputStream(file3)))
                                    : new FileReader(file3);
                    try {
                        LicenseHtmlGeneratorFromXml.parse(inputStreamReader2, hashMap, hashMap2);
                        inputStreamReader2.close();
                    } catch (IOException | XmlPullParserException e) {
                        inputStreamReader = inputStreamReader2;
                        e = e;
                        Log.e("LicenseGeneratorFromXml", "Failed to parse " + file3, e);
                        if (inputStreamReader != null) {
                            try {
                                inputStreamReader.close();
                            } catch (IOException unused) {
                                Log.w("LicenseGeneratorFromXml", "Failed to close " + file3);
                            }
                        }
                    }
                } catch (IOException | XmlPullParserException e2) {
                    e = e2;
                    inputStreamReader = null;
                }
            }
        }
        if (hashMap.isEmpty() || hashMap2.isEmpty()) {
            return null;
        }
        try {
            printWriter = new PrintWriter(file2);
        } catch (IOException | SecurityException e3) {
            e = e3;
            printWriter = null;
        }
        try {
            LicenseHtmlGeneratorFromXml.generateHtml(
                    arrayList, hashMap, hashMap2, printWriter, string);
            printWriter.flush();
            printWriter.close();
            return file2;
        } catch (IOException | SecurityException e4) {
            e = e4;
            Log.e("LicenseGeneratorFromXml", "Failed to generate " + file2, e);
            if (printWriter == null) {
                return null;
            }
            printWriter.close();
            return null;
        }
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}
}
