package publicscreeningnavigation.app;

import java.util.ArrayList;

/**
 * Created by Jo on 13.06.14.
 */
public class filter {

    private static filter instance = null;

    public static filter getInstance(){
        if (instance == null){
            instance = new filter();
        }
        return instance;
    }

    private filter() {}

    public ArrayList<screeningLocation> filterLocationsForName(String nameToFilter){
        ArrayList<screeningLocation> filtered = new ArrayList<screeningLocation>();
        for (screeningLocation location : locationStore.sharedLocations()){
            if (location.nameIsLike(nameToFilter)){
                filtered.add(location);
            }
        }
        return filtered;
    }

    public ArrayList<screeningLocation> filterLocationsForTag(String tagToFilter){
        ArrayList<screeningLocation> filtered = new ArrayList<screeningLocation>();
        for (screeningLocation location : locationStore.sharedLocations()){
            if (location.isTaggedWith(tagToFilter)){
                filtered.add(location);
            }
        }
        return filtered;
    }
}
