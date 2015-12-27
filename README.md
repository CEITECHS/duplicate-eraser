# duplicate-eraser
ELK stack, with twitter stream input. 

## USE CASE, FOR LEARNING PURPOSES ONLY
Streamed tweets with the top two presidential contenders poll results at a constituency level as it was made public and shared on various social media and on twitter in particular.
With the help of open-source technologies, aim is to showcase how much insights can be gained from unstructured data, leading to informed decision all around for various purposes.
**BE REMINDED THAT** : *Care has not been taken to ensure the accuracy, completeness and reliability of the tweets and it`s contained information , we assumes no responsibility therefore.*  

##Sample tweets 
> - Jimbo la Mwanga, Kilimanjaro Kura: 41,136 @MagufuliJP (CCM):  25,738 @edwardlowassatz (Chadema): 15,148 #tanzaniadecides
> - Jimbo Hanang, Manyara Kura: 99,464 @MagufuliJP (CCM):  63,205 @edwardlowassatz (Chadema): 32,367 #tanzaniadecides
> - Jaji Lubuva Matokeo ya Urais Jimbo la Lindi Mjini Kura: 38,992 @MagufuliJP (CCM): 21,088 @edwardlowassatz (Chadema): 17,607 #tanzaniadecides

###Centralize Data Processing of All Types Ex. TWEETS
>Logstash is a data pipeline that helps you process logs and other event data from a variety of systems. With 200 plugins and counting, Logstash can connect to a variety of sources and stream data at scale to a central analytics system. <br/>

- In the context used twitter as a datasource and elasticsearch as central data-store for analytics, the configuration looks like

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
```


# Resulted Dashboard
![screenshot](dashboard-screenshot.png)

**DISCLAIMER :**
*Care has not been taken to ensure the accuracy, completeness and reliability of the tweets and it`s contained information , we assumes no responsibility therefore.*

#References
- [Grok Debugger](https://grokdebug.herokuapp.com/) 
- [Logistash](https://www.elastic.co/products/logstash)
- [Elasticsearch](https://www.elastic.co/products/elasticsearch)
- [Kibana](https://www.elastic.co/products/kibana)
- [Elasticsearch Java API](https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html)
- [Spring Data Elasticsearch](https://github.com/spring-projects/spring-data-elasticsearch)
- [Indexing Twitter With Logstash and Elasticsearch](http://david.pilato.fr/blog/2015/06/01/indexing-twitter-with-logstash-and-elasticsearch/)