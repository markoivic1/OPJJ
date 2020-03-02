package hr.fer.zemris.java.hw16.rest;


import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Class which uses REST.
 * It is used to get tag names from a file which are then sent as json.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
@Path("/tags")
public class DrawTags{

    /**
     * Context of a servlet.
     * Used to get the real path to the file that contains data info.
     */
    @Context
    private ServletContext context;

    /**
     * Method which processes file and filters avaliable tags.
     * @return Returns all of the available tags.
     */
    @GET
    @Produces("application/json")
    public Response drawTags() {
        Set<String> tags = new HashSet<>();
        fillTags(tags);
        JSONObject result = new JSONObject();
        result.put("tags", tags);
        return Response.status(Status.OK).entity(result.toString()).build();
    }

    /**
     * Method used to fill given set with tags taken from '/WEB-INF/opisnik.txt'
     * @param set Set which will be filled.
     */
    private void fillTags(Set<String> set) {
        java.nio.file.Path path = Paths.get(context.getRealPath("/WEB-INF/opisnik.txt"));
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Unable to open file 'opisnik.txt'");
            return;
        }
        for (int i = 2; i < lines.size(); i+=3) {
            set.addAll(Arrays.stream(lines.get(i).split(", ")).collect(Collectors.toList()));
        }
    }
}
