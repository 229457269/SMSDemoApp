package com.zhaoq.smsdemoapp;

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends Activity {
	
	private Button mBtnBindPhone;
	
	private static String APPKey = "140c0b92a4dd8";
	private static String APPScrate="2920ea2910440cc7c603610fed58212b";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//��ʼ��
		SMSSDK.initSDK(getApplicationContext(), APPKey, APPScrate);
		
		mBtnBindPhone = (Button) this.findViewById(R.id.btn_bind_phone);
		
		//����  ����¼���
		mBtnBindPhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//ע���ֻ�����
				RegisterPage registerPage = new RegisterPage();
				//ע��ص��¼�
				registerPage.setRegisterCallback(new EventHandler(){
					//�¼���ɺ�  ���ã�
					@Override
					public void afterEvent(int event, int result, Object data) {
						//�ж�   ����Ƿ��Ѿ����   �����  ��ȡ����data
						if(result == SMSSDK.RESULT_COMPLETE){
							//��ȡ����
							HashMap<String, Object> maps = (HashMap<String, Object>) data;
							//����  �ֻ�����Ϣ  
							String country = (String) maps.get("country");
							
							String phone = (String) maps.get("phone");
							
							//�ύ��Ϣ
							submitUserInfo(country, phone);
						}
					}
				});
				//��ʾע�����
				registerPage.show(getApplicationContext());
			}
		});
	}
	
	/**
	 * �ύ�û���Ϣ
	 * @param country
	 * @param phone
	 */
	public void submitUserInfo(String country,String phone){
		//�ύ��Ϣ   uid
		Random r = new Random();
		String uid = Math.abs(r.nextInt())+"";//�û�id  �������
		String nickName = "zhaoq";//�ǳ�
		
		SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
	}
	
}
