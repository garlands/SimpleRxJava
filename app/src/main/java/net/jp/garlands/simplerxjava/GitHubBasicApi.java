package net.jp.garlands.simplerxjava;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubBasicApi {
    @GET("users/{user}/repos")
    Call<List> listRepos(@Path("user") String user);

    @GET("repos/{user}/{repo}/contributors")
    Call<List> listRepoContributors(
            @Path("user") String user,
            @Path("repo") String repo);
}
