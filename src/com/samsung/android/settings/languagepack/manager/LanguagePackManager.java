package com.samsung.android.settings.languagepack.manager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.text.TextUtils;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;

import com.android.settings.Utils;

import com.google.gson.Gson;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.sdk.scs.ai.asr.Environment;
import com.samsung.android.settings.languagepack.appsstub.RequestStubApi;
import com.samsung.android.settings.languagepack.constant.LanguagePackConstant;
import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.data.LanguageInfo$$ExternalSyntheticLambda2;
import com.samsung.android.settings.languagepack.data.PackageInfo;
import com.samsung.android.settings.languagepack.gdiff.GDiffPatcher;
import com.samsung.android.settings.languagepack.installer.ApkInstaller;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.service.LanguagePackDownloadService;
import com.samsung.android.settings.languagepack.utils.LanguagePackUtils;
import com.sec.ims.configuration.DATA;
import com.sec.ims.settings.ImsProfile;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilderFactory;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackManager {
    private static final String AT_SIGN = "@";
    private static final int BUFFER_SIZE = 8192;
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final boolean DEBUG = false;
    private static final String DOWNLOAD_DIRECTORY = "LanguagePack";
    private static final int PROGRESS_UPDATE_STEP = 1137664;
    private static final int READ_TIMEOUT = 10000;
    private static final String SEPARATOR_REGEX = ":";
    private static final String TAG = "LanguagePackManager";
    private static final int WRITE_FILE_CANCEL = 2;
    private static final int WRITE_FILE_FAILURE = 1;
    private static final int WRITE_FILE_PAUSE = 3;
    private static final int WRITE_FILE_SUCCESS = 0;
    private static LanguagePackManager sInstance;
    private Context mContext;
    long mLastMakeLanguageListTime;
    private LangPackDownloadListener mListener;
    private LangPackVersionUpdateListener mVersionUpdateListener;
    final int MAX_LOG_LENGTH = ImsProfile.DEFAULT_DEREG_TIMEOUT;
    private Map<String, LanguageInfo> mLanguageInfoMap = new ConcurrentHashMap();
    private Map<String, String> mLatestVersionMap = new ConcurrentHashMap();
    boolean mCancelFlag = false;
    private final Object mVersionUpdateLock = new Object();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface LangPackDownloadListener {
        void finishUninstall(LanguageInfo languageInfo);

        void notifyUpdateStatus(
                LanguagePackDownloadService.Status status, LanguageInfo languageInfo);

        void publishProgress(long j, LanguageInfo languageInfo);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface LangPackVersionUpdateListener {
        void finishUpdate();

        void updateLatestVersion(String str, String str2);
    }

    public static PackageInfo $r8$lambda$AZRpAaaiFzu2x4MhN8lGEMhuapw(
            LanguagePackManager languagePackManager,
            List list,
            LanguagePack languagePack,
            TtsInfo ttsInfo) {
        String[] strArr;
        boolean equals;
        languagePackManager.getClass();
        PackageInfo packageInfo =
                new PackageInfo(
                        ttsInfo.mName, TextUtils.equals("samsungtts", ttsInfo.mTarget) ? 4 : 2);
        ((ArrayList) packageInfo.mVariant).addAll(ttsInfo.mVariants);
        String str = languagePack.mLanguageCode;
        List list2 = packageInfo.mVariant;
        Iterator it = ((ArrayList) list).iterator();
        while (true) {
            if (!it.hasNext()) {
                strArr = null;
                break;
            }
            String[] strArr2 = (String[]) it.next();
            String str2 = strArr2[0];
            String str3 = strArr2[1];
            String str4 = strArr2[2];
            String str5 = strArr2[3];
            String str6 = strArr2[4];
            String[] split = str2.split("[-_]");
            String[] split2 = str.split("[-_]");
            String str7 = split2[0];
            if (split2.length > 1) {
                Locale locale = new Locale(str7, split2[1]);
                equals =
                        split.length > 1
                                && locale.getISO3Language().equals(split[0])
                                && locale.getISO3Country().equals(split[1]);
            } else {
                equals = new Locale(str7).getISO3Language().equals(split[0]);
            }
            if (equals) {
                String str8 = "true".equals(str4) ? "multi" : "single";
                ArrayList arrayList = (ArrayList) list2;
                String str9 = arrayList.size() > 1 ? "multi" : "single";
                if (str8.equals(str9) && arrayList.contains(str3)) {
                    String str10 = TAG;
                    StringBuilder m =
                            SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                    "preloaded tts pack. locale = ",
                                    str,
                                    ", type = ",
                                    str9,
                                    ", variant = ");
                    ConstraintWidget$$ExternalSyntheticOutline0.m(
                            m, str3, ", version = ", str5, ", version name = ");
                    m.append(str6);
                    Log.i(str10, m.toString());
                    strArr = new String[] {str5, str6};
                    break;
                }
            }
        }
        if (strArr != null) {
            packageInfo.mPreloadedVersionCode = convertStringToLong(strArr[0]);
            packageInfo.mPreloadedVersionName = strArr[1];
            packageInfo.mIsPreloaded = true;
        }
        return packageInfo;
    }

    /* renamed from: $r8$lambda$DzXnHny8MJoNnQYzR88feiog0-w, reason: not valid java name */
    public static boolean m1245$r8$lambda$DzXnHny8MJoNnQYzR88feiog0w(
            LanguagePackManager languagePackManager, PackageInfo packageInfo) {
        languagePackManager.getClass();
        return !TextUtils.isEmpty(packageInfo.mPkgName)
                && packageInfo.isPackageInstalled(languagePackManager.mContext);
    }

    public static /* synthetic */ void $r8$lambda$Sezx9BbzOdJHrjfzecLGXwx9PTc(
            LanguagePackManager languagePackManager, PackageInfo packageInfo) {
        String str = languagePackManager.mLatestVersionMap.get(packageInfo.mPkgName);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        packageInfo.mLatestVersion = convertStringToLong(str);
    }

    public static /* synthetic */ void $r8$lambda$bKNyHMbzoepa90lkuraZgq7LrjA(
            LanguagePackManager languagePackManager, PackageInfo packageInfo) {
        String str = languagePackManager.mLatestVersionMap.get(packageInfo.mPkgName);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        packageInfo.mLatestVersion = Long.parseLong(str);
    }

    public static LanguageInfo $r8$lambda$noDwASTUeaavHNfVfZBM2knNJmk(
            LanguagePackManager languagePackManager,
            List list,
            final String str,
            final LanguagePack languagePack) {
        LanguageInfo languageInfo =
                languagePackManager.mLanguageInfoMap.get(languagePack.mLanguageCode);
        if (languageInfo == null) {
            Context context = languagePackManager.mContext;
            languageInfo = new LanguageInfo();
            languageInfo.mList = new CopyOnWriteArrayList();
            languageInfo.mSupportType = 0;
            languageInfo.mToastType = 0;
            languageInfo.mContext = context;
        }
        languageInfo.mOrder = languagePack.mOrder;
        languageInfo.mLanguageCode = languagePack.mLanguageCode.replace("_", "-");
        languageInfo.mLanguageDisplayName = languagePack.mDisplayName;
        languageInfo.mIsStandardTranslationLanguage = languagePack.mIsStandardLanguageCode;
        languageInfo.mShowLocale = languagePack.mShowLocale;
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(languagePack.mTranslationPkgName)) {
            String[] split = languagePack.mLanguageCode.split("[-_]");
            if (!SignalSeverity.NONE.equals(languagePack.mTranslationType)
                    && TextUtils.equals(split[0], "en")) {
                PackageInfo packageInfo = new PackageInfo(ApnSettings.MVNO_NONE, 8);
                packageInfo.mIsPreloaded = true;
                arrayList.add(packageInfo);
            }
        } else {
            arrayList.add(new PackageInfo(languagePack.mTranslationPkgName, 8));
        }
        arrayList.addAll(
                (Collection)
                        languagePack.mExtraTranslationPkgNames.stream()
                                .map(new LanguagePackManager$$ExternalSyntheticLambda9(1))
                                .collect(Collectors.toList()));
        if (languagePack.mSupportS2T && !TextUtils.isEmpty(languagePack.mS2tPkgName)) {
            arrayList.add(new PackageInfo(languagePack.mS2tPkgName, 16));
        }
        if (!TextUtils.isEmpty(languagePack.mAsrPkgName)) {
            arrayList.add(new PackageInfo(languagePack.mAsrPkgName, 1));
        }
        ArrayList arrayList2 = new ArrayList();
        if (!languagePack.mTtsPkgInfoList.isEmpty()) {
            final int i = 0;
            List list2 =
                    (List)
                            languagePack.mTtsPkgInfoList.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.settings.languagepack.manager.LanguagePack$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    TtsInfo ttsInfo = (TtsInfo) obj;
                                                    switch (i) {
                                                        case 0:
                                                            return !TextUtils.isEmpty(ttsInfo.mName)
                                                                    && "bixby"
                                                                            .equals(
                                                                                    ttsInfo.mTarget);
                                                        case 1:
                                                            return "multi".equals(ttsInfo.mType);
                                                        default:
                                                            return "single".equals(ttsInfo.mType);
                                                    }
                                                }
                                            })
                                    .collect(Collectors.toList());
            final int i2 = 1;
            Optional findFirst =
                    list2.stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.settings.languagepack.manager.LanguagePack$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            TtsInfo ttsInfo = (TtsInfo) obj;
                                            switch (i2) {
                                                case 0:
                                                    return !TextUtils.isEmpty(ttsInfo.mName)
                                                            && "bixby".equals(ttsInfo.mTarget);
                                                case 1:
                                                    return "multi".equals(ttsInfo.mType);
                                                default:
                                                    return "single".equals(ttsInfo.mType);
                                            }
                                        }
                                    })
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.settings.languagepack.manager.LanguagePack$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            LanguagePack languagePack2 = LanguagePack.this;
                                            String str2 = str;
                                            languagePack2.getClass();
                                            String str3 = ((TtsInfo) obj).mEngine;
                                            String[] split2 = str2.split("\\.");
                                            String[] split3 = str3.split("\\.");
                                            int min = Math.min(split2.length, split3.length);
                                            int i3 = 0;
                                            while (true) {
                                                if (i3 < min) {
                                                    int parseInt = Integer.parseInt(split2[i3]);
                                                    int parseInt2 = Integer.parseInt(split3[i3]);
                                                    if (parseInt < parseInt2) {
                                                        return false;
                                                    }
                                                    if (parseInt > parseInt2) {
                                                        break;
                                                    }
                                                    i3++;
                                                } else if (split2.length < split3.length) {
                                                    return false;
                                                }
                                            }
                                            return true;
                                        }
                                    })
                            .findFirst();
            TtsInfo ttsInfo = new TtsInfo();
            ttsInfo.mVariants = new ArrayList();
            final TtsInfo ttsInfo2 = (TtsInfo) findFirst.orElse(ttsInfo);
            if (!TextUtils.isEmpty(ttsInfo2.mName)) {
                arrayList2.add(ttsInfo2);
            }
            final int i3 = 2;
            arrayList2.addAll(
                    (Collection)
                            list2.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.settings.languagepack.manager.LanguagePack$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    TtsInfo ttsInfo3 = (TtsInfo) obj;
                                                    switch (i3) {
                                                        case 0:
                                                            return !TextUtils.isEmpty(
                                                                            ttsInfo3.mName)
                                                                    && "bixby"
                                                                            .equals(
                                                                                    ttsInfo3.mTarget);
                                                        case 1:
                                                            return "multi".equals(ttsInfo3.mType);
                                                        default:
                                                            return "single".equals(ttsInfo3.mType);
                                                    }
                                                }
                                            })
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.settings.languagepack.manager.LanguagePack$$ExternalSyntheticLambda4
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    TtsInfo ttsInfo3 = (TtsInfo) obj;
                                                    return (ttsInfo3.mVariants.isEmpty()
                                                                    || TtsInfo.this.mVariants
                                                                            .contains(
                                                                                    ttsInfo3
                                                                                            .mVariants
                                                                                            .get(
                                                                                                    0)))
                                                            ? false
                                                            : true;
                                                }
                                            })
                                    .collect(Collectors.toList()));
        }
        arrayList.addAll(
                (Collection)
                        arrayList2.stream()
                                .map(
                                        new LanguagePackManager$$ExternalSyntheticLambda2(
                                                languagePackManager, list, languagePack, 1))
                                .collect(Collectors.toList()));
        arrayList.stream()
                .filter(new LanguagePackManager$$ExternalSyntheticLambda0(2))
                .forEach(new LanguagePackManager$$ExternalSyntheticLambda4(languagePackManager, 1));
        languageInfo.mList.clear();
        languageInfo.mSupportType = 0;
        languageInfo.mList.addAll(arrayList);
        if (languageInfo.mList.stream().anyMatch(new LanguageInfo$$ExternalSyntheticLambda2(1))) {
            languageInfo.mSupportType |= 6;
        }
        if (languageInfo.mList.stream().anyMatch(new LanguageInfo$$ExternalSyntheticLambda2(2))) {
            languageInfo.mSupportType |= 6;
        }
        if (languageInfo.mList.stream().anyMatch(new LanguageInfo$$ExternalSyntheticLambda2(3))) {
            languageInfo.mSupportType |= 1;
        }
        if (languageInfo.mList.stream().anyMatch(new LanguageInfo$$ExternalSyntheticLambda2(4))) {
            languageInfo.mSupportType |= 8;
        }
        if (languageInfo.mList.stream().anyMatch(new LanguageInfo$$ExternalSyntheticLambda2(5))) {
            languageInfo.mSupportType |= 16;
        }
        return languageInfo;
    }

    /* renamed from: -$$Nest$mnotifyUninstallStatus, reason: not valid java name */
    public static void m1248$$Nest$mnotifyUninstallStatus(
            LanguagePackManager languagePackManager, LanguageInfo languageInfo) {
        languagePackManager.getClass();
        Log.d(TAG, "notifyUninstallStatus() : " + languageInfo.mLanguageCode);
        LangPackDownloadListener langPackDownloadListener = languagePackManager.mListener;
        if (langPackDownloadListener != null) {
            langPackDownloadListener.finishUninstall(languageInfo);
        }
    }

    public LanguagePackManager(Context context) {
        this.mContext = context;
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
    }

    public static long convertStringToLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return -1L;
        }
    }

    public static HttpURLConnection getHttpURLConnection(URL url, long j) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        if (httpURLConnection == null) {
            Log.e(TAG, "[getHttpURLConnection] HttpURLConnection is null!  ");
            return null;
        }
        if (j > 0) {
            httpURLConnection.setRequestProperty("Range", "bytes=" + j + "-");
        }
        httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();
        String str = TAG;
        Log.i(str, "[getHttpURLConnection] response code :  : " + responseCode);
        if (responseCode == 200 || responseCode == 206) {
            return httpURLConnection;
        }
        Log.e(str, "[getHttpURLConnection] Wrong response code :  : " + responseCode);
        return null;
    }

    public static LanguagePackManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LanguagePackManager(context.getApplicationContext());
        }
        return sInstance;
    }

    public static boolean wrongSignature(Context context, String str, String str2) {
        X509Certificate x509Certificate;
        Log.i(TAG, "[AsyncDownloader] Check signature validation ");
        try {
            android.content.pm.PackageInfo packageArchiveInfo =
                    context.getPackageManager().getPackageArchiveInfo(str, 64);
            if (packageArchiveInfo != null
                    && (x509Certificate =
                                    (X509Certificate)
                                            CertificateFactory.getInstance("X.509")
                                                    .generateCertificate(
                                                            new ByteArrayInputStream(
                                                                    packageArchiveInfo.signatures[0]
                                                                            .toByteArray())))
                            != null) {
                StringBuilder sb = new StringBuilder();
                for (byte b : x509Certificate.getSignature()) {
                    sb.append((int) b);
                }
                if (str2.equals(sb.toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        Log.e(TAG, "[AsyncDownloader] malformed or wrong APK! ");
        return true;
    }

    public final File apkFromDeltaFile(File file, String str, String str2) {
        GDiffPatcher gDiffPatcher = new GDiffPatcher();
        ByteBuffer allocate = ByteBuffer.allocate(2048);
        gDiffPatcher.buf = allocate;
        gDiffPatcher.buf2 = allocate.array();
        File file2 = new File(LanguagePackUtils.getApkSourceDir(this.mContext, str));
        File file3 =
                new File(
                        new File(
                                this.mContext.getFilesDir(),
                                ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                        new StringBuilder(DOWNLOAD_DIRECTORY),
                                        File.separator,
                                        str2)),
                        AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                str, "_repacked.apk"));
        String absolutePath = file2.getAbsolutePath();
        String absolutePath2 = file.getAbsolutePath();
        String absolutePath3 = file3.getAbsolutePath();
        android.util.Log.d("Gdiff", "start patching file");
        File file4 = new File(absolutePath);
        File file5 = new File(absolutePath2);
        File file6 = new File(absolutePath3);
        if (file4.length() > 2147483647L || file5.length() > 2147483647L) {
            android.util.Log.e("Gdiff", "source or patch is too large, max length is 2147483647");
            android.util.Log.e("Gdiff", "aborting..");
        } else {
            try {
                gDiffPatcher.patch(file4, file5, file6);
            } catch (IOException unused) {
                android.util.Log.e("Gdiff", "exception diff patch");
            }
            android.util.Log.d("Gdiff", "finished patching file");
        }
        try {
            if (!file.delete()) {
                Log.d(TAG, "Failed to delete a file");
            }
        } catch (SecurityException e) {
            Log.d(TAG, ApnSettings.MVNO_NONE + e.getLocalizedMessage());
        }
        return file3;
    }

    public final void deleteTemporalDownloadedFile(String str) {
        File file =
                new File(
                        this.mContext.getFilesDir(),
                        ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                new StringBuilder(DOWNLOAD_DIRECTORY), File.separator, str));
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    Log.e(TAG, "deleteTemporalDownloadedFile : " + file2.getName());
                    file2.delete();
                }
            }
            file.delete();
        }
    }

    public final LanguageInfo getLanguageInfo(String str) {
        return this.mLanguageInfoMap.get(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0091, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x008e, code lost:

       if (r1 == null) goto L18;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.ArrayList getPreloadedTTSList() {
        /*
            r8 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            android.content.Context r8 = r8.mContext     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            android.content.ContentResolver r2 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r8 = "content://com.samsung.SMT.LanguageProvider"
            android.net.Uri r3 = android.net.Uri.parse(r8)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r5 = "preload"
            java.lang.String r8 = "available_voices"
            java.lang.String[] r4 = new java.lang.String[]{r8}     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            if (r1 == 0) goto L6e
            r1.moveToFirst()     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
        L26:
            java.lang.String r8 = "locale"
            int r8 = r1.getColumnIndex(r8)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r8 = r1.getString(r8)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r2 = "variant"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r3 = "multiSpeaker"
            int r3 = r1.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r3 = r1.getString(r3)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r4 = "version"
            int r4 = r1.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r4 = r1.getString(r4)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r5 = "versionName"
            int r5 = r1.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String r5 = r1.getString(r5)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            java.lang.String[] r8 = new java.lang.String[]{r8, r2, r3, r4, r5}     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            r0.add(r8)     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            boolean r8 = r1.moveToNext()     // Catch: java.lang.Throwable -> L6a android.database.CursorIndexOutOfBoundsException -> L6c
            if (r8 != 0) goto L26
            goto L6e
        L6a:
            r8 = move-exception
            goto L92
        L6c:
            r8 = move-exception
            goto L74
        L6e:
            if (r1 == 0) goto L91
        L70:
            r1.close()
            goto L91
        L74:
            java.lang.String r2 = com.samsung.android.settings.languagepack.manager.LanguagePackManager.TAG     // Catch: java.lang.Throwable -> L6a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6a
            r3.<init>()     // Catch: java.lang.Throwable -> L6a
            java.lang.String r4 = "CursorIndexOutOfBoundsException : "
            r3.append(r4)     // Catch: java.lang.Throwable -> L6a
            java.lang.String r8 = r8.getMessage()     // Catch: java.lang.Throwable -> L6a
            r3.append(r8)     // Catch: java.lang.Throwable -> L6a
            java.lang.String r8 = r3.toString()     // Catch: java.lang.Throwable -> L6a
            com.samsung.android.settings.languagepack.logger.Log.d(r2, r8)     // Catch: java.lang.Throwable -> L6a
            if (r1 == 0) goto L91
            goto L70
        L91:
            return r0
        L92:
            if (r1 == 0) goto L97
            r1.close()
        L97:
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.languagepack.manager.LanguagePackManager.getPreloadedTTSList():java.util.ArrayList");
    }

    public final boolean isNewerLatestVersion(String str, String str2) {
        String str3 = this.mLatestVersionMap.get(str);
        return str3 == null || convertStringToLong(str2) > convertStringToLong(str3);
    }

    public final boolean isPausedState(String str) {
        LanguagePackDownloadService.Status status = this.mLanguageInfoMap.get(str).mStatus;
        return status == LanguagePackDownloadService.Status.STATUS_PAUSE
                || (LanguagePackUtils.isDirectoryExists(this.mContext, str)
                        && status != LanguagePackDownloadService.Status.STATUS_DOWNLOADING);
    }

    public final boolean isPossibleDeltaDownload(
            String str, String str2, String str3, String str4, String str5) {
        Set<String> stringSet =
                this.mContext
                        .getSharedPreferences("language_pack_delta_update", 0)
                        .getStringSet("language_pack_delta_update_fail_list", null);
        return (convertStringToLong(str3) <= 0
                        || TextUtils.isEmpty(str2)
                        || TextUtils.isEmpty(str4)
                        || TextUtils.isEmpty(str5)
                        || (stringSet != null && stringSet.contains(str)))
                ? false
                : true;
    }

    public final List makeLanguageList() {
        int i = 1;
        int i2 = 0;
        boolean z =
                System.currentTimeMillis() - this.mLastMakeLanguageListTime
                        > LanguagePackConstant.LANGUAGE_LIST_UPDATE_PERIOD_MILLIS;
        if (this.mLanguageInfoMap.isEmpty() || z) {
            String langpackConfigInfo = Environment.getLangpackConfigInfo(this.mContext);
            if (TextUtils.isEmpty(langpackConfigInfo)) {
                Log.d(TAG, "LanguagePack ConfigInfo is null.");
                return new ArrayList();
            }
            int i3 = 0;
            while (i3 < langpackConfigInfo.length()) {
                String str = TAG;
                int length = langpackConfigInfo.length();
                int i4 = i3 + ImsProfile.DEFAULT_DEREG_TIMEOUT;
                Log.d(str, langpackConfigInfo.substring(i3, Math.min(length, i4)));
                i3 = i4;
            }
            try {
                LanguagePack[] languagePackArr =
                        (LanguagePack[])
                                new Gson().fromJson(langpackConfigInfo, LanguagePack[].class);
                ArrayList preloadedTTSList = getPreloadedTTSList();
                Context context = this.mContext;
                StringBuilder sb = Utils.sBuilder;
                String str2 = null;
                if (context != null) {
                    try {
                        android.content.pm.PackageInfo packageInfo =
                                context.getPackageManager().getPackageInfo("com.samsung.SMT", 0);
                        if (packageInfo != null) {
                            str2 = packageInfo.versionName;
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
                List list =
                        (List)
                                Arrays.stream(languagePackArr)
                                        .filter(
                                                new LanguagePackManager$$ExternalSyntheticLambda0(
                                                        i2))
                                        .filter(
                                                new LanguagePackManager$$ExternalSyntheticLambda0(
                                                        i))
                                        .map(
                                                new LanguagePackManager$$ExternalSyntheticLambda2(
                                                        this, preloadedTTSList, str2, i2))
                                        .collect(Collectors.toList());
                this.mLanguageInfoMap.clear();
                list.stream()
                        .filter(new LanguagePackManager$$ExternalSyntheticLambda0(3))
                        .forEach(new LanguagePackManager$$ExternalSyntheticLambda4(this, i2));
                this.mLastMakeLanguageListTime = System.currentTimeMillis();
            } catch (Exception e) {
                Log.e(TAG, "failed to convert json to instance : " + e.getMessage());
            }
        }
        ((List) this.mLanguageInfoMap.values().stream().sorted().collect(Collectors.toList()))
                .forEach(new LanguagePackManager$$ExternalSyntheticLambda5(new AtomicInteger()));
        return new ArrayList(this.mLanguageInfoMap.values());
    }

    public final void notifyUpdateStatus(
            LanguageInfo languageInfo, LanguagePackDownloadService.Status status, int i) {
        Log.d(TAG, "notifyUpdateStatus() : " + languageInfo.mLanguageCode + " : " + status);
        if (status == LanguagePackDownloadService.Status.STATUS_ERROR
                || status == LanguagePackDownloadService.Status.STATUS_CANCEL) {
            deleteTemporalDownloadedFile(languageInfo.mLanguageCode);
        }
        languageInfo.mToastType = i | languageInfo.mToastType;
        this.mLanguageInfoMap.get(languageInfo.mLanguageCode).mStatus = status;
        LangPackDownloadListener langPackDownloadListener = this.mListener;
        if (langPackDownloadListener != null) {
            langPackDownloadListener.notifyUpdateStatus(status, languageInfo);
        }
    }

    public final boolean requestRecentPackageVersion(String str) {
        Log.d(TAG, "requestRecentPackageVersion total app Id = " + str);
        try {
            NodeList elementsByTagName =
                    DocumentBuilderFactory.newInstance()
                            .newDocumentBuilder()
                            .parse(
                                    new InputSource(
                                            new StringReader(
                                                    RequestStubApi.getInstance(this.mContext)
                                                            .getDownloadInfoByXML(str))))
                            .getElementsByTagName("appInfo");
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                Element element = (Element) elementsByTagName.item(i);
                String textContent = element.getElementsByTagName("appId").item(0).getTextContent();
                String textContent2 =
                        element.getElementsByTagName("versionCode").item(0).getTextContent();
                if (isNewerLatestVersion(textContent, textContent2)) {
                    updatePackageVersion(textContent, textContent2);
                }
            }
            return elementsByTagName.getLength() > 0;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return false;
        }
    }

    public final void setCancel() {
        this.mCancelFlag = true;
    }

    public final void setDownloadListener(LangPackDownloadListener langPackDownloadListener) {
        this.mListener = langPackDownloadListener;
    }

    public final void setVersionUpdateListener(
            LangPackVersionUpdateListener langPackVersionUpdateListener) {
        this.mVersionUpdateListener = langPackVersionUpdateListener;
    }

    public final void setVersionUpdateMap(Map map) {
        this.mLatestVersionMap = map;
        this.mLanguageInfoMap
                .values()
                .forEach(new LanguagePackManager$$ExternalSyntheticLambda4(this, 2));
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x03af A[Catch: all -> 0x0231, Exception -> 0x0235, IOException -> 0x03ab, TRY_LEAVE, TryCatch #10 {IOException -> 0x03ab, blocks: (B:162:0x03a7, B:153:0x03af), top: B:161:0x03a7 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:? A[Catch: all -> 0x0231, Exception -> 0x0235, SYNTHETIC, TRY_LEAVE, TryCatch #36 {Exception -> 0x0235, all -> 0x0231, blocks: (B:212:0x0225, B:194:0x023b, B:148:0x0263, B:140:0x0269, B:146:0x026d, B:136:0x0287, B:128:0x028d, B:134:0x0291, B:124:0x02a7, B:116:0x02ad, B:122:0x02b1, B:111:0x02c4, B:103:0x02ca, B:109:0x02ce, B:94:0x02d7, B:69:0x02dd, B:70:0x02e4, B:72:0x0306, B:75:0x0323, B:81:0x0331, B:84:0x034b, B:92:0x02e1, B:162:0x03a7, B:153:0x03af, B:158:0x03b6, B:157:0x03b3), top: B:193:0x023b }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03a7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0397 A[Catch: all -> 0x020c, Exception -> 0x0210, IOException -> 0x0393, TRY_LEAVE, TryCatch #27 {IOException -> 0x0393, blocks: (B:179:0x038f, B:169:0x0397), top: B:178:0x038f }] */
    /* JADX WARN: Removed duplicated region for block: B:177:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x038f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:207:0x03c9  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0143 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:298:0x050e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0437 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:302:0x0539 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0534 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:331:0x054b  */
    /* JADX WARN: Removed duplicated region for block: B:333:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0546 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:338:0x0541 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x015e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startDownloadAndInstall(
            com.samsung.android.settings.languagepack.service.LanguagePackDownloadService
                            .DownloadTask
                    r24) {
        /*
            Method dump skipped, instructions count: 1411
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.languagepack.manager.LanguagePackManager.startDownloadAndInstall(com.samsung.android.settings.languagepack.service.LanguagePackDownloadService$DownloadTask):void");
    }

    public final void startUninstall(String str, boolean z) {
        final LanguageInfo languageInfo = getLanguageInfo(str);
        ApkInstaller.InstallerCallbackListener installerCallbackListener =
                new ApkInstaller
                        .InstallerCallbackListener() { // from class:
                                                       // com.samsung.android.settings.languagepack.manager.LanguagePackManager.1
                    @Override // com.samsung.android.settings.languagepack.installer.ApkInstaller.InstallerCallbackListener
                    public final void uninstallFinish() {
                        LanguagePackManager.m1248$$Nest$mnotifyUninstallStatus(
                                LanguagePackManager.this, languageInfo);
                    }

                    @Override // com.samsung.android.settings.languagepack.installer.ApkInstaller.InstallerCallbackListener
                    public final void installFinish() {}
                };
        ArrayList arrayList = new ArrayList();
        Iterator it = languageInfo.mList.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (packageInfo.isPackageInstalled(this.mContext)) {
                String str2 = packageInfo.mPkgName;
                if (z && packageInfo.mType == 8) {
                    Log.d(TAG, "PackageInfo : " + str2 + " Skip removed.");
                } else {
                    arrayList.add(str2);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        ApkInstaller apkInstaller = new ApkInstaller(this.mContext, installerCallbackListener);
        apkInstaller.mMode = "uninstall";
        ((HashMap) apkInstaller.mPackages).clear();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            ((HashMap) apkInstaller.mPackages).put((String) it2.next(), ApnSettings.MVNO_NONE);
        }
        apkInstaller.mContext.registerReceiver(
                apkInstaller.mCallbackReceiver, new IntentFilter("UNINSTALLER_CALLBACK"), 2);
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            String str3 = (String) it3.next();
            Intent intent = new Intent("UNINSTALLER_CALLBACK");
            intent.setPackage(apkInstaller.mContext.getPackageName());
            intent.putExtra("android.content.pm.extra.PACKAGE_NAME", str3);
            apkInstaller
                    .mContext
                    .getPackageManager()
                    .getPackageInstaller()
                    .uninstall(
                            str3,
                            PendingIntent.getBroadcast(
                                            apkInstaller.mContext,
                                            str3.hashCode(),
                                            intent,
                                            201326592)
                                    .getIntentSender());
        }
    }

    public final void updateLatestPackageVersion(List list) {
        synchronized (this.mVersionUpdateLock) {
            int i = 0;
            boolean z = false;
            while (i < list.size()) {
                try {
                    int i2 = i + 10;
                    List subList = list.subList(i, Math.min(i2, list.size()));
                    StringBuilder sb = new StringBuilder();
                    Iterator it = subList.iterator();
                    while (it.hasNext()) {
                        String str =
                                ((String) it.next())
                                        + AT_SIGN
                                        + "-1"
                                        + AT_SIGN
                                        + DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                        if (sb.length() > 0) {
                            sb.append(SEPARATOR_REGEX);
                        }
                        sb.append(str);
                    }
                    z |= requestRecentPackageVersion(sb.toString());
                    i = i2;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z) {
                this.mVersionUpdateListener.finishUpdate();
                this.mLanguageInfoMap
                        .values()
                        .forEach(new LanguagePackManager$$ExternalSyntheticLambda4(this, 2));
            }
        }
    }

    public final void updatePackageVersion(String str, String str2) {
        this.mLatestVersionMap.put(str, str2);
        this.mVersionUpdateListener.updateLatestVersion(str, str2);
    }

    public final Map updatePackageVersionAndSize(List list) {
        String str;
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) list;
        Iterator it = copyOnWriteArrayList.iterator();
        String str2 = ApnSettings.MVNO_NONE;
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            try {
                str =
                        String.valueOf(
                                this.mContext
                                        .getPackageManager()
                                        .getPackageInfo(packageInfo.mPkgName, 0)
                                        .versionCode);
            } catch (PackageManager.NameNotFoundException e) {
                Log.d(TAG, "getVersionCode() Exception :: " + e.getMessage());
                str = "-1";
            }
            StringBuilder sb = new StringBuilder();
            String str3 = packageInfo.mPkgName;
            String m =
                    AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                            BackStackRecord$$ExternalSyntheticOutline0.m(sb, str3, AT_SIGN, str),
                            AT_SIGN,
                            "-1".equals(str)
                                    ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN
                                    : LanguagePackUtils.getSHA1DigestValue(this.mContext, str3));
            if (!str2.isEmpty()) {
                m =
                        AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                str2, SEPARATOR_REGEX, m);
            }
            str2 = m;
        }
        Log.d(TAG, "updatePackageVersionAndSize, total app Id = " + str2);
        HashMap hashMap = new HashMap();
        try {
            NodeList elementsByTagName =
                    DocumentBuilderFactory.newInstance()
                            .newDocumentBuilder()
                            .parse(
                                    new InputSource(
                                            new StringReader(
                                                    RequestStubApi.getInstance(this.mContext)
                                                            .getDownloadInfoByXML(str2))))
                            .getElementsByTagName("appInfo");
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                Element element = (Element) elementsByTagName.item(i);
                Node item = element.getElementsByTagName("appId").item(0);
                final String textContent =
                        item != null ? item.getTextContent() : ApnSettings.MVNO_NONE;
                Node item2 = element.getElementsByTagName("versionCode").item(0);
                final String textContent2 =
                        item2 != null ? item2.getTextContent() : ApnSettings.MVNO_NONE;
                Node item3 = element.getElementsByTagName("contentSize").item(0);
                String textContent3 =
                        item3 != null ? item3.getTextContent() : ApnSettings.MVNO_NONE;
                Node item4 = element.getElementsByTagName("deltaContentSize").item(0);
                String textContent4 =
                        item4 != null ? item4.getTextContent() : ApnSettings.MVNO_NONE;
                Node item5 = element.getElementsByTagName("installedBinaryHashValue").item(0);
                boolean isPossibleDeltaDownload =
                        isPossibleDeltaDownload(
                                textContent,
                                "deltaDownloadURI",
                                textContent4,
                                item5 != null ? item5.getTextContent() : ApnSettings.MVNO_NONE,
                                "updateBinaryHashValue");
                Log.d(
                        TAG,
                        "updatePackageVersionAndSize, isDelta = "
                                + isPossibleDeltaDownload
                                + "   PackageName = "
                                + textContent);
                copyOnWriteArrayList.stream()
                        .filter(new LanguagePackManager$$ExternalSyntheticLambda8(2, textContent))
                        .findFirst()
                        .ifPresent(
                                new Consumer() { // from class:
                                                 // com.samsung.android.settings.languagepack.manager.LanguagePackManager$$ExternalSyntheticLambda18
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        LanguagePackManager languagePackManager =
                                                LanguagePackManager.this;
                                        String str4 = textContent;
                                        String str5 = textContent2;
                                        PackageInfo packageInfo2 = (PackageInfo) obj;
                                        if (languagePackManager.isNewerLatestVersion(str4, str5)) {
                                            packageInfo2.mLatestVersion =
                                                    LanguagePackManager.convertStringToLong(str5);
                                            languagePackManager.updatePackageVersion(str4, str5);
                                        }
                                    }
                                });
                if (!TextUtils.isEmpty(textContent)) {
                    if (convertStringToLong(textContent4) > 0 && isPossibleDeltaDownload) {
                        textContent3 = textContent4;
                    }
                    hashMap.put(textContent, Long.valueOf(convertStringToLong(textContent3)));
                }
            }
        } catch (Exception e2) {
            Log.d(TAG, e2.getMessage());
        }
        return hashMap;
    }

    public final int writeTempFile(
            InputStream inputStream, FileOutputStream fileOutputStream, LanguageInfo languageInfo) {
        if (inputStream == null) {
            Log.e(TAG, "[writeTempFile] input stream is null");
            return 1;
        }
        long j = languageInfo.mCurrentDownloadedSize;
        Log.i(TAG, "[writeTempFile]  : is.available = " + inputStream.available());
        byte[] bArr = new byte[8192];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                languageInfo.mCurrentDownloadedSize = j;
                return 0;
            }
            fileOutputStream.write(bArr, 0, read);
            j += read;
            if (languageInfo.mTotalLanguagePackSize > 0) {
                int i2 = (int) j;
                if (i2 >= PROGRESS_UPDATE_STEP + i) {
                    long j2 = i2;
                    LangPackDownloadListener langPackDownloadListener = this.mListener;
                    if (langPackDownloadListener != null) {
                        languageInfo.mCurrentDownloadedSize = j2;
                        langPackDownloadListener.publishProgress(j2, languageInfo);
                    }
                    i = i2;
                }
                if (this.mCancelFlag) {
                    this.mCancelFlag = false;
                    return 2;
                }
                if (languageInfo.mStatus == LanguagePackDownloadService.Status.STATUS_PAUSE) {
                    return 3;
                }
            }
        }
    }
}
