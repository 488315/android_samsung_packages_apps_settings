package com.samsung.android.settings.security;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.password.ChooseLockSettingsHelper;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LockKnoxProfileAfterTimeoutActivity extends SettingsActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class LockAfterTimeoutKnoxProfileFragment extends InstrumentedPreferenceFragment {
        public FragmentActivity mActivity;
        public Context mContext;
        public DevicePolicyManager mDevicePolicyManager;
        public RecyclerView mListView;
        public int mProfileChallengeUserId;
        public boolean mPasswordConfirmed = false;
        public boolean mWaitingForConfirmation = false;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class RadioAdapter extends RecyclerView.Adapter {
            public final boolean mChecked;
            public final Context mContext;
            public long mCurrentTimeout;
            public final CharSequence[] mEntries;
            public final List mListItems = new ArrayList();
            public final CharSequence[] mValues;

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

            public RadioAdapter(
                    Context context,
                    CharSequence[] charSequenceArr,
                    CharSequence[] charSequenceArr2,
                    long j) {
                int i = 0;
                this.mChecked = false;
                this.mContext = context;
                this.mEntries = charSequenceArr;
                this.mValues = charSequenceArr2;
                this.mCurrentTimeout = j;
                if (charSequenceArr == null) {
                    Log.e(
                            "KKG::LockKnoxProfileAfterTimeoutActivity",
                            "addScreenTimeoutListView failed");
                    return;
                }
                while (true) {
                    CharSequence[] charSequenceArr3 = this.mEntries;
                    if (i >= charSequenceArr3.length) {
                        break;
                    }
                    CharSequence charSequence = charSequenceArr3[i];
                    CharSequence charSequence2 = this.mValues[i];
                    ScreenTimeoutList screenTimeoutList = new ScreenTimeoutList();
                    screenTimeoutList.mListItemTitle = charSequence.toString();
                    screenTimeoutList.mListItemValue = charSequence2.toString();
                    if (charSequence2.equals(String.valueOf(this.mCurrentTimeout))) {
                        screenTimeoutList.mIsChecked = true;
                        this.mChecked = true;
                    }
                    this.mListItems.add(screenTimeoutList);
                    notifyDataSetChanged();
                    i++;
                }
                if (this.mChecked) {
                    return;
                }
                ((ScreenTimeoutList) this.mListItems.get(this.mListItems.size() - 1)).mIsChecked =
                        true;
                this.mChecked = true;
                notifyDataSetChanged();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public final int getItemCount() {
                return ((ArrayList) this.mListItems).size();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public final void onBindViewHolder(
                    final RecyclerView.ViewHolder viewHolder, final int i) {
                if (viewHolder instanceof RecyclerViewHolder) {
                    RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
                    recyclerViewHolder.mTitleView.setText(
                            ((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i))
                                    .mListItemTitle);
                    recyclerViewHolder.mCheckBoxView.setChecked(
                            ((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i)).mIsChecked);
                    if (((ScreenTimeoutList) ((ArrayList) this.mListItems).get(i)).mIsChecked) {
                        recyclerViewHolder.mTitleView.setStateDescription(
                                this.mContext.getString(R.string.sec_wifi_checked_tss));
                    } else {
                        recyclerViewHolder.mTitleView.setStateDescription(
                                this.mContext.getString(R.string.sec_wifi_unchecked_tss));
                    }
                    viewHolder.itemView.setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.security.LockKnoxProfileAfterTimeoutActivity.LockAfterTimeoutKnoxProfileFragment.RadioAdapter.1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    RadioAdapter radioAdapter = RadioAdapter.this;
                                    RecyclerView.ViewHolder viewHolder2 = viewHolder;
                                    int i2 = i;
                                    for (int i3 = 0; i3 < radioAdapter.mEntries.length; i3++) {
                                        if (i3 == i2) {
                                            ((ScreenTimeoutList)
                                                                    ((ArrayList)
                                                                                    radioAdapter
                                                                                            .mListItems)
                                                                            .get(i3))
                                                            .mIsChecked =
                                                    true;
                                            ((RecyclerViewHolder) viewHolder2)
                                                    .mCheckBoxView.setChecked(
                                                            ((ScreenTimeoutList)
                                                                            ((ArrayList)
                                                                                            radioAdapter
                                                                                                    .mListItems)
                                                                                    .get(i3))
                                                                    .mIsChecked);
                                            radioAdapter.mCurrentTimeout =
                                                    Long.parseLong(
                                                            ((ScreenTimeoutList)
                                                                            ((ArrayList)
                                                                                            radioAdapter
                                                                                                    .mListItems)
                                                                                    .get(i3))
                                                                    .mListItemValue);
                                        } else {
                                            ((ScreenTimeoutList)
                                                                    ((ArrayList)
                                                                                    radioAdapter
                                                                                            .mListItems)
                                                                            .get(i3))
                                                            .mIsChecked =
                                                    false;
                                        }
                                        radioAdapter.notifyItemChanged(i3);
                                    }
                                    int parseInt =
                                            Integer.parseInt(
                                                    ((ScreenTimeoutList)
                                                                    ((ArrayList)
                                                                                    RadioAdapter
                                                                                            .this
                                                                                            .mListItems)
                                                                            .get(i))
                                                            .mListItemValue);
                                    Settings.Secure.putIntForUser(
                                            RadioAdapter.this.mContext.getContentResolver(),
                                            "knox_screen_off_timeout",
                                            parseInt,
                                            LockAfterTimeoutKnoxProfileFragment.this
                                                    .mProfileChallengeUserId);
                                    Log.e(
                                            "KKG::LockKnoxProfileAfterTimeoutActivity",
                                            "KNOX_SCREEN_OFF_TIMEOUT timeout value = " + parseInt);
                                    SemPersonaManager.refreshLockTimer(
                                            LockAfterTimeoutKnoxProfileFragment.this
                                                    .mProfileChallengeUserId);
                                    LockAfterTimeoutKnoxProfileFragment.this.mActivity.finish();
                                }
                            });
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View m =
                        MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                                viewGroup,
                                R.layout.sec_lock_after_timeout_list_item,
                                viewGroup,
                                false);
                RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(m);
                recyclerViewHolder.mTitleView = (TextView) m.findViewById(R.id.timeout_title);
                recyclerViewHolder.mCheckBoxView = (RadioButton) m.findViewById(R.id.checkbox);
                return recyclerViewHolder;
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class VerticalLineItemDecoration extends RecyclerView.ItemDecoration {
            public final Drawable mDivider;

            public VerticalLineItemDecoration(Context context) {
                TypedArray obtainStyledAttributes =
                        context.obtainStyledAttributes(new int[] {android.R.attr.listDivider});
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
                Resources resources =
                        LockAfterTimeoutKnoxProfileFragment.this.mContext.getResources();
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
            return 0;
        }

        @Override // androidx.fragment.app.Fragment
        public final void onActivityResult(int i, int i2, Intent intent) {
            super.onActivityResult(i, i2, intent);
            if (i == 100) {
                this.mWaitingForConfirmation = false;
                if (i2 != -1) {
                    Log.secD("KKG::LockKnoxProfileAfterTimeoutActivity", "Lock confirm failed");
                    this.mActivity.finish();
                } else {
                    Log.secD("KKG::LockKnoxProfileAfterTimeoutActivity", "Lock confirmed");
                    this.mPasswordConfirmed = true;
                    this.mListView.setVisibility(0);
                }
            }
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mContext = getContext();
            this.mActivity = getActivity();
        }

        @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            View inflate =
                    layoutInflater.inflate(
                            R.layout.sec_lock_after_knox_profile_timeout, viewGroup, false);
            RecyclerView recyclerView =
                    (RecyclerView) inflate.findViewById(R.id.lock_after_timeout_list);
            this.mListView = recyclerView;
            if (recyclerView != null) {
                recyclerView.semSetRoundedCorners(15);
                this.mListView.semSetRoundedCornerColor(
                        15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(1);
                this.mListView.addItemDecoration(new VerticalLineItemDecoration(this.mContext));
                this.mListView.setLayoutManager(linearLayoutManager);
                this.mListView.setHasFixedSize(true);
                this.mListView.seslSetFillBottomEnabled(true);
                this.mListView.setItemAnimator(null);
            }
            int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
            NestedScrollView nestedScrollView =
                    (NestedScrollView) inflate.findViewById(R.id.nested_scroll_view);
            nestedScrollView.setPaddingRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            nestedScrollView.semSetRoundedCorners(15);
            nestedScrollView.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            nestedScrollView.semSetRoundedCornerOffset(listHorizontalPadding);
            return inflate;
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            super.onResume();
            long longForUser =
                    Settings.Secure.getLongForUser(
                            this.mContext.getContentResolver(),
                            "knox_screen_off_timeout",
                            0L,
                            this.mProfileChallengeUserId);
            DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
            long maximumTimeToLock =
                    devicePolicyManager != null
                            ? devicePolicyManager.getMaximumTimeToLock(
                                    null, this.mProfileChallengeUserId)
                            : 0L;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            LockKnoxProfileAfterTimeoutActivity.setScreenTimeoutAdapter(
                    this.mContext, longForUser, maximumTimeToLock, arrayList, arrayList2);
            this.mListView.setAdapter(
                    new RadioAdapter(
                            this.mContext,
                            (CharSequence[]) arrayList.toArray(new CharSequence[0]),
                            (CharSequence[]) arrayList2.toArray(new CharSequence[0]),
                            longForUser));
            this.mListView.setSelected(true);
            this.mListView.setClickable(true);
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean("password_already_confirmed", this.mPasswordConfirmed);
            bundle.putBoolean("waiting_for_confirmation", this.mWaitingForConfirmation);
        }

        @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            this.mDevicePolicyManager =
                    (DevicePolicyManager) this.mContext.getSystemService("device_policy");
            this.mProfileChallengeUserId =
                    this.mActivity
                            .getIntent()
                            .getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
            if (bundle != null) {
                this.mPasswordConfirmed = bundle.getBoolean("password_already_confirmed");
                this.mWaitingForConfirmation = bundle.getBoolean("waiting_for_confirmation");
            }
            if (this.mPasswordConfirmed || this.mWaitingForConfirmation) {
                return;
            }
            ChooseLockSettingsHelper.Builder builder =
                    new ChooseLockSettingsHelper.Builder(getActivity(), this);
            builder.mRequestCode = 100;
            builder.mTitle =
                    getActivity().getString(R.string.sec_unlock_set_unlock_launch_picker_title);
            builder.mReturnCredentials = true;
            builder.mUserId = this.mProfileChallengeUserId;
            builder.mKnoxWorkProfileSecurity = true;
            if (builder.show()) {
                this.mWaitingForConfirmation = true;
                Log.secD(
                        "KKG::LockKnoxProfileAfterTimeoutActivity", "Need to confirm Current Lock");
            }
            this.mListView.setVisibility(4);
        }
    }

    public static void addCurrentTimeoutIfNeeded(
            Context context,
            long j,
            long j2,
            CharSequence[] charSequenceArr,
            CharSequence[] charSequenceArr2,
            ArrayList arrayList,
            ArrayList arrayList2) {
        boolean z = false;
        for (int i = 0; i < charSequenceArr2.length; i++) {
            long parseLong = Long.parseLong(charSequenceArr2[i].toString());
            if (!z && j < parseLong) {
                arrayList.add(
                        context.getString(
                                R.string.text_security_timeout_msg_b2c,
                                getTimeoutNewEntry(context, j)));
                arrayList2.add(Long.toString(j));
                z = true;
            }
            arrayList.add(charSequenceArr[i]);
            arrayList2.add(charSequenceArr2[i]);
        }
        if (j2 <= 0 || z) {
            return;
        }
        arrayList.add(
                context.getString(
                        R.string.text_security_timeout_msg_b2c, getTimeoutNewEntry(context, j)));
        arrayList2.add(Long.toString(j));
    }

    public static String getTimeoutNewEntry(Context context, long j) {
        String str;
        long j2 = j / 1000;
        long j3 = j2 / 60;
        long j4 = j2 % 60;
        if (j3 > 0) {
            int i = (int) j3;
            str =
                    String.format(
                            context.getResources()
                                    .getQuantityString(R.plurals.lock_timeout_minutes, i),
                            Integer.valueOf(i));
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        if (j3 > 0 && j4 > 0) {
            str = str.concat(" ");
        }
        if (j4 > 0) {
            StringBuilder m =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
            int i2 = (int) j4;
            m.append(
                    String.format(
                            context.getResources()
                                    .getQuantityString(R.plurals.lock_timeout_seconds, i2),
                            Integer.valueOf(i2)));
            str = m.toString();
        }
        Log.secD("KKG::LockKnoxProfileAfterTimeoutActivity", "getTimeoutNewEntry : " + str);
        return str;
    }

    public static void setScreenTimeoutAdapter(
            Context context, long j, long j2, ArrayList arrayList, ArrayList arrayList2) {
        CharSequence[] charSequenceArr;
        CharSequence[] charSequenceArr2;
        String[] stringArray =
                context.getResources()
                        .getStringArray(R.array.lock_knox_profile_after_timeout_values);
        CharSequence[] charSequenceArr3 = new CharSequence[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            if (Integer.parseInt(stringArray[i].toString()) == 0) {
                charSequenceArr3[i] = context.getString(R.string.when_screen_turns_off);
            } else if (Integer.parseInt(stringArray[i].toString()) == -1) {
                charSequenceArr3[i] = context.getString(R.string.when_device_restarts);
            } else {
                int parseInt = (int) ((Integer.parseInt(stringArray[i].toString()) / 1000) / 60);
                charSequenceArr3[i] =
                        String.format(
                                context.getString(R.string.text_security_timeout_msg_b2c),
                                String.format(
                                        context.getResources()
                                                .getQuantityString(
                                                        R.plurals.lock_timeout_minutes, parseInt),
                                        Integer.valueOf(parseInt)));
            }
        }
        if (j2 > 0) {
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (int i2 = 0; i2 < stringArray.length; i2++) {
                long parseLong = Long.parseLong(stringArray[i2].toString());
                if (parseLong > 0 && parseLong < j2) {
                    arrayList3.add(charSequenceArr3[i2]);
                    arrayList4.add(stringArray[i2]);
                }
            }
            arrayList3.add(
                    context.getString(R.string.lock_timeout_max, getTimeoutNewEntry(context, j2)));
            arrayList4.add(Long.toString(j2));
            charSequenceArr2 = (CharSequence[]) arrayList3.toArray(new CharSequence[0]);
            charSequenceArr = (CharSequence[]) arrayList4.toArray(new CharSequence[0]);
        } else {
            charSequenceArr = stringArray;
            charSequenceArr2 = charSequenceArr3;
        }
        int length = charSequenceArr.length;
        int i3 = 0;
        while (true) {
            if (i3 < length) {
                if (Long.parseLong(charSequenceArr[i3].toString()) == j) {
                    break;
                } else {
                    i3++;
                }
            } else if (j2 <= 0) {
                ArrayList arrayList5 = new ArrayList();
                ArrayList arrayList6 = new ArrayList();
                addCurrentTimeoutIfNeeded(
                        context, j, j2, charSequenceArr2, charSequenceArr, arrayList5, arrayList6);
                charSequenceArr2 = (CharSequence[]) arrayList5.toArray(new CharSequence[0]);
                charSequenceArr = (CharSequence[]) arrayList6.toArray(new CharSequence[0]);
            } else if (j > 0 && j2 > j) {
                ArrayList arrayList7 = new ArrayList();
                ArrayList arrayList8 = new ArrayList();
                addCurrentTimeoutIfNeeded(
                        context, j, j2, charSequenceArr2, charSequenceArr, arrayList7, arrayList8);
                charSequenceArr2 = (CharSequence[]) arrayList7.toArray(new CharSequence[0]);
                charSequenceArr = (CharSequence[]) arrayList8.toArray(new CharSequence[0]);
            }
        }
        arrayList.addAll(Arrays.asList(charSequenceArr2));
        arrayList2.addAll(Arrays.asList(charSequenceArr));
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(
                ":settings:show_fragment", LockAfterTimeoutKnoxProfileFragment.class.getName());
        intent.putExtra(":android:no_headers", true);
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return LockAfterTimeoutKnoxProfileFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        findViewById(R.id.content_parent).setFitsSystemWindows(false);
        View findViewById = findViewById(R.id.round_corner);
        if (findViewById != null) {
            int listHorizontalPadding = Utils.getListHorizontalPadding(getApplicationContext());
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    @Override // com.android.settings.SettingsActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        if (KnoxUtils.isAvailableWithMultiWindowForKnox(
                this,
                getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId()))) {
            return;
        }
        Toast.makeText(this, getString(R.string.lock_screen_doesnt_support_multi_window_text), 0)
                .show();
        finish();
    }
}
