package org.grails.gradle.plugin.util

import groovy.transform.CompileStatic
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer

/**
 * @author Graeme Rocher
 * @since 3.0
 */
@CompileStatic
class SourceSets {

    /**
     * Finds the main SourceSet for the project
     * @param project The project
     * @return The main source set or null if it can't be found
     */
    static SourceSet findMainSourceSet(Project project) {
       return findSourceSet(project, SourceSet.MAIN_SOURCE_SET_NAME)
    }

    /**
     * Finds the main SourceSet for the project
     * @param project The project
     * @return The main source set or null if it can't be found
     */
    static SourceSet findSourceSet(Project project, String name) {
        SourceSetContainer sourceSets = findSourceSets(project)
        return sourceSets?.find { SourceSet sourceSet ->
            sourceSet.name == name
        } as SourceSet
    }

    static SourceSetContainer findSourceSets(Project project) {
        JavaPluginConvention plugin = project.getConvention().getPlugin(JavaPluginConvention)
        SourceSetContainer sourceSets = plugin?.sourceSets
        return sourceSets
    }

    static String resolveGrailsAppDir(Project project) {
        List<String> grailsAppDirs = ['grails-app', 'app']
        String grailsAppDir = grailsAppDirs.find { String dir -> project.file(dir).exists() }
        if (!grailsAppDir) {
            throw new GradleException("Grails requires an application directory : 'grails-app' or 'app'.")
        }
        return grailsAppDir
    }
}
