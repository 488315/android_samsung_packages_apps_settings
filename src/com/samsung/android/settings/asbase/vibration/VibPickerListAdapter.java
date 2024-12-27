package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.vibration.VibPickerActivity.DeleteAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibPickerListAdapter extends RecyclerView.Adapter {
    public final Context mContext;
    public int mCustomPatternCount;
    public final String mFrom;
    public final int mType;
    public final ArrayList mVibPickerItemList;
    public int mSelectedItemPosition = -1;
    public boolean mSyncEnabled = false;
    public boolean mIsBluetoothA2dpOn = false;
    public int mSelectedItemOtherSimPosition = -1;
    public VibPickerActivity.AnonymousClass2 mListener = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DecorationViewHolder extends RecyclerView.ViewHolder {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RoleDescriptionAccessibilityDelegate extends AccessibilityDelegateCompat {
        public final String mRoleDescription = "Radio button";

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(
                View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                    view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setRoleDescription(this.mRoleDescription);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageButton mDeleteItemButton;
        public final CheckedTextView mRadioTextView;
        public final TextView mSubtitle;
        public final LinearLayout mSubtitleLayout;
        public final VibPickerListAdapter$ViewHolder$$ExternalSyntheticLambda0 onDeleteItemListener;
        public final AnonymousClass1 onSelectItemListener;
        public int roundMode;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.asbase.vibration.VibPickerListAdapter$ViewHolder$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.asbase.vibration.VibPickerListAdapter$ViewHolder$1] */
        public ViewHolder(View view) {
            super(view);
            this.onDeleteItemListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.asbase.vibration.VibPickerListAdapter$ViewHolder$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            VibPickerListAdapter.ViewHolder viewHolder =
                                    VibPickerListAdapter.ViewHolder.this;
                            int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                            VibPickerListAdapter vibPickerListAdapter = VibPickerListAdapter.this;
                            VibPickerItem vibPickerItem =
                                    (VibPickerItem)
                                            vibPickerListAdapter.mVibPickerItemList.get(
                                                    bindingAdapterPosition);
                            if (vibPickerListAdapter.mCustomPatternCount < 2) {
                                VibPickerItem vibPickerItem2 =
                                        (VibPickerItem)
                                                vibPickerListAdapter.mVibPickerItemList.get(
                                                        bindingAdapterPosition - 1);
                                if (vibPickerItem2.mIsCustom == 1) {
                                    vibPickerListAdapter.mVibPickerItemList.remove(vibPickerItem2);
                                }
                            }
                            vibPickerListAdapter.mVibPickerItemList.remove(vibPickerItem);
                            int i = vibPickerListAdapter.mCustomPatternCount - 1;
                            vibPickerListAdapter.mCustomPatternCount = i;
                            int i2 = vibPickerListAdapter.mSelectedItemPosition;
                            if (i2 > bindingAdapterPosition) {
                                if (i == 0) {
                                    vibPickerListAdapter.mSelectedItemPosition = i2 - 2;
                                } else {
                                    vibPickerListAdapter.mSelectedItemPosition = i2 - 1;
                                }
                            }
                            Iterator it = vibPickerListAdapter.mVibPickerItemList.iterator();
                            int i3 = 0;
                            while (it.hasNext()) {
                                VibPickerItem vibPickerItem3 = (VibPickerItem) it.next();
                                if (vibPickerItem3.mItemType != 0
                                        && vibPickerItem3.mIsCustom == 1) {
                                    vibPickerItem3.mPosition = i3;
                                    i3++;
                                }
                            }
                            VibPickerActivity.AnonymousClass2 anonymousClass2 =
                                    vibPickerListAdapter.mListener;
                            if (anonymousClass2 != null) {
                                int i4 = vibPickerListAdapter.mSelectedItemPosition;
                                int i5 = vibPickerItem.mPattern;
                                VibPickerActivity vibPickerActivity = VibPickerActivity.this;
                                vibPickerActivity.mCheckedPos = i4;
                                VibPickerListAdapter vibPickerListAdapter2 =
                                        vibPickerActivity.mVibPickerListAdapter;
                                if (vibPickerListAdapter2 != null) {
                                    vibPickerActivity.mCustomPatternCount =
                                            vibPickerListAdapter2.mCustomPatternCount;
                                }
                                if (vibPickerActivity.mQueryHelper.getPatternName(i5) != null) {
                                    vibPickerActivity.new DeleteAsyncTask()
                                            .execute(Integer.valueOf(i5));
                                }
                            }
                            vibPickerListAdapter.notifyDataSetChanged();
                        }
                    };
            this.onSelectItemListener =
                    new View.OnClickListener() { // from class:
                        // com.samsung.android.settings.asbase.vibration.VibPickerListAdapter.ViewHolder.1
                        /* JADX WARN: Removed duplicated region for block: B:68:0x019c  */
                        /* JADX WARN: Removed duplicated region for block: B:71:0x01a0  */
                        @Override // android.view.View.OnClickListener
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void onClick(android.view.View r10) {
                            /*
                                Method dump skipped, instructions count: 456
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.samsung.android.settings.asbase.vibration.VibPickerListAdapter.ViewHolder.AnonymousClass1.onClick(android.view.View):void");
                        }
                    };
            CheckedTextView checkedTextView =
                    (CheckedTextView) view.findViewById(R.id.radiobutton_checkedtextview);
            this.mRadioTextView = checkedTextView;
            if (checkedTextView != null) {
                if (VibPickerListAdapter.this.mContext.getThemeResId()
                        == R.style.VibrationPickerDefault) {
                    checkedTextView.setCheckMarkTintList(null);
                }
                this.mDeleteItemButton = (ImageButton) view.findViewById(R.id.removebutton);
                this.mSubtitleLayout = (LinearLayout) view.findViewById(R.id.subtitle_layout);
                this.mSubtitle = (TextView) view.findViewById(R.id.subtitle_textview);
                ViewCompat.setAccessibilityDelegate(
                        checkedTextView, new RoleDescriptionAccessibilityDelegate());
            }
        }
    }

    public VibPickerListAdapter(Context context, int i, ArrayList arrayList, int i2, String str) {
        this.mType = i;
        this.mVibPickerItemList = arrayList;
        this.mContext = context;
        this.mCustomPatternCount = i2;
        notifyItemRangeChanged(0, arrayList.size());
        this.mFrom = str;
    }

    public final VibPickerItem getItem(int i) {
        return (VibPickerItem) this.mVibPickerItemList.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mVibPickerItemList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return VibPickerListItemViewType.getViewTypeByPosition(i, this.mVibPickerItemList.size());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0047 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0007 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getPositionByIndex(int r5) {
        /*
            r4 = this;
            java.util.ArrayList r0 = r4.mVibPickerItemList
            java.util.Iterator r0 = r0.iterator()
            r1 = 0
        L7:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L4a
            java.lang.Object r2 = r0.next()
            com.samsung.android.settings.asbase.vibration.VibPickerItem r2 = (com.samsung.android.settings.asbase.vibration.VibPickerItem) r2
            if (r2 == 0) goto L7
            int r3 = r2.mPattern
            if (r3 != r5) goto L7
            int r1 = r2.mPosition
            java.lang.String r3 = "Custom"
            java.lang.String r2 = r2.mCategory
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L28
        L25:
            int r1 = r1 + 2
            goto L37
        L28:
            java.lang.String r3 = "Galaxy"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L4a
            int r2 = r4.mCustomPatternCount
            if (r2 <= 0) goto L25
            int r2 = r2 + 3
            int r1 = r1 + r2
        L37:
            java.lang.String r2 = "android.intent.extra.pattern.FROM_CONTACT"
            java.lang.String r3 = r4.mFrom
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L43
            int r1 = r1 + 1
        L43:
            boolean r2 = com.samsung.android.settings.asbase.rune.VibRune.SUPPORT_SYNC_WITH_HAPTIC
            if (r2 == 0) goto L7
            int r1 = r1 + 1
            goto L7
        L4a:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.vibration.VibPickerListAdapter.getPositionByIndex(int):int");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            VibPickerListItemViewType vibPickerListItemViewType =
                    (VibPickerListItemViewType)
                            ((HashMap) VibPickerListItemViewType.typeMap)
                                    .get(Integer.valueOf(getItemViewType(i)));
            if (vibPickerListItemViewType == null) {
                vibPickerListItemViewType = VibPickerListItemViewType.MIDDLE_VIEW;
            }
            viewHolder2.roundMode = vibPickerListItemViewType.getRoundMode();
            VibPickerItem vibPickerItem = (VibPickerItem) this.mVibPickerItemList.get(i);
            if (vibPickerItem.mItemType == 0) {
                viewHolder2.mSubtitleLayout.setVisibility(0);
                viewHolder2.mRadioTextView.setVisibility(8);
                viewHolder2.mDeleteItemButton.setVisibility(8);
                viewHolder2.mSubtitleLayout.setClickable(false);
                viewHolder2.mSubtitle.setText(vibPickerItem.mTitle);
                return;
            }
            viewHolder2.mSubtitleLayout.setVisibility(8);
            viewHolder2.mRadioTextView.setVisibility(0);
            viewHolder2.mDeleteItemButton.setVisibility(0);
            viewHolder2.mRadioTextView.setOnClickListener(null);
            viewHolder2.mDeleteItemButton.setOnClickListener(null);
            CheckedTextView checkedTextView = viewHolder2.mRadioTextView;
            String str = vibPickerItem.mName;
            checkedTextView.setText(str);
            viewHolder2.mRadioTextView.setOnClickListener(viewHolder2.onSelectItemListener);
            viewHolder2.mRadioTextView.setChecked(i == this.mSelectedItemPosition);
            if (VibRune.SUPPORT_SYNC_WITH_HAPTIC && vibPickerItem.mPattern == 50000) {
                if (this.mSyncEnabled) {
                    if (this.mIsBluetoothA2dpOn) {
                        viewHolder2.mRadioTextView.setText(setSpanText(vibPickerItem, 1));
                    } else {
                        viewHolder2.mRadioTextView.setText(str);
                    }
                    viewHolder2.mRadioTextView.setEnabled(true);
                } else {
                    viewHolder2.mRadioTextView.setText(setSpanText(vibPickerItem, 0));
                    viewHolder2.mRadioTextView.setEnabled(false);
                }
            }
            String str2 = this.mFrom;
            if ("android.intent.extra.pattern.FROM_CONTACT".equals(str2)
                    && "Default".equals(vibPickerItem.mCategory)
                    && this.mContext
                            .getString(R.string.sec_vib_picker_default_item_title)
                            .equals(str)) {
                viewHolder2.mRadioTextView.setText(setSpanText(vibPickerItem, 2));
            }
            if (vibPickerItem.mIsCustom != 1) {
                viewHolder2.mDeleteItemButton.setVisibility(8);
                return;
            }
            viewHolder2.mDeleteItemButton.setOnClickListener(viewHolder2.onDeleteItemListener);
            if (i != this.mSelectedItemPosition) {
                viewHolder2.mDeleteItemButton.setVisibility(0);
            } else {
                viewHolder2.mDeleteItemButton.setVisibility(8);
            }
            if ("android.intent.extra.pattern.FROM_CONTACT".equals(str2)) {
                viewHolder2.mDeleteItemButton.setVisibility(8);
            }
            if (i == this.mSelectedItemOtherSimPosition) {
                viewHolder2.mDeleteItemButton.setVisibility(8);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater =
                (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        VibPickerListItemViewType vibPickerListItemViewType =
                (VibPickerListItemViewType)
                        ((HashMap) VibPickerListItemViewType.typeMap).get(Integer.valueOf(i));
        if (vibPickerListItemViewType == null) {
            vibPickerListItemViewType = VibPickerListItemViewType.MIDDLE_VIEW;
        }
        View view = vibPickerListItemViewType.getView(layoutInflater, viewGroup, this.mContext);
        return i == VibPickerListItemViewType.FOOTER_VIEW.getValue()
                ? new DecorationViewHolder(view)
                : new ViewHolder(view);
    }

    public final SpannableString setSpanText(VibPickerItem vibPickerItem, int i) {
        String concat;
        int i2;
        String str = vibPickerItem.mName;
        int length = str.length();
        if (i == 0) {
            if (this.mType == 0) {
                concat =
                        str.concat(
                                "\n"
                                        + this.mContext.getString(
                                                R.string
                                                        .sec_vib_picker_not_available_sync_ringtone));
            } else {
                concat =
                        str.concat(
                                "\n"
                                        + this.mContext.getString(
                                                R.string
                                                        .sec_vib_picker_not_available_sync_notification));
            }
            i2 = R.style.SyncItemSummaryTextStyle;
        } else {
            if (i == 1) {
                concat =
                        str.concat(
                                "\n"
                                        + this.mContext.getString(
                                                R.string
                                                        .sec_vib_picker_not_available_when_connected_bluetooth));
            } else {
                concat = str.concat("\n" + vibPickerItem.mTitle);
            }
            i2 = R.style.DefaultItemSummaryTextStyle;
        }
        SpannableString spannableString = new SpannableString(concat);
        spannableString.setSpan(
                new TextAppearanceSpan(this.mContext, i2), length, concat.length(), 33);
        return spannableString;
    }
}
