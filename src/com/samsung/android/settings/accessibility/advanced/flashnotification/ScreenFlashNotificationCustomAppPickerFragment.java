package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Keep;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.picker.features.composable.custom.CustomFrame;
import androidx.picker.features.composable.custom.CustomStrategy;
import androidx.picker.features.composable.custom.CustomViewHolder;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.widget.AppPickerState$OnStateChangeListener;
import androidx.picker.widget.SeslAppPickerListView;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.accessibility.FlashNotificationsUtil;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.LoadingViewController;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenFlashNotificationCustomAppPickerFragment extends SettingsPreferenceFragment
        implements MenuItem.OnActionExpandListener {
    public static SeslAppPickerListView appPickerListView;
    public static Context context;
    public static final HashMap items = new HashMap();
    public static FragmentManager parentFragmentManager;
    public LoadingViewController loadingViewController;
    public ViewGroup pinnedHeader;
    public SearchView searchView;
    public final Set installedAppSet = new HashSet();
    public boolean searchExpanded = false;
    public int checkedItemCount = 0;
    public final AnonymousClass1 onBackPressedCallback =
            new OnBackPressedCallback() { // from class:
                                          // com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppPickerFragment.1
                @Override // androidx.activity.OnBackPressedCallback
                public final void handleOnBackPressed() {
                    HashMap hashMap = ScreenFlashNotificationCustomAppPickerFragment.items;
                    ScreenFlashNotificationCustomAppPickerFragment
                            screenFlashNotificationCustomAppPickerFragment =
                                    ScreenFlashNotificationCustomAppPickerFragment.this;
                    if (screenFlashNotificationCustomAppPickerFragment.checkedItemCount <= 0) {
                        Toast.makeText(
                                        ScreenFlashNotificationCustomAppPickerFragment.context,
                                        R.string.screen_flash_notification_no_apps_selected_toast,
                                        0)
                                .show();
                    }
                    FragmentActivity activity =
                            screenFlashNotificationCustomAppPickerFragment.getActivity();
                    if (activity != null) {
                        activity.finish();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Keep
    public static class ColorChipCustomViewHolder extends CustomViewHolder {
        private ImageButton colorChipButton;

        public ColorChipCustomViewHolder(View view) {
            super(view);
            this.colorChipButton = (ImageButton) view.findViewById(R.id.color_chip);
        }

        @Override // androidx.picker.features.composable.custom.CustomViewHolder
        public void bindData(AppInfoData appInfoData) {
            final FlashNotificationUtil.ScreenFlashInfo screenFlashInfo =
                    (FlashNotificationUtil.ScreenFlashInfo)
                            ScreenFlashNotificationCustomAppPickerFragment.items.get(
                                    appInfoData.getPackageName());
            if (!appInfoData.getSelected()) {
                this.colorChipButton.setVisibility(8);
                return;
            }
            this.colorChipButton.setVisibility(0);
            String string =
                    ScreenFlashNotificationCustomAppPickerFragment.context.getString(
                            R.string.screen_flash_notification_color_setting_icon_app_picker,
                            FlashNotificationsUtil.getColorDescriptionText(
                                    ScreenFlashNotificationCustomAppPickerFragment.context,
                                    screenFlashInfo.color),
                            appInfoData.getLabel());
            Drawable mutate =
                    AppCompatResources.getDrawable(
                                    ScreenFlashNotificationCustomAppPickerFragment.context,
                                    R.drawable.screen_flash_color_chip)
                            .mutate();
            mutate.setTintList(
                    FlashNotificationUtil.getColorChipStateList(
                            ScreenFlashNotificationCustomAppPickerFragment.context,
                            screenFlashInfo.color));
            this.colorChipButton.setImageDrawable(mutate);
            this.colorChipButton.setContentDescription(string);
            this.colorChipButton.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppPickerFragment.ColorChipCustomViewHolder.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            final FlashNotificationUtil.ScreenFlashInfo screenFlashInfo2 =
                                    FlashNotificationUtil.ScreenFlashInfo.this;
                            ScreenFlashNotificationColorDialogFragment.getInstance(
                                            screenFlashInfo2.color,
                                            new Consumer() { // from class:
                                                             // com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppPickerFragment$ColorChipCustomViewHolder$1$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    FlashNotificationUtil.ScreenFlashInfo
                                                            screenFlashInfo3 =
                                                                    FlashNotificationUtil
                                                                            .ScreenFlashInfo.this;
                                                    screenFlashInfo3.color =
                                                            ((Integer) obj).intValue();
                                                    ScreenFlashNotificationCustomAppPickerFragment
                                                            .m1087$$Nest$smsetScreenFlashColorWithAliases(
                                                                    screenFlashInfo3);
                                                    ScreenFlashNotificationCustomAppPickerFragment
                                                            .appPickerListView
                                                            .getAdapter()
                                                            .notifyDataSetChanged();
                                                }
                                            })
                                    .show(
                                            ScreenFlashNotificationCustomAppPickerFragment
                                                    .parentFragmentManager,
                                            "ScreenFlashNotificationColorDialogFragment");
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Keep
    public static class ColorChipStrategy extends CustomStrategy {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppPickerFragment$ColorChipStrategy$1, reason: invalid class name */
        public final class AnonymousClass1 implements CustomFrame {
            @Override // androidx.picker.features.composable.ComposableFrame
            public final int getLayoutResId() {
                return R.layout.screen_flash_notification_app_list_item_color_chip_layout;
            }

            @Override // androidx.picker.features.composable.ComposableFrame
            public final Class getViewHolderClass() {
                return ColorChipCustomViewHolder.class;
            }
        }

        @Override // androidx.picker.features.composable.custom.CustomStrategy
        public List<CustomFrame> getCustomFrameList() {
            return Collections.singletonList(new AnonymousClass1());
        }
    }

    /* renamed from: -$$Nest$smsetScreenFlashColorWithAliases, reason: not valid java name */
    public static boolean m1087$$Nest$smsetScreenFlashColorWithAliases(
            FlashNotificationUtil.ScreenFlashInfo screenFlashInfo) {
        String[] strArr =
                (String[])
                        ((HashMap) FlashNotificationUtil.ALIAS_MAP)
                                .get(screenFlashInfo.packageName);
        if (strArr == null) {
            return setScreenFlashColor(screenFlashInfo);
        }
        boolean z = true;
        for (String str : strArr) {
            if (str != null) {
                z &=
                        setScreenFlashColor(
                                new FlashNotificationUtil.ScreenFlashInfo(
                                        str, screenFlashInfo.color, screenFlashInfo.checked));
            }
        }
        return setScreenFlashColor(screenFlashInfo) && z;
    }

    public static boolean setScreenFlashColor(
            FlashNotificationUtil.ScreenFlashInfo screenFlashInfo) {
        boolean z;
        boolean z2;
        Log.d(
                "ScreenFlashNotificationCustomAppPickerFragment",
                "setScreenFlashColor: " + screenFlashInfo);
        ContentResolver contentResolver = context.getContentResolver();
        String string =
                Settings.Secure.getString(contentResolver, "screen_flash_notification_color_apps");
        if (string == null) {
            string = ApnSettings.MVNO_NONE;
        }
        int i = screenFlashInfo.color;
        boolean z3 = screenFlashInfo.checked;
        String str = screenFlashInfo.packageName;
        if ("com.android.server.telecom".equals(str) && !screenFlashInfo.checked) {
            HashMap hashMap = items;
            FlashNotificationUtil.ScreenFlashInfo screenFlashInfo2 =
                    (FlashNotificationUtil.ScreenFlashInfo)
                            hashMap.get("com.samsung.android.incallui");
            FlashNotificationUtil.ScreenFlashInfo screenFlashInfo3 =
                    (FlashNotificationUtil.ScreenFlashInfo)
                            hashMap.get("com.samsung.android.dialer");
            if (screenFlashInfo2 != null && (z2 = screenFlashInfo2.checked)) {
                i = screenFlashInfo2.color;
                z3 = z2;
            } else if (screenFlashInfo3 != null && (z = screenFlashInfo3.checked)) {
                i = screenFlashInfo3.color;
                z3 = z;
            }
        }
        Matcher matcher =
                Pattern.compile(str.replaceAll("\\.", "\\\\\\.") + "[^;]*;").matcher(string);
        while (matcher.find()) {
            string = string.replace(matcher.group(), ApnSettings.MVNO_NONE);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        sb.append(str);
        sb.append('#');
        sb.append(String.format("%08X", Integer.valueOf(i)));
        sb.append('#');
        sb.append(z3 ? '1' : '0');
        sb.append(';');
        return Settings.Secure.putString(
                contentResolver, "screen_flash_notification_color_apps", sb.toString());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 6013;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context2) {
        super.onAttach(context2);
        context = context2;
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getOnBackPressedDispatcher().addCallback(this.onBackPressedCallback);
        }
        parentFragmentManager = getParentFragmentManager();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (getActivity() == null || menu == null) {
            return;
        }
        menuInflater.inflate(R.menu.accessibility_flashnoti_menu, menu);
        MenuItem findItem = menu.findItem(R.id.search_app_list_menu);
        if (findItem != null) {
            findItem.setShowAsAction(10);
            findItem.setVisible(true);
            findItem.setOnActionExpandListener(this);
            SearchView searchView = (SearchView) findItem.getActionView();
            this.searchView = searchView;
            searchView.onSearchClicked();
            this.searchView.requestFocus();
            SearchView searchView2 = this.searchView;
            searchView2.mMaxWidth = Preference.DEFAULT_ORDER;
            searchView2.requestLayout();
            this.searchView.mOnQueryChangeListener = new AnonymousClass2();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        onCreateView.requireViewById(R.id.recycler_view).setImportantForAccessibility(2);
        int listHorizontalPadding = Utils.getListHorizontalPadding(context);
        this.pinnedHeader = (ViewGroup) onCreateView.requireViewById(R.id.pinned_header);
        View inflate =
                layoutInflater.inflate(
                        R.layout.screen_flash_notification_pinned_header_description,
                        viewGroup,
                        false);
        inflate.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        this.pinnedHeader.addView(inflate);
        this.pinnedHeader.setVisibility(0);
        View inflate2 =
                layoutInflater.inflate(
                        R.layout.screen_flash_notification_app_picker, viewGroup, false);
        inflate2.setVisibility(8);
        ((ViewGroup) onCreateView.requireViewById(android.R.id.list_container)).addView(inflate2);
        SeslAppPickerListView seslAppPickerListView =
                (SeslAppPickerListView) inflate2.requireViewById(R.id.app_picker_view);
        appPickerListView = seslAppPickerListView;
        seslAppPickerListView.setAppListOrder(2);
        appPickerListView.setItemAnimator(null);
        appPickerListView.seslSetGoToTopEnabled(true);
        appPickerListView.seslSetFastScrollerEnabled(true);
        appPickerListView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        View requireViewById = onCreateView.requireViewById(R.id.loading_container);
        TextView textView = (TextView) onCreateView.requireViewById(android.R.id.empty);
        textView.setText(R.string.no_applications);
        this.loadingViewController = new LoadingViewController(requireViewById, inflate2, textView);
        updateTitle$1();
        return onCreateView;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        remove();
        super.onDetach();
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        this.searchExpanded = false;
        this.pinnedHeader.setVisibility(0);
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        this.searchExpanded = true;
        this.pinnedHeader.setVisibility(8);
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getA11ySettingsMetricsFeatureProvider().clicked(6013, "A11Y0001");
            if (this.checkedItemCount <= 0) {
                Toast.makeText(
                                context,
                                R.string.screen_flash_notification_no_apps_selected_toast,
                                0)
                        .show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        int i = this.checkedItemCount > 0 ? 1 : 0;
        Settings.System.putInt(context.getContentResolver(), "screen_flash_notification", i);
        Settings.System.putInt(
                context.getContentResolver(), "screen_flash_notification_color_mode", i);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getA11ySettingsMetricsFeatureProvider().visible(context, 0, 6013, 0);
        if (this.searchExpanded) {
            return;
        }
        this.loadingViewController.handleLoadingContainer(false, false, false);
        new Thread(
                        new ScreenFlashNotificationCustomAppPickerFragment$$ExternalSyntheticLambda1(
                                this, 0))
                .start();
    }

    public final void updateTitle$1() {
        String string;
        HashMap hashMap = items;
        this.checkedItemCount =
                (int)
                        hashMap.values().stream()
                                .filter(
                                        new ScreenFlashNotificationCustomAppPickerFragment$$ExternalSyntheticLambda0())
                                .count();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            if (this.checkedItemCount > 0) {
                string = this.checkedItemCount + "/" + hashMap.size();
            } else {
                string = activity.getString(R.string.screen_flash_notification_app_list_title);
            }
            activity.setTitle(string);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationCustomAppPickerFragment$2, reason: invalid class name */
    public final class AnonymousClass2
            implements AppPickerState$OnStateChangeListener, SearchView.OnQueryTextListener {
        public /* synthetic */ AnonymousClass2() {}

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "Text changed ", str, "ScreenFlashNotificationCustomAppPickerFragment");
            HashMap hashMap = ScreenFlashNotificationCustomAppPickerFragment.items;
            ScreenFlashNotificationCustomAppPickerFragment
                    screenFlashNotificationCustomAppPickerFragment =
                            ScreenFlashNotificationCustomAppPickerFragment.this;
            screenFlashNotificationCustomAppPickerFragment.getClass();
            ScreenFlashNotificationCustomAppPickerFragment.appPickerListView.setSearchFilter(
                    str,
                    new ScreenFlashNotificationCustomAppPickerFragment$$ExternalSyntheticLambda2(
                            screenFlashNotificationCustomAppPickerFragment));
            return true;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public void onQueryTextSubmit(String str) {
            Log.i("ScreenFlashNotificationCustomAppPickerFragment", "Text submit");
            HashMap hashMap = ScreenFlashNotificationCustomAppPickerFragment.items;
            ScreenFlashNotificationCustomAppPickerFragment
                    screenFlashNotificationCustomAppPickerFragment =
                            ScreenFlashNotificationCustomAppPickerFragment.this;
            screenFlashNotificationCustomAppPickerFragment.getClass();
            ScreenFlashNotificationCustomAppPickerFragment.appPickerListView.setSearchFilter(
                    str,
                    new ScreenFlashNotificationCustomAppPickerFragment$$ExternalSyntheticLambda2(
                            screenFlashNotificationCustomAppPickerFragment));
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateChanged(AppInfo appInfo, boolean z) {
            FlashNotificationUtil.ScreenFlashInfo screenFlashInfo =
                    (FlashNotificationUtil.ScreenFlashInfo)
                            ScreenFlashNotificationCustomAppPickerFragment.items.get(
                                    appInfo.packageName);
            screenFlashInfo.checked = !screenFlashInfo.checked;
            if (ScreenFlashNotificationCustomAppPickerFragment
                    .m1087$$Nest$smsetScreenFlashColorWithAliases(screenFlashInfo)) {
                ScreenFlashNotificationCustomAppPickerFragment.this.updateTitle$1();
            }
            ScreenFlashNotificationCustomAppPickerFragment.appPickerListView
                    .getAdapter()
                    .notifyDataSetChanged();
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateAllChanged(boolean z) {}
    }
}
