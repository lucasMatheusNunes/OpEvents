package br.com.zup.op.events

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.info.GitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class SwaggerConfig {

  @Autowired
  lateinit var build: Optional<BuildProperties>
  @Autowired
  lateinit var git: Optional<GitProperties>

  @Bean
  fun api(): Docket {
    var version = "1.0"
    if (build.isPresent && git.isPresent) {
      var buildInfo = build.get()
      var gitInfo = git.get()
      version = "${buildInfo.version}-${gitInfo.shortCommitId}-${gitInfo.branch}"
    }
    return Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo(version))
        .select()
        .apis(RequestHandlerSelectors.basePackage("br.com.zup.op.events.interfaces.controller"))
        .paths(PathSelectors.any())
        .build()
  }

  private fun apiInfo(version: String): ApiInfo {
    return ApiInfoBuilder()
        .title("API - Republish Events")
        .description("Republish operations of events Kafka")
        .version(version)
        .build()
  }

}

