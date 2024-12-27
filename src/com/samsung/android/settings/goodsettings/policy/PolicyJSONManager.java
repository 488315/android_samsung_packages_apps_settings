package com.samsung.android.settings.goodsettings.policy;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PolicyJSONManager {
    private static final String TAG = "[GS]PolicyJSONManager";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12, types: [java.io.OutputStreamWriter, java.io.Writer] */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.google.gson.Gson] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.google.gson.stream.JsonWriter] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.google.gson.stream.JsonWriter] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    public static void writeJson(Context e, PolicyData policyData) {
        ?? r0 = GoodSettingsContract.POLICY_FILE_NAME;
        ?? r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        r1 = 0;
        try {
            try {
                try {
                    e.deleteFile(GoodSettingsContract.POLICY_FILE_NAME);
                    e = e.openFileOutput(GoodSettingsContract.POLICY_FILE_NAME, 0);
                    try {
                        r0 = new OutputStreamWriter((OutputStream) e, StandardCharsets.UTF_8);
                        try {
                            JsonWriter jsonWriter = new JsonWriter(r0);
                            try {
                                r1 = new Gson();
                                r1.toJson(policyData, PolicyData.class, jsonWriter);
                                try {
                                    jsonWriter.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                                try {
                                    r0.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            } catch (IOException e4) {
                                e = e4;
                                r1 = jsonWriter;
                                e.printStackTrace();
                                if (r1 != 0) {
                                    try {
                                        r1.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                    }
                                }
                                if (r0 != 0) {
                                    try {
                                        r0.close();
                                    } catch (IOException e6) {
                                        e6.printStackTrace();
                                    }
                                }
                                if (e != 0) {
                                    e.close();
                                    r0 = r0;
                                    r1 = r1;
                                    e = e;
                                }
                            } catch (Throwable th) {
                                th = th;
                                r1 = jsonWriter;
                                if (r1 != 0) {
                                    try {
                                        r1.close();
                                    } catch (IOException e7) {
                                        e7.printStackTrace();
                                    }
                                }
                                if (r0 != 0) {
                                    try {
                                        r0.close();
                                    } catch (IOException e8) {
                                        e8.printStackTrace();
                                    }
                                }
                                if (e == 0) {
                                    throw th;
                                }
                                try {
                                    e.close();
                                    throw th;
                                } catch (IOException e9) {
                                    e9.printStackTrace();
                                    throw th;
                                }
                            }
                        } catch (IOException e10) {
                            e = e10;
                        }
                    } catch (IOException e11) {
                        e = e11;
                        r0 = 0;
                    } catch (Throwable th2) {
                        th = th2;
                        r0 = 0;
                    }
                } catch (IOException e12) {
                    e = e12;
                    e = 0;
                    r0 = 0;
                } catch (Throwable th3) {
                    th = th3;
                    e = 0;
                    r0 = 0;
                }
                if (e != 0) {
                    e.close();
                    r0 = r0;
                    r1 = r1;
                    e = e;
                }
            } catch (IOException e13) {
                e = e13;
                e.printStackTrace();
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }
}
