package com.samsung.android.settings.inputmethod;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MousePointerSettingsFragment extends SettingsPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass5(R.xml.sec_mouse_pointer_settings);
    public View[] mColorEntityViews;
    public Context mContext;
    public int mLastProgress;
    public ImageView mMouseCursorImg;
    public View mMousePointerView;
    public View mMousePreview;
    public AnonymousClass3 mPointerColorObserver;
    public AnonymousClass3 mPointerSizeObserver;
    public SeslSeekBar mSeekBar;
    public boolean mTouchInProgress;
    public int[] pointerColorList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.MousePointerSettingsFragment$5, reason: invalid class name */
    public final class AnonymousClass5 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            String groupName = GtsGroup.GROUP_KEY_MOUSE_AND_TRACKPAD.getGroupName();
            hashMap.put("mouse_pointer_size", groupName);
            hashMap.put("mouse_custom_pointer_color", groupName);
            return hashMap;
        }
    }

    /* renamed from: -$$Nest$msetMousePointerSize, reason: not valid java name */
    public static void m1239$$Nest$msetMousePointerSize(
            MousePointerSettingsFragment mousePointerSettingsFragment, Context context, int i) {
        mousePointerSettingsFragment.getClass();
        if (i < 1 || i > 9) {
            throw new IllegalArgumentException("out of range");
        }
        Settings.System.putInt(context.getContentResolver(), "mouse_pointer_size_step", i);
        LoggingHelper.insertEventLogging(770105, 77018, i);
    }

    public final View createMousePointerView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View findViewById;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        if ((Utils.isTablet()
                        && ActivityEmbeddingController.getInstance(getActivity())
                                .isActivityEmbedded(getActivity()))
                || Rune.isSamsungDexMode(this.mContext)) {
            this.mMousePointerView =
                    layoutInflater.inflate(R.layout.sec_mouse_pointer_settings_tablet, viewGroup);
        } else {
            this.mMousePointerView =
                    layoutInflater.inflate(R.layout.sec_mouse_pointer_settings, viewGroup);
        }
        View findViewById2 = this.mMousePointerView.findViewById(R.id.cursorImgContainer);
        this.mMousePreview = findViewById2;
        findViewById2.semSetRoundedCorners(15);
        this.mMousePreview.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mMouseCursorImg = (ImageView) this.mMousePreview.findViewById(R.id.mouse_cursor_img);
        LinearLayout linearLayout =
                (LinearLayout) this.mMousePointerView.findViewById(R.id.mouseColorViewContainer);
        boolean z = getContext().getResources().getConfiguration().orientation == 2;
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        if ((Utils.isTablet()
                        && ActivityEmbeddingController.getInstance(getActivity())
                                .isActivityEmbedded(getActivity()))
                || Rune.isSamsungDexMode(this.mContext)) {
            ((NestedScrollView)
                            this.mMousePointerView.findViewById(
                                    R.id.sec_mouse_pointer_settings_layout))
                    .setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        } else if (z) {
            ((LinearLayout)
                            this.mMousePointerView.findViewById(
                                    R.id.sec_mouse_pointer_settings_layout_land))
                    .setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        } else {
            ((NestedScrollView)
                            this.mMousePointerView.findViewById(
                                    R.id.sec_mouse_pointer_settings_layout))
                    .setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        }
        if ((getActivity() instanceof SecSettingsBaseActivity)
                && (findViewById = getActivity().findViewById(R.id.round_corner)) != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        updatePointerColor();
        updatePointerSize();
        this.pointerColorList =
                getContext().getResources().getIntArray(R.array.mouse_pointer_color_array);
        this.mColorEntityViews =
                new View[] {
                    linearLayout.findViewById(R.id.mouse_pointer_color_black),
                    linearLayout.findViewById(R.id.mouse_pointer_color_white),
                    linearLayout.findViewById(R.id.mouse_pointer_color_green),
                    linearLayout.findViewById(R.id.mouse_pointer_color_yellow),
                    linearLayout.findViewById(R.id.mouse_pointer_color_orange),
                    linearLayout.findViewById(R.id.mouse_pointer_color_purple),
                    linearLayout.findViewById(R.id.mouse_pointer_color_blue)
                };
        int i = 0;
        while (true) {
            View[] viewArr = this.mColorEntityViews;
            if (i >= viewArr.length) {
                break;
            }
            ((ImageView) viewArr[i].findViewById(R.id.pointer_color_item))
                    .setColorFilter(this.pointerColorList[i]);
            ((ImageView) this.mColorEntityViews[i].findViewById(R.id.pointer_color_check_img))
                    .setColorFilter(
                            this.mContext
                                    .getResources()
                                    .getColor(
                                            i == 0
                                                    ? R.color.mouse_pointer_color_check_img_white
                                                    : R.color.mouse_pointer_color_check_img_black),
                            PorterDuff.Mode.SRC_ATOP);
            this.mColorEntityViews[i].setContentDescription(getContentDescriptionAtIndex(i, false));
            this.mColorEntityViews[i].setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.inputmethod.MousePointerSettingsFragment.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            MousePointerSettingsFragment mousePointerSettingsFragment =
                                    MousePointerSettingsFragment.this;
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    MousePointerSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                            int currentPosition = mousePointerSettingsFragment.getCurrentPosition();
                            MousePointerSettingsFragment mousePointerSettingsFragment2 =
                                    MousePointerSettingsFragment.this;
                            int i2 = 0;
                            mousePointerSettingsFragment2.handleClickOnColorItem(
                                    mousePointerSettingsFragment2
                                            .mColorEntityViews[currentPosition],
                                    false);
                            ContentResolver contentResolver =
                                    MousePointerSettingsFragment.this.mContext.getContentResolver();
                            MousePointerSettingsFragment mousePointerSettingsFragment3 =
                                    MousePointerSettingsFragment.this;
                            int[] iArr = mousePointerSettingsFragment3.pointerColorList;
                            int id = view.getId();
                            while (true) {
                                View[] viewArr2 = mousePointerSettingsFragment3.mColorEntityViews;
                                if (i2 >= viewArr2.length) {
                                    i2 = -1;
                                    break;
                                } else if (id == viewArr2[i2].getId()) {
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                            Settings.System.putInt(
                                    contentResolver, "mouse_pointer_color", iArr[i2]);
                            MousePointerSettingsFragment.this.handleClickOnColorItem(view, true);
                            MousePointerSettingsFragment mousePointerSettingsFragment4 =
                                    MousePointerSettingsFragment.this;
                            view.announceForAccessibility(
                                    mousePointerSettingsFragment4.getContentDescriptionAtIndex(
                                            mousePointerSettingsFragment4.getCurrentPosition(),
                                            true));
                        }
                    });
            i++;
        }
        int currentPosition = getCurrentPosition();
        this.mColorEntityViews[currentPosition]
                .findViewById(R.id.pointer_color_check_img)
                .setVisibility(0);
        this.mColorEntityViews[currentPosition].setContentDescription(
                getContentDescriptionAtIndex(currentPosition, true));
        LinearLayout linearLayout2 =
                (LinearLayout) this.mMousePointerView.findViewById(R.id.customPointerContainer);
        linearLayout2.semSetRoundedCorners(15);
        linearLayout2.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) this.mMousePointerView.findViewById(R.id.custom_seekbar);
        TextView textView = (TextView) this.mMousePointerView.findViewById(R.id.left_text);
        TextView textView2 = (TextView) this.mMousePointerView.findViewById(R.id.right_text);
        textView.setText(R.string.sec_small);
        textView2.setText(R.string.sec_large);
        int i2 =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "mouse_pointer_size_step", 1);
        if (seslSeekBar != this.mSeekBar) {
            this.mSeekBar = seslSeekBar;
            Settings.System.getInt(getContext().getContentResolver(), "mouse_pointer_size_step", 1);
            this.mSeekBar.setMax(8);
            this.mSeekBar.setProgress(i2 - 1);
            this.mSeekBar.setContentDescription(Integer.toString(i2));
            this.mSeekBar.setMode(8);
        } else {
            this.mSeekBar.setProgress(
                    Settings.System.getInt(
                                    getContext().getContentResolver(), "mouse_pointer_size_step", 1)
                            - 1);
        }
        seslSeekBar.setOnSeekBarChangeListener(
                new SeslSeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.samsung.android.settings.inputmethod.MousePointerSettingsFragment.2
                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(
                            SeslSeekBar seslSeekBar2, int i3, boolean z2) {
                        MousePointerSettingsFragment mousePointerSettingsFragment =
                                MousePointerSettingsFragment.this;
                        mousePointerSettingsFragment.getClass();
                        if (!mousePointerSettingsFragment.mTouchInProgress && z2) {
                            MousePointerSettingsFragment.m1239$$Nest$msetMousePointerSize(
                                    mousePointerSettingsFragment,
                                    mousePointerSettingsFragment.getContext(),
                                    i3 + 1);
                        }
                        if (i3 != mousePointerSettingsFragment.mLastProgress) {
                            seslSeekBar2.performHapticFeedback(4);
                            mousePointerSettingsFragment.mLastProgress = i3;
                        }
                    }

                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeslSeekBar seslSeekBar2) {
                        MousePointerSettingsFragment.this.mTouchInProgress = true;
                    }

                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeslSeekBar seslSeekBar2) {
                        MousePointerSettingsFragment mousePointerSettingsFragment =
                                MousePointerSettingsFragment.this;
                        mousePointerSettingsFragment.mTouchInProgress = false;
                        MousePointerSettingsFragment.m1239$$Nest$msetMousePointerSize(
                                mousePointerSettingsFragment,
                                mousePointerSettingsFragment.getContext(),
                                mousePointerSettingsFragment.mSeekBar.getProgress() + 1);
                    }
                });
        return this.mMousePointerView;
    }

    public final String getContentDescriptionAtIndex(int i, boolean z) {
        String string;
        String string2 = this.mContext.getString(R.string.button_tts);
        switch (i) {
            case 0:
                string = this.mContext.getString(R.string.color_black);
                break;
            case 1:
                string = this.mContext.getString(R.string.color_white);
                break;
            case 2:
                string = this.mContext.getString(R.string.color_green);
                break;
            case 3:
                string = this.mContext.getString(R.string.color_yellow);
                break;
            case 4:
                string = this.mContext.getString(R.string.color_orange);
                break;
            case 5:
                string = this.mContext.getString(R.string.color_purple);
                break;
            case 6:
                string = this.mContext.getString(R.string.color_blue);
                break;
            default:
                return string2;
        }
        if (!z) {
            return AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(string, " ", string2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getString(R.string.selected));
        sb.append(" ");
        sb.append(string);
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, " ", string2);
    }

    public final int getCurrentPosition() {
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "mouse_pointer_color",
                        this.pointerColorList[0]);
        int[] intArray = getContext().getResources().getIntArray(R.array.mouse_pointer_color_array);
        for (int i2 = 0; i2 < intArray.length; i2++) {
            if (i == intArray[i2]) {
                return i2;
            }
        }
        return -1;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 770105;
    }

    public final void handleClickOnColorItem(View view, boolean z) {
        if (z) {
            view.findViewById(R.id.pointer_color_check_img).setVisibility(0);
            ((ImageView) view.findViewById(R.id.pointer_color_check_img))
                    .setColorFilter(
                            getCurrentPosition() == 0
                                    ? this.mContext
                                            .getResources()
                                            .getColor(R.color.mouse_pointer_color_check_img_white)
                                    : this.mContext
                                            .getResources()
                                            .getColor(R.color.mouse_pointer_color_check_img_black),
                            PorterDuff.Mode.SRC_ATOP);
        } else {
            view.findViewById(R.id.pointer_color_check_img).setVisibility(8);
        }
        view.setContentDescription(getContentDescriptionAtIndex(getCurrentPosition(), z));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        createMousePointerView(
                LayoutInflater.from(this.mContext),
                (ViewGroup) getActivity().findViewById(R.id.main_content));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setAutoRemoveInsetCategory(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContext = getContext();
        return createMousePointerView(layoutInflater, null);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.inputmethod.MousePointerSettingsFragment$3] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.settings.inputmethod.MousePointerSettingsFragment$3] */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mPointerColorObserver == null) {
            final int i = 0;
            this.mPointerColorObserver =
                    new ContentObserver(
                            this,
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.samsung.android.settings.inputmethod.MousePointerSettingsFragment.3
                        public final /* synthetic */ MousePointerSettingsFragment this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            switch (i) {
                                case 0:
                                    super.onChange(z);
                                    MousePointerSettingsFragment mousePointerSettingsFragment =
                                            this.this$0;
                                    BaseSearchIndexProvider baseSearchIndexProvider =
                                            MousePointerSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                                    mousePointerSettingsFragment.updatePointerColor();
                                    break;
                                default:
                                    super.onChange(z);
                                    MousePointerSettingsFragment mousePointerSettingsFragment2 =
                                            this.this$0;
                                    BaseSearchIndexProvider baseSearchIndexProvider2 =
                                            MousePointerSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                                    mousePointerSettingsFragment2.updatePointerSize();
                                    break;
                            }
                        }
                    };
        }
        if (this.mPointerSizeObserver == null) {
            final int i2 = 1;
            this.mPointerSizeObserver =
                    new ContentObserver(
                            this,
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.samsung.android.settings.inputmethod.MousePointerSettingsFragment.3
                        public final /* synthetic */ MousePointerSettingsFragment this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            switch (i2) {
                                case 0:
                                    super.onChange(z);
                                    MousePointerSettingsFragment mousePointerSettingsFragment =
                                            this.this$0;
                                    BaseSearchIndexProvider baseSearchIndexProvider =
                                            MousePointerSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                                    mousePointerSettingsFragment.updatePointerColor();
                                    break;
                                default:
                                    super.onChange(z);
                                    MousePointerSettingsFragment mousePointerSettingsFragment2 =
                                            this.this$0;
                                    BaseSearchIndexProvider baseSearchIndexProvider2 =
                                            MousePointerSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                                    mousePointerSettingsFragment2.updatePointerSize();
                                    break;
                            }
                        }
                    };
        }
        getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("mouse_pointer_color"),
                        false,
                        this.mPointerColorObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("mouse_pointer_size_step"),
                        false,
                        this.mPointerSizeObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getContentResolver().unregisterContentObserver(this.mPointerColorObserver);
        getContentResolver().unregisterContentObserver(this.mPointerSizeObserver);
    }

    public final void updatePointerColor() {
        int[] intArray = getContext().getResources().getIntArray(R.array.mouse_pointer_color_array);
        TypedArray obtainTypedArray =
                getContext()
                        .getResources()
                        .obtainTypedArray(R.array.mouse_pointer_color_drawable_array);
        int[] iArr = new int[obtainTypedArray.length()];
        int i = 0;
        for (int i2 = 0; i2 < obtainTypedArray.length(); i2++) {
            iArr[i2] = obtainTypedArray.getResourceId(i2, 0);
        }
        obtainTypedArray.recycle();
        int i3 = Settings.System.getInt(getContentResolver(), "mouse_pointer_color", intArray[0]);
        ImageView imageView = this.mMouseCursorImg;
        int[] intArray2 =
                getContext().getResources().getIntArray(R.array.mouse_pointer_color_array);
        while (true) {
            if (i >= intArray2.length) {
                i = -1;
                break;
            } else if (i3 == intArray2[i]) {
                break;
            } else {
                i++;
            }
        }
        imageView.setImageResource(iArr[i]);
    }

    public final void updatePointerSize() {
        int i = Settings.System.getInt(getContentResolver(), "mouse_pointer_size_step", 1);
        ((WindowManager) getContext().getSystemService("window"))
                .getDefaultDisplay()
                .getMetrics(new DisplayMetrics());
        ViewGroup.LayoutParams layoutParams = this.mMouseCursorImg.getLayoutParams();
        int dimension =
                (int) getResources().getDimension(R.dimen.mouse_pointer_color_item_content_size);
        int dimension2 =
                (int) getResources().getDimension(R.dimen.mouse_pointer_color_item_content_size);
        double d = i - ((i - 1) * 0.5d);
        layoutParams.width = (int) (dimension * d);
        layoutParams.height = (int) (dimension2 * d);
        this.mMouseCursorImg.setLayoutParams(layoutParams);
    }
}
