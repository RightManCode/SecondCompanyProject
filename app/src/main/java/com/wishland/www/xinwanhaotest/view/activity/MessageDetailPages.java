package com.wishland.www.xinwanhaotest.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.MessageToDetailBean;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MessageDetailPages extends AutoLayoutActivity {

    @BindView(R.id.message_listitem_textview_ok)
    TextView messageListitemTextviewOk;
    @BindView(R.id.message_listitem_delitem)
    ImageView messageListitemDelitem;
    @BindView(R.id.message_listitem_textview_time)
    TextView messageListitemTextviewTime;
    @BindView(R.id.message_detailpages_message_title)
    TextView messageDetailpagesMessageTitle;
    @BindView(R.id.message_detailpages_message_content)
    TextView messageDetailpagesMessageContent;
    @BindView(R.id.top_fanhui)
    ImageView topFanhui;
    @BindView(R.id.message_detail_fanhui)
    Button messageDetailFanhui;
    @BindView(R.id.top_welcome)
    TextView topwelcome;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagedetailpages);
        ButterKnife.bind(this);
        ActivityManager.getActivityManager().addActivity(this);
        messageListitemDelitem.setVisibility(View.GONE);
        init();
    }

    private void init() {
        topwelcome.setText(R.string.my_message);
        Bundle message = getIntent().getExtras();
        MessageToDetailBean message1 = (MessageToDetailBean) message.getSerializable("message");
        messageListitemTextviewTime.setText(message1.getTime());
        messageDetailpagesMessageTitle.setText(message1.getTitle());
        messageDetailpagesMessageContent.setText(message1.getDetailedInfo());
    }

    @OnClick({R.id.top_fanhui,R.id.message_detail_fanhui, R.id.button_pc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_fanhui:
            case R.id.message_detail_fanhui:
                finish();
                break;
            case R.id.button_pc:  //进入浏览器PC版
                Model.getInstance().toBrowser(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().deleteActivity(this);
    }
}
