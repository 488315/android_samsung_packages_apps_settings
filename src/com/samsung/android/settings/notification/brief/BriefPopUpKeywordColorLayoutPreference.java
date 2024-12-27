package com.samsung.android.settings.notification.brief;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.view.SeslContentViewInsetsCallback;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.picker3.app.SeslColorPickerDialog;
import androidx.picker3.widget.SeslColorPicker;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopUpKeywordColorLayoutPreference extends LayoutPreference {
    public static final Handler customViewHandler = new Handler();
    public final String TAG;
    public SeslColorPickerDialog mColorPickerDialog;
    public final Context mContext;
    public final AnonymousClass4 mCoustomColorOnClickListener;
    public final AnonymousClass4 mCustomTextColorClickListener;
    public ArrayList mCustomTextFilterList;
    public EditText mEditTextView;
    public String mEditTextViewName;
    public TextView mEditWrongText;
    public TextView mMainText;
    public View mNotiIconBg;
    public int mPreviousColor;
    public Button mSelectTextColorButton;
    public Button mTextAddButton;
    public final AnonymousClass6 mTextColorSetListener;
    public TextColorListAdapter mTextListAdapter;
    public ListView mTextListView;
    public final AnonymousClass7 resetCustomView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnFocusChangeListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.view.View.OnFocusChangeListener
        public final void onFocusChange(View view, boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    if (!z) {
                        ((InputMethodManager)
                                        ((BriefPopUpKeywordColorLayoutPreference) this.this$0)
                                                .mContext.getSystemService("input_method"))
                                .hideSoftInputFromWindow(
                                        ((BriefPopUpKeywordColorLayoutPreference) this.this$0)
                                                .mEditTextView.getWindowToken(),
                                        0);
                        ((BriefPopUpKeywordColorLayoutPreference) this.this$0).checkTextAvailable();
                        break;
                    }
                    break;
                default:
                    if (view != null && !z) {
                        TextColorListAdapter.m1263$$Nest$mupdateText(
                                (TextColorListAdapter) this.this$0, view);
                        TextColorListAdapter.m1262$$Nest$mfinishEdit(
                                (TextColorListAdapter) this.this$0, view);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference$3, reason: invalid class name */
    public final class AnonymousClass3 implements TextView.OnEditorActionListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass3(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (this.$r8$classId) {
                case 0:
                    if (i == 6) {
                        BriefPopUpKeywordColorLayoutPreference
                                briefPopUpKeywordColorLayoutPreference =
                                        (BriefPopUpKeywordColorLayoutPreference) this.this$0;
                        BriefPopUpKeywordColorLayoutPreference.m1261$$Nest$msaveAppCustomColorText(
                                briefPopUpKeywordColorLayoutPreference,
                                briefPopUpKeywordColorLayoutPreference.mEditTextView.getText());
                        ((BriefPopUpKeywordColorLayoutPreference) this.this$0).checkTextAvailable();
                        break;
                    }
                    break;
                default:
                    if (i == 6 && textView != null) {
                        TextColorListAdapter.m1263$$Nest$mupdateText(
                                (TextColorListAdapter) this.this$0, textView);
                        textView.clearFocus();
                        TextColorListAdapter.m1262$$Nest$mfinishEdit(
                                (TextColorListAdapter) this.this$0, textView);
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends Preference.BaseSavedState {
        public String name;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.name);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TextColorListAdapter extends BaseAdapter {
        public final LayoutInflater mInflater;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference$TextColorListAdapter$1, reason: invalid class name */
        public final class AnonymousClass1 implements View.OnKeyListener {
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() != 4) {
                    return false;
                }
                view.clearFocus();
                return false;
            }
        }

        /* renamed from: -$$Nest$mfinishEdit, reason: not valid java name */
        public static void m1262$$Nest$mfinishEdit(
                TextColorListAdapter textColorListAdapter, View view) {
            TextView textView;
            textColorListAdapter.getClass();
            if (view.getParent() != null
                    && (textView =
                                    (TextView)
                                            ((ViewGroup) view.getParent())
                                                    .findViewById(R.id.text_wrong))
                            != null) {
                textView.setVisibility(4);
            }
            ((InputMethodManager)
                            BriefPopUpKeywordColorLayoutPreference.this.mContext.getSystemService(
                                    "input_method"))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        /* renamed from: -$$Nest$mupdateText, reason: not valid java name */
        public static void m1263$$Nest$mupdateText(
                TextColorListAdapter textColorListAdapter, View view) {
            textColorListAdapter.getClass();
            TextView textView = (TextView) view;
            if (textView.getText() == null) {
                return;
            }
            String charSequence = textView.getText().toString();
            BriefPopUpKeywordColorLayoutPreference briefPopUpKeywordColorLayoutPreference =
                    BriefPopUpKeywordColorLayoutPreference.this;
            Handler handler = BriefPopUpKeywordColorLayoutPreference.customViewHandler;
            if (!briefPopUpKeywordColorLayoutPreference.isAddAvailable(charSequence)) {
                if (!textView.getText().toString().equals((String) view.getTag())
                        && charSequence != null
                        && !charSequence.isEmpty()
                        && !charSequence.trim().isEmpty()) {
                    Context context = BriefPopUpKeywordColorLayoutPreference.this.mContext;
                    Toast.makeText(
                                    context,
                                    context.getResources()
                                            .getString(
                                                    R.string
                                                            .brief_popup_keyword_color_already_define),
                                    0)
                            .show();
                }
                ((TextView) view).setText((String) view.getTag());
                return;
            }
            BriefPopUpUtils.saveCustomTextColor(
                    BriefPopUpKeywordColorLayoutPreference.this.mContext,
                    BriefPopUpUtils.loadCustomTextColor(
                            BriefPopUpKeywordColorLayoutPreference.this.mContext,
                            (String) view.getTag()),
                    charSequence);
            BriefPopUpUtils.removeCustomTextColor(
                    BriefPopUpKeywordColorLayoutPreference.this.mContext, (String) view.getTag());
            BriefPopUpKeywordColorLayoutPreference briefPopUpKeywordColorLayoutPreference2 =
                    BriefPopUpKeywordColorLayoutPreference.this;
            String str = (String) view.getTag();
            int indexOf =
                    briefPopUpKeywordColorLayoutPreference2.mCustomTextFilterList.indexOf(str);
            if (indexOf > -1) {
                briefPopUpKeywordColorLayoutPreference2.mCustomTextFilterList.remove(str);
                briefPopUpKeywordColorLayoutPreference2.mCustomTextFilterList.add(
                        indexOf, charSequence);
                briefPopUpKeywordColorLayoutPreference2.mTextListAdapter.notifyDataSetChanged();
            }
            view.setTag(charSequence);
        }

        public TextColorListAdapter() {
            this.mInflater =
                    LayoutInflater.from(BriefPopUpKeywordColorLayoutPreference.this.mContext);
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return BriefPopUpKeywordColorLayoutPreference.this.mCustomTextFilterList.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return 0L;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view =
                        this.mInflater.inflate(
                                R.layout.brief_popup_keyword_text_color_item_layout,
                                viewGroup,
                                false);
            }
            EditText editText = (EditText) view.findViewById(R.id.text_name);
            Button button = (Button) view.findViewById(R.id.text_color_icon);
            Button button2 = (Button) view.findViewById(R.id.delete_icon);
            String str =
                    (String)
                            BriefPopUpKeywordColorLayoutPreference.this.mCustomTextFilterList.get(
                                    (getCount() - i) - 1);
            if (str == null) {
                return null;
            }
            if (getCount() - 1 == i) {
                view.findViewById(R.id.normal_divider).setVisibility(8);
            }
            editText.setPrivateImeOptions("disableImage=true");
            editText.setImeOptions(editText.getImeOptions() | 33554432);
            editText.setText(str);
            editText.setTag(str);
            editText.setOnKeyListener(new AnonymousClass1());
            int i2 = 1;
            editText.setOnEditorActionListener(new AnonymousClass3(i2, this));
            editText.setOnFocusChangeListener(new AnonymousClass1(i2, this));
            editText.addTextChangedListener(
                    BriefPopUpKeywordColorLayoutPreference.this.new CustomWhatcher(view));
            int loadCustomTextColor =
                    BriefPopUpUtils.loadCustomTextColor(
                            BriefPopUpKeywordColorLayoutPreference.this.mContext, str);
            if (button != null) {
                Drawable drawable =
                        BriefPopUpKeywordColorLayoutPreference.this.mContext.getDrawable(
                                R.drawable.brief_popup_keyword_text_color_icon_bg);
                if (drawable != null && (drawable instanceof GradientDrawable)) {
                    GradientDrawable gradientDrawable = (GradientDrawable) drawable;
                    gradientDrawable.setColor(loadCustomTextColor);
                    button.setBackground(gradientDrawable);
                }
                if (button.getTag() != null) {
                    button.getTag();
                }
            }
            button2.setTag(Integer.valueOf((getCount() - i) - 1));
            button2.setOnClickListener(
                    BriefPopUpKeywordColorLayoutPreference.this.mCustomTextColorClickListener);
            button.setTag(Integer.valueOf((getCount() - i) - 1));
            button.setOnClickListener(
                    BriefPopUpKeywordColorLayoutPreference.this.mCustomTextColorClickListener);
            view.setTag(Integer.valueOf((getCount() - i) - 1));
            return view;
        }

        @Override // android.widget.BaseAdapter
        public final void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            BriefPopUpKeywordColorLayoutPreference briefPopUpKeywordColorLayoutPreference =
                    BriefPopUpKeywordColorLayoutPreference.this;
            TextColorListAdapter textColorListAdapter =
                    briefPopUpKeywordColorLayoutPreference.mTextListAdapter;
            ListView listView = briefPopUpKeywordColorLayoutPreference.mTextListView;
            briefPopUpKeywordColorLayoutPreference.getClass();
            int makeMeasureSpec =
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), Integer.MIN_VALUE);
            int i = 0;
            for (int i2 = 0; i2 < listView.getCount(); i2++) {
                View view = textColorListAdapter.getView(i2, null, listView);
                view.measure(makeMeasureSpec, 0);
                i += view.getMeasuredHeight();
            }
            ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
            layoutParams.height = ((listView.getCount() - 1) * listView.getDividerHeight()) + i;
            listView.setLayoutParams(layoutParams);
            listView.requestLayout();
        }
    }

    /* renamed from: -$$Nest$minitCustomView, reason: not valid java name */
    public static void m1260$$Nest$minitCustomView(
            BriefPopUpKeywordColorLayoutPreference briefPopUpKeywordColorLayoutPreference) {
        TextView textView = briefPopUpKeywordColorLayoutPreference.mMainText;
        if (textView == null || briefPopUpKeywordColorLayoutPreference.mNotiIconBg == null) {
            return;
        }
        textView.setText(
                briefPopUpKeywordColorLayoutPreference
                        .mContext
                        .getResources()
                        .getString(R.string.brief_popup_sample_text_title));
        briefPopUpKeywordColorLayoutPreference.mNotiIconBg.setBackgroundTintList(
                ColorStateList.valueOf(
                        briefPopUpKeywordColorLayoutPreference.mContext.getColor(
                                R.color.brief_popup_sample_icon_color)));
    }

    /* renamed from: -$$Nest$msaveAppCustomColorText, reason: not valid java name */
    public static void m1261$$Nest$msaveAppCustomColorText(
            BriefPopUpKeywordColorLayoutPreference briefPopUpKeywordColorLayoutPreference,
            CharSequence charSequence) {
        AnonymousClass7 anonymousClass7;
        briefPopUpKeywordColorLayoutPreference.getClass();
        if (briefPopUpKeywordColorLayoutPreference.isAddAvailable(charSequence.toString())) {
            String charSequence2 = charSequence.toString();
            int color =
                    briefPopUpKeywordColorLayoutPreference.mContext.getColor(
                            R.color.brief_popup_edit_text_predefine_color);
            briefPopUpKeywordColorLayoutPreference.mCustomTextFilterList.add(charSequence2);
            BriefPopUpUtils.saveCustomTextColor(
                    briefPopUpKeywordColorLayoutPreference.mContext, color, charSequence2);
            briefPopUpKeywordColorLayoutPreference.mTextListAdapter.notifyDataSetChanged();
            String charSequence3 = charSequence.toString();
            TextView textView = briefPopUpKeywordColorLayoutPreference.mMainText;
            if (textView != null) {
                textView.setText(charSequence3);
            }
            Handler handler = customViewHandler;
            if (handler != null
                    && (anonymousClass7 = briefPopUpKeywordColorLayoutPreference.resetCustomView)
                            != null) {
                handler.removeCallbacks(anonymousClass7);
                handler.postDelayed(briefPopUpKeywordColorLayoutPreference.resetCustomView, 3000L);
            }
        }
        if (briefPopUpKeywordColorLayoutPreference.mEditTextView.getText().toString().length()
                <= 50) {
            briefPopUpKeywordColorLayoutPreference.mEditTextView.setText(ApnSettings.MVNO_NONE);
        }
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference$4] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference$4] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference$6] */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference$7] */
    public BriefPopUpKeywordColorLayoutPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "BriefPopUpKeywordColorLayoutPreference";
        final int i = 0;
        this.mCoustomColorOnClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference.4
                    public final /* synthetic */ BriefPopUpKeywordColorLayoutPreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                if (view.getId() == R.id.custom_text_plus_btn) {
                                    BriefPopUpKeywordColorLayoutPreference
                                            briefPopUpKeywordColorLayoutPreference = this.this$0;
                                    BriefPopUpKeywordColorLayoutPreference
                                            .m1261$$Nest$msaveAppCustomColorText(
                                                    briefPopUpKeywordColorLayoutPreference,
                                                    briefPopUpKeywordColorLayoutPreference
                                                            .mEditTextView.getText());
                                    ((InputMethodManager)
                                                    this.this$0.mContext.getSystemService(
                                                            "input_method"))
                                            .hideSoftInputFromWindow(
                                                    this.this$0.mEditTextView.getWindowToken(), 0);
                                    this.this$0.checkTextAvailable();
                                    break;
                                }
                                break;
                            default:
                                int id = view.getId();
                                if (id != R.id.delete_icon) {
                                    if (id == R.id.text_color_icon) {
                                        this.this$0.mSelectTextColorButton =
                                                (Button) view.findViewById(R.id.text_color_icon);
                                        String str =
                                                (String)
                                                        this.this$0.mCustomTextFilterList.get(
                                                                ((Integer) view.getTag())
                                                                        .intValue());
                                        BriefPopUpKeywordColorLayoutPreference
                                                briefPopUpKeywordColorLayoutPreference2 =
                                                        this.this$0;
                                        briefPopUpKeywordColorLayoutPreference2.mPreviousColor =
                                                BriefPopUpUtils.loadCustomTextColor(
                                                        briefPopUpKeywordColorLayoutPreference2
                                                                .mContext,
                                                        str);
                                        this.this$0.showSeslColorPicker();
                                        break;
                                    }
                                } else {
                                    int intValue = ((Integer) view.getTag()).intValue();
                                    BriefPopUpKeywordColorLayoutPreference
                                            briefPopUpKeywordColorLayoutPreference3 = this.this$0;
                                    View childAt =
                                            briefPopUpKeywordColorLayoutPreference3.mTextListView
                                                    .getChildAt(
                                                            (briefPopUpKeywordColorLayoutPreference3
                                                                                    .mTextListAdapter
                                                                                    .getCount()
                                                                            - intValue)
                                                                    - 1);
                                    if (childAt != null) {
                                        EditText editText =
                                                (EditText) childAt.findViewById(R.id.text_name);
                                        if (editText != null) {
                                            editText.setOnFocusChangeListener(null);
                                            TextColorListAdapter.m1262$$Nest$mfinishEdit(
                                                    briefPopUpKeywordColorLayoutPreference3
                                                            .mTextListAdapter,
                                                    editText);
                                        }
                                        BriefPopUpUtils.removeCustomTextColor(
                                                briefPopUpKeywordColorLayoutPreference3.mContext,
                                                (String)
                                                        briefPopUpKeywordColorLayoutPreference3
                                                                .mCustomTextFilterList.get(
                                                                intValue));
                                        String str2 =
                                                (String)
                                                        briefPopUpKeywordColorLayoutPreference3
                                                                .mCustomTextFilterList.get(
                                                                intValue);
                                        briefPopUpKeywordColorLayoutPreference3
                                                .mCustomTextFilterList.remove(intValue);
                                        briefPopUpKeywordColorLayoutPreference3.mTextListAdapter
                                                .notifyDataSetChanged();
                                        AccessibilityManager accessibilityManager =
                                                (AccessibilityManager)
                                                        briefPopUpKeywordColorLayoutPreference3
                                                                .mContext.getSystemService(
                                                                "accessibility");
                                        if (accessibilityManager != null
                                                && accessibilityManager.isEnabled()) {
                                            AccessibilityEvent obtain =
                                                    AccessibilityEvent.obtain(
                                                            NetworkAnalyticsConstants.DataPoints
                                                                    .FLAG_SOURCE_PORT);
                                            obtain.getText().clear();
                                            List<CharSequence> text = obtain.getText();
                                            StringBuilder m =
                                                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                            .m(str2);
                                            m.append(
                                                    briefPopUpKeywordColorLayoutPreference3
                                                            .mContext
                                                            .getResources()
                                                            .getString(
                                                                    R.string
                                                                            .removed_color_by_keyword));
                                            text.add(m.toString());
                                            accessibilityManager.sendAccessibilityEvent(obtain);
                                        }
                                        briefPopUpKeywordColorLayoutPreference3
                                                .checkTextAvailable();
                                    }
                                    ListView listView = this.this$0.mTextListView;
                                    if (listView != null) {
                                        listView.clearFocus();
                                    }
                                    BriefPopUpKeywordColorLayoutPreference
                                            .m1260$$Nest$minitCustomView(this.this$0);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mCustomTextColorClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference.4
                    public final /* synthetic */ BriefPopUpKeywordColorLayoutPreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                if (view.getId() == R.id.custom_text_plus_btn) {
                                    BriefPopUpKeywordColorLayoutPreference
                                            briefPopUpKeywordColorLayoutPreference = this.this$0;
                                    BriefPopUpKeywordColorLayoutPreference
                                            .m1261$$Nest$msaveAppCustomColorText(
                                                    briefPopUpKeywordColorLayoutPreference,
                                                    briefPopUpKeywordColorLayoutPreference
                                                            .mEditTextView.getText());
                                    ((InputMethodManager)
                                                    this.this$0.mContext.getSystemService(
                                                            "input_method"))
                                            .hideSoftInputFromWindow(
                                                    this.this$0.mEditTextView.getWindowToken(), 0);
                                    this.this$0.checkTextAvailable();
                                    break;
                                }
                                break;
                            default:
                                int id = view.getId();
                                if (id != R.id.delete_icon) {
                                    if (id == R.id.text_color_icon) {
                                        this.this$0.mSelectTextColorButton =
                                                (Button) view.findViewById(R.id.text_color_icon);
                                        String str =
                                                (String)
                                                        this.this$0.mCustomTextFilterList.get(
                                                                ((Integer) view.getTag())
                                                                        .intValue());
                                        BriefPopUpKeywordColorLayoutPreference
                                                briefPopUpKeywordColorLayoutPreference2 =
                                                        this.this$0;
                                        briefPopUpKeywordColorLayoutPreference2.mPreviousColor =
                                                BriefPopUpUtils.loadCustomTextColor(
                                                        briefPopUpKeywordColorLayoutPreference2
                                                                .mContext,
                                                        str);
                                        this.this$0.showSeslColorPicker();
                                        break;
                                    }
                                } else {
                                    int intValue = ((Integer) view.getTag()).intValue();
                                    BriefPopUpKeywordColorLayoutPreference
                                            briefPopUpKeywordColorLayoutPreference3 = this.this$0;
                                    View childAt =
                                            briefPopUpKeywordColorLayoutPreference3.mTextListView
                                                    .getChildAt(
                                                            (briefPopUpKeywordColorLayoutPreference3
                                                                                    .mTextListAdapter
                                                                                    .getCount()
                                                                            - intValue)
                                                                    - 1);
                                    if (childAt != null) {
                                        EditText editText =
                                                (EditText) childAt.findViewById(R.id.text_name);
                                        if (editText != null) {
                                            editText.setOnFocusChangeListener(null);
                                            TextColorListAdapter.m1262$$Nest$mfinishEdit(
                                                    briefPopUpKeywordColorLayoutPreference3
                                                            .mTextListAdapter,
                                                    editText);
                                        }
                                        BriefPopUpUtils.removeCustomTextColor(
                                                briefPopUpKeywordColorLayoutPreference3.mContext,
                                                (String)
                                                        briefPopUpKeywordColorLayoutPreference3
                                                                .mCustomTextFilterList.get(
                                                                intValue));
                                        String str2 =
                                                (String)
                                                        briefPopUpKeywordColorLayoutPreference3
                                                                .mCustomTextFilterList.get(
                                                                intValue);
                                        briefPopUpKeywordColorLayoutPreference3
                                                .mCustomTextFilterList.remove(intValue);
                                        briefPopUpKeywordColorLayoutPreference3.mTextListAdapter
                                                .notifyDataSetChanged();
                                        AccessibilityManager accessibilityManager =
                                                (AccessibilityManager)
                                                        briefPopUpKeywordColorLayoutPreference3
                                                                .mContext.getSystemService(
                                                                "accessibility");
                                        if (accessibilityManager != null
                                                && accessibilityManager.isEnabled()) {
                                            AccessibilityEvent obtain =
                                                    AccessibilityEvent.obtain(
                                                            NetworkAnalyticsConstants.DataPoints
                                                                    .FLAG_SOURCE_PORT);
                                            obtain.getText().clear();
                                            List<CharSequence> text = obtain.getText();
                                            StringBuilder m =
                                                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                            .m(str2);
                                            m.append(
                                                    briefPopUpKeywordColorLayoutPreference3
                                                            .mContext
                                                            .getResources()
                                                            .getString(
                                                                    R.string
                                                                            .removed_color_by_keyword));
                                            text.add(m.toString());
                                            accessibilityManager.sendAccessibilityEvent(obtain);
                                        }
                                        briefPopUpKeywordColorLayoutPreference3
                                                .checkTextAvailable();
                                    }
                                    ListView listView = this.this$0.mTextListView;
                                    if (listView != null) {
                                        listView.clearFocus();
                                    }
                                    BriefPopUpKeywordColorLayoutPreference
                                            .m1260$$Nest$minitCustomView(this.this$0);
                                    break;
                                }
                                break;
                        }
                    }
                };
        this.mTextColorSetListener =
                new SeslColorPickerDialog
                        .OnColorSetListener() { // from class:
                                                // com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference.6
                    @Override // androidx.picker3.app.SeslColorPickerDialog.OnColorSetListener
                    public final void onColorSet(int i3) {
                        AnonymousClass7 anonymousClass7;
                        BriefPopUpKeywordColorLayoutPreference
                                briefPopUpKeywordColorLayoutPreference =
                                        BriefPopUpKeywordColorLayoutPreference.this;
                        Log.i(
                                briefPopUpKeywordColorLayoutPreference.TAG,
                                "Text Color set Listener : " + Integer.toHexString(i3));
                        Context context2 = briefPopUpKeywordColorLayoutPreference.mContext;
                        String string =
                                Settings.Global.getString(
                                        context2.getContentResolver(),
                                        "edgelighting_recently_used_color");
                        if (string == null || string.isEmpty()) {
                            Settings.Global.putString(
                                    context2.getContentResolver(),
                                    "edgelighting_recently_used_color",
                                    i3 + ";");
                        } else {
                            String[] split = string.split(";");
                            int length = split.length < 6 ? split.length : 6;
                            String str = ApnSettings.MVNO_NONE;
                            for (int i4 = 0; i4 < length; i4++) {
                                if (!split[i4].equals(Integer.toString(i3))) {
                                    str =
                                            ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                            .m(str),
                                                    split[i4],
                                                    ";");
                                }
                            }
                            Settings.Global.putString(
                                    context2.getContentResolver(),
                                    "edgelighting_recently_used_color",
                                    i3 + ";" + str);
                        }
                        Button button =
                                briefPopUpKeywordColorLayoutPreference.mSelectTextColorButton;
                        if (button != null) {
                            Drawable drawable =
                                    briefPopUpKeywordColorLayoutPreference.mContext.getDrawable(
                                            R.drawable.brief_popup_keyword_text_color_icon_bg);
                            if (drawable != null && (drawable instanceof GradientDrawable)) {
                                GradientDrawable gradientDrawable = (GradientDrawable) drawable;
                                gradientDrawable.setColor(i3);
                                button.setBackground(gradientDrawable);
                            }
                            if (button.getTag() != null) {
                                button.getTag();
                            }
                            View view = briefPopUpKeywordColorLayoutPreference.mNotiIconBg;
                            if (view != null) {
                                view.setBackgroundTintList(ColorStateList.valueOf(i3));
                            }
                            BriefPopUpUtils.saveCustomTextColor(
                                    briefPopUpKeywordColorLayoutPreference.mContext,
                                    i3,
                                    (String)
                                            briefPopUpKeywordColorLayoutPreference
                                                    .mCustomTextFilterList.get(
                                                    ((Integer)
                                                                    briefPopUpKeywordColorLayoutPreference
                                                                            .mSelectTextColorButton
                                                                            .getTag())
                                                            .intValue()));
                            TextView textView = briefPopUpKeywordColorLayoutPreference.mMainText;
                            if (textView != null) {
                                textView.setText(
                                        (CharSequence)
                                                briefPopUpKeywordColorLayoutPreference
                                                        .mCustomTextFilterList.get(
                                                        ((Integer)
                                                                        briefPopUpKeywordColorLayoutPreference
                                                                                .mSelectTextColorButton
                                                                                .getTag())
                                                                .intValue()));
                            }
                            Handler handler =
                                    BriefPopUpKeywordColorLayoutPreference.customViewHandler;
                            if (handler == null
                                    || (anonymousClass7 =
                                                    briefPopUpKeywordColorLayoutPreference
                                                            .resetCustomView)
                                            == null) {
                                return;
                            }
                            handler.removeCallbacks(anonymousClass7);
                            handler.postDelayed(
                                    briefPopUpKeywordColorLayoutPreference.resetCustomView, 3000L);
                        }
                    }
                };
        this.resetCustomView =
                new Runnable() { // from class:
                                 // com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference.7
                    @Override // java.lang.Runnable
                    public final void run() {
                        BriefPopUpKeywordColorLayoutPreference.m1260$$Nest$minitCustomView(
                                BriefPopUpKeywordColorLayoutPreference.this);
                    }
                };
        this.mContext = context;
    }

    public final void checkTextAvailable() {
        EditText editText = this.mEditTextView;
        if (editText == null || this.mTextAddButton == null || this.mEditWrongText == null) {
            return;
        }
        String editable = editText.getText().toString();
        if (isAddAvailable(editable)) {
            this.mEditWrongText.setVisibility(4);
            if (editable.length() == 50) {
                String string =
                        this.mContext
                                .getResources()
                                .getString(R.string.brief_popup_keyword_color_text_too_long, 50);
                TextView textView = this.mEditWrongText;
                if (textView != null) {
                    textView.setText(string);
                }
                this.mEditWrongText.setVisibility(0);
            }
            this.mTextAddButton.setBackgroundTintList(
                    ColorStateList.valueOf(
                            this.mContext.getColor(R.color.brief_popup_edit_text_color_add_icon)));
            this.mTextAddButton.setEnabled(true);
            return;
        }
        String string2 =
                editable.contains(";")
                        ? this.mContext
                                .getResources()
                                .getString(R.string.brief_popup_keyword_color_delimeter_error_text)
                        : editable.length() > 50
                                ? this.mContext
                                        .getResources()
                                        .getString(
                                                R.string.brief_popup_keyword_color_text_too_long,
                                                50)
                                : this.mContext
                                        .getResources()
                                        .getString(
                                                R.string.brief_popup_keyword_color_wrong_text,
                                                editable);
        TextView textView2 = this.mEditWrongText;
        if (textView2 != null) {
            textView2.setText(string2);
        }
        if (editable.isEmpty() || editable.trim().isEmpty()) {
            this.mEditWrongText.setVisibility(4);
        } else {
            this.mEditWrongText.setVisibility(0);
        }
        this.mTextAddButton.setBackgroundTintList(
                ColorStateList.valueOf(
                        this.mContext.getColor(
                                R.color.brief_popup_edit_text_color_add_icon_disable)));
        this.mTextAddButton.setEnabled(false);
    }

    public final boolean isAddAvailable(String str) {
        return (str == null
                        || str.isEmpty()
                        || str.trim().isEmpty()
                        || str.contains(";")
                        || str.length() > 50
                        || BriefPopUpUtils.loadCustomTextColor(this.mContext, str) != -1)
                ? false
                : true;
    }

    @Override // com.android.settingslib.widget.LayoutPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Button button = (Button) this.mRootView.findViewById(R.id.custom_text_plus_btn);
        this.mTextAddButton = button;
        button.setOnClickListener(this.mCoustomColorOnClickListener);
        EditText editText = (EditText) this.mRootView.findViewById(R.id.custom_text_edit);
        this.mEditTextView = editText;
        editText.setPrivateImeOptions("disableImage=true");
        this.mEditTextView.setOnFocusChangeListener(new AnonymousClass1(0, this));
        TextView textView = (TextView) this.mRootView.findViewById(R.id.custom_text_wrong);
        this.mEditWrongText = textView;
        textView.setVisibility(4);
        this.mEditTextView.addTextChangedListener(
                new TextWatcher() { // from class:
                                    // com.samsung.android.settings.notification.brief.BriefPopUpKeywordColorLayoutPreference.2
                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        BriefPopUpKeywordColorLayoutPreference
                                briefPopUpKeywordColorLayoutPreference =
                                        BriefPopUpKeywordColorLayoutPreference.this;
                        Handler handler = BriefPopUpKeywordColorLayoutPreference.customViewHandler;
                        briefPopUpKeywordColorLayoutPreference.checkTextAvailable();
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}
                });
        this.mEditTextView.setOnEditorActionListener(new AnonymousClass3(0, this));
        this.mTextListView = (ListView) this.mRootView.findViewById(R.id.text_color_list);
        this.mCustomTextFilterList = new ArrayList();
        TextColorListAdapter textColorListAdapter = new TextColorListAdapter();
        this.mTextListAdapter = textColorListAdapter;
        this.mTextListView.setAdapter((ListAdapter) textColorListAdapter);
        this.mTextListView.setItemsCanFocus(true);
        HashMap loadCustomTextList = BriefPopUpUtils.loadCustomTextList(this.mContext);
        if (loadCustomTextList != null) {
            Iterator it = loadCustomTextList.keySet().iterator();
            while (it.hasNext()) {
                this.mCustomTextFilterList.add((String) it.next());
            }
            this.mTextListAdapter.notifyDataSetChanged();
        }
        this.mNotiIconBg = this.mRootView.findViewById(R.id.brief_popup_sample_icon_bg);
        this.mMainText = (TextView) this.mRootView.findViewById(R.id.main_text);
        this.mEditTextView.setText(this.mEditTextViewName);
        checkTextAvailable();
        Activity activity = (Activity) this.mContext;
        activity.getWindow().setDecorFitsSystemWindows(false);
        View findViewById = activity.findViewById(android.R.id.content);
        SeslContentViewInsetsCallback seslContentViewInsetsCallback =
                new SeslContentViewInsetsCallback(
                        WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(),
                        WindowInsets.Type.ime());
        findViewById.setWindowInsetsAnimationCallback(seslContentViewInsetsCallback);
        findViewById.setOnApplyWindowInsetsListener(seslContentViewInsetsCallback);
    }

    @Override // androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        try {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            this.mEditTextViewName = savedState.name;
        } catch (Exception e) {
            Log.e(this.TAG, "ClassCastException in SavedState", e);
        }
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.name = this.mEditTextView.getText().toString();
        return savedState;
    }

    public final void showSeslColorPicker() {
        int[] iArr;
        Context context = this.mContext;
        AnonymousClass6 anonymousClass6 = this.mTextColorSetListener;
        int i = this.mPreviousColor;
        String string =
                Settings.Global.getString(
                        context.getContentResolver(), "edgelighting_recently_used_color");
        if (string == null || string.isEmpty()) {
            iArr = null;
        } else {
            String[] split = string.split(";");
            iArr = new int[split.length];
            for (int i2 = 0; i2 < split.length; i2++) {
                iArr[i2] = Integer.parseInt(split[i2]);
            }
        }
        SeslColorPickerDialog seslColorPickerDialog =
                new SeslColorPickerDialog(context, anonymousClass6, i, iArr, false);
        this.mColorPickerDialog = seslColorPickerDialog;
        SeslColorPicker seslColorPicker = seslColorPickerDialog.mColorPicker;
        View findViewById = seslColorPicker.findViewById(R.id.sesl_last_used_color_slot);
        seslColorPicker.mEyeDropperView.setVisibility(8);
        findViewById.setVisibility(0);
        this.mColorPickerDialog.show();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomWhatcher implements TextWatcher {
        public final View convertView;

        public CustomWhatcher(View view) {
            this.convertView = view;
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            TextView textView = (TextView) this.convertView.findViewById(R.id.text_wrong);
            EditText editText = (EditText) this.convertView.findViewById(R.id.text_name);
            String editable2 = editable.toString();
            if (!editText.hasFocus()
                    || editable2 == null
                    || editable2.equals(editText.getTag().toString())) {
                return;
            }
            BriefPopUpKeywordColorLayoutPreference briefPopUpKeywordColorLayoutPreference =
                    BriefPopUpKeywordColorLayoutPreference.this;
            Handler handler = BriefPopUpKeywordColorLayoutPreference.customViewHandler;
            if (briefPopUpKeywordColorLayoutPreference.isAddAvailable(editable2)) {
                textView.setVisibility(4);
            } else if (editable2.isEmpty() || editable2.trim().isEmpty()) {
                textView.setVisibility(4);
            } else {
                textView.setVisibility(0);
                textView.setText(
                        BriefPopUpKeywordColorLayoutPreference.this
                                .mContext
                                .getResources()
                                .getString(
                                        R.string.brief_popup_keyword_color_wrong_text, editable2));
            }
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
    }
}
