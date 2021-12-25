package com.land.route.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class Graph {

    private final List<Vertex> vertexes;
    private final List<Edge> edges;



}
