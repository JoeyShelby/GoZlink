package com.joey.gozlink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 2022-05-28
 * JoeyShelby
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 打开 app 后立即打开 zlink
        openApp("com.zjinnova.zlink");

        /*长按切换启动器*/
        findViewById(R.id.backgroundImage).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent paramIntent = new Intent("android.intent.action.MAIN");
                paramIntent.setComponent(new ComponentName("android", "com.android.internal.app.ResolverActivity"));
                paramIntent.addCategory("android.intent.category.DEFAULT");
                paramIntent.addCategory("android.intent.category.HOME");
                startActivity(paramIntent);
                return false;
            }
        });

        /*单击打开 zlink*/
        findViewById(R.id.backgroundImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openApp("com.zjinnova.zlink");
            }
        });
    }

    /**
     * 根据包名打开 app
     * @param packageName 包名
     */
    private void openApp(String packageName) {
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES); //检测 packageName 是否安装，未安装则进入 catch ，重新选择启动器
            startActivity(packageManager.getLaunchIntentForPackage(packageName)); //启动 packageName
        } catch (PackageManager.NameNotFoundException e) {  //报错，关闭程序
            e.printStackTrace();
            Toast.makeText(this, "未检测到 "+ packageName, Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            this.finish();
        }
    }

}