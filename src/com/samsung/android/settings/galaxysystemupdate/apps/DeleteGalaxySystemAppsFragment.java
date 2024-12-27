package com.samsung.android.settings.galaxysystemupdate.apps;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SettingsBaseActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.galaxysystemupdate.apps.data.ApexPackageVo;
import com.samsung.android.settings.galaxysystemupdate.apps.data.GalaxySystemAppsInfoManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DeleteGalaxySystemAppsFragment extends SettingsPreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public DeleteGalaxySystemAppsAdapter adapter;
    public FragmentActivity context;
    public Rect decorationRect;
    public GalaxySystemAppsInfoDialogFragment dialog;
    public Handler handler;
    public int mHorizontalPadding;
    public SeslRoundedCorner mListRoundedCorner;
    public RoundedDecoration mRoundedDecoration;
    public RelativeLayout removeButtonLayout;
    public Queue seletedPackageQueue;
    public SettingsBaseActivity settingsBaseActivity;
    public String selectedPkgName = null;
    public AnonymousClass1 galaxySystemAppsInstallerListener = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.galaxysystemupdate.apps.DeleteGalaxySystemAppsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RoundedDecoration extends RecyclerView.ItemDecoration {
        public RoundedDecoration() {}

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void seslOnDispatchDraw(
                Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            DeleteGalaxySystemAppsFragment deleteGalaxySystemAppsFragment =
                    DeleteGalaxySystemAppsFragment.this;
            deleteGalaxySystemAppsFragment.decorationRect.set(
                    deleteGalaxySystemAppsFragment.mHorizontalPadding,
                    0,
                    recyclerView.getWidth() - deleteGalaxySystemAppsFragment.mHorizontalPadding,
                    recyclerView.getHeight());
            SeslRoundedCorner seslRoundedCorner = deleteGalaxySystemAppsFragment.mListRoundedCorner;
            seslRoundedCorner.mRoundedCornerBounds.set(
                    deleteGalaxySystemAppsFragment.decorationRect);
            seslRoundedCorner.drawRoundedCornerInternal$1(canvas);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x0216, code lost:

       if (r4 == 0) goto L152;
    */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01fb, code lost:

       r4.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01f9, code lost:

       if (r4 == 0) goto L152;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00ff A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0141 A[Catch: all -> 0x0145, SecurityException -> 0x0149, IOException -> 0x014d, LOOP:0: B:27:0x013b->B:29:0x0141, LOOP_END, TryCatch #23 {IOException -> 0x014d, SecurityException -> 0x0149, all -> 0x0145, blocks: (B:26:0x0139, B:27:0x013b, B:29:0x0141, B:31:0x0151), top: B:25:0x0139 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0151 A[EDGE_INSN: B:30:0x0151->B:31:0x0151 BREAK  A[LOOP:0: B:27:0x013b->B:29:0x0141], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0156 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x016f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10, types: [android.content.pm.PackageInstaller$Session] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13, types: [android.content.pm.PackageInstaller$Session] */
    /* JADX WARN: Type inference failed for: r2v14, types: [android.content.pm.PackageInstaller$Session] */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v29 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v30 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r4v10, types: [android.content.pm.PackageInstaller$Session] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12, types: [android.content.pm.PackageInstaller$Session] */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v16, types: [android.content.pm.PackageInstaller$Session] */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v21, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v16, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22 */
    /* JADX WARN: Type inference failed for: r6v23, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v24 */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v26 */
    /* JADX WARN: Type inference failed for: r6v28, types: [android.content.IntentFilter] */
    /* JADX WARN: Type inference failed for: r6v30 */
    /* JADX WARN: Type inference failed for: r6v31 */
    /* JADX WARN: Type inference failed for: r6v32 */
    /* JADX WARN: Type inference failed for: r6v33 */
    /* JADX WARN: Type inference failed for: r6v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void downgradeGalaxySystemApps() {
        /*
            Method dump skipped, instructions count: 619
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.galaxysystemupdate.apps.DeleteGalaxySystemAppsFragment.downgradeGalaxySystemApps():void");
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
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("pkg_name");
            this.selectedPkgName = string;
            if (string != null) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("selected_pos : "),
                        this.selectedPkgName,
                        "DeleteGalaxySystemAppsFragment");
            }
        } else {
            Log.d("DeleteGalaxySystemAppsFragment", "icicle is null");
        }
        setHasOptionsMenu(false);
        this.context = getActivity();
        Looper myLooper = Looper.myLooper();
        if (myLooper == null) {
            throw new NullPointerException("There is no looper in the calling thread.");
        }
        this.handler = new Handler(myLooper);
        this.seletedPackageQueue = new LinkedList();
        this.mHorizontalPadding = Utils.getListHorizontalPadding(this.context);
        this.decorationRect = new Rect();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View findViewById;
        View inflate =
                layoutInflater.inflate(R.layout.sec_galaxy_system_apps_package, (ViewGroup) null);
        this.settingsBaseActivity = (SettingsBaseActivity) getActivity();
        NestedScrollView nestedScrollView = (NestedScrollView) inflate.findViewById(R.id.container);
        nestedScrollView.semSetRoundedCorners(15);
        nestedScrollView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        nestedScrollView.semSetRoundedCornerOffset(this.mHorizontalPadding);
        SettingsBaseActivity settingsBaseActivity = this.settingsBaseActivity;
        if ((settingsBaseActivity instanceof SecSettingsBaseActivity)
                && (findViewById = settingsBaseActivity.findViewById(R.id.round_corner)) != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            int i = this.mHorizontalPadding;
            layoutParams.setMarginsRelative(i, 0, i, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        ArrayList activeApexList =
                GalaxySystemAppsInfoManager.getActiveApexList(getContext(), true);
        if (this.selectedPkgName != null) {
            Iterator it = activeApexList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ApexPackageVo apexPackageVo = (ApexPackageVo) it.next();
                if (apexPackageVo.packageName.equals(this.selectedPkgName)) {
                    apexPackageVo.isSelected = true;
                    break;
                }
            }
        }
        TextView textView = (TextView) inflate.findViewById(R.id.text_no_item);
        RecyclerView recyclerView =
                (RecyclerView) inflate.findViewById(R.id.galaxy_system_package_list);
        if (recyclerView != null) {
            int i2 = this.mHorizontalPadding;
            recyclerView.setPadding(i2, 0, i2, 0);
            recyclerView.setScrollBarStyle(33554432);
            recyclerView.seslSetFillHorizontalPaddingEnabled(true);
            if (activeApexList.size() > 0) {
                if (this.mListRoundedCorner == null) {
                    this.mListRoundedCorner = new SeslRoundedCorner(this.context);
                }
                try {
                    this.mListRoundedCorner.setRoundedCorners(15);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                if (this.mRoundedDecoration == null) {
                    this.mRoundedDecoration = new RoundedDecoration();
                }
                recyclerView.addItemDecoration(this.mRoundedDecoration);
                textView.setVisibility(8);
                recyclerView.setVisibility(0);
                DeleteGalaxySystemAppsAdapter deleteGalaxySystemAppsAdapter =
                        new DeleteGalaxySystemAppsAdapter();
                deleteGalaxySystemAppsAdapter.listener = null;
                deleteGalaxySystemAppsAdapter.searchData = activeApexList;
                this.adapter = deleteGalaxySystemAppsAdapter;
                recyclerView.setAdapter(deleteGalaxySystemAppsAdapter);
                getContext();
                recyclerView.setLayoutManager(new LinearLayoutManager(1));
                this.adapter.listener =
                        new DeleteGalaxySystemAppsFragment$$ExternalSyntheticLambda0(this);
            } else {
                textView.setVisibility(0);
                recyclerView.setVisibility(8);
            }
        }
        RelativeLayout relativeLayout =
                (RelativeLayout) this.settingsBaseActivity.findViewById(R.id.button_bar);
        this.removeButtonLayout = relativeLayout;
        final DeleteGalaxySystemAppsFragment$$ExternalSyntheticLambda0
                deleteGalaxySystemAppsFragment$$ExternalSyntheticLambda0 =
                        new DeleteGalaxySystemAppsFragment$$ExternalSyntheticLambda0(this);
        relativeLayout.setOnClickListener(
                new DeleteGalaxySystemAppsFragment$$ExternalSyntheticLambda2());
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView)
                        ((LayoutInflater) this.context.getSystemService("layout_inflater"))
                                .inflate(
                                        R.layout.sec_bottom_naviagtion_delete_layout,
                                        (ViewGroup) this.removeButtonLayout,
                                        false);
        bottomNavigationView.selectedListener =
                new BottomNavigationView
                        .OnNavigationItemSelectedListener() { // from class:
                                                              // com.samsung.android.settings.galaxysystemupdate.apps.DeleteGalaxySystemAppsFragment$$ExternalSyntheticLambda3
                    @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                    public final boolean onNavigationItemSelected(MenuItem menuItem) {
                        int i3 = DeleteGalaxySystemAppsFragment.$r8$clinit;
                        DeleteGalaxySystemAppsFragment deleteGalaxySystemAppsFragment =
                                DeleteGalaxySystemAppsFragment.this;
                        deleteGalaxySystemAppsFragment.getClass();
                        if (menuItem.getItemId() == R.id.delete_btn) {
                            int size =
                                    ((LinkedList)
                                                    deleteGalaxySystemAppsFragment
                                                            .seletedPackageQueue)
                                            .size();
                            GalaxySystemAppsInfoDialogFragment galaxySystemAppsInfoDialogFragment =
                                    new GalaxySystemAppsInfoDialogFragment();
                            galaxySystemAppsInfoDialogFragment.isSingular = true;
                            galaxySystemAppsInfoDialogFragment.listener =
                                    deleteGalaxySystemAppsFragment$$ExternalSyntheticLambda0;
                            if (size > 1) {
                                galaxySystemAppsInfoDialogFragment.isSingular = false;
                            }
                            deleteGalaxySystemAppsFragment.dialog =
                                    galaxySystemAppsInfoDialogFragment;
                            galaxySystemAppsInfoDialogFragment.show(
                                    deleteGalaxySystemAppsFragment
                                            .requireActivity()
                                            .getSupportFragmentManager(),
                                    ApnSettings.MVNO_NONE);
                        }
                        return false;
                    }
                };
        this.removeButtonLayout.addView(bottomNavigationView);
        if (this.selectedPkgName != null) {
            this.removeButtonLayout.setVisibility(0);
        } else {
            this.removeButtonLayout.setVisibility(8);
        }
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.galaxySystemAppsInstallerListener != null) {
            this.galaxySystemAppsInstallerListener = null;
        }
        super.onDestroy();
    }
}
