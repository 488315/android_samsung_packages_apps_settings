package com.samsung.android.settings.galaxysystemupdate.apps;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.galaxysystemupdate.apps.data.GalaxySystemAppsInfoManager;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GalaxySystemAppsFragment extends SettingsPreferenceFragment {
    public GalaxySystemAppsAdapter adapter;
    public FragmentActivity context;
    public Rect decorationRect;
    public int mHorizontalPadding;
    public SeslRoundedCorner mListRoundedCorner;
    public RoundedDecoration mRoundedDecoration;
    public SettingsBaseActivity settingsBaseActivity;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RoundedDecoration extends RecyclerView.ItemDecoration {
        public RoundedDecoration() {}

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void seslOnDispatchDraw(
                Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            GalaxySystemAppsFragment galaxySystemAppsFragment = GalaxySystemAppsFragment.this;
            galaxySystemAppsFragment.decorationRect.set(
                    galaxySystemAppsFragment.mHorizontalPadding,
                    0,
                    recyclerView.getWidth() - galaxySystemAppsFragment.mHorizontalPadding,
                    recyclerView.getHeight());
            SeslRoundedCorner seslRoundedCorner = galaxySystemAppsFragment.mListRoundedCorner;
            seslRoundedCorner.mRoundedCornerBounds.set(galaxySystemAppsFragment.decorationRect);
            seslRoundedCorner.drawRoundedCornerInternal$1(canvas);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 60180;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        this.context = getActivity();
        if (Looper.myLooper() == null) {
            throw new NullPointerException("There is no looper in the calling thread.");
        }
        this.mHorizontalPadding = Utils.getListHorizontalPadding(this.context);
        this.decorationRect = new Rect();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 1, 0, R.string.common_edit);
        if (GalaxySystemAppsInfoManager.getActiveApexList(this.context, true).size() == 0) {
            menu.getItem(0).setEnabled(false);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
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
                GalaxySystemAppsInfoManager.getActiveApexList(this.context, false);
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
                GalaxySystemAppsAdapter galaxySystemAppsAdapter = new GalaxySystemAppsAdapter();
                galaxySystemAppsAdapter.listener = null;
                galaxySystemAppsAdapter.searchData = activeApexList;
                this.adapter = galaxySystemAppsAdapter;
                recyclerView.setAdapter(galaxySystemAppsAdapter);
                getContext();
                recyclerView.setLayoutManager(new LinearLayoutManager(1));
                this.adapter.listener =
                        new GalaxySystemAppsFragment$$ExternalSyntheticLambda0(
                                this, activeApexList);
            } else {
                textView.setVisibility(0);
                recyclerView.setVisibility(8);
            }
        }
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return false;
        }
        showDeleteActivity(null);
        return true;
    }

    public final void showDeleteActivity(String str) {
        Bundle m = str != null ? AbsAdapter$1$$ExternalSyntheticOutline0.m("pkg_name", str) : null;
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = DeleteGalaxySystemAppsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = m;
        subSettingLauncher.setTitleRes(R.string.galaxy_system_apps, null);
        launchRequest.mSourceMetricsCategory = 60180;
        subSettingLauncher.launch();
    }
}
