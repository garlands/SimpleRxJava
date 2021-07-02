package net.jp.garlands.simplerxjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TestFragment.newInstance("1","2"))
                    .commitNow();
        }

        RxBus.toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .ofType(TestEvent.class)
                .subscribe(event ->{
                   if( event.getStatusResult() == TestEvent.Result.SUCCESS) {
                       String str = event.getStatusString();
                       Log.d("TEST", "receive event :" + str);
                   } else {
                       Log.d("TEST", "receive event failed");
                   }
                });
    }
}