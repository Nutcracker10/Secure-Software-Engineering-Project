package ie.ucd.dfh.model;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HibernateSearchDao {
    @PersistenceContext
    private EntityManager entityManager;

    private QueryBuilder getQueryBuilderArtifact() throws InterruptedException {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();

        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Flight.class)
                .get();
    }

    private FullTextQuery getJpaQueryFlight(org.apache.lucene.search.Query luceneQuery) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.createFullTextQuery(luceneQuery, Flight.class);
    }

    public List<Flight> fuzzySearchFlight(String searchTerm) throws InterruptedException {
        Query fuzzyQuery = getQueryBuilderArtifact().keyword().fuzzy()
                .withEditDistanceUpTo(2).withPrefixLength(0).onFields("departure", "arrival","depAirport","arrAirport")
                .matching(searchTerm).createQuery();


        List<Flight> results = getJpaQueryFlight(fuzzyQuery).getResultList();
        return results;
    }

}
