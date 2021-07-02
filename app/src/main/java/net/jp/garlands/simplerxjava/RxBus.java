package net.jp.garlands.simplerxjava;

//import java.util.Observable;
//import java.util.Observer;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;


public class RxBus {
    private static final PublishSubject<Object> bus = PublishSubject.create();

    private RxBus() {

    }

    public static Observer<Object> toObserver() { return bus; }

    public static void send(Object event) { bus.onNext(event); }

    public static Observable<Object> toObservable() { return bus; }

    public static Disposable subscribe(Object event) { return toObservable().subscribe(); }
}
