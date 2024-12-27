package com.samsung.android.settings.display;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FontPreviewBubbleListAdapter extends BaseAdapter {
    public final ArrayList mFontPreviewBubbleList = new ArrayList();
    public Typeface mTypeface;

    public final void addItem(String str, boolean z) {
        FontPreviewBubbleListItem fontPreviewBubbleListItem = new FontPreviewBubbleListItem();
        fontPreviewBubbleListItem.mMessageText = str;
        fontPreviewBubbleListItem.mIsIncoming = z;
        this.mFontPreviewBubbleList.add(fontPreviewBubbleListItem);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return this.mFontPreviewBubbleList.size();
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        return this.mFontPreviewBubbleList.get(i);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        Drawable drawable;
        if (view == null) {
            view =
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.font_preview_bubble_list_item, viewGroup, false);
        }
        LinearLayout linearLayout =
                (LinearLayout) view.findViewById(R.id.font_preview_message_bubble_item);
        TextView textView =
                (TextView) view.findViewById(R.id.font_preview_message_bubble_recv_icon);
        TextView textView2 = (TextView) view.findViewById(R.id.font_preview_message_bubble_text);
        ViewGroup viewGroup2 =
                (ViewGroup) view.findViewById(R.id.font_preview_message_bubble_background);
        FontPreviewBubbleListItem fontPreviewBubbleListItem =
                (FontPreviewBubbleListItem) this.mFontPreviewBubbleList.get(i);
        Resources resources = viewGroup.getResources();
        int dimensionPixelOffset =
                resources.getDimensionPixelOffset(R.dimen.message_text_left_right_padding);
        int dimensionPixelOffset2 =
                resources.getDimensionPixelOffset(R.dimen.message_text_top_padding);
        int dimensionPixelOffset3 =
                resources.getDimensionPixelOffset(R.dimen.message_text_bottom_padding);
        if (fontPreviewBubbleListItem.mIsIncoming) {
            linearLayout.setGravity(8388611);
            drawable = resources.getDrawable(R.drawable.font_preview_recv_message_background, null);
            textView.setText(ImsProfile.TIMER_NAME_A);
            textView.setTextColor(
                    resources.getColor(R.color.font_preview_recv_icon_text_color, null));
            textView.setBackground(
                    resources.getDrawable(R.drawable.font_preview_recv_icon_background, null));
            textView.setVisibility(0);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) viewGroup2.getLayoutParams();
            layoutParams.setMarginStart(
                    resources.getDimensionPixelSize(
                            R.dimen.sec_font_preview_message_start_padding));
            viewGroup2.setLayoutParams(layoutParams);
            textView2.setTextColor(
                    resources.getColor(R.color.font_preview_recv_bubble_text_color, null));
        } else {
            linearLayout.setGravity(8388613);
            drawable = resources.getDrawable(R.drawable.font_preview_send_message_background, null);
            textView.setVisibility(8);
            textView2.setTextColor(
                    resources.getColor(R.color.font_preview_send_bubble_text_color, null));
        }
        drawable.setAutoMirrored(true);
        viewGroup2.setBackground(drawable);
        viewGroup2.setPaddingRelative(
                dimensionPixelOffset,
                dimensionPixelOffset2,
                dimensionPixelOffset,
                dimensionPixelOffset3);
        textView2.setText(fontPreviewBubbleListItem.mMessageText);
        textView2.setTypeface(this.mTypeface);
        return view;
    }
}
