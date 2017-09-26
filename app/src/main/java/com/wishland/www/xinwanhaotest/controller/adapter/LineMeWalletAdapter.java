package com.wishland.www.xinwanhaotest.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.LineMoneyBean;
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
 * Created by Administrator on 2017/4/25.
 */

public class LineMeWalletAdapter extends BaseAdapter {

    private final List<LineMoneyBean.DataBean.WalletBean> titlename;
    private final RotateAnimation an;
    private final Context context;
    private boolean judgeA = true;
    private final Model instance;
    private Map<String, String> map;


    public LineMeWalletAdapter(Context baseContext, List<LineMoneyBean.DataBean.WalletBean> titlename) {
        this.titlename = titlename;
        this.context = baseContext;
        map = new HashMap<>();
        instance = Model.getInstance();
        an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        an.setInterpolator(new LinearInterpolator());//不停顿
        an.setRepeatCount(1000);//重复次数
        an.setFillAfter(true);//停在最后
        an.setDuration(800);
    }

    @Override
    public int getCount() {
        return titlename.size();
    }

    @Override
    public Object getItem(int i) {
        return titlename.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, final ViewGroup viewGroup) {
        final ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.linegridviewitem, null, false);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.lineGridviewItemTextname.setText(titlename.get(i).getName());
        viewholder.lineGridviewItemTextmoney.setText(titlename.get(i).getAmout());


        if (clickTemp == i) {
            viewholder.lineGridviewItemClickBg.setBackgroundResource(R.color.line_gridview_item_pressed);
            viewholder.lineGridviewItemOk.setVisibility(View.VISIBLE);
        } else {
            viewholder.lineGridviewItemClickBg.setBackgroundResource(R.color.line_gridview_item_normal);
            viewholder.lineGridviewItemOk.setVisibility(View.GONE);
        }

        viewholder.lineitemrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judgeA) {
                    judgeA = false;
                    requestItem(viewholder.lineitemrequest, i, viewholder.lineGridviewItemTextmoney);
                    viewholder.lineitemrequest.startAnimation(an);

                } else {
                    ToastUtil.showUI((Activity) context, "正在请求，请稍等...");
                }

            }
        });
        return convertView;
    }

    private int clickTemp = -1;

    public void setSelection(int position) {
        clickTemp = position;
    }

    class ViewHolder {
        @BindView(R.id.line_gridview_item_textname)
        TextView lineGridviewItemTextname;
        @BindView(R.id.line_gridview_item_textmoney)
        TextView lineGridviewItemTextmoney;
        @BindView(R.id.line_gridview_item_ok)
        ImageView lineGridviewItemOk;
        @BindView(R.id.line_gridview_item_click_bg)
        RelativeLayout lineGridviewItemClickBg;
        @BindView(R.id.line_item_request)
        ImageView lineitemrequest;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void requestItem(final ImageView lineitemrequest, final int i, final TextView lineGridviewItemTextmoney) {
        String token = instance.getUserSP().getToken(UserSP.LOGIN_TOKEN);
        String wallettype = titlename.get(i).getWallettype();
        if (map.size() != 0) {
            map.clear();
        }
        map.put("type", wallettype);

        instance.apiItem(wallettype, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("Item刷新完成");
                judgeA = true;
                lineitemrequest.clearAnimation();
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("Item刷新异常：" + e.getMessage());
                LogUtil.e("Item刷新异常：" + e.getMessage());
                ToastUtil.showShort(context, "请求异常...");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        String balance = data.optString("balance");
                        lineGridviewItemTextmoney.setText(balance + "");
                        ToastUtil.showShort(context, titlename.get(i).getName() + ": " + "刷新成功");
                        titlename.get(i).setAmout(balance + "");
                    }
                    AppUtils.getInstance().onRespons("Item刷新成功");
                    LogUtil.e("Item刷新成功");
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("Item刷新异常：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("Item刷新异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
