package net.jp.garlands.simplerxjava;

import net.jp.garlands.simplerxjava.model.Repository;
import net.jp.garlands.simplerxjava.model.Contributor;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubRxApi {
    @GET("users/{user}/repos")
    Observable<List<Repository>> listRepos(@Path("user") String user);

    @GET("repos/{user}/{repo}/contributors")
    Observable<List<Contributor>> listRepoContributors(
            @Path("user") String user,
            @Path("repo") String repo);
}
