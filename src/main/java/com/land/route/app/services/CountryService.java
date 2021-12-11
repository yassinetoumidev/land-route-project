package com.land.route.app.services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.land.route.app.exceptions.CountryNotFoundException;
import com.land.route.app.exceptions.CrossNotFoundException;
import com.land.route.app.model.Country;
import com.land.route.app.model.Edge;
import com.land.route.app.model.Graph;
import com.land.route.app.model.Route;
import com.land.route.app.model.Vertex;

@Service
public class CountryService {
	private static final TypeReference<List<Country>> TYPE_REF = new TypeReference<List<Country>>() {
	};
	private final String BASE_URL = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json";

	private Graph graph;

	@PostConstruct
	public void init() throws IOException {
		List<Country> countries = getCountries();
		graph = convertListToGraph(countries);
	}

	public Route getRoute(String origin, String destination) throws CrossNotFoundException, CountryNotFoundException {

		boolean existOrigin = graph.getVertexes().contains(new Vertex(origin));
		if (!existOrigin) {
			throw new CountryNotFoundException("Origin country " + origin + " not found");
		}
		boolean existDest = graph.getVertexes().contains(new Vertex(destination));
		if (!existDest) {
			throw new CountryNotFoundException("Destination country " + destination + " not found");
		}
		LinkedList<Vertex> path = getPath(graph, origin, destination);

		if (path == null) {
			throw new CrossNotFoundException("There is no land crossing");
		}
		Route route = new Route();
		route.setRoute(path);
		return route;

	}

	private List<Country> getCountries() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(new URL(BASE_URL), TYPE_REF);
	}

	private Graph convertListToGraph(List<Country> countries) {

		List<Vertex> nodes = new ArrayList<Vertex>();
		List<Edge> edges = new ArrayList<Edge>();

		for (Country country : countries) {
			Vertex location = new Vertex(country.getCca3());
			nodes.add(location);
			for (String border : country.getBorders()) {
				Edge lane = new Edge(location, new Vertex(border));
				edges.add(lane);
			}
		}

		Graph graph = new Graph(nodes, edges);
		return graph;
	}

	private LinkedList<Vertex> getPath(Graph graph, String src, String dest) {
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(new Vertex(src));
		LinkedList<Vertex> path = dijkstra.getPath(new Vertex(dest));
		return path;
	}

}
