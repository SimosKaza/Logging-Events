package database.DAO;

import com.datastax.driver.core.ResultSet;
import event.EventResource;
import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EventResourceDAOTest {

    @Rule
    public CassandraCQLUnit cassandraCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("simple.cql","logevents"));

    @Test
    public void insert() throws Exception {
        EventResourceDAO eventResourceDAO = new EventResourceDAO();
        eventResourceDAO.insert(cassandraCQLUnit.session, new EventResource((short)1, (long) 103401345, "toula"));
        ResultSet result = cassandraCQLUnit.session.execute("select * from event where time=103401345");
        assertThat(result.iterator().next().getString("message"), is("toula"));
    }
}