------------- Configure Kafka on Windows -----------

Step-1: Install and Configure Zookeeper.

Step-2: Install and Configure Kafka.


---- Step-1: Install and Configure Zookeeper ----

1. Dowload the Zookeeper from officail site -
https://zookeeper.apache.org/releases.html

2. Right-click on the file, and extract it at the same location using 7-zip.

   Note the -bin appended to the name. If you don’t see this then you’ve downloaded and extracted the wrong file. Go back to the mirror.
   This is important otherwise when you start the Zookeeper server, you’ll get an error like this

3. Copy the Zookeeper folder at as you want as installation files.

4. Go to C:\apache-zookeeper-3.6.1-bin\apache-zookeeper-3.6.1-bin\conf 
	a) Rename the zoo_sample.cfg file to zoo.cfg
	b) Open it with Visual Studio Code, you can open in any text editor.
		dataDir=../logs
		
5. Set user variable 
	ZOOKEEPER_HOME = C:\apache-zookeeper-3.6.1-bin\apache-zookeeper-3.6.1-bin
	
6. Set Set System Variable
	double click on the Path and add the following at the end
	%ZOOKEEPER_HOME%\bin
	
7. Open Command Prompt
	Open a command prompt in your zookeeper bin directory and type in

	zkserver

	It’ll start spewing out a whole bunch of messages.
	Now Zookeeper server is running on localhost:2181. The AdminServer on port 8080 is a new addition. We can use that port on our browser to monitor zookeeper.

	However, you can’t go to port 2181 where zookeeper is running. Zookeeper is for Kafka to use as a core kernel of a Kafka Cluster. If you navigate to that port on your browser, which will send some TCP traffic to it that it's not expecting, you’ll crash the server. 



   ----- Step-2: Install and Configure Kafka.-------
1. Download the Kafka as per your Scala Version from official site.
	https://kafka.apache.org/downloads
	
2. Use 7-zip to extract the tgz to a tar file. Then use 7-zip to extract the tar file to a location as installation folder.

3. We’re not going to be setting any environment variables for Kafka. Kafka is the one looking for zookeeper and JDK. 
   Even the producers and the consumers live within the Kafka ecosystem. They are not separate applications that’d be looking to find Kafka on your computer.
   
4. There is a sample server.properties file at 'C:\kafka_2.12-2.5.0\config'.

	a) If we need multiple brokers then duplicate this file once for each broker. For example, if you need 2 message brokers then you’ll end up with server.b1.properties and server.b2.properties.
	   In each file, you’ll change the following —
	   broker.id=0
	   broker.id=1
	   broker.id=2
	   
	 But Here, we will have only one so default is fine!
	 
	 b) log.dirs=C:\Apache\kafka_2.12–2.3.1\logs
	 
	 c) You’ll notice the timeout values, partition values, and default Zookeeper port number which all would come in handy later for debugging if problems arise.

	 d) By default, Apache Kafka will run on port 9092 and Apache Zookeeper will run on port 2181.

   With that our configuration for Kafka is done. Let’s fire up the server.


5. To Running Kafka: Make sure that Zookeeper server is still running.

6. Open Terminal on windows directory in your Kafka\bin installed directory. 
	Ex: C:\kafka_2.12-2.5.0\bin\windows
	
7. Run the below command to start kafka server 
	kafka-server-start.bat C:\kafka_2.12-2.5.0\config\server.properties
	
   You’ll see some activity in your Zookeeper terminal. It might take a new snapshot and start a new log file. At this point your Kafka is up and running.

   Keep in Mind:
		The dataDir which we set up as the logs directory will start to fill up pretty fast with the snapshots.	So clean this directory yourself. 
		
		
		
----------- Create Topic ---------

1. kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test


----------- Creating a Producer and Consumer to Test Server ---------------

	kafka-console-producer.bat --broker-list localhost:9092 --topic test


	Before kafka version 2.0 (<2.0):
	kafka-console-consumer.bat --zookeeper localhost:2181 --topic test

	After kafka version 2.0 (>= 2.0): 
	kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test
	
	
	Now type anything in the producer command prompt and press Enter, and you should be able to see the message in the other consumer command prompt.
	

---------- Frequently Used some userful command ---------------	
	
	1. List Topics:
	   kafka-topics.bat --list --zookeeper localhost:2181 
	
	2. Describe Topic: 
	   kafka-topics.bat --describe --zookeeper localhost:2181 --topic [Topic Name]
	
	3. Delete Topic: 
       kafka-run-class.bat kafka.admin.TopicCommand --delete --topic [topic_to_delete] --zookeeper localhost:2181



-----------  Let us write Spark Stream Program -------------
1. Setup scala and spark environment, below is Git URL and YouTube Link-

2. Additionally downloaded below 2 jars and add into build path-
	org.apache.servicemix.bundles.kafka-clients-2.5.0_1
	spark-streaming-kafka-0-10_2.12-2.4.0
	
3. KafkaSparkStreaming.scala in this repository.


