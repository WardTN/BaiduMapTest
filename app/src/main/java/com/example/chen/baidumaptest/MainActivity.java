package com.example.chen.baidumaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivity extends AppCompatActivity {
   public TextView mTextView;
    public LocationClient mlc;
   public   StringBuilder position;
    private void initLocation(){
        //findview
        mTextView = (TextView) findViewById(R.id.first);
        //客户端配置对象
        LocationClientOption lco = new LocationClientOption();
        //定位时间间隔，就是我们上面说的f服务要实现的功能
        //f服务的运行才能保证一直定位
        // span参数不能小于1000ms
        lco.setScanSpan(4000);
        //初始化定位客户端
        mlc = new LocationClient(getApplicationContext());
        //给定位客户端注册自定义监听器
        mlc.registerLocationListener(new LocationListener());
        //设置一些配置选项
        //当然这个lco配置还有很多，具体在下面我会列举，
        mlc.setLocOption(lco);
        //启动定位客户端
        mlc.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLocation();
    }

    public class LocationListener implements BDLocationListener{


        //接受位置信息
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //通过一个StringBuilder来存放信息并展示
            position = new StringBuilder();
            position.append("经度：").append(bdLocation.getLongitude())
                    .append("\n纬度：").append(bdLocation.getLatitude())
                    .append("\n定位方式：");
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                position.append("GPS");
            }else if(bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                position.append("网络");
            }
            //在UI线程更新
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(position);
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlc.stop();
    }
}
