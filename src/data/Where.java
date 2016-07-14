package data;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Tbaios on 13.07.2016.
 */
public class Where {

    @Expose
    private List<Corperation> corperations;

    public Where(){
        this.corperations = new LinkedList<>();
    }

    public void addCorp(Corperation c){
        this.corperations.add(c);
    }

    public Corperation getCorp(String s){
        try{
            List<Corperation> l = corperations.stream().filter(e->e.getName().equalsIgnoreCase(s)).collect(Collectors.toList());
            if(l.size() > 0){
                return l.get(0);
            }else{
                return null;
            }
        }catch(Exception e){
            return null;
        }

    }

    public List<Corperation> getCorperations() {
        return corperations;
    }

    public void setCorperations(List<Corperation> corperations) {
        this.corperations = corperations;
    }

    @Override
    public String toString(){
        Gson g = new Gson();

        return g.toJson(this.corperations);
    }

    public void deleteCorp(String selectedItem) {
        for (int i = 0; i < this.corperations.size(); i++) {
            if(this.corperations.get(i).getName().equalsIgnoreCase(selectedItem)){
                this.corperations.remove(i);
            }
        }
    }
}
