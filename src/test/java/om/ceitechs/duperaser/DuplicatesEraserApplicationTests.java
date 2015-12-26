package om.ceitechs.duperaser;

import jdk.nashorn.internal.ir.annotations.Ignore;
import om.ceitechs.duperaser.domain.ConstituencyEntity;
import om.ceitechs.duperaser.domain.ConstituencyResult;
import om.ceitechs.duperaser.repositories.ResultsRepository;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DuplicatesEraserApplication.class)
public class DuplicatesEraserApplicationTests {

    @Autowired
    public ResultsRepository repository;

	@Ignore
	public void contextLoads() {
        //System.out.println(repository.count() + " Count");
        ConstituencyResult result = repository.findOne("AVCus0nTpcW_ys1ZLfBd");
        assertNotNull(result);
        System.out.println(result);
        //assertTrue(result.getCONSTITUENCY().equalsIgnoreCase("TEMEKE"));
	}

    @Ignore
    public void getAllTest(){
      repository.findAll().forEach(r -> System.out.println(new ConstituencyEntity(r)));
    }

    @Ignore
    public void getRTTest(){
        List<ConstituencyResult> rtList = new ArrayList<>();
        List<String> dup = new ArrayList<>();
        repository.findAll().forEach(r -> {
            if(r.getText().contains("RT @")) rtList.add(r);
        });


        Map<String, List<ConstituencyEntity>> rtGroups = rtList.stream()
                .map(ConstituencyEntity::new)
                .collect(Collectors.groupingBy(ConstituencyEntity::getName));

        rtGroups.forEach((n,l) -> {
            if(l.size() > 1) {
                for(int i =1; i< l.size(); i++) dup.add(l.get(i).getId());
            }
        });

        System.out.println(Arrays.toString(dup.toArray()));


    }

    @Ignore
    public void getAllDuplicates(){
        System.out.println("Duplicate Size is " + DuplicatesFindUtility.findDuplicateResultsIds(repository.findAll()).size());
    }


    @Test
    public void DeleteDuplicates(){
        DuplicatesFindUtility.findAndDeleteDuplicateResultsIds(repository.findAll());
    }


}
