# IZS - Kafka Academy

This repository allows to setup a Docker environment for the Kafka Academy:

* 3-node Zookeeper ensemble
* 3-node Kafka cluster 
* Kafka Connect
* 2 PostgreSQL instances (a source DB and a target DB)
* Confluent Control Center
* Confluent Schema Registry

## How to run

Be sure to have Docker installed and set Docker memory to a minimum of 8GB. 

<img width="934" alt="Screenshot 2021-10-21 at 15 30 29" src="https://user-images.githubusercontent.com/12952543/138288132-d2922cc2-12ea-40c4-9e3a-a0b33ef3406c.png">

In order to prevent conflicts with existent Docker containers i suggest to stop all your running containers with the following:

```
docker stop $(docker ps -a -q)
```

Then run the following command from the repository root directory:

```
docker-compose up -d
```

It will take some time to download all the Docker images. 
By running the following command you can see if everything is started up:

```
docker-compose ps
```

The output should be something like:

```
NAME              IMAGE                                       COMMAND                  SERVICE           CREATED         STATUS                   PORTS
broker1           confluentinc/cp-enterprise-kafka            "/etc/confluent/dock…"   broker1           4 minutes ago   Up 4 minutes             0.0.0.0:9092->9092/tcp, 0.0.0.0:29092->29092/tcp
broker2           confluentinc/cp-enterprise-kafka            "/etc/confluent/dock…"   broker2           4 minutes ago   Up 4 minutes             0.0.0.0:9093->9093/tcp, 9092/tcp, 0.0.0.0:39092->39092/tcp
broker3           confluentinc/cp-enterprise-kafka            "/etc/confluent/dock…"   broker3           4 minutes ago   Up 4 minutes             0.0.0.0:9094->9094/tcp, 9092/tcp, 0.0.0.0:49092->49092/tcp
connect           confluentinc/cp-kafka-connect               "/etc/confluent/dock…"   connect           4 minutes ago   Up 4 minutes (healthy)   0.0.0.0:8083->8083/tcp, 9092/tcp
control-center    confluentinc/cp-enterprise-control-center   "/etc/confluent/dock…"   control-center    4 minutes ago   Up 4 minutes             0.0.0.0:9021->9021/tcp
postgres          postgres                                    "docker-entrypoint.s…"   postgres          4 minutes ago   Up 4 minutes             0.0.0.0:5432->5432/tcp
postgresTarget    postgres                                    "docker-entrypoint.s…"   postgresTarget    4 minutes ago   Up 4 minutes             0.0.0.0:5433->5432/tcp
schema-registry   confluentinc/cp-schema-registry             "/etc/confluent/dock…"   schema-registry   4 minutes ago   Up 4 minutes (healthy)   0.0.0.0:8081->8081/tcp
zk1               confluentinc/cp-zookeeper                   "/etc/confluent/dock…"   zk1               4 minutes ago   Up 4 minutes             2181/tcp, 2888/tcp, 0.0.0.0:22181->22181/tcp, 0.0.0.0:22888->22888/tcp, 3888/tcp, 0.0.0.0:23888->23888/tcp
zk2               confluentinc/cp-zookeeper                   "/etc/confluent/dock…"   zk2               4 minutes ago   Up 4 minutes             2181/tcp, 2888/tcp, 0.0.0.0:32181->32181/tcp, 0.0.0.0:32888->32888/tcp, 3888/tcp, 0.0.0.0:33888->33888/tcp
zk3               confluentinc/cp-zookeeper                   "/etc/confluent/dock…"   zk3               4 minutes ago   Up 4 minutes             2181/tcp, 2888/tcp, 0.0.0.0:42181->42181/tcp, 0.0.0.0:42888->42888/tcp, 3888/tcp, 0.0.0.0:43888->43888/tcp
```

## How to stop

Run the following:

```
docker-compose down -v
```
