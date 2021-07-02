package net.jp.garlands.simplerxjava;

import net.jp.garlands.simplerxjava.model.Contributor;
import net.jp.garlands.simplerxjava.model.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubBasicService {
    private GitHubBasicApi gitHubApi;

    GitHubBasicService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubApi = retrofit.create(GitHubBasicApi.class);
    }

    List<String> getTopContributors(String userName) {
        try {
            List<Repository> repos = gitHubApi
                    .listRepos(userName)
                    .execute()
                    .body();

            repos = repos != null ? repos : Collections.emptyList();

            return repos.stream()
                    .flatMap(repo -> getContributors(userName, repo))
                    .sorted((a, b) -> b.getContributions() - a.getContributions())
                    .map(Contributor::getName)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Stream<Contributor> getContributors(String userName, Repository repo) {
        List<Contributor> contributors = null;
        try {
            contributors = gitHubApi
                    .listRepoContributors(userName, repo.getName())
                    .execute()
                    .body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        contributors = contributors != null ? contributors : Collections.emptyList();

        return contributors.stream()
                .filter(c -> c.getContributions() > 100);
    }
}
