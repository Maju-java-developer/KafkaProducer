FROM jenkins/jenkins:lts

USER root

# Install docker CLI
RUN apt-get update && apt-get install -y docker.io curl

# Install docker-compose
RUN curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" \
    -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose

# Optional: install Maven, Git, Node.js, etc. if needed
# RUN apt-get install -y maven git nodejs

# Add jenkins user to docker group
RUN groupadd -g 999 docker || true && usermod -aG docker jenkins

USER jenkins
