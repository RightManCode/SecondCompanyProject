package com.wishland.www.xinwanhaotest.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.MessageBean;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/26.
 */

public class MessageListViewAdapter extends BastListAdapter {

    private List<MessageBean.DataBean.DataListBean> dataList;
    private Context baseContext;
    private TextView messagePageitemTextview;
    public ViewHolder viewholder;
    private Map<String, String> map;

    public MessageListViewAdapter(Context baseContext, List<MessageBean.DataBean.DataListBean> dataList, TextView messagePageitemTextview) {
        super(dataList);
        this.dataList = dataList;
        this.baseContext = baseContext;
        this.messagePageitemTextview = messagePageitemTextview;
        map = new HashMap<>();
    }


    @Override
    public View setView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagelistitem, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.messageListitemTextviewTime.setText(dataList.get(position).getTime());
        viewholder.messageListitemMessageTitle.setText(dataList.get(position).getTitle());
        viewholder.messageListitemMessageContent.setText(dataList.get(position).getDetailedInfo());

        viewholder.messageListitemTextviewOk.setText(((dataList.get(position).getIsNew() + "").equals("0") ? "已读" : "未读"));

        messagePageitemTextview.setText("共：" + dataList.size() + " 条数据");

        viewholder.messageListitemDelitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSuccess(position);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    viewholder.messageListitemTextviewOk.setText("已读");
                    mOnItemClickListener.onItemClick(view, position);
                }
            }
        });


        return convertView;
    }

    private boolean delOk = true;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 删除消息
     *
     * @param number 删除下标
     */
    public void requestDelMessage(final int number) {
        String token = UserSP.getSPInstance().getToken(UserSP.LOGIN_TOKEN);
        String msgId = dataList.get(number).getMsgId() + "";
        if (map.size() != 0) {
            map.clear();
        }
        map.put("msgid", msgId);
        Model.getInstance().apiDelMessage(msgId, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("消息删除完成");
                LogUtil.e("消息删除完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("消息删除异常：" + e.getMessage());
                LogUtil.e("消息删除异常");
            }

            @Override
            public void onNext(ResponseBody messagejson) {
                delOk = true;
                try {
                    String string = messagejson.string();
                    JSONObject jsonObject = Model.getInstance().getJsonObject(string);
                    Model.getInstance().setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    AppUtils.getInstance().onRespons("消息删除成功");
                    LogUtil.e("消息删除成功");
                    if (status == 200) {
                        dataList.remove(number);
                        notifyDataSetChanged();
                        ToastUtil.showShort(baseContext, "消息删除成功");
                    } else {
                        ToastUtil.showShort(baseContext, "网络异常");
                    }
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("消息删除异常：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("消息删除异常：" + e.getMessage());
                    e.printStackTrace();
                }


            }
        });
    }

    private void setSuccess(final int position) {
        View view = View.inflate(baseContext, R.layout.line_success, null);
        TextView mLine_success_message = (TextView) view.findViewById(R.id.line_success_message);
        TextView mLine_success_fromto = (TextView) view.findViewById(R.id.line_success_from_to);
        TextView mLine_success_money = (TextView) view.findViewById(R.id.line_success_money);
        TextView tv_line = (TextView) view.findViewById(R.id.tv_line);
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.ll_linear);
        Button ld_line = (Button) view.findViewById(R.id.ld_line);
        Button lo_line = (Button) view.findViewById(R.id.lo_line);

        linear.setVisibility(View.VISIBLE);
        tv_line.setVisibility(View.GONE);

        mLine_success_message.setText("删除");
        mLine_success_fromto.setText("确定本条删除？");
        mLine_success_money.setVisibility(View.GONE);
        AlertDialog.Builder alertDialog = Model.getInstance().getAlertDialog(baseContext);
        alertDialog.setView(view)
                .setCancelable(false);
        final AlertDialog show = alertDialog.show();


        //取消删除item
        ld_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });

        //确定删除item
        lo_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
                if (delOk) {
                    delOk = false;
                    requestDelMessage(position);
                } else {
                    ToastUtil.showUI((Activity) baseContext, "您已经点击过了...");
                }
            }
        });
    }


    static class ViewHolder {
        @BindView(R.id.message_listitem_textview_left)
        TextView messageListitemTextviewLeft;
        @BindView(R.id.message_listitem_textview_ok)
        TextView messageListitemTextviewOk;
        @BindView(R.id.message_listitem_textview_right)
        TextView messageListitemTextviewRight;
        @BindView(R.id.message_listitem_delitem)
        ImageView messageListitemDelitem;
        @BindView(R.id.message_listitem_textview_time)
        TextView messageListitemTextviewTime;
        @BindView(R.id.message_listitem_message_title)
        TextView messageListitemMessageTitle;
        @BindView(R.id.message_listitem_message_content)
        TextView messageListitemMessageContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
