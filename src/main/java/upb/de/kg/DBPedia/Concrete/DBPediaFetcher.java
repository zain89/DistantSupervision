package upb.de.kg.DBPedia.Concrete;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import upb.de.kg.DBPedia.Interfaces.IDataFetcher;
import upb.de.kg.DataModel.Domain;
import upb.de.kg.DataModel.Range;
import upb.de.kg.DataModel.Relation;
import upb.de.kg.DataModel.ResourcePair;
import upb.de.kg.Logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBPediaFetcher implements IDataFetcher {

    private static final int LIMIT = 10;
    private static final String OntologyPREFIX = "PREFIX dbo: <http://dbpedia.org/ontology/> ";
    private static final String RDFSPREFIX = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";

    /// Execute Query on the DBPedia Source
    private ResultSet executeQuery(String exeQuery) {
        Query query = QueryFactory.create(exeQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ((QueryEngineHTTP) qexec).addParam("timeout", "10000");

        // Execute.
        ResultSet rs = qexec.execSelect();
        return rs;
    }

    public List<Domain> getDomainList(Relation relation) {
        String domainQuery = String.format("%s%s SELECT Distinct ?domain WHERE { %s rdfs:domain ?domain .}", RDFSPREFIX, OntologyPREFIX, relation.toString());

        List<Domain> domainList = new ArrayList<Domain>();

        ResultSet resultSet = executeQuery(domainQuery);
        while (resultSet.hasNext()) {
            QuerySolution soln = resultSet.nextSolution();
            RDFNode x = soln.get("domain");
            String domainStr = x.toString();
            Domain domain = new Domain(domainStr);
            if (!domainList.contains(domain)) {
                domainList.add(domain);
            }
        }
        return domainList;
    }

    public List<Range> getRangeList(Relation relation) {
        String rangeQuery = String.format("%s%sSELECT Distinct ?range WHERE { %s rdfs:range ?range .}", RDFSPREFIX, OntologyPREFIX, relation.toString());

        ResultSet resultSet = executeQuery(rangeQuery);
        List<Range> rangeList = new ArrayList<Range>();

        while (resultSet.hasNext()) {
            QuerySolution soln = resultSet.nextSolution();
            RDFNode x = soln.get("range");
            String rangeStr = x.toString();
            Range range = new Range(rangeStr);
            if (!rangeList.contains(range)) {
                rangeList.add(range);
            }
        }
        return rangeList;
    }

    public List<ResourcePair> getResourcePair(Relation relation) throws IOException {
        //String rangeQuery = String.format("%s%s SELECT ?source ?target WHERE {?source dbo:spouse ?target} LIMIT %d", RDFSPREFIX, OntologyPREFIX, LIMIT);

        Logger.info("Query -----------------------------------");
        String labelQuery = String.format("%s%s SELECT ?x ?xlabel ?y ?ylabel " +
                        "WHERE {?x %s ?y." +
                        "?x rdfs:label ?xlabel." +
                        "?y rdfs:label ?ylabel. " +
                        "FILTER (langMatches( lang(?xlabel), \"en\" ) ) " +
                        "FILTER (langMatches( lang(?ylabel), \"en\" ) )}" +
                        "LIMIT 10"
                , RDFSPREFIX, OntologyPREFIX, relation.toString());


        Logger.info(labelQuery);

        ResultSet resultSet = executeQuery(labelQuery);
        List<ResourcePair> resourceList = new ArrayList<ResourcePair>();

        int count = 0;
        while (resultSet.hasNext()) {
            count++;
            QuerySolution soln = resultSet.nextSolution();

            Resource resourceSrc = soln.getResource("x");
            Resource resourceTarget = soln.getResource("y");
            Literal srcLabel = soln.getLiteral("xlabel");
            Literal trgLabel = soln.getLiteral("ylabel");

            Logger.info(Integer.toString(count) + "-----------------------------------");
            Logger.info("ResouseSource:" + srcLabel);
            Logger.info("ResouseTarget:" + trgLabel);

            upb.de.kg.DataModel.Resource resSrc = new upb.de.kg.DataModel.Resource(resourceSrc.toString(), relation, srcLabel.toString());
            upb.de.kg.DataModel.Resource trgSrc = new upb.de.kg.DataModel.Resource(resourceSrc.toString(), relation, trgLabel.toString());

            ResourcePair resourcePair = new ResourcePair(resSrc, trgSrc, relation);
            resourceList.add(resourcePair);
        }
        return resourceList;

    }


}
