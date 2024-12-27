package com.samsung.android.settings.eternal.validate;

import android.content.ContentProviderClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.lib.episode.EpisodeConstant;
import com.samsung.android.lib.episode.EpisodeUtils;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.settings.eternal.data.SupplierInfo;
import com.samsung.android.settings.eternal.log.EternalFileLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RestoreValidationTask extends AsyncTask {
    public final AnonymousClass1 mComparator =
            new Comparator() { // from class:
                               // com.samsung.android.settings.eternal.validate.RestoreValidationTask.1
                public final Collator sCollator = Collator.getInstance();

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.sCollator.compare(
                            ((RestoreValidationResultItem) obj).mKey,
                            ((RestoreValidationResultItem) obj2).mKey);
                }
            };
    public RestoreValidationSource mRestoreValidationSource;
    public long mStartTime;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.eternal.validate.RestoreValidationTask$2, reason: invalid class name */
    public final class AnonymousClass2 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            String str = (String) obj;
            String str2 = (String) obj2;
            if ("value".equals(str)) {
                return -1;
            }
            if ("value".equals(str2)) {
                return 1;
            }
            return str.compareTo(str2);
        }
    }

    public static void decompressBundleIfNeeded(Scene scene) {
        ArrayList convertStringToArrayList;
        Bundle bundle = scene.mSceneValue;
        if (TextUtils.equals(scene.mSceneKey, "/GeneralInfo/PackageList")) {
            bundle.putString("value", EpisodeUtils.decompressString(bundle.getString("value")));
            return;
        }
        if (scene.mSceneValue.containsKey("compressedEternalItems")
                && (convertStringToArrayList =
                                EpisodeUtils.convertStringToArrayList(
                                        bundle.getString("compressedEternalItems")))
                        != null) {
            Iterator it = convertStringToArrayList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                bundle.putString(str, EpisodeUtils.decompressString(bundle.getString(str)));
            }
        }
    }

    public static JSONObject generateBaseJSON(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            if ("value".equals(str)) {
                str = ApnSettings.MVNO_NONE;
            }
            jSONObject.put("ValueOrAttrName", str);
            if (str2 == null) {
                str2 = "null";
            }
            jSONObject.put("Source", str2);
            if (str3 == null) {
                str3 = "null";
            }
            jSONObject.put("Target", str3);
            jSONObject.put("Result", ApnSettings.MVNO_NONE);
            jSONObject.put("Reason", ApnSettings.MVNO_NONE);
        } catch (JSONException e) {
            Log.e("Eternal/ValidationTask", "generateBaseJSON() failed : " + e.toString());
        }
        return jSONObject;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void confirmValidationResult(
            org.json.JSONObject r7,
            java.lang.String r8,
            boolean r9,
            boolean r10,
            com.samsung.android.lib.episode.Scene r11) {
        /*
            Method dump skipped, instructions count: 248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.validate.RestoreValidationTask.confirmValidationResult(org.json.JSONObject,"
                    + " java.lang.String, boolean, boolean,"
                    + " com.samsung.android.lib.episode.Scene):void");
    }

    @Override // android.os.AsyncTask
    public final Object doInBackground(Object[] objArr) {
        HashMap hashMap;
        RestoreValidationSource[] restoreValidationSourceArr = (RestoreValidationSource[]) objArr;
        EternalFileLog.d("Eternal/ValidationTask", "ValidationTask START");
        try {
            this.mStartTime = System.currentTimeMillis();
            RestoreValidationSource restoreValidationSource = restoreValidationSourceArr[0];
            this.mRestoreValidationSource = restoreValidationSource;
            hashMap = restoreValidationSource.mBaseSceneMap;
        } catch (Exception e) {
            EternalFileLog.e("Eternal/ValidationTask", "ValidationTask " + e);
        }
        if (hashMap != null && !hashMap.isEmpty()) {
            RestoreValidationSource restoreValidationSource2 = this.mRestoreValidationSource;
            RestoreValidationSource.generateSourceInfo(
                    restoreValidationSource2.mBaseSceneMap,
                    restoreValidationSource2.mBaseSourceInfo);
            RestoreValidationSource.generateSourceInfo(
                    restoreValidationSource2.mCompareSceneMap,
                    restoreValidationSource2.mCompareSourceInfo);
            writeResultToFile(fetchValidationInternal());
            loggingTaskResult(true);
            return Boolean.TRUE;
        }
        loggingTaskResult(false);
        return Boolean.FALSE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ff, code lost:

       if (r0 != null) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0103, code lost:

       if (r0 == null) goto L50;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.HashMap fetchValidationInternal() {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.validate.RestoreValidationTask.fetchValidationInternal():java.util.HashMap");
    }

    public final void loggingTaskResult(boolean z) {
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m(
                        "ValidationTask END. Validation Result = ", " / took ", z);
        m.append(System.currentTimeMillis() - this.mStartTime);
        EternalFileLog.d("Eternal/ValidationTask", m.toString());
    }

    public final boolean requestValidationToAppProvider(String str, Scene scene, Scene scene2) {
        RestoreValidationSource restoreValidationSource = this.mRestoreValidationSource;
        if (restoreValidationSource.mContext == null) {
            EternalFileLog.e(
                    "Eternal/ValidationTask", "requestValidationToAppProvider() context is null");
        } else {
            HashMap hashMap = restoreValidationSource.mSupplierMap;
            if (hashMap == null || hashMap.isEmpty()) {
                EternalFileLog.e(
                        "Eternal/ValidationTask",
                        "requestValidationToAppProvider() supplierMap is null");
            } else {
                if (this.mRestoreValidationSource.mSupplierMap.get(str) == null) {
                    EternalFileLog.e(
                            "Eternal/ValidationTask",
                            "requestValidationToAppProvider() SupplierInfo of uid - "
                                    + str
                                    + " is null");
                    return false;
                }
                Uri uri = ((SupplierInfo) this.mRestoreValidationSource.mSupplierMap.get(str)).mUri;
                if (uri == null) {
                    EternalFileLog.e(
                            "Eternal/ValidationTask",
                            "requestValidationToAppProvider() uri is null");
                    return false;
                }
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                arrayList.add(scene);
                arrayList.add(scene2);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("sceneList", arrayList);
                try {
                    ContentProviderClient acquireUnstableContentProviderClient =
                            this.mRestoreValidationSource
                                    .mContext
                                    .getContentResolver()
                                    .acquireUnstableContentProviderClient(uri);
                    try {
                        if (acquireUnstableContentProviderClient == null) {
                            EternalFileLog.e(
                                    "Eternal/ValidationTask",
                                    "requestValidationToAppProvider ["
                                            + uri.toString()
                                            + "] contentProviderClient is null");
                        } else {
                            String str2 = EpisodeConstant.DTD_VERSION;
                            Bundle call =
                                    acquireUnstableContentProviderClient.call(
                                            "validate", KnoxVpnPolicyConstants.NEW_FW, bundle);
                            if (call != null) {
                                boolean z = call.getBoolean("value");
                                acquireUnstableContentProviderClient.close();
                                return z;
                            }
                        }
                        if (acquireUnstableContentProviderClient != null) {
                            acquireUnstableContentProviderClient.close();
                        }
                    } finally {
                    }
                } catch (Exception e) {
                    EternalFileLog.d(
                            "Eternal/ValidationTask",
                            "requestValidationToAppProvider() - [" + uri + "] " + e.getMessage());
                }
            }
        }
        return false;
    }

    public final void writeResultToFile(HashMap hashMap) {
        StringBuffer stringBuffer = new StringBuffer(4096);
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            Set keySet = hashMap.keySet();
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(keySet);
            Collections.sort(arrayList);
            arrayList.remove("GeneralInfo");
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add("GeneralInfo");
            arrayList2.addAll(arrayList);
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                List list = (List) hashMap.get(str);
                Collections.sort(list, this.mComparator);
                JSONObject jSONObject2 = new JSONObject();
                JSONArray jSONArray2 = new JSONArray();
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    JSONObject jSONObject3 = ((RestoreValidationResultItem) it2.next()).mResultJSON;
                    if (jSONObject3 != null) {
                        jSONArray2.put(jSONObject3);
                    }
                }
                if (jSONArray2.length() > 0) {
                    jSONObject2.put(str, jSONArray2);
                }
                if (jSONObject2.length() > 0) {
                    jSONArray.put(jSONObject2);
                }
            }
            if (jSONArray.length() > 0) {
                jSONObject.put("Category", jSONArray);
            }
        } catch (JSONException e) {
            Log.e(
                    "Eternal/ValidationTask",
                    "generateResultJSONAfterSortingByKey() failed : " + e.toString());
        }
        stringBuffer.append(jSONObject.toString());
        if (stringBuffer.length() == 0) {
            return;
        }
        File file = new File("/data/log/eternal/");
        if (!file.exists() && !file.mkdirs()) {
            Log.e("Eternal/ValidationTask", "writeResultToFile() Failed to create directory");
            return;
        }
        String m =
                ComposerKt$$ExternalSyntheticOutline0.m(
                        "/data/log/eternal/eternalValidation_",
                        new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()),
                        ".log");
        try {
            Charset charset = StandardCharsets.UTF_8;
            FileWriter fileWriter = new FileWriter(m, charset, false);
            try {
                String stringBuffer2 = stringBuffer.toString();
                String substring = m.substring(m.length() - 16, m.length());
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(1, new SecretKeySpec(substring.getBytes(charset), "AES"));
                fileWriter.append(
                        (CharSequence)
                                Base64.getEncoder()
                                        .encodeToString(
                                                cipher.doFinal(stringBuffer2.getBytes(charset))));
                fileWriter.close();
            } finally {
            }
        } catch (Exception e2) {
            Log.e("Eternal/ValidationTask", "writeResultToFile() failed : " + e2.toString());
        }
    }
}
