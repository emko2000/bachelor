package bachelor;

import java.util.Set;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.NetworkUtils;
import org.locationtech.jts.geom.Coordinate;
import org.matsim.core.utils.geometry.geotools.MGC;
import org.matsim.core.utils.geometry.transformations.GeotoolsTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.gis.ShapeFileReader;
import org.locationtech.jts.geom.Geometry;
import org.matsim.utils.gis.shp2matsim.ShpGeometryUtils;
import org.matsim.freightDemandGeneration.FreightDemandGeneration;
import java.nio.file.Path;
import java.util.stream.Collectors;


public class NetworkModification {

	public static void main(String[] args) {

		int counter = 0;

		//read network file
		String networkLoc = "https://svn.vsp.tu-berlin.de/repos/public-svn/matsim/scenarios/countries/de/berlin/berlin-v5.5-10pct/input/berlin-v5.5-network.xml.gz";
		Network network = NetworkUtils.readNetwork(networkLoc);

		//read shapefile
		String shapefile = "scenarios/freightDemandGeneration/testShape/Bezirke_-_Berlin/Berlin_Bezirke.shp";

		//transform coordinates from the shapefile to matsim coordinates
		//var transformation = TransformationFactory.getCoordinateTransformation("EPSG:31468", "EPSG:3857");
		var transformation = new GeotoolsTransformation("EPSG:31468", "EPSG:3857");



		var feature = ShapeFileReader.getAllFeatures(shapefile);

		// Create a list for the coordinates
		//var shapeFileGeometries = features.stream()
		//		.map(simpleFeature -> (Geometry) simpleFeature.getDefaultGeometry())
		//		.collect(Collectors.toList());

		//Transfer the geotoolcoordinate to matsim coordinate
		for (var geometryList : feature){
			var coord = geometryList.getCoordinate();
			var transformCoord = transformation.transform(coord);
			var geotoolspoint = MGC.coordinate2Point(geometryList);



			if (shapeFileGeometries.contains(network)){
						counter = counter + 1;
						Link newLink = network.getFactory().createLink(Id.createLinkId(counter), network.getNodes().get(Id.createNodeId("100027768")), network.getNodes().get(Id.createNodeId(shapeFileGeometries)));
						newLink.setAllowedModes(Set.of("drone"));
						network.addLink(newLink);
			}
		}
		var test = shapeFileGeometries;
		System.out.println(test);

		/*TODO:
		 * shape file Berlin Bezirke einlesen
		 * Links filtern nach Bezirk Charlottenburg-Wilmersdorf
		 * ^(mit einer schleife eine knoten durchgehen und gucken ob sie im shape file ist, wenn ja dann link speichern)
		 * neue Links für Drohne erzeugen (Hin-und Rückweg)
		 *
		 * Hinweis: Iteration 300, Zeit in Sekunde, Infinite auswählen, csv nach excel kopieren und einfügen,
		 * in csv-file sind depots
		 *
		 *
		 *
		 */

	}


}
