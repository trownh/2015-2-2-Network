package com.network.termproj;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView main_tv_throughput;
	private TextView main_tv_singal;
	private TextView main_tv_rtt;
	private TextView main_tv_rttserver;
	private TextView main_tv_ch;
	private TextView main_tv_ssid;
	private TextView main_tv_defaultrouter;
	
	private TextView main_tv_server;
	
	private ListView main_lv;
	
	private EditText main_et_url;
		
	
	private long Starttime;
	private int filesize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		main_tv_throughput = (TextView)findViewById(R.id.main_tv_throughput);
		main_tv_singal = (TextView)findViewById(R.id.main_tv_singal);
		main_tv_rtt = (TextView)findViewById(R.id.main_tv_rtt);
		main_tv_rttserver = (TextView)findViewById(R.id.main_tv_rttserver);
		main_tv_ch = (TextView)findViewById(R.id.main_tv_ch);
		main_tv_ssid = (TextView)findViewById(R.id.main_tv_ssid);
		main_tv_defaultrouter =  (TextView)findViewById(R.id.main_tv_gateway);
		
		main_tv_server = (TextView)findViewById(R.id.main_tv_server);
		
		main_lv = (ListView)findViewById(R.id.main_lv);	
		main_et_url = (EditText)findViewById(R.id.main_et_url);
		
		main_et_url.setText("http://cs.yonsei.ac.kr/cs/upload/Community/201511/95ee7a38cf53365d405f179b5ba5c78a3570c8fd.pdf");
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void mOnClick(View v)
    {
		if(v.getId()==R.id.main_btn_connect)
    	{
			ConnectivityManager manager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if(wifi.isConnected()){
					

				startDownLoad();
				get_signal_Str();
				get_channel();
				get_rtt();
				main_lv = null;
				
			}
			else{
				Toast.makeText(this, "와이파이에 연결되지 않았습니다", 1).show();
			}
    	}else if(v.getId()==R.id.main_btn_sel){
    		
    		final String[] items = {"컴퓨터과학", "공과대학", "서울대컴공과", "고려대공대"};
    		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setTitle("선택해주세요");
    		builder.setItems(items, new DialogInterface.OnClickListener()
    		{
    			@Override
    			public void onClick(DialogInterface dialog, int which)
    			{
    				Toast.makeText(MainActivity.this,
    				items[which] + " 선택", Toast.LENGTH_SHORT).show();
    				if(items[which].equals("컴퓨터과학")){
    					main_et_url.setText("http://cs.yonsei.ac.kr/cs/upload/Community/201511/95ee7a38cf53365d405f179b5ba5c78a3570c8fd.pdf");
    				}else if(items[which].equals("공과대학")){
    					main_et_url.setText("https://engineering.yonsei.ac.kr/new/download.asp?id=1&file=2016%B3%E2%5F1%C7%"
    							+ "D0%B1%E2%5F%B1%B9%B0%A1%C0%E5%C7%D0%B1%DD%5F%BD%C5%C3%BB%B8%C5%B4%BA%BE%F31%2Epdf");
    				}else if(items[which].equals("서울대컴공과")){
    					main_et_url.setText("http://cse.snu.ac.kr/sites/default/files/node--notice/"
    							+ "%28%EB%B6%99%EC%9E%842%292015%ED%95%99%EB%85%84%EB%8F%84%20%EB%8F%99%EA%B3%84%EA%"
    							+ "B3%84%EC%A0%88%ED%95%99%EA%B8%B0%20%EC%88%98%EA%B0%95%EC%8B%A0%EC%B2%AD%20%EB%A7%A4%EB%89%B4%EC%96%BC.pdf");
    				}
    				else if(items[which].equals("고려대공대")){
    					main_et_url.setText("http://eng.korea.ac.kr/NFUpload/nfupload_down.php?tmp_name=20151008112758024bf4ovhz0.pdf&name="
    							+ "%EC%9B%8C%ED%81%AC%EC%83%B5+%EC%95%88%EB%82%B4%EB%AC%B8+%28%EB%93%B1%EB%A1%9D+%EB%B0%8F+%EC%98%A4%EC%8B%9C%"
    							+ "EB%8A%94%EA%B8%B8+%EC%95%88%EB%82%B4+%ED%8F%AC%ED%95%A8%29.pdf");
    				}
    			}
    		});
    		builder.show();
    		
    	}
    	else if(v.getId()==R.id.main_btn_clear){
    		
    		 main_tv_throughput.setText("");
    		 main_tv_singal.setText("");
    		 main_tv_rtt.setText("");
    		 main_tv_ch.setText("");
    		 main_tv_ssid.setText("");
    		 main_tv_defaultrouter.setText("");
    		
    		 main_tv_rttserver.setText("");
    		 
    		 main_tv_server.setText("");
    		 
    		 main_lv = null;
    		 m_Adapter = null;
    		 main_lv = (ListView)findViewById(R.id.main_lv);
    		 m_Adapter = new CustomAdapter();
    		 main_lv.setAdapter(m_Adapter);
    		 
    	}
		/*
		else if(v.getId()==R.id.main_btn_save){
		}
		*/
    }
	
	
	
	
	
	
	//RTT
	private void get_rtt() {
		// TODO Auto-generated method stub
		DhcpInfo d;
	    WifiManager wifii;
		wifii= (WifiManager) getSystemService(Context.WIFI_SERVICE);
        d=wifii.getDhcpInfo();
        main_tv_defaultrouter.setText( intToIp(d.gateway));
        
        
	}

	public class RoundTripTime extends AsyncTask<String, String, String> {

		private Context mContext;

		//
		Double[] result;
		Double[] result_server;
		private PipedOutputStream mPOut;
	      private PipedInputStream mPIn;
	      LineNumberReader mReader;
	      Process mProcess;
	      
	      Process mProcess_server;
	      private PipedOutputStream mPOut_server;
	      private PipedInputStream mPIn_server;
	      LineNumberReader mReader_server;
		//
		
	      String ServerIP="";
	      
		public RoundTripTime(Context context) {
			mContext = context;
		}

		@Override
		protected void onPreExecute() {
			
			mPOut = new PipedOutputStream();
			try
	         {
	            mPIn = new PipedInputStream(mPOut);
	            mReader = new LineNumberReader(new InputStreamReader(mPIn));
	         }
	         catch(Exception e)
	         {
	            
	         }
			
			mPOut_server = new PipedOutputStream();
			try
	         {
	            mPIn_server = new PipedInputStream(mPOut_server);
	            mReader_server = new LineNumberReader(new InputStreamReader(mPIn_server));
	         }
	         catch(Exception e)
	         {
	            
	         }
			
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			
			//RTT
			DhcpInfo dhcd;
		    WifiManager wifii;
			wifii= (WifiManager) getSystemService(Context.WIFI_SERVICE);
			dhcd=wifii.getDhcpInfo();
	       String Defaulut_ip = intToIp(dhcd.gateway);
	       
	       int initial = 0;
	       result = new Double[4];
	       int cnt;
	       try
	         {
	    	   
	            mProcess = new ProcessBuilder()
	               .command("/system/bin/ping",Defaulut_ip)
	               .redirectErrorStream(true)
	               .start();
	            
	            cnt = 0;
	            try
	            {
	            	
	            	InputStream in = mProcess.getInputStream();
	                OutputStream out = mProcess.getOutputStream();
	                byte[]b = new byte[1024];
	                int c;
	                //if(mReader.ready()) { mReader.readLine(); }
	                while((c = in.read(b))!= -1)
	                {
	                	
	                	
	                   if(cnt > 3)
	                   {
	                	   
	                      break;
	                   }
	                   
	                   mPOut.write(b, 0 ,c);
	                   

	                   
	                   while(mReader.ready())
	                   {
	                	   
	                    	  
	                      if(initial == 0)
	                      {
	            
	                    	 mReader.readLine();
	                    	  
	                    	 
	                    	 
	                         initial = 1;
	                         String origin = mReader.readLine();
	                         
	                         
	            
	                         int startIdx = origin.indexOf("time=");
	                         origin = origin.substring(startIdx+5);
	                         int endIdx = origin.indexOf("ms");
	                         origin = origin.substring(0, endIdx);
	                         result[cnt] = Double.parseDouble(origin);
	                         
	                      }
	                      else
	                      {
	                      String origin = mReader.readLine();
	                      
	                      int startIdx = origin.indexOf("time=");
	                      origin = origin.substring(startIdx+5);
	                      int endIdx = origin.indexOf("ms");
	                      origin = origin.substring(0, endIdx);
	                      result[cnt] = Double.parseDouble(origin);
	                      }
	                    }
	                   cnt++;
	                }   
	               out.close();
	               in.close();
	               mPOut.close();
	               mPIn.close();
	               
	            }
	            finally
	            {
	               mProcess.destroy();
	            }
	         }
	         catch(Exception e)
	         {
	            
	         }
	       /////////////////////////////////////////////////
	       
	       String IP="";
	       try {
	    	   URL url = new URL(main_et_url.getText().toString());
	    	   System.out.println(url.getHost());
	    	   InetAddress address = InetAddress.getByName(url.getHost());
	    	   String tmp = address.toString();
	    	   IP = tmp.substring(tmp.indexOf("/")+1,tmp.length());
			} catch (Exception e) {
				// TODO: handle exception
			}
	       ServerIP = IP;
	       
	       initial = 0;
	       result_server = new Double[4];
	       result_server[0] = 99999.0;
	       result_server[1] = 99999.0;
	       result_server[2] = 99999.0;
	       result_server[3] = 99999.0;
	       
	       try
	         {
	    	   mProcess_server = new ProcessBuilder()
	               .command("/system/bin/ping",IP)
	               .redirectErrorStream(true)
	               .start();
	            
	            cnt = 0;
	            try
	            {

	            	InputStream in_server = mProcess_server.getInputStream();
	                OutputStream out_server = mProcess_server.getOutputStream();
	                byte[]b = new byte[1024];
	                int c;
	                //if(mReader.ready()) { mReader.readLine(); }
	               
	                long systime = System.currentTimeMillis();
	                while(true){
	                	long systimenext = System.currentTimeMillis();
	                	if(systimenext - systime > 10000){
	                		break;
	                	}
	                	if(in_server.available()>0)
	                	{
	                		c = in_server.read((b));
	                		if(c!=-1)
	                			{
	                			 if(cnt > 3){
	 		                	    break;
	 		                   }
	 		                   
	 		                   mPOut_server.write(b, 0 ,c);
	 		                   
	 		                   
	 		                   
	 		                   while(mReader_server.ready())
	 		                   {
	 		                	   
	 		                    	  
	 		                      if(initial == 0)
	 		                      {
	 		            
	 		                    	 mReader_server.readLine();
	 		                    	  
	 		                    	 
	 		                    	 
	 		                         initial = 1;
	 		                         String origin = mReader_server.readLine();
	 		                         

	 		      	 	    	   	
	 		      	 	    	   	
	 		                         int startIdx = origin.indexOf("time=");
	 		                         origin = origin.substring(startIdx+5);
	 		                         int endIdx = origin.indexOf("ms");
	 		                         origin = origin.substring(0, endIdx);
	 		                         result_server[cnt] = Double.parseDouble(origin);
	 		                         
	 		                      }
	 		                      else
	 		                      {
	 		                      String origin = mReader_server.readLine();
	 		                      
	 		                      int startIdx = origin.indexOf("time=");
	 		                      origin = origin.substring(startIdx+5);
	 		                      int endIdx = origin.indexOf("ms");
	 		                      origin = origin.substring(0, endIdx);
	 		                      result_server[cnt] = Double.parseDouble(origin);
	 		                      }
	 		                    }
	 		                   cnt++;
	                		}
	                	}
	                	
	                	
	                }
	                /*
	                	while((c = in_server.read(b))!= -1)
		                {
		                   if(cnt > 3){
		                	    break;
		                   }
		                   
		                   mPOut_server.write(b, 0 ,c);
		                   
		                   
		                   
		                   while(mReader_server.ready())
		                   {
		                	   
		                    	  
		                      if(initial == 0)
		                      {
		            
		                    	 mReader_server.readLine();
		                    	  
		                    	 
		                    	 
		                         initial = 1;
		                         String origin = mReader_server.readLine();
		                         

		      	 	    	   	
		      	 	    	   	
		                         int startIdx = origin.indexOf("time=");
		                         origin = origin.substring(startIdx+5);
		                         int endIdx = origin.indexOf("ms");
		                         origin = origin.substring(0, endIdx);
		                         result_server[cnt] = Double.parseDouble(origin);
		                         
		                      }
		                      else
		                      {
		                      String origin = mReader_server.readLine();
		                      
		                      int startIdx = origin.indexOf("time=");
		                      origin = origin.substring(startIdx+5);
		                      int endIdx = origin.indexOf("ms");
		                      origin = origin.substring(0, endIdx);
		                      result_server[cnt] = Double.parseDouble(origin);
		                      }
		                    }
		                   cnt++;
		                }  
	                
	                	*/
	                 
	               out_server.close();
	               in_server.close();
	               mPOut_server.close();
	               mPIn_server.close();
	               
	            }
	            catch(Exception e)
	            {
	            	
	            }
	            finally
	            {
	               mProcess_server.destroy();
	               Log.v("ssv", "aaa");
	            }
	         }
	         catch(Exception e)
	         {
	            
	         }
	       
	       
			return null;
		}

		

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String unused) {		
			
			double r = (result[0] + result[1] + result[2] + result[3] )/ 4;
			r = (double)(Math.round(1000 * r))/1000;
			main_tv_rtt.setText(String.valueOf(r));
			
			double r2 = (result_server[0] + result_server[1] + result_server[2] + result_server[3] )/ 4;
			r2 = (double)(Math.round(1000 * r2))/1000;
			main_tv_rttserver.setText(String.valueOf(r2));
			
			main_tv_server.setText(ServerIP);
		}
	}
	
	
	//RTT

	public String intToIp(int addr) {
	    return  ((addr & 0xFF) + "." + 
	            ((addr >>>= 8) & 0xFF) + "." + 
	            ((addr >>>= 8) & 0xFF) + "." + 
	            ((addr >>>= 8) & 0xFF));
	}
	
	
	//채널 관련 메소드
	private CustomAdapter           m_Adapter;
	private void get_channel() {
		// TODO Auto-generated method stub
		WifiManager mWM = (WifiManager) this.getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = mWM.getConnectionInfo();

		int mCurrentFrequency=0;
		
		main_lv = null;
		m_Adapter = null;
		main_lv = (ListView)findViewById(R.id.main_lv);
		m_Adapter = new CustomAdapter();
		main_lv.setAdapter(m_Adapter);
		
		try {
			for (ScanResult scanResult : mWM.getScanResults()) {
				if (wifiInfo != null && wifiInfo.getBSSID() != null
					&& wifiInfo.getBSSID().equals(scanResult.BSSID)) {
					mCurrentFrequency = scanResult.frequency;
					main_tv_ch.setText( convertFrequencyToChannel(mCurrentFrequency)+"" );	
					main_tv_ssid.setText(scanResult.SSID);
					break;
				}
			}
			
		} catch (Exception e) {
		}
		
		try {
			for (ScanResult scanResult : mWM.getScanResults()) {
				if (wifiInfo != null && wifiInfo.getBSSID() != null && !(scanResult.SSID.equals(""))
						) {
					String ssid = scanResult.SSID;
					int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
					String ch = convertFrequencyToChannel(scanResult.frequency)+"";
					m_Adapter.add(ssid, ch, level);
				}
			}
		} catch (Exception e) {
		}
		
		
	}
	public static int convertFrequencyToChannel(int freq) {
		if(freq >= 2412 && freq <= 2484) {
			if (freq == 2484)
				return (freq-2412) /5;
			return (freq-2412) /5 + 1;
		}else if( freq >= 5170 && freq <= 5825) {
			return (freq-5170) /5 + 34;
		}
		else {
			return -1;
		}
	}
	//채널 관련 메소드


	


	public void get_signal_Str(){
		WifiManager wman = (WifiManager) this.getSystemService(Context.WIFI_SERVICE); 
		WifiInfo info = wman.getConnectionInfo();  
		int _rssi = info.getRssi();  
		main_tv_singal.setText(_rssi + ""); 
	}
	
	
	
	private void startDownLoad() {
		// TODO Auto-generated method stub
		String url = main_et_url.getText().toString();
		//new DownloadFileAsync(this).execute(url,"1","1");
		new DownloadFileAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
		
		new RoundTripTime(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
		//new RoundTripTime(this).execute(url,"1","1");
	}

	
	
	public class DownloadFileAsync extends AsyncTask<String, String, String> {

		private ProgressDialog mDlg;
		private Context mContext;

		public DownloadFileAsync(Context context) {
			mContext = context;
		}

		@Override
		protected void onPreExecute() {
			mDlg = new ProgressDialog(MainActivity.this);
			//mDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mDlg.setMessage("로딩중입니다..");
			mDlg.show(); 

			Starttime = System.currentTimeMillis();
			
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			int count = 0;
			
			
			try {
				Thread.sleep(100);
				URL url = new URL(params[0].toString());
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

				filesize = lenghtOfFile;
				
				/*
				File file = Environment.getRootDirectory();
				String path = file.getAbsolutePath()+"/Network_TestDownload";
				 */
				
				File file;
				String path ="storage/emulated/0/Network_TestDownload";
				
				file = new File(path);
				if(!file.exists()){
					file.mkdirs();
				}
				
				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(path+"/testdown.pdf");

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
								
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 수행이 끝나고 리턴하는 값은 다음에 수행될 onProgressUpdate 의 파라미터가 된다
			return null;
		}

		
		@Override
		protected void onProgressUpdate(String... progress) {
			if (progress[0].equals("progress")) {
				mDlg.setProgress(Integer.parseInt(progress[1]));
				mDlg.setMessage(progress[2]);
			} else if (progress[0].equals("max")) {
				mDlg.setMax(Integer.parseInt(progress[1]));
			}
		}
		 
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String unused) {
			mDlg.dismiss();
			//Toast.makeText(mContext, Integer.toString(result) + " total sum",
					//Toast.LENGTH_SHORT).show();
			
			main_tv_throughput.setText( (long)(filesize*8/( (System.currentTimeMillis()-Starttime)/1000.0 )) +"");
			//main_tv_rtt.setText( (result[0]+result[1]+result[2]+result[3])/4.0+"" );
		}
	}

	
	
}
