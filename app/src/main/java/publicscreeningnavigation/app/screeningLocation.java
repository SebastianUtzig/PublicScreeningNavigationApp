package publicscreeningnavigation.app;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Jo on 13.06.14.
 */
public class screeningLocation {

    private String name;
    private String description;
    private ArrayList<String> tags;
    private int identifier;
    private LatLng position;

    screeningLocation(String name, int identifier, LatLng position) {
        this.name = name;
        this.identifier = identifier;
        this.position = position;
        this.tags = new ArrayList<String>();
    }

    public String toString() {
        return this.name+" at "+this.position.toString();
    }

    //function returns true if adding tag was successful
    //returns false if not, which is the case if the tag was already there
    public boolean addTag(String tag) {
        if (!this.tags.contains(tag)){
            this.tags.add(tag);
            return true;
        }
        else {
            return false;
        }
    }

    //returns true if no tag in tags array was there yet
    //returns false if there were tags that were not added
    public void addTags(ArrayList<String> tags){
        for (String tag : tags){
            this.tags.add(tag);
        }
    }

    public void setDescription(String description){
        this.description = description;
    }

}
