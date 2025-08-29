# Jenkins Master Dockerfile (local dev)
FROM jenkins/jenkins:lts-jdk17

USER root
RUN apt-get update && apt-get install -y --no-install-recommends curl bash python3 \
    && rm -rf /var/lib/apt/lists/*

# Copy plugin list & install (cache layer if unchanged)
USER jenkins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Keep setup wizard (remove this comment & set JAVA_OPTS env later to skip if desired)
EXPOSE 8080 50000