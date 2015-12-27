# DATA ANALYSIS
ELK stack, with twitter stream input.

## USE CASE, FOR LEARNING PURPOSES ONLY
Streamed tweets with the top two presidential contenders poll results at a constituency level as it was made public and shared on various social media and on twitter in particular.<br/>
With the use open-source technologies, aim is to showcase how much insights can be gained from unstructured data, leading to informed decision all around for various purposes.<br/>
**BE REMINDED THAT** : *Care has not been taken to ensure the accuracy, completeness and reliability of the tweets and it`s contained information , we assumes no responsibility therefore.*  

##Sample tweets 
> - Jimbo la Mwanga, Kilimanjaro Kura: 41,136 @MagufuliJP (CCM):  25,738 @edwardlowassatz (Chadema): 15,148 #tanzaniadecides
> - Jimbo Hanang, Manyara Kura: 99,464 @MagufuliJP (CCM):  63,205 @edwardlowassatz (Chadema): 32,367 #tanzaniadecides
> - Jaji Lubuva Matokeo ya Urais Jimbo la Lindi Mjini Kura: 38,992 @MagufuliJP (CCM): 21,088 @edwardlowassatz (Chadema): 17,607 #tanzaniadecides

# Resulted Dashboard - Kibana
![screenshot](dashboard-screenshot.png)

**DISCLAIMER :**
*Care has not been taken to ensure the accuracy, completeness and reliability of the tweets and it`s contained information , we assumes no responsibility therefore.*


###Centralize Data Processing of All Types Ex. TWEETS
>Logstash is a data pipeline that helps you process logs and other event data from a variety of systems. With 200 plugins and counting, Logstash can connect to a variety of sources and stream data at scale to a central analytics system. <br/>

- In this context used twitter as a logistash datasource and elasticsearch as central data-store for analytics, refer folder **config/** sample configuration looks like

``` ruby
input {
  twitter {
      consumer_key => "<<CONSUMER_KEY>>"
      consumer_secret => "<<CONSUMER_SECRET>>"
      keywords => ["Kura", "Jimbo "]
      oauth_token => "<<AUTH_TOKEN>>"
      oauth_token_secret => "<<AUTH_TOKEN_SECRET>>"
      full_tweet => true
   }
}

output {
 if "Kata ya" in [text] and "Kura:" in [text] and "@MagufuliJP (CCM):" in [text] and "@edwardlowassatz (Chadema):" in [text]{
     stdout { codec => rubydebug }
    elasticsearch {
	    protocol => "http"
	    host => "localhost"
	    index => "twitter"
	    document_type => "tweet"
  }
 }

}
run command : bin/logstash -f Tanzania-uchaguzi-2015.conf
```

### DEALING WITH DUPLICATES
- Re-tweets etc
``` java
public class DuplicatesFindUtility {

    public static List<String> findDuplicateResultsIds(Iterable<ConstituencyResult> resultIterable){
        List<String> duplicateResultsId = new ArrayList<>();
        Map<String, List<ConstituencyEntity>> resultGroups = StreamSupport
                .stream(resultIterable.spliterator(), false)
                .map(ConstituencyEntity::new)
                .collect(Collectors.groupingBy(ConstituencyEntity::getName));

        resultGroups.forEach((n, l) -> {
            if (l.size() > 1) {
                System.out.println(n + " --- X " + l.size() );
                for (int i = 1; i < l.size(); i++) {
                    duplicateResultsId.add(l.get(i).getId());

                };
            }
        });

        return duplicateResultsId;
    }

    private static void deleteDuplicates(String index, String type, List<String> ids){
        Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        deleteDuplicateConstituencesByIds(client,index,type,ids);
    }

    public static void deleteDuplicateConstituencesByIds(Client client, String index, String type, List<String> iDsToDelete){
        iDsToDelete.forEach(id ->{
            DeleteResponse deleteResponse = client.prepareDelete(index,type,id)
                    .execute()
                    .actionGet();
            System.out.println(id +" >>>>> " +deleteResponse.isFound());
        });

    }

    public static void  findAndDeleteDuplicateResultsIds(Iterable<ConstituencyResult> resultIterable){
        System.out.println("1 : Identifying duplicates.......");
        List<String> duplicatesIds = findDuplicateResultsIds(resultIterable);
        System.out.println("2 : found " + duplicatesIds.size() + " duplicates ");
        if(duplicatesIds.size() > 0){
            System.out.println("3: Deleting duplicates please wait ......");
            deleteDuplicates("constituencywitter","constituencytweet",duplicatesIds);
            System.out.println("5: CleanUp completed successfully");
        }

    }

}
```

#References
- [Grok Debugger](https://grokdebug.herokuapp.com/)
- [Logistash](https://www.elastic.co/products/logstash)
- [Elasticsearch](https://www.elastic.co/products/elasticsearch)
- [Kibana](https://www.elastic.co/products/kibana)
- [Elasticsearch Java API](https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html)
- [Spring Data Elasticsearch](https://github.com/spring-projects/spring-data-elasticsearch)
- [Indexing Twitter With Logstash and Elasticsearch](http://david.pilato.fr/blog/2015/06/01/indexing-twitter-with-logstash-and-elasticsearch/)

[CEITECHS](http://www.ceitechs.com) is here to  help you Manage vast amount of data and gain valuable insights you've not imagined 