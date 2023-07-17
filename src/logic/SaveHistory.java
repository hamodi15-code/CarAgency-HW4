package logic;

import utils.Capture;

import java.util.Stack;

public class SaveHistory {
    private Stack<Capture> history = new Stack<>();

    public void save(Capture capture){
        history.push(capture);
        if(history.size()>3) history.remove(3);
    }

    public Capture load(){
        return history.pop();
    }

    public int getSize(){
        return history.size();
    }
}
