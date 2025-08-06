FROM jenkins/jenkins:lts

USER root

# install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    mvn -v

    # Install docker CLI
RUN apt-get update && apt-get install -y docker.io curl

# Install docker-compose
RUN curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" \
    -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose

# Set JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# Add jenkins user to docker group
RUN groupadd -g 999 docker || true && usermod -aG docker jenkins

USER jenkins
