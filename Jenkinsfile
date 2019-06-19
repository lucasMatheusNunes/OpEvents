node {

  try {

    buildDockerBuilder {
      dockerRepositoryName = "kafka-ops-manager"
      dockerFileLocation = "."
      team = "kafka-ops-manager"
    }

    mvnBuildWithCompose {
      composeFileName = "."
      composeService = "kafka-ops-manager"
      composeProjectName = "kafka-ops-manager"
      team = "kafka-ops-manager"
      useBuilder = "true"
    }

    buildDockerContainer {
      dockerRepositoryName = "kafka-ops-manager"
      dockerFileLocation = "."
      team = "kafka-ops-manager"
    }

    deployDockerService {
      dockerRepositoryName = "kafka-ops-manager"
      dockerSwarmStack = "kafka-ops-manager"
      dockerService = "application"
      team = "kafka-ops-manager"
    }

    deployDockerServiceK8s {
      microservice = "kafka-ops-manager"
    }

    grenRelease{}

    deleteReleaseBranch{}

  } catch (e) {

      notifyBuildStatus {
        buildStatus = "FAILED"
      }
      throw e

  }

}