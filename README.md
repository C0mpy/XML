# XML

Pokretanje:
- clone repo
- install Marklogic bazu podataka sa: http://www.marklogic.com/
- tutorijal za podesavanje baze i pristup iz Java programa: https://developer.marklogic.com/learn/java/setup, uname, pass: root
- napraviti novu schemu 'xml' i dodati u njoj forest "NewForest" (mozda ime foresta nije ni bitno)
- pokrenuti marklogic: find Marklogic Start
- u dir /XML/static pozvati: bower install za instalaciju svih frontend zavisnosti

napomena:
- nakon svakog menjanja xsd schema, klase iz modela se moraju obrisati i izgenerisati iznova iz promenjenih schema:
  - pozicionirati se u /XML/src/main/resources/schema
  - pozvati: xjc -d ../../java/ -p [imepaketa] [imescheme.xsd]    ovom komandom kazemo da se klase izgenerisu u direktorijumu ../../java/ u paketu [imepaketa] iz scheme [imescheme.xsd]
  - primeri:
    - xjc -d ../../java -p model amandman.xsd
    - xjc -d ../../java -p model korisnik.xsd
    - xjc -d ../../java -p model akt.xsd
