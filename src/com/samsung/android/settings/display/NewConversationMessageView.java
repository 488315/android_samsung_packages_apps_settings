package com.samsung.android.settings.display;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NewConversationMessageView extends LinearLayout {
    public TextView mContactRecvIconView;
    public LinearLayout mConversationItemView;
    public final boolean mIncoming;
    public final CharSequence mMessageText;
    public ViewGroup mMessageTextAndInfoView;
    public TextView mMessageTextView;

    public NewConversationMessageView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mConversationItemView = (LinearLayout) findViewById(R.id.conversation_item);
        this.mMessageTextAndInfoView = (ViewGroup) findViewById(R.id.message_text_and_info);
        this.mMessageTextView = (TextView) findViewById(R.id.message_text);
        this.mContactRecvIconView = (TextView) findViewById(R.id.conversation_icon_recv);
        this.mMessageTextView.setText(this.mMessageText);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Resources resources = getResources();
        int dimensionPixelOffset =
                resources.getDimensionPixelOffset(R.dimen.message_text_left_right_padding);
        int lineCount = this.mMessageTextView.getLineCount();
        if (lineCount == 1) {
            int dimensionPixelOffset2 =
                    (resources.getDimensionPixelOffset(R.dimen.message_item_line_count_one_height)
                                    - resources.getDimensionPixelOffset(
                                            R.dimen.message_item_text_line_count_one_height))
                            / 2;
            this.mMessageTextAndInfoView.setPaddingRelative(
                    dimensionPixelOffset,
                    dimensionPixelOffset2,
                    dimensionPixelOffset,
                    dimensionPixelOffset2);
        } else if (lineCount != 2) {
            int dimensionPixelOffset3 =
                    (resources.getDimensionPixelOffset(R.dimen.message_item_line_count_three_height)
                                    - resources.getDimensionPixelOffset(
                                            R.dimen.message_item_text_line_count_three_height))
                            / 2;
            this.mMessageTextAndInfoView.setPaddingRelative(
                    dimensionPixelOffset,
                    dimensionPixelOffset3,
                    dimensionPixelOffset,
                    dimensionPixelOffset3);
        } else {
            int dimensionPixelOffset4 =
                    (resources.getDimensionPixelOffset(R.dimen.message_item_line_count_two_height)
                                    - resources.getDimensionPixelOffset(
                                            R.dimen.message_item_text_line_count_two_height))
                            / 2;
            this.mMessageTextAndInfoView.setPaddingRelative(
                    dimensionPixelOffset,
                    dimensionPixelOffset4,
                    dimensionPixelOffset,
                    dimensionPixelOffset4);
        }
        Drawable drawable =
                resources.getDrawable(
                        this.mIncoming
                                ? R.drawable.sec_conversation_recv_message_bg
                                : R.drawable.sec_conversation_sent_message_bg,
                        null);
        drawable.setAutoMirrored(true);
        this.mMessageTextAndInfoView.setBackground(drawable);
        if (this.mIncoming) {
            int dimensionPixelSize =
                    resources.getDimensionPixelSize(R.dimen.sec_font_preview_message_start_padding);
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) this.mMessageTextAndInfoView.getLayoutParams();
            marginLayoutParams.setMarginStart(dimensionPixelSize);
            this.mMessageTextAndInfoView.setLayoutParams(marginLayoutParams);
            this.mConversationItemView.setGravity(8388611);
            this.mContactRecvIconView.setVisibility(0);
        } else {
            this.mConversationItemView.setGravity(8388613);
        }
        int dimensionPixelSize2 =
                resources.getDimensionPixelSize(
                        R.dimen.sec_font_preview_message_list_bottom_margin);
        ViewGroup.MarginLayoutParams marginLayoutParams2 =
                (ViewGroup.MarginLayoutParams) this.mConversationItemView.getLayoutParams();
        marginLayoutParams2.setMargins(0, 0, 0, dimensionPixelSize2);
        this.mConversationItemView.setLayoutParams(marginLayoutParams2);
        this.mMessageTextView.setTextColor(
                getContext()
                        .getColor(
                                this.mIncoming
                                        ? R.color.sec_font_preview_recv_message_color
                                        : R.color.sec_font_preview_sent_message_color));
        super.onMeasure(i, i2);
    }

    public NewConversationMessageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NewConversationMessageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NewConversationMessageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.ConversationMessageView);
        this.mIncoming = obtainStyledAttributes.getBoolean(3, true);
        this.mMessageText = obtainStyledAttributes.getString(4);
        obtainStyledAttributes.getString(1);
        obtainStyledAttributes.getColor(2, 0);
        obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(context).inflate(R.layout.sec_new_conversation_message_content, this);
    }
}
