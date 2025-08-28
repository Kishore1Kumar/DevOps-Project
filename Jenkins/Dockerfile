# Fully loaded Jenkins LTS with plugins + JCasC + Docker CLI (client)
FROM jenkins/jenkins:lts

USER root

# Install Docker CLI so Jenkins can run docker builds on a remote Docker host (or local if you wire it)
RUN apt-get update && \
    apt-get install -y curl apt-transport-https ca-certificates gnupg && \
    install -m 0755 -d /etc/apt/keyrings && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg && \
    chmod a+r /etc/apt/keyrings/docker.gpg && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian $(. /etc/os-release; echo $VERSION_CODENAME) stable" \
      > /etc/apt/sources.list.d/docker.list && \
    apt-get update && apt-get install -y docker-ce-cli && \
    rm -rf /var/lib/apt/lists/*

# Preinstall essential plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Jenkins Configuration as Code (JCasC)
COPY casc/jenkins.yaml /usr/share/jenkins/ref/casc/jenkins.yaml
ENV CASC_JENKINS_CONFIG=/usr/share/jenkins/ref/casc/jenkins.yaml

# Disable setup wizard (we use JCasC)
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# Make sure jenkins owns its home and ref dirs
RUN chown -R jenkins:jenkins /var/jenkins_home /usr/share/jenkins/ref

USER jenkins

# (No CMD needed; base image provides)
