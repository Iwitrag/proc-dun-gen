package cz.iwitrag.procdungen.generator.implementations.physical;

import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungen.api.data.Point;
import cz.iwitrag.procdungen.api.data.Room;
import cz.iwitrag.procdungen.generator.GeneratorException;
import cz.iwitrag.procdungen.util.RandomGenerator;
import delaunay_triangulation.Delaunay_Triangulation;
import delaunay_triangulation.Point_dt;
import delaunay_triangulation.Triangle_dt;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Connects room using delaunay triangulation and minimum spanning tree
 */
public class RoomConnector {

    private Dungeon dungeon;
    private RandomGenerator randomGenerator;

    public RoomConnector(Dungeon dungeon, RandomGenerator randomGenerator) {
        this.dungeon = dungeon;
        this.randomGenerator = randomGenerator;
    }

    public void connectRooms() throws GeneratorException {
        Set<Room[]> triangles;
        double averageWeight;
        try {
            triangles = delaunayTriangulation();
        } catch (Exception ex) {
            throw new GeneratorException("Error in Delaunay Triangulation: " + ex.getMessage());
        }
        try {
            averageWeight = primMinimumSpanningTree();
        } catch (Exception ex) {
            throw new GeneratorException("Error in calculating minimum spanning tree: " + ex.getMessage());
        }
        try {
            connectAloneRooms(triangles, averageWeight);
        } catch (Exception ex) {
            throw new GeneratorException("Error in connecting alone rooms: " + ex.getMessage());
        }

    }

    private Point_dt[] getRoomCenters() {
        Set<Room> rooms = dungeon.getRooms();
        Point_dt[] centers = new Point_dt[rooms.size()];
        int i = 0;
        for (Room room : rooms) {
            Point<Double> center = room.getCenter();
            centers[i++] = new Point_dt(center.getX(), center.getY());
        }
        return centers;
    }

    private Set<Room[]> delaunayTriangulation() throws GeneratorException {
        Delaunay_Triangulation delaunay = new Delaunay_Triangulation(getRoomCenters());
        Set<Room[]> triangles = new LinkedHashSet<>();
        for (Iterator<Triangle_dt> iterator = delaunay.trianglesIterator(); iterator.hasNext(); ) {
            Triangle_dt triangle = iterator.next();
            Room room1 = triangle.p1() != null ? getRoomByCenter(triangle.p1()) : null;
            Room room2 = triangle.p2() != null ? getRoomByCenter(triangle.p2()) : null;
            Room room3 = triangle.p3() != null ? getRoomByCenter(triangle.p3()) : null;
            Room[] roomsInTriangle = new Room[]{room1, room2, room3};
            triangles.add(roomsInTriangle);
            if (room1 != null && room2 != null)
                room1.addConnectedRoom(room2, true);
            if (room1 != null && room3 != null)
                room1.addConnectedRoom(room3, true);
            if (room2 != null && room3 != null)
                room2.addConnectedRoom(room3, true);
        }
        return triangles;
    }

    private double primMinimumSpanningTree() {
        SimpleWeightedGraph<Room, DefaultEdge> graph = new SimpleWeightedGraph<>(DefaultEdge.class);
        // First add vertexes
        for (Room room : dungeon.getRooms()) {
            graph.addVertex(room);
        }
        // Then connect them by edges
        double totalWeight = 0;
        for (Room room : dungeon.getRooms()) {
            for (Room room2 : room.getConnectedRooms()) {
                DefaultEdge edge = graph.addEdge(room, room2);
                if (edge != null) {
                    double weight = room.getCenter().getDistance2D(room2.getCenter(), false);
                    totalWeight += weight;
                    graph.setEdgeWeight(edge, weight);
                }
            }
            room.clearConnectedRooms();
        }
        PrimMinimumSpanningTree<Room, DefaultEdge> primMinimumSpanningTree = new PrimMinimumSpanningTree<>(graph);
        SpanningTreeAlgorithm.SpanningTree<DefaultEdge> tree = primMinimumSpanningTree.getSpanningTree();
        int edgeCount = 0;
        for (DefaultEdge edge : tree) {
            edgeCount++;
            graph.getEdgeSource(edge).addConnectedRoom(graph.getEdgeTarget(edge), true);
        }
        return totalWeight/edgeCount/3;
    }

    private void connectAloneRooms(Set<Room[]> triangles, double averageWeight) {
        for (Room room : dungeon.getRooms()) {
            int size = room.getConnectedRoomsSize();
            double chance = Math.max(0.00, 100.00-(50.00*size)); // 2+ connections = no chance to connect
            if (randomGenerator.chance(chance)) {
                Set<Room> eligibleRooms = new LinkedHashSet<>();
                for (Room[] triangle : triangles) {
                    for (Room triangleRoom : triangle) {
                        if (triangleRoom != null && triangleRoom == room) {
                            for (Room neighbour : triangle) {
                                if (neighbour != null && neighbour != triangleRoom && !triangleRoom.isConnected(neighbour))
                                    eligibleRooms.add(neighbour);
                            }
                        }
                    }
                }
                for (Room eligibleRoom : randomGenerator.getShuffledList(eligibleRooms)) {
                    double weight = room.getCenter().getDistance2D(eligibleRoom.getCenter(), false);
                    if (weight <= averageWeight) {
                        room.addConnectedRoom(eligibleRoom, true);
                        break;
                    }
                }
            }
        }
    }

    private Room getRoomByCenter(Point_dt point) throws GeneratorException {
        for (Room room : dungeon.getRooms()) {
            Point<Double> center = room.getCenter();
            if (center.getX() == point.x() && center.getY() == point.y())
                return room;
        }
        throw new GeneratorException("Failed to return Room from centerpoint used in triangulation");
    }
}
