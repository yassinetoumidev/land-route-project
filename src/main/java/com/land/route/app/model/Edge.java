package com.land.route.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Edge {

	private final Vertex source;
	private final Vertex destination;
	private final int weight = 1;



}