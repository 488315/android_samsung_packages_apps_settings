package com.android.settingslib.core.instrumentation;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SharedPreferencesLogger implements SharedPreferences {
    public final Context mContext;
    public final int mMetricCategory;
    public final MetricsFeatureProvider mMetricsFeature;
    public final Set mPreferenceKeySet = new ConcurrentSkipListSet();
    public final String mTag;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AsyncPackageCheck extends AsyncTask {
        public AsyncPackageCheck() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            String[] strArr = (String[]) objArr;
            String str = strArr[0];
            String str2 = strArr[1];
            PackageManager packageManager =
                    SharedPreferencesLogger.this.mContext.getPackageManager();
            try {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str2);
                if (str2 != null) {
                    str2 = unflattenFromString.getPackageName();
                }
            } catch (Exception unused) {
            }
            try {
                packageManager.getPackageInfo(str2, 4194304);
                SharedPreferencesLogger.this.logPackageName(str, str2);
                return null;
            } catch (PackageManager.NameNotFoundException unused2) {
                SharedPreferencesLogger.this.logValue(str2, str, true);
                return null;
            }
        }
    }

    public SharedPreferencesLogger(
            Context context,
            String str,
            SettingsMetricsFeatureProvider settingsMetricsFeatureProvider,
            int i) {
        this.mContext = context;
        this.mTag = str;
        this.mMetricsFeature = settingsMetricsFeatureProvider;
        this.mMetricCategory = i;
    }

    @Override // android.content.SharedPreferences
    public final boolean contains(String str) {
        return false;
    }

    @Override // android.content.SharedPreferences
    public final SharedPreferences.Editor edit() {
        return new EditorLogger();
    }

    @Override // android.content.SharedPreferences
    public final Map getAll() {
        return null;
    }

    public void logPackageName(String str, String str2) {
        this.mMetricsFeature.action(
                this.mMetricCategory,
                853,
                0,
                0,
                AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(str, ":", str2));
    }

    public void logValue(String str, Object obj) {
        logValue(obj, str, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void logValue(Object obj, String str, boolean z) {
        String m = AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(this.mTag, "/", str);
        if (!z && !((ConcurrentSkipListSet) this.mPreferenceKeySet).contains(m)) {
            ((ConcurrentSkipListSet) this.mPreferenceKeySet).add(m);
            return;
        }
        int i = Integer.MIN_VALUE;
        i = Integer.MIN_VALUE;
        if (obj instanceof Long) {
            Long l = (Long) obj;
            if (l.longValue() <= 2147483647L) {
                if (l.longValue() >= -2147483648L) {
                    i = l.intValue();
                }
                this.mMetricsFeature.changed(this.mMetricCategory, i, str);
            }
            i = Integer.MAX_VALUE;
            this.mMetricsFeature.changed(this.mMetricCategory, i, str);
        }
        if (obj instanceof Integer) {
            i = ((Integer) obj).intValue();
        } else if (obj instanceof Boolean) {
            i = ((Boolean) obj).booleanValue();
        } else if (obj instanceof Float) {
            float floatValue = ((Float) obj).floatValue();
            if (floatValue <= 2.14748365E9f) {
                if (floatValue >= -2.14748365E9f) {
                    i = (int) floatValue;
                }
            }
            i = Integer.MAX_VALUE;
        } else if (!(obj instanceof String)) {
            Log.w("SharedPreferencesLogger", "Tried to log unloggable object=" + obj);
            return;
        } else {
            try {
                i = Integer.parseInt((String) obj);
            } catch (NumberFormatException unused) {
                Log.w("SharedPreferencesLogger", "Tried to log unloggable object=" + obj);
                return;
            }
        }
        this.mMetricsFeature.changed(this.mMetricCategory, i, str);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EditorLogger implements SharedPreferences.Editor {
        public EditorLogger() {}

        @Override // android.content.SharedPreferences.Editor
        public final boolean commit() {
            return true;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putBoolean(String str, boolean z) {
            SharedPreferencesLogger.this.logValue(str, Boolean.valueOf(z));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putFloat(String str, float f) {
            SharedPreferencesLogger.this.logValue(str, Float.valueOf(f));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putInt(String str, int i) {
            SharedPreferencesLogger.this.logValue(str, Integer.valueOf(i));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putLong(String str, long j) {
            SharedPreferencesLogger.this.logValue(str, Long.valueOf(j));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putString(String str, String str2) {
            SharedPreferencesLogger sharedPreferencesLogger = SharedPreferencesLogger.this;
            sharedPreferencesLogger.getClass();
            sharedPreferencesLogger.new AsyncPackageCheck()
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, str, str2);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor putStringSet(String str, Set set) {
            SharedPreferencesLogger sharedPreferencesLogger = SharedPreferencesLogger.this;
            String join = TextUtils.join(",", set);
            sharedPreferencesLogger.getClass();
            sharedPreferencesLogger.new AsyncPackageCheck()
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, str, join);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final void apply() {}

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor clear() {
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public final SharedPreferences.Editor remove(String str) {
            return this;
        }
    }

    @Override // android.content.SharedPreferences
    public final void registerOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {}

    @Override // android.content.SharedPreferences
    public final void unregisterOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {}

    @Override // android.content.SharedPreferences
    public final boolean getBoolean(String str, boolean z) {
        return z;
    }

    @Override // android.content.SharedPreferences
    public final float getFloat(String str, float f) {
        return f;
    }

    @Override // android.content.SharedPreferences
    public final int getInt(String str, int i) {
        return i;
    }

    @Override // android.content.SharedPreferences
    public final long getLong(String str, long j) {
        return j;
    }

    @Override // android.content.SharedPreferences
    public final String getString(String str, String str2) {
        return str2;
    }

    @Override // android.content.SharedPreferences
    public final Set getStringSet(String str, Set set) {
        return set;
    }
}
