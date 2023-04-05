FROM openjdk:17
LABEL "com.adidas.pac.vendor"="Carlos Diaz Maya"
LABEL "version"="0.0.1"
LABEL "description"="This application allow processing of payments"
COPY target/PaymentAuthorizeChallenge-0.0.1-SNAPSHOT-executable.jar authorize.jar
ENTRYPOINT ["java","-jar","/authorize.jar"]
#ENTRYPOINT ["/bin/bash"]