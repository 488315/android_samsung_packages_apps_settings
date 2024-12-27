package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.R$styleable;
import com.android.settingslib.widget.CandidateInfo;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SingleChoicePreference extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View.OnClickListener buttonClickListener;
    public CharSequence buttonTitle;
    public final SingleChoiceAdapter gridAdapter;
    public final int itemLayoutRes;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface CheckItemDefaultKey {
        String getDefaultKey();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnItemSelectedListener {
        void onItemSelected(String str);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SingleChoiceAdapter extends RecyclerView.Adapter {
        public CheckItemDefaultKey defaultKey;
        public final int itemLayoutResId;
        public List list;
        public ViewGroup parent;

        public SingleChoiceAdapter(int i) {
            this.itemLayoutResId = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            List list = this.list;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            int i2 = this.itemLayoutResId;
            this.parent = viewGroup;
            LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
            if (i2 != -1 && this.list.get(i) != null) {
                return new ViewHolder(from.inflate(i2, viewGroup, false));
            }
            int i3 = SingleChoicePreference.$r8$clinit;
            Log.e("SingleChoicePreference", "onCreateViewHolder - ViewHolder cannot be created");
            return new ViewHolder(new View(viewGroup.getContext()));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.itemView.setBackground(
                    viewHolder
                            .itemView
                            .getContext()
                            .getDrawable(R.drawable.single_choice_pref_item_bg));
            final SingleChoiceCandidateInfo singleChoiceCandidateInfo =
                    (SingleChoiceCandidateInfo) this.list.get(i);
            if (this.itemLayoutResId == -1 || singleChoiceCandidateInfo == null) {
                int i2 = SingleChoicePreference.$r8$clinit;
                Log.e("SingleChoicePreference", "onBindViewHolder - wrong Item");
                return;
            }
            String defaultKey = this.defaultKey.getDefaultKey();
            boolean equals =
                    defaultKey == null
                            ? singleChoiceCandidateInfo.defaultItem
                            : defaultKey.equals(singleChoiceCandidateInfo.key);
            ViewGroup viewGroup = this.parent;
            final boolean z = viewGroup == null || viewGroup.isEnabled();
            viewHolder.itemView.setEnabled(z);
            viewHolder.itemView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference$SingleChoiceAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SingleChoicePreference.SingleChoiceAdapter singleChoiceAdapter =
                                    SingleChoicePreference.SingleChoiceAdapter.this;
                            boolean z2 = z;
                            SingleChoicePreference.SingleChoiceCandidateInfo
                                    singleChoiceCandidateInfo2 = singleChoiceCandidateInfo;
                            singleChoiceAdapter.getClass();
                            if (z2) {
                                singleChoiceCandidateInfo2.listener.onItemSelected(
                                        singleChoiceCandidateInfo2.key);
                                singleChoiceAdapter.notifyDataSetChanged();
                            }
                        }
                    });
            if (viewHolder.guideImageView != null) {
                Drawable loadIcon = singleChoiceCandidateInfo.loadIcon();
                if (loadIcon != null) {
                    viewHolder.guideImageView.setEnabled(z);
                    viewHolder.guideImageView.setImageDrawable(loadIcon);
                } else {
                    viewHolder.guideImageView.setVisibility(8);
                }
            }
            TextView textView = viewHolder.titleView;
            if (textView != null) {
                CharSequence charSequence = singleChoiceCandidateInfo.label;
                if (charSequence != null) {
                    textView.setEnabled(z);
                    viewHolder.titleView.setSelected(equals);
                    viewHolder.titleView.setText(charSequence);
                    viewHolder.titleView.setTypeface(
                            Typeface.create(Typeface.create("sec", 0), equals ? 600 : 400, false));
                } else {
                    textView.setVisibility(8);
                }
            }
            CompoundButton compoundButton = viewHolder.button;
            if (compoundButton != null) {
                compoundButton.setEnabled(z);
                viewHolder.button.setChecked(equals);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SingleChoiceCandidateInfo extends CandidateInfo {
        public final boolean defaultItem;
        public final Drawable icon;
        public final String key;
        public final CharSequence label;
        public final OnItemSelectedListener listener;

        public SingleChoiceCandidateInfo(
                String str,
                CharSequence charSequence,
                Drawable drawable,
                OnItemSelectedListener onItemSelectedListener,
                boolean z) {
            super(true);
            this.key = str;
            this.label = charSequence;
            this.icon = drawable;
            this.listener = onItemSelectedListener;
            this.defaultItem = z;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.key;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public Drawable loadIcon() {
            return this.icon;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.label;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final CompoundButton button;
        public final ImageView guideImageView;
        public final TextView titleView;

        public ViewHolder(View view) {
            super(view);
            this.guideImageView = (ImageView) view.findViewById(R.id.item_guide_image);
            this.titleView = (TextView) view.findViewById(R.id.item_title);
            this.button = (CompoundButton) view.findViewById(android.R.id.checkbox);
        }
    }

    public SingleChoicePreference(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.singleChoicePreferenceStyle, R.attr.preferenceStyle));
        this.itemLayoutRes = -1;
        TypedArray obtainStyledAttributes =
                context.getTheme()
                        .obtainStyledAttributes(
                                attributeSet, R$styleable.SingleChoicePreference, 0, 0);
        for (int i = 0; i < obtainStyledAttributes.getIndexCount(); i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 0) {
                this.itemLayoutRes = obtainStyledAttributes.getResourceId(index, -1);
            }
        }
        obtainStyledAttributes.recycle();
        this.gridAdapter = createAdapter(this.itemLayoutRes);
    }

    public SingleChoiceAdapter createAdapter(int i) {
        return new SingleChoiceAdapter(i);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        RecyclerView recyclerView =
                (RecyclerView) preferenceViewHolder.itemView.findViewById(R.id.item_recyclerView);
        if (recyclerView != null) {
            recyclerView.setAdapter(this.gridAdapter);
            preferenceViewHolder.itemView.getContext();
            recyclerView.setLayoutManager(new GridLayoutManager(this.gridAdapter.getItemCount()));
        }
        Button button = (Button) preferenceViewHolder.itemView.findViewById(R.id.button);
        if (button != null) {
            if (TextUtils.isEmpty(this.buttonTitle) || this.buttonClickListener == null) {
                button.setVisibility(8);
            } else {
                button.setVisibility(0);
                button.setText(this.buttonTitle);
                button.setOnClickListener(this.buttonClickListener);
            }
            Drawable mutate =
                    getContext()
                            .getDrawable(R.drawable.a11y_body_contained_button_default)
                            .mutate();
            mutate.setTintList(
                    getContext()
                            .getColorStateList(
                                    R.color.sec_widget_multi_button_selected_icon_color));
            button.setBackground(mutate);
            button.setTextColor(
                    getContext().getColorStateList(R.color.a11y_colored_button_text_color));
        }
    }
}
