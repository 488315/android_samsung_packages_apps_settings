package com.android.settings.applications.manageapplications;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.role.RoleManager;
import android.app.usage.IUsageStatsManager;
import android.app.usage.StorageStats;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.text.AlphabeticIndex;
import android.icu.text.NumberFormat;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IUserManager;
import android.os.LocaleList;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.preference.PreferenceFrameLayout;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.ArraySet;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SectionIndexer;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.appcompat.view.SeslContentViewInsetsCallback;
import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.airbnb.lottie.LottieAnimationView;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.AppStateAlarmsAndRemindersBridge;
import com.android.settings.applications.AppStateAppOpsBridge;
import com.android.settings.applications.AppStateBaseBridge;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.applications.AppStateInstallAppsBridge;
import com.android.settings.applications.AppStateManageExternalStorageBridge;
import com.android.settings.applications.AppStateMediaManagementAppsBridge;
import com.android.settings.applications.AppStateNotificationBridge;
import com.android.settings.applications.AppStateOverlayBridge;
import com.android.settings.applications.AppStateTurnScreenOnBridge;
import com.android.settings.applications.AppStateUsageBridge;
import com.android.settings.applications.AppStateWriteSettingsBridge;
import com.android.settings.applications.AppStorageSettings;
import com.android.settings.applications.AppStoreUtil;
import com.android.settings.applications.ProcessStatsSummary;
import com.android.settings.applications.UsageAccessDetails;
import com.android.settings.applications.appinfo.AlarmsAndRemindersDetails;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.applications.appinfo.AppLocaleDetails;
import com.android.settings.applications.appinfo.DrawOverlayDetails;
import com.android.settings.applications.appinfo.ExternalSourcesDetails;
import com.android.settings.applications.appinfo.LongBackgroundTasksDetails;
import com.android.settings.applications.appinfo.ManageExternalStorageDetails;
import com.android.settings.applications.appinfo.MediaManagementAppsDetails;
import com.android.settings.applications.appinfo.TurnScreenOnDetails;
import com.android.settings.applications.appinfo.WriteSettingsDetails;
import com.android.settings.applications.manageapplications.ManageApplications;
import com.android.settings.applications.specialaccess.SpecialAccessSettings;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.fuelgauge.AdvancedPowerUsageDetail;
import com.android.settings.fuelgauge.HighPowerDetail;
import com.android.settings.homepage.TopLevelSettings;
import com.android.settings.localepicker.AppLocalePickerActivity;
import com.android.settings.nfc.AppStateNfcTagAppsBridge;
import com.android.settings.nfc.ChangeNfcTagAppsStateDetails;
import com.android.settings.notification.ConfigureNotificationSettings;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.AppNotificationSettings;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.SecurityAdvancedSettings;
import com.android.settings.spa.SpaActivity;
import com.android.settings.spa.app.AllAppListPageProvider;
import com.android.settings.spa.app.appcompat.UserAspectRatioAppsPageProvider;
import com.android.settings.spa.app.appinfo.AppInfoSettingsProvider;
import com.android.settings.spa.app.appinfo.CloneAppInfoSettingsProvider;
import com.android.settings.spa.app.battery.BatteryOptimizationModeAppListPageProvider;
import com.android.settings.widget.LoadingViewController;
import com.android.settings.wifi.AppStateChangeWifiStateBridge;
import com.android.settings.wifi.ChangeWifiStateDetails;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;
import com.android.settingslib.applications.AppIconCacheManager;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.fuelgauge.PowerAllowlistBackend;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.LottieColorUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.os.SemDvfsManager;
import com.samsung.android.sdhms.SemAppRestrictionManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.applications.AppCommonUtils;
import com.samsung.android.settings.applications.AppStateManageFullScreenIntentsBridge;
import com.samsung.android.settings.applications.AppStateMediaRoutingAppsBridge;
import com.samsung.android.settings.applications.appinfo.FullScreenIntentsDetails;
import com.samsung.android.settings.applications.appinfo.MediaRoutingAppsDetails;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.general.GeneralDeviceSettings;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.sec.ims.im.ImIntent;
import com.sec.ims.settings.ImsProfile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.WeakHashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ManageApplications extends SettingsPreferenceFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, SecSettingsBaseActivity.onKeyEventListener {
    static final String EXTRA_EXPAND_SEARCH_VIEW = "expand_search_view";
    public final AnonymousClass1 handler;
    public ApplicationsAdapter mApplications;
    public ApplicationsState mApplicationsState;
    public String mCurrentPkgName;
    public int mCurrentUid;
    public View mEmptyView;
    public TextView mEmptyViewText;
    boolean mExpandSearch;
    public AppFilterItem mFilter;
    FilterSpinnerAdapter mFilterAdapter;
    public View mFilterEmptyView;
    public Spinner mFilterSpinner;
    public int mFilterType;
    public boolean mFromPowerUsageSettings;
    public boolean mFromSecuritySettings;
    public String mHighlightKey;
    public CharSequence mInvalidSizeStr;
    public boolean mIsPageLaunchedBySearch;
    public boolean mIsPersonalOnly;
    public boolean mIsPrivateProfileOnly;
    public boolean mIsReducedMargin;
    public boolean mIsSecureOrSeparationUserId;
    public boolean mIsWorkOnly;
    public int mListType;
    public View mLoadingContainer;
    public NotificationBackend mNotificationBackend;
    public ApplicationsState.AppEntry mNotificationClickEntry;
    public ApplicationViewHolder mNotificationClickHolder;
    public Menu mOptionsMenu;
    View mPinnedHeader;
    RecyclerView mRecyclerView;
    public AnonymousClass2 mResetAppsDialogCallback;
    public ResetAppsHelper mResetAppsHelper;
    public View mRootView;
    public SearchView mSearchView;
    public String mSearchViewText;
    public boolean mShowSystem;
    public int mStorageItemUserID;
    public int mStorageType;
    public AppFilterItem mTmpFilter;
    public int mUpdatedListCount;
    public IUsageStatsManager mUsageStatsManager;
    public UserManager mUserManager;
    public String mVolumeUuid;
    public int mWorkUserId;
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public static final Set LIST_TYPES_WITH_INSTANT = new ArraySet(Arrays.asList(0, 3));
    public static String mHideAppList = null;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass6();
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER = new AnonymousClass7();
    int mSortOrder = 0;
    public int mTmpSortOrder = 0;
    public ApplicationViewHolder mClickHolder = null;
    public String mHighlightTargetPackageName = null;
    public int mHighlightTargetPackageUid = 0;
    public boolean mSearchExpanded = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.manageapplications.ManageApplications$4, reason: invalid class name */
    public final class AnonymousClass4 implements RadioGroup.OnCheckedChangeListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;
        public final /* synthetic */ View val$customView;

        public /* synthetic */ AnonymousClass4(int i, View view, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
            this.val$customView = view;
        }

        @Override // android.widget.RadioGroup.OnCheckedChangeListener
        public final void onCheckedChanged(RadioGroup radioGroup, int i) {
            String str;
            String str2;
            switch (this.$r8$classId) {
                case 0:
                    this.val$customView.findViewById(i).announceForAccessibility(((ManageApplications) this.this$0).getString(R.string.selected));
                    if (i == R.id.order_name) {
                        ManageApplications manageApplications = (ManageApplications) this.this$0;
                        manageApplications.mTmpSortOrder = 0;
                        str = manageApplications.getString(R.string.sec_sort_by_name);
                    } else if (i == R.id.order_size) {
                        ManageApplications manageApplications2 = (ManageApplications) this.this$0;
                        manageApplications2.mTmpSortOrder = 1;
                        str = manageApplications2.getString(R.string.sec_sort_by_size);
                    } else if (i == R.id.order_last_used) {
                        ManageApplications manageApplications3 = (ManageApplications) this.this$0;
                        manageApplications3.mTmpSortOrder = 2;
                        str = manageApplications3.getString(R.string.sec_sort_by_last_used);
                    } else if (i == R.id.order_last_updated) {
                        ManageApplications manageApplications4 = (ManageApplications) this.this$0;
                        manageApplications4.mTmpSortOrder = 3;
                        str = manageApplications4.getString(R.string.sec_sort_by_last_updated);
                    } else {
                        str = null;
                    }
                    AccessibilityManager accessibilityManager = (AccessibilityManager) ((ManageApplications) this.this$0).getSystemService("accessibility");
                    if (accessibilityManager != null && accessibilityManager.isEnabled() && !TextUtils.isEmpty(str)) {
                        AccessibilityEvent obtain = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
                        obtain.getText().add(str);
                        accessibilityManager.sendAccessibilityEvent(obtain);
                        break;
                    }
                    break;
                case 1:
                    this.val$customView.findViewById(i).announceForAccessibility(((ApplicationsAdapter) this.this$0).mContext.getString(R.string.selected));
                    if (i != R.id.filter_all) {
                        if (i != R.id.filter_installed_by_user) {
                            if (i != R.id.filter_enable) {
                                if (i == R.id.filter_disable) {
                                    ((ApplicationsAdapter) this.this$0).mManageApplications.mTmpFilter = AppFilterRegistry.getInstance().mFilters[7];
                                    break;
                                }
                            } else {
                                ((ApplicationsAdapter) this.this$0).mManageApplications.mTmpFilter = AppFilterRegistry.getInstance().mFilters[5];
                                break;
                            }
                        } else {
                            ((ApplicationsAdapter) this.this$0).mManageApplications.mTmpFilter = AppFilterRegistry.getInstance().mFilters[31];
                            break;
                        }
                    } else {
                        ((ApplicationsAdapter) this.this$0).mManageApplications.mTmpFilter = AppFilterRegistry.getInstance().mFilters[4];
                        break;
                    }
                    break;
                default:
                    this.val$customView.findViewById(i).announceForAccessibility(((ApplicationsAdapter) this.this$0).mContext.getString(R.string.selected));
                    if (i == R.id.order_name) {
                        ApplicationsAdapter applicationsAdapter = (ApplicationsAdapter) this.this$0;
                        applicationsAdapter.mManageApplications.mTmpSortOrder = 0;
                        str2 = applicationsAdapter.mContext.getString(R.string.sec_sort_by_name);
                    } else if (i == R.id.order_size) {
                        ApplicationsAdapter applicationsAdapter2 = (ApplicationsAdapter) this.this$0;
                        applicationsAdapter2.mManageApplications.mTmpSortOrder = 1;
                        str2 = applicationsAdapter2.mContext.getString(R.string.sec_sort_by_size);
                    } else if (i == R.id.order_last_used) {
                        ApplicationsAdapter applicationsAdapter3 = (ApplicationsAdapter) this.this$0;
                        applicationsAdapter3.mManageApplications.mTmpSortOrder = 2;
                        str2 = applicationsAdapter3.mContext.getString(R.string.sec_sort_by_last_used);
                    } else if (i == R.id.order_last_updated) {
                        ApplicationsAdapter applicationsAdapter4 = (ApplicationsAdapter) this.this$0;
                        applicationsAdapter4.mManageApplications.mTmpSortOrder = 3;
                        str2 = applicationsAdapter4.mContext.getString(R.string.sec_sort_by_last_updated);
                    } else {
                        str2 = null;
                    }
                    AccessibilityManager accessibilityManager2 = (AccessibilityManager) ((ApplicationsAdapter) this.this$0).mContext.getSystemService("accessibility");
                    if (accessibilityManager2 != null && accessibilityManager2.isEnabled() && !TextUtils.isEmpty(str2)) {
                        AccessibilityEvent obtain2 = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
                        obtain2.getText().add(str2);
                        accessibilityManager2.sendAccessibilityEvent(obtain2);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.manageapplications.ManageApplications$6, reason: invalid class name */
    public final class AnonymousClass6 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider, com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "app_perms";
            ((SearchIndexableData) searchIndexableRaw).intentAction = "android.intent.action.MANAGE_PERMISSIONS";
            searchIndexableRaw.title = String.valueOf(R.string.app_permissions);
            searchIndexableRaw.keywords = context.getResources().getString(R.string.keywords_more_apps_permissions_settings);
            searchIndexableRaw.screenTitle = context.getResources().getString(R.string.category_applications);
            arrayList.add(searchIndexableRaw);
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw2).key = "app_settings";
            Set set = AppCommonUtils.SEARCH_CLASS_NAME_LIST;
            searchIndexableRaw2.title = String.valueOf(R.string.sec_app_settings);
            searchIndexableRaw2.keywords = context.getResources().getString(R.string.sec_app_settings);
            searchIndexableRaw2.screenTitle = context.getResources().getString(R.string.category_applications);
            arrayList.add(searchIndexableRaw2);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider, com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_app_settings_search;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.manageapplications.ManageApplications$7, reason: invalid class name */
    public final class AnonymousClass7 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
            ManageApplications.m735$$Nest$smaddStatusData(arrayList, roleManager.getRoleHolders("android.app.role.ASSISTANT"), 99);
            ManageApplications.m735$$Nest$smaddStatusData(arrayList, roleManager.getRoleHolders("android.app.role.BROWSER"), 3891);
            ManageApplications.m735$$Nest$smaddStatusData(arrayList, roleManager.getRoleHolders("android.app.role.DIALER"), 3893);
            ManageApplications.m735$$Nest$smaddStatusData(arrayList, roleManager.getRoleHolders("android.app.role.HOME"), 55);
            ManageApplications.m735$$Nest$smaddStatusData(arrayList, roleManager.getRoleHolders("android.app.role.SMS"), 3895);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DividerItemDecorator extends RecyclerView.ItemDecoration {
        public final Drawable mDivider;

        public DividerItemDecorator(Drawable drawable) {
            this.mDivider = drawable;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
            ManageApplications manageApplications = ManageApplications.this;
            TypedArray obtainStyledAttributes = manageApplications.getContext().obtainStyledAttributes(new int[]{android.R.attr.listPreferredItemPaddingStart, android.R.attr.listPreferredItemPaddingEnd});
            int dimension = (int) (manageApplications.getResources().getDimension(R.dimen.sec_app_list_item_icon_min_width) + manageApplications.getResources().getDimension(R.dimen.sec_widget_list_item_padding_start));
            int paddingStart = recyclerView.getPaddingStart() + recyclerView.getLeft() + dimension;
            int right = ((int) (recyclerView.getRight() - obtainStyledAttributes.getDimension(1, 0.0f))) - recyclerView.getPaddingEnd();
            if (recyclerView.getLayoutDirection() == 1) {
                paddingStart = recyclerView.getPaddingEnd() + ((int) obtainStyledAttributes.getDimension(1, 0.0f));
                right = (recyclerView.getRight() - recyclerView.getPaddingStart()) - dimension;
            }
            obtainStyledAttributes.recycle();
            int childCount = recyclerView.getChildCount() - 2;
            for (int i = 0; i <= childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                if (childAt.getTag() == null) {
                    int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) childAt.getLayoutParams())).bottomMargin;
                    this.mDivider.setBounds(paddingStart, bottom, right, this.mDivider.getIntrinsicHeight() + bottom);
                    this.mDivider.draw(canvas);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FilterSpinnerAdapter extends ArrayAdapter {
        public final Context mContext;
        public final ArrayList mFilterOptions;
        public final ManageApplications mManageApplications;

        public FilterSpinnerAdapter(ManageApplications manageApplications) {
            super(manageApplications.getContext(), android.R.layout.simple_spinner_item);
            this.mFilterOptions = new ArrayList();
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.mContext = manageApplications.getContext();
            this.mManageApplications = manageApplications;
        }

        public final void enableFilter(int i) {
            AppFilterItem appFilterItem = AppFilterRegistry.getInstance().mFilters[i];
            if (this.mFilterOptions.contains(appFilterItem)) {
                return;
            }
            boolean z = ManageApplications.DEBUG;
            if (z) {
                Log.d("ManageApplications", "Enabling filter " + ((Object) this.mContext.getText(appFilterItem.mTitle)));
            }
            this.mFilterOptions.add(appFilterItem);
            if (this.mManageApplications.mListType != 1) {
                Collections.sort(this.mFilterOptions);
            }
            updateFilterView(this.mFilterOptions.size() > 1);
            notifyDataSetChanged();
            if (this.mFilterOptions.size() == 1) {
                if (z) {
                    Log.d("ManageApplications", "Auto selecting filter " + appFilterItem + " " + ((Object) this.mContext.getText(appFilterItem.mTitle)));
                }
                this.mManageApplications.mFilterSpinner.setSelection(0);
                this.mManageApplications.onItemSelected(null, null, 0, 0L);
            }
            if (this.mFilterOptions.size() > 1) {
                int indexOf = this.mFilterOptions.indexOf(AppFilterRegistry.getInstance().mFilters[this.mManageApplications.mFilterType]);
                if (indexOf != -1) {
                    this.mManageApplications.mFilterSpinner.setSelection(indexOf);
                    this.mManageApplications.onItemSelected(null, null, indexOf, 0L);
                }
            }
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final int getCount() {
            return this.mFilterOptions.size();
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final Object getItem(int i) {
            return this.mContext.getText(((AppFilterItem) this.mFilterOptions.get(i)).mTitle);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            TextView textView = (TextView) view2.findViewById(android.R.id.text1);
            ArrayList arrayList = this.mManageApplications.mApplications.mEntries;
            int size = arrayList != null ? arrayList.size() : 0;
            int i2 = this.mManageApplications.mListType;
            if ((i2 == 6 || i2 == 5 || i2 == 13 || i2 == 12 || i2 == 4 || i2 == 11 || i2 == 8 || i2 == 10 || i2 == 103) && size > 0) {
                size--;
            }
            NumberFormat numberFormat = NumberFormat.getInstance(this.mContext.getResources().getConfiguration().getLocales().get(0));
            if (this.mManageApplications.mListType == 1) {
                textView.setText(this.mContext.getText(((AppFilterItem) this.mFilterOptions.get(i)).mTitle).toString());
            } else {
                textView.setText(this.mContext.getText(((AppFilterItem) this.mFilterOptions.get(i)).mTitle).toString() + " (" + numberFormat.format(size) + ")");
            }
            textView.setTextColor(Utils.getColorAttr(this.mContext, android.R.attr.textColorPrimary));
            return view2;
        }

        public void updateFilterView(boolean z) {
            ManageApplications manageApplications = this.mManageApplications;
            if (manageApplications.mListType == 0) {
                return;
            }
            if (z) {
                manageApplications.mPinnedHeader.setVisibility(0);
            } else {
                manageApplications.mPinnedHeader.setVisibility(8);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RoundedDecoration extends RecyclerView.ItemDecoration {
        public RoundedDecoration() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void seslOnDispatchDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            ManageApplications manageApplications = ManageApplications.this;
            SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(manageApplications.getActivity());
            seslRoundedCorner.setRoundedCorners(15);
            seslRoundedCorner.setRoundedCornerColor(15, manageApplications.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            SeslRoundedCorner seslRoundedCorner2 = new SeslRoundedCorner(manageApplications.getActivity());
            seslRoundedCorner2.setRoundedCorners(3);
            seslRoundedCorner2.setRoundedCornerColor(3, manageApplications.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            SeslRoundedCorner seslRoundedCorner3 = new SeslRoundedCorner(manageApplications.getActivity());
            seslRoundedCorner3.setRoundedCorners(12);
            seslRoundedCorner3.setRoundedCornerColor(12, manageApplications.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                int i2 = recyclerView.getChildViewHolder(childAt).mItemViewType;
                if (i2 == 8) {
                    if (manageApplications.mListType == 0) {
                        seslRoundedCorner3.drawRoundedCorner(childAt, canvas);
                    } else {
                        seslRoundedCorner.drawRoundedCorner(childAt, canvas);
                    }
                } else if (i2 == 6 && manageApplications.mListType != 0) {
                    seslRoundedCorner2.drawRoundedCorner(childAt, canvas);
                } else if (i2 == 7 || i2 == 1) {
                    seslRoundedCorner3.drawRoundedCorner(childAt, canvas);
                } else if (i2 == 9 && manageApplications.mListType == 0) {
                    ApplicationsAdapter applicationsAdapter = manageApplications.mApplications;
                    if (applicationsAdapter.mUseFooter && applicationsAdapter.getItemCount() == 2) {
                        seslRoundedCorner3.drawRoundedCorner(childAt, canvas);
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SectionInfo {
        public final String label;
        public final int position;

        public SectionInfo(String str, int i) {
            this.label = str;
            this.position = i;
        }

        public final String toString() {
            return this.label;
        }
    }

    /* renamed from: -$$Nest$smaddStatusData, reason: not valid java name */
    public static void m735$$Nest$smaddStatusData(ArrayList arrayList, List list, int i) {
        if (list.isEmpty()) {
            return;
        }
        String valueOf = String.valueOf(i);
        String str = (String) list.get(0);
        StatusData statusData = new StatusData();
        statusData.mStatusValue = str;
        statusData.mStatusKey = valueOf;
        arrayList.add(statusData);
    }

    /* renamed from: -$$Nest$smisSupportedRelativeLink, reason: not valid java name */
    public static boolean m736$$Nest$smisSupportedRelativeLink(ManageApplications manageApplications) {
        Context context = manageApplications.getContext();
        return manageApplications.mListType == 0 && Rune.supportRelativeLink() && !com.android.settings.Utils.isGuestMode(context) && !KnoxUtils.isApplicationRestricted(context, "Settings_Relative_Link_View", "hide");
    }

    public ManageApplications() {
        new Handler() { // from class: com.android.settings.applications.manageapplications.ManageApplications.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                ManageApplications manageApplications = ManageApplications.this;
                ApplicationsAdapter applicationsAdapter = manageApplications.mApplications;
                if (applicationsAdapter != null) {
                    if (applicationsAdapter.mResumed) {
                        applicationsAdapter.mResumed = false;
                        applicationsAdapter.mSession.onPause();
                        AppStateBaseBridge appStateBaseBridge = applicationsAdapter.mExtraInfoBridge;
                        if (appStateBaseBridge != null) {
                            appStateBaseBridge.pause();
                        }
                    }
                    manageApplications.mApplications.resume(manageApplications.mSortOrder);
                    manageApplications.mApplications.updateLoading();
                }
            }
        };
        this.mNotificationClickHolder = null;
        this.mNotificationClickEntry = null;
    }

    public static String getClassName(Intent intent, Bundle bundle) {
        String string = bundle != null ? bundle.getString("classname") : null;
        return string == null ? intent.getComponent().getClassName() : string;
    }

    public static ApplicationsState.AppFilter getCompositeFilter(int i, int i2, String str) {
        ApplicationsState.AnonymousClass15 anonymousClass15 = new ApplicationsState.AnonymousClass15(str);
        if (i == 3) {
            return i2 == 0 ? new ApplicationsState.AnonymousClass30(ApplicationsState.FILTER_APPS_EXCEPT_GAMES, anonymousClass15) : anonymousClass15;
        }
        if (i == 9) {
            return new ApplicationsState.AnonymousClass30(ApplicationsState.FILTER_GAMES, anonymousClass15);
        }
        return null;
    }

    public static int getTitleResId(Intent intent, Bundle bundle) {
        int intExtra = intent.getIntExtra(":settings:show_fragment_title_resid", R.string.category_applications);
        String className = getClassName(intent, bundle);
        if (className.equals(Settings.UsageAccessSettingsActivity.class.getName())) {
            return R.string.usage_access;
        }
        if (className.equals(Settings.HighPowerApplicationsActivity.class.getName())) {
            return R.string.high_power_apps;
        }
        if (className.equals(Settings.OverlaySettingsActivity.class.getName())) {
            return AppCommonUtils.getOverlayTitle();
        }
        if (className.equals(Settings.WriteSettingsActivity.class.getName())) {
            return AppCommonUtils.getWriteSettingsTitle();
        }
        if (className.equals(Settings.ManageExternalSourcesActivity.class.getName())) {
            return R.string.install_other_apps;
        }
        if (className.equals(Settings.ChangeWifiStateActivity.class.getName())) {
            return R.string.change_wifi_state_title;
        }
        if (className.equals(Settings.ManageExternalStorageActivity.class.getName())) {
            return R.string.manage_external_storage_title;
        }
        if (className.equals(Settings.MediaManagementAppsActivity.class.getName())) {
            return R.string.media_management_apps_title;
        }
        if (className.equals(Settings.AlarmsAndRemindersActivity.class.getName())) {
            return R.string.alarms_and_reminders_title;
        }
        if (className.equals(Settings.NotificationAppListActivity.class.getName()) || className.equals(Settings.NotificationReviewPermissionsActivity.class.getName())) {
            return R.string.app_notifications_title;
        }
        if (className.equals(AppLocaleDetails.class.getName())) {
            return R.string.app_locales_picker_menu_title;
        }
        if (className.equals(Settings.AppBatteryUsageActivity.class.getName())) {
            return R.string.app_battery_usage_title;
        }
        if (className.equals(Settings.LongBackgroundTasksActivity.class.getName())) {
            return R.string.long_background_tasks_title;
        }
        if (className.equals(Settings.ClonedAppsListActivity.class.getName())) {
            return R.string.cloned_apps_dashboard_title;
        }
        if (className.equals(Settings.ChangeNfcTagAppsActivity.class.getName())) {
            return R.string.change_nfc_tag_apps_title;
        }
        if (className.equals(Settings.TurnScreenOnSettingsActivity.class.getName())) {
            return R.string.turn_screen_on_title;
        }
        if (!className.equals("AppSetting")) {
            return className.equals(Settings.ManageUnknownSourceAppsActivity.class.getName()) ? R.string.sec_manage_unknown_app : className.equals(Settings.ManageFullScreenIntentsActivity.class.getName()) ? R.string.sec_full_screen_intent_title : className.equals(Settings.MediaRoutingActivity.class.getName()) ? R.string.media_routing_control_title : intExtra == -1 ? R.string.category_applications : intExtra;
        }
        Set set = AppCommonUtils.SEARCH_CLASS_NAME_LIST;
        return R.string.sec_app_settings;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002f, code lost:
    
        r1 = true;
        android.util.Log.d("ManageApplications", "allow list app:true");
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004d, code lost:
    
        if (r2 != null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004f, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005b, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0058, code lost:
    
        if (r2 == null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean hasAllowListByMDM(androidx.fragment.app.FragmentActivity r10, java.lang.String r11) {
        /*
            java.lang.String r0 = "ManageApplications"
            r1 = 0
            r2 = 0
            java.lang.String r3 = "content://com.sec.knox.provider2/ApplicationPolicy"
            android.net.Uri r5 = android.net.Uri.parse(r3)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            android.content.ContentResolver r4 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            java.lang.String r7 = "getAllPackagesFromBatteryOptimizationWhiteList"
            r9 = 0
            r6 = 0
            r8 = 0
            android.database.Cursor r2 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            if (r2 == 0) goto L4d
            boolean r10 = r2.moveToFirst()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            if (r10 == 0) goto L4d
        L1f:
            java.lang.String r10 = "getAllPackagesFromBatteryOptimizationWhiteList"
            int r10 = r2.getColumnIndex(r10)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            java.lang.String r10 = r2.getString(r10)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            boolean r10 = r11.equals(r10)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            if (r10 == 0) goto L47
            r1 = 1
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            r10.<init>()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            java.lang.String r11 = "allow list app:"
            r10.append(r11)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            r10.append(r1)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            android.util.Log.d(r0, r10)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            goto L4d
        L45:
            r10 = move-exception
            goto L5c
        L47:
            boolean r10 = r2.moveToNext()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L53
            if (r10 != 0) goto L1f
        L4d:
            if (r2 == 0) goto L5b
        L4f:
            r2.close()
            goto L5b
        L53:
            java.lang.String r10 = "failed to get allow list by MDM."
            android.util.Log.d(r0, r10)     // Catch: java.lang.Throwable -> L45
            if (r2 == 0) goto L5b
            goto L4f
        L5b:
            return r1
        L5c:
            if (r2 == 0) goto L61
            r2.close()
        L61:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.applications.manageapplications.ManageApplications.hasAllowListByMDM(androidx.fragment.app.FragmentActivity, java.lang.String):boolean");
    }

    public static void insertToggleEventLogging(int i, int i2, String str, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put("pkgname", str);
        hashMap.put("newValue", z ? "On" : "Off");
        SALogging.insertSALog(Integer.toString(i), Integer.toString(i2), hashMap, 0);
    }

    public static SpannableString updateSearchKeywordColorSpan(FragmentActivity fragmentActivity, ApplicationsState.AppEntry appEntry, TextView textView) {
        SpannableString spannableString = new SpannableString(appEntry.label);
        StringTokenizer stringTokenizer = new StringTokenizer((String) AppUtils.mSearchKeyword, " ");
        for (ForegroundColorSpan foregroundColorSpan : (ForegroundColorSpan[]) spannableString.getSpans(0, spannableString.length(), ForegroundColorSpan.class)) {
            spannableString.removeSpan(foregroundColorSpan);
        }
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            String str = appEntry.label;
            int i = 0;
            do {
                char[] semGetPrefixCharForSpan = TextUtils.semGetPrefixCharForSpan(textView.getPaint(), str, nextToken.toCharArray());
                if (semGetPrefixCharForSpan != null) {
                    nextToken = new String(semGetPrefixCharForSpan);
                }
                String lowerCase = str.toLowerCase();
                int indexOf = str.length() == lowerCase.length() ? lowerCase.indexOf(nextToken.toLowerCase()) : str.indexOf(nextToken);
                int length = nextToken.length() + indexOf;
                if (indexOf < 0) {
                    break;
                }
                int i2 = indexOf + i;
                i += length;
                spannableString.setSpan(new TypefaceSpan(Typeface.create(Typeface.create("sec", 1), 600, false)), i2, i, 33);
                spannableString.setSpan(new ForegroundColorSpan(fragmentActivity.getColor(R.color.sesl_primary_dark_color_light)), i2, i, 33);
                str = str.substring(length);
                if (str.toLowerCase().indexOf(nextToken.toLowerCase()) != -1) {
                }
            } while (i < 200);
        }
        return spannableString;
    }

    public void createHeader() {
        FragmentActivity activity = getActivity();
        FrameLayout frameLayout = (FrameLayout) this.mRootView.findViewById(R.id.pinned_header);
        int i = this.mListType;
        if (i != 0 && i != 101) {
            View inflate = activity.getLayoutInflater().inflate(R.layout.sec_apps_filter_spinner, (ViewGroup) frameLayout, false);
            this.mPinnedHeader = inflate;
            inflate.semSetRoundedCorners(15);
            this.mPinnedHeader.semSetRoundedCornerColor(15, getContext().getColor(R.color.sec_widget_round_and_bgcolor));
            Spinner spinner = (Spinner) this.mPinnedHeader.findViewById(R.id.filter_spinner);
            this.mFilterSpinner = spinner;
            spinner.semSetRoundedCorners(15);
            this.mFilterSpinner.semSetRoundedCornerColor(15, getContext().getColor(R.color.sec_widget_round_and_bgcolor));
            FilterSpinnerAdapter filterSpinnerAdapter = new FilterSpinnerAdapter(this);
            this.mFilterAdapter = filterSpinnerAdapter;
            this.mFilterSpinner.setAdapter((SpinnerAdapter) filterSpinnerAdapter);
            this.mFilterSpinner.setOnItemSelectedListener(this);
            frameLayout.addView(this.mPinnedHeader, 0);
        }
        AppFilterRegistry appFilterRegistry = AppFilterRegistry.getInstance();
        int i2 = this.mListType;
        appFilterRegistry.getClass();
        int defaultFilterType = AppFilterRegistry.getDefaultFilterType(i2);
        int i3 = this.mListType;
        if (i3 != 0 && i3 != 101) {
            this.mFilterAdapter.enableFilter(defaultFilterType);
        }
        if (this.mListType == 1) {
            this.mFilterAdapter.enableFilter(2);
            this.mFilterAdapter.enableFilter(3);
            this.mFilterAdapter.enableFilter(16);
            this.mFilterAdapter.enableFilter(28);
            FilterSpinnerAdapter filterSpinnerAdapter2 = this.mFilterAdapter;
            filterSpinnerAdapter2.getClass();
            AppFilterItem appFilterItem = AppFilterRegistry.getInstance().mFilters[4];
            if (filterSpinnerAdapter2.mFilterOptions.remove(appFilterItem)) {
                boolean z = DEBUG;
                if (z) {
                    Log.d("ManageApplications", "Disabling filter " + appFilterItem + " " + ((Object) filterSpinnerAdapter2.mContext.getText(appFilterItem.mTitle)));
                }
                Collections.sort(filterSpinnerAdapter2.mFilterOptions);
                filterSpinnerAdapter2.updateFilterView(filterSpinnerAdapter2.mFilterOptions.size() > 1);
                filterSpinnerAdapter2.notifyDataSetChanged();
                if (filterSpinnerAdapter2.mManageApplications.mFilter == appFilterItem && filterSpinnerAdapter2.mFilterOptions.size() > 0) {
                    if (z) {
                        Log.d("ManageApplications", "Auto selecting filter " + filterSpinnerAdapter2.mFilterOptions.get(0) + ((Object) filterSpinnerAdapter2.mContext.getText(((AppFilterItem) filterSpinnerAdapter2.mFilterOptions.get(0)).mTitle)));
                    }
                    filterSpinnerAdapter2.mManageApplications.mFilterSpinner.setSelection(0);
                    filterSpinnerAdapter2.mManageApplications.onItemSelected(null, null, 0, 0L);
                }
            }
        }
        if (this.mListType == 5) {
            this.mFilterAdapter.enableFilter(1);
        }
        if (this.mListType == 15) {
            this.mFilterAdapter.enableFilter(4);
            this.mFilterAdapter.enableFilter(21);
            this.mFilterAdapter.enableFilter(22);
            this.mFilterAdapter.enableFilter(23);
        }
        setCompositeFilter$1();
    }

    public final int getEventCategory() {
        int i = this.mListType;
        if (i == 4) {
            return 3922;
        }
        if (i == 19) {
            return 3925;
        }
        if (i == 6) {
            return 3913;
        }
        if (i == 7) {
            return 3916;
        }
        if (i == 8) {
            return 3920;
        }
        if (i == 104) {
            return 3926;
        }
        if (i == 105) {
            return 3927;
        }
        switch (i) {
            case 10:
                return 3924;
            case 11:
                return 3911;
            case 12:
                return 3921;
            case 13:
                return 3915;
            default:
                return 0;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        int i = this.mListType;
        if (i == 1) {
            return ConfigureNotificationSettings.class.getName();
        }
        if (i != 19) {
            if (i == 101) {
                return ManageApplications.class.getName();
            }
            switch (i) {
                case 4:
                case 6:
                case 7:
                    break;
                case 5:
                    return this.mFromPowerUsageSettings ? AdvancedPowerUsageDetail.class.getName() : SpecialAccessSettings.class.getName();
                case 8:
                    return this.mFromSecuritySettings ? SecurityAdvancedSettings.class.getName() : SpecialAccessSettings.class.getName();
                default:
                    switch (i) {
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            break;
                        case 14:
                            return GeneralDeviceSettings.class.getName();
                        default:
                            switch (i) {
                                case 103:
                                    return this.mFromSecuritySettings ? SecurityAdvancedSettings.class.getName() : SpecialAccessSettings.class.getName();
                                case 104:
                                case 105:
                                    break;
                                default:
                                    return TopLevelSettings.class.getName();
                            }
                    }
            }
        }
        return SpecialAccessSettings.class.getName();
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final RecyclerView getListViewWithSpacing() {
        return this.mRecyclerView;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        int i = this.mListType;
        if (i == 0) {
            return 65;
        }
        if (i == 1) {
            return 133;
        }
        if (i == 104) {
            return 39000;
        }
        if (i == 105) {
            return 40000;
        }
        switch (i) {
            case 3:
                return 182;
            case 4:
                return 95;
            case 5:
                return 184;
            case 6:
                return 221;
            case 7:
                return 1976;
            case 8:
                return 808;
            case 9:
                return 838;
            case 10:
                return FileType.XLSB;
            case 11:
                return 1822;
            case 12:
                return 1869;
            case 13:
                return 1874;
            case 14:
                return 1911;
            case 15:
                return 1931;
            case 16:
                return 1975;
            case 17:
                return 1989;
            case 18:
                return 2016;
            case 19:
                return 2020;
            default:
                return 0;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return getTopLevelPreferenceKey(getIntent(), getArguments());
    }

    public final boolean isFastScrollEnabled$1() {
        int i = this.mListType;
        if (i == 0 || i == 1) {
            return true;
        }
        return (i == 3 || i == 9 || i == 6 || i == 7) && this.mSortOrder == 0;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        final String str;
        if (i != 1 || (str = this.mCurrentPkgName) == null) {
            return;
        }
        int i3 = this.mListType;
        if (i3 == 5 || i3 == 6 || i3 == 7 || i3 == 18) {
            this.mApplications.mExtraInfoBridge.forceUpdate(this.mCurrentUid, str);
            return;
        }
        final ApplicationsState applicationsState = this.mApplicationsState;
        final int userId = UserHandle.getUserId(this.mCurrentUid);
        synchronized (applicationsState.mEntriesMap) {
            try {
                final ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) ((HashMap) applicationsState.mEntriesMap.get(userId)).get(str);
                if (appEntry != null && ApplicationsState.hasFlag(appEntry.info.flags, 8388608)) {
                    applicationsState.mBackgroundHandler.post(new Runnable() { // from class: com.android.settingslib.applications.ApplicationsState$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ApplicationsState applicationsState2 = ApplicationsState.this;
                            ApplicationsState.AppEntry appEntry2 = appEntry;
                            String str2 = str;
                            int i4 = userId;
                            ApplicationsState.BackgroundHandler backgroundHandler = applicationsState2.mBackgroundHandler;
                            try {
                                try {
                                    StorageStats queryStatsForPackage = applicationsState2.mStats.queryStatsForPackage(appEntry2.info.storageUuid, str2, UserHandle.of(i4));
                                    long cacheQuotaBytes = applicationsState2.mStats.getCacheQuotaBytes(appEntry2.info.storageUuid.toString(), appEntry2.info.uid);
                                    PackageStats packageStats = new PackageStats(str2, i4);
                                    packageStats.codeSize = queryStatsForPackage.getAppBytes();
                                    packageStats.dataSize = queryStatsForPackage.getDataBytes();
                                    packageStats.cacheSize = Math.min(queryStatsForPackage.getCacheBytes(), cacheQuotaBytes);
                                    backgroundHandler.mStatsObserver.onGetStatsCompleted(packageStats, true);
                                } catch (PackageManager.NameNotFoundException | IOException | NullPointerException e) {
                                    Log.w("ApplicationsState", "Failed to query stats: " + e);
                                    backgroundHandler.mStatsObserver.getClass();
                                }
                            } catch (RemoteException unused) {
                            }
                        }
                    });
                }
            } finally {
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        int intValue = ((Integer) ((LinkedHashMap) ManageApplicationsUtil.LIST_TYPE_MAP).getOrDefault(getClassName(getActivity().getIntent(), getArguments()), 0)).intValue();
        this.mListType = intValue;
        Intrinsics.checkNotNullParameter(context, "context");
        String str = null;
        if (FeatureFlagUtils.isEnabled(context, "settings_enable_spa")) {
            switch (intValue) {
                case 0:
                    AllAppListPageProvider allAppListPageProvider = AllAppListPageProvider.INSTANCE;
                    str = "AllAppList";
                    break;
                case 1:
                    str = "AppListNotifications";
                    break;
                case 6:
                    str = "TogglePermissionAppList/".concat("DisplayOverOtherApps");
                    break;
                case 7:
                    str = "TogglePermissionAppList/".concat("ModifySystemSettings");
                    break;
                case 8:
                    str = "TogglePermissionAppList/".concat("InstallUnknownApps");
                    break;
                case 10:
                    str = "TogglePermissionAppList/".concat("WifiControl");
                    break;
                case 11:
                    str = "TogglePermissionAppList/".concat("AllFilesAccess");
                    break;
                case 12:
                    str = "TogglePermissionAppList/".concat("AlarmsAndReminders");
                    break;
                case 13:
                    str = "TogglePermissionAppList/".concat("MediaManagementApps");
                    break;
                case 14:
                    str = "AppLanguages";
                    break;
                case 15:
                    BatteryOptimizationModeAppListPageProvider batteryOptimizationModeAppListPageProvider = BatteryOptimizationModeAppListPageProvider.INSTANCE;
                    str = "BatteryOptimizationModeAppList";
                    break;
                case 16:
                    str = "TogglePermissionAppList/".concat("LongBackgroundTasksApps");
                    break;
                case 18:
                    str = "TogglePermissionAppList/".concat("NfcTagAppsSettings");
                    break;
                case 19:
                    str = "TogglePermissionAppList/".concat("TurnScreenOnApps");
                    break;
                case 20:
                    UserAspectRatioAppsPageProvider userAspectRatioAppsPageProvider = UserAspectRatioAppsPageProvider.INSTANCE;
                    str = "UserAspectRatioAppsPage";
                    break;
            }
        }
        if (str != null) {
            SpaActivity.Companion companion = SpaActivity.Companion;
            SpaActivity.Companion.startSpaActivity(context, str);
            getActivity().finish();
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        ApplicationsState.AppEntry appEntry;
        ApplicationInfo applicationInfo;
        if (this.mApplications == null) {
            return;
        }
        this.mRecyclerView.getClass();
        int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition == -1) {
            Log.w("ManageApplications", "Cannot find position for child, skipping onClick handling");
            return;
        }
        ArrayList arrayList = this.mApplications.mEntries;
        if ((arrayList != null ? arrayList.size() : 0) <= childAdapterPosition || (appEntry = (ApplicationsState.AppEntry) this.mApplications.mEntries.get(childAdapterPosition)) == null || (applicationInfo = appEntry.info) == null) {
            return;
        }
        this.mCurrentPkgName = applicationInfo.packageName;
        this.mCurrentUid = applicationInfo.uid;
        ApplicationViewHolder applicationViewHolder = (ApplicationViewHolder) this.mRecyclerView.getChildViewHolder(view);
        int i = this.mListType;
        if (i == 5) {
            this.mClickHolder = applicationViewHolder;
            LoggingHelper.insertEventLogging(65, 3803, !applicationViewHolder.mSwitch.isChecked());
        } else if (i == 6 || i == 7 || i == 13 || i == 12 || i == 4 || i == 11 || i == 8 || i == 10) {
            this.mClickHolder = applicationViewHolder;
        } else if (i == 1) {
            this.mNotificationClickHolder = applicationViewHolder;
            this.mNotificationClickEntry = appEntry;
        } else if (i == 104 || i == 19 || i == 105) {
            this.mClickHolder = applicationViewHolder;
        }
        startApplicationDetailsActivity(appEntry);
        RecyclerView recyclerView = this.mRecyclerView;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setNestedScrollingEnabled(recyclerView, true);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        MenuItem findItem;
        super.onConfigurationChanged(configuration);
        Menu menu = this.mOptionsMenu;
        if (menu != null && (findItem = menu.findItem(11)) != null) {
            findItem.setVisible(false);
        }
        boolean z = configuration.screenWidthDp <= 250;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null && z != this.mIsReducedMargin) {
            this.mIsReducedMargin = z;
            recyclerView.setAdapter(this.mApplications);
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Boolean valueOf;
        int i;
        SemDvfsManager createInstance;
        int i2;
        enableAutoFlowLogging(false);
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        if (activity.isFinishing()) {
            return;
        }
        setHasOptionsMenu(true);
        this.mIsReducedMargin = getResources().getConfiguration().screenWidthDp <= 250;
        new Configuration().updateFrom(getContext().getResources().getConfiguration());
        if (!com.android.settings.Utils.isDeviceProvisioned(activity) && com.android.settings.Utils.isFrpChallengeRequired(activity)) {
            Log.i("ManageApplications", "Refusing to start because device is not provisioned");
            activity.finish();
            return;
        }
        getActivity().getWindow().setDecorFitsSystemWindows(false);
        View findViewById = getActivity().findViewById(android.R.id.content);
        SeslContentViewInsetsCallback seslContentViewInsetsCallback = new SeslContentViewInsetsCallback(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(), WindowInsets.Type.ime());
        findViewById.setWindowInsetsAnimationCallback(seslContentViewInsetsCallback);
        findViewById.setOnApplyWindowInsetsListener(seslContentViewInsetsCallback);
        getActivity().getWindow().setSoftInputMode(16);
        ((SettingsActivity) activity).mOnKeyEventListener = this;
        if (getIntent().getBundleExtra(":settings:show_fragment_args") == null) {
            valueOf = Boolean.FALSE;
        } else {
            String stringExtra = getIntent().getStringExtra(":settings:fragment_args_key");
            if (stringExtra == null) {
                valueOf = Boolean.FALSE;
            } else {
                this.mHighlightKey = stringExtra;
                valueOf = Boolean.valueOf(!stringExtra.isEmpty());
            }
        }
        this.mIsPageLaunchedBySearch = valueOf.booleanValue();
        this.mUserManager = (UserManager) activity.getSystemService(UserManager.class);
        this.mApplicationsState = ApplicationsState.getInstance(activity.getApplication());
        FragmentActivity activity2 = getActivity();
        SemDvfsManager createInstance2 = SemDvfsManager.createInstance(activity2, activity2.getPackageName(), 12);
        int[] supportedFrequency = createInstance2.getSupportedFrequency();
        if (supportedFrequency == null) {
            Log.d("Booster", "mSupportedCPUFreqTable is null");
            i2 = -1;
            i = -1;
            createInstance = null;
        } else {
            int length = supportedFrequency.length;
            i = length > 1 ? supportedFrequency[1] : length > 0 ? supportedFrequency[0] : -1;
            if (i > 0) {
                createInstance2.setDvfsValue(i);
            }
            Log.d("Booster", "mCpuMinFreq = " + i);
            createInstance = SemDvfsManager.createInstance(activity2, activity2.getPackageName(), 16);
            int[] supportedFrequency2 = createInstance.getSupportedFrequency();
            if (supportedFrequency2 == null) {
                i2 = -1;
            } else {
                int length2 = supportedFrequency2.length;
                i2 = length2 > 1 ? supportedFrequency2[1] : length2 > 0 ? supportedFrequency2[0] : -1;
                if (i2 > 0) {
                    createInstance.setDvfsValue(i2);
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(i2, "mGpuMinFreq = ", "Booster");
            }
        }
        long uptimeMillis = SystemClock.uptimeMillis() - 0;
        if (uptimeMillis <= 0 || uptimeMillis >= 0) {
            if (i > 0) {
                createInstance2.acquire(3000);
            }
            if (i2 > 0) {
                createInstance.acquire(3000);
            }
        }
        Intent intent = activity.getIntent();
        Bundle arguments = getArguments();
        String string = arguments != null ? arguments.getString("fromFragment") : null;
        this.mFromSecuritySettings = string != null && string.equals("security_settings");
        this.mFromPowerUsageSettings = string != null && string.equals("power_usage");
        int titleResId = getTitleResId(intent, arguments);
        String className = getClassName(intent, arguments);
        if (className.equals("AppSetting")) {
            this.mListType = 101;
        }
        this.mUpdatedListCount = 0;
        this.mHighlightTargetPackageName = null;
        Uri data = intent.getData();
        if (data != null) {
            this.mHighlightTargetPackageName = data.getSchemeSpecificPart();
            this.mHighlightTargetPackageUid = getActivity().getUser().getIdentifier();
        }
        int i3 = this.mListType;
        if (i3 == 1) {
            this.mUsageStatsManager = IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));
            this.mNotificationBackend = new NotificationBackend();
            this.mSortOrder = R.id.sort_order_recent_notification;
            if (className.equals(Settings.NotificationReviewPermissionsActivity.class.getName())) {
                Settings.Global.putInt(getContext().getContentResolver(), "review_permissions_notification_state", 1);
            }
        } else if (i3 == 3) {
            if (arguments == null || !arguments.containsKey("volumeUuid")) {
                this.mListType = 0;
            } else {
                this.mVolumeUuid = arguments.getString("volumeUuid");
                this.mStorageType = arguments.getInt(UniversalCredentialUtil.AGENT_STORAGE_TYPE, 0);
            }
            this.mSortOrder = 1;
        } else if (i3 == 9) {
            this.mSortOrder = 1;
        } else if (i3 == 18) {
            this.mShowSystem = true;
        } else if (i3 == 5) {
            this.mShowSystem = true;
        } else if (i3 == 6) {
            try {
                Uri data2 = intent.getData();
                if (data2 != null && TextUtils.equals("package", data2.getScheme())) {
                    if (ActivityManager.getService().getLaunchedFromUid(getActivity().getActivityToken()) == -1) {
                        Log.w("ManageApplications", "Error obtaining calling uid");
                    } else if (IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat")) == null) {
                        Log.w("ManageApplications", "Error obtaining IPlatformCompat service");
                    }
                }
            } catch (RemoteException e) {
                Log.w("ManageApplications", "Error reporting SAW intent restriction", e);
            }
        }
        this.mStorageItemUserID = arguments != null ? arguments.getInt("android.intent.extra.USER_ID") : UserHandle.myUserId();
        AppFilterRegistry appFilterRegistry = AppFilterRegistry.getInstance();
        int i4 = this.mListType;
        appFilterRegistry.getClass();
        int defaultFilterType = AppFilterRegistry.getDefaultFilterType(i4);
        AppFilterItem[] appFilterItemArr = appFilterRegistry.mFilters;
        this.mFilter = appFilterItemArr[defaultFilterType];
        this.mIsPersonalOnly = arguments != null && arguments.getInt(ImsProfile.SERVICE_PROFILE) == 1;
        this.mIsWorkOnly = arguments != null && arguments.getInt(ImsProfile.SERVICE_PROFILE) == 2;
        if (SemPersonaManager.isSecureFolderId(UserHandle.getCallingUserId()) || SemPersonaManager.isAppSeparationUserId(UserHandle.getCallingUserId())) {
            Log.d("ManageApplications", "(Secure folder) or (App Separation)");
            this.mIsWorkOnly = true;
            this.mIsSecureOrSeparationUserId = true;
        }
        this.mIsPrivateProfileOnly = arguments != null && arguments.getInt(ImsProfile.SERVICE_PROFILE) == 4;
        int i5 = arguments != null ? arguments.getInt("workId") : UserHandle.myUserId();
        this.mWorkUserId = i5;
        if (this.mIsWorkOnly && i5 == UserHandle.myUserId()) {
            this.mWorkUserId = com.android.settings.Utils.getManagedProfileId(this.mUserManager, UserHandle.myUserId());
        }
        this.mApplicationsState.mWorkUserId = this.mWorkUserId;
        if (SemPersonaManager.isSecureFolderId(this.mStorageItemUserID)) {
            this.mIsWorkOnly = true;
            this.mWorkUserId = this.mStorageItemUserID;
            Preference$$ExternalSyntheticOutline0.m(new StringBuilder("(Secure folder Storage) set mIsWorkOnly and mWorkUserId : "), this.mWorkUserId, "ManageApplications");
        }
        this.mExpandSearch = activity.getIntent().getBooleanExtra(EXTRA_EXPAND_SEARCH_VIEW, false);
        if (bundle != null) {
            this.mSortOrder = bundle.getInt("sortOrder", this.mSortOrder);
            this.mShowSystem = bundle.getBoolean("showSystem", this.mShowSystem);
            this.mFilterType = bundle.getInt("filterType", 4);
            this.mExpandSearch = bundle.getBoolean(EXTRA_EXPAND_SEARCH_VIEW);
            this.mFilter = appFilterItemArr[this.mFilterType];
            this.mSearchViewText = bundle.getString("search_view_text");
        }
        String string2 = SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigBlockNotiAppList");
        mHideAppList = string2;
        if (this.mListType == 1 && string2 != null && !string2.isEmpty() && mHideAppList.split(",") == null) {
            Log.d("ManageApplications", "Hide app notification list is null");
        }
        this.mInvalidSizeStr = activity.getText(R.string.invalid_size_value);
        new Object(this) { // from class: com.android.settings.applications.manageapplications.ManageApplications.2
            public final /* synthetic */ ManageApplications this$0;
        };
        this.mResetAppsHelper = new ResetAppsHelper(getActivity());
        if (titleResId > 0) {
            activity.setTitle(titleResId);
        }
        String stringExtra2 = intent.getStringExtra(":settings:fragment_args_key");
        if (!TextUtils.isEmpty(stringExtra2)) {
            String[] split = stringExtra2.split(";");
            if (split.length == 2) {
                this.mCurrentPkgName = split[0];
                this.mCurrentUid = Integer.parseInt(split[1]);
                Log.d("ManageApplications", "searched package name : " + this.mCurrentPkgName + ", uid : " + this.mCurrentUid);
                startApplicationDetailsActivity(null);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        }
        LoggingHelper.insertFlowLogging(getMetricsCategory());
        LoggingHelper.insertEntranceLogging(getMetricsCategory());
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (getActivity() == null) {
            return;
        }
        this.mOptionsMenu = menu;
        menuInflater.inflate(R.menu.sec_manage_apps, menu);
        MenuItem findItem = menu.findItem(R.id.search_app_list_menu);
        if (findItem != null) {
            findItem.setOnActionExpandListener(this);
            SearchView searchView = (SearchView) findItem.getActionView();
            this.mSearchView = searchView;
            if (searchView != null) {
                searchView.setQueryHint(getText(R.string.sec_app_search_title));
                SearchView searchView2 = this.mSearchView;
                searchView2.mOnQueryChangeListener = this;
                searchView2.mMaxWidth = Preference.DEFAULT_ORDER;
                searchView2.requestLayout();
                AppUtils.mSearchKeyword = ApnSettings.MVNO_NONE;
                LinearLayout linearLayout = (LinearLayout) this.mSearchView.findViewById(R.id.search_plate);
                if (linearLayout != null) {
                    linearLayout.setPadding(0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
                }
                findItem.getIcon().setColorFilter(getResources().getColor(R.color.sec_search_magnifier_icon_tint_color), PorterDuff.Mode.SRC_IN);
                findItem.setShowAsAction(10);
                if (this.mExpandSearch) {
                    findItem.expandActionView();
                    String str = this.mSearchViewText;
                    if (str != null && str.length() > 0 && linearLayout != null) {
                        EditText editText = (EditText) linearLayout.findViewById(R.id.search_src_text);
                        editText.setText(this.mSearchViewText);
                        editText.setSelection(editText.length());
                    }
                }
            }
            updateOptionsMenu$1();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        PreferenceFrameLayout.LayoutParams layoutParams;
        if (getActivity().isFinishing()) {
            return null;
        }
        if (this.mListType == 6 && !com.android.settings.Utils.isSystemAlertWindowEnabled(getContext())) {
            this.mRootView = layoutInflater.inflate(R.layout.manage_applications_apps_unsupported, (ViewGroup) null);
            setHasOptionsMenu(false);
            return this.mRootView;
        }
        View inflate = layoutInflater.inflate(R.layout.sec_manage_applications_apps, (ViewGroup) null);
        this.mRootView = inflate;
        View findViewById = inflate.findViewById(R.id.loading_container);
        this.mLoadingContainer = findViewById;
        findViewById.semSetRoundedCorners(15);
        this.mLoadingContainer.semSetRoundedCornerColor(15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mEmptyView = this.mRootView.findViewById(android.R.id.empty);
        this.mEmptyViewText = (TextView) this.mRootView.findViewById(R.id.empty_view_text);
        this.mEmptyView.semSetRoundedCorners(15);
        this.mEmptyView.semSetRoundedCornerColor(15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        LinearLayout linearLayout = (LinearLayout) this.mRootView.findViewById(R.id.app_noti_sub_title);
        if (this.mListType == 1) {
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(8);
        }
        this.mRecyclerView = (RecyclerView) this.mRootView.findViewById(R.id.apps_list);
        ApplicationsAdapter applicationsAdapter = new ApplicationsAdapter(this.mApplicationsState, this, this.mFilter, bundle);
        this.mApplications = applicationsAdapter;
        if (bundle != null) {
            applicationsAdapter.mHasReceivedLoadEntries = bundle.getBoolean("hasEntries", false);
            this.mApplications.mHasReceivedBridgeCallback = bundle.getBoolean("hasBridge", false);
        }
        this.mRecyclerView.setItemAnimator(null);
        RecyclerView recyclerView = this.mRecyclerView;
        getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        this.mRecyclerView.setAdapter(this.mApplications);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.seslSetGoToTopEnabled(true);
        this.mRecyclerView.seslSetFastScrollerEnabled(isFastScrollEnabled$1());
        this.mRecyclerView.seslSetFillBottomEnabled(true);
        this.mRecyclerView.addItemDecoration(new RoundedDecoration());
        this.mRecyclerView.addItemDecoration(new DividerItemDecorator(getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
        RecyclerView.ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).mSupportsChangeAnimations = false;
        }
        final AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        if (appBarLayout != null) {
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.android.settings.applications.manageapplications.ManageApplications$$ExternalSyntheticLambda0
                @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
                public final void onOffsetChanged(AppBarLayout appBarLayout2, int i) {
                    int totalScrollRange;
                    ManageApplications manageApplications = ManageApplications.this;
                    View view = manageApplications.mEmptyView;
                    if (view == null || view.getVisibility() != 0) {
                        return;
                    }
                    int height = ((Toolbar) manageApplications.getActivity().findViewById(R.id.action_bar)).getHeight();
                    Rect rect = new Rect();
                    AppBarLayout appBarLayout3 = appBarLayout;
                    appBarLayout3.getWindowVisibleDisplayFrame(rect);
                    ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
                    int i2 = (rect.bottom - rect.top) - height;
                    if (Math.abs(i) >= appBarLayout3.getTotalScrollRange()) {
                        Log.i("ManageApplications", "new height MATCH_PARENT");
                        totalScrollRange = -1;
                    } else {
                        totalScrollRange = i2 - (appBarLayout3.getTotalScrollRange() - Math.abs(i));
                        MainClearConfirm$$ExternalSyntheticOutline0.m(totalScrollRange, "new height : ", "ManageApplications");
                    }
                    if (layoutParams2.height != totalScrollRange) {
                        layoutParams2.height = totalScrollRange;
                        view.post(new ManageApplications$$ExternalSyntheticLambda1(0, view, layoutParams2));
                    }
                }
            });
        }
        if ((viewGroup instanceof PreferenceFrameLayout) && (layoutParams = this.mRootView.getLayoutParams()) != null) {
            layoutParams.removeBorders = true;
        }
        createHeader();
        ResetAppsHelper resetAppsHelper = this.mResetAppsHelper;
        resetAppsHelper.getClass();
        if (bundle != null && bundle.getBoolean("resetDialog")) {
            resetAppsHelper.buildResetDialog();
        }
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.action_bar);
        toolbar.setBackInvokedCallbackEnabled(false);
        String str = getString(R.string.sec_app_search_title) + " " + getString(R.string.sec_toolbar_collapse_description);
        if (!TextUtils.isEmpty(str)) {
            toolbar.ensureCollapseButtonView();
        }
        AppCompatImageButton appCompatImageButton = toolbar.mCollapseButtonView;
        if (appCompatImageButton != null) {
            appCompatImageButton.setContentDescription(str);
            toolbar.mCollapseButtonView.setTooltipText(str);
            toolbar.mCollapseDescription = str;
        }
        getActivity().getOnBackInvokedDispatcher().registerSystemOnBackInvokedCallback(new OnBackInvokedCallback() { // from class: com.android.settings.applications.manageapplications.ManageApplications.3
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                MenuItem findItem;
                ManageApplications manageApplications = ManageApplications.this;
                if (!manageApplications.mSearchExpanded) {
                    FragmentActivity activity = manageApplications.getActivity();
                    if (activity != null) {
                        activity.finish();
                        return;
                    }
                    return;
                }
                Menu menu = manageApplications.mOptionsMenu;
                if (menu == null || (findItem = menu.findItem(R.id.search_app_list_menu)) == null) {
                    return;
                }
                findItem.collapseActionView();
                ManageApplications.this.mSearchExpanded = false;
            }
        });
        return this.mRootView;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyOptionsMenu() {
        this.mOptionsMenu = null;
        TextView textView = this.mEmptyViewText;
        if (textView != null) {
            textView.setText(R.string.no_applications);
        }
        ApplicationsAdapter applicationsAdapter = this.mApplications;
        if (applicationsAdapter != null) {
            applicationsAdapter.setUnvisiblePrefs(true);
        }
        if (this.mSearchView != null) {
            this.mSearchExpanded = false;
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        MenuItem findItem;
        super.onDestroyView();
        Menu menu = this.mOptionsMenu;
        if (menu != null && (findItem = menu.findItem(R.id.search_app_list_menu)) != null) {
            findItem.collapseActionView();
        }
        ApplicationsAdapter applicationsAdapter = this.mApplications;
        if (applicationsAdapter != null) {
            applicationsAdapter.mSession.onDestroy();
            AppStateBaseBridge appStateBaseBridge = applicationsAdapter.mExtraInfoBridge;
            if (appStateBaseBridge != null) {
                appStateBaseBridge.release();
            }
            AlertDialog alertDialog = applicationsAdapter.mSortByDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
        }
        this.mRootView = null;
        AppIconCacheManager.getInstance();
        AppIconCacheManager appIconCacheManager = AppIconCacheManager.sAppIconCacheManager;
        if (appIconCacheManager != null) {
            appIconCacheManager.mDrawableCache.evictAll();
            AppIconCacheManager.mCurCacheSize = 0;
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        this.mFilter = (AppFilterItem) this.mFilterAdapter.mFilterOptions.get(i);
        setCompositeFilter$1();
        this.mApplications.setFilter(this.mFilter);
        if (DEBUG) {
            Log.d("ManageApplications", "Selecting filter " + ((Object) getContext().getText(this.mFilter.mTitle)));
        }
        if (this.mListType == 1) {
            this.mRecyclerView.seslSetFastScrollerEnabled(isFastScrollEnabled$1());
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity.onKeyEventListener
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        Menu menu;
        if (i != 84 || (menu = this.mOptionsMenu) == null) {
            return false;
        }
        MenuItem findItem = menu.findItem(R.id.search_app_list_menu);
        if (findItem == null) {
            return true;
        }
        findItem.expandActionView();
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        TextView textView = this.mEmptyViewText;
        if (textView != null) {
            textView.setText(R.string.no_applications);
        }
        ApplicationsAdapter applicationsAdapter = this.mApplications;
        if (applicationsAdapter != null) {
            applicationsAdapter.setUnvisiblePrefs(true);
        }
        if (this.mSearchView != null) {
            this.mSearchExpanded = false;
        }
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        if (this.mListType == 14) {
            LoggingHelper.insertEventLogging(1911, 8131);
        } else {
            LoggingHelper.insertEventLogging(65, EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS);
        }
        this.mEmptyViewText.setText(R.string.sec_app_search_no_result);
        this.mApplications.setUnvisiblePrefs(false);
        this.mSearchExpanded = true;
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        menuItem.getItemId();
        int itemId = menuItem.getItemId();
        if (itemId == R.id.sort_order) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.sec_sort_order);
            this.mTmpSortOrder = this.mSortOrder;
            View inflate = getActivity().getLayoutInflater().inflate(R.layout.sec_apps_sort_by_dialog, (ViewGroup) null);
            ((RadioButton) inflate.findViewById(R.id.order_name)).setText(R.string.sec_sort_by_name);
            ((RadioButton) inflate.findViewById(R.id.order_size)).setText(R.string.sec_sort_by_size);
            ((RadioButton) inflate.findViewById(R.id.order_last_used)).setText(R.string.sec_sort_by_last_used);
            ((RadioButton) inflate.findViewById(R.id.order_last_updated)).setText(R.string.sec_sort_by_last_updated);
            RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.group_order);
            int i = this.mTmpSortOrder;
            if (i == 1) {
                ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
            } else if (i == 2) {
                ((RadioButton) radioGroup.getChildAt(2)).setChecked(true);
            } else if (i != 3) {
                ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) radioGroup.getChildAt(3)).setChecked(true);
            }
            radioGroup.setOnCheckedChangeListener(new AnonymousClass4(0, inflate, this));
            if (inflate.getParent() != null) {
                ((ViewGroup) inflate.getParent()).removeView(inflate);
            }
            builder.setView(inflate);
            builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() { // from class: com.android.settings.applications.manageapplications.ManageApplications.5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    ManageApplications manageApplications = ManageApplications.this;
                    manageApplications.mSortOrder = manageApplications.mTmpSortOrder;
                    manageApplications.mRecyclerView.seslSetFastScrollerEnabled(manageApplications.isFastScrollEnabled$1());
                    ManageApplications manageApplications2 = ManageApplications.this;
                    ApplicationsAdapter applicationsAdapter = manageApplications2.mApplications;
                    if (applicationsAdapter != null) {
                        applicationsAdapter.rebuild(manageApplications2.mSortOrder, false);
                    }
                    dialogInterface.dismiss();
                }
            });
            AlertDialog create = builder.create();
            create.semSetAnchor(getActivity().findViewById(R.id.action_bar), 1);
            create.show();
        } else if (itemId == R.id.show_system || itemId == R.id.hide_system) {
            if (menuItem.getItemId() == R.id.show_system) {
                SALogging.insertSALog(Integer.toString(36021), "NSTE0018");
            }
            if (this.mRecyclerView != null && getContext() != null) {
                this.mRecyclerView.announceForAccessibility(getContext().getString(itemId == R.id.show_system ? R.string.sec_menu_show_system_feedback : R.string.sec_menu_hide_system_feedback));
            }
            this.mShowSystem = !this.mShowSystem;
            this.mApplications.rebuild();
        } else {
            if (itemId == R.id.reset_app_preferences) {
                LoggingHelper.insertEventLogging(65, 3832);
                boolean hasBaseUserRestriction = RestrictedLockUtilsInternal.hasBaseUserRestriction(getActivity(), UserHandle.myUserId(), "no_control_apps");
                RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(getActivity(), UserHandle.myUserId(), "no_control_apps");
                if (checkIfRestrictionEnforced == null || hasBaseUserRestriction) {
                    this.mResetAppsHelper.buildResetDialog();
                } else {
                    RestrictedLockUtils.sendShowAdminSupportDetailsIntent(getActivity(), checkIfRestrictionEnforced);
                }
                return true;
            }
            if (itemId == R.id.advanced) {
                if (this.mListType == 1) {
                    SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
                    String name = ConfigureNotificationSettings.class.getName();
                    SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                    launchRequest.mDestinationName = name;
                    subSettingLauncher.setTitleRes(R.string.configure_notification_settings, null);
                    launchRequest.mSourceMetricsCategory = getMetricsCategory();
                    subSettingLauncher.setResultListener(this, 2);
                    subSettingLauncher.launch();
                } else {
                    startActivityForResult(new Intent("android.settings.MANAGE_DEFAULT_APPS_SETTINGS").setPackage(getContext().getPackageManager().getPermissionControllerPackageName()), 2);
                }
                return true;
            }
            if (itemId != R.id.delete_all_app_clones) {
                if (itemId == R.id.manage_memory) {
                    SubSettingLauncher subSettingLauncher2 = new SubSettingLauncher(getContext());
                    String name2 = ProcessStatsSummary.class.getName();
                    SubSettingLauncher.LaunchRequest launchRequest2 = subSettingLauncher2.mLaunchRequest;
                    launchRequest2.mDestinationName = name2;
                    subSettingLauncher2.setTitleRes(R.string.memory_settings_title, null);
                    launchRequest2.mSourceMetricsCategory = getMetricsCategory();
                    subSettingLauncher2.setResultListener(this, 0);
                    subSettingLauncher2.launch();
                    return true;
                }
                if (itemId == R.id.manage_perms) {
                    try {
                        startActivity(Rune.isChinaModel() ? new Intent("com.samsung.intent.action.ACTION_MANAGE_PERMISSIONS_CHN") : new Intent("android.intent.action.MANAGE_PERMISSIONS"));
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                if (itemId != R.id.special_access) {
                    if (itemId != R.id.perms_usage) {
                        return false;
                    }
                    Intent intent = new Intent("android.intent.action.MAIN");
                    intent.setClassName("com.samsung.deviceagent", "com.samsung.deviceagent.MainActivity");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e2) {
                        e2.printStackTrace();
                    }
                    return true;
                }
                SubSettingLauncher subSettingLauncher3 = new SubSettingLauncher(getContext());
                String name3 = SpecialAccessSettings.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest3 = subSettingLauncher3.mLaunchRequest;
                launchRequest3.mDestinationName = name3;
                subSettingLauncher3.setTitleRes(R.string.special_access, null);
                launchRequest3.mSourceMetricsCategory = getMetricsCategory();
                subSettingLauncher3.setResultListener(this, 0);
                subSettingLauncher3.launch();
                return true;
            }
            int cloneUserId = com.android.settings.Utils.getCloneUserId(getContext());
            if (cloneUserId == -1) {
                return false;
            }
            IUserManager asInterface = IUserManager.Stub.asInterface(ServiceManager.getService("user"));
            CloneBackend cloneBackend = CloneBackend.getInstance(getContext());
            try {
                if (asInterface.removeUser(cloneUserId)) {
                    cloneBackend.mCloneUserId = -1;
                    this.mApplications.rebuild();
                } else if (DEBUG) {
                    Log.e("ManageApplications", "Failed to remove cloned user");
                }
            } catch (RemoteException e3) {
                Log.e("ManageApplications", "Failed to remove cloned apps", e3);
                Toast.makeText(getContext(), getContext().getString(R.string.delete_all_app_clones_failure), 1).show();
            }
        }
        updateOptionsMenu$1();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        ApplicationsAdapter applicationsAdapter = this.mApplications;
        if (applicationsAdapter == null || !applicationsAdapter.mResumed) {
            return;
        }
        applicationsAdapter.mResumed = false;
        applicationsAdapter.mSession.onPause();
        AppStateBaseBridge appStateBaseBridge = applicationsAdapter.mExtraInfoBridge;
        if (appStateBaseBridge != null) {
            appStateBaseBridge.pause();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        int i = this.mListType;
        if (i != 5 && i == 6) {
            LoggingHelper.insertFlowLogging(7717);
        }
        updateOptionsMenu$1();
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final boolean onQueryTextChange(String str) {
        this.mApplications.filterSearch(str);
        AppUtils.mSearchKeyword = str;
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        ApplicationsAdapter applicationsAdapter = this.mApplications;
        if (applicationsAdapter != null) {
            applicationsAdapter.resume(this.mSortOrder);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mResetAppsHelper.mResetDialog != null) {
            bundle.putBoolean("resetDialog", true);
        }
        bundle.putInt("sortOrder", this.mSortOrder);
        bundle.putInt("filterType", this.mFilter.mFilterType);
        bundle.putBoolean("showSystem", this.mShowSystem);
        if (this.mSearchView != null) {
            bundle.putBoolean(EXTRA_EXPAND_SEARCH_VIEW, !r0.mIconified);
            SearchView searchView = this.mSearchView;
            if (!searchView.mIconified) {
                bundle.putString("search_view_text", ((EditText) ((LinearLayout) searchView.findViewById(R.id.search_plate)).findViewById(R.id.search_src_text)).getText().toString());
            }
        }
        ApplicationsAdapter applicationsAdapter = this.mApplications;
        if (applicationsAdapter != null) {
            bundle.putBoolean("hasEntries", applicationsAdapter.mHasReceivedLoadEntries);
            bundle.putBoolean("hasBridge", this.mApplications.mHasReceivedBridgeCallback);
            bundle.putInt("state_last_scroll_index", ((LinearLayoutManager) this.mApplications.mManageApplications.mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition());
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mApplications != null) {
            if (this.mListType == 1) {
                getActivity().setTitle(R.string.app_notifications_title);
            }
            this.mApplications.updateLoading();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        AlertDialog alertDialog;
        super.onStop();
        ResetAppsHelper resetAppsHelper = this.mResetAppsHelper;
        if (resetAppsHelper == null || (alertDialog = resetAppsHelper.mResetDialog) == null) {
            return;
        }
        alertDialog.dismiss();
        resetAppsHelper.mResetDialog = null;
    }

    public final void setCompositeFilter$1() {
        ApplicationsState.AppFilter compositeFilter = getCompositeFilter(this.mListType, this.mStorageType, this.mVolumeUuid);
        if (compositeFilter == null) {
            compositeFilter = this.mFilter.mFilter;
        }
        boolean z = this.mIsWorkOnly;
        if (z) {
            compositeFilter = new ApplicationsState.AnonymousClass30(compositeFilter, ApplicationsState.FILTER_WORK);
        }
        boolean z2 = this.mIsPrivateProfileOnly;
        if (z2) {
            compositeFilter = new ApplicationsState.AnonymousClass30(compositeFilter, ApplicationsState.FILTER_PRIVATE_PROFILE);
        }
        if (this.mIsPersonalOnly || (!z && !z2)) {
            compositeFilter = new ApplicationsState.AnonymousClass30(compositeFilter, ApplicationsState.FILTER_PERSONAL);
        }
        ApplicationsAdapter applicationsAdapter = this.mApplications;
        applicationsAdapter.mCompositeFilter = compositeFilter;
        applicationsAdapter.rebuild();
    }

    public final void startAppInfoFragment(Class cls, int i) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.semForceHideSoftInput();
        }
        AppInfoBase.startAppInfoFragment(cls, getString(i), this.mCurrentPkgName, this.mCurrentUid, this, 1, getMetricsCategory());
    }

    public final void startApplicationDetailsActivity(ApplicationsState.AppEntry appEntry) {
        Intent resolveIntent;
        int i = this.mListType;
        int i2 = 0;
        if (i == 1) {
            startAppInfoFragment(AppNotificationSettings.class, R.string.app_notifications_title);
            HashMap hashMap = new HashMap();
            hashMap.put(ImIntent.Extras.EXTRA_FROM, "more");
            hashMap.put("app", appEntry.info.packageName);
            SALogging.insertSALog(Integer.toString(36021), "NSTE0034", hashMap, 0);
            return;
        }
        AppStateAppOpsBridge.PermissionState permissionState = null;
        AppStateAppOpsBridge.PermissionState permissionInfo = null;
        AppStateAppOpsBridge.PermissionState createPermissionState = null;
        AppStateAlarmsAndRemindersBridge.AlarmsAndRemindersState createPermissionState2 = null;
        AppStateAppOpsBridge.PermissionState permissionInfo2 = null;
        AppStateChangeWifiStateBridge.WifiSettingsState wifiSettingsState = null;
        AppStateAppOpsBridge.PermissionState permissionState2 = null;
        if (i == 101) {
            if (!this.mIsWorkOnly || this.mIsSecureOrSeparationUserId) {
                resolveIntent = AppStoreUtil.resolveIntent(getContext(), new Intent("com.sec.android.intent.action.SEC_APPLICATION_SETTINGS").setPackage(appEntry.info.packageName));
            } else {
                Context context = getContext();
                Intent intent = new Intent("com.sec.android.intent.action.SEC_APPLICATION_SETTINGS").setPackage(appEntry.info.packageName);
                ResolveInfo resolveActivityAsUser = context.getPackageManager().resolveActivityAsUser(intent, 0, this.mWorkUserId);
                if (resolveActivityAsUser != null) {
                    Intent intent2 = new Intent(intent.getAction());
                    ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
                    resolveIntent = intent2.setClassName(activityInfo.packageName, activityInfo.name);
                } else {
                    resolveIntent = null;
                }
            }
            if (resolveIntent == null) {
                StringBuilder sb = new StringBuilder("no intent for App Settings - Pkg : ");
                sb.append(appEntry.info.packageName);
                sb.append(", ");
                sb.append(this.mIsWorkOnly);
                sb.append(", ");
                sb.append(this.mWorkUserId);
                sb.append(", ");
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mIsSecureOrSeparationUserId, "ManageApplications");
                return;
            }
            resolveIntent.setAction(null);
            resolveIntent.putExtra("from_settings", true);
            resolveIntent.putExtra("StartEdit", true);
            LoggingHelper.insertEventLogging(getMetricsCategory(), 60111, appEntry.info.packageName);
            if (this.mIsWorkOnly) {
                getContext().startActivityAsUser(resolveIntent, UserHandle.semOf(this.mWorkUserId));
                return;
            } else {
                getContext().startActivity(resolveIntent);
                return;
            }
        }
        if (i == 104) {
            synchronized (appEntry) {
                if (this.mApplications != null) {
                    try {
                        AppStateManageFullScreenIntentsBridge appStateManageFullScreenIntentsBridge = new AppStateManageFullScreenIntentsBridge(getActivity(), this.mApplicationsState, null);
                        ApplicationInfo applicationInfo = appEntry.info;
                        permissionState = appStateManageFullScreenIntentsBridge.getPermissionInfo(applicationInfo.uid, applicationInfo.packageName);
                    } catch (Exception e) {
                        Log.e("ManageApplications", "Click disabled app: " + e.getMessage());
                        return;
                    }
                }
                if (permissionState != null && permissionState.permissionDeclared && !"com.samsung.knox.securefolder".equals(appEntry.info.packageName)) {
                    FullScreenIntentsDetails fullScreenIntentsDetails = new FullScreenIntentsDetails();
                    FragmentActivity activity = getActivity();
                    int i3 = appEntry.info.uid;
                    boolean isPermissible = true ^ permissionState.isPermissible();
                    AppOpsManager appOpsManager = (AppOpsManager) activity.getSystemService("appops");
                    fullScreenIntentsDetails.mAppOpsManager = appOpsManager;
                    if (!isPermissible) {
                        i2 = 2;
                    }
                    appOpsManager.setUidMode(133, i3, i2);
                    ApplicationsAdapter applicationsAdapter = this.mApplications;
                    if (applicationsAdapter != null && this.mClickHolder != null) {
                        AppStateBaseBridge appStateBaseBridge = applicationsAdapter.mExtraInfoBridge;
                        ApplicationInfo applicationInfo2 = appEntry.info;
                        appStateBaseBridge.forceUpdate(applicationInfo2.uid, applicationInfo2.packageName);
                        insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, permissionState.isPermissible());
                    }
                    return;
                }
                Log.i("ManageApplications", "This app is in a secure folder or doesn't need full screen intents permission");
                return;
            }
        }
        if (i == 105) {
            if (appEntry == null || appEntry.info == null) {
                return;
            }
            synchronized (appEntry) {
                if (this.mApplications != null) {
                    try {
                        AppStateMediaRoutingAppsBridge appStateMediaRoutingAppsBridge = new AppStateMediaRoutingAppsBridge(getActivity(), this.mApplicationsState, null);
                        ApplicationInfo applicationInfo3 = appEntry.info;
                        String str = applicationInfo3.packageName;
                        int i4 = applicationInfo3.uid;
                        AppStateAppOpsBridge.PermissionState permissionInfo3 = appStateMediaRoutingAppsBridge.getPermissionInfo(i4, str);
                        permissionInfo3.appOpMode = appStateMediaRoutingAppsBridge.mAppOpsManager.unsafeCheckOpNoThrow("android:media_routing_control", i4, str);
                        permissionState2 = permissionInfo3;
                    } catch (Exception unused) {
                        Log.d("ManageApplications", "Click disabled app");
                        return;
                    }
                }
                if (permissionState2 != null && permissionState2.permissionDeclared) {
                    MediaRoutingAppsDetails mediaRoutingAppsDetails = new MediaRoutingAppsDetails();
                    FragmentActivity activity2 = getActivity();
                    int i5 = appEntry.info.uid;
                    boolean isPermissible2 = true ^ permissionState2.isPermissible();
                    AppOpsManager appOpsManager2 = (AppOpsManager) activity2.getSystemService(AppOpsManager.class);
                    mediaRoutingAppsDetails.mAppOpsManager = appOpsManager2;
                    if (!isPermissible2) {
                        i2 = 2;
                    }
                    appOpsManager2.setUidMode(139, i5, i2);
                    ApplicationsAdapter applicationsAdapter2 = this.mApplications;
                    if (applicationsAdapter2 != null && this.mClickHolder != null) {
                        AppStateBaseBridge appStateBaseBridge2 = applicationsAdapter2.mExtraInfoBridge;
                        ApplicationInfo applicationInfo4 = appEntry.info;
                        appStateBaseBridge2.forceUpdate(applicationInfo4.uid, applicationInfo4.packageName);
                        insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, permissionState2.isPermissible());
                    }
                    return;
                }
                return;
            }
        }
        switch (i) {
            case 3:
                startAppInfoFragment(AppStorageSettings.class, R.string.storage_settings);
                return;
            case 4:
                toggleSwitchUsageAccess(appEntry);
                return;
            case 5:
                toggleSwitchHighPower(appEntry);
                return;
            case 6:
                toggleSwitchOverlay(appEntry);
                return;
            case 7:
                toggleSwitchWriteSettings(appEntry);
                return;
            case 8:
                toggleSwitchManageSources(appEntry);
                return;
            case 9:
                startAppInfoFragment(AppStorageSettings.class, R.string.game_storage_settings);
                return;
            case 10:
                if (appEntry == null || appEntry.info == null) {
                    return;
                }
                synchronized (appEntry) {
                    ApplicationsAdapter applicationsAdapter3 = this.mApplications;
                    if (applicationsAdapter3 != null) {
                        try {
                            AppStateChangeWifiStateBridge appStateChangeWifiStateBridge = (AppStateChangeWifiStateBridge) applicationsAdapter3.mExtraInfoBridge;
                            ApplicationInfo applicationInfo5 = appEntry.info;
                            wifiSettingsState = new AppStateChangeWifiStateBridge.WifiSettingsState(new AppStateChangeWifiStateBridge.WifiSettingsState(appStateChangeWifiStateBridge.getPermissionInfo(applicationInfo5.uid, applicationInfo5.packageName)));
                        } catch (Exception unused2) {
                            Log.d("ManageApplications", "Click disabled app");
                            return;
                        }
                    }
                    if (wifiSettingsState != null && wifiSettingsState.permissionDeclared) {
                        ChangeWifiStateDetails changeWifiStateDetails = new ChangeWifiStateDetails();
                        FragmentActivity activity3 = getActivity();
                        ApplicationInfo applicationInfo6 = appEntry.info;
                        String str2 = applicationInfo6.packageName;
                        int i6 = applicationInfo6.uid;
                        boolean isPermissible3 = wifiSettingsState.isPermissible();
                        changeWifiStateDetails.logSpecialPermissionChange(true ^ (isPermissible3 ? 1 : 0), str2);
                        AppOpsManager appOpsManager3 = (AppOpsManager) activity3.getSystemService("appops");
                        changeWifiStateDetails.mAppOpsManager = appOpsManager3;
                        appOpsManager3.setMode(71, i6, str2, isPermissible3 ? 1 : 0);
                        ApplicationsAdapter applicationsAdapter4 = this.mApplications;
                        if (applicationsAdapter4 != null && this.mClickHolder != null) {
                            AppStateBaseBridge appStateBaseBridge3 = applicationsAdapter4.mExtraInfoBridge;
                            ApplicationInfo applicationInfo7 = appEntry.info;
                            appStateBaseBridge3.forceUpdate(applicationInfo7.uid, applicationInfo7.packageName);
                            insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, wifiSettingsState.isPermissible());
                        }
                        return;
                    }
                    return;
                }
            case 11:
                if (appEntry == null || appEntry.info == null) {
                    return;
                }
                synchronized (appEntry) {
                    if (this.mApplications != null) {
                        try {
                            AppStateManageExternalStorageBridge appStateManageExternalStorageBridge = new AppStateManageExternalStorageBridge(getActivity(), this.mApplicationsState, null);
                            ApplicationInfo applicationInfo8 = appEntry.info;
                            permissionInfo2 = appStateManageExternalStorageBridge.getPermissionInfo(applicationInfo8.uid, applicationInfo8.packageName);
                        } catch (Exception unused3) {
                            Log.d("ManageApplications", "Click disabled app");
                            return;
                        }
                    }
                    if (permissionInfo2 != null && permissionInfo2.permissionDeclared) {
                        ManageExternalStorageDetails manageExternalStorageDetails = new ManageExternalStorageDetails();
                        FragmentActivity activity4 = getActivity();
                        ApplicationInfo applicationInfo9 = appEntry.info;
                        String str3 = applicationInfo9.packageName;
                        int i7 = applicationInfo9.uid;
                        boolean isPermissible4 = true ^ permissionInfo2.isPermissible();
                        manageExternalStorageDetails.mAppOpsManager = (AppOpsManager) activity4.getSystemService("appops");
                        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                        if (featureFactoryImpl == null) {
                            throw new UnsupportedOperationException("No feature factory configured");
                        }
                        SettingsMetricsFeatureProvider metricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
                        manageExternalStorageDetails.mMetricsFeatureProvider = metricsFeatureProvider;
                        int i8 = isPermissible4 ? 1730 : 1731;
                        FragmentActivity activity5 = manageExternalStorageDetails.getActivity();
                        metricsFeatureProvider.getClass();
                        metricsFeatureProvider.action(MetricsFeatureProvider.getAttribution(activity5), i8, 1822, 0, str3);
                        AppOpsManager appOpsManager4 = manageExternalStorageDetails.mAppOpsManager;
                        if (!isPermissible4) {
                            i2 = 2;
                        }
                        appOpsManager4.setUidMode(92, i7, i2);
                        ApplicationsAdapter applicationsAdapter5 = this.mApplications;
                        if (applicationsAdapter5 != null && this.mClickHolder != null) {
                            AppStateBaseBridge appStateBaseBridge4 = applicationsAdapter5.mExtraInfoBridge;
                            ApplicationInfo applicationInfo10 = appEntry.info;
                            appStateBaseBridge4.forceUpdate(applicationInfo10.uid, applicationInfo10.packageName);
                            insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, permissionInfo2.isPermissible());
                        }
                        return;
                    }
                    return;
                }
            case 12:
                if (appEntry == null || appEntry.info == null) {
                    return;
                }
                synchronized (appEntry) {
                    if (this.mApplications != null) {
                        try {
                            AppStateAlarmsAndRemindersBridge appStateAlarmsAndRemindersBridge = new AppStateAlarmsAndRemindersBridge(getActivity(), this.mApplicationsState, null);
                            ApplicationInfo applicationInfo11 = appEntry.info;
                            createPermissionState2 = appStateAlarmsAndRemindersBridge.createPermissionState(applicationInfo11.uid, applicationInfo11.packageName);
                        } catch (Exception unused4) {
                            Log.d("ManageApplications", "Click disabled app");
                            return;
                        }
                    }
                    if (createPermissionState2 != null && createPermissionState2.shouldBeVisible()) {
                        AlarmsAndRemindersDetails alarmsAndRemindersDetails = new AlarmsAndRemindersDetails();
                        FragmentActivity activity6 = getActivity();
                        boolean isAllowed = true ^ createPermissionState2.isAllowed();
                        int i9 = appEntry.info.uid;
                        AppOpsManager appOpsManager5 = (AppOpsManager) activity6.getSystemService(AppOpsManager.class);
                        alarmsAndRemindersDetails.mAppOpsManager = appOpsManager5;
                        if (!isAllowed) {
                            i2 = 2;
                        }
                        appOpsManager5.setUidMode("android:schedule_exact_alarm", i9, i2);
                        ApplicationsAdapter applicationsAdapter6 = this.mApplications;
                        if (applicationsAdapter6 != null && this.mClickHolder != null) {
                            AppStateBaseBridge appStateBaseBridge5 = applicationsAdapter6.mExtraInfoBridge;
                            ApplicationInfo applicationInfo12 = appEntry.info;
                            appStateBaseBridge5.forceUpdate(applicationInfo12.uid, applicationInfo12.packageName);
                            insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, createPermissionState2.isAllowed());
                        }
                        return;
                    }
                    return;
                }
            case 13:
                if (appEntry == null || appEntry.info == null) {
                    return;
                }
                synchronized (appEntry) {
                    if (this.mApplications != null) {
                        try {
                            AppStateMediaManagementAppsBridge appStateMediaManagementAppsBridge = new AppStateMediaManagementAppsBridge(getActivity(), this.mApplicationsState, null);
                            ApplicationInfo applicationInfo13 = appEntry.info;
                            createPermissionState = appStateMediaManagementAppsBridge.createPermissionState(applicationInfo13.uid, applicationInfo13.packageName);
                        } catch (Exception unused5) {
                            Log.d("ManageApplications", "Click disabled app");
                            return;
                        }
                    }
                    if (createPermissionState != null && createPermissionState.permissionDeclared) {
                        MediaManagementAppsDetails mediaManagementAppsDetails = new MediaManagementAppsDetails();
                        FragmentActivity activity7 = getActivity();
                        int i10 = appEntry.info.uid;
                        boolean isPermissible5 = true ^ createPermissionState.isPermissible();
                        AppOpsManager appOpsManager6 = (AppOpsManager) activity7.getSystemService(AppOpsManager.class);
                        mediaManagementAppsDetails.mAppOpsManager = appOpsManager6;
                        if (!isPermissible5) {
                            i2 = 2;
                        }
                        appOpsManager6.setUidMode(110, i10, i2);
                        ApplicationsAdapter applicationsAdapter7 = this.mApplications;
                        if (applicationsAdapter7 != null && this.mClickHolder != null) {
                            AppStateBaseBridge appStateBaseBridge6 = applicationsAdapter7.mExtraInfoBridge;
                            ApplicationInfo applicationInfo14 = appEntry.info;
                            appStateBaseBridge6.forceUpdate(applicationInfo14.uid, applicationInfo14.packageName);
                            insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, createPermissionState.isPermissible());
                        }
                        return;
                    }
                    return;
                }
            case 14:
                LoggingHelper.insertEventLogging(1911, 8132, this.mCurrentPkgName);
                Intent intent3 = new Intent(getContext(), (Class<?>) AppLocalePickerActivity.class);
                intent3.setData(Uri.parse("package:" + this.mCurrentPkgName));
                getContext().startActivityAsUser(intent3, UserHandle.getUserHandleForUid(this.mCurrentUid));
                return;
            case 15:
                AdvancedPowerUsageDetail.startBatteryDetailPage(getActivity(), this, this.mCurrentPkgName, UserHandle.getUserHandleForUid(this.mCurrentUid));
                return;
            case 16:
                startAppInfoFragment(LongBackgroundTasksDetails.class, R.string.long_background_tasks_label);
                return;
            case 17:
                int userId = UserHandle.getUserId(this.mCurrentUid);
                UserInfo userInfo = this.mUserManager.getUserInfo(userId);
                if (userInfo == null || userInfo.isCloneProfile()) {
                    Context context2 = getContext();
                    String route = AppInfoSettingsProvider.INSTANCE.getRoute(userId, this.mCurrentPkgName);
                    SpaActivity.Companion companion = SpaActivity.Companion;
                    SpaActivity.Companion.startSpaActivity(context2, route);
                    return;
                }
                Context context3 = getContext();
                CloneAppInfoSettingsProvider cloneAppInfoSettingsProvider = CloneAppInfoSettingsProvider.INSTANCE;
                String packageName = this.mCurrentPkgName;
                Intrinsics.checkNotNullParameter(packageName, "packageName");
                String str4 = "CloneAppInfoSettingsProvider/" + packageName + "/" + userId;
                SpaActivity.Companion companion2 = SpaActivity.Companion;
                SpaActivity.Companion.startSpaActivity(context3, str4);
                return;
            case 18:
                startAppInfoFragment(ChangeNfcTagAppsStateDetails.class, R.string.change_nfc_tag_apps_title);
                return;
            case 19:
                if (appEntry == null || appEntry.info == null) {
                    return;
                }
                synchronized (appEntry) {
                    if (this.mApplications != null) {
                        try {
                            AppStateTurnScreenOnBridge appStateTurnScreenOnBridge = new AppStateTurnScreenOnBridge(getActivity(), this.mApplicationsState, null);
                            ApplicationInfo applicationInfo15 = appEntry.info;
                            permissionInfo = appStateTurnScreenOnBridge.getPermissionInfo(applicationInfo15.uid, applicationInfo15.packageName);
                        } catch (Exception unused6) {
                            Log.d("ManageApplications", "Click disabled app");
                            return;
                        }
                    }
                    if (permissionInfo != null && permissionInfo.permissionDeclared) {
                        TurnScreenOnDetails turnScreenOnDetails = new TurnScreenOnDetails();
                        FragmentActivity activity8 = getActivity();
                        int i11 = appEntry.info.uid;
                        boolean isPermissible6 = true ^ permissionInfo.isPermissible();
                        AppOpsManager appOpsManager7 = (AppOpsManager) activity8.getSystemService(AppOpsManager.class);
                        turnScreenOnDetails.mAppOpsManager = appOpsManager7;
                        if (!isPermissible6) {
                            i2 = 2;
                        }
                        appOpsManager7.setUidMode("android:turn_screen_on", i11, i2);
                        ApplicationsAdapter applicationsAdapter8 = this.mApplications;
                        if (applicationsAdapter8 != null && this.mClickHolder != null) {
                            AppStateBaseBridge appStateBaseBridge7 = applicationsAdapter8.mExtraInfoBridge;
                            ApplicationInfo applicationInfo16 = appEntry.info;
                            appStateBaseBridge7.forceUpdate(applicationInfo16.uid, applicationInfo16.packageName);
                            insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, permissionInfo.isPermissible());
                        }
                        return;
                    }
                    return;
                }
            default:
                startAppInfoFragment(AppInfoDashboardFragment.class, R.string.application_info_label);
                return;
        }
    }

    public final void toggleSwitchHighPower(ApplicationsState.AppEntry appEntry) {
        if (appEntry == null || appEntry.info == null) {
            return;
        }
        synchronized (appEntry) {
            try {
                if (!PowerAllowlistBackend.getInstance(getActivity()).isSysAllowlisted(appEntry.info.packageName) && !hasAllowListByMDM(getActivity(), appEntry.info.packageName)) {
                    HighPowerDetail highPowerDetail = new HighPowerDetail();
                    FragmentActivity activity = getActivity();
                    ApplicationInfo applicationInfo = appEntry.info;
                    highPowerDetail.setPowerAllowlisted(activity, applicationInfo.packageName, applicationInfo.uid);
                    ApplicationsAdapter applicationsAdapter = this.mApplications;
                    if (applicationsAdapter != null && this.mClickHolder != null) {
                        AppStateBaseBridge appStateBaseBridge = applicationsAdapter.mExtraInfoBridge;
                        ApplicationInfo applicationInfo2 = appEntry.info;
                        appStateBaseBridge.forceUpdate(applicationInfo2.uid, applicationInfo2.packageName);
                    }
                }
            } finally {
            }
        }
    }

    public final void toggleSwitchManageSources(ApplicationsState.AppEntry appEntry) {
        if (appEntry == null || appEntry.info == null) {
            return;
        }
        synchronized (appEntry) {
            try {
                AppStateInstallAppsBridge.InstallAppsState installAppsState = null;
                if (this.mApplications != null) {
                    try {
                        AppStateInstallAppsBridge appStateInstallAppsBridge = new AppStateInstallAppsBridge(getActivity(), this.mApplicationsState, null);
                        ApplicationInfo applicationInfo = appEntry.info;
                        installAppsState = appStateInstallAppsBridge.createInstallAppsStateFor(applicationInfo.uid, applicationInfo.packageName);
                        if (this.mApplications.mSummaryRes == R.string.disabled_by_admin) {
                            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(getContext(), this.mApplications.mEnforcedAdmin);
                            return;
                        }
                    } catch (Exception unused) {
                        Log.d("ManageApplications", "Click disabled app");
                        return;
                    }
                }
                if (installAppsState != null && installAppsState.isPotentialAppSource()) {
                    ExternalSourcesDetails externalSourcesDetails = new ExternalSourcesDetails();
                    FragmentActivity activity = getActivity();
                    ApplicationInfo applicationInfo2 = appEntry.info;
                    String str = applicationInfo2.packageName;
                    int i = applicationInfo2.uid;
                    boolean z = !installAppsState.canInstallApps();
                    AppOpsManager appOpsManager = (AppOpsManager) activity.getSystemService("appops");
                    externalSourcesDetails.mAppOpsManager = appOpsManager;
                    appOpsManager.setMode(66, i, str, z ? 0 : 2);
                    ApplicationsAdapter applicationsAdapter = this.mApplications;
                    if (applicationsAdapter != null && this.mClickHolder != null) {
                        AppStateBaseBridge appStateBaseBridge = applicationsAdapter.mExtraInfoBridge;
                        ApplicationInfo applicationInfo3 = appEntry.info;
                        appStateBaseBridge.forceUpdate(applicationInfo3.uid, applicationInfo3.packageName);
                        insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, installAppsState.canInstallApps());
                    }
                }
            } finally {
            }
        }
    }

    public final void toggleSwitchOverlay(ApplicationsState.AppEntry appEntry) {
        AppStateOverlayBridge.OverlayState overlayState;
        if (appEntry == null || appEntry.info == null) {
            return;
        }
        synchronized (appEntry) {
            if (this.mApplications != null) {
                try {
                    AppStateOverlayBridge appStateOverlayBridge = (AppStateOverlayBridge) this.mApplications.mExtraInfoBridge;
                    ApplicationInfo applicationInfo = appEntry.info;
                    overlayState = new AppStateOverlayBridge.OverlayState(new AppStateOverlayBridge.OverlayState(appStateOverlayBridge.getPermissionInfo(applicationInfo.uid, applicationInfo.packageName)));
                } catch (Exception unused) {
                    Log.d("ManageApplications", "Click disabled app");
                    return;
                }
            } else {
                overlayState = null;
            }
            if (overlayState != null && overlayState.permissionDeclared && overlayState.controlEnabled && !"com.samsung.knox.securefolder".equals(appEntry.info.packageName)) {
                DrawOverlayDetails drawOverlayDetails = new DrawOverlayDetails();
                FragmentActivity activity = getActivity();
                ApplicationInfo applicationInfo2 = appEntry.info;
                String str = applicationInfo2.packageName;
                int i = applicationInfo2.uid;
                boolean z = !overlayState.isPermissible();
                AppOpsManager appOpsManager = (AppOpsManager) activity.getSystemService("appops");
                drawOverlayDetails.mAppOpsManager = appOpsManager;
                appOpsManager.setMode(24, i, str, z ? 0 : 2);
                ApplicationsAdapter applicationsAdapter = this.mApplications;
                if (applicationsAdapter != null && this.mClickHolder != null) {
                    AppStateBaseBridge appStateBaseBridge = applicationsAdapter.mExtraInfoBridge;
                    ApplicationInfo applicationInfo3 = appEntry.info;
                    appStateBaseBridge.forceUpdate(applicationInfo3.uid, applicationInfo3.packageName);
                    insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, overlayState.isPermissible());
                }
            }
        }
    }

    public final void toggleSwitchUsageAccess(ApplicationsState.AppEntry appEntry) {
        AppStateUsageBridge.UsageState usageState;
        if (appEntry == null || appEntry.info == null) {
            return;
        }
        synchronized (appEntry) {
            ApplicationsAdapter applicationsAdapter = this.mApplications;
            if (applicationsAdapter != null) {
                try {
                    AppStateUsageBridge appStateUsageBridge = (AppStateUsageBridge) applicationsAdapter.mExtraInfoBridge;
                    ApplicationInfo applicationInfo = appEntry.info;
                    usageState = new AppStateUsageBridge.UsageState(new AppStateUsageBridge.UsageState(appStateUsageBridge.getPermissionInfo(applicationInfo.uid, applicationInfo.packageName)));
                } catch (Exception unused) {
                    Log.d("ManageApplications", "Click disabled app");
                    return;
                }
            } else {
                usageState = null;
            }
            if (usageState != null && usageState.permissionDeclared) {
                UsageAccessDetails usageAccessDetails = new UsageAccessDetails();
                FragmentActivity activity = getActivity();
                ApplicationInfo applicationInfo2 = appEntry.info;
                usageAccessDetails.setHasAccess(activity, applicationInfo2.packageName, applicationInfo2.uid, usageState, !usageState.isPermissible());
                ApplicationsAdapter applicationsAdapter2 = this.mApplications;
                if (applicationsAdapter2 != null && this.mClickHolder != null) {
                    AppStateBaseBridge appStateBaseBridge = applicationsAdapter2.mExtraInfoBridge;
                    ApplicationInfo applicationInfo3 = appEntry.info;
                    appStateBaseBridge.forceUpdate(applicationInfo3.uid, applicationInfo3.packageName);
                    insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, usageState.isPermissible());
                }
            }
        }
    }

    public final void toggleSwitchWriteSettings(ApplicationsState.AppEntry appEntry) {
        AppStateWriteSettingsBridge.WriteSettingsState writeSettingsState;
        if (appEntry == null || appEntry.info == null) {
            return;
        }
        synchronized (appEntry) {
            ApplicationsAdapter applicationsAdapter = this.mApplications;
            if (applicationsAdapter != null) {
                try {
                    AppStateWriteSettingsBridge appStateWriteSettingsBridge = (AppStateWriteSettingsBridge) applicationsAdapter.mExtraInfoBridge;
                    ApplicationInfo applicationInfo = appEntry.info;
                    writeSettingsState = new AppStateWriteSettingsBridge.WriteSettingsState(appStateWriteSettingsBridge.getWriteSettingsInfo(applicationInfo.uid, applicationInfo.packageName));
                } catch (Exception unused) {
                    Log.d("ManageApplications", "Click disabled app");
                    return;
                }
            } else {
                writeSettingsState = null;
            }
            if (writeSettingsState != null && writeSettingsState.permissionDeclared) {
                WriteSettingsDetails writeSettingsDetails = new WriteSettingsDetails();
                FragmentActivity activity = getActivity();
                ApplicationInfo applicationInfo2 = appEntry.info;
                String str = applicationInfo2.packageName;
                int i = applicationInfo2.uid;
                boolean z = !writeSettingsState.isPermissible();
                AppOpsManager appOpsManager = (AppOpsManager) activity.getSystemService("appops");
                writeSettingsDetails.mAppOpsManager = appOpsManager;
                appOpsManager.setMode(23, i, str, z ? 0 : 2);
                ApplicationsAdapter applicationsAdapter2 = this.mApplications;
                if (applicationsAdapter2 != null && this.mClickHolder != null) {
                    AppStateBaseBridge appStateBaseBridge = applicationsAdapter2.mExtraInfoBridge;
                    ApplicationInfo applicationInfo3 = appEntry.info;
                    appStateBaseBridge.forceUpdate(applicationInfo3.uid, applicationInfo3.packageName);
                    insertToggleEventLogging(getMetricsCategory(), getEventCategory(), appEntry.info.packageName, writeSettingsState.isPermissible());
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateOptionsMenu$1() {
        /*
            Method dump skipped, instructions count: 377
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.applications.manageapplications.ManageApplications.updateOptionsMenu$1():void");
    }

    public static String getTopLevelPreferenceKey(Intent intent, Bundle bundle) {
        String string = bundle != null ? bundle.getString("classname") : null;
        if (string == null) {
            string = intent.getComponent().getClassName();
        }
        String string2 = bundle != null ? bundle.getString("fromFragment") : null;
        boolean z = string2 != null && string2.equals("security_settings");
        return (string.equals(Settings.NotificationAppListActivity.class.getName()) || string.equals(Settings.NotificationReviewPermissionsActivity.class.getName())) ? "top_level_notifications" : string.equals(AppLocaleDetails.class.getName()) ? "top_level_general" : string.equals(Settings.ManageExternalSourcesActivity.class.getName()) ? z ? "top_level_security_and_privacy" : "top_level_apps" : (string.equals(Settings.ManageUnknownSourceAppsActivity.class.getName()) && Rune.FEATURE_MANAGE_UNKNOWN_APPS && z) ? "top_level_security_and_privacy" : "top_level_apps";
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ApplicationsAdapter extends RecyclerView.Adapter implements AppStateBaseBridge.Callback, SectionIndexer, ApplicationsState.Callbacks {
        public static final SectionInfo[] EMPTY_SECTIONS = new SectionInfo[0];
        public AppFilterItem mAppFilter;
        public final PowerAllowlistBackend mBackend;
        public ApplicationsState.AppFilter mCompositeFilter;
        public final FragmentActivity mContext;
        public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
        public ArrayList mEntries;
        public ArrayList mEntriesForSearch;
        public final AppStateBaseBridge mExtraInfoBridge;
        public boolean mHasEnabledEntry;
        public boolean mHasReceivedBridgeCallback;
        public boolean mHasReceivedLoadEntries;
        public boolean mHeaderButtonVisible;
        public AlphabeticIndex.ImmutableIndex mIndex;
        public int mLastIndex;
        public SettingsPreferenceFragmentLinkData mLinkedDataStorage;
        public final LoadingViewController mLoadingViewController;
        public final ManageApplications mManageApplications;
        OnScrollListener mOnScrollListener;
        public ArrayList mOriginalEntries;
        public int[] mPositionToSectionIndex;
        public boolean mPrefVisible;
        public RecyclerView mRecyclerView;
        public SecRelativeLinkView mRelativeLinkView;
        public boolean mResumed;
        public SearchFilter mSearchFilter;
        public final ApplicationsState.Session mSession;
        public AlertDialog mSortByDialog;
        public final ApplicationsState mState;
        public final boolean mUseFooter;
        public final boolean mUseHeader;
        public final boolean mUsePref;
        public int mLastSortMode = -1;
        public int mWhichSize = 0;
        public int mHighlightTargetPosition = -2;
        public SectionInfo[] mSections = EMPTY_SECTIONS;
        public boolean mIsEnable = true;
        public int mSummaryRes = -1;
        public int mSamsungMediaProviderUid = -1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class OnScrollListener extends RecyclerView.OnScrollListener {
            public ApplicationsAdapter mAdapter;
            public String mClassName;
            public boolean mDelayNotifyDataChange;
            public InputMethodManager mInputMethodManager;
            public InteractionJankMonitor mMonitor;
            public int mScrollState;

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public final void onScrollStateChanged(int i, RecyclerView recyclerView) {
                this.mScrollState = i;
                if (i == 0 && this.mDelayNotifyDataChange) {
                    this.mDelayNotifyDataChange = false;
                    this.mAdapter.notifyDataSetChanged();
                    return;
                }
                if (i != 1) {
                    if (i == 0) {
                        this.mMonitor.end(28);
                    }
                } else {
                    InputMethodManager inputMethodManager = this.mInputMethodManager;
                    if (inputMethodManager != null && inputMethodManager.isActive()) {
                        this.mInputMethodManager.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
                    }
                    this.mMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(28, recyclerView).setTag(this.mClassName));
                }
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class SearchFilter extends Filter {
            public SearchFilter() {
            }

            /* JADX WARN: Code restructure failed: missing block: B:33:0x00b1, code lost:
            
                if (r0.isClosed() == false) goto L34;
             */
            /* JADX WARN: Code restructure failed: missing block: B:34:0x00b3, code lost:
            
                r0.close();
             */
            /* JADX WARN: Code restructure failed: missing block: B:87:0x00d6, code lost:
            
                if (r0.isClosed() == false) goto L34;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:17:0x0172  */
            /* JADX WARN: Removed duplicated region for block: B:32:0x00ad  */
            /* JADX WARN: Removed duplicated region for block: B:38:0x00e7  */
            /* JADX WARN: Removed duplicated region for block: B:6:0x0140  */
            /* JADX WARN: Type inference failed for: r0v5, types: [android.content.Context, androidx.fragment.app.FragmentActivity] */
            @Override // android.widget.Filter
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence r11) {
                /*
                    Method dump skipped, instructions count: 445
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.settings.applications.manageapplications.ManageApplications.ApplicationsAdapter.SearchFilter.performFiltering(java.lang.CharSequence):android.widget.Filter$FilterResults");
            }

            @Override // android.widget.Filter
            public final void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                ApplicationsAdapter applicationsAdapter = ApplicationsAdapter.this;
                applicationsAdapter.mEntries = (ArrayList) filterResults.values;
                applicationsAdapter.notifyDataSetChanged();
                ManageApplications manageApplications = ApplicationsAdapter.this.mManageApplications;
                if (manageApplications.mListType == 1) {
                    manageApplications.mFilterAdapter.notifyDataSetChanged();
                }
                int itemCount = ApplicationsAdapter.this.getItemCount();
                if (itemCount != 0) {
                    ApplicationsAdapter applicationsAdapter2 = ApplicationsAdapter.this;
                    if (!applicationsAdapter2.mUseFooter || itemCount != 1) {
                        applicationsAdapter2.mLoadingViewController.showContent(false);
                        return;
                    }
                }
                ApplicationsAdapter.this.mLoadingViewController.showEmpty();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0165  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0171 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:33:0x019c  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x01a5  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x019e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public ApplicationsAdapter(com.android.settingslib.applications.ApplicationsState r19, com.android.settings.applications.manageapplications.ManageApplications r20, com.android.settings.applications.manageapplications.AppFilterItem r21, android.os.Bundle r22) {
            /*
                Method dump skipped, instructions count: 427
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settings.applications.manageapplications.ManageApplications.ApplicationsAdapter.<init>(com.android.settingslib.applications.ApplicationsState, com.android.settings.applications.manageapplications.ManageApplications, com.android.settings.applications.manageapplications.AppFilterItem, android.os.Bundle):void");
        }

        public void filterSearch(String str) {
            if (this.mSearchFilter == null) {
                this.mSearchFilter = new SearchFilter();
            }
            if (this.mOriginalEntries == null) {
                Log.w("ManageApplications", "Apps haven't loaded completely yet, so nothing can be filtered");
            } else {
                this.mSearchFilter.filter(str);
            }
        }

        public final Comparator getComparatorByLastSortMode() {
            int i = this.mLastSortMode;
            if (i != 1) {
                return i == 2 ? ApplicationsState.LAST_USED_COMPARATOR : i == 3 ? ApplicationsState.LAST_UPDATED_COMPARATOR : i == R.id.sort_order_recent_notification ? AppStateNotificationBridge.RECENT_NOTIFICATION_COMPARATOR : i == R.id.sort_order_frequent_notification ? AppStateNotificationBridge.FREQUENCY_NOTIFICATION_COMPARATOR : ApplicationsState.ALPHA_COMPARATOR;
            }
            int i2 = this.mWhichSize;
            return i2 == 1 ? ApplicationsState.INTERNAL_SIZE_COMPARATOR : i2 == 2 ? ApplicationsState.EXTERNAL_SIZE_COMPARATOR : ApplicationsState.SIZE_COMPARATOR;
        }

        public final String getContentDescriptionWithSwitch(String str, boolean z) {
            String sb;
            StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
            m.append(this.mContext.getString(R.string.comma));
            m.append(" ");
            String sb2 = m.toString();
            if (z) {
                StringBuilder m2 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(sb2);
                m2.append(this.mContext.getString(R.string.switch_on_text));
                sb = m2.toString();
            } else {
                StringBuilder m3 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(sb2);
                m3.append(this.mContext.getString(R.string.switch_off_text));
                sb = m3.toString();
            }
            StringBuilder m4 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(sb);
            m4.append(this.mContext.getString(R.string.comma));
            m4.append(" ");
            m4.append(this.mContext.getString(R.string.sec_switch));
            return m4.toString();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            ArrayList arrayList = this.mEntries;
            if (arrayList == null) {
                return 0;
            }
            return (arrayList != null ? arrayList.size() : 0) + (this.mUseFooter ? 1 : 0);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final long getItemId(int i) {
            int i2 = this.mManageApplications.mListType;
            if (i == this.mEntries.size() || i == -1) {
                return -1L;
            }
            return ((ApplicationsState.AppEntry) this.mEntries.get(i)).id;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemViewType(int i) {
            int itemCount = getItemCount();
            byte b = itemCount + (-1) == i;
            boolean z = this.mUseHeader;
            if (z && i == 0) {
                return 4;
            }
            boolean z2 = this.mUsePref;
            if (z2 && i == 0) {
                return 9;
            }
            int i2 = (z || z2) ? 1 : 0;
            boolean z3 = this.mUseFooter;
            if (z3) {
                i2++;
            }
            int i3 = itemCount - i2;
            if (i3 == 1) {
                if ((z || z2) && i == 1) {
                    return 8;
                }
                if ((z || z2 || z3) && i == 0) {
                    return 8;
                }
            } else if (i3 == 0) {
                return 5;
            }
            if ((z || z2) && i == 1) {
                return 6;
            }
            if (!z && !z2 && i == 0) {
                return 6;
            }
            if (z3 && b == true) {
                return 5;
            }
            return (itemCount - (z3 ? 1 : 0)) - 1 == i ? 7 : 0;
        }

        @Override // android.widget.SectionIndexer
        public final int getPositionForSection(int i) {
            SectionInfo[] sectionInfoArr = this.mSections;
            if (i > sectionInfoArr.length - 1) {
                i = sectionInfoArr.length - 1;
            }
            return sectionInfoArr[i].position;
        }

        @Override // android.widget.SectionIndexer
        public final int getSectionForPosition(int i) {
            if (i > this.mPositionToSectionIndex.length - 1) {
                Log.d("ManageApplications", "pos=" + i);
                Preference$$ExternalSyntheticOutline0.m(new StringBuilder("section length="), this.mPositionToSectionIndex.length, "ManageApplications");
                i = Math.max(this.mPositionToSectionIndex.length + (-1), 0);
            }
            return this.mPositionToSectionIndex[i];
        }

        @Override // android.widget.SectionIndexer
        public final Object[] getSections() {
            return this.mSections;
        }

        public final void hightlightTargetApp(final ApplicationViewHolder applicationViewHolder, ApplicationsState.AppEntry appEntry) {
            ManageApplications manageApplications = this.mManageApplications;
            String str = manageApplications.mHighlightTargetPackageName;
            int i = manageApplications.mHighlightTargetPackageUid;
            String str2 = appEntry.info.packageName;
            if (this.mHighlightTargetPosition >= 0 && TextUtils.equals(str, str2) && UserHandle.getUserId(appEntry.info.uid) == i) {
                ManageApplications manageApplications2 = this.mManageApplications;
                final int i2 = manageApplications2.mUpdatedListCount + 1;
                manageApplications2.mUpdatedListCount = i2;
                applicationViewHolder.itemView.postDelayed(new Runnable() { // from class: com.android.settings.applications.manageapplications.ManageApplications$ApplicationsAdapter$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ManageApplications.ApplicationsAdapter applicationsAdapter = ManageApplications.ApplicationsAdapter.this;
                        int i3 = i2;
                        ApplicationViewHolder applicationViewHolder2 = applicationViewHolder;
                        if (applicationsAdapter.mHighlightTargetPosition == -1 || i3 != applicationsAdapter.mManageApplications.mUpdatedListCount) {
                            return;
                        }
                        if (applicationViewHolder2.itemView.getBackground() != null) {
                            applicationViewHolder2.itemView.getBackground().setHotspot(applicationViewHolder2.itemView.getWidth() / 2, applicationViewHolder2.itemView.getHeight() / 2);
                        }
                        applicationViewHolder2.itemView.setPressed(true);
                        applicationsAdapter.mHighlightTargetPosition = -1;
                        applicationsAdapter.mManageApplications.mHighlightTargetPackageName = null;
                    }
                }, 600L);
                applicationViewHolder.itemView.postDelayed(new ManageApplications$ApplicationsAdapter$$ExternalSyntheticLambda2(4, applicationViewHolder), 750L);
            }
        }

        public final void logAction(int i) {
            ((InstrumentedPreferenceFragment) this.mManageApplications).mMetricsFeatureProvider.action(this.mContext, i, new Pair[0]);
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public final void onAllSizesComputed() {
            if (this.mLastSortMode == 1) {
                rebuild();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onAttachedToRecyclerView(RecyclerView recyclerView) {
            String str = this.mManageApplications.getClass().getName() + "_" + this.mManageApplications.mListType;
            this.mRecyclerView = recyclerView;
            OnScrollListener onScrollListener = new OnScrollListener();
            onScrollListener.mScrollState = 0;
            onScrollListener.mAdapter = this;
            onScrollListener.mInputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
            onScrollListener.mMonitor = InteractionJankMonitor.getInstance();
            onScrollListener.mClassName = str;
            this.mOnScrollListener = onScrollListener;
            this.mRecyclerView.addOnScrollListener(onScrollListener);
        }

        /* JADX WARN: Code restructure failed: missing block: B:224:0x048a, code lost:
        
            if (r3.controlEnabled != false) goto L224;
         */
        /* JADX WARN: Code restructure failed: missing block: B:235:0x04c5, code lost:
        
            if (r3.isDefaultActiveApp(r10.uid, r10.packageName) == false) goto L224;
         */
        /* JADX WARN: Removed duplicated region for block: B:134:0x056b  */
        /* JADX WARN: Removed duplicated region for block: B:143:0x0593 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:153:0x05bc  */
        /* JADX WARN: Removed duplicated region for block: B:160:0x05ec  */
        /* JADX WARN: Removed duplicated region for block: B:161:0x05d0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r17, int r18) {
            /*
                Method dump skipped, instructions count: 1644
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settings.applications.manageapplications.ManageApplications.ApplicationsAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            if (this.mUseFooter && i == 5) {
                LinearLayout linearLayout = new LinearLayout(this.mContext);
                linearLayout.setOrientation(1);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                if (ManageApplications.m736$$Nest$smisSupportedRelativeLink(this.mManageApplications)) {
                    this.mLinkedDataStorage = new SettingsPreferenceFragmentLinkData();
                    Intent intent = new Intent("com.sec.android.app.myfiles.RUN_STORAGE_ANALYSIS");
                    intent.putExtra("package_name", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                    SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData = this.mLinkedDataStorage;
                    settingsPreferenceFragmentLinkData.flowId = "9013";
                    settingsPreferenceFragmentLinkData.callerMetric = 65;
                    settingsPreferenceFragmentLinkData.intent = intent;
                    FragmentActivity fragmentActivity = this.mContext;
                    if (com.android.settings.Utils.hasPackage(fragmentActivity, fragmentActivity.getString(R.string.config_sec_toplevel_devicecare_package))) {
                        this.mLinkedDataStorage.topLevelKey = "top_level_devicecare";
                    }
                    this.mLinkedDataStorage.titleRes = R.string.as_app_title;
                    SecRelativeLinkView secRelativeLinkView = new SecRelativeLinkView(this.mContext);
                    this.mRelativeLinkView = secRelativeLinkView;
                    secRelativeLinkView.setBackgroundResource(R.color.sec_widget_round_and_bgcolor);
                    this.mRelativeLinkView.findViewById(R.id.relative_link_container).setPaddingRelative(0, 0, 0, 0);
                    SecRelativeLinkView secRelativeLinkView2 = this.mRelativeLinkView;
                    secRelativeLinkView2.setPaddingRelative(secRelativeLinkView2.getPaddingStart(), this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_app_relative_view_padding_top) + this.mRelativeLinkView.getPaddingTop(), this.mRelativeLinkView.getPaddingEnd(), this.mRelativeLinkView.getPaddingBottom());
                    linearLayout.addView(this.mRelativeLinkView);
                }
                linearLayout.addView(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sec_manage_applications_apps_footer, viewGroup, false));
                view = linearLayout;
            } else if (this.mUseHeader && i == 4) {
                View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sec_manage_applications_desc, viewGroup, false);
                inflate.setTag("noDivider");
                view = inflate;
            } else if (this.mUsePref && i == 9) {
                View m = MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.sec_manage_applications_order, viewGroup, false);
                this.mManageApplications.mFilterEmptyView = m.findViewById(R.id.filter_empty);
                View findViewById = m.findViewById(R.id.default_app_settings);
                findViewById.semSetRoundedCorners(15);
                findViewById.semSetRoundedCornerColor(15, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
                ((TextView) m.findViewById(R.id.default_app_settings_title)).setText(R.string.app_default_dashboard_title);
                ((TextView) m.findViewById(R.id.default_app_settings_summary)).setText(R.string.sec_default_apps_summary);
                View findViewById2 = m.findViewById(R.id.samsung_app_settings);
                findViewById2.semSetRoundedCorners(15);
                findViewById2.semSetRoundedCornerColor(15, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
                ((TextView) m.findViewById(R.id.samsung_app_settings_title)).setText(R.string.sec_app_settings);
                View findViewById3 = m.findViewById(R.id.filter);
                findViewById3.semSetRoundedCorners(3);
                findViewById3.semSetRoundedCornerColor(3, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
                m.setTag("noDivider");
                view = m;
            } else {
                int i2 = this.mManageApplications.mListType;
                if (i2 == 1) {
                    view = ApplicationViewHolder.newView(1, viewGroup, true, true);
                } else if (i2 == 17 && i == 2) {
                    FragmentActivity fragmentActivity2 = this.mContext;
                    int i3 = ApplicationViewHolder.$r8$clinit;
                    ViewGroup viewGroup2 = (ViewGroup) MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.preference_app_header_animation, viewGroup, false);
                    ((TextView) viewGroup2.findViewById(R.id.apps_top_intro_text)).setText(R.string.desc_cloned_apps_intro_text);
                    LottieAnimationView lottieAnimationView = (LottieAnimationView) viewGroup2.findViewById(R.id.illustration_lottie);
                    lottieAnimationView.setAnimation(R.raw.app_cloning);
                    lottieAnimationView.setVisibility(0);
                    LottieColorUtils.applyDynamicColors(fragmentActivity2, lottieAnimationView);
                    ((TextView) viewGroup2.findViewById(R.id.app_list_text)).setText(R.string.desc_cloneable_app_list_text);
                    view = viewGroup2;
                } else {
                    view = (i2 == 17 && i == 3) ? ApplicationViewHolder.newView(17, viewGroup, true, true) : (i2 == 4 || i2 == 5 || i2 == 6 || i2 == 7 || i2 == 13 || i2 == 12 || i2 == 11 || i2 == 8 || i2 == 104 || i2 == 19 || i2 == 105 || i2 == 10) ? ApplicationViewHolder.newView(i2, viewGroup, true, false) : ApplicationViewHolder.newView(i2, viewGroup, false, false);
                }
            }
            return new ApplicationViewHolder(view);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
            this.mOnScrollListener = null;
            this.mRecyclerView = null;
        }

        @Override // com.android.settings.applications.AppStateBaseBridge.Callback
        public final void onExtraInfoUpdated() {
            SearchView searchView;
            ManageApplications manageApplications = this.mManageApplications;
            if (manageApplications.mListType == 1 && (searchView = manageApplications.mSearchView) != null && TextUtils.isEmpty(searchView.mSearchSrcTextView.getText())) {
                notifyDataSetChanged();
            }
            this.mHasReceivedBridgeCallback = true;
            rebuild();
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public final void onLauncherInfoChanged() {
            if (this.mManageApplications.mShowSystem) {
                return;
            }
            rebuild();
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public final void onLoadEntriesCompleted() {
            this.mHasReceivedLoadEntries = true;
            rebuild();
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public final void onPackageListChanged() {
            rebuild();
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public final void onPackageSizeChanged(String str) {
            if (this.mEntries == null || TextUtils.isEmpty(this.mManageApplications.mCurrentPkgName)) {
                return;
            }
            int size = this.mEntries.size();
            for (int i = 0; i < size; i++) {
                ApplicationInfo applicationInfo = ((ApplicationsState.AppEntry) this.mEntries.get(i)).info;
                if (applicationInfo != null && TextUtils.equals(str, applicationInfo.packageName)) {
                    if (TextUtils.equals(this.mManageApplications.mCurrentPkgName, applicationInfo.packageName)) {
                        rebuild();
                        return;
                    }
                    if (!this.mUseHeader || i > 2) {
                        OnScrollListener onScrollListener = this.mOnScrollListener;
                        if (onScrollListener.mScrollState == 0) {
                            if (ManageApplications.DEBUG) {
                                Log.i("ManageApplications", "postNotifyItemChange");
                            }
                            onScrollListener.mAdapter.notifyItemChanged(i);
                        } else {
                            onScrollListener.mDelayNotifyDataChange = true;
                        }
                    }
                }
            }
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public final void onRebuildComplete(ArrayList arrayList) {
            String str;
            String str2;
            ApplicationInfo applicationInfo;
            ApplicationInfo applicationInfo2;
            if (ManageApplications.DEBUG) {
                Log.d("ManageApplications", "onRebuildComplete size=" + arrayList.size());
            }
            FragmentActivity fragmentActivity = this.mContext;
            AppUtils.preloadTopIcons(fragmentActivity, arrayList, fragmentActivity.getResources().getInteger(R.integer.config_num_visible_app_icons));
            int i = this.mAppFilter.mFilterType;
            if (i == 0 || i == 1) {
                int size = arrayList.size();
                ArrayList arrayList2 = new ArrayList(size);
                ApplicationInfo applicationInfo3 = null;
                int i2 = 0;
                while (i2 < size) {
                    ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) arrayList.get(i2);
                    ApplicationInfo applicationInfo4 = appEntry.info;
                    if (!((applicationInfo3 == null || applicationInfo4 == null || (str = ((PackageItemInfo) applicationInfo3).packageName) == null || (str2 = ((PackageItemInfo) applicationInfo4).packageName) == null) ? false : str.equals(str2))) {
                        arrayList2.add(appEntry);
                    }
                    i2++;
                    applicationInfo3 = applicationInfo4;
                }
                arrayList2.trimToSize();
                arrayList = arrayList2;
            }
            this.mEntries = arrayList;
            this.mOriginalEntries = arrayList;
            this.mHasEnabledEntry = false;
            SectionInfo[] sectionInfoArr = EMPTY_SECTIONS;
            if (arrayList != null) {
                int i3 = 0;
                while (i3 < this.mEntries.size()) {
                    if (this.mEntries.get(i3) != null) {
                        if (this.mManageApplications.mListType == 8 && (applicationInfo2 = ((ApplicationsState.AppEntry) this.mEntries.get(i3)).info) != null && AppUtils.isSystemUidApp(applicationInfo2.uid)) {
                            this.mEntries.remove(i3);
                        } else if (this.mManageApplications.mListType != 6 || (applicationInfo = ((ApplicationsState.AppEntry) this.mEntries.get(i3)).info) == null || !AppUtils.isSystemUidApp(applicationInfo.uid) || Arrays.binarySearch(this.mContext.getResources().getStringArray(R.array.display_over_apps_permission_change_exempt), applicationInfo.packageName) < 0) {
                            if (this.mManageApplications.mListType == 8 && this.mIsEnable && !this.mHasEnabledEntry && (((ApplicationsState.AppEntry) this.mEntries.get(i3)).extraInfo instanceof AppStateInstallAppsBridge.InstallAppsState) && ((AppStateInstallAppsBridge.InstallAppsState) ((ApplicationsState.AppEntry) this.mEntries.get(i3)).extraInfo).canInstallApps()) {
                                this.mHasEnabledEntry = true;
                            }
                            if (((ApplicationsState.AppEntry) this.mEntries.get(i3)).info != null) {
                                if (this.mHighlightTargetPosition != -1 && TextUtils.equals(((ApplicationsState.AppEntry) this.mEntries.get(i3)).info.packageName, this.mManageApplications.mHighlightTargetPackageName) && UserHandle.getUserId(((ApplicationsState.AppEntry) this.mEntries.get(i3)).info.uid) == this.mManageApplications.mHighlightTargetPackageUid) {
                                    this.mHighlightTargetPosition = i3;
                                }
                                if ("com.samsung.gpuwatchapp".equals(((ApplicationsState.AppEntry) this.mEntries.get(i3)).info.packageName)) {
                                    arrayList.remove(i3);
                                }
                            }
                        } else {
                            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(new StringBuilder(), applicationInfo.packageName, " is hide in Display over other apps(Appear on top) permission setting for the Scone application.", "ManageApplications");
                            this.mEntries.remove(i3);
                        }
                        i3--;
                        i3++;
                    }
                    if (((ApplicationsState.AppEntry) this.mEntries.get(i3)).info == null) {
                        this.mEntries.remove(i3);
                    } else if (this.mManageApplications.mListType != 1 || ((ApplicationsState.AppEntry) this.mEntries.get(i3)).info.enabled) {
                        int userId = UserHandle.getUserId(((ApplicationsState.AppEntry) this.mEntries.get(i3)).info.uid);
                        if (SemPersonaManager.isKnoxId(UserHandle.getCallingUserId())) {
                            if (this.mManageApplications.mIsWorkOnly) {
                                if (userId != UserHandle.getCallingUserId()) {
                                    this.mEntries.remove(i3);
                                } else {
                                    i3++;
                                }
                            } else if (SemPersonaManager.isKnoxId(userId)) {
                                this.mEntries.remove(i3);
                            } else {
                                i3++;
                            }
                        } else if (this.mManageApplications.mIsWorkOnly) {
                            if (SemPersonaManager.isSecureFolderId(userId)) {
                                this.mEntries.remove(i3);
                            } else {
                                i3++;
                            }
                        } else if (SemPersonaManager.isKnoxId(userId)) {
                            this.mEntries.remove(i3);
                        } else {
                            i3++;
                        }
                    } else {
                        this.mEntries.remove(i3);
                    }
                    i3--;
                    i3++;
                }
                ArrayList arrayList3 = this.mEntries;
                this.mEntries = arrayList3;
                if (this.mManageApplications.mListType == 0) {
                    if (arrayList3.size() == 0) {
                        Log.i("ManageApplications", "mFilterEmptyView VISIBLE");
                        View view = this.mManageApplications.mFilterEmptyView;
                        if (view != null) {
                            view.setVisibility(0);
                        }
                    } else {
                        Log.i("ManageApplications", "mFilterEmptyView GONE");
                        View view2 = this.mManageApplications.mFilterEmptyView;
                        if (view2 != null) {
                            view2.setVisibility(8);
                        }
                    }
                }
                Log.i("ManageApplications", "mEntries.size() : " + this.mEntries.size());
                if ((this.mUseHeader && this.mEntries.size() > 0) || this.mUsePref) {
                    Log.i("ManageApplications", "new AppEntry(mContext, null, -1) added");
                    this.mEntries.add(0, new ApplicationsState.AppEntry(this.mContext, null, -1L));
                }
                if (this.mEntries != null) {
                    ManageApplications manageApplications = this.mManageApplications;
                    boolean z = ManageApplications.DEBUG;
                    if (manageApplications.isFastScrollEnabled$1() && this.mManageApplications.mSortOrder == 0) {
                        if (this.mIndex == null) {
                            LocaleList locales = this.mContext.getResources().getConfiguration().getLocales();
                            if (locales.size() == 0) {
                                locales = new LocaleList(Locale.ENGLISH);
                            }
                            AlphabeticIndex alphabeticIndex = new AlphabeticIndex(locales.get(0));
                            int size2 = locales.size();
                            for (int i4 = 1; i4 < size2; i4++) {
                                Locale locale = locales.get(i4);
                                if (!((locale == null || !"zh".equals(locale.getLanguage()) || TextUtils.isEmpty(locale.getScript())) ? false : "Hant".equals(ULocale.addLikelySubtags(ULocale.forLocale(locale)).getScript()))) {
                                    alphabeticIndex.addLabels(locales.get(i4));
                                }
                            }
                            alphabeticIndex.addLabels(Locale.ENGLISH);
                            this.mIndex = alphabeticIndex.buildImmutableIndex();
                        }
                        ArrayList arrayList4 = new ArrayList();
                        int size3 = this.mEntries.size();
                        this.mPositionToSectionIndex = new int[size3];
                        int i5 = -1;
                        for (int i6 = 0; i6 < size3; i6++) {
                            String str3 = ((ApplicationsState.AppEntry) this.mEntries.get(i6)).label;
                            AlphabeticIndex.ImmutableIndex immutableIndex = this.mIndex;
                            if (TextUtils.isEmpty(str3)) {
                                str3 = ApnSettings.MVNO_NONE;
                            }
                            int bucketIndex = immutableIndex.getBucketIndex(str3);
                            if (bucketIndex != i5) {
                                if (this.mUsePref && i6 == 0) {
                                    arrayList4.add(new SectionInfo(ApnSettings.MVNO_NONE, i6));
                                } else {
                                    arrayList4.add(new SectionInfo(this.mIndex.getBucket(bucketIndex).getLabel(), i6));
                                }
                                i5 = bucketIndex;
                            }
                            this.mPositionToSectionIndex[i6] = arrayList4.size() - 1;
                        }
                        this.mSections = (SectionInfo[]) arrayList4.toArray(sectionInfoArr);
                        SearchView searchView = this.mManageApplications.mSearchView;
                        if (searchView == null || !searchView.isVisibleToUser()) {
                            notifyDataSetChanged();
                        }
                    }
                }
                this.mSections = sectionInfoArr;
                this.mPositionToSectionIndex = null;
            } else {
                this.mEntries = null;
                this.mSections = sectionInfoArr;
                this.mPositionToSectionIndex = null;
            }
            SearchView searchView2 = this.mManageApplications.mSearchView;
            if (searchView2 == null || !searchView2.isVisibleToUser()) {
                notifyDataSetChanged();
                ManageApplications manageApplications2 = this.mManageApplications;
                if (manageApplications2.mListType == 1) {
                    manageApplications2.mFilterAdapter.notifyDataSetChanged();
                }
            } else if (TextUtils.isEmpty(this.mManageApplications.mSearchView.mSearchSrcTextView.getText())) {
                notifyDataSetChanged();
                ManageApplications manageApplications3 = this.mManageApplications;
                if (manageApplications3.mListType == 1) {
                    manageApplications3.mFilterAdapter.notifyDataSetChanged();
                }
            }
            if (getItemCount() == 0 || (this.mUseFooter && getItemCount() == 1)) {
                this.mLoadingViewController.showEmpty();
            } else {
                this.mLoadingViewController.showContent(false);
                SearchView searchView3 = this.mManageApplications.mSearchView;
                if (searchView3 != null && searchView3.isVisibleToUser()) {
                    Editable text = this.mManageApplications.mSearchView.mSearchSrcTextView.getText();
                    if (!TextUtils.isEmpty(text)) {
                        filterSearch(text.toString());
                    }
                }
            }
            if (this.mLastIndex != -1 && getItemCount() > this.mLastIndex) {
                this.mManageApplications.mRecyclerView.getLayoutManager().scrollToPosition(this.mLastIndex);
                this.mLastIndex = -1;
            }
            this.mManageApplications.mRecyclerView.postDelayed(new Runnable() { // from class: com.android.settings.applications.manageapplications.ManageApplications.ApplicationsAdapter.1
                @Override // java.lang.Runnable
                public final void run() {
                    ApplicationsAdapter applicationsAdapter = ApplicationsAdapter.this;
                    int i7 = applicationsAdapter.mHighlightTargetPosition;
                    if (i7 >= 0) {
                        applicationsAdapter.mManageApplications.mRecyclerView.scrollToPosition(i7 + 2);
                    }
                }
            }, 500L);
            ManageApplications manageApplications4 = this.mManageApplications;
            if (manageApplications4.mListType == 4) {
                return;
            }
            if (!manageApplications4.mIsWorkOnly || manageApplications4.mWorkUserId == UserHandle.myUserId() || this.mManageApplications.mIsSecureOrSeparationUserId) {
                ManageApplications manageApplications5 = this.mManageApplications;
                boolean z2 = this.mState.mHaveDisabledApps;
                manageApplications5.getClass();
            } else {
                boolean z3 = this.mState.mWorkHaveDisabledApp;
            }
            ManageApplications manageApplications6 = this.mManageApplications;
            if (manageApplications6.mListType == 14) {
                manageApplications6.updateOptionsMenu$1();
            }
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public final void onRunningStateChanged(boolean z) {
            this.mManageApplications.getActivity().setProgressBarIndeterminateVisibility(z);
        }

        public final void rebuild(int i, boolean z) {
            if (i != this.mLastSortMode || z) {
                this.mManageApplications.mSortOrder = i;
                this.mLastSortMode = i;
                rebuild();
            }
        }

        public final void resume(int i) {
            ApplicationsState.AppEntry appEntry;
            if (ManageApplications.DEBUG) {
                SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(new StringBuilder("Resume!  mResumed="), this.mResumed, "ManageApplications");
            }
            if (this.mResumed) {
                rebuild(i, false);
            } else {
                this.mResumed = true;
                this.mSession.onResume();
                this.mLastSortMode = i;
                AppStateBaseBridge appStateBaseBridge = this.mExtraInfoBridge;
                if (appStateBaseBridge != null) {
                    appStateBaseBridge.resume(false);
                }
                rebuild();
            }
            ManageApplications manageApplications = this.mManageApplications;
            if (manageApplications.mListType == 1 && (appEntry = manageApplications.mNotificationClickEntry) != null && manageApplications.mNotificationClickHolder != null) {
                NotificationBackend notificationBackend = manageApplications.mNotificationBackend;
                ApplicationInfo applicationInfo = appEntry.info;
                String str = applicationInfo.packageName;
                int i2 = applicationInfo.uid;
                notificationBackend.getClass();
                boolean notificationsBanned = NotificationBackend.getNotificationsBanned(i2, str);
                ManageApplications manageApplications2 = this.mManageApplications;
                ApplicationsState.AppEntry appEntry2 = manageApplications2.mNotificationClickEntry;
                Object obj = appEntry2.extraInfo;
                if (obj instanceof AppStateNotificationBridge.NotificationsSentState) {
                    AppStateNotificationBridge.NotificationsSentState notificationsSentState = (AppStateNotificationBridge.NotificationsSentState) obj;
                    if (notificationsSentState.blocked != notificationsBanned) {
                        notificationsSentState.blocked = notificationsBanned;
                        updateSummary(manageApplications2.mNotificationClickHolder, appEntry2);
                        ManageApplications manageApplications3 = this.mManageApplications;
                        updateSwitch(manageApplications3.mNotificationClickHolder, manageApplications3.mNotificationClickEntry);
                        ManageApplications manageApplications4 = this.mManageApplications;
                        manageApplications4.mNotificationClickEntry = null;
                        manageApplications4.mNotificationClickHolder = null;
                    }
                }
            }
            if (this.mManageApplications.mListType == 8) {
                this.mIsEnable = true;
                this.mSummaryRes = -1;
                int enterprisePolicyEnabled = com.android.settings.Utils.getEnterprisePolicyEnabled(this.mContext, "content://com.sec.knox.provider/RestrictionPolicy2", "isNonMarketAppAllowed");
                boolean z = enterprisePolicyEnabled == 0;
                Log.d("ManageApplications", "checkExternalSourcesCondition : blockNonMarketAppInstall " + z + "(" + enterprisePolicyEnabled + ")");
                UserManager userManager = UserManager.get(this.mContext);
                ManageApplications manageApplications5 = this.mManageApplications;
                int i3 = manageApplications5.mIsWorkOnly ? manageApplications5.mWorkUserId : 0;
                boolean hasUserRestrictionForUser = userManager.hasUserRestrictionForUser("no_install_unknown_sources", UserHandle.of(i3));
                boolean hasUserRestrictionForUser2 = userManager.hasUserRestrictionForUser("no_install_unknown_sources_globally", UserHandle.of(i3));
                if (hasUserRestrictionForUser || hasUserRestrictionForUser2 || z) {
                    RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, i3, "no_install_unknown_sources");
                    StringBuilder m = Utils$$ExternalSyntheticOutline0.m("checkExternalSourcesCondition : [disallowInstallUnknownSources = ", hasUserRestrictionForUser, "], [disallowInstallUnknownSourcesGlobally = ", hasUserRestrictionForUser2, "], [admin null? ");
                    m.append(checkIfRestrictionEnforced == null);
                    m.append("]");
                    Log.d("ManageApplications", m.toString());
                    this.mIsEnable = false;
                    if (checkIfRestrictionEnforced != null) {
                        this.mEnforcedAdmin = checkIfRestrictionEnforced;
                        this.mSummaryRes = R.string.disabled_by_admin;
                    }
                }
                if (this.mSummaryRes != R.string.disabled_by_admin) {
                    RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced2 = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, UserHandle.myUserId(), "no_install_unknown_sources_globally");
                    RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced3 = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, UserHandle.myUserId(), "no_install_unknown_sources");
                    StringBuilder sb = new StringBuilder("checkExternalSourcesCondition : isAdminGlobal = ");
                    sb.append(checkIfRestrictionEnforced2 != null);
                    sb.append(" isAdmin = ");
                    ActionBarContextView$$ExternalSyntheticOutline0.m(sb, checkIfRestrictionEnforced3 != null, "ManageApplications");
                    if (checkIfRestrictionEnforced3 == null && checkIfRestrictionEnforced2 == null) {
                        return;
                    }
                    if (checkIfRestrictionEnforced3 != null) {
                        checkIfRestrictionEnforced2 = checkIfRestrictionEnforced3;
                    }
                    this.mEnforcedAdmin = checkIfRestrictionEnforced2;
                    this.mIsEnable = false;
                    this.mSummaryRes = R.string.disabled_by_admin;
                }
            }
        }

        public final void setFilter(AppFilterItem appFilterItem) {
            this.mAppFilter = appFilterItem;
            int i = appFilterItem.mFilterType;
            HashMap hashMap = new HashMap();
            int i2 = this.mManageApplications.mListType;
            if (i2 != 1) {
                if (i2 == 15) {
                    switch (i) {
                        case 21:
                            logAction(1794);
                            break;
                        case 22:
                            logAction(1795);
                            break;
                        case 23:
                            logAction(1793);
                            break;
                        default:
                            logAction(1792);
                            break;
                    }
                }
                rebuild();
                return;
            }
            if (3 == i) {
                rebuild(R.id.sort_order_frequent_notification, false);
                hashMap.put("NSTE0019", "most frequent");
            } else if (2 == i) {
                rebuild(R.id.sort_order_recent_notification, false);
                hashMap.put("NSTE0019", "most recent");
            } else if (16 == i) {
                rebuild(R.id.sort_order_alpha, true);
                hashMap.put("NSTE0019", "blocked");
            } else if (28 == appFilterItem.mFilterType) {
                rebuild(R.id.sort_order_alpha, true);
                hashMap.put("NSTE0019", "all");
            }
            if (this.mLastSortMode != appFilterItem.mFilterType) {
                SALogging.insertSALog(Integer.toString(36021), "NSTE0019", hashMap, 0);
            }
        }

        public final void setUnvisiblePrefs(boolean z) {
            if (this.mUsePref) {
                this.mPrefVisible = z;
            }
            if (this.mRelativeLinkView != null) {
                if (ManageApplications.m736$$Nest$smisSupportedRelativeLink(this.mManageApplications) && z) {
                    this.mRelativeLinkView.setVisibility(0);
                } else {
                    this.mRelativeLinkView.setVisibility(8);
                }
            }
            if (this.mManageApplications.mListType == 8 && this.mHeaderButtonVisible != z) {
                this.mHeaderButtonVisible = z;
                rebuild();
            } else if (this.mUsePref || this.mUseFooter) {
                rebuild();
            }
        }

        public void updateLoading() {
            if (this.mHasReceivedLoadEntries && this.mSession.getAllApps().size() != 0) {
                this.mLoadingViewController.showContent(false);
            } else {
                LoadingViewController loadingViewController = this.mLoadingViewController;
                loadingViewController.mFgHandler.postDelayed(loadingViewController.mShowLoadingContainerRunnable, 100L);
            }
        }

        public final void updateSummary(ApplicationViewHolder applicationViewHolder, ApplicationsState.AppEntry appEntry) {
            AppStateWriteSettingsBridge.WriteSettingsState writeSettingsInfo;
            ManageApplications manageApplications = this.mManageApplications;
            int i = manageApplications.mListType;
            if (i == 1) {
                Object obj = appEntry.extraInfo;
                if (obj == null || !(obj instanceof AppStateNotificationBridge.NotificationsSentState)) {
                    applicationViewHolder.setSummary(null);
                    return;
                } else {
                    applicationViewHolder.setSummary(AppStateNotificationBridge.getSummary(this.mContext, (AppStateNotificationBridge.NotificationsSentState) obj, this.mLastSortMode));
                    return;
                }
            }
            if (i == 101) {
                applicationViewHolder.setSummary(null);
                return;
            }
            if (i == 104) {
                Object obj2 = appEntry.extraInfo;
                if (obj2 == null || !(obj2 instanceof AppStateAppOpsBridge.PermissionState)) {
                    applicationViewHolder.mSwitch.setChecked(false);
                } else {
                    applicationViewHolder.mSwitch.setChecked(((AppStateAppOpsBridge.PermissionState) obj2).isPermissible());
                }
                applicationViewHolder.mSwitch.setVisibility(0);
                applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
                return;
            }
            if (i == 105) {
                Object obj3 = appEntry.extraInfo;
                if (obj3 == null || !(obj3 instanceof AppStateAppOpsBridge.PermissionState)) {
                    applicationViewHolder.mSwitch.setChecked(false);
                } else {
                    applicationViewHolder.mSwitch.setChecked(((AppStateAppOpsBridge.PermissionState) obj3).isPermissible());
                }
                applicationViewHolder.mSwitch.setVisibility(0);
                applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
                return;
            }
            int i2 = R.string.app_permission_summary_not_allowed;
            switch (i) {
                case 4:
                    break;
                case 5:
                    Object obj4 = appEntry.extraInfo;
                    if (obj4 != null) {
                        applicationViewHolder.mSwitch.setChecked(obj4 != Boolean.TRUE);
                    }
                    applicationViewHolder.mSwitch.setVisibility(0);
                    applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
                    return;
                case 6:
                    if (appEntry.extraInfo instanceof AppStateAppOpsBridge.PermissionState) {
                        applicationViewHolder.mSwitch.setChecked(new AppStateOverlayBridge.OverlayState((AppStateAppOpsBridge.PermissionState) appEntry.extraInfo).isPermissible());
                    } else {
                        applicationViewHolder.mSwitch.setChecked(false);
                    }
                    applicationViewHolder.mSwitch.setVisibility(0);
                    applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
                    return;
                case 7:
                    FragmentActivity fragmentActivity = this.mContext;
                    Object obj5 = appEntry.extraInfo;
                    if (obj5 instanceof AppStateWriteSettingsBridge.WriteSettingsState) {
                        writeSettingsInfo = (AppStateWriteSettingsBridge.WriteSettingsState) obj5;
                    } else if (obj5 instanceof AppStateAppOpsBridge.PermissionState) {
                        writeSettingsInfo = new AppStateWriteSettingsBridge.WriteSettingsState((AppStateAppOpsBridge.PermissionState) obj5);
                    } else {
                        AppStateWriteSettingsBridge appStateWriteSettingsBridge = new AppStateWriteSettingsBridge(fragmentActivity, null, null);
                        ApplicationInfo applicationInfo = appEntry.info;
                        writeSettingsInfo = appStateWriteSettingsBridge.getWriteSettingsInfo(applicationInfo.uid, applicationInfo.packageName);
                    }
                    if (writeSettingsInfo.isPermissible()) {
                        i2 = R.string.app_permission_summary_allowed;
                    }
                    applicationViewHolder.setSummary(fragmentActivity.getString(i2));
                    Object obj6 = appEntry.extraInfo;
                    if (obj6 instanceof AppStateAppOpsBridge.PermissionState) {
                        SwitchCompat switchCompat = applicationViewHolder.mSwitch;
                        AppStateAppOpsBridge.PermissionState permissionState = (AppStateAppOpsBridge.PermissionState) obj6;
                        String str = permissionState.packageName;
                        int i3 = permissionState.appOpMode;
                        boolean z = permissionState.staticPermissionGranted;
                        if (i3 != 3) {
                            z = i3 == 0;
                        }
                        switchCompat.setChecked(z);
                    } else {
                        applicationViewHolder.mSwitch.setChecked(false);
                    }
                    applicationViewHolder.mSwitch.setVisibility(0);
                    applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
                    return;
                case 8:
                    Object obj7 = appEntry.extraInfo;
                    if (obj7 instanceof AppStateInstallAppsBridge.InstallAppsState) {
                        applicationViewHolder.mSwitch.setChecked(((AppStateInstallAppsBridge.InstallAppsState) obj7).canInstallApps());
                    } else {
                        applicationViewHolder.mSwitch.setChecked(false);
                    }
                    applicationViewHolder.mSwitch.setVisibility(0);
                    applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
                    return;
                default:
                    switch (i) {
                        case 10:
                            Object obj8 = appEntry.extraInfo;
                            if (obj8 instanceof AppStateChangeWifiStateBridge.WifiSettingsState) {
                                AppStateChangeWifiStateBridge.WifiSettingsState wifiSettingsState = (AppStateChangeWifiStateBridge.WifiSettingsState) obj8;
                                boolean z2 = !wifiSettingsState.permissionDeclared;
                                boolean isPermissible = wifiSettingsState.isPermissible();
                                SwitchCompat switchCompat2 = applicationViewHolder.mSwitch;
                                if (!z2 && !isPermissible) {
                                    r3 = false;
                                }
                                switchCompat2.setChecked(r3);
                            } else {
                                applicationViewHolder.mSwitch.setChecked(false);
                            }
                            applicationViewHolder.mSwitch.setVisibility(0);
                            applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
                            break;
                        case 11:
                        case 13:
                            break;
                        case 12:
                            Object obj9 = appEntry.extraInfo;
                            if (obj9 instanceof AppStateAlarmsAndRemindersBridge.AlarmsAndRemindersState) {
                                applicationViewHolder.mSwitch.setChecked(((AppStateAlarmsAndRemindersBridge.AlarmsAndRemindersState) obj9).isAllowed());
                            } else {
                                applicationViewHolder.mSwitch.setChecked(false);
                            }
                            applicationViewHolder.mSwitch.setVisibility(0);
                            applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
                            break;
                        case 14:
                            applicationViewHolder.setSummary(AppLocaleDetails.getSummary(this.mContext, appEntry.info));
                            break;
                        case 15:
                            applicationViewHolder.setSummary(null);
                            break;
                        case 16:
                            applicationViewHolder.setSummary(LongBackgroundTasksDetails.getSummary(this.mContext, appEntry));
                            break;
                        case 17:
                            applicationViewHolder.setSummary(null);
                            break;
                        case 18:
                            FragmentActivity fragmentActivity2 = this.mContext;
                            Object obj10 = appEntry.extraInfo;
                            if ((obj10 instanceof AppStateNfcTagAppsBridge.NfcTagAppState ? (AppStateNfcTagAppsBridge.NfcTagAppState) obj10 : new AppStateNfcTagAppsBridge.NfcTagAppState(false, false)).mIsAllowed) {
                                i2 = R.string.app_permission_summary_allowed;
                            }
                            applicationViewHolder.setSummary(fragmentActivity2.getString(i2));
                            break;
                        case 19:
                            Object obj11 = appEntry.extraInfo;
                            if (obj11 instanceof AppStateAppOpsBridge.PermissionState) {
                                applicationViewHolder.mSwitch.setChecked(((AppStateAppOpsBridge.PermissionState) obj11).isPermissible());
                            } else {
                                applicationViewHolder.mSwitch.setChecked(false);
                            }
                            applicationViewHolder.mSwitch.setVisibility(0);
                            applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
                            break;
                        default:
                            applicationViewHolder.updateSizeText(appEntry, manageApplications.mInvalidSizeStr, this.mWhichSize);
                            break;
                    }
                    return;
            }
            Object obj12 = appEntry.extraInfo;
            if (obj12 == null || !(obj12 instanceof AppStateAppOpsBridge.PermissionState)) {
                applicationViewHolder.mSwitch.setChecked(false);
            } else {
                applicationViewHolder.mSwitch.setChecked(((AppStateAppOpsBridge.PermissionState) obj12).isPermissible());
            }
            applicationViewHolder.mSwitch.setVisibility(0);
            applicationViewHolder.updateSizeText(appEntry, this.mManageApplications.mInvalidSizeStr, this.mWhichSize);
        }

        public final void updateSwitch(final ApplicationViewHolder applicationViewHolder, final ApplicationsState.AppEntry appEntry) {
            boolean z;
            ViewGroup viewGroup;
            ManageApplications manageApplications = this.mManageApplications;
            int i = manageApplications.mListType;
            if (i != 1) {
                if (i != 17) {
                    return;
                }
                FragmentActivity fragmentActivity = this.mContext;
                final FragmentActivity activity = manageApplications.getActivity();
                applicationViewHolder.getClass();
                final Context applicationContext = activity.getApplicationContext();
                View.OnClickListener anonymousClass1 = new View.OnClickListener() { // from class: com.android.settings.applications.manageapplications.ApplicationViewHolder.1
                    public final /* synthetic */ ManageApplications.ApplicationsAdapter val$adapter;
                    public final /* synthetic */ Context val$context;
                    public final /* synthetic */ ApplicationsState.AppEntry val$entry;
                    public final /* synthetic */ FragmentActivity val$manageApplicationsActivity;

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.applications.manageapplications.ApplicationViewHolder$1$1 */
                    public final class AsyncTaskC00191 extends AsyncTask {
                        public final /* synthetic */ CloneBackend val$cloneBackend;
                        public final /* synthetic */ String val$packageName;

                        public AsyncTaskC00191(CloneBackend cloneBackend, String str) {
                            r2 = cloneBackend;
                            r3 = str;
                        }

                        @Override // android.os.AsyncTask
                        public final Object doInBackground(Object[] objArr) {
                            return Integer.valueOf(r2.installCloneApp(r3));
                        }

                        @Override // android.os.AsyncTask
                        public final void onPostExecute(Object obj) {
                            ApplicationViewHolder.this.mProgressBar.setVisibility(4);
                            ApplicationViewHolder.this.mAddIcon.setVisibility(0);
                            if (((Integer) obj).intValue() != 0) {
                                ApplicationViewHolder.this.setSummary(null);
                            } else {
                                r4.rebuild();
                            }
                        }
                    }

                    public AnonymousClass1(final Context applicationContext2, final ApplicationsState.AppEntry appEntry2, final ManageApplications.ApplicationsAdapter this, final FragmentActivity activity2) {
                        r2 = applicationContext2;
                        r3 = appEntry2;
                        r4 = this;
                        r5 = activity2;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CloneBackend cloneBackend = CloneBackend.getInstance(r2);
                        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                        if (featureFactoryImpl == null) {
                            throw new UnsupportedOperationException("No feature factory configured");
                        }
                        SettingsMetricsFeatureProvider metricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
                        ApplicationsState.AppEntry appEntry2 = r3;
                        String str = appEntry2.info.packageName;
                        if (ApplicationViewHolder.this.mWidgetContainer != null) {
                            if (!appEntry2.isClonedProfile()) {
                                metricsFeatureProvider.action(r2, 1807, new Pair[0]);
                                ApplicationViewHolder.this.mAddIcon.setVisibility(4);
                                ApplicationViewHolder.this.mProgressBar.setVisibility(0);
                                ApplicationViewHolder applicationViewHolder2 = ApplicationViewHolder.this;
                                applicationViewHolder2.mSummaryText = applicationViewHolder2.mContext.getString(R.string.cloned_app_creation_summary);
                                applicationViewHolder2.updateDisableTextToSummary();
                                applicationViewHolder2.updateSummaryVisibility();
                                new AsyncTask() { // from class: com.android.settings.applications.manageapplications.ApplicationViewHolder.1.1
                                    public final /* synthetic */ CloneBackend val$cloneBackend;
                                    public final /* synthetic */ String val$packageName;

                                    public AsyncTaskC00191(CloneBackend cloneBackend2, String str2) {
                                        r2 = cloneBackend2;
                                        r3 = str2;
                                    }

                                    @Override // android.os.AsyncTask
                                    public final Object doInBackground(Object[] objArr) {
                                        return Integer.valueOf(r2.installCloneApp(r3));
                                    }

                                    @Override // android.os.AsyncTask
                                    public final void onPostExecute(Object obj) {
                                        ApplicationViewHolder.this.mProgressBar.setVisibility(4);
                                        ApplicationViewHolder.this.mAddIcon.setVisibility(0);
                                        if (((Integer) obj).intValue() != 0) {
                                            ApplicationViewHolder.this.setSummary(null);
                                        } else {
                                            r4.rebuild();
                                        }
                                    }
                                }.execute(new Void[0]);
                                return;
                            }
                            if (r3.isClonedProfile()) {
                                metricsFeatureProvider.action(r2, 1808, new Pair[0]);
                                FragmentActivity fragmentActivity2 = r5;
                                cloneBackend2.getClass();
                                Intent intent = new Intent("android.intent.action.UNINSTALL_PACKAGE", Uri.parse("package:" + str2));
                                intent.putExtra("android.intent.extra.UNINSTALL_ALL_USERS", false);
                                intent.putExtra("android.intent.extra.USER", UserHandle.of(cloneBackend2.mCloneUserId));
                                fragmentActivity2.startActivityAsUser(intent, UserHandle.of(cloneBackend2.mCloneUserId));
                            }
                        }
                    }
                };
                if (applicationViewHolder.mAddIcon != null) {
                    if (appEntry2.isClonedProfile()) {
                        applicationViewHolder.mAddIcon.setBackground(fragmentActivity.getDrawable(R.drawable.ic_trash_can));
                        applicationViewHolder.mSummaryText = applicationViewHolder.mContext.getString(R.string.cloned_app_created_summary);
                        applicationViewHolder.updateDisableTextToSummary();
                        applicationViewHolder.updateSummaryVisibility();
                    } else {
                        applicationViewHolder.mAddIcon.setBackground(fragmentActivity.getDrawable(R.drawable.ic_add_24dp));
                    }
                    applicationViewHolder.mAddIcon.setOnClickListener(anonymousClass1);
                    return;
                }
                return;
            }
            AppStateNotificationBridge.NotificationsSentState notificationsSentState = AppStateNotificationBridge.getNotificationsSentState(appEntry2);
            applicationViewHolder.mWidgetContainer.setVisibility(notificationsSentState != null && ((z = notificationsSentState.blockable) || (!z && notificationsSentState.blocked)) ? 0 : 8);
            FragmentActivity fragmentActivity2 = this.mContext;
            NotificationBackend notificationBackend = this.mManageApplications.mNotificationBackend;
            int i2 = this.mLastSortMode;
            applicationViewHolder.mContext = fragmentActivity2;
            applicationViewHolder.mBackend = notificationBackend;
            applicationViewHolder.mLastSortMode = i2;
            AppStateNotificationBridge.NotificationsSentState notificationsSentState2 = AppStateNotificationBridge.getNotificationsSentState(appEntry2);
            boolean z2 = notificationsSentState2 == null ? false : notificationsSentState2.blockable;
            boolean z3 = AppStateNotificationBridge.getNotificationsSentState(appEntry2) != null ? !r0.blocked : false;
            if (applicationViewHolder.mSwitch == null || (viewGroup = applicationViewHolder.mWidgetContainer) == null) {
                return;
            }
            viewGroup.setOnClickListener(appEntry2 != null ? new View.OnClickListener() { // from class: com.android.settings.applications.manageapplications.ApplicationViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ApplicationViewHolder applicationViewHolder2 = ApplicationViewHolder.this;
                    ApplicationsState.AppEntry appEntry2 = appEntry2;
                    applicationViewHolder2.getClass();
                    SwitchCompat switchCompat = (SwitchCompat) ((ViewGroup) view).findViewById(android.R.id.switch_widget);
                    ApplicationInfo applicationInfo = appEntry2.info;
                    if (AppUtils.isDeepSleepingEnable(applicationInfo.uid, applicationInfo.packageName) && !switchCompat.isChecked()) {
                        ApplicationInfo applicationInfo2 = new ApplicationInfo();
                        ApplicationInfo applicationInfo3 = appEntry2.info;
                        applicationInfo2.packageName = applicationInfo3.packageName;
                        applicationInfo2.uid = applicationInfo3.uid;
                        new SemAppRestrictionManager().restrict(3, 2, true, applicationInfo2.packageName, applicationInfo2.uid);
                        Context context = applicationViewHolder2.mContext;
                        Toast.makeText(context, context.getResources().getString(R.string.sec_app_notification_deep_sleeping_toast_text, appEntry2.label), 0).show();
                    }
                    if (switchCompat == null || !switchCompat.isEnabled()) {
                        return;
                    }
                    switchCompat.toggle();
                    NotificationBackend notificationBackend2 = applicationViewHolder2.mBackend;
                    ApplicationInfo applicationInfo4 = appEntry2.info;
                    String str = applicationInfo4.packageName;
                    int i3 = applicationInfo4.uid;
                    boolean isChecked = switchCompat.isChecked();
                    notificationBackend2.getClass();
                    NotificationBackend.setNotificationsEnabledForPackage(i3, str, isChecked);
                    AppStateNotificationBridge.NotificationsSentState notificationsSentState3 = AppStateNotificationBridge.getNotificationsSentState(appEntry2);
                    if (notificationsSentState3 != null) {
                        notificationsSentState3.blocked = !switchCompat.isChecked();
                        applicationViewHolder2.setSummary(AppStateNotificationBridge.getSummary(applicationViewHolder2.mContext, notificationsSentState3, applicationViewHolder2.mLastSortMode));
                    }
                    HashMap hashMap = new HashMap();
                    hashMap.put(ImIntent.Extras.EXTRA_FROM, "app list");
                    StringBuilder sb = new StringBuilder();
                    sb.append(appEntry2.info.packageName);
                    sb.append(";");
                    sb.append(switchCompat.isChecked() ? "On" : "Off");
                    hashMap.put("turnOnOff", sb.toString());
                    SALogging.insertSALog(Integer.toString(36021), "NSTE0005", hashMap, 0);
                }
            } : null);
            applicationViewHolder.mSwitch.setChecked(z3);
            applicationViewHolder.mSwitch.setEnabled(z2);
            applicationViewHolder.mSwitch.setContentDescription(appEntry2.labelDescription);
        }

        public final void rebuild() {
            ApplicationsState.AnonymousClass30 anonymousClass30;
            if (this.mHasReceivedLoadEntries && (this.mExtraInfoBridge == null || this.mHasReceivedBridgeCallback)) {
                if (Environment.isExternalStorageEmulated()) {
                    this.mWhichSize = 0;
                } else {
                    this.mWhichSize = 1;
                }
                ApplicationsState.AppFilter appFilter = this.mAppFilter.mFilter;
                ManageApplications manageApplications = this.mManageApplications;
                int i = manageApplications.mListType;
                if (i == 1 && !manageApplications.mShowSystem) {
                    appFilter = new ApplicationsState.AnonymousClass30(appFilter, AppStateNotificationBridge.FILTER_APP_NOTI_BLOCKABLE);
                }
                ApplicationsState.AppFilter appFilter2 = this.mCompositeFilter;
                if (appFilter2 != null) {
                    appFilter = new ApplicationsState.AnonymousClass30(appFilter, appFilter2);
                }
                if (!manageApplications.mShowSystem) {
                    if (((ArraySet) ManageApplications.LIST_TYPES_WITH_INSTANT).contains(Integer.valueOf(i))) {
                        anonymousClass30 = new ApplicationsState.AnonymousClass30(appFilter, ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER_AND_INSTANT);
                    } else {
                        anonymousClass30 = new ApplicationsState.AnonymousClass30(appFilter, ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER);
                    }
                    appFilter = anonymousClass30;
                }
                ThreadUtils.postOnBackgroundThread(new ManageApplications$$ExternalSyntheticLambda1(1, this, new ApplicationsState.AnonymousClass30(appFilter, ApplicationsState.FILTER_NOT_HIDE)));
                return;
            }
            if (ManageApplications.DEBUG) {
                StringBuilder sb = new StringBuilder("Not rebuilding until all the app entries loaded. !mHasReceivedLoadEntries=");
                sb.append(!this.mHasReceivedLoadEntries);
                sb.append(" !mExtraInfoBridgeNull=");
                sb.append(this.mExtraInfoBridge != null);
                sb.append(" !mHasReceivedBridgeCallback=");
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, !this.mHasReceivedBridgeCallback, "ManageApplications");
            }
        }

        @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
        public final void onPackageIconChanged() {
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final void onQueryTextSubmit(String str) {
    }
}
