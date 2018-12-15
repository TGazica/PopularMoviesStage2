package org.tomislavgazica.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailersResponse {

    @SerializedName("results")
    @Expose
    private List<Trailer> trailers = new ArrayList<>();

    public TrailersResponse() {
    }

    public TrailersResponse(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
