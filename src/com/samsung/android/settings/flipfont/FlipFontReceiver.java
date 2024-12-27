package com.samsung.android.settings.flipfont;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.secutil.Log;

import com.samsung.android.fontutil.FontWriter;
import com.samsung.android.fontutil.SemTypeface;
import com.samsung.android.fontutil.TypefaceParser;
import com.samsung.android.knox.net.apn.ApnSettings;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FlipFontReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public String mFlipFontsDisplayName;
    public String mFlipFontsName;
    public String mFlipFontsPackageName;
    public SemTypeface mTypeFace;
    public PackageManager mPackageManager = null;
    public final FontWriter mFontWriter = new FontWriter();

    public final void findTypefaces(AssetManager assetManager, String str) {
        InputStream openInputStream;
        InputStream open;
        try {
            String[] list = assetManager.list("xml");
            int i = 0;
            if (list != null && list.length != 0) {
                int length = list.length;
                while (i < length) {
                    String str2 = list[i];
                    try {
                        open = assetManager.open("xml/" + str2);
                    } catch (Exception e) {
                        Log.e(
                                "FlipFontReceiver",
                                "Not possible to open, continue to next file : " + e);
                    }
                    try {
                        parseTypefaceXml(str2, open, str);
                        if (open != null) {
                            open.close();
                        }
                        i++;
                    } catch (Throwable th) {
                        if (open != null) {
                            try {
                                open.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
                return;
            }
            String type =
                    this.mContext
                            .getContentResolver()
                            .getType(Uri.parse("content://" + str + "/fonts"));
            String[] split = (type == null || type.isEmpty()) ? null : type.split("\n");
            if (split == null || split.length == 0) {
                return;
            }
            int length2 = split.length;
            while (i < length2) {
                String str3 = split[i];
                try {
                    openInputStream =
                            this.mContext
                                    .getContentResolver()
                                    .openInputStream(
                                            Uri.parse("content://" + str + "/xml/" + str3));
                } catch (Exception e2) {
                    Log.e("FlipFontReceiver", "couldn't process that xml file " + e2);
                }
                try {
                    parseTypefaceXml(str3, openInputStream, str);
                    if (openInputStream != null) {
                        openInputStream.close();
                    }
                    i++;
                } catch (Throwable th3) {
                    if (openInputStream != null) {
                        try {
                            openInputStream.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                    }
                    throw th3;
                }
            }
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:93:0x0236 A[EDGE_INSN: B:93:0x0236->B:94:0x0236 BREAK  A[LOOP:2: B:60:0x016f->B:73:0x016f], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x016f A[SYNTHETIC] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReceive(android.content.Context r10, android.content.Intent r11) {
        /*
            Method dump skipped, instructions count: 592
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.flipfont.FlipFontReceiver.onReceive(android.content.Context,"
                    + " android.content.Intent):void");
    }

    public final void parseTypefaceXml(String str, InputStream inputStream, String str2) {
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            TypefaceParser typefaceParser = new TypefaceParser();
            xMLReader.setContentHandler(typefaceParser);
            xMLReader.parse(new InputSource(inputStream));
            this.mTypeFace = typefaceParser.getParsedData();
            if (str2.equals("com.monotype.android.font.samsungone")) {
                this.mTypeFace.setTypefaceFilename("SamsungOneUI-Regular.xml");
            } else {
                this.mTypeFace.setTypefaceFilename(str);
            }
            this.mTypeFace.setFontPackageName(str2);
            this.mFlipFontsName =
                    this.mTypeFace.getTypefaceFilename().replace(".xml", ApnSettings.MVNO_NONE);
            this.mFlipFontsDisplayName = this.mTypeFace.getName();
        } catch (Exception e) {
            Log.d("FlipFontReceiver", "File parsing is not possible. Omit this TypeFace. " + e);
        }
    }
}
