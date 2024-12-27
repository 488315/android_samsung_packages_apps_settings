package com.samsung.android.settings.display;

import android.R;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.IActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.fontutil.FontWriter;
import com.samsung.android.fontutil.SemTypeface;
import com.samsung.android.fontutil.TypefaceFile;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.flipfont.FontListAdapter;
import com.samsung.android.settings.logging.LoggingHelper;

import java.io.File;
import java.io.InputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecFontStylePreferenceFragment extends SettingsPreferenceFragment {
    public FragmentActivity mContext;
    public FontListAdapter mFontListAdapter;
    public RecyclerView mFontStyleListView;
    public NestedScrollView mFontStyleScrollView;
    public Resources mResources;
    public AlertDialog mWarningDialog;
    public int mPreviousFontIndex = -1;
    public int mCurrentFontIndex = -1;
    public boolean mIsRunLoadListTask = false;
    public boolean mCallbackFromDownloadFont = false;
    public LoadListTask mLoadListTask = null;
    public String mUninstallFontsPackageName = ApnSettings.MVNO_NONE;
    public final SecFontStylePreferenceFragment$$ExternalSyntheticLambda0
            mWarningDialogClickListener =
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.display.SecFontStylePreferenceFragment$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            SecFontStylePreferenceFragment secFontStylePreferenceFragment =
                                    SecFontStylePreferenceFragment.this;
                            if (i == -2) {
                                int i2 = secFontStylePreferenceFragment.mPreviousFontIndex;
                                secFontStylePreferenceFragment.mCurrentFontIndex = i2;
                                FontListAdapter fontListAdapter =
                                        secFontStylePreferenceFragment.mFontListAdapter;
                                if (fontListAdapter != null) {
                                    fontListAdapter.setItemChecked(i2);
                                }
                                dialogInterface.dismiss();
                                return;
                            }
                            if (i != -1) {
                                secFontStylePreferenceFragment.getClass();
                                return;
                            }
                            try {
                                secFontStylePreferenceFragment.mContext.startActivity(
                                        new Intent(
                                                        "android.intent.action.DELETE",
                                                        Uri.parse(
                                                                "package:"
                                                                        + secFontStylePreferenceFragment
                                                                                .mUninstallFontsPackageName))
                                                .setFlags(872415232));
                                secFontStylePreferenceFragment.finish();
                            } catch (Exception e) {
                                Log.secE(
                                        "SecFontStylePreferenceFragment",
                                        "cannot start UninstallerActivity : " + e.getMessage());
                            }
                        }
                    };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LoadListTask extends AsyncTask {
        public LoadListTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            if (FontListAdapter.mFontListAdapter != null) {
                FontListAdapter.mFontListAdapter = null;
            }
            SecFontStylePreferenceFragment secFontStylePreferenceFragment =
                    SecFontStylePreferenceFragment.this;
            secFontStylePreferenceFragment.mFontListAdapter =
                    FontListAdapter.getInstanceFontListAdapter(
                            secFontStylePreferenceFragment.mContext);
            try {
                SecFontStylePreferenceFragment.this.mFontListAdapter
                        .setInitDownloadFontAndThemeFont();
                FontListAdapter fontListAdapter =
                        SecFontStylePreferenceFragment.this.mFontListAdapter;
                fontListAdapter.mIsFontPreviewActivity = true;
                fontListAdapter.loadTypefaces();
            } catch (Exception e) {
                Log.secD(
                        "SecFontStylePreferenceFragment",
                        "this is regarding debugging. it is not problem: " + e.getMessage());
                cancel(true);
            }
            return null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            SecFontStylePreferenceFragment.this.setFontStyleList();
            SecFontStylePreferenceFragment.this.mIsRunLoadListTask = false;
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            SecFontStylePreferenceFragment.this.mIsRunLoadListTask = true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VerticalLineItemDecoration extends RecyclerView.ItemDecoration {
        public final Drawable mDivider;

        public VerticalLineItemDecoration(FragmentActivity fragmentActivity) {
            TypedArray obtainStyledAttributes =
                    fragmentActivity.obtainStyledAttributes(new int[] {R.attr.listDivider});
            this.mDivider = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(
                Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            rect.bottom = this.mDivider.getIntrinsicHeight();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
            SecFontStylePreferenceFragment secFontStylePreferenceFragment =
                    SecFontStylePreferenceFragment.this;
            int dimensionPixelSize =
                    secFontStylePreferenceFragment.mResources.getDimensionPixelSize(
                                    com.android.settings.R.dimen
                                            .sec_widget_list_checkbox_width_for_divider_padding_inset)
                            + secFontStylePreferenceFragment.mResources.getDimensionPixelSize(
                                    com.android.settings.R.dimen
                                            .sec_widget_list_checkbox_width_for_divider_inset);
            for (int i = 0; i < recyclerView.getChildCount() - 1; i++) {
                View childAt = recyclerView.getChildAt(i);
                RecyclerView.LayoutParams layoutParams =
                        (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int paddingLeft = recyclerView.getPaddingLeft() + dimensionPixelSize;
                int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
                int bottom =
                        childAt.getBottom()
                                + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                int intrinsicHeight = this.mDivider.getIntrinsicHeight() + bottom;
                if (recyclerView.getLayoutDirection() == 1) {
                    paddingLeft = recyclerView.getPaddingLeft();
                    width =
                            (recyclerView.getWidth() - recyclerView.getPaddingRight())
                                    - dimensionPixelSize;
                }
                this.mDivider.setBounds(paddingLeft, bottom, width, intrinsicHeight);
                this.mDivider.draw(canvas);
            }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7446;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mResources = activity.getResources();
        getActivity().setTitle(com.android.settings.R.string.sec_font_style_title);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View findViewById;
        int color =
                this.mContext.getColor(com.android.settings.R.color.sec_widget_round_and_bgcolor);
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        View inflate =
                layoutInflater.inflate(
                        com.android.settings.R.layout.sec_font_style_list, (ViewGroup) null);
        LinearLayout linearLayout =
                (LinearLayout)
                        inflate.findViewById(
                                com.android.settings.R.id.font_style_list_view_container);
        linearLayout.semSetRoundedCorners(15);
        linearLayout.semSetRoundedCornerColor(15, color);
        this.mFontStyleScrollView =
                (NestedScrollView)
                        inflate.findViewById(com.android.settings.R.id.font_style_scroll_view);
        this.mFontStyleListView =
                (RecyclerView) inflate.findViewById(com.android.settings.R.id.font_style_list_view);
        this.mFontStyleScrollView.semSetRoundedCorners(15);
        this.mFontStyleScrollView.semSetRoundedCornerColor(15, color);
        this.mFontStyleScrollView.semSetRoundedCornerOffset(
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(
                                com.android.settings.R.dimen.sec_scroll_view_corner_radius));
        if (this.mFontListAdapter != null) {
            setFontStyleList();
        } else if (!this.mIsRunLoadListTask) {
            LoadListTask loadListTask = new LoadListTask();
            this.mLoadListTask = loadListTask;
            loadListTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
        this.mFontStyleListView.setSelected(true);
        this.mFontStyleListView.setClickable(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(1);
        linearLayoutManager.mAutoMeasure = true;
        this.mFontStyleListView.setLayoutManager(linearLayoutManager);
        this.mFontStyleListView.setHasFixedSize(true);
        this.mFontStyleListView.setNestedScrollingEnabled(false);
        this.mFontStyleListView.setOverScrollMode(0);
        this.mFontStyleListView.addItemDecoration(new VerticalLineItemDecoration(this.mContext));
        Log.secD("SecFontStylePreferenceFragment", "called onCreate()");
        if ((getActivity() instanceof SecSettingsBaseActivity)
                && (findViewById =
                                getActivity().findViewById(com.android.settings.R.id.round_corner))
                        != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(15);
            findViewById.semSetRoundedCornerColor(15, color);
        }
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        LoadListTask loadListTask = this.mLoadListTask;
        if (loadListTask != null) {
            loadListTask.cancel(true);
            this.mIsRunLoadListTask = false;
        }
        if (this.mFontListAdapter != null) {
            if (FontListAdapter.mFontListAdapter != null) {
                FontListAdapter.mFontListAdapter = null;
            }
            this.mFontListAdapter = null;
        }
        AlertDialog alertDialog = this.mWarningDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mWarningDialog.dismiss();
            this.mUninstallFontsPackageName = ApnSettings.MVNO_NONE;
        }
        Log.secD("SecFontStylePreferenceFragment", "called onDestroy()");
    }

    public final void onItemClick(int i) {
        Log.secD("SecFontStylePreferenceFragment", "onItemClick : " + i);
        FontListAdapter fontListAdapter = this.mFontListAdapter;
        if (fontListAdapter == null) {
            Log.secD(
                    "SecFontStylePreferenceFragment",
                    "onItemClick : mFontListAdapter is null, so return.");
            return;
        }
        this.mPreviousFontIndex = this.mCurrentFontIndex;
        this.mCurrentFontIndex = i;
        boolean z = false;
        if (fontListAdapter.getItemViewType(i) == 1) {
            Log.d(
                    "SecFontStylePreferenceFragment",
                    "onItemClick : viewType is FooterView, which means Download Font Menu. So"
                        + " return.");
            FragmentActivity fragmentActivity = this.mContext;
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            String str =
                    (String)
                            fragmentActivity
                                    .getResources()
                                    .getText(
                                            com.android.settings.R.string
                                                    .sec_monotype_samsung_apps_uri);
            Intent intent = new Intent();
            intent.setData(Uri.parse(str));
            intent.addFlags(335544352);
            if (!Utils.isIntentAvailable(this.mContext, intent)) {
                intent = SecDisplayUtils.getFontDownloadMarketIntent(this.mContext);
            }
            this.mIsRunLoadListTask = false;
            this.mCallbackFromDownloadFont = true;
            this.mContext.startActivity(intent);
            LoggingHelper.insertEventLogging(7446, 4205, 3L);
            return;
        }
        if (i == 0 || i == 1 || i == 2) {
            LoggingHelper.insertEventLogging(7446, 4205, i);
        }
        this.mFontListAdapter.setItemChecked(i);
        String obj = this.mFontListAdapter.mFontPackageNames.get(this.mCurrentFontIndex).toString();
        if (SecDisplayUtils.isInvalidFont(this.mContext, obj)) {
            showWarningDialog(obj);
            return;
        }
        final String obj2 =
                this.mFontListAdapter.mTypefaceFiles.get(this.mCurrentFontIndex).toString();
        FontWriter fontWriter = new FontWriter();
        if (obj2.equals("default")) {
            fontWriter.deleteFontDirectory(" ");
            fontWriter.writeLoc("default#default");
        } else {
            SemTypeface findMatchingTypeface =
                    this.mFontListAdapter.mTypefaceFinder.findMatchingTypeface(obj2);
            int indexOf = obj2.indexOf(".xml");
            String substring = indexOf > 0 ? obj2.substring(0, indexOf) : obj2;
            File createFontDirectory = fontWriter.createFontDirectory(substring);
            if (findMatchingTypeface != null) {
                boolean z2 = false;
                for (int i2 = 0; i2 < findMatchingTypeface.mSansFonts.size(); i2++) {
                    TypefaceFile typefaceFile =
                            (TypefaceFile) findMatchingTypeface.mSansFonts.get(i2);
                    try {
                        obj =
                                this.mFontListAdapter
                                        .mFontPackageNames
                                        .get(this.mCurrentFontIndex)
                                        .toString();
                        Log.secD(
                                "SecFontStylePreferenceFragment",
                                "setOkButtonPressed : Application name, " + obj);
                        ApplicationInfo applicationInfo =
                                this.mFontListAdapter.mPackageManager.getApplicationInfo(obj, 128);
                        applicationInfo.publicSourceDir = applicationInfo.sourceDir;
                        AssetManager assets =
                                this.mFontListAdapter
                                        .mPackageManager
                                        .getResourcesForApplication(applicationInfo)
                                        .getAssets();
                        Log.secD(
                                "SecFontStylePreferenceFragment",
                                "tpf.getFileName(): " + typefaceFile.getFileName());
                        InputStream open = assets.open("fonts/" + typefaceFile.getFileName());
                        boolean copyFontFile =
                                fontWriter.copyFontFile(
                                        createFontDirectory, open, typefaceFile.getDroidName());
                        open.close();
                        z2 = copyFontFile;
                    } catch (Exception unused) {
                        String fileName = typefaceFile.getFileName();
                        String droidName = typefaceFile.getDroidName();
                        try {
                            InputStream openInputStream =
                                    this.mContext
                                            .getContentResolver()
                                            .openInputStream(
                                                    Uri.parse(
                                                            "content://"
                                                                    + obj
                                                                    + "/fonts/"
                                                                    + fileName));
                            try {
                                z2 =
                                        fontWriter.copyFontFile(
                                                createFontDirectory, openInputStream, droidName);
                                if (openInputStream != null) {
                                    openInputStream.close();
                                }
                            } catch (Throwable th) {
                                if (openInputStream != null) {
                                    try {
                                        openInputStream.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                            }
                        } catch (Exception e) {
                            Log.secE(
                                    "SecFontStylePreferenceFragment",
                                    "cannot copy font file : " + e.getMessage());
                            z2 = false;
                        }
                    }
                }
                z = z2;
            }
            if (z) {
                Log.v("SecFontStylePreferenceFragment", "**setOkButtonPressed - enospc error **");
                showWarningDialog(obj);
                return;
            }
            fontWriter.deleteFontDirectory(substring);
            try {
                fontWriter.writeLoc(
                        createFontDirectory.getAbsolutePath()
                                + "#"
                                + this.mFontListAdapter
                                        .mFontNames
                                        .get(this.mCurrentFontIndex)
                                        .toString());
            } catch (RuntimeException e2) {
                Log.secD(
                        "SecFontStylePreferenceFragment",
                        "fontWriter.writeLoc() : RuntimeException occured. " + e2);
            }
        }
        new Handler(Looper.getMainLooper())
                .post(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.display.SecFontStylePreferenceFragment$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecFontStylePreferenceFragment secFontStylePreferenceFragment =
                                        SecFontStylePreferenceFragment.this;
                                String str2 = obj2;
                                secFontStylePreferenceFragment.getClass();
                                try {
                                    try {
                                        IActivityManager service = ActivityManager.getService();
                                        Configuration globalConfiguration =
                                                service.getGlobalConfiguration();
                                        if (str2.equals("default")) {
                                            globalConfiguration.FlipFont = 1;
                                        } else {
                                            globalConfiguration.FlipFont =
                                                    Math.abs(str2.hashCode()) + 1;
                                        }
                                        service.updateConfiguration(globalConfiguration);
                                    } catch (RemoteException e3) {
                                        Log.secE(
                                                "SecFontStylePreferenceFragment",
                                                "cannot update Configuration : " + e3.getMessage());
                                    }
                                    secFontStylePreferenceFragment.finish();
                                } catch (Throwable th3) {
                                    secFontStylePreferenceFragment.finish();
                                    throw th3;
                                }
                            }
                        });
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "flip_font_style", this.mCurrentFontIndex);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mFontListAdapter == null || this.mCallbackFromDownloadFont) {
            if (!this.mIsRunLoadListTask) {
                LoadListTask loadListTask = new LoadListTask();
                this.mLoadListTask = loadListTask;
                loadListTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                Log.secD("SecFontStylePreferenceFragment", "called onResume()");
            }
            this.mCallbackFromDownloadFont = false;
        }
    }

    public final void setFontStyleList() {
        this.mFontStyleListView.setAdapter(this.mFontListAdapter);
        FontListAdapter fontListAdapter = this.mFontListAdapter;
        if (fontListAdapter != null) {
            fontListAdapter.mOnItemClickListener = this;
        }
        if (fontListAdapter == null) {
            Log.secD(
                    "SecFontStylePreferenceFragment",
                    "setCurrentFontStyle : mFontListAdapter is null, so return.");
        } else {
            String[] split = Typeface.semGetFontPathOfCurrentFontStyle(this.mContext, 1).split("/");
            String str = split.length - 1 > 0 ? split[split.length - 1] : "default";
            if (!str.equals("default")) {
                str = str.concat(".xml");
            }
            int indexOf = this.mFontListAdapter.mTypefaceFiles.indexOf(str);
            this.mCurrentFontIndex = indexOf;
            this.mFontListAdapter.setItemChecked(indexOf);
        }
        this.mFontStyleListView.post(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.display.SecFontStylePreferenceFragment$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecFontStylePreferenceFragment secFontStylePreferenceFragment =
                                SecFontStylePreferenceFragment.this;
                        int i = secFontStylePreferenceFragment.mCurrentFontIndex;
                        if (i == -1
                                || secFontStylePreferenceFragment.mFontStyleListView.getChildAt(i)
                                        == null) {
                            return;
                        }
                        NestedScrollView nestedScrollView =
                                secFontStylePreferenceFragment.mFontStyleScrollView;
                        nestedScrollView.smoothScrollBy(
                                0 - nestedScrollView.getScrollX(),
                                ((int)
                                                secFontStylePreferenceFragment
                                                        .mFontStyleListView
                                                        .getChildAt(
                                                                secFontStylePreferenceFragment
                                                                        .mCurrentFontIndex)
                                                        .getY())
                                        - nestedScrollView.getScrollY(),
                                IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                    }
                });
    }

    public final void showWarningDialog(String str) {
        this.mUninstallFontsPackageName = str;
        AlertDialog create =
                new AlertDialog.Builder(this.mContext)
                        .setMessage(com.android.settings.R.string.sec_monotype_not_supported_font)
                        .setPositiveButton(
                                com.android.settings.R.string.delete,
                                this.mWarningDialogClickListener)
                        .setNegativeButton(R.string.cancel, this.mWarningDialogClickListener)
                        .setOnCancelListener(
                                new DialogInterface
                                        .OnCancelListener() { // from class:
                                                              // com.samsung.android.settings.display.SecFontStylePreferenceFragment$$ExternalSyntheticLambda3
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        SecFontStylePreferenceFragment
                                                secFontStylePreferenceFragment =
                                                        SecFontStylePreferenceFragment.this;
                                        int i = secFontStylePreferenceFragment.mPreviousFontIndex;
                                        secFontStylePreferenceFragment.mCurrentFontIndex = i;
                                        FontListAdapter fontListAdapter =
                                                secFontStylePreferenceFragment.mFontListAdapter;
                                        if (fontListAdapter != null) {
                                            fontListAdapter.setItemChecked(i);
                                        }
                                        dialogInterface.dismiss();
                                    }
                                })
                        .create();
        this.mWarningDialog = create;
        create.show();
    }
}
