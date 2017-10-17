package com.example.sz.gridviewtable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 *@author sunzheng
 *@date  2017/10/17
 * @describe
 * */
public class MainActivity extends Activity {

    GridView gridView;
    List<String> mListdata=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridview);
        initCalendar();
        setGridView();
    }

    private void initCalendar() {
        for (int i = 0; i <14; i++) {
            mListdata.add(getFetureDate(i));
        }
    }
    public static String getFetureDate(int past) {
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        int todayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        String result = format.format(today);
        return result+","+weeks[todayIndex];
    }

    /**设置GirdView参数，绑定数据*/
    private void setGridView() {
        int size = mListdata.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * length  * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(1); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

        GridViewAdapter adapter = new GridViewAdapter(getApplicationContext(), mListdata);
        gridView.setAdapter(adapter);
    }

    public class GridViewAdapter extends BaseAdapter {
        Context context;
        List<String> list;
        public GridViewAdapter(Context context, List<String> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=new ViewHolder();
            if(convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
                viewHolder.tvWeek=(TextView)convertView.findViewById(R.id.tv_week);
                viewHolder.tvDate=(TextView)convertView.findViewById(R.id.tv_date);
                viewHolder.tvZao = (TextView) convertView.findViewById(R.id.tv_zao);
                viewHolder.tvZhong = (TextView) convertView.findViewById(R.id.tv_xia);
                viewHolder. tvWan=(TextView)convertView.findViewById(R.id.tv_wan);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder) convertView.getTag();
            }

            final String [] text=mListdata.get(position).split(",");
            viewHolder.tvWeek.setText(text[1]);
            viewHolder.tvDate.setText(text[0]);

            viewHolder.tvZao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,  text[0] + "上午", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.tvZhong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,text[0] +"下午",Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.tvWan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,  text[0]  + "晚上", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }
    class ViewHolder{
        TextView tvWeek,tvDate,tvZao,tvZhong,tvWan;
    }
}
