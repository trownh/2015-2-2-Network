package com.network.termproj;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    
    // 문자열을 보관 할 ArrayList
    private ArrayList<String>   m_List_ch;
    private ArrayList<String>   m_List_ssid;
    private ArrayList<Integer>   	m_List_level;
    
    // 생성자
    public CustomAdapter() {
    	m_List_ch = new ArrayList<String>();
    	m_List_ssid = new ArrayList<String>();
    	m_List_level = new ArrayList<Integer>();
    }
 
    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return m_List_ch.size();
    }
 
    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return m_List_ch.get(position);
    }
 
    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
         
        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);
            
            TextView tv_ssid = (TextView) convertView.findViewById(R.id.row_ssid);
            tv_ssid.setTextColor(Color.BLACK);
            tv_ssid.setText(m_List_ssid.get(position).toString());
            tv_ssid.setTextSize(13);
            
            // TextView에 현재 position의 문자열 추가
            TextView tv_ch = (TextView) convertView.findViewById(R.id.row_ch);
            tv_ch.setTextColor(Color.BLACK);
            tv_ch.setText(m_List_ch.get(position).toString());
            tv_ch.setTextSize(13);
            
            ImageView lv_level = (ImageView) convertView.findViewById(R.id.row_level);
            switch (m_List_level.get(position)) {
			case 0:
				lv_level.setImageResource(R.drawable.wifi_0);
				break;
			case 1:
				lv_level.setImageResource(R.drawable.wifi_1);
				break;
			case 2:
				lv_level.setImageResource(R.drawable.wifi_2);
				break;
			case 3:
				lv_level.setImageResource(R.drawable.wifi_3);
				break;
			default:
				lv_level.setImageResource(R.drawable.wifi_4);
				break;
            }
        }
        else{
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);
            
            TextView tv_ssid = (TextView) convertView.findViewById(R.id.row_ssid);
            tv_ssid.setTextColor(Color.BLACK);
            tv_ssid.setText(m_List_ssid.get(position).toString());
            tv_ssid.setTextSize(13);
            
            // TextView에 현재 position의 문자열 추가
            TextView tv_ch = (TextView) convertView.findViewById(R.id.row_ch);
            tv_ch.setTextColor(Color.BLACK);
            tv_ch.setText(m_List_ch.get(position).toString());
            tv_ch.setTextSize(13);
            
            ImageView lv_level = (ImageView) convertView.findViewById(R.id.row_level);
            switch (m_List_level.get(position)) {
			case 0:
				lv_level.setImageResource(R.drawable.wifi_0);
				break;
			case 1:
				lv_level.setImageResource(R.drawable.wifi_1);
				break;
			case 2:
				lv_level.setImageResource(R.drawable.wifi_2);
				break;
			case 3:
				lv_level.setImageResource(R.drawable.wifi_3);
				break;
			default:
				lv_level.setImageResource(R.drawable.wifi_4);
				break;
            }
        }
        
         
        return convertView;
    }
     
    // 외부에서 아이템 추가 요청 시 사용
    public void add(String ssid, String ch, int level ) {
    	m_List_ch.add(ch); 
    	m_List_ssid.add(ssid);
    	m_List_level.add(level);
    }
     
    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
    	m_List_ch.remove(_position);
    }
}