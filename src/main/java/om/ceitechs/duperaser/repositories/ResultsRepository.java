package om.ceitechs.duperaser.repositories;

import om.ceitechs.duperaser.domain.ConstituencyResult;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by iddymagohe on 10/28/15.
 */

public interface ResultsRepository extends ElasticsearchRepository<ConstituencyResult,String>{

    //List<ConstituencyResult> findByLastname(String lastname);

}
