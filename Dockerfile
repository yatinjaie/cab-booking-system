FROM maven:3.6.3-ibmjava-8-alpine AS MAVEN_TOOL_CHAIN
	
RUN apk add --update openssl


WORKDIR /build-env
ADD . /build-env
RUN apk update &&  apk add bash && apk add libstdc++
ENV LC_ALL=C
ENV LANG       en_US.UTF-8
ENV LANGUAGE   en_US:en


# Pre build commands
USER root
RUN wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/backend-project/springboot/maven/2.x/pre-build.sh
RUN chmod 775 ./pre-build.sh
RUN sh pre-build.sh

COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/

EXPOSE 8080

# Build the app
RUN wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/backend-project/springboot/maven/2.x/build.sh
RUN chmod 775 ./build.sh
RUN sh build.sh

# Add extra docker commands here (if any)...

# Run the app
RUN wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/backend-project/springboot/maven/2.x/run.sh
RUN chmod 775 ./run.sh
CMD sh run.sh
