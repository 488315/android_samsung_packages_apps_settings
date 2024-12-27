package com.samsung.android.settings.display;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.DisplaySettings;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.sec.ims.IMSParameter;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenTimeoutActivity extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.sec_screen_timeout_settings);
    public ScreenTimeoutAdapter mAdapter;
    public Context mContext;
    public boolean mDexMode;
    public View mLayoutPowerSavingDescription;
    public long mMaxTimeout;
    public LayoutPreference mPowerSavingDescription;
    public SecInsetCategoryPreference mPowerSavingDescriptionInsetCategory;
    public AnonymousClass1 mPowerSavingObserver;
    public RecyclerView mScreenTimeoutList;
    public AnonymousClass1 mScreenTimeoutObserver;
    public View mScreenTimeoutView;
    public boolean mHasFooterView = false;
    public boolean mItemEnabledPowerSaving = true;
    public boolean mItemEnabled = true;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScreenTimeoutAdapter extends RecyclerView.Adapter {
        public long mCurrentTimeout;
        public final CharSequence[] mEntries;
        public final boolean mHasFooterView;
        public final List mListItems = new ArrayList();
        public final CharSequence[] mValues;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class RecyclerFooterViewHolder extends RecyclerView.ViewHolder {
            public View mFooterView;
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class RecyclerViewHolder extends RecyclerView.ViewHolder {
            public RadioButton mCheckBoxView;
            public TextView mTitleView;
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ScreenTimeoutList {
            public boolean mIsChecked;
            public String mListItemTitle;
            public String mListItemValue;
        }

        public ScreenTimeoutAdapter(
                boolean z,
                CharSequence[] charSequenceArr,
                CharSequence[] charSequenceArr2,
                long j) {
            this.mEntries = charSequenceArr;
            this.mValues = charSequenceArr2;
            this.mCurrentTimeout = j;
            this.mHasFooterView = z;
            if (charSequenceArr == null) {
                Log.e("ScreenTimeoutActivity", "addScreenTimeoutListView failed");
                return;
            }
            int i = 0;
            while (true) {
                CharSequence[] charSequenceArr3 = this.mEntries;
                if (i >= charSequenceArr3.length) {
                    return;
                }
                CharSequence charSequence = charSequenceArr3[i];
                CharSequence charSequence2 = this.mValues[i];
                ScreenTimeoutList screenTimeoutList = new ScreenTimeoutList();
                screenTimeoutList.mListItemTitle = charSequence.toString();
                screenTimeoutList.mListItemValue = charSequence2.toString();
                if (charSequence2.equals(String.valueOf(this.mCurrentTimeout))) {
                    screenTimeoutList.mIsChecked = true;
                }
                this.mListItems.add(screenTimeoutList);
                notifyDataSetChanged();
                i++;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.mListItems.size() + (this.mHasFooterView ? 1 : 0);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemViewType(int i) {
            return (this.mHasFooterView && getItemCount() - 1 == i) ? 2 : 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
            boolean z = viewHolder instanceof RecyclerViewHolder;
            ScreenTimeoutActivity screenTimeoutActivity = ScreenTimeoutActivity.this;
            if (!z) {
                if (viewHolder instanceof RecyclerFooterViewHolder) {
                    final RestrictedLockUtils.EnforcedAdmin checkIfMaximumTimeToLockIsSet =
                            RestrictedLockUtilsInternal.checkIfMaximumTimeToLockIsSet(
                                    screenTimeoutActivity.mContext);
                    ((RecyclerFooterViewHolder) viewHolder)
                            .itemView
                            .findViewById(R.id.admin_more_details_link)
                            .setOnClickListener(
                                    new View
                                            .OnClickListener() { // from class:
                                                                 // com.samsung.android.settings.display.ScreenTimeoutActivity.ScreenTimeoutAdapter.2
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                                    ScreenTimeoutActivity.this.mContext,
                                                    checkIfMaximumTimeToLockIsSet);
                                        }
                                    });
                    return;
                }
                return;
            }
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
            recyclerViewHolder.mTitleView.setText(
                    ((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i)).mListItemTitle);
            recyclerViewHolder.mCheckBoxView.setChecked(
                    ((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i)).mIsChecked);
            if (((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i)).mIsChecked) {
                recyclerViewHolder.mTitleView.setStateDescription(
                        screenTimeoutActivity.mContext.getString(R.string.sec_wifi_checked_tss));
            } else {
                recyclerViewHolder.mTitleView.setStateDescription(
                        screenTimeoutActivity.mContext.getString(R.string.sec_wifi_unchecked_tss));
            }
            boolean z2 = false;
            recyclerViewHolder.mTitleView.setEnabled(
                    screenTimeoutActivity.mItemEnabled
                            && screenTimeoutActivity.mItemEnabledPowerSaving);
            RadioButton radioButton = recyclerViewHolder.mCheckBoxView;
            if (screenTimeoutActivity.mItemEnabled
                    && screenTimeoutActivity.mItemEnabledPowerSaving) {
                z2 = true;
            }
            radioButton.setEnabled(z2);
            viewHolder.itemView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.display.ScreenTimeoutActivity.ScreenTimeoutAdapter.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i2;
                            ScreenTimeoutAdapter screenTimeoutAdapter = ScreenTimeoutAdapter.this;
                            ScreenTimeoutActivity screenTimeoutActivity2 =
                                    ScreenTimeoutActivity.this;
                            if (screenTimeoutActivity2.mItemEnabled
                                    && screenTimeoutActivity2.mItemEnabledPowerSaving) {
                                RecyclerView.ViewHolder viewHolder2 = viewHolder;
                                int i3 = i;
                                int i4 = 0;
                                while (true) {
                                    i2 = 1;
                                    if (i4 >= screenTimeoutAdapter.mEntries.length) {
                                        break;
                                    }
                                    if (i4 == i3) {
                                        ((ScreenTimeoutList)
                                                                ((ArrayList)
                                                                                screenTimeoutAdapter
                                                                                        .mListItems)
                                                                        .get(i4))
                                                        .mIsChecked =
                                                true;
                                        ((RecyclerViewHolder) viewHolder2)
                                                .mCheckBoxView.setChecked(
                                                        ((ScreenTimeoutList)
                                                                        ((ArrayList)
                                                                                        screenTimeoutAdapter
                                                                                                .mListItems)
                                                                                .get(i4))
                                                                .mIsChecked);
                                        screenTimeoutAdapter.mCurrentTimeout =
                                                Long.parseLong(
                                                        ((ScreenTimeoutList)
                                                                        ((ArrayList)
                                                                                        screenTimeoutAdapter
                                                                                                .mListItems)
                                                                                .get(i4))
                                                                .mListItemValue);
                                    } else {
                                        ((ScreenTimeoutList)
                                                                ((ArrayList)
                                                                                screenTimeoutAdapter
                                                                                        .mListItems)
                                                                        .get(i4))
                                                        .mIsChecked =
                                                false;
                                    }
                                    screenTimeoutAdapter.notifyItemChanged(i4);
                                    i4++;
                                }
                                int parseInt =
                                        Integer.parseInt(
                                                ((ScreenTimeoutList)
                                                                ((ArrayList)
                                                                                ScreenTimeoutAdapter
                                                                                        .this
                                                                                        .mListItems)
                                                                        .get(i))
                                                        .mListItemValue);
                                boolean isDesktopDualMode =
                                        Utils.isDesktopDualMode(
                                                ScreenTimeoutActivity.this.mContext);
                                ScreenTimeoutActivity screenTimeoutActivity3 =
                                        ScreenTimeoutActivity.this;
                                if (screenTimeoutActivity3.mDexMode) {
                                    Utils.setDeXSettings(
                                            screenTimeoutActivity3.getContentResolver(),
                                            "timeout_dex",
                                            ((ScreenTimeoutList)
                                                            ((ArrayList)
                                                                            ScreenTimeoutAdapter
                                                                                    .this
                                                                                    .mListItems)
                                                                    .get(i))
                                                    .mListItemValue);
                                    if (!isDesktopDualMode
                                            || parseInt
                                                    >= Settings.System.getLong(
                                                            ScreenTimeoutActivity.this.mContext
                                                                    .getContentResolver(),
                                                            "screen_off_timeout",
                                                            30000L)) {
                                        return;
                                    }
                                    Toast.makeText(
                                                    ScreenTimeoutActivity.this.mContext,
                                                    R.string
                                                            .screen_timeout_toast_message_for_both_display,
                                                    0)
                                            .show();
                                    return;
                                }
                                Settings.System.putInt(
                                        screenTimeoutActivity3.getContentResolver(),
                                        "screen_off_timeout",
                                        parseInt);
                                ScreenTimeoutActivity.this.getClass();
                                switch (parseInt) {
                                    case 30000:
                                        break;
                                    case 60000:
                                        i2 = 2;
                                        break;
                                    case 120000:
                                        i2 = 3;
                                        break;
                                    case 300000:
                                        i2 = 4;
                                        break;
                                    case 600000:
                                        i2 = 5;
                                        break;
                                    default:
                                        i2 = 0;
                                        break;
                                }
                                LoggingHelper.insertEventLogging(4212, 4212, i2);
                                if (!isDesktopDualMode
                                        || parseInt
                                                <= Long.valueOf(
                                                                Utils.getStringFromDeXSettings(
                                                                        ScreenTimeoutActivity.this
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "timeout_dex",
                                                                        Integer.toString(600000)))
                                                        .longValue()) {
                                    return;
                                }
                                Toast.makeText(
                                                ScreenTimeoutActivity.this.mContext,
                                                R.string
                                                        .screen_timeout_toast_message_for_both_display,
                                                0)
                                        .show();
                            }
                        }
                    });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (this.mHasFooterView && i == 2) {
                View m =
                        MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                                viewGroup,
                                R.layout.admin_disabled_other_options_footer,
                                viewGroup,
                                false);
                RecyclerFooterViewHolder recyclerFooterViewHolder = new RecyclerFooterViewHolder(m);
                recyclerFooterViewHolder.mFooterView = m;
                return recyclerFooterViewHolder;
            }
            if (i != 1) {
                return null;
            }
            View m2 =
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.sec_screen_timeout_list_item, viewGroup, false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(m2);
            recyclerViewHolder.mTitleView = (TextView) m2.findViewById(R.id.timeout_title);
            recyclerViewHolder.mCheckBoxView = (RadioButton) m2.findViewById(R.id.checkbox);
            return recyclerViewHolder;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VerticalLineItemDecoration extends RecyclerView.ItemDecoration {
        public final Drawable mDivider;
        public boolean mIsVisibleFooterViewDivider;

        public VerticalLineItemDecoration(Context context) {
            int[] iArr = {android.R.attr.listDivider};
            this.mIsVisibleFooterViewDivider = false;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(iArr);
            this.mDivider = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(
                Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (recyclerView.getAdapter() == null
                    || RecyclerView.getChildAdapterPosition(view)
                            == recyclerView.getAdapter().getItemCount() - 1) {
                return;
            }
            rect.bottom = this.mDivider.getIntrinsicHeight();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
            int childCount = recyclerView.getChildCount();
            Resources resources = ScreenTimeoutActivity.this.mContext.getResources();
            int dimensionPixelSize =
                    resources.getDimensionPixelSize(
                                    R.dimen
                                            .sec_widget_list_checkbox_width_for_divider_padding_inset)
                            + resources.getDimensionPixelSize(
                                    R.dimen.sec_widget_list_checkbox_width_for_divider_inset);
            for (int i = 0; i < childCount - 1; i++) {
                View childAt = recyclerView.getChildAt(i);
                RecyclerView.LayoutParams layoutParams =
                        (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int paddingLeft = recyclerView.getPaddingLeft();
                int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
                int bottom =
                        childAt.getBottom()
                                + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                int intrinsicHeight = this.mDivider.getIntrinsicHeight() + bottom;
                if (!this.mIsVisibleFooterViewDivider || i != childCount - 2) {
                    if (recyclerView.getLayoutDirection() == 0) {
                        paddingLeft = recyclerView.getPaddingLeft() + dimensionPixelSize;
                        width = recyclerView.getWidth() - recyclerView.getPaddingRight();
                    } else {
                        paddingLeft = recyclerView.getPaddingLeft();
                        width =
                                (recyclerView.getWidth() - recyclerView.getPaddingRight())
                                        - dimensionPixelSize;
                    }
                }
                this.mDivider.setBounds(paddingLeft, bottom, width, intrinsicHeight);
                this.mDivider.draw(canvas);
            }
        }
    }

    public final void changeLowBatteryUI() {
        Intent registerReceiver =
                this.mContext.registerReceiver(
                        null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        int intExtra = registerReceiver.getIntExtra("level", -1);
        int intExtra2 = registerReceiver.getIntExtra("scale", -1);
        boolean z = registerReceiver.getIntExtra(IMSParameter.CALL.STATUS, -1) == 2;
        int i = 100;
        if (intExtra >= 0 && intExtra2 > 0) {
            i = (intExtra * 100) / intExtra2;
        }
        if (this.mScreenTimeoutList != null) {
            if (i > 5 || z) {
                this.mItemEnabled = true;
                return;
            }
            this.mItemEnabled = false;
            Log.i("ScreenTimeoutActivity", "Low battery: " + i + " - isCharging: " + z);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.screen_timeout;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ScreenTimeoutActivity";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        if (this.mContext == null) {
            this.mContext = getContext();
        }
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return 4212;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_screen_timeout_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        this.mDexMode = getIntent().getBooleanExtra("Screen_timeout_dex_mode", false);
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if ((devicePolicyManager != null ? devicePolicyManager.getMaximumTimeToLock(null) : 0L)
                > 0) {
            this.mHasFooterView = true;
        }
        changeLowBatteryUI();
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("screen_timeout_layout");
        if (layoutPreference != null) {
            LinearLayout linearLayout =
                    (LinearLayout)
                            layoutPreference.mRootView.findViewById(R.id.screen_timeout_container);
            this.mScreenTimeoutView = linearLayout;
            if (linearLayout != null) {
                linearLayout.semSetRoundedCorners(15);
                this.mScreenTimeoutView.semSetRoundedCornerColor(
                        15,
                        this.mContext
                                .getResources()
                                .getColor(R.color.sec_widget_round_and_bgcolor));
                this.mScreenTimeoutList =
                        (RecyclerView)
                                this.mScreenTimeoutView.findViewById(R.id.screen_timeout_list);
                this.mScreenTimeoutList.setLayoutManager(new LinearLayoutManager(1));
                this.mScreenTimeoutList.setHasFixedSize(true);
                VerticalLineItemDecoration verticalLineItemDecoration =
                        new VerticalLineItemDecoration(this.mContext);
                verticalLineItemDecoration.mIsVisibleFooterViewDivider = this.mHasFooterView;
                this.mScreenTimeoutList.addItemDecoration(verticalLineItemDecoration);
                this.mScreenTimeoutList.semSetRoundedCorners(15);
                this.mScreenTimeoutList.semSetRoundedCornerColor(
                        15,
                        this.mContext
                                .getResources()
                                .getColor(R.color.sec_widget_round_and_bgcolor));
                this.mScreenTimeoutList.seslSetFillBottomEnabled(true);
                this.mScreenTimeoutList.setItemAnimator(null);
            }
        }
        this.mPowerSavingDescription =
                (LayoutPreference) findPreference("power_saving_description");
        this.mPowerSavingDescriptionInsetCategory =
                (SecInsetCategoryPreference) findPreference("power_saving_description_inset");
        LayoutPreference layoutPreference2 = this.mPowerSavingDescription;
        if (layoutPreference2 != null) {
            this.mLayoutPowerSavingDescription =
                    layoutPreference2.mRootView.findViewById(
                            R.id.sec_power_saving_layout_dark_mode);
            TextView textView =
                    (TextView)
                            this.mPowerSavingDescription.mRootView.findViewById(
                                    R.id.sec_dark_mode_power_saving_text);
            if (textView != null) {
                String string =
                        this.mContext.getString(
                                R.string.sec_settings_you_can_change_this_in_power_saving,
                                DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                                DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
                int indexOf = TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.SMS_WRITE_UICC);
                int indexOf2 =
                        TextUtils.indexOf(string, DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF);
                if (indexOf == -1 || indexOf2 == -1) {
                    return;
                }
                int i = indexOf2 + 2;
                if (indexOf <= i) {
                    i = indexOf;
                    indexOf = i;
                }
                String replaceAll =
                        string.replaceAll(DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, ApnSettings.MVNO_NONE)
                                .replaceAll(
                                        DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF,
                                        ApnSettings.MVNO_NONE);
                int i2 = indexOf - 4;
                if (i2 > replaceAll.length()) {
                    textView.setText(replaceAll);
                    return;
                }
                ClickableSpan clickableSpan =
                        new ClickableSpan() { // from class:
                                              // com.samsung.android.settings.display.ScreenTimeoutActivity.3
                            @Override // android.text.style.ClickableSpan
                            public final void onClick(View view) {
                                view.playSoundEffect(0);
                                Uri parse = Uri.parse("content://com.samsung.android.sm.dcapi");
                                Bundle bundle2 = new Bundle();
                                ScreenTimeoutActivity screenTimeoutActivity =
                                        ScreenTimeoutActivity.this;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        ScreenTimeoutActivity.SEARCH_INDEX_DATA_PROVIDER;
                                Bundle call =
                                        screenTimeoutActivity
                                                .getContentResolver()
                                                .call(
                                                        parse,
                                                        "psm_start_power_saving_activity",
                                                        (String) null,
                                                        bundle2);
                                boolean z = call.getBoolean("result");
                                boolean z2 = call.getBoolean("changeable");
                                if (!z) {
                                    Log.d("ScreenTimeoutActivity", "API call has failed");
                                }
                                if (z2) {
                                    Log.d("ScreenTimeoutActivity", "Enter in power saving");
                                }
                            }
                        };
                SpannableStringBuilder spannableStringBuilder =
                        new SpannableStringBuilder(replaceAll);
                spannableStringBuilder.setSpan(clickableSpan, i, i2, 0);
                spannableStringBuilder.setSpan(
                        new ForegroundColorSpan(
                                this.mContext.getColor(
                                        R.color.sec_tips_description_link_text_color)),
                        i,
                        i2,
                        0);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setText(spannableStringBuilder);
            }
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mScreenTimeoutObserver != null) {
            getContentResolver().unregisterContentObserver(this.mScreenTimeoutObserver);
            this.mScreenTimeoutObserver = null;
        }
        if (this.mPowerSavingObserver != null) {
            getContentResolver().unregisterContentObserver(this.mPowerSavingObserver);
            this.mPowerSavingObserver = null;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.samsung.android.settings.display.ScreenTimeoutActivity$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.samsung.android.settings.display.ScreenTimeoutActivity$1] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService("device_policy");
        if (devicePolicyManager != null) {
            long maximumTimeToLock =
                    devicePolicyManager.getMaximumTimeToLock(null, UserHandle.myUserId());
            long j = this.mMaxTimeout;
            if (j != maximumTimeToLock) {
                if (j != -1) {
                    finish();
                }
                this.mMaxTimeout = maximumTimeToLock;
            }
        }
        updateRelatedGUIByPowerSaving();
        setScreenTimeoutAdapter(true);
        try {
            if (this.mScreenTimeoutObserver == null) {
                final int i = 0;
                this.mScreenTimeoutObserver =
                        new ContentObserver(
                                this,
                                new Handler()) { // from class:
                                                 // com.samsung.android.settings.display.ScreenTimeoutActivity.1
                            public final /* synthetic */ ScreenTimeoutActivity this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z, Uri uri) {
                                switch (i) {
                                    case 0:
                                        ScreenTimeoutActivity screenTimeoutActivity = this.this$0;
                                        BaseSearchIndexProvider baseSearchIndexProvider =
                                                ScreenTimeoutActivity.SEARCH_INDEX_DATA_PROVIDER;
                                        screenTimeoutActivity.setScreenTimeoutAdapter(false);
                                        break;
                                    default:
                                        this.this$0.updateRelatedGUIByPowerSaving();
                                        this.this$0.setScreenTimeoutAdapter(true);
                                        break;
                                }
                            }
                        };
                getContentResolver()
                        .registerContentObserver(
                                Settings.System.getUriFor("screen_off_timeout"),
                                false,
                                this.mScreenTimeoutObserver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (this.mPowerSavingObserver == null) {
                final int i2 = 1;
                this.mPowerSavingObserver =
                        new ContentObserver(
                                this,
                                new Handler()) { // from class:
                                                 // com.samsung.android.settings.display.ScreenTimeoutActivity.1
                            public final /* synthetic */ ScreenTimeoutActivity this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z, Uri uri) {
                                switch (i2) {
                                    case 0:
                                        ScreenTimeoutActivity screenTimeoutActivity = this.this$0;
                                        BaseSearchIndexProvider baseSearchIndexProvider =
                                                ScreenTimeoutActivity.SEARCH_INDEX_DATA_PROVIDER;
                                        screenTimeoutActivity.setScreenTimeoutAdapter(false);
                                        break;
                                    default:
                                        this.this$0.updateRelatedGUIByPowerSaving();
                                        this.this$0.setScreenTimeoutAdapter(true);
                                        break;
                                }
                            }
                        };
                getContentResolver()
                        .registerContentObserver(
                                Settings.Global.getUriFor("pms_settings_screen_time_out_enabled"),
                                false,
                                this.mPowerSavingObserver);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        this.mMaxTimeout = -1L;
        changeLowBatteryUI();
        super.onStart();
    }

    public final void setScreenTimeoutAdapter(boolean z) {
        CharSequence[] screenTimeoutEntryandValue;
        CharSequence[] screenTimeoutEntryandValue2;
        long longValue =
                this.mDexMode
                        ? Long.valueOf(
                                        Utils.getStringFromDeXSettings(
                                                this.mContext.getContentResolver(),
                                                "timeout_dex",
                                                Integer.toString(600000)))
                                .longValue()
                        : Settings.System.getLong(
                                this.mContext.getContentResolver(), "screen_off_timeout", 30000L);
        if (z || longValue != this.mAdapter.mCurrentTimeout) {
            if (this.mDexMode) {
                screenTimeoutEntryandValue =
                        SecDisplayUtils.getDexScreenTimeoutEntryandValue(
                                1, longValue, this.mContext);
                screenTimeoutEntryandValue2 =
                        SecDisplayUtils.getDexScreenTimeoutEntryandValue(
                                2, longValue, this.mContext);
            } else {
                screenTimeoutEntryandValue =
                        SecDisplayUtils.getScreenTimeoutEntryandValue(
                                this.mContext, Long.valueOf(longValue), 1);
                screenTimeoutEntryandValue2 =
                        SecDisplayUtils.getScreenTimeoutEntryandValue(
                                this.mContext, Long.valueOf(longValue), 2);
            }
            ScreenTimeoutAdapter screenTimeoutAdapter =
                    new ScreenTimeoutAdapter(
                            this.mHasFooterView,
                            screenTimeoutEntryandValue,
                            screenTimeoutEntryandValue2,
                            longValue);
            this.mAdapter = screenTimeoutAdapter;
            this.mScreenTimeoutList.setAdapter(screenTimeoutAdapter);
            this.mScreenTimeoutList.setSelected(true);
            this.mScreenTimeoutList.setClickable(true);
        }
    }

    public final void updateRelatedGUIByPowerSaving() {
        if (Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "pms_settings_screen_time_out_enabled",
                        0)
                != 0) {
            this.mItemEnabledPowerSaving = false;
            View view = this.mLayoutPowerSavingDescription;
            if (view != null) {
                view.setVisibility(0);
            }
            SecInsetCategoryPreference secInsetCategoryPreference =
                    this.mPowerSavingDescriptionInsetCategory;
            if (secInsetCategoryPreference != null) {
                secInsetCategoryPreference.setVisible(true);
                return;
            }
            return;
        }
        this.mItemEnabledPowerSaving = true;
        View view2 = this.mLayoutPowerSavingDescription;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        SecInsetCategoryPreference secInsetCategoryPreference2 =
                this.mPowerSavingDescriptionInsetCategory;
        if (secInsetCategoryPreference2 != null) {
            secInsetCategoryPreference2.setVisible(false);
        }
    }
}
