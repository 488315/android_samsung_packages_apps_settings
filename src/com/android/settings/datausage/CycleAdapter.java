package com.android.settings.datausage;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CycleAdapter extends ArrayAdapter {
    public boolean mIsSettedDate;
    public AdapterView.OnItemSelectedListener mListener;
    public long mSettedEndTime;
    public long mSettedStartTime;
    public final SpinnerInterface mSpinner;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SpinnerInterface {
        Object getSelectedItem();

        void setAdapter(CycleAdapter cycleAdapter);

        void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener);

        void setSelection(int i);
    }

    public CycleAdapter(Context context, SpinnerInterface spinnerInterface) {
        super(context, R.layout.sec_data_usage_list_filter_spinner_item);
        this.mIsSettedDate = false;
        this.mSettedStartTime = 0L;
        this.mSettedEndTime = 0L;
        setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        this.mSpinner = spinnerInterface;
        spinnerInterface.setAdapter(this);
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter,
              // android.widget.SpinnerAdapter
    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        View dropDownView = super.getDropDownView(i, view, viewGroup);
        CycleItem cycleItem = (CycleItem) getItem(i);
        if (cycleItem != null && !TextUtils.isEmpty(cycleItem.description)) {
            dropDownView.setContentDescription(cycleItem.description);
        }
        return dropDownView;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        CycleItem cycleItem = (CycleItem) getItem(i);
        if (cycleItem != null && !TextUtils.isEmpty(cycleItem.description)) {
            view2.setContentDescription(cycleItem.description);
        }
        return view2;
    }

    public final void updateCycleList(List list) {
        int i;
        AdapterView.OnItemSelectedListener onItemSelectedListener;
        AdapterView.OnItemSelectedListener onItemSelectedListener2 = this.mListener;
        if (onItemSelectedListener2 != null) {
            this.mSpinner.setOnItemSelectedListener(onItemSelectedListener2);
        }
        CycleItem cycleItem = (CycleItem) this.mSpinner.getSelectedItem();
        clear();
        Context context = getContext();
        try {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                Range range = (Range) it.next();
                add(
                        new CycleItem(
                                context,
                                ((Long) range.getLower()).longValue(),
                                ((Long) range.getUpper()).longValue()));
            }
        } catch (NullPointerException unused) {
            Log.i("CycleAdapter", "exception in load cycle data");
        }
        if (getCount() > 0) {
            if (Rune.SUPPORT_SMARTMANAGER_CN && this.mListener != null) {
                CycleItem cycleItem2 = new CycleItem();
                cycleItem2.dateSetByUser = false;
                cycleItem2.isUserSettedItem = false;
                cycleItem2.label = context.getString(R.string.select_date_to_view);
                cycleItem2.dateSetByUser = true;
                add(cycleItem2);
                if (this.mIsSettedDate) {
                    add(new CycleItem(context, this.mSettedStartTime, 0, this.mSettedEndTime));
                }
            }
            if (cycleItem != null) {
                for (int count = getCount() - 1; count >= 0; count--) {
                    CycleItem cycleItem3 = (CycleItem) getItem(count);
                    int compare = Long.compare(cycleItem3.start, cycleItem.start);
                    if (Rune.SUPPORT_SMARTMANAGER_CN
                            && cycleItem3.isUserSettedItem
                            && !cycleItem.isUserSettedItem) {
                        compare = -1;
                    }
                    if (compare >= 0) {
                        i = count;
                        break;
                    }
                }
            }
            i = 0;
            this.mSpinner.setSelection(i);
            if (!Rune.SUPPORT_SMARTMANAGER_CN
                    || Objects.equals((CycleItem) getItem(i), cycleItem)
                    || (onItemSelectedListener = this.mListener) == null) {
                return;
            }
            onItemSelectedListener.onItemSelected(null, null, i, 0L);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CycleItem implements Comparable {
        public boolean dateSetByUser;
        public final String description;
        public final long end;
        public boolean isUserSettedItem;
        public CharSequence label;
        public final long start;

        public CycleItem(Context context, long j, long j2) {
            String string;
            this.dateSetByUser = false;
            this.isUserSettedItem = false;
            this.label = Utils.formatDateRange(context, j, j2);
            this.start = j;
            this.end = j2;
            if (context == null) {
                string = null;
            } else {
                long j3 = j2 - 1;
                string =
                        context.getString(
                                R.string.sec_data_usage_summary_preference_cycle_label_tts,
                                Utils.formatDateRange(context, j, j),
                                Utils.formatDateRange(context, j3, j3));
            }
            this.description = string;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            CycleItem cycleItem = (CycleItem) obj;
            int compare = Long.compare(this.start, cycleItem.start);
            if (Rune.SUPPORT_SMARTMANAGER_CN
                    && this.isUserSettedItem
                    && !cycleItem.isUserSettedItem) {
                return -1;
            }
            return compare;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof CycleItem)) {
                return false;
            }
            CycleItem cycleItem = (CycleItem) obj;
            return this.start == cycleItem.start && this.end == cycleItem.end;
        }

        public final String toString() {
            return this.label.toString();
        }

        public CycleItem(Context context, long j, int i, long j2) {
            String string;
            this.dateSetByUser = false;
            this.isUserSettedItem = false;
            this.label = Utils.formatDateRange(context, j, j2);
            this.start = j;
            this.end = j2;
            this.isUserSettedItem = true;
            if (context == null) {
                string = null;
            } else {
                long j3 = j2 - 1;
                string =
                        context.getString(
                                R.string.sec_data_usage_summary_preference_cycle_label_tts,
                                Utils.formatDateRange(context, j, j),
                                Utils.formatDateRange(context, j3, j3));
            }
            this.description = string;
        }
    }
}
