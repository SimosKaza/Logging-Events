package database.DAO;

import com.datastax.driver.core.Session;
import event.EventResource;

public class EventResourceDAO  {

    public void insert(Session session, EventResource event) {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("logevents.event").append("(version, time, message ) ")
                .append("VALUES (")
                .append(event.getVersion()).append(", ")
                .append(event.getTime()).append(", '")
                .append(event.getMessage()).append("');");

        String query = sb.toString();
        session.execute(query);
    }
}
