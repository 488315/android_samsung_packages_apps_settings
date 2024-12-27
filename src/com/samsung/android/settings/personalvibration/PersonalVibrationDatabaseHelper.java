package com.samsung.android.settings.personalvibration;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;

import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.vibration.PatternRestoreHelper;
import com.samsung.android.settings.asbase.vibration.VibPickerConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PersonalVibrationDatabaseHelper extends SQLiteOpenHelper {
    public static PersonalVibrationDatabaseHelper sSingleton;
    public final Context mContext;
    public final String[] mSoundDB;
    public static final HashMap mRingPatternData = new HashMap();
    public static final HashMap mNotiPatternData = new HashMap();
    public static boolean mSupportedColorful = false;
    public static boolean mSupportedLive = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PatternInfo {
        public final String mCustomData;
        public final int mIsCustom;
        public final String mName;
        public final int mPatternIndex;
        public final int mVibrationType;

        public PatternInfo(int i, String str, int i2, String str2, int i3) {
            this.mName = str;
            this.mPatternIndex = i;
            this.mVibrationType = i2;
            this.mCustomData = str2;
            this.mIsCustom = i3;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VibSettingsObserver extends ContentObserver {
        public final ContentResolver resolver;
        public final String[] soundDBName;
        public final String[] syncDBName;

        public VibSettingsObserver() {
            super(new Handler(Looper.getMainLooper()));
            this.resolver = PersonalVibrationDatabaseHelper.this.mContext.getContentResolver();
            this.soundDBName =
                    new String[] {
                        "ringtone", "ringtone_2", "notification_sound", "notification_sound_2"
                    };
            this.syncDBName =
                    new String[] {
                        "sync_vibration_with_ringtone",
                        "sync_vibration_with_ringtone_2",
                        "sync_vibration_with_notification",
                        "sync_vibration_with_notification"
                    };
            if (!VibRune.SUPPORT_SYNC_WITH_HAPTIC) {
                return;
            }
            int i = 0;
            while (true) {
                String[] strArr = this.soundDBName;
                if (i >= strArr.length) {
                    return;
                }
                PersonalVibrationDatabaseHelper.this.mSoundDB[i] =
                        Settings.System.getString(this.resolver, strArr[i]);
                this.resolver.registerContentObserver(
                        Settings.System.getUriFor(this.soundDBName[i]), false, this);
                i++;
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            if (!VibRune.SUPPORT_SYNC_WITH_HAPTIC) {
                return;
            }
            int i = 0;
            while (true) {
                String[] strArr = this.soundDBName;
                if (i >= strArr.length) {
                    return;
                }
                String string = Settings.System.getString(this.resolver, strArr[i]);
                boolean z2 = Settings.System.getInt(this.resolver, this.syncDBName[i], 1) == 1;
                if (string != null
                        && !string.equals(PersonalVibrationDatabaseHelper.this.mSoundDB[i])) {
                    HashMap hashMap = PersonalVibrationDatabaseHelper.mRingPatternData;
                    Log.d(
                            "PersonalVibrationDatabaseHelper",
                            "ringtone or notification sound has changed");
                    PersonalVibrationDatabaseHelper.this.mSoundDB[i] = string;
                    boolean hasHapticChannels =
                            AudioManager.hasHapticChannels(
                                    PersonalVibrationDatabaseHelper.this.mContext,
                                    Uri.parse(string));
                    if (z2 && !hasHapticChannels) {
                        Settings.System.putInt(this.resolver, this.syncDBName[i], 0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.syncDBName[i]);
                        sb.append(" set off because ");
                        AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                                sb,
                                this.soundDBName[i],
                                " does not have Haptic Channel",
                                "PersonalVibrationDatabaseHelper");
                    }
                }
                i++;
            }
        }
    }

    public PersonalVibrationDatabaseHelper(Context context) {
        super(context, "personalvibration.db", (SQLiteDatabase.CursorFactory) null, 4);
        this.mContext = context;
        mSupportedColorful = PatternRestoreHelper.supportsColorfulPattern(context);
        mSupportedLive = PatternRestoreHelper.supportsLivePattern(context);
        this.mSoundDB = new String[4];
        new VibSettingsObserver();
    }

    public static synchronized PersonalVibrationDatabaseHelper getInstance(Context context) {
        PersonalVibrationDatabaseHelper personalVibrationDatabaseHelper;
        synchronized (PersonalVibrationDatabaseHelper.class) {
            try {
                if (sSingleton == null) {
                    sSingleton =
                            new PersonalVibrationDatabaseHelper(context.getApplicationContext());
                }
                personalVibrationDatabaseHelper = sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        return personalVibrationDatabaseHelper;
    }

    public static int queryDbId(SQLiteDatabase sQLiteDatabase, String str, int i) {
        int i2 = TextUtils.equals(str, "registerinfo") ? 1 : 3;
        try {
            Cursor query =
                    sQLiteDatabase.query(
                            str,
                            null,
                            "vibration_pattern=?",
                            new String[] {Integer.toString(i)},
                            null,
                            null,
                            null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        i2 = query.getInt(query.getColumnIndexOrThrow("_id"));
                        Log.d("PersonalVibrationDatabaseHelper", "queryDbId() retId=" + i2);
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i2;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:84|85|(2:86|87)|88|(1:90)|91|(1:93)(1:138)|94|95|96|97|(3:104|105|(10:107|108|109|110|111|112|113|(1:101)|102|103))|99|(0)|102|103) */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x01d6, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x01e3, code lost:

       r0.printStackTrace();
    */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x01e0, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x01e1, code lost:

       r4 = "PersonalVibrationDatabaseHelper";
    */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01da A[Catch: Exception -> 0x01d6, TRY_LEAVE, TryCatch #6 {Exception -> 0x01d6, blocks: (B:101:0x01da, B:125:0x01d5, B:124:0x01d2, B:119:0x01cc), top: B:97:0x0198, inners: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x019a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x033c  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0159  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initDatabase(android.database.sqlite.SQLiteDatabase r34, boolean r35) {
        /*
            Method dump skipped, instructions count: 904
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.personalvibration.PersonalVibrationDatabaseHelper.initDatabase(android.database.sqlite.SQLiteDatabase,"
                    + " boolean):void");
    }

    public final void makePatternData(int i, HashMap hashMap) {
        Iterator<List<VibPickerConstants.PatternContainer>> it =
                new ArrayList<List<VibPickerConstants.PatternContainer>>(
                        i) { // from class:
                             // com.samsung.android.settings.personalvibration.PersonalVibrationDatabaseHelper.1
                    final /* synthetic */ int val$type;

                    {
                        this.val$type = i;
                        if (!VibRune.SUPPORT_SEC_VIBRATION_PICKER) {
                            if (i == 0) {
                                add(VibPickerConstants.SIMPLE_RINGTONE_PATTERN_LEGACY);
                                if (PersonalVibrationDatabaseHelper.mSupportedColorful) {
                                    add(VibPickerConstants.COLORFUL_RINGTONE_PATTERN_LEGACY);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        add(
                                i == 0
                                        ? VibPickerConstants.SIMPLE_RINGTONE_PATTERN
                                        : VibPickerConstants.SIMPLE_NOTIFICATION_PATTERN);
                        if (PersonalVibrationDatabaseHelper.mSupportedColorful) {
                            add(
                                    i == 0
                                            ? VibPickerConstants.COLORFUL_RINGTONE_PATTERN
                                            : VibPickerConstants.COLORFUL_NOTIFICATION_PATTERN);
                        }
                        if (PersonalVibrationDatabaseHelper.mSupportedLive) {
                            add(
                                    i == 0
                                            ? VibPickerConstants.LIVE_RINGTONE_PATTERN
                                            : VibPickerConstants.LIVE_NOTIFICATION_PATTERN);
                        }
                        if (VibRune.SUPPORT_DEFAULT_CATEGORIZE) {
                            add(VibPickerConstants.SILENT_PATTERN);
                        }
                    }
                }.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            for (VibPickerConstants.PatternContainer patternContainer : it.next()) {
                int i3 = i2 + 1;
                hashMap.put(
                        Integer.valueOf(i2),
                        new PatternInfo(
                                patternContainer.index,
                                patternContainer.name,
                                i,
                                ApnSettings.MVNO_NONE,
                                0));
                i2 = i3;
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onConfigure(SQLiteDatabase sQLiteDatabase) {
        super.onConfigure(sQLiteDatabase);
        sQLiteDatabase.enableWriteAheadLogging();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS registerinfo (_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                    + " NOT NULL , vibration_name VARCHAR NOT NULL , vibration_pattern INTEGER NOT"
                    + " NULL ,vibration_type INTEGER NOT NULL , custom_data VARCHAR ,is_custom"
                    + " INTEGER)");
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS notification (_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                    + " NOT NULL , vibration_name VARCHAR NOT NULL , vibration_pattern INTEGER NOT"
                    + " NULL ,vibration_type INTEGER NOT NULL , custom_data VARCHAR ,is_custom"
                    + " INTEGER)");
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS customindex (_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                    + " NOT NULL , vibration_type VARCHAR NOT NULL , pattern_index INTEGER NOT"
                    + " NULL)");
        initDatabase(sQLiteDatabase, false);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.setVersion(i);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        int size;
        String str;
        int i3;
        Log.w(
                "PersonalVibrationDatabaseHelper",
                CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0.m(
                        "Upgrading from version ",
                        " to ",
                        i,
                        i2,
                        ", which will destroy all old data"));
        boolean z = i != 2;
        if (z) {
            for (int i4 = 0; i4 < 2; i4++) {
                if (i4 == 0) {
                    str = "registerinfo";
                    size = mRingPatternData.size();
                } else {
                    size = mNotiPatternData.size();
                    str = "notification";
                }
                try {
                    String str2 = str;
                    Cursor query =
                            sQLiteDatabase.query(
                                    str, null, "is_custom=?", new String[] {"1"}, null, null, null);
                    if (query != null) {
                        try {
                            if (query.moveToFirst()) {
                                Log.d(
                                        "PersonalVibrationDatabaseHelper",
                                        "custom db count(" + str2 + ") : " + query.getCount());
                                int i5 = 0;
                                do {
                                    PatternInfo patternInfo =
                                            new PatternInfo(
                                                    query.getInt(
                                                                    query.getColumnIndexOrThrow(
                                                                            "vibration_pattern"))
                                                            - 50024,
                                                    query.getString(
                                                            query.getColumnIndexOrThrow(
                                                                    "vibration_name")),
                                                    query.getInt(
                                                            query.getColumnIndexOrThrow(
                                                                    "vibration_type")),
                                                    query.getString(
                                                            query.getColumnIndexOrThrow(
                                                                    "custom_data")),
                                                    query.getInt(
                                                            query.getColumnIndexOrThrow(
                                                                    "is_custom")));
                                    if (TextUtils.equals(str2, "registerinfo")) {
                                        i3 = i5 + 1;
                                        mRingPatternData.put(
                                                Integer.valueOf(i5 + size), patternInfo);
                                    } else {
                                        i3 = i5 + 1;
                                        mNotiPatternData.put(
                                                Integer.valueOf(i5 + size), patternInfo);
                                    }
                                    i5 = i3;
                                } while (query.moveToNext());
                            }
                        } catch (Throwable th) {
                            try {
                                query.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                    if (query != null) {
                        query.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS registerinfo");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS notification");
        if (!z) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS customindex");
        }
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS registerinfo (_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                    + " NOT NULL , vibration_name VARCHAR NOT NULL , vibration_pattern INTEGER NOT"
                    + " NULL ,vibration_type INTEGER NOT NULL , custom_data VARCHAR ,is_custom"
                    + " INTEGER)");
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS notification (_id INTEGER PRIMARY KEY  AUTOINCREMENT "
                    + " NOT NULL , vibration_name VARCHAR NOT NULL , vibration_pattern INTEGER NOT"
                    + " NULL ,vibration_type INTEGER NOT NULL , custom_data VARCHAR ,is_custom"
                    + " INTEGER)");
        if (!z) {
            sQLiteDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS customindex (_id INTEGER PRIMARY KEY  AUTOINCREMENT"
                        + "  NOT NULL , vibration_type VARCHAR NOT NULL , pattern_index INTEGER NOT"
                        + " NULL)");
        }
        initDatabase(sQLiteDatabase, z);
    }
}
