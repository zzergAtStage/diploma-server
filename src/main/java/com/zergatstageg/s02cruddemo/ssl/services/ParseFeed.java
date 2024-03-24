package com.zergatstageg.s02cruddemo.ssl.services;


import com.zergatstageg.s02cruddemo.ssl.domain.Earthquake;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processing.core.PApplet;
import processing.data.XML;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * The helper class to provide data, which will be showed on the map
 * Data sources as well as schemas is being used from
 */

public class ParseFeed {
    private final Logger logger = LoggerFactory.getLogger(ParseFeed.class);

    /**
     * This method is to parse a GeoRSS feed corresponding to earthquakes around
     * the globe.
     *
     * @param p        PApplet being used
     * @param fileName file name or URL for data source
     * @return List of "points"
     */
    public static List<Earthquake> parseEarthquake(PApplet p, String fileName) {
        List<Earthquake> features = new ArrayList<>();

        XML rss = p.loadXML(fileName);
        // Get all items
        XML[] itemXML = rss.getChildren("entry");
        Earthquake point;

        for (int i = 0; i < itemXML.length; i++) {

            // get location and create feature
            Location location = getLocationFromPoint(itemXML[i]);

            // if successful create PointFeature and add to list
            if (location != null) {
                point = new Earthquake(location);
                features.add(point);
            } else {
                continue;
            }

            // Sets title if existing
            String titleStr = getStringVal(itemXML[i], "title");
            if (titleStr != null) {
                point.putProperty("title", titleStr);
                // get magnitude from title
                point.putProperty("magnitude", Float.parseFloat(titleStr.substring(2, 5)));
            }

            // Sets depth(elevation) if existing
            float depthVal = getFloatVal(itemXML[i], "georss:elev");

            // NOT SURE ABOUT CHECKING ERR CONDITION BECAUSE 0 COULD BE VALID?
            // get one decimal place when converting to km
            int interVal = (int) (depthVal / 100);
            depthVal = (float) interVal / 10;
            point.putProperty("depth", Math.abs((depthVal)));


            // Sets age if existing
            XML[] catXML = itemXML[i].getChildren("category");
            for (int c = 0; c < catXML.length; c++) {
                String label = catXML[c].getString("label");
                if ("Age".equals(label)) {
                    String ageStr = catXML[c].getString("term");
                    point.putProperty("age", ageStr);
                }
            }


        }
        return features;
    }

    /**
     * Gets location from georss:point tag
     *
     * @param itemXML Node which has point as child
     * @return Location object corresponding to point
     */
    private static Location getLocationFromPoint(XML itemXML) {
        // set loc to null in case of failure
        Location loc = null;
        XML pointXML = itemXML.getChild("georss:point");

        // set location if existing
        if (pointXML != null && pointXML.getContent() != null) {
            String pointStr = pointXML.getContent();
            String[] latLon = pointStr.split(" ");
            float lat = Float.parseFloat(latLon[0]);
            float lon = Float.parseFloat(latLon[1]);

            loc = new Location(lat, lon);
        }

        return loc;
    }

    /*
     * Get String content from child node.
     */
    private static String getStringVal(XML itemXML, String tagName) {
        // Sets title if existing
        String str = null;
        XML strXML = itemXML.getChild(tagName);

        // check if node exists and has content
        if (strXML != null && strXML.getContent() != null) {
            str = strXML.getContent();
        }

        return str;
    }

    /*
     * Get float value from child node
     */
    private static float getFloatVal(XML itemXML, String tagName) {
        return Float.parseFloat(getStringVal(itemXML, tagName));
    }


    /**
     * This method is to parse a file containing airport information.
     * The file and its format can be found:
     * <a href="http://openflights.org/data.html#airport">...</a>
     * It is also included with the UC San Diego MOOC package in the file airports.dat
     *
     * @param p        - PApplet being used
     * @param fileName - file name or URL for data source
     */
    public static List<PointFeature> parseAirports(PApplet p, String fileName) {
        List<PointFeature> features = new ArrayList<PointFeature>();

        String[] rows = p.loadStrings(fileName);
        for (String row : rows) {

            // hot-fix for altitude when lat lon out of place
            int i = 0;

            // split row by commas not in quotations
            String[] columns = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            // get location and create feature
            float lat = Float.parseFloat(columns[6]);
            float lon = Float.parseFloat(columns[7]);

            Location loc = new Location(lat, lon);
            PointFeature point = new PointFeature(loc);

            // set ID to OpenFlights unique identifier
            point.setId(columns[0]);

            // get other fields from csv
            point.addProperty("name", columns[1]);
            point.putProperty("city", columns[2]);
            point.putProperty("country", columns[3]);

            // pretty sure IATA/FAA is used in routes.dat
            // get airport IATA/FAA code
            if (!columns[4].isEmpty()) {
                point.putProperty("code", columns[4]);
            }
            // get airport ICAO code if no IATA
            else if (!columns[5].isEmpty()) {
                point.putProperty("code", columns[5]);
            }

            point.putProperty("altitude", columns[8 + i]);

            features.add(point);
        }
        return features;
    }

    /**
     * This method is to parse a file containing airport route information.
     * The file and its format can be found:
     * <a href="http://openflights.org/data.html#route">...</a>
     * It is also included with the UC San Diego MOOC package in the file routes.dat
     *
     * @param p        - PApplet being used
     * @param fileName - file name or URL for data source
     */
    public static List<ShapeFeature> parseRoutes(PApplet p, String fileName) {
        List<ShapeFeature> routes = new ArrayList<ShapeFeature>();

        String[] rows = p.loadStrings(fileName);

        for (String row : rows) {
            String[] columns = row.split(",");

            ShapeFeature route = new ShapeFeature(Feature.FeatureType.LINES);

            // set id to be OpenFlights identifier for source airport
            // check that both airports on route have OpenFlights Identifier
            if (!columns[3].equals("\\N") && !columns[5].equals("\\N")) {
                // set "source" property to be OpenFlights identifier for source airport
                route.putProperty("source", columns[3]);
                // "destination property" -- OpenFlights identifier
                route.putProperty("destination", columns[5]);

                routes.add(route);
            }
        }
        return routes;
    }


    /**
     * This method is to parse a file containing life expectancy information from
     * the world bank.
     * The file and its format can be found:
     * http://data.worldbank.org/indicator/SP.DYN.LE00.IN
     * <p>
     * It is also included with the UC San Diego MOOC package
     * in the file LifeExpectancyWorldBank.csv
     *
     * @param p        - PApplet being used
     * @param fileName - file name or URL for data source
     * @return A HashMap of country->average age of death
     */
    public static HashMap<String, Float> loadLifeExpectancyFromCSV(PApplet p, String fileName) {
        // HashMap key: country ID and  data: lifeExp at birth
        HashMap<String, Float> lifeExpMap = new HashMap<String, Float>();

        // get lines of csv file
        String[] rows = p.loadStrings(fileName);

        // Reads country name and population density value from CSV row
        for (String row : rows) {
            // split row by commas not in quotations
            String[] columns = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            for (int i = columns.length - 1; i > 3; i--) {

                // check if value exists for year
                if (!columns[i].equals("..")) {
                    lifeExpMap.put(columns[3], Float.parseFloat(columns[i]));

                    // break once most recent data is found
                    break;
                }
            }
        }
        return lifeExpMap;
    }
}