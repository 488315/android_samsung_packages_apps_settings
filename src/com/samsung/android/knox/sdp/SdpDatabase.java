package com.samsung.android.knox.sdp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import com.samsung.android.knox.sdp.core.SdpException;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SdpDatabase {
    private static final String CLASS_NAME = "SdpDatabase";
    private static final boolean DEBUG = false;
    private static final String TAG = "SdpDatabase";
    private static final boolean runAllConvert = false;
    private String mAlias;
    private final ContextInfo mContextInfo;
    private int mEngineId;

    public SdpDatabase(String str) throws SdpException {
        this.mEngineId = -1;
        enforcePermission();
        this.mAlias = str;
        this.mContextInfo = new ContextInfo(Binder.getCallingUid());
        SdpEngineInfo engineInfo = getEngineInfo(this.mAlias);
        if (engineInfo == null) {
            throw new SdpException(-5);
        }
        this.mEngineId = engineInfo.getId();
    }

    private void enforcePermission() throws SdpException {
        IDarManagerService asInterface =
                IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (asInterface != null) {
            try {
                if (asInterface.isLicensed() == 0) {
                } else {
                    throw new SdpException(-9);
                }
            } catch (RemoteException e) {
                Log.e("SdpDatabase", "Failed to talk with sdp service...", e);
            }
        }
    }

    private String formSensitiveColumnStmt(int i, String str, String str2) {
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return null;
        }
        return Anchor$$ExternalSyntheticOutline0.m(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "table=", str, ";columns=", str2, ";engine_id="),
                i,
                ";");
    }

    private String formSensitivePolicy(String str, String str2, String str3) throws Exception {
        String formSensitiveColumnStmt = formSensitiveColumnStmt(this.mEngineId, str2, str3);
        if (formSensitiveColumnStmt == null) {
            return null;
        }
        return MotionLayout$$ExternalSyntheticOutline0.m(
                "pragma ",
                str == null ? ApnSettings.MVNO_NONE : str.concat("."),
                "set_sensitive_columns(\"",
                formSensitiveColumnStmt,
                "\");");
    }

    private SdpEngineInfo getEngineInfo(String str) {
        try {
            IDarManagerService asInterface =
                    IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
            if (asInterface != null) {
                return asInterface.getEngineInfo(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.e("SdpDatabase", "Failed to talk with sdp service...", e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0075, code lost:

       if (r5.isClosed() != false) goto L32;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0078, code lost:

       return r3;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isSensitive(
            android.database.sqlite.SQLiteDatabase r6,
            java.lang.String r7,
            java.lang.String r8,
            java.lang.String r9) {
        /*
            r5 = this;
            java.lang.String r0 = "pragma "
            java.lang.String r1 = "."
            java.lang.String r2 = "SdpDatabase"
            r3 = 0
            if (r6 != 0) goto L10
            java.lang.String r5 = "isSensitive :: invalid DB"
            android.util.Log.d(r2, r5)
            return r3
        L10:
            int r4 = r5.mEngineId
            if (r4 >= 0) goto L21
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "isSensitive :: invalid engine "
            r6.<init>(r7)
            java.lang.String r5 = r5.mAlias
            com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0.m(r6, r5, r2)
            return r3
        L21:
            r5 = 0
            if (r7 != 0) goto L2b
            java.lang.String r7 = ""
            goto L2f
        L27:
            r6 = move-exception
            goto L79
        L29:
            r6 = move-exception
            goto L6e
        L2b:
            java.lang.String r7 = r7.concat(r1)     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
        L2f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            r1.append(r7)     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            java.lang.String r7 = "get_sensitive_columns("
            r1.append(r7)     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            r1.append(r8)     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            java.lang.String r7 = ")"
            r1.append(r7)     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            java.lang.String r7 = r1.toString()     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            android.database.Cursor r5 = r6.rawQuery(r7, r5)     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            boolean r6 = r5.moveToFirst()     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            if (r6 == 0) goto L64
        L52:
            java.lang.String r6 = r5.getString(r3)     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            boolean r6 = r9.equals(r6)     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            if (r6 == 0) goto L5e
            r3 = 1
            goto L64
        L5e:
            boolean r6 = r5.moveToNext()     // Catch: java.lang.Throwable -> L27 android.database.sqlite.SQLiteException -> L29
            if (r6 != 0) goto L52
        L64:
            boolean r6 = r5.isClosed()
            if (r6 != 0) goto L78
        L6a:
            r5.close()
            goto L78
        L6e:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L27
            boolean r6 = r5.isClosed()
            if (r6 != 0) goto L78
            goto L6a
        L78:
            return r3
        L79:
            boolean r7 = r5.isClosed()
            if (r7 != 0) goto L82
            r5.close()
        L82:
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.knox.sdp.SdpDatabase.isSensitive(android.database.sqlite.SQLiteDatabase,"
                    + " java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public boolean setSensitive(
            SQLiteDatabase sQLiteDatabase, String str, String str2, List<String> list)
            throws SdpException {
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpDatabase.setSensitive");
        StringBuilder sb = new StringBuilder();
        if (sQLiteDatabase == null) {
            Log.d("SdpDatabase", "setSensitive :: invalid DB");
            return false;
        }
        if (this.mEngineId < 0) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("setSensitive :: invalid engine "),
                    this.mAlias,
                    "SdpDatabase");
            return false;
        }
        SdpEngineInfo engineInfo = getEngineInfo(this.mAlias);
        if (engineInfo == null || engineInfo.getState() == 1) {
            Log.d("SdpDatabase", "setSensitive failed, engine is locked!!! " + this.mAlias);
            throw new SdpException(-6);
        }
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        Cursor cursor = null;
        try {
            if (sQLiteDatabase.isReadOnly()) {
                Log.d(
                        "SdpDatabase",
                        "Error : DB is readonly. setSensitiveDBPolicy require write permission for"
                            + " DB");
                return false;
            }
            sQLiteDatabase.execSQL(formSensitivePolicy(str, str2, sb.toString()));
            Cursor rawQuery = sQLiteDatabase.rawQuery("select count(*) from " + str2, null);
            if (rawQuery.moveToFirst() && rawQuery.getInt(0) > 0) {
                sQLiteDatabase.execSQL("VACUUM");
            }
            if (!rawQuery.isClosed()) {
                rawQuery.close();
            }
            sQLiteDatabase.setSdpDatabase();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (0 != 0) {
                cursor.close();
            }
            return false;
        }
    }

    public boolean updateStateToDB(SQLiteDatabase sQLiteDatabase, String str, int i) {
        if (sQLiteDatabase == null) {
            Log.d("SdpDatabase", "updateStateToDB :: invalid DB");
            return false;
        }
        SdpEngineInfo engineInfo = getEngineInfo(this.mAlias);
        if (engineInfo == null) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("updateStateToDB :: can't find engine "),
                    this.mAlias,
                    "SdpDatabase");
            return false;
        }
        if (engineInfo.getState() != i) {
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i, "updateStateToDB :: invalid state : ", " (current stat : ");
            m.append(engineInfo.getState());
            m.append(")");
            Log.d("SdpDatabase", m.toString());
            return false;
        }
        Cursor cursor = null;
        try {
            Log.d(
                    "SdpDatabase",
                    "updateSDPStateToDB called with dbalias = " + str + " sdpState = " + i);
            String concat = str == null ? ApnSettings.MVNO_NONE : str.concat(".");
            if (i == 1) {
                sQLiteDatabase.execSQL("pragma " + concat + "sdp_locked;");
            } else if (i == 2) {
                sQLiteDatabase.execSQL("pragma " + concat + "sdp_unlocked;");
                Cursor cursor2 = null;
                int i2 = 1;
                while (i2 > 0) {
                    try {
                        Log.d(
                                "SdpDatabase",
                                "calling next : pragma runoneconvert  in sdpState = " + i);
                        cursor2 =
                                sQLiteDatabase.rawQuery(
                                        "pragma " + concat + "sdp_run_one_convert", null);
                        if (cursor2 != null && cursor2.getCount() != 0) {
                            if (cursor2.moveToFirst()) {
                                i2 = cursor2.getInt(0);
                            }
                            Thread.sleep(30L);
                            cursor2.close();
                        }
                        Log.d("SdpDatabase", "Cursor is null or there are no rows after query...");
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        Log.d(
                                "SdpDatabase",
                                "DONE calling all pragma runoneconvert  in sdpState = " + i);
                    } catch (Exception e) {
                        e = e;
                        cursor = cursor2;
                        e.printStackTrace();
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    }
                }
                Log.d("SdpDatabase", "DONE calling all pragma runoneconvert  in sdpState = " + i);
            }
            return true;
        } catch (Exception e2) {
            e = e2;
        }
    }

    public boolean updateStateToDB(SQLiteDatabase sQLiteDatabase, int i) {
        return updateStateToDB(sQLiteDatabase, null, i);
    }
}
