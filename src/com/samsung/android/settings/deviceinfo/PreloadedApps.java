package com.samsung.android.settings.deviceinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.settings.widget.SecIconResizer;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PreloadedApps extends SettingsPreferenceFragment {
    public Context mContext;
    public String[] mOptionMenuText;
    public PackageManager mPm;
    public PreferenceScreen mPrefScreen;
    public List mPreloadedPackageInfo;
    public ProgressDialog mProgress = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.PreloadedApps$1, reason: invalid class name */
    public final class AnonymousClass1 extends AsyncTask {
        public final /* synthetic */ boolean val$isShowSystemApps;

        public AnonymousClass1(boolean z) {
            this.val$isShowSystemApps = z;
        }

        /* JADX WARN: Can't wrap try/catch for region: R(9:22|(1:26)|27|(14:(1:29)(9:110|111|112|113|114|115|(1:117)|118|(19:120|121|(1:32)|33|34|35|36|37|(3:38|39|(3:41|(3:43|44|45)(1:47)|46)(1:48))|49|50|51|(4:71|(7:74|75|77|78|(3:83|84|85)|86|72)|90|91)|53|(1:55)|56|(3:58|(5:61|62|64|65|59)|67)|68|(1:70))(2:122|123))|36|37|(4:38|39|(0)(0)|46)|49|50|51|(0)|53|(0)|56|(0)|68|(0))|30|(0)|33|34|35) */
        /* JADX WARN: Code restructure failed: missing block: B:108:0x012e, code lost:

           r2 = move-exception;
        */
        /* JADX WARN: Code restructure failed: missing block: B:109:0x0144, code lost:

           r2.printStackTrace();
        */
        /* JADX WARN: Code restructure failed: missing block: B:92:0x0184, code lost:

           if (r3.size() > 0) goto L102;
        */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00e5  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x010b A[Catch: all -> 0x0125, TryCatch #4 {all -> 0x0125, blocks: (B:39:0x0105, B:41:0x010b, B:44:0x0114), top: B:38:0x0105, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0127 A[EDGE_INSN: B:48:0x0127->B:49:0x0127 BREAK  A[LOOP:2: B:38:0x0105->B:46:0x0105], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0188  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x0198  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x01c3  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x014f  */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object doInBackground(java.lang.Object[] r9) {
            /*
                Method dump skipped, instructions count: 455
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.deviceinfo.PreloadedApps.AnonymousClass1.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            ProgressDialog progressDialog = PreloadedApps.this.mProgress;
            if (progressDialog == null || !progressDialog.isShowing()) {
                return;
            }
            try {
                try {
                    PreloadedApps.this.mProgress.dismiss();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            } finally {
                PreloadedApps.this.mProgress = null;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            PreloadedApps.this.mPrefScreen.removeAll();
            PreloadedApps preloadedApps = PreloadedApps.this;
            if (preloadedApps.mProgress == null) {
                preloadedApps.mProgress = new ProgressDialog(PreloadedApps.this.mContext);
                PreloadedApps.this.mProgress.setCancelable(false);
                PreloadedApps.this.mProgress.setIndeterminate(true);
                PreloadedApps preloadedApps2 = PreloadedApps.this;
                preloadedApps2.mProgress.setMessage(
                        preloadedApps2.mContext.getString(
                                R.string.device_info_preloaded_apps_loading));
                PreloadedApps.this.mProgress.show();
            }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 40;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mPrefScreen = getPreferenceManager().createPreferenceScreen(this.mContext);
        this.mOptionMenuText =
                new String[] {
                    this.mContext.getResources().getString(R.string.menu_show_system),
                    this.mContext.getResources().getString(R.string.menu_hide_system)
                };
        setPreferenceScreen(this.mPrefScreen);
        setHasOptionsMenu(true);
        new AnonymousClass1(true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 72047102, 0, this.mOptionMenuText[0]);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 72047102) {
            if (menuItem.getTitle().equals(this.mOptionMenuText[0])) {
                menuItem.setTitle(this.mOptionMenuText[1]);
                new AnonymousClass1(false)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            } else {
                menuItem.setTitle(this.mOptionMenuText[0]);
                this.mPrefScreen.removeAll();
                new AnonymousClass1(true)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public final void setList$2(List list) {
        Collections.sort(
                list,
                new Comparator() { // from class:
                                   // com.samsung.android.settings.deviceinfo.PreloadedApps.2
                    public final Collator collator = Collator.getInstance();

                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return this.collator.compare(
                                ((PackageInfo) obj)
                                        .applicationInfo.loadLabel(PreloadedApps.this.mPm),
                                ((PackageInfo) obj2)
                                        .applicationInfo.loadLabel(PreloadedApps.this.mPm));
                    }
                });
        for (int i = 0; i < list.size(); i++) {
            PackageInfo packageInfo = (PackageInfo) list.get(i);
            Drawable loadIcon = packageInfo.applicationInfo.loadIcon(this.mPm, true, 1);
            Preference preference = new Preference(this.mContext);
            preference.setTitle(packageInfo.applicationInfo.loadLabel(this.mPm));
            SecIconResizer secIconResizer = new SecIconResizer(this.mContext);
            int dimension =
                    (int)
                            secIconResizer
                                    .mContext
                                    .getResources()
                                    .getDimension(R.dimen.sec_widget_list_app_icon_size);
            secIconResizer.mIconHeight = dimension;
            secIconResizer.mIconWidth = dimension;
            preference.setIcon(
                    loadIcon != null ? secIconResizer.createIconThumbnail(loadIcon) : null);
            this.mPrefScreen.addPreference(preference);
        }
    }
}
