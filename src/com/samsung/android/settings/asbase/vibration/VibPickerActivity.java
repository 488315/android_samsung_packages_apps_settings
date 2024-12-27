package com.samsung.android.settings.asbase.vibration;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.utils.VibUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class VibPickerActivity extends Fragment {
    public Context mContext;
    public int mDefaultPatternSepIndex;
    public FrameLayout mIntensityContainer;
    public FrameLayout mListContainer;
    public VibPickerQueryHelper mQueryHelper;
    public RecyclerView mRecyclerView;
    public ContentResolver mResolver;
    public Ringtone mRingtone;
    public Bundle mSavedInstanceState;
    public Handler mScrollHandler;
    public VibPickerItem mSelectedItemOtherSim;
    public String mSepIndexDbName;
    public Uri mSoundUri;
    public String mSyncWithHapticDbName;
    public VibPickerHelper mVibHelper;
    public SecVibPickerIntensitySettings mVibIntensitySeekBar;
    public Vibrator mVibrator;
    public static final Handler mHandler = new Handler();
    public static final HashMap mCategoryLookupTable =
            new HashMap<
                    Integer,
                    String>() { // from class:
                                // com.samsung.android.settings.asbase.vibration.VibPickerActivity.1
                {
                    put(0, "None");
                    put(1, "Default");
                    put(2, "Custom");
                    put(3, "Galaxy");
                }
            };
    public boolean mHasHapticChannels = false;
    public int mStaticItemCount = 0;
    public int mCustomPatternCount = 0;
    public int mCheckedPos = -1;
    public int mSyncBasePos = 1;
    public VibPickerListAdapter mVibPickerListAdapter = null;
    public final ArrayList mAllItemList = new ArrayList();
    public final ArrayList mRealItemList = new ArrayList();
    public final AnonymousClass2 mItemClickListener = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibPickerActivity$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DeleteAsyncTask extends AsyncTask {
        public DeleteAsyncTask() {}

        /* JADX WARN: Removed duplicated region for block: B:8:0x0045  */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object doInBackground(java.lang.Object[] r10) {
            /*
                r9 = this;
                java.lang.Integer[] r10 = (java.lang.Integer[]) r10
                com.samsung.android.settings.asbase.vibration.VibPickerActivity r9 = com.samsung.android.settings.asbase.vibration.VibPickerActivity.this
                r0 = 0
                r1 = r10[r0]
                int r1 = r1.intValue()
                com.samsung.android.settings.asbase.vibration.VibPickerQueryHelper r2 = r9.mQueryHelper
                r2.getClass()
                android.content.ContentResolver r3 = r2.mResolver     // Catch: java.lang.Exception -> L33
                android.net.Uri r4 = r2.mUri     // Catch: java.lang.Exception -> L33
                java.lang.String r6 = "is_custom=?"
                java.lang.String r2 = "0"
                java.lang.String[] r7 = new java.lang.String[]{r2}     // Catch: java.lang.Exception -> L33
                r8 = 0
                r5 = 0
                android.database.Cursor r2 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Exception -> L33
                if (r2 == 0) goto L36
                int r3 = r2.getCount()     // Catch: java.lang.Throwable -> L29
                goto L37
            L29:
                r3 = move-exception
                r2.close()     // Catch: java.lang.Throwable -> L2e
                goto L32
            L2e:
                r2 = move-exception
                r3.addSuppressed(r2)     // Catch: java.lang.Exception -> L33
            L32:
                throw r3     // Catch: java.lang.Exception -> L33
            L33:
                r2 = move-exception
                r3 = r0
                goto L3e
            L36:
                r3 = r0
            L37:
                if (r2 == 0) goto L41
                r2.close()     // Catch: java.lang.Exception -> L3d
                goto L41
            L3d:
                r2 = move-exception
            L3e:
                r2.printStackTrace()
            L41:
                if (r3 != 0) goto L45
                goto Ld1
            L45:
                com.samsung.android.settings.asbase.vibration.VibPickerQueryHelper r2 = r9.mQueryHelper
                android.content.ContentResolver r4 = r2.mResolver
                android.net.Uri r2 = r2.mUri
                java.lang.String r1 = java.lang.Integer.toString(r1)
                java.lang.String[] r1 = new java.lang.String[]{r1}
                java.lang.String r5 = "vibration_pattern=?"
                int r1 = r4.delete(r2, r5, r1)
                r2 = 1
                if (r1 != r2) goto Ld1
                com.samsung.android.settings.asbase.vibration.VibPickerQueryHelper r1 = r9.mQueryHelper
                r4 = 2
                java.util.List r1 = r1.getPatternList(r4)
                java.util.ArrayList r1 = (java.util.ArrayList) r1
                java.util.Iterator r1 = r1.iterator()
            L6a:
                boolean r4 = r1.hasNext()
                if (r4 == 0) goto Ld1
                java.lang.Object r4 = r1.next()
                android.os.Bundle r4 = (android.os.Bundle) r4
                com.samsung.android.settings.asbase.vibration.VibPickerItem r4 = r9.getPickerItem(r4)
                android.content.ContentValues r5 = new android.content.ContentValues
                r5.<init>()
                int r3 = r3 + r2
                java.lang.Integer r6 = java.lang.Integer.valueOf(r3)
                java.lang.String r7 = "_id"
                r5.put(r7, r6)
                java.lang.String r6 = "vibration_name"
                java.lang.String r7 = r4.mName
                r5.put(r6, r7)
                int r6 = r4.mPattern
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                java.lang.String r7 = "vibration_pattern"
                r5.put(r7, r6)
                int r6 = r4.mVibType
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                java.lang.String r7 = "vibration_type"
                r5.put(r7, r6)
                java.lang.String r6 = "custom_data"
                java.lang.String r7 = r4.mData
                r5.put(r6, r7)
                int r6 = r4.mIsCustom
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                java.lang.String r7 = "is_custom"
                r5.put(r7, r6)
                com.samsung.android.settings.asbase.vibration.VibPickerQueryHelper r6 = r9.mQueryHelper
                android.content.ContentResolver r7 = r6.mResolver
                android.net.Uri r6 = r6.mUri
                int r4 = r4.mId
                java.lang.String r4 = java.lang.Integer.toString(r4)
                java.lang.String[] r4 = new java.lang.String[]{r4}
                java.lang.String r8 = "_id=?"
                r7.update(r6, r5, r8, r4)
                goto L6a
            Ld1:
                r9 = r10[r0]
                return r9
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.asbase.vibration.VibPickerActivity.DeleteAsyncTask.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            VibPickerContainer vibPickerContainer =
                    (VibPickerContainer) VibPickerActivity.this.getActivity();
            boolean z = VibPickerActivity.this.mVibHelper.mIsSecondSim;
            int intValue = ((Integer) obj).intValue();
            if (vibPickerContainer.isMultiSimEnabled()) {
                if (z) {
                    VibPickerActivity vibPickerActivity =
                            (VibPickerActivity)
                                    vibPickerContainer
                                            .getSupportFragmentManager()
                                            .mFragmentStore
                                            .getFragments()
                                            .get(0);
                    RecyclerView.ViewHolder findViewHolderForAdapterPosition =
                            vibPickerActivity.mRecyclerView.findViewHolderForAdapterPosition(
                                    vibPickerActivity.mVibPickerListAdapter.getPositionByIndex(
                                            intValue));
                    if (findViewHolderForAdapterPosition != null) {
                        ((ImageButton)
                                        findViewHolderForAdapterPosition.itemView.findViewById(
                                                R.id.removebutton))
                                .performClick();
                    }
                    ((VibPickerActivity)
                                    vibPickerContainer
                                            .getSupportFragmentManager()
                                            .mFragmentStore
                                            .getFragments()
                                            .get(1))
                            .updateSelectedItemOtherSim();
                    ((VibPickerActivity)
                                    vibPickerContainer
                                            .getSupportFragmentManager()
                                            .mFragmentStore
                                            .getFragments()
                                            .get(0))
                            .updateSelectedItemOtherSim();
                    return;
                }
                VibPickerActivity vibPickerActivity2 =
                        (VibPickerActivity)
                                vibPickerContainer
                                        .getSupportFragmentManager()
                                        .mFragmentStore
                                        .getFragments()
                                        .get(1);
                RecyclerView.ViewHolder findViewHolderForAdapterPosition2 =
                        vibPickerActivity2.mRecyclerView.findViewHolderForAdapterPosition(
                                vibPickerActivity2.mVibPickerListAdapter.getPositionByIndex(
                                        intValue));
                if (findViewHolderForAdapterPosition2 != null) {
                    ((ImageButton)
                                    findViewHolderForAdapterPosition2.itemView.findViewById(
                                            R.id.removebutton))
                            .performClick();
                }
                ((VibPickerActivity)
                                vibPickerContainer
                                        .getSupportFragmentManager()
                                        .mFragmentStore
                                        .getFragments()
                                        .get(0))
                        .updateSelectedItemOtherSim();
                ((VibPickerActivity)
                                vibPickerContainer
                                        .getSupportFragmentManager()
                                        .mFragmentStore
                                        .getFragments()
                                        .get(1))
                        .updateSelectedItemOtherSim();
            }
        }
    }

    public static int getKeyByCategoryTitle(String str) {
        for (Integer num : mCategoryLookupTable.keySet()) {
            if (str.equals(mCategoryLookupTable.get(num))) {
                return num.intValue();
            }
        }
        return -1;
    }

    public final void addPatternList(int i) {
        Iterator it = ((ArrayList) this.mQueryHelper.getPatternList(i)).iterator();
        while (it.hasNext()) {
            VibPickerItem pickerItem = getPickerItem((Bundle) it.next());
            if (pickerItem.mIsCustom == 1) {
                this.mCustomPatternCount++;
            }
            String str = pickerItem.mCategory;
            if (pickerItem.mPattern == 50084) {
                ((ArrayList) this.mAllItemList.get(getKeyByCategoryTitle(str))).add(0, pickerItem);
            } else {
                ((ArrayList) this.mAllItemList.get(getKeyByCategoryTitle(str))).add(pickerItem);
            }
        }
    }

    public final VibPickerItem getPickerItem(Bundle bundle) {
        int i;
        int i2;
        String str;
        int i3 = bundle.getInt("_id");
        String string = bundle.getString("vibration_name");
        int i4 = bundle.getInt("vibration_pattern");
        int i5 = bundle.getInt("vibration_type");
        String string2 = bundle.getString("custom_data");
        int i6 = bundle.getInt("is_custom");
        int i7 = bundle.getInt("position");
        String str2 = i6 == 1 ? "Custom" : "Galaxy";
        if (i4 == 50084) {
            int i8 = 1 - this.mSyncBasePos;
            this.mStaticItemCount++;
            str = "Default";
            i = i8;
            i2 = -1;
        } else {
            i = i7;
            i2 = i3;
            str = str2;
        }
        return new VibPickerItem(i2, i, null, i5, 1, string, str, i4, string2, i6);
    }

    public final int getSepIndex() {
        int i =
                Settings.System.getInt(
                        this.mResolver, this.mSepIndexDbName, this.mDefaultPatternSepIndex);
        if (!VibRune.SUPPORT_SYNC_WITH_HAPTIC
                || Settings.System.getInt(this.mResolver, this.mSyncWithHapticDbName, 0) != 1) {
            return i;
        }
        if (this.mHasHapticChannels) {
            return 50000;
        }
        Settings.System.putInt(this.mResolver, this.mSyncWithHapticDbName, 0);
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:115:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initView$3(android.view.View r22) {
        /*
            Method dump skipped, instructions count: 1257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.vibration.VibPickerActivity.initView$3(android.view.View):void");
    }

    public final boolean isAdaptLandScapeMode(Configuration configuration) {
        return (Utils.isTablet()
                        || getActivity().isInMultiWindowMode()
                        || configuration.smallestScreenWidthDp >= 420
                        || configuration.orientation != 2
                        || this.mVibHelper.mIsFromContact
                        || VibUtils.isSupportDcHaptic(this.mContext))
                ? false
                : true;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        ViewGroup viewGroup = (ViewGroup) getView();
        viewGroup.removeAllViewsInLayout();
        initView$3(
                isAdaptLandScapeMode(configuration)
                        ? LayoutInflater.from(this.mContext)
                                .inflate(R.layout.sec_vibpicer_v2_land, viewGroup)
                        : LayoutInflater.from(this.mContext)
                                .inflate(R.layout.sec_vibpicker_v2, viewGroup));
        if (this.mRecyclerView != null) {
            this.mScrollHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.asbase.vibration.VibPickerActivity.3
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((LinearLayoutManager)
                                            VibPickerActivity.this.mRecyclerView.getLayoutManager())
                                    .scrollToPositionWithOffset(
                                            VibPickerActivity.this.mCheckedPos, 0);
                        }
                    },
                    20L);
        }
        Utils.applyLandscapeFullScreen(this.mContext, getActivity().getWindow());
        super.onConfigurationChanged(configuration);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mVibrator =
                ((VibratorManager) getActivity().getSystemService("vibrator_manager"))
                        .getDefaultVibrator();
        this.mSavedInstanceState = bundle;
        this.mVibHelper =
                new VibPickerHelper(
                        getArguments().getInt("sim_slot"),
                        this.mContext,
                        getActivity().getIntent());
        this.mScrollHandler = new Handler(Looper.getMainLooper());
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                isAdaptLandScapeMode(this.mContext.getResources().getConfiguration())
                        ? layoutInflater.inflate(R.layout.sec_vibpicer_v2_land, viewGroup, false)
                        : layoutInflater.inflate(R.layout.sec_vibpicker_v2, viewGroup, false);
        initView$3(inflate);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        Log.v("VibPickerActivity", "onDestroy()");
        this.mAllItemList.clear();
        this.mRealItemList.clear();
        this.mStaticItemCount = 0;
        this.mCustomPatternCount = 0;
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return false;
        }
        mHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.asbase.vibration.VibPickerActivity$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VibPickerActivity vibPickerActivity = VibPickerActivity.this;
                        Handler handler = VibPickerActivity.mHandler;
                        vibPickerActivity.getClass();
                    }
                },
                100L);
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        Vibrator vibrator = this.mVibrator;
        if (vibrator != null) {
            vibrator.cancel();
        }
        Ringtone ringtone = this.mRingtone;
        if (ringtone != null) {
            ringtone.stop();
        }
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Utils.applyLandscapeFullScreen(this.mContext, getActivity().getWindow());
        updateSelectedItemOtherSim();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        Log.e("VibPickerActivity", "onSaveInstanceState - " + this.mCheckedPos);
        bundle.putInt("instant_position", this.mCheckedPos);
    }

    public final void setResultBySelection(int i) {
        Intent intent = new Intent();
        if (i != -1) {
            intent.setData(
                    Uri.parse(
                            "content://com.android.settings.personalvibration.PersonalVibrationProvider/"
                                    + i));
        }
        getActivity().setResult(-1, intent);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSelectedItemOtherSim() {
        /*
            r4 = this;
            com.samsung.android.settings.asbase.vibration.VibPickerHelper r0 = r4.mVibHelper
            boolean r0 = r0.mIsSecondSim
            r1 = 0
            if (r0 == 0) goto L20
            androidx.fragment.app.FragmentActivity r0 = r4.getActivity()
            com.samsung.android.settings.asbase.vibration.VibPickerContainer r0 = (com.samsung.android.settings.asbase.vibration.VibPickerContainer) r0
            java.util.ArrayList r2 = r0.mSelectedItemList
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L38
            java.util.ArrayList r0 = r0.mSelectedItemList
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            r1 = r0
            com.samsung.android.settings.asbase.vibration.VibPickerItem r1 = (com.samsung.android.settings.asbase.vibration.VibPickerItem) r1
            goto L38
        L20:
            androidx.fragment.app.FragmentActivity r0 = r4.getActivity()
            com.samsung.android.settings.asbase.vibration.VibPickerContainer r0 = (com.samsung.android.settings.asbase.vibration.VibPickerContainer) r0
            java.util.ArrayList r2 = r0.mSelectedItemList
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L38
            java.util.ArrayList r0 = r0.mSelectedItemList
            r1 = 1
            java.lang.Object r0 = r0.get(r1)
            r1 = r0
            com.samsung.android.settings.asbase.vibration.VibPickerItem r1 = (com.samsung.android.settings.asbase.vibration.VibPickerItem) r1
        L38:
            r0 = -1
            if (r1 == 0) goto L55
            r4.mSelectedItemOtherSim = r1
            r2 = 2132027369(0x7f1427e9, float:1.9693297E38)
            java.lang.String r2 = r4.getString(r2)
            java.lang.String r3 = r1.mCategory
            boolean r2 = android.text.TextUtils.equals(r3, r2)
            if (r2 == 0) goto L55
            com.samsung.android.settings.asbase.vibration.VibPickerListAdapter r2 = r4.mVibPickerListAdapter
            int r1 = r1.mPattern
            int r1 = r2.getPositionByIndex(r1)
            goto L56
        L55:
            r1 = r0
        L56:
            com.samsung.android.settings.asbase.vibration.VibPickerListAdapter r4 = r4.mVibPickerListAdapter
            if (r1 != r0) goto L64
            int r1 = r4.mSelectedItemOtherSimPosition
            if (r1 == r0) goto L6e
            r4.mSelectedItemOtherSimPosition = r0
            r4.notifyItemChanged(r1)
            goto L6e
        L64:
            int r0 = r4.mSelectedItemOtherSimPosition
            r4.notifyItemChanged(r0)
            r4.mSelectedItemOtherSimPosition = r1
            r4.notifyItemChanged(r1)
        L6e:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.vibration.VibPickerActivity.updateSelectedItemOtherSim():void");
    }
}
