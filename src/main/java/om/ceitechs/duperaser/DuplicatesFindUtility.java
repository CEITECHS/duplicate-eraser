package om.ceitechs.duperaser;

import om.ceitechs.duperaser.domain.ConstituencyEntity;
import om.ceitechs.duperaser.domain.ConstituencyResult;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by iddymagohe on 10/29/15.
 */
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
