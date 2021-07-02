package net.jp.garlands.simplerxjava;

import net.jp.garlands.simplerxjava.model.Contributor;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class GitHubRxService {

    private net.jp.garlands.simplerxjava.GitHubRxApi gitHubApi;

    GitHubRxService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        gitHubApi = retrofit.create(net.jp.garlands.simplerxjava.GitHubRxApi.class);
    }

    Observable<String> getTopContributors(String userName) {
        return gitHubApi.listRepos(userName)
                .flatMapIterable(x -> x)
                .flatMap(repo -> gitHubApi.listRepoContributors(userName, repo.getName()))
                .flatMapIterable(x -> x)
                .filter(c -> c.getContributions() > 100)
                .sorted((a, b) -> b.getContributions() - a.getContributions())
                .map(Contributor::getName)
                .distinct();
    }
}