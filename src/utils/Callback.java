package utils;

import java.util.ArrayList;
import java.util.List;

public class Callback {
    private boolean isEmpty;
    private boolean alreadyStarted = false;
    private List<Call> calls = new ArrayList<>();

    public static final Callback EMPTY = new Callback(true);

    private Callback(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public Callback(){
        this(false);
    }


    public void observe(Call call){
        if(isEmpty) {
            alreadyStarted = true;
            call.onStartProcess();
            call.onFinishProcess(false);
            return;
        }
        calls.add(call);

        if(alreadyStarted){
            call.onStartProcess();
        }
    }

    public void notifyOnFinish(boolean state){
        for (Call call : calls) {
            call.onFinishProcess(state);
        }
    }
    public void notifyOnStart(){
        alreadyStarted = true;
        for (Call call : calls) {
            call.onStartProcess();
        }
    }
}
