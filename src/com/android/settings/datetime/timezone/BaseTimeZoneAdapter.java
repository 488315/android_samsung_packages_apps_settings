package com.android.settings.datetime.timezone;

import android.R;
import android.content.Context;
import android.graphics.Typeface;
import android.icu.text.BreakIterator;
import android.icu.text.CaseMap;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BaseTimeZoneAdapter extends RecyclerView.Adapter {
    static final int TYPE_HEADER = 0;
    static final int TYPE_ITEM = 1;
    public ArrayFilter mFilter;
    public final CharSequence mHeaderText;
    public final boolean mIsSupportContrastView;
    public List mItems;
    public final Locale mLocale;
    public final BaseTimeZonePicker.OnListItemClickListener mOnListItemClickListener;
    public final List mOriginalItems;
    public String mQueryText = ApnSettings.MVNO_NONE;
    public final boolean mShowHeader;
    public final boolean mShowItemSummary;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface AdapterItem {
        String getCurrentTime();

        long getItemId();

        String[] getSearchKeys();

        CharSequence getSummary();

        CharSequence getTitle();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ArrayFilter extends Filter {
        public final BreakIterator mBreakIterator;

        public ArrayFilter() {
            this.mBreakIterator = BreakIterator.getWordInstance(BaseTimeZoneAdapter.this.mLocale);
        }

        @Override // android.widget.Filter
        public final Filter.FilterResults performFiltering(CharSequence charSequence) {
            List list;
            if (TextUtils.isEmpty(charSequence)) {
                BaseTimeZoneAdapter baseTimeZoneAdapter = BaseTimeZoneAdapter.this;
                list = baseTimeZoneAdapter.mOriginalItems;
                baseTimeZoneAdapter.mQueryText = ApnSettings.MVNO_NONE;
            } else {
                BaseTimeZoneAdapter.this.mQueryText = charSequence.toString();
                String lowerCase =
                        charSequence.toString().toLowerCase(BaseTimeZoneAdapter.this.mLocale);
                ArrayList arrayList = new ArrayList();
                for (AdapterItem adapterItem : BaseTimeZoneAdapter.this.mOriginalItems) {
                    String[] searchKeys = adapterItem.getSearchKeys();
                    int length = searchKeys.length;
                    int i = 0;
                    while (true) {
                        if (i < length) {
                            String lowerCase2 =
                                    searchKeys[i].toLowerCase(BaseTimeZoneAdapter.this.mLocale);
                            if (lowerCase2.startsWith(lowerCase)) {
                                arrayList.add(adapterItem);
                                break;
                            }
                            this.mBreakIterator.setText(lowerCase2);
                            int next = this.mBreakIterator.next();
                            int i2 = 0;
                            while (next != -1) {
                                if (this.mBreakIterator.getRuleStatus() != 0
                                        && lowerCase2.startsWith(lowerCase, i2)) {
                                    arrayList.add(adapterItem);
                                    break;
                                }
                                i2 = next;
                                next = this.mBreakIterator.next();
                            }
                            i++;
                        }
                    }
                }
                list = arrayList;
            }
            Filter.FilterResults filterResults = new Filter.FilterResults();
            filterResults.values = list;
            filterResults.count = list.size();
            return filterResults;
        }

        @Override // android.widget.Filter
        public void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            BaseTimeZoneAdapter baseTimeZoneAdapter = BaseTimeZoneAdapter.this;
            baseTimeZoneAdapter.mItems = (List) filterResults.values;
            baseTimeZoneAdapter.notifyDataSetChanged();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FooterViewHolder extends RecyclerView.ViewHolder {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;

        public HeaderViewHolder(View view) {
            super(view);
            this.mTextView = (TextView) view.findViewById(R.id.title);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ItemViewHolder<T extends AdapterItem> extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final Context mContext;
        public final TextView mIconTextView;
        public AdapterItem mItem;
        public final BaseTimeZonePicker.OnListItemClickListener mOnListItemClickListener;
        public final View mSummaryFrame;
        public final TextView mSummaryView;
        public final TextView mTimeView;
        public final TextView mTitleView;

        public ItemViewHolder(
                View view, BaseTimeZonePicker.OnListItemClickListener onListItemClickListener) {
            super(view);
            view.setOnClickListener(this);
            this.mSummaryFrame = view.findViewById(com.android.settings.R.id.summary_frame);
            this.mTitleView = (TextView) view.findViewById(R.id.title);
            this.mIconTextView = (TextView) view.findViewById(com.android.settings.R.id.icon_text);
            this.mSummaryView = (TextView) view.findViewById(R.id.summary);
            this.mTimeView = (TextView) view.findViewById(com.android.settings.R.id.current_time);
            this.mOnListItemClickListener = onListItemClickListener;
            this.mContext = view.getContext();
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.mOnListItemClickListener.onListItemClick(this.mItem);
        }
    }

    public BaseTimeZoneAdapter(
            List list,
            BaseTimeZonePicker.OnListItemClickListener onListItemClickListener,
            Locale locale,
            boolean z,
            CharSequence charSequence) {
        Locale locale2 = Locale.getDefault();
        this.mIsSupportContrastView =
                (locale2.getLanguage().startsWith("ar")
                                || locale2.getLanguage().startsWith("as")
                                || locale2.getLanguage().startsWith("bn"))
                        ? false
                        : true;
        this.mOriginalItems = list;
        this.mItems = list;
        this.mOnListItemClickListener = onListItemClickListener;
        this.mLocale = locale;
        this.mShowItemSummary = z;
        this.mShowHeader = charSequence != null;
        this.mHeaderText = charSequence;
        super.setHasStableIds(true);
    }

    public AdapterItem getDataItem(int i) {
        return (AdapterItem) this.mItems.get(i - (this.mShowHeader ? 1 : 0));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mItems.size() + (this.mShowHeader ? 1 : 0) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        if (i == getItemCount() - 1) {
            return -1L;
        }
        if (this.mShowHeader && i == 0) {
            return -1L;
        }
        return getDataItem(i).getItemId();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        int size = this.mItems.size();
        boolean z = this.mShowHeader;
        if (i == size + (z ? 1 : 0)) {
            return 2;
        }
        return ((z && i == 0) ? 1 : 0) ^ 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int i2;
        int i3;
        if (viewHolder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) viewHolder).mTextView.setText(this.mHeaderText);
            return;
        }
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            AdapterItem dataItem = getDataItem(i);
            itemViewHolder.mItem = dataItem;
            itemViewHolder.mTitleView.setText(dataItem.getTitle());
            itemViewHolder.mIconTextView.setText((CharSequence) null);
            itemViewHolder.mSummaryView.setText(dataItem.getSummary());
            itemViewHolder.mTimeView.setText(dataItem.getCurrentTime());
            itemViewHolder.mSummaryFrame.setVisibility(this.mShowItemSummary ? 0 : 8);
            boolean z = this.mShowHeader;
            if (z && i == 1) {
                itemViewHolder.itemView.semSetRoundedCorners(3);
                itemViewHolder.itemView.semSetRoundedCornerColor(
                        3,
                        itemViewHolder
                                .mContext
                                .getResources()
                                .getColor(
                                        com.android.settings.R.color.sec_widget_round_and_bgcolor));
            } else if (i == (this.mItems.size() + (z ? 1 : 0)) - 1) {
                itemViewHolder.itemView.semSetRoundedCorners(12);
                itemViewHolder.itemView.semSetRoundedCornerColor(
                        12,
                        itemViewHolder
                                .mContext
                                .getResources()
                                .getColor(
                                        com.android.settings.R.color.sec_widget_round_and_bgcolor));
            } else {
                itemViewHolder.itemView.semSetRoundedCorners(0);
            }
            String charSequence = itemViewHolder.mTitleView.getText().toString();
            if (!this.mIsSupportContrastView || TextUtils.isEmpty(this.mQueryText)) {
                itemViewHolder.mTitleView.setText(charSequence);
                return;
            }
            CaseMap.Title title = SpannableUtil.TITLE_CASE_MAP;
            String language = Locale.getDefault().getLanguage();
            if ("hi".equals(language)
                    || "ta".equals(language)
                    || "ml".equals(language)
                    || "te".equals(language)
                    || "or".equals(language)
                    || "ne".equals(language)
                    || "as".equals(language)
                    || "bn".equals(language)
                    || "gu".equals(language)
                    || "si".equals(language)
                    || "pa".equals(language)
                    || "kn".equals(language)
                    || "mr".equals(language)) {
                String str = this.mQueryText;
                int indexOf = charSequence.toLowerCase().indexOf(str.toLowerCase());
                int length = str.length() + indexOf;
                while (indexOf >= 1) {
                    int i4 = indexOf - 1;
                    if (!SpannableUtil.isJoiningCharacter(charSequence.charAt(i4))) {
                        break;
                    }
                    if (Locale.getDefault().getLanguage().equals("or")) {
                        char charAt = charSequence.charAt(indexOf);
                        if (!Locale.getDefault().getLanguage().equals("or")) {
                            if (!Locale.getDefault().getLanguage().equals("hi")) {
                                if (charAt >= 2309 && charAt <= 2324) {
                                    break;
                                }
                            } else if (charAt >= 2309 && charAt <= 2324) {
                                break;
                            }
                        } else if (charAt >= 2821 && charAt <= 2836) {
                            break;
                        }
                    }
                    if (Locale.getDefault().getLanguage().equals("hi")
                            && charSequence.charAt(i4) == 2319) {
                        break;
                    } else {
                        indexOf--;
                    }
                }
                if (Locale.getDefault().getLanguage().equals("hi")
                        && indexOf >= 1
                        && charSequence.charAt(indexOf - 1) == 2352
                        && SpannableUtil.isJoiningCharacter(charSequence.charAt(indexOf))) {
                    indexOf--;
                }
                while (length < charSequence.length()
                        && SpannableUtil.isJoiningCharacter(charSequence.charAt(length))) {
                    length++;
                }
                int[] iArr = {indexOf, length};
                i2 = iArr[0];
                i3 = iArr[1];
            } else {
                i2 = charSequence.toLowerCase().indexOf(this.mQueryText.toLowerCase());
                i3 = this.mQueryText.length() + i2;
            }
            if (i2 != -1) {
                SpannableString spannableString = new SpannableString(charSequence);
                ForegroundColorSpan foregroundColorSpan =
                        new ForegroundColorSpan(
                                itemViewHolder
                                        .mContext
                                        .getResources()
                                        .getColor(
                                                com.android.settings.R.color
                                                        .sec_preference_summary_primary_color));
                spannableString.setSpan(
                        new TypefaceSpan(Typeface.create(Typeface.create("sec", 1), 600, false)),
                        i2,
                        i3,
                        33);
                spannableString.setSpan(foregroundColorSpan, i2, i3, 33);
                itemViewHolder.mTitleView.setText(spannableString);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        if (i == 0) {
            return new HeaderViewHolder(
                    from.inflate(
                            com.android.settings.R.layout.sec_preference_category_layout,
                            viewGroup,
                            false));
        }
        if (i == 1) {
            return new ItemViewHolder(
                    from.inflate(
                            com.android.settings.R.layout.time_zone_search_item, viewGroup, false),
                    this.mOnListItemClickListener);
        }
        if (i == 2) {
            return new FooterViewHolder(
                    from.inflate(
                            com.android.settings.R.layout.sec_time_zone_footer_holder,
                            viewGroup,
                            false));
        }
        throw new IllegalArgumentException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unexpected viewType: "));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void setHasStableIds(boolean z) {
        super.setHasStableIds(true);
    }
}
