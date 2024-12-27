package com.samsung.android.settings.eternal.defaultvalue;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.lib.episode.Scene;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class JSONManager {
    public static JSONObject createJSONWithScene(Scene scene) {
        if (scene == null || scene.mSceneValue == null) {
            Log.e("JSONManager", "createJSONWithScene() scene is empty");
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        Bundle bundle = scene.mSceneValue;
        if (bundle == null) {
            Log.e("JSONManager", "createJSONWithScene() valueBundle is null");
            return null;
        }
        for (String str : bundle.keySet()) {
            try {
                jSONObject.put(str, bundle.get(str));
            } catch (JSONException e) {
                Log.e("JSONManager", "createJSONWithScene() " + e.getMessage());
                return null;
            }
        }
        return jSONObject;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.HashMap getDefaultSceneHashMap() {
        /*
            java.lang.String r0 = "JSONManager"
            java.lang.String r1 = "/efs/sec_efs/SettingsBackup.json"
            boolean r2 = androidx.picker.widget.SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(r1)
            r3 = 0
            if (r2 != 0) goto Lc
            return r3
        Lc:
            java.io.FileReader r2 = new java.io.FileReader     // Catch: java.lang.Exception -> L3f
            java.io.File r4 = new java.io.File     // Catch: java.lang.Exception -> L3f
            r4.<init>(r1)     // Catch: java.lang.Exception -> L3f
            r2.<init>(r4)     // Catch: java.lang.Exception -> L3f
            r1 = 4096(0x1000, float:5.74E-42)
            char[] r4 = new char[r1]     // Catch: java.lang.Throwable -> L2a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2a
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L2a
        L1f:
            int r1 = r2.read(r4)     // Catch: java.lang.Throwable -> L2a
            r6 = -1
            if (r1 == r6) goto L2c
            r5.append(r4)     // Catch: java.lang.Throwable -> L2a
            goto L1f
        L2a:
            r1 = move-exception
            goto L45
        L2c:
            int r1 = r5.length()     // Catch: java.lang.Throwable -> L2a
            if (r1 <= 0) goto L41
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L2a
            java.lang.String r4 = r5.toString()     // Catch: java.lang.Throwable -> L2a
            r1.<init>(r4)     // Catch: java.lang.Throwable -> L2a
            r2.close()     // Catch: java.lang.Exception -> L3f
            goto L59
        L3f:
            r1 = move-exception
            goto L4e
        L41:
            r2.close()     // Catch: java.lang.Exception -> L3f
            goto L58
        L45:
            r2.close()     // Catch: java.lang.Throwable -> L49
            goto L4d
        L49:
            r2 = move-exception
            r1.addSuppressed(r2)     // Catch: java.lang.Exception -> L3f
        L4d:
            throw r1     // Catch: java.lang.Exception -> L3f
        L4e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "getDefaultJSONObjectFromFile() "
            r2.<init>(r4)
            com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0.m(r1, r2, r0)
        L58:
            r1 = r3
        L59:
            if (r1 != 0) goto L61
            java.lang.String r1 = "getDefaultSceneHashMap() - defaultHeader is null"
            android.util.Log.e(r0, r1)
            return r3
        L61:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "getDefaultSceneHashMap() - size : "
            r2.<init>(r4)
            int r4 = r1.length()
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r0, r2)
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            java.util.Iterator r4 = r1.keys()
            if (r4 != 0) goto L87
            java.lang.String r1 = "getDefaultSceneHashMap() - iterator is null"
            android.util.Log.e(r0, r1)
            return r3
        L87:
            boolean r5 = r4.hasNext()
            java.lang.String r6 = "getDefaultSceneHashMap() "
            if (r5 == 0) goto Lbf
            java.lang.Object r5 = r4.next()
            java.lang.String r5 = (java.lang.String) r5
            boolean r7 = android.text.TextUtils.isEmpty(r5)
            if (r7 == 0) goto L9c
            goto L87
        L9c:
            org.json.JSONObject r7 = r1.getJSONObject(r5)     // Catch: org.json.JSONException -> Laa
            com.samsung.android.lib.episode.Scene r7 = getSceneFromJSON(r5, r7)     // Catch: org.json.JSONException -> Laa
            if (r7 == 0) goto L87
            r2.put(r5, r7)     // Catch: org.json.JSONException -> Laa
            goto L87
        Laa:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r6)
            java.lang.String r1 = r1.getMessage()
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            android.util.Log.e(r0, r1)
            return r3
        Lbf:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r6)
            int r3 = r2.size()
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.defaultvalue.JSONManager.getDefaultSceneHashMap():java.util.HashMap");
    }

    public static Scene getSceneFromJSON(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        Scene.Builder builder = new Scene.Builder(str);
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            if (!TextUtils.isEmpty(next)) {
                try {
                    String string = jSONObject.getString(next);
                    if ("value".equals(next)) {
                        builder.setValue(string, false);
                    } else {
                        builder.addAttribute(string, next);
                    }
                } catch (JSONException e) {
                    Log.e("JSONManager", "getSceneFromJSON() " + e.getMessage());
                    return null;
                }
            }
        }
        return builder.build();
    }

    public static boolean updateDefaultJSONFile(List list) {
        HashMap defaultSceneHashMap = getDefaultSceneHashMap();
        if (defaultSceneHashMap != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Scene scene = (Scene) it.next();
                if (scene != null && defaultSceneHashMap.get(scene.mSceneKey) == null) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            new StringBuilder("mergeDefaultValue "),
                            scene.mSceneKey,
                            "JSONManager");
                    defaultSceneHashMap.put(scene.mSceneKey, scene);
                }
            }
            String[] strArr = XmlManager.DEFAULT_VALUE_BLOCK_LIST;
            for (int i = 0; i < 6; i++) {
                String str = strArr[i];
                if (!TextUtils.isEmpty(str) && ((Scene) defaultSceneHashMap.remove(str)) != null) {
                    AbsAdapter$$ExternalSyntheticOutline0.m(
                            "removeDefaultValueOfBlockList() - ", str, "JSONManager");
                }
            }
            list = new ArrayList(defaultSceneHashMap.values());
        }
        JSONObject jSONObject = new JSONObject();
        for (Scene scene2 : list) {
            try {
                jSONObject.put(scene2.mSceneKey, createJSONWithScene(scene2));
            } catch (JSONException e) {
                Log.e("JSONManager", "getJSONFromSceneList() " + e.getMessage());
                jSONObject = null;
            }
        }
        if (jSONObject == null) {
            return false;
        }
        File file = new File("/efs/sec_efs/SettingsBackup.json");
        if (file.exists()) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "deleteOldJSONFile result = ", "JSONManager", file.delete());
        } else {
            Log.d("JSONManager", "deleteOldJSONFile does not exist");
        }
        try {
            FileWriter fileWriter = new FileWriter("/efs/sec_efs/SettingsBackup.json");
            try {
                fileWriter.write(jSONObject.toString(4));
                fileWriter.close();
                return true;
            } catch (Throwable th) {
                try {
                    fileWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | JSONException e2) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("updateDefaultJSONFile() "), "JSONManager");
            return false;
        }
    }
}
