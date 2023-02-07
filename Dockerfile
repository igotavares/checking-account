ARG BUILD_IMAGE=maven:3.8.5-openjdk-11-slim
ARG RUNTIME_IMAGE=openjdk:11-jre-slim

#####################################################
###  Stage: Compile                               ###
#####################################################

FROM ${BUILD_IMAGE} as build
WORKDIR /app
COPY . .
RUN mvn -e -B compile -DskipTests -Djacoco.skip=true

#####################################################
###  Stage(Optional): Run Unit Tests              ###
#####################################################

FROM build as test
ARG SKIPTESTS=true
WORKDIR /app
RUN if [ "$SKIPTESTS" = "false" ] ; \
    then mvn -e -B test -Dmaven.test.failure.ignore=true; \
    fi

#####################################################
###  Stage(Optional): Sonar Analysis              ###
#####################################################

FROM test as sonar
ARG SONAR_ENABLED=false
ARG SONAR_URL=''
ARG SONAR_USERNAME=''
WORKDIR /app
RUN if [ "$SONAR_ENABLED" = "true" ] ; \
    then mvn -e -B sonar:sonar \
        -Dsonar.host.url=${SONAR_URL} \
        -Dsonar.login=${SONAR_USERNAME}; \
    fi

#####################################################
###  Stage: Package                               ###
#####################################################

FROM sonar as package
WORKDIR /app
RUN mvn -e -B package -DskipTests -Djacoco.skip=true

#####################################################
### Stage: Run Image                              ###
#####################################################

FROM ${RUNTIME_IMAGE}
COPY --from=package app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=env","-jar","/app.jar"]	



