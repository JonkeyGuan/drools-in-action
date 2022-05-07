package com.droolsinaction.location;

import java.io.Serializable;

public class QueryResult implements Serializable {

    static final long serialVersionUID = 1L;

    private Query query;

    private String result;

    public QueryResult() {
    }

    public QueryResult(Query query, String result) {
        this.query = query;
        this.result = result;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return query + " " + result;
    }

}
