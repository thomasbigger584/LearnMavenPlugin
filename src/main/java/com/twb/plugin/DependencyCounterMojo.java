package com.twb.plugin;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;
import java.util.function.Predicate;

@Mojo(name = "dependency-counter", defaultPhase = LifecyclePhase.COMPILE)
public class DependencyCounterMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "scope")
    String scope;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        @SuppressWarnings("unchecked")
        List<Dependency> dependencies = project.getDependencies();
        long numDependencies = dependencies.stream()
                .filter(new Predicate<Dependency>() {
                    @Override
                    public boolean test(Dependency dependency) {
                        return (scope == null || scope.isEmpty()) || scope.equals(dependency.getScope());
                    }
                })
                .count();
        getLog().info("Number of Dependencies: " + numDependencies);
    }
}
