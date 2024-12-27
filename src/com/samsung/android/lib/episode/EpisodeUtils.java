package com.samsung.android.lib.episode;

import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EpisodeUtils {
    public static String compressString(String str) {
        if (str != null && str.length() != 0) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    try {
                        Charset charset = StandardCharsets.UTF_8;
                        gZIPOutputStream.write(str.getBytes(charset));
                        gZIPOutputStream.flush();
                        gZIPOutputStream.finish();
                        String str2 =
                                new String(
                                        Base64.encode(byteArrayOutputStream.toByteArray(), 2),
                                        charset);
                        gZIPOutputStream.close();
                        byteArrayOutputStream.close();
                        return str2;
                    } finally {
                    }
                } catch (Throwable th) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String convertListToString(List list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                sb.append((String) it.next());
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static ArrayList convertStringToArrayList(String str) {
        if (str == null) {
            return null;
        }
        return new ArrayList(Arrays.asList(str.split(",")));
    }

    public static String decompressString(String str) {
        if (str == null) {
            return null;
        }
        byte[] decode = Base64.decode(str, 2);
        byte[] decode2 = Base64.decode(str, 2);
        Charset charset = StandardCharsets.UTF_8;
        String trim = new String(decode2, charset).trim();
        if (trim != null && trim.length() > 0 && decode != null && decode.length > 0) {
            StringBuilder sb = new StringBuilder();
            try {
                GZIPInputStream gZIPInputStream =
                        new GZIPInputStream(new ByteArrayInputStream(decode));
                try {
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(gZIPInputStream, charset));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                bufferedReader.close();
                                gZIPInputStream.close();
                                return sb.toString();
                            }
                            sb.append(readLine);
                        } finally {
                        }
                    }
                } catch (Throwable th) {
                    try {
                        gZIPInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void isSettingAppSupportBnR() {
        Log.d(
                "Eternal/EpisodeUtils",
                "isSettingAppSupportBnR() - osVersion : "
                        + Build.VERSION.SDK_INT
                        + " / isEngBinary : "
                        + Build.TYPE.equals("eng"));
    }
}
