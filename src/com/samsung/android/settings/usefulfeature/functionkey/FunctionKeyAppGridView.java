package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.picker.widget.SeslAppPickerGridView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FunctionKeyAppGridView extends SettingsPreferenceFragment {
    public SeslAppPickerGridView mAppPickerGridView;
    public LauncherAppListLoader mAsyncTask;
    public ArrayList mBlockList = null;
    public Context mContext;
    public TextView mEmptyViewText;
    public ViewGroup mLoading;
    public int mPressType;
    public MenuItem mSearchMenu;
    public SearchView mSearchView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyAppGridView$3, reason: invalid class name */
    public final class AnonymousClass3 implements SearchView.OnQueryTextListener {
        public AnonymousClass3() {}

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public final boolean onQueryTextChange(String str) {
            FunctionKeyAppGridView.this.mAppPickerGridView.setSearchFilter(
                    str, new FunctionKeyAppGridView$$ExternalSyntheticLambda0(this));
            return true;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public final void onQueryTextSubmit(String str) {
            FunctionKeyAppGridView.this.mAppPickerGridView.setSearchFilter(str, null);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LauncherAppListLoader extends AsyncTask {
        public LauncherAppListLoader() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            return loadAppList();
        }

        /* JADX WARN: Removed duplicated region for block: B:70:0x01e5 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:76:0x01c4 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final synchronized java.util.List loadAppList() {
            /*
                Method dump skipped, instructions count: 605
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyAppGridView.LauncherAppListLoader.loadAppList():java.util.List");
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            FunctionKeyAppGridView.this.mLoading.setVisibility(8);
            FunctionKeyAppGridView.this.mAppPickerGridView.setVisibility(0);
            FunctionKeyAppGridView.this.mAppPickerGridView.submitList(loadAppList());
            MenuItem menuItem = FunctionKeyAppGridView.this.mSearchMenu;
            if (menuItem != null) {
                menuItem.setVisible(true);
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            FunctionKeyAppGridView.this.mLoading.setVisibility(0);
            FunctionKeyAppGridView.this.mAppPickerGridView.setVisibility(8);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final RecyclerView getListViewWithSpacing() {
        return this.mAppPickerGridView;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7616;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        LauncherAppListLoader launcherAppListLoader = new LauncherAppListLoader();
        this.mAsyncTask = launcherAppListLoader;
        launcherAppListLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        String string = getArguments().getString("pressed_type");
        if ("long".equals(string)) {
            this.mPressType = 1;
        } else if ("short".equals(string)) {
            this.mPressType = 0;
        } else {
            this.mPressType = 2;
        }
        ArrayList arrayList = new ArrayList();
        if (!Rune.supportBixbyClient()) {
            arrayList.add("AssistantHomeLauncherActivity");
        }
        Iterator<ResolveInfo> it =
                getPackageManager()
                        .queryIntentContentProviders(
                                new Intent(
                                        "com.samsung.android.intent.action.SIDE_BUTTON_SETTINGS"),
                                128)
                        .iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().providerInfo.packageName);
        }
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "com.sec.android.app.camera.Camera",
                "com.google.android.apps.googleassistant",
                "yourapp24.android.tools.alice_lite",
                "com.amazon.dee.app");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "com.microsoft.cortana",
                "com.testa.databot",
                "com.nuance.balerion",
                "com.hound.android.app");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "com.artificialsolutions.teneo.va.prod",
                "com.magnifis.parking",
                "com.multiverse.jarvis",
                "com.multiverse.jarvis.go");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "com.pa.gs.ai",
                "ai.saiy.android",
                "bolo.codeplay.com.bolo",
                "com.bulletproof.voicerec.avxfree");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "com.studios.nebulas.noah",
                "com.multiverse.fridayai",
                "jp.classmethod.android.Friendly",
                "com.google.android.apps.accessibility.voiceaccess");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "com.stupendous.voiceassistant",
                "com.voiceassistant",
                "com.avaya.endpoint.avayavoiceassistant",
                "com.thanksmister.iot.voicepanel");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "io.justoneapp.voice",
                "com.voicecommandforgoogleassistant.editingmultiapps",
                "com.combo.voiceassistant",
                "com.voice.assistant.commands");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "net.wintersun.launchalexa",
                "com.gigaaa.app.android",
                "com.siri.siriapple.siriassistant",
                "com.iflytek.cmcc");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "com.xiaomi.smarthome",
                "com.iflytek.speechcloud",
                "com.chongdong.cloud",
                "com.alibaba.ailabs.tg");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "com.tianming",
                "com.wowenwen.yy",
                "com.linglong.android",
                "com.iflytek.android.viafly.news");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList,
                "com.singulariti.niapp",
                "com.zaijia.xiaodu",
                "com.tencent.mia.speaker",
                "com.xiaomi.xiaoailite");
        arrayList.add("com.orion.xiaoya.speakerclient");
        arrayList.add("com.naver.nozzle");
        if (!UsefulfeatureUtils.isPayWithSamsungPayVisible(this.mContext)) {
            arrayList.add("com.samsung.android.spay");
            arrayList.add("com.samsung.android.spaymini");
        }
        this.mBlockList = arrayList;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.sec_labs_applist_menu, menu);
        ((Toolbar) getActivity().findViewById(R.id.action_bar))
                .setBackInvokedCallbackEnabled(false);
        getActivity()
                .getOnBackInvokedDispatcher()
                .registerSystemOnBackInvokedCallback(
                        new OnBackInvokedCallback() { // from class:
                                                      // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyAppGridView.2
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                SearchView searchView = FunctionKeyAppGridView.this.mSearchView;
                                if (searchView == null || !searchView.hasFocus()) {
                                    FunctionKeyAppGridView.this.getActivity().finish();
                                    return;
                                }
                                MenuItem menuItem = FunctionKeyAppGridView.this.mSearchMenu;
                                if (menuItem != null) {
                                    menuItem.collapseActionView();
                                }
                            }
                        });
        MenuItem findItem = menu.findItem(R.id.search_apps);
        this.mSearchMenu = findItem;
        this.mSearchView = (SearchView) findItem.getActionView();
        SeslAppPickerGridView seslAppPickerGridView = this.mAppPickerGridView;
        if (seslAppPickerGridView != null && seslAppPickerGridView.getVisibility() == 8) {
            this.mSearchMenu.setVisible(false);
        }
        LinearLayout linearLayout = (LinearLayout) this.mSearchView.findViewById(R.id.search_plate);
        if (linearLayout != null) {
            linearLayout.setPadding(
                    0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
        }
        this.mSearchView.setQueryHint(getString(R.string.sec_app_search_title));
        this.mSearchView.mOnQueryChangeListener = new AnonymousClass3();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_app_shortcut_view, viewGroup, false);
        SeslAppPickerGridView seslAppPickerGridView =
                (SeslAppPickerGridView) inflate.findViewById(R.id.appshortcutview);
        this.mAppPickerGridView = seslAppPickerGridView;
        if (seslAppPickerGridView != null) {
            seslAppPickerGridView.setAppListOrder(1);
            this.mAppPickerGridView.setNestedScrollingEnabled(true);
            this.mAppPickerGridView.seslSetFastScrollerEnabled(true);
            this.mAppPickerGridView.seslSetGoToTopEnabled(true);
            this.mAppPickerGridView.seslSetFillBottomEnabled(false);
            this.mAppPickerGridView.addFooter(
                    getLayoutInflater()
                            .inflate(
                                    R.layout.sec_allowed_networks_settings_footer,
                                    (ViewGroup) null));
            this.mAppPickerGridView.addHeader(
                    getLayoutInflater()
                            .inflate(
                                    R.layout.sec_allowed_network_empty_header_view,
                                    (ViewGroup) null),
                    0);
            this.mAppPickerGridView.setItemAnimator(null);
            this.mAppPickerGridView.setOnItemClickEventListener(
                    new FunctionKeyAppGridView$$ExternalSyntheticLambda0(this));
            this.mAppPickerGridView.setVisibility(8);
        }
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.loading_panel);
        this.mLoading = viewGroup2;
        viewGroup2.semSetRoundedCorners(3);
        this.mLoading.semSetRoundedCornerColor(
                3, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        TextView textView = (TextView) inflate.findViewById(android.R.id.empty);
        this.mEmptyViewText = textView;
        if (textView != null) {
            textView.setTextAppearance(this.mContext, R.style.no_item_text_style);
            this.mEmptyViewText.setText(R.string.sec_app_search_no_result);
            ViewGroup viewGroup3 = this.mLoading;
            if (viewGroup3 != null) {
                viewGroup3
                        .getViewTreeObserver()
                        .addOnGlobalLayoutListener(
                                new ViewTreeObserver
                                        .OnGlobalLayoutListener() { // from class:
                                                                    // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyAppGridView.1
                                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                                    public final void onGlobalLayout() {
                                        ViewGroup viewGroup4 = FunctionKeyAppGridView.this.mLoading;
                                        if (viewGroup4 != null) {
                                            viewGroup4
                                                    .getViewTreeObserver()
                                                    .removeGlobalOnLayoutListener(this);
                                            FunctionKeyAppGridView.this.mEmptyViewText.setMaxWidth(
                                                    (FunctionKeyAppGridView.this.mLoading
                                                                            .getMeasuredWidth()
                                                                    / 4)
                                                            * 3);
                                            FunctionKeyAppGridView.this.mEmptyViewText.setGravity(
                                                    17);
                                        }
                                    }
                                });
            }
        }
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        if (this.mAsyncTask != null) {
            Log.d("FunctionKeyAppGridView", "App list load canceled");
            this.mAsyncTask.cancel(true);
            this.mAsyncTask = null;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mLoading.getVisibility() == 0) {
            this.mLoading.setVisibility(8);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        MenuItem menuItem = this.mSearchMenu;
        if (menuItem == null || this.mEmptyViewText == null) {
            return;
        }
        menuItem.collapseActionView();
        this.mEmptyViewText.setVisibility(8);
    }
}
