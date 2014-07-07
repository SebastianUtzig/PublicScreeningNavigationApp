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

    private static String server_ip ="192.168.1.59";

    public static locationStore getInstance() {
        if (instance == null){
            instance = new locationStore();
        }
        return instance;
    }

    public static String getServerAddress(){
        if (instance == null){
            instance = new locationStore();
        }
        return server_ip;}
    public static void setServerAddress(String ip){
        if (instance == null){
            instance = new locationStore();
        }
        server_ip=ip;
    }

    public static ArrayList<screeningLocation> sharedLocations(){
        if (instance == null){
            instance = new locationStore();
        }
        return locations;
    }

    public static void addLocation(screeningLocation location){
        if (instance == null){
            instance = new locationStore();
        }
        locations.add(location);
    }

    private locationStore() {
        locations = new ArrayList<screeningLocation>();
    }

    // init offline set of locations - is not used yet
    private static ArrayList<screeningLocation> createScreeningSet() {

        screeningLocation reserveBank = new screeningLocation("Reservebank",1,new LatLng(50.974296,11.327415));
        reserveBank.addTag("smoking");
        reserveBank.addTag("food");
        reserveBank.addTag("alcohol");
        reserveBank.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        screeningLocation weimarHalle = new screeningLocation("Weimarhalle",2,new LatLng(50.983552,11.325311));
        weimarHalle.addTag("no smoking");
        weimarHalle.addTag("food");
        weimarHalle.addTag("no alcohol");
        weimarHalle.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        screeningLocation atrium = new screeningLocation("Atrium Weimar",3,new LatLng(50.985173,11.329163));
        atrium.addTag("no smoking");
        atrium.addTag("food");
        atrium.addTag("no alcohol");
        atrium.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        screeningLocation zumFalken = new screeningLocation("Zum Falken",4,new LatLng(50.97554,11.322082));
        zumFalken.addTag("smoking");
        zumFalken.addTag("food");
        zumFalken.addTag("alcohol");
        zumFalken.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        screeningLocation leibniz = new screeningLocation("Studentenwohnheim Leibnizalle",5,new LatLng(50.979256,11.338497));
        leibniz.addTag("smoking");
        leibniz.addTag("barbecuse - bring your own food");
        leibniz.addTag("bring your own alcohol");
        leibniz.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        ArrayList<screeningLocation> locations = new ArrayList<screeningLocation>();
        locations.add(reserveBank);
        locations.add(weimarHalle);
        locations.add(atrium);
        locations.add(zumFalken);
        locations.add(leibniz);

        return locations;
    }
}
