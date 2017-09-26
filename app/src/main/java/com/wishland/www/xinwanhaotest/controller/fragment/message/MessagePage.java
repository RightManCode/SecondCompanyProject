package com.wishland.www.xinwanhaotest.controller.fragment.message;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.adapter.MessageListViewAdapter;
import com.wishland.www.xinwanhaotest.controller.base.BaseFragment;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.MessageBean;
import com.wishland.www.xinwanhaotest.model.bean.MessageToDetailBean;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.view.activity.LoginInActivity;
import com.wishland.www.xinwanhaotest.view.activity.MainActivity;
import com.wishland.www.xinwanhaotest.view.activity.MessageDetailPages;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/15.
 * 消息中心
 */

public class MessagePage extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.message_all_textview)
    TextView messageAllTextview;
    @BindView(R.id.message_show_listview)
    ListView messageShowListview;
    @BindView(R.id.message_pageitem_textview)
    TextView messagePageitemTextview;
    @BindView(R.id.quest_refresh)
    public MaterialRefreshLayout questrefresh;

    private List<MessageBean.DataBean.DataListBean> dataList;
    private MessageListViewAdapter messageListViewAdapter;
    private Model instance;
    private int unReadMsg;
    private UserSP userSP;
    private Map<String, String> map;

    @Override
    public View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View message = inflater.inflate(R.layout.messagepage, container, false);
        unbinder = ButterKnife.bind(this, message);
        return message;
    }

    @Override
    public void setData() {
        super.setData();
        init();
    }

    private void init() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        questrefresh.autoRefresh();
        questrefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                requestMessage();
            }
        });


    }


    private void setListView(final List<MessageBean.DataBean.DataListBean> dataList) {
        messageListViewAdapter = new MessageListViewAdapter(baseContext, dataList, messagePageitemTextview);
        messageShowListview.setAdapter(messageListViewAdapter);

        messageListViewAdapter.setOnItemClickListener(new MessageListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int isNew = dataList.get(position).getIsNew();
                //只有消息未读时,请求消息已读
                if (!(dataList.get(position).getIsNew() + "").equals("0")) {
                    requestGolbal(dataList.get(position).getMsgId() + "");
                }

                Intent intent = new Intent(baseContext, MessageDetailPages.class);
                MessageBean.DataBean.DataListBean data = dataList.get(position);
                MessageToDetailBean messagetodetailbean = new MessageToDetailBean(data.getMsgId() + "", data.getTime(), data.getTitle(), data.getDetailedInfo());
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", messagetodetailbean);
                intent.putExtras(bundle);
                baseContext.startActivity(intent);
            }
        });
    }

    private void requestMessage() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        int queryCnt = 500;
        int queryId = 0;
        if (map.size() != 0) {
            map.clear();
        }
        map.put("queryCnt", queryCnt + "");
        map.put("queryId", queryId + "");

        instance.apiMessage(queryCnt + "", queryId + "", token, NetUtils.getParamsPro(map).get("signature")
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        questrefresh.finishRefresh();
                        AppUtils.getInstance().onRespons("消息页面请求完成");
                        LogUtil.e("消息页面请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        questrefresh.finishRefresh();
                        AppUtils.getInstance().onRespons("消息页面请求失败：" + e.getMessage());
                        LogUtil.e("消息页面请求失败" + e.getMessage());
                        if (dataList != null) {
                            dataList.clear();
                        }
                        if (messageListViewAdapter != null) {
                            messageListViewAdapter.notifyDataSetChanged();
                        }
                        messagePageitemTextview.setText("共：" + 0 + " 条数据");
                        messageAllTextview.setText("( " + 0 + " )");
                    }

                    @Override
                    public void onNext(ResponseBody message) {
                        questrefresh.finishRefresh();
                        try {
                            String string = message.string();
                            JSONObject jsonObject = instance.getJsonObject(string);
                            instance.setToken_SP(jsonObject.optString("token"));
                            int status = jsonObject.optInt("status");
                            AppUtils.getInstance().onRespons("消息页面请求成功");
                            LogUtil.e("消息页面请求成功");
                            if (status == 200) {
                                MessageBean messageBean = instance.getGson().fromJson(string, MessageBean.class);
                                dataList = messageBean.getData().getDataList();
                                unReadMsg = messageBean.getData().getUnReadMsg();
                                setListView(dataList);
                                messageAllTextview.setText("( " + unReadMsg + " )");
                                ((MainActivity) baseContext).newCount(unReadMsg + "");
                            } else {
                                String errorMsg = jsonObject.optString("errorMsg");
                                if (errorMsg.equals("用户未登录")) {
                                    instance.skipLoginActivity((MainActivity) baseContext, LoginInActivity.class);
                                } else {
                                    ToastUtil.showShort(baseContext, "请求异常...");
                                }
                                messageAllTextview.setText("( " + 0 + " )");
                                messagePageitemTextview.setText("共 " + 0 + "条记录");
                            }
                            messageListViewAdapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            AppUtils.getInstance().onRespons("消息页面请求失败：" + e.getMessage());
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppUtils.getInstance().onRespons("消息页面请求失败：" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }

        );
    }

    private void requestGolbal(final String msgId) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("Msgid", msgId);

        instance.apiReadMessage(msgId, token, NetUtils.getParamsPro(map).get("signature")
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        AppUtils.getInstance().onRespons("消息已读请求完成");
                        LogUtil.e("消息已读请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppUtils.getInstance().onRespons("消息已读请求异常：" + e.getMessage());
                        LogUtil.e("消息已读请求异常：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            JSONObject jsonObject = instance.getJsonObject(string);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            if (status == 200) {
                                if (unReadMsg != 0 && unReadMsg > 0) {
                                    unReadMsg -= 1;
                                    messageAllTextview.setText("( " + unReadMsg + " )");
                                    ((MainActivity) baseContext).newCount(unReadMsg + "");
                                }
                                AppUtils.getInstance().onRespons("消息已读请求成功");
                                LogUtil.e("消息已读请求成功:" + string);
                            } else {
                                LogUtil.e("消息已读请求失败：" + jsonObject.optString("errorMsg"));
                            }

                        } catch (IOException e) {
                            AppUtils.getInstance().onRespons("消息已读请求异常：" + e.getMessage());
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppUtils.getInstance().onRespons("消息已读请求异常：" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.button_pc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_pc:  //进入浏览器PC版
                instance.toBrowser(baseContext);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
