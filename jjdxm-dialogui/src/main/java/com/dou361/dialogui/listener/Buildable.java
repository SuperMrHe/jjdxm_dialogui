package com.dou361.dialogui.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.R;
import com.dou361.dialogui.ToolUtils;
import com.dou361.dialogui.adapter.SuperLvAdapter;
import com.dou361.dialogui.adapter.SuperLvHolder;
import com.dou361.dialogui.bottomsheet.BottomHorizontalHolder;
import com.dou361.dialogui.bottomsheet.BottomSheetBean;
import com.dou361.dialogui.bottomsheet.BottomVerticalHolder;
import com.dou361.dialogui.config.BuildBean;
import com.dou361.dialogui.config.CommonConfig;
import com.dou361.dialogui.holder.AlertDialogHolder;
import com.dou361.dialogui.holder.BottomSheetCancelHolder;
import com.dou361.dialogui.holder.CenterSheetHolder;

/**
 * ========================================
 * <p/>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2016/11/1 16:15
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class Buildable {

    protected static int singleChosen;

    protected BuildBean buildByType(BuildBean bean) {
        ToolUtils.fixContext(bean);
        switch (bean.type) {
            case CommonConfig.TYPE_LOADING_HORIZONTAL:
                ToolUtils.newCustomDialog(bean);
                buildLoadingHorizontal(bean);
                break;
            case CommonConfig.TYPE_LOADING_VERTICAL:
                ToolUtils.newCustomDialog(bean);
                buildLoadingVertical(bean);
                break;
            case CommonConfig.TYPE_MD_ALERT:
                buildMdAlert(bean);
                break;
            case CommonConfig.TYPE_SINGLE_CHOOSE:
                buildSingleChoose(bean);
                break;
            case CommonConfig.TYPE_MD_MULTI_CHOOSE:
                buildMdMultiChoose(bean);
                break;
            case CommonConfig.TYPE_ALERT:
                ToolUtils.newCustomDialog(bean);
                buildAlert(bean);
                break;
            case CommonConfig.TYPE_ALERT_HORIZONTAL:
                ToolUtils.newCustomDialog(bean);
                buildAlertHorizontal(bean);
                break;
            case CommonConfig.TYPE_ALERT_VERTICAL:
                ToolUtils.newCustomDialog(bean);
                buildAlertVertical(bean);
                break;
            case CommonConfig.TYPE_BOTTOM_SHEET_CANCEL:
                ToolUtils.newCustomDialog(bean);
                buildBottomSheetCancel(bean);
                break;
            case CommonConfig.TYPE_ALERT_INPUT:
                ToolUtils.newCustomDialog(bean);
                buildAlertInput(bean);
                break;
            case CommonConfig.TYPE_CENTER_SHEET:
                ToolUtils.newCustomDialog(bean);
                buildCenterSheet(bean);
                break;
            case CommonConfig.TYPE_CUSTOM_ALERT:
                ToolUtils.newCustomDialog(bean);
                bean.dialog.setContentView(bean.customView);
                bean.dialog.getWindow().setGravity(bean.gravity);
                break;
            case CommonConfig.TYPE_BOTTOM_SHEET:
                buildBottomSheet(bean);
                break;
            case CommonConfig.TYPE_BOTTOM_SHEET_VERTICAL:
                buildBottomSheetVertical(bean);
                break;
            case CommonConfig.TYPE_BOTTOM_SHEET_HORIZONTAL:
                buildBottomSheetHorizontal(bean);
                break;
            default:
                break;


        }
        ToolUtils.setDialogStyle(bean);
        ToolUtils.setCancelable(bean);
        return bean;
    }

    private void buildBottomSheetHorizontal(final BuildBean bean) {
        final BottomSheetDialog dialog = new BottomSheetDialog(bean.context);
        LinearLayout root = (LinearLayout) View.inflate(bean.context, R.layout.dialogui_item_bottomsheet_lv, null);
        TextView tvTitle = (TextView) root.findViewById(R.id.dialogui_tv_title);
        if (TextUtils.isEmpty(bean.title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(bean.title);
        }
        GridView listView = new GridView(bean.context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(params);
        // ListView listView = (ListView) root.findViewById(R.id.lv);
        listView.setNumColumns(bean.gridColumns);
        root.addView(listView, 1);
        if (bean.mAdapter == null) {
            SuperLvAdapter adapter = new SuperLvAdapter(bean.context) {
                @Override
                protected SuperLvHolder generateNewHolder(Context context, int itemViewType) {
                    return new BottomHorizontalHolder(context);
                }
            };
            bean.mAdapter = adapter;
        }
        listView.setAdapter(bean.mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BottomSheetBean sheetBean = (BottomSheetBean) bean.lvDatas.get(position);
                dialog.dismiss();
                bean.itemListener.onItemClick(sheetBean.text, position);
            }
        });
        bean.mAdapter.addAll(bean.lvDatas);
        dialog.setContentView(root);
        bean.dialog = dialog;
    }

    private void buildBottomSheetVertical(final BuildBean bean) {
        final BottomSheetDialog dialog = new BottomSheetDialog(bean.context);
        LinearLayout root = (LinearLayout) View.inflate(bean.context, R.layout.dialogui_item_bottomsheet_lv, null);
        TextView tvTitle = (TextView) root.findViewById(R.id.dialogui_tv_title);
        if (TextUtils.isEmpty(bean.title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(bean.title);
        }
        ListView listView = new ListView(bean.context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(params);
        listView.setDividerHeight(0);
        // ListView listView = (ListView) root.findViewById(R.id.lv);
        root.addView(listView, 1);
        if (bean.mAdapter == null) {
            SuperLvAdapter adapter = new SuperLvAdapter(bean.context) {
                @Override
                protected SuperLvHolder generateNewHolder(Context context, int itemViewType) {
                    return new BottomVerticalHolder(context);
                }
            };
            bean.mAdapter = adapter;
        }
        listView.setAdapter(bean.mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BottomSheetBean sheetBean = (BottomSheetBean) bean.lvDatas.get(position);
                dialog.dismiss();
                bean.itemListener.onItemClick(sheetBean.text, position);
            }
        });
        bean.mAdapter.addAll(bean.lvDatas);
        dialog.setContentView(root);
        bean.dialog = dialog;
    }

    private void buildBottomSheet(BuildBean bean) {
        final BottomSheetDialog dialog = new BottomSheetDialog(bean.context);
        dialog.setContentView(bean.customView);
        dialog.setCancelable(bean.cancelable);
        dialog.setCanceledOnTouchOutside(bean.outsideTouchable);
        bean.dialog = dialog;
    }

    protected BuildBean buildLoadingVertical(BuildBean bean) {
        View root = View.inflate(bean.context, R.layout.dialogui_loading_vertical, null);
        TextView tvMsg = (TextView) root.findViewById(R.id.dialogui_tv_msg);
        tvMsg.setText(bean.msg);
        bean.dialog.setContentView(root);
        return bean;
    }


    protected BuildBean buildLoadingHorizontal(BuildBean bean) {
        View root = View.inflate(bean.context, R.layout.dialogui_loading_horizontal, null);
        TextView tvMsg = (TextView) root.findViewById(R.id.dialogui_tv_msg);
        tvMsg.setText(bean.msg);
        bean.dialog.setContentView(root);
        return bean;
    }

    protected BuildBean buildMdAlert(final BuildBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(bean.context);
        builder.setTitle(bean.title)
                .setMessage(bean.msg)
                .setPositiveButton(bean.text1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bean.listener.onPositive();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(bean.text2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bean.listener.onNegative();
                        dialog.dismiss();
                    }
                }).setNeutralButton(bean.text3, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bean.listener.onNeutral();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                bean.listener.onCancle();
            }
        });
        bean.alertDialog = dialog;
        return bean;
    }

    protected BuildBean buildSingleChoose(final BuildBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(bean.context);
        singleChosen = bean.defaultChosen;
        builder.setTitle(bean.title)
                .setSingleChoiceItems(bean.wordsMd, bean.defaultChosen, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        singleChosen = i;
                        if (bean.itemListener != null) {
                            bean.itemListener.onItemClick(bean.wordsMd[i], i);
                        }

                        if (bean.listener == null) {
                            DialogUIUtils.dismiss(dialogInterface);
                        }

                    }
                });

        AlertDialog dialog = builder.create();
        bean.alertDialog = dialog;
        return bean;
    }

    protected BuildBean buildMdMultiChoose(final BuildBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(bean.context);
        builder.setTitle(bean.title)
                .setCancelable(true)
                .setPositiveButton(bean.text1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (bean.listener != null) {
                            DialogUIUtils.dismiss(dialogInterface);
                            bean.listener.onPositive();
                            bean.listener.onGetChoose(bean.checkedItems);
                        }
                    }
                })
                .setNegativeButton(bean.text2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (bean.listener != null) {
                            DialogUIUtils.dismiss(dialogInterface);
                            bean.listener.onNegative();
                        }
                    }
                })
                .setMultiChoiceItems(bean.wordsMd, bean.checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                    }
                })
        ;

        AlertDialog dialog = builder.create();
        bean.alertDialog = dialog;
        return bean;
    }

    protected BuildBean buildAlert(BuildBean bean) {
        bean.isVertical = true;
        bean.hint1 = "";
        buildCommon(bean);
        return bean;
    }

    protected BuildBean buildAlertHorizontal(BuildBean bean) {
        bean.isVertical = false;
        bean.hint1 = "";
        bean.hint2 = "";
        buildCommon(bean);
        return bean;
    }

    protected BuildBean buildAlertVertical(BuildBean bean) {
        bean.isVertical = true;
        bean.hint1 = "";
        bean.hint2 = "";
        buildCommon(bean);
        return bean;
    }

    protected BuildBean buildCenterSheet(BuildBean bean) {
        CenterSheetHolder holder = new CenterSheetHolder(bean.context);
        bean.dialog.setContentView(holder.rootView);
        holder.assingDatasAndEvents(bean.context, bean);
        bean.viewHeight = ToolUtils.mesureHeight(holder.rootView, holder.lv);
        Window window = bean.dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        return bean;
    }

    protected BuildBean buildBottomSheetCancel(BuildBean bean) {
        BottomSheetCancelHolder holder = new BottomSheetCancelHolder(bean.context);
        bean.dialog.setContentView(holder.rootView);
        holder.assingDatasAndEvents(bean.context, bean);
        bean.viewHeight = ToolUtils.mesureHeight(holder.rootView, holder.lv);
        Window window = bean.dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mystyle);
        return bean;
    }


    protected BuildBean buildAlertInput(BuildBean bean) {
        buildCommon(bean);
        return bean;
    }

    private BuildBean buildCommon(BuildBean bean) {
        AlertDialogHolder holder = new AlertDialogHolder(bean.context);
        bean.dialog.setContentView(holder.rootView);
        holder.assingDatasAndEvents(bean.context, bean);
        int height = ToolUtils.mesureHeight(holder.rootView, holder.tvMsg, holder.et1, holder.et2);
        bean.viewHeight = height;
        return bean;

    }


}
