package com.lyl.smzdk.ui.search;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.BtLoadDataEvent;
import com.lyl.smzdk.ui.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class SearchFragment extends BaseFragment {


    @BindView(R.id.search_actionbar_edt)
    EditText searchActionbarEdt;
    @BindView(R.id.search_actionbar_edt_clear)
    ImageView searchActionbarEdtClear;
    @BindView(R.id.search_actionbar_btn)
    TextView searchActionbarBtn;
    @BindView(R.id.search_tablayout)
    TabLayout searchTablayout;
    @BindView(R.id.search_viewpager)
    ViewPager searchViewpager;

    private ClipboardManager clipboardManager;

    private String[] mSearchMenu = {"资源1", "资源2", "资源3", "资源4"};
    private String[] mSearchType = {Constans.BT_TYPE_1, Constans.BT_TYPE_2, Constans.BT_TYPE_3, Constans.BT_TYPE_4};

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onHiddenChanged(false);
        initView();
        setViewPager();
        clipboardManager = (ClipboardManager) getHolder().getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public void onStart() {
        super.onStart();
        getClipData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden){
            setStatusBarColor(R.color.search_primary);
        }
    }

    /**
     * 获取剪切板上的文字，并设置给编辑框
     */
    private void getClipData() {
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData == null) return;

        ClipData.Item item = clipData.getItemAt(0);
        if (item != null) {
            String content = item.getText().toString();
            if (!TextUtils.isEmpty(content)){
                searchActionbarEdt.setText(content);
                searchContent(content);
            }
        }
    }

    private void setViewPager() {
        searchViewpager.setOffscreenPageLimit(4);
        searchViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return SearchListFragment.newInstance(mSearchType[i], "");
            }

            @Override
            public int getCount() {
                return mSearchMenu.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mSearchMenu[position];
            }
        });
        searchTablayout.setupWithViewPager(searchViewpager);
    }

    private void initView() {
        searchActionbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = searchActionbarEdt.getText().toString().trim();
                searchContent(content);
            }
        });

        searchActionbarEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = searchActionbarEdt.getText().toString().trim();
                    searchContent(content);
                    return true;
                }
                return false;
            }
        });

        searchActionbarEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    searchActionbarEdtClear.setVisibility(View.GONE);
                } else {
                    searchActionbarEdtClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        searchActionbarEdtClear.setColorFilter(ContextCompat.getColor(getHolder(), R.color.search_primary));
        searchActionbarEdtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchActionbarEdt.setText("");
            }
        });
    }

    private void searchContent(String content) {
        EventBus.getDefault().post(new BtLoadDataEvent(content));
    }
}
