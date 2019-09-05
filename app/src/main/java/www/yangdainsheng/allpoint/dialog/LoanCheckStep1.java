package www.yangdainsheng.allpoint.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import www.yangdainsheng.allpoint.R;
import www.yangdainsheng.allpoint.util.CommonUtil;


/**
 * 此对话框是校验身份证，联系人，运营商页面在点击返回键时，验证个步骤认证状态的
 */
public class LoanCheckStep1 extends Dialog {

    public LoanCheckStep1(@NonNull Context context) {
        super(context, R.style.dialog_bottom);
        mActivity = (Activity)context;
    }

    private TextView mTvTitle;
    private TextView mTvID;
    private TextView mTvIDStatus;
    private TextView mTvContact;
    private TextView mTvContactStatus;
    private TextView mTvOperate;
    private TextView mTvOperateStatus;
    private TextView mTvCancel;
    private TextView mTvContinue;

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_step_1);
        initViews();
        setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        mTvTitle = findViewById(R.id.tv_title);
        mTvID = findViewById(R.id.tv_id);
        mTvIDStatus = findViewById(R.id.tv_id_status);
        mTvContact = findViewById(R.id.tv_contact);
        mTvContactStatus = findViewById(R.id.tv_contact_status);
        mTvOperate = findViewById(R.id.tv_operate);
        mTvOperateStatus = findViewById(R.id.tv_operate_status);
        mTvID = findViewById(R.id.tv_id);
        mTvID = findViewById(R.id.tv_id);
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvContinue = findViewById(R.id.tv_continue);

        SpannableString spannableString = new SpannableString(String.format("还差%s步即可激活额度", 1));
        spannableString.setSpan(new AbsoluteSizeSpan(CommonUtil.dip2px(22)), 2, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),2, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mTvTitle.setText(spannableString);
        mTvCancel.setOnClickListener(v -> {
            dismiss();
        });
        mTvContinue.setOnClickListener(v -> dismiss());
    }

    private void setCredited(TextView textView) {
        textView.setTextColor(getContext().getResources().getColor(R.color.load_txt_color_3));
        textView.setText("已认证");
        Drawable drawable = getContext().getResources().getDrawable(R.mipmap.icon_dialog_check_right);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setCompoundDrawablePadding(CommonUtil.dip2px(5));
    }
}
