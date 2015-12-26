package om.ceitechs.duperaser.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by iddymagohe on 10/28/15.
 */

@Document(indexName = "constituencywitter", type = "constituencytweet")
@Getter
@Setter
public class ConstituencyResult {

    @Id
    private String id;
    @Field(type = FieldType.String) private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstituencyResult result = (ConstituencyResult) o;

        return id.equals(result.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ConstituencyResult{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
