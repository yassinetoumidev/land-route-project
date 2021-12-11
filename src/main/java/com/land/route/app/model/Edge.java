package com.land.route.app.model;

public class Edge {

	private final Vertex source;
	private final Vertex destination;
	private final int weight = 1;

	public Edge(Vertex source, Vertex destination) {

		this.source = source;
		this.destination = destination;

	}

	public Vertex getDestination() {
		return destination;
	}

	public Vertex getSource() {
		return source;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return source + " " + destination;
	}

}