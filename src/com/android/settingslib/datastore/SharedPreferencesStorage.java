package com.android.settingslib.datastore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SharedPreferencesStorage extends BackupRestoreFileStorage
        implements KeyedObservable {
    public final /* synthetic */ KeyedDataObservable $$delegate_0;
    public final Function2 filter;
    public final String name;
    public final SharedPreferences sharedPreferences;
    public final SharedPreferencesStorage$sharedPreferencesListener$1 sharedPreferencesListener;
    public final boolean verbose;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Companion {
        public static File getSharedPreferencesFile(Context context, String name) {
            Intrinsics.checkNotNullParameter(context, "<this>");
            Intrinsics.checkNotNullParameter(name, "name");
            File dataDir = context.getDataDir();
            Intrinsics.checkNotNull(dataDir);
            return new File(new File(dataDir, "shared_prefs"), name.concat(".xml"));
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:6:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0108  */
    /* JADX WARN: Type inference failed for: r1v4, types: [android.content.SharedPreferences$OnSharedPreferenceChangeListener, com.android.settingslib.datastore.SharedPreferencesStorage$sharedPreferencesListener$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SharedPreferencesStorage(android.content.Context r25) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.datastore.SharedPreferencesStorage.<init>(android.content.Context):void");
    }

    @Override // com.android.settingslib.datastore.KeyedObservable
    public final void addObserver(
            BackupRestoreStorageManager.StorageWrapper observer, Executor executor) {
        Intrinsics.checkNotNullParameter(observer, "observer");
        Intrinsics.checkNotNullParameter(executor, "executor");
        this.$$delegate_0.addObserver(observer, executor);
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final BackupCodec defaultCodec() {
        return BackupZipCodec.BEST_COMPRESSION;
    }

    public final void delete(SharedPreferences sharedPreferences, String str) {
        if (!this.context.deleteSharedPreferences(str)) {
            sharedPreferences.edit().clear().commit();
            return;
        }
        Log.i("BackupRestoreStorage", "SharedPreferences " + str + " deleted");
    }

    @Override // com.android.settingslib.datastore.BackupRestoreFileStorage
    public final File getBackupFile() {
        return Companion.getSharedPreferencesFile(
                this.context,
                getIntermediateName$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore());
    }

    public final String
            getIntermediateName$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore() {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("_br_", this.name);
    }

    @Override // com.android.settingslib.datastore.BackupRestoreStorage
    public final String getName() {
        return this.name;
    }

    public final SharedPreferences
            getSharedPreferences$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore() {
        return this.sharedPreferences;
    }

    public SharedPreferences.Editor
            mergeSharedPreferences$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(
                    SharedPreferences sharedPreferences,
                    Map<String, ? extends Object> entries,
                    String operation) {
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(operation, "operation");
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (Map.Entry<String, ? extends Object> entry : entries.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (((Boolean) this.filter.invoke(key, value)).booleanValue()) {
                if (value instanceof Boolean) {
                    edit.putBoolean(key, ((Boolean) value).booleanValue());
                    if (this.verbose) {
                        StringBuilder m =
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        "[", this.name, "] ", operation, " Boolean ");
                        m.append(key);
                        m.append("=");
                        m.append(value);
                        Log.v("BackupRestoreStorage", m.toString());
                    }
                } else if (value instanceof Float) {
                    edit.putFloat(key, ((Number) value).floatValue());
                    if (this.verbose) {
                        StringBuilder m2 =
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        "[", this.name, "] ", operation, " Float ");
                        m2.append(key);
                        m2.append("=");
                        m2.append(value);
                        Log.v("BackupRestoreStorage", m2.toString());
                    }
                } else if (value instanceof Integer) {
                    edit.putInt(key, ((Number) value).intValue());
                    if (this.verbose) {
                        StringBuilder m3 =
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        "[", this.name, "] ", operation, " Int ");
                        m3.append(key);
                        m3.append("=");
                        m3.append(value);
                        Log.v("BackupRestoreStorage", m3.toString());
                    }
                } else if (value instanceof Long) {
                    edit.putLong(key, ((Number) value).longValue());
                    if (this.verbose) {
                        StringBuilder m4 =
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        "[", this.name, "] ", operation, " Long ");
                        m4.append(key);
                        m4.append("=");
                        m4.append(value);
                        Log.v("BackupRestoreStorage", m4.toString());
                    }
                } else if (value instanceof String) {
                    edit.putString(key, (String) value);
                    if (this.verbose) {
                        StringBuilder m5 =
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        "[", this.name, "] ", operation, " String ");
                        m5.append(key);
                        m5.append("=");
                        m5.append(value);
                        Log.v("BackupRestoreStorage", m5.toString());
                    }
                } else {
                    if (value instanceof Set) {
                        Iterator it = ((Iterable) value).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Object next = it.next();
                            if (!(next instanceof String)) {
                                r7 = next;
                                break;
                            }
                        }
                        if (r7 != null) {
                            String str = this.name;
                            Class<?> cls = r7.getClass();
                            StringBuilder m6 =
                                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                            "[", str, "] ", operation, " StringSet ");
                            m6.append(key);
                            m6.append("=");
                            m6.append(value);
                            m6.append(" but non string found: ");
                            m6.append(r7);
                            m6.append(" (");
                            m6.append(cls);
                            m6.append(")");
                            Log.e("BackupRestoreStorage", m6.toString());
                        } else {
                            Intrinsics.checkNotNull(
                                    value,
                                    "null cannot be cast to non-null type"
                                        + " kotlin.collections.Set<kotlin.String>");
                            edit.putStringSet(key, (Set) value);
                            if (this.verbose) {
                                StringBuilder m7 =
                                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                                "[", this.name, "] ", operation, " StringSet ");
                                m7.append(key);
                                m7.append("=");
                                m7.append(value);
                                Log.v("BackupRestoreStorage", m7.toString());
                            }
                        }
                    } else {
                        String str2 = this.name;
                        r7 = value != null ? value.getClass() : null;
                        StringBuilder m8 =
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        "[", str2, "] ", operation, " ");
                        m8.append(key);
                        m8.append("=");
                        m8.append(value);
                        m8.append(", unknown type: ");
                        m8.append(r7);
                        Log.e("BackupRestoreStorage", m8.toString());
                    }
                }
            } else if (this.verbose) {
                StringBuilder m9 =
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                "[", this.name, "] ", operation, " skips ");
                m9.append(key);
                m9.append("=");
                m9.append(value);
                Log.v("BackupRestoreStorage", m9.toString());
            }
        }
        Intrinsics.checkNotNull(edit);
        return edit;
    }

    @Override // com.android.settingslib.datastore.KeyedObservable
    public final void notifyChange(int i) {
        this.$$delegate_0.notifyChange(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settingslib.datastore.BackupRestoreFileStorage
    public final void onRestoreFinished(File file) {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(
                this.sharedPreferencesListener);
        SharedPreferences sharedPreferences =
                this.context.getSharedPreferences(
                        getIntermediateName$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(),
                        4);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        SharedPreferences sharedPreferences2 = this.sharedPreferences;
        Map<String, ?> all = sharedPreferences.getAll();
        Intrinsics.checkNotNullExpressionValue(all, "getAll(...)");
        mergeSharedPreferences$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(
                        sharedPreferences2, all, "Restore")
                .commit();
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(
                this.sharedPreferencesListener);
        delete(
                sharedPreferences,
                getIntermediateName$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore());
    }

    @Override // com.android.settingslib.datastore.KeyedObservable
    public final void removeObserver(BackupRestoreStorageManager.StorageWrapper storageWrapper) {
        this.$$delegate_0.removeObserver(storageWrapper);
    }

    public static /* synthetic */ void
            getIntermediateName$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore$annotations() {}
}
