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
    
    // ���ڿ��� ���� �� ArrayList
    private ArrayList<String>   m_List_ch;
    private ArrayList<String>   m_List_ssid;
    private ArrayList<Integer>   	m_List_level;
    
    // ������
    public CustomAdapter() {
    	m_List_ch = new ArrayList<String>();
    	m_List_ssid = new ArrayList<String>();
    	m_List_level = new ArrayList<Integer>();
    }
 
    // ���� �������� ���� ����
    @Override
    public int getCount() {
        return m_List_ch.size();
    }
 
    // ���� �������� ������Ʈ�� ����, Object�� ��Ȳ�� �°� �����ϰų� ���Ϲ��� ������Ʈ�� ĳ�����ؼ� ���
    @Override
    public Object getItem(int position) {
        return m_List_ch.get(position);
    }
 
    // ������ position�� ID �� ����
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    // ��� �� ������ ����
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
         
        // ����Ʈ�� ������鼭 ���� ȭ�鿡 ������ �ʴ� �������� converView�� null�� ���·� ��� ��
        if ( convertView == null ) {
            // view�� null�� ��� Ŀ���� ���̾ƿ��� ��� ��
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);
            
            TextView tv_ssid = (TextView) convertView.findViewById(R.id.row_ssid);
            tv_ssid.setTextColor(Color.BLACK);
            tv_ssid.setText(m_List_ssid.get(position).toString());
            tv_ssid.setTextSize(13);
            
            // TextView�� ���� position�� ���ڿ� �߰�
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
            
            // TextView�� ���� position�� ���ڿ� �߰�
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
     
    // �ܺο��� ������ �߰� ��û �� ���
    public void add(String ssid, String ch, int level ) {
    	m_List_ch.add(ch); 
    	m_List_ssid.add(ssid);
    	m_List_level.add(level);
    }
     
    // �ܺο��� ������ ���� ��û �� ���
    public void remove(int _position) {
    	m_List_ch.remove(_position);
    }
}