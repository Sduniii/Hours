package data;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tbaios on 13.07.2016.
 */
public class Where {

    @Expose
    private List<Corperation> state;

    public Where(){
        this.state = new LinkedList<>();
    }

    public void addCorp(Corperation c){
        this.state.add(c);
    }

    public Corperation getCorp(int s){
        try{
            Corperation c = this.state.get(s);
            return c;
        }catch(Exception e){
            return null;
        }

    }

    public List<Corperation> getState() {
        return state;
    }

    public void setState(List<Corperation> state) {
        this.state = state;
    }

    @Override
    public String toString(){
        Gson g = new Gson();

        return g.toJson(this.state);
    }
}
