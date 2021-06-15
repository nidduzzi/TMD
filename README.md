# TMD

Ahmad Izzuddin 1908919  

## Janji

Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Credits

music from [Bensound.com](Bensound.com)

## Video

[Youtube link](https://youtu.be/a4WWBewuOsU)  

## Database Setup

Run the mysql script [`highestlevel.sql`](highestlevel.sql)

## Compilation

Requires [Apache Ant](https://ant.apache.org/) and [JDK16](https://jdk.java.net/16/)  
Execute from archive root  

```shell
ant -f TMD -Dnb.internal.action.name=rebuild clean jar
```

## Execution

Execute from archive root directory

Run JAR

```shell
java -jar ./TMD/dist/TMD.jar
```

Run classes

```shell
java -cp ./TMD/build/classes;mysql-connector-java-5.0.5.jar view.Main
```
