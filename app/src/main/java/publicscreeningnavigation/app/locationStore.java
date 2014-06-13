package publicscreeningnavigation.app;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jo on 13.06.14.
 */
public class locationStore {

    private static locationStore instance = null;
    private static ArrayList<screeningLocation> locations = null;

    public static locationStore getInstance() {
        if (instance == null){
            instance = new locationStore();
        }
        return instance;
    }

    public static ArrayList<screeningLocation> sharedLocations(){
        if (instance == null){
            instance = new locationStore();
        }
        return locations;
    }

    private locationStore() {
        locations = createScreeningSet();
    }

    private static ArrayList<screeningLocation> createScreeningSet() {

        screeningLocation reserveBank = new screeningLocation("Reservebank",1,new LatLng(50.974296,11.327415));
        reserveBank.addTag("smoking");
        reserveBank.addTag("food");
        reserveBank.addTag("alcohol");

        screeningLocation weimarHalle = new screeningLocation("Weimarhalle",2,new LatLng(50.983552,11.325311));
        weimarHalle.addTag("no smoking");
        weimarHalle.addTag("food");
        weimarHalle.addTag("no alcohol");

        screeningLocation atrium = new screeningLocation("Atrium Weimar",3,new LatLng(50.985173,11.329163));
        atrium.addTag("no smoking");
        atrium.addTag("food");
        atrium.addTag("no alcohol");

        screeningLocation zumFalken = new screeningLocation("Zum Falken",4,new LatLng(50.97554,11.322082));
        zumFalken.addTag("smoking");
        zumFalken.addTag("food");
        zumFalken.addTag("alcohol");

        screeningLocation leibniz = new screeningLocation("Studentenwohnheim Leibnizalle",4,new LatLng(50.979256,11.338497));
        leibniz.addTag("smoking");
        leibniz.addTag("barbecuse - bring your own food");
        leibniz.addTag("bring your own alcohol");

        ArrayList<screeningLocation> locations = new ArrayList<screeningLocation>();
        locations.add(reserveBank);
        locations.add(weimarHalle);
        locations.add(atrium);
        locations.add(zumFalken);
        locations.add(leibniz);

        return locations;
    }
}
