package com.zy.musicplayer.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by  zy on 2017/6/2.
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //
 * //         .............................................
 * //                  佛祖保佑             永无BUG
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {
    public List<T> mList;
    private int LayoutId;
    private OnItemClickLitener itemClickLitener;

    public void setItemClickLitener(OnItemClickLitener itemClickLitener) {
        this.itemClickLitener = itemClickLitener;
    }


    public BaseAdapter(List<T> list, int LayoutId) {
        super();
        this.mList = list;
        this.LayoutId = LayoutId;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BaseViewHolder) {
            if (itemClickLitener != null) {
                //设置了点击监听
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getLayoutPosition();
                        itemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });
            }
            bindData((BaseViewHolder) holder, position);
        }
    }

    protected abstract void bindData(BaseViewHolder holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(LayoutId, parent, false);
        return new BaseViewHolder(inflate);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 加载更多数据
     */
    public void LoadMoreItemData(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 加载更多数据
     */
    public void RefreshItemData(List<T> list) {
        mList.clear();
        mList.addAll(list);
        this.notifyDataSetChanged();

    }

    /**
     * 返回数据集合
     *
     * @return
     */
    public List<T> getList() {
        return mList;
    }

    /**
     * 点击事件回调
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }
}
